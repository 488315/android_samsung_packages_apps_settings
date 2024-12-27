package com.android.settings.wifi.dpp;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.FragmentManagerImpl;

import com.android.settings.R;
import com.android.settings.SetupWizardUtils;
import com.android.settings.core.InstrumentedActivity;

import com.google.android.setupdesign.util.ThemeHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class WifiDppBaseActivity extends InstrumentedActivity {
    public FragmentManagerImpl mFragmentManager;

    public abstract void handleIntent(Intent intent);

    @Override // android.app.Activity, android.view.ContextThemeWrapper
    public final void onApplyThemeResource(Resources.Theme theme, int i, boolean z) {
        theme.applyStyle(R.style.SetupWizardPartnerResource, true);
        super.onApplyThemeResource(theme, i, z);
    }

    @Override // com.android.settings.core.InstrumentedActivity,
              // com.android.settingslib.core.lifecycle.ObservableActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setTheme(SetupWizardUtils.getTheme(this, getIntent()));
        setTheme(2132083573);
        ThemeHelper.trySetDynamicColor(this);
        setContentView(R.layout.wifi_dpp_activity);
        this.mFragmentManager = getSupportFragmentManager();
        if (bundle == null) {
            handleIntent(getIntent());
        }
    }
}
