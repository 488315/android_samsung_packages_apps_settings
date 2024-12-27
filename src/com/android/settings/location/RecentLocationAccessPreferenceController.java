package com.android.settings.location;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.text.RelativeDateTimeFormatter;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.DeviceConfig;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settingslib.applications.RecentAppOpsAccess;
import com.android.settingslib.utils.StringUtil;
import com.android.settingslib.widget.AppPreference;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RecentLocationAccessPreferenceController extends LocationBasePreferenceController {
    public static final int MAX_APPS = 3;
    private boolean isEnabled;
    private PreferenceCategory mCategoryRecentLocationRequests;
    RecentAppOpsAccess mRecentLocationApps;
    private boolean mShowSystem;
    private boolean mSystemSettingChanged;
    private int mType;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PackageEntryClickedListener implements Preference.OnPreferenceClickListener {
        public final Context mContext;
        public final String mPackage;
        public final UserHandle mUserHandle;

        public PackageEntryClickedListener(Context context, String str, UserHandle userHandle) {
            this.mContext = context;
            this.mPackage = str;
            this.mUserHandle = userHandle;
        }

        @Override // androidx.preference.Preference.OnPreferenceClickListener
        public final boolean onPreferenceClick(Preference preference) {
            Intent intent = new Intent("android.intent.action.MANAGE_APP_PERMISSION");
            intent.setPackage(
                    this.mContext.getPackageManager().getPermissionControllerPackageName());
            intent.putExtra(
                    "android.intent.extra.PERMISSION_GROUP_NAME",
                    "android.permission-group.LOCATION");
            intent.putExtra("android.intent.extra.PACKAGE_NAME", this.mPackage);
            intent.putExtra("android.intent.extra.USER", this.mUserHandle);
            this.mContext.startActivity(intent);
            return true;
        }
    }

    public RecentLocationAccessPreferenceController(Context context, String str) {
        this(context, str, RecentAppOpsAccess.createForLocation(context));
    }

    public static AppPreference createAppPreference(
            Context context,
            RecentAppOpsAccess.Access access,
            DashboardFragment dashboardFragment) {
        AppPreference appPreference = new AppPreference(context);
        appPreference.setIcon(access.icon);
        appPreference.setTitle(access.label);
        appPreference.setSummary(
                StringUtil.formatRelativeTime(
                        context,
                        System.currentTimeMillis() - access.accessFinishTime,
                        false,
                        RelativeDateTimeFormatter.Style.LONG));
        appPreference.setOnPreferenceClickListener(
                new PackageEntryClickedListener(
                        dashboardFragment.getContext(), access.packageName, access.userHandle));
        return appPreference;
    }

    public static boolean isRequestMatchesProfileType(
            UserManager userManager, RecentAppOpsAccess.Access access, int i) {
        boolean isManagedProfile = userManager.isManagedProfile(access.userHandle.getIdentifier());
        if (!isManagedProfile || (i & 2) == 0) {
            return (isManagedProfile || (i & 1) == 0) ? false : true;
        }
        return true;
    }

    private void loadRecentAccesses() {
        this.mCategoryRecentLocationRequests.removeAll();
        Context context = this.mCategoryRecentLocationRequests.getContext();
        ArrayList arrayList = new ArrayList();
        UserManager userManager = UserManager.get(this.mContext);
        List<RecentAppOpsAccess.Access> appList =
                this.mRecentLocationApps.getAppList(this.mShowSystem);
        Collections.sort(
                appList, Collections.reverseOrder(new RecentAppOpsAccess.AnonymousClass1()));
        for (RecentAppOpsAccess.Access access : appList) {
            if (isRequestMatchesProfileType(userManager, access, this.mType)) {
                arrayList.add(access);
                if (arrayList.size() == 3) {
                    break;
                }
            }
        }
        if (arrayList.size() <= 0) {
            LayoutPreference layoutPreference =
                    new LayoutPreference(this.mContext, R.layout.sec_recent_access_no_apps);
            layoutPreference.setSelectable(false);
            this.mCategoryRecentLocationRequests.addPreference(layoutPreference);
        } else {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                this.mCategoryRecentLocationRequests.addPreference(
                        createAppPreference(
                                context, (RecentAppOpsAccess.Access) it.next(), this.mFragment));
            }
        }
    }

    public void clearPreferenceList() {
        PreferenceCategory preferenceCategory = this.mCategoryRecentLocationRequests;
        if (preferenceCategory != null) {
            preferenceCategory.removeAll();
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mCategoryRecentLocationRequests =
                (PreferenceCategory) preferenceScreen.findPreference(getPreferenceKey());
        this.mLocationEnabler.refreshLocationMode();
        loadRecentAccesses();
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.isEnabled ? 0 : 2;
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
        boolean isEnabled = this.mLocationEnabler.isEnabled(i);
        if (SemEmergencyManager.isEmergencyMode(this.mContext)) {
            isEnabled = false;
        }
        this.mCategoryRecentLocationRequests.setVisible(isEnabled);
        if (!this.isEnabled && isEnabled) {
            this.mSystemSettingChanged = true;
            updateState(null);
        }
        this.isEnabled = isEnabled;
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

    public void updateShowSystem() {
        this.mSystemSettingChanged = true;
        clearPreferenceList();
        loadRecentAccesses();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        if (this.mSystemSettingChanged) {
            loadRecentAccesses();
            this.mSystemSettingChanged = false;
        }
    }

    @Override // com.android.settings.location.LocationBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public RecentLocationAccessPreferenceController(
            Context context, String str, RecentAppOpsAccess recentAppOpsAccess) {
        super(context, str);
        this.mType = 7;
        boolean z = false;
        this.mShowSystem = false;
        this.mSystemSettingChanged = false;
        this.isEnabled = true;
        this.mRecentLocationApps = recentAppOpsAccess;
        if (DeviceConfig.getBoolean("privacy", "location_indicators_small_enabled", false)
                && Settings.Secure.getInt(
                                this.mContext.getContentResolver(), "locationShowSystemOps", 0)
                        == 1) {
            z = true;
        }
        this.mShowSystem = z;
    }
}
