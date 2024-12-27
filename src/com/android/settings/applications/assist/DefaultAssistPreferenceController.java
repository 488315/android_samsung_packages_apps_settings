package com.android.settings.applications.assist;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.service.voice.VoiceInteractionServiceInfo;
import android.text.TextUtils;

import androidx.preference.Preference;

import com.android.internal.app.AssistUtils;
import com.android.settings.R;
import com.android.settings.applications.defaultapps.DefaultAppPreferenceController;
import com.android.settingslib.applications.DefaultAppInfo;

import com.samsung.android.feature.SemFloatingFeature;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DefaultAssistPreferenceController extends DefaultAppPreferenceController {
    public final AssistUtils mAssistUtils;
    public final Intent mIntent;
    public final String mPrefKey;
    public final boolean mShowSetting;

    public DefaultAssistPreferenceController(Context context) {
        super(context);
        this.mPrefKey = "default_assist";
        this.mShowSetting = true;
        this.mAssistUtils = new AssistUtils(context);
        String permissionControllerPackageName =
                this.mPackageManager.getPermissionControllerPackageName();
        if (permissionControllerPackageName != null) {
            this.mIntent =
                    new Intent("android.intent.action.MANAGE_DEFAULT_APP")
                            .setPackage(permissionControllerPackageName)
                            .putExtra(
                                    "android.intent.extra.ROLE_NAME", "android.app.role.ASSISTANT");
        } else {
            this.mIntent = null;
        }
    }

    public String getAssistSettingsActivity(
            ComponentName componentName, ResolveInfo resolveInfo, PackageManager packageManager) {
        VoiceInteractionServiceInfo voiceInteractionServiceInfo =
                new VoiceInteractionServiceInfo(packageManager, resolveInfo.serviceInfo);
        if (voiceInteractionServiceInfo.getSupportsAssist()) {
            return voiceInteractionServiceInfo.getSettingsActivity();
        }
        return null;
    }

    @Override // com.android.settings.applications.defaultapps.DefaultAppPreferenceController
    public final DefaultAppInfo getDefaultAppInfo() {
        AssistUtils assistUtils = this.mAssistUtils;
        int i = this.mUserId;
        ComponentName assistComponentForUser = assistUtils.getAssistComponentForUser(i);
        if (assistComponentForUser == null) {
            return null;
        }
        return new DefaultAppInfo(this.mContext, this.mPackageManager, i, assistComponentForUser);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return this.mPrefKey;
    }

    @Override // com.android.settings.applications.defaultapps.DefaultAppPreferenceController
    public final Intent getSettingIntent(DefaultAppInfo defaultAppInfo) {
        ComponentName assistComponentForUser;
        String assistSettingsActivity;
        if (!this.mShowSetting
                || (assistComponentForUser =
                                this.mAssistUtils.getAssistComponentForUser(this.mUserId))
                        == null) {
            return null;
        }
        List<ResolveInfo> queryIntentServices =
                this.mPackageManager.queryIntentServices(
                        new Intent("android.service.voice.VoiceInteractionService")
                                .setPackage(assistComponentForUser.getPackageName()),
                        128);
        if (queryIntentServices == null
                || queryIntentServices.isEmpty()
                || (assistSettingsActivity =
                                getAssistSettingsActivity(
                                        assistComponentForUser,
                                        queryIntentServices.get(0),
                                        this.mPackageManager))
                        == null) {
            return null;
        }
        return new Intent("android.intent.action.MAIN")
                .setComponent(
                        new ComponentName(
                                assistComponentForUser.getPackageName(), assistSettingsActivity));
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), "default_assist")) {
            return false;
        }
        Intent intent = this.mIntent;
        if (intent == null) {
            return true;
        }
        this.mContext.startActivity(intent);
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return this.mContext.getResources().getBoolean(R.bool.config_show_assist_and_voice_input)
                && !SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_AI_AGENT", false);
    }
}
