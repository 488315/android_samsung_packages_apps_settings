package com.android.settings.datausage;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;

import com.android.settings.SettingsActivity;
import com.android.settingslib.AppItem;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AppDataUsageActivity extends SettingsActivity {
    @Override // com.android.settings.SettingsActivity
    public final boolean isValidFragment(String str) {
        return AppDataUsage.class.getName().equals(str);
    }

    @Override // com.android.settings.SettingsActivity,
              // com.android.settings.core.SettingsBaseActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        Intent intent = getIntent();
        if (intent == null || intent.getData() == null) {
            Log.d("AppDataUsageActivity", "intent data is null");
            try {
                try {
                    super.onCreate(bundle);
                } catch (Exception e) {
                    Log.d("AppDataUsageActivity", "onCreate() exception", e);
                }
                return;
            } finally {
                finish();
            }
        }
        String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
        try {
            int packageUid = getPackageManager().getPackageUid(schemeSpecificPart, 0);
            Bundle bundle2 = new Bundle();
            AppItem appItem = new AppItem(packageUid);
            appItem.addUid(packageUid);
            bundle2.putParcelable("app_item", appItem);
            intent.putExtra(":settings:show_fragment_args", bundle2);
            intent.putExtra(":settings:show_fragment", AppDataUsage.class.getName());
            super.onCreate(bundle);
        } catch (PackageManager.NameNotFoundException unused) {
            MotionLayout$$ExternalSyntheticOutline0.m(
                    "invalid package: ", schemeSpecificPart, "AppDataUsageActivity");
            try {
                super.onCreate(bundle);
            } catch (Exception unused2) {
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
