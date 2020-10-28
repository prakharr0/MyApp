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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    private Button createAccountButton;
    private EditText inputName, phoneNumber, password;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        loadingBar = new ProgressDialog(this);

        createAccountButton = (Button) findViewById(R.id.create_account_button);
        inputName = (EditText) findViewById(R.id.signup_name_input);
        phoneNumber = (EditText) findViewById(R.id.signup_number_input);
        password = (EditText) findViewById(R.id.signup_password_input);


        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }

    public void createAccount(){
        String name = inputName.getText().toString();
        String phone = phoneNumber.getText().toString();
        String pass = password.getText().toString();

        //check for empty entries
        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"Please write your name", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(phone)){
            Toast.makeText(this,"Please write your phone number", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(pass)){
            Toast.makeText(this,"Please write your password", Toast.LENGTH_SHORT).show();
        }

        //if everything is okay
        else {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait while details are being verified.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            //check if phone number is already in use
            validatePhoneNumber(name, pass,phone);
        }

    }

    public void validatePhoneNumber(String name, String pass, String phone){
        //database reference -- in app gradle search CODE DATABASE
        final DatabaseReference rootReference;
        rootReference = FirebaseDatabase.getInstance().getReference();

        rootReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!(snapshot.child("Users").child(phone).exists())){
                    HashMap<String, Object> userDataMap = new HashMap<>();
                    userDataMap.put("phone",phone);
                    userDataMap.put("password",pass);
                    userDataMap.put("name",name);

                    rootReference.child("Users").child(phone).updateChildren(userDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(SignupActivity.this, "Welcome to world of Fashion!", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }
                                    else {
                                        Toast.makeText(SignupActivity.this, "Some error occurred: Please try again", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                    }
                                }
                            });
                }
                else{
                    Toast.makeText(SignupActivity.this, "This phone number already exists", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();

                    Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}