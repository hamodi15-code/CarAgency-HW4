package logic.instructors;

public class SlowInstructor extends Task{
    @Override
    public long calculateTimeForDistance(int distance) {
        return distance * 10000L;
    }
}
