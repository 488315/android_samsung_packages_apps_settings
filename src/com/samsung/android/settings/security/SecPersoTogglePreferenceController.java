package com.samsung.android.settings.security;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.util.Log;
import android.widget.Toast;

import androidx.preference.Preference;

import com.android.internal.telephony.ISemTelephony;
import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecPersoTogglePreferenceController extends TogglePreferenceController {
    private static final String TAG = "SecIccLockSettings.SecPersoTogglePreferenceController";
    private SecIccLockManager mIccLockManager;

    public SecPersoTogglePreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (!Rune.FEATURE_SIM_LOCK_KOR) {
            return 3;
        }
        SecIccLockManager secIccLockManager = this.mIccLockManager;
        return (secIccLockManager == null || !secIccLockManager.isEuicc()) ? 0 : 2;
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
        SecIccLockManager secIccLockManager = this.mIccLockManager;
        secIccLockManager.getClass();
        ISemTelephony asInterface =
                ISemTelephony.Stub.asInterface(ServiceManager.getService("isemtelephony"));
        if (asInterface == null) {
            return false;
        }
        try {
            return asInterface.getIccUsimPersoEnabledForSubId(secIccLockManager.mSubscriptionId);
        } catch (RemoteException e) {
            Log.e("SecIccLockSettings.SecIccLockManager", "isPersoEnabled : " + e.getMessage());
            return false;
        }
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
        Log.d(TAG, "setChecked() : " + z);
        int i = SystemProperties.getInt("ril.initPB", 0);
        if (this.mIccLockManager.mSlotIndex == 1) {
            i = SystemProperties.getInt("ril.initPB2", 0);
        }
        if (i != 1) {
            Toast.makeText(this.mContext, R.string.on_reading_usim, 0).show();
            return false;
        }
        this.mIccLockManager.setIccLockMode(1);
        SecIccLockManager secIccLockManager = this.mIccLockManager;
        secIccLockManager.mIccToState = z;
        SecSimPersoDialog.show(this.mContext, secIccLockManager);
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        preference.setEnabled(this.mIccLockManager.iccMenuEnabled());
        if (preference.getOnPreferenceChangeListener() == null) {
            preference.setOnPreferenceChangeListener(this);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
