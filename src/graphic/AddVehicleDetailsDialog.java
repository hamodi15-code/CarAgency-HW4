package graphic;

import graphic.vehicleTypes.*;

import javax.swing.*;
import java.awt.*;

public class AddVehicleDetailsDialog extends JDialog {
    private final String chosenType;
    public AddVehicleDetailsDialog(String type, String title) {
        super(new JFrame(), title, true);
        chosenType = type;
        setSize(700, 350);
        setLayout(new BorderLayout());
        GetVehicleDetails();

    }

    public void GetVehicleDetails() {
        switch (chosenType)
        {
            case "Jeep": {
                JeepInitDialog jeepInitDialog = new JeepInitDialog();
                jeepInitDialog.setVisible(true);
                break;
            }
            case "Frigate":{
                FrigateInitDialog frigateInitDialog = new FrigateInitDialog();
                frigateInitDialog.setVisible(true);
                break;
            }
            case"SpyGlider": {
                SpyGliderInitDialog spyGliderInitDialog = new SpyGliderInitDialog();
                spyGliderInitDialog.setVisible(true);
                break;
            }
            case "ToyGlider": {
                ToyGliderInitDialog toyGliderInitDialog = new ToyGliderInitDialog();
                toyGliderInitDialog.setVisible(true);
                break;
            }
            case"Bicycle":{
                BicycleInitDialog bicycleInitDialog = new BicycleInitDialog();
                bicycleInitDialog.setVisible(true);
                break;
            }
            case  "Amphibious":{
                AmphibiousInitDialog amphibiousInitDialog = new AmphibiousInitDialog();
                amphibiousInitDialog.setVisible(true);
                break;
            }
            case "CruiseShip": {
                CruiseShipInitDialog cruiseShipInitDialog = new CruiseShipInitDialog();
                cruiseShipInitDialog.setVisible(true);
                break;
            }
            case "ElectricBicycle":{
                ElectricBicycleInitDialog electricBicycleInitDialog = new ElectricBicycleInitDialog();
                electricBicycleInitDialog.setVisible(true);
                break;
            }
            case "HybridPlane":
            {
                HybridPlaneInitDialog hybridPlaneInitDialog = new HybridPlaneInitDialog();
                hybridPlaneInitDialog.setVisible(true);
                break;
            }
            default:{
                throw new IllegalArgumentException("sorry you need to enter a valid Type");
            }
        }

    }


}

