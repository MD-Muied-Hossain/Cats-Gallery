package com.example.catsgallery.ViewModel;

import android.app.Application;
//import android.support.annotation.NonNull;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.catsgallery.Model.CatTableModel;
import com.example.catsgallery.Repository.CatRepository;

import java.util.List;

public class CatViewModel extends AndroidViewModel {
    private CatRepository catRepository;
    public LiveData<List<CatTableModel>> getAllCats;

    public CatViewModel(@NonNull Application application) {
        super(application);
        catRepository = new CatRepository(application);
        getAllCats= catRepository.getAllCats();
    }

    public void insert(List<CatTableModel> cats)
    {
        catRepository.insert(cats);
    }

    public LiveData<List<CatTableModel>> getAllCats()
    {
        return getAllCats;
    }
}