package thrive.com.permissions;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    int REQUEST_CODE = 20000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.buttom);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_permission();
            }
        });
    }

    public void check_permission() {

        //ContextCompat.checkSelfPermission 检测某个权限是否已经被授予
        //PERMISSION_GRANTED  已通過 ; PERMISSION_DENIED 未通過

        int permission = ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permission |= ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CAMERA);
        permission |= ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION);

        Log.i("permission", "permission = " + permission);
        if (permission == PackageManager.PERMISSION_DENIED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            //只能抓到第一個
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA
                    , android.Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                } else {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("權限")
                            .setMessage("未賦予權限 " + permissions[i])
                            .setPositiveButton("check", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                }
            }
        }
    }
}
