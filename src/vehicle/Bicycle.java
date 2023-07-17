package vehicle;

import java.time.LocalDateTime;

public class Bicycle extends LandVehicle implements NonMotorized{

    public Bicycle(String modelName, int maxPassengers, int maxSpeed, String roadType) {
        super(modelName, maxPassengers, maxSpeed, 2, roadType);
    }

    @Override
    public String getPowerSourceInformation() {
        return "Manual";
    }

    @Override
    public char getEnergyScore() {
        return 'A';
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (other == this) {
            return true;
        }

        if (!(other instanceof Bicycle)) {
            return false;
        }

        Bicycle otherBicycle = (Bicycle) other;

        return getModelName().equals(otherBicycle.getModelName()) &&
                getMaxPassengers() == otherBicycle.getMaxPassengers() &&
                getMaxSpeed() == otherBicycle.getMaxSpeed() &&
                getRoadType().equals(otherBicycle.getRoadType()) &&
                getPowerSourceInformation().equals(otherBicycle.getPowerSourceInformation()) &&
                getEnergyScore() == otherBicycle.getEnergyScore();
    }

    @Override
    public String toString() {
        return "Bicycle model: " + getModelName() +
                ", max passengers: " + getMaxPassengers() +
                ", max speed: " + getMaxSpeed() +
                ", number of wheels: " + getNumOfWheels() +
                ", road type: " + getRoadType() +
                ", power source information: " + getPowerSourceInformation() +
                ", energy score: " + getEnergyScore();
    }


}
