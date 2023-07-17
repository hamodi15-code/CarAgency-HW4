package vehicle;

public class HybridPlane extends Amphibious {
    private AirVehicle airVehicle;
    public HybridPlane(String modelName, int maxPassengers, int maxSpeed, int numOfWheels, boolean sailWithWind, String countryFlag, int averageFuel, int averageEngineLife) {
        super(modelName, maxPassengers, maxSpeed, numOfWheels, sailWithWind, countryFlag, averageFuel, averageEngineLife);
        airVehicle = new AirVehicle(modelName, maxPassengers, maxSpeed,  "Army") {};
    }
    public String getUsePurpose()
    {
        return airVehicle.getUsePurpose();
    }

    @Override
    public String toString() {
        String landVehicleDetails =
                "Model Name: " + landVehicle.getModelName() + "\n" +
                        " Max Passengers: " + landVehicle.getMaxPassengers() + "\n" +
                        " Max Speed: " + landVehicle.getMaxSpeed() + "\n" +
                        " Number of Wheels: " + landVehicle.getNumOfWheels() + "\n" +
                        " Road Type: " + landVehicle.getRoadType() + "\n" +
                        " Kilometrage: " + landVehicle.getKilometrage() + "\n";

        String marineVehicleDetails =
                " Sail With Wind: " + marineVehicle.getSailWithWind() + "\n" +
                        " Country Flag: " + marineVehicle.getCountryFlag() + "\n";

        String motorizedDetails =
                " Average Fuel: " + getAverageFuel() + "\n" +
                        " Average Engine Life: " + getAverageEngineLife() + "\n";
        String AirVehiclesDetails = " Use Purpose: "+ airVehicle.getUsePurpose();
        return " Hybrid Plane Vehicle Details:\n" +
                landVehicleDetails + "\n" +
                marineVehicleDetails + "\n" +
                motorizedDetails +"\n"+
                AirVehiclesDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof HybridPlane)) {
            return false;
        }
        HybridPlane hybridPlane = (HybridPlane) o;

        return hybridPlane.getModelName().equals(getModelName()) &&
                hybridPlane.getMaxPassengers() == getMaxPassengers() &&
                hybridPlane.getMaxSpeed() == getMaxSpeed() &&
                hybridPlane.getNumOfWheels() == getNumOfWheels() &&
                hybridPlane.getRoadType().equals(getRoadType()) &&
                hybridPlane.getSailWithWind() == getSailWithWind() &&
                hybridPlane.getCountryFlag().equals(getCountryFlag()) &&
                hybridPlane.getAverageFuel() == getAverageFuel() &&
                hybridPlane.getAverageEngineLife() == getAverageEngineLife()&&
                hybridPlane.getUsePurpose().equals(getUsePurpose());
    }
}
