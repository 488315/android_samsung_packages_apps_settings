package com.samsung.android.settings.biometrics.fingerprint;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.UserHandle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.biometrics.BiometricsRadioButtonPreference;
import com.samsung.android.settings.lockscreen.LockUtils;
import com.samsung.android.settings.widget.SecDividerItemDecorator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class FingerprintShowIconSettings extends SettingsPreferenceFragment {
    public BiometricsRadioButtonPreference mButtonLockAlways;
    public BiometricsRadioButtonPreference mButtonLockNever;
    public BiometricsRadioButtonPreference mButtonLockTap;
    public BiometricsRadioButtonPreference mButtonScreenOffAlways;
    public BiometricsRadioButtonPreference mButtonScreenOffNever;
    public BiometricsRadioButtonPreference mButtonScreenOffTap;
    public Context mContext;
    public boolean mIsFromUsefulFeature;
    public PowerManager mPowerManager;
    public int mUserId;

    public final void cancelAndSessionEnd$5() {
        Log.d("FpstFingerprintShowIconSettings", "Cancel session");
        Intent intent = new Intent();
        intent.putExtra("reason", "cancelsession");
        setResult(0, intent);
        if (isFinishingOrDestroyed()) {
            return;
        }
        finish();
    }

    public final void disableScreenOffSetting(boolean z) {
        if (LockUtils.isSupportAodService()) {
            this.mButtonScreenOffAlways.setEnabled(z);
        }
        this.mButtonScreenOffTap.setEnabled(z);
        this.mButtonScreenOffNever.setEnabled(z);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return this.mIsFromUsefulFeature
                ? ApnSettings.MVNO_NONE
                : FingerprintSettings.class.getName();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 8288;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d("FpstFingerprintShowIconSettings", "onCreate()");
        this.mContext = getContext();
        this.mUserId = UserHandle.myUserId();
        addPreferencesFromResource(R.xml.sec_fingerprint_show_icon_settings);
        BiometricsRadioButtonPreference biometricsRadioButtonPreference =
                (BiometricsRadioButtonPreference) findPreference("button_lock_always");
        this.mButtonLockAlways = biometricsRadioButtonPreference;
        biometricsRadioButtonPreference.mListener = this;
        BiometricsRadioButtonPreference biometricsRadioButtonPreference2 =
                (BiometricsRadioButtonPreference) findPreference("button_lock_tap");
        this.mButtonLockTap = biometricsRadioButtonPreference2;
        biometricsRadioButtonPreference2.mListener = this;
        BiometricsRadioButtonPreference biometricsRadioButtonPreference3 =
                (BiometricsRadioButtonPreference) findPreference("button_lock_never");
        this.mButtonLockNever = biometricsRadioButtonPreference3;
        biometricsRadioButtonPreference3.mListener = this;
        if (LockUtils.isSupportAodService()) {
            BiometricsRadioButtonPreference biometricsRadioButtonPreference4 =
                    (BiometricsRadioButtonPreference) findPreference("button_screen_off_always");
            this.mButtonScreenOffAlways = biometricsRadioButtonPreference4;
            biometricsRadioButtonPreference4.mListener = this;
            BiometricsRadioButtonPreference biometricsRadioButtonPreference5 =
                    (BiometricsRadioButtonPreference) findPreference("button_screen_off_tap");
            this.mButtonScreenOffTap = biometricsRadioButtonPreference5;
            biometricsRadioButtonPreference5.mListener = this;
            BiometricsRadioButtonPreference biometricsRadioButtonPreference6 =
                    (BiometricsRadioButtonPreference) findPreference("button_screen_off_never");
            this.mButtonScreenOffNever = biometricsRadioButtonPreference6;
            biometricsRadioButtonPreference6.mListener = this;
        } else {
            removePreference("button_screen_off_always");
        }
        PowerManager powerManager = (PowerManager) getSystemService("power");
        this.mPowerManager = powerManager;
        if (powerManager != null
                && powerManager.isPowerSaveMode()
                && LockUtils.isSupportAodService()) {
            disableScreenOffSetting(false);
        }
        BroadcastReceiver broadcastReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.samsung.android.settings.biometrics.fingerprint.FingerprintShowIconSettings.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        PowerManager powerManager2 = FingerprintShowIconSettings.this.mPowerManager;
                        if (powerManager2 != null) {
                            if (powerManager2.isPowerSaveMode()) {
                                FingerprintShowIconSettings.this.disableScreenOffSetting(false);
                            } else {
                                FingerprintShowIconSettings.this.disableScreenOffSetting(true);
                            }
                        }
                    }
                };
        if (!FingerprintSettingsUtils.getFingerprintAlwaysOnBooleanValue(
                this.mContext, this.mUserId)) {
            disableScreenOffSetting(false);
        }
        this.mContext.registerReceiver(
                broadcastReceiver, new IntentFilter("android.os.action.POWER_SAVE_MODE_CHANGED"));
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.mUserId = arguments.getInt("android.intent.extra.USER_ID", UserHandle.myUserId());
            this.mIsFromUsefulFeature = arguments.getBoolean("fingerprint_useful_feature", false);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final void onMultiWindowModeChanged(boolean z) {
        super.onMultiWindowModeChanged(z);
        if (!z || Rune.isSamsungDexMode(this.mContext)) {
            return;
        }
        Toast.makeText(
                        this.mContext,
                        getString(R.string.sec_fingerprint_doesnt_support_multi_window_text),
                        0)
                .show();
        cancelAndSessionEnd$5();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        Log.d("FpstFingerprintShowIconSettings", "onPause");
        if (getActivity().isChangingConfigurations() || isFinishingOrDestroyed()) {
            return;
        }
        cancelAndSessionEnd$5();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        Log.d("FpstFingerprintShowIconSettings", "onResume");
        updatePreference$9();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        setDivider(null);
        getListView()
                .addItemDecoration(
                        new SecDividerItemDecorator(
                                (int)
                                        (getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .sec_widget_list_with_checkbox_size)
                                                + getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .sec_widget_list_with_checkbox_margin_end)
                                                + getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .sec_widget_list_with_checkbox_margin_start)),
                                getContext(),
                                getResources().getDrawable(R.drawable.sec_top_level_list_divider)));
    }

    public final void updatePreference$9() {
        this.mButtonLockAlways.setChecked(false);
        this.mButtonLockTap.setChecked(false);
        this.mButtonLockNever.setChecked(false);
        int fingerprintLockIconValue =
                FingerprintSettingsUtils.getFingerprintLockIconValue(this.mContext, this.mUserId);
        if (fingerprintLockIconValue == 0) {
            this.mButtonLockNever.setChecked(true);
        } else if (fingerprintLockIconValue == 1) {
            this.mButtonLockTap.setChecked(true);
        } else if (fingerprintLockIconValue == 2) {
            this.mButtonLockAlways.setChecked(true);
        }
        if (LockUtils.isSupportAodService()) {
            this.mButtonScreenOffAlways.setChecked(false);
            this.mButtonScreenOffTap.setChecked(false);
            this.mButtonScreenOffNever.setChecked(false);
            int fingerprintScreenOffIconAodDbValue =
                    FingerprintSettingsUtils.getFingerprintScreenOffIconAodDbValue(
                            this.mContext, this.mUserId);
            if (fingerprintScreenOffIconAodDbValue == 0) {
                this.mButtonScreenOffNever.setChecked(true);
            } else if (fingerprintScreenOffIconAodDbValue == 1) {
                this.mButtonScreenOffTap.setChecked(true);
            } else {
                if (fingerprintScreenOffIconAodDbValue != 2) {
                    return;
                }
                this.mButtonScreenOffAlways.setChecked(true);
            }
        }
    }
}
