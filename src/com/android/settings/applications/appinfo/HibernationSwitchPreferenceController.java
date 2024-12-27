package com.android.settings.applications.appinfo;

import android.app.AppOpsManager;
import android.apphibernation.AppHibernationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.permission.PermissionControllerManager;
import android.provider.DeviceConfig;
import android.util.Log;
import android.util.Slog;

import androidx.appcompat.view.SeslContentViewInsetsCallback$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.settings.R;
import com.android.settingslib.applications.AppUtils;
import com.android.settingslib.core.lifecycle.events.OnDestroy;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;
import java.util.function.IntConsumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class HibernationSwitchPreferenceController extends AppInfoPreferenceControllerBase
        implements Preference.OnPreferenceChangeListener, OnDestroy {
    private static final String TAG = "HibernationSwitchPrefController";
    private final AppOpsManager mAppOpsManager;
    private int mHibernationEligibility;
    private boolean mHibernationEligibilityLoaded;
    private boolean mIsPackageExemptByDefault;
    boolean mIsPackageSet;
    private boolean mIsSupportedArchive;
    private String mPackageName;
    private int mPackageUid;
    private PermissionControllerManager mPermissionControllerManager;

    public HibernationSwitchPreferenceController(Context context, String str) {
        super(context, str);
        this.mHibernationEligibility = -1;
        this.mIsSupportedArchive = false;
        this.mAppOpsManager = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        this.mPermissionControllerManager =
                (PermissionControllerManager)
                        context.getSystemService(PermissionControllerManager.class);
    }

    private static boolean hibernationTargetsPreSApps() {
        return DeviceConfig.getBoolean(
                "app_hibernation", "app_hibernation_targets_pre_s_apps", false);
    }

    private boolean isAppEligibleForHibernation() {
        int i;
        return (!this.mHibernationEligibilityLoaded
                        || (i = this.mHibernationEligibility) == 1
                        || i == -1)
                ? false
                : true;
    }

    private static boolean isHibernationEnabled() {
        return DeviceConfig.getBoolean("app_hibernation", "app_hibernation_enabled", true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateState$0(Preference preference, int i) {
        this.mHibernationEligibility = i;
        this.mHibernationEligibilityLoaded = true;
        updateState(preference);
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (AppUtils.isAppInstalled(this.mContext, this.mPackageName)) {
            return (isHibernationEnabled() && this.mIsPackageSet) ? 0 : 2;
        }
        return 0;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    public boolean isPackageHibernationExemptByUser() {
        if (!this.mIsPackageSet) {
            return true;
        }
        int unsafeCheckOpNoThrow =
                this.mAppOpsManager.unsafeCheckOpNoThrow(
                        "android:auto_revoke_permissions_if_unused",
                        this.mPackageUid,
                        this.mPackageName);
        return unsafeCheckOpNoThrow == 3
                ? this.mIsPackageExemptByDefault
                : unsafeCheckOpNoThrow != 0;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnDestroy
    public void onDestroy() {
        if (this.mPermissionControllerManager != null) {
            this.mPermissionControllerManager = null;
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        try {
            boolean booleanValue = ((Boolean) obj).booleanValue();
            if (preference != null) {
                Log.i(TAG, "Target Preference : " + preference.getKey());
            }
            Log.i(TAG, "newState : " + booleanValue);
            this.mAppOpsManager.setUidMode(
                    "android:auto_revoke_permissions_if_unused",
                    this.mPackageUid,
                    !booleanValue ? 1 : 0);
            if (!booleanValue) {
                AppHibernationManager appHibernationManager =
                        (AppHibernationManager)
                                this.mContext.getSystemService(AppHibernationManager.class);
                appHibernationManager.setHibernatingForUser(this.mPackageName, false);
                appHibernationManager.setHibernatingGlobally(this.mPackageName, false);
            }
            AppInfoDashboardFragment appInfoDashboardFragment = this.mParent;
            if (appInfoDashboardFragment instanceof AppInfoDashboardFragment) {
                appInfoDashboardFragment.refreshUi();
            }
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.applications.appinfo.AppInfoDashboardFragment.Callback
    public void refreshUi() {
        super.refreshUi();
        setPackage(this.mPackageName);
        if (getAvailabilityStatus() == 0) {
            this.mPreference.setVisible(true);
        } else {
            this.mPreference.setVisible(false);
        }
        updateState(this.mPreference);
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    public void setPackage(String str) {
        this.mPackageName = str;
        PackageManager packageManager = this.mContext.getPackageManager();
        int i = packageManager.hasSystemFeature("android.hardware.type.automotive") ? 30 : 29;
        try {
            this.mPackageUid = packageManager.getPackageUid(str, 0);
            this.mIsPackageExemptByDefault =
                    !hibernationTargetsPreSApps() && packageManager.getTargetSdkVersion(str) <= i;
            this.mIsPackageSet = true;
            Log.i(TAG, "mIsPackageSet true");
        } catch (PackageManager.NameNotFoundException unused) {
            Slog.w(TAG, "Package [" + this.mPackageName + "] is not found!");
            this.mIsPackageSet = false;
            Log.i(TAG, "mIsPackageSet false");
        }
    }

    public void setSupportedArchive(boolean z) {
        this.mIsSupportedArchive = z;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(final Preference preference) {
        super.updateState(preference);
        Log.i(TAG, "isAppEligibleForHibernation : " + isAppEligibleForHibernation());
        Log.i(
                TAG,
                "!isPackageHibernationExemptByUser : "
                        + (isPackageHibernationExemptByUser() ^ true));
        SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(
                new StringBuilder("mHibernationEligibilityLoaded : "),
                this.mHibernationEligibilityLoaded,
                TAG);
        if (!AppUtils.isAppInstalled(this.mContext, this.mPackageName)) {
            preference.setEnabled(false);
            return;
        }
        if (this.mIsSupportedArchive) {
            this.mPreference.setTitle(R.string.unused_apps_switch_v2);
            this.mPreference.setSummary(R.string.unused_apps_switch);
        }
        ((TwoStatePreference) preference)
                .setChecked(isAppEligibleForHibernation() && !isPackageHibernationExemptByUser());
        preference.setEnabled(isAppEligibleForHibernation());
        if (this.mHibernationEligibilityLoaded) {
            return;
        }
        this.mPermissionControllerManager.getHibernationEligibility(
                this.mPackageName,
                this.mContext.getMainExecutor(),
                new IntConsumer() { // from class:
                                    // com.android.settings.applications.appinfo.HibernationSwitchPreferenceController$$ExternalSyntheticLambda0
                    @Override // java.util.function.IntConsumer
                    public final void accept(int i) {
                        HibernationSwitchPreferenceController.this.lambda$updateState$0(
                                preference, i);
                    }
                });
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
