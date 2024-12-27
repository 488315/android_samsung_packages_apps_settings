package com.android.settings.development;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SelectDebugAppPreferenceController extends DeveloperOptionsPreferenceController
        implements PreferenceControllerMixin, OnActivityResultListener {
    public final DevelopmentSettingsDashboardFragment mFragment;
    public final PackageManager mPackageManager;

    public SelectDebugAppPreferenceController(
            Context context,
            DevelopmentSettingsDashboardFragment developmentSettingsDashboardFragment) {
        super(context);
        this.mFragment = developmentSettingsDashboardFragment;
        this.mPackageManager = this.mContext.getPackageManager();
    }

    public Intent getActivityStartIntent() {
        return new Intent(this.mContext, (Class<?>) AppPicker.class);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "debug_app";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), "debug_app")) {
            return false;
        }
        Intent activityStartIntent = getActivityStartIntent();
        activityStartIntent.putExtra("com.android.settings.extra.DEBUGGABLE", true);
        this.mFragment.startActivityForResult(activityStartIntent, 1);
        return true;
    }

    @Override // com.android.settings.development.OnActivityResultListener
    public final boolean onActivityResult(int i, int i2, Intent intent) {
        if (i != 1 || i2 != -1) {
            return false;
        }
        Settings.Global.putString(
                this.mContext.getContentResolver(), "debug_app", intent.getAction());
        updatePreferenceSummary();
        return true;
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        this.mPreference.setSummary(
                this.mContext.getResources().getString(R.string.debug_app_not_set));
    }

    public final void updatePreferenceSummary() {
        String string = Settings.Global.getString(this.mContext.getContentResolver(), "debug_app");
        if (string == null || string.length() <= 0) {
            this.mPreference.setSummary(
                    this.mContext.getResources().getString(R.string.debug_app_not_set));
            return;
        }
        Preference preference = this.mPreference;
        Resources resources = this.mContext.getResources();
        try {
            CharSequence applicationLabel =
                    this.mPackageManager.getApplicationLabel(
                            this.mPackageManager.getApplicationInfo(string, 512));
            if (applicationLabel != null) {
                string = applicationLabel.toString();
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        preference.setSummary(resources.getString(R.string.debug_app_set, string));
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        updatePreferenceSummary();
    }
}
