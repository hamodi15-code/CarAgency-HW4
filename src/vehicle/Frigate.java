package vehicle;

import dto.WrongCaptureToRestoreException;
import utils.Capture;

import java.time.LocalDateTime;

public class Frigate extends MarineVehicle implements Motorized {
    private int averageFuel;
    private int averageEngineLife;

    public Frigate(String modelName, int maxPassengers, int maxSpeed, boolean sailWithWind) {
        super(modelName, maxPassengers, maxSpeed, sailWithWind, "Israel");

        setAverageFuel(500);
        setAverageEngineLife(4);
    }

    public void setAverageFuel(int averageFuel) {
        this.averageFuel = averageFuel;
    }

    public void setAverageEngineLife(int averageEngineLife) {
        this.averageEngineLife = averageEngineLife;
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
    public String toString() {
        return "Model is " + getModelName() + " , Traveled " + getKilometrage()
                + "km, Max speed of " + getMaxSpeed() + "km/h, "
                + "Max passangers " + getMaxPassengers() + " persons, "
                + "Average engine life is " + getAverageEngineLife() + " years, "
                + ", Average fuel " + getAverageFuel() + "L " + "Sails under " + getCountryFlag() + " flag"
                + (getSailWithWind() ? "Sails with the wind" : "Sails agains the wind")
                + "\n";
    }


    @Override
    public boolean equals(Object f) {

        boolean ans = false;
        if (f instanceof Frigate) {
            ans = !(this.getModelName() == ((Frigate) f).getModelName())
                    && this.getKilometrage() == ((Frigate) f).getKilometrage()
                    && this.getMaxSpeed() == ((Frigate) f).getMaxSpeed();
        }
        return ans;
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
        private final int averageFuel;
        private final int averageEngineLife;

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
