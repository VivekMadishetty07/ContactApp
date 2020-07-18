package com.contactapp.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.contactapp.R;
import com.contactapp.roomDb.Contacts;
import com.contactapp.roomDb.DatabaseClient;

public class AddContact extends AppCompatActivity {
ImageView back;
Button addContact;
EditText firstName,lastName,Address,Phone,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        back=findViewById(R.id.ivBack);
        addContact=findViewById(R.id.btAdd);
        firstName=findViewById(R.id.etFirstName);
        lastName=findViewById(R.id.etLastName);
        Address=findViewById(R.id.etAddress);
        Phone=findViewById(R.id.etPhone);
        email=findViewById(R.id.etEmail);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContactToPhoneBook();
            }
        });
    }

    private void addContactToPhoneBook() {
        final String sFirstName = firstName.getText().toString().trim();
        final String sLastName = lastName.getText().toString().trim();
        final String sEmail = email.getText().toString().trim();
        final String sPhone = Phone.getText().toString().trim();
        final String sAddress = Address.getText().toString().trim();
        if (sFirstName.isEmpty()){
            firstName.setError("First Name Required");
            firstName.requestFocus();
            return;
        }  if (sLastName.isEmpty()){
            lastName.setError("Last Name Required");
            lastName.requestFocus();
            return;
        }  if (sEmail.isEmpty()){
            email.setError("Email Required");
            email.requestFocus();
            return;
        }  if (sPhone.isEmpty()){
            Phone.setError("Phone Required");
            Phone.requestFocus();
            return;
        }  if (sAddress.isEmpty()){
            Address.setError("Address Required");
            Address.requestFocus();
            return;
        }
        Contacts task = new Contacts();
        task.setFirst_name(sFirstName);
        task.setLast_name(sLastName);
        task.setAddress(sAddress);
        task.setEmail(sEmail);
        task.setPhone_number(sPhone);


        //adding to database
        DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                .contactDao()
                .insert(task);
        Toast.makeText(this, "Contact Added Successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}