package com.android.settings.applications;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.FeatureFlagUtils;

import com.android.settings.SettingsActivity;
import com.android.settings.SettingsApplication;
import com.android.settings.applications.appinfo.AppInfoDashboardFragment;
import com.android.settings.homepage.SettingsHomepageActivity;
import com.android.settings.homepage.TopLevelSettings;
import com.android.settings.spa.SpaActivity;
import com.android.settings.spa.app.appinfo.AppInfoSettingsProvider;

import com.google.android.material.appbar.AppBarLayout;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.applications.AppCommonUtils;

import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class InstalledAppDetailsTop extends SettingsActivity {
    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity
    public final boolean canUsePathProvider() {
        Set set = AppCommonUtils.SEARCH_CLASS_NAME_LIST;
        if (getCallingActivity() != null) {
            if (((ArraySet) AppCommonUtils.SEARCH_CLASS_NAME_LIST)
                    .contains(getCallingActivity().getClassName())) {
                return true;
            }
        }
        return super.canUsePathProvider();
    }

    @Override // com.android.settings.SettingsActivity, android.app.Activity
    public final Intent getIntent() {
        Intent intent = new Intent(super.getIntent());
        intent.putExtra(":settings:show_fragment", AppInfoDashboardFragment.class.getName());
        return intent;
    }

    @Override // com.android.settings.SettingsActivity
    public final boolean isValidFragment(String str) {
        return AppInfoDashboardFragment.class.getName().equals(str);
    }

    @Override // com.android.settings.SettingsActivity,
              // com.android.settings.core.SettingsBaseActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        SettingsHomepageActivity homeActivity;
        super.onCreate(bundle);
        Intent intent = getIntent();
        String stringExtra =
                intent.getExtras() == null
                        ? ApnSettings.MVNO_NONE
                        : intent.getStringExtra(":settings:hierarchy_parent_title");
        if (!TextUtils.isEmpty(stringExtra)
                && (homeActivity =
                                ((SettingsApplication) getApplicationContext()).getHomeActivity())
                        != null) {
            AppBarLayout appBarLayout = homeActivity.mAppBarLayout;
            if (appBarLayout != null) {
                appBarLayout.setExpanded(false);
            }
            TopLevelSettings topLevelSettings = homeActivity.mMainFragment;
            if (topLevelSettings != null) {
                topLevelSettings.setHighlightMenuKey(
                        topLevelSettings.findPreferenceKey(stringExtra));
            }
        }
        if (isFinishing() || !FeatureFlagUtils.isEnabled(this, "settings_enable_spa")) {
            return;
        }
        Uri data = super.getIntent().getData();
        if (data != null) {
            String route =
                    AppInfoSettingsProvider.INSTANCE.getRoute(
                            getUserId(), data.getSchemeSpecificPart());
            SpaActivity.Companion companion = SpaActivity.Companion;
            SpaActivity.Companion.startSpaActivity(this, route);
        }
        finish();
    }
}
