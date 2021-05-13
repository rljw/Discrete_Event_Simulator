package cs2030.simulator;

class Stats {
    private final double waitTime;
    private final int numServed;
    private final int numLeft;

    Stats(double waitTime, int numServed, int numLeft) {
        this.waitTime = waitTime;
        this.numServed = numServed;
        this.numLeft = numLeft;
    }

    Stats addWait(double time) {
        return new Stats(waitTime + time, numServed, numLeft);
    }

    Stats addServed() {
        return new Stats(waitTime, numServed + 1, numLeft);
    }

    Stats addLeft() {
        return new Stats(waitTime, numServed, numLeft + 1);
    }

    public String toString() {
        double avgWaitingTime = waitTime / numServed;
        if (numServed == 0) {
            return String.format("[%.3f %s %s]", 
                    0.0, numServed, numLeft);
        } else {
            return String.format("[%.3f %s %s]", 
                    avgWaitingTime, numServed, numLeft);
        }
    }

}
