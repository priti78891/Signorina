package com.e.signorinasign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.e.signorinasign.Model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button createAccountButton;
    private EditText InputName,InputPhoneNumber,InputPassword;
    private ProgressDialog loadingBar;

    Context context;
    Resources resource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        createAccountButton = (Button) findViewById(R.id.create_account_btn);
        InputName = (EditText) findViewById(R.id.register_name);
        InputPassword = (EditText) findViewById(R.id.register_password);
        InputPhoneNumber = (EditText) findViewById(R.id.register_phoneNumber);
        loadingBar = new ProgressDialog(this);
        if(Users.flag==1) {
            context = LocaleHelper.setLocale(RegisterActivity.this, "hi");
            resource = context.getResources();
            createAccountButton.setText(resource.getString(R.string.create_account));
            InputName.setText(resource.getString(R.string.name));
            InputPassword.setText(resource.getString(R.string.password));
            InputPhoneNumber.setText(resource.getString(R.string.phone_number));

        }
        if(Users.flag==0)
        {
            context = LocaleHelper.setLocale(RegisterActivity.this, "en");
            resource = context.getResources();
            createAccountButton.setText(resource.getString(R.string.create_account));
            InputName.setText(resource.getString(R.string.name));
            InputPassword.setText(resource.getString(R.string.password));
            InputPhoneNumber.setText(resource.getString(R.string.phone_number));
        }

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }

        });


    }

    private void createAccount() {

        String name = InputName.getText().toString();
        String password = InputPassword.getText().toString();
        String phone = InputPhoneNumber.getText().toString();
        if(TextUtils.isEmpty(name))
        {
            Toast.makeText(this,"Please Write Your name...",Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Please Write Your Password...",Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(phone))
        {
            Toast.makeText(this,"Please Write Your Phone Number...",Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            ValidatePhoneNumber(name,phone,password);
        }

    }

    private void ValidatePhoneNumber(String name, String phone, String password) {


        final DatabaseReference RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!(snapshot.child("users").child(phone).exists()))
                {
                    HashMap<String,Object> userDataMap = new HashMap<>();
                    userDataMap.put("phone",phone);
                    userDataMap.put("name",name);
                    userDataMap.put("password",password);

                    RootRef.child("users").child(phone).updateChildren(userDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(RegisterActivity.this, "Congratulation, your account has been created.", Toast.LENGTH_SHORT).show();

                                        loadingBar.dismiss();
                                        Intent intent = new Intent(RegisterActivity.this,LognActivity.class);
                                        startActivity(intent);


                                    }
                                    else
                                    {
                                        loadingBar.dismiss();
                                        Toast.makeText(RegisterActivity.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "This" + phone + "already exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please try again using another phone number" , Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(intent);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    }
