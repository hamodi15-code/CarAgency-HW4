package graphic.vehicleTypes;

import logic.Repository;
import utils.Call;
import utils.Callback;
import vehicle.Frigate;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import vehicle.Vehicle;


public class FrigateInitDialog extends JDialog {
    private JButton okDetailsButton, imageUploadButton;
    private ImageIcon image1, image2;
    private JLabel label1,label2;
    private JCheckBox isSailWithWind;
    private ImageIcon selectedImage = null;
    private  JTextField modelNameText, maxPassengersText, maxSpeedText;
    private String colorOfTheVehicle;
    public FrigateInitDialog(){
        JPanel ImagePanel = new JPanel();
        ImagePanel.setLayout(new GridLayout(3,1,30,30));
        label1 = new JLabel();
        label2 = new JLabel();
        image1 = new ImageIcon("frigate1.jpg");
        image2 = new ImageIcon("frigate2.jpg");
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
        JPanel FrigatePanel = new JPanel();
        FrigatePanel.setLayout(new GridLayout(5,2,0,0));
        modelNameText = new JTextField();
        maxPassengersText = new JTextField();
        maxSpeedText = new JTextField();
        FrigatePanel.add(new JLabel("Model name:"));
        FrigatePanel.add(modelNameText);
        FrigatePanel.add(new JLabel("Max Passengers:"));
        FrigatePanel.add(maxPassengersText);
        FrigatePanel.add(new JLabel("Max Speed:"));
        FrigatePanel.add(maxSpeedText);
        FrigatePanel.add(new JLabel("Sail With Wind:"));
        isSailWithWind = new JCheckBox();
        FrigatePanel.add(isSailWithWind);

        String [] colors= {"Pink","Purple", "Turquoise"};
        final JComboBox colorsComboBox=new JComboBox(colors);
        colorsComboBox.setBounds(50, 100,90,20);
        colorsComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorOfTheVehicle = (String) colorsComboBox.getItemAt(colorsComboBox.getSelectedIndex());
            }
        });
        FrigatePanel.add(colorsComboBox);

        JPanel okPlace = new JPanel();
        okDetailsButton = new JButton("OK");


        okDetailsButton.addActionListener(e -> btnApply());
        okPlace.add(okDetailsButton);
        imageUploadButton = new JButton("Upload image...");
        imageUploadButton.addActionListener(e -> uploadImage());
        FrigatePanel.add(imageUploadButton);
        add(okPlace,BorderLayout.SOUTH);
        add(FrigatePanel,BorderLayout.WEST);
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
        // convert the text field to the appropriate type.
        String chosenModelName =modelNameText.getText();
        int chosenMaxPassengers = Integer.parseInt(maxPassengersText.getText());
        int chosenMaxSpeed =Integer.parseInt(maxSpeedText.getText());

        Vehicle vehicle=new Frigate(chosenModelName, chosenMaxPassengers, chosenMaxSpeed, isSailWithWind.isSelected());
        VehicleColor vehicleColor= new VehicleColorDecorator(vehicle);
        vehicleColor.setVehicleColor(colorOfTheVehicle);

        Repository repository = Repository.getInstance();
        Callback callback = repository.addVehicle(vehicle, selectedImage);
        callback.observe(new Call() {
            @Override
            public void onFinishProcess(boolean state) {
                JOptionPane.showMessageDialog(null,"Frigate was added successfully !");
                setVisible(false);
            }

            @Override
            public void onStartProcess() {
                okDetailsButton.setEnabled(false);
            }
        });
    }
}
