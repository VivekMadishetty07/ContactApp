package com.contactapp.contacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.contactapp.R;
import com.contactapp.contacts.AddContact;
import com.contactapp.contacts.ContactAdapter;
import com.contactapp.roomDb.Contacts;
import com.contactapp.roomDb.DatabaseClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
RecyclerView userList;
FloatingActionButton addContact;
ArrayList<Contacts> listOfContacts;
TextView noContact;
ContactAdapter adapter;
SearchView searchView;

    @Override
    protected void onStart() {
        super.onStart();
        userList=findViewById(R.id.rvContactList);
        searchView=findViewById(R.id.searchView);
        addContact=findViewById(R.id.fbAddContact);
        noContact=findViewById(R.id.tvNoContact);
        listOfContacts= (ArrayList<Contacts>) DatabaseClient
                .getInstance(getApplicationContext())
                .getAppDatabase()
                .contactDao()
                .getAll();
        if (listOfContacts.isEmpty()){
            noContact.setVisibility(View.VISIBLE);
        }else {
            adapter=new ContactAdapter(this,listOfContacts);
            userList.setAdapter(adapter);
        }
        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddContact.class));
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
}