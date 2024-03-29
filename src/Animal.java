

import java.util.List;
import java.util.Random;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael KÃ¶lling
 * @version 2011.07.31
 */
public abstract class Animal
{
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    // The animal's age.
    private int age;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();

    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Field field, Location location)
    {
        alive = true;
        this.field = field;
        setLocation(location);
        this.age = 0;
    }
    
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    abstract public void act(List<Animal> newAnimals);

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField()
    {
        return field;
    }

    /**
     * An abstract method to allow the call of the getMaxAge() method from any subclasses
     * @return The max age of the animal
     */
    public abstract int getMaxAge();

    /**
     * An abstract method to allow the call of the getBreedingProbability() method from any subclasses
     * @return The breeding probability of the animal
     */
    protected abstract double getBreedingProbability();
    
    /**
     * An abstract method to allow the call of the getMaxLitterSize() method from any subclasses
     * @return The max litter size of the animal
     */
    protected abstract int getMaxLitterSize();

    /**
     * An abstract method to allow the call of the getBreedingAge() method from any subclasses
     * @return The breeding age of the animal
     */
    protected abstract int getBreedingAge();

    /**
     * Return the animal's age.
     * @return The animal's age.
     */
    public int getAge() {
        return age;
    }

    /**
     * Set the animal's age.
     * @param age The animal's new age.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * An animal can breed if it has reached the breeding age.
     * @return true if the animal can breed, false otherwise.
     */
    protected boolean canBreed()
    {
        return age >= getBreedingAge();
    }

    /**
     * Increase the age. 
     * This could result in the animal's death.
     */
    protected void incrementAge()
    {
        age++;
        if(age > getMaxAge()) {
            setDead();
        }
    }

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    protected int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= getBreedingProbability()) {
            births = rand.nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }

    /**
     * Check whether or not this animal is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newAnimals A list to return newly born animals.
     */
    protected abstract void giveBirth(List<Animal> newAnimals);
}