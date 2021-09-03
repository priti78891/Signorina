package com.e.signorinasign;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductActivity extends AppCompatActivity {
    private String pid;
   private ImageView p_image,p_remove,p_add;
   private TextView p_name,p_description,p_price,quantity;
    int TotalQuantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        pid = getIntent().getStringExtra("pid");

        p_image = (ImageView)findViewById(R.id.p_image);
        p_remove = (ImageView)findViewById(R.id.p_remove);
        p_add = (ImageView)findViewById(R.id.p_add);
        p_name = (TextView)findViewById(R.id.p_name);
        p_description=(TextView)findViewById(R.id.p_description);
        p_price=(TextView)findViewById(R.id.p_price);
        quantity = (TextView)findViewById(R.id.quqntity);

        p_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TotalQuantity<10)
                {
                    TotalQuantity++;
                    quantity.setText(String.valueOf(TotalQuantity));
                }
            }
        });

        p_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TotalQuantity>0)
                {
                    TotalQuantity--;
                    quantity.setText(String.valueOf(TotalQuantity));
                }
            }
        });

    }
}