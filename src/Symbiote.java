

import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a symbiote (Marvel comics).
 * Symbiotes age, move, attach to hosts, and die.
 * 
 * @author Robert Morgan
 * @author Michael Varrella
 * @version Java 
 */
public class Symbiote extends Animal{

    //Characteristics shared by all symbiotes (class variables).

    //The age at which a symbiote can start to breed.
    private static final int    BREEDING_AGE         = 250;
    //The age to which a symbiote can live.
    private static final int    MAX_AGE              = 300;
    //The likelihood of a symbiote breeding.
    private static final double BREEDING_PROBABILITY = .3;
    //The maximum number of births.
    private static final int    MAX_LITTER_SIZE      = 1;
    //A shared random number generator to control breeding.
    private static final Random rand                 = Randomizer.getRandom();

    //Individual characteristics (instance fields).

    /**
     * Create a new symbiote. A symbiote may be created with age zero
     * (a new born) or with a random age.
     * 
     * @param randomAge If true, the symbiote will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Symbiote(boolean randomAge, Field field, Location location){
        super(field, location);
        setAge(0);
        if(randomAge){
            setAge(rand.nextInt(MAX_AGE));
        }
    }

    /**
     * @return The breeding age of the symbiote.
     */
    public int getBreedingAge(){
        return BREEDING_AGE;
    }

    /**
     * @return The age the symbiote can live to.
     */
    public int getMaxAge(){
        return MAX_AGE;
    }

    /**
     * @return The likelihood the symbiote will successfully breed.
     */
    public double getBreedingProbability(){
        return BREEDING_PROBABILITY;
    }

    /**
     * @return The maximum number of symbiotes that can be birthed.
     */
    public int getMaxLitterSize(){
        return MAX_LITTER_SIZE;
    }

 
    /**
     * Looks for a new host (fox and/or rabbit). Moves to the
     * adjacent location containing either of the two. The first 
     * host it finds is symbiotically connected with.
     * 
     * @param newSymbiotes A list to return newly formed hosts.
     * @return Where the host was found, or null if it wasn't.
     */
    private Location findHost(List<Animal> newSymbiotes){
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()){
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            
            if(animal instanceof Rabbit){
                Rabbit rabbit = (Rabbit) animal;
                if(rabbit.isAlive()){
                    //Remove the rabbit from the field.
                    rabbit.setDead();
                    //Create a new symbiote from  the dead rabbit.
                    giveBirth(newSymbiotes);
                    return where;
                }
            }

            if(animal instanceof Fox){
                Fox fox = (Fox) animal;
                if(fox.isAlive()){
                    //Remove the fox from the field.
                    fox.setDead();
                    //Create a new symbiote from the dead fox.
                    giveBirth(newSymbiotes);
                    return where;
                }
            }
        }

        return null;
    }

    /**
     * This is what the symbiote does most of the time; it seeks
     * a host. In the process, it might breed or die of old age.
     * 
     * @param newSymbiotes A list to return newly created symbiotes.
     */
    public void act(List<Animal> newSymbiotes){
        incrementAge();
        if(isAlive()){
            Location newLocation = findHost(newSymbiotes);

            if(newLocation == null){
                newLocation = getField().freeAdjacentLocation(getLocation());
            }

            if(newLocation != null){
                setLocation(newLocation);
            } else {
                setDead();
            }
        }
    }

    /**
     * Check whether or not this symbiote is to give birth at this step.
     * New births will made into free adjacent location.
     * 
     * @param newSymbiotes A list to return newly created symbiotes.
     */
    public void giveBirth(List<Animal> newSymbiotes){
        // New symbiotes are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Symbiote young = new Symbiote(false, field, loc);
            newSymbiotes.add(young);
        }
    }

}
