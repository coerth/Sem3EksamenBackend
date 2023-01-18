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
 * A DTO for the {@link entities.Owner} entity
 */
public class OwnerDto implements Serializable {
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
    private Set<InnerDogDto> dogs = new LinkedHashSet<>();

    public OwnerDto(Integer id, String name, String address, String phone, Set<InnerDogDto> dogs) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.dogs = dogs;
    }

    public OwnerDto(Owner owner)
    {
        if(owner.getId() != null)
        {
            this.id = owner.getId();
        }
        this.name = owner.getName();
        this.address = owner.getAddress();
        this.phone = owner.getPhone();
        if(owner.getDogs() != null)
        {
            owner.getDogs().forEach(dog -> dogs.add(new InnerDogDto(dog)));
        }
    }

    public static List<OwnerDto> getDTOS(List<Owner> ownerList)
    {
        List<OwnerDto> ownerDtoList = new ArrayList<>();
        ownerList.forEach(owner -> ownerDtoList.add(new OwnerDto(owner)));

        return ownerDtoList;
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

    public Set<InnerDogDto> getDogs() {
        return dogs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OwnerDto entity = (OwnerDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.name, entity.name) &&
                Objects.equals(this.address, entity.address) &&
                Objects.equals(this.phone, entity.phone) &&
                Objects.equals(this.dogs, entity.dogs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, phone, dogs);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "address = " + address + ", " +
                "phone = " + phone + ", " +
                "dogs = " + dogs + ")";
    }

    /**
     * A DTO for the {@link entities.Dog} entity
     */
    public static class InnerDogDto implements Serializable {
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
        private Set<InnerWalkerDto> walkers = new LinkedHashSet<>();

        public InnerDogDto(Integer id, String name, String breed, String image, String gender, LocalDate birthdate, Set<InnerWalkerDto> walkers) {
            this.id = id;
            this.name = name;
            this.breed = breed;
            this.image = image;
            this.gender = gender;
            this.birthdate = birthdate.toString();
            this.walkers = walkers;
        }

        public InnerDogDto(Dog dog)
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
            if(dog.getWalkers() != null)
            {
                dog.getWalkers().forEach(walker -> walkers.add(new InnerWalkerDto(walker)));
            }
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

        public Set<InnerWalkerDto> getWalkers() {
            return walkers;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            InnerDogDto entity = (InnerDogDto) o;
            return Objects.equals(this.id, entity.id) &&
                    Objects.equals(this.name, entity.name) &&
                    Objects.equals(this.image, entity.image) &&
                    Objects.equals(this.gender, entity.gender) &&
                    Objects.equals(this.birthdate, entity.birthdate) &&
                    Objects.equals(this.walkers, entity.walkers);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name, image, gender, birthdate, walkers);
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "(" +
                    "id = " + id + ", " +
                    "name = " + name + ", " +
                    "image = " + image + ", " +
                    "gender = " + gender + ", " +
                    "birthdate = " + birthdate + ", " +
                    "walkers = " + walkers + ")";
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
}