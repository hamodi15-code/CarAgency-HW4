package logic.instructors;

import vehicle.Vehicle;

public abstract class Task implements Runnable {
    private boolean running = false;
    private Vehicle vehicle;
    private int distance;

    public boolean isBusy(){
        return running;
    }
    public Vehicle getCurrentVehicle(){
        return vehicle;
    }

    public void prepareForDrive(Vehicle vehicle, int distance){
        this.vehicle = vehicle;
        this.distance = distance;
    }

    public abstract long calculateTimeForDistance(int distance);


    @Override
    public void run() {
        running = true;

        long time = calculateTimeForDistance(distance);

        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        vehicle.move(distance);
        vehicle = null;
        running = false;
    }
}
