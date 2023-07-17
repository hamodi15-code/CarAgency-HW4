package utils;

import java.io.Serializable;
import java.time.LocalDateTime;

public interface Capture extends Serializable {
    LocalDateTime getTimestamp();

}
