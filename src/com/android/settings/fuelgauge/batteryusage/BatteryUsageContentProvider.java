package com.android.settings.fuelgauge.batteryusage;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.room.util.DBUtil;
import androidx.sqlite.SQLiteConnection;

import com.android.settings.fuelgauge.batteryusage.db.AppUsageEventDao_Impl;
import com.android.settings.fuelgauge.batteryusage.db.AppUsageEventEntity;
import com.android.settings.fuelgauge.batteryusage.db.BatteryEventDao_Impl;
import com.android.settings.fuelgauge.batteryusage.db.BatteryEventEntity;
import com.android.settings.fuelgauge.batteryusage.db.BatteryState;
import com.android.settings.fuelgauge.batteryusage.db.BatteryStateDao_Impl;
import com.android.settings.fuelgauge.batteryusage.db.BatteryStateDatabase;
import com.android.settings.fuelgauge.batteryusage.db.BatteryUsageSlotDao_Impl;
import com.android.settings.fuelgauge.batteryusage.db.BatteryUsageSlotEntity;
import com.android.settingslib.fuelgauge.BatteryUtils;

import com.samsung.android.knox.restriction.PhoneRestrictionPolicy;

import kotlin.jvm.functions.Function1;

import java.time.Clock;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BatteryUsageContentProvider extends ContentProvider {
    public static final UriMatcher sUriMatcher;
    public AppUsageEventDao_Impl mAppUsageEventDao;
    public BatteryEventDao_Impl mBatteryEventDao;
    public BatteryStateDao_Impl mBatteryStateDao;
    public BatteryUsageSlotDao_Impl mBatteryUsageSlotDao;
    public Clock mClock;
    public static final Duration QUERY_DURATION_HOURS = Duration.ofDays(6);
    public static final List ALL_BATTERY_EVENT_TYPES =
            Arrays.stream(BatteryEventType.values())
                    .map(new BatteryUsageContentProvider$$ExternalSyntheticLambda0())
                    .toList();

    static {
        UriMatcher uriMatcher = new UriMatcher(-1);
        sUriMatcher = uriMatcher;
        uriMatcher.addURI("com.android.settings.battery.usage.provider", "BatteryState", 1);
        uriMatcher.addURI(
                "com.android.settings.battery.usage.provider", "appUsageLatestTimestamp", 2);
        uriMatcher.addURI("com.android.settings.battery.usage.provider", "AppUsageEvent", 3);
        uriMatcher.addURI("com.android.settings.battery.usage.provider", "BatteryEvent", 4);
        uriMatcher.addURI(
                "com.android.settings.battery.usage.provider", "lastFullChargeTimestamp", 5);
        uriMatcher.addURI(
                "com.android.settings.battery.usage.provider", "batteryStateLatestTimestamp", 6);
        uriMatcher.addURI("com.android.settings.battery.usage.provider", "BatteryUsageSlot", 7);
    }

    public static long getQueryValueFromUri(Uri uri, String str, long j) {
        String queryParameter = uri.getQueryParameter(str);
        if (TextUtils.isEmpty(queryParameter)) {
            Log.w("BatteryUsageContentProvider", "empty query value");
            return j;
        }
        try {
            return Long.parseLong(queryParameter);
        } catch (NumberFormatException e) {
            Log.e("BatteryUsageContentProvider", "invalid query value: " + queryParameter, e);
            return j;
        }
    }

    @Override // android.content.ContentProvider
    public final int delete(Uri uri, String str, String[] strArr) {
        throw new UnsupportedOperationException("unsupported!");
    }

    public final long getQueryTimestamp(Uri uri) {
        Log.d("BatteryUsageContentProvider", "getQueryTimestamp from uri: " + uri);
        return getQueryValueFromUri(
                uri,
                PhoneRestrictionPolicy.TIMESTAMP,
                this.mClock.millis() - QUERY_DURATION_HOURS.toMillis());
    }

    @Override // android.content.ContentProvider
    public final String getType(Uri uri) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues contentValues) {
        try {
            int match = sUriMatcher.match(uri);
            if (match != 1) {
                if (match == 7) {
                    final BatteryUsageSlotDao_Impl batteryUsageSlotDao_Impl =
                            this.mBatteryUsageSlotDao;
                    final BatteryUsageSlotEntity batteryUsageSlotEntity =
                            new BatteryUsageSlotEntity(
                                    contentValues.containsKey(PhoneRestrictionPolicy.TIMESTAMP)
                                            ? contentValues
                                                    .getAsLong(PhoneRestrictionPolicy.TIMESTAMP)
                                                    .longValue()
                                            : 0L,
                                    contentValues.containsKey("batteryUsageSlot")
                                            ? contentValues.getAsString("batteryUsageSlot")
                                            : null);
                    batteryUsageSlotDao_Impl.getClass();
                    DBUtil.performBlocking(
                            batteryUsageSlotDao_Impl.__db,
                            false,
                            true,
                            new Function1() { // from class:
                                              // com.android.settings.fuelgauge.batteryusage.db.BatteryUsageSlotDao_Impl$$ExternalSyntheticLambda0
                                @Override // kotlin.jvm.functions.Function1
                                public final Object invoke(Object obj) {
                                    BatteryUsageSlotDao_Impl.this
                                            .__insertAdapterOfBatteryUsageSlotEntity.insert(
                                            (SQLiteConnection) obj, batteryUsageSlotEntity);
                                    return null;
                                }
                            });
                } else if (match == 3) {
                    final AppUsageEventDao_Impl appUsageEventDao_Impl = this.mAppUsageEventDao;
                    final AppUsageEventEntity create = AppUsageEventEntity.create(contentValues);
                    appUsageEventDao_Impl.getClass();
                    DBUtil.performBlocking(
                            appUsageEventDao_Impl.__db,
                            false,
                            true,
                            new Function1() { // from class:
                                              // com.android.settings.fuelgauge.batteryusage.db.AppUsageEventDao_Impl$$ExternalSyntheticLambda0
                                @Override // kotlin.jvm.functions.Function1
                                public final Object invoke(Object obj) {
                                    AppUsageEventDao_Impl.this.__insertAdapterOfAppUsageEventEntity
                                            .insert((SQLiteConnection) obj, create);
                                    return null;
                                }
                            });
                } else {
                    if (match != 4) {
                        throw new IllegalArgumentException("unknown URI: " + uri);
                    }
                    final BatteryEventDao_Impl batteryEventDao_Impl = this.mBatteryEventDao;
                    final BatteryEventEntity batteryEventEntity =
                            new BatteryEventEntity(
                                    contentValues.containsKey("batteryEventType")
                                            ? contentValues
                                                    .getAsInteger("batteryEventType")
                                                    .intValue()
                                            : 0,
                                    contentValues.containsKey("batteryLevel")
                                            ? contentValues.getAsInteger("batteryLevel").intValue()
                                            : 0,
                                    contentValues.containsKey(PhoneRestrictionPolicy.TIMESTAMP)
                                            ? contentValues
                                                    .getAsLong(PhoneRestrictionPolicy.TIMESTAMP)
                                                    .longValue()
                                            : 0L);
                    batteryEventDao_Impl.getClass();
                    DBUtil.performBlocking(
                            batteryEventDao_Impl.__db,
                            false,
                            true,
                            new Function1() { // from class:
                                              // com.android.settings.fuelgauge.batteryusage.db.BatteryEventDao_Impl$$ExternalSyntheticLambda0
                                @Override // kotlin.jvm.functions.Function1
                                public final Object invoke(Object obj) {
                                    BatteryEventDao_Impl.this.__insertAdapterOfBatteryEventEntity
                                            .insert((SQLiteConnection) obj, batteryEventEntity);
                                    return null;
                                }
                            });
                }
            } else {
                final BatteryStateDao_Impl batteryStateDao_Impl = this.mBatteryStateDao;
                final BatteryState create2 = BatteryState.create(contentValues);
                batteryStateDao_Impl.getClass();
                DBUtil.performBlocking(
                        batteryStateDao_Impl.__db,
                        false,
                        true,
                        new Function1() { // from class:
                                          // com.android.settings.fuelgauge.batteryusage.db.BatteryStateDao_Impl$$ExternalSyntheticLambda0
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                BatteryStateDao_Impl.this.__insertAdapterOfBatteryState.insert(
                                        (SQLiteConnection) obj, create2);
                                return null;
                            }
                        });
            }
            return uri;
        } catch (RuntimeException e) {
            if (e instanceof IllegalArgumentException) {
                throw e;
            }
            Log.e("BatteryUsageContentProvider", "insert() from:" + uri + " error:", e);
            return null;
        }
    }

    @Override // android.content.ContentProvider
    public final boolean onCreate() {
        if (BatteryUtils.isWorkProfile(getContext())) {
            Log.w("BatteryUsageContentProvider", "do not create provider for work profile");
            return false;
        }
        this.mClock = Clock.systemUTC();
        BatteryStateDatabase batteryStateDatabase = BatteryStateDatabase.getInstance(getContext());
        this.mBatteryStateDao = batteryStateDatabase.batteryStateDao();
        this.mAppUsageEventDao = batteryStateDatabase.appUsageEventDao();
        this.mBatteryEventDao = batteryStateDatabase.batteryEventDao();
        this.mBatteryUsageSlotDao = batteryStateDatabase.batteryUsageSlotDao();
        Log.w("BatteryUsageContentProvider", "create content provider from " + getCallingPackage());
        return true;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(10:30|(11:45|46|(2:48|49)|51|(1:34)|44|36|37|38|39|40)|32|(0)|44|36|37|38|39|40) */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x016e, code lost:

       if (r11 != false) goto L39;
    */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0183, code lost:

       r11 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0184, code lost:

       android.util.Log.e("BatteryUsageContentProvider", "query() from:" + r10 + " error:", r11);
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:34:0x016a  */
    /* JADX WARN: Type inference failed for: r12v13, types: [java.util.List] */
    @Override // android.content.ContentProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.database.Cursor query(
            android.net.Uri r10,
            java.lang.String[] r11,
            java.lang.String r12,
            java.lang.String[] r13,
            java.lang.String r14) {
        /*
            Method dump skipped, instructions count: 792
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.fuelgauge.batteryusage.BatteryUsageContentProvider.query(android.net.Uri,"
                    + " java.lang.String[], java.lang.String, java.lang.String[],"
                    + " java.lang.String):android.database.Cursor");
    }

    public void setClock(Clock clock) {
        this.mClock = clock;
    }

    @Override // android.content.ContentProvider
    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        throw new UnsupportedOperationException("unsupported!");
    }
}
