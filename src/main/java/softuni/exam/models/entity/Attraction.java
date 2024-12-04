package softuni.exam.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
@Table(name = "attractions")
public class Attraction extends BaseEntity {

    @Column(name = "description", nullable = false)

    private String description;

    @Column(name = "elevation", nullable = false)
    @PositiveOrZero(message = "Elevation must be zero or positive.")
    private Integer elevation;

    @Column(name = "name", nullable = false)

    private String name;

    @Column(name = "type",nullable = false)

    private String type;

    @OneToMany(mappedBy = "attraction")
    private Set<Visitor> visitors;

    @ManyToOne(optional = false)
    @JoinColumn(name = "country_id" , referencedColumnName = "id")
    private Country country;

    public Attraction() {
    }

    public Attraction(String description, Integer elevation, String name, String type, Set<Visitor> visitors, Country country) {
        this.description = description;
        this.elevation = elevation;
        this.name = name;
        this.type = type;
        this.visitors = visitors;
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getElevation() {
        return elevation;
    }

    public void setElevation(Integer elevation) {
        this.elevation = elevation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Visitor> getVisitors() {
        return visitors;
    }

    public void setVisitors(Set<Visitor> visitors) {
        this.visitors = visitors;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
