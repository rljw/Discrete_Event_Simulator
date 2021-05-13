package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

class ArrivalEvent extends Event {
    
    ArrivalEvent(Customer customer, double time) {
        super(customer, time);
    }

    @Override
    Event getNextEvent(Server[] servers) {
        Server freeServer = getFreeServer(servers);
        //System.out.println(freeServer.getId());
        if (freeServer == null) {
            return new LeaveEvent(super.getCustomer(), 
                    super.getTime());

        } else if (freeServer.canServe()) {
            //servers[freeServer.getId() - 1].serveTrigger(super.getCustomer());
            return new ServeEvent(super.getCustomer(), 
                    super.getTime(), freeServer);

        } else if (freeServer.canWait()) {
            //servers[freeServer.getId() - 1].waitTrigger(super.getCustomer());
            return new WaitEvent(super.getCustomer(), 
                    super.getTime(), freeServer);
       
        } else {
            System.out.println("Error");
            return null;
        }
    }

    @Override
    Event getNextEvent(Server[] servers, ArrayBlockingQueue<Double> restTime,
            List<Double> serviceTime) {
        return getNextEvent(servers);
    }
 
    @Override
    Event getNextEvent(Server[] servers, ArrayBlockingQueue<Double> restTime) {
        return getNextEvent(servers);
    }
    
    @Override
    boolean isServeEvent() {
        return false;
    }

    Server getFreeServer(Server[] servers) {
        Server freeServer = null;
        for (Server server: servers) {
            if (server.canServe() && server.getWait() == null) {
                freeServer = server;
                return freeServer;
            }
        }

        if (super.getCustomer().isGreedy()) {
            for (Server server: servers) {
                if (freeServer == null && server.canWait()) {
                    freeServer = server;
                } else if (freeServer != null && 
                        server.getQueueLength() < freeServer.getQueueLength()) {
                    freeServer = server;
                } else if (freeServer != null && 
                        server.getQueueLength() == freeServer.getQueueLength()) {
                    continue;    
                }
            }
        } else {
            for (Server server: servers) {
                if (server.canWait()) {
                    freeServer = server;
                    break;
                } 
            }
        }
        
        return freeServer;
    }
    
    @Override
    Server getServer() {
        return null;
    }

    @Override
    public String toString() {
        return String.format("%s %s arrives",
                String.format("%.3f", super.getTime()), 
                this.getCustomer().toString());
    }
    
    @Override
    Stats updateStats(Stats[] currStats) {
        return currStats[0];
    }

}
