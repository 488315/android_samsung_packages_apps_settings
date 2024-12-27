package com.android.settings.applications.autofill;

import android.content.Intent;
import android.os.Bundle;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.applications.credentials.DefaultCombinedPicker;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AutofillPickerActivity extends SettingsActivity {
    @Override // com.android.settings.SettingsActivity
    public final boolean isValidFragment(String str) {
        return super.isValidFragment(str) || DefaultCombinedPicker.class.getName().equals(str);
    }

    @Override // com.android.settings.SettingsActivity,
              // com.android.settings.core.SettingsBaseActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        Intent intent = getIntent();
        String schemeSpecificPart =
                intent.getData() != null ? intent.getData().getSchemeSpecificPart() : null;
        intent.putExtra(":settings:show_fragment", DefaultCombinedPicker.class.getName());
        intent.putExtra(":settings:show_fragment_title_resid", R.string.credman_picker_title);
        intent.putExtra("package_name", schemeSpecificPart);
        super.onCreate(bundle);
        if (intent.getData() == null) {
            finish();
        }
    }
}
