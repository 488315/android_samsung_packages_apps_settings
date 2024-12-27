package com.android.settings.applications;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.text.RelativeDateTimeFormatter;
import android.text.TextUtils;
import android.util.ArrayMap;

import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.spa.app.appinfo.AppInfoSettingsProvider;
import com.android.settingslib.applications.AppUtils;
import com.android.settingslib.applications.ApplicationsState;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.utils.StringUtil;
import com.android.settingslib.widget.AppPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AppsPreferenceController extends BasePreferenceController
        implements LifecycleObserver {
    static final String KEY_ALL_APP_INFO = "all_app_infos";
    static final String KEY_GENERAL_CATEGORY = "general_category";
    static final String KEY_RECENT_APPS_CATEGORY = "recent_apps_category";
    static final String KEY_SEE_ALL = "see_all_apps";
    public static final int SHOW_RECENT_APP_COUNT = 4;
    Preference mAllAppsInfoPref;
    private final ApplicationsState mApplicationsState;
    PreferenceCategory mGeneralCategory;
    private Fragment mHost;
    private boolean mInitialLaunch;
    List<RecentAppStatsMixin.UsageStatsWrapper> mRecentApps;
    PreferenceCategory mRecentAppsCategory;
    Preference mSeeAllPref;

    public AppsPreferenceController(Context context) {
        super(context, KEY_RECENT_APPS_CATEGORY);
        this.mInitialLaunch = false;
        this.mApplicationsState =
                ApplicationsState.getInstance((Application) this.mContext.getApplicationContext());
    }

    private void displayRecentApps() {
        boolean z;
        if (this.mRecentAppsCategory != null) {
            ArrayMap arrayMap = new ArrayMap();
            int preferenceCount = this.mRecentAppsCategory.getPreferenceCount();
            for (int i = 0; i < preferenceCount; i++) {
                Preference preference = this.mRecentAppsCategory.getPreference(i);
                String key = preference.getKey();
                if (!TextUtils.equals(key, KEY_SEE_ALL)) {
                    arrayMap.put(key, preference);
                }
            }
            int i2 = 0;
            for (RecentAppStatsMixin.UsageStatsWrapper usageStatsWrapper : this.mRecentApps) {
                final String packageName = usageStatsWrapper.mUsageStats.getPackageName();
                StringBuilder m =
                        EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(
                                packageName);
                int i3 = usageStatsWrapper.mUserId;
                m.append(i3);
                String sb = m.toString();
                final ApplicationsState.AppEntry entry =
                        this.mApplicationsState.getEntry(i3, packageName);
                if (entry != null) {
                    Preference preference2 = (Preference) arrayMap.remove(sb);
                    if (preference2 == null) {
                        preference2 = new AppPreference(this.mContext);
                        z = false;
                    } else {
                        z = true;
                    }
                    preference2.setKey(sb);
                    preference2.setTitle(entry.label);
                    preference2.setIcon(AppUtils.getIconWithoutCache(this.mContext, entry));
                    preference2.setSummary(
                            StringUtil.formatRelativeTime(
                                    this.mContext,
                                    System.currentTimeMillis() - r5.getLastTimeUsed(),
                                    false,
                                    RelativeDateTimeFormatter.Style.LONG));
                    int i4 = i2 + 1;
                    preference2.setOrder(i2);
                    preference2.setOnPreferenceClickListener(
                            new Preference
                                    .OnPreferenceClickListener() { // from class:
                                                                   // com.android.settings.applications.AppsPreferenceController$$ExternalSyntheticLambda0
                                @Override // androidx.preference.Preference.OnPreferenceClickListener
                                public final boolean onPreferenceClick(Preference preference3) {
                                    boolean lambda$displayRecentApps$0;
                                    lambda$displayRecentApps$0 =
                                            AppsPreferenceController.this
                                                    .lambda$displayRecentApps$0(
                                                            packageName, entry, preference3);
                                    return lambda$displayRecentApps$0;
                                }
                            });
                    if (!z) {
                        this.mRecentAppsCategory.addPreference(preference2);
                    }
                    i2 = i4;
                }
            }
            Iterator it = arrayMap.values().iterator();
            while (it.hasNext()) {
                this.mRecentAppsCategory.removePreference((Preference) it.next());
            }
        }
    }

    private void initPreferences(PreferenceScreen preferenceScreen) {
        this.mRecentAppsCategory =
                (PreferenceCategory) preferenceScreen.findPreference(KEY_RECENT_APPS_CATEGORY);
        this.mGeneralCategory =
                (PreferenceCategory) preferenceScreen.findPreference(KEY_GENERAL_CATEGORY);
        this.mAllAppsInfoPref = preferenceScreen.findPreference(KEY_ALL_APP_INFO);
        this.mSeeAllPref = preferenceScreen.findPreference(KEY_SEE_ALL);
        this.mRecentAppsCategory.setVisible(false);
        this.mGeneralCategory.setVisible(false);
        this.mAllAppsInfoPref.setVisible(false);
        this.mSeeAllPref.setVisible(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$displayRecentApps$0(
            String str, ApplicationsState.AppEntry appEntry, Preference preference) {
        AppInfoSettingsProvider.startAppInfoSettings(
                str, appEntry.info.uid, this.mHost, 1001, getMetricsCategory());
        return true;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        initPreferences(preferenceScreen);
        refreshUi();
        this.mInitialLaunch = true;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 1;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    public void loadAllAppsCount() {
        Context context = this.mContext;
        new InstalledAppCounter(
                context,
                context
                        .getPackageManager()) { // from class:
                                                // com.android.settings.applications.AppsPreferenceController.1
            @Override // com.android.settings.applications.AppCounter
            public final void onCountComplete(int i) {
                if (AppsPreferenceController.this.mRecentApps.isEmpty()) {
                    AppsPreferenceController appsPreferenceController =
                            AppsPreferenceController.this;
                    appsPreferenceController.mAllAppsInfoPref.setSummary(
                            ((AbstractPreferenceController) appsPreferenceController)
                                    .mContext.getString(R.string.apps_summary, Integer.valueOf(i)));
                } else {
                    AppsPreferenceController appsPreferenceController2 =
                            AppsPreferenceController.this;
                    appsPreferenceController2.mSeeAllPref.setTitle(
                            StringUtil.getIcuPluralsString(
                                    ((AbstractPreferenceController) appsPreferenceController2)
                                            .mContext,
                                    i,
                                    R.string.see_all_apps_title));
                }
            }
        }.execute(new Void[0]);
    }

    public List<RecentAppStatsMixin.UsageStatsWrapper> loadRecentApps() {
        RecentAppStatsMixin recentAppStatsMixin = new RecentAppStatsMixin(this.mContext);
        recentAppStatsMixin.loadDisplayableRecentApps(4);
        return recentAppStatsMixin.mRecentApps;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        this.mInitialLaunch = false;
    }

    public void refreshUi() {
        loadAllAppsCount();
        List<RecentAppStatsMixin.UsageStatsWrapper> loadRecentApps = loadRecentApps();
        this.mRecentApps = loadRecentApps;
        if (loadRecentApps.isEmpty()) {
            this.mAllAppsInfoPref.setVisible(true);
            this.mRecentAppsCategory.setVisible(false);
            this.mGeneralCategory.setVisible(false);
            this.mSeeAllPref.setVisible(false);
            return;
        }
        displayRecentApps();
        this.mAllAppsInfoPref.setVisible(false);
        this.mRecentAppsCategory.setVisible(true);
        this.mGeneralCategory.setVisible(true);
        this.mSeeAllPref.setVisible(true);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setFragment(Fragment fragment) {
        this.mHost = fragment;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (this.mInitialLaunch) {
            return;
        }
        refreshUi();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
