package com.android.settings.notification;

import android.app.INotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.OnActivityResultListener;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.RestrictedLockUtilsInternal;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.SettingsPreferenceFragmentLinkData;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.settings.asbase.rune.AudioRune;
import com.samsung.android.settings.connection.SecSimFeatureProviderImpl;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.lockscreen.LockUtils;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.samsung.android.settings.notification.NotificationEditorPreferenceController;
import com.samsung.android.settings.notification.brief.NotificationPopupStyleController;
import com.samsung.android.settings.widget.SecRelativeLinkView;
import com.samsung.android.settings.widget.SecRestrictedPreference;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ConfigureNotificationSettings extends DashboardFragment
        implements OnActivityResultListener {
    static final String KEY_SWIPE_DOWN = "gesture_swipe_down_fingerprint_notifications";
    public static final int MY_USER_ID = UserHandle.myUserId();
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass2(R.xml.sec_configure_notification_settings);
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass3();
    public LockPatternUtils mLockPatternUtils;
    public SecRelativeLinkView mRelativeLinkView;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.notification.ConfigureNotificationSettings$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            return ConfigureNotificationSettings.buildPreferenceControllers$2(context);
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            if (Utils.isMinimalBatteryUseEnabled(context)) {
                ((ArrayList) nonIndexableKeys).add("notis_popup_style_main_settings");
            }
            LockPatternUtils lockPatternUtils = new LockPatternUtils(context);
            boolean isAlwaysOnDisplayEnabled = LockUtils.isAlwaysOnDisplayEnabled(context);
            if (lockPatternUtils.isLockScreenDisabled(UserHandle.myUserId())
                    && !isAlwaysOnDisplayEnabled) {
                ((ArrayList) nonIndexableKeys).add("lockscreen_notifications");
            }
            return nonIndexableKeys;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.notification.ConfigureNotificationSettings$3, reason: invalid class name */
    public final class AnonymousClass3 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            ArrayList arrayList = new ArrayList();
            new NotificationBackend();
            try {
                int allNotificationListenersCount =
                        NotificationBackend.sINM.getAllNotificationListenersCount();
                String valueOf = String.valueOf(36117);
                String valueOf2 = String.valueOf(allNotificationListenersCount);
                StatusData statusData = new StatusData();
                statusData.mStatusValue = valueOf2;
                statusData.mStatusKey = valueOf;
                arrayList.add(statusData);
                try {
                    Iterator it =
                            ((ArrayList)
                                            NotificationBackend.getNotificationPackagesList(
                                                    context, context.getUserId()))
                                    .iterator();
                    while (it.hasNext()) {
                        ResolveInfo resolveInfo = (ResolveInfo) it.next();
                        INotificationManager iNotificationManager = NotificationBackend.sINM;
                        ActivityInfo activityInfo = resolveInfo.activityInfo;
                        int numNotificationChannelsForPackage =
                                iNotificationManager.getNumNotificationChannelsForPackage(
                                        activityInfo.packageName,
                                        activityInfo.applicationInfo.uid,
                                        false);
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append(resolveInfo.activityInfo.packageName);
                        stringBuffer.append(":");
                        stringBuffer.append(Integer.toString(numNotificationChannelsForPackage));
                        SALogging.insertSALog(
                                Integer.toString(36022), "NSTE0038", stringBuffer.toString());
                    }
                    return arrayList;
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } catch (RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }
    }

    public static List buildPreferenceControllers$2(Context context) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new EmergencyBroadcastPreferenceController(context));
        arrayList.add(
                new NotificationPopupStyleController(context, "notis_popup_style_main_settings"));
        arrayList.add(
                new NotificationEditorPreferenceController(
                        context, "lock_editor_preview_preference"));
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.getApplication();
        }
        return buildPreferenceControllers$2(context);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "ConfigNotiSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return FileType.SHOW;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_configure_notification_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        NotificationAssistantPreferenceController notificationAssistantPreferenceController =
                (NotificationAssistantPreferenceController)
                        use(NotificationAssistantPreferenceController.class);
        if (notificationAssistantPreferenceController != null) {
            notificationAssistantPreferenceController.setFragment(this);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        this.mLockPatternUtils = new LockPatternUtils(getActivity());
        replaceEnterpriseStringTitle(
                "lock_screen_work_redact",
                "Settings.WORK_PROFILE_LOCK_SCREEN_REDACT_NOTIFICATION_TITLE",
                R.string.lock_screen_notifs_redact_work);
        replaceEnterpriseStringSummary(
                "lock_screen_work_redact",
                "Settings.WORK_PROFILE_LOCK_SCREEN_REDACT_NOTIFICATION_SUMMARY",
                R.string.lock_screen_notifs_redact_work_summary);
        Intent intent = getIntent();
        if (intent != null
                && (intent.hasExtra(":settings:show_fragment_title_res_package_name")
                        || intent.hasExtra(":settings:show_fragment_title_resid"))) {
            try {
                PackageManager packageManager = getPackageManager();
                String stringExtra =
                        intent.getStringExtra(":settings:show_fragment_title_res_package_name");
                int intExtra = intent.getIntExtra(":settings:show_fragment_title_resid", -1);
                if (stringExtra != null && intExtra != -1) {
                    getActivity()
                            .setTitle(
                                    packageManager
                                            .getResourcesForApplication(stringExtra)
                                            .getString(intExtra));
                }
            } catch (Exception e) {
                Log.e("ConfigNotiSettings", e.toString());
            }
        }
        SecRestrictedPreference secRestrictedPreference =
                (SecRestrictedPreference)
                        preferenceScreen.findPreference("lockscreen_notifications");
        if (LockUtils.isAlwaysOnDisplayEnabled(getContext())
                || !this.mLockPatternUtils.isLockScreenDisabled(MY_USER_ID)) {
            secRestrictedPreference.setVisible(true);
        } else {
            secRestrictedPreference.setVisible(false);
        }
        secRestrictedPreference.setDisabledByAdmin(
                RestrictedLockUtilsInternal.checkIfKeyguardFeaturesDisabled(
                        getActivity(), 12, UserHandle.semGetMyUserId()));
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        if (preference.getKey().equals("all_notifications")) {
            LoggingHelper.insertEventLogging(36011, "NSTE0031");
        } else if (preference.getKey().equals("notis_popup_style_main_settings")) {
            LoggingHelper.insertEventLogging(36011, "NSTE0033");
        } else if (preference.getKey().equals("zen_mode_notifications")) {
            LoggingHelper.insertEventLogging(36011, "NSTE0007");
        } else if (preference.getKey().equals("more_settings")) {
            LoggingHelper.insertEventLogging(36011, "NSTE0008");
        }
        return super.onPreferenceTreeClick(preference);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        getContext();
        if (Rune.supportRelativeLink()
                && !KnoxUtils.isApplicationRestricted(
                        getContext(), "Settings_Relative_Link_View", "hide")
                && this.mRelativeLinkView == null) {
            this.mRelativeLinkView = new SecRelativeLinkView(getActivity());
            if (AudioRune.SUPPORT_SPEAKER) {
                SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData =
                        new SettingsPreferenceFragmentLinkData();
                settingsPreferenceFragmentLinkData.titleRes =
                        R.string.relative_link_notification_sound;
                Bundle bundle = new Bundle();
                FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                if (featureFactoryImpl == null) {
                    throw new UnsupportedOperationException("No feature factory configured");
                }
                SecSimFeatureProviderImpl secSimFeatureProviderImpl =
                        (SecSimFeatureProviderImpl) featureFactoryImpl.getSecSimFeatureProvider();
                if (!secSimFeatureProviderImpl.isMultiSimModel()
                        || (secSimFeatureProviderImpl.getEnabledSimCnt() < 2
                                && secSimFeatureProviderImpl.getFirstSlotIndex() <= 0)) {
                    bundle.putString(":settings:fragment_args_key", "notification_sound");
                } else {
                    bundle.putString(":settings:fragment_args_key", "ds_notification_sound");
                }
                settingsPreferenceFragmentLinkData.flowId = "36111";
                settingsPreferenceFragmentLinkData.callerMetric = FileType.SHOW;
                Intent intent = new Intent();
                intent.setClassName(
                        KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                        "com.android.settings.Settings$SoundSettingsActivity");
                intent.putExtra(":settings:show_fragment_args", bundle);
                intent.putExtra("extra_from_search", true);
                settingsPreferenceFragmentLinkData.intent = intent;
                this.mRelativeLinkView.pushLinkData(settingsPreferenceFragmentLinkData);
            }
            if (Build.VERSION.SEM_PLATFORM_INT >= 100500
                    && !KnoxUtils.isApplicationRestricted(
                            getContext(),
                            List.of(
                                    "accessibility_advanced_settings",
                                    "accessibility_flash_notificaitons"))) {
                SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData2 =
                        new SettingsPreferenceFragmentLinkData();
                settingsPreferenceFragmentLinkData2.titleRes =
                        R.string.relative_link_flash_notification;
                Intent intent2 = new Intent();
                settingsPreferenceFragmentLinkData2.fragment =
                        "com.samsung.android.settings.accessibility.advanced.flashnotification.SecFlashNotificationsPreferenceFragment";
                settingsPreferenceFragmentLinkData2.flowId = "36112";
                settingsPreferenceFragmentLinkData2.callerMetric = FileType.SHOW;
                settingsPreferenceFragmentLinkData2.intent = intent2;
                settingsPreferenceFragmentLinkData2.topLevelKey = "top_level_accessibility";
                this.mRelativeLinkView.pushLinkData(settingsPreferenceFragmentLinkData2);
            }
            if (Rune.FRONT_COVER_SCREEN_ENABLE_TO_SHOW_SETTINGS
                    || LockUtils.isCameraCoverAttached(getContext())) {
                SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData3 =
                        new SettingsPreferenceFragmentLinkData();
                settingsPreferenceFragmentLinkData3.titleRes =
                        R.string.relative_link_cover_screen_notifications;
                Intent intent3 = new Intent();
                intent3.setAction(
                        "com.samsung.android.app.aodservice.intent.action.COVER_SCREEN_SETTING");
                settingsPreferenceFragmentLinkData3.flowId = "36114";
                settingsPreferenceFragmentLinkData3.callerMetric = FileType.SHOW;
                settingsPreferenceFragmentLinkData3.intent = intent3;
                settingsPreferenceFragmentLinkData3.topLevelKey = "top_level_cover_screen";
                this.mRelativeLinkView.pushLinkData(settingsPreferenceFragmentLinkData3);
            }
            this.mRelativeLinkView.create(this);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {}
}
