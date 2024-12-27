package com.android.settings.notification.app;

import android.content.Context;
import android.content.pm.ParceledListSlice;
import android.content.pm.ShortcutInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.service.notification.ConversationChannelWrapper;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.notification.NotificationBackend;
import com.android.settingslib.widget.AppPreference;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AppConversationListPreferenceController extends NotificationPreferenceController {
    public final AppConversationListPreferenceController$$ExternalSyntheticLambda0
            mConversationComparator;
    public List mConversations;
    public PreferenceCategory mPreference;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.notification.app.AppConversationListPreferenceController$1, reason: invalid class name */
    public final class AnonymousClass1 extends AsyncTask {
        public AnonymousClass1() {}

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            AppConversationListPreferenceController appConversationListPreferenceController =
                    AppConversationListPreferenceController.this;
            NotificationBackend notificationBackend =
                    appConversationListPreferenceController.mBackend;
            NotificationBackend.AppRow appRow = appConversationListPreferenceController.mAppRow;
            String str = appRow.pkg;
            int i = appRow.uid;
            notificationBackend.getClass();
            ParceledListSlice conversations = NotificationBackend.getConversations(i, str);
            if (conversations == null) {
                return null;
            }
            AppConversationListPreferenceController appConversationListPreferenceController2 =
                    AppConversationListPreferenceController.this;
            appConversationListPreferenceController2.mConversations =
                    appConversationListPreferenceController2.filterAndSortConversations(
                            conversations.getList());
            return null;
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            AppConversationListPreferenceController appConversationListPreferenceController =
                    AppConversationListPreferenceController.this;
            if (((NotificationPreferenceController) appConversationListPreferenceController)
                            .mContext
                    == null) {
                return;
            }
            appConversationListPreferenceController.populateList$1();
        }
    }

    public AppConversationListPreferenceController(
            Context context, NotificationBackend notificationBackend) {
        super(context, notificationBackend);
        this.mConversations = new ArrayList();
        this.mConversationComparator =
                new AppConversationListPreferenceController$$ExternalSyntheticLambda0();
    }

    public Preference createConversationPref(
            ConversationChannelWrapper conversationChannelWrapper) {
        AppPreference appPreference =
                new AppPreference(((NotificationPreferenceController) this).mContext);
        populateConversationPreference(conversationChannelWrapper, appPreference);
        return appPreference;
    }

    public List filterAndSortConversations(List list) {
        Collections.sort(list, this.mConversationComparator);
        return list;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return "conversations";
    }

    public int getTitleResId() {
        return R.string.conversations_category_title;
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x005d, code lost:

       if (r0 != false) goto L28;
    */
    @Override // com.android.settings.notification.app.NotificationPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean isAvailable() {
        /*
            r4 = this;
            com.android.settings.notification.NotificationBackend$AppRow r0 = r4.mAppRow
            r1 = 0
            if (r0 != 0) goto L6
            return r1
        L6:
            boolean r2 = r0.banned
            if (r2 == 0) goto Lb
            return r1
        Lb:
            android.app.NotificationChannel r2 = r4.mChannel
            com.android.settings.notification.NotificationBackend r3 = r4.mBackend
            if (r2 == 0) goto L2d
            java.lang.String r2 = r0.pkg
            int r0 = r0.uid
            r3.getClass()
            boolean r0 = com.android.settings.notification.NotificationBackend.onlyHasDefaultChannel(r0, r2)
            if (r0 != 0) goto L2c
            android.app.NotificationChannel r0 = r4.mChannel
            java.lang.String r0 = r0.getId()
            java.lang.String r2 = "miscellaneous"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L2d
        L2c:
            return r1
        L2d:
            com.android.settings.notification.NotificationBackend$AppRow r0 = r4.mAppRow
            java.lang.String r2 = r0.pkg
            int r0 = r0.uid
            r3.getClass()
            android.content.pm.ParceledListSlice r0 = com.android.settings.notification.NotificationBackend.getConversations(r0, r2)
            java.util.List r0 = r0.getList()
            if (r0 == 0) goto L5f
            boolean r0 = r0.isEmpty()
            if (r0 == 0) goto L47
            goto L5f
        L47:
            com.android.settings.notification.NotificationBackend$AppRow r0 = r4.mAppRow
            java.lang.String r2 = r0.pkg
            int r0 = r0.uid
            android.app.INotificationManager r3 = com.android.settings.notification.NotificationBackend.sINM     // Catch: java.lang.Exception -> L54
            boolean r0 = r3.hasSentValidMsg(r2, r0)     // Catch: java.lang.Exception -> L54
            goto L5d
        L54:
            r0 = move-exception
            java.lang.String r2 = "NotificationBackend"
            java.lang.String r3 = "Error calling NoMan"
            android.util.Log.w(r2, r3, r0)
            r0 = r1
        L5d:
            if (r0 != 0) goto L6b
        L5f:
            com.android.settings.notification.NotificationBackend$AppRow r4 = r4.mAppRow
            java.lang.String r0 = r4.pkg
            int r4 = r4.uid
            boolean r4 = com.android.settings.notification.NotificationBackend.isInInvalidMsgState(r4, r0)
            if (r4 == 0) goto L6c
        L6b:
            r1 = 1
        L6c:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.notification.app.AppConversationListPreferenceController.isAvailable():boolean");
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController
    public final boolean isIncludedInFilter() {
        return false;
    }

    public final void populateConversationPreference(
            final ConversationChannelWrapper conversationChannelWrapper,
            final Preference preference) {
        ShortcutInfo shortcutInfo = conversationChannelWrapper.getShortcutInfo();
        preference.setTitle(
                shortcutInfo != null
                        ? shortcutInfo.getLabel()
                        : conversationChannelWrapper.getNotificationChannel().getName());
        preference.setSummary(
                conversationChannelWrapper.getNotificationChannel().getGroup() != null
                        ? ((NotificationPreferenceController) this)
                                .mContext.getString(
                                        R.string.notification_conversation_summary,
                                        conversationChannelWrapper.getParentChannelLabel(),
                                        conversationChannelWrapper.getGroupLabel())
                        : conversationChannelWrapper.getParentChannelLabel());
        if (shortcutInfo != null) {
            Context context = ((NotificationPreferenceController) this).mContext;
            NotificationBackend.AppRow appRow = this.mAppRow;
            String str = appRow.pkg;
            int i = appRow.uid;
            boolean isImportantConversation =
                    conversationChannelWrapper.getNotificationChannel().isImportantConversation();
            this.mBackend.getClass();
            preference.setIcon(
                    NotificationBackend.getConversationDrawable(
                            context, shortcutInfo, str, i, isImportantConversation));
        }
        preference.setKey(conversationChannelWrapper.getNotificationChannel().getId());
        preference.setOnPreferenceClickListener(
                new Preference
                        .OnPreferenceClickListener() { // from class:
                                                       // com.android.settings.notification.app.AppConversationListPreferenceController$$ExternalSyntheticLambda1
                    @Override // androidx.preference.Preference.OnPreferenceClickListener
                    public final boolean onPreferenceClick(Preference preference2) {
                        ConversationChannelWrapper conversationChannelWrapper2 =
                                conversationChannelWrapper;
                        AppConversationListPreferenceController
                                appConversationListPreferenceController =
                                        AppConversationListPreferenceController.this;
                        appConversationListPreferenceController.getClass();
                        CharSequence title = preference.getTitle();
                        Bundle bundle = new Bundle();
                        bundle.putInt(
                                NetworkAnalyticsConstants.DataPoints.UID,
                                appConversationListPreferenceController.mAppRow.uid);
                        bundle.putString(
                                "package", appConversationListPreferenceController.mAppRow.pkg);
                        bundle.putString(
                                "android.provider.extra.CHANNEL_ID",
                                conversationChannelWrapper2
                                        .getNotificationChannel()
                                        .getParentChannelId());
                        bundle.putString(
                                "android.provider.extra.CONVERSATION_ID",
                                conversationChannelWrapper2
                                        .getNotificationChannel()
                                        .getConversationId());
                        bundle.putBoolean("fromSettings", true);
                        SubSettingLauncher subSettingLauncher =
                                new SubSettingLauncher(
                                        ((NotificationPreferenceController)
                                                        appConversationListPreferenceController)
                                                .mContext);
                        String name = ChannelNotificationSettings.class.getName();
                        SubSettingLauncher.LaunchRequest launchRequest =
                                subSettingLauncher.mLaunchRequest;
                        launchRequest.mDestinationName = name;
                        launchRequest.mArguments = bundle;
                        launchRequest.mExtras = bundle;
                        launchRequest.mTitle = title;
                        launchRequest.mSourceMetricsCategory = 72;
                        subSettingLauncher.launch();
                        return true;
                    }
                });
    }

    public void populateList$1() {
        PreferenceCategory preferenceCategory = this.mPreference;
        if (preferenceCategory == null) {
            return;
        }
        preferenceCategory.setTitle(getTitleResId());
        this.mPreference.removeAll();
        this.mPreference.setVisible(!this.mConversations.isEmpty());
        for (ConversationChannelWrapper conversationChannelWrapper : this.mConversations) {
            if (!conversationChannelWrapper.getNotificationChannel().isDemoted()) {
                this.mPreference.addPreference(createConversationPref(conversationChannelWrapper));
            }
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        this.mPreference = (PreferenceCategory) preference;
        if (this.mAppRow == null) {
            return;
        }
        new AnonymousClass1().execute(new Void[0]);
    }
}
