package com.n26.n26.utils;

import java.time.Instant;

public class Utils {
    public static Long getDayInPastOrFuture() {
        return Instant.now().toEpochMilli();
    }
}
