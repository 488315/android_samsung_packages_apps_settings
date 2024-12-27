package com.samsung.android.settings.notification.zen;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.service.notification.ZenModeConfig;
import android.util.ArrayMap;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.notification.zen.AbstractZenModePreferenceController;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

import java.util.HashMap;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ZenModeSettingsHeaderPreferenceController
        extends AbstractZenModePreferenceController {
    @Override // com.android.settings.notification.zen.AbstractZenModePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "sec_zen_header_preference";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        boolean z;
        ArrayMap arrayMap;
        Uri uri;
        super.updateState(preference);
        ZenModeConfig zenModeConfig = this.mNotificationManager.getZenModeConfig();
        boolean z2 = (zenModeConfig == null || zenModeConfig.manualRule == null) ? false : true;
        boolean z3 = z2 && zenModeConfig.manualRule.conditionId == null;
        boolean z4 =
                z2
                        && (uri = zenModeConfig.manualRule.conditionId) != null
                        && ZenModeConfig.isValidCountdownConditionId(uri);
        if (zenModeConfig != null
                && zenModeConfig.manualRule == null
                && (arrayMap = zenModeConfig.automaticRules) != null
                && !arrayMap.isEmpty()) {
            Iterator it = zenModeConfig.automaticRules.values().iterator();
            while (it.hasNext()) {
                if (((ZenModeConfig.ZenRule) it.next()).isAutomaticActive()) {
                    z = true;
                    break;
                }
            }
        }
        z = false;
        String string = this.mContext.getString(R.string.dnd_off_no_schedule_intial_header);
        StringBuilder sb = new StringBuilder();
        new HashMap();
        String str = null;
        if (z3) {
            String str2 = zenModeConfig.manualRule.enabler;
            if (str2 != null) {
                PackageManager packageManager = this.mContext.getPackageManager();
                try {
                    str =
                            packageManager
                                    .getApplicationLabel(packageManager.getApplicationInfo(str2, 0))
                                    .toString();
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                sb.append(str);
                string = this.mContext.getString(R.string.sec_zen_mode_footer_by_app_name, sb);
            } else {
                string = this.mContext.getString(R.string.zen_mode_settings_dnd_manual_indefinite);
            }
        } else if (z4) {
            long tryParseCountdownConditionId =
                    ZenModeConfig.tryParseCountdownConditionId(
                            zenModeConfig.manualRule.conditionId);
            string =
                    this.mContext.getString(
                            R.string.sec_zen_mode_footer_until_time,
                            ZenModeConfig.getFormattedTime(
                                    this.mContext,
                                    tryParseCountdownConditionId,
                                    ZenModeConfig.isToday(tryParseCountdownConditionId),
                                    this.mContext.getUserId()));
        } else if (z) {
            String description =
                    ZenModeConfig.getDescription(this.mContext, true, zenModeConfig, false);
            Iterator it2 = zenModeConfig.automaticRules.values().iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                ZenModeConfig.ZenRule zenRule = (ZenModeConfig.ZenRule) it2.next();
                if (zenRule.isAutomaticActive() && description.equals(zenRule.name)) {
                    if (ZenModeConfig.isValidScheduleConditionId(zenRule.conditionId)) {
                        long nextChangeTime =
                                ZenModeConfig.toScheduleCalendar(zenRule.conditionId)
                                        .getNextChangeTime(System.currentTimeMillis());
                        Context context = this.mContext;
                        CharSequence formattedTime =
                                ZenModeConfig.getFormattedTime(
                                        context, nextChangeTime, true, context.getUserId());
                        if (ZenModeConfig.isToday(nextChangeTime)) {
                            sb.append(
                                    this.mContext
                                            .getResources()
                                            .getString(
                                                    R.string.dnd_on_schedule_on_today_header,
                                                    formattedTime));
                        } else {
                            sb.append(
                                    this.mContext
                                            .getResources()
                                            .getString(
                                                    R.string.dnd_on_schedule_on_next_day_header,
                                                    formattedTime));
                        }
                    } else {
                        boolean equals =
                                zenRule.pkg.equals(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
                        Resources resources = this.mContext.getResources();
                        String str3 = equals ? "com.samsung.android.app.routines" : zenRule.pkg;
                        PackageManager packageManager2 = this.mContext.getPackageManager();
                        try {
                            str =
                                    packageManager2
                                            .getApplicationLabel(
                                                    packageManager2.getApplicationInfo(str3, 0))
                                            .toString();
                        } catch (PackageManager.NameNotFoundException e2) {
                            e2.printStackTrace();
                        }
                        sb.append(
                                resources.getString(
                                        R.string.sec_zen_mode_footer_by_app_and_schedule_name,
                                        str,
                                        description));
                    }
                }
            }
            string = sb.toString();
        }
        preference.setTitle(string);
    }
}
