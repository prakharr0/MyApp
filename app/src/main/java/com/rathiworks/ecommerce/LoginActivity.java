package com.rathiworks.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rathiworks.ecommerce.Model.Users;
import com.rathiworks.ecommerce.Prevalent.Prevalent;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private EditText phoneNumber, password;
    private Button loginButton;
    private ProgressDialog loadingBar;

    private String parentDbName = "Users";

    //for remember me
    private CheckBox checkBoxRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkBoxRememberMe = (CheckBox) findViewById(R.id.remember_me_checkbox);
        loginButton = (Button) findViewById(R.id.loginactivity_login_button);
        phoneNumber = (EditText) findViewById(R.id.number_input);
        password = (EditText) findViewById(R.id.password_input);
        loadingBar = new ProgressDialog(this);

        Paper.init(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logInUser(phoneNumber.getText().toString(),password.getText().toString());
            }
        });
    }

    public void logInUser(String number, String password){
        //check for empty entries
        if(TextUtils.isEmpty(number)){
            Toast.makeText(this,"Please write your phone number", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please write your password", Toast.LENGTH_SHORT).show();
        }

        else {
            loadingBar.setTitle("Logging In");
            loadingBar.setMessage("Please wait while details are being verified.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            allowAccess(number,password);

        }
    }

    public void allowAccess(String phone, String password){

        if(checkBoxRememberMe.isChecked()){
            Paper.book().write(Prevalent.userPhoneKey, phone);
            Paper.book().write(Prevalent.userPasswordKey, password);
        }

        final DatabaseReference rootReference;
        rootReference = FirebaseDatabase.getInstance().getReference();

        rootReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("Does it exist", String.valueOf(snapshot.child(parentDbName).child(phone).exists()));
                if(snapshot.child(parentDbName).child(phone).exists()){
                    Users userData = snapshot.child("Users").child(phone).getValue(Users.class);

                    Log.d("phone number ok: " , String.valueOf(userData.getPhone().equals(phone)));
                    if(userData.getPhone().equals(phone)){
                        Log.d("password ok: " , String.valueOf(userData.getPassword().equals(password)));
                        if(userData.getPassword().equals(password)){
                            Log.d("Hi", " in for auth");
                            Toast.makeText(LoginActivity.this, "Logged In successfully", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }

                        else {
                            Log.d("Given Password: " ,password);
                            Log.d("user password: ", userData.getPassword());
                            Log.d("checking equality:", String.valueOf(password.equals(userData.getPassword())));
                            Toast.makeText(LoginActivity.this, "Enter the correct details", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    }
                }
                else {
                    Toast.makeText(LoginActivity.this, "Please sign up first", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}