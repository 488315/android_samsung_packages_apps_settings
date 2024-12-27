package com.android.settings.notification.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;

import com.samsung.android.settings.notification.SecAlertRadioPreferenceController;
import com.samsung.android.settings.notification.SecSilentRadioPreferenceController;
import com.samsung.android.settings.notification.app.DividerPreferenceController;
import com.samsung.android.settings.notification.app.NotificationsOnSecInsetCategoryNoStrokePreferenceController;
import com.samsung.android.settings.notification.app.NotificationsOnSecInsetCategoryOnePreferenceController;
import com.samsung.android.settings.notification.app.NotificationsOnSecInsetCategoryTwoPreferenceController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ChannelNotificationSettings extends NotificationSettings {
    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        ((NotificationSettings) this).mControllers = arrayList;
        arrayList.add(new HeaderPreferenceController(context, this, 0));
        ((ArrayList) ((NotificationSettings) this).mControllers)
                .add(
                        new BlockPreferenceController(
                                context, this.mDependentFieldListener, this.mBackend, true));
        List list = ((NotificationSettings) this).mControllers;
        NotificationSettings.DependentFieldListener dependentFieldListener =
                this.mDependentFieldListener;
        SecAlertRadioPreferenceController secAlertRadioPreferenceController =
                new SecAlertRadioPreferenceController(context, this.mBackend);
        secAlertRadioPreferenceController.mDependentFieldListener = dependentFieldListener;
        ((ArrayList) list).add(secAlertRadioPreferenceController);
        List list2 = ((NotificationSettings) this).mControllers;
        NotificationSettings.DependentFieldListener dependentFieldListener2 =
                this.mDependentFieldListener;
        SecSilentRadioPreferenceController secSilentRadioPreferenceController =
                new SecSilentRadioPreferenceController(context, this.mBackend);
        secSilentRadioPreferenceController.mDependentFieldListener = dependentFieldListener2;
        ((ArrayList) list2).add(secSilentRadioPreferenceController);
        List list3 = ((NotificationSettings) this).mControllers;
        NotificationSettings.DependentFieldListener dependentFieldListener3 =
                this.mDependentFieldListener;
        MinImportancePreferenceController minImportancePreferenceController =
                new MinImportancePreferenceController(context, this.mBackend);
        minImportancePreferenceController.mDependentFieldListener = dependentFieldListener3;
        ((ArrayList) list3).add(minImportancePreferenceController);
        List list4 = ((NotificationSettings) this).mControllers;
        ArrayList arrayList2 = (ArrayList) list4;
        arrayList2.add(
                new HighImportancePreferenceController(
                        context, this.mBackend, this.mDependentFieldListener));
        List list5 = ((NotificationSettings) this).mControllers;
        ArrayList arrayList3 = (ArrayList) list5;
        arrayList3.add(
                new AllowSoundPreferenceController(
                        context, this.mBackend, this.mDependentFieldListener));
        ((ArrayList) ((NotificationSettings) this).mControllers)
                .add(
                        new SoundPreferenceController(
                                context, this, this.mDependentFieldListener, this.mBackend));
        ((ArrayList) ((NotificationSettings) this).mControllers)
                .add(new VibrationPreferenceController(context, this.mBackend));
        ((ArrayList) ((NotificationSettings) this).mControllers)
                .add(new DescriptionPreferenceController(context, null));
        List list6 = ((NotificationSettings) this).mControllers;
        new LockPatternUtils(context);
        ((ArrayList) list6).add(new VisibilityPreferenceController(context, this.mBackend));
        ((ArrayList) ((NotificationSettings) this).mControllers)
                .add(new LightsPreferenceController(context, this.mBackend));
        ((ArrayList) ((NotificationSettings) this).mControllers)
                .add(new BadgePreferenceController(context, this.mBackend));
        ((ArrayList) ((NotificationSettings) this).mControllers)
                .add(new NotificationsOffPreferenceController(context, null));
        List list7 = ((NotificationSettings) this).mControllers;
        ConversationPromotePreferenceController conversationPromotePreferenceController =
                new ConversationPromotePreferenceController(context, this.mBackend);
        conversationPromotePreferenceController.mHostFragment = this;
        ((ArrayList) list7).add(conversationPromotePreferenceController);
        ((ArrayList) ((NotificationSettings) this).mControllers)
                .add(new DividerPreferenceController(context, this.mBackend, true));
        ((ArrayList) ((NotificationSettings) this).mControllers)
                .add(
                        new NotificationsOnSecInsetCategoryNoStrokePreferenceController(
                                context, null));
        ((ArrayList) ((NotificationSettings) this).mControllers)
                .add(new NotificationsOnSecInsetCategoryOnePreferenceController(context, null));
        ((ArrayList) ((NotificationSettings) this).mControllers)
                .add(new NotificationsOnSecInsetCategoryTwoPreferenceController(context, null));
        return new ArrayList(((NotificationSettings) this).mControllers);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "ChannelSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 265;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_channel_notification_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        for (Object obj : ((NotificationSettings) this).mControllers) {
            if (obj instanceof PreferenceManager.OnActivityResultListener) {
                ((PreferenceManager.OnActivityResultListener) obj).onActivityResult(i, i2, intent);
            }
        }
    }

    @Override // com.android.settings.notification.app.NotificationSettings,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        Bundle arguments = getArguments();
        if (preferenceScreen == null
                || arguments == null
                || arguments.getBoolean("fromSettings", false)) {
            return;
        }
        preferenceScreen.mInitialExpandedChildrenCount = Preference.DEFAULT_ORDER;
    }

    @Override // com.android.settings.notification.app.NotificationSettings,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        if (getListView() != null) {
            getListView().mDrawLastRoundedCorner = false;
        }
        if (this.mUid < 0
                || TextUtils.isEmpty(this.mPkg)
                || this.mPkgInfo == null
                || this.mChannel == null) {
            Log.w("ChannelSettings", "Missing package or uid or packageinfo or channel");
            finish();
            return;
        }
        getActivity().setTitle(this.mChannel.getName());
        if (TextUtils.isEmpty(this.mChannel.getConversationId()) || this.mChannel.isDemoted()) {
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
                        this.mPreferenceFilter);
                notificationPreferenceController.displayPreference(getPreferenceScreen());
            }
            updatePreferenceStates();
            animatePanel();
            return;
        }
        SubSettingLauncher subSettingLauncher =
                new SubSettingLauncher(((NotificationSettings) this).mContext);
        String name = ConversationNotificationSettings.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mArguments = getArguments();
        launchRequest.mExtras = getIntent() != null ? getIntent().getExtras() : null;
        launchRequest.mSourceMetricsCategory = 265;
        Intent intent = subSettingLauncher.toIntent();
        if (this.mPreferenceFilter != null) {
            intent.setClass(((NotificationSettings) this).mContext, ChannelPanelActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
