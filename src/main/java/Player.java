import javafx.geometry.Pos;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.io.*;
import java.math.*;
import java.util.stream.IntStream;

/**
 * Save humans, destroy zombies!
 **/
class Player {

    public static void main(String args[]) {

        Scanner in = new Scanner(System.in);

        Heroe heroe = new Heroe(-1, 0, 0);

        //Leer x actual
        heroe.x = in.nextInt();
        //leer y actual
        heroe.y = in.nextInt();

        //Almacenar humanos
        List<Human> humans = getHumans(in, heroe);
        //Almacenar Zombies
        List<Zombie> zombies = getZombies(in, humans, heroe);

        GeneticAlgorithm ga = new GeneticAlgorithm(heroe, humans, zombies);
        ga.generation(0,150, 12);
        heroe.dna = ga.currentDNA;


        int cont = 0;
        // game loop
        while (true) {
            //Leer x actual
            heroe.x = heroe.x == -1 ? in.nextInt() : heroe.x;
            //leer y actual
            heroe.y = heroe.y == -1 ? in.nextInt() : heroe.y;

            //Almacenar humanos
            humans = humans == null ? getHumans(in, heroe) : humans;

            //Almacenar Zombies
            zombies = zombies == null ? getZombies(in, humans, heroe) : zombies;

            if(cont > 0) {
                ga = new GeneticAlgorithm(heroe, humans, zombies);
                ga.generation(cont, heroe.dna, 20, 12);
                heroe.dna = heroe.dna.isBetter(ga.currentDNA) ? heroe.dna : ga.currentDNA;
            }

            heroe.update(new Object[]{cont}, 2);

            //Aplicar estrategia de juego
            /*if(!ga.currentDNA.valid){
                heroe.update(new Object[]{zombies, humans}, 1);
                //heroe.update(new Object[]{cont}, 3);
            }else{
                heroe.update(new Object[]{cont}, 2);
            }*/

            //Destino
            System.out.println(heroe.target.x + " " +heroe.target.y);

            humans = null;
            zombies = null;
            heroe.x = -1;
            heroe.y = -1;

            //zombies.stream().map(z->z.distanceHuman).forEach(System.err::println);
            cont++;
        }
    }

    static List<Human> getHumans(Scanner in, Heroe heroe){
        int humanCount = in.nextInt();
        List<Human> humans = new ArrayList<>(humanCount);
        for (int i = 0; i < humanCount; i++) {
            int humanId = in.nextInt();
            int humanX = in.nextInt();
            int humanY = in.nextInt();

            humans.add(new Human(humanId, humanX, humanY));

        }
        return humans;
    }

    static List<Zombie> getZombies(Scanner in, List<Human> humans, Heroe heroe){
        int zombieCount = in.nextInt();
        List<Zombie> zombies = new ArrayList<Zombie>(zombieCount);
        for (int i = 0; i < zombieCount; i++) {
            int zombieId = in.nextInt();
            int zombieX = in.nextInt();
            int zombieY = in.nextInt();
            int zombieXNext = in.nextInt();
            int zombieYNext = in.nextInt();

            Zombie z = new Zombie(zombieId, zombieX, zombieY);
            zombies.add(z);

            //Comprobar si podra ser salvado
            for(Human h : humans){
                h.update(z, heroe);
                z.setTarget((Position)h);
            }

        }
        return zombies;
    }

}

class Position{
    int x,y;
    int id;

    Position(){}

    Position(int id, int x, int y){
        this.id = id;
        this.x = x;
        this.y = y;
    }

    Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        Position pos = (Position) obj;
        return pos.x == this.x && pos.y == this.y && pos.id == this.id;
    }

    @Override
    public String toString() {
        //return String.format("x: %d, y: %d, id: %d", x, y, id);
        return String.format("%d,%d",x,y);
    }

    Position copy(){
        return new Position(this.id, this.x, this.y);
    }
}

class Heroe extends Position{

    Position target;
    int distanceTarget;
    DNA dna;


    Heroe(int id, int x, int y){
        super(id, x, y);
        this.target = null;
        this.distanceTarget = Integer.MAX_VALUE;
    }

