package com.android.settings.accessibility;

import android.os.Bundle;
import android.view.Menu;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.core.instrumentation.Instrumentable;

import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AccessibilitySettingsForSetupWizardActivity extends SettingsActivity {
    @Override // com.android.settings.SettingsActivity,
              // com.android.settings.core.SettingsBaseActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        findViewById(R.id.content_parent).setFitsSystemWindows(false);
    }

    @Override // android.app.Activity
    public final boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override // android.app.Activity
    public final boolean onNavigateUp() {
        onBackPressed();
        getWindow().getDecorView().sendAccessibilityEvent(32);
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.settings.SettingsActivity,
              // androidx.preference.PreferenceFragmentCompat.OnPreferenceStartFragmentCallback
    public final void onPreferenceStartFragment(
            PreferenceFragmentCompat preferenceFragmentCompat, Preference preference) {
        Bundle extras = preference.getExtras();
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putInt("help_uri_resource", 0);
        extras.putBoolean("need_search_icon_in_action_bar", false);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this);
        String fragment = preference.getFragment();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = fragment;
        launchRequest.mArguments = extras;
        launchRequest.mSourceMetricsCategory =
                preferenceFragmentCompat instanceof Instrumentable
                        ? ((Instrumentable) preferenceFragmentCompat).getMetricsCategory()
                        : 0;
        Bundle extras2 = getIntent().getExtras();
        Bundle bundle = new Bundle();
        for (String str : Arrays.asList("firstRun", "isSetupFlow")) {
            bundle.putBoolean(str, extras2.getBoolean(str, false));
        }
        launchRequest.mExtras = bundle;
        subSettingLauncher.launch();
    }

    @Override // android.app.Activity
    public final void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        setTitle(bundle.getCharSequence("activity_title"));
    }

    @Override // com.android.settings.SettingsActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity,
              // android.app.Activity
    public final void onSaveInstanceState(Bundle bundle) {
        bundle.putCharSequence("activity_title", getTitle());
        super.onSaveInstanceState(bundle);
    }
}
