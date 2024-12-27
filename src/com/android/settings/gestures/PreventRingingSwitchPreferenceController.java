package com.android.settings.gestures;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.widget.CompoundButton;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.widget.MainSwitchPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PreventRingingSwitchPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin, CompoundButton.OnCheckedChangeListener {
    public final Context mContext;
    MainSwitchPreference mSwitch;

    public PreventRingingSwitchPreferenceController(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        Preference findPreference;
        super.displayPreference(preferenceScreen);
        if (!isAvailable()
                || (findPreference =
                                preferenceScreen.findPreference("gesture_prevent_ringing_switch"))
                        == null) {
            return;
        }
        findPreference.setOnPreferenceClickListener(
                new Preference
                        .OnPreferenceClickListener() { // from class:
                                                       // com.android.settings.gestures.PreventRingingSwitchPreferenceController$$ExternalSyntheticLambda0
                    @Override // androidx.preference.Preference.OnPreferenceClickListener
                    public final boolean onPreferenceClick(Preference preference) {
                        PreventRingingSwitchPreferenceController
                                preventRingingSwitchPreferenceController =
                                        PreventRingingSwitchPreferenceController.this;
                        Settings.Secure.putInt(
                                preventRingingSwitchPreferenceController.mContext
                                        .getContentResolver(),
                                "volume_hush_gesture",
                                (Settings.Secure.getInt(
                                                                preventRingingSwitchPreferenceController
                                                                        .mContext
                                                                        .getContentResolver(),
                                                                "volume_hush_gesture",
                                                                1)
                                                        != 0
                                                ? 1
                                                : 0)
                                        ^ 1);
                        return true;
                    }
                });
        MainSwitchPreference mainSwitchPreference = (MainSwitchPreference) findPreference;
        this.mSwitch = mainSwitchPreference;
        mainSwitchPreference.setTitle(
                this.mContext.getString(R.string.prevent_ringing_main_switch_title));
        this.mSwitch.addOnSwitchChangeListener(this);
        updateState(this.mSwitch);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "gesture_prevent_ringing_switch";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return this.mContext
                .getResources()
                .getBoolean(android.R.bool.config_wirelessConsentRequired);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        int i =
                Settings.Secure.getInt(
                        this.mContext.getContentResolver(), "volume_hush_gesture", 1);
        int i2 = i != 0 ? i : 1;
        ContentResolver contentResolver = this.mContext.getContentResolver();
        if (!z) {
            i2 = 0;
        }
        Settings.Secure.putInt(contentResolver, "volume_hush_gesture", i2);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        boolean z =
                Settings.Secure.getInt(this.mContext.getContentResolver(), "volume_hush_gesture", 1)
                        != 0;
        MainSwitchPreference mainSwitchPreference = this.mSwitch;
        if (mainSwitchPreference != null) {
            mainSwitchPreference.updateStatus(z);
        }
    }
}