    void selectTarget(List<Human> humans){

        //Reseteamos
        this.distanceTarget = Integer.MAX_VALUE;
        this.target = null;

        //Elegimos que humano salvar
        for(Human h : humans){

            //System.err.println(String.format("ID: %d, Killed: %b, distanceTarget: %d, stepDistanceZombie: %d", h.id, h.isKilled, distanceTarget, h.stepDistanceZombie));

            if(!h.isKilled && distanceTarget > h.stepDistanceZombie){
                this.distanceTarget = h.stepDistanceZombie;
                this.target = h.nearZombie.copy();
                //System.err.println(String.format("Heroe target %s", target));
            }
        }
    }

    void centroid(List<Zombie> zombies){
        if(distanceTarget == Integer.MAX_VALUE){

            this.target = new Position(0, 0);

            for(Zombie z : zombies){
                this.target.x += z.x;
                this.target.y += z.y;
            }

            this.target.x /= zombies.size();
            this.target.y /= zombies.size() + 2000;

        }
    }

    void update(Object[] args, int strategy){
        switch (strategy) {
            case 1:
                List<Zombie> zombies = (List<Zombie>) args[0];
                List<Human> humans = (List<Human>) args[1];
                selectTarget(humans);
                centroid(zombies);
                break;
            case 2:
                if((int)args[0] < dna.dna.size()){
                    target = dna.dna.get((int)args[0]);
                }
                break;
            case 3:
                if(((int)args[0])*2+1 < a.length){
                    target = new Position(a[((int)args[0])*2], a[((int)args[0])*2+1]);
                }
                break;
        }
    }

    List<Zombie> getZombiesNotKilled(List<Zombie> zombies){
        return zombies.stream().filter( z -> Utils.HUMAN_RANGE < Utils.calcDistance(this.x, this.y, z.x, z.y)).collect(Collectors.toList());
    }

    Heroe copy(){
        return new Heroe(this.id, this.x, this.y);
    }

}

class Human extends Position{

    int stepDistancePlayer;
    boolean isKilled = false;
    Zombie nearZombie;
    int stepDistanceZombie = Integer.MAX_VALUE;

    public Human(int id, int x, int y){
        this.x = x;
        this.y = y;
        this.id = id;
    }

    void update(Zombie z, Heroe h){
        //Calculamos pasos del player hasta nosotros
        setStepPlayerDistance(h);

        //Establecemos el enemigo que viene a por nosotros
        setEnemy(z, h);

        //Comprobamos si ya hay alguien que nos matara sin poder hacer nada
        setIsKilled();
    }

    private void setStepPlayerDistance(Heroe p){
        stepDistancePlayer = (int)Utils.calcDistance(x, y, p.x, p.y) / Utils.HUMAN_STEP - 1;
    }

    private void setEnemy(Zombie z, Heroe p){
        double zombieSteps = z.calcStepDistance(this);

        //¿Le podemos alcanzar?
        if(stepDistancePlayer <= zombieSteps){
            //¿Es el zombie mas cercano?
            if(zombieSteps < stepDistanceZombie){
                this.stepDistanceZombie = (int)zombieSteps;
                this.nearZombie = z;
                //System.err.println("id:"+this.id+" "+this.stepDistanceZombie +" "+ this.stepDistancePlayer);
            }
        }
    }

    private void setIsKilled(){
        this.isKilled = this.stepDistanceZombie < this.stepDistancePlayer;
    }

    Human copy(){
        return new Human(this.id, this.x, this.y);
    }

}

class Zombie extends Position{

    Position target;
    int distanceTotalTarget;
    int distanceTarget;

    public Zombie(int id, int x, int y){
        super(id, x, y);
        reset();
    }

    void reset(){
        this.distanceTotalTarget = Integer.MAX_VALUE;
        this.target = null;
    }

    void setTarget(Position p){
        double distance = Utils.calcDistance(x, y, p.x, p.y);
        //System.err.println(String.format("Zombie id:%d target %s distanceP:%d, distanceTarget:%d", id, p, distance, distanceTarget));
        if(distanceTotalTarget > distance){
            target = p;
            distanceTarget = calcStepDistance(p);
            distanceTotalTarget = (int)distance;
        }
    }

    public int calcStepDistance(Position p){
        return (int)Utils.calcDistance(x, y, p.x, p.y) / Utils.ZOMBIE_STEP + 1;
    }

