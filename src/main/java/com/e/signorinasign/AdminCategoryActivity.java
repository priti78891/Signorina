package com.e.signorinasign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity {

    private ImageView tshirts,shirts,girlsDresses,sweater;
    private ImageView glasses,purse,shoes,sandal;
    private ImageView headphone,laptop,mobile,camera;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        tshirts = (ImageView) findViewById(R.id.tshirts);
        shirts = (ImageView) findViewById(R.id.shirts);
        girlsDresses = (ImageView) findViewById(R.id.female);
        sweater = (ImageView) findViewById(R.id.sweater);
        glasses = (ImageView) findViewById(R.id.glasses);
        purse = (ImageView) findViewById(R.id.purse);
        shoes = (ImageView) findViewById(R.id.shoes);
        sandal = (ImageView) findViewById(R.id.sandals);
        headphone = (ImageView) findViewById(R.id.headphone);
        laptop = (ImageView) findViewById(R.id.laptop);
        mobile = (ImageView) findViewById(R.id.mobile);
        camera = (ImageView) findViewById(R.id.camera);


        tshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminActivity.class);
                intent.putExtra("category","tshirts");
                startActivity(intent);
            }
        });
        shirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminActivity.class);
                intent.putExtra("category","shirts");
                startActivity(intent);
            }
        });
        girlsDresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminActivity.class);
                intent.putExtra("category","girlsDresses");
                startActivity(intent);
            }
        });
        sweater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminActivity.class);
                intent.putExtra("category","sweater");
                startActivity(intent);
            }
        });
        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminActivity.class);
                intent.putExtra("category","glasses");
                startActivity(intent);
            }
        });
        purse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminActivity.class);
                intent.putExtra("category","purse");
                startActivity(intent);
            }
        });
        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminActivity.class);
                intent.putExtra("category","shoes");
                startActivity(intent);
            }
        });
        sandal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminActivity.class);
                intent.putExtra("category","sandals");
                startActivity(intent);
            }
        });
        headphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminActivity.class);
                intent.putExtra("category","headphones");
                startActivity(intent);
            }
        });
        laptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminActivity.class);
                intent.putExtra("category","laptop");
                startActivity(intent);
            }
        });
        mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminActivity.class);
                intent.putExtra("category","mobile");
                startActivity(intent);
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminActivity.class);
                intent.putExtra("category","camera");
                startActivity(intent);
            }
        });


    }
}