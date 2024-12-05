package softuni.exam.service.impl;

import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xmls.PersonalDataRootDto;
import softuni.exam.models.dto.xmls.VisitorDto;
import softuni.exam.models.dto.xmls.VisitorRootDto;
import softuni.exam.models.entity.PersonalData;
import softuni.exam.models.entity.Visitor;
import softuni.exam.repository.AttractionRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.repository.PersonalDataRepository;
import softuni.exam.repository.VisitorRepository;
import softuni.exam.service.VisitorService;
import softuni.exam.util.XmlParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

//ToDo - Implement all the methods

@Service
public class VisitorServiceImpl implements VisitorService {

    private static final String VISITORS_PATH = "src/main/resources/files/xml/visitors.xml";
    private final VisitorRepository visitorRepository;
    private final XmlParser xmlParser;
    private final PersonalDataRepository personalDataRepository;
    private final ModelMapper modelMapper;
    private final AttractionRepository attractionRepository;
    private final CountryRepository countryRepository;

    @Autowired
    public VisitorServiceImpl(VisitorRepository visitorRepository, XmlParser xmlParser, PersonalDataRepository personalDataRepository, ModelMapper modelMapper, AttractionRepository attractionRepository, CountryRepository countryRepository) {
        this.visitorRepository = visitorRepository;
        this.xmlParser = xmlParser;
        this.personalDataRepository = personalDataRepository;
        this.modelMapper = modelMapper;
        this.attractionRepository = attractionRepository;
        this.countryRepository = countryRepository;

    }

    @Override
    public boolean areImported() {
        return this.visitorRepository.count() > 0;
    }

    @Override
    public String readVisitorsFileContent() throws IOException {
        return Files.readString(Path.of(VISITORS_PATH));
    }

    @Override
    public String importVisitors() throws JAXBException {


        VisitorRootDto visitorRootDto = this.xmlParser.fromFile(VISITORS_PATH, VisitorRootDto.class);
        StringBuilder sb = new StringBuilder();

        for(VisitorDto visitorDto:visitorRootDto.getVisitors()){

            PersonalData personalData = this.personalDataRepository.findById(visitorDto.getPersonalData()).get();

            if(this.visitorRepository.findByFirstNameAndLastName(visitorDto.getFirstName(),visitorDto.getLastName()).isPresent() || this.visitorRepository.findByPersonalData(personalData).isPresent() ) {

                sb.append("Invalid visitor\n");
                continue;

            }

            Visitor visitor = this.modelMapper.map(visitorDto, Visitor.class);

            visitor.setAttraction(this.attractionRepository.findById(visitorDto.getAttraction()).get());
            visitor.setCountry(this.countryRepository.findById(visitorDto.getCountry()).get());
            visitor.setPersonalData(personalData);

            this.visitorRepository.saveAndFlush(visitor);
            sb.append(String.format("Successfully imported visitor %s %s\n", visitor.getFirstName(), visitor.getLastName()));


        }
        return sb.toString();
    }
}
