package com.android.settings.applications.appinfo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.widget.EntityHeaderController;
import com.android.settingslib.applications.AppUtils;
import com.android.settingslib.applications.ApplicationsState;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AppHeaderViewPreferenceController extends BasePreferenceController
        implements AppInfoDashboardFragment.Callback, LifecycleObserver {
    private static final String KEY_HEADER = "header_view";
    private EntityHeaderController mEntityHeaderController;
    private LayoutPreference mHeader;
    private final Lifecycle mLifecycle;
    private final String mPackageName;
    private final AppInfoDashboardFragment mParent;

    public AppHeaderViewPreferenceController(
            Context context,
            AppInfoDashboardFragment appInfoDashboardFragment,
            String str,
            Lifecycle lifecycle) {
        super(context, KEY_HEADER);
        this.mParent = appInfoDashboardFragment;
        this.mPackageName = str;
        this.mLifecycle = lifecycle;
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
    }

    public static int getInstallationStatus(ApplicationInfo applicationInfo) {
        return (applicationInfo.flags & 8388608) == 0
                ? R.string.not_installed
                : applicationInfo.enabled ? R.string.installed : R.string.disabled;
    }

    private void setAppLabelAndIcon(PackageInfo packageInfo, ApplicationsState.AppEntry appEntry) {
        this.mParent.getActivity();
        boolean isInstant = AppUtils.isInstant(packageInfo.applicationInfo);
        String string =
                isInstant
                        ? this.mContext.getString(R.string.install_type_instant)
                        : this.mContext.getString(getInstallationStatus(appEntry.info));
        EntityHeaderController entityHeaderController = this.mEntityHeaderController;
        entityHeaderController.setLabel(appEntry);
        entityHeaderController.mIcon =
                AppUtils.getIconWithoutCache(entityHeaderController.mAppContext, appEntry);
        entityHeaderController.mIsInstantApp = isInstant;
        entityHeaderController.mSummary = string;
        entityHeaderController.done(false);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mHeader = (LayoutPreference) preferenceScreen.findPreference(KEY_HEADER);
        EntityHeaderController entityHeaderController =
                new EntityHeaderController(
                        this.mParent.getActivity(),
                        this.mParent,
                        this.mHeader.mRootView.findViewById(R.id.entity_header));
        entityHeaderController.mPackageName = this.mPackageName;
        entityHeaderController.mAction1 = 0;
        entityHeaderController.mAction2 = 0;
        entityHeaderController.bindHeaderButtons();
        this.mEntityHeaderController = entityHeaderController;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
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

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoDashboardFragment.Callback
    public void refreshUi() {
        AppInfoDashboardFragment appInfoDashboardFragment = this.mParent;
        setAppLabelAndIcon(
                appInfoDashboardFragment.mPackageInfo, appInfoDashboardFragment.mAppEntry);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
