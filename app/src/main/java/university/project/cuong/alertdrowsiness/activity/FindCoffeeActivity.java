package university.project.cuong.alertdrowsiness.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import university.project.cuong.alertdrowsiness.R;
import university.project.cuong.alertdrowsiness.model.Place;
import university.project.cuong.alertdrowsiness.utils.GPSTracker;


public class FindCoffeeActivity extends FragmentActivity implements OnMapReadyCallback {
    public static final String API_KEY="AIzaSyAs41pODUKe1yqSA3BNgHDMIAdw1gC3U2g";
    public double latLocationCurrent=21.0463644;
    public double longLocationCurrent=105.7793742;
    public int pos=0;
    public static final String URL_API="https://maps.googleapis.com/maps/api/place/nearbysearch/json";
    ArrayList<Place> places;
    Spinner spinner;
    ArrayAdapter adapter;
    String[] radiusSpinner={"500 m","1 km","5 km"};
    int[] radius={500,1000,5000};
    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_coffee);
        getControls();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getPlaces();
        GPSTracker gps = new GPSTracker(this);
        double latitude = gps.getLatitude();
        double longitude = gps.getLongitude();
        Log.i("Vi tri hien tai: ",String.valueOf(latitude)+"-"+String.valueOf(longitude));
    }
    void getControls(){
        spinner=findViewById(R.id.option_radius);
        adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,radiusSpinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pos=i;
                getPlaces();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        places=new ArrayList<>();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng my_home = new LatLng(21.0427074, 105.7798578);
        mMap.addMarker(new MarkerOptions().position(my_home).title("My Home"));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                my_home).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);

    }
    void getPlaces(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String URL="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+String.valueOf(latLocationCurrent)+","+String.valueOf(longLocationCurrent)+"&radius="+radius[pos]+"&type=restaurant&key="+API_KEY;
        RequestQueue requestQueue= Volley.newRequestQueue(FindCoffeeActivity.this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    mMap.clear();
                    places.clear();
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray results=jsonObject.getJSONArray("results");
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(
                            new LatLng(latLocationCurrent,longLocationCurrent)).zoom(14).build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    for(int i=0;i<results.length();i++){
                        Place place=new Place();
                        place.setName(results.getJSONObject(i).getString("name"));
                        place.setLat(results.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lat"));
                        place.setLng(results.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lng"));
                        place.setIcon(results.getJSONObject(i).getString("icon"));
                        LatLng placeLocation = new LatLng(place.getLat(), place.getLng());
                        mMap.addMarker(new MarkerOptions().position(placeLocation).title(place.getName()));
                        places.add(place);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                finally {
                    progressDialog.dismiss();
                }
                Log.i("ResponsePlace", "Success");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("ResponsePlace", "Error");
                progressDialog.dismiss();
            }
        });
        requestQueue.add(stringRequest);
    }
}
