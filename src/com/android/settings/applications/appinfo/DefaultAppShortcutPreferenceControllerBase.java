package com.android.settings.applications.appinfo;

import android.app.role.RoleManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreference;
import androidx.preference.SecPreferenceUtils;

import com.android.internal.util.CollectionUtils;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.applications.AppUtils;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class DefaultAppShortcutPreferenceControllerBase extends BasePreferenceController {
    private static final String TAG = "DefaultAppShortcutPreferenceControllerBase";
    private boolean mAppVisible;
    private boolean mIsArchived;
    protected final String mPackageName;
    private PreferenceScreen mPreferenceScreen;
    private final RoleManager mRoleManager;
    private final String mRoleName;
    private boolean mRoleVisible;

    public DefaultAppShortcutPreferenceControllerBase(
            Context context, String str, String str2, String str3) {
        super(context, str);
        this.mRoleName = str2;
        this.mPackageName = str3;
        RoleManager roleManager = (RoleManager) context.getSystemService(RoleManager.class);
        this.mRoleManager = roleManager;
        Executor mainExecutor = this.mContext.getMainExecutor();
        roleManager.isRoleVisible(
                str2,
                mainExecutor,
                new DefaultAppShortcutPreferenceControllerBase$$ExternalSyntheticLambda0(this, 0));
        roleManager.isApplicationVisibleForRole(
                str2,
                str3,
                mainExecutor,
                new DefaultAppShortcutPreferenceControllerBase$$ExternalSyntheticLambda0(this, 1));
        this.mIsArchived = AppUtils.isArchived(this.mContext, str3);
    }

    private boolean isDefaultApp() {
        return TextUtils.equals(
                this.mPackageName,
                (String)
                        CollectionUtils.firstOrNull(
                                this.mRoleManager.getRoleHolders(this.mRoleName)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(Boolean bool) {
        this.mRoleVisible = bool.booleanValue();
        refreshAvailability();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(Boolean bool) {
        this.mAppVisible = bool.booleanValue();
        refreshAvailability();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateControllerVisibility$2(Boolean bool) {
        this.mRoleVisible = bool.booleanValue();
        refreshAvailability();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateControllerVisibility$3(Boolean bool) {
        this.mAppVisible = bool.booleanValue();
        refreshAvailability();
    }

    private void refreshAvailability() {
        Preference findPreference;
        PreferenceScreen preferenceScreen = this.mPreferenceScreen;
        if (preferenceScreen == null
                || (findPreference = preferenceScreen.findPreference(getPreferenceKey())) == null) {
            return;
        }
        updateState(findPreference);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreferenceScreen = preferenceScreen;
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        String str =
                (String)
                        CollectionUtils.firstOrNull(
                                this.mRoleManager.getRoleHolders(this.mRoleName));
        if (TextUtils.isEmpty(str)) {
            return this.mContext.getString(R.string.sec_no_default_app);
        }
        PackageManager packageManager = this.mContext.getPackageManager();
        Intent intent = AppUtils.sBrowserIntent;
        return com.android.settingslib.utils.applications.AppUtils.getApplicationLabel(
                packageManager, str);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(this.mPreferenceKey, preference.getKey())) {
            return false;
        }
        this.mContext.startActivity(
                new Intent("android.intent.action.MANAGE_DEFAULT_APP")
                        .setPackage(
                                this.mContext
                                        .getPackageManager()
                                        .getPermissionControllerPackageName())
                        .putExtra("android.intent.extra.ROLE_NAME", this.mRoleName));
        return true;
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

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    public void updateControllerVisibility() {
        Log.d(TAG, "updateControllerVisibility");
        Executor mainExecutor = this.mContext.getMainExecutor();
        this.mRoleManager.isRoleVisible(
                this.mRoleName,
                mainExecutor,
                new DefaultAppShortcutPreferenceControllerBase$$ExternalSyntheticLambda0(this, 2));
        this.mRoleManager.isApplicationVisibleForRole(
                this.mRoleName,
                this.mPackageName,
                mainExecutor,
                new DefaultAppShortcutPreferenceControllerBase$$ExternalSyntheticLambda0(this, 3));
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (preference instanceof SecPreference) {
            SecPreference secPreference = (SecPreference) preference;
            secPreference.getClass();
            SecPreferenceUtils.applySummaryColor(secPreference, true);
        }
        if (((UserManager) this.mContext.getSystemService(UserManager.class)).isManagedProfile()) {
            preference.setVisible(false);
            return;
        }
        String key = preference.getKey();
        if (Utils.hasPackage(this.mContext, "com.bmwgroup.touchcommand")
                && (key.equals("default_home") || key.equals("home_dcm"))) {
            preference.setVisible(false);
            return;
        }
        if (Rune.isJapanDCMModel() && key.equals("default_home")) {
            preference.setVisible(false);
            return;
        }
        if (!Rune.isJapanDCMModel() && key.equals("home_dcm")) {
            preference.setVisible(false);
            return;
        }
        boolean isArchived = AppUtils.isArchived(this.mContext, this.mPackageName);
        if (isArchived) {
            preference.setVisible(false);
            this.mIsArchived = isArchived;
            return;
        }
        if (this.mRoleVisible && this.mAppVisible) {
            preference.setVisible(true);
        } else {
            preference.setVisible(false);
        }
        if (isArchived == this.mIsArchived) {
            return;
        }
        this.mIsArchived = isArchived;
        updateControllerVisibility();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
