import java.util.List;
import java.util.Iterator;
import java.util.Random;

public class Symbiote extends Animal{

    private static final int    BREEDING_AGE         = 1;
    private static final int    MAX_AGE              = 500;
    private static final double BREEDING_PROBABILITY = .5;
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

    public getMaxLitterSize(){
        return MAX_LITTER_SIZE;
    }

    private Location findHost(){
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
                    giveBirth(List<Animal> newSymbiotes);
                    return where;
                }
            }

            if(animal instanceof Fox){
                Fox fox = (Fox) animal;
                if(fox.isAlive()){
                    fox.setDead();
                    giveBirth(List<Animal> newSymbiotes);
                    return where;
                }
            }

            if(animal instanceof Symbiote){
                Symbiote symbiote = (Symbiote) animal;
                if(symbiote.isAlive()){
                    if(symbiote.getAge() > animal.getAge()){
                        symbiote.setDead();
                        return where;
                    } else {
                        animal.setDead();
                        return where;
                    }
                }
            }
        }

        return null;
    }

    public void act(List<Animal> newSymbiotes){
        incrementAge();
        if(isAlive()){
            Location newLocation = findHost();

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

}
