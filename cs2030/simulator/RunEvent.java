package cs2030.simulator;

import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import cs2030.simulator.RandomGenerator;
import cs2030.simulator.EventComparator;

public class RunEvent {
    
    private final int numOfServers;
    private final Server[] servers;
    private final List<Customer> customers;
    private final PriorityQueue<Event> queue;
    private final Stats[] stats;
    private final List<Double> serviceTimes;

    //Constructors
    public RunEvent(int numOfServers, List<Customer> customers) {
        this.numOfServers = numOfServers;
        this.servers = new Server[numOfServers];
        for (int i = 0; i < numOfServers; i++) {
            servers[i] = new Server(i + 1);
        }
        this.customers = customers;
        this.queue = new PriorityQueue<Event>(new EventComparator());
        for (Customer customer: customers) {
            this.queue.add(new ArrivalEvent(customer, customer.getTime()));
        }
        this.stats = new Stats[]{new Stats(0, 0, 0)};
        this.serviceTimes = null;
        //this.restTime = new ArrayList<Double>(); 
    }
    
    //for main2
    public RunEvent(int numOfServers, int queuelength, List<Customer> customers, 
            List<Double> serviceTimes) {
        this.numOfServers = numOfServers;
        this.servers = new Server[numOfServers];
        for (int i = 0; i < numOfServers; i++) {
            servers[i] = new Server(i + 1, queuelength); //only diff from above
        }
        this.customers = customers;
        this.queue = new PriorityQueue<Event>(new EventComparator());
        for (Customer customer: customers) {
            this.queue.add(new ArrivalEvent(customer, customer.getTime()));
        }
        this.stats = new Stats[]{new Stats(0, 0, 0)};
        this.serviceTimes = serviceTimes;
        //this.restTime = new ArrayList<Double>();
    }

    //Main method: running the event
    public void run() {
        while (!queue.isEmpty()) {
            Event currEvent = queue.poll();
            System.out.println(currEvent);
            Event nextEvent = currEvent.getNextEvent(servers);
            if (nextEvent != null) {
                queue.add(nextEvent);
            }

            stats[0] = currEvent.updateStats(stats);
           
            //For Debugging
            /*
            for (Server server: servers) {
                System.out.println("Serving: " + server.getServe());
                System.out.println("Waiting: " + server.getWait());
            }
            */
        }
        System.out.println(stats[0]);
    }

    //For Main4
    public void run(ArrayBlockingQueue<Double> restTime) {
        //System.out.println(restTime);
        while (!queue.isEmpty()) {
            Event currEvent = queue.poll();
            /*
            if (currEvent.isServeEvent() && currEvent.getServer() != null) {
                System.out.println("CHECK" + currEvent.getServer().getServiceDoneTime());
            }
            */
            if (currEvent.isServeEvent() && !currEvent.getServer().canServe()) {
                double correctTime = currEvent.getServer().getServiceDoneTime()
                    - currEvent.getServer().getQueueServiceTimes();
                //System.out.println("RESTTIME" + currEvent.getServer().getRestTime());
                /*
                System.out.println(currEvent.getServer().getServiceDoneTime() +
                        "+" +
                        correctTime);
                */
                if (currEvent.getTime() != correctTime) {
                    //System.out.println("NOWSWRONGTIME" + currEvent.getTime());
                    //System.out.println("CHANGETIMEEEEE" + correctTime);
                    currEvent.getCustomer().wait(correctTime - currEvent.getTime());
                    
                    stats[0] = stats[0].addWait(correctTime - currEvent.getTime());
                    queue.add(new ServeEvent(currEvent.getCustomer(), 
                                correctTime, 
                               currEvent.getServer()));
                } else {
                    if (!currEvent.toString().equals("")) {
                        System.out.println(currEvent);
                    }
                    Event nextEvent = currEvent.getNextEvent(servers, restTime);
                    if (nextEvent != null) {
                        queue.add(nextEvent);
                    }
                
                    stats[0] = currEvent.updateStats(stats);
                }
                
            } else {
                if (!currEvent.toString().equals("")) {
                    System.out.println(currEvent);
                }
                Event nextEvent = currEvent.getNextEvent(servers, restTime);
                if (nextEvent != null) {
                    queue.add(nextEvent);
                }
                
                stats[0] = currEvent.updateStats(stats);
            }

            //For Debugging
            /*            
            for (Server server: servers) {
                System.out.println("Serving: " + server.getServe());
                System.out.println("Waiting: " + server.getWait());
                System.out.println("Rest Status ----------" + server.getResting());
            }
            */
        }
        System.out.println(stats[0]); 
    }

