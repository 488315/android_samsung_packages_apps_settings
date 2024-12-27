package com.samsung.android.settings.lockscreen;

import android.app.admin.DevicePolicyManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.appcompat.widget.SeslToggleSwitch;
import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceCategory;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.Utils;
import com.android.settings.accessibility.AccessibilitySettings$$ExternalSyntheticOutline0;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.notification.ConfigureNotificationSettings;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.general.BaseResetSettingsData;
import com.samsung.android.settings.general.OnResetSettingsDataListener;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.lockscreen.ShowNotificationContentPreference.GetAppListAsyncTask;
import com.samsung.android.settings.lockscreen.controller.LockScreenNotificationStyleController;
import com.samsung.android.settings.lockscreen.controller.LockScreenNotificationStyleDotDescriptionController;
import com.samsung.android.settings.lockscreen.controller.NotiStyleDividerController;
import com.samsung.android.settings.lockscreen.controller.SensitiveWorkProfileNotificationPreferenceController;
import com.samsung.android.settings.lockscreen.controller.ShowAlertNotificationsController;
import com.samsung.android.settings.lockscreen.controller.ShowContentWhenUnlockedController;
import com.samsung.android.settings.lockscreen.controller.ShowOnLockScreenNotificationPreferenceController;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.samsung.android.settings.notification.RadioPreference;
import com.samsung.android.settings.widget.SecUnclickablePreference;
import com.sec.ims.configuration.DATA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class LockScreenNotificationSettings extends DashboardFragment
        implements CompoundButton.OnCheckedChangeListener,
                SecConceptBehavior,
                SubScreenShowNotificationWarningFragment.DialogFragmentListener,
                SeslToggleSwitch.OnBeforeCheckedChangeListener,
                SubScreenShowNotificationWarningFragment.DialogFragmentCheckListener {
    public static final OnResetSettingsDataListener RESET_SETTINGS_DATA;
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER;
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER;
    public SecUnclickablePreference mAODSummary;
    public boolean mFromSetupWizard;
    public Handler mHandler;
    public RadioPreference mHideContentPreference;
    public boolean mIsLockScreenDisabled;
    public boolean mJustPPP;
    public LockPatternUtils mLockPatternUtils;
    public SettingsObserver mSettingsObserver;
    public ShowNotificationContentPreference mShowContentPreference;
    public SettingsMainSwitchBar mSwitchBar;
    public boolean isCheckedCoverScreenShowNotificationCheckBox = true;
    public final AnonymousClass1 mContentPreferenceClickListener =
            new Preference
                    .OnPreferenceClickListener() { // from class:
                                                   // com.samsung.android.settings.lockscreen.LockScreenNotificationSettings.1
                @Override // androidx.preference.Preference.OnPreferenceClickListener
                public final boolean onPreferenceClick(Preference preference) {
                    BaseSearchIndexProvider baseSearchIndexProvider =
                            LockScreenNotificationSettings.SEARCH_INDEX_DATA_PROVIDER;
                    LockScreenNotificationSettings lockScreenNotificationSettings =
                            LockScreenNotificationSettings.this;
                    boolean lockscreenAllowPrivateNotifications =
                            lockScreenNotificationSettings.getLockscreenAllowPrivateNotifications();
                    if ("show_content".equals(preference.getKey())
                            && !lockscreenAllowPrivateNotifications) {
                        Settings.Secure.putIntForUser(
                                lockScreenNotificationSettings.getContentResolver(),
                                "lock_screen_allow_private_notifications",
                                1,
                                -2);
                        ShowNotificationContentPreference showNotificationContentPreference =
                                lockScreenNotificationSettings.mShowContentPreference;
                        RadioButton radioButton = showNotificationContentPreference.mShowContentBtn;
                        if (radioButton != null) {
                            radioButton.setChecked(true);
                        }
                        showNotificationContentPreference.setSettingsBtnEnabled(true);
                        lockScreenNotificationSettings.mHideContentPreference.setChecked(false);
                        return true;
                    }
                    if (!"hide_content".equals(preference.getKey())
                            || !lockscreenAllowPrivateNotifications) {
                        return false;
                    }
                    Settings.Secure.putIntForUser(
                            lockScreenNotificationSettings.getContentResolver(),
                            "lock_screen_allow_private_notifications",
                            0,
                            -2);
                    ShowNotificationContentPreference showNotificationContentPreference2 =
                            lockScreenNotificationSettings.mShowContentPreference;
                    RadioButton radioButton2 = showNotificationContentPreference2.mShowContentBtn;
                    if (radioButton2 != null) {
                        radioButton2.setChecked(false);
                    }
                    showNotificationContentPreference2.setSettingsBtnEnabled(false);
                    lockScreenNotificationSettings.mHideContentPreference.setChecked(true);
                    return true;
                }
            };
    public boolean mNeedRedaction = false;
    public final List mSecControllers = new ArrayList();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.lockscreen.LockScreenNotificationSettings$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            ArrayList arrayList = (ArrayList) nonIndexableKeys;
            arrayList.add("description_pref");
            if (new LockPatternUtils(context).isLockScreenDisabled(UserHandle.myUserId())) {
                AccessibilitySettings$$ExternalSyntheticOutline0.m(
                        arrayList,
                        "noti_inverse_text",
                        "noti_card_seekbar",
                        "where_to_show",
                        "notification_details");
                AccessibilitySettings$$ExternalSyntheticOutline0.m(
                        arrayList,
                        "notification_icons_only",
                        "hide_content",
                        "lock_screen_notifications",
                        "sensitive_work_profile");
                return nonIndexableKeys;
            }
            if (KnoxUtils.isApplicationRestricted(
                    context,
                    Arrays.asList("top_level_lockscreen", "lock_screen_menu_notifications"))) {
                AccessibilitySettings$$ExternalSyntheticOutline0.m(
                        arrayList,
                        "noti_inverse_text",
                        "noti_card_seekbar",
                        "where_to_show",
                        "notification_details");
                arrayList.add("notification_icons_only");
                arrayList.add("hide_content");
                arrayList.add("lock_screen_notifications");
                return nonIndexableKeys;
            }
            if (!LockUtils.isSupportAodService()) {
                arrayList.add("where_to_show");
            }
            if (Settings.System.getIntForUser(
                            context.getContentResolver(),
                            "lockscreen_minimizing_notification",
                            1,
                            UserHandle.myUserId())
                    == 1) {
                arrayList.add("noti_inverse_text");
                arrayList.add("noti_card_seekbar");
            }
            arrayList.add("lock_screen_notifications");
            arrayList.add("lockscreen_notification_style_description");
            if (!Utils.isAfwProfile(context)) {
                arrayList.add("sensitive_work_profile");
            }
            return nonIndexableKeys;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.lockscreen.LockScreenNotificationSettings$4, reason: invalid class name */
    public final class AnonymousClass4 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            ArrayList arrayList = new ArrayList();
            int intForUser =
                    Settings.System.getIntForUser(
                            context.getContentResolver(),
                            "lockscreen_minimizing_notification",
                            -1,
                            -2);
            boolean z =
                    Settings.Secure.getInt(
                                    context.getContentResolver(),
                                    "lock_screen_allow_private_notifications",
                                    0)
                            == 0;
            String str = ApnSettings.MVNO_NONE;
            String str2 =
                    intForUser != 0
                            ? intForUser != 1
                                    ? ApnSettings.MVNO_NONE
                                    : z ? "Hide content & icons only" : "Icons only"
                            : z ? "Hide content (Detailed + Hide)" : "Detailed";
            String valueOf = String.valueOf(9004);
            StatusData statusData = new StatusData();
            statusData.mStatusValue = str2;
            statusData.mStatusKey = valueOf;
            arrayList.add(statusData);
            boolean z2 =
                    Settings.System.getInt(
                                    context.getContentResolver(),
                                    "lockscreen_notifications_option",
                                    0)
                            == 0;
            String valueOf2 = String.valueOf(9016);
            String str3 = z2 ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
            StatusData statusData2 = new StatusData();
            statusData2.mStatusValue = str3;
            statusData2.mStatusKey = valueOf2;
            arrayList.add(statusData2);
            int i =
                    Settings.System.getInt(
                            context.getContentResolver(),
                            "lock_screen_show_silent_notifications",
                            1);
            if (i == 0) {
                str = "Alerting notifications only";
            } else if (i == 1) {
                str = "Alerting and silent notifications";
            }
            String valueOf3 = String.valueOf(9032);
            StatusData statusData3 = new StatusData();
            statusData3.mStatusValue = str;
            statusData3.mStatusKey = valueOf3;
            arrayList.add(statusData3);
            int i2 =
                    Settings.Secure.getInt(
                            context.getContentResolver(), "lock_screen_show_notifications", 0);
            String valueOf4 = String.valueOf(9003);
            String valueOf5 = String.valueOf(i2);
            StatusData statusData4 = new StatusData();
            statusData4.mStatusValue = valueOf5;
            statusData4.mStatusKey = valueOf4;
            arrayList.add(statusData4);
            String str4 =
                    (i2 == 0
                                    || Settings.Secure.getIntForUser(
                                                    context.getContentResolver(),
                                                    "lock_screen_allow_private_notifications",
                                                    0,
                                                    -2)
                                            == 0)
                            ? "Hide Content"
                            : "Show Content";
            String valueOf6 = String.valueOf(9033);
            StatusData statusData5 = new StatusData();
            statusData5.mStatusValue = str4;
            statusData5.mStatusKey = valueOf6;
            arrayList.add(statusData5);
            return arrayList;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.lockscreen.LockScreenNotificationSettings$5, reason: invalid class name */
    public final class AnonymousClass5 extends BaseResetSettingsData {
        @Override // com.samsung.android.settings.general.OnResetSettingsDataListener
        public final void resetSettings(Context context) {
            ContentResolver contentResolver = context.getContentResolver();
            Settings.Secure.putInt(contentResolver, "lock_screen_show_notifications", 1);
            Settings.System.putIntForUser(
                    contentResolver, "lockscreen_minimizing_notification", 1, -2);
            Settings.System.putInt(
                    contentResolver,
                    "lock_noticard_opacity",
                    context.getResources()
                            .getInteger(R.integer.config_default_lock_noticard_opacity));
            Settings.Secure.putInt(contentResolver, "lock_screen_show_silent_notifications", 1);
            Settings.Secure.putInt(contentResolver, "lock_screen_allow_private_notifications", 1);
            Settings.Secure.putInt(
                    contentResolver,
                    "lock_screen_allow_private_notifications_when_unsecure",
                    Rune.NOTIS_LOCKSCREEN_SHOW_CONTENT_WHEN_UNLOCKED_DEFAULT_ON ? 1 : 0);
            Settings.System.putInt(contentResolver, "notification_text_color_inversion", 1);
            Settings.System.putInt(contentResolver, "lockscreen_notifications_option", 0);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SettingsObserver extends ContentObserver {
        public final Uri LOCKSCREEN_NOTIFICATION_STYLE;

        public SettingsObserver(Handler handler) {
            super(handler);
            this.LOCKSCREEN_NOTIFICATION_STYLE =
                    Settings.System.getUriFor("lockscreen_minimizing_notification");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            LockScreenNotificationSettings lockScreenNotificationSettings =
                    LockScreenNotificationSettings.this;
            BaseSearchIndexProvider baseSearchIndexProvider =
                    LockScreenNotificationSettings.SEARCH_INDEX_DATA_PROVIDER;
            lockScreenNotificationSettings.updateVisible$1();
        }

        public final void setListening(boolean z) {
            if (z) {
                LockScreenNotificationSettings.this
                        .getContext()
                        .getContentResolver()
                        .registerContentObserver(this.LOCKSCREEN_NOTIFICATION_STYLE, false, this);
            } else {
                LockScreenNotificationSettings.this
                        .getContext()
                        .getContentResolver()
                        .unregisterContentObserver(this);
            }
        }
    }

    static {
        Log.isLoggable("LockScreenNotificationSettings", 3);
        SEARCH_INDEX_DATA_PROVIDER =
                new AnonymousClass2(R.xml.sec_lockscreen_notification_settings);
        STATUS_LOGGING_PROVIDER = new AnonymousClass4();
        RESET_SETTINGS_DATA = new AnonymousClass5();
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(
                new ShowOnLockScreenNotificationPreferenceController(
                        context, "lock_screen_notifications"));
        arrayList.add(
                new LockScreenNotificationStyleController(
                        context, "lock_screen_notifications_style"));
        arrayList.add(
                new LockScreenNotificationStyleDotDescriptionController(
                        context, "lockscreen_notification_style_description"));
        arrayList.add(new ShowContentWhenUnlockedController(context));
        arrayList.add(
                new ShowAlertNotificationsController(context, "lockscreen_alert_notification"));
        arrayList.add(new NotiStyleDividerController(context));
        arrayList.add(new SensitiveWorkProfileNotificationPreferenceController(context, this));
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Object obj = (AbstractPreferenceController) it.next();
            if (obj instanceof SecConceptControllerBehavior) {
                ((ArrayList) this.mSecControllers).add((SecConceptControllerBehavior) obj);
            }
        }
        return arrayList;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return ConfigureNotificationSettings.class.getName();
    }

    public final boolean getLockscreenAllowPrivateNotifications() {
        return Settings.Secure.getInt(
                        getContentResolver(), "lock_screen_allow_private_notifications", 0)
                != 0;
    }

    public final boolean getLockscreenNotificationsEnabled() {
        return Settings.Secure.getInt(getContentResolver(), "lock_screen_show_notifications", 1)
                != 0;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return null;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        if (getIntent() != null) {
            getIntent().getBooleanExtra("needRedaction", false);
        }
        return 4435;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_lockscreen_notification_settings;
    }

    public final void masterSwitchChanged(boolean z) {
        Iterator it = ((ArrayList) this.mSecControllers).iterator();
        while (it.hasNext()) {
            ((SecConceptControllerBehavior) it.next())
                    .accept("lock_screen_show_notifications", Boolean.valueOf(z));
        }
        ShowNotificationContentPreference showNotificationContentPreference =
                this.mShowContentPreference;
        if (showNotificationContentPreference != null) {
            showNotificationContentPreference.setEnabled(z);
        }
        RadioPreference radioPreference = this.mHideContentPreference;
        if (radioPreference != null) {
            radioPreference.setEnabled(z);
        }
    }

    public final void notifyChange(Object obj, String str) {
        Iterator it = ((ArrayList) this.mSecControllers).iterator();
        while (it.hasNext()) {
            ((SecConceptControllerBehavior) it.next()).accept(str, obj);
        }
    }

    @Override // androidx.appcompat.widget.SeslToggleSwitch.OnBeforeCheckedChangeListener
    public final boolean onBeforeCheckedChanged(boolean z) {
        Log.d("LockScreenNotificationSettings", "onBeforeCheckedChanged: ");
        if (RestrictedLockUtilsInternal.checkIfKeyguardFeaturesDisabled(
                                getActivity(), 12, UserHandle.semGetMyUserId())
                        == null
                && !z
                && Settings.Secure.getIntForUser(
                                getContentResolver(), "lock_screen_show_notifications", 1, -2)
                        == 1) {
            Log.d("LockScreenNotificationSettings", "onBeforeCheckedChanged:1 ");
            SubScreenShowNotificationWarningFragment subScreenShowNotificationWarningFragment =
                    new SubScreenShowNotificationWarningFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("dialog_type", 0);
            bundle.putInt("arg_switch_checked_state", z ? 1 : 0);
            subScreenShowNotificationWarningFragment.setArguments(bundle);
            subScreenShowNotificationWarningFragment.mDialogFragmentListener = this;
            subScreenShowNotificationWarningFragment.setCancelable(false);
            subScreenShowNotificationWarningFragment.show(
                    getParentFragmentManager(), "LockScreenNotificationSettings");
            return true;
        }
        if (z
                && Settings.Secure.getIntForUser(
                                getContentResolver(), "lock_screen_show_notifications", 1, -2)
                        == 0) {
            Log.d("LockScreenNotificationSettings", "onBeforeCheckedChanged:2 ");
            SubScreenShowNotificationWarningFragment subScreenShowNotificationWarningFragment2 =
                    new SubScreenShowNotificationWarningFragment();
            Bundle bundle2 = new Bundle();
            bundle2.putInt("dialog_type", 2);
            bundle2.putInt("arg_switch_checked_state", z ? 1 : 0);
            subScreenShowNotificationWarningFragment2.setArguments(bundle2);
            subScreenShowNotificationWarningFragment2.mDialogFragmentListener = this;
            subScreenShowNotificationWarningFragment2.setCancelable(false);
            subScreenShowNotificationWarningFragment2.mDialogFragmentCheckListener = this;
            subScreenShowNotificationWarningFragment2.show(
                    getParentFragmentManager(), "LockScreenNotificationSettings");
        }
        return false;
    }

    public final void onChanged(boolean z) {
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) getActivity().getSystemService("device_policy");
        RestrictedLockUtils.EnforcedAdmin checkIfKeyguardFeaturesDisabled =
                RestrictedLockUtilsInternal.checkIfKeyguardFeaturesDisabled(
                        getActivity(), 12, UserHandle.semGetMyUserId());
        int semGetMyUserId = UserHandle.semGetMyUserId();
        if (devicePolicyManager != null && checkIfKeyguardFeaturesDisabled != null) {
            devicePolicyManager.getKeyguardDisabledFeatures(
                    checkIfKeyguardFeaturesDisabled.component, semGetMyUserId);
            masterSwitchChanged(false);
            return;
        }
        Settings.Secure.putIntForUser(
                getContentResolver(), "lock_screen_show_notifications", z ? 1 : 0, -2);
        getMetricsCategory();
        LoggingHelper.insertEventLogging(4435, 4451, z ? 1L : 0L);
        masterSwitchChanged(z);
        if (this.mIsLockScreenDisabled) {
            Settings.System.putInt(
                    getContentResolver(), "lockscreen_notifications_option", !z ? 1 : 0);
        }
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        Settings.Secure.putInt(getContentResolver(), "lock_screen_show_notifications", z ? 1 : 0);
        getMetricsCategory();
        LoggingHelper.insertEventLogging(4435, 4451, z ? 1L : 0L);
        masterSwitchChanged(z);
        if (this.mIsLockScreenDisabled) {
            Settings.System.putInt(
                    getContentResolver(), "lockscreen_notifications_option", !z ? 1 : 0);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Preference$$ExternalSyntheticOutline0.m(
                new StringBuilder("onConfigurationChanged:"),
                configuration.orientation,
                "LockScreenNotificationSettings");
        Iterator it = ((ArrayList) this.mSecControllers).iterator();
        while (it.hasNext()) {
            ((SecConceptControllerBehavior) it.next()).updateConfigurationChanged(configuration);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mSettingsObserver = new SettingsObserver(this.mHandler);
        LockPatternUtils lockPatternUtils = new LockPatternUtils(getActivity());
        this.mLockPatternUtils = lockPatternUtils;
        this.mIsLockScreenDisabled =
                lockPatternUtils.isLockScreenDisabled(UserHandle.semGetMyUserId());
        Log.d("LockScreenNotificationSettings", "onCreate");
        Intent intent = getIntent();
        this.mNeedRedaction = intent.getBooleanExtra("needRedaction", false);
        this.mFromSetupWizard = intent.getBooleanExtra("fromSetupWizard", false);
        this.mJustPPP = intent.getBooleanExtra("justPPP", false);
        this.mAODSummary = (SecUnclickablePreference) findPreference("description_pref");
        Bundle arguments = getArguments();
        String string = arguments != null ? arguments.getString("fromFragment") : null;
        if (string != null) {
            string.equals("configure_settings");
        }
        this.mShowContentPreference =
                (ShowNotificationContentPreference) findPreference("show_content");
        this.mHideContentPreference = (RadioPreference) findPreference("hide_content");
        ShowNotificationContentPreference showNotificationContentPreference =
                this.mShowContentPreference;
        if (showNotificationContentPreference != null) {
            showNotificationContentPreference.setOnPreferenceClickListener(
                    this.mContentPreferenceClickListener);
        }
        RadioPreference radioPreference = this.mHideContentPreference;
        if (radioPreference != null) {
            radioPreference.setOnPreferenceClickListener(this.mContentPreferenceClickListener);
            this.mHideContentPreference.setChecked(!getLockscreenAllowPrivateNotifications());
            if (Rune.COVERSCREEN_SETTINGS) {
                this.mHideContentPreference.setSummary(
                        R.string.sec_lockscreen_notifications_hide_content_description);
            } else {
                this.mHideContentPreference.setSummary(ApnSettings.MVNO_NONE);
            }
        }
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.removeOnSwitchChangeListener(this);
            this.mSwitchBar.hide();
        }
    }

    public final void onPositiveClick() {
        onChanged(false);
        this.mSwitchBar.setChecked(false);
        Settings.Secure.putIntForUser(
                getContentResolver(), "cover_screen_show_notification", 0, -2);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.mSettingsObserver.setListening(true);
        updateVisible$1();
        ShowNotificationContentPreference showNotificationContentPreference =
                this.mShowContentPreference;
        if (showNotificationContentPreference instanceof ShowNotificationContentPreference) {
            showNotificationContentPreference.mIsAllAppChecked = Boolean.TRUE;
            showNotificationContentPreference.new GetAppListAsyncTask().execute(new Void[0]);
        }
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) getActivity().getSystemService("device_policy");
        if (!Utils.isAfwProfile(getActivity())) {
            removePreference("sensitive_work_profile");
        }
        RestrictedLockUtils.EnforcedAdmin checkIfKeyguardFeaturesDisabled =
                RestrictedLockUtilsInternal.checkIfKeyguardFeaturesDisabled(
                        getActivity(), 12, UserHandle.semGetMyUserId());
        int semGetMyUserId = UserHandle.semGetMyUserId();
        this.mSwitchBar.setDisabledByAdmin(checkIfKeyguardFeaturesDisabled);
        if (devicePolicyManager == null || checkIfKeyguardFeaturesDisabled == null) {
            this.mSwitchBar.setChecked(getLockscreenNotificationsEnabled());
            masterSwitchChanged(getLockscreenNotificationsEnabled());
        } else {
            this.mSwitchBar.setChecked(
                    getLockscreenNotificationsEnabled()
                            && (devicePolicyManager.getKeyguardDisabledFeatures(
                                                    checkIfKeyguardFeaturesDisabled.component,
                                                    semGetMyUserId)
                                            & 8)
                                    == 0);
            masterSwitchChanged(false);
        }
        RestrictedLockUtilsInternal.checkIfKeyguardFeaturesDisabled(
                getActivity(), 4, UserHandle.semGetMyUserId());
        if (!this.mIsLockScreenDisabled || !LockUtils.isSupportAodService()) {
            SecUnclickablePreference secUnclickablePreference = this.mAODSummary;
            if (secUnclickablePreference != null) {
                secUnclickablePreference.setVisible(false);
                return;
            }
            return;
        }
        SecUnclickablePreference secUnclickablePreference2 = this.mAODSummary;
        if (secUnclickablePreference2 != null) {
            secUnclickablePreference2.setVisible(true);
        }
        removePreference("lockscreen_notification_style_description");
        removePreference("locksreen_notification_style_editor_category");
        removePreference("show_content");
        removePreference("hide_content");
        removePreference("divider");
        updatePreferenceStates();
        getListView().mDrawLastRoundedCorner = false;
        PreferenceCategory preferenceCategory =
                (PreferenceCategory) findPreference("advanced_category");
        if (preferenceCategory != null) {
            getPreferenceScreen().removePreference(preferenceCategory);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        this.mSettingsObserver.setListening(false);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        Log.d("LockScreenNotificationSettings", "onViewCreated");
        SettingsMainSwitchBar settingsMainSwitchBar =
                ((SettingsActivity) getActivity()).mMainSwitch;
        this.mSwitchBar = settingsMainSwitchBar;
        if (settingsMainSwitchBar != null) {
            ViewGroup.MarginLayoutParams marginLayoutParams =
                    (ViewGroup.MarginLayoutParams) settingsMainSwitchBar.getLayoutParams();
            marginLayoutParams.setMargins(
                    marginLayoutParams.leftMargin,
                    marginLayoutParams.topMargin,
                    marginLayoutParams.rightMargin,
                    0);
            this.mSwitchBar.setLayoutParams(marginLayoutParams);
        }
        if (Rune.COVERSCREEN_SETTINGS || LockUtils.isCameraCoverAttached(getContext())) {
            this.mSwitchBar.getSwitch().mOnBeforeListener = this;
        }
        this.mSwitchBar.addOnSwitchChangeListener(this);
        this.mSwitchBar.show();
        if (this.mNeedRedaction) {
            ViewGroup viewGroup = (ViewGroup) getActivity().findViewById(R.id.button_bar);
            View inflate =
                    LayoutInflater.from(getActivity())
                            .inflate(R.layout.sec_redaction_bottom_button, (ViewGroup) null);
            ((Button) inflate.findViewById(R.id.button_done))
                    .setOnClickListener(
                            new View
                                    .OnClickListener() { // from class:
                                                         // com.samsung.android.settings.lockscreen.LockScreenNotificationSettings.3
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view2) {
                                    String str;
                                    String str2;
                                    String sb;
                                    LockScreenNotificationSettings lockScreenNotificationSettings =
                                            LockScreenNotificationSettings.this;
                                    BaseSearchIndexProvider baseSearchIndexProvider =
                                            LockScreenNotificationSettings
                                                    .SEARCH_INDEX_DATA_PROVIDER;
                                    if (lockScreenNotificationSettings
                                            .getLockscreenNotificationsEnabled()) {
                                        String[] stringArray =
                                                LockScreenNotificationSettings.this
                                                        .getResources()
                                                        .getStringArray(
                                                                R.array
                                                                        .lockscreen_notification_view_style_event_logging);
                                        int intForUser =
                                                Settings.System.getIntForUser(
                                                        LockScreenNotificationSettings.this
                                                                .getContentResolver(),
                                                        "lockscreen_minimizing_notification",
                                                        -1,
                                                        -2);
                                        if (intForUser >= 0) {
                                            str2 = "ON, " + ((Object) stringArray[intForUser]);
                                        } else {
                                            str2 = "ON";
                                        }
                                        String m =
                                                AbstractResolvableFuture$$ExternalSyntheticOutline0
                                                        .m(str2, ", ");
                                        if (Settings.System.getInt(
                                                        LockScreenNotificationSettings.this
                                                                .getContext()
                                                                .getContentResolver(),
                                                        "lock_screen_show_silent_notifications",
                                                        1)
                                                == 1) {
                                            StringBuilder m2 =
                                                    EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0
                                                            .m(m);
                                            m2.append(
                                                    LockScreenNotificationSettings.this
                                                            .getContext()
                                                            .getString(
                                                                    R.string
                                                                            .lock_screen_notifs_show_all_event_logging));
                                            sb = m2.toString();
                                        } else {
                                            StringBuilder m3 =
                                                    EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0
                                                            .m(m);
                                            m3.append(
                                                    LockScreenNotificationSettings.this
                                                            .getContext()
                                                            .getString(
                                                                    R.string
                                                                            .lock_screen_notifs_show_alerting_event_logging));
                                            sb = m3.toString();
                                        }
                                        StringBuilder m4 =
                                                EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0
                                                        .m(sb);
                                        m4.append(
                                                !LockScreenNotificationSettings.this
                                                                .getLockscreenAllowPrivateNotifications()
                                                        ? ", Hide"
                                                        : ApnSettings.MVNO_NONE);
                                        str = m4.toString();
                                    } else {
                                        str = "OFF";
                                    }
                                    LockScreenNotificationSettings.this.getMetricsCategory();
                                    SALogging.insertSALog(String.valueOf(4435), "U4470", str);
                                    LockScreenNotificationSettings lockScreenNotificationSettings2 =
                                            LockScreenNotificationSettings.this;
                                    if (!lockScreenNotificationSettings2.mJustPPP
                                            && !lockScreenNotificationSettings2.mFromSetupWizard) {
                                        try {
                                            Intent fmmDialogIntent =
                                                    LockUtils.getFmmDialogIntent(
                                                            lockScreenNotificationSettings2
                                                                    .getContext());
                                            if (fmmDialogIntent != null) {
                                                LockScreenNotificationSettings.this
                                                        .getActivity()
                                                        .startActivity(fmmDialogIntent);
                                            }
                                        } catch (Exception e) {
                                            Log.d(
                                                    "LockScreenNotificationSettings",
                                                    "Exception occurs when excuting fmmIntent : "
                                                            + e.getMessage());
                                        }
                                    }
                                    LockScreenNotificationSettings.this.finish();
                                }
                            });
            if (viewGroup != null) {
                viewGroup.removeAllViews();
                LinearLayout.LayoutParams layoutParams =
                        (LinearLayout.LayoutParams) viewGroup.getLayoutParams();
                layoutParams.gravity = 8388613;
                viewGroup.addView(inflate, layoutParams);
                viewGroup.setVisibility(0);
            }
            TypedArray obtainStyledAttributes =
                    getActivity()
                            .obtainStyledAttributes(
                                    R.style.LockScreenTheme, KnoxUtils.lockscreenAttrs);
            boolean z = obtainStyledAttributes.getBoolean(0, false);
            obtainStyledAttributes.recycle();
            if (z
                    && !getActivity().isInMultiWindowMode()
                    && R.style.LockScreenTheme == getActivity().getThemeResId()) {
                LinearLayout linearLayout =
                        (LinearLayout)
                                getActivity()
                                        .getWindow()
                                        .getDecorView()
                                        .findViewById(R.id.content_parent);
                FrameLayout.LayoutParams layoutParams2 =
                        (FrameLayout.LayoutParams) linearLayout.getLayoutParams();
                layoutParams2.width = (int) getResources().getDimension(R.dimen.dialog_title_width);
                linearLayout.setLayoutParams(layoutParams2);
                getListView().setPadding(12, 0, 0, 0);
            }
        }
        if (this.mLockPatternUtils.isLockScreenDisabled(UserHandle.semGetMyUserId())
                || !LockUtils.isSupportAodService()) {
            getListView().mDrawLastRoundedCorner = false;
        }
    }

    public final void updateVisible$1() {
        Iterator<List<AbstractPreferenceController>> it = getPreferenceControllers().iterator();
        while (it.hasNext()) {
            for (AbstractPreferenceController abstractPreferenceController : it.next()) {
                if (abstractPreferenceController
                        instanceof LockScreenNotificationStyleDotDescriptionController) {
                    ((LockScreenNotificationStyleDotDescriptionController)
                                    abstractPreferenceController)
                            .updateVisible();
                }
            }
        }
    }
}
