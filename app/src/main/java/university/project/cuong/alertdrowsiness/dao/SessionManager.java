package university.project.cuong.alertdrowsiness.dao;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
   // private static final String PREF_NAME = "AndroidHivePref";

    // All Shared Preferences Keys
    //private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    //public static final String KEY_NAME = "username";

    // Email address (make variable public to access from outside)
    //public static final String KEY_EMAIL = "password";

    public static final String PREFS_NAME = "mypre";
    public static final String PREF_USERNAME = "username";
    public static final String PREF_PASSWORD = "password";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREFS_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    public String getInformation(String key1){
        return pref.getString(key1,"");
    }
}
