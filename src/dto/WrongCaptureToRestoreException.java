package dto;

public class WrongCaptureToRestoreException extends RuntimeException{
    public WrongCaptureToRestoreException(Class<?> from, Class<?> to) {
        super("you can not restore the instance of %s using capture %s".formatted(to.toString(), from.toString()));
    }
}
