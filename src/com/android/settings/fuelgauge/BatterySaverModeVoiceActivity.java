package com.android.settings.fuelgauge;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.settings.utils.VoiceSettingsActivity;
import com.android.settingslib.fuelgauge.BatterySaverUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BatterySaverModeVoiceActivity extends VoiceSettingsActivity {
    @Override // com.android.settings.utils.VoiceSettingsActivity
    public final boolean onVoiceSettingInteraction(Intent intent) {
        if (!intent.hasExtra("android.settings.extra.battery_saver_mode_enabled")) {
            Log.v("BatterySaverModeVoiceActivity", "Missing battery saver mode extra");
        } else if (!BatterySaverUtils.setPowerSaveMode(
                2,
                this,
                intent.getBooleanExtra("android.settings.extra.battery_saver_mode_enabled", false),
                true)) {
            Log.v("BatterySaverModeVoiceActivity", "Unable to set power mode");
            if (getVoiceInteractor() != null) {
                getVoiceInteractor()
                        .submitRequest(
                                new VoiceInteractor.AbortVoiceRequest(
                                        (CharSequence) null, (Bundle) null));
            }
        } else if (getVoiceInteractor() != null) {
            getVoiceInteractor().submitRequest(new VoiceSettingsActivity.AnonymousClass1(null));
        }
        return true;
    }
}
