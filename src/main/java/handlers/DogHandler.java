package handlers;

import dtos.DogDto;
import entities.Dog;
import entities.Owner;
import facades.DogFacade;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;

public class DogHandler
{

    public static Dog dogUpdateHandler(Dog dog, DogDto dogDto)
    {
        Dog updatedDog = dog;

        if(dogDto.getName() != null && !dog.getName().equals(dogDto.getName()))
        {
            updatedDog.setName(dogDto.getName());
        }

        if(dogDto.getBreed() != null && !dog.getBreed().equals(dogDto.getBreed()))
        {
            updatedDog.setBreed(dogDto.getBreed());
        }

        if(dogDto.getImage() != null && !dog.getImage().equals(dogDto.getImage()))
        {
            updatedDog.setImage(dogDto.getImage());
        }

        if(dogDto.getGender() != null && !dog.getGender().equals(dogDto.getGender()))
        {
            updatedDog.setGender(dogDto.getGender());
        }

        if(dogDto.getBirthdate() != null && !dog.getBirthdate().toString().equals(dogDto.getBirthdate()))
        {
            updatedDog.setBirthdate(LocalDate.parse(dogDto.getBirthdate()));
        }

        if(dogDto.getOwner() != null && dog.getOwner().getId().equals(dogDto.getOwner().getId()))
        {
            dog.setOwner(innerOwnerHandler(dog.getOwner(), dogDto.getOwner()));
        }

        return updatedDog;
    }

    private static Owner innerOwnerHandler(Owner owner, DogDto.InnerOwnerDto innerOwnerDto)
    {
        if(innerOwnerDto.getName() != null && !owner.getName().equals(innerOwnerDto.getName()))
        {
            owner.setName(innerOwnerDto.getName());
        }

        if(innerOwnerDto.getAddress() != null && !owner.getAddress().equals(innerOwnerDto.getAddress()))
        {
            owner.setAddress(innerOwnerDto.getAddress());
        }

        if(innerOwnerDto.getPhone() != null && !owner.getPhone().equals(innerOwnerDto.getPhone()))
        {
            owner.setPhone(innerOwnerDto.getPhone());
        }

        return owner;
    }
}
