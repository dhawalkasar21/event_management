package com.example.eventmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    TextInputEditText email;
    TextInputEditText password;
    Button signInButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(MainActivity.this, EventList.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();



        email = findViewById(R.id.email_input);
        password = findViewById(R.id.password_input);
        signInButton = findViewById(R.id.signIn);
        onButtonPressed();
    }

    void onButtonPressed(){
       signInButton.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view) {
               Log.d("",email.getText().toString());
               Log.d("",password.getText().toString());
               signInFirebase(email.getText().toString().trim(),password.getText().toString());
           }
       });
    }

    void signInFirebase(String email,String password) {

            mAuth.signInWithEmailAndPassword("kasar.dhawalmca21@gmail.com", "12345678")
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent = new Intent(MainActivity.this, EventList.class);
                                startActivity(intent);
                                // updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(MainActivity.this, "U are not an admin",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }

                            // ...
                        }
                    });
    }
}