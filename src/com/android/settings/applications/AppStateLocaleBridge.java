package com.android.settings.applications;

import android.content.Context;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.ArrayMap;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.Utils;
import com.android.settingslib.applications.ApplicationsState;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppStateLocaleBridge extends AppStateBaseBridge {
    public static final AnonymousClass1 FILTER_APPS_LOCALE = new AnonymousClass1();
    public final Context mContext;
    public final Map mUserIdToAppInfoByProfiles;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AppInfoByProfiles {
        public final Context mContextAsUser;
        public final List mListInfos;

        public AppInfoByProfiles(Context context, int i) {
            Context createContextAsUser = context.createContextAsUser(UserHandle.of(i), 0);
            this.mContextAsUser = createContextAsUser;
            this.mListInfos =
                    createContextAsUser
                            .getPackageManager()
                            .queryIntentActivities(AppLocaleUtil.LAUNCHER_ENTRY_INTENT, 128);
        }
    }

    public AppStateLocaleBridge(
            FragmentActivity fragmentActivity,
            ApplicationsState applicationsState,
            AppStateBaseBridge.Callback callback,
            UserManager userManager) {
        super(applicationsState, callback);
        this.mUserIdToAppInfoByProfiles = new ArrayMap();
        this.mContext = fragmentActivity;
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(fragmentActivity.getUserId()));
        int managedProfileId = Utils.getManagedProfileId(userManager, fragmentActivity.getUserId());
        if (managedProfileId != -10000) {
            arrayList.add(Integer.valueOf(managedProfileId));
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            int intValue = num.intValue();
            if (!((ArrayMap) this.mUserIdToAppInfoByProfiles).containsKey(num)) {
                ((ArrayMap) this.mUserIdToAppInfoByProfiles)
                        .put(num, new AppInfoByProfiles(this.mContext, intValue));
            }
        }
    }

    public final AppInfoByProfiles getAppInfo(int i) {
        if (((ArrayMap) this.mUserIdToAppInfoByProfiles).containsKey(Integer.valueOf(i))) {
            return (AppInfoByProfiles)
                    ((ArrayMap) this.mUserIdToAppInfoByProfiles).get(Integer.valueOf(i));
        }
        AppInfoByProfiles appInfoByProfiles = new AppInfoByProfiles(this.mContext, i);
        ((ArrayMap) this.mUserIdToAppInfoByProfiles).put(Integer.valueOf(i), appInfoByProfiles);
        return appInfoByProfiles;
    }

    @Override // com.android.settings.applications.AppStateBaseBridge
    public final void loadAllExtraInfo() {
        ArrayList allApps = this.mAppSession.getAllApps();
        for (int i = 0; i < allApps.size(); i++) {
            ApplicationsState.AppEntry appEntry = (ApplicationsState.AppEntry) allApps.get(i);
            AppInfoByProfiles appInfo = getAppInfo(UserHandle.getUserId(appEntry.info.uid));
            appEntry.extraInfo =
                    AppLocaleUtil.canDisplayLocaleUi(
                                    appInfo.mContextAsUser, appEntry.info, appInfo.mListInfos)
                            ? Boolean.TRUE
                            : Boolean.FALSE;
        }
    }

    @Override // com.android.settings.applications.AppStateBaseBridge
    public final void updateExtraInfo(ApplicationsState.AppEntry appEntry, String str, int i) {
        AppInfoByProfiles appInfo = getAppInfo(UserHandle.getUserId(i));
        appEntry.extraInfo =
                AppLocaleUtil.canDisplayLocaleUi(
                                appInfo.mContextAsUser, appEntry.info, appInfo.mListInfos)
                        ? Boolean.TRUE
                        : Boolean.FALSE;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.applications.AppStateLocaleBridge$1, reason: invalid class name */
    public final class AnonymousClass1 implements ApplicationsState.AppFilter {
        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public final boolean filterApp(ApplicationsState.AppEntry appEntry) {
            Object obj = appEntry.extraInfo;
            if (obj != null) {
                return obj == Boolean.TRUE;
            }
            AppStateClonedAppsBridge$1$$ExternalSyntheticOutline0.m(
                    new StringBuilder("["),
                    appEntry.info.packageName,
                    "] has No extra info.",
                    "AppStateLocaleBridge");
            return false;
        }

        @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
        public final void init() {}
    }
}
