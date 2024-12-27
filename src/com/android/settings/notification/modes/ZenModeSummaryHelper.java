package com.android.settings.notification.modes;

import android.R;
import android.content.Context;
import android.service.notification.ZenPolicy;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenModeSummaryHelper {
    public static final int[] ALL_PRIORITY_CATEGORIES = {5, 6, 7, 2, 8, 1, 0, 3, 4};
    public final ZenModesBackend mBackend;
    public final Context mContext;

    public ZenModeSummaryHelper(Context context, ZenModesBackend zenModesBackend) {
        this.mContext = context;
        this.mBackend = zenModesBackend;
    }

    public final String getBlockedEffectsSummary(ZenMode zenMode) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(0);
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(4);
        arrayList.add(5);
        arrayList.add(6);
        if (this.mContext.getResources().getBoolean(R.bool.config_knownNetworksEnabledForService)) {
            arrayList.add(1);
        }
        ZenPolicy policy = zenMode.getPolicy();
        for (int i = 0; i < arrayList.size(); i++) {
            if (!policy.isVisualEffectAllowed(((Integer) arrayList.get(i)).intValue(), false)) {
                ZenPolicy policy2 = zenMode.getPolicy();
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    if (policy2.isVisualEffectAllowed(
                            ((Integer) arrayList.get(i2)).intValue(), false)) {
                        return this.mContext
                                .getResources()
                                .getString(
                                        com.android.settings.R.string
                                                .zen_mode_restrict_notifications_summary_custom);
                    }
                }
                return this.mContext
                        .getResources()
                        .getString(
                                com.android.settings.R.string
                                        .zen_mode_restrict_notifications_summary_hidden);
            }
        }
        return this.mContext
                .getResources()
                .getString(
                        com.android.settings.R.string
                                .zen_mode_restrict_notifications_summary_muted);
    }

    public final List getEnabledCategories(ZenPolicy zenPolicy, Predicate predicate) {
        ArrayList arrayList = new ArrayList();
        int[] iArr = ALL_PRIORITY_CATEGORIES;
        boolean z = false;
        int i = 0;
        while (i < 9) {
            int i2 = iArr[i];
            boolean isEmpty = arrayList.isEmpty();
            if (predicate.test(Integer.valueOf(i2))
                    && zenPolicy.isCategoryAllowed(i2, z)
                    && ((i2 != 4
                                    || !zenPolicy.isCategoryAllowed(3, z)
                                    || zenPolicy.getPriorityCallSenders() != 1)
                            && (i2 != 8
                                    || !zenPolicy.isCategoryAllowed(8, z)
                                    || zenPolicy.getPriorityConversationSenders() == 2))) {
                arrayList.add(
                        i2 == 5
                                ? isEmpty
                                        ? this.mContext.getString(
                                                com.android.settings.R.string
                                                        .zen_mode_alarms_list_first)
                                        : this.mContext.getString(
                                                com.android.settings.R.string.zen_mode_alarms_list)
                                : i2 == 6
                                        ? isEmpty
                                                ? this.mContext.getString(
                                                        com.android.settings.R.string
                                                                .zen_mode_media_list_first)
                                                : this.mContext.getString(
                                                        com.android.settings.R.string
                                                                .zen_mode_media_list)
                                        : i2 == 7
                                                ? isEmpty
                                                        ? this.mContext.getString(
                                                                com.android.settings.R.string
                                                                        .zen_mode_system_list_first)
                                                        : this.mContext.getString(
                                                                com.android.settings.R.string
                                                                        .zen_mode_system_list)
                                                : i2 == 2
                                                        ? zenPolicy.getPriorityMessageSenders() == 1
                                                                ? this.mContext.getString(
                                                                        com.android.settings.R
                                                                                .string
                                                                                .zen_mode_from_anyone)
                                                                : zenPolicy
                                                                                        .getPriorityMessageSenders()
                                                                                == 2
                                                                        ? this.mContext.getString(
                                                                                com.android.settings
                                                                                        .R.string
                                                                                        .zen_mode_from_contacts)
                                                                        : this.mContext.getString(
                                                                                com.android.settings
                                                                                        .R.string
                                                                                        .zen_mode_from_starred)
                                                        : (i2 == 8
                                                                        && zenPolicy
                                                                                        .getPriorityConversationSenders()
                                                                                == 2)
                                                                ? isEmpty
                                                                        ? this.mContext.getString(
                                                                                com.android.settings
                                                                                        .R.string
                                                                                        .zen_mode_from_important_conversations)
                                                                        : this.mContext.getString(
                                                                                com.android.settings
                                                                                        .R.string
                                                                                        .zen_mode_from_important_conversations_second)
                                                                : i2 == 1
                                                                        ? isEmpty
                                                                                ? this.mContext
                                                                                        .getString(
                                                                                                com
                                                                                                        .android
                                                                                                        .settings
                                                                                                        .R
                                                                                                        .string
                                                                                                        .zen_mode_events_list_first)
                                                                                : this.mContext
                                                                                        .getString(
                                                                                                com
                                                                                                        .android
                                                                                                        .settings
                                                                                                        .R
                                                                                                        .string
                                                                                                        .zen_mode_events_list)
                                                                        : i2 == 0
                                                                                ? isEmpty
                                                                                        ? this
                                                                                                .mContext
                                                                                                .getString(
                                                                                                        com
                                                                                                                .android
                                                                                                                .settings
                                                                                                                .R
                                                                                                                .string
                                                                                                                .zen_mode_reminders_list_first)
                                                                                        : this
                                                                                                .mContext
                                                                                                .getString(
                                                                                                        com
                                                                                                                .android
                                                                                                                .settings
                                                                                                                .R
                                                                                                                .string
                                                                                                                .zen_mode_reminders_list)
                                                                                : i2 == 3
                                                                                        ? zenPolicy
                                                                                                                .getPriorityCallSenders()
                                                                                                        == 1
                                                                                                ? isEmpty
                                                                                                        ? this
                                                                                                                .mContext
                                                                                                                .getString(
                                                                                                                        com
                                                                                                                                .android
                                                                                                                                .settings
                                                                                                                                .R
                                                                                                                                .string
                                                                                                                                .zen_mode_from_anyone)
                                                                                                        : this
                                                                                                                .mContext
                                                                                                                .getString(
                                                                                                                        com
                                                                                                                                .android
                                                                                                                                .settings
                                                                                                                                .R
                                                                                                                                .string
                                                                                                                                .zen_mode_all_callers)
                                                                                                : zenPolicy
                                                                                                                        .getPriorityCallSenders()
                                                                                                                == 2
                                                                                                        ? isEmpty
                                                                                                                ? this
                                                                                                                        .mContext
                                                                                                                        .getString(
                                                                                                                                com
                                                                                                                                        .android
                                                                                                                                        .settings
                                                                                                                                        .R
                                                                                                                                        .string
                                                                                                                                        .zen_mode_from_contacts)
                                                                                                                : this
                                                                                                                        .mContext
                                                                                                                        .getString(
                                                                                                                                com
                                                                                                                                        .android
                                                                                                                                        .settings
                                                                                                                                        .R
                                                                                                                                        .string
                                                                                                                                        .zen_mode_contacts_callers)
                                                                                                        : isEmpty
                                                                                                                ? this
                                                                                                                        .mContext
                                                                                                                        .getString(
                                                                                                                                com
                                                                                                                                        .android
                                                                                                                                        .settings
                                                                                                                                        .R
                                                                                                                                        .string
                                                                                                                                        .zen_mode_from_starred)
                                                                                                                : this
                                                                                                                        .mContext
                                                                                                                        .getString(
                                                                                                                                com
                                                                                                                                        .android
                                                                                                                                        .settings
                                                                                                                                        .R
                                                                                                                                        .string
                                                                                                                                        .zen_mode_starred_callers)
                                                                                        : i2 == 4
                                                                                                ? isEmpty
                                                                                                        ? this
                                                                                                                .mContext
                                                                                                                .getString(
                                                                                                                        com
                                                                                                                                .android
                                                                                                                                .settings
                                                                                                                                .R
                                                                                                                                .string
                                                                                                                                .zen_mode_repeat_callers)
                                                                                                        : this
                                                                                                                .mContext
                                                                                                                .getString(
                                                                                                                        com
                                                                                                                                .android
                                                                                                                                .settings
                                                                                                                                .R
                                                                                                                                .string
                                                                                                                                .zen_mode_repeat_callers_list)
                                                                                                : ApnSettings
                                                                                                        .MVNO_NONE);
            }
            i++;
            z = false;
        }
        return arrayList;
    }
}
