package com.android.settings.inputmethod;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;

import com.android.settings.SettingsActivity;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class InputMethodAndSubtypeEnablerActivity extends SettingsActivity {
    public static final String FRAGMENT_NAME = InputMethodAndSubtypeEnabler.class.getName();

    @Override // com.android.settings.SettingsActivity, android.app.Activity
    public final Intent getIntent() {
        Intent intent = new Intent(super.getIntent());
        if (!intent.hasExtra(":settings:show_fragment")) {
            intent.putExtra(":settings:show_fragment", FRAGMENT_NAME);
        }
        return intent;
    }

    @Override // com.android.settings.SettingsActivity
    public final boolean isValidFragment(String str) {
        return FRAGMENT_NAME.equals(str);
    }

    @Override // com.android.settings.SettingsActivity,
              // com.android.settings.core.SettingsBaseActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    @Override // android.app.Activity
    public final boolean onNavigateUp() {
        finish();
        return true;
    }
}
