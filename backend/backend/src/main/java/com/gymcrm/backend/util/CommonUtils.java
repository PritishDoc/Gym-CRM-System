package com.gymcrm.backend.util;

import java.util.Random;

public class CommonUtils {

    private static final Random random = new Random();

    public static String generateOtp() {
        return String.format("%06d", random.nextInt(999999));
    }
}