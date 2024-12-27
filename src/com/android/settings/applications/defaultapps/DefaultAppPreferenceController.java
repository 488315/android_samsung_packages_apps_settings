package com.android.settings.applications.defaultapps;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.applications.DefaultAppInfo;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.widget.TwoTargetPreference;

import com.samsung.android.settings.widget.SecGearPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class DefaultAppPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin {
    public final PackageManager mPackageManager;
    public final int mUserId;
    public final UserManager mUserManager;

    public DefaultAppPreferenceController(Context context) {
        super(context);
        this.mPackageManager = context.getPackageManager();
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mUserId = UserHandle.myUserId();
    }

    public abstract DefaultAppInfo getDefaultAppInfo();

    public Intent getSettingIntent(DefaultAppInfo defaultAppInfo) {
        return null;
    }

    public boolean showAppSummary() {
        return false;
    }

    public boolean showLabelAsTitle() {
        return false;
    }

    public void startActivity(Intent intent) {
        this.mContext.startActivity(intent);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        DefaultAppInfo defaultAppInfo;
        DefaultAppInfo defaultAppInfo2;
        DefaultAppInfo defaultAppInfo3;
        DefaultAppInfo defaultAppInfo4 = getDefaultAppInfo();
        CharSequence loadLabel =
                (isAvailable() && (defaultAppInfo = getDefaultAppInfo()) != null)
                        ? defaultAppInfo.loadLabel()
                        : null;
        if (preference instanceof TwoTargetPreference) {
            ((TwoTargetPreference) preference).mIconSize = 1;
        }
        if (TextUtils.isEmpty(loadLabel)) {
            Log.d("DefaultAppPrefControl", "No default app");
            if (showLabelAsTitle()) {
                preference.setTitle(R.string.app_list_preference_none);
                preference.setSummary((CharSequence) null);
            } else {
                preference.setSummary(R.string.app_list_preference_none);
            }
            preference.setIcon((Drawable) null);
        } else {
            if (showLabelAsTitle() && showAppSummary()) {
                preference.setTitle(loadLabel);
                preference.setSummary(
                        (isAvailable() && (defaultAppInfo3 = getDefaultAppInfo()) != null)
                                ? defaultAppInfo3.summary
                                : null);
            } else if (showLabelAsTitle()) {
                preference.setTitle(loadLabel);
            } else {
                preference.setSummary(loadLabel);
            }
            preference.setIcon(
                    Utils.getSafeIcon(
                            (isAvailable() && (defaultAppInfo2 = getDefaultAppInfo()) != null)
                                    ? defaultAppInfo2.loadIcon()
                                    : null));
        }
        if (preference instanceof SecGearPreference) {
            final Intent settingIntent = getSettingIntent(defaultAppInfo4);
            if (settingIntent != null) {
                SecGearPreference secGearPreference = (SecGearPreference) preference;
                secGearPreference.mOnGearClickListener =
                        new SecGearPreference
                                .OnGearClickListener() { // from class:
                                                         // com.android.settings.applications.defaultapps.DefaultAppPreferenceController$$ExternalSyntheticLambda0
                            @Override // com.samsung.android.settings.widget.SecGearPreference.OnGearClickListener
                            public final void onGearClick(SecGearPreference secGearPreference2) {
                                DefaultAppPreferenceController.this.startActivity(settingIntent);
                            }
                        };
                secGearPreference.notifyChanged();
            } else {
                SecGearPreference secGearPreference2 = (SecGearPreference) preference;
                secGearPreference2.mOnGearClickListener = null;
                secGearPreference2.notifyChanged();
            }
        }
    }
}
