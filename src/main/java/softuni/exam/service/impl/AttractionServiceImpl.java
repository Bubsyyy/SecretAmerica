package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.jsons.AttractionImportDto;
import softuni.exam.models.entity.Attraction;
import softuni.exam.repository.AttractionRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.AttractionService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

//ToDo - Implement all the methods
@Service

public class AttractionServiceImpl implements AttractionService {

    private static final String ATTRACTIONS_FILE_PATH = "src/main/resources/files/json/attractions.json";
    private final AttractionRepository attractionRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final CountryRepository countryRepository;

    @Autowired
    public AttractionServiceImpl(AttractionRepository attractionRepository, ValidationUtil validationUtil, ModelMapper modelMapper, Gson gson, CountryRepository countryRepository) {
        this.attractionRepository = attractionRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.countryRepository = countryRepository;
    }

    @Override
    public boolean areImported() {
        return this.attractionRepository.count() > 0;
    }

    @Override
    public String readAttractionsFileContent() throws IOException {
        return Files.readString(Path.of(ATTRACTIONS_FILE_PATH));
    }

    @Override
    public String importAttractions() throws IOException {

        StringBuilder sb = new StringBuilder();
        AttractionImportDto[] attractionImportDtos = this.gson.fromJson(readAttractionsFileContent(), AttractionImportDto[].class);

        for (AttractionImportDto attractionImportDto : attractionImportDtos) {
            Optional<Attraction> optional = this.attractionRepository.findByName(attractionImportDto.getName());
            if (!this.validationUtil.isValid(attractionImportDto) || optional.isPresent()) {
                sb.append("Invalid attraction\n");
                continue;
            }

            Attraction attraction = this.modelMapper.map(attractionImportDto, Attraction.class);

            attraction.setCountry(this.countryRepository.getById(attractionImportDto.getCountry()));
            this.attractionRepository.saveAndFlush(attraction);
            sb.append(String.format("Successfully imported attraction %s\n", attraction.getName()));
        }

        return sb.toString();

    }

    @Override
    public String exportAttractions() {
        return null;
    }
}
