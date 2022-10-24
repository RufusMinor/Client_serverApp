package com.template;

import com.template.LoadingActivity;
import java.security.SecureRandom;

public class Uuid extends LoadingActivity {

         private static volatile SecureRandom numberRandom = null;
         private static final long MSB = 0x8000000000000000L;



    public static String result() {
        SecureRandom ng = numberRandom;
        if (ng == null) {
            numberRandom = ng = new SecureRandom();
        }

        return Long.toHexString(MSB | ng.nextLong()) + Long.toHexString(MSB | ng.nextLong());
    }
}