package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

class RestEvent extends Event {
    private final Server server;
    private final double restTime;

    RestEvent(Customer customer, double time, Server server, double restTime) {
        super(customer, time);
        this.server = server;
        this.restTime = restTime;
    }
    
    @Override
    public String toString() {
        return "";
        //System.out.println(super.getTime());
        //return super.getTime() + server.getId() + "rest for "  +restTime +
        //    "SERVER FREE TIME" + server.getServiceDoneTime();
    }

    @Override
    Event getNextEvent(Server[] servers) {
        //servers[server.getId() - 1].endRest();
        return new UnrestEvent(super.getCustomer(),
                super.getTime() + restTime,
                this.server);
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
