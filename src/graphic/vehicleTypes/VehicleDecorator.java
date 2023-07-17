package graphic.vehicleTypes;

public abstract class VehicleDecorator implements VehicleColor {
   protected VehicleColor coloredVehicle;
   public VehicleDecorator(VehicleColor coloredVehicle){
       this.coloredVehicle = coloredVehicle;
   }
    @Override
    public void setVehicleColor(String color) {
        coloredVehicle.setVehicleColor(color);
    }
}
