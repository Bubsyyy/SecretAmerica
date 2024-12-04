package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.jsons.CountryImport;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

//ToDo - Implement all the methods
@Service
public class CountryServiceImpl implements CountryService {

    private static final String COUNTRIES_PATH = "src/main/resources/files/json/countries.json";
    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }
    @Override
    public boolean areImported() {
        return this.countryRepository.count() > 0;
    }

    @Override
    public String readCountryFileContent() throws IOException {
        return Files.readString(Path.of(COUNTRIES_PATH));
    }

    @Override
    public String importCountries() throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        CountryImport[] countryImports = this.gson.fromJson(
                new FileReader(COUNTRIES_PATH), CountryImport[].class);

        for (CountryImport countryImport : countryImports) {
            Optional<Country> optional = this.countryRepository.findByName(countryImport.getName());
            if (!this.validationUtil.isValid(countryImport) || optional.isPresent()) {
                sb.append("Invalid country\n");
                continue;
            }

            Country country = this.modelMapper.map(countryImport, Country.class);
            this.countryRepository.saveAndFlush(country);
            sb.append(String.format("Successfully imported country %s\n", country.getName()));
        }

        return sb.toString();
    }
    }

