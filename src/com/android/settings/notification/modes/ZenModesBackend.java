package com.android.settings.notification.modes;

import android.app.AutomaticZenRule;
import android.app.INotificationManager;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.database.Cursor;
import android.os.ServiceManager;
import android.service.notification.ZenModeConfig;

import com.android.settings.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenModesBackend {
    public static final INotificationManager sINM =
            INotificationManager.Stub.asInterface(ServiceManager.getService("notification"));
    public static ZenModesBackend sInstance;
    public final Context mContext;
    public final NotificationManager mNotificationManager;

    public ZenModesBackend(Context context) {
        this.mContext = context;
        this.mNotificationManager =
                (NotificationManager) context.getSystemService(NotificationManager.class);
    }

    public final ZenMode getManualDndMode(ZenModeConfig zenModeConfig) {
        ZenModeConfig.ZenRule zenRule = zenModeConfig.manualRule;
        return new ZenMode(
                "manual_dnd",
                new AutomaticZenRule.Builder(
                                this.mContext.getString(R.string.zen_mode_settings_title),
                                zenRule.conditionId)
                        .setType(zenRule.type)
                        .setZenPolicy(zenRule.zenPolicy)
                        .setDeviceEffects(zenRule.zenDeviceEffects)
                        .setManualInvocationAllowed(zenRule.allowManualInvocation)
                        .setConfigurationActivity((ComponentName) null)
                        .setInterruptionFilter(2)
                        .build(),
                zenModeConfig.isManualActive(),
                true);
    }

    public final ZenMode getMode(String str) {
        ZenModeConfig.ZenRule zenRule;
        ZenModeConfig zenModeConfig = this.mNotificationManager.getZenModeConfig();
        if ("manual_dnd".equals(str)) {
            return getManualDndMode(zenModeConfig);
        }
        AutomaticZenRule automaticZenRule = this.mNotificationManager.getAutomaticZenRule(str);
        if (automaticZenRule == null) {
            return null;
        }
        return new ZenMode(
                str,
                automaticZenRule,
                (zenModeConfig == null
                                || (zenRule =
                                                (ZenModeConfig.ZenRule)
                                                        zenModeConfig.automaticRules.get(str))
                                        == null
                                || !zenRule.isAutomaticActive())
                        ? false
                        : true,
                false);
    }

    public final List getModes() {
        ArrayList arrayList = new ArrayList();
        ZenModeConfig zenModeConfig = this.mNotificationManager.getZenModeConfig();
        arrayList.add(getManualDndMode(zenModeConfig));
        for (Map.Entry<String, AutomaticZenRule> entry :
                this.mNotificationManager.getAutomaticZenRules().entrySet()) {
            String key = entry.getKey();
            AutomaticZenRule value = entry.getValue();
            ZenModeConfig.ZenRule zenRule =
                    (ZenModeConfig.ZenRule) zenModeConfig.automaticRules.get(key);
            arrayList.add(
                    new ZenMode(key, value, zenRule != null && zenRule.isAutomaticActive(), false));
        }
        arrayList.sort(new ZenModesBackend$$ExternalSyntheticLambda0());
        return arrayList;
    }

    public List<String> getStarredContacts(Cursor cursor) {
        ArrayList arrayList = new ArrayList();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String string = cursor.getString(0);
                if (string == null) {
                    string = this.mContext.getString(R.string.zen_mode_starred_contacts_empty_name);
                }
                arrayList.add(string);
            } while (cursor.moveToNext());
        }
        return arrayList;
    }
}
