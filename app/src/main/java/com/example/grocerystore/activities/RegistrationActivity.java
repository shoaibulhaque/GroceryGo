package com.example.grocerystore.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocerystore.R;
import com.example.grocerystore.fragments.HomeFragment;
import com.example.grocerystore.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    Button signUp;
    EditText name,email,password;
    TextView SignIn;
    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        signUp = findViewById(R.id.signup_button);
        name = findViewById(R.id.Name);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.password);
        SignIn = findViewById(R.id.login_view);

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
                finish();

            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createUser();

            }
        });
    }

    private void createUser() {
        String userName = name.getText().toString();
        String userEmail = email.getText().toString();
        String userPass = password.getText().toString();

        if (TextUtils.isEmpty(userName)){
            Toast.makeText(this,"Name field is Empty",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(userEmail))
        {
            Toast.makeText(this,"Email field is Empty",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(userPass))
        {
            Toast.makeText(this,"Password field is Empty",Toast.LENGTH_SHORT).show();
            return;
        }
        if (userPass.length() < 6){
            Toast.makeText(this, "Password is too short ! must be greater than 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }
        // create user
        auth.createUserWithEmailAndPassword(userEmail, userPass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            UserModel user = new UserModel(userName, userEmail, userPass);
                            String id = task.getResult().getUser().getUid();
                            database.getReference().child("Users").child(id).setValue(user);

                            Toast.makeText(RegistrationActivity.this, "Registration Successful and Logging In", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                            finish();
                        }
                        else {
                            Toast.makeText(RegistrationActivity.this, "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}