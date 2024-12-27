package com.android.settings.location;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.DeviceConfig;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.applications.appinfo.AppInfoDashboardFragment;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settingslib.location.RecentLocationApps;
import com.android.settingslib.widget.AppPreference;

import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RecentLocationRequestPreferenceController extends LocationBasePreferenceController {
    static final String KEY_SEE_ALL_BUTTON = "recent_location_requests_see_all_button";
    public static final int MAX_APPS = 3;
    private PreferenceCategory mCategoryRecentLocationRequests;
    RecentLocationApps mRecentLocationApps;
    private Preference mSeeAllButton;
    private int mType;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PackageEntryClickedListener implements Preference.OnPreferenceClickListener {
        public final DashboardFragment mFragment;
        public final String mPackage;
        public final UserHandle mUserHandle;

        public PackageEntryClickedListener(
                DashboardFragment dashboardFragment, String str, UserHandle userHandle) {
            this.mFragment = dashboardFragment;
            this.mPackage = str;
            this.mUserHandle = userHandle;
        }

        @Override // androidx.preference.Preference.OnPreferenceClickListener
        public final boolean onPreferenceClick(Preference preference) {
            Bundle bundle = new Bundle();
            bundle.putString("package", this.mPackage);
            DashboardFragment dashboardFragment = this.mFragment;
            SubSettingLauncher subSettingLauncher =
                    new SubSettingLauncher(dashboardFragment.getContext());
            String name = AppInfoDashboardFragment.class.getName();
            SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
            launchRequest.mDestinationName = name;
            launchRequest.mArguments = bundle;
            subSettingLauncher.setTitleRes(R.string.application_info_label, null);
            launchRequest.mUserHandle = this.mUserHandle;
            launchRequest.mSourceMetricsCategory = dashboardFragment.getMetricsCategory();
            subSettingLauncher.launch();
            return true;
        }
    }

    public RecentLocationRequestPreferenceController(Context context, String str) {
        super(context, str);
        this.mType = 7;
        this.mRecentLocationApps = new RecentLocationApps(context);
    }

    public static AppPreference createAppPreference(
            Context context,
            RecentLocationApps.Request request,
            DashboardFragment dashboardFragment) {
        AppPreference appPreference = new AppPreference(context);
        appPreference.setIcon(request.icon);
        appPreference.setTitle(request.label);
        appPreference.setOnPreferenceClickListener(
                new PackageEntryClickedListener(
                        dashboardFragment, request.packageName, request.userHandle));
        return appPreference;
    }

    public static boolean isRequestMatchesProfileType(
            UserManager userManager, RecentLocationApps.Request request, int i) {
        boolean isManagedProfile = userManager.isManagedProfile(request.userHandle.getIdentifier());
        if (!isManagedProfile || (i & 2) == 0) {
            return (isManagedProfile || (i & 1) == 0) ? false : true;
        }
        return true;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mCategoryRecentLocationRequests =
                (PreferenceCategory) preferenceScreen.findPreference(getPreferenceKey());
        Preference findPreference = preferenceScreen.findPreference(KEY_SEE_ALL_BUTTON);
        this.mSeeAllButton = findPreference;
        findPreference.setVisible(false);
        SemEmergencyManager.getInstance(this.mContext);
        Context context = this.mCategoryRecentLocationRequests.getContext();
        ArrayList arrayList = new ArrayList();
        UserManager userManager = UserManager.get(this.mContext);
        Iterator it =
                ((ArrayList)
                                this.mRecentLocationApps.getAppListSorted(
                                        DeviceConfig.getBoolean(
                                                        "privacy",
                                                        "location_indicators_small_enabled",
                                                        false)
                                                && Settings.Secure.getInt(
                                                                this.mContext.getContentResolver(),
                                                                "locationShowSystemOps",
                                                                0)
                                                        == 1))
                        .iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            RecentLocationApps.Request request = (RecentLocationApps.Request) it.next();
            if (isRequestMatchesProfileType(userManager, request, this.mType)) {
                arrayList.add(request);
                if (arrayList.size() == 3) {
                    this.mSeeAllButton.setVisible(true);
                    break;
                }
            }
        }
        if (arrayList.size() > 0) {
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                this.mCategoryRecentLocationRequests.addPreference(
                        createAppPreference(
                                context, (RecentLocationApps.Request) it2.next(), this.mFragment));
            }
        } else {
            AppPreference appPreference = new AppPreference(context);
            appPreference.setTitle(R.string.location_no_recent_apps);
            appPreference.setSingleLineTitle(false);
            appPreference.setSelectable(false);
            this.mCategoryRecentLocationRequests.addPreference(appPreference);
        }
        if (SemEmergencyManager.isEmergencyMode(this.mContext)) {
            this.mCategoryRecentLocationRequests.removeAll();
            this.mSeeAllButton.setVisible(false);
        }
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        SemEmergencyManager.getInstance(this.mContext);
        return !SemEmergencyManager.isEmergencyMode(this.mContext) ? 0 : 3;
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.location.LocationBasePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.location.LocationBasePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.location.LocationEnabler.LocationModeChangeListener
    public void onLocationModeChanged(int i, boolean z) {
        this.mCategoryRecentLocationRequests.setEnabled(this.mLocationEnabler.isEnabled(i));
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    public void setProfileType(int i) {
        this.mType = i;
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
