package com.samsung.android.settings.theftprotection.location;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.util.AtomicFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.samsung.android.settings.theftprotection.logging.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class LocationStorage {
    public final StorageHandler mHandler;
    public final List mLocationList = new ArrayList();
    public final File mStorageFile;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class InstanceHolder {
        public static final LocationStorage INSTANCE = new LocationStorage();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class StorageHandler extends Handler {
        public StorageHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            FileOutputStream fileOutputStream;
            if (message.what == 1) {
                LocationStorage locationStorage = LocationStorage.this;
                locationStorage.getClass();
                Log.d("LocationStorage", "saveLocationData START");
                AtomicFile atomicFile = new AtomicFile(locationStorage.mStorageFile);
                try {
                    fileOutputStream = atomicFile.startWrite();
                    try {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.prettyPrinting = true;
                        fileOutputStream.write(
                                gsonBuilder
                                        .create()
                                        .toJson(locationStorage.mLocationList)
                                        .getBytes(StandardCharsets.UTF_8));
                        Log.d(
                                "LocationStorage",
                                "Success to save storage data size : "
                                        + ((ArrayList) locationStorage.mLocationList).size());
                        atomicFile.finishWrite(fileOutputStream);
                    } catch (Exception e) {
                        e = e;
                        atomicFile.failWrite(fileOutputStream);
                        Log.e("LocationStorage", "Fail to save storage data" + e.getMessage());
                        Log.d("LocationStorage", "saveLocationData END");
                    }
                } catch (Exception e2) {
                    e = e2;
                    fileOutputStream = null;
                }
                Log.d("LocationStorage", "saveLocationData END");
            }
        }
    }

    public LocationStorage() {
        File file =
                new File(
                        Environment.getUserSystemDirectory(UserHandle.myUserId()),
                        "sec_settings_location.json");
        this.mStorageFile = file;
        this.mHandler = new StorageHandler(Looper.getMainLooper());
        Log.d("LocationStorage", "loadLocationDataInternal START");
        ArrayList arrayList = new ArrayList();
        try {
            arrayList.addAll(
                    Arrays.stream(
                                    (LocationData[])
                                            new Gson()
                                                    .fromJson(
                                                            new String(
                                                                    new AtomicFile(file)
                                                                            .readFully(),
                                                                    StandardCharsets.UTF_8),
                                                            LocationData[].class))
                            .toList());
        } catch (Exception e) {
            Log.e("LocationStorage", "Fail to load storage data" + e.getMessage());
        }
        ((ArrayList) this.mLocationList).clear();
        ((ArrayList) this.mLocationList).addAll(arrayList);
        Log.d("LocationStorage", "loadLocationDataInternal END");
    }

    public final synchronized void addLocationData(LocationData locationData) {
        if (((ArrayList) this.mLocationList).size() >= 10) {
            Log.d("LocationStorage", "addLocationData : unable to add");
            return;
        }
        Log.d("LocationStorage", "addLocationData : " + locationData.mKey);
        ((ArrayList) this.mLocationList).add(locationData);
        this.mHandler.sendMessage(this.mHandler.obtainMessage(1));
    }

    public final List loadLocationData() {
        Log.d(
                "LocationStorage",
                "loadLocationData size : " + ((ArrayList) this.mLocationList).size());
        return new ArrayList(this.mLocationList);
    }

    public final synchronized void updateLocationData(final LocationData locationData) {
        this.mLocationList.stream()
                .filter(new LocationStorage$$ExternalSyntheticLambda1(0, locationData))
                .findFirst()
                .ifPresent(
                        new Consumer() { // from class:
                                         // com.samsung.android.settings.theftprotection.location.LocationStorage$$ExternalSyntheticLambda2
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                LocationStorage locationStorage = LocationStorage.this;
                                LocationData locationData2 = locationData;
                                LocationData locationData3 = (LocationData) obj;
                                locationStorage.getClass();
                                locationData3.mEnterLocation = locationData2.mEnterLocation;
                                locationData3.mLatitude = locationData2.mLatitude;
                                locationData3.mLongitude = locationData2.mLongitude;
                                locationData3.mRadius = locationData2.mRadius;
                                locationData3.mSSID = locationData2.mSSID;
                                locationData3.mAddress = locationData2.mAddress;
                                locationData3.mName = locationData2.mName;
                                locationData3.mType = locationData2.mType;
                                Log.d(
                                        "LocationStorage",
                                        "updateLocationData updated : " + locationData3.mKey);
                                LocationStorage.StorageHandler storageHandler =
                                        locationStorage.mHandler;
                                storageHandler.sendMessage(storageHandler.obtainMessage(1));
                            }
                        });
    }

    public final LocationData loadLocationData(String str) {
        return (LocationData)
                this.mLocationList.stream()
                        .filter(new LocationStorage$$ExternalSyntheticLambda1(1, str))
                        .findFirst()
                        .orElse(null);
    }
}
