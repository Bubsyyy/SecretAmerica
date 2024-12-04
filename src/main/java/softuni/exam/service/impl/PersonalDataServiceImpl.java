package softuni.exam.service.impl;

import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xmls.PersonalDataDto;
import softuni.exam.models.dto.xmls.PersonalDataRootDto;
import softuni.exam.models.entity.Gender;
import softuni.exam.models.entity.PersonalData;
import softuni.exam.repository.PersonalDataRepository;
import softuni.exam.service.PersonalDataService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

//ToDo - Implement all the methods

@Service
public class PersonalDataServiceImpl implements PersonalDataService {

    private static final String PERSONAL_DATA_FILE_PATH = "src/main/resources/files/xml/personal_data.xml";
    private final PersonalDataRepository personalDataRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    @Autowired
    public PersonalDataServiceImpl(PersonalDataRepository personalDataRepository, ValidationUtil validationUtil, ModelMapper modelMapper, XmlParser xmlParser) {
        this.personalDataRepository = personalDataRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;

    }

    @Override
    public boolean areImported() {
        return this.personalDataRepository.count() > 0;
    }

    @Override
    public String readPersonalDataFileContent() throws IOException {
        return Files.readString(Path.of(PERSONAL_DATA_FILE_PATH));
    }

    @Override
    public String importPersonalData() throws JAXBException {


        PersonalDataRootDto personalDataRootDto = this.xmlParser.fromFile(PERSONAL_DATA_FILE_PATH, PersonalDataRootDto.class);
        StringBuilder sb = new StringBuilder();
        for(PersonalDataDto personalDataDto : personalDataRootDto.getPersonalDataDtos()){

            if(!validationUtil.isValid(personalDataDto) || this.personalDataRepository.findByCardNumber(personalDataDto.getCardNumber()).isPresent()){
                sb.append("Invalid personal data\n");
                continue;
            }

            PersonalData personalData = this.modelMapper.map(personalDataDto, PersonalData.class);
            personalData.setGender(Gender.valueOf(personalDataDto.getGender()));
            this.personalDataRepository.saveAndFlush(personalData);
            sb.append(String.format("Successfully imported personal data for visitor with card number %s\n", personalData.getCardNumber()));

        }



        return sb.toString();

    }
}