    List<Human> getHumansNotKilled(List<Human> humans){
        return humans.stream().filter( h -> Utils.ZOMBIE_RANGE < Utils.calcDistance(this.x, this.y, h.x, h.y)).collect(Collectors.toList());
    }

    Zombie copy(){
        return new Zombie(this.id, this.x, this.y);
    }

}

class Utils{

    static final int HUMAN_STEP = 1000;
    static final int ZOMBIE_STEP = 400;

    static final int HUMAN_RANGE = 2000;
    static final int ZOMBIE_RANGE = 0;

    static final int TURNS = 40;
    static final int WIDE = 16000;
    static final int HIGH = 9000;
    static final int POPULATION = 1;

    static int[] FIBONACCI = new int[]{ 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765, 10946 };


    public static double calcDistance(int x, int y, int x2, int y2){
        //System.err.println((x2-x)+" "+(y2-y));
        //System.err.println(((x2-x)*(x2-x))+" "+((y2-y)*(y2-y)));
        //System.err.println(Math.sqrt(((x2-x)*(x2-x)) + ((y2-y)*(y2-y))));



        return Math.sqrt(((x2-x)*(x2-x)) + ((y2-y)*(y2-y)));
    }

    public static Position calcNextPosition(Position position, Position target, int maxMovement) {
        if(target==null)
            System.err.println(target);
        Position nextZombie = new Position(target.x - position.x, target.y - position.y);
        double length = Math.sqrt(nextZombie.x * nextZombie.x + nextZombie.y * nextZombie.y);

        if (length <= maxMovement)
        {
            nextZombie.x = target.x;
            nextZombie.y = target.y;
        }
        else
        {
            nextZombie.x = position.x + (int)Math.floor((nextZombie.x / length) * maxMovement);
            nextZombie.y = position.y + (int)Math.floor((nextZombie.y / length) * maxMovement);
        }

        return nextZombie;

    }

    public static int calcSlope(int x1, int y1, int x2, int y2){
        if (x1!=x2) {
            return (y1-y2) / (x1-x2);
        }
        return 100000;
    }

    public static int calcScore(int humansAlive, int zombiesKilled){

        //System.err.println(humansAlive+"z"+zombiesKilled);
        int score = 0;
        for(int i=0;i<zombiesKilled;i++){
            score += humansAlive * humansAlive * 10 * FIBONACCI[i];
        }

        return score;
    }

    public static int generateRandomIntIntRange(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

}

class Simulation{

    int score;
    int gameMovements;
    int numOfSimulations;
    boolean valid;
    List<Zombie> zombies;
    List<Human> humans;
    Heroe heroe;

    Simulation(int numOfSimulations, List<Zombie> zombies, List<Human> humans, Heroe heroe){
        this.gameMovements = 1;
        this.score = 0;
        this.numOfSimulations = numOfSimulations;
        this.valid = true;

        this.zombies = zombies;
        this.humans = humans;
        this.heroe = heroe;
    }

