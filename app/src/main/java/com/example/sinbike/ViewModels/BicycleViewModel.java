package com.example.sinbike.ViewModels;

import android.app.Application;
import android.util.Log;

import com.example.sinbike.Observers.BicycleViewModelObserver;
import com.example.sinbike.POJO.Bicycle;
import com.example.sinbike.Repositories.Firestore.Resource;
import com.example.sinbike.Repositories.common.CompletionLiveData;
import com.example.sinbike.Repositories.common.QueryLiveData;
import com.example.sinbike.Services.BicycleService;
import com.google.firebase.firestore.GeoPoint;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

public class BicycleViewModel extends AndroidViewModel
{
    private static final String TAG = "BicycleViewModel";

    private LifecycleOwner lifecycleOwner;
    private BicycleService bicycleService;
    private BicycleViewModelObserver observer;

    public BicycleViewModel(Application application){
        super(application);
        this.bicycleService = new BicycleService(application);
    }

    public void setObserver(BicycleViewModelObserver observer) {
        this.observer = observer;
    }

    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
    }

    public void getAllBicycle(){
        final LiveData<com.example.sinbike.Repositories.common.Resource<List<Bicycle>>> liveobs = this.bicycleService.getAllBicycle();
        Observer obs = new Observer<com.example.sinbike.Repositories.common.Resource<List<Bicycle>>>(){
            @Override
            public void onChanged(com.example.sinbike.Repositories.common.Resource<List<Bicycle>> listResource) {
                List<Bicycle> bicycles = listResource.data();

                Log.d(TAG, bicycles.toString());

                observer.showBicycleList(bicycles);
                liveobs.removeObserver(this);
            }
        };
        liveobs.observe(this.lifecycleOwner, obs);
    }

    public void updateBicycleLocation(String docId, Bicycle bicycle, double latitude, double longitude){
        GeoPoint newGeoPoint = new GeoPoint(latitude, longitude);
        bicycle.setCoordinate(newGeoPoint);
        this.bicycleService.update(docId, bicycle);
    }
}
