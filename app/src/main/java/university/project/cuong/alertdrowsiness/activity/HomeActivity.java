package university.project.cuong.alertdrowsiness.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
    private
    static final String PERMISSION = Manifest.permission.CAMERA;
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

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 101 && permissions[0].equals(PERMISSION)) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(HomeActivity.this, DetectDrowsinessActivity.class);
                startActivity(intent);
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

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.card_detectiondrownsiness:
                boolean hasCamera = isPermissionGranted(PERMISSION);
                if (hasCamera) {
                    intent = new Intent(HomeActivity.this, DetectDrowsinessActivity.class);
                    startActivity(intent);
                } else {
                    ActivityCompat.requestPermissions(HomeActivity.this, new String[]{PERMISSION}, 101);
                }
                break;
            case R.id.card_profile:
                intent = new Intent(HomeActivity.this, UpdatVersionActivity.class);
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
