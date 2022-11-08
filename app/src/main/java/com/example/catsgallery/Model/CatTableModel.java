package com.example.catsgallery.Model;


//import android.support.annotation.NonNull;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;


//cateTableModel
@Entity(tableName = "cateTableModel",indices = @Index(value = {"id"},unique = true))
public class CatTableModel {
    @PrimaryKey
    @NonNull
    @SerializedName("id")
    @ColumnInfo(name = "id")
    String id;

    @SerializedName("url")
    @ColumnInfo(name = "image")
    String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}