package com.android.settings.notification.zen;

import android.content.ComponentName;
import android.content.Context;
import android.net.Uri;
import android.service.notification.ZenModeConfig;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settingslib.core.lifecycle.Lifecycle;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenModeBehaviorFooterPreferenceController
        extends AbstractZenModePreferenceController {
    public final int mTitleRes;

    public ZenModeBehaviorFooterPreferenceController(Context context, Lifecycle lifecycle, int i) {
        super(context, "footer_preference", lifecycle);
        this.mTitleRes = i;
    }

    public final String getFooterText() {
        int i;
        int i2;
        int zenMode = getZenMode();
        if (zenMode != 2 && zenMode != 3) {
            return this.mContext.getString(this.mTitleRes);
        }
        ZenModeConfig zenModeConfig = this.mNotificationManager.getZenModeConfig();
        ZenModeConfig.ZenRule zenRule = zenModeConfig.manualRule;
        if (zenRule != null && ((i2 = zenRule.zenMode) == 2 || i2 == 3)) {
            Uri uri = zenRule.conditionId;
            String str = zenRule.enabler;
            if (str == null) {
                return this.mContext.getString(R.string.zen_mode_qs_set_behavior);
            }
            String ownerCaption =
                    ZenModeConfig.getOwnerCaption(
                            AbstractZenModePreferenceController.mZenModeConfigWrapper.mContext,
                            str);
            if (!ownerCaption.isEmpty()) {
                return this.mContext.getString(R.string.zen_mode_app_set_behavior, ownerCaption);
            }
        }
        for (ZenModeConfig.ZenRule zenRule2 : zenModeConfig.automaticRules.values()) {
            if (zenRule2.isAutomaticActive() && ((i = zenRule2.zenMode) == 2 || i == 3)) {
                ComponentName componentName = zenRule2.component;
                if (componentName != null) {
                    return this.mContext.getString(
                            R.string.zen_mode_app_set_behavior, componentName.getPackageName());
                }
            }
        }
        return this.mContext.getString(R.string.zen_mode_unknown_app_set_behavior);
    }

    @Override // com.android.settings.notification.zen.AbstractZenModePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "footer_preference";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        preference.setTitle(getFooterText());
    }
}
