package com.example.catsgallery.Database;


import android.content.Context;
import android.os.AsyncTask;
//import android.support.annotation.NonNull;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.catsgallery.Dao.CatDao;
import com.example.catsgallery.Model.CatTableModel;

@Database(entities = {CatTableModel.class},version = 6)
public abstract class CatDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "cateTableModel";
    public abstract CatDao catDao();
    public static volatile CatDatabase INSTANCE = null;

    public static CatDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (CatDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, CatDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(callback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static Callback callback = new Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsyn(INSTANCE);
        }
    };

    static  class  PopulateDbAsyn extends AsyncTask<Void,Void,Void>{
        private CatDao catDao;
        public PopulateDbAsyn(CatDatabase catDatabase)
        {
            catDao =catDatabase.catDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            catDao.deleteAll();
            return null;
        }
    }
}