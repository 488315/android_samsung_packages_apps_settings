package com.android.settings.remoteauth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavGraph;
import androidx.navigation.NavHostController;
import androidx.navigation.NavInflater;
import androidx.navigation.fragment.NavHostFragment;

import com.android.settings.R;
import com.android.settings.SetupWizardUtils;
import com.android.settings.core.InstrumentedActivity;

import com.google.android.setupdesign.util.ThemeHelper;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\b\u0017\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"
        },
        d2 = {
            "Lcom/android/settings/remoteauth/RemoteAuthActivity;",
            "Lcom/android/settings/core/InstrumentedActivity;",
            "<init>",
            "()V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public class RemoteAuthActivity extends InstrumentedActivity {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.core.InstrumentedActivity,
              // com.android.settingslib.core.lifecycle.ObservableActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setTheme(SetupWizardUtils.getTheme(this, getIntent()));
        ThemeHelper.trySetDynamicColor(this);
        setContentView(R.layout.remote_auth_root);
        Fragment findFragmentById =
                getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        Intrinsics.checkNotNull(
                findFragmentById,
                "null cannot be cast to non-null type"
                    + " androidx.navigation.fragment.NavHostFragment");
        NavHostController navHostController$navigation_fragment_release =
                ((NavHostFragment) findFragmentById)
                        .getNavHostController$navigation_fragment_release();
        NavGraph inflate =
                ((NavInflater)
                                navHostController$navigation_fragment_release.navInflater$delegate
                                        .getValue())
                        .inflate(R.navigation.remote_auth_navigation);
        inflate.setStartDestinationId(R.id.remote_auth_settings_fragment);
        navHostController$navigation_fragment_release.setGraph(inflate, null);
    }
}
