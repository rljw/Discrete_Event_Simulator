package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

class DoneEvent extends Event {
    private final Server server;
    //private final Counter counter;

    DoneEvent(Customer customer, double time, Server server) {
        super(customer, time);
        this.server = server;
        //this.counter = counter;
    }

    @Override
    public String toString() {
        //System.out.println(server.getServiceDoneTime());
        return String.format("%s %s done serving by server %s",
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

    @Override
    Event getNextEvent(Server[] servers, ArrayBlockingQueue<Double> restTime,
            List<Double> serviceTime) {
        return getNextEvent(servers, restTime);
    }
 
    @Override
    Event getNextEvent(Server[] servers) {
        //System.out.println(server.getId() + "Serving:" + server.getServe());
        //System.out.println(server.getId() + "Waiting:" + server.getQueue()); 
        servers[server.getId() - 1].updateStatus();
        return null;
    }
    
    @Override
    Event getNextEvent(Server[] servers, ArrayBlockingQueue<Double> restTime) {
        servers[server.getId() - 1].doneServe();
        double restNow = restTime.poll();
        //System.out.println(server.getId() + "Serving:" + server.getServe());
        //System.out.println(server.getId() + "Waiting:" + server.getQueue()); 
        //System.out.println("rest" + restNow);

        if (restNow > 0.0) {
            servers[server.getId() - 1].setResting();
            servers[server.getId() - 1].addRest(restNow);
            //System.out.println(server.getRestTime());
            //System.out.println(server.getServiceDoneTime());
            return new RestEvent(super.getCustomer(), super.getTime(), this.server, restNow);
        }
        return null;
    }

    @Override
    Stats updateStats(Stats[] currStats) {
        return currStats[0];
    }

    /*    
    Counter getCounter() {
        return this.counter;
    }
    */

}
