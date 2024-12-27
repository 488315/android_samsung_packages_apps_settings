package com.android.settings.network.telephony;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.DeviceConfig;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.settings.applications.manageapplications.CloneBackend$$ExternalSyntheticOutline0;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NullAlgorithmsPreferenceController extends TelephonyTogglePreferenceController {
    private static final String LOG_TAG = "NullAlgosController";
    private TelephonyManager mTelephonyManager;

    public NullAlgorithmsPreferenceController(Context context, String str) {
        super(context, str);
        this.mTelephonyManager =
                (TelephonyManager) this.mContext.getSystemService(TelephonyManager.class);
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.network.telephony.TelephonyAvailabilityCallback
    public int getAvailabilityStatus(int i) {
        if (this.mTelephonyManager == null) {
            Log.w(
                    LOG_TAG,
                    "Telephony manager not yet initialized. Marking availability as"
                        + " CONDITIONALLY_UNAVAILABLE");
            this.mTelephonyManager =
                    (TelephonyManager) this.mContext.getSystemService(TelephonyManager.class);
            return 2;
        }
        if (!DeviceConfig.getBoolean("cellular_security", "enable_null_cipher_toggle", true)) {
            Log.i(LOG_TAG, "Null cipher toggle is disabled by DeviceConfig");
            return 2;
        }
        try {
            this.mTelephonyManager.isNullCipherAndIntegrityPreferenceEnabled();
            return 0;
        } catch (UnsupportedOperationException e) {
            Log.i(LOG_TAG, "Null cipher enablement is unsupported: " + e.getMessage());
            return 3;
        } catch (Exception e2) {
            CloneBackend$$ExternalSyntheticOutline0.m(
                    e2,
                    new StringBuilder(
                            "Failed isNullCipherAndIntegrityEnabled. Setting availability to"
                                + " CONDITIONALLY_UNAVAILABLE. Exception: "),
                    LOG_TAG);
            return 2;
        }
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        try {
            return !this.mTelephonyManager.isNullCipherAndIntegrityPreferenceEnabled();
        } catch (Exception e) {
            CloneBackend$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder(
                            "Failed isNullCipherAndIntegrityEnabled. Defaulting toggle to checked ="
                                + " true. Exception: "),
                    LOG_TAG);
            return true;
        }
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        if (z) {
            Log.i(LOG_TAG, "Encryption required. Disabling null algorithms.");
        } else {
            Log.i(LOG_TAG, "Encryption not required. Enabling null algorithms.");
        }
        try {
            this.mTelephonyManager.setNullCipherAndIntegrityEnabled(!z);
            return true;
        } catch (Exception e) {
            CloneBackend$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder(
                            "Failed setNullCipherAndIntegrityEnabled. Setting not updated."
                                + " Exception: "),
                    LOG_TAG);
            return false;
        }
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
