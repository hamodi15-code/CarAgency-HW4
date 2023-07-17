package dto;

public class NoSuchInstructorsException extends RuntimeException{
    public NoSuchInstructorsException() {
        super("no such instructors now");
    }
}
