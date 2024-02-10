package com.example.tunnelchat;
import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.Manifest;
import pub.devrel.easypermissions.EasyPermissions;

import android.Manifest;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends Activity {

    FirebaseAuth auth;
    RecyclerView mainUserRecyclerView;
    Useradapter adapter;
    FirebaseDatabase database;
    ArrayList<Users> usersArrayList;
    ImageView imglogout, setbut, cumbut;
    final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setbut = findViewById(R.id.settingBut);
        final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
        cumbut = findViewById(R.id.camBut);
        imglogout = findViewById(R.id.logoutimg);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        // Initialize RecyclerView
        mainUserRecyclerView = findViewById(R.id.mainUserRecyclerView);
        mainUserRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        usersArrayList = new ArrayList<>();
        adapter = new Useradapter(MainActivity.this, usersArrayList);
        mainUserRecyclerView.setAdapter(adapter);

        // Check if the user is authenticated
        if (auth.getCurrentUser() == null){
            Intent intent = new Intent(MainActivity.this, login.class);
            startActivity(intent);
            finish();
        } else {
            // User is authenticated, proceed with retrieving contacts and filtering users
            retrieveContacts();
        }

        // Logout functionality
        imglogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle logout
                // Display confirmation dialog
                Dialog dialog = new Dialog(MainActivity.this,R.style.dialoge);
                dialog.setContentView(R.layout.dialog_layout);
                Button yesButton = dialog.findViewById(R.id.yesbnt);
                Button noButton = dialog.findViewById(R.id.nobnt);

                yesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Logout user
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(MainActivity.this, login.class);
                        startActivity(intent);
                        finish();
                    }
                });

                noButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Dismiss dialog
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        // Handle Settings button click
        setbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Settings button click
                Intent intent = new Intent(MainActivity.this, setting.class);
                startActivity(intent);
            }
        });

        // Handle Camera button click
        cumbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Camera button click
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 10);
            }
        });


        if(EasyPermissions.hasPermissions(this, permission.READ_CONTACTS)) {
            // Permission already granted, proceed with your logic
            retrieveContacts();

        } else {
            // Permission has not been granted, request it
            EasyPermissions.requestPermissions(
                    this,
                    "This app needs permission to access contacts.",
                    PERMISSIONS_REQUEST_READ_CONTACTS,
                    permission.READ_CONTACTS
            );
        }
    }

    // Method to retrieve contacts from the device


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            // Check if the permission has been granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with your logic
                retrieveContacts();
            } else {
                // Permission denied, show a message or handle the denial gracefully
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void retrieveContacts() {
        List<String> contactsList = new ArrayList<>();

        // Query the contacts content provider
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String contactNumber = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                // Format the contact number to remove non-numeric characters and spaces
                contactNumber = contactNumber.replaceAll("[^0-9]", "");
                // Add the contact number to the list
                contactsList.add(contactNumber);
            }
            cursor.close();
        }

        // Now, you have the list of contact numbers
        // You can proceed with filtering your user list based on these contact numbers
        filterUserList(contactsList);
    }

    // Method to filter user list based on contact numbers
    private void filterUserList(List<String> contactsList) {
        DatabaseReference reference = database.getReference().child("user");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersArrayList.clear(); // Clear previous data
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Users user = dataSnapshot.getValue(Users.class);
                    // Filter users based on contacts
                    if (contactsList.contains(user.getPhoneNumberr())) {
                        usersArrayList.add(user);
                    }
                }
                adapter.notifyDataSetChanged(); // Notify adapter about data change
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Failed to retrieve users data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
