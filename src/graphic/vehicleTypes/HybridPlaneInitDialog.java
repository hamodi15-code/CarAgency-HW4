package graphic.vehicleTypes;


import logic.Repository;
import utils.Call;
import utils.Callback;
import vehicle.HybridPlane;
import vehicle.Vehicle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class HybridPlaneInitDialog extends JDialog {
    private JButton okDetailsButton, imageUploadButton;
    private String chosenModelName, chosenPowerSourceInformation, chosenRoadType, chosenCountryFlag;
    private int chosenMaxSpeed, chosenAverageFuel, chosenEngineLife, chosenMaxPassengers, chosenNumOfWheels;
    private ImageIcon image1, image2, chosenImage =null;
    private JLabel label1,label2;
    private ImageIcon selectedImage = null;
    private JCheckBox isSailWithWind;
    private  JTextField modelNameText, maxPassengersText, maxSpeedText, numWheelsText, avgFuelText, avgEngineLifeText, countryFlagText;
    private String colorOfTheVehicle;
    public HybridPlaneInitDialog(){
        JPanel ImagePanel = new JPanel();
        ImagePanel.setLayout(new GridLayout(2,1,30,30));
        label1 = new JLabel();
        label2 = new JLabel();
        image1 = new ImageIcon("hybridplane1.jpg");
        image2 = new ImageIcon("hybridplane2.jpg");
        label1.setIcon(image1);
        label2.setIcon(image2);
        MouseListener listener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedImage = (ImageIcon) ((JLabel) e.getSource()).getIcon();
                JOptionPane.showMessageDialog(null,"Image chosen successfully !");
            }
        };
        label1.addMouseListener(listener);
        label2.addMouseListener(listener);
        ImagePanel.add(label1);
        ImagePanel.add(label2);
        add(ImagePanel,BorderLayout.EAST);
        JPanel HybridPlanePanel = new JPanel();
        HybridPlanePanel.setLayout(new GridLayout(10,2,0,0));
        modelNameText = new JTextField();
        maxPassengersText = new JTextField();
        maxSpeedText = new JTextField();
        numWheelsText = new JTextField();
        avgFuelText = new JTextField();
        avgEngineLifeText = new JTextField();
        countryFlagText = new JTextField();
        HybridPlanePanel.add(new JLabel("Model name:"));
        HybridPlanePanel.add(modelNameText);
        HybridPlanePanel.add(new JLabel("Max Passengers:"));
        HybridPlanePanel.add(maxPassengersText);
        HybridPlanePanel.add(new JLabel("Max Speed:"));
        HybridPlanePanel.add(maxSpeedText);
        HybridPlanePanel.add(new JLabel("Num of Wheels:"));
        HybridPlanePanel.add(numWheelsText);
        HybridPlanePanel.add(new JLabel("Average Fuel :"));
        HybridPlanePanel.add(avgFuelText);
        HybridPlanePanel.add(new JLabel("Average Engine Life:"));
        HybridPlanePanel.add(avgEngineLifeText);
        HybridPlanePanel.add(new JLabel("Sail With Wind:"));
        isSailWithWind = new JCheckBox();
        HybridPlanePanel.add(isSailWithWind);
        HybridPlanePanel.add(new JLabel("Country Flag:"));
        HybridPlanePanel.add(countryFlagText);

        String [] colors= {"Pink","Purple", "Turquoise"};
        final JComboBox colorsComboBox=new JComboBox(colors);
        colorsComboBox.setBounds(50, 100,90,20);
        colorsComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorOfTheVehicle = (String) colorsComboBox.getItemAt(colorsComboBox.getSelectedIndex());
            }
        });
        HybridPlanePanel.add(colorsComboBox);

        JPanel okPlace = new JPanel();
        okDetailsButton = new JButton("OK");
        okDetailsButton.addActionListener(e -> btnApply());
        okPlace.add(okDetailsButton);
        imageUploadButton = new JButton("Upload image...");
        imageUploadButton.addActionListener(e ->uploadImage());
        HybridPlanePanel.add(imageUploadButton);
        add(okPlace,BorderLayout.SOUTH);
        add(HybridPlanePanel,BorderLayout.WEST);
        pack();
    }
    private void uploadImage() {
        FileDialog fd = new FileDialog((Frame) null, "Please choose an image:", FileDialog.LOAD);
        fd.setVisible(true);
        if (fd.getFile() == null) {
            JOptionPane.showMessageDialog(null, "No image loaded");
        }
        else {
            File f = new File(fd.getDirectory(), fd.getFile());
            try {
                Image img1 = ImageIO.read(f);
                selectedImage = new ImageIcon(img1);
//                imageUploadButton.setVisible(false);
//                label1.setVisible(false);
//                label2.setVisible(false);
                JOptionPane.showMessageDialog(null,"Image chosen successfully !");
            } catch (IOException ex) {
                System.out.println("Cannot load image");
            }
        }
    }

    private void btnApply() {
        if(selectedImage == null){
            JOptionPane.showMessageDialog(null,"Select image first!");
            return;
        }
        chosenModelName =modelNameText.getText();
        chosenMaxPassengers = Integer.parseInt(maxPassengersText.getText());
        chosenMaxSpeed =Integer.parseInt(maxSpeedText.getText());
        chosenNumOfWheels =Integer.parseInt(numWheelsText.getText());
        chosenAverageFuel =Integer.parseInt(avgFuelText.getText());
        chosenEngineLife =Integer.parseInt(avgEngineLifeText.getText());
        chosenCountryFlag = countryFlagText.getText();

        Vehicle vehicle =new HybridPlane(chosenModelName, chosenMaxPassengers, chosenMaxSpeed, chosenNumOfWheels, isSailWithWind.isSelected(), chosenCountryFlag, chosenAverageFuel, chosenEngineLife);
        VehicleColor vehicleColor= new VehicleColorDecorator(vehicle);
        vehicleColor.setVehicleColor(colorOfTheVehicle);

        Repository repository = Repository.getInstance();
        Callback callback = repository.addVehicle(vehicle, selectedImage);
        callback.observe(new Call() {
            @Override
            public void onFinishProcess(boolean state) {
                JOptionPane.showMessageDialog(null,"Hybrid Plane was added successfully !");
                setVisible(false);
            }

            @Override
            public void onStartProcess() {
                okDetailsButton.setEnabled(false);
            }
        });

    }
}
