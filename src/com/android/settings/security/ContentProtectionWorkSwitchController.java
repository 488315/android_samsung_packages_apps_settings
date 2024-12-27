package com.android.settings.security;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.os.UserManager;

import androidx.preference.PreferenceScreen;

import com.android.internal.hidden_from_bootclasspath.android.view.contentprotection.flags.Flags;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedSwitchPreference;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ContentProtectionWorkSwitchController extends TogglePreferenceController {
    private int mContentProtectionPolicy;
    private UserHandle mManagedProfile;

    public ContentProtectionWorkSwitchController(Context context, String str) {
        super(context, str);
        this.mContentProtectionPolicy = 1;
        if (Flags.manageDevicePolicyEnabled()) {
            UserHandle managedProfile = getManagedProfile();
            this.mManagedProfile = managedProfile;
            if (managedProfile != null) {
                this.mContentProtectionPolicy = getContentProtectionPolicy(managedProfile);
            }
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        RestrictedSwitchPreference restrictedSwitchPreference;
        super.displayPreference(preferenceScreen);
        UserHandle managedProfile =
                Flags.manageDevicePolicyEnabled() ? this.mManagedProfile : getManagedProfile();
        if (managedProfile == null
                || (restrictedSwitchPreference =
                                (RestrictedSwitchPreference)
                                        preferenceScreen.findPreference(getPreferenceKey()))
                        == null) {
            return;
        }
        restrictedSwitchPreference.setDisabledByAdmin(getEnforcedAdmin(managedProfile));
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return !Flags.manageDevicePolicyEnabled()
                ? getManagedProfile() != null ? 0 : 2
                : (this.mManagedProfile == null || this.mContentProtectionPolicy == 0) ? 2 : 0;
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

    public int getContentProtectionPolicy(UserHandle userHandle) {
        return ContentProtectionPreferenceUtils.getContentProtectionPolicy(
                this.mContext, userHandle);
    }

    public RestrictedLockUtils.EnforcedAdmin getEnforcedAdmin(UserHandle userHandle) {
        return RestrictedLockUtils.getProfileOrDeviceOwner(this.mContext, null, userHandle);
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

    public UserHandle getManagedProfile() {
        UserManager userManager = (UserManager) this.mContext.getSystemService(UserManager.class);
        if (userManager == null) {
            return null;
        }
        return Utils.getManagedProfile(userManager);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_security;
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
        return Flags.manageDevicePolicyEnabled() && this.mContentProtectionPolicy == 2;
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
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
