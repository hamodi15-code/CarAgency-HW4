package graphic.vehicleTypes;

import logic.Repository;
import utils.Call;
import utils.Callback;
import vehicle.Amphibious;
import vehicle.Vehicle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class AmphibiousInitDialog extends JDialog {
    private JButton okDetailsButton, imageUploadButton;

    private ImageIcon image1, image2, chosenImage =null;
    private JLabel label1,label2;
    private ImageIcon selectedImage = null;
    private JCheckBox isSailWithWind;
    private  JTextField modelNameText, maxPassengersText, maxSpeedText, numWheelsText, avgFuelText, avgEngineLife, countryFlagText;
    private String colorOfTheVehicle;
    public AmphibiousInitDialog(){
        JPanel ImagePanel = new JPanel();
        ImagePanel.setLayout(new GridLayout(2,1,30,30));
        label1 = new JLabel();
        label2 = new JLabel();
        image1 = new ImageIcon("amphibious1.jpg");
        image2 = new ImageIcon("amphibious2.jpg");
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
        JPanel AmphibiousPanel = new JPanel();
        AmphibiousPanel.setLayout(new GridLayout(10,2,0,0));
        modelNameText = new JTextField();
        maxPassengersText = new JTextField();
        maxSpeedText = new JTextField();
        numWheelsText = new JTextField();
        avgFuelText = new JTextField();
        avgEngineLife = new JTextField();
        countryFlagText = new JTextField();
        AmphibiousPanel.add(new JLabel("Model name:"));
        AmphibiousPanel.add(modelNameText);
        AmphibiousPanel.add(new JLabel("Max Passengers:"));
        AmphibiousPanel.add(maxPassengersText);
        AmphibiousPanel.add(new JLabel("Max Speed:"));
        AmphibiousPanel.add(maxSpeedText);
        AmphibiousPanel.add(new JLabel("Num of Wheels:"));
        AmphibiousPanel.add(numWheelsText);
        AmphibiousPanel.add(new JLabel("Average Fuel:"));
        AmphibiousPanel.add(avgFuelText);
        AmphibiousPanel.add(new JLabel("Average Engine Life:"));
        AmphibiousPanel.add(avgEngineLife);
        AmphibiousPanel.add(new JLabel("Does Sail With Wind?"));
        isSailWithWind = new JCheckBox();
        AmphibiousPanel.add(isSailWithWind);
        AmphibiousPanel.add(new JLabel("Country Flag:"));
        AmphibiousPanel.add(countryFlagText);


        String [] colors= {"Pink","Purple", "Turquoise"};
        final JComboBox colorsComboBox=new JComboBox(colors);
        colorsComboBox.setBounds(50, 100,90,20);
        colorsComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorOfTheVehicle = (String) colorsComboBox.getItemAt(colorsComboBox.getSelectedIndex());
            }
        });
        AmphibiousPanel.add(colorsComboBox);

        JPanel OkPlace = new JPanel();
        okDetailsButton = new JButton("OK");
        okDetailsButton.addActionListener(e -> btnApply());
        OkPlace.add(okDetailsButton);
        imageUploadButton = new JButton("Upload image...");
        imageUploadButton.addActionListener(e -> uploadImage());
        AmphibiousPanel.add(imageUploadButton);
        add(OkPlace,BorderLayout.SOUTH);
        add(AmphibiousPanel,BorderLayout.WEST);
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
        String chosenModelName =modelNameText.getText();
        int chosenMaxPassengers = Integer.parseInt(maxPassengersText.getText());
        int chosenMaxSpeed =Integer.parseInt(maxSpeedText.getText());
        int chosenNumOfWheels =Integer.parseInt(numWheelsText.getText());
        int chosenAverageFuel =Integer.parseInt(avgFuelText.getText());
        int chosenEngineLife =Integer.parseInt(avgEngineLife.getText());
        String chosenCountryFlag = countryFlagText.getText();

        Vehicle vehicle=new Amphibious(chosenModelName, chosenMaxPassengers, chosenMaxSpeed, chosenNumOfWheels, isSailWithWind.isSelected(), chosenCountryFlag, chosenAverageFuel, chosenEngineLife);
        VehicleColor vehicleColor= new VehicleColorDecorator(vehicle);
        vehicleColor.setVehicleColor(colorOfTheVehicle);

        Repository repository = Repository.getInstance();
        Callback callback = repository.addVehicle(vehicle, selectedImage);
        callback.observe(new Call() {
            @Override
            public void onFinishProcess(boolean state) {
                JOptionPane.showMessageDialog(null,"Amphibious was added successfully !");
                setVisible(false);
            }

            @Override
            public void onStartProcess() {
                okDetailsButton.setEnabled(false);
            }
        });
    }
}
