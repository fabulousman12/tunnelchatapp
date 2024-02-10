/*package com.example.tunnelchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Phonenum extends AppCompatActivity {
    EditText phone, otp;
    Button reg, mainreg1;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonenum);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        phone = findViewById(R.id.phoneNum);
        otp = findViewById(R.id.Otpget);
        reg = findViewById(R.id.Registerbut);
        mainreg1 = findViewById(R.id.mainregT);
        DatabaseReference reference = database.getReference().child("user");

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pho = phone.getText().toString();

                if (pho.isEmpty()) {
                    Toast.makeText(Phonenum.this, "Please enter number", Toast.LENGTH_SHORT).show();
                } else if (pho.length() != 10) {
                    Toast.makeText(Phonenum.this, "Please enter a valid 10-digit number", Toast.LENGTH_SHORT).show();
                } else {
                    // Get the current user's ID
                    FirebaseUser currentUser = auth.getCurrentUser();
                    if (currentUser != null) {
                        String userId = currentUser.getUid();

                        // Save the phone number under the user's ID node
                        DatabaseReference userRef = reference.child(userId);
                        userRef.child("phonenumber").setValue(pho);

                        Toast.makeText(Phonenum.this, "Phone number registered successfully", Toast.LENGTH_SHORT).show();

                        // Move to the MainActivity or wherever you want to go next
                        Intent intent = new Intent(Phonenum.this, MainActivity.class);
                        startActivity(intent);
                        finish(); // Finish the Phonenum activity to prevent going back to it with the back button
                    } else {
                        // User is not authenticated
                        Toast.makeText(Phonenum.this, "User not authenticated", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
*/