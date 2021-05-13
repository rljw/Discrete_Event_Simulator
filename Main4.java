import java.util.Scanner;

import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import cs2030.simulator.Customer;
import cs2030.simulator.RunEvent;


class Main4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int numOfServers = sc.nextInt();
        int maxQueueLength = sc.nextInt();
        int numOfCustomers = sc.nextInt();

        List<Customer> customers = new ArrayList<Customer>();
        
        int count = 0;
        for (int i = 0; i < numOfCustomers; i++) {
            count++;
            double arrivalTime = sc.nextDouble();
            double serviceTime = sc.nextDouble();
            customers.add(new Customer(arrivalTime, count, serviceTime));
        }
        
        ArrayBlockingQueue<Double> restTime = new ArrayBlockingQueue<Double>(numOfCustomers); 
        while (sc.hasNextDouble()) {
            restTime.add(sc.nextDouble());
        }
       
        RunEvent runner = new RunEvent(numOfServers, maxQueueLength, customers, null);
        runner.run(restTime);
    }

}
