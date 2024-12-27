package com.android.settings.biometrics.fingerprint;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.fingerprint.FingerprintManager;
import android.os.UserHandle;
import android.provider.Settings;
import androidx.preference.Preference;
import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.Utils;
import com.android.settings.search.BaseSearchIndexProvider;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FingerprintSettingsRequireScreenOnToAuthPreferenceController extends FingerprintSettingsPreferenceController {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass1();
    private static final String TAG = "FingerprintSettingsRequireScreenOnToAuthPreferenceController";

    @VisibleForTesting
    protected FingerprintManager mFingerprintManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.biometrics.fingerprint.FingerprintSettingsRequireScreenOnToAuthPreferenceController$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
    }

    public FingerprintSettingsRequireScreenOnToAuthPreferenceController(Context context, String str) {
        super(context, str);
        this.mFingerprintManager = Utils.getFingerprintManagerOrNull(context);
    }

    private int getUserHandle() {
        return UserHandle.of(getUserId()).getIdentifier();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        FingerprintManager fingerprintManager = this.mFingerprintManager;
        if (fingerprintManager != null && fingerprintManager.isHardwareDetected() && this.mFingerprintManager.isPowerbuttonFps()) {
            return this.mFingerprintManager.hasEnrolledTemplates(getUserId()) ? 0 : 2;
        }
        return 3;
    }

    @Override // com.android.settings.biometrics.fingerprint.FingerprintSettingsPreferenceController, com.android.settings.core.TogglePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.biometrics.fingerprint.FingerprintSettingsPreferenceController, com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.biometrics.fingerprint.FingerprintSettingsPreferenceController, com.android.settings.core.TogglePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.biometrics.fingerprint.FingerprintSettingsPreferenceController, com.android.settings.core.TogglePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.biometrics.fingerprint.FingerprintSettingsPreferenceController, com.android.settings.core.TogglePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.biometrics.fingerprint.FingerprintSettingsPreferenceController, com.android.settings.core.TogglePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.biometrics.fingerprint.FingerprintSettingsPreferenceController, com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    /* JADX WARN: Type inference failed for: r0v9, types: [boolean, int] */
    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        if (!FingerprintSettings.isFingerprintHardwareDetected(this.mContext) || getRestrictingAdmin() != null) {
            return false;
        }
        int intForUser = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "sfps_performant_auth_enabled_v2", -1, getUserHandle());
        int i = intForUser;
        if (intForUser == -1) {
            ?? r0 = this.mContext.getResources().getBoolean(R.bool.config_reduceBrightColorsAvailable);
            Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "sfps_performant_auth_enabled_v2", r0, getUserHandle());
            i = r0;
        }
        return i == 1;
    }

    @Override // com.android.settings.biometrics.fingerprint.FingerprintSettingsPreferenceController, com.android.settings.core.TogglePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.biometrics.fingerprint.FingerprintSettingsPreferenceController, com.android.settings.core.TogglePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.biometrics.fingerprint.FingerprintSettingsPreferenceController, com.android.settings.core.TogglePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "sfps_performant_auth_enabled_v2", z ? 1 : 0, getUserHandle());
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (!FingerprintSettings.isFingerprintHardwareDetected(this.mContext)) {
            preference.setEnabled(false);
            return;
        }
        if (!this.mFingerprintManager.hasEnrolledTemplates(getUserId())) {
            preference.setEnabled(false);
        } else if (getRestrictingAdmin() != null) {
            preference.setEnabled(false);
        } else {
            preference.setEnabled(true);
        }
    }

    @Override // com.android.settings.biometrics.fingerprint.FingerprintSettingsPreferenceController, com.android.settings.core.TogglePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
