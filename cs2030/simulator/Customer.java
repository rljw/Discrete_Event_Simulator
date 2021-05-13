package cs2030.simulator;

import java.util.function.Supplier;

public class Customer {
    private final double time;
    private final int id;
    //private static int counter = 1;
    private final double[] serviceTime;
    private final double[] waitTime;
    private final boolean greedy;
    //Constructors
    
    //For Main2
    public Customer(double time, int id, double serviceTime) {
        this.time = time;
        this.id = id; 
        this.waitTime = new double[]{0.0};
        this.serviceTime = new double[]{serviceTime};
        this.greedy = false;
    }

    //For Main3
    public Customer(double time, int id, double serviceTime, boolean greedy) {
        this.time = time;
        this.id = id; 
        this.waitTime = new double[]{0.0};
        this.serviceTime = new double[]{serviceTime};
        this.greedy = greedy;
    }

    //For Main5
    public Customer(double time, int id,  Supplier<Double> serveTime, boolean greedy) {
        this.time = time;
        this.id = id;
        this.waitTime = new double[]{0.0};
        this.greedy = greedy;
        this.serviceTime = new double[]{0.0};
    }

    /* 
    protected Customer(Customer customer, double wait) {
        this.time = customer.time;
        this.id = customer.id;
        this.waitTime = wait;
        this.serviceTime = customer.serviceTime;
        this.greedy = customer.greedy;
    }
    */
    double getTime() {
        return this.time;
    }

    int getId() {
        return this.id;
    }

    boolean isGreedy() {
        return this.greedy;
    }
    
    @Override
    public String toString() {
        if (this.greedy) {
            return this.getId() + "(greedy)";
        } else {
            return String.format("%s", this.getId());
        }
    }

    double getServiceTime() {
        return this.serviceTime[0];
    }
    
    void setService(double time) {
        this.serviceTime[0] = time;
    }
    
    double getWaitTime() {
        return this.waitTime[0];
    }

    void wait(double time) {
        this.waitTime[0] += time;
        //System.out.println(this.getId() +":::::::::::::::::::::::TIME CHANGE::::::"+ time );
 
    }
    
    double getServeEndTime() {
        return this.getTime() +
            this.getWaitTime() +
            this.getServiceTime();
    }

}
