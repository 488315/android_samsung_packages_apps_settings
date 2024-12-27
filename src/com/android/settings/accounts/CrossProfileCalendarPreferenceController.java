package com.android.settings.accounts;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.TogglePreferenceController;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class CrossProfileCalendarPreferenceController extends TogglePreferenceController {
    private static final String TAG = "CrossProfileCalendarPreferenceController";
    private UserHandle mManagedUser;

    public CrossProfileCalendarPreferenceController(Context context, String str) {
        super(context, str);
        this.mManagedUser =
                Utils.getManagedProfile((UserManager) context.getSystemService(UserManager.class));
    }

    private static Context createPackageContextAsUser(Context context, int i) {
        try {
            return context.createPackageContextAsUser(
                    context.getPackageName(), 0, UserHandle.of(i));
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Failed to create user context", e);
            return null;
        }
    }

    public static boolean isCrossProfileCalendarDisallowedByAdmin(Context context, int i) {
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager)
                        createPackageContextAsUser(context, i)
                                .getSystemService(DevicePolicyManager.class);
        if (devicePolicyManager == null) {
            return true;
        }
        Set crossProfileCalendarPackages = devicePolicyManager.getCrossProfileCalendarPackages();
        return crossProfileCalendarPackages != null && crossProfileCalendarPackages.isEmpty();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        UserHandle userHandle = this.mManagedUser;
        return (userHandle == null
                        || isCrossProfileCalendarDisallowedByAdmin(
                                this.mContext, userHandle.getIdentifier()))
                ? 4
                : 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_accounts;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return this.mManagedUser != null
                && Settings.Secure.getIntForUser(
                                this.mContext.getContentResolver(),
                                "cross_profile_calendar_enabled",
                                0,
                                this.mManagedUser.getIdentifier())
                        == 1;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        if (this.mManagedUser == null) {
            return false;
        }
        return Settings.Secure.putIntForUser(
                this.mContext.getContentResolver(),
                "cross_profile_calendar_enabled",
                z ? 1 : 0,
                this.mManagedUser.getIdentifier());
    }

    public void setManagedUser(UserHandle userHandle) {
        this.mManagedUser = userHandle;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
