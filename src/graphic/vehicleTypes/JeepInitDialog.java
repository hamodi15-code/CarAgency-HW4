package graphic.vehicleTypes;

import logic.Repository;
import utils.Call;
import utils.Callback;
import vehicle.Jeep;
import vehicle.Vehicle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class JeepInitDialog extends JDialog {
    private JLabel label1,label2;
    private  JTextField modelNameText, maxSpeedText, avgFuelText, avgEngineLifeText;
    private ImageIcon image1, image2;
    private JButton okDetailsButton, imageUploadButton;
    private ImageIcon selectedImage = null;
    private String colorOfTheVehicle;
    public JeepInitDialog(){
        setTitle("Init jeep details");
        JPanel ImagePanel = new JPanel();
        ImagePanel.setLayout(new GridLayout(2,1,0,30));
        label1 = new JLabel();
        label2 = new JLabel();
        image1 = new ImageIcon("jeep1.jpg");
        image2 = new ImageIcon("jeep2.jpg");
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
        JPanel JeepPanel = new JPanel();
        JeepPanel.setLayout(new GridLayout(6,2,0,0));
        modelNameText = new JTextField();
        maxSpeedText = new JTextField();
        avgFuelText = new JTextField();
        avgEngineLifeText = new JTextField();
        JeepPanel.add(new JLabel("Model name:"));
        JeepPanel.add(modelNameText);
        JeepPanel.add(new JLabel("Max speed:"));
        JeepPanel.add(maxSpeedText);
        JeepPanel.add(new JLabel("Average fuel:"));
        JeepPanel.add(avgFuelText);
        JeepPanel.add(new JLabel("Average engine life:"));
        JeepPanel.add(avgEngineLifeText);

        String [] colors= {"Pink","Purple", "Turquoise"};
        final JComboBox colorsComboBox=new JComboBox(colors);
        colorsComboBox.setBounds(50, 100,90,20);
        colorsComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorOfTheVehicle = (String) colorsComboBox.getItemAt(colorsComboBox.getSelectedIndex());
            }
        });
        JeepPanel.add(colorsComboBox);

        JPanel okPlace = new JPanel();
        okDetailsButton = new JButton("OK");

        okDetailsButton.addActionListener(e -> btnApply());
        okPlace.add(okDetailsButton);
        imageUploadButton = new JButton("Upload image...");
        imageUploadButton.addActionListener(e -> uploadImage());
        JeepPanel.add(imageUploadButton);
        add(okPlace,BorderLayout.SOUTH);
        add(JeepPanel,BorderLayout.WEST);
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

        String chosenModelName = modelNameText.getText();
        int chosenMaxSpeed = Integer.parseInt(maxSpeedText.getText());
        int chosenAverageFuel = Integer.parseInt(avgFuelText.getText());
        int chosenEngineLife = Integer.parseInt(avgEngineLifeText.getText());

        Vehicle vehicle = new Jeep(chosenModelName, chosenMaxSpeed, chosenAverageFuel, chosenEngineLife);
        VehicleColor vehicleColor= new VehicleColorDecorator(vehicle);
        vehicleColor.setVehicleColor(colorOfTheVehicle);

        Repository repository = Repository.getInstance();
        Callback callback = repository.addVehicle(vehicle, selectedImage);
        callback.observe(new Call() {
            @Override
            public void onFinishProcess(boolean state) {
                JOptionPane.showMessageDialog(null,"Jeep was added successfully!");
                setVisible(false);
            }

            @Override
            public void onStartProcess() {
                okDetailsButton.setEnabled(false);
            }
        });
    }

}
