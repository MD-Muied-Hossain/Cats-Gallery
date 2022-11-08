package com.example.catsgallery.Repository;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;

import com.example.catsgallery.Dao.CatDao;
import com.example.catsgallery.Database.CatDatabase;
import com.example.catsgallery.Model.CatTableModel;

import java.util.List;

public class CatRepository {
    public CatDao catDao;
    public LiveData<List<CatTableModel>> getAllCats;
    private CatDatabase database;

    public CatRepository(Application application){
        database = CatDatabase.getInstance(application);
        catDao = database.catDao();
        getAllCats = catDao.getcats();

    }

    public void insert(List<CatTableModel> cats){
        new InsertAsyncTask(catDao).execute(cats);

    }
    public LiveData<List<CatTableModel>> getAllCats(){
        return getAllCats;
    }


    private static class InsertAsyncTask extends AsyncTask<List<CatTableModel>,Void,Void>{
        private CatDao catDao;

        public InsertAsyncTask(CatDao catDao)
        {
            this.catDao = catDao;
        }
        @Override
        protected Void doInBackground(List<CatTableModel>... lists) {
            catDao.insert(lists[0]);
            return null;
        }
    }

}