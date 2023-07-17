package graphic;

import logic.TestDriveService;
import vehicle.Vehicle;

import javax.swing.*;

public class CarAgencyFrame extends JFrame {
    private static CarAgencyFrame carAgencyFrame = null;
    private CarAgencyFrame() {
        setTitle("Car Agency");
        setSize(1400, 650);
        add(new CarAgencyPanel());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
    }
    public static CarAgencyFrame getInstance() {
        if (carAgencyFrame == null) {
            carAgencyFrame = new CarAgencyFrame();
        }
        return carAgencyFrame;
    }

    public static void main(String[] args) {
        ImageIcon logo = new ImageIcon("logo.png");
        CarAgencyFrame carAgencyFrame = CarAgencyFrame.getInstance();
        carAgencyFrame.setVisible(true);
        carAgencyFrame.setIconImage(logo.getImage());

    }



    private void openAlert(String message){
        System.out.println(message);
        JOptionPane.showMessageDialog(this,
                message,
                "Warning",
                JOptionPane.INFORMATION_MESSAGE);
    }
}

