package com.samsung.android.settings.biometrics.fingerprint;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.fingerprint.FingerprintManager;
import android.os.UserHandle;

import androidx.preference.Preference;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.biometrics.BiometricsGenericHelper;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.knox.UCMUtils;
import com.samsung.android.settings.lockscreen.LockUtils;
import com.samsung.android.settings.security.SecurityUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class FingerprintSettingsPreferenceController extends BasePreferenceController {
    public static final String KEY_FINGER_SCANNER = "finger_scanner";
    private FingerprintManager mFingerprintManger;
    private boolean mIsStaringSetting;
    private LockPatternUtils mLockPatternUtils;

    public FingerprintSettingsPreferenceController(Context context) {
        this(context, KEY_FINGER_SCANNER);
    }

    private boolean isFingerPreferenceEnabled() {
        if (UCMUtils.isUCMKeyguardEnabled()
                && !UCMUtils.isSupportBiometricForUCM(this.mContext.getUserId())) {
            return false;
        }
        if (!Rune.isSamsungDexMode(this.mContext)
                || BiometricsGenericHelper.isEnabledBiometricsMenuInDexMode(this.mContext)) {
            return this.mLockPatternUtils.isSecure(UserHandle.myUserId())
                    || !LockUtils.isLockMenuDisabledByEdm(this.mContext);
        }
        return false;
    }

    private int numEnrolledFingerprints() {
        List enrolledFingerprints;
        FingerprintManager fingerprintManager = this.mFingerprintManger;
        if (fingerprintManager == null
                || (enrolledFingerprints =
                                fingerprintManager.getEnrolledFingerprints(UserHandle.myUserId()))
                        == null) {
            return 0;
        }
        return enrolledFingerprints.size();
    }

    private void startFingerprintScanner() {
        Intent intent = new Intent().setClass(this.mContext, FingerprintEntry.class);
        intent.setFlags(8388608);
        intent.putExtra("from_biometric_fragment", true);
        intent.putExtra("fromBiometricsMenu", true);
        try {
            this.mContext.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (!SecurityUtils.hasFingerprintFeature(this.mContext)
                || Utils.isGuestUser(this.mContext)
                || Rune.isShopDemo(this.mContext)) {
            return 3;
        }
        if (!isFingerPreferenceEnabled()) {
            return 5;
        }
        this.mIsStaringSetting = false;
        return 0;
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!preference.getKey().equals(getPreferenceKey()) || this.mIsStaringSetting) {
            return super.handlePreferenceTreeClick(preference);
        }
        this.mIsStaringSetting = true;
        startFingerprintScanner();
        return true;
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

    /* JADX WARN: Removed duplicated region for block: B:10:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:18:? A[RETURN, SYNTHETIC] */
    @Override // com.android.settingslib.core.AbstractPreferenceController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void updateState(androidx.preference.Preference r6) {
        /*
            r5 = this;
            super.updateState(r6)
            android.hardware.fingerprint.FingerprintManager r0 = r5.mFingerprintManger
            if (r0 == 0) goto L51
            boolean r0 = r0.hasEnrolledFingerprints()
            r1 = 2132026246(0x7f142386, float:1.969102E38)
            r2 = 1
            r3 = 0
            if (r0 == 0) goto L36
            int r0 = r5.numEnrolledFingerprints()
            if (r0 <= 0) goto L32
            android.content.Context r5 = r5.mContext
            android.content.res.Resources r5 = r5.getResources()
            java.lang.Integer r1 = java.lang.Integer.valueOf(r0)
            java.lang.Object[] r1 = new java.lang.Object[]{r1}
            r4 = 2131886144(0x7f120040, float:1.9406859E38)
            java.lang.String r5 = r5.getQuantityString(r4, r0, r1)
            r6.setSummary(r5)
            r5 = r2
            goto L3a
        L32:
            r6.setSummary(r1)
            goto L39
        L36:
            r6.setSummary(r1)
        L39:
            r5 = r3
        L3a:
            boolean r0 = r6 instanceof androidx.preference.SecPreferenceScreen
            if (r0 == 0) goto L51
            r0 = r6
            androidx.preference.SecPreferenceScreen r0 = (androidx.preference.SecPreferenceScreen) r0
            if (r5 == 0) goto L4a
            boolean r5 = r6.isEnabled()
            if (r5 == 0) goto L4a
            goto L4b
        L4a:
            r2 = r3
        L4b:
            r0.getClass()
            androidx.preference.SecPreferenceUtils.applySummaryColor(r0, r2)
        L51:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsPreferenceController.updateState(androidx.preference.Preference):void");
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public FingerprintSettingsPreferenceController(Context context, String str) {
        super(context, str);
        this.mFingerprintManger = Utils.getFingerprintManagerOrNull(this.mContext);
        this.mLockPatternUtils = new LockPatternUtils(context);
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
