package university.project.cuong.alertdrowsiness.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

import university.project.cuong.alertdrowsiness.R;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView imgdetect;
    private CardView imgupdate;
    private CardView imghistrory;
    private CardView imgplaces;
    private CardView imgapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        getControls();
//        ImageView imageView = (ImageView) findViewById(R.id.bg);
//        Glide.with(this).asGif().load(R.raw.bg_animation).into(imageView);
    }

    public void getControls() {
        imgdetect = (CardView) findViewById(R.id.card_detectiondrownsiness);
        imgdetect.setOnClickListener(this);
        imgupdate = (CardView) findViewById(R.id.card_profile);
        imgupdate.setOnClickListener(this);
        imghistrory = (CardView) findViewById(R.id.card_history);
        imghistrory.setOnClickListener(this);
        imgplaces = (CardView) findViewById(R.id.card_findarea);
        imgplaces.setOnClickListener(this);
        imgapp = (CardView) findViewById(R.id.card_app);
        imgapp.setOnClickListener(this);
        //imgiconhome=(ImageView) findViewById(R.id.img_iconhome);
        //imgiconhome.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.card_detectiondrownsiness:
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(this);
                builder.setTitle("Phát hiện ngủ gật")
                        .setMessage("Sử dụng phương pháp")
                        .setPositiveButton("Phân lớp", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent it = new Intent(HomeActivity.this, DetectDrowsinessActivity.class);
                                startActivity(it);
                            }
                        })
                        .setNegativeButton("Google Vision", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent it = new Intent(HomeActivity.this, DetectDrowsinessMobileVisionActivity.class);
                                startActivity(it);
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

                break;
            case R.id.card_profile:
                intent = new Intent(HomeActivity.this, InfomationActivity.class);
                startActivity(intent);
                break;
            case R.id.card_history:
                intent = new Intent(HomeActivity.this, HistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.card_findarea:
                intent = new Intent(HomeActivity.this, FindCoffeeActivity.class);
                startActivity(intent);
                break;
            case R.id.card_app:
                intent = new Intent(HomeActivity.this, IntroduceAppActivity.class);
                startActivity(intent);
                break;
        }
    }
}
