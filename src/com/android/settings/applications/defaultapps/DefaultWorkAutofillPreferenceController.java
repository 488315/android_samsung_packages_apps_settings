package com.android.settings.applications.defaultapps;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.autofill.AutofillManager;

import com.android.settings.Utils;
import com.android.settingslib.applications.DefaultAppInfo;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DefaultWorkAutofillPreferenceController
        extends DefaultAutofillPreferenceController {
    public final AutofillManager mAutofillManager;
    public final UserHandle mUserHandle;

    public DefaultWorkAutofillPreferenceController(Context context) {
        super(context);
        UserHandle managedProfile = Utils.getManagedProfile(this.mUserManager);
        this.mUserHandle = managedProfile;
        if (managedProfile != null) {
            this.mAutofillManager =
                    (AutofillManager)
                            context.createContextAsUser(
                                            UserHandle.of(
                                                    Utils.getManagedProfileId(
                                                            UserManager.get(context),
                                                            UserHandle.myUserId())),
                                            0)
                                    .getSystemService(AutofillManager.class);
        } else {
            this.mAutofillManager =
                    (AutofillManager) context.getSystemService(AutofillManager.class);
        }
    }

    @Override // com.android.settings.applications.defaultapps.DefaultAutofillPreferenceController,
              // com.android.settings.applications.defaultapps.DefaultAppPreferenceController
    public final DefaultAppInfo getDefaultAppInfo() {
        String stringForUser =
                Settings.Secure.getStringForUser(
                        this.mContext.getContentResolver(),
                        "autofill_service",
                        this.mUserHandle.getIdentifier());
        if (TextUtils.isEmpty(stringForUser)) {
            return null;
        }
        return new DefaultAppInfo(
                this.mContext,
                this.mPackageManager,
                this.mUserHandle.getIdentifier(),
                ComponentName.unflattenFromString(stringForUser));
    }

    @Override // com.android.settings.applications.defaultapps.DefaultAutofillPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "default_autofill_work";
    }

    @Override // com.android.settings.applications.defaultapps.DefaultAutofillPreferenceController,
              // com.android.settings.applications.defaultapps.DefaultAppPreferenceController
    public final Intent getSettingIntent(DefaultAppInfo defaultAppInfo) {
        if (defaultAppInfo == null) {
            return null;
        }
        return new DefaultAutofillPicker.AutofillSettingIntentProvider(
                        this.mContext, this.mUserHandle.getIdentifier(), defaultAppInfo.getKey())
                .getIntent();
    }

    @Override // com.android.settings.applications.defaultapps.DefaultAutofillPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        AutofillManager autofillManager;
        return this.mUserHandle != null
                && (autofillManager = this.mAutofillManager) != null
                && autofillManager.hasAutofillFeature()
                && this.mAutofillManager.isAutofillSupported();
    }

    @Override // com.android.settings.applications.defaultapps.DefaultAppPreferenceController
    public final void startActivity(Intent intent) {
        this.mContext.startActivityAsUser(intent, this.mUserHandle);
    }
}
