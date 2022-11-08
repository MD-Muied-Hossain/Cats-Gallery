package com.example.catsgallery.Dao;



import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.catsgallery.Model.CatTableModel;

import java.util.List;

@Dao
public interface CatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<CatTableModel> cats);

    @Query("SELECT DISTINCT * FROM cateTableModel")
    LiveData<List<CatTableModel>>  getcats();

    @Query("DELETE FROM cateTableModel")
    void deleteAll();
}