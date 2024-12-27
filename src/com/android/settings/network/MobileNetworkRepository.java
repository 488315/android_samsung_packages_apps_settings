package com.android.settings.network;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.telephony.ims.ImsManager;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import androidx.room.MultiInstanceInvalidationService;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteStatement;

import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.network.telephony.MobileNetworkUtils;
import com.android.settings.network.telephony.SubscriptionRepositoryKt;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.mobile.dataservice.MobileNetworkDatabase;
import com.android.settingslib.mobile.dataservice.MobileNetworkInfoDao_Impl;
import com.android.settingslib.mobile.dataservice.MobileNetworkInfoDao_Impl$$ExternalSyntheticLambda0;
import com.android.settingslib.mobile.dataservice.MobileNetworkInfoEntity;
import com.android.settingslib.mobile.dataservice.SubscriptionInfoDao_Impl;
import com.android.settingslib.mobile.dataservice.SubscriptionInfoDao_Impl$$ExternalSyntheticLambda1;
import com.android.settingslib.mobile.dataservice.UiccInfoDao_Impl;
import com.android.settingslib.mobile.dataservice.UiccInfoEntity;

import com.samsung.android.knox.accounts.Account;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class MobileNetworkRepository
        extends SubscriptionManager.OnSubscriptionsChangedListener {
    public static MobileNetworkRepository sInstance;
    public final AirplaneModeObserver mAirplaneModeObserver;
    public final Context mContext;
    public final DataRoamingObserver mDataRoamingObserver;
    public final SettingsMetricsFeatureProvider mMetricsFeatureProvider;
    public final MobileNetworkDatabase mMobileNetworkDatabase;
    public final MobileNetworkInfoDao_Impl mMobileNetworkInfoDao;
    public final SubscriptionInfoDao_Impl mSubscriptionInfoDao;
    public final SubscriptionManager mSubscriptionManager;
    public static final boolean DEBUG = Log.isLoggable("MobileNetworkRepository", 3);
    public static final ExecutorService sExecutor = Executors.newSingleThreadExecutor();
    public static final Map sCacheSubscriptionInfoEntityMap = new ArrayMap();
    public static final Map sCacheMobileNetworkInfoEntityMap = new ArrayMap();
    public static final Map sCacheUiccInfoEntityMap = new ArrayMap();
    public static final Collection sCallbacks = new CopyOnWriteArrayList();
    public static final Object sInstanceLock = new Object();
    public List mAvailableSubInfoEntityList = new ArrayList();
    public List mActiveSubInfoEntityList = new ArrayList();
    public List mUiccInfoEntityList = new ArrayList();
    public List mMobileNetworkInfoEntityList = new ArrayList();
    public int mPhysicalSlotIndex = -1;
    public int mLogicalSlotIndex = -1;
    public int mCardState = 1;
    public int mPortIndex = -1;
    public int mCardId = -2;
    public boolean mIsEuicc = false;
    public boolean mIsRemovable = false;
    public boolean mIsActive = false;
    public final Map mSubscriptionInfoMap = new ArrayMap();
    public final Map mTelephonyManagerMap = new HashMap();
    public final Map mTelephonyCallbackMap = new HashMap();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AirplaneModeObserver extends ContentObserver {
        public final Uri mAirplaneModeSettingUri;

        public AirplaneModeObserver(Handler handler) {
            super(handler);
            this.mAirplaneModeSettingUri = Settings.Global.getUriFor("airplane_mode_on");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            if (uri.equals(this.mAirplaneModeSettingUri)) {
                boolean isAirplaneModeOn = MobileNetworkRepository.this.isAirplaneModeOn();
                Iterator it =
                        ((CopyOnWriteArrayList) MobileNetworkRepository.sCallbacks).iterator();
                while (it.hasNext()) {
                    ((MobileNetworkCallback) it.next()).onAirplaneModeChanged(isAirplaneModeOn);
                }
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DataRoamingObserver extends ContentObserver {
        public final String mBaseField;
        public int mRegSubId;

        public DataRoamingObserver(Handler handler) {
            super(handler);
            this.mRegSubId = -1;
            this.mBaseField = "data_roaming";
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            TelephonyManager telephonyManager =
                    (TelephonyManager)
                            ((HashMap) MobileNetworkRepository.this.mTelephonyManagerMap)
                                    .get(Integer.valueOf(this.mRegSubId));
            if (telephonyManager == null) {
                return;
            }
            MobileNetworkRepository.sExecutor.execute(
                    new MobileNetworkRepository$$ExternalSyntheticLambda1(
                            1, this, telephonyManager));
            boolean isDataRoamingEnabled = telephonyManager.isDataRoamingEnabled();
            Iterator it = ((CopyOnWriteArrayList) MobileNetworkRepository.sCallbacks).iterator();
            while (it.hasNext()) {
                ((MobileNetworkCallback) it.next())
                        .onDataRoamingChanged(this.mRegSubId, isDataRoamingEnabled);
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PhoneCallStateTelephonyCallback extends TelephonyCallback
            implements TelephonyCallback.CallStateListener,
                    TelephonyCallback.UserMobileDataStateListener {
        public final int mSubId;

        public PhoneCallStateTelephonyCallback(int i) {
            this.mSubId = i;
        }

        @Override // android.telephony.TelephonyCallback.CallStateListener
        public final void onCallStateChanged(int i) {
            Iterator it = ((CopyOnWriteArrayList) MobileNetworkRepository.sCallbacks).iterator();
            while (it.hasNext()) {
                ((MobileNetworkCallback) it.next()).onCallStateChanged(i);
            }
        }

        @Override // android.telephony.TelephonyCallback.UserMobileDataStateListener
        public final void onUserMobileDataStateChanged(boolean z) {
            Preference$$ExternalSyntheticOutline0.m(
                    RowView$$ExternalSyntheticOutline0.m(
                            "onUserMobileDataStateChanged enabled ", " on SUB ", z),
                    this.mSubId,
                    "MobileNetworkRepository");
            MobileNetworkRepository.sExecutor.execute(
                    new MobileNetworkRepository$$ExternalSyntheticLambda5(1, this));
        }
    }

    public MobileNetworkRepository(Context context) {
        this.mContext = context;
        synchronized (MobileNetworkDatabase.sLOCK) {
            try {
                if (Objects.isNull(MobileNetworkDatabase.sInstance)) {
                    Log.d("MobileNetworkDatabase", "createDatabase.");
                    Intrinsics.checkNotNullParameter(context, "context");
                    RoomDatabase.Builder builder =
                            new RoomDatabase.Builder(context, MobileNetworkDatabase.class, null);
                    builder.requireMigration = false;
                    builder.allowDestructiveMigrationOnDowngrade = true;
                    builder.multiInstanceInvalidationIntent =
                            builder.name != null
                                    ? new Intent(
                                            builder.context,
                                            (Class<?>) MultiInstanceInvalidationService.class)
                                    : null;
                    MobileNetworkDatabase.sInstance = (MobileNetworkDatabase) builder.build();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        MobileNetworkDatabase mobileNetworkDatabase = MobileNetworkDatabase.sInstance;
        this.mMobileNetworkDatabase = mobileNetworkDatabase;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        SettingsMetricsFeatureProvider metricsFeatureProvider =
                featureFactoryImpl.getMetricsFeatureProvider();
        this.mMetricsFeatureProvider = metricsFeatureProvider;
        metricsFeatureProvider.action(context, 1964, new Pair[0]);
        this.mSubscriptionManager =
                (SubscriptionManager) context.getSystemService(SubscriptionManager.class);
        this.mSubscriptionInfoDao = mobileNetworkDatabase.mSubscriptionInfoDao();
        mobileNetworkDatabase.mUiccInfoDao();
        this.mMobileNetworkInfoDao = mobileNetworkDatabase.mMobileNetworkInfoDao();
        this.mAirplaneModeObserver = new AirplaneModeObserver(new Handler(Looper.getMainLooper()));
        this.mDataRoamingObserver = new DataRoamingObserver(new Handler(Looper.getMainLooper()));
    }

    public static MobileNetworkRepository getInstance(Context context) {
        synchronized (sInstanceLock) {
            try {
                MobileNetworkRepository mobileNetworkRepository = sInstance;
                if (mobileNetworkRepository != null) {
                    return mobileNetworkRepository;
                }
                if (DEBUG) {
                    Log.d("MobileNetworkRepository", "Init the instance.");
                }
                MobileNetworkRepository mobileNetworkRepository2 =
                        new MobileNetworkRepository(context);
                sInstance = mobileNetworkRepository2;
                return mobileNetworkRepository2;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void addRegister(
            LifecycleOwner lifecycleOwner, MobileNetworkCallback mobileNetworkCallback, int i) {
        ArrayList arrayList;
        ArrayList arrayList2;
        boolean z = DEBUG;
        if (z) {
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    i, "addRegister by SUB ID ", "MobileNetworkRepository");
        }
        CopyOnWriteArrayList copyOnWriteArrayList = (CopyOnWriteArrayList) sCallbacks;
        if (copyOnWriteArrayList.isEmpty()) {
            this.mSubscriptionManager.addOnSubscriptionsChangedListener(
                    this.mContext.getMainExecutor(), this);
            AirplaneModeObserver airplaneModeObserver = this.mAirplaneModeObserver;
            Context context = this.mContext;
            airplaneModeObserver.getClass();
            context.getContentResolver()
                    .registerContentObserver(
                            airplaneModeObserver.mAirplaneModeSettingUri,
                            false,
                            airplaneModeObserver);
            Log.d("MobileNetworkRepository", "addRegister done");
        }
        copyOnWriteArrayList.add(mobileNetworkCallback);
        if (z) {
            Log.d("MobileNetworkRepository", "Observe subInfo.");
        }
        final SubscriptionInfoDao_Impl mSubscriptionInfoDao =
                this.mMobileNetworkDatabase.mSubscriptionInfoDao();
        mSubscriptionInfoDao.getClass();
        final RoomSQLiteQuery acquire =
                RoomSQLiteQuery.acquire(
                        0,
                        "SELECT * FROM subscriptionInfo ORDER BY  CASE WHEN simSlotIndex >= 0 THEN"
                            + " 1 ELSE 2 END , simSlotIndex");
        final int i2 = 1;
        mSubscriptionInfoDao
                .__db
                .getInvalidationTracker()
                .createLiveData(
                        new String[] {"subscriptionInfo"},
                        new Callable() { // from class:
                            // com.android.settingslib.mobile.dataservice.SubscriptionInfoDao_Impl.2
                            @Override // java.util.concurrent.Callable
                            public final Object call() {
                                String string;
                                int i3;
                                int i4;
                                boolean z2;
                                int i5;
                                boolean z3;
                                int i6;
                                boolean z4;
                                int i7;
                                boolean z5;
                                int i8;
                                boolean z6;
                                int i9;
                                boolean z7;
                                int i10;
                                boolean z8;
                                boolean z9;
                                Cursor query =
                                        DBUtil.query(SubscriptionInfoDao_Impl.this.__db, acquire);
                                try {
                                    int columnIndexOrThrow =
                                            CursorUtil.getColumnIndexOrThrow(query, "sudId");
                                    int columnIndexOrThrow2 =
                                            CursorUtil.getColumnIndexOrThrow(query, "simSlotIndex");
                                    int columnIndexOrThrow3 =
                                            CursorUtil.getColumnIndexOrThrow(query, "carrierId");
                                    int columnIndexOrThrow4 =
                                            CursorUtil.getColumnIndexOrThrow(
                                                    query, Account.DISPLAY_NAME);
                                    int columnIndexOrThrow5 =
                                            CursorUtil.getColumnIndexOrThrow(query, "carrierName");
                                    int columnIndexOrThrow6 =
                                            CursorUtil.getColumnIndexOrThrow(query, "dataRoaming");
                                    int columnIndexOrThrow7 =
                                            CursorUtil.getColumnIndexOrThrow(query, "mcc");
                                    int columnIndexOrThrow8 =
                                            CursorUtil.getColumnIndexOrThrow(query, "mnc");
                                    int columnIndexOrThrow9 =
                                            CursorUtil.getColumnIndexOrThrow(query, "countryIso");
                                    int columnIndexOrThrow10 =
                                            CursorUtil.getColumnIndexOrThrow(query, "isEmbedded");
                                    int columnIndexOrThrow11 =
                                            CursorUtil.getColumnIndexOrThrow(query, "cardId");
                                    int columnIndexOrThrow12 =
                                            CursorUtil.getColumnIndexOrThrow(query, "portIndex");
                                    int columnIndexOrThrow13 =
                                            CursorUtil.getColumnIndexOrThrow(
                                                    query, "isOpportunistic");
                                    int columnIndexOrThrow14 =
                                            CursorUtil.getColumnIndexOrThrow(query, "groupUUID");
                                    int columnIndexOrThrow15 =
                                            CursorUtil.getColumnIndexOrThrow(
                                                    query, "subscriptionType");
                                    int columnIndexOrThrow16 =
                                            CursorUtil.getColumnIndexOrThrow(query, "uniqueName");
                                    int columnIndexOrThrow17 =
                                            CursorUtil.getColumnIndexOrThrow(
                                                    query, "isSubscriptionVisible");
                                    int columnIndexOrThrow18 =
                                            CursorUtil.getColumnIndexOrThrow(
                                                    query, "getFormattedPhoneNumber");
                                    int columnIndexOrThrow19 =
                                            CursorUtil.getColumnIndexOrThrow(
                                                    query, "isFirstRemovableSubscription");
                                    int columnIndexOrThrow20 =
                                            CursorUtil.getColumnIndexOrThrow(
                                                    query, "isDefaultSubscriptionSelection");
                                    int columnIndexOrThrow21 =
                                            CursorUtil.getColumnIndexOrThrow(
                                                    query, "isValidSubscription");
                                    int columnIndexOrThrow22 =
                                            CursorUtil.getColumnIndexOrThrow(
                                                    query, "isUsableSubscription");
                                    int columnIndexOrThrow23 =
                                            CursorUtil.getColumnIndexOrThrow(
                                                    query, "isActiveSubscription");
                                    int columnIndexOrThrow24 =
                                            CursorUtil.getColumnIndexOrThrow(
                                                    query, "isAvailableSubscription");
                                    int columnIndexOrThrow25 =
                                            CursorUtil.getColumnIndexOrThrow(
                                                    query, "isActiveDataSubscriptionId");
                                    ArrayList arrayList3 = new ArrayList();
                                    while (query.moveToNext()) {
                                        String string2 =
                                                query.isNull(columnIndexOrThrow)
                                                        ? null
                                                        : query.getString(columnIndexOrThrow);
                                        int i11 = query.getInt(columnIndexOrThrow2);
                                        int i12 = query.getInt(columnIndexOrThrow3);
                                        String string3 =
                                                query.isNull(columnIndexOrThrow4)
                                                        ? null
                                                        : query.getString(columnIndexOrThrow4);
                                        String string4 =
                                                query.isNull(columnIndexOrThrow5)
                                                        ? null
                                                        : query.getString(columnIndexOrThrow5);
                                        int i13 = query.getInt(columnIndexOrThrow6);
                                        String string5 =
                                                query.isNull(columnIndexOrThrow7)
                                                        ? null
                                                        : query.getString(columnIndexOrThrow7);
                                        String string6 =
                                                query.isNull(columnIndexOrThrow8)
                                                        ? null
                                                        : query.getString(columnIndexOrThrow8);
                                        String string7 =
                                                query.isNull(columnIndexOrThrow9)
                                                        ? null
                                                        : query.getString(columnIndexOrThrow9);
                                        boolean z10 = query.getInt(columnIndexOrThrow10) != 0;
                                        int i14 = query.getInt(columnIndexOrThrow11);
                                        int i15 = query.getInt(columnIndexOrThrow12);
                                        boolean z11 = query.getInt(columnIndexOrThrow13) != 0;
                                        String string8 =
                                                query.isNull(columnIndexOrThrow14)
                                                        ? null
                                                        : query.getString(columnIndexOrThrow14);
                                        int i16 = columnIndexOrThrow;
                                        int i17 = columnIndexOrThrow15;
                                        int i18 = query.getInt(i17);
                                        int i19 = columnIndexOrThrow16;
                                        if (query.isNull(i19)) {
                                            columnIndexOrThrow16 = i19;
                                            i3 = columnIndexOrThrow17;
                                            string = null;
                                        } else {
                                            string = query.getString(i19);
                                            columnIndexOrThrow16 = i19;
                                            i3 = columnIndexOrThrow17;
                                        }
                                        if (query.getInt(i3) != 0) {
                                            columnIndexOrThrow17 = i3;
                                            i4 = columnIndexOrThrow18;
                                            z2 = true;
                                        } else {
                                            columnIndexOrThrow17 = i3;
                                            i4 = columnIndexOrThrow18;
                                            z2 = false;
                                        }
                                        String string9 =
                                                query.isNull(i4) ? null : query.getString(i4);
                                        columnIndexOrThrow18 = i4;
                                        int i20 = columnIndexOrThrow19;
                                        String str = string9;
                                        if (query.getInt(i20) != 0) {
                                            columnIndexOrThrow19 = i20;
                                            i5 = columnIndexOrThrow20;
                                            z3 = true;
                                        } else {
                                            columnIndexOrThrow19 = i20;
                                            i5 = columnIndexOrThrow20;
                                            z3 = false;
                                        }
                                        if (query.getInt(i5) != 0) {
                                            columnIndexOrThrow20 = i5;
                                            i6 = columnIndexOrThrow21;
                                            z4 = true;
                                        } else {
                                            columnIndexOrThrow20 = i5;
                                            i6 = columnIndexOrThrow21;
                                            z4 = false;
                                        }
                                        if (query.getInt(i6) != 0) {
                                            columnIndexOrThrow21 = i6;
                                            i7 = columnIndexOrThrow22;
                                            z5 = true;
                                        } else {
                                            columnIndexOrThrow21 = i6;
                                            i7 = columnIndexOrThrow22;
                                            z5 = false;
                                        }
                                        if (query.getInt(i7) != 0) {
                                            columnIndexOrThrow22 = i7;
                                            i8 = columnIndexOrThrow23;
                                            z6 = true;
                                        } else {
                                            columnIndexOrThrow22 = i7;
                                            i8 = columnIndexOrThrow23;
                                            z6 = false;
                                        }
                                        if (query.getInt(i8) != 0) {
                                            columnIndexOrThrow23 = i8;
                                            i9 = columnIndexOrThrow24;
                                            z7 = true;
                                        } else {
                                            columnIndexOrThrow23 = i8;
                                            i9 = columnIndexOrThrow24;
                                            z7 = false;
                                        }
                                        if (query.getInt(i9) != 0) {
                                            columnIndexOrThrow24 = i9;
                                            i10 = columnIndexOrThrow25;
                                            z8 = true;
                                        } else {
                                            columnIndexOrThrow24 = i9;
                                            i10 = columnIndexOrThrow25;
                                            z8 = false;
                                        }
                                        if (query.getInt(i10) != 0) {
                                            columnIndexOrThrow25 = i10;
                                            z9 = true;
                                        } else {
                                            columnIndexOrThrow25 = i10;
                                            z9 = false;
                                        }
                                        arrayList3.add(
                                                new SubscriptionInfoEntity(
                                                        string2, i11, i12, string3, string4, i13,
                                                        string5, string6, string7, z10, i14, i15,
                                                        z11, string8, i18, string, z2, str, z3, z4,
                                                        z5, z6, z7, z8, z9));
                                        columnIndexOrThrow = i16;
                                        columnIndexOrThrow15 = i17;
                                    }
                                    return arrayList3;
                                } finally {
                                    query.close();
                                }
                            }

                            public final void finalize() {
                                acquire.release();
                            }
                        })
                .observe(
                        lifecycleOwner,
                        new Observer(this) { // from class:
                            // com.android.settings.network.MobileNetworkRepository$$ExternalSyntheticLambda0
                            public final /* synthetic */ MobileNetworkRepository f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // androidx.lifecycle.Observer
                            public final void onChanged(Object obj) {
                                switch (i2) {
                                    case 0:
                                        List list = (List) obj;
                                        MobileNetworkRepository mobileNetworkRepository = this.f$0;
                                        mobileNetworkRepository.getClass();
                                        mobileNetworkRepository.mUiccInfoEntityList =
                                                new ArrayList(list);
                                        Iterator it =
                                                ((CopyOnWriteArrayList)
                                                                MobileNetworkRepository.sCallbacks)
                                                        .iterator();
                                        while (it.hasNext()) {
                                            ((MobileNetworkRepository.MobileNetworkCallback)
                                                            it.next())
                                                    .onAllUiccInfoChanged(list);
                                        }
                                        mobileNetworkRepository.mMetricsFeatureProvider.action(
                                                mobileNetworkRepository.mContext, 1972, 0);
                                        return;
                                    case 1:
                                        MobileNetworkRepository mobileNetworkRepository2 = this.f$0;
                                        List list2 = (List) obj;
                                        synchronized (mobileNetworkRepository2) {
                                            try {
                                                List list3 =
                                                        mobileNetworkRepository2
                                                                .mAvailableSubInfoEntityList;
                                                if (list3 != null
                                                        && ((ArrayList) list3).size()
                                                                == list2.size()
                                                        && mobileNetworkRepository2
                                                                .mAvailableSubInfoEntityList
                                                                .containsAll(list2)) {
                                                    Log.d(
                                                            "MobileNetworkRepository",
                                                            "onAvailableSubInfoChanged, duplicates"
                                                                + " = "
                                                                    + list2);
                                                    return;
                                                }
                                                mobileNetworkRepository2
                                                                .mAvailableSubInfoEntityList =
                                                        new ArrayList(list2);
                                                Log.d(
                                                        "MobileNetworkRepository",
                                                        "onAvailableSubInfoChanged,"
                                                            + " availableSubInfoEntityList = "
                                                                + list2);
                                                Iterator it2 =
                                                        ((CopyOnWriteArrayList)
                                                                        MobileNetworkRepository
                                                                                .sCallbacks)
                                                                .iterator();
                                                while (it2.hasNext()) {
                                                    ((MobileNetworkRepository.MobileNetworkCallback)
                                                                    it2.next())
                                                            .onAvailableSubInfoChanged(list2);
                                                }
                                                mobileNetworkRepository2.mMetricsFeatureProvider
                                                        .action(
                                                                mobileNetworkRepository2.mContext,
                                                                1971,
                                                                0);
                                                List list4 =
                                                        (List)
                                                                list2.stream()
                                                                        .filter(
                                                                                new MobileNetworkRepository$$ExternalSyntheticLambda9(
                                                                                        0))
                                                                        .filter(
                                                                                new MobileNetworkRepository$$ExternalSyntheticLambda9(
                                                                                        1))
                                                                        .collect(
                                                                                Collectors
                                                                                        .toList());
                                                Log.d(
                                                        "MobileNetworkRepository",
                                                        "onActiveSubInfoChanged,"
                                                            + " activeSubInfoEntityList = "
                                                                + list4);
                                                ArrayList arrayList3 = new ArrayList(list4);
                                                synchronized (mobileNetworkRepository2) {
                                                    mobileNetworkRepository2
                                                                    .mActiveSubInfoEntityList =
                                                            list4;
                                                }
                                                Iterator it3 =
                                                        ((CopyOnWriteArrayList)
                                                                        MobileNetworkRepository
                                                                                .sCallbacks)
                                                                .iterator();
                                                while (it3.hasNext()) {
                                                    ((MobileNetworkRepository.MobileNetworkCallback)
                                                                    it3.next())
                                                            .onActiveSubInfoChanged(arrayList3);
                                                }
                                                return;
                                            } finally {
                                            }
                                        }
                                    default:
                                        List list5 = (List) obj;
                                        MobileNetworkRepository mobileNetworkRepository3 = this.f$0;
                                        mobileNetworkRepository3.getClass();
                                        mobileNetworkRepository3.mMobileNetworkInfoEntityList =
                                                new ArrayList(list5);
                                        Iterator it4 =
                                                ((CopyOnWriteArrayList)
                                                                MobileNetworkRepository.sCallbacks)
                                                        .iterator();
                                        while (it4.hasNext()) {
                                            ((MobileNetworkRepository.MobileNetworkCallback)
                                                            it4.next())
                                                    .onAllMobileNetworkInfoChanged(list5);
                                        }
                                        mobileNetworkRepository3.mMetricsFeatureProvider.action(
                                                mobileNetworkRepository3.mContext, 1973, 0);
                                        return;
                                }
                            }
                        });
        if (z) {
            Log.d("MobileNetworkRepository", "Observe UICC info.");
        }
        final UiccInfoDao_Impl mUiccInfoDao = this.mMobileNetworkDatabase.mUiccInfoDao();
        mUiccInfoDao.getClass();
        final RoomSQLiteQuery acquire2 =
                RoomSQLiteQuery.acquire(0, "SELECT * FROM uiccInfo ORDER BY sudId");
        final int i3 = 0;
        mUiccInfoDao
                .__db
                .getInvalidationTracker()
                .createLiveData(
                        new String[] {"uiccInfo"},
                        new Callable() { // from class:
                            // com.android.settingslib.mobile.dataservice.UiccInfoDao_Impl.2
                            @Override // java.util.concurrent.Callable
                            public final Object call() {
                                Cursor query = DBUtil.query(UiccInfoDao_Impl.this.__db, acquire2);
                                try {
                                    int columnIndexOrThrow =
                                            CursorUtil.getColumnIndexOrThrow(query, "sudId");
                                    int columnIndexOrThrow2 =
                                            CursorUtil.getColumnIndexOrThrow(
                                                    query, "physicalSlotIndex");
                                    int columnIndexOrThrow3 =
                                            CursorUtil.getColumnIndexOrThrow(
                                                    query, "logicalSlotIndex");
                                    int columnIndexOrThrow4 =
                                            CursorUtil.getColumnIndexOrThrow(query, "cardId");
                                    int columnIndexOrThrow5 =
                                            CursorUtil.getColumnIndexOrThrow(query, "isEuicc");
                                    int columnIndexOrThrow6 =
                                            CursorUtil.getColumnIndexOrThrow(
                                                    query, "isMultipleEnabledProfilesSupported");
                                    int columnIndexOrThrow7 =
                                            CursorUtil.getColumnIndexOrThrow(query, "cardState");
                                    int columnIndexOrThrow8 =
                                            CursorUtil.getColumnIndexOrThrow(query, "isRemovable");
                                    int columnIndexOrThrow9 =
                                            CursorUtil.getColumnIndexOrThrow(query, "isActive");
                                    int columnIndexOrThrow10 =
                                            CursorUtil.getColumnIndexOrThrow(query, "portIndex");
                                    ArrayList arrayList3 = new ArrayList();
                                    while (query.moveToNext()) {
                                        arrayList3.add(
                                                new UiccInfoEntity(
                                                        query.isNull(columnIndexOrThrow)
                                                                ? null
                                                                : query.getString(
                                                                        columnIndexOrThrow),
                                                        query.isNull(columnIndexOrThrow2)
                                                                ? null
                                                                : query.getString(
                                                                        columnIndexOrThrow2),
                                                        query.getInt(columnIndexOrThrow3),
                                                        query.getInt(columnIndexOrThrow4),
                                                        query.getInt(columnIndexOrThrow5) != 0,
                                                        query.getInt(columnIndexOrThrow6) != 0,
                                                        query.getInt(columnIndexOrThrow7),
                                                        query.getInt(columnIndexOrThrow8) != 0,
                                                        query.getInt(columnIndexOrThrow9) != 0,
                                                        query.getInt(columnIndexOrThrow10)));
                                    }
                                    return arrayList3;
                                } finally {
                                    query.close();
                                }
                            }

                            public final void finalize() {
                                acquire2.release();
                            }
                        })
                .observe(
                        lifecycleOwner,
                        new Observer(this) { // from class:
                            // com.android.settings.network.MobileNetworkRepository$$ExternalSyntheticLambda0
                            public final /* synthetic */ MobileNetworkRepository f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // androidx.lifecycle.Observer
                            public final void onChanged(Object obj) {
                                switch (i3) {
                                    case 0:
                                        List list = (List) obj;
                                        MobileNetworkRepository mobileNetworkRepository = this.f$0;
                                        mobileNetworkRepository.getClass();
                                        mobileNetworkRepository.mUiccInfoEntityList =
                                                new ArrayList(list);
                                        Iterator it =
                                                ((CopyOnWriteArrayList)
                                                                MobileNetworkRepository.sCallbacks)
                                                        .iterator();
                                        while (it.hasNext()) {
                                            ((MobileNetworkRepository.MobileNetworkCallback)
                                                            it.next())
                                                    .onAllUiccInfoChanged(list);
                                        }
                                        mobileNetworkRepository.mMetricsFeatureProvider.action(
                                                mobileNetworkRepository.mContext, 1972, 0);
                                        return;
                                    case 1:
                                        MobileNetworkRepository mobileNetworkRepository2 = this.f$0;
                                        List list2 = (List) obj;
                                        synchronized (mobileNetworkRepository2) {
                                            try {
                                                List list3 =
                                                        mobileNetworkRepository2
                                                                .mAvailableSubInfoEntityList;
                                                if (list3 != null
                                                        && ((ArrayList) list3).size()
                                                                == list2.size()
                                                        && mobileNetworkRepository2
                                                                .mAvailableSubInfoEntityList
                                                                .containsAll(list2)) {
                                                    Log.d(
                                                            "MobileNetworkRepository",
                                                            "onAvailableSubInfoChanged, duplicates"
                                                                + " = "
                                                                    + list2);
                                                    return;
                                                }
                                                mobileNetworkRepository2
                                                                .mAvailableSubInfoEntityList =
                                                        new ArrayList(list2);
                                                Log.d(
                                                        "MobileNetworkRepository",
                                                        "onAvailableSubInfoChanged,"
                                                            + " availableSubInfoEntityList = "
                                                                + list2);
                                                Iterator it2 =
                                                        ((CopyOnWriteArrayList)
                                                                        MobileNetworkRepository
                                                                                .sCallbacks)
                                                                .iterator();
                                                while (it2.hasNext()) {
                                                    ((MobileNetworkRepository.MobileNetworkCallback)
                                                                    it2.next())
                                                            .onAvailableSubInfoChanged(list2);
                                                }
                                                mobileNetworkRepository2.mMetricsFeatureProvider
                                                        .action(
                                                                mobileNetworkRepository2.mContext,
                                                                1971,
                                                                0);
                                                List list4 =
                                                        (List)
                                                                list2.stream()
                                                                        .filter(
                                                                                new MobileNetworkRepository$$ExternalSyntheticLambda9(
                                                                                        0))
                                                                        .filter(
                                                                                new MobileNetworkRepository$$ExternalSyntheticLambda9(
                                                                                        1))
                                                                        .collect(
                                                                                Collectors
                                                                                        .toList());
                                                Log.d(
                                                        "MobileNetworkRepository",
                                                        "onActiveSubInfoChanged,"
                                                            + " activeSubInfoEntityList = "
                                                                + list4);
                                                ArrayList arrayList3 = new ArrayList(list4);
                                                synchronized (mobileNetworkRepository2) {
                                                    mobileNetworkRepository2
                                                                    .mActiveSubInfoEntityList =
                                                            list4;
                                                }
                                                Iterator it3 =
                                                        ((CopyOnWriteArrayList)
                                                                        MobileNetworkRepository
                                                                                .sCallbacks)
                                                                .iterator();
                                                while (it3.hasNext()) {
                                                    ((MobileNetworkRepository.MobileNetworkCallback)
                                                                    it3.next())
                                                            .onActiveSubInfoChanged(arrayList3);
                                                }
                                                return;
                                            } finally {
                                            }
                                        }
                                    default:
                                        List list5 = (List) obj;
                                        MobileNetworkRepository mobileNetworkRepository3 = this.f$0;
                                        mobileNetworkRepository3.getClass();
                                        mobileNetworkRepository3.mMobileNetworkInfoEntityList =
                                                new ArrayList(list5);
                                        Iterator it4 =
                                                ((CopyOnWriteArrayList)
                                                                MobileNetworkRepository.sCallbacks)
                                                        .iterator();
                                        while (it4.hasNext()) {
                                            ((MobileNetworkRepository.MobileNetworkCallback)
                                                            it4.next())
                                                    .onAllMobileNetworkInfoChanged(list5);
                                        }
                                        mobileNetworkRepository3.mMetricsFeatureProvider.action(
                                                mobileNetworkRepository3.mContext, 1973, 0);
                                        return;
                                }
                            }
                        });
        Log.d("MobileNetworkRepository", "Observe mobile network info.");
        final MobileNetworkInfoDao_Impl mMobileNetworkInfoDao =
                this.mMobileNetworkDatabase.mMobileNetworkInfoDao();
        mMobileNetworkInfoDao.getClass();
        final RoomSQLiteQuery acquire3 =
                RoomSQLiteQuery.acquire(0, "SELECT * FROM MobileNetworkInfo ORDER BY subId");
        final int i4 = 2;
        mMobileNetworkInfoDao
                .__db
                .getInvalidationTracker()
                .createLiveData(
                        new String[] {"MobileNetworkInfo"},
                        new Callable() { // from class:
                            // com.android.settingslib.mobile.dataservice.MobileNetworkInfoDao_Impl.2
                            @Override // java.util.concurrent.Callable
                            public final Object call() {
                                Cursor query =
                                        DBUtil.query(MobileNetworkInfoDao_Impl.this.__db, acquire3);
                                try {
                                    int columnIndexOrThrow =
                                            CursorUtil.getColumnIndexOrThrow(query, "subId");
                                    int columnIndexOrThrow2 =
                                            CursorUtil.getColumnIndexOrThrow(
                                                    query, "isContactDiscoveryEnabled");
                                    int columnIndexOrThrow3 =
                                            CursorUtil.getColumnIndexOrThrow(
                                                    query, "isContactDiscoveryVisible");
                                    int columnIndexOrThrow4 =
                                            CursorUtil.getColumnIndexOrThrow(
                                                    query, "isMobileDataEnabled");
                                    int columnIndexOrThrow5 =
                                            CursorUtil.getColumnIndexOrThrow(
                                                    query, "isCdmaOptions");
                                    int columnIndexOrThrow6 =
                                            CursorUtil.getColumnIndexOrThrow(query, "isGsmOptions");
                                    int columnIndexOrThrow7 =
                                            CursorUtil.getColumnIndexOrThrow(query, "isWorldMode");
                                    int columnIndexOrThrow8 =
                                            CursorUtil.getColumnIndexOrThrow(
                                                    query, "shouldDisplayNetworkSelectOptions");
                                    int columnIndexOrThrow9 =
                                            CursorUtil.getColumnIndexOrThrow(
                                                    query, "isTdscdmaSupported");
                                    int columnIndexOrThrow10 =
                                            CursorUtil.getColumnIndexOrThrow(
                                                    query, "activeNetworkIsCellular");
                                    int columnIndexOrThrow11 =
                                            CursorUtil.getColumnIndexOrThrow(
                                                    query, "showToggleForPhysicalSim");
                                    int columnIndexOrThrow12 =
                                            CursorUtil.getColumnIndexOrThrow(
                                                    query, "isDataRoamingEnabled");
                                    ArrayList arrayList3 = new ArrayList();
                                    while (query.moveToNext()) {
                                        arrayList3.add(
                                                new MobileNetworkInfoEntity(
                                                        query.isNull(columnIndexOrThrow)
                                                                ? null
                                                                : query.getString(
                                                                        columnIndexOrThrow),
                                                        query.getInt(columnIndexOrThrow2) != 0,
                                                        query.getInt(columnIndexOrThrow3) != 0,
                                                        query.getInt(columnIndexOrThrow4) != 0,
                                                        query.getInt(columnIndexOrThrow5) != 0,
                                                        query.getInt(columnIndexOrThrow6) != 0,
                                                        query.getInt(columnIndexOrThrow7) != 0,
                                                        query.getInt(columnIndexOrThrow8) != 0,
                                                        query.getInt(columnIndexOrThrow9) != 0,
                                                        query.getInt(columnIndexOrThrow10) != 0,
                                                        query.getInt(columnIndexOrThrow11) != 0,
                                                        query.getInt(columnIndexOrThrow12) != 0));
                                    }
                                    return arrayList3;
                                } finally {
                                    query.close();
                                }
                            }

                            public final void finalize() {
                                acquire3.release();
                            }
                        })
                .observe(
                        lifecycleOwner,
                        new Observer(this) { // from class:
                            // com.android.settings.network.MobileNetworkRepository$$ExternalSyntheticLambda0
                            public final /* synthetic */ MobileNetworkRepository f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // androidx.lifecycle.Observer
                            public final void onChanged(Object obj) {
                                switch (i4) {
                                    case 0:
                                        List list = (List) obj;
                                        MobileNetworkRepository mobileNetworkRepository = this.f$0;
                                        mobileNetworkRepository.getClass();
                                        mobileNetworkRepository.mUiccInfoEntityList =
                                                new ArrayList(list);
                                        Iterator it =
                                                ((CopyOnWriteArrayList)
                                                                MobileNetworkRepository.sCallbacks)
                                                        .iterator();
                                        while (it.hasNext()) {
                                            ((MobileNetworkRepository.MobileNetworkCallback)
                                                            it.next())
                                                    .onAllUiccInfoChanged(list);
                                        }
                                        mobileNetworkRepository.mMetricsFeatureProvider.action(
                                                mobileNetworkRepository.mContext, 1972, 0);
                                        return;
                                    case 1:
                                        MobileNetworkRepository mobileNetworkRepository2 = this.f$0;
                                        List list2 = (List) obj;
                                        synchronized (mobileNetworkRepository2) {
                                            try {
                                                List list3 =
                                                        mobileNetworkRepository2
                                                                .mAvailableSubInfoEntityList;
                                                if (list3 != null
                                                        && ((ArrayList) list3).size()
                                                                == list2.size()
                                                        && mobileNetworkRepository2
                                                                .mAvailableSubInfoEntityList
                                                                .containsAll(list2)) {
                                                    Log.d(
                                                            "MobileNetworkRepository",
                                                            "onAvailableSubInfoChanged, duplicates"
                                                                + " = "
                                                                    + list2);
                                                    return;
                                                }
                                                mobileNetworkRepository2
                                                                .mAvailableSubInfoEntityList =
                                                        new ArrayList(list2);
                                                Log.d(
                                                        "MobileNetworkRepository",
                                                        "onAvailableSubInfoChanged,"
                                                            + " availableSubInfoEntityList = "
                                                                + list2);
                                                Iterator it2 =
                                                        ((CopyOnWriteArrayList)
                                                                        MobileNetworkRepository
                                                                                .sCallbacks)
                                                                .iterator();
                                                while (it2.hasNext()) {
                                                    ((MobileNetworkRepository.MobileNetworkCallback)
                                                                    it2.next())
                                                            .onAvailableSubInfoChanged(list2);
                                                }
                                                mobileNetworkRepository2.mMetricsFeatureProvider
                                                        .action(
                                                                mobileNetworkRepository2.mContext,
                                                                1971,
                                                                0);
                                                List list4 =
                                                        (List)
                                                                list2.stream()
                                                                        .filter(
                                                                                new MobileNetworkRepository$$ExternalSyntheticLambda9(
                                                                                        0))
                                                                        .filter(
                                                                                new MobileNetworkRepository$$ExternalSyntheticLambda9(
                                                                                        1))
                                                                        .collect(
                                                                                Collectors
                                                                                        .toList());
                                                Log.d(
                                                        "MobileNetworkRepository",
                                                        "onActiveSubInfoChanged,"
                                                            + " activeSubInfoEntityList = "
                                                                + list4);
                                                ArrayList arrayList3 = new ArrayList(list4);
                                                synchronized (mobileNetworkRepository2) {
                                                    mobileNetworkRepository2
                                                                    .mActiveSubInfoEntityList =
                                                            list4;
                                                }
                                                Iterator it3 =
                                                        ((CopyOnWriteArrayList)
                                                                        MobileNetworkRepository
                                                                                .sCallbacks)
                                                                .iterator();
                                                while (it3.hasNext()) {
                                                    ((MobileNetworkRepository.MobileNetworkCallback)
                                                                    it3.next())
                                                            .onActiveSubInfoChanged(arrayList3);
                                                }
                                                return;
                                            } finally {
                                            }
                                        }
                                    default:
                                        List list5 = (List) obj;
                                        MobileNetworkRepository mobileNetworkRepository3 = this.f$0;
                                        mobileNetworkRepository3.getClass();
                                        mobileNetworkRepository3.mMobileNetworkInfoEntityList =
                                                new ArrayList(list5);
                                        Iterator it4 =
                                                ((CopyOnWriteArrayList)
                                                                MobileNetworkRepository.sCallbacks)
                                                        .iterator();
                                        while (it4.hasNext()) {
                                            ((MobileNetworkRepository.MobileNetworkCallback)
                                                            it4.next())
                                                    .onAllMobileNetworkInfoChanged(list5);
                                        }
                                        mobileNetworkRepository3.mMetricsFeatureProvider.action(
                                                mobileNetworkRepository3.mContext, 1973, 0);
                                        return;
                                }
                            }
                        });
        if (i != -1) {
            createTelephonyManagerBySubId(i);
            DataRoamingObserver dataRoamingObserver = this.mDataRoamingObserver;
            Context context2 = this.mContext;
            dataRoamingObserver.mRegSubId = i;
            String str = dataRoamingObserver.mBaseField;
            MobileNetworkRepository.this.createTelephonyManagerBySubId(i);
            if (((TelephonyManager)
                                    ((HashMap) MobileNetworkRepository.this.mTelephonyManagerMap)
                                            .get(Integer.valueOf(i)))
                            .getSimCount()
                    != 1) {
                str = str + i;
            }
            context2.getContentResolver()
                    .registerContentObserver(
                            Settings.Global.getUriFor(str), false, dataRoamingObserver);
        }
        if (mobileNetworkCallback != null) {
            synchronized (this) {
                try {
                    arrayList =
                            this.mAvailableSubInfoEntityList != null
                                    ? new ArrayList(this.mAvailableSubInfoEntityList)
                                    : null;
                    arrayList2 =
                            this.mActiveSubInfoEntityList != null
                                    ? new ArrayList(this.mActiveSubInfoEntityList)
                                    : null;
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (arrayList != null) {
                mobileNetworkCallback.onAvailableSubInfoChanged(arrayList);
            }
            if (arrayList2 != null) {
                mobileNetworkCallback.onActiveSubInfoChanged(arrayList2);
            }
        }
    }

    public final void createTelephonyManagerBySubId(int i) {
        if (i != -1) {
            if (!((HashMap) this.mTelephonyCallbackMap).containsKey(Integer.valueOf(i))) {
                PhoneCallStateTelephonyCallback phoneCallStateTelephonyCallback =
                        new PhoneCallStateTelephonyCallback(i);
                TelephonyManager createForSubscriptionId =
                        ((TelephonyManager) this.mContext.getSystemService(TelephonyManager.class))
                                .createForSubscriptionId(i);
                createForSubscriptionId.registerTelephonyCallback(
                        this.mContext.getMainExecutor(), phoneCallStateTelephonyCallback);
                ((HashMap) this.mTelephonyCallbackMap)
                        .put(Integer.valueOf(i), phoneCallStateTelephonyCallback);
                ((HashMap) this.mTelephonyManagerMap)
                        .put(Integer.valueOf(i), createForSubscriptionId);
                return;
            }
        }
        if (DEBUG) {
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    i,
                    "createTelephonyManagerBySubId: directly return for subId = ",
                    "MobileNetworkRepository");
        }
    }

    public final void deleteAllInfoBySubId(final String str) {
        TelephonyManager telephonyManagerBySubId;
        DialogFragment$$ExternalSyntheticOutline0.m(
                "deleteAllInfoBySubId, subId = ", str, "MobileNetworkRepository");
        SubscriptionInfoDao_Impl mSubscriptionInfoDao =
                this.mMobileNetworkDatabase.mSubscriptionInfoDao();
        mSubscriptionInfoDao.getClass();
        DBUtil.performBlocking(
                mSubscriptionInfoDao.__db,
                false,
                true,
                new SubscriptionInfoDao_Impl$$ExternalSyntheticLambda1(str, 1));
        UiccInfoDao_Impl mUiccInfoDao = this.mMobileNetworkDatabase.mUiccInfoDao();
        mUiccInfoDao.getClass();
        DBUtil.performBlocking(
                mUiccInfoDao.__db,
                false,
                true,
                new Function1() { // from class:
                                  // com.android.settingslib.mobile.dataservice.UiccInfoDao_Impl$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        SQLiteStatement prepare =
                                ((SQLiteConnection) obj)
                                        .prepare("DELETE FROM uiccInfo WHERE sudId = ?");
                        String str2 = str;
                        try {
                            if (str2 == null) {
                                prepare.bindNull(1);
                            } else {
                                prepare.bindText(1, str2);
                            }
                            prepare.step();
                            prepare.close();
                            return null;
                        } catch (Throwable th) {
                            prepare.close();
                            throw th;
                        }
                    }
                });
        MobileNetworkInfoDao_Impl mMobileNetworkInfoDao =
                this.mMobileNetworkDatabase.mMobileNetworkInfoDao();
        mMobileNetworkInfoDao.getClass();
        DBUtil.performBlocking(
                mMobileNetworkInfoDao.__db,
                false,
                true,
                new MobileNetworkInfoDao_Impl$$ExternalSyntheticLambda0(str, 1));
        final int i = 0;
        ((ArrayList) this.mUiccInfoEntityList)
                .removeIf(
                        new Predicate() { // from class:
                                          // com.android.settings.network.MobileNetworkRepository$$ExternalSyntheticLambda7
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                int i2 = i;
                                String str2 = str;
                                switch (i2) {
                                    case 0:
                                        return ((UiccInfoEntity) obj).subId.equals(str2);
                                    default:
                                        return ((MobileNetworkInfoEntity) obj).subId.equals(str2);
                                }
                            }
                        });
        final int i2 = 1;
        ((ArrayList) this.mMobileNetworkInfoEntityList)
                .removeIf(
                        new Predicate() { // from class:
                                          // com.android.settings.network.MobileNetworkRepository$$ExternalSyntheticLambda7
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                int i22 = i2;
                                String str2 = str;
                                switch (i22) {
                                    case 0:
                                        return ((UiccInfoEntity) obj).subId.equals(str2);
                                    default:
                                        return ((MobileNetworkInfoEntity) obj).subId.equals(str2);
                                }
                            }
                        });
        int parseInt = Integer.parseInt(str);
        if (((HashMap) this.mTelephonyCallbackMap).containsKey(Integer.valueOf(parseInt))
                && (telephonyManagerBySubId = getTelephonyManagerBySubId(this.mContext, parseInt))
                        != null) {
            PhoneCallStateTelephonyCallback phoneCallStateTelephonyCallback =
                    (PhoneCallStateTelephonyCallback)
                            ((HashMap) this.mTelephonyCallbackMap).get(Integer.valueOf(parseInt));
            if (phoneCallStateTelephonyCallback != null) {
                telephonyManagerBySubId.unregisterTelephonyCallback(
                        phoneCallStateTelephonyCallback);
                ((HashMap) this.mTelephonyCallbackMap).remove(Integer.valueOf(parseInt));
            }
        }
        ((ArrayMap) this.mSubscriptionInfoMap).remove(Integer.valueOf(parseInt));
        ((HashMap) this.mTelephonyManagerMap).remove(Integer.valueOf(parseInt));
        ((ArrayMap) sCacheSubscriptionInfoEntityMap).remove(Integer.valueOf(parseInt));
        ((ArrayMap) sCacheUiccInfoEntityMap).remove(Integer.valueOf(parseInt));
        ((ArrayMap) sCacheMobileNetworkInfoEntityMap).remove(Integer.valueOf(parseInt));
        this.mMetricsFeatureProvider.action(this.mContext, 1965, parseInt);
    }

    public final TelephonyManager getTelephonyManagerBySubId(Context context, int i) {
        TelephonyManager telephonyManager =
                (TelephonyManager) ((HashMap) this.mTelephonyManagerMap).get(Integer.valueOf(i));
        if (telephonyManager != null) {
            return telephonyManager;
        }
        if (context == null) {
            return telephonyManager;
        }
        TelephonyManager telephonyManager2 =
                (TelephonyManager) context.getSystemService(TelephonyManager.class);
        if (telephonyManager2 != null) {
            return telephonyManager2.createForSubscriptionId(i);
        }
        if (!DEBUG) {
            return telephonyManager2;
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i, "Can not get TelephonyManager for subId ", "MobileNetworkRepository");
        return telephonyManager2;
    }

    public final void insertMobileNetworkInfo(
            Context context, int i, TelephonyManager telephonyManager) {
        boolean z;
        boolean z2;
        if (telephonyManager != null) {
            z = telephonyManager.isDataEnabled();
            z2 = telephonyManager.isDataRoamingEnabled();
        } else {
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    i, "TelephonyManager is null, subId = ", "MobileNetworkRepository");
            z = false;
            z2 = false;
        }
        String valueOf = String.valueOf(i);
        Drawable drawable = MobileNetworkUtils.EMPTY_DRAWABLE;
        boolean isContactDiscoveryEnabled =
                MobileNetworkUtils.isContactDiscoveryEnabled(
                        (ImsManager) context.getSystemService(ImsManager.class), i);
        boolean isContactDiscoveryVisible =
                MobileNetworkUtils.isContactDiscoveryVisible(context, i);
        boolean isCdmaOptions = MobileNetworkUtils.isCdmaOptions(context, i);
        boolean isGsmOptions = MobileNetworkUtils.isGsmOptions(context, i);
        boolean isWorldMode = MobileNetworkUtils.isWorldMode(context, i);
        boolean shouldDisplayNetworkSelectOptions =
                MobileNetworkUtils.shouldDisplayNetworkSelectOptions(context, i);
        boolean isTdscdmaSupported = MobileNetworkUtils.isTdscdmaSupported(context, i);
        boolean activeNetworkIsCellular = MobileNetworkUtils.activeNetworkIsCellular(context);
        SubscriptionManager subscriptionManager = this.mSubscriptionManager;
        Pattern pattern = SubscriptionUtil.REGEX_DISPLAY_NAME_SUFFIX_PATTERN;
        MobileNetworkInfoEntity mobileNetworkInfoEntity =
                new MobileNetworkInfoEntity(
                        valueOf,
                        isContactDiscoveryEnabled,
                        isContactDiscoveryVisible,
                        z,
                        isCdmaOptions,
                        isGsmOptions,
                        isWorldMode,
                        shouldDisplayNetworkSelectOptions,
                        isTdscdmaSupported,
                        activeNetworkIsCellular,
                        subscriptionManager.canDisablePhysicalSubscription(),
                        z2);
        Log.d(
                "MobileNetworkRepository",
                "insertMobileNetworkInfo, mobileNetworkInfoEntity = " + mobileNetworkInfoEntity);
        ArrayMap arrayMap = (ArrayMap) sCacheMobileNetworkInfoEntityMap;
        if (arrayMap.containsKey(Integer.valueOf(i))
                && ((MobileNetworkInfoEntity) arrayMap.get(Integer.valueOf(i)))
                        .equals(mobileNetworkInfoEntity)) {
            return;
        }
        arrayMap.put(Integer.valueOf(i), mobileNetworkInfoEntity);
        MobileNetworkDatabase mobileNetworkDatabase = this.mMobileNetworkDatabase;
        final MobileNetworkInfoEntity[] mobileNetworkInfoEntityArr = {mobileNetworkInfoEntity};
        mobileNetworkDatabase.getClass();
        Log.d("MobileNetworkDatabase", "insertMobileNetworkInfo");
        final MobileNetworkInfoDao_Impl mMobileNetworkInfoDao =
                mobileNetworkDatabase.mMobileNetworkInfoDao();
        mMobileNetworkInfoDao.getClass();
        DBUtil.performBlocking(
                mMobileNetworkInfoDao.__db,
                false,
                true,
                new Function1() { // from class:
                                  // com.android.settingslib.mobile.dataservice.MobileNetworkInfoDao_Impl$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        MobileNetworkInfoDao_Impl.this.__insertAdapterOfMobileNetworkInfoEntity
                                .insert(
                                        (SQLiteConnection) obj,
                                        (Object[]) mobileNetworkInfoEntityArr);
                        return null;
                    }
                });
        this.mMetricsFeatureProvider.action(this.mContext, 1969, i);
    }

    public final boolean isAirplaneModeOn() {
        return Settings.Global.getInt(this.mContext.getContentResolver(), "airplane_mode_on", 0)
                != 0;
    }

    @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
    public final void onSubscriptionsChanged() {
        Context context = this.mContext;
        Pattern pattern = SubscriptionUtil.REGEX_DISPLAY_NAME_SUFFIX_PATTERN;
        sExecutor.execute(
                new MobileNetworkRepository$$ExternalSyntheticLambda1(
                        0,
                        this,
                        SubscriptionRepositoryKt.getSelectableSubscriptionInfoList(context)));
    }

    public final void removeRegister(MobileNetworkCallback mobileNetworkCallback) {
        Collection collection;
        synchronized (this) {
            collection = sCallbacks;
            ((CopyOnWriteArrayList) collection).remove(mobileNetworkCallback);
        }
        if (((CopyOnWriteArrayList) collection).isEmpty()) {
            this.mSubscriptionManager.removeOnSubscriptionsChangedListener(this);
            AirplaneModeObserver airplaneModeObserver = this.mAirplaneModeObserver;
            Context context = this.mContext;
            airplaneModeObserver.getClass();
            context.getContentResolver().unregisterContentObserver(airplaneModeObserver);
            DataRoamingObserver dataRoamingObserver = this.mDataRoamingObserver;
            Context context2 = this.mContext;
            dataRoamingObserver.getClass();
            context2.getContentResolver().unregisterContentObserver(dataRoamingObserver);
            ((HashMap) this.mTelephonyManagerMap)
                    .forEach(
                            new BiConsumer() { // from class:
                                               // com.android.settings.network.MobileNetworkRepository$$ExternalSyntheticLambda4
                                @Override // java.util.function.BiConsumer
                                public final void accept(Object obj, Object obj2) {
                                    TelephonyManager telephonyManager = (TelephonyManager) obj2;
                                    TelephonyCallback telephonyCallback =
                                            (TelephonyCallback)
                                                    ((HashMap)
                                                                    MobileNetworkRepository.this
                                                                            .mTelephonyCallbackMap)
                                                            .get((Integer) obj);
                                    if (telephonyCallback != null) {
                                        telephonyManager.unregisterTelephonyCallback(
                                                telephonyCallback);
                                    }
                                }
                            });
            ((HashMap) this.mTelephonyCallbackMap).clear();
            ((HashMap) this.mTelephonyManagerMap).clear();
            Log.d("MobileNetworkRepository", "removeRegister done");
        }
    }

    public final void updateEntity() {
        Map map = sCacheSubscriptionInfoEntityMap;
        if (map != null || !((ArrayMap) map).isEmpty()) {
            sExecutor.execute(new MobileNetworkRepository$$ExternalSyntheticLambda5(0, this));
        }
        boolean isAirplaneModeOn = isAirplaneModeOn();
        Iterator it = ((CopyOnWriteArrayList) sCallbacks).iterator();
        while (it.hasNext()) {
            ((MobileNetworkCallback) it.next()).onAirplaneModeChanged(isAirplaneModeOn);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface MobileNetworkCallback {
        void onAvailableSubInfoChanged(List list);

        default void onActiveSubInfoChanged(List list) {}

        default void onAirplaneModeChanged(boolean z) {}

        default void onAllMobileNetworkInfoChanged(List list) {}

        default void onAllUiccInfoChanged(List list) {}

        default void onCallStateChanged(int i) {}

        default void onDataRoamingChanged(int i, boolean z) {}
    }
}
