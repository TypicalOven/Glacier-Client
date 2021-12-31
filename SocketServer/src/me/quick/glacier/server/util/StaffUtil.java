package me.quick.glacier.server.util;

import me.quick.glacier.server.client.GlacierClient;

public class StaffUtil {

    public static boolean isStaff(GlacierClient client) {
        int count = 0;
        if(client.getUsername().equals("quick") || client.getUsername().equals("TheGloriousDuck")) count++;
        //if(Integer.parseInt(client.getUid()) >= 2) count++;

        if(count >= 2) {
            return true;
        } else return false;
    }

}
