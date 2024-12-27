package com.android.settings.biometrics.combination;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;

import com.android.settings.Utils;
import com.android.settings.biometrics.activeunlock.ActiveUnlockStatusUtils;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;

import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.sec.ims.volte2.data.VolteConstants;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BiometricSettingsKeyguardPreferenceController extends TogglePreferenceController {
    private static final int DEFAULT = 1;
    private static final int OFF = 0;
    private static final int ON = 1;
    private int mUserId;

    public BiometricSettingsKeyguardPreferenceController(Context context, String str) {
        super(context, str);
    }

    private int getAvailabilityFromRestrictingAdmin() {
        return getRestrictingAdmin() != null ? 4 : 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (new ActiveUnlockStatusUtils(this.mContext).isAvailable()) {
            return getAvailabilityFromRestrictingAdmin();
        }
        if (Utils.isMultipleBiometricsSupported(this.mContext)) {
            return getAvailabilityFromRestrictingAdmin();
        }
        return 3;
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

    public RestrictedLockUtils.EnforcedAdmin getRestrictingAdmin() {
        return RestrictedLockUtilsInternal.checkIfKeyguardFeaturesDisabled(
                this.mContext, VolteConstants.ErrorCode.UNSUPPORTED_URI_SCHEME, this.mUserId);
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

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return Settings.Secure.getIntForUser(
                        this.mContext.getContentResolver(),
                        "biometric_keyguard_enabled",
                        1,
                        this.mUserId)
                == 1;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public final boolean isSliceable() {
        return false;
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
        return Settings.Secure.putIntForUser(
                this.mContext.getContentResolver(),
                "biometric_keyguard_enabled",
                z ? 1 : 0,
                this.mUserId);
    }

    public void setUserId(int i) {
        this.mUserId = i;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
