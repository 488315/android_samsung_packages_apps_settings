package com.android.settings.fuelgauge.batteryusage.bugreport;

import android.content.Context;
import android.util.Log;

import androidx.room.util.DBUtil;

import com.android.settings.fuelgauge.batteryusage.db.BatteryEventDao_Impl;
import com.android.settings.fuelgauge.batteryusage.db.BatteryEventDao_Impl$$ExternalSyntheticLambda1;
import com.android.settings.fuelgauge.batteryusage.db.BatteryReattributeDao;
import com.android.settings.fuelgauge.batteryusage.db.BatteryStateDatabase;
import com.android.settings.overlay.FeatureFactoryImpl;

import java.io.PrintWriter;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class LogUtils {
    public static final Duration DUMP_TIME_OFFSET = Duration.ofHours(24);
    public static final Duration DUMP_TIME_OFFSET_FOR_ENTRY = Duration.ofHours(4);

    public static void dumpBatteryReattributeDatabaseHist(
            BatteryReattributeDao batteryReattributeDao, PrintWriter printWriter) {
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl.getPowerUsageFeatureProvider().getClass();
        printWriter.println("\n\tBatteryReattribute is disabled!");
    }

    public static void dumpListItems(
            final PrintWriter printWriter, List list, final Function function) {
        final AtomicInteger atomicInteger = new AtomicInteger(0);
        try {
            list.forEach(
                    new Consumer() { // from class:
                                     // com.android.settings.fuelgauge.batteryusage.bugreport.LogUtils$$ExternalSyntheticLambda5
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            PrintWriter printWriter2 = printWriter;
                            Function function2 = function;
                            AtomicInteger atomicInteger2 = atomicInteger;
                            printWriter2.println(function2.apply(obj));
                            if (atomicInteger2.incrementAndGet() % 20 == 0) {
                                printWriter2.flush();
                            }
                        }
                    });
        } catch (RuntimeException e) {
            Log.e("LogUtils", "dumpListItems() error: ", e);
        }
        printWriter.flush();
    }

    public static long getLastFullChargeTimestamp(Context context) {
        BatteryEventDao_Impl batteryEventDao =
                BatteryStateDatabase.getInstance(context).batteryEventDao();
        try {
            Long l =
                    (Long)
                            DBUtil.performBlocking(
                                    batteryEventDao.__db,
                                    true,
                                    false,
                                    new BatteryEventDao_Impl$$ExternalSyntheticLambda1());
            if (l != null) {
                return l.longValue();
            }
            return 0L;
        } catch (RuntimeException e) {
            Log.e("LogUtils", "getLastFullChargeTimestamp() error: ", e);
            return 0L;
        }
    }
}
