package org.saarang.instieventsapp.Utils;

/**
 * Created by kevin selva prasanna on 24-Aug-15.
 */
public class AppConstants {
    public static String GCM_SENDER_ID = "544871083090";

    public static String getResultMessage(int position, String hostel, String event){

        if (position == 1){
            return "Another brick in the wall for you. " + hostel + ", winners at " + event + "!";
        } else if (position == 2) {
            return "Twist and shout. Jump around. " + hostel + "  finished second  at " + event + "";
        } else if (position == 3) {
            return "Twist and shout. Jump around. " + hostel + "  finished third  at " + event + "";
        } else if (position ==0) {
            return "It's been a hard day's night for " + hostel + " at " + event + ". But let it be, and carry on.";
        } else {
            return "It's a long way to the top if you wanna rock and roll. But you're almost there. "
                    + hostel + " got "+ position + "th in " + event + "!";
        }
    }
}
