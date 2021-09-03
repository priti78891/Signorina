package com.e.signorinasign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.e.signorinasign.Model.Users;
import com.e.signorinasign.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class LognActivity extends AppCompatActivity {
    private Button logout;
    private Button login;
    private EditText InputPhoneNumber, InputPassword;
    private ProgressDialog loadingBar;
    private CheckBox chkBox;
    private TextView anAdmin,notAnAdmin;
    private String parentDb = "users";

    Context context;
    Resources resource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logn);



        logout = (Button) findViewById(R.id.logout);
        login = (Button) findViewById(R.id.login_btn);
        InputPassword = (EditText) findViewById(R.id.password);
        InputPhoneNumber = (EditText) findViewById(R.id.phoneNumber);
        anAdmin = (TextView) findViewById(R.id.an_admin);
        notAnAdmin = (TextView) findViewById(R.id.not_an_admin);
        loadingBar = new ProgressDialog(this);
        chkBox = (CheckBox) findViewById(R.id.remember_me);
        Paper.init(this);
        if(Users.flag==1) {
            context = LocaleHelper.setLocale(LognActivity.this, "hi");
            resource = context.getResources();

            InputPassword.setText(resource.getString(R.string.password));
            logout.setText(resource.getString(R.string.logout));
            login.setText(resource.getString(R.string.login));
            InputPhoneNumber.setText(resource.getString(R.string.phone_number));
            anAdmin.setText(resource.getString(R.string.an_admin));
            notAnAdmin.setText(resource.getString(R.string.not_an_admin));


        }

        if(Users.flag==0)
        {
            context = LocaleHelper.setLocale(LognActivity.this, "en");
            resource = context.getResources();

            InputPassword.setText(resource.getString(R.string.password));
            logout.setText(resource.getString(R.string.logout));
            login.setText(resource.getString(R.string.login));
            InputPhoneNumber.setText(resource.getString(R.string.phone_number));
            anAdmin.setText(resource.getString(R.string.an_admin));
            notAnAdmin.setText(resource.getString(R.string.not_an_admin));
        }
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().destroy();
                Intent intent = new Intent(LognActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
        anAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login.setText("Login Admin");
                anAdmin.setVisibility(View.INVISIBLE);
                notAnAdmin.setVisibility(View.VISIBLE);
                parentDb = "Admins";

            }
        });

        notAnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login.setText("Login");
                anAdmin.setVisibility(View.VISIBLE);
                notAnAdmin.setVisibility(View.INVISIBLE);
                parentDb = "users";
            }
        });




    }

    private void loginUser() {

        String password = InputPassword.getText().toString().trim();
        String phone = InputPhoneNumber.getText().toString().trim();

        if(TextUtils.isEmpty(phone))
        {
            Toast.makeText(this,"Please Write Your Phone Number...",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Please Write Your Password...",Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessToAccount(phone,password);
        }
    }

    private void AllowAccessToAccount(String phone, String password) {
        if(chkBox.isChecked())
        {
            Paper.book().write(Prevalent.UserPhoneKey,phone);
            Paper.book().write(Prevalent.UserPasswordKey,password);
        }

        final DatabaseReference RootRef= FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.child(parentDb).child(phone).exists())
                {
                    Users userData = snapshot.child(parentDb).child(phone).getValue(Users.class);
                    if(userData.getPhone().equals(phone))
                    {
                        if(userData.getPassword().equals(password))
                        {
                            if(parentDb.equals("Admins"))
                            {
                                Toast.makeText(LognActivity.this, "Welcom Admin, you are logged in Successfully...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(LognActivity.this,AdminCategoryActivity.class);
                                startActivity(intent);
                            }
                            else if(parentDb.equals("users"))
                            {
                                Toast.makeText(LognActivity.this, "logged in Successfully...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(LognActivity.this,HomeActivity.class);
                                startActivity(intent);
                            }


                        }
                        else
                        {
                            loadingBar.dismiss();
                            Toast.makeText(LognActivity.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                else
                {
                    Toast.makeText(LognActivity.this, "Account with "+ phone + "number do not exists", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}