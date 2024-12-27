package com.samsung.android.settings.biometrics.face;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.face.FaceManager;
import android.os.UserHandle;

import androidx.preference.Preference;
import androidx.preference.SecPreferenceScreen;
import androidx.preference.SecPreferenceUtils;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.biometrics.BiometricsGenericHelper;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.knox.UCMUtils;
import com.samsung.android.settings.lockscreen.LockUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class FaceSettingsPreferenceController extends BasePreferenceController {
    public static final String KEY_FACE_SETTINGS = "silent_lock";
    private boolean mIsStaringSetting;
    private LockPatternUtils mLockPatternUtils;

    public FaceSettingsPreferenceController(Context context) {
        this(context, KEY_FACE_SETTINGS);
    }

    private boolean isFacePreferenceEnabled() {
        if (Rune.isSamsungDexMode(this.mContext)
                && !BiometricsGenericHelper.isEnabledBiometricsMenuInDexMode(this.mContext)) {
            return false;
        }
        if (KnoxUtils.isKnoxOrganizationOwnedDevice(this.mContext, UserHandle.myUserId())
                && KnoxUtils.isMultifactorAuthEnforced(this.mContext, UserHandle.myUserId())) {
            return false;
        }
        if (!UCMUtils.isUCMKeyguardEnabled()
                || UCMUtils.isSupportBiometricForUCM(this.mContext.getUserId())) {
            return this.mLockPatternUtils.isSecure(UserHandle.myUserId())
                    || !LockUtils.isLockMenuDisabledByEdm(this.mContext);
        }
        return false;
    }

    private void startFaceSettings() {
        Intent intent = new Intent().setClass(this.mContext, FaceEntry.class);
        intent.setFlags(8388608);
        intent.putExtra("from_biometric_fragment", true);
        try {
            this.mContext.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (Utils.isGuestUser(this.mContext) || Rune.isShopDemo(this.mContext)) {
            return 3;
        }
        if (!isFacePreferenceEnabled()) {
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
        startFaceSettings();
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

    @Override // com.android.settings.core.BasePreferenceController
    public void updateNonIndexableKeys(List<String> list) {
        super.updateNonIndexableKeys(list);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        boolean z;
        super.updateState(preference);
        FaceManager faceManagerOrNull = Utils.getFaceManagerOrNull(this.mContext);
        if (faceManagerOrNull != null) {
            if (faceManagerOrNull.hasEnrolledTemplates(UserHandle.myUserId())) {
                preference.setSummary(R.string.sec_face_register_summary);
                z = true;
            } else {
                preference.setSummary(R.string.sec_face_add_summary);
                z = false;
            }
            if (preference instanceof SecPreferenceScreen) {
                SecPreferenceScreen secPreferenceScreen = (SecPreferenceScreen) preference;
                boolean z2 = z && preference.isEnabled();
                secPreferenceScreen.getClass();
                SecPreferenceUtils.applySummaryColor(secPreferenceScreen, z2);
            }
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public FaceSettingsPreferenceController(Context context, String str) {
        super(context, str);
        this.mLockPatternUtils = new LockPatternUtils(context);
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
