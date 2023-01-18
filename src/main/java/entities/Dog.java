package entities;

import dtos.DogDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NamedQuery(name="Dog.deleteAllRows",query = "DELETE from Dog")
@Table(name = "dog")
public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 45)
    @NotNull
    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Size(max = 45)
    @NotNull
    @Column(name = "breed", nullable = false, length = 45)
    private String breed;

    @Size(max = 45)
    @Column(name = "image", length = 45)
    private String image;

    @NotNull
    @Lob
    @Column(name = "gender", nullable = false)
    private String gender;

    @NotNull
    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    @ManyToMany(mappedBy = "dogs")
    @JoinTable(name = "walker_has_dog",
            joinColumns = @JoinColumn(name = "Dog_id"),
            inverseJoinColumns = @JoinColumn(name = "Walker_id"))
    private Set<Walker> walkers = new LinkedHashSet<>();

    public Dog() {
    }

    public Dog(String name, String breed, String gender, LocalDate birthdate, Owner owner) {
        this.name = name;
        this.breed = breed;
        this.gender = gender;
        this.birthdate = birthdate;
        this.owner = owner;
        this.owner.getDogs().add(this);
    }

    public Dog(DogDto dogDto)
    {
        if(dogDto.getId() != null)
        {
            this.id = dogDto.getId();
        }

        this.name = dogDto.getName();
        this.breed = dogDto.getBreed();
        this.gender = dogDto.getGender();
        this.birthdate = LocalDate.parse(dogDto.getBirthdate());
        this.owner = new Owner(dogDto.getOwner());
        this.owner.getDogs().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dog dog = (Dog) o;
        return id.equals(dog.id) && name.equals(dog.name) && image.equals(dog.image) && gender.equals(dog.gender) && birthdate.equals(dog.birthdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, image, gender, birthdate);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Set<Walker> getWalkers() {
        return walkers;
    }

    public void setWalkers(Set<Walker> walkers) {
        this.walkers = walkers;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", gender='" + gender + '\'' +
                ", birthdate=" + birthdate +
                ", owner=" + owner.getName() +
                ", walkers=" + walkers.size() +
                '}';
    }
}