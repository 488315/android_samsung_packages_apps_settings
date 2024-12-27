package com.android.settings.fuelgauge.batteryusage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.UserManager;
import android.util.ArrayMap;
import android.util.Log;

import com.android.settings.fuelgauge.BatterySettingsStorage$OptimizationModeEntity$$ExternalSyntheticOutline0;
import com.android.settingslib.fuelgauge.BatteryUtils;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.restriction.PhoneRestrictionPolicy;

import java.io.PrintWriter;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class DatabaseUtils {
    public static final Uri BATTERY_CONTENT_URI;
    public static final List BATTERY_LEVEL_RECORD_EVENTS;
    public static final long USAGE_QUERY_BUFFER_HOURS;
    static Supplier<Cursor> sFakeSupplier;

    static {
        Duration.ofMinutes(5L).toMillis();
        Duration.ofSeconds(2L).toMillis();
        USAGE_QUERY_BUFFER_HOURS = Duration.ofHours(3L).toMillis();
        new Uri.Builder()
                .scheme("content")
                .authority("com.android.settings.battery.usage.provider")
                .appendPath("AppUsageEvent")
                .build();
        new Uri.Builder()
                .scheme("content")
                .authority("com.android.settings.battery.usage.provider")
                .appendPath("BatteryEvent")
                .build();
        BATTERY_CONTENT_URI =
                new Uri.Builder()
                        .scheme("content")
                        .authority("com.android.settings.battery.usage.provider")
                        .appendPath("BatteryState")
                        .build();
        new Uri.Builder()
                .scheme("content")
                .authority("com.android.settings.battery.usage.provider")
                .appendPath("BatteryUsageSlot")
                .build();
        BATTERY_LEVEL_RECORD_EVENTS =
                List.of(BatteryEventType.FULL_CHARGED, BatteryEventType.EVEN_HOUR);
    }

    public static List getBatteryEvents(Context context, Calendar calendar, long j, List list) {
        long currentTimeMillis = System.currentTimeMillis();
        long max = Math.max(j, getTimestampSixDaysAgo(calendar));
        Log.d("DatabaseUtils", "getBatteryEvents for timestamp: " + max);
        List loadListFromContentProvider =
                loadListFromContentProvider(
                        context,
                        new Uri.Builder()
                                .scheme("content")
                                .authority("com.android.settings.battery.usage.provider")
                                .appendPath("BatteryEvent")
                                .appendQueryParameter(
                                        PhoneRestrictionPolicy.TIMESTAMP, Long.toString(max))
                                .appendQueryParameter(
                                        "batteryEventType",
                                        (String)
                                                list.stream()
                                                        .map(
                                                                new DatabaseUtils$$ExternalSyntheticLambda1(
                                                                        1))
                                                        .collect(Collectors.joining(",")))
                                .build(),
                        new DatabaseUtils$$ExternalSyntheticLambda1(2));
        Log.d(
                "DatabaseUtils",
                String.format(
                        "getBatteryEvents size=%d in %d/ms",
                        Integer.valueOf(loadListFromContentProvider.size()),
                        BatterySettingsStorage$OptimizationModeEntity$$ExternalSyntheticOutline0.m(
                                currentTimeMillis)));
        return loadListFromContentProvider;
    }

    public static long getBatteryStateLatestTimestampBeforeQueryTimestamp(Context context, long j) {
        long currentTimeMillis = System.currentTimeMillis();
        long loadLongFromContentProvider =
                loadLongFromContentProvider(
                        context,
                        new Uri.Builder()
                                .scheme("content")
                                .authority("com.android.settings.battery.usage.provider")
                                .appendPath("batteryStateLatestTimestamp")
                                .appendQueryParameter(
                                        PhoneRestrictionPolicy.TIMESTAMP, Long.toString(j))
                                .build());
        Log.d(
                "DatabaseUtils",
                String.format(
                        "getBatteryStateLatestTimestamp() batteryStateLatestTimestamp=%s in %d/ms",
                        ConvertUtils.utcToLocalTimeForLogging(loadLongFromContentProvider),
                        BatterySettingsStorage$OptimizationModeEntity$$ExternalSyntheticOutline0.m(
                                currentTimeMillis)));
        return loadLongFromContentProvider;
    }

    public static Map getHistoryMapSinceLatestRecordBeforeQueryTimestamp(
            Context context, Calendar calendar, long j, long j2) {
        long timestampSixDaysAgo = getTimestampSixDaysAgo(calendar);
        Log.d(
                "DatabaseUtils",
                "sixDaysAgoTimestamp: "
                        + ConvertUtils.utcToLocalTimeForLogging(timestampSixDaysAgo));
        return getHistoryMapSinceQueryTimestamp(
                context,
                Math.max(
                        Math.max(timestampSixDaysAgo, j2),
                        j != 0
                                ? getBatteryStateLatestTimestampBeforeQueryTimestamp(context, j)
                                : 0L));
    }

    public static Map<Long, Map<String, BatteryHistEntry>> getHistoryMapSinceQueryTimestamp(
            Context context, long j) {
        long currentTimeMillis = System.currentTimeMillis();
        List<BatteryHistEntry> loadListFromContentProvider =
                loadListFromContentProvider(
                        context,
                        new Uri.Builder()
                                .scheme("content")
                                .authority("com.android.settings.battery.usage.provider")
                                .appendPath("BatteryState")
                                .appendQueryParameter(
                                        PhoneRestrictionPolicy.TIMESTAMP, Long.toString(j))
                                .build(),
                        new DatabaseUtils$$ExternalSyntheticLambda1(0));
        ArrayMap arrayMap = new ArrayMap();
        for (BatteryHistEntry batteryHistEntry : loadListFromContentProvider) {
            long j2 = batteryHistEntry.mTimestamp;
            String key = batteryHistEntry.getKey();
            Map map = (Map) arrayMap.get(Long.valueOf(j2));
            if (map == null) {
                map = new ArrayMap();
                arrayMap.put(Long.valueOf(j2), map);
            }
            map.put(key, batteryHistEntry);
        }
        if (arrayMap.isEmpty()) {
            Log.d("DatabaseUtils", "getBatteryHistoryMap() returns empty or null");
        } else {
            Log.d(
                    "DatabaseUtils",
                    String.format(
                            "getBatteryHistoryMap() size=%d in %d/ms",
                            Integer.valueOf(arrayMap.size()),
                            BatterySettingsStorage$OptimizationModeEntity$$ExternalSyntheticOutline0
                                    .m(currentTimeMillis)));
        }
        return arrayMap;
    }

    public static long getLastFullChargeTime(Context context) {
        long currentTimeMillis = System.currentTimeMillis();
        long loadLongFromContentProvider =
                loadLongFromContentProvider(
                        context,
                        new Uri.Builder()
                                .scheme("content")
                                .authority("com.android.settings.battery.usage.provider")
                                .appendPath("lastFullChargeTimestamp")
                                .build());
        Log.d(
                "DatabaseUtils",
                String.format(
                        "getLastFullChargeTime() lastFullChargeTime=%s in %d/ms",
                        ConvertUtils.utcToLocalTimeForLogging(loadLongFromContentProvider),
                        BatterySettingsStorage$OptimizationModeEntity$$ExternalSyntheticOutline0.m(
                                currentTimeMillis)));
        return loadLongFromContentProvider;
    }

    public static Context getParentContext(Context context) {
        if (!BatteryUtils.isWorkProfile(context)) {
            return context;
        }
        try {
            return context.createPackageContextAsUser(
                    context.getPackageName(),
                    0,
                    ((UserManager) context.getSystemService(UserManager.class))
                            .getProfileParent(context.getUser()));
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("DatabaseUtils", "context.createPackageContextAsUser() fail:", e);
            return null;
        }
    }

    public static long getTimestampSixDaysAgo(Calendar calendar) {
        Calendar calendar2 =
                calendar == null ? Calendar.getInstance() : (Calendar) calendar.clone();
        calendar2.add(6, -6);
        calendar2.set(11, 0);
        calendar2.set(12, 0);
        calendar2.set(13, 0);
        calendar2.set(14, 0);
        return calendar2.getTimeInMillis();
    }

    public static <T> T loadFromContentProvider(
            Context context, Uri uri, T t, Function<Cursor, T> function) {
        Context parentContext = getParentContext(context);
        if (parentContext == null) {
            return t;
        }
        Supplier<Cursor> supplier = sFakeSupplier;
        Cursor query =
                supplier != null
                        ? supplier.get()
                        : parentContext.getContentResolver().query(uri, null, null, null);
        if (query != null) {
            try {
                if (query.getCount() != 0) {
                    t = function.apply(query);
                }
            } catch (Throwable th) {
                try {
                    query.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        if (query != null) {
            query.close();
        }
        return t;
    }

    public static List loadListFromContentProvider(
            Context context, Uri uri, final Function function) {
        return (List)
                loadFromContentProvider(
                        context,
                        uri,
                        new ArrayList(),
                        new Function() { // from class:
                                         // com.android.settings.fuelgauge.batteryusage.DatabaseUtils$$ExternalSyntheticLambda2
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                Function function2 = function;
                                Cursor cursor = (Cursor) obj;
                                ArrayList arrayList = new ArrayList();
                                while (cursor.moveToNext()) {
                                    arrayList.add(function2.apply(cursor));
                                }
                                return arrayList;
                            }
                        });
    }

    public static long loadLongFromContentProvider(Context context, Uri uri) {
        return ((Long)
                        loadFromContentProvider(
                                context,
                                uri,
                                0L,
                                new Function() { // from class:
                                                 // com.android.settings.fuelgauge.batteryusage.DatabaseUtils$$ExternalSyntheticLambda0
                                    public final /* synthetic */ long f$0 = 0;

                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        long j = this.f$0;
                                        Cursor cursor = (Cursor) obj;
                                        if (cursor.moveToFirst()) {
                                            j = cursor.getLong(0);
                                        }
                                        return Long.valueOf(j);
                                    }
                                }))
                .longValue();
    }

    public static void writeString(
            Context context, PrintWriter printWriter, String str, String str2) {
        SharedPreferences sharedPreferences =
                context.getApplicationContext()
                        .getSharedPreferences("battery_usage_shared_prefs", 0);
        if (sharedPreferences == null) {
            return;
        }
        printWriter.println(
                "\t\t" + str + ": " + sharedPreferences.getString(str2, ApnSettings.MVNO_NONE));
    }
}
