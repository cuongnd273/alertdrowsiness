package university.project.cuong.alertdrowsiness.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import university.project.cuong.alertdrowsiness.R;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgdetect;
    private ImageView imgcndl;
    private ImageView imglichsu;
    private ImageView imgnoinghi;
    static final String PERMISSION = Manifest.permission.CAMERA;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getControls();
//        ImageView imageView = (ImageView) findViewById(R.id.bg);
//        Glide.with(this).asGif().load(R.raw.bg_animation).into(imageView);
    }

    public void getControls() {
        imgdetect = (ImageView) findViewById(R.id.imgoto);
        imgdetect.setOnClickListener(this);
        imgcndl = (ImageView) findViewById(R.id.imgcsdl);
        imgcndl.setOnClickListener(this);
        imglichsu = (ImageView) findViewById(R.id.imglichsu);
        imglichsu.setOnClickListener(this);
        imgnoinghi = (ImageView) findViewById(R.id.imgnoinghi);
        imgnoinghi.setOnClickListener(this);


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
            case R.id.imgoto:
                boolean hasCamera = isPermissionGranted(PERMISSION);
                if (hasCamera) {
                    intent = new Intent(HomeActivity.this, DetectDrowsinessActivity.class);
                    startActivity(intent);
                } else {
                    ActivityCompat.requestPermissions(HomeActivity.this, new String[]{PERMISSION}, 101);
                }
                break;
            case R.id.imgcsdl:
                intent = new Intent(HomeActivity.this, UpdatVersionActivity.class);
                startActivity(intent);
                break;
            case R.id.imglichsu:
                intent = new Intent(HomeActivity.this, HistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.imgnoinghi:
                intent = new Intent(HomeActivity.this, FindCoffeeActivity.class);
                startActivity(intent);
                break;
        }
    }
}
