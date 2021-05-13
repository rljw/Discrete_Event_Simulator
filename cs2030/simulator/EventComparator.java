package cs2030.simulator;

import java.util.Comparator;

class EventComparator implements Comparator<Event> {
    
    private final double threshold = 1e-5;
    private final double negthreshold = -1e-5;

    @Override
    public int compare(Event e1, Event e2) {
        double result = e1.getTime() - e2.getTime();
        //System.out.println("" + negthreshold);
        if (result < threshold && result > negthreshold 
                && e1.getCustomer().getId() < e2.getCustomer().getId()) {
            return -1;
        } else if (result < threshold && result > negthreshold) {
            return 1;
        }
        if (result < 0) {
            //System.out.println(result);
            return -1;
        } else if (result > 0) {
            //System.out.println(result);
            return 1;
        } else if (e1.getCustomer().getId() < e2.getCustomer().getId()) {
            //System.out.println(result); 
            return -1;
        } else {
            return 1;
        }
    }

}
