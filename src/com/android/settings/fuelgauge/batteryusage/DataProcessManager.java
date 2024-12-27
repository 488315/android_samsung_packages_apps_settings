package com.android.settings.fuelgauge.batteryusage;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.UserManager;
import android.util.ArrayMap;
import android.util.Log;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settingslib.spaprivileged.model.app.AppListRepositoryUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DataProcessManager {
    public static final List POWER_CONNECTION_EVENTS =
            List.of(BatteryEventType.POWER_CONNECTED, BatteryEventType.POWER_DISCONNECTED);

    @VisibleForTesting static Map<Long, Map<String, BatteryHistEntry>> sFakeBatteryHistoryMap;
    public final List mAppUsageEventList;
    public Map mAppUsagePeriodMap;
    public final List mBatteryEventList;
    public final Map mBatteryHistoryMap;
    public final List mBatteryUsageSlotList;
    public final PowerUsageAdvanced$$ExternalSyntheticLambda1 mCallbackFunction;
    public final Context mContext;
    public final Handler mHandler;
    public final List mHourlyBatteryLevelsPerDay;
    public boolean mIsBatteryEventLoaded;
    public boolean mIsBatteryUsageSlotLoaded;
    public boolean mIsCurrentAppUsageLoaded;
    public boolean mIsCurrentBatteryHistoryLoaded;
    public boolean mIsDatabaseAppUsageLoaded;
    public final boolean mIsFromPeriodJob;
    public final long mLastFullChargeTimestamp;
    public final long mRawStartTimestamp;
    public boolean mShowScreenOnTime;
    public Set mSystemAppsPackageNames;
    public Set mSystemAppsUids;
    public final UserIdsSeries mUserIdsSeries;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.fuelgauge.batteryusage.DataProcessManager$2, reason: invalid class name */
    public final class AnonymousClass2 extends AsyncTask {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ DataProcessManager this$0;

        public /* synthetic */ AnonymousClass2(DataProcessManager dataProcessManager, int i) {
            this.$r8$classId = i;
            this.this$0 = dataProcessManager;
        }

        /* JADX WARN: Can't wrap try/catch for region: R(15:103|(2:105|(2:107|(1:109)(1:142))(1:143))(1:144)|110|(14:128|129|(4:131|132|133|134)(1:139)|135|(1:117)|118|119|120|122|123|124|98|(2:100|101)(1:102)|88)(1:114)|115|(0)|118|119|120|122|123|124|98|(0)(0)|88) */
        /* JADX WARN: Code restructure failed: missing block: B:126:0x0614, code lost:

           android.util.Log.w("ConvertUtils", "UsageEvent instance ID API error");
        */
        /* JADX WARN: Code restructure failed: missing block: B:127:0x0620, code lost:

           android.util.Log.w("ConvertUtils", java.lang.String.format("Fail to get uid for package %s of user %d)", r14.getPackageName(), r10));
        */
        /* JADX WARN: Removed duplicated region for block: B:100:0x0637  */
        /* JADX WARN: Removed duplicated region for block: B:102:0x063b A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:117:0x05e3  */
        @Override // android.os.AsyncTask
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object doInBackground(java.lang.Object[] r35) {
            /*
                Method dump skipped, instructions count: 1690
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.settings.fuelgauge.batteryusage.DataProcessManager.AnonymousClass2.doInBackground(java.lang.Object[]):java.lang.Object");
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            switch (this.$r8$classId) {
                case 0:
                    List list = (List) obj;
                    if (list == null || list.isEmpty()) {
                        Log.d("DataProcessManager", "currentAppUsageList is null or empty");
                    } else {
                        ((ArrayList) this.this$0.mAppUsageEventList).addAll(list);
                    }
                    DataProcessManager dataProcessManager = this.this$0;
                    dataProcessManager.mIsCurrentAppUsageLoaded = true;
                    DataProcessManager.m934$$Nest$mtryToProcessAppUsageData(dataProcessManager);
                    break;
                case 1:
                    Map map = (Map) obj;
                    Map map2 = this.this$0.mBatteryHistoryMap;
                    if (map2 != null) {
                        for (Map.Entry entry : map2.entrySet()) {
                            if (((Map) entry.getValue())
                                    .containsKey("CURRENT_TIME_BATTERY_HISTORY_PLACEHOLDER")) {
                                entry.setValue(map);
                            }
                        }
                    }
                    DataProcessManager dataProcessManager2 = this.this$0;
                    dataProcessManager2.mIsCurrentBatteryHistoryLoaded = true;
                    dataProcessManager2.tryToGenerateFinalDataAndApplyCallback();
                    break;
                case 2:
                    List list2 = (List) obj;
                    if (list2 == null || list2.isEmpty()) {
                        Log.d("DataProcessManager", "databaseAppUsageList is null or empty");
                    } else {
                        ((ArrayList) this.this$0.mAppUsageEventList).addAll(list2);
                    }
                    DataProcessManager dataProcessManager3 = this.this$0;
                    dataProcessManager3.mIsDatabaseAppUsageLoaded = true;
                    DataProcessManager.m934$$Nest$mtryToProcessAppUsageData(dataProcessManager3);
                    break;
                case 3:
                    List list3 = (List) obj;
                    if (list3 == null || list3.isEmpty()) {
                        Log.d("DataProcessManager", "batteryEventList is null or empty");
                    } else {
                        ((ArrayList) this.this$0.mBatteryEventList).clear();
                        ((ArrayList) this.this$0.mBatteryEventList).addAll(list3);
                    }
                    DataProcessManager dataProcessManager4 = this.this$0;
                    dataProcessManager4.mIsBatteryEventLoaded = true;
                    DataProcessManager.m934$$Nest$mtryToProcessAppUsageData(dataProcessManager4);
                    break;
                case 4:
                    List list4 = (List) obj;
                    if (list4 == null || list4.isEmpty()) {
                        Log.d("DataProcessManager", "batteryUsageSlotList is null or empty");
                    } else {
                        ((ArrayList) this.this$0.mBatteryUsageSlotList).clear();
                        ((ArrayList) this.this$0.mBatteryUsageSlotList).addAll(list4);
                    }
                    DataProcessManager dataProcessManager5 = this.this$0;
                    dataProcessManager5.mIsBatteryUsageSlotLoaded = true;
                    dataProcessManager5.tryToGenerateFinalDataAndApplyCallback();
                    break;
                case 5:
                    Map map3 = (Map) obj;
                    DataProcessManager dataProcessManager6 = this.this$0;
                    Handler handler = dataProcessManager6.mHandler;
                    if (handler != null && dataProcessManager6.mCallbackFunction != null) {
                        handler.post(
                                new DataProcessManager$6$$ExternalSyntheticLambda0(this, map3));
                        break;
                    }
                    break;
                default:
                    Map map4 = (Map) obj;
                    DataProcessManager dataProcessManager7 = this.this$0;
                    Handler handler2 = dataProcessManager7.mHandler;
                    if (handler2 != null && dataProcessManager7.mCallbackFunction != null) {
                        handler2.post(
                                new DataProcessManager$6$$ExternalSyntheticLambda0(
                                        this, map4, (byte) 0));
                        break;
                    }
                    break;
            }
        }
    }

    /* renamed from: -$$Nest$mgetSystemAppsPackageNames, reason: not valid java name */
    public static Set m931$$Nest$mgetSystemAppsPackageNames(DataProcessManager dataProcessManager) {
        Set set;
        synchronized (dataProcessManager) {
            try {
                if (dataProcessManager.mSystemAppsPackageNames == null) {
                    Context context = dataProcessManager.mContext;
                    Set<String> set2 = DataProcessor.sTestSystemAppsPackageNames;
                    if (set2 == null) {
                        set2 =
                                AppListRepositoryUtil.getSystemPackageNames(
                                        context, context.getUserId());
                    }
                    dataProcessManager.mSystemAppsPackageNames = set2;
                }
                set = dataProcessManager.mSystemAppsPackageNames;
            } catch (Throwable th) {
                throw th;
            }
        }
        return set;
    }

    /* renamed from: -$$Nest$mgetSystemAppsUids, reason: not valid java name */
    public static Set m932$$Nest$mgetSystemAppsUids(DataProcessManager dataProcessManager) {
        Set set;
        synchronized (dataProcessManager) {
            try {
                if (dataProcessManager.mSystemAppsUids == null) {
                    dataProcessManager.mSystemAppsUids =
                            DataProcessor.getSystemAppsUids(dataProcessManager.mContext);
                }
                set = dataProcessManager.mSystemAppsUids;
            } catch (Throwable th) {
                throw th;
            }
        }
        return set;
    }

    /* renamed from: -$$Nest$mshouldLoadAppUsageData, reason: not valid java name */
    public static boolean m933$$Nest$mshouldLoadAppUsageData(
            DataProcessManager dataProcessManager) {
        synchronized (dataProcessManager) {
            if (!dataProcessManager.mShowScreenOnTime) {
                return false;
            }
            UserIdsSeries userIdsSeries = dataProcessManager.mUserIdsSeries;
            int i = userIdsSeries.mCurrentUserId;
            UserManager userManager = userIdsSeries.mUserManager;
            if (userManager != null && userManager.isUserUnlocked(i)) {
                return true;
            }
            Log.d("DataProcessManager", "shouldLoadAppUsageData: false, current user is locked");
            dataProcessManager.mShowScreenOnTime = false;
            return false;
        }
    }

    /* renamed from: -$$Nest$mtryToProcessAppUsageData, reason: not valid java name */
    public static void m934$$Nest$mtryToProcessAppUsageData(DataProcessManager dataProcessManager) {
        ArrayMap arrayMap;
        List list;
        if (dataProcessManager.mIsCurrentAppUsageLoaded
                && dataProcessManager.mIsDatabaseAppUsageLoaded
                && dataProcessManager.mIsBatteryEventLoaded) {
            if (dataProcessManager.mShowScreenOnTime) {
                Context context = dataProcessManager.mContext;
                List list2 = dataProcessManager.mHourlyBatteryLevelsPerDay;
                List list3 = dataProcessManager.mAppUsageEventList;
                List list4 = dataProcessManager.mBatteryEventList;
                Map map = DataProcessor.EMPTY_BATTERY_MAP;
                ArrayList arrayList = (ArrayList) list3;
                if (arrayList.isEmpty()) {
                    Log.w("DataProcessor", "appUsageEventList is empty");
                    arrayMap = null;
                } else {
                    Collections.sort(arrayList, DataProcessor.APP_USAGE_EVENT_TIMESTAMP_COMPARATOR);
                    Collections.sort(list4, DataProcessor.BATTERY_EVENT_TIMESTAMP_COMPARATOR);
                    ArrayMap arrayMap2 = new ArrayMap();
                    int i = 0;
                    while (i < list2.size()) {
                        ArrayMap arrayMap3 = new ArrayMap();
                        arrayMap2.put(Integer.valueOf(i), arrayMap3);
                        if (list2.get(i) != null) {
                            List list5 =
                                    ((BatteryLevelData.PeriodBatteryLevelData) list2.get(i))
                                            .mTimestamps;
                            int i2 = 0;
                            while (i2 < list5.size() - 1) {
                                long longValue = ((Long) list5.get(i2)).longValue();
                                int i3 = i2 + 1;
                                long longValue2 = ((Long) list5.get(i3)).longValue();
                                long j = DatabaseUtils.USAGE_QUERY_BUFFER_HOURS;
                                long j2 = longValue - j;
                                long j3 = longValue2 + j;
                                ArrayList arrayList2 = new ArrayList();
                                Iterator it = arrayList.iterator();
                                while (true) {
                                    if (!it.hasNext()) {
                                        list = list5;
                                        break;
                                    }
                                    list = list5;
                                    AppUsageEvent appUsageEvent = (AppUsageEvent) it.next();
                                    long timestamp = appUsageEvent.getTimestamp();
                                    if (timestamp > j3) {
                                        break;
                                    }
                                    if (timestamp >= j2) {
                                        arrayList2.add(appUsageEvent);
                                    }
                                    list5 = list;
                                }
                                arrayMap3.put(
                                        Integer.valueOf(i2),
                                        DataProcessor.buildAppUsagePeriodList(
                                                context, arrayList2, list4, longValue, longValue2));
                                i2 = i3;
                                list5 = list;
                                context = context;
                            }
                        }
                        i++;
                        context = context;
                    }
                    arrayMap = arrayMap2;
                }
                dataProcessManager.mAppUsagePeriodMap = arrayMap;
            }
            dataProcessManager.tryToGenerateFinalDataAndApplyCallback();
        }
    }

    public DataProcessManager(
            Context context,
            Handler handler,
            UserIdsSeries userIdsSeries,
            long j,
            long j2,
            PowerUsageAdvanced$$ExternalSyntheticLambda1
                    powerUsageAdvanced$$ExternalSyntheticLambda1,
            List list,
            Map map) {
        this.mAppUsageEventList = new ArrayList();
        this.mBatteryEventList = new ArrayList();
        this.mBatteryUsageSlotList = new ArrayList();
        this.mIsCurrentBatteryHistoryLoaded = false;
        this.mIsCurrentAppUsageLoaded = false;
        this.mIsDatabaseAppUsageLoaded = false;
        this.mIsBatteryEventLoaded = false;
        this.mIsBatteryUsageSlotLoaded = false;
        this.mShowScreenOnTime = true;
        this.mSystemAppsPackageNames = null;
        this.mSystemAppsUids = null;
        this.mContext = context.getApplicationContext();
        this.mHandler = handler;
        this.mUserIdsSeries = userIdsSeries;
        this.mIsFromPeriodJob = false;
        this.mRawStartTimestamp = j;
        this.mLastFullChargeTimestamp = j2;
        this.mCallbackFunction = powerUsageAdvanced$$ExternalSyntheticLambda1;
        this.mHourlyBatteryLevelsPerDay = list;
        this.mBatteryHistoryMap = map;
    }

    @VisibleForTesting
    public List<AppUsageEvent> getAppUsageEventList() {
        return this.mAppUsageEventList;
    }

    @VisibleForTesting
    public Map<Integer, Map<Integer, Map<Long, Map<String, List<AppUsagePeriod>>>>>
            getAppUsagePeriodMap() {
        return this.mAppUsagePeriodMap;
    }

    @VisibleForTesting
    public boolean getIsBatteryEventLoaded() {
        return this.mIsBatteryEventLoaded;
    }

    @VisibleForTesting
    public boolean getIsCurrentAppUsageLoaded() {
        return this.mIsCurrentAppUsageLoaded;
    }

    @VisibleForTesting
    public boolean getIsCurrentBatteryHistoryLoaded() {
        return this.mIsCurrentBatteryHistoryLoaded;
    }

    @VisibleForTesting
    public boolean getIsDatabaseAppUsageLoaded() {
        return this.mIsDatabaseAppUsageLoaded;
    }

    @VisibleForTesting
    public boolean getShowScreenOnTime() {
        return this.mShowScreenOnTime;
    }

    public final void start() {
        if (this.mHourlyBatteryLevelsPerDay == null) {
            new AnonymousClass2(this, 5).execute(new Void[0]);
            return;
        }
        if (this.mIsFromPeriodJob) {
            this.mIsCurrentBatteryHistoryLoaded = true;
            this.mIsCurrentAppUsageLoaded = true;
            this.mIsBatteryUsageSlotLoaded = true;
        } else {
            new AnonymousClass2(this, 1).execute(new Void[0]);
            new AnonymousClass2(this, 0).execute(new Void[0]);
            UserIdsSeries userIdsSeries = this.mUserIdsSeries;
            UserManager userManager = userIdsSeries.mUserManager;
            if (userManager != null
                    && userManager.isMainUser()
                    && userIdsSeries.mPrivateUser == null
                    && userIdsSeries.mManagedProfileUser == null) {
                new AnonymousClass2(this, 4).execute(new Void[0]);
            } else {
                this.mIsBatteryUsageSlotLoaded = true;
            }
        }
        new AnonymousClass2(this, 2).execute(new Void[0]);
        new AnonymousClass2(this, 3).execute(new Void[0]);
    }

    public final void tryToGenerateFinalDataAndApplyCallback() {
        if (this.mIsCurrentBatteryHistoryLoaded
                && this.mIsCurrentAppUsageLoaded
                && this.mIsDatabaseAppUsageLoaded
                && this.mIsBatteryEventLoaded
                && this.mIsBatteryUsageSlotLoaded) {
            synchronized (this) {
                new AnonymousClass2(this, 6).execute(new Void[0]);
            }
        }
    }

    public DataProcessManager(
            Context context,
            Handler handler,
            UserIdsSeries userIdsSeries,
            PowerUsageAdvanced$$ExternalSyntheticLambda1
                    powerUsageAdvanced$$ExternalSyntheticLambda1) {
        this.mAppUsageEventList = new ArrayList();
        this.mBatteryEventList = new ArrayList();
        this.mBatteryUsageSlotList = new ArrayList();
        this.mIsCurrentBatteryHistoryLoaded = false;
        this.mIsCurrentAppUsageLoaded = false;
        this.mIsDatabaseAppUsageLoaded = false;
        this.mIsBatteryEventLoaded = false;
        this.mIsBatteryUsageSlotLoaded = false;
        this.mShowScreenOnTime = true;
        this.mSystemAppsPackageNames = null;
        this.mSystemAppsUids = null;
        this.mContext = context.getApplicationContext();
        this.mHandler = handler;
        this.mUserIdsSeries = userIdsSeries;
        this.mCallbackFunction = powerUsageAdvanced$$ExternalSyntheticLambda1;
        this.mIsFromPeriodJob = false;
        this.mRawStartTimestamp = 0L;
        this.mLastFullChargeTimestamp = 0L;
        this.mHourlyBatteryLevelsPerDay = null;
        this.mBatteryHistoryMap = null;
        this.mShowScreenOnTime = false;
    }
}
