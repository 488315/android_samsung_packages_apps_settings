package com.android.settingslib.mobile.dataservice;

import androidx.room.RoomDatabase;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class MobileNetworkDatabase extends RoomDatabase {
    public static MobileNetworkDatabase sInstance;
    public static final Object sLOCK = new Object();

    public abstract MobileNetworkInfoDao_Impl mMobileNetworkInfoDao();

    public abstract SubscriptionInfoDao_Impl mSubscriptionInfoDao();

    public abstract UiccInfoDao_Impl mUiccInfoDao();
}
