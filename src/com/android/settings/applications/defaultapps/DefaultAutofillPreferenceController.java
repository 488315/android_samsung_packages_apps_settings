package com.android.settings.applications.defaultapps;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.autofill.AutofillManager;

import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceUtils;

import com.android.settingslib.applications.DefaultAppInfo;

import com.samsung.android.settings.widget.SecGearPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DefaultAutofillPreferenceController extends DefaultAppPreferenceController {
    public final AutofillManager mAutofillManager;

    public DefaultAutofillPreferenceController(Context context) {
        super(context);
        this.mAutofillManager =
                (AutofillManager) this.mContext.getSystemService(AutofillManager.class);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SecGearPreference secGearPreference =
                (SecGearPreference) preferenceScreen.findPreference(getPreferenceKey());
        if (secGearPreference != null) {
            SecPreferenceUtils.applySummaryColor(secGearPreference, true);
        }
    }

    @Override // com.android.settings.applications.defaultapps.DefaultAppPreferenceController
    public DefaultAppInfo getDefaultAppInfo() {
        String string =
                Settings.Secure.getString(this.mContext.getContentResolver(), "autofill_service");
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        return new DefaultAppInfo(
                this.mContext,
                this.mPackageManager,
                this.mUserId,
                ComponentName.unflattenFromString(string));
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return "default_autofill_main";
    }

    @Override // com.android.settings.applications.defaultapps.DefaultAppPreferenceController
    public Intent getSettingIntent(DefaultAppInfo defaultAppInfo) {
        if (defaultAppInfo == null) {
            return null;
        }
        return new DefaultAutofillPicker.AutofillSettingIntentProvider(
                        this.mContext, this.mUserId, defaultAppInfo.getKey())
                .getIntent();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public boolean isAvailable() {
        AutofillManager autofillManager = this.mAutofillManager;
        return autofillManager != null
                && autofillManager.hasAutofillFeature()
                && this.mAutofillManager.isAutofillSupported();
    }

    @Override // com.android.settings.applications.defaultapps.DefaultAppPreferenceController
    public final boolean showLabelAsTitle() {
        return true;
    }
}
