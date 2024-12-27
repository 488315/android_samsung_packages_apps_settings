package com.android.settings.notification.app;

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.fragment.app.FragmentManager;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;

import com.samsung.android.settings.notification.NotificationsOnSecInsetCategoryPopupPreferenceController;
import com.samsung.android.settings.notification.SecAlertConversationPreferenceController;
import com.samsung.android.settings.notification.SecBubbleCategoryPreferenceController;
import com.samsung.android.settings.notification.SecPriorityConversationPreferenceController;
import com.samsung.android.settings.notification.SecSilentConversationPreferenceController;
import com.samsung.android.settings.notification.app.NotificationsOnSecInsetCategoryTwoPreferenceController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ConversationNotificationSettings extends NotificationSettings {
    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        ((NotificationSettings) this).mControllers = arrayList;
        arrayList.add(new ConversationHeaderPreferenceController(context, this));
        ((NotificationSettings) this)
                .mControllers.add(
                        new BlockPreferenceController(
                                context, this.mDependentFieldListener, this.mBackend, false));
        ((NotificationSettings) this)
                .mControllers.add(
                        new HighImportancePreferenceController(
                                context, this.mBackend, this.mDependentFieldListener));
        ((NotificationSettings) this)
                .mControllers.add(
                        new SoundPreferenceController(
                                context, this, this.mDependentFieldListener, this.mBackend));
        ((NotificationSettings) this)
                .mControllers.add(new VibrationPreferenceController(context, this.mBackend));
        List list = ((NotificationSettings) this).mControllers;
        new LockPatternUtils(context);
        list.add(new VisibilityPreferenceController(context, this.mBackend));
        ((NotificationSettings) this)
                .mControllers.add(new LightsPreferenceController(context, this.mBackend));
        ((NotificationSettings) this)
                .mControllers.add(new BadgePreferenceController(context, this.mBackend));
        ((NotificationSettings) this)
                .mControllers.add(new NotificationsOffPreferenceController(context, null));
        List list2 = ((NotificationSettings) this).mControllers;
        FragmentManager childFragmentManager = getChildFragmentManager();
        BubblePreferenceController bubblePreferenceController =
                new BubblePreferenceController(context, this.mBackend);
        bubblePreferenceController.mFragmentManager = childFragmentManager;
        bubblePreferenceController.mIsAppPage = false;
        bubblePreferenceController.mListener = null;
        list2.add(bubblePreferenceController);
        ((NotificationSettings) this)
                .mControllers.add(new BubbleLinkPreferenceController(context, null));
        List list3 = ((NotificationSettings) this).mControllers;
        ConversationDemotePreferenceController conversationDemotePreferenceController =
                new ConversationDemotePreferenceController(context, this.mBackend);
        conversationDemotePreferenceController.mHostFragment = this;
        list3.add(conversationDemotePreferenceController);
        ((NotificationSettings) this)
                .mControllers.add(
                        new SecPriorityConversationPreferenceController(
                                context, this.mBackend, this.mDependentFieldListener));
        ((NotificationSettings) this)
                .mControllers.add(
                        new SecAlertConversationPreferenceController(
                                context, this.mBackend, this.mDependentFieldListener));
        ((NotificationSettings) this)
                .mControllers.add(
                        new SecSilentConversationPreferenceController(
                                context, this.mBackend, this.mDependentFieldListener));
        ((NotificationSettings) this)
                .mControllers.add(
                        new NotificationsOnSecInsetCategoryTwoPreferenceController(context, null));
        ((NotificationSettings) this)
                .mControllers.add(
                        new NotificationsOnSecInsetCategoryPopupPreferenceController(
                                context, null));
        ((NotificationSettings) this)
                .mControllers.add(new SecBubbleCategoryPreferenceController(context, null));
        return new ArrayList(((NotificationSettings) this).mControllers);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "ConvoSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1830;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_conversation_notification_settings;
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
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        if (this.mUid < 0
                || TextUtils.isEmpty(this.mPkg)
                || this.mPkgInfo == null
                || this.mChannel == null) {
            Log.w("ConvoSettings", "Missing package or uid or packageinfo or channel");
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
                    this.mConversationDrawable,
                    this.mConversationInfo,
                    this.mSuspendedAppsAdmin,
                    null);
            notificationPreferenceController.displayPreference(getPreferenceScreen());
        }
        updatePreferenceStates();
        animatePanel();
    }
}
