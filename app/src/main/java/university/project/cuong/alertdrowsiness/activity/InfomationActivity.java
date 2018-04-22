package university.project.cuong.alertdrowsiness.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import university.project.cuong.alertdrowsiness.R;
import university.project.cuong.alertdrowsiness.adapter.UserAdapter;
import university.project.cuong.alertdrowsiness.contants.APIConstants;
import university.project.cuong.alertdrowsiness.dao.SessionManager;
import university.project.cuong.alertdrowsiness.model.User;


public class InfomationActivity extends AppCompatActivity {
    private TextView tvname;
    private TextView tvemail;
    private TextView tvaddress;
    private TextView tvtelephone;
    private TextView tvidentityCard;
    private TextView tvsex;
    UserAdapter userAdapter;
    SessionManager session;

    ListView lvUser;
    ArrayList<User> arrayUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_user);
        getControls();
        getInfomationUser();
    }
    public void getControls()
    {
        tvname= (TextView)findViewById(R.id.tvname2);
        tvemail=(TextView) findViewById(R.id.tvemail2);
        tvaddress=(TextView) findViewById(R.id.tvaddress2);
        tvtelephone=(TextView)findViewById(R.id.tvtelephone2);
        tvidentityCard=(TextView)findViewById(R.id.tvidentityCard2);
        tvsex=(TextView)findViewById(R.id.tvsex2);
        lvUser = (ListView) findViewById(R.id.lvUser);
        arrayUsers = new ArrayList<>();
        userAdapter = new UserAdapter(this, R.layout.activity_updat_version, arrayUsers);
        lvUser.setAdapter(userAdapter);
        session=new SessionManager(this);
    }
    private void getInfomationUser(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String response = null;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConstants.URL_INFO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response != null){
                    //save shared preference
                    try {
                        JSONArray arr = new JSONArray(response);
                        for (int i = 0; i < arr.length(); i++) {
                            try {
                                JSONObject object = arr.getJSONObject(i);
                                System.out.println("ObjectValue" + i + "" + object.toString());
                                User user = new User();
                                user.setUserName(object.getString("username"));
                                user.setAddress(object.getString("address"));
                                user.setEmail(object.getString("email"));
                                user.setIdentityCard(object.getString("identitycard"));
                                user.setSex(object.getString("sex"));
                                user.setTelephone(object.getString("telephone"));

                                arrayUsers.add(user);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        userAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else {
                    Toast.makeText(getBaseContext(), "Error System", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(InfomationActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String name= session.getInformation("username");
                params.put("username",name);
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }


}
