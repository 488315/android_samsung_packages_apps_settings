package com.android.settings.development;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.text.TextUtils;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class MockLocationAppPreferenceController extends DeveloperOptionsPreferenceController
        implements PreferenceControllerMixin, OnActivityResultListener {
    public static final int[] MOCK_LOCATION_APP_OPS = {58};
    public final AppOpsManager mAppsOpsManager;
    public final DevelopmentSettingsDashboardFragment mFragment;
    public final PackageManager mPackageManager;

    public MockLocationAppPreferenceController(
            Context context,
            DevelopmentSettingsDashboardFragment developmentSettingsDashboardFragment) {
        super(context);
        this.mFragment = developmentSettingsDashboardFragment;
        this.mAppsOpsManager = (AppOpsManager) context.getSystemService("appops");
        this.mPackageManager = context.getPackageManager();
    }

    public String getCurrentMockLocationApp() {
        List<AppOpsManager.PackageOps> packagesForOps =
                this.mAppsOpsManager.getPackagesForOps(MOCK_LOCATION_APP_OPS);
        if (packagesForOps == null) {
            return null;
        }
        for (AppOpsManager.PackageOps packageOps : packagesForOps) {
            if (((AppOpsManager.OpEntry) packageOps.getOps().get(0)).getMode() == 0) {
                return packageOps.getPackageName();
            }
        }
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "mock_location_app";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), "mock_location_app")) {
            return false;
        }
        Intent intent = new Intent(this.mContext, (Class<?>) AppPicker.class);
        intent.putExtra(
                "com.android.settings.extra.REQUESTIING_PERMISSION",
                "android.permission.ACCESS_MOCK_LOCATION");
        this.mFragment.startActivityForResult(intent, 2);
        return true;
    }

    @Override // com.android.settings.development.OnActivityResultListener
    public final boolean onActivityResult(int i, int i2, Intent intent) {
        if (i != 2 || i2 != -1) {
            return false;
        }
        String action = intent.getAction();
        removeAllMockLocations();
        if (!TextUtils.isEmpty(action)) {
            try {
                this.mAppsOpsManager.setMode(
                        58, this.mPackageManager.getApplicationInfo(action, 512).uid, action, 0);
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        updateMockLocation();
        return true;
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsDisabled() {
        super.onDeveloperOptionsDisabled();
        removeAllMockLocations();
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsEnabled() {
        int enterprisePolicyEnabled =
                Utils.getEnterprisePolicyEnabled(
                        this.mContext,
                        "content://com.sec.knox.provider/RestrictionPolicy2",
                        "isMockLocationEnabled",
                        null);
        if (enterprisePolicyEnabled != -1) {
            this.mPreference.setEnabled(enterprisePolicyEnabled == 1);
        } else {
            super.onDeveloperOptionsEnabled();
        }
    }

    public final void removeAllMockLocations() {
        List<AppOpsManager.PackageOps> packagesForOps =
                this.mAppsOpsManager.getPackagesForOps(MOCK_LOCATION_APP_OPS);
        if (packagesForOps == null) {
            return;
        }
        for (AppOpsManager.PackageOps packageOps : packagesForOps) {
            if (((AppOpsManager.OpEntry) packageOps.getOps().get(0)).getMode() != 2) {
                String packageName = packageOps.getPackageName();
                try {
                    this.mAppsOpsManager.setMode(
                            58,
                            this.mPackageManager.getApplicationInfo(packageName, 512).uid,
                            packageName,
                            2);
                } catch (PackageManager.NameNotFoundException unused) {
                }
            }
        }
    }

    public final void updateMockLocation() {
        String currentMockLocationApp = getCurrentMockLocationApp();
        if (TextUtils.isEmpty(currentMockLocationApp)) {
            this.mPreference.setSummary(
                    this.mContext.getResources().getString(R.string.mock_location_app_not_set));
            return;
        }
        Preference preference = this.mPreference;
        Resources resources = this.mContext.getResources();
        try {
            CharSequence applicationLabel =
                    this.mPackageManager.getApplicationLabel(
                            this.mPackageManager.getApplicationInfo(currentMockLocationApp, 512));
            if (applicationLabel != null) {
                currentMockLocationApp = applicationLabel.toString();
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        preference.setSummary(
                resources.getString(R.string.mock_location_app_set, currentMockLocationApp));
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        updateMockLocation();
        int enterprisePolicyEnabled =
                Utils.getEnterprisePolicyEnabled(
                        this.mContext,
                        "content://com.sec.knox.provider/RestrictionPolicy2",
                        "isMockLocationEnabled",
                        null);
        if (enterprisePolicyEnabled != -1) {
            preference.setEnabled(enterprisePolicyEnabled == 1);
        }
    }
}
