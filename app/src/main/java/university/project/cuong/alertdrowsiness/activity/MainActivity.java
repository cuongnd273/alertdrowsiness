package university.project.cuong.alertdrowsiness.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private Button Login;
    private EditText UserName;
    private EditText Password;
    private int counter=3;

    String urlLogin = "http://192.168.0.100/server/login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //UserName=(EditText)findViewById(R.id.etUsername);
        //Password=(EditText)findViewById(R.id.etPassword);
        Login = (Button) findViewById(R.id.btnLogin);

        AnhXa();

        Login.setOnClickListener(this);

    }

        private void Login(String url) {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.trim().equals("success")) {
                                //Toast.makeText(MainActivity.this, "Login success!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            } else {
                                Toast.makeText(MainActivity.this, "Login false!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this, "System error.", Toast.LENGTH_SHORT).show();
                            Log.d("AAA", "Lỗi\n" + error.toString());
                        }
                    }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", UserName.getText().toString().trim());
                    params.put("password", Password.getText().toString().trim());

                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }

        private void AnhXa() {
            Login = (Button) findViewById(R.id.btnLogin);
//        btnSignin = (Button) findViewById(R.id.btnSignin);

            UserName = (EditText) findViewById(R.id.etUsername);
            Password = (EditText) findViewById(R.id.etPassword);
        }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnLogin){
            String username = UserName.getText().toString().trim();
            String password = Password.getText().toString().trim();

//            if (username.isEmpty() || password.isEmpty()) {
//                Toast.makeText(MainActivity.this, "Please enter your info.", Toast.LENGTH_SHORT).show();
//            } else {
//                Login(urlLogin);
//            }
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
            finish();
        }
    }
}

