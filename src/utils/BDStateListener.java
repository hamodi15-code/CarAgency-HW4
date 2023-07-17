package utils;

import vehicle.Vehicle;

import java.util.List;

public interface BDStateListener {
    void onStateChange(List<Vehicle> updatedBD);
}
