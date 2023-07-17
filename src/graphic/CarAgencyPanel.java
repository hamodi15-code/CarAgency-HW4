package graphic;

import logic.Repository;
import logic.SaveHistory;
import utils.Call;
import utils.Callback;
import vehicle.Amphibious;
import vehicle.MarineVehicle;
import vehicle.Vehicle;

import javax.swing.*;
import java.awt.*;

/**
 * The CarAgencyPanel class represents a panel for a car agency application.
 * <p>
 * It provides a user interface with various buttons and functionalities
 * <p>
 * related to managing vehicles in the agency.
 */
public class CarAgencyPanel extends JPanel {
    private final Repository repository;
    public String chosenType;
    private JButton resetKmButton;
    private JButton resetFlagButton;

    private JButton totalKmButton;
    private SaveHistory saveHistory = new SaveHistory();
    private JButton loadButton;

    /**
     * Constructs a CarAgencyPanel object.
     * Sets the layout of the panel and initializes the repository.
     * Creates the main menu bar for the car agency application.
     */
    public CarAgencyPanel() {
        this.setLayout(new BorderLayout());
        repository = Repository.getInstance();
        createMenuBar();
    }

    /**
     * Creates the main menu bar for the car agency application.
     * <p>
     * Adds a background image, buttons for different operations,
     * <p>
     * and sets up event listeners for these buttons.
     */
    private void createMenuBar() {
        JLabel label = new JLabel();
        ImageIcon image = new ImageIcon("back1.png");
        label.setIcon(image);
        this.add(label);





        JPanel mainMenu = new JPanel();
        mainMenu.setLayout(new GridLayout(1, 7, 8, 0));
        JButton addVehicleButton = new JButton("Add vehicle");
        JButton chooseVehicleButton = new JButton("In stock vehicles");
        resetKmButton = new JButton("Reset KM for all");
        resetFlagButton = new JButton("Reset flag for all");
        JButton currentInventoryButton = new JButton("Current inventory");
        JButton exitButton = new JButton("Exit");
        Color fg = new Color(161, 109, 63);
        addVehicleButton.setForeground(fg);
        chooseVehicleButton.setForeground(fg);
        resetKmButton.setForeground(fg);
        resetFlagButton.setForeground(fg);
        exitButton.setForeground(fg);
        currentInventoryButton.setForeground(fg);

        //activate the main menu
        mainMenu.add(addVehicleButton);
        addVehicleButton.addActionListener(e -> connectToAddVehicleDialog());
        mainMenu.add(chooseVehicleButton);
        chooseVehicleButton.addActionListener(e -> connectToChooseVehicleDialog());
        mainMenu.add(resetKmButton);
        resetKmButton.addActionListener(e -> ResetKMFunction());
        mainMenu.add(resetFlagButton);
        resetFlagButton.addActionListener(e -> connectToFlagChangeDialog());
        currentInventoryButton.addActionListener(e -> connectToInventoryDialog());
        mainMenu.add(currentInventoryButton);
        totalKmButton=new JButton("Total KM");
        totalKmButton.setForeground(fg);
        totalKmButton.addActionListener(e->updatedTotalKM());
        mainMenu.add(totalKmButton);
        mainMenu.add(exitButton);


        JButton saveButton = new JButton("Save");
        saveButton.setForeground(fg);
        saveButton.addActionListener(e -> saveBtn());
        loadButton = new JButton("Load (0)");
        loadButton.setForeground(fg);
        loadButton.setEnabled(false);
        loadButton.addActionListener(e -> loadBtn());

        mainMenu.add(saveButton);
        mainMenu.add(loadButton);

        //mainMenu.add(new JLabel("Total KM "+Vehicle.vehicleAgencyKm));

        exitButton.addActionListener(e -> System.exit(0));
        this.add(mainMenu, BorderLayout.NORTH);
    }

