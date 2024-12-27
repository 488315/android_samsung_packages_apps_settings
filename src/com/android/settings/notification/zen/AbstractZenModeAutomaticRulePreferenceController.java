package com.android.settings.notification.zen;

import android.app.AutomaticZenRule;
import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.preference.Preference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AbstractZenModeAutomaticRulePreferenceController
        extends AbstractZenModePreferenceController {
    public ZenModeBackend mBackend;
    public Fragment mParent;
    public PackageManager mPm;

    public static ComponentName getSettingsActivity(
            PackageManager packageManager,
            AutomaticZenRule automaticZenRule,
            ComponentInfo componentInfo) {
        String string;
        ComponentName unflattenFromString;
        String packageName =
                automaticZenRule != null
                        ? automaticZenRule.getPackageName()
                        : componentInfo.packageName;
        if (automaticZenRule == null || automaticZenRule.getConfigurationActivity() == null) {
            if (componentInfo != null) {
                if (componentInfo instanceof ActivityInfo) {
                    unflattenFromString =
                            new ComponentName(componentInfo.packageName, componentInfo.name);
                } else {
                    Bundle bundle = componentInfo.metaData;
                    if (bundle != null
                            && (string =
                                            bundle.getString(
                                                    "android.service.zen.automatic.configurationActivity"))
                                    != null) {
                        unflattenFromString = ComponentName.unflattenFromString(string);
                    }
                }
            }
            unflattenFromString = null;
        } else {
            unflattenFromString = automaticZenRule.getConfigurationActivity();
        }
        if (unflattenFromString == null || packageName == null) {
            return unflattenFromString;
        }
        try {
            if (packageManager.getPackageUid(packageName, 0)
                    == packageManager.getPackageUid(unflattenFromString.getPackageName(), 0)) {
                return unflattenFromString;
            }
            Log.w(
                    "PrefControllerMixin",
                    "Config activity not in owner package for " + automaticZenRule.getName());
            return null;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e("PrefControllerMixin", "Failed to find config activity");
            return null;
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        this.mBackend.getAutomaticZenRules();
    }
}
