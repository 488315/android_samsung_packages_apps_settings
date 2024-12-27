package com.android.settings.applications;

import android.content.Intent;

import com.android.settings.SettingsActivity;
import com.android.settings.applications.intentpicker.AppLaunchSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class InstalledAppOpenByDefaultActivity extends SettingsActivity {
    @Override // com.android.settings.SettingsActivity, android.app.Activity
    public final Intent getIntent() {
        Intent intent = new Intent(super.getIntent());
        intent.putExtra(":settings:show_fragment", AppLaunchSettings.class.getName());
        return intent;
    }

    @Override // com.android.settings.SettingsActivity
    public final boolean isValidFragment(String str) {
        return AppLaunchSettings.class.getName().equals(str);
    }
}