    public void execute(int startFrom, int strategy){

        for(int i=startFrom;i<numOfSimulations && !zombies.isEmpty() && !humans.isEmpty();i++){
            //System.err.println("Step: "+i);
            gameMovements++;

            for(Zombie z : zombies) {

                z.reset();
                z.setTarget(heroe);

                for (Human h : humans) {
                    h.update(z, heroe);
                    z.setTarget(h);
                }
            }

            switch (strategy) {
                case 1:
                    Object[] args = new Object[]{zombies, humans};
                    heroe.update(args, strategy);
                    break;
                default:
                    heroe.update(new Object[]{i}, strategy);
                    break;
            }

            //Movemos todos los zombies
            for(Zombie z : zombies){

                //Calculamos nueva posicion
                Position p = Utils.calcNextPosition((Position)z, z.target, Utils.ZOMBIE_STEP);
                //System.err.println(Utils.calcDistance(z.x,z.y,humans.get(0).x,humans.get(0).y));

                //Actualizamos posicion
                z.x = p.x;
                z.y = p.y;
                //System.err.println(String.format("Actualizar Zombie: %s -> %d",z,z.target.id));
            }

            //Movemos heroe
            //System.err.println(String.format("Heroe target %s", heroe.target));
            if(heroe.target != null){
                Position p = Utils.calcNextPosition((Position)heroe, heroe.target, Utils.HUMAN_STEP);
                heroe.x = p.x;
                heroe.y = p.y;
            }
            //System.err.println(String.format("Heroe pos %s",heroe));


            //System.err.println(Utils.calcDistance(heroe.x, heroe.y, zombies.get(0).x,zombies.get(0).y));

            //Vemos si heroe se come algun zombie
            int zombiesAlive = zombies.size();
            zombies = heroe.getZombiesNotKilled(zombies);

            //System.err.println(String.format("Humans Alive: %d", humansAlive));
            //System.err.println(String.format("Zombies Killed: %d", zombiesKilled));

            //Puntuar (humansAlive and zombiesKilled)
            score += Utils.calcScore(humans.size(), zombiesAlive - zombies.size());

            humans = humans.stream().map(h->h.copy()).collect(Collectors.toList());

            //Vemos si un zombie se come un humano
            for(Zombie z : zombies){
                humans = z.getHumansNotKilled(humans);
                //System.err.println(humans.size());
            }

        }

        valid = !humans.isEmpty();

        if(valid) {
            //System.err.println("************************");
            //System.err.println(String.format("Moves: %d, Score: %d, Valid: %b", this.gameMovements, this.score, this.valid));
            //System.err.println("************************");
        }

    }

}

class DNA implements Comparable{
    private int score;

    int realScore;
    int moves;
    boolean valid;
    List<Position> dna;
    double fitness;

    DNA(List<Position> dna){
        this.dna = dna;
        score = 0;
        fitness = 0;
    }

    DNA(List<Position> dna, int re){
        this(dna);
        this.realScore = re;
    }

    DNA(List<Position> dna, int re, int mv, boolean valid){
        this(dna);
        this.realScore = re;
        this.moves = mv;
        this.valid = valid;
    }

    void setScore(boolean valid, int moves, int sc){
        realScore = sc;
        this.moves = moves;
        this.valid = valid;
        this.score = valid ? sc * 2 + 100 - moves : sc;
    }

    int getScore(){
        return this.score;
    }

    boolean isBetter(DNA d2){
        return this.realScore > d2.realScore;
    }

    @Override
    public String toString(){
        return String.format("Valid: %b,Moves: %d, Score: %d", valid, moves, realScore);
    }

    @Override
    public boolean equals(Object obj) {
        DNA d2 = (DNA) obj;
        return d2.fitness == this.fitness;
    }

    DNA copy(){
        return new DNA(this.dna.stream().map( d -> d.copy()).collect(Collectors.toList()), realScore, moves, valid);
    }

    @Override
    public int compareTo(Object o) {
        DNA d2 = (DNA)o;
        return Double.compare(this.fitness, d2.fitness);
    }
}

class GeneticAlgorithm{


    DNA currentDNA;
    List<Zombie> zombies;
    List<Human> humans;
    Heroe heroe;
    DNA strategy2Solution;

    GeneticAlgorithm(Heroe heroe, List<Human> humans, List<Zombie> zombies){
        this.heroe = heroe;
        this.humans = humans;
        this.zombies = zombies;
    }

    Position getRandomPosition(){
        return new Position(new Random().nextInt(Utils.WIDE), new Random().nextInt(Utils.HIGH));
    }

    DNA createDNA(){
        return new DNA(IntStream.range(0, Utils.TURNS).boxed()
                .map(i->getRandomPosition())
                .collect(Collectors.toList()));
    }

    List<DNA> createPopulation(int population){
        return IntStream.range(0, population).boxed().map( i -> createDNA()).collect(Collectors.toList());
    }

    void generation(int startFrom, int generations, int population){
        generation(startFrom, null, generations, population);
    }

