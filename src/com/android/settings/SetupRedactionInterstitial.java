package com.android.settings;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.settings.notification.RedactionInterstitial;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class SetupRedactionInterstitial extends RedactionInterstitial {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class SetupRedactionInterstitialFragment
            extends RedactionInterstitial.RedactionInterstitialFragment {}

    public static void setEnabled(Context context, boolean z) {
        context.getPackageManager()
                .setComponentEnabledSetting(
                        new ComponentName(context, (Class<?>) SetupRedactionInterstitial.class),
                        z ? 1 : 2,
                        1);
    }

    @Override // com.android.settings.notification.RedactionInterstitial,
              // com.android.settings.SettingsActivity, android.app.Activity
    public final Intent getIntent() {
        Intent intent = new Intent(super.getIntent());
        intent.putExtra(
                ":settings:show_fragment", SetupRedactionInterstitialFragment.class.getName());
        return intent;
    }

    @Override // com.android.settings.notification.RedactionInterstitial,
              // com.android.settings.SettingsActivity
    public final boolean isValidFragment(String str) {
        return SetupRedactionInterstitialFragment.class.getName().equals(str);
    }

    @Override // com.android.settings.notification.RedactionInterstitial,
              // com.android.settings.SettingsActivity,
              // com.android.settings.core.SettingsBaseActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        if (!getIntent().getBooleanExtra("isSetupFlow", false)) {
            finish();
        }
        super.onCreate(bundle);
    }
}
