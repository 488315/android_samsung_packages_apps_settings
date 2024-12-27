package com.android.settings;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

import com.android.settings.utils.VoiceSettingsActivity;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class AirplaneModeVoiceActivity extends VoiceSettingsActivity {
    @Override // com.android.settings.utils.VoiceSettingsActivity
    public final boolean onVoiceSettingInteraction(Intent intent) {
        if (intent.hasExtra("airplane_mode_enabled")) {
            ((ConnectivityManager) getSystemService("connectivity"))
                    .setAirplaneMode(intent.getBooleanExtra("airplane_mode_enabled", false));
            return true;
        }
        Log.v("AirplaneModeVoiceActivity", "Missing airplane mode extra");
        return true;
    }
}
