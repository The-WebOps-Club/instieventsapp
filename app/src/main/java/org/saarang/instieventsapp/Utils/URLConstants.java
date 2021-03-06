package org.saarang.instieventsapp.Utils;

/**
 * Created by Seetharaman on 16-08-2015.
 */
public class URLConstants {

    public static String SERVER = "http://litsoc.saarang.org/";

    // Test Server
//    public static String SERVER = "http://litsoctest.saarang.org/";
//    public static String SERVER = "http://192.168.0.5:9000/";

    public static String URL_EVENT_FETCH = SERVER+ "api/events/";
    public static String URL_SUBSCRIBE = SERVER + "api/clubs";
    public static String URL_LOGIN = SERVER+ "auth/local/mobile";
    public static String URL_INSTI_LOGIN = "http://10.24.0.224/mobapi/ldap/login.php";
    public static String URL_SCORECARD_FETCH= SERVER+ "api/scoreboards/";
    public static String URL_SUBCLUBS=SERVER + "api/clubs/subscribeall";
    public static String URL_REGISTER_DEVICE = SERVER + "api/users/gcmRegister";
    public static String URL_SUBSCRIBE_CLUB=SERVER+"api/clubs/subscribe/";
    public static String URL_UNSUBSCRIBE_CLUB=SERVER+"api/clubs/unsubscribe/";
    public static String URL_REFRESH = SERVER+ "api/users/refresh";

    public static String URL_CLUB_LOGO = "http://saarang.org/litsoc/images/club_logos/";
}
