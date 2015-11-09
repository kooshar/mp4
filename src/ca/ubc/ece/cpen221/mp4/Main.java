package ca.ubc.ece.cpen221.mp4;

import javax.swing.SwingUtilities;

import ca.ubc.ece.cpen221.mp4.ai.*;
import ca.ubc.ece.cpen221.mp4.bombs.Bomb;
import ca.ubc.ece.cpen221.mp4.bombs.NuclearBomb;
import ca.ubc.ece.cpen221.mp4.items.Gardener;
import ca.ubc.ece.cpen221.mp4.items.Grass;
import ca.ubc.ece.cpen221.mp4.items.animals.*;
import ca.ubc.ece.cpen221.mp4.staff.WorldImpl;
import ca.ubc.ece.cpen221.mp4.staff.WorldUI;
import ca.ubc.ece.cpen221.mp4.vehicles.Bagger288;
import ca.ubc.ece.cpen221.mp4.vehicles.Bike;
import ca.ubc.ece.cpen221.mp4.vehicles.Truck;

/**
 * The Main class initialize a world with some {@link Grass}, {@link Rabbit}s,
 * {@link Fox}es, {@link Gnat}s, {@link Gardener}, etc.
 *
 * You may modify or add Items/Actors to the World.
 *
 */
public class Main {

    static final int X_DIM = 40;
    static final int Y_DIM = 40;
    static final int SPACES_PER_GRASS = 7;
    static final int INITIAL_GRASS = X_DIM * Y_DIM / SPACES_PER_GRASS;
    static final int INITIAL_GNATS = INITIAL_GRASS / 4;
    static final int INITIAL_RABBITS = INITIAL_GRASS / 4;
    static final int INITIAL_FOXES = INITIAL_GRASS / 32;
    static final int INITIAL_TIGERS = INITIAL_GRASS / 32;
    static final int INITIAL_BEARS = INITIAL_GRASS / 40;
    static final int INITIAL_HYENAS = INITIAL_GRASS / 32;
    static final int INITIAL_CARS = INITIAL_GRASS / 100;
    static final int INITIAL_TRUCKS = INITIAL_GRASS / 150;
    static final int INITIAL_BAGGER288 = INITIAL_GRASS / 150;
    static final int INITIAL_BIKE = INITIAL_GRASS / 150;
    static final int INITIAL_MOTORCYCLES = INITIAL_GRASS / 64;
    static final int INITIAL_MANS = INITIAL_GRASS / 150;
    static final int INITIAL_WOMANS = INITIAL_GRASS / 100;
    static final int INITIAL_HUNTERS = INITIAL_GRASS / 150;
    static final int INITIAL_NUKES = 1;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().createAndShowWorld();
            }
        });
    }

    public void createAndShowWorld() {
        World world = new WorldImpl(X_DIM, Y_DIM);
        initialize(world);
        new WorldUI(world).show();
    }

    public void initialize(World world) {
        addGrass(world);
        world.addActor(new Gardener());

        addGnats(world);
        addRabbits(world);
        addFoxes(world);
        addTrucks(world);
        addBagger288s(world);
        addBikes(world);

        // addNukes(world);
        // TODO: You may add your own creatures here!
    }

    private void addGrass(World world) {
        for (int i = 0; i < INITIAL_GRASS; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            world.addItem(new Grass(loc));
        }
    }

    private void addGnats(World world) {
        for (int i = 0; i < INITIAL_GNATS; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            Gnat gnat = new Gnat(loc);
            world.addItem(gnat);
            world.addActor(gnat);
        }
    }

    private void addFoxes(World world) {
        FoxAI foxAI = new FoxAI();
        for (int i = 0; i < INITIAL_FOXES; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            Fox fox = new Fox(foxAI, loc);
            world.addItem(fox);
            world.addActor(fox);
        }
    }

    private void addRabbits(World world) {
        RabbitAI rabbitAI = new RabbitAI();
        for (int i = 0; i < INITIAL_RABBITS; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            Rabbit rabbit = new Rabbit(rabbitAI, loc);
            world.addItem(rabbit);
            world.addActor(rabbit);
        }
    }

    private void addNukes(World world) {
        for (int i = 0; i < INITIAL_NUKES; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            Bomb bomb = new NuclearBomb(Util.getRandomEmptyLocation(world), 20);
            world.addItem(bomb);
            world.addActor(bomb);
        }
    }

    private void addTrucks(World world) {
        TruckAI truckAI = new TruckAI();
        for (int i = 0; i < INITIAL_TRUCKS; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            Truck truck = new Truck(truckAI, loc);
            world.addItem(truck);
            world.addActor(truck);
        }
    }

    private void addBagger288s(World world) {
        Bagger288AI bagger288AI = new Bagger288AI();
        for (int i = 0; i < INITIAL_BAGGER288; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            Bagger288 bagger288 = new Bagger288(bagger288AI, loc);
            world.addItem(bagger288);
            world.addActor(bagger288);
        }
    }

    private void addBikes(World world) {
        BikeAI bikeAI = new BikeAI();
        for (int i = 0; i < INITIAL_BIKE; i++) {
            Location loc = Util.getRandomEmptyLocation(world);
            Bike bike = new Bike(bikeAI, loc);
            world.addItem(bike);
            world.addActor(bike);
        }
    }
}