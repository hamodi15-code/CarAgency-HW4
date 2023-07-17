package graphic;

import logic.Repository;
import vehicle.Vehicle;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ChooseVehicleDialog extends JDialog {

    private final Repository repository;
    private final JLabel labelCurrentVehicle;
    private final JButton testDriveButton;
    private final JButton purchaseVehicleButton;
    private int imageNum = 0;
    private JPanel imagePanel;
    private List<JLabel> labelList;
    private Vehicle vehicleSelected;

    public ChooseVehicleDialog() {
        super(new JFrame(), "Choose Vehicle", false);
        repository = Repository.getInstance();
        setSize(900, 800);
        setLayout(new BorderLayout());
        imagePanel = new JPanel();
        int rows = imageNum / 2;
        imagePanel.setLayout(new GridLayout(rows, 3, 15, 20));
        add(imagePanel, BorderLayout.NORTH);
        setImageList();



        JPanel buttonsPanel = new JPanel();
        testDriveButton = new JButton("Test Drive");
        purchaseVehicleButton = new JButton("Purchase Vehicle");
        labelCurrentVehicle = new JLabel("no vehicle selected");



        buttonsPanel.add(labelCurrentVehicle);
        buttonsPanel.add(testDriveButton);
        buttonsPanel.add(purchaseVehicleButton);

        testDriveButton.setEnabled(false);
        purchaseVehicleButton.setEnabled(false);

        imagePanel.add(buttonsPanel, BorderLayout.SOUTH);


        testDriveButton.addActionListener(e -> testDriveFunction());
        purchaseVehicleButton.addActionListener(e -> purchaseFunction());
        repository.addOnRepChangeListener(updatedBD -> updateItemList());
    }

    private void updateItemList() {
        imagePanel.removeAll();
        setImageList();
        repaint();
        revalidate();
    }

    public void setImageList() {
        Map<Vehicle, ImageIcon> images = repository.getImages();
        labelList = new ArrayList<>();
        System.out.println(images.size());
        //Border colorfulLine = BorderFactory.createLineBorder(Color.)

        for (Map.Entry<Vehicle, ImageIcon> entry : images.entrySet()) {
            Color color=null;
            if(entry.getKey().getVehicleColor() == "Pink"){
                color = Color.MAGENTA;
            }
            if(entry.getKey().getVehicleColor() == "Purple"){
                color = new Color(189,171,217);
            }
            if(entry.getKey().getVehicleColor() == "Turquoise"){
                color = new Color(47,213,201);
            }

            Border colorfulLine = BorderFactory.createLineBorder(color,20);
            JLabel label = new JLabel();
            label.setIcon(entry.getValue());
            label.setBorder(colorfulLine);
            label.addMouseListener(new OnImageClicked());

            label.setToolTipText(entry.getKey().toString());
            labelList.add(label);
            imagePanel.add(label);
        }
    }

    private void onVehicleClicked(Vehicle vehicle){
        vehicleSelected = vehicle;

        testDriveButton.setEnabled(true);
        purchaseVehicleButton.setEnabled(true);
        labelCurrentVehicle.setText(vehicle.toString());
    }

    public void purchaseFunction() {
        if(vehicleSelected == null) return;
        VehiclePurchaseManager.getInstance().purchase(vehicleSelected);
    }

    public void testDriveFunction() {
        if(vehicleSelected == null) return;
        new TestDriveDialog(vehicleSelected);
    }



    class OnImageClicked extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e) {
            int i = labelList.indexOf(e.getSource());
            Vehicle vehicle = repository.getVehicles().get(i);
            onVehicleClicked(vehicle);
        }
    }

}
