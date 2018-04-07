package university.project.cuong.alertdrowsiness.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;


import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import university.project.cuong.alertdrowsiness.R;
import university.project.cuong.alertdrowsiness.adapter.HistoryAdapter;
import university.project.cuong.alertdrowsiness.model.History;


public class HistoryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ArrayList<History> histories;
    HistoryAdapter adapter;
    ListView listHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getControls();
        histories.add(new History(new Timestamp(System.currentTimeMillis()),2,34,45));
        histories.add(new History(new Timestamp(System.currentTimeMillis()),2,34,45));
        histories.add(new History(new Timestamp(System.currentTimeMillis()),2,34,45));
        histories.add(new History(new Timestamp(System.currentTimeMillis()),2,34,45));
        histories.add(new History(new Timestamp(System.currentTimeMillis()),2,34,45));
        histories.add(new History(new Timestamp(System.currentTimeMillis()),2,34,45));
        adapter.refresh();
    }
    void getControls(){
        listHistory=findViewById(R.id.list);
        histories=new ArrayList<>();
        adapter=new HistoryAdapter(this,histories);
        listHistory.setOnItemClickListener(this);
        listHistory.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent=new Intent(HistoryActivity.this,MapsActivity.class);
        startActivity(intent);
    }
}
