package softuni.exam.models.dto.xmls;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "visitors")
@XmlAccessorType(XmlAccessType.FIELD)
public class VisitorRootDto implements Serializable {

    @XmlElement(name = "visitor")
    private List<VisitorDto> visitors;

    public List<VisitorDto> getVisitors() {
        return visitors;
    }

    public void setVisitors(List<VisitorDto> visitors) {
        this.visitors = visitors;
    }
}
