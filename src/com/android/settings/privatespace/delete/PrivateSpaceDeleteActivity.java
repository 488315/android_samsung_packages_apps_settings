package com.android.settings.privatespace.delete;

import android.os.Bundle;

import androidx.navigation.NavHostController;
import androidx.navigation.NavInflater;
import androidx.navigation.fragment.NavHostFragment;

import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.settings.R;
import com.android.settings.SetupWizardUtils;
import com.android.settings.core.InstrumentedActivity;

import com.google.android.setupdesign.util.ThemeHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PrivateSpaceDeleteActivity extends InstrumentedActivity {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2040;
    }

    @Override // com.android.settings.core.InstrumentedActivity,
              // com.android.settingslib.core.lifecycle.ObservableActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        if (Flags.allowPrivateProfile() && android.multiuser.Flags.enablePrivateSpaceFeatures()) {
            setTheme(SetupWizardUtils.getTheme(this, getIntent()));
            ThemeHelper.trySetDynamicColor(this);
            super.onCreate(bundle);
            setContentView(R.layout.privatespace_setup_root);
            NavHostController navHostController$navigation_fragment_release =
                    ((NavHostFragment)
                                    getSupportFragmentManager()
                                            .findFragmentById(R.id.ps_nav_host_fragment))
                            .getNavHostController$navigation_fragment_release();
            navHostController$navigation_fragment_release.setGraph(
                    ((NavInflater)
                                    navHostController$navigation_fragment_release
                                            .navInflater$delegate.getValue())
                            .inflate(R.navigation.private_space_delete_nav),
                    null);
        }
    }
}
