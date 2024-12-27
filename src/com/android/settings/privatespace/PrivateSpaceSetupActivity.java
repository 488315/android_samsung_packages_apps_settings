package com.android.settings.privatespace;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavHostController;
import androidx.navigation.NavInflater;
import androidx.navigation.fragment.NavHostFragment;

import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.settings.R;
import com.android.settings.SetupWizardUtils;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;

import com.google.android.setupdesign.util.ThemeHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PrivateSpaceSetupActivity extends FragmentActivity {
    public SettingsMetricsFeatureProvider mMetricsFeatureProvider;
    public NavHostFragment mNavHostFragment;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        if (i == 1 && i2 == -1) {
            this.mNavHostFragment
                    .getNavHostController$navigation_fragment_release()
                    .navigate(R.id.action_pre_finish_delay_fragment);
        } else if (i == 2) {
            if (i2 == -1) {
                this.mMetricsFeatureProvider.action((Context) this, 1889, true);
                this.mNavHostFragment
                        .getNavHostController$navigation_fragment_release()
                        .navigate(R.id.show_set_lock_fragment);
            } else {
                this.mMetricsFeatureProvider.action((Context) this, 1889, false);
                this.mNavHostFragment
                        .getNavHostController$navigation_fragment_release()
                        .navigate(R.id.action_advance_login_error);
            }
        }
        super.onActivityResult(i, i2, intent);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        if (Flags.allowPrivateProfile() && android.multiuser.Flags.enablePrivateSpaceFeatures()) {
            setTheme(SetupWizardUtils.getTheme(this, getIntent()));
            ThemeHelper.trySetDynamicColor(this);
            super.onCreate(bundle);
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
            setContentView(R.layout.privatespace_setup_root);
            NavHostFragment navHostFragment =
                    (NavHostFragment)
                            getSupportFragmentManager().findFragmentById(R.id.ps_nav_host_fragment);
            this.mNavHostFragment = navHostFragment;
            NavHostController navHostController$navigation_fragment_release =
                    navHostFragment.getNavHostController$navigation_fragment_release();
            navHostController$navigation_fragment_release.setGraph(
                    ((NavInflater)
                                    navHostController$navigation_fragment_release
                                            .navInflater$delegate.getValue())
                            .inflate(R.navigation.privatespace_main_context_nav),
                    null);
        }
    }
}
