package softuni.exam.models.dto.jsons;

import com.google.gson.annotations.Expose;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class CountryImport implements Serializable {

    @Expose
    @Size(min=3, max=40)
    private String name;

    @Expose
    @Positive
    private Double area;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }
}
