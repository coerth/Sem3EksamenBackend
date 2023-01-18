package dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link entities.Owner} entity
 */
public class OwnerDto implements Serializable {
    private final Integer id;
    @Size(max = 45)
    @NotNull
    private final String name;
    @Size(max = 45)
    @NotNull
    private final String address;
    @Size(max = 45)
    @NotNull
    private final String phone;
    private final Set<InnerDogDto> dogs;

    public OwnerDto(Integer id, String name, String address, String phone, Set<InnerDogDto> dogs) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.dogs = dogs;
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
        private final Integer id;
        @Size(max = 45)
        @NotNull
        private final String name;
        @Size(max = 45)
        private final String image;
        @NotNull
        private final String gender;
        @NotNull
        private final LocalDate birthdate;
        private final Set<InnerWalkerDto> walkers;

        public InnerDogDto(Integer id, String name, String image, String gender, LocalDate birthdate, Set<InnerWalkerDto> walkers) {
            this.id = id;
            this.name = name;
            this.image = image;
            this.gender = gender;
            this.birthdate = birthdate;
            this.walkers = walkers;
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

        public LocalDate getBirthdate() {
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
            private final Integer id;
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