package com.example.ucemap.service.informacionSingleton;

import android.location.Location;

public class DataManager {
    private static DataManager instance;
    private Location lastKnownLocation;

    private static boolean banderaLocalizacion;

    private DataManager() {
        // Constructor privado para evitar instanciación directa
    }

    public static synchronized DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public Location getLastKnownLocation() {
        return lastKnownLocation;
    }

    public void setLastKnownLocation(Location location) {
        this.lastKnownLocation = location;
    }

    public static boolean getBanderaLocalizacion() {
        return banderaLocalizacion;
    }

    public static void setBanderaLocalizacion(boolean banderaLocalizacion) {
        DataManager.banderaLocalizacion = banderaLocalizacion;
    }
}
