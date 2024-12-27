package com.android.settings.fuelgauge.batterytip;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.sec.ims.IMSParameter;
import com.sec.ims.ImsManager;

import java.util.Collections;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BatteryDatabaseManager {
    public static BatteryDatabaseManager sSingleton;
    public final AnomalyDatabaseHelper mDatabaseHelper;

    public BatteryDatabaseManager(Context context) {
        AnomalyDatabaseHelper anomalyDatabaseHelper;
        AnomalyDatabaseHelper anomalyDatabaseHelper2 = AnomalyDatabaseHelper.sSingleton;
        synchronized (AnomalyDatabaseHelper.class) {
            try {
                if (AnomalyDatabaseHelper.sSingleton == null) {
                    AnomalyDatabaseHelper.sSingleton =
                            new AnomalyDatabaseHelper(
                                    context.getApplicationContext(),
                                    "battery_settings.db",
                                    null,
                                    5);
                }
                anomalyDatabaseHelper = AnomalyDatabaseHelper.sSingleton;
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mDatabaseHelper = anomalyDatabaseHelper;
    }

    public static synchronized BatteryDatabaseManager getInstance(Context context) {
        BatteryDatabaseManager batteryDatabaseManager;
        synchronized (BatteryDatabaseManager.class) {
            try {
                if (sSingleton == null) {
                    sSingleton = new BatteryDatabaseManager(context);
                }
                batteryDatabaseManager = sSingleton;
            } catch (Throwable th) {
                throw th;
            }
        }
        return batteryDatabaseManager;
    }

    public static void setUpForTest(BatteryDatabaseManager batteryDatabaseManager) {
        sSingleton = batteryDatabaseManager;
    }

    public final synchronized void deleteAction(int i, String str) {
        this.mDatabaseHelper
                .getWritableDatabase()
                .delete(
                        IMSParameter.CALL.ACTION,
                        "action_type = ? AND uid = ? AND package_name = ? ",
                        new String[] {String.valueOf(0), String.valueOf(i), String.valueOf(str)});
    }

    public final synchronized void insertAction(long j, int i, String str) {
        SQLiteDatabase writableDatabase = this.mDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NetworkAnalyticsConstants.DataPoints.UID, Integer.valueOf(i));
        contentValues.put("package_name", str);
        contentValues.put(ImsManager.INTENT_PARAM_RCS_ENABLE_TYPE, (Integer) 0);
        contentValues.put("time_stamp_ms", Long.valueOf(j));
        writableDatabase.insertWithOnConflict(IMSParameter.CALL.ACTION, null, contentValues, 5);
    }

    public final synchronized void updateAnomalies(List list) {
        try {
            if (!list.isEmpty()) {
                int size = list.size();
                String[] strArr = new String[size];
                for (int i = 0; i < size; i++) {
                    strArr[i] = ((AppInfo) list.get(i)).packageName;
                }
                SQLiteDatabase writableDatabase = this.mDatabaseHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("anomaly_state", (Integer) 1);
                writableDatabase.update(
                        "anomaly",
                        contentValues,
                        "package_name IN ("
                                + TextUtils.join(",", Collections.nCopies(list.size(), "?"))
                                + ")",
                        strArr);
            }
        } catch (Throwable th) {
            throw th;
        }
    }
}
