package com.rathiworks.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rathiworks.ecommerce.Model.Users;
import com.rathiworks.ecommerce.Prevalent.Prevalent;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private Button signUpButton;
    private Button logInButton;
    String parentDbName = "Users";

    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signUpButton = (Button) findViewById(R.id.signup_button);
        logInButton = (Button) findViewById(R.id.login_button);
        loadingBar = new ProgressDialog(this);

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        //see if remembered
        Paper.init(this);

        String userPhoneKey = Paper.book().read(Prevalent.userPhoneKey);
        String userPasswordKey = Paper.book().read(Prevalent.userPasswordKey);

        if(userPhoneKey != null && userPasswordKey != null){
            if(!TextUtils.isEmpty(userPasswordKey) && !TextUtils.isEmpty(userPhoneKey)){
                allowAccess(userPhoneKey,userPasswordKey);

                loadingBar.setTitle("Logging In");
                loadingBar.setMessage("Please wait while details are being verified.");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

            }
        }
    }

    private void allowAccess(String phone, String password) {
        final DatabaseReference rootReference;
        rootReference = FirebaseDatabase.getInstance().getReference();

        rootReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.child(parentDbName).child(phone).exists()) {
                    Users userData = snapshot.child(parentDbName).child(phone).getValue(Users.class);

                    if (userData.getPhone().equals(phone)) {

                        if (userData.getPassword().equals(password)) {
                            Toast.makeText(MainActivity.this, "Logged In successfully", Toast.LENGTH_SHORT).show();

                            loadingBar.dismiss();
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(intent);
                        } else {
                            loadingBar.dismiss();
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}