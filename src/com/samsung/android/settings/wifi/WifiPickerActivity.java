package com.samsung.android.settings.wifi;

import android.content.Intent;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.wifi.WifiSettings;
import com.android.settings.wifi.p2p.WifiP2pSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiPickerActivity extends SettingsActivity {
    @Override // com.android.settings.SettingsActivity, android.app.Activity
    public final Intent getIntent() {
        Intent intent = new Intent(super.getIntent());
        if (!intent.hasExtra(":settings:show_fragment")) {
            intent.putExtra(":settings:show_fragment", WifiSettings.class.getName());
            intent.putExtra(":settings:show_fragment_title_resid", R.string.wifi_settings_title);
        }
        return intent;
    }

    @Override // com.android.settings.SettingsActivity
    public final boolean isValidFragment(String str) {
        return WifiSettings.class.getName().equals(str)
                || WifiP2pSettings.class.getName().equals(str);
    }
}
