package com.android.settings.notification.zen;

import android.app.AutomaticZenRule;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.service.notification.ZenModeConfig;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;

import androidx.preference.PreferenceViewHolder;
import androidx.preference.SecSwitchPreferenceScreen;

import com.android.settings.R;
import com.android.settings.search.BaseSearchIndexProvider;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ZenRulePreference extends SecSwitchPreferenceScreen
        implements View.OnLongClickListener {
    public final ZenModeBackend mBackend;
    public final Context mContext;
    public final SimpleDateFormat mDayFormat;
    public final String mId;
    public CharSequence mName;
    public final OnPreferenceLongClickListener mPreferenceLongClickListner;
    public AutomaticZenRule mRule;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnPreferenceLongClickListener {
        void onPreferenceLongClick(ZenRulePreference zenRulePreference);
    }

    static {
        BaseSearchIndexProvider baseSearchIndexProvider =
                ZenModeAutomationSettings.SEARCH_INDEX_DATA_PROVIDER;
    }

    public ZenRulePreference(
            Context context,
            Map.Entry entry,
            ZenModeBackend zenModeBackend,
            OnPreferenceLongClickListener onPreferenceLongClickListener) {
        super(context);
        this.mDayFormat = new SimpleDateFormat("EEE");
        new SimpleDateFormat("EEE");
        this.mBackend = zenModeBackend;
        this.mContext = context;
        AutomaticZenRule automaticZenRule = (AutomaticZenRule) entry.getValue();
        this.mRule = automaticZenRule;
        this.mName = automaticZenRule.getName();
        String str = (String) entry.getKey();
        this.mId = str;
        setKey(str);
        setSummary(computeRuleSummary(this.mRule));
        setTitle(this.mName);
        setPersistent();
        setKey(str);
        this.mPreferenceLongClickListner = onPreferenceLongClickListener;
        setChecked(this.mRule.isEnabled());
    }

    public final String computeRuleSummary(AutomaticZenRule automaticZenRule) {
        int i;
        int i2;
        int i3;
        PackageManager packageManager = this.mContext.getPackageManager();
        String str = null;
        if (automaticZenRule != null) {
            ZenModeConfig.ScheduleInfo tryParseScheduleConditionId =
                    ZenModeConfig.tryParseScheduleConditionId(automaticZenRule.getConditionId());
            if (tryParseScheduleConditionId != null) {
                StringBuilder sb = new StringBuilder();
                int[] iArr = tryParseScheduleConditionId.days;
                if (iArr != null && iArr.length > 0) {
                    if (iArr.length == 7) {
                        str = this.mContext.getResources().getString(R.string.sec_dnd_every_day);
                    } else if (iArr.length == 2
                            && (((i2 = iArr[0]) == 1 || i2 == 7)
                                    && ((i3 = iArr[1]) == 1 || i3 == 7))) {
                        str = this.mContext.getResources().getString(R.string.sec_dnd_week_ends);
                    } else {
                        if (iArr.length == 5) {
                            for (int i4 : iArr) {
                                i = (i4 == 1 || i4 == 7) ? 0 : i + 1;
                            }
                            str =
                                    this.mContext
                                            .getResources()
                                            .getString(R.string.sec_dnd_week_days);
                        }
                        StringBuilder sb2 = new StringBuilder();
                        Calendar calendar = Calendar.getInstance();
                        int i5 = ZenModeScheduleDaysSelection.$r8$clinit;
                        int[] iArr2 = new int[7];
                        int firstDayOfWeek = calendar.getFirstDayOfWeek();
                        for (int i6 = 0; i6 < 7; i6++) {
                            if (firstDayOfWeek > 7) {
                                firstDayOfWeek = 1;
                            }
                            iArr2[i6] = firstDayOfWeek;
                            firstDayOfWeek++;
                        }
                        for (int i7 = 0; i7 < 7; i7++) {
                            int i8 = iArr2[i7];
                            int i9 = 0;
                            while (true) {
                                if (i9 >= iArr.length) {
                                    break;
                                }
                                if (i8 == iArr[i9]) {
                                    calendar.set(7, i8);
                                    if (sb2.length() > 0) {
                                        sb2.append(
                                                this.mContext.getString(
                                                        R.string.summary_divider_text));
                                    }
                                    sb2.append(this.mDayFormat.format(calendar.getTime()));
                                } else {
                                    i9++;
                                }
                            }
                        }
                        if (sb2.length() > 0) {
                            str = sb2.toString();
                        }
                    }
                }
                if (str != null) {
                    sb.append(str);
                    sb.append('\n');
                }
                int i10 = tryParseScheduleConditionId.startHour;
                int i11 = tryParseScheduleConditionId.startMinute;
                int i12 = tryParseScheduleConditionId.endHour;
                int i13 = tryParseScheduleConditionId.endMinute;
                StringBuilder sb3 = new StringBuilder();
                if (i10 == i12 && i11 == i13) {
                    sb3.append(
                            this.mContext
                                    .getResources()
                                    .getString(R.string.sec_dnd_settings_night_clock_all_day));
                } else {
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.clear();
                    calendar2.set(DateFormat.is24HourFormat(this.mContext) ? 11 : 10, i10);
                    calendar2.set(12, i11);
                    sb3.append(
                            DateFormat.getTimeFormat(this.mContext)
                                    .format(new Date(calendar2.getTimeInMillis())));
                    sb3.append(" ~ ");
                    calendar2.clear();
                    calendar2.set(DateFormat.is24HourFormat(this.mContext) ? 11 : 10, i12);
                    calendar2.set(12, i13);
                    String format =
                            DateFormat.getTimeFormat(this.mContext)
                                    .format(new Date(calendar2.getTimeInMillis()));
                    if ((i10 * 60) + i11 >= (i12 * 60) + i13) {
                        format =
                                this.mContext
                                        .getResources()
                                        .getString(
                                                R.string.zen_mode_end_time_next_day_summary_format,
                                                format);
                    }
                    sb3.append(format);
                }
                sb.append(sb3.toString());
                return sb.toString();
            }
            try {
                ApplicationInfo applicationInfo =
                        automaticZenRule.getOwner() != null
                                ? packageManager.getApplicationInfo(
                                        automaticZenRule.getOwner().getPackageName(), 0)
                                : null;
                if (automaticZenRule.getOwner() != null
                        && automaticZenRule
                                .getOwner()
                                .getPackageName()
                                .equals(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG)
                        && !ZenModeConfig.isValidScheduleConditionId(
                                automaticZenRule.getConditionId())) {
                    applicationInfo =
                            packageManager.getApplicationInfo(
                                    "com.samsung.android.app.routines", 0);
                }
                if (applicationInfo != null) {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(applicationInfo.loadLabel(packageManager));
                    sb4.append('\n');
                    sb4.append(this.mContext.getString(R.string.sec_dnd_app_rules));
                    int interruptionFilter = automaticZenRule.getInterruptionFilter();
                    String string =
                            interruptionFilter != 2
                                    ? interruptionFilter != 3
                                            ? interruptionFilter != 4
                                                    ? null
                                                    : this.mContext.getString(
                                                            R.string.zen_mode_option_alarms)
                                            : this.mContext.getString(
                                                    R.string.zen_mode_option_no_interruptions)
                                    : this.mContext.getString(
                                            R.string.zen_mode_option_important_interruptions);
                    if (string != null) {
                        sb4.append(this.mContext.getString(R.string.comma) + " ");
                        sb4.append(string);
                    }
                    return sb4.toString();
                }
            } catch (PackageManager.NameNotFoundException e) {
                Log.d("ZenRulePreference", "Expected package not found: " + e);
                this.mBackend.removeZenRule(this.mId);
            }
        }
        return null;
    }

    @Override // androidx.preference.SecSwitchPreferenceScreen,
              // androidx.preference.SeslSwitchPreferenceScreen,
              // androidx.preference.SwitchPreferenceCompat, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.itemView.setOnLongClickListener(this);
    }

    @Override // android.view.View.OnLongClickListener
    public final boolean onLongClick(View view) {
        this.mPreferenceLongClickListner.onPreferenceLongClick(this);
        return true;
    }
}
