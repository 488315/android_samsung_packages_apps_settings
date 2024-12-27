package com.samsung.android.settings.lockscreen.controller;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.picker.widget.SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceScreen;
import androidx.preference.SecPreferenceUtils;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.core.instrumentation.Instrumentable;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.knox.UCMUtils;
import com.samsung.android.settings.lockscreen.LockUtils;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.privacy.SecurityAndPrivacySettings;
import com.samsung.android.settings.security.SecurityUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ScreenLockTypePreferenceController extends BasePreferenceController {
    public static final String CHOOSE_LOCK_GENERIC =
            "com.android.settings.password.ChooseLockGeneric$ChooseLockGenericFragment";
    public static final int SET_OR_CHANGE_LOCK_METHOD_REQUEST = 123;
    private static final String TAG = "ScreenLockTypePreferenceController";
    private final LockPatternUtils mLockPatternUtils;
    private Fragment mParentFragment;
    private SecPreferenceScreen mPreference;
    private int mRequestCode;
    private final int mUserId;

    public ScreenLockTypePreferenceController(Context context, String str) {
        super(context, str);
        this.mUserId = UserHandle.myUserId();
        this.mLockPatternUtils = new LockPatternUtils(context);
    }

    private String getBiometricsSummary(boolean z, boolean z2) {
        if (Settings.Secure.getIntForUser(
                        this.mContext.getContentResolver(),
                        "knox_finger_print_plus",
                        0,
                        this.mUserId)
                == 1) {
            StringBuilder sb = new StringBuilder();
            int keyguardStoredPasswordQuality =
                    this.mLockPatternUtils.getKeyguardStoredPasswordQuality(this.mUserId);
            if (keyguardStoredPasswordQuality == 65536) {
                Context context = this.mContext;
                sb.append(
                        context.getString(
                                R.string.text_two_locktype_multifactor,
                                context.getString(R.string.sec_unlock_set_unlock_pattern_title)));
            } else if (keyguardStoredPasswordQuality == 131072
                    || keyguardStoredPasswordQuality == 196608) {
                Context context2 = this.mContext;
                sb.append(
                        context2.getString(
                                R.string.text_two_locktype_multifactor,
                                context2.getString(R.string.sec_unlock_set_unlock_pin_title)));
            } else if (keyguardStoredPasswordQuality == 262144
                    || keyguardStoredPasswordQuality == 327680
                    || keyguardStoredPasswordQuality == 393216) {
                Context context3 = this.mContext;
                sb.append(
                        context3.getString(
                                R.string.text_two_locktype_multifactor,
                                context3.getString(R.string.sec_unlock_set_unlock_password_title)));
            }
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        String language = this.mContext.getResources().getConfiguration().locale.getLanguage();
        String str =
                "ar".equals(language)
                        ? "، "
                        : "ja".equals(language) ? "、" : "zh".equals(language) ? "，" : ", ";
        int keyguardStoredPasswordQuality2 =
                this.mLockPatternUtils.getKeyguardStoredPasswordQuality(this.mUserId);
        if (keyguardStoredPasswordQuality2 == 65536) {
            sb2.append(this.mContext.getString(R.string.sec_unlock_set_unlock_pattern_title));
        } else if (keyguardStoredPasswordQuality2 == 131072
                || keyguardStoredPasswordQuality2 == 196608) {
            sb2.append(this.mContext.getString(R.string.sec_unlock_set_unlock_pin_title));
        } else if (keyguardStoredPasswordQuality2 == 262144
                || keyguardStoredPasswordQuality2 == 327680
                || keyguardStoredPasswordQuality2 == 393216) {
            sb2.append(this.mContext.getString(R.string.sec_unlock_set_unlock_password_title));
        } else if (keyguardStoredPasswordQuality2 == 458752) {
            return SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0
                    .m(this.mContext, R.string.unlock_set_unlock_cac_pin_title, sb2);
        }
        if (z2) {
            sb2.append(str);
            sb2.append(this.mContext.getString(R.string.sec_face_title));
        }
        if (z) {
            sb2.append(str);
            sb2.append(this.mContext.getString(R.string.sec_fingerprint));
        }
        return sb2.toString();
    }

    private int getLockType() {
        if (!this.mLockPatternUtils.isSecure(this.mUserId)) {
            return this.mLockPatternUtils.isLockScreenDisabled(this.mUserId)
                    ? R.string.sec_unlock_set_unlock_off_title
                    : R.string.sec_unlock_set_unlock_none_title;
        }
        int keyguardStoredPasswordQuality =
                this.mLockPatternUtils.getKeyguardStoredPasswordQuality(this.mUserId);
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                keyguardStoredPasswordQuality, "passwordQulity : ", TAG);
        if (keyguardStoredPasswordQuality == 65536) {
            return R.string.sec_unlock_set_unlock_pattern_title;
        }
        if (keyguardStoredPasswordQuality == 131072 || keyguardStoredPasswordQuality == 196608) {
            return R.string.sec_unlock_set_unlock_pin_title;
        }
        if (keyguardStoredPasswordQuality == 262144
                || keyguardStoredPasswordQuality == 327680
                || keyguardStoredPasswordQuality == 393216) {
            return R.string.sec_unlock_set_unlock_password_title;
        }
        if (keyguardStoredPasswordQuality != 458752) {
            return 0;
        }
        return R.string.unlock_set_unlock_cac_pin_title;
    }

    private boolean startFragment() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("fromScreenLock", true);
        bundle.putString("hierarchical_parent", this.mParentFragment.getClass().getName());
        FragmentActivity activity = this.mParentFragment.getActivity();
        if (!(activity instanceof SettingsActivity)) {
            Log.w(
                    TAG,
                    "Parent isn't SettingsActivity nor PreferenceActivity, thus there's no way to"
                        + " launch the given Fragment (name:"
                        + " com.android.settings.password.ChooseLockGeneric$ChooseLockGenericFragment,"
                        + " requestCode: 123)");
            return false;
        }
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(activity);
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName =
                "com.android.settings.password.ChooseLockGeneric$ChooseLockGenericFragment";
        launchRequest.mArguments = bundle;
        subSettingLauncher.setResultListener(this.mParentFragment, 123);
        subSettingLauncher.setTitleRes(R.string.sec_unlock_set_unlock_launch_picker_title, null);
        LifecycleOwner lifecycleOwner = this.mParentFragment;
        launchRequest.mSourceMetricsCategory =
                lifecycleOwner instanceof Instrumentable
                        ? ((Instrumentable) lifecycleOwner).getMetricsCategory()
                        : 0;
        subSettingLauncher.launch();
        if (this.mLockPatternUtils.isSecure(this.mUserId)) {
            activity.overridePendingTransition(0, 0);
        }
        return true;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SecPreferenceScreen secPreferenceScreen =
                (SecPreferenceScreen) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = secPreferenceScreen;
        if (secPreferenceScreen != null) {
            secPreferenceScreen.getClass();
            SecPreferenceUtils.applySummaryColor(secPreferenceScreen, true);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (Rune.isShopDemo(this.mContext)) {
            return 4;
        }
        boolean isLockSettingsBlockonDexMode =
                LockUtils.isLockSettingsBlockonDexMode(this.mContext);
        boolean isKioskModeEnabled = SemPersonaManager.isKioskModeEnabled(this.mContext);
        if (isLockSettingsBlockonDexMode || isKioskModeEnabled) {
            return 5;
        }
        return ((!UCMUtils.isEnforcedCredentialStorageExistAsUser(UserHandle.myUserId())
                                || UCMUtils.isSupportChangePin(this.mUserId))
                        && !LockUtils.isLockMenuDisabledByEdm(this.mContext))
                ? 0
                : 5;
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        if (!this.mLockPatternUtils.isSecure(this.mUserId)) {
            return this.mLockPatternUtils.isLockScreenDisabled(this.mUserId)
                    ? this.mContext.getString(R.string.sec_unlock_set_unlock_off_title)
                    : this.mContext.getString(R.string.sec_unlock_set_unlock_none_title);
        }
        boolean z = this.mLockPatternUtils.getBiometricState(1, this.mUserId) == 1;
        boolean z2 = this.mLockPatternUtils.getBiometricState(256, this.mUserId) == 1;
        if (SecurityUtils.isFingerprintDisabledByDPM(this.mContext, this.mUserId)) {
            z = false;
        }
        return getBiometricsSummary(
                z, SecurityUtils.isFaceDisabledByDPM(this.mContext, this.mUserId) ? false : z2);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!getPreferenceKey().equals(preference.getKey())) {
            return super.handlePreferenceTreeClick(preference);
        }
        if (!Rune.isSamsungDexMode(this.mContext)
                && LockUtils.isInMultiWindow(this.mParentFragment.getActivity())) {
            Toast.makeText(
                            this.mParentFragment.getActivity(),
                            R.string.lock_screen_doesnt_support_multi_window_text,
                            0)
                    .show();
            return true;
        }
        boolean z = this.mParentFragment instanceof SecurityAndPrivacySettings;
        LoggingHelper.insertEventLogging(z ? 9032 : 4400, z ? String.valueOf(56050) : "LSE4401");
        startFragment();
        return true;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    public void init(Fragment fragment) {
        this.mParentFragment = fragment;
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        this.mPreference.setVisible(isAvailable());
        if (getAvailabilityStatus() == 5) {
            this.mPreference.setEnabled(false);
        } else {
            this.mPreference.setEnabled(true);
        }
        super.updateState(preference);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public void init(Fragment fragment, int i) {
        this.mParentFragment = fragment;
        this.mRequestCode = i;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
