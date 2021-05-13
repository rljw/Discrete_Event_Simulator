import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import cs2030.simulator.Customer;
import cs2030.simulator.RunEvent;

class Main1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int numOfServers = sc.nextInt();
        
        //PriorityQueue<Event> queue = new PriorityQueue<Event>(new EventComparator());
        //Counter counter = new Counter(numOfServers);
        List<Customer> customers = new ArrayList<Customer>();
        
        int count = 0;
        while (sc.hasNextDouble()) {
            count++;
            double arrivalTime = sc.nextDouble();
            customers.add(new Customer(arrivalTime, count, 1.0, false));
        }
       
        RunEvent runner = new RunEvent(numOfServers, customers);
        runner.run();
    }

}
