
import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import cs2030.simulator.Customer;
import cs2030.simulator.RunEvent;
import cs2030.simulator.RandomGenerator;

class Main5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int numOfServers = sc.nextInt();
        int maxQueueLength = sc.nextInt();
        int numOfCustomers = sc.nextInt();
        int seed = sc.nextInt();

        double arrivalRate = sc.nextDouble();
        double serviceRate = sc.nextDouble();
        double restingRate = sc.nextDouble();
        double restingProb = sc.nextDouble();
        double greedyProb = sc.nextDouble();

        RunEvent.run(numOfServers, maxQueueLength, numOfCustomers, seed, 
                arrivalRate, serviceRate, restingRate, restingProb, greedyProb);
    }

}
