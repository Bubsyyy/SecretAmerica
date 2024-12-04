package softuni.exam.models.dto.xmls;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "personal_datas")
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonalDataRootDto implements Serializable {

    @XmlElement(name = "personal_data")
    private List<PersonalDataDto> personalDataDtos;

    public List<PersonalDataDto> getPersonalDataDtos() {
        return personalDataDtos;
    }

    public void setPersonalDataDtos(List<PersonalDataDto> personalDataDtos) {
        this.personalDataDtos = personalDataDtos;
    }
}
