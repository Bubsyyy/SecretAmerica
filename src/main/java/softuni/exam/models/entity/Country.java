package softuni.exam.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
@Table(name = "countries")
public class Country extends BaseEntity {

    @Column(name = "area")
    private Double area;

    @Column(name = "name", nullable = false, unique = true)

    private String name;

    public Country() {
    }

    public Country(Double area, String name) {
        this.area = area;
        this.name = name;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
