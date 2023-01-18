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
 * A DTO for the {@link entities.Walker} entity
 */
public class WalkerDto implements Serializable {
    private Integer id;
    @Size(max = 45)
    @NotNull
    private String name;
    @Size(max = 45)
    @NotNull
    private String address;
    @Size(max = 45)
    @NotNull
    private String phone;
    private Set<InnerDogDto> dogs = new LinkedHashSet<>();

    public WalkerDto(Integer id, String name, String address, String phone, Set<InnerDogDto> dogs) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.dogs = dogs;
    }

    public WalkerDto(Walker walker)
    {
        if(walker.getId() != null)
        {
            this.id = walker.getId();
        }

        this.name = walker.getName();
        this. address = walker.getAddress();
        this.phone = walker.getPhone();
        if(walker.getDogs() != null)
        {
            walker.getDogs().forEach(dog -> dogs.add(new InnerDogDto(dog)));
        }
    }

    public static List<WalkerDto> getDTOS(List<Walker> walkerList)
    {
        List<WalkerDto> walkerDtoList = new ArrayList<>();
        walkerList.forEach(walker -> walkerDtoList.add(new WalkerDto(walker)));

        return walkerDtoList;
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
        WalkerDto entity = (WalkerDto) o;
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
        private final String image;
        @NotNull
        private final String gender;
        @NotNull
        private final String birthdate;
        @NotNull
        private final InnerOwnerDto owner;

        public InnerDogDto(Integer id, String name, String image, String gender, LocalDate birthdate, InnerOwnerDto owner) {
            this.id = id;
            this.name = name;
            this.image = image;
            this.gender = gender;
            this.birthdate = birthdate.toString();
            this.owner = owner;
        }

        public InnerDogDto(Dog dog)
        {
            if(dog.getId() != null)
            {
                this.id = dog.getId();
            }
            this.name = dog.getName();
            this.image = dog.getImage();
            this.gender = dog.getGender();
            this.birthdate = dog.getBirthdate().toString();
            this.owner = new InnerOwnerDto(dog.getOwner());
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
                    Objects.equals(this.owner, entity.owner);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name, image, gender, birthdate, owner);
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "(" +
                    "id = " + id + ", " +
                    "name = " + name + ", " +
                    "image = " + image + ", " +
                    "gender = " + gender + ", " +
                    "birthdate = " + birthdate + ", " +
                    "owner = " + owner + ")";
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
    }
}