import java.awt.Color;
import java.util.Random;
import java.util.List;


/**
 * An interface used to populate a simulation. This behavior builds the
 * simulation, by adding animals and assigning them colors.
 * 
 * @author Michael Varrella
 * @author Robert Morgan
 * @version Java
 */
public interface Populate {

    // The probability that a fox will be created in any given grid position.
    static final double FOX_CREATION_PROBABILITY = 0.02;
    // The probability that a rabbit will be created in any given grid position.
    static final double RABBIT_CREATION_PROBABILITY = 0.08; 
    // The probability that a symbiote will be created in any given grid position.
    static final double SYMBIOTE_CREATION_PROBABILITY = .001;

    /**
     * Assign colors to each of the animals in the simulation.
     * 
     * @param view The object used to show the simulation.
     */
    public static void assignColors(SimulatorView view){
        view.setColor(Rabbit.class, Color.orange);
        view.setColor(Fox.class, Color.blue);
        view.setColor(Symbiote.class, Color.black);
    }

    /**
     * Randomly populate the field with foxes, rabbits, and symbiotes.
     * 
     * @param field The field used in the simulation.
     * @param animals The list of animals used in the simulation.
     */
    public static void populate(Field field, List<Animal> animals)
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= FOX_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Fox fox = new Fox(true, field, location);
                    animals.add(fox);
                }
                else if(rand.nextDouble() <= RABBIT_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Rabbit rabbit = new Rabbit(true, field, location);
                    animals.add(rabbit);
                }
                else if(rand.nextDouble() <= SYMBIOTE_CREATION_PROBABILITY){
                    Location location = new Location(row, col);
                    Symbiote symbiote = new Symbiote(true, field, location);
                    animals.add(symbiote);
                }
                // else leave the location empty.
            }
        }
    }
}
