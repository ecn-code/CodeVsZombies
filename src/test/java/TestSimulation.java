import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestSimulation {
/*
    @Test
    public void numOfSimulations() {
        Simulation simulation = new Simulation(40);
        assertEquals(40, simulation.numOfSimulations, 0);
    }

    @Test
    public void numOfSimulations2() {
        Simulation simulation = new Simulation(30);
        assertNotEquals(40, simulation.numOfSimulations, 0);
    }

    @Test
    public void executeSimple5Steps() {
        Simulation simulation = new Simulation(4);

        List<Zombie> zombies = Arrays.asList(new Zombie(0, 8250,8999, null));
        List<Human> humans = Arrays.asList(new Human(0, 8250,4500, false, null));
        Heroe heroe = new Heroe(-1, 0,0, null, null);

        simulation.execute(zombies, humans, heroe,1);

        assertEquals(Arrays.asList(new Zombie(0, 8250,7399, null)), zombies);
        assertEquals(new Heroe(-1,2819,2831, null, null), heroe);
        assertEquals(5, simulation.gameMovements, 0);
    }

    @Test
    public void executeSimple40Steps() {
        Simulation simulation = new Simulation(40);

        List<Zombie> zombies = Arrays.asList(new Zombie(0, 8250,8999, null));
        List<Human> humans = Arrays.asList(new Human(0, 8250,4500, false, null));
        Heroe heroe = new Heroe(-1, 0,0, null, null);

        simulation.execute(zombies, humans, heroe,1);

        assertEquals(Arrays.asList(new Zombie(0, 8250,5399, null)), zombies);
        assertEquals(new Heroe(-1,7053,5399, null, null), heroe);
        assertEquals(10, simulation.gameMovements, 0);
    }

    @Test
    public void execute2Zombies5Steps() {
        Simulation simulation = new Simulation(4);

        List<Zombie> zombies = Arrays.asList(new Zombie(0, 3100,7000, null),new Zombie(1, 11500,7100, null));
        List<Human> humans = Arrays.asList(new Human(0, 950,6000, false, null),new Human(1, 8000,6100, false, null));
        Heroe heroe = new Heroe(-1, 5000,0, null, null);

        simulation.execute(zombies, humans, heroe,1);

        assertEquals(Arrays.asList(new Zombie(0, 1648,6324, null), new Zombie(1, 9960,6660, null)), zombies);
        assertEquals(new Heroe(-1,3539,3705, null, null), heroe);
        assertEquals(5, simulation.gameMovements, 0);
    }

    @Test
    public void execute2Zombies40Steps() {
        Simulation simulation = new Simulation(40);

        List<Zombie> zombies = Arrays.asList(new Zombie(0, 3100,7000, null),new Zombie(1, 11500,7100, null));
        List<Human> humans = Arrays.asList(new Human(0, 950,6000, false, null),new Human(1, 8000,6100, false, null));
        Heroe heroe = new Heroe(-1, 5000,0, null, null);

        simulation.execute(zombies, humans, heroe,1);

        assertEquals(Arrays.asList(new Zombie(0, 950,6000, null), new Zombie(1, 8000,6100, null)), zombies);
        assertEquals(new Heroe(-1,6186,5849, null, null), heroe);
        assertEquals(11, simulation.gameMovements, 0);
    }

    /*@Test
    public void execute3VS310Steps() {
        Simulation simulation = new Simulation(9);

        List<Zombie> zombies = Arrays.asList(new Zombie(0, 2000,1500),new Zombie(1, 13900,6500), new Zombie(2, 7000,7500));
        List<Human> humans = Arrays.asList(new Human(0, 9000,1200),new Human(1, 400,6000));
        Heroe heroe = new Heroe(-1, 7500,2000);

        simulation.execute(zombies, humans, heroe,1);

        assertEquals(Arrays.asList(new Zombie(0, 2527,2177), new Zombie(1, 11452,3854), new Zombie(2, 5924,5039)), zombies);
        assertEquals(new Heroe(-1,7757,3936), heroe);
        assertEquals(10, simulation.gameMovements, 0);
    }

    @Test
    public void execute3VS340Steps() {
        Simulation simulation = new Simulation(40);

        List<Zombie> zombies = Arrays.asList(new Zombie(0, 2000,1500),new Zombie(1, 13900,6500), new Zombie(2, 7000,7500));
        List<Human> humans = Arrays.asList(new Human(0, 9000,1200),new Human(1, 400,6000));
        Heroe heroe = new Heroe(-1, 7500,2000);

        simulation.execute(zombies, humans, heroe,3);

        assertEquals(Arrays.asList(new Zombie(0, 3521,5336), new Zombie(1, 10784,4182), new Zombie(2, 7995,4199)), zombies);
        assertEquals(new Heroe(-1,5268,4500), heroe);
        assertEquals(21, simulation.gameMovements, 0);
    }
*/

    @Test
    public void executeComboOpportunity(){
        List<Zombie> zombies = Arrays.asList(
                new Zombie(0, 8000,4500),
                new Zombie(1, 9000,4500),
                new Zombie(2, 10000,4500),
                new Zombie(3, 11000,4500),
                new Zombie(4, 12000,4500),
                new Zombie(5, 13000,4500),
                new Zombie(6, 14000,4500),
                new Zombie(7, 15000,3500),
                new Zombie(8, 14500,2500),
                new Zombie(9, 15900,500)
        );

        List<Human> humans = Arrays.asList(new Human(0, 100,4000));


    }

    @Test
    public void executeCross40StepsGA() {
        List<Zombie> zombies = Arrays.asList(
                new Zombie(0, 2000,1200),
                new Zombie(1, 3000,1800),
                new Zombie(2, 4000,2400),
                new Zombie(3, 5000,3000),
                new Zombie(4, 6000,3600),

                new Zombie(5, 9000,5400),
                new Zombie(6, 10000,6000),
                new Zombie(7, 11000,6600),
                new Zombie(8, 12000,7200),
                new Zombie(9, 13000,7800),
                new Zombie(10, 14000,8400),

                new Zombie(11, 14000,600),
                new Zombie(12, 13000,1200),
                new Zombie(13, 12000,1800),
                new Zombie(14, 11000,2400),
                new Zombie(15, 10000,3000),
                new Zombie(16, 9000,3600),

                new Zombie(17, 6000,5400),
                new Zombie(18, 5000,6000),
                new Zombie(19, 4000,6600),
                new Zombie(20, 3000,7200),
                new Zombie(21, 2000,7800),
                new Zombie(22, 1000,8400));



        List<Human> humans = Arrays.asList(new Human(0, 0,4500),new Human(1, 15999,4500),new Human(2, 8000,7999));
        Heroe heroe = new Heroe(-1, 8000,0);

        GeneticAlgorithm ga = new GeneticAlgorithm(heroe, humans, zombies);
        ga.generation(0,200, 15);
    }

    @Test
    public void execute3VS11Steps() {

        List<Zombie> zombies = Arrays.asList(new Zombie(0, 2000,1500),new Zombie(1, 13900,6500), new Zombie(2, 7000,7500));
        List<Human> humans = Arrays.asList(new Human(0, 9000,1200),new Human(1, 400,6000));
        Heroe heroe = new Heroe(-1, 7500,2000);

        Simulation simulation = new Simulation(10, zombies, humans, heroe);

        simulation.execute(0,2);

        /*assertEquals(Arrays.asList(new Zombie(0, 659,5265), new Zombie(1, 10784,4182), new Zombie(2, 7995,4199)), zombies);
        assertEquals(new Heroe(-1,10107,4587), heroe);
        assertEquals(11, simulation.gameMovements, 0);*/
    }

    @Test
    public void executeSimple40StepsGA() {

        double times130 = 0;
        double times160 = 0;
        int times = 100;
        for(int i = 0;i<times;i++) {
            List<Zombie> zombies = Arrays.asList(new Zombie(0, 2000, 1500), new Zombie(1, 13900, 6500), new Zombie(2, 7000, 7500));
            List<Human> humans = Arrays.asList(new Human(0, 9000, 1200), new Human(1, 400, 6000));
            Heroe heroe = new Heroe(-1, 7500, 2000);

            GeneticAlgorithm ga = new GeneticAlgorithm(heroe, humans, zombies);
            ga.generation(0,180, 14);
            times130 += ga.currentDNA.realScore >= 130 ? 1 : 0;
            times160 += ga.currentDNA.realScore >= 131 ? 1 : 0;
        }

        System.err.println(String.format("130 %f, >130 %f", (times130/times*100), (times160/times*100)));

        //assertEquals(Arrays.asList(new Zombie(0, 8250,5399)), zombies);
        //assertEquals(new Heroe(-1,7053,5399), heroe);
        //assertEquals(10, simulation.gameMovements, 0);
    }

  /*  @Test
    public void executeSeletion() {
        GeneticAlgorithm ga = new GeneticAlgorithm(null, null, null);

        List<DNA> dnas = new ArrayList<>();
        dnas.add(new DNA(Arrays.asList()));
        dnas.add(dnas.get(0).copy());
        dnas.add(dnas.get(0).copy());

        dnas.get(0).setScore(true, 10, 10);
        dnas.get(1).setScore(true, 9, 10);
        dnas.get(2).setScore(true, 8, 10);

        dnas = ga.selection(dnas);


        List<DNA> dnasExpected = new ArrayList<>();
        dnasExpected.add(new DNA(Arrays.asList()));
        dnasExpected.add(dnasExpected.get(0).copy());
        dnasExpected.add(dnasExpected.get(0).copy());

        dnasExpected.get(0).setScore(true, 8, 10);
        dnasExpected.get(1).setScore(true, 9, 10);
        dnasExpected.get(2).setScore(true, 10, 10);

        assertEquals(ga.calcFitness(dnasExpected), dnas);
    }
*/

}