package university.project.cuong.alertdrowsiness.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import university.project.cuong.alertdrowsiness.R;


public class FindCoffeeActivity extends AppCompatActivity {
    public static final String URL_API="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&type=restaurant&key";
    Spinner spinner;
    ArrayAdapter adapter;
    String[] radiusSpinner={"500 m","1 km","5 km"};
    int[] radius={500,1000,5000};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_coffee);
        getControls();
    }
    void getControls(){
        spinner=findViewById(R.id.option_radius);
        adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,radiusSpinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(FindCoffeeActivity.this, "Bán kính "+radiusSpinner[i], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    void getPlaces(){
        RequestQueue requestQueue= Volley.newRequestQueue(FindCoffeeActivity.this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("key","");
                return params;
            }
        };
    }
}
