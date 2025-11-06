package org.ball.Utils;

import java.time.LocalDate;

public class DataUtil {

    public static int getCurrentYear() {
        return LocalDate.now().getYear();
    }
}
