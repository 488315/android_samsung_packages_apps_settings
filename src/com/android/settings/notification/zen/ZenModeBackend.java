package com.android.settings.notification.zen;

import android.app.ActivityManager;
import android.app.AutomaticZenRule;
import android.app.Flags;
import android.app.INotificationManager;
import android.app.NotificationManager;
import android.content.Context;
import android.database.Cursor;
import android.icu.text.MessageFormat;
import android.net.Uri;
import android.os.ServiceManager;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.service.notification.ZenModeConfig;
import android.util.Log;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenModeBackend {
    protected static final String ZEN_MODE_FROM_ANYONE = "zen_mode_from_anyone";
    protected static final String ZEN_MODE_FROM_CONTACTS = "zen_mode_from_contacts";
    public static final String ZEN_MODE_FROM_NONE = "zen_mode_from_none";
    protected static final String ZEN_MODE_FROM_STARRED = "zen_mode_from_starred";
    public static ZenModeBackend sInstance;
    public final Context mContext;
    public final NotificationManager mNotificationManager;
    public NotificationManager.Policy mPolicy;
    public int mZenMode;
    public static final INotificationManager sINM =
            INotificationManager.Stub.asInterface(ServiceManager.getService("notification"));
    public static final Comparator<Map.Entry<String, AutomaticZenRule>> RULE_COMPARATOR =
            new AnonymousClass1();
    public String mRuleId = null;
    public BixbyRoutineActionHandler mRSHandler = null;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.notification.zen.ZenModeBackend$1, reason: invalid class name */
    public final class AnonymousClass1 implements Comparator {
        public static String key(AutomaticZenRule automaticZenRule) {
            return (ZenModeConfig.isValidScheduleConditionId(automaticZenRule.getConditionId())
                            ? 1
                            : ZenModeConfig.isValidEventConditionId(
                                            automaticZenRule.getConditionId())
                                    ? 2
                                    : 3)
                    + automaticZenRule.getName().toString();
        }

        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            Map.Entry entry = (Map.Entry) obj;
            Map.Entry entry2 = (Map.Entry) obj2;
            List list = ZenModeConfig.DEFAULT_RULE_IDS;
            boolean contains = list.contains(entry.getKey());
            if (contains != list.contains(entry2.getKey())) {
                return contains ? -1 : 1;
            }
            int compare =
                    Long.compare(
                            ((AutomaticZenRule) entry.getValue()).getCreationTime(),
                            ((AutomaticZenRule) entry2.getValue()).getCreationTime());
            return compare != 0
                    ? compare
                    : key((AutomaticZenRule) entry.getValue())
                            .compareTo(key((AutomaticZenRule) entry2.getValue()));
        }
    }

    public ZenModeBackend(Context context) {
        this.mContext = context;
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService("notification");
        this.mNotificationManager = notificationManager;
        this.mZenMode =
                Settings.Global.getInt(context.getContentResolver(), "zen_mode", this.mZenMode);
        if (notificationManager != null) {
            this.mPolicy = notificationManager.getNotificationPolicy();
        }
    }

    public static boolean canAppBypassDnd(int i, String str) {
        try {
            return sINM.canAppBypassDnd(str, i);
        } catch (Exception e) {
            Log.w("ZenModeSettingsBackend", "Error calling NoMan", e);
            return false;
        }
    }

    public static ZenModeBackend getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ZenModeBackend(context);
        }
        return sInstance;
    }

    public static String getKeyFromSetting(int i) {
        return i != 0
                ? i != 1
                        ? i != 2 ? ZEN_MODE_FROM_NONE : ZEN_MODE_FROM_STARRED
                        : ZEN_MODE_FROM_CONTACTS
                : ZEN_MODE_FROM_ANYONE;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static int getSettingFromPrefKey(String str) {
        char c;
        switch (str.hashCode()) {
            case -946901971:
                if (str.equals(ZEN_MODE_FROM_NONE)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -423126328:
                if (str.equals(ZEN_MODE_FROM_CONTACTS)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 187510959:
                if (str.equals(ZEN_MODE_FROM_ANYONE)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 462773226:
                if (str.equals(ZEN_MODE_FROM_STARRED)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        if (c == 0) {
            return 0;
        }
        if (c != 1) {
            return c != 2 ? -1 : 2;
        }
        return 1;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static int getZenPolicySettingFromPrefKey(String str) {
        char c;
        switch (str.hashCode()) {
            case -946901971:
                if (str.equals(ZEN_MODE_FROM_NONE)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -423126328:
                if (str.equals(ZEN_MODE_FROM_CONTACTS)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 187510959:
                if (str.equals(ZEN_MODE_FROM_ANYONE)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 462773226:
                if (str.equals(ZEN_MODE_FROM_STARRED)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        if (c == 0) {
            return 1;
        }
        if (c != 1) {
            return c != 2 ? 4 : 3;
        }
        return 2;
    }

    public static void setAppBypassDnd(int i, String str, boolean z) {
        try {
            sINM.setAppBypassDnd(str, i, z);
        } catch (Exception e) {
            Log.w("ZenModeSettingsBackend", "Error calling NoMan", e);
        }
    }

    public final Map.Entry[] getAutomaticZenRules() {
        Map<String, AutomaticZenRule> automaticZenRules =
                NotificationManager.from(this.mContext).getAutomaticZenRules();
        Map.Entry[] entryArr =
                (Map.Entry[])
                        automaticZenRules
                                .entrySet()
                                .toArray(new Map.Entry[automaticZenRules.size()]);
        Arrays.sort(entryArr, RULE_COMPARATOR);
        return entryArr;
    }

    public final int getContactsSummary(int i) {
        int priorityCallSenders;
        if (i == 4) {
            if (isPriorityCategoryEnabled(i)) {
                priorityCallSenders = getPriorityMessageSenders();
            }
            priorityCallSenders = -1;
        } else {
            if (i == 8 && isPriorityCategoryEnabled(i)) {
                priorityCallSenders = getPriorityCallSenders();
            }
            priorityCallSenders = -1;
        }
        return priorityCallSenders != 0
                ? priorityCallSenders != 1
                        ? priorityCallSenders != 2
                                ? i == 4
                                        ? R.string.zen_mode_from_none_messages
                                        : R.string.zen_mode_from_none_calls
                                : R.string.zen_mode_from_starred
                        : R.string.zen_mode_from_contacts
                : R.string.zen_mode_from_anyone;
    }

    public final int getPriorityCallSenders() {
        if (isPriorityCategoryEnabled(8)) {
            return this.mPolicy.priorityCallSenders;
        }
        return -1;
    }

    public final int getPriorityMessageSenders() {
        if (isPriorityCategoryEnabled(4)) {
            return this.mPolicy.priorityMessageSenders;
        }
        return -1;
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

    public final String getStarredContactsSummary() {
        Cursor cursor;
        try {
            cursor =
                    this.mContext
                            .getContentResolver()
                            .query(
                                    ContactsContract.Contacts.CONTENT_URI,
                                    new String[] {"display_name"},
                                    "starred=1",
                                    null,
                                    "times_contacted");
            try {
                List<String> starredContacts = getStarredContacts(cursor);
                if (cursor != null) {
                    cursor.close();
                }
                int size = starredContacts.size();
                MessageFormat messageFormat =
                        new MessageFormat(
                                this.mContext.getString(
                                        R.string.zen_mode_starred_contacts_summary_contacts),
                                Locale.getDefault());
                HashMap hashMap = new HashMap();
                hashMap.put("count", Integer.valueOf(size));
                if (size >= 1) {
                    hashMap.put("contact_1", starredContacts.get(0));
                    if (size >= 2) {
                        hashMap.put("contact_2", starredContacts.get(1));
                        if (size == 3) {
                            hashMap.put("contact_3", starredContacts.get(2));
                        }
                    }
                }
                return messageFormat.format(hashMap);
            } catch (Throwable th) {
                th = th;
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            cursor = null;
        }
    }

    public final boolean isPriorityCategoryEnabled(int i) {
        return (this.mPolicy.priorityCategories & i) != 0;
    }

    public final void removeZenRule(String str) {
        if (Flags.modesApi()) {
            this.mNotificationManager.removeAutomaticZenRule(str, true);
        } else {
            NotificationManager.from(this.mContext).removeAutomaticZenRule(str);
        }
    }

    public final void savePolicyWithExceptions(
            int i, int i2, int i3, int i4, int i5, int i6, int i7, List list, int i8, List list2) {
        NotificationManager.Policy policy =
                new NotificationManager.Policy(i, i2, i3, i4, i5, i6, i7, list, i8, list2);
        this.mPolicy = policy;
        this.mNotificationManager.setNotificationPolicy(policy);
    }

    public final void saveSenders(int i, int i2) {
        String str;
        int i3;
        String str2;
        int i4;
        int priorityCallSenders = getPriorityCallSenders();
        int priorityMessageSenders = getPriorityMessageSenders();
        int priorityCallSenders2 =
                i == 8
                        ? getPriorityCallSenders()
                        : i == 4
                                ? getPriorityMessageSenders()
                                : i == 256
                                        ? isPriorityCategoryEnabled(256)
                                                ? this.mPolicy.priorityConversationSenders
                                                : 3
                                        : -1;
        List exceptionContacts =
                this.mNotificationManager.getNotificationPolicy().getExceptionContacts();
        NotificationManager.Policy policy = this.mPolicy;
        int i5 = policy.state;
        boolean z = i2 != -1;
        int i6 = i2 == -1 ? priorityCallSenders2 : i2;
        if (i == 8) {
            str = "Calls";
            i3 = i6;
        } else {
            str = ApnSettings.MVNO_NONE;
            i3 = priorityCallSenders;
        }
        if (i == 4) {
            str2 = "Messages";
            i4 = i6;
        } else {
            str2 = str;
            i4 = priorityMessageSenders;
        }
        int i7 = policy.priorityCategories;
        savePolicyWithExceptions(
                z ? i | i7 : (~i) & i7,
                i3,
                i4,
                policy.suppressedVisualEffects,
                i5,
                policy.priorityConversationSenders,
                policy.exceptionContactsFlag,
                exceptionContacts,
                policy.appBypassDndFlag,
                policy.getAppBypassDndList());
        if (ZenModeSettingsBase.DEBUG) {
            Log.d(
                    "ZenModeSettingsBackend",
                    "onPrefChange allow"
                            + str2
                            + "="
                            + z
                            + " allow"
                            + str2
                            + "From="
                            + ZenModeConfig.sourceToString(i6));
        }
    }

    public final void saveSoundPolicy(int i, boolean z) {
        NotificationManager.Policy policy = this.mPolicy;
        int i2 = policy.priorityCategories;
        int i3 = z ? i | i2 : (~i) & i2;
        int i4 = policy.priorityCallSenders;
        int i5 = policy.priorityMessageSenders;
        int i6 = policy.suppressedVisualEffects;
        int i7 = policy.state;
        int i8 = policy.priorityConversationSenders;
        int i9 = policy.exceptionContactsFlag;
        List exceptionContacts =
                this.mNotificationManager.getNotificationPolicy().getExceptionContacts();
        NotificationManager.Policy policy2 = this.mPolicy;
        savePolicyWithExceptions(
                i3,
                i4,
                i5,
                i6,
                i7,
                i8,
                i9,
                exceptionContacts,
                policy2.appBypassDndFlag,
                policy2.getAppBypassDndList());
    }

    public final void saveVisualEffectsPolicy(int i, boolean z) {
        Settings.Secure.putInt(this.mContext.getContentResolver(), "zen_settings_updated", 1);
        NotificationManager.Policy policy = this.mPolicy;
        int i2 = policy.suppressedVisualEffects;
        int i3 = (z ? i | i2 : (~i) & i2) & (-4);
        int i4 = policy.priorityCategories;
        int i5 = policy.priorityCallSenders;
        int i6 = policy.priorityMessageSenders;
        int i7 = policy.state;
        int i8 = policy.priorityConversationSenders;
        int i9 = policy.exceptionContactsFlag;
        List exceptionContacts =
                this.mNotificationManager.getNotificationPolicy().getExceptionContacts();
        NotificationManager.Policy policy2 = this.mPolicy;
        savePolicyWithExceptions(
                i4,
                i5,
                i6,
                i3,
                i7,
                i8,
                i9,
                exceptionContacts,
                policy2.appBypassDndFlag,
                policy2.getAppBypassDndList());
    }

    public final void setLifeStyleZenModeoff() {
        String str = this.mRuleId;
        if (str != null) {
            this.mNotificationManager.removeAutomaticZenRule(str);
            this.mRuleId = null;
            return;
        }
        Map<String, AutomaticZenRule> automaticZenRules =
                this.mNotificationManager.getAutomaticZenRules();
        if (automaticZenRules.isEmpty()) {
            return;
        }
        for (String str2 : automaticZenRules.keySet()) {
            AutomaticZenRule automaticZenRule = automaticZenRules.get(str2);
            if (!ZenModeConfig.isValidScheduleConditionId(automaticZenRule.getConditionId())
                    && automaticZenRule
                            .getPackageName()
                            .equals(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG)) {
                this.mNotificationManager.removeAutomaticZenRule(str2);
            }
        }
    }

    public final void setZenMode(int i) {
        if (Flags.modesApi()) {
            this.mNotificationManager.setZenMode(i, null, "ZenModeSettingsBackend", true);
        } else {
            NotificationManager.from(this.mContext).setZenMode(i, null, "ZenModeSettingsBackend");
        }
        this.mZenMode =
                Settings.Global.getInt(
                        this.mContext.getContentResolver(), "zen_mode", this.mZenMode);
    }

    public final void setZenModeForDuration(int i) {
        Uri uri =
                ZenModeConfig.toTimeCondition(
                                this.mContext, i, ActivityManager.getCurrentUser(), true)
                        .id;
        if (Flags.modesApi()) {
            this.mNotificationManager.setZenMode(1, uri, "ZenModeSettingsBackend", true);
        } else {
            this.mNotificationManager.setZenMode(1, uri, "ZenModeSettingsBackend");
        }
        this.mZenMode =
                Settings.Global.getInt(
                        this.mContext.getContentResolver(), "zen_mode", this.mZenMode);
    }

    public final void updateZenRule(String str, AutomaticZenRule automaticZenRule) {
        if (Flags.modesApi()) {
            this.mNotificationManager.updateAutomaticZenRule(str, automaticZenRule, true);
        } else {
            NotificationManager.from(this.mContext).updateAutomaticZenRule(str, automaticZenRule);
        }
    }
}
