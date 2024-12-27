package com.android.settings.applications;

import android.content.pm.ApplicationInfo;
import android.telephony.SmsManager;

import com.android.settingslib.applications.ApplicationsState;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppStateSmsPremBridge extends AppStateBaseBridge {
    public static final AnonymousClass1 FILTER_APP_PREMIUM_SMS = new AnonymousClass1();
    public final SmsManager mSmsManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SmsState {
        public int smsState;
    }

    public AppStateSmsPremBridge(
            ApplicationsState applicationsState, AppStateBaseBridge.Callback callback) {
        super(applicationsState, callback);
        this.mSmsManager = SmsManager.getDefault();
    }

    @Override // com.android.settings.applications.AppStateBaseBridge
    public final void loadAllExtraInfo() {
        ArrayList allApps = this.mAppSession.getAllApps();
        int size = allApps.size();
        for (int i = 0; i < size; i++) {
            ApplicationsState.AppEntry appEntry = (ApplicationsState.AppEntry) allApps.get(i);
            ApplicationInfo applicationInfo = appEntry.info;
            updateExtraInfo(appEntry, applicationInfo.packageName, applicationInfo.uid);
        }
    }

    @Override // com.android.settings.applications.AppStateBaseBridge
    public final void updateExtraInfo(ApplicationsState.AppEntry appEntry, String str, int i) {
        SmsState smsState = new SmsState();
        smsState.smsState = this.mSmsManager.getPremiumSmsConsent(str);
        appEntry.extraInfo = smsState;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.applications.AppStateSmsPremBridge$1, reason: invalid class name */
    public final class AnonymousClass1 implements ApplicationsState.AppFilter {
        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public final boolean filterApp(ApplicationsState.AppEntry appEntry) {
            Object obj = appEntry.extraInfo;
            return (obj instanceof SmsState) && ((SmsState) obj).smsState != 0;
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public final void init() {}
    }
}
