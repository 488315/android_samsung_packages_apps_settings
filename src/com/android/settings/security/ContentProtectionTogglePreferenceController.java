package com.android.settings.security;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.widget.CompoundButton;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.internal.hidden_from_bootclasspath.android.view.contentprotection.flags.Flags;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.TogglePreferenceController;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settings.widget.SettingsMainSwitchPreference;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ContentProtectionTogglePreferenceController extends TogglePreferenceController
        implements CompoundButton.OnCheckedChangeListener {
    static final String KEY_CONTENT_PROTECTION_PREFERENCE = "content_protection_user_consent";
    private int mContentProtectionPolicy;
    private final ContentResolver mContentResolver;
    private RestrictedLockUtils.EnforcedAdmin mEnforcedAdmin;
    private SettingsMainSwitchPreference mSwitchBar;

    public ContentProtectionTogglePreferenceController(Context context, String str) {
        super(context, str);
        this.mContentProtectionPolicy = 1;
        this.mContentResolver = context.getContentResolver();
        if (Flags.manageDevicePolicyEnabled()) {
            this.mEnforcedAdmin = getEnforcedAdmin();
            this.mContentProtectionPolicy = getContentProtectionPolicy(getManagedProfile());
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        if (findPreference instanceof SettingsMainSwitchPreference) {
            SettingsMainSwitchPreference settingsMainSwitchPreference =
                    (SettingsMainSwitchPreference) findPreference;
            this.mSwitchBar = settingsMainSwitchPreference;
            settingsMainSwitchPreference.addOnSwitchChangeListener(this);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
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

    public RestrictedLockUtils.EnforcedAdmin getEnforcedAdmin() {
        return RestrictedLockUtilsInternal.getDeviceOwner(this.mContext);
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
        int i;
        if (this.mEnforcedAdmin != null) {
            if (!Flags.manageDevicePolicyEnabled() || (i = this.mContentProtectionPolicy) == 1) {
                return false;
            }
            if (i == 2) {
                return true;
            }
        }
        return Settings.Global.getInt(this.mContentResolver, KEY_CONTENT_PROTECTION_PREFERENCE, 0)
                >= 0;
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

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (z != getThreadEnabled()) {
            setChecked(z);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        if (Flags.manageDevicePolicyEnabled()
                && this.mEnforcedAdmin != null
                && this.mContentProtectionPolicy != 0) {
            return false;
        }
        Settings.Global.putInt(
                this.mContentResolver, KEY_CONTENT_PROTECTION_PREFERENCE, z ? 1 : -1);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        RestrictedLockUtils.EnforcedAdmin enforcedAdmin;
        super.updateState(preference);
        if (!Flags.manageDevicePolicyEnabled()) {
            this.mEnforcedAdmin = getEnforcedAdmin();
            this.mContentProtectionPolicy = 1;
        }
        SettingsMainSwitchPreference settingsMainSwitchPreference = this.mSwitchBar;
        if (settingsMainSwitchPreference == null
                || (enforcedAdmin = this.mEnforcedAdmin) == null
                || this.mContentProtectionPolicy == 0) {
            return;
        }
        settingsMainSwitchPreference.mEnforcedAdmin = enforcedAdmin;
        SettingsMainSwitchBar settingsMainSwitchBar = settingsMainSwitchPreference.mMainSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.setDisabledByAdmin(enforcedAdmin);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