    private void loadBtn() {
        repository.restore(saveHistory.load());

        int size = saveHistory.getSize();
        loadButton.setText("load (%d)".formatted(size));
        loadButton.setEnabled(size != 0);
    }

    private void saveBtn() {
        saveHistory.save(repository.save());
        int size = saveHistory.getSize();
        loadButton.setText("load (%d)".formatted(size));
        loadButton.setEnabled(size != 0);
    }

    private void updatedTotalKM(){
        JOptionPane.showMessageDialog(null, "Total KM "+Vehicle.vehicleAgencyKm);
    }
    /**
     * Opens a dialog box showing the current inventory report
     * if there are vehicles registered in the car agency.
     */
    public void connectToInventoryDialog() {
        if (repository.getVehicles().size() == 0) {
            JOptionPane.showMessageDialog(null, "No Vehicles Registered In Car Agency !");
            return;
        }
        ReportDialog report = new ReportDialog("Current inventory report");
        report.setVisible(true);
    }

    /**
     * Resets the kilometer reading for all vehicles in the car agency.
     * Updates the database asynchronously and displays appropriate messages.
     */
    public void ResetKMFunction() {
        if (repository.getVehicles().size() == 0) {
            JOptionPane.showMessageDialog(null, "No Vehicles Registered In Car Agency !");
            return;
        }
        // Go through the  list of vehicles and reset the kilometer.
        for (Vehicle vehicle : repository.getVehicles()) {
            vehicle.resetKilometrage();
        }
        Callback callback = repository.updateAll(true);
        callback.observe(new Call() {
            @Override
            public void onFinishProcess(boolean state) {
                JOptionPane.showMessageDialog(null, "Reset KM for All Vehicles successfully !");
                resetKmButton.setEnabled(true);
            }

            @Override
            public void onStartProcess() {
                resetKmButton.setEnabled(false);
                JOptionPane.showMessageDialog(null, "DataBase is updating now...");
            }
        });
    }

    /**
     * Opens a dialog box for changing the flag of marine vehicles.
     * <p>
     * Updates the database asynchronously and displays appropriate messages.
     */
    public void connectToFlagChangeDialog() {
        boolean doesMarineExist = false;
        if (repository.getVehicles().size() == 0) {
            JOptionPane.showMessageDialog(null, "No Vehicles Registered In Car Agency !");
            return;
        }

        for (Vehicle vehicle : repository.getVehicles()) {
            if (vehicle instanceof MarineVehicle || vehicle instanceof Amphibious) {
                doesMarineExist = true;
                break;
            }
        }
        if (!doesMarineExist) {
            JOptionPane.showMessageDialog(null, "No marine vehicles registered in car agency !");
            return;
        }

        FlagChangeDialog flagChange = new FlagChangeDialog(this, "Flag Changing ");
        flagChange.setVisible(true);
        Callback callback = repository.updateAll(true);
        callback.observe(new Call() {
            @Override
            public void onFinishProcess(boolean state) {
                JOptionPane.showMessageDialog(null, "Reset flag for all MarineVehicles successfully !");
                resetFlagButton.setEnabled(true);
            }

            @Override
            public void onStartProcess() {
                resetFlagButton.setEnabled(false);
                JOptionPane.showMessageDialog(null, "DataBase is updating now...");
            }
        });


    }

    /**
     * Opens a dialog box for choosing a vehicle from the existing vehicles in the car agency.
     */
    public void connectToChooseVehicleDialog() {
        if (repository.getVehicles().size() == 0) {
            JOptionPane.showMessageDialog(null, "No Vehicles Registered In Car Agency !");
            return;
        }
        ChooseVehicleDialog chooseVehicle = new ChooseVehicleDialog();
        chooseVehicle.setVisible(true);
    }

    /**
     * Opens a dialog box for adding a new vehicle type.
     */
    public void connectToAddVehicleDialog() {
        AddVehicleTypeDialog carType = new AddVehicleTypeDialog(this, "Choose car type:");
        carType.setVisible(true);
        carType.dispose();
    }

}
