package university.project.cuong.alertdrowsiness.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by cuong on 4/9/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="alertdrowsiness";
    public static final int DATABASE_VERSION=1;
    public static final String TABLE_HISTORY="history";
    public static final String HISTORY_ID="id";
    public static final String HISTORY_TIME="time";
    public static final String HISTORY_LAT="lat";
    public static final String HISTORY_LNG="lng";
    public static final String HISTORY_DURATION="duration";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql="create table history ("+HISTORY_ID+" integer primary key AUTOINCREMENT,"+HISTORY_TIME+" real,"+HISTORY_LAT+" real,"+HISTORY_LNG+" real,"+HISTORY_DURATION+" integer)";
        sqLiteDatabase.execSQL(sql);
        Log.i("SQLITE", sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_HISTORY);
        onCreate(sqLiteDatabase);
    }
}
