package logic;

import dto.WrongCaptureToRestoreException;
import utils.BDStateListener;
import utils.Callback;
import utils.Capture;
import utils.Captureable;
import vehicle.Vehicle;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.*;

public class Repository implements Captureable {

    private static Repository instance;
    public static Repository getInstance(){
        if(instance == null) {
            instance = new Repository();
        }
        return instance;
    }
    private List<BDStateListener> listeners = new ArrayList<>();

    private Repository() {}

    private List<Vehicle> vehicles = new ArrayList<>();
    private Map<Vehicle, ImageIcon> images = new HashMap<>();


    public Callback addVehicle(Vehicle vehicle, ImageIcon image){
        Callback callback = new Callback();
        new Thread(){
            @Override
            public void run() {
                yieldToListener();
                callback.notifyOnStart();
                processing();
                vehicles.add(vehicle);
                images.put(vehicle, image);
                callback.notifyOnFinish(true);
                notifyChanges();
            }
        }.start();

        return callback;
    }

    public Callback updatedInventoryReport(){
        Callback callback = new Callback();
        new Thread(){
            @Override
            public void run() {
                yieldToListener();
                callback.notifyOnStart();
                processing();
                callback.notifyOnFinish(true);
                notifyChanges();
            }
        }.start();

        return callback;
    }

    private void yieldToListener(){
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Callback removeVehicle(Vehicle vehicle){
        Callback callback = new Callback();
        new Thread(){
            @Override
            public void run() {
                callback.notifyOnStart();
                processing();
                boolean remove = vehicles.remove(vehicle);
                images.remove(vehicle);
                callback.notifyOnFinish(remove);
                notifyChanges();
            }
        }.start();
        return callback;
    }

    public Callback updateAll(boolean processing){
        Callback callback = new Callback();
        new Thread() {
            @Override
            public void run() {
                callback.notifyOnStart();
                if(processing) processing();
                notifyChanges();
                callback.notifyOnFinish(true);
            }
        }.start();
        return callback;
    }


    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void addOnRepChangeListener(BDStateListener listener){
        listeners.add(listener);
    }

    public void notifyChanges(){
        for (BDStateListener listener : listeners) {
            listener.onStateChange(vehicles);
        }
    }

    public Map<Vehicle, ImageIcon> getImages() {
        return images;
    }


    private void processing(){
        int time = (int) (Math.random() * 5 * 1000 + 3 * 1000);
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Capture save() {
        return new RepState(vehicles, images);
    }

    @Override
    public void restore(Capture capture) {
        if(capture.getClass() != RepState.class) throw new WrongCaptureToRestoreException(getClass(), capture.getClass());
        RepState repState = (RepState) capture;
        this.images = repState.images;
        this.vehicles = repState.vehicles;
        notifyChanges();
    }


    class RepState implements Capture{
        private List<Vehicle> vehicles;
        private Map<Vehicle, ImageIcon> images;
        private LocalDateTime timestamp;

        public RepState(List<Vehicle> vehicles, Map<Vehicle, ImageIcon> images) {
            this.vehicles = new ArrayList<>(vehicles);
            this.images = new HashMap<>(images);
            this.timestamp = LocalDateTime.now();
        }

        @Override
        public LocalDateTime getTimestamp() {
            return timestamp;
        }
    }

}