    void generation(int startFrom, DNA dnaFather, int generations, int population){
        //Crear la poblacion
        List<DNA> dnas = createPopulation(population);

        //Create save way
        dnas.set(10, createDNAValid(startFrom, heroe.copy(),
                zombies.stream().map(z->z.copy()).collect(Collectors.toList()),
                humans.stream().map(h->h.copy()).collect(Collectors.toList())));

        if(dnaFather != null){
            dnas.set(9, dnaFather);
        }


        long t1 = System.nanoTime();
        for(int i=0;i<generations;i++){
            //Evaluamos la poblacion y nos quedamos los mejores
            dnas = simulate(startFrom, dnas);
            dnas = selection(dnas);

            //Mezclamos a los mejores
            dnas = crossover(dnas);

            //Mutamos a la poblacion
            dnas = mutation(dnas);
        }

        if(!currentDNA.valid || strategy2Solution.isBetter(currentDNA)){
            currentDNA = strategy2Solution;
            //System.err.println(String.format("Strategy2 %d", strategy2Solution.realScore));
        }

        System.err.println(String.format("TimeElapsed: %fs, %s",(System.nanoTime() - t1)/1000000000d, currentDNA.toString()));
    }

    private DNA createDNAValid(int startFrom, Heroe pHero, List<Zombie> pZombies, List<Human> pHumans) {
        List<Position> pos = new ArrayList<>(40);
        int score = 0;
        int i;
        for(i=startFrom;i<40 && !pZombies.isEmpty();i++){
            Simulation simulation = new Simulation(1, pZombies,
                    pHumans,
                    pHero);
            simulation.execute(startFrom,1);

            pos.add((Position) pHero.copy());
            score += simulation.score;

            pZombies = simulation.zombies;
            pHumans = simulation.humans;
        }
        strategy2Solution = new DNA(pos);
        strategy2Solution.setScore(true, i, score);

        return strategy2Solution.copy();
    }

    List<DNA> simulate(int startFrom, List<DNA> dnas){
        for(DNA dna : dnas) {

            Heroe hero = heroe.copy();
            hero.dna = dna;

            //Ejecutamos la simulacion
            //System.err.println(dna);
            Simulation simulation = new Simulation(Utils.TURNS, zombies.stream().map(z -> z.copy()).collect(Collectors.toList()),
                    humans.stream().map(h -> h.copy()).collect(Collectors.toList()),
                    hero);
            simulation.execute(startFrom,2);

            dna.setScore(simulation.valid, simulation.gameMovements, simulation.score);
        }
        return dnas;
    }

    List<DNA> selection(List<DNA> dnas){

        //Quedarnos con los mejores dnas
        dnas = calcFitness(dnas);
        dnas = dnas.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());
        DNA selected = dnas.get(0);

        if(currentDNA == null || (selected.valid && selected.isBetter(currentDNA))){
            currentDNA = selected.copy();
        }

        /*List<DNA> betterGs = new ArrayList<>(dnas);
        for(int i=betterGs.size();i<dnas.size();i++){
            betterGs.add(selected.copy());
        }*/

        return dnas;
    }

    List<DNA> calcFitness(List<DNA> dnas){
        double sum = 0;
        for(DNA dna : dnas){
            sum += dna.getScore();
        }

        for(DNA dna : dnas){
            dna.fitness = dna.getScore() / sum;
        }

        return dnas;
    }

    List<DNA> crossover(List<DNA> dnas){
        List<DNA> dnasCrossed = new ArrayList<>();
        for(int i=0;i<1;i+=2){
            List<Position> d1A = new ArrayList<>(dnas.get(0).dna.subList(0, dnas.get(0).dna.size()/2));
            List<Position> d1B = new ArrayList<>(dnas.get(0).dna.subList(dnas.get(0).dna.size()/2, dnas.get(0).dna.size()));
            List<Position> d2A = new ArrayList<>(dnas.get(1).dna.subList(0, dnas.get(1).dna.size()/2));
            List<Position> d2B = new ArrayList<>(dnas.get(1).dna.subList(dnas.get(1).dna.size()/2, dnas.get(1).dna.size()));

            d1A.addAll(d2B);
            d2A.addAll(d1B);

            dnas.set(i+10, new DNA(d1A));
            dnas.set(i+11, new DNA(d2A));
        }
        return dnas;
    }

    List<DNA> mutation(List<DNA> dnas){

        for(int i=0;i<dnas.size();i++){
            for(int j=0;j<dnas.get(i).dna.size();j++) {
                for(int x=0;x<(currentDNA.valid ? 1 : 5);x++) {
                    if (Utils.generateRandomIntIntRange(0, 10) > 9) {
                        dnas.get(i).dna.set(j, getRandomPosition());
                    }
                }
            }
        }
        return dnas;
    }

}

