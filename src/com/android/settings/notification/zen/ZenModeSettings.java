package com.android.settings.notification.zen;

import android.app.AutomaticZenRule;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.service.notification.ZenModeConfig;
import android.util.Log;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentManager;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.SwitchPreferenceCompat;

import com.android.settings.R;
import com.android.settings.notification.NotificationBackend;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.core.lifecycle.Lifecycle;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.bixby.AppContext;
import com.samsung.android.settings.connection.moreconnection.EmergencyAlertsPreferenceController;
import com.samsung.android.settings.general.BaseResetSettingsData;
import com.samsung.android.settings.general.OnResetSettingsDataListener;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.samsung.android.settings.notification.reminder.NotificationUtils;
import com.samsung.android.settings.notification.zen.SecZenModeBypassingAppsController;
import com.samsung.android.settings.notification.zen.ZenModeEnableNowPreferenceController;
import com.samsung.android.settings.notification.zen.ZenModeSettingsHeaderPreferenceController;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ZenModeSettings extends ZenModeSettingsBase
        implements ZenRulePreference.OnPreferenceLongClickListener {
    public static final OnResetSettingsDataListener RESET_SETTINGS_DATA = new AnonymousClass1();
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass2();
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass3(R.xml.zen_mode_settings);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.notification.zen.ZenModeSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseResetSettingsData {
        @Override // com.samsung.android.settings.general.OnResetSettingsDataListener
        public final void resetSettings(Context context) {
            Log.d("ZenModeSettings", "reset DND based settings");
            NotificationManager.from(context).setZenMode(0, null, "ZenModeSettings");
            NotificationManager.Policy notificationPolicy =
                    NotificationManager.from(context).getNotificationPolicy();
            Set<Map.Entry<String, AutomaticZenRule>> entrySet =
                    NotificationManager.from(context).getAutomaticZenRules().entrySet();
            for (Map.Entry entry : (Map.Entry[]) entrySet.toArray(new Map.Entry[entrySet.size()])) {
                NotificationManager.from(context).removeAutomaticZenRule((String) entry.getKey());
            }
            OnResetSettingsDataListener onResetSettingsDataListener =
                    ZenModeSettings.RESET_SETTINGS_DATA;
            ZenModeConfig.ScheduleInfo scheduleInfo = new ZenModeConfig.ScheduleInfo();
            scheduleInfo.days = ZenModeConfig.ALL_DAYS;
            scheduleInfo.startHour = 22;
            scheduleInfo.startMinute = 0;
            scheduleInfo.endHour = 7;
            scheduleInfo.endMinute = 0;
            NotificationManager.from(context)
                    .addAutomaticZenRule(
                            new AutomaticZenRule(
                                    context.getResources().getString(17043652),
                                    ZenModeConfig.getScheduleConditionProvider(),
                                    null,
                                    ZenModeConfig.toScheduleConditionId(scheduleInfo),
                                    null,
                                    2,
                                    false));
            Settings.Global.putInt(context.getContentResolver(), "zen_duration", 0);
            int i = ((notificationPolicy.priorityCategories & (-32)) | 96) & (-385);
            int i2 = notificationPolicy.suppressedVisualEffects & (-512);
            if (Rune.NOTIS_DND_DEFAULT_VALUE) {
                i2 |= 20;
            }
            NotificationManager.from(context)
                    .setNotificationPolicy(
                            new NotificationManager.Policy(
                                    i,
                                    notificationPolicy.priorityCallSenders,
                                    notificationPolicy.priorityMessageSenders,
                                    i2,
                                    notificationPolicy.priorityConversationSenders));
            int i3 = NotificationUtils.mInstalledAppCountInWhiteList;
            PackageManager packageManager = context.getPackageManager();
            ZenModeBackend zenModeBackend = ZenModeBackend.getInstance(context);
            new NotificationBackend();
            List notificationPackagesList =
                    NotificationBackend.getNotificationPackagesList(context, context.getUserId());
            Intent intent = new Intent();
            intent.setPackage(EmergencyAlertsPreferenceController.AOSP_GOOGLE_CMAS_PACKAGE);
            ArrayList arrayList = (ArrayList) notificationPackagesList;
            arrayList.add(packageManager.resolveActivity(intent, 0));
            try {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    String str = ((ResolveInfo) it.next()).activityInfo.packageName;
                    int packageUidAsUser =
                            packageManager.getPackageUidAsUser(str, context.getUserId());
                    zenModeBackend.getClass();
                    if (ZenModeBackend.canAppBypassDnd(packageUidAsUser, str)) {
                        ZenModeBackend.setAppBypassDnd(packageUidAsUser, str, false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Settings.Global.putInt(
                    context.getContentResolver(), "zen_selected_exception_contacts_allowed", 0);
            Settings.Global.putInt(
                    context.getContentResolver(),
                    SecZenModeBypassingAppsController.ZEN_MODE_BYPASSING_APPS_DB_KEY,
                    0);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.notification.zen.ZenModeSettings$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            int i;
            ArrayList arrayList = new ArrayList();
            int i2 = 0;
            int i3 = Settings.Secure.getInt(context.getContentResolver(), "zen_duration", 0);
            String str = i3 < 0 ? "ask every time" : i3 == 0 ? "until I turn it off" : "time";
            String valueOf = String.valueOf(36301);
            StatusData statusData = new StatusData();
            statusData.mStatusValue = str;
            statusData.mStatusKey = valueOf;
            arrayList.add(statusData);
            Map<String, AutomaticZenRule> automaticZenRules =
                    NotificationManager.from(context).getAutomaticZenRules();
            if (automaticZenRules != null) {
                Iterator<Map.Entry<String, AutomaticZenRule>> it =
                        automaticZenRules.entrySet().iterator();
                i = 0;
                while (it.hasNext()) {
                    if (it.next().getValue() != null) {
                        i++;
                    }
                }
            } else {
                i = 0;
            }
            String valueOf2 = String.valueOf(36302);
            String valueOf3 = String.valueOf(i);
            StatusData statusData2 = new StatusData();
            statusData2.mStatusValue = valueOf3;
            statusData2.mStatusKey = valueOf2;
            arrayList.add(statusData2);
            Map<String, AutomaticZenRule> automaticZenRules2 =
                    NotificationManager.from(context).getAutomaticZenRules();
            if (automaticZenRules2 != null) {
                Iterator<Map.Entry<String, AutomaticZenRule>> it2 =
                        automaticZenRules2.entrySet().iterator();
                while (it2.hasNext()) {
                    AutomaticZenRule value = it2.next().getValue();
                    if (value != null && value.isEnabled()) {
                        i2++;
                    }
                }
            }
            String valueOf4 = String.valueOf(36303);
            String valueOf5 = String.valueOf(i2);
            StatusData statusData3 = new StatusData();
            statusData3.mStatusValue = valueOf5;
            statusData3.mStatusKey = valueOf4;
            arrayList.add(statusData3);
            return arrayList;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.notification.zen.ZenModeSettings$3, reason: invalid class name */
    public final class AnonymousClass3 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            return ZenModeSettings.buildPreferenceControllers(context, null, null, null);
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            ArrayList arrayList = (ArrayList) nonIndexableKeys;
            arrayList.add("zen_mode_duration_settings");
            arrayList.add("zen_mode_settings");
            arrayList.add("zen_call_header_text");
            return nonIndexableKeys;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SummaryBuilder {
        public static final int[] ALL_PRIORITY_CATEGORIES = {32, 64, 128, 4, 256, 2, 1, 8, 16};
        public final Context mContext;

        public SummaryBuilder(Context context) {
            this.mContext = context;
        }

        public static boolean isCategoryEnabled(NotificationManager.Policy policy, int i) {
            return (policy.priorityCategories & i) != 0;
        }

        public final String getCallsSettingSummary(NotificationManager.Policy policy) {
            ArrayList arrayList =
                    (ArrayList)
                            getEnabledCategories(
                                    policy,
                                    new ZenModeSettings$SummaryBuilder$$ExternalSyntheticLambda0(
                                            2));
            int size = arrayList.size();
            return size == 0
                    ? this.mContext.getString(R.string.zen_mode_none_calls)
                    : size == 1
                            ? this.mContext.getString(
                                    R.string.zen_mode_calls_summary_one, arrayList.get(0))
                            : this.mContext.getString(
                                    R.string.zen_mode_calls_summary_two,
                                    arrayList.get(0),
                                    arrayList.get(1));
        }

        public int getEnabledAutomaticRulesCount() {
            Map<String, AutomaticZenRule> automaticZenRules =
                    NotificationManager.from(this.mContext).getAutomaticZenRules();
            int i = 0;
            if (automaticZenRules != null) {
                Iterator<Map.Entry<String, AutomaticZenRule>> it =
                        automaticZenRules.entrySet().iterator();
                while (it.hasNext()) {
                    AutomaticZenRule value = it.next().getValue();
                    if (value != null && value.isEnabled()) {
                        i++;
                    }
                }
            }
            return i;
        }

        public final List getEnabledCategories(
                NotificationManager.Policy policy, Predicate predicate) {
            String string;
            ArrayList arrayList = new ArrayList();
            int[] iArr = ALL_PRIORITY_CATEGORIES;
            for (int i = 0; i < 9; i++) {
                int i2 = iArr[i];
                boolean isEmpty = arrayList.isEmpty();
                if (predicate.test(Integer.valueOf(i2))
                        && isCategoryEnabled(policy, i2)
                        && ((i2 != 16
                                        || !isCategoryEnabled(policy, 8)
                                        || policy.priorityCallSenders != 0)
                                && (i2 != 256
                                        || !isCategoryEnabled(policy, 256)
                                        || policy.priorityConversationSenders == 2))) {
                    if (i2 == 32) {
                        string =
                                isEmpty
                                        ? this.mContext.getString(R.string.zen_mode_alarms)
                                        : this.mContext.getString(R.string.zen_mode_alarms_list);
                    } else if (i2 == 64) {
                        string =
                                isEmpty
                                        ? this.mContext.getString(R.string.zen_mode_media)
                                        : this.mContext.getString(R.string.zen_mode_media_list);
                    } else if (i2 == 128) {
                        string =
                                isEmpty
                                        ? this.mContext.getString(R.string.zen_mode_system)
                                        : this.mContext.getString(R.string.zen_mode_system_list);
                    } else if (i2 == 4) {
                        int i3 = policy.priorityMessageSenders;
                        string =
                                i3 == 0
                                        ? this.mContext.getString(R.string.zen_mode_from_anyone)
                                        : i3 == 1
                                                ? this.mContext.getString(
                                                        R.string.zen_mode_from_contacts)
                                                : this.mContext.getString(
                                                        R.string.zen_mode_from_starred);
                    } else if (i2 == 256 && policy.priorityConversationSenders == 2) {
                        string =
                                isEmpty
                                        ? this.mContext.getString(
                                                R.string.zen_mode_from_important_conversations)
                                        : this.mContext.getString(
                                                R.string
                                                        .zen_mode_from_important_conversations_second);
                    } else if (i2 == 2) {
                        string =
                                isEmpty
                                        ? this.mContext.getString(R.string.zen_mode_events)
                                        : this.mContext.getString(R.string.zen_mode_events_list);
                    } else if (i2 == 1) {
                        string =
                                isEmpty
                                        ? this.mContext.getString(R.string.sec_zen_mode_reminders)
                                        : this.mContext.getString(R.string.zen_mode_reminders_list);
                    } else if (i2 == 8) {
                        int i4 = policy.priorityCallSenders;
                        string =
                                i4 == 0
                                        ? isEmpty
                                                ? this.mContext.getString(
                                                        R.string.zen_mode_from_anyone)
                                                : this.mContext.getString(
                                                        R.string.zen_mode_all_callers)
                                        : i4 == 1
                                                ? isEmpty
                                                        ? this.mContext.getString(
                                                                R.string.zen_mode_from_contacts)
                                                        : this.mContext.getString(
                                                                R.string.zen_mode_contacts_callers)
                                                : isEmpty
                                                        ? this.mContext.getString(
                                                                R.string.zen_mode_from_starred)
                                                        : this.mContext.getString(
                                                                R.string.zen_mode_starred_callers);
                    } else {
                        string =
                                i2 == 16
                                        ? isEmpty
                                                ? this.mContext.getString(
                                                        R.string.zen_mode_repeat_callers)
                                                : this.mContext.getString(
                                                        R.string.zen_mode_repeat_callers_list)
                                        : ApnSettings.MVNO_NONE;
                    }
                    arrayList.add(string);
                }
            }
            return arrayList;
        }

        public final String getMessagesSettingSummary(NotificationManager.Policy policy) {
            ArrayList arrayList =
                    (ArrayList)
                            getEnabledCategories(
                                    policy,
                                    new ZenModeSettings$SummaryBuilder$$ExternalSyntheticLambda0(
                                            0));
            int size = arrayList.size();
            return size == 0
                    ? this.mContext.getString(R.string.zen_mode_none_messages)
                    : size == 1
                            ? (String) arrayList.get(0)
                            : this.mContext.getString(
                                    R.string.zen_mode_calls_summary_two,
                                    arrayList.get(0),
                                    arrayList.get(1));
        }
    }

    public static List buildPreferenceControllers(
            Context context,
            Lifecycle lifecycle,
            FragmentManager fragmentManager,
            ZenModeSettings zenModeSettings) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ZenModePeoplePreferenceController(context, lifecycle));
        arrayList.add(new ZenModeBypassingAppsPreferenceController(context, lifecycle));
        arrayList.add(
                new ZenModeDurationPreferenceController(
                        context, "zen_mode_duration_settings", lifecycle));
        arrayList.add(
                new ZenModeSettingsHeaderPreferenceController(
                        context, "sec_zen_header_preference", lifecycle));
        arrayList.add(new ZenModeSoundVibrationPreferenceController(context, lifecycle));
        ZenModeEnableNowPreferenceController zenModeEnableNowPreferenceController =
                new ZenModeEnableNowPreferenceController(context, "enable_now", lifecycle);
        zenModeEnableNowPreferenceController.mFragment = fragmentManager;
        arrayList.add(zenModeEnableNowPreferenceController);
        arrayList.add(
                new ZenModeAutomaticRulesPreferenceController(
                        context, zenModeSettings, lifecycle, zenModeSettings));
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        return buildPreferenceControllers(
                context, getSettingsLifecycle(), getFragmentManager(), this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final AppContext getAppContext() {
        SwitchPreferenceCompat switchPreferenceCompat =
                (SwitchPreferenceCompat) findPreference("enable_now");
        if (switchPreferenceCompat == null) {
            return null;
        }
        AppContext.Builder builder = new AppContext.Builder();
        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("dndEnabeld", switchPreferenceCompat.isChecked() ? "true" : "false");
        jSONArray.put(jSONObject2);
        JSONArray jSONArray2 = builder.mLLMContext;
        jSONObject.put(
                "type",
                jSONArray.length() > 1
                        ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m(
                                "DoNotDisturbSettings", "[]")
                        : "DoNotDisturbSettings");
        int length = jSONArray.length();
        JSONObject jSONObject3 = jSONArray;
        if (length <= 1) {
            jSONObject3 = jSONObject2;
        }
        jSONObject.put("value", jSONObject3);
        jSONArray2.put(jSONObject);
        return builder.build();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 76;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.zen_mode_settings;
    }

    @Override // com.android.settings.SettingsPreferenceFragment
    public final boolean isPreferenceAnimationAllowed() {
        return false;
    }

    @Override // com.android.settings.notification.zen.ZenRulePreference.OnPreferenceLongClickListener
    public final void onPreferenceLongClick(ZenRulePreference zenRulePreference) {
        Bundle m =
                AbsAdapter$1$$ExternalSyntheticOutline0.m(
                        "selected_id", zenRulePreference.getKey());
        Intent intent = new Intent();
        intent.setClassName(
                KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                "com.android.settings.Settings$SecZenModeDeleteRulesSettingsActivity");
        intent.addFlags(67108864);
        intent.putExtra(":settings:show_fragment_args", m);
        startActivity(intent);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        if (!preference.getKey().equals("zen_mode_add_automatic_rule")) {
            return super.onPreferenceTreeClick(preference);
        }
        try {
            Intent addFlags = new Intent().addFlags(67108864);
            addFlags.setAction("android.settings.ZEN_MODE_SCHEDULE_RULE_SETTINGS");
            startActivity(addFlags);
            return true;
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            return true;
        }
    }
}
