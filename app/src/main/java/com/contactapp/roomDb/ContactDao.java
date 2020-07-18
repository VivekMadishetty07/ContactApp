package com.contactapp.roomDb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ContactDao {

    @Query("SELECT * FROM contacts")
    List<Contacts> getAll();

    @Insert
    void insert(Contacts contacts);

    @Delete
    void delete(Contacts contacts);

    @Update
    void update(Contacts contacts);

}