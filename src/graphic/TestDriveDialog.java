package graphic;

import dto.NoSuchInstructorsException;
import dto.VehicleTakenException;
import logic.TestDriveService;
import vehicle.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.locks.Lock;

public class TestDriveDialog extends JDialog implements ActionListener {

    private JFrame frame;
    private JTextField textField;
    private JButton okButton;

    private double distance, sleepTime;
    private Vehicle tempVehicle;
    private TestDriveService testDriveService;

    public TestDriveDialog(Vehicle vehicle) {
        dispose();
        tempVehicle = vehicle;
        //this.index = index;
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setLocation(360, 360);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2, 0, 0));
        JLabel label = new JLabel("Enter The Distance:");
        panel.add(label);
        textField = new JTextField();
        panel.add(textField);
        JPanel OkPlace = new JPanel();
        okButton = new JButton("OK");
        okButton.addActionListener(this);
        OkPlace.add(okButton);
        frame.add(OkPlace, BorderLayout.SOUTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.setSize(700, 350);
        frame.pack();
        frame.setVisible(true);
        testDriveService = TestDriveService.getInstance();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            frame.setVisible(false);
            distance = Double.parseDouble((textField.getText()));
            try {
                testDriveService.goTestDrive(tempVehicle, (int) distance);
                JOptionPane.showMessageDialog(null, "test drive has started!");
            }catch (VehicleTakenException vte){
                JOptionPane.showMessageDialog(null, "this vehicle taken");
            }catch (NoSuchInstructorsException nsi){
                JOptionPane.showMessageDialog(null, "All competent instructors are busy, try later");
            }
            dispose();
        }
    }
}
