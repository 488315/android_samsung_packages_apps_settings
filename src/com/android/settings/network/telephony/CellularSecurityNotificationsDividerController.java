package com.android.settings.network.telephony;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.safetycenter.SafetyCenterManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class CellularSecurityNotificationsDividerController extends BasePreferenceController {
    private static final String LOG_TAG = "CellularSecurityNotificationsDividerController";
    protected SafetyCenterManager mSafetyCenterManager;
    private TelephonyManager mTelephonyManager;

    public CellularSecurityNotificationsDividerController(Context context, String str) {
        super(context, str);
        this.mTelephonyManager =
                (TelephonyManager) this.mContext.getSystemService(TelephonyManager.class);
        this.mSafetyCenterManager =
                (SafetyCenterManager) this.mContext.getSystemService(SafetyCenterManager.class);
    }

    public boolean areNotificationsEnabled() {
        return this.mTelephonyManager.isNullCipherNotificationsEnabled()
                && this.mTelephonyManager.isCellularIdentifierDisclosureNotificationsEnabled();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (!isSafetyCenterSupported()) {
            return 3;
        }
        if (this.mTelephonyManager == null) {
            Log.w(LOG_TAG, "Telephony manager not yet initialized");
            this.mTelephonyManager =
                    (TelephonyManager) this.mContext.getSystemService(TelephonyManager.class);
        }
        try {
            areNotificationsEnabled();
            return 0;
        } catch (UnsupportedOperationException e) {
            Log.i(
                    LOG_TAG,
                    "Cellular security notifications are unsupported, hiding divider: "
                            + e.getMessage());
            return 3;
        }
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

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
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

    public boolean isSafetyCenterSupported() {
        SafetyCenterManager safetyCenterManager =
                (SafetyCenterManager) this.mContext.getSystemService(SafetyCenterManager.class);
        this.mSafetyCenterManager = safetyCenterManager;
        if (safetyCenterManager == null) {
            return false;
        }
        return safetyCenterManager.isSafetyCenterEnabled();
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

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
