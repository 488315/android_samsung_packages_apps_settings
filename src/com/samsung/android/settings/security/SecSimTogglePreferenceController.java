package com.samsung.android.settings.security;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;

import com.samsung.android.knox.ucm.configurator.UniversalCredentialManager;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecSimTogglePreferenceController extends TogglePreferenceController {
    private static final String TAG = "SecIccLockSettings.SecSimTogglePreferenceController";
    private SecIccLockManager mIccLockManager;

    public SecSimTogglePreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        if (findPreference == null) {
            return;
        }
        if (Rune.isChinaCTCModel() || Rune.isChinaModel()) {
            if (this.mIccLockManager.isCTCSlot1()) {
                findPreference.setTitle(
                        this.mContext
                                .getApplicationContext()
                                .getResources()
                                .getString(R.string.sim_pin_toggle)
                                .replace(UniversalCredentialManager.APPLET_FORM_FACTOR_SIM, "UIM"));
                findPreference.setSummary(R.string.ruim_sim_lock_on);
            } else {
                findPreference.setTitle(
                        this.mContext
                                .getApplicationContext()
                                .getResources()
                                .getString(R.string.sim_pin_toggle));
                findPreference.setSummary(R.string.sec_sim_lock_on);
            }
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
        return 0;
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

    public void init(SecIccLockManager secIccLockManager) {
        this.mIccLockManager = secIccLockManager;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return this.mIccLockManager.mTelephonyManager.isIccLockEnabled();
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
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m("setChecked() : ", TAG, z);
        this.mIccLockManager.setIccLockMode(1);
        SecIccLockManager secIccLockManager = this.mIccLockManager;
        secIccLockManager.mIccToState = z;
        SecSimPinDialog.show(this.mContext, secIccLockManager);
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        preference.setEnabled(this.mIccLockManager.iccMenuEnabled());
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
