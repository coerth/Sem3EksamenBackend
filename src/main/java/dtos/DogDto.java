package dtos;

import entities.Dog;
import entities.Owner;
import entities.Walker;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

/**
 * A DTO for the {@link entities.Dog} entity
 */
public class DogDto implements Serializable {
    private Integer id;
    @Size(max = 45)
    @NotNull
    private final String name;

    @Size(max = 45)
    @NotNull
    private final String breed;
    @Size(max = 45)
    private final String image;
    @NotNull
    private final String gender;
    @NotNull
    private final String birthdate;
    @NotNull
    private final InnerOwnerDto owner;
    private Set<InnerWalkerDto> walkers = new LinkedHashSet<>();

    public DogDto(Integer id, String name, String breed, String image, String gender, LocalDate birthdate, InnerOwnerDto owner, Set<InnerWalkerDto> walkers) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.image = image;
        this.gender = gender;
        this.birthdate = birthdate.toString();
        this.owner = owner;
        this.walkers = walkers;
    }

    public DogDto(Dog dog)
    {
        if(dog.getId() != null)
        {
            this.id = dog.getId();
        }
        this.name = dog.getName();
        this.breed = dog.getBreed();
        this.image = dog.getImage();
        this.gender = dog.getGender();
        this.birthdate = dog.getBirthdate().toString();
        this.owner = new InnerOwnerDto(dog.getOwner());
        if(dog.getWalkers() != null)
        {
            dog.getWalkers().forEach(walker -> walkers.add(new InnerWalkerDto(walker)));
        }
    }

    public static List<DogDto> getDTOS(List<Dog> dogList)
    {
        List<DogDto> dogDtoList = new ArrayList<>();
        dogList.forEach(dog -> dogDtoList.add(new DogDto(dog)));

        return dogDtoList;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getGender() {
        return gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public InnerOwnerDto getOwner() {
        return owner;
    }

    public Set<InnerWalkerDto> getWalkers() {
        return walkers;
    }

    public String getBreed() {
        return breed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DogDto entity = (DogDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.name, entity.name) &&
                Objects.equals(this.image, entity.image) &&
                Objects.equals(this.gender, entity.gender) &&
                Objects.equals(this.birthdate, entity.birthdate) &&
                Objects.equals(this.owner, entity.owner) &&
                Objects.equals(this.walkers, entity.walkers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, image, gender, birthdate, owner, walkers);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "breed = " + breed + ", " +
                "image = " + image + ", " +
                "gender = " + gender + ", " +
                "birthdate = " + birthdate + ", " +
                "owner = " + owner + ", " +
                "walkers = " + walkers + ")";
    }

    /**
     * A DTO for the {@link entities.Owner} entity
     */
    public static class InnerOwnerDto implements Serializable {
        private Integer id;
        @Size(max = 45)
        @NotNull
        private final String name;
        @Size(max = 45)
        @NotNull
        private final String address;
        @Size(max = 45)
        @NotNull
        private final String phone;

        public InnerOwnerDto(Integer id, String name, String address, String phone) {
            this.id = id;
            this.name = name;
            this.address = address;
            this.phone = phone;
        }

        public InnerOwnerDto(Owner owner)
        {
            if(owner.getId() != null)
            {
                this.id = owner.getId();
            }
            this.name = owner.getName();
            this.address = owner.getAddress();
            this.phone = owner.getPhone();
        }

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getAddress() {
            return address;
        }

        public String getPhone() {
            return phone;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            InnerOwnerDto entity = (InnerOwnerDto) o;
            return Objects.equals(this.id, entity.id) &&
                    Objects.equals(this.name, entity.name) &&
                    Objects.equals(this.address, entity.address) &&
                    Objects.equals(this.phone, entity.phone);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name, address, phone);
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "(" +
                    "id = " + id + ", " +
                    "name = " + name + ", " +
                    "address = " + address + ", " +
                    "phone = " + phone + ")";
        }
    }

    /**
     * A DTO for the {@link entities.Walker} entity
     */
    public static class InnerWalkerDto implements Serializable {
        private Integer id;
        @Size(max = 45)
        @NotNull
        private final String name;
        @Size(max = 45)
        @NotNull
        private final String address;
        @Size(max = 45)
        @NotNull
        private final String phone;

        public InnerWalkerDto(Integer id, String name, String address, String phone) {
            this.id = id;
            this.name = name;
            this.address = address;
            this.phone = phone;
        }

        public InnerWalkerDto(Walker walker)
        {
            if(walker.getId() != null)
            {
                this.id = walker.getId();
            }
            this.name = walker.getName();
            this.address = walker.getAddress();
            this.phone = walker.getPhone();
        }

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getAddress() {
            return address;
        }

        public String getPhone() {
            return phone;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            InnerWalkerDto entity = (InnerWalkerDto) o;
            return Objects.equals(this.id, entity.id) &&
                    Objects.equals(this.name, entity.name) &&
                    Objects.equals(this.address, entity.address) &&
                    Objects.equals(this.phone, entity.phone);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name, address, phone);
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "(" +
                    "id = " + id + ", " +
                    "name = " + name + ", " +
                    "address = " + address + ", " +
                    "phone = " + phone + ")";
        }
    }
}