package university.project.cuong.alertdrowsiness.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import university.project.cuong.alertdrowsiness.R;
import university.project.cuong.alertdrowsiness.contants.APIConstants;
import university.project.cuong.alertdrowsiness.model.User;

public class SignInActivity extends AppCompatActivity {

    private EditText etname,etpassword,etaddress,ettelephone,etsex,etidentitycard,etemail;
    private Button btnCreate;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private ProgressDialog pDialog;
    private TextView btnLogin;
    //private static final String TAG = "SignInActivity";

    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_TELEPHONE = "telephone";
    public static final String KEY_SEX = "sex";
    public static final String KEY_IDENTITYCARD = "identitycard";
    public static final String KEY_EMAIL="email";
    public static String  PREFS_NAME="mypre";
    public static String PREF_USERNAME="username";
    public static String PREF_PASSWORD="password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        addControls();
        addEvents();

    }

    private void addControls() {

        etname=(EditText)findViewById(R.id.input_name);
        etpassword=(EditText)findViewById(R.id.input_password);
        etaddress=(EditText)findViewById(R.id.input_address);
        ettelephone=(EditText)findViewById(R.id.input_telephone);
        etsex=(EditText)findViewById(R.id.input_sex);
        etidentitycard=(EditText)findViewById(R.id.input_identitycard);
        etemail=(EditText)findViewById(R.id.input_email);
        btnLogin=(TextView)findViewById(R.id.link_login);


        btnCreate=(Button)findViewById(R.id.btn_signup);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Đang đăng ký...");
        pDialog.setCanceledOnTouchOutside(false);
    }

    private void addEvents() {
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get data input
                String username = etname.getText().toString().trim();
                String password = etpassword.getText().toString().trim();
                String address = etaddress.getText().toString().trim();
                String telephone = ettelephone.getText().toString().trim();
                String sex = etsex.getText().toString().trim();
                String identitycard = etidentitycard.getText().toString().trim();
                String email = etemail.getText().toString().trim();
                //Call method register
                registerUser(username, password, address,telephone,sex,identitycard,email);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Method register
     *
     * @param username
     * @param password
     * @param telephone    result json
     */
    private void registerUser(final String username, final String password, final String address, final String telephone, final String sex, final String identitycard, final String email) {

       // if (checkEditText(etname) && checkEditText(etpassword) && checkEditText(etaddress)&& checkEditText(etidentitycard)&& checkEditText(etemail)
              // && checkEditText(ettelephone)&& checkEditText(etsex) && isValidTelephone(ettelephone.getText().toString().trim())) {
            pDialog.show();
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest registerRequest = new StringRequest(Request.Method.POST, APIConstants.URL_SIGN_IN,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("response", response);
                            String message = "";
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.getInt("success") == 1) {
                                    User account = new User();
                                    account.setUserName(jsonObject.getString("username"));
                                    account.setPassword(jsonObject.getString("password"));
                                    rememberMe(account.UserName,account.Password);
                                    message = jsonObject.getString("message");
                                    Toast.makeText(SignInActivity.this, message, Toast.LENGTH_LONG).show();
                                    //Start LoginActivity
                                    Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                } else {
                                    message = jsonObject.getString("message");
                                    Toast.makeText(SignInActivity.this, message, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException error) {
                                VolleyLog.d("", "Error: " + error.getMessage());
                            }
                            pDialog.dismiss();
                       }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d("", "Error: " + error.getMessage());
                            pDialog.dismiss();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put(KEY_USERNAME, username);
                    params.put(KEY_PASSWORD, password);
                    params.put(KEY_ADDRESS, address);
                    params.put(KEY_TELEPHONE, telephone);
                    params.put(KEY_SEX,sex);
                    params.put(KEY_IDENTITYCARD,identitycard);
                    params.put(KEY_EMAIL,email);

                    return params;
                }

            };
            //RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(registerRequest);
        //}
    }

    /**
     * Check Input
     */
    private boolean checkEditText(EditText editText) {
        if (editText.getText().toString().trim().length() > 0)
            return true;
        else {
            editText.setError("Vui lòng nhập dữ liệu!");
        }
        return false;
    }

    /**
     * Check Email
     */
    /**private boolean isValidTelephone(String target) {
     if (target.matches("[0-9]"))
     return true;
     else {
     ettelephone.setError("Số điện thoại sai định dạng!");
     }
     return false;
     }*/

    private boolean isValidTelephone(String phone) {
        boolean check = false;
        if (!Pattern.matches("[0-9]+", phone)) {
            if (phone.length() < 6 || phone.length() > 13) {
                // if(phone.length() != 10) {
                check = false;
                //txtPhone.setError("Not Valid Number");
                ettelephone.setError("Số điện thoại sai định dạng!");
            } else {
                check = true;
            }
        } else {
            check = false;
        }
        return check;
    }

    public void rememberMe(String username, String password){
        //save username and password in SharedPreferences
        getSharedPreferences(PREFS_NAME,MODE_PRIVATE)
                .edit()
                .putString(PREF_USERNAME,username)
                .putString(PREF_PASSWORD,password)
                .commit();
    }

}
