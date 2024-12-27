package com.android.settings.notification.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.notification.NotificationBackend;

import com.samsung.android.settings.notification.SecAppAlertRadioPreferenceController;
import com.samsung.android.settings.notification.app.AppNotificationTypeController;
import com.samsung.android.settings.notification.app.AppNotificationTypePreferenceController;
import com.samsung.android.settings.notification.app.DefaultChannelSecInsetCategoryPreferenceController;
import com.samsung.android.settings.notification.app.DividerPreferenceController;
import com.samsung.android.settings.notification.app.HideContentLockScreenPreferenceController;
import com.samsung.android.settings.notification.app.NotificationsOnSecInsetCategoryNoStrokePreferenceController;
import com.samsung.android.settings.notification.app.PopupTypeTextPreferenceController;
import com.samsung.android.settings.notification.app.SecAlertShowNotificationPreferenceController;
import com.samsung.android.settings.notification.app.SecAppShowLiveNotificationController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AppNotificationSettings extends NotificationSettings {
    static {
        Log.isLoggable("AppNotificationSettings", 3);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        ((NotificationSettings) this).mControllers = arrayList;
        arrayList.add(new HeaderPreferenceController(context, this, this.mBackend));
        ((NotificationSettings) this)
                .mControllers.add(
                        new BlockPreferenceController(
                                context, this.mDependentFieldListener, this.mBackend, false));
        ((NotificationSettings) this)
                .mControllers.add(
                        new FullScreenIntentPermissionPreferenceController(context, this.mBackend));
        ((NotificationSettings) this).mControllers.add(new AppLinkPreferenceController(context));
        ((NotificationSettings) this)
                .mControllers.add(new DescriptionPreferenceController(context, null));
        ((NotificationSettings) this)
                .mControllers.add(new NotificationsOffPreferenceController(context, null));
        List list = ((NotificationSettings) this).mControllers;
        DeepSleepingPreferenceController deepSleepingPreferenceController =
                new DeepSleepingPreferenceController(context, null);
        deepSleepingPreferenceController.mContext = context;
        list.add(deepSleepingPreferenceController);
        ((NotificationSettings) this)
                .mControllers.add(new DividerPreferenceController(context, this.mBackend, false));
        List list2 = ((NotificationSettings) this).mControllers;
        NotificationBackend notificationBackend = this.mBackend;
        PopupTypeTextPreferenceController popupTypeTextPreferenceController =
                new PopupTypeTextPreferenceController(context, notificationBackend);
        popupTypeTextPreferenceController.mBackend = notificationBackend;
        list2.add(popupTypeTextPreferenceController);
        List list3 = ((NotificationSettings) this).mControllers;
        NotificationBackend notificationBackend2 = this.mBackend;
        SecAppShowLiveNotificationController secAppShowLiveNotificationController =
                new SecAppShowLiveNotificationController(context, notificationBackend2);
        secAppShowLiveNotificationController.mBackend = notificationBackend2;
        list3.add(secAppShowLiveNotificationController);
        ((NotificationSettings) this)
                .mControllers.add(new SecAlertShowNotificationPreferenceController(context, null));
        List list4 = ((NotificationSettings) this).mControllers;
        AppChannelLinkPreferenceController appChannelLinkPreferenceController =
                new AppChannelLinkPreferenceController(context, null);
        appChannelLinkPreferenceController.mChannelHighlightId = null;
        appChannelLinkPreferenceController.mContext = context;
        list4.add(appChannelLinkPreferenceController);
        ((NotificationSettings) this)
                .mControllers.add(
                        new SecAppAlertRadioPreferenceController(
                                context, this.mBackend, this.mDependentFieldListener));
        List list5 = ((NotificationSettings) this).mControllers;
        NotificationSettings.DependentFieldListener dependentFieldListener =
                this.mDependentFieldListener;
        NotificationBackend notificationBackend3 = this.mBackend;
        AppNotificationTypeController appNotificationTypeController =
                new AppNotificationTypeController(context, notificationBackend3);
        appNotificationTypeController.mBackend = notificationBackend3;
        appNotificationTypeController.mDependentFieldListener = dependentFieldListener;
        list5.add(appNotificationTypeController);
        List list6 = ((NotificationSettings) this).mControllers;
        NotificationBackend notificationBackend4 = this.mBackend;
        HideContentLockScreenPreferenceController hideContentLockScreenPreferenceController =
                new HideContentLockScreenPreferenceController(context, notificationBackend4);
        hideContentLockScreenPreferenceController.mCurrentLockType = 1;
        hideContentLockScreenPreferenceController.mBackend = notificationBackend4;
        list6.add(hideContentLockScreenPreferenceController);
        ((NotificationSettings) this)
                .mControllers.add(
                        new AppNotificationTypePreferenceController(context, this.mBackend));
        ((NotificationSettings) this)
                .mControllers.add(
                        new NotificationsOnSecInsetCategoryNoStrokePreferenceController(
                                context, null));
        ((NotificationSettings) this)
                .mControllers.add(
                        new DefaultChannelSecInsetCategoryPreferenceController(context, null));
        return new ArrayList(((NotificationSettings) this).mControllers);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "AppNotificationSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 7300;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_app_notification_settings_v1;
    }

    @Override // com.android.settings.notification.app.NotificationSettings,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        Bundle bundleExtra;
        super.onResume();
        setAutoRemoveInsetCategory(false);
        if (getListView() != null) {
            getListView().mDrawLastRoundedCorner = false;
        }
        if (this.mUid < 0 || TextUtils.isEmpty(this.mPkg) || this.mPkgInfo == null) {
            Log.w("AppNotificationSettings", "Missing package or uid or packageinfo");
            finish();
            return;
        }
        Iterator it = ((ArrayList) ((NotificationSettings) this).mControllers).iterator();
        while (it.hasNext()) {
            NotificationPreferenceController notificationPreferenceController =
                    (NotificationPreferenceController) it.next();
            notificationPreferenceController.onResume(
                    this.mAppRow,
                    this.mChannel,
                    this.mChannelGroup,
                    null,
                    null,
                    this.mSuspendedAppsAdmin,
                    null);
            if (notificationPreferenceController instanceof AppChannelLinkPreferenceController) {
                Intent intent = getActivity().getIntent();
                String str = null;
                if (intent != null
                        && (bundleExtra = intent.getBundleExtra(":settings:show_fragment_args"))
                                != null) {
                    str = bundleExtra.getString("highlight_channel_key");
                }
                if (!TextUtils.isEmpty(str)) {
                    ((AppChannelLinkPreferenceController) notificationPreferenceController)
                                    .mChannelHighlightId =
                            str;
                }
            }
            notificationPreferenceController.displayPreference(getPreferenceScreen());
        }
        updatePreferenceStates();
    }
}
