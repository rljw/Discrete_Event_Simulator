package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

class WaitEvent extends Event {
    private final Server server;
    
    WaitEvent(Customer customer, double time, Server server) {
        super(customer, time);
        this.server = server;
    }

    @Override
    public String toString() {
        return String.format("%s %s waits at server %s",
                String.format("%.3f", super.getTime()),
                this.getCustomer().toString(),
                this.server.getId());
    }
    
    @Override
    Server getServer() {
        return this.server;
    }   
    
    @Override
    boolean isServeEvent() {
        return false;
    }

   
    double getServeTime() {
        return this.server.getServiceDoneTime();
    }

    double getWaitTime() {
        return this.getServeTime() - super.getTime();
    }

    @Override
    Event getNextEvent(Server[] servers) {
        /*
        if (server.getServe() != null) {
        System.out.println(server.getServe().getTime() + "+" +
                server.getServe().getWaitTime() + "+" +
                server.getServe().getServiceTime());
        }*/
        //System.out.println("NEXT SERVE TIME " + this.getServeTime());
        servers[server.getId() - 1].waitTrigger(super.getCustomer());
        super.getCustomer().wait(this.getWaitTime());
        
        //System.out.println(server.getId() + "Serving:" + server.getServe());
        //System.out.println(server.getId() + "Waiting:" + server.getQueue());
        //System.out.println(" NEXT SERVE TIME" + this.getServeTime());
        return new ServeEvent(
                super.getCustomer(), 
                this.getServeTime(),
                server);
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
    Stats updateStats(Stats[] currStats) {
        //System.out.println("ServiceEndTime: " + this.getServeTime());
        //System.out.println("Wait Time: " + this.getWaitTime());
        return currStats[0].addWait(this.getWaitTime());
    } 

}
