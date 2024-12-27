package com.android.settings.notification;

import android.content.Intent;

import com.android.settings.R;
import com.android.settings.SettingsActivity;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RedactionSettingsStandalone extends SettingsActivity {
    @Override // com.android.settings.SettingsActivity, android.app.Activity
    public final Intent getIntent() {
        Intent intent = new Intent(super.getIntent());
        intent.putExtra(
                        ":settings:show_fragment",
                        RedactionInterstitial.RedactionInterstitialFragment.class.getName())
                .putExtra("extra_prefs_show_button_bar", true)
                .putExtra("extra_prefs_set_back_text", (String) null)
                .putExtra(
                        "extra_prefs_set_next_text",
                        getString(R.string.app_notifications_dialog_done));
        return intent;
    }

    @Override // com.android.settings.SettingsActivity
    public final boolean isValidFragment(String str) {
        return RedactionInterstitial.RedactionInterstitialFragment.class.getName().equals(str);
    }
}
