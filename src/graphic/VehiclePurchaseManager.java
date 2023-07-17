package graphic;

import logic.Repository;
import vehicle.Vehicle;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class VehiclePurchaseManager {
    private static VehiclePurchaseManager vehiclePurchaseManager = null;
    private final Repository repository;
    private List<ChangeListener> listenerList;

    private VehiclePurchaseManager() {
        listenerList = new ArrayList<>();
        repository = Repository.getInstance();
    }

    public static VehiclePurchaseManager getInstance() {
        if (vehiclePurchaseManager == null) {
            vehiclePurchaseManager = new VehiclePurchaseManager();
        }
        return vehiclePurchaseManager;
    }


    public void purchase(Vehicle vehicle) {
        new Thread(() -> {
            try {
                JOptionPane.showMessageDialog(null, "wait...");
                int delay = ThreadLocalRandom.current().nextInt(5000, 10001);
                Thread.sleep(delay);
            } catch (InterruptedException e) {}
            new PurchaseDialog(vehicle);

        }).start();
    }




}

