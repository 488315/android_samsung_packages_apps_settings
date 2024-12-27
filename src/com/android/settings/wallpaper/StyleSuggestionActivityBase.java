package com.android.settings.wallpaper;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.display.WallpaperPreferenceController;

import com.google.android.setupcompat.util.WizardManagerHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class StyleSuggestionActivityBase extends Activity {
    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        PackageManager packageManager = getPackageManager();
        Intent addFlags =
                new Intent()
                        .setComponent(
                                new WallpaperPreferenceController(this, "unused key")
                                        .getComponentName())
                        .addFlags(33554432);
        WizardManagerHelper.copyWizardManagerExtras(getIntent(), addFlags);
        addExtras(addFlags);
        if (packageManager.resolveActivity(addFlags, 0) != null) {
            startActivity(addFlags);
        } else {
            startFallbackSuggestion();
        }
        finish();
    }

    public void startFallbackSuggestion() {
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this);
        String name = WallpaperTypeSettings.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        subSettingLauncher.setTitleRes(R.string.wallpaper_suggestion_title, null);
        launchRequest.mSourceMetricsCategory = 35;
        subSettingLauncher.addFlags(33554432);
        subSettingLauncher.launch();
    }

    public void addExtras(Intent intent) {}
}
