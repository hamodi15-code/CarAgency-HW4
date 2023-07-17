package graphic.vehicleTypes;

import graphic.CarAgencyPanel;
import logic.Repository;
import utils.Call;
import utils.Callback;
import vehicle.Amphibious;
import vehicle.Bicycle;
import vehicle.Vehicle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class BicycleInitDialog extends JDialog {
    private CarAgencyPanel panel;
    private JButton okDetailsButton, imageUploadButton;

    private ImageIcon image1, image2, chosenImage =null;
    private JLabel label1,label2;
    private ImageIcon selectedImage = null;
    private  JTextField modelNameText, maxPassengersText, maxSpeedText, roadTypeText;
    private String colorOfTheVehicle;
    public BicycleInitDialog(){
        JPanel ImagePanel = new JPanel();
        ImagePanel.setLayout(new GridLayout(3,1,30,30));
        label1 = new JLabel();
        label2 = new JLabel();
        image1 = new ImageIcon("bicycle1.jpg");
        image2 = new ImageIcon("bicycle2.jpg");
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
        JPanel BicyclePanel = new JPanel();
        BicyclePanel.setLayout(new GridLayout(5,2,0,0));
        modelNameText = new JTextField();
        maxPassengersText = new JTextField();
        maxSpeedText = new JTextField();
        roadTypeText = new JTextField();
        BicyclePanel.add(new JLabel("Model name:"));
        BicyclePanel.add(modelNameText);
        BicyclePanel.add(new JLabel("Max Passengers:"));
        BicyclePanel.add(maxPassengersText);
        BicyclePanel.add(new JLabel("Max Speed:"));
        BicyclePanel.add(maxSpeedText);
        BicyclePanel.add(new JLabel("Road Type:"));
        BicyclePanel.add(roadTypeText);


        String [] colors= {"Pink","Purple", "Turquoise"};
        final JComboBox colorsComboBox=new JComboBox(colors);
        colorsComboBox.setBounds(50, 100,90,20);
        colorsComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorOfTheVehicle = (String) colorsComboBox.getItemAt(colorsComboBox.getSelectedIndex());
            }
        });
        BicyclePanel.add(colorsComboBox);

        JPanel okPlace = new JPanel();
        okDetailsButton = new JButton("OK");


        okDetailsButton.addActionListener(e -> btnApply());
        okPlace.add(okDetailsButton);

        imageUploadButton = new JButton("Upload image...");
        imageUploadButton.addActionListener(e -> uploadImage());

        BicyclePanel.add(imageUploadButton);
        add(okPlace,BorderLayout.SOUTH);
        add(BicyclePanel,BorderLayout.WEST);
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
        String chosenRoadType =(roadTypeText.getText());
        Vehicle vehicle = new Bicycle(chosenModelName, chosenMaxPassengers, chosenMaxSpeed, chosenRoadType);
        VehicleColor vehicleColor= new VehicleColorDecorator(vehicle);
        vehicleColor.setVehicleColor(colorOfTheVehicle);

        Repository repository = Repository.getInstance();
        Callback callback = repository.addVehicle(vehicle, selectedImage);
        callback.observe(new Call() {
            @Override
            public void onFinishProcess(boolean state) {
                JOptionPane.showMessageDialog(null,"Bicycle was added successfully !");
                setVisible(false);
            }

            @Override
            public void onStartProcess() {
                okDetailsButton.setEnabled(false);
            }
        });

    }
}
