package com.example.sinbike.Observers;

import com.example.sinbike.POJO.Bicycle;
import com.example.sinbike.POJO.ParkingLot;

import java.util.List;

public interface ParkingLotViewModelObserver {
    public void showParkingLotList(List<ParkingLot> parkingLotList);

}
