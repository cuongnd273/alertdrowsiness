package university.project.cuong.alertdrowsiness.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import university.project.cuong.alertdrowsiness.R;
import university.project.cuong.alertdrowsiness.adapter.HistoryAdapter;
import university.project.cuong.alertdrowsiness.contants.APIConstants;
import university.project.cuong.alertdrowsiness.dao.HistoryDao;
import university.project.cuong.alertdrowsiness.model.History;


public class HistoryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    ArrayList<History> arrHistories;
    ListView listHistory;
    ImageView choosestarttime, chooseesendtime, ivfind;
    TextView starttime, endtime, txtXe;
    DatePickerDialog datePickerDialog;
    Date startDate;
    Date endDate;
    HistoryAdapter historyAdapter;
    HistoryDao historyDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getControls();
        getHistoryFromSqlite();
    }

    void getControls() {
        listHistory = findViewById(R.id.list);
        //histories=new ArrayList<>();
        //adapter=new HistoryAdapter(this,histories);
        listHistory.setOnItemClickListener(this);
        arrHistories = new ArrayList<>();
        historyAdapter = new HistoryAdapter(this, R.layout.item_history, arrHistories);
        listHistory.setAdapter(historyAdapter);
        Spinner mySpinner = (Spinner) findViewById(R.id.dropdown_biensoxe);

        ArrayAdapter<String> myAdaptor = new ArrayAdapter<String>(HistoryActivity.this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.names));
        myAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdaptor);

        //Choose time

        choosestarttime = (ImageView) findViewById(R.id.choosestarttime);
        chooseesendtime = (ImageView) findViewById(R.id.chooseendtime);
        ivfind = (ImageView) findViewById(R.id.ivfind);
        //txtXe=(TextView) findViewById(R.id.txtXe);
        starttime = (TextView) findViewById(R.id.starttime);
        endtime = (TextView) findViewById(R.id.endtime);


        // btnStartDate=(Button)findViewById(R.id.btnStartDate);
        //btnEndDate=(Button)findViewById(R.id.btnEndDate);

        choosestarttime.setOnClickListener(this);
        chooseesendtime.setOnClickListener(this);
        ivfind.setOnClickListener(this);

        historyDao = new HistoryDao(this);
        historyDao.open();
    }

    private Date stringToDate(String aDate, String aFormat) {

        if (aDate == null) return null;
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat(aFormat);
        Date stringDate = simpledateformat.parse(aDate, pos);
        return stringDate;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choosestarttime:

                Calendar calendar = Calendar.getInstance();
                int _year = calendar.get(Calendar.YEAR);
                int _month = calendar.get(Calendar.MONTH);
                int _day = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(HistoryActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        starttime.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
                    }
                }, _year, _month, _day);
                //startDate=stringToDate(starttime.getText().toString(),"yyyy/MM/dd");
                datePickerDialog.show();
                break;
            case R.id.chooseendtime:

                Calendar calendar1 = Calendar.getInstance();
                int _year1 = calendar1.get(Calendar.YEAR);
                int _month1 = calendar1.get(Calendar.MONTH);
                int _day1 = calendar1.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(HistoryActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        endtime.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
                    }
                }, _year1, _month1, _day1);
                // endDate=stringToDate(endtime.getText().toString(),"yyyy/MM/dd");
                datePickerDialog.show();
                break;

            case R.id.ivfind:
                getInfomationHistory();
                break;
        }
    }

    private void getInfomationHistory() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String response = null;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConstants.URL_HISTORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    //save shared preference
                    try {
                        JSONArray arr = new JSONArray(response);
                        for (int i = 0; i < arr.length(); i++) {
                            try {
                                JSONObject object = arr.getJSONObject(i);
                                System.out.println("ObjectValue" + i + "" + object.toString());
                                History history = new History();
                                history.setDuration(object.getInt("duration"));
                                history.setLatlocation(object.getLong("latlocation"));
                                history.setLonglocation(object.getLong("longlocation"));
                                history.setTime(object.getLong("time"));
                                arrHistories.add(history);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        historyAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getBaseContext(), "Error System", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HistoryActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //String name= session.getInformation("username");
                params.put("startDate", starttime.getText().toString());
                params.put("endDate", endtime.getText().toString());
                params.put("licenseplate", "18D128316");

                return params;
            }
        };
        requestQueue.add(stringRequest);

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(HistoryActivity.this, MapsActivity.class);
        intent.putExtra("lat",arrHistories.get(i).getLatlocation());
        intent.putExtra("lng",arrHistories.get(i).getLonglocation());
        startActivity(intent);
    }

    void getHistoryFromSqlite() {
        arrHistories=historyDao.getHistory();
        historyAdapter.refresh(arrHistories);
    }
}
