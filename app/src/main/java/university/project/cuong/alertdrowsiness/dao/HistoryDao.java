package university.project.cuong.alertdrowsiness.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import university.project.cuong.alertdrowsiness.database.DatabaseHelper;
import university.project.cuong.alertdrowsiness.model.History;

/**
 * Created by cuong on 4/9/2018.
 */

public class HistoryDao {
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;

    public HistoryDao(Context context) {
        databaseHelper=new DatabaseHelper(context);
        db=databaseHelper.getWritableDatabase();
    }
    long createAHistory(History history){
        ContentValues contentValues=new ContentValues();
        contentValues.put(DatabaseHelper.HISTORY_TIME, String.valueOf(history.getTime()));
        contentValues.put(DatabaseHelper.HISTORY_LAT,history.getLatLocation());
        contentValues.put(DatabaseHelper.HISTORY_LNG,history.getLongLocation());
        contentValues.put(DatabaseHelper.HISTORY_DURATION,history.getDuration());
        return db.insert(DatabaseHelper.TABLE_HISTORY,null,contentValues);
    }
    ArrayList<History> getHistory(int page){
        ArrayList<History> histories=new ArrayList<>();
        String sql="select * from "+DA;
        Cursor cursor=db.rawQuery(sql,null);
    }
}
