package com.samsung.android.settings.theftprotection.timer;

import android.content.Intent;

import com.android.settings.SettingsActivity;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ProtectionTimerActivity extends SettingsActivity {
    @Override // com.android.settings.SettingsActivity, android.app.Activity
    public final Intent getIntent() {
        Intent intent = new Intent(super.getIntent());
        if (!intent.hasExtra(":settings:show_fragment")) {
            if (intent.getBooleanExtra("delay_running", false)) {
                intent.putExtra(":settings:show_fragment", ProtectionTimerFragment.class.getName());
                intent.putExtra(":settings:show_fragment_title", "Security delay in progress");
            } else {
                intent.putExtra(
                        ":settings:show_fragment",
                        ProtectionTimerDisclaimerFragment.class.getName());
                intent.putExtra(
                        ":settings:show_fragment_title",
                        "Security delay required to turn off Require biometrics for sensitive"
                            + " settings");
            }
        }
        return intent;
    }

    @Override // com.android.settings.SettingsActivity
    public final boolean isValidFragment(String str) {
        return ProtectionTimerDisclaimerFragment.class.getName().equals(str)
                || ProtectionTimerFragment.class.getName().equals(str);
    }
}
