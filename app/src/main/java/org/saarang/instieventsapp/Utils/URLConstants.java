package org.saarang.instieventsapp.Utils;

/**
 * Created by Seetharaman on 16-08-2015.
 */
public class URLConstants {

    public static String SERVER = "http://litsoc.saarang.org/";

    public static String URL_EVENT_FETCH = SERVER+ "api/events/";
    public static String URL_SUBSCRIBE = SERVER + "api/clubs";
    public static String URL_LOGIN = SERVER+ "auth/local/mobile";
    public static String URL_SCORECARD_FETCH= SERVER+ "api/scoreboards/";
    public static String URL_SUBCLUBS=SERVER + "api/clubs/subscribeall";
    public static String URL_REGISTER_DEVICE = SERVER + "api/users/gcmRegister";
    public static String URL_SUBSCRIBE_CLUB=SERVER+"api/clubs/subscribe/:";
    public static String URL_REFRESH = SERVER + "api/users/refresh/";
}
