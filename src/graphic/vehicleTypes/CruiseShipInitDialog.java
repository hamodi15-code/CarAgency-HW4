package graphic.vehicleTypes;

import logic.Repository;
import utils.Call;
import utils.Callback;
import vehicle.CruiseShip;
import vehicle.Vehicle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class CruiseShipInitDialog extends JDialog {
    private JButton okDetailsButton, imageUploadButton;
    private ImageIcon image1, image2, chosenImage =null;
    private JLabel label1,label2;
    private ImageIcon selectedImage = null;
    private  JTextField modelNameText, maxPassengersText, maxSpeedText, countryFlagText, avgFuelText, avgEngineLifeText;
    private String colorOfTheVehicle;
    public CruiseShipInitDialog(){
        JPanel ImagePanel = new JPanel();
        ImagePanel.setLayout(new GridLayout(3,1,30,30));
        label1 = new JLabel();
        label2 = new JLabel();
        image1 = new ImageIcon("cruiseship1.jpg");
        image2 = new ImageIcon("cruiseship2.jpg");
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
        JPanel CruiseShipPanel = new JPanel();
        CruiseShipPanel.setLayout(new GridLayout(7,2,0,0));
        modelNameText = new JTextField();
        maxPassengersText = new JTextField();
        maxSpeedText = new JTextField();
        countryFlagText = new JTextField();
        avgFuelText = new JTextField();
        avgEngineLifeText = new JTextField();
        CruiseShipPanel.add(new JLabel("Model name:"));
        CruiseShipPanel.add(modelNameText);
        CruiseShipPanel.add(new JLabel("Max Passengers:"));
        CruiseShipPanel.add(maxPassengersText);
        CruiseShipPanel.add(new JLabel("Max Speed:"));
        CruiseShipPanel.add(maxSpeedText);
        CruiseShipPanel.add(new JLabel("Country Flag:"));
        CruiseShipPanel.add(countryFlagText);
        CruiseShipPanel.add(new JLabel("Average Fuel:"));
        CruiseShipPanel.add(avgFuelText);
        CruiseShipPanel.add(new JLabel("Average Engine Life:"));
        CruiseShipPanel.add(avgEngineLifeText);

        String [] colors= {"Pink","Purple", "Turquoise"};
        final JComboBox colorsComboBox=new JComboBox(colors);
        colorsComboBox.setBounds(50, 100,90,20);
        colorsComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorOfTheVehicle = (String) colorsComboBox.getItemAt(colorsComboBox.getSelectedIndex());
            }
        });
        CruiseShipPanel.add(colorsComboBox);

        JPanel okPlace = new JPanel();
        okDetailsButton = new JButton("OK");

        okDetailsButton.addActionListener(e -> btnApply());
        okPlace.add(okDetailsButton);
        imageUploadButton = new JButton("Upload image...");
        imageUploadButton.addActionListener(e -> uploadImage());
        CruiseShipPanel.add(imageUploadButton);
        add(okPlace,BorderLayout.SOUTH);
        add(CruiseShipPanel,BorderLayout.WEST);
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
        String chosenModelName =modelNameText.getText();
        int chosenMaxPassengers = Integer.parseInt(maxPassengersText.getText());
        int chosenMaxSpeed =Integer.parseInt(maxSpeedText.getText());
        String chosenCountryFlag =(countryFlagText.getText());
        int chosenAverageFuel = Integer.parseInt(avgFuelText.getText());
        int chosenEngineLife = Integer.parseInt(avgEngineLifeText.getText());

        Vehicle vehicle = new CruiseShip(chosenModelName, chosenMaxPassengers, chosenMaxSpeed, chosenCountryFlag, chosenAverageFuel, chosenEngineLife);
        VehicleColor vehicleColor= new VehicleColorDecorator(vehicle);
        vehicleColor.setVehicleColor(colorOfTheVehicle);

        Repository repository = Repository.getInstance();
        Callback callback = repository.addVehicle(vehicle, selectedImage);
        callback.observe(new Call() {
            @Override
            public void onFinishProcess(boolean state) {
                JOptionPane.showMessageDialog(null,"CruiseShip was added successfully !");
                setVisible(false);
            }

            @Override
            public void onStartProcess() {
                okDetailsButton.setEnabled(false);
            }
        });

    }
}
