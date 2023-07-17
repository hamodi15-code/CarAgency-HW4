package vehicle;

import dto.WrongCaptureToRestoreException;
import utils.Capture;

import java.time.LocalDateTime;

public class SpyGlider extends AirVehicle implements NonMotorized {
    private char energyScore;
    private String powerSourceInformatin;

    public SpyGlider(String powerSourceInformatin) {
        super("Privileged", 1, 50, "Army");
        setPowerSourceInformation(powerSourceInformatin);
        setEnergyScore('C');
    }

    public void setPowerSourceInformation(String powerSourceInformatin) {
        this.powerSourceInformatin = powerSourceInformatin;
    }

    public void setEnergyScore(char energyScore) {
        this.energyScore = energyScore;
    }

    public String getPowerSourceInformation() {
        return powerSourceInformatin;
    }

    public char getEnergyScore() {
        return energyScore;
    }

    @Override
    public String toString() {
        return "Model is " + getModelName() + " , Traveled " + getKilometrage()
                + "km, Max speed of " + getMaxSpeed() + "km/h, "
                + "Max passangers " + getMaxPassengers() + " persons, "
                + "Use purpose " + getUsePurpose()
                + " Power source information " + getPowerSourceInformation()
                + ",Energy score is " + getEnergyScore()
                + "\n";
    }


    @Override
    public boolean equals(Object s) {
        if (this == s) return true;
        boolean ans = false;
        if (s instanceof SpyGlider) {
            ans = this.powerSourceInformatin == getPowerSourceInformation()
                    && this.getKilometrage() == ((SpyGlider) s).getKilometrage();
        }
        return ans;
    }


    @Override
    public Capture save() {
        return new State(energyScore, powerSourceInformatin, (AirVehicle.State) super.save());
    }

    @Override
    public void restore(Capture capture) {
        if(capture.getClass() != State.class) throw new WrongCaptureToRestoreException(getClass(), capture.getClass());
        super.restore(capture);
        State spyGlider = (State) capture;
        this.powerSourceInformatin = spyGlider.powerSourceInformatin;
        this.energyScore = spyGlider.energyScore;
    }

    class State implements Capture {

        private final char energyScore;
        private final String powerSourceInformatin;

        private final AirVehicle.State superState;

        State(char energyScore, String powerSourceInformatin, AirVehicle.State superState) {
            this.energyScore = energyScore;
            this.powerSourceInformatin = powerSourceInformatin;
            this.superState = superState;
        }

        @Override
        public LocalDateTime getTimestamp() {
            return superState.getTimestamp();
        }
    }
}
