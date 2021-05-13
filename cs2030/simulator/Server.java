package cs2030.simulator;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.Iterator;

class Server {
   
    private final Customer[] serve;
    private final ArrayBlockingQueue<Customer> wait;
    private final int id;
    private final double[] serviceDoneTime;
    private final boolean[] resting;
    private final double[] restTime;
   
    Server(int id) {
        this.serve = new Customer[1];
        this.wait = new ArrayBlockingQueue<Customer>(1);
        this.id = id;
        this.serviceDoneTime = new double[]{0.0};
        this.resting = new boolean[]{false};
        this.restTime = new double[]{0.0};
    }
    
    Server(int id, int queueLength) {
        this.serve = new Customer[1];
        this.wait = new ArrayBlockingQueue<Customer>(queueLength);
        this.id = id;
        this.serviceDoneTime = new double[]{0.0};
        this.resting = new boolean[]{false};
        this.restTime = new double[]{0.0};
    }
    
    double getServiceDoneTime() {
        return serviceDoneTime[0];
    }

    int getId() {
        return this.id;
    }
    
    ArrayBlockingQueue<Customer> getQueue() {
        return this.wait;
    }

    boolean getResting() {
        return this.resting[0];
    }

    double getRestTime() {
        return this.restTime[0];
    }

    boolean canServe() {
        return serve[0] == null && resting[0] == false && this.getWait() == null;
    }

    boolean canWait() {
        if (wait.remainingCapacity() > 0) {
            return true;
        } 
        return false;
    }

    void serveTrigger(Customer newCust) {
        this.serve[0] = newCust;
        if (newCust != null) {
            this.serviceDoneTime[0] = this.getServiceEndTime(); 
            //System.out.println("[SERVE]SERVICEDONETIME UPDATE: " + this.serviceDoneTime[0]); 
        }
    }

    void waitTrigger(Customer newCust) {
        wait.add(newCust);
        if (newCust != null) {
            this.serviceDoneTime[0] = this.getServiceEndTime();
        }
        //System.out.println("[WAIT] SERVICEDONETIME UPDATE: " + this.serviceDoneTime[0]);
    }
    
    void doneServe() {
        this.serveTrigger(null);
    }

    void doNothing() {
        this.serve[0] = this.serve[0];
    }

    void updateStatus() {
        this.serve[0] = this.wait.poll();
        this.serviceDoneTime[0] = this.getServiceEndTime();
        //System.out.println("[UPDATE] SERVICEDONETIME UPDATE: " + this.serviceDoneTime[0]);
 
    }
   
    void setResting() {
        resting[0] = true;
    }

    void endRest() {
        resting[0] = false;
    }

    void addRest(double time) {
        //System.out.println("BEFORE CHANGE " + this.serviceDoneTime[0]);
        restTime[0] += time;
        this.serviceDoneTime[0] += time;
        //this.wait.forEach(x -> x.wait(time));

        //System.out.println("AFTER CHANGE " + restTime[0]);
        //System.out.println("[REST] SERVICEDONETIME :" + this.serviceDoneTime[0]);
    }
    
    void addServiceTime(double time) {
        this.serviceDoneTime[0] += time;
    }

    //Intermediate, used to calc moment you enter queue
    double getServiceEndTime() {
        //System.out.println("servicedonetime" + this.getServiceDoneTime());
        //System.out.println("service end :" + endTime);
        //System.out.println("rest time :" + restTime[0]);
        double endTime = 0.0;
        Customer[] queue = this.wait.toArray(new Customer[0]);
        for (int i = 0; i < queue.length; i++) {
            if (i != queue.length - 1) {       
                //System.out.println(queue[i]);
                endTime += queue[i].getServiceTime();
            }
        }
        //System.out.println("intermediate endtime: " + endTime); 
        if (this.getServe() != null) {
            endTime += this.getServe().getServeEndTime();
        } else {
            endTime += this.getServiceDoneTime();
        }
        
        //System.out.println("final endtime:" + endTime);
        //endTime += restTime[0];
        return endTime;
            
        //return this.serviceDoneTime[0];
    }

    double getQueueServiceTimes() {
        double time = 0.0;
        Customer[] queue = this.wait.toArray(new Customer[0]);
        for (int i = 0; i < queue.length; i++) {
            if (i != queue.length - 1) {       
                //System.out.println(queue[i]);
                time += queue[i].getServiceTime();
            }
        }
        return time; 
    }
    
    int getQueueLength() {
        if (this.canServe()) {
            return 0;
        } else {
            return 1 + this.wait.toArray(new Customer[0]).length;
        }
    }

    Customer getServe() {
        return this.serve[0];
    }

    Customer getWait() {
        return this.wait.peek();
    }

    void exitQueue() {
        this.wait.poll();
    }

}
