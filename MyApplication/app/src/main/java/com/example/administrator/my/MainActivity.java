package com.example.administrator.my;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity {

    public final static String FILE_NAME = "filename";
    EditText editTextBrowser;
    Button buttonBrowser ;
    RadioButton radioButton;
    RadioButton radioButton1;
    Button getCamera;
    Bitmap thumbnail;
    int REQUEST_CAMERA = 0 , CAMERA_PHOTO_REQUEST_CODE = 1;
    Button music ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = (TextView) findViewById(R.id.tv);
        editTextBrowser = (EditText) findViewById(R.id.editText);
        buttonBrowser = (Button) findViewById(R.id.button);
        radioButton = (RadioButton) findViewById(R.id.radioButton);
        radioButton1 = (RadioButton) findViewById(R.id.radioButton2);
        getCamera = (Button)findViewById(R.id.getCamera);
        music = (Button)findViewById(R.id.music);

       music.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this , Music.class);
               startActivity(intent);
           }
       });
    getCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radioButton.isChecked()){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);

                }
                if(radioButton1.isChecked()){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
                    startActivityForResult(intent, CAMERA_PHOTO_REQUEST_CODE);

                }
            }
        });

        buttonBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW , Uri.parse("http://" + editTextBrowser.getText().toString()));
                startActivity(intent);
            }
        });
        Intent intent = getIntent();

        String fileName = intent.getStringExtra(FILE_NAME);
        if (!TextUtils.isEmpty(fileName))
            tv.setText(fileName);
    }

    public void onClickStart(View v) {
        startService(new Intent(this, MyService.class));
    }


    public void onClickStop(View v) {
        stopService(new Intent(this, MyService.class));
    }
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

        if (requestCode == REQUEST_CAMERA || requestCode == CAMERA_PHOTO_REQUEST_CODE) {
                onCaptureImageResult(data);
            }
        }
    }
    private void onCaptureImageResult(Intent data) {
        thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(MainActivity.this, Main3Activity.class);
        intent.putExtra("Image", thumbnail);
        startActivity(intent);
    }

}
