package vehicle;

import dto.WrongCaptureToRestoreException;
import utils.Capture;

import java.time.LocalDateTime;

public abstract class LandVehicle extends Vehicle {
    private int numOfWheels;
    private String roadType;

    public LandVehicle(String modelName, int maxPassengers, int maxSpeed, int numOfWheels, String roadType) {
        super(modelName, maxPassengers, maxSpeed);
        this.numOfWheels = numOfWheels;
        this.roadType = roadType;
    }

    public int getNumOfWheels() {
        return numOfWheels;
    }

    public void setNumOfWheels(int numOfWheels) {
        this.numOfWheels = numOfWheels;
    }

    public String getRoadType() {
        return roadType;
    }

    public void setRoadType(String roadType) {
        this.roadType = roadType;
    }

    @Override
    public Capture save() {
        return new State(numOfWheels, roadType, (Vehicle.State) super.save());
    }

    @Override
    public void restore(Capture capture) {
        if(capture.getClass() != State.class) throw new WrongCaptureToRestoreException(getClass(), capture.getClass());
        super.restore(capture);
        State saved = (State) capture;
        this.numOfWheels = saved.numOfWheels;
        this.roadType = saved.roadType;
    }

    class State implements Capture {
        private final int numOfWheels;
        private final String roadType;

        private final Vehicle.State superState;

        public State(int numOfWheels, String roadType, Vehicle.State superState) {
            this.numOfWheels = numOfWheels;
            this.roadType = roadType;
            this.superState = superState;
        }

        @Override
        public LocalDateTime getTimestamp() {
            return superState.getTimestamp();
        }
    }
}
