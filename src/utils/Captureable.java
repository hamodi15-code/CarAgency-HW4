package utils;

public interface Captureable {
    Capture save();
    void restore(Capture capture);
}
