package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;  
import java.util.concurrent.ArrayBlockingQueue;

class ServeEvent extends Event {
    private final Server server;
    
    ServeEvent(Customer customer, double time, Server server) {
        super(customer, time);
        this.server = server;
    }

    @Override
    public String toString() {
        return String.format("%s %s serves by server %s", 
                String.format("%.3f", super.getTime()), 
                this.getCustomer().toString(),
                this.server.getId());
    }
    
    @Override
    boolean isServeEvent() {
        return true;
    }

  
    @Override
    Event getNextEvent(Server[] servers) {
        //counter.detrigger(server);
        if (this.server.getWait() != null) {
            //System.out.println("UPDATING STATUS");
            servers[server.getId() - 1].updateStatus();
        } else if (this.server.canServe()) {
            servers[server.getId() - 1].serveTrigger(super.getCustomer());
            //servers[server.getId() - 1].addServiceTime(
            //        super.getCustomer().getServiceTime());
        } else if (this.server.getServe().getId() == super.getCustomer().getId()) {
            //System.out.println("ERROR");
            servers[server.getId() - 1].doNothing();
        }        
        //System.out.println(server.getId() + "Serving:" + server.getServe());
        //System.out.println(server.getId() + "Waiting:" + server.getQueue());
        /* 
        System.out.println(server.getServe().getTime() + "+" +
                server.getServe().getWaitTime() + "+" +
                server.getServe().getServiceTime());
        System.out.println("NEXT SERVICE TIME" + server.getServiceDoneTime());        
        */
        //System.out.println(super.getTime() + "+" + super.getCustomer().getServiceTime());
        return new DoneEvent(super.getCustomer(), 
                super.getTime() + super.getCustomer().getServiceTime(), server);
    }

    @Override
    Event getNextEvent(Server[] servers, ArrayBlockingQueue<Double> restTime) {
        return getNextEvent(servers);
    }

    @Override
    Event getNextEvent(Server[] servers, ArrayBlockingQueue<Double> restTime,
            List<Double> serviceTime) {
        super.getCustomer().setService(serviceTime.remove(0));
        //System.out.println("UPDARE CUSTSVCTIME " + super.getCustomer().getWaitTime());
        /*
        System.out.println(super.getCustomer().getTime() + "+" +
                super.getCustomer().getWaitTime() + "+" +
                super.getCustomer().getServiceTime());
        */
        if (this.server.getWait() != null) {
            //System.out.println("UPDATING STATUS");
            servers[server.getId() - 1].updateStatus();
        } else if (this.server.canServe()) {
            servers[server.getId() - 1].serveTrigger(super.getCustomer());
            //servers[server.getId() - 1].addServiceTime(
            //        super.getCustomer().getServiceTime());
        } else if (this.server.getServe().getId() == super.getCustomer().getId()) {
            //System.out.println("ERROR");
            servers[server.getId() - 1].doNothing();
        }        
        //System.out.println(server.getId() + "Serving:" + server.getServe());
        //System.out.println(server.getId() + "Waiting:" + server.getQueue());
        
        return new DoneEvent(super.getCustomer(), 
                super.getTime() + super.getCustomer().getServiceTime(), server);
 
    }


    @Override
    Server getServer() {
        return this.server;
    }    

    @Override
    Stats updateStats(Stats[] currStats) {
        return currStats[0].addServed();
    }
}
