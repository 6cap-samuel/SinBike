package com.example.sinbike.Services;

import android.app.Application;

import com.example.sinbike.POJO.Bicycle;
import com.example.sinbike.POJO.Fault;
import com.example.sinbike.Repositories.BicycleRepository;
import com.example.sinbike.Repositories.FaultRepository;
import com.example.sinbike.Repositories.Firestore.Resource;
import com.example.sinbike.Repositories.common.CompletionLiveData;
import com.example.sinbike.Repositories.common.QueryLiveData;

import androidx.lifecycle.LiveData;

public class BicycleService {
    private static final String TAG = "FaultService";
    private BicycleRepository bicycleRepository;

    public BicycleService(Application application){
        this.bicycleRepository = new BicycleRepository(application);
    }

    public QueryLiveData<Bicycle> getAllBicycle(){
        return this.bicycleRepository.getAllBicycle();
    }

    public CompletionLiveData create(Bicycle bicycle){
        return this.bicycleRepository.createBicycle(bicycle);
    }

    public LiveData<Resource<Boolean>> update(String docId, Bicycle bicycle){
        return this.bicycleRepository.updateBicycle(docId, bicycle);
    }

    public LiveData<Resource<Boolean>> delete(String docId){
        return this.bicycleRepository.deleteBicycle(docId);
    }
}
