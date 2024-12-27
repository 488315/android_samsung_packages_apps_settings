package com.samsung.android.settings.biometrics;

import android.content.Context;
import android.os.PowerManager;
import android.os.UserHandle;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Toast;

import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

import com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsUtils;
import com.samsung.android.settings.biometrics.fingerprint.FingerprintShowIconSettings;
import com.samsung.android.settings.widget.SecRadioButtonPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BiometricsRadioButtonPreference extends SecRadioButtonPreference {
    public FingerprintShowIconSettings mListener;
    public Toast mToast;

    public BiometricsRadioButtonPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mListener = null;
        this.mToast = null;
    }

    @Override // com.samsung.android.settings.widget.SecRadioButtonPreference,
              // androidx.preference.SecCheckBoxPreference, androidx.preference.CheckBoxPreference,
              // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.itemView.setEnabled(true);
    }

    @Override // com.samsung.android.settings.widget.SecRadioButtonPreference,
              // androidx.preference.TwoStatePreference, androidx.preference.Preference
    public final void onClick() {
        int i = 0;
        FingerprintShowIconSettings fingerprintShowIconSettings = this.mListener;
        if (fingerprintShowIconSettings != null) {
            Log.d(
                    "FpstFingerprintShowIconSettings",
                    "onRadioButtonClicked : " + ((Object) getTitle()));
            String key = getKey();
            key.getClass();
            switch (key) {
                case "button_screen_off_always":
                case "button_lock_always":
                    i = 2;
                    break;
                case "button_lock_tap":
                case "button_screen_off_tap":
                    i = 1;
                    break;
            }
            if (getKey().contains("screen_off")) {
                FingerprintSettingsUtils.setFingerprintScreenOffIconAodValue(
                        fingerprintShowIconSettings.mContext,
                        i,
                        fingerprintShowIconSettings.mUserId);
            } else {
                FingerprintSettingsUtils.setFingerprintLockIconValue(
                        fingerprintShowIconSettings.mContext,
                        i,
                        fingerprintShowIconSettings.mUserId);
            }
            fingerprintShowIconSettings.updatePreference$9();
        }
    }

    @Override // androidx.preference.Preference
    public final void performClick() {
        super.performClick();
        Context context = getContext();
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        if (isEnabled()) {
            return;
        }
        if (powerManager.isPowerSaveMode()) {
            Toast toast = this.mToast;
            if (toast != null) {
                toast.cancel();
            }
            Toast makeText =
                    Toast.makeText(
                            context,
                            R.string.sec_biometircs_cannot_change_setting_by_powersaving_mode,
                            0);
            this.mToast = makeText;
            makeText.show();
            Log.d("BiometricsRestrictedSwitchPreference", "Power save mode is on");
            return;
        }
        if (FingerprintSettingsUtils.getFingerprintAlwaysOnBooleanValue(
                context, UserHandle.myUserId())) {
            return;
        }
        Toast toast2 = this.mToast;
        if (toast2 != null) {
            toast2.cancel();
        }
        Toast makeText2 =
                Toast.makeText(
                        context, R.string.sec_fingerprint_show_fingerprint_icon_aod_off_toast, 0);
        this.mToast = makeText2;
        makeText2.show();
        Log.d("BiometricsRestrictedSwitchPreference", "'Fingerprint always on' is on");
    }

    public BiometricsRadioButtonPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mListener = null;
        this.mToast = null;
    }

    public BiometricsRadioButtonPreference(Context context) {
        super(context);
        this.mListener = null;
        this.mToast = null;
    }
}
