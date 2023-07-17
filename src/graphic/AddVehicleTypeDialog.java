package graphic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class AddVehicleTypeDialog extends JDialog {

    private CarAgencyPanel panel;
    private JButton OkType;

    private static final String[] Types = {"Frigate", "Jeep", "SpyGlider", "ToyGlider", "Amphibious", "Bicycle", "CruiseShip", "ElectricBicycle", "HybridPlane"};
    private static final int NUMOFTYPES = Types.length;

    private JRadioButton[] Select1;

    public AddVehicleTypeDialog(CarAgencyPanel panel, String title) {
        super(new JFrame(), title, true);
        this.panel = panel;
        setSize(700, 350);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLUE);
        GetVehicleType();
    }

    //Printing all existing types in a car dealership so that the user can choose the type he wants
    public void GetVehicleType() {
        JPanel vehicleChoice = new JPanel();
        vehicleChoice.setLayout(new GridLayout(4, 3, 15, 20));
        vehicleChoice.setBorder(BorderFactory.createTitledBorder("Choose the Vehicle Type:"));
        ButtonGroup vehicleType = new ButtonGroup();
        Select1 = new JRadioButton[NUMOFTYPES];

        // make the types as a group of buttons

        ItemListener itemListener = e -> selectVehicleType();//lambda

        for (int i = 0; i < NUMOFTYPES; i++) {
            Select1[i] = new JRadioButton(Types[i], false);
            Select1[i].addItemListener(itemListener);
            vehicleType.add(Select1[i]);
            vehicleChoice.add(Select1[i]);
        }
        OkType = new JButton("ok");
        //lambda
        OkType.addActionListener(e -> {
            if (panel.chosenType == null) {
                return;
            }
            setVisible(false);
            new AddVehicleDetailsDialog(panel.chosenType, "Details");
        });
        vehicleChoice.add(OkType);
        add(vehicleChoice);
    }

    private void selectVehicleType() {
        for (int i = 0; i < NUMOFTYPES; i++) {
            if (Select1[i].isSelected()) {
                panel.chosenType = Types[i];
                break;
            }
        }
    }


}
