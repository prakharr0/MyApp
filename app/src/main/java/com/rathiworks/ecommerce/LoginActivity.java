package com.rathiworks.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class LoginActivity extends AppCompatActivity {

    private EditText phoneNumber, password;
    private Button loginButton;
    private ProgressDialog loadingBar;

    private String parentDbName = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.loginactivity_login_button);
        phoneNumber = (EditText) findViewById(R.id.number_input);
        password = (EditText) findViewById(R.id.password_input);
        loadingBar = new ProgressDialog(this);

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

    public void allowAccess(String number, String password){
        final DatabaseReference rootReference;
        rootReference = FirebaseDatabase.getInstance().getReference();

        rootReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(parentDbName).child(number).exists()){
                    Users userData = snapshot.child(parentDbName).child(number).getValue(Users.class);

                    if(userData.getPhone().equals(number)){
                        if(userData.getPassword().equals(password)){
                            Toast.makeText(LoginActivity.this, "Logged In successfully", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
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