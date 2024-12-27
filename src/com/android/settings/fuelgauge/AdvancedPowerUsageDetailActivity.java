package com.android.settings.fuelgauge;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.Utils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AdvancedPowerUsageDetailActivity extends AppCompatActivity {
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        Uri data = intent == null ? null : intent.getData();
        String schemeSpecificPart = data == null ? null : data.getSchemeSpecificPart();
        if (schemeSpecificPart != null) {
            Bundle bundle2 = new Bundle(4);
            PackageManager packageManager = getPackageManager();
            bundle2.putString("extra_package_name", schemeSpecificPart);
            bundle2.putString("extra_power_usage_percent", Utils.formatPercentage(0));
            if (intent.getBooleanExtra("request_ignore_background_restriction", false)) {
                bundle2.putString(":settings:fragment_args_key", "background_activity");
            }
            try {
                bundle2.putInt("extra_uid", packageManager.getPackageUid(schemeSpecificPart, 0));
            } catch (PackageManager.NameNotFoundException e) {
                Log.w(
                        "AdvancedPowerDetailActivity",
                        "Cannot find package: ".concat(schemeSpecificPart),
                        e);
            }
            SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this);
            String name = AdvancedPowerUsageDetail.class.getName();
            SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
            launchRequest.mDestinationName = name;
            subSettingLauncher.setTitleRes(R.string.battery_details_title, null);
            launchRequest.mArguments = bundle2;
            launchRequest.mSourceMetricsCategory = 20;
            subSettingLauncher.addFlags(intent.getFlags());
            subSettingLauncher.launch();
        }
        finish();
    }
}
