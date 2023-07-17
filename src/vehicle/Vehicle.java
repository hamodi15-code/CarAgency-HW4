package vehicle;


import dto.WrongCaptureToRestoreException;
import graphic.vehicleTypes.VehicleColor;
import utils.Capture;
import utils.Captureable;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class Vehicle implements Captureable, VehicleColor {
    public static double vehicleAgencyKm=0;
    protected String color=null;
    protected static int idCount = 0;
    protected int id;
    protected double kilometrage;
    protected String modelName;
    protected int maxPassengers;
    protected int maxSpeed;

    public Vehicle(String modelName, int maxPassengers, int maxSpeed) {
        this.kilometrage = 0;
        this.modelName = modelName;
        this.maxPassengers = maxPassengers;
        this.maxSpeed = maxSpeed;
        id = idCount++;
    }
    @Override
    public void setVehicleColor(String color) {
        this.color=color;
    }
    public String getVehicleColor() {
        return color;
    }

    public String getModelName() {
        return modelName;
    }

    public int getMaxPassengers() {
        return maxPassengers;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public double getKilometrage() {
        return kilometrage;
    }

    public void move(double distance) {
        this.kilometrage += distance;
        vehicleAgencyKm+=distance;
    }

    public void resetKilometrage() {
        this.kilometrage = 0;
        vehicleAgencyKm=0;
    }

   /* public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setKilometrage(double kilometrage) {
        this.kilometrage = kilometrage;
    }


    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setMaxPassengers(int maxPassengers) {
        this.maxPassengers = maxPassengers;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return id == vehicle.id && Double.compare(vehicle.kilometrage, kilometrage) == 0 && maxPassengers == vehicle.maxPassengers && maxSpeed == vehicle.maxSpeed && Objects.equals(modelName, vehicle.modelName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, kilometrage, modelName, maxPassengers, maxSpeed);
    }


    @Override
    public Capture save() {
        return new State(id, kilometrage, modelName, maxPassengers, maxSpeed);
    }

    @Override
    public void restore(Capture capture) {
        if(capture.getClass() != State.class) throw new WrongCaptureToRestoreException(capture.getClass(), getClass());
        State saved = (State) capture;
        this.kilometrage = saved.kilometrage;
        this.modelName = saved.modelName;
        this.maxPassengers = saved.maxPassengers;
        this.maxSpeed = saved.maxSpeed;
        id = saved.id;
    }

    class State implements Capture {
        private final int id;
        private final double kilometrage;
        private final String modelName;
        private final int maxPassengers;
        private final int maxSpeed;
        private final LocalDateTime time;

        public State(int id, double kilometrage, String modelName, int maxPassengers, int maxSpeed) {
            this.id = id;
            this.kilometrage = kilometrage;
            this.modelName = modelName;
            this.maxPassengers = maxPassengers;
            this.maxSpeed = maxSpeed;
            time = LocalDateTime.now();
        }
        @Override
        public LocalDateTime getTimestamp() {
            return time;
        }
    }
}
