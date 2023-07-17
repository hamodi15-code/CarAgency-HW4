package vehicle;

import dto.WrongCaptureToRestoreException;
import utils.Capture;

import java.time.LocalDateTime;

public class CruiseShip extends MarineVehicle implements Motorized, Commercial {
    private int averageFuel;
    private int averageEngineLife;
    public CruiseShip(String modelName, int maxPassengers, int maxSpeed, String countryFlag, int averageFuel, int averageEngineLife) {
        super(modelName, maxPassengers, maxSpeed, true, countryFlag);
        setAverageFuel(averageFuel);
        setAverageEngineLife(averageEngineLife);
    }

    @Override
    public String getLicenseType() {
        return "UNLIMITED";
    }

    @Override
    public int getAverageFuel() {
        return averageFuel;
    }

    @Override
    public int getAverageEngineLife() {
        return averageEngineLife;
    }

    @Override
    public void setAverageFuel(int fuel) {
        this.averageFuel=fuel;
    }

    @Override
    public void setAverageEngineLife(int life) {
        this.averageEngineLife=life;

    }

    @Override
    public String toString() {
        return "Model is "  + getModelName()+ " , Traveled " + getKilometrage()
                + "km, Max speed of "+ getMaxSpeed() + "km/h, "
                + "Max passangers " + getMaxPassengers() + " persons, "
                + "Average engine life is " + getAverageEngineLife() + " years, "
                + ", Average fuel " + getAverageFuel()+"L, " + "Sails under " + getCountryFlag() + " flag, "
                + (getSailWithWind() ? "Sails with the wind" : "Sails agains the wind")
                +"\n";
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CruiseShip otherShip)) {
            return false;
        }
        return super.equals(other) && this.averageFuel == otherShip.averageFuel
                && this.averageEngineLife == otherShip.averageEngineLife;
    }

    @Override
    public Capture save() {
        return new MotorizedState(averageFuel, averageEngineLife, (Vehicle.State) super.save());
    }

    @Override
    public void restore(Capture capture) {
        if(capture.getClass() != Jeep.MotorizedState.class) throw new WrongCaptureToRestoreException(getClass(), capture.getClass());
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
