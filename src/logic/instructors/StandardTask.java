package logic.instructors;

public class StandardTask extends Task {

    @Override
    public long calculateTimeForDistance(int distance) {
        return distance * 1000L;
    }
}
