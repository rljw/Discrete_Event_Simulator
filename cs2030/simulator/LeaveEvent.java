package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

class LeaveEvent extends Event {
    
    LeaveEvent(Customer customer, double time) {
        super(customer, time);
    }

    @Override
    public String toString() {
        return String.format("%s %s leaves",
                String.format("%.3f", super.getTime()),
                this.getCustomer().toString());
    }
    
    @Override
    boolean isServeEvent() {
        return false;
    }

    @Override
    Server getServer() {
        return null;
    }   

    @Override
    Event getNextEvent(Server[] servers, ArrayBlockingQueue<Double> restTime,
            List<Double> serviceTime) {
        return getNextEvent(servers);
    }
 
    @Override
    Event getNextEvent(Server[] servers) {
        return null;
    }
    
    @Override
    Event getNextEvent(Server[] servers, ArrayBlockingQueue<Double> restTime) {
        return getNextEvent(servers);
    }

    @Override
    Stats updateStats(Stats[] currStats) {
        return currStats[0].addLeft();
    }

    /*
    Counter getCounter() {
        return this.counter;
    }
    */
    


}
