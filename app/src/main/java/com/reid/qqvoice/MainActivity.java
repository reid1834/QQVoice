package com.reid.qqvoice;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static final String TAG = MainActivity.class.getSimpleName();
    private  PermissionManager mPermissionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mPermissionManager = new PermissionManager(this);

        if (!mPermissionManager.checkCameraLaunchPermissions()) {
            if (!mPermissionManager.requestCameraAllPermissions()) {
                return;
            }
        }
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream inputStream =  getResources().openRawResource(R.raw.voice);
                    File file = new File(Environment.getExternalStorageDirectory().getPath(),"voice.wav");
                    if(!file.exists()){
                        file.createNewFile();
                    }
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    byte[] buffer = new byte[10];
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    int len = 0;
                    while ((len=inputStream.read(buffer))!=-1){
                        outputStream.write(buffer,0,len);
                    }
                    byte[] bs = outputStream.toByteArray();
                    fileOutputStream.write(bs);
                    outputStream.close();
                    inputStream.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("getPath",e.getMessage());
                }
            }
        }).start();

    }

    public void startChange(View view) {
        String path = Environment.getExternalStorageDirectory().getPath()+File.separator+"voice.wav";
        Log.i("getPath",path);
        switch (view.getId()) {
            // 普通
            case R.id.btn_normal:
                VoiceTools.changeVoice(path, 0);
                break;
            // 萝莉
            case R.id.btn_luoli:
                VoiceTools.changeVoice(path, 1);
                break;// 萝莉
            // 大叔
            case R.id.btn_dashu:
                VoiceTools.changeVoice(path, 2);
                break;
            // 惊悚
            case R.id.btn_jingsong:
                VoiceTools.changeVoice(path, 3);
                break;
            // 搞怪
            case R.id.btn_gaoguai:
                VoiceTools.changeVoice(path, 4);
                break;
            // 空灵
            case R.id.btn_kongling:
                VoiceTools.changeVoice(path, 5);
                break;

        }

    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult(), grantResults = " + grantResults.length);
        if (grantResults.length <= 0) {
            return;
        }
        if (mPermissionManager.getCameraLaunchPermissionRequestCode()
                == requestCode) {
            // If permission all on, activity will resume.
            if (!mPermissionManager.isCameraLaunchPermissionsResultReady(
                    permissions, grantResults)) {
                // more than one critical permission was denied
                // activity finish, exit and destroy

                Toast.makeText(getApplicationContext(), getResources().getString(R.string.denied_required_permission), Toast.LENGTH_SHORT)
                        .show();

                finish();
            }
        }
    }

}
