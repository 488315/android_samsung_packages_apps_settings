package com.android.settings.notification.zen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.notification.NotificationBackend;
import com.android.settings.notification.zen.lifestyle.LifeStyleZenFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.core.lifecycle.Lifecycle;

import com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.samsung.android.settings.notification.app.AppNotificationTypeAdapter;
import com.samsung.android.settings.notification.app.AppNotificationTypeInfo;
import com.samsung.android.settings.notification.reminder.NotificationUtils;
import com.samsung.android.settings.notification.zen.SecZenModeBypassingAppsController;
import com.samsung.android.settings.widget.SecRecyclerViewPreference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ZenModeBypassingAppsSettings extends ZenModeSettingsBase
        implements AppNotificationTypeAdapter.ZenModeAllBypassingAddAppsListener {
    public static BixbyRoutineActionHandler RSHandler;
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.zen_mode_bypassing_apps);
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass2();
    public static ZenModeAllBypassingAddAppsController mAllBypassingAddAppsController;
    public static boolean mIsFromDnd;
    public UserManager mUm;
    public final int REQUEST_CODE_OK = 1;
    public ArrayList result = new ArrayList();
    public final NotificationBackend mNotificationBackend = new NotificationBackend();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.notification.zen.ZenModeBypassingAppsSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            return ZenModeBypassingAppsSettings.buildPreferenceControllers$2(context, null);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.notification.zen.ZenModeBypassingAppsSettings$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            int i;
            ArrayList arrayList = new ArrayList();
            try {
                i = NotificationBackend.sINM.getAppsBypassingDndCount(UserHandle.myUserId());
            } catch (Exception e) {
                Log.w("getAppsBypassingDndCount", "Error calling NoMan", e);
                i = 0;
            }
            String valueOf = String.valueOf(36313);
            String valueOf2 = String.valueOf(i);
            StatusData statusData = new StatusData();
            statusData.mStatusValue = valueOf2;
            statusData.mStatusKey = valueOf;
            arrayList.add(statusData);
            return arrayList;
        }
    }

    public static List buildPreferenceControllers$2(Context context, Lifecycle lifecycle) {
        ArrayList arrayList = new ArrayList();
        ZenModeAllBypassingAddAppsController zenModeAllBypassingAddAppsController =
                new ZenModeAllBypassingAddAppsController(context, lifecycle, mIsFromDnd);
        mAllBypassingAddAppsController = zenModeAllBypassingAddAppsController;
        arrayList.add(zenModeAllBypassingAddAppsController);
        arrayList.add(
                new SecZenModeBypassingAppsController(
                        context, "key_sec_zen_mode_bypassing_apps_dropdown_menu", mIsFromDnd));
        return arrayList;
    }

    public final ArrayList allowPackageNameList() {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        if (mIsFromDnd) {
            ArrayList dndException =
                    NotificationUtils.getDndException(
                            ((ZenModeSettingsBase) this).mContext, this.mBackend);
            while (i < dndException.size()) {
                arrayList.add(
                        ((AppNotificationTypeInfo) dndException.get(i)).mPackage
                                + ":"
                                + ((AppNotificationTypeInfo) dndException.get(i)).uId);
                i++;
            }
        } else {
            String[] split = BixbyRoutineActionHandler.app_exist_list.split(";");
            while (i < split.length) {
                arrayList.add(split[i]);
                i++;
            }
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.getApplication();
        }
        Bundle arguments = getArguments();
        if ((arguments != null ? arguments.getString("path") : null) != null) {
            mIsFromDnd = true;
        } else {
            mIsFromDnd = false;
        }
        Lifecycle settingsLifecycle = getSettingsLifecycle();
        new NotificationBackend();
        return buildPreferenceControllers$2(context, settingsLifecycle);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return (mIsFromDnd ? ZenModeSettings.class : LifeStyleZenFragment.class).getName();
    }

    @Override // com.android.settings.notification.zen.ZenModeSettingsBase,
              // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "ZenBypassingApps";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1588;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.zen_mode_bypassing_apps;
    }

    @Override // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == this.REQUEST_CODE_OK) {
            try {
                this.result = intent.getStringArrayListExtra("selected_apps");
                if (!mIsFromDnd) {
                    HashSet hashSet = new HashSet();
                    Iterator it = this.result.iterator();
                    while (it.hasNext()) {
                        hashSet.add((String) it.next());
                    }
                    RSHandler.getClass();
                    BixbyRoutineActionHandler.setApp_exist_list(hashSet);
                    return;
                }
                Iterator it2 = allowPackageNameList().iterator();
                while (it2.hasNext()) {
                    String str = (String) it2.next();
                    String str2 = str.split(":")[0];
                    int packageUidAsUser =
                            ((ZenModeSettingsBase) this)
                                    .mContext
                                    .getPackageManager()
                                    .getPackageUidAsUser(str2, Integer.parseInt(str.split(":")[1]));
                    this.mBackend.getClass();
                    ZenModeBackend.setAppBypassDnd(packageUidAsUser, str2, false);
                }
                Iterator it3 = this.result.iterator();
                while (it3.hasNext()) {
                    String str3 = (String) it3.next();
                    String str4 = str3.split(":")[0];
                    int packageUidAsUser2 =
                            ((ZenModeSettingsBase) this)
                                    .mContext
                                    .getPackageManager()
                                    .getPackageUidAsUser(
                                            str4, Integer.parseInt(str3.split(":")[1]));
                    this.mBackend.getClass();
                    ZenModeBackend.setAppBypassDnd(packageUidAsUser2, str4, true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.android.settings.notification.zen.ZenModeSettingsBase,
              // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        if (preferenceScreen == null) {
            return;
        }
        RSHandler = BixbyRoutineActionHandler.getInstance();
        ((SecRecyclerViewPreference) preferenceScreen.findPreference("zen_contact"))
                        .mAppNotificationTypeAdapter
                        .mZenModeAllBypassingAddAppsListener =
                this;
        this.mUm =
                (UserManager)
                        ((ZenModeSettingsBase) this).mContext.getSystemService(UserManager.class);
    }
}
