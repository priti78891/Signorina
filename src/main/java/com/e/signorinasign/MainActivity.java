package com.e.signorinasign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.e.signorinasign.Model.Users;
import com.e.signorinasign.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;


public class MainActivity extends AppCompatActivity {
    private Button joinNowButton, loginButton;
    private ProgressDialog loadingBar;
    private String parentDb ="users";
    private TextView language,app_slogan;
    boolean lang_selected = true;
    Context context;
    Resources resource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        joinNowButton = (Button) findViewById(R.id.main_join_btn);
        loginButton = (Button) findViewById(R.id.main_login_btn);
        app_slogan = (TextView) findViewById(R.id.app_slogan);
        loadingBar = new ProgressDialog(this);
        Paper.init(this);

        joinNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,LognActivity.class);
                startActivity(intent);
            }
        });

        language = (TextView)findViewById(R.id.language);
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] languages = {"ENGLISH", "HINDI"};
                int checkedItem;
                if(lang_selected)
                {
                    checkedItem = 0;

                }
                else
                {
                    checkedItem = 1;
                }
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("select a language").setSingleChoiceItems(languages, checkedItem,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                language.setText(languages[i]);
                                if(languages[i].equals("ENGLISH"))
                                {
                                    Users.flag=0;
                                  context = LocaleHelper.setLocale(MainActivity.this,"en");
                                  resource = context.getResources();

                                  joinNowButton.setText(resource.getString(R.string.join_now));
                                  loginButton.setText(resource.getString(R.string.already_have_an_account_login));
                                  app_slogan.setText(resource.getString(R.string.happiness_is_not_in_money_but_in_shopping));

                                }
                                if(languages[i].equals("HINDI"))
                                {
                                    lang_selected=false;

                                    Users.flag=1;
                                    context = LocaleHelper.setLocale(MainActivity.this,"hi");
                                    resource = context.getResources();

                                    joinNowButton.setText(resource.getString(R.string.join_now));
                                    loginButton.setText(resource.getString(R.string.already_have_an_account_login));
                                    app_slogan.setText(resource.getString(R.string.happiness_is_not_in_money_but_in_shopping));

                                }


                            }
                        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.create().show();
            }
        });

        String UserPhoneKey = Paper.book().read(Prevalent.UserPhoneKey);
        String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);

        if(UserPhoneKey != null && UserPasswordKey != null)
        {
            if(!TextUtils.isEmpty(UserPasswordKey) && !TextUtils.isEmpty(UserPhoneKey))
            {
                AllowAccess(UserPasswordKey,UserPhoneKey);

                loadingBar.setTitle("Already Logged in");
                loadingBar.setMessage("Please wait...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

            }
        }

    }

    private void AllowAccess(final String password,final String phone) {

        final DatabaseReference RootRef= FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.child(parentDb).child(phone).exists()) {
                    Users userData = snapshot.child(parentDb).child(phone).getValue(Users.class);
                    if (userData.getPhone().equals(phone)) {
                        if (userData.getPassword().equals(password)) {
                            Toast.makeText(MainActivity.this, "Please wait, you are already logged in.", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                            Prevalent.currentOnlineUsers = userData;
                            startActivity(intent);


                        } else {
                            loadingBar.dismiss();
                            Toast.makeText(MainActivity.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
                        }

                    }
                } else {
                    Toast.makeText(MainActivity.this, "Account with " + phone + "number do not exists", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });



    }
}