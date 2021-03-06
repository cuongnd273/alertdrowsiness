package university.project.cuong.alertdrowsiness.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import university.project.cuong.alertdrowsiness.R;
import university.project.cuong.alertdrowsiness.contants.APIConstants;
import university.project.cuong.alertdrowsiness.dao.HistoryDao;
import university.project.cuong.alertdrowsiness.dao.SessionManager;
import university.project.cuong.alertdrowsiness.model.History;
import university.project.cuong.alertdrowsiness.model.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button Login;
    private EditText UserName;
    private EditText Password;
    private int counter = 3;
    private TextView register;
    private ProgressDialog pDialog;
    // private static final String TAG = "MainActivity";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    private static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;
    private static final String PERMISSION_ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String PERMISSION_ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;


    @Override
    protected void onStart() {
        super.onStart();
        boolean hasCamera = isPermissionGranted(PERMISSION_CAMERA);
        boolean hasAccessFine = isPermissionGranted(PERMISSION_ACCESS_FINE_LOCATION);
        boolean hasAccessCoarse = isPermissionGranted(PERMISSION_ACCESS_COARSE_LOCATION);
        if (!hasCamera || !hasAccessFine || !hasAccessCoarse){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{PERMISSION_CAMERA,PERMISSION_ACCESS_FINE_LOCATION,PERMISSION_ACCESS_COARSE_LOCATION}, 101);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControl();
        addEvent();

        //Login.setOnClickListener(this);

        //textView.setOnClickListener(this);
    }

    private void addControl() {
        UserName = (EditText) findViewById(R.id.etUsername);
        Password = (EditText) findViewById(R.id.etPassword);
        Login = (Button) findViewById(R.id.btnLogin);
        register = (TextView) findViewById(R.id.tvcreateuser);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Đang đăng nhập...");
        pDialog.setCanceledOnTouchOutside(false);
    }

    private void addEvent() {
        Login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    /**
     * Method login
     *
     * @param username
     * @param password result json
     */
    public void loginAccount(final String username, final String password) {

        if (checkEditText(UserName) && checkEditText(Password)) {
            pDialog.show();
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest requestLogin = new StringRequest(Request.Method.POST, APIConstants.URL_LOGIN,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Log.d(TAG, response);
                            String message = "";
                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.getInt("success") == 1) {
                                    User account = new User();
                                    account.setUserName(jsonObject.getString("username"));
                                    account.setPassword(jsonObject.getString("password"));
                                    rememberMe(account.UserName, account.Password);
                                    message = jsonObject.getString("message");
                                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                    intent.putExtra("login", account);
                                    startActivity(intent);
                                } else {
                                    message = jsonObject.getString("message");
                                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            pDialog.dismiss();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //VolleyLog.d(TAG, "Error: " + error.getMessage());
                            pDialog.dismiss();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put(KEY_USERNAME, username);
                    params.put(KEY_PASSWORD, password);
                    return params;
                }
            };
            //RequestQueue queue = Volley.newRequestQueue(this);
            requestQueue.add(requestLogin);
        }
    }

    /**
     * Check input
     */
    private boolean checkEditText(EditText editText) {
        if (editText.getText().toString().trim().length() > 0)
            return true;
        else {
            editText.setError("Vui lòng nhập dữ liệu!");
        }
        return false;
    }

    public void rememberMe(String username, String password) {
        //save username and password in SharedPreferences
        SharedPreferences.Editor editor = getSharedPreferences(SessionManager.PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(SessionManager.PREF_USERNAME, username);
        editor.putString(SessionManager.PREF_PASSWORD, password);
        editor.apply();
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        if (v.getId() == R.id.btnLogin) {
            String username = UserName.getText().toString().trim();
            String password = Password.getText().toString().trim();
            // Call method
//            loginAccount(username, password);

            intent= new Intent(MainActivity.this,HomeActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.tvcreateuser) {
            intent = new Intent(MainActivity.this, SignInActivity.class);
            startActivity(intent);
        }
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 101 && permissions[0].equals(PERMISSION_CAMERA)) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Toast.makeText(this, "Ứng dụng không thể hoạt động nếu không được cấp quyền", Toast.LENGTH_SHORT).show();
            }
        }
    }

    boolean isPermissionGranted(String permission) {
        int result = ContextCompat.checkSelfPermission(this, permission);
        if (result == PackageManager.PERMISSION_GRANTED) return true;
        else if (result == PackageManager.PERMISSION_DENIED) return false;
        else throw new IllegalStateException("Cannot check permission " + permission);
    }
}

