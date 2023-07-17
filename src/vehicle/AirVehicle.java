package vehicle;

import dto.WrongCaptureToRestoreException;
import utils.Capture;

import java.time.LocalDateTime;

public abstract class AirVehicle extends Vehicle {
    private String usePurpose;

    public AirVehicle(String modelName, int maxPassengers, int maxSpeed, String usePurpose) {
        super(modelName, maxPassengers, maxSpeed);
        this.usePurpose = usePurpose;
    }

    public String getUsePurpose() {
        return usePurpose;
    }

    public void setUsePurpose(String usePurpose) {
        this.usePurpose = usePurpose;
    }

    @Override
    public Capture save() {

        return new State(usePurpose, (Vehicle.State) super.save());
    }

    @Override
    public void restore(Capture capture) {
        if (capture.getClass() != State.class) throw new WrongCaptureToRestoreException(getClass(), capture.getClass());
        super.restore(capture);
        State airVehicle = (State) capture;
        this.usePurpose = airVehicle.usePurpose;
    }

    class State implements Capture {

        private final String usePurpose;
        private final Vehicle.State superState;

        State(String usePurpose, Vehicle.State superState) {
            this.usePurpose = usePurpose;
            this.superState = superState;
        }

        @Override
        public LocalDateTime getTimestamp() {
            return superState.getTimestamp();
        }
    }
}
