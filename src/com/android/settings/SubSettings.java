package com.android.settings;

import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class SubSettings extends SettingsActivity {
    @Override // com.android.settings.SettingsActivity
    public boolean isValidFragment(String str) {
        DialogFragment$$ExternalSyntheticOutline0.m("Launching fragment ", str, "SubSettings");
        return true;
    }

    @Override // android.app.Activity
    public final boolean onNavigateUp() {
        finish();
        return true;
    }
}
