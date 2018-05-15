package university.project.cuong.alertdrowsiness.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import university.project.cuong.alertdrowsiness.database.DatabaseHelper;
import university.project.cuong.alertdrowsiness.model.History;

public class HistoryDao {
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;

    public HistoryDao(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public void open() {
        db = databaseHelper.getWritableDatabase();
    }

    public long insertHistory(History history) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.HISTORY_TIME,System.currentTimeMillis());
        contentValues.put(DatabaseHelper.HISTORY_LAT, history.getLatlocation());
        contentValues.put(DatabaseHelper.HISTORY_LNG, history.getLonglocation());
        return db.insert(DatabaseHelper.TABLE_HISTORY, null, contentValues);
    }

    public ArrayList<History> getHistory() {
        ArrayList<History> histories = new ArrayList<>();
        Cursor cursor = db.query(DatabaseHelper.TABLE_HISTORY, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            History history = new History();
            history.setTime(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.HISTORY_TIME)));
            history.setLatlocation(cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.HISTORY_LAT)));
            history.setLonglocation(cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.HISTORY_LNG)));
            history.setDuration(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.HISTORY_DURATION)));
            histories.add(history);
        }
        return histories;
    }

}
