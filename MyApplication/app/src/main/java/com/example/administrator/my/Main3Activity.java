package com.example.administrator.my;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class Main3Activity extends AppCompatActivity {
       ImageView imageView;
       Bitmap bit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        imageView = (ImageView) findViewById(R.id.imageView2);
         bit = (Bitmap) getIntent().getExtras().get("Image");
        imageView.setImageBitmap(bit);
    }
}
