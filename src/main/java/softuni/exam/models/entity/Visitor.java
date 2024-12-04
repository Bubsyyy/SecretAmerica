package softuni.exam.models.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "visitors", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"first_name", "last_name"})})
public class Visitor extends BaseEntity{

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @ManyToOne(optional = false)
    @JoinColumn(name = "attraction_id", referencedColumnName = "id")
    private Attraction attraction;

    @ManyToOne(optional = false)
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;

    @OneToOne(optional = false)
    @JoinColumn(name = "personal_data_id", referencedColumnName = "id", unique = true)
    private PersonalData personalData;

    public Visitor() {
    }

    public Visitor(String firstName, String lastName, Attraction attraction, Country country, PersonalData personalData) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.attraction = attraction;
        this.country = country;
        this.personalData = personalData;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Attraction getAttraction() {
        return attraction;
    }

    public void setAttraction(Attraction attraction) {
        this.attraction = attraction;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public PersonalData getPersonalData() {
        return personalData;
    }

    public void setPersonalData(PersonalData personalData) {
        this.personalData = personalData;
    }
}
