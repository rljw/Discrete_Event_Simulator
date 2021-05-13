import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import cs2030.simulator.Customer;
import cs2030.simulator.RunEvent;


class Main3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int numOfServers = sc.nextInt();
        int maxQueueLength = sc.nextInt();
        
        List<Customer> customers = new ArrayList<Customer>();
        
        int count = 0;
        while (sc.hasNextDouble()) {
            count++;
            double arrivalTime = sc.nextDouble();
            double serviceTime = sc.nextDouble();
            boolean greedy = sc.nextBoolean();
            customers.add(new Customer(arrivalTime, count, serviceTime, greedy));
        }
       
        RunEvent runner = new RunEvent(numOfServers, maxQueueLength, customers, null);
        runner.run();
    }

}
