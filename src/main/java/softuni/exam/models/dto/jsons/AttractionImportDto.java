package softuni.exam.models.dto.jsons;

import com.google.gson.annotations.Expose;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class AttractionImportDto implements Serializable {

    @Expose
    @Size(min = 5, max = 40)
     private String name;

    @Expose
    @Size(min = 10, max = 100)
     private String description;

    @Expose
    @Size(min = 3, max = 30)
     private String type;

    @Expose
    @PositiveOrZero
     private Integer elevation;

    @Expose
     private long country;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getElevation() {
        return elevation;
    }

    public void setElevation(Integer elevation) {
        this.elevation = elevation;
    }

    public long getCountry() {
        return country;
    }

    public void setCountry(long country) {
        this.country = country;
    }
}
