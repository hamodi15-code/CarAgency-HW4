package graphic.vehicleTypes;

import logic.Repository;
import utils.Call;
import utils.Callback;
import vehicle.ToyGlider;
import vehicle.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ToyGliderInitDialog extends JDialog {
    private JLabel label1,label2;
    private  JTextField powerSourceText;
    private ImageIcon image1, image2;
    private JButton okDetailsButton, imageUploadButton;
    private ImageIcon selectedImage = null;
    private String colorOfTheVehicle;
    public ToyGliderInitDialog(){
        JPanel ImagePanel = new JPanel();
        ImagePanel.setLayout(new GridLayout(3,2,0,0));
        label1 = new JLabel();
        label2 = new JLabel();
        image1 = new ImageIcon("toyglider1.jpg");
        image2 = new ImageIcon("toyglider2.jpg");
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

        String [] colors= {"Pink","Purple", "Turquoise"};
        final JComboBox colorsComboBox=new JComboBox(colors);
        colorsComboBox.setBounds(50, 100,90,20);
        colorsComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorOfTheVehicle = (String) colorsComboBox.getItemAt(colorsComboBox.getSelectedIndex());
            }
        });
        ImagePanel.add(colorsComboBox);

        // default details for this type
        JPanel okPlace = new JPanel();
        okDetailsButton = new JButton("OK");
        okDetailsButton.addActionListener(e -> btnApply());
        okPlace.add(okDetailsButton);
        imageUploadButton = new JButton("Upload image..");
        imageUploadButton.addActionListener(e ->uploadImage());
        ImagePanel.add(imageUploadButton);
        add(okPlace,BorderLayout.SOUTH);
        add(ImagePanel,BorderLayout.CENTER);
        pack();
    }

    private void uploadImage() {

    }

    private void btnApply() {
        if(selectedImage == null){
            JOptionPane.showMessageDialog(null,"Select image first!");
            return;
        }
        Vehicle vehicle=new ToyGlider();
        VehicleColor vehicleColor= new VehicleColorDecorator(vehicle);
        vehicleColor.setVehicleColor(colorOfTheVehicle);

        Repository repository = Repository.getInstance();
        Callback callback = repository.addVehicle(vehicle, selectedImage);
        callback.observe(new Call() {
            @Override
            public void onFinishProcess(boolean state) {
                JOptionPane.showMessageDialog(null,"ToyGlider was added successfully !");
                setVisible(false);
            }

            @Override
            public void onStartProcess() {
                okDetailsButton.setEnabled(false);
            }
        });
    }
}
