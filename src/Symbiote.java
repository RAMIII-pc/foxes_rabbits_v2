

import java.util.List;
import java.util.Iterator;
import java.util.Random;

public class Symbiote extends Animal{

    private static final int    BREEDING_AGE         = 45;
    private static final int    MAX_AGE              = 50;
    private static final double BREEDING_PROBABILITY = .0000001;
    private static final int    MAX_LITTER_SIZE      = 1;
    private static final Random rand                 = Randomizer.getRandom();

    public Symbiote(boolean randomAge, Field field, Location location){
        super(field, location);
        setAge(0);
        if(randomAge){
            setAge(rand.nextInt(MAX_AGE));
        }
    }

    public int getBreedingAge(){
        return BREEDING_AGE;
    }

    public int getMaxAge(){
        return MAX_AGE;
    }

    public double getBreedingProbability(){
        return BREEDING_PROBABILITY;
    }

    public int getMaxLitterSize(){
        return MAX_LITTER_SIZE;
    }

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
                    rabbit.setDead();
                    giveBirth(newSymbiotes);
                    return where;
                }
            }

            if(animal instanceof Fox){
                Fox fox = (Fox) animal;
                if(fox.isAlive()){
                    fox.setDead();
                    giveBirth(newSymbiotes);
                    return where;
                }
            }
        }

        return null;
    }

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

    public void giveBirth(List<Animal> newSymbiotes){
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