    //For Main5
    public static void run(int numOfServers, int maxQueueLength, int numOfCustomers,
            int seed, double arrivalRate, double serviceRate, double restingRate, 
            double restingProb, double greedyProb) {
        
        RandomGenerator rg = new RandomGenerator(seed, arrivalRate, 
                serviceRate, restingRate);
        //System.out.println(rg.genServiceTime());
        //Preparation
        PriorityQueue<Event> queue = new PriorityQueue<Event>(new EventComparator()); 
        
        Server[] servers = new Server[numOfServers];
        int queueLength = maxQueueLength;
        for (int i = 0; i < numOfServers; i++) {
            servers[i] = new Server(i + 1, queueLength);
        }
       
        ArrayBlockingQueue<Double> restTimes = new ArrayBlockingQueue<Double>(numOfCustomers);
        List<Double> serviceTimes = new ArrayList<Double>();

        for (int i = 0; i < numOfCustomers; i++) {
            serviceTimes.add(rg.genServiceTime());
            if (rg.genRandomRest() < restingProb) {
                restTimes.add(rg.genRestPeriod());
            } else {
                restTimes.add(0.0);
            }
        }

        //System.out.println(serviceTimes);

        List<Customer> customers = new ArrayList<Customer>();
        int count = 0;
        double arrivalTime = 0.0;
        for (int i = 0; i < numOfCustomers; i++) {
            count++;
            double custType = rg.genCustomerType();
            customers.add(new Customer(arrivalTime, count, 
                        0.0, custType < greedyProb));
            arrivalTime += rg.genInterArrivalTime();
        }
        
        for (Customer customer: customers) {
            queue.add(new ArrivalEvent(customer, customer.getTime()));
        }
 
        Stats[] stats = new Stats[]{new Stats(0, 0, 0)};
 
        //RunEvent runner = new RunEvent(numOfServers, maxQueueLength, customers);
        //runner.run(restTimes);
        while (!queue.isEmpty()) {
            Event currEvent = queue.poll();
            /*
            if (currEvent.isServeEvent() && currEvent.getServer() != null) {
                System.out.println("CHECK" + currEvent.getServer().getServiceDoneTime());
            }
            */
            if (currEvent.isServeEvent() && !currEvent.getServer().canServe()) {
                double correctTime = currEvent.getServer().getServiceDoneTime()
                    - currEvent.getServer().getQueueServiceTimes();
                //System.out.println("RESTTIME" + currEvent.getServer().getRestTime());
                /*
                System.out.println(currEvent.getServer().getServiceDoneTime() +
                        "+" +
                        correctTime);
                */
                //System.out.println("CHECK" + currEvent.getServer().getServiceDoneTime());
                if (currEvent.getTime() != correctTime) {
                    //System.out.println("NOWSWRONGTIME" + currEvent.getTime());
                    //System.out.println("CHANGETIMEEEEE" + correctTime);
                    currEvent.getCustomer().wait(correctTime - currEvent.getTime());
                    stats[0] = stats[0].addWait(correctTime - currEvent.getTime());
                    queue.add(new ServeEvent(currEvent.getCustomer(), 
                                correctTime, 
                               currEvent.getServer()));
                } else {
                    if (!currEvent.toString().equals("")) {
                        System.out.println(currEvent);
                    }
                    Event nextEvent = currEvent.getNextEvent(servers, restTimes, serviceTimes);
                    if (nextEvent != null) {
                        queue.add(nextEvent);
                    }
                
                    stats[0] = currEvent.updateStats(stats);
                }               
            } else {
                if (!currEvent.toString().equals("")) {
                    System.out.println(currEvent);
                }
                Event nextEvent = currEvent.getNextEvent(servers, restTimes, serviceTimes);
                if (nextEvent != null) {
                    queue.add(nextEvent);
                }
                
                stats[0] = currEvent.updateStats(stats);
            }
        }
        System.out.println(stats[0]);
    }
}
