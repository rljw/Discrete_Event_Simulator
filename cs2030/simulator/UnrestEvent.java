package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

class UnrestEvent extends Event {
    private final Server server;


    UnrestEvent(Customer customer, double time, Server server) {
        super(customer, time);
        this.server = server;
    }
    
    @Override
    public String toString() {
        return "";
    }

    @Override
    Event getNextEvent(Server[] servers) {
        servers[server.getId() - 1].endRest();
        return null;
    }
    
    @Override
    Event getNextEvent(Server[] servers, ArrayBlockingQueue<Double> restTime) {
        return getNextEvent(servers);
    }

    @Override
    Event getNextEvent(Server[] servers, ArrayBlockingQueue<Double> restTime,
            List<Double> serviceTime) {
        return getNextEvent(servers);
    }
 
    @Override
    Server getServer() {
        return this.server;
    }   
 
    @Override
    boolean isServeEvent() {
        return false;
    }

   
    @Override
    Stats updateStats(Stats[] currStats) {
        return currStats[0];
    }


}
