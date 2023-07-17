package logic;

import dto.NoSuchInstructorsException;
import dto.VehicleTakenException;
import logic.instructors.SlowInstructor;
import logic.instructors.StandardTask;
import logic.instructors.Task;
import vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestDriveService {
    private static TestDriveService instance;
    public static synchronized TestDriveService getInstance(){
        if(instance == null){
            instance = new TestDriveService();
        }
        return instance;
    }
    private final ExecutorService executorService;

    private List<Task> instructors = new ArrayList<>();


    private TestDriveService() {
        for (int i = 0; i < 7; i++) {
            instructors.add(new StandardTask());
        }
        executorService = Executors.newWorkStealingPool();
    }


    /**
     * @param vehicle
     * @param distance
     * @return
     * @throws VehicleTakenException
     * @throws NoSuchInstructorsException
     */
    public void goTestDrive(Vehicle vehicle, int distance){
        if(!isVehicleFree(vehicle)) throw new VehicleTakenException();
        Task instructor = findFreeInstructor();
        if(instructor == null) throw new NoSuchInstructorsException();
        instructor.prepareForDrive(vehicle, distance);
        executorService.execute(instructor);
    }

    private Task findFreeInstructor() {
        for (Task instructor : instructors) {
            if(!instructor.isBusy()){
                return instructor;
            }
        }
        return null;
    }

    private boolean isVehicleFree(Vehicle vehicle) {
        for (Task instructor : instructors) {
            if (instructor.getCurrentVehicle() != null && instructor.getCurrentVehicle().equals(vehicle)) {
                return false;
            }
        }
        return true;
    }


}
