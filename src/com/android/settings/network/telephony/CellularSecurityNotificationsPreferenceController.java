package com.android.settings.network.telephony;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.safetycenter.SafetyCenterManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.settings.applications.manageapplications.CloneBackend$$ExternalSyntheticOutline0;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class CellularSecurityNotificationsPreferenceController
        extends TelephonyTogglePreferenceController {
    private static final String LOG_TAG = "CellularSecurityNotificationsPreferenceController";
    protected SafetyCenterManager mSafetyCenterManager;
    private TelephonyManager mTelephonyManager;

    public CellularSecurityNotificationsPreferenceController(Context context, String str) {
        super(context, str);
        this.mTelephonyManager =
                (TelephonyManager) this.mContext.getSystemService(TelephonyManager.class);
        this.mSafetyCenterManager =
                (SafetyCenterManager) this.mContext.getSystemService(SafetyCenterManager.class);
    }

    private boolean areFlagsEnabled() {
        return true;
    }

    private boolean areNotificationsEnabled() {
        if (this.mTelephonyManager == null) {
            Log.w(LOG_TAG, "Telephony manager not yet initialized");
            this.mTelephonyManager =
                    (TelephonyManager) this.mContext.getSystemService(TelephonyManager.class);
        }
        return this.mTelephonyManager.isNullCipherNotificationsEnabled()
                && this.mTelephonyManager.isCellularIdentifierDisclosureNotificationsEnabled();
    }

    private void setNotifications(boolean z) {
        this.mTelephonyManager.setEnableCellularIdentifierDisclosureNotifications(z);
        this.mTelephonyManager.setNullCipherNotificationsEnabled(z);
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.network.telephony.TelephonyAvailabilityCallback
    public int getAvailabilityStatus(int i) {
        if (!isSafetyCenterSupported() || !areFlagsEnabled()) {
            return 3;
        }
        try {
            areNotificationsEnabled();
            return 0;
        } catch (UnsupportedOperationException e) {
            Log.i(LOG_TAG, "Cellular security notifications are unsupported: " + e.getMessage());
            return 3;
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

    public CellularSecurityNotificationsPreferenceController init(int i) {
        this.mTelephonyManager =
                ((TelephonyManager) this.mContext.getSystemService(TelephonyManager.class))
                        .createForSubscriptionId(i);
        return this;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        if (!areFlagsEnabled()) {
            return false;
        }
        try {
            return areNotificationsEnabled();
        } catch (Exception e) {
            CloneBackend$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder(
                            "Failed isNullCipherNotificationsEnabled and"
                                + " isCellularIdentifierDisclosureNotificationsEnabled.Defaulting"
                                + " toggle to checked = true. Exception: "),
                    LOG_TAG);
            return false;
        }
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    public boolean isSafetyCenterSupported() {
        SafetyCenterManager safetyCenterManager =
                (SafetyCenterManager) this.mContext.getSystemService(SafetyCenterManager.class);
        this.mSafetyCenterManager = safetyCenterManager;
        if (safetyCenterManager == null) {
            return false;
        }
        return safetyCenterManager.isSafetyCenterEnabled();
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
            Log.i(LOG_TAG, "Enabling cellular security notifications.");
        } else {
            Log.i(LOG_TAG, "Disabling cellular security notifications.");
        }
        if (!areFlagsEnabled()) {
            return false;
        }
        try {
            setNotifications(z);
            return true;
        } catch (Exception e) {
            CloneBackend$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder(
                            "Failed setCellularIdentifierDisclosureNotificationEnabled or "
                                + " setNullCipherNotificationsEnabled. Setting not updated."
                                + " Exception: "),
                    LOG_TAG);
            setNotifications(!z);
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
