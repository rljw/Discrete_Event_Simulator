package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

public abstract class Event {
    private final Customer customer;
    private final double time;
    
    Event(Customer customer, double time) {
        this.customer = customer;
        this.time = time;
    }
    
    Customer getCustomer() {
        return this.customer;
    }

    double getTime() {
        return this.time;   
    }
    
    abstract boolean isServeEvent();

    abstract Server getServer();

    abstract Event getNextEvent(Server[] servers);
    
    abstract Event getNextEvent(Server[] servers, ArrayBlockingQueue<Double> restTime);

    abstract Event getNextEvent(Server[] servers, ArrayBlockingQueue<Double> restTime,
            List<Double> serviceTime);
   
    abstract Stats updateStats(Stats[] currStats);
}
