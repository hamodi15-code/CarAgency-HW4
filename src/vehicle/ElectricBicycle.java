package vehicle;

import dto.WrongCaptureToRestoreException;
import utils.Capture;

import java.time.LocalDateTime;

public class ElectricBicycle extends LandVehicle implements Motorized{
    private int averageEngineLife;
    private int averageFuel;
    public ElectricBicycle(String modelName, int maxPassengers, int maxSpeed, String roadType,int averageEngineLife) {
        super(modelName, maxPassengers, maxSpeed, 2, roadType);
        setAverageEngineLife(averageEngineLife);
        setAverageFuel(20);
    }

    @Override
    public int getAverageFuel() {
        return averageFuel;
    }

    @Override
    public void setAverageFuel(int fuel) {
        this.averageFuel=fuel;
    }

    @Override
    public int getAverageEngineLife() {
        return averageEngineLife;
    }

    @Override
    public void setAverageEngineLife(int life) {
        this.averageEngineLife=life;
    }
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (other == this) {
            return true;
        }

        if (!(other instanceof ElectricBicycle)) {
            return false;
        }

        ElectricBicycle otherBicycle = (ElectricBicycle) other;

        return getModelName().equals(otherBicycle.getModelName()) &&
                getMaxPassengers() == otherBicycle.getMaxPassengers() &&
                getMaxSpeed() == otherBicycle.getMaxSpeed() &&
                getNumOfWheels() == otherBicycle.getNumOfWheels() &&
                getRoadType().equals(otherBicycle.getRoadType()) &&
                getAverageFuel() == otherBicycle.getAverageFuel()&&
                getAverageEngineLife() == otherBicycle.getAverageEngineLife();

    }

    @Override
    public String toString() {
        return "Electric Bicycle model: " + getModelName() +
                ", max passengers: " + getMaxPassengers() +
                ", max speed: " + getMaxSpeed() +
                ", number of wheels: " + getNumOfWheels() +
                ", road type: " + getRoadType() +
                ", average fuel: " + getAverageFuel() +
                ", average engine life: " + getAverageEngineLife();
    }


    @Override
    public Capture save() {
        return new MotorizedState(averageFuel, averageEngineLife, (Vehicle.State) super.save());
    }

    @Override
    public void restore(Capture capture) {
        if(capture.getClass() != MotorizedState.class) throw new WrongCaptureToRestoreException(getClass(), capture.getClass());
        MotorizedState motorizedState = (MotorizedState) capture;
        super.restore(motorizedState.superState);
        this.averageEngineLife = motorizedState.averageEngineLife;
        this.averageFuel = motorizedState.averageFuel;
    }

    class MotorizedState implements Capture{
        private int averageFuel;
        private int averageEngineLife;

        private Vehicle.State superState;

        MotorizedState(int averageFuel, int averageEngineLife, Vehicle.State superState) {
            this.averageFuel = averageFuel;
            this.averageEngineLife = averageEngineLife;
            this.superState = superState;
        }

        @Override
        public LocalDateTime getTimestamp() {
            return superState.getTimestamp();
        }
    }
}
