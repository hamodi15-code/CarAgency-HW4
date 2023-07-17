package graphic.vehicleTypes;

public class VehicleColorDecorator extends VehicleDecorator{

    public VehicleColorDecorator(VehicleColor coloredVehicle) {
        super(coloredVehicle);
    }

    @Override
    public void setVehicleColor(String color) {
        coloredVehicle.setVehicleColor(color);
    }
}
