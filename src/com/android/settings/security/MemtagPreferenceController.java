package com.android.settings.security;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemProperties;
import android.text.TextUtils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManagerImpl;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.internal.os.Zygote;
import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.RestrictedSwitchPreference;

import com.samsung.android.knox.zt.config.securelog.SignalSeverity;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class MemtagPreferenceController extends TogglePreferenceController {
    private Fragment mFragment;
    private Preference mPreference;

    public MemtagPreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = findPreference;
        refreshSummary(findPreference);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (TextUtils.equals(
                        "force_off",
                        SystemProperties.get(
                                "persist.device_config.runtime_native_boot.bootloader_override"))
                || TextUtils.equals(
                        "force_on",
                        SystemProperties.get(
                                "persist.device_config.runtime_native_boot.bootloader_override"))) {
            return 5;
        }
        return SystemProperties.getBoolean("ro.arm64.memtag.bootctl_settings_toggle", false)
                ? 0
                : 3;
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
        return R.string.menu_key_security;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return this.mContext.getResources().getString(MemtagHelper.getSummary());
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
        return MemtagHelper.isChecked();
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
        SystemProperties.set("arm64.memtag.bootctl", z ? "memtag" : SignalSeverity.NONE);
        Preference preference = this.mPreference;
        if (preference != null) {
            refreshSummary(preference);
        }
        if (z == Zygote.nativeSupportsMemoryTagging()) {
            return true;
        }
        int i = z ? R.string.memtag_reboot_message_on : R.string.memtag_reboot_message_off;
        FragmentManagerImpl supportFragmentManager =
                this.mFragment.getActivity().getSupportFragmentManager();
        if (supportFragmentManager.findFragmentByTag("MemtagRebootDialog") != null) {
            return true;
        }
        MemtagRebootDialog memtagRebootDialog = new MemtagRebootDialog();
        memtagRebootDialog.mMessage = i;
        memtagRebootDialog.show(supportFragmentManager, "MemtagRebootDialog");
        return true;
    }

    public void setFragment(Fragment fragment) {
        this.mFragment = fragment;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        RestrictedLockUtils.EnforcedAdmin checkIfMteIsDisabled =
                RestrictedLockUtilsInternal.checkIfMteIsDisabled(this.mContext);
        if (checkIfMteIsDisabled != null) {
            ((RestrictedSwitchPreference) preference).setDisabledByAdmin(checkIfMteIsDisabled);
        }
        refreshSummary(preference);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
