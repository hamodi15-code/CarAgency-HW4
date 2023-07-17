package vehicle;

import dto.WrongCaptureToRestoreException;
import utils.Capture;

import java.time.LocalDateTime;

public abstract class MarineVehicle extends Vehicle {
    private boolean sailWithWind;
    private String countryFlag;

    public MarineVehicle(String modelName, int maxPassengers, int maxSpeed, boolean sailWithWind, String countryFlag) {
        super(modelName, maxPassengers, maxSpeed);
        this.sailWithWind = sailWithWind;
        this.countryFlag = countryFlag;
    }

    public boolean getSailWithWind() {
        return sailWithWind;
    }

    public void setSailWithWind(boolean sailWithWind) {
        this.sailWithWind = sailWithWind;
    }

    public String getCountryFlag() {
        return countryFlag;
    }

    public void setCountryFlag(String countryFlag) {
        this.countryFlag = countryFlag;
    }


    @Override
    public Capture save() {
        return new State(sailWithWind, countryFlag, (Vehicle.State) super.save());
    }

    @Override
    public void restore(Capture capture) {
        if (capture.getClass() != State.class) throw new WrongCaptureToRestoreException(getClass(), capture.getClass());
        super.restore(capture);
        State marineVehicle = (State) capture;
        this.sailWithWind = marineVehicle.sailWithWind;
        this.countryFlag = marineVehicle.countryFlag;
    }

    class State implements Capture {

        private boolean sailWithWind;
        private String countryFlag;

        private Vehicle.State superState;

        State(boolean sailWithWind, String countryFlag, Vehicle.State superState) {
            this.sailWithWind = sailWithWind;
            this.countryFlag = countryFlag;
            this.superState = superState;
        }

        @Override
        public LocalDateTime getTimestamp() {
            return superState.getTimestamp();
        }
    }
}
