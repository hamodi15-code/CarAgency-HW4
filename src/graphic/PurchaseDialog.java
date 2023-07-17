package graphic;

import logic.Repository;
import utils.Call;
import utils.Callback;
import vehicle.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PurchaseDialog extends JDialog implements ActionListener {
    private final Repository repository;
    private JFrame frame;
    private JButton yesButton, noButton;
    private JLabel label;
    private int index;
    Vehicle tempVehicle;

    public PurchaseDialog(Vehicle vehicle) {
        tempVehicle=vehicle;
        //this.index = index;
        repository = Repository.getInstance();
        frame = new JFrame();
        frame.setSize(250, 150);
        frame.setResizable(false);
        frame.setLocation(360, 360);
        frame.setLayout(new BorderLayout());
        label = new JLabel("Are you sure you want to buy?");
        frame.add(label, BorderLayout.CENTER);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        yesButton = new JButton("YES");
        yesButton.addActionListener(this);
        panel.add(yesButton);
        noButton = new JButton("NO");
        noButton.addActionListener(this);
        panel.add(noButton);
        frame.add(panel, BorderLayout.SOUTH);
        frame.setVisible(true);



    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == yesButton) {
            //tempVehicle = repository.getVehicles().get(index);
            Callback callback = repository.removeVehicle(tempVehicle);
            callback.observe(new Call() {
                @Override
                public void onFinishProcess(boolean state) {
                    JOptionPane.showMessageDialog(null, "Purchase completed successfully!");
                    frame.setVisible(false);
                }
                @Override
                public void onStartProcess() {
                    yesButton.setEnabled(false);
                }
            });

        } else {
            JOptionPane.showMessageDialog(null, "Your purchase is canceled!");
            frame.setVisible(false);
        }
    }
}
