package com.example.dangkymonhoc.GiaoDien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.dangkymonhoc.R;

public class EditPassActivity extends AppCompatActivity {
    String maSV;
    ImageButton img_Back;
    Button btn_EditPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pass);

        btn_EditPass = findViewById(R.id.btnEditPass);
        img_Back = findViewById(R.id.btnBackEditPass);


        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditPassActivity.this, SettingActivity.class);
                i.putExtra("maSV",maSV);
                startActivity(i);

            }
        });
        Intent intent = getIntent();
        maSV = intent.getStringExtra("maSV");
        Log.d("maSvUser",maSV);
    }
}