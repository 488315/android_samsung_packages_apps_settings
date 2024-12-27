package com.samsung.android.settings.notification.reminder;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.CompoundButton;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecDropDownPreference;
import androidx.preference.SecPreferenceScreen;
import androidx.preference.SwitchPreference;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.widget.SettingsMainSwitchBar;

import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.samsung.android.settings.notification.reminder.controller.NotificationReminderAppPickerPreferenceController;
import com.samsung.android.settings.notification.reminder.controller.NotificationReminderTimeIntervalController;
import com.sec.ims.configuration.DATA;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class NotificationReminderPreferenceFragment extends DashboardFragment
        implements CompoundButton.OnCheckedChangeListener {
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass1();
    public FragmentActivity mContext = null;
    public SecPreferenceScreen mNotificationReminderApps;
    public SecDropDownPreference mNotificationTimeInterval;
    public SettingsMainSwitchBar mSwitchBar;
    public SwitchPreference mVibrateReminderSwitch;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.notification.reminder.NotificationReminderPreferenceFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            ArrayList arrayList = new ArrayList();
            boolean z =
                    Settings.System.getInt(
                                    context.getContentResolver(),
                                    "notification_reminder_selectable",
                                    0)
                            == 1;
            String valueOf = String.valueOf(36414);
            String str = z ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
            StatusData statusData = new StatusData();
            statusData.mStatusValue = str;
            statusData.mStatusKey = valueOf;
            arrayList.add(statusData);
            return arrayList;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.getApplication();
        }
        getSettingsLifecycle();
        ArrayList arrayList = new ArrayList();
        arrayList.add(
                new NotificationReminderTimeIntervalController(
                        context, "notification_time_interval"));
        arrayList.add(
                new NotificationReminderAppPickerPreferenceController(
                        context, "notification_reminder_app_picker"));
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "NotificationReminderPreferenceFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 36046;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.notification_reminder;
    }

    public final void initUI$1() {
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        if (preferenceScreen != null) {
            this.mVibrateReminderSwitch =
                    (SwitchPreference)
                            preferenceScreen.findPreference("notification_reminder_vibrate");
            this.mNotificationTimeInterval =
                    (SecDropDownPreference)
                            preferenceScreen.findPreference("notification_time_interval");
            this.mNotificationReminderApps =
                    (SecPreferenceScreen)
                            preferenceScreen.findPreference("notification_reminder_app_picker");
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        SettingsMainSwitchBar settingsMainSwitchBar =
                ((SettingsActivity) getActivity()).mMainSwitch;
        this.mSwitchBar = settingsMainSwitchBar;
        settingsMainSwitchBar.show();
        super.onActivityCreated(bundle);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        Settings.System.putInt(
                this.mContext.getContentResolver(), "notification_reminder_selectable", z ? 1 : 0);
        setEnabledBadgePrefs(z);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        initUI$1();
        boolean z =
                Settings.System.getInt(
                                this.mContext.getContentResolver(),
                                "notification_reminder_selectable",
                                0)
                        == 1;
        this.mSwitchBar.setChecked(z);
        setEnabledBadgePrefs(z);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d("NotificationReminderPreferenceFragment", "onCreate()");
        this.mContext = getActivity();
        initUI$1();
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        this.mSwitchBar.hide();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        this.mSwitchBar.removeOnSwitchChangeListener(this);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.mSwitchBar.addOnSwitchChangeListener(this);
        boolean z =
                Settings.System.getInt(
                                this.mContext.getContentResolver(),
                                "notification_reminder_selectable",
                                0)
                        == 1;
        this.mSwitchBar.setChecked(z);
        setEnabledBadgePrefs(z);
    }

    public final void setEnabledBadgePrefs(boolean z) {
        try {
            this.mVibrateReminderSwitch.setEnabled(z);
            this.mNotificationTimeInterval.setEnabled(z);
            this.mNotificationReminderApps.setEnabled(z);
        } catch (NullPointerException e) {
            Log.d("NotificationReminderPreferenceFragment", e.toString());
        }
    }
}
