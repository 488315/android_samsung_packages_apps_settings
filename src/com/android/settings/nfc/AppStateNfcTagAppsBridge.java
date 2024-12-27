package com.android.settings.nfc;

import android.app.ActivityManager;
import android.content.pm.ApplicationInfo;
import android.nfc.NfcAdapter;
import android.os.UserHandle;
import android.os.UserManager;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.applications.AppStateBaseBridge;
import com.android.settings.applications.AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0;
import com.android.settingslib.applications.ApplicationsState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppStateNfcTagAppsBridge extends AppStateBaseBridge {
    public final NfcAdapter mNfcAdapter;
    public static final Map sList = new HashMap();
    public static final AnonymousClass1 FILTER_APPS_NFC_TAG = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class NfcTagAppState {
        public final boolean mIsAllowed;
        public final boolean mIsExisted;

        public NfcTagAppState(boolean z, boolean z2) {
            this.mIsExisted = z;
            this.mIsAllowed = z2;
        }
    }

    public AppStateNfcTagAppsBridge(
            FragmentActivity fragmentActivity,
            ApplicationsState applicationsState,
            AppStateBaseBridge.Callback callback) {
        super(applicationsState, callback);
        NfcAdapter defaultAdapter = NfcAdapter.getDefaultAdapter(fragmentActivity);
        this.mNfcAdapter = defaultAdapter;
        if (defaultAdapter == null || !defaultAdapter.isTagIntentAppPreferenceSupported()) {
            return;
        }
        Iterator it =
                ((UserManager)
                                fragmentActivity
                                        .createContextAsUser(
                                                UserHandle.of(ActivityManager.getCurrentUser()), 0)
                                        .getSystemService(UserManager.class))
                        .getEnabledProfiles()
                        .iterator();
        while (it.hasNext()) {
            int identifier = ((UserHandle) it.next()).getIdentifier();
            ((HashMap) sList)
                    .put(
                            Integer.valueOf(identifier),
                            this.mNfcAdapter.getTagIntentAppPreferenceForUser(identifier));
        }
    }

    @Override // com.android.settings.applications.AppStateBaseBridge
    public final void loadAllExtraInfo() {
        ArrayList allApps = this.mAppSession.getAllApps();
        for (int i = 0; i < allApps.size(); i++) {
            ApplicationsState.AppEntry appEntry = (ApplicationsState.AppEntry) allApps.get(i);
            ApplicationInfo applicationInfo = appEntry.info;
            updateExtraInfo(appEntry, applicationInfo.packageName, applicationInfo.uid);
        }
    }

    @Override // com.android.settings.applications.AppStateBaseBridge
    public final void updateExtraInfo(ApplicationsState.AppEntry appEntry, String str, int i) {
        int userId = UserHandle.getUserId(i);
        Map map = (Map) ((HashMap) sList).getOrDefault(Integer.valueOf(userId), new HashMap());
        if (map.containsKey(str)) {
            appEntry.extraInfo = new NfcTagAppState(true, ((Boolean) map.get(str)).booleanValue());
        } else {
            appEntry.extraInfo = new NfcTagAppState(false, false);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.nfc.AppStateNfcTagAppsBridge$1, reason: invalid class name */
    public final class AnonymousClass1 implements ApplicationsState.AppFilter {
        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public final boolean filterApp(ApplicationsState.AppEntry appEntry) {
            Object obj = appEntry.extraInfo;
            if (obj != null) {
                return ((NfcTagAppState) obj).mIsExisted;
            }
            AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0.m(
                    new StringBuilder("["),
                    appEntry.info.packageName,
                    "] has No extra info.",
                    "AppStateNfcTagAppsBridge");
            return false;
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public final void init() {}
    }
}
