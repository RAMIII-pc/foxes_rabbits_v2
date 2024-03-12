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

    

}
