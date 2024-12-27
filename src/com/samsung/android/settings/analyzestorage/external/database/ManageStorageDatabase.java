package com.samsung.android.settings.analyzestorage.external.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.samsung.android.settings.analyzestorage.data.database.dao.UnusedAppBnRInfoDao_Impl;
import com.samsung.android.settings.analyzestorage.domain.log.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class ManageStorageDatabase extends RoomDatabase {
    public static volatile ManageStorageDatabase sInstance;

    public static ManageStorageDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (ManageStorageDatabase.class) {
                try {
                    if (sInstance == null) {
                        Log.d("ManageStorageDatabase", "ManageStorageDatabase ] create database");
                        RoomDatabase.Builder databaseBuilder =
                                Room.databaseBuilder(
                                        context.getApplicationContext(),
                                        ManageStorageDatabase.class,
                                        "manage_storage.db");
                        databaseBuilder.requireMigration = false;
                        databaseBuilder.allowDestructiveMigrationOnDowngrade = true;
                        databaseBuilder.allowMainThreadQueries = true;
                        sInstance = (ManageStorageDatabase) databaseBuilder.build();
                    }
                } finally {
                }
            }
        }
        return sInstance;
    }

    public abstract UnusedAppBnRInfoDao_Impl unusedAppBnRInfoDao();
}
