package com.android.settings.notification.app;

import android.content.Context;
import android.content.pm.ShortcutInfo;
import android.os.Bundle;
import android.os.UserHandle;
import android.service.notification.ConversationChannelWrapper;
import android.text.BidiFormatter;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.notification.NotificationBackend;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.widget.AppPreference;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.settings.widget.SecUnclickablePreference;

import java.text.Collator;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ConversationListPreferenceController extends AbstractPreferenceController {
    public final NotificationBackend mBackend;
    Comparator<ConversationChannelWrapper> mConversationComparator;
    public PreferenceGroup mPreferenceGroup;

    /* renamed from: $r8$lambda$8Tk9FmWLidk-Z3TJ-pk0U4IpZ6M, reason: not valid java name */
    public static void m988$r8$lambda$8Tk9FmWLidkZ3TJpk0U4IpZ6M(
            ConversationListPreferenceController conversationListPreferenceController,
            ConversationChannelWrapper conversationChannelWrapper,
            AppPreference appPreference) {
        conversationListPreferenceController.getClass();
        CharSequence title = appPreference.getTitle();
        Bundle bundle = new Bundle();
        bundle.putInt(
                NetworkAnalyticsConstants.DataPoints.UID, conversationChannelWrapper.getUid());
        bundle.putString("package", conversationChannelWrapper.getPkg());
        bundle.putString(
                "android.provider.extra.CHANNEL_ID",
                conversationChannelWrapper.getNotificationChannel().getId());
        bundle.putString(
                "android.provider.extra.CONVERSATION_ID",
                conversationChannelWrapper.getNotificationChannel().getConversationId());
        SubSettingLauncher subSettingLauncher =
                new SubSettingLauncher(conversationListPreferenceController.mContext);
        String name = ChannelNotificationSettings.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mArguments = bundle;
        launchRequest.mExtras = bundle;
        launchRequest.mUserHandle =
                UserHandle.getUserHandleForUid(conversationChannelWrapper.getUid());
        launchRequest.mTitle = title;
        launchRequest.mSourceMetricsCategory = 1834;
        subSettingLauncher.launch();
    }

    public static AppPreference $r8$lambda$wI0yZPbjwmretWfozxHBppIYuVo(
            final ConversationListPreferenceController conversationListPreferenceController,
            final ConversationChannelWrapper conversationChannelWrapper) {
        conversationListPreferenceController.getClass();
        final AppPreference appPreference =
                new AppPreference(conversationListPreferenceController.mContext);
        ShortcutInfo shortcutInfo = conversationChannelWrapper.getShortcutInfo();
        appPreference.setTitle(
                shortcutInfo != null
                        ? BidiFormatter.getInstance().unicodeWrap(shortcutInfo.getLabel())
                        : conversationChannelWrapper.getNotificationChannel().getName());
        appPreference.setSummary(
                TextUtils.isEmpty(conversationChannelWrapper.getGroupLabel())
                        ? conversationChannelWrapper.getParentChannelLabel()
                        : conversationListPreferenceController.mContext.getString(
                                R.string.notification_conversation_summary,
                                conversationChannelWrapper.getParentChannelLabel(),
                                conversationChannelWrapper.getGroupLabel()));
        Context context = conversationListPreferenceController.mContext;
        ShortcutInfo shortcutInfo2 = conversationChannelWrapper.getShortcutInfo();
        String pkg = conversationChannelWrapper.getPkg();
        int uid = conversationChannelWrapper.getUid();
        boolean isImportantConversation =
                conversationChannelWrapper.getNotificationChannel().isImportantConversation();
        conversationListPreferenceController.mBackend.getClass();
        appPreference.setIcon(
                NotificationBackend.getConversationDrawable(
                        context, shortcutInfo2, pkg, uid, isImportantConversation));
        appPreference.setKey(conversationChannelWrapper.getNotificationChannel().getId());
        appPreference.setOnPreferenceClickListener(
                new Preference
                        .OnPreferenceClickListener() { // from class:
                                                       // com.android.settings.notification.app.ConversationListPreferenceController$$ExternalSyntheticLambda3
                    @Override // androidx.preference.Preference.OnPreferenceClickListener
                    public final boolean onPreferenceClick(Preference preference) {
                        ConversationListPreferenceController
                                .m988$r8$lambda$8Tk9FmWLidkZ3TJpk0U4IpZ6M(
                                        ConversationListPreferenceController.this,
                                        conversationChannelWrapper,
                                        appPreference);
                        return true;
                    }
                });
        return appPreference;
    }

    public ConversationListPreferenceController(
            Context context, NotificationBackend notificationBackend) {
        super(context);
        this.mConversationComparator =
                new Comparator() { // from class:
                                   // com.android.settings.notification.app.ConversationListPreferenceController.1
                    public final Collator sCollator = Collator.getInstance();

                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        ConversationChannelWrapper conversationChannelWrapper =
                                (ConversationChannelWrapper) obj;
                        ConversationChannelWrapper conversationChannelWrapper2 =
                                (ConversationChannelWrapper) obj2;
                        if (conversationChannelWrapper.getShortcutInfo() != null
                                && conversationChannelWrapper2.getShortcutInfo() == null) {
                            return -1;
                        }
                        if (conversationChannelWrapper.getShortcutInfo() != null
                                || conversationChannelWrapper2.getShortcutInfo() == null) {
                            if (conversationChannelWrapper.getShortcutInfo() == null
                                    && conversationChannelWrapper2.getShortcutInfo() == null) {
                                return conversationChannelWrapper
                                        .getNotificationChannel()
                                        .getId()
                                        .compareTo(
                                                conversationChannelWrapper2
                                                        .getNotificationChannel()
                                                        .getId());
                            }
                            if (conversationChannelWrapper.getShortcutInfo().getLabel() != null
                                    || conversationChannelWrapper2.getShortcutInfo().getLabel()
                                            == null) {
                                if (conversationChannelWrapper.getShortcutInfo().getLabel() == null
                                        || conversationChannelWrapper2.getShortcutInfo().getLabel()
                                                != null) {
                                    return this.sCollator.compare(
                                            conversationChannelWrapper
                                                    .getShortcutInfo()
                                                    .getLabel()
                                                    .toString(),
                                            conversationChannelWrapper2
                                                    .getShortcutInfo()
                                                    .getLabel()
                                                    .toString());
                                }
                                return -1;
                            }
                        }
                        return 1;
                    }
                };
        this.mBackend = notificationBackend;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreferenceGroup =
                (PreferenceGroup) preferenceScreen.findPreference(getPreferenceKey());
    }

    public abstract SecUnclickablePreference getSummaryPreference();

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    public abstract boolean matchesFilter(ConversationChannelWrapper conversationChannelWrapper);

    public void populateConversations(List<ConversationChannelWrapper> list) {
        final AtomicInteger atomicInteger = new AtomicInteger(100);
        list.stream()
                .filter(
                        new Predicate() { // from class:
                                          // com.android.settings.notification.app.ConversationListPreferenceController$$ExternalSyntheticLambda0
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                ConversationListPreferenceController
                                        conversationListPreferenceController =
                                                ConversationListPreferenceController.this;
                                ConversationChannelWrapper conversationChannelWrapper =
                                        (ConversationChannelWrapper) obj;
                                conversationListPreferenceController.getClass();
                                return !conversationChannelWrapper
                                                .getNotificationChannel()
                                                .isDemoted()
                                        && conversationListPreferenceController.matchesFilter(
                                                conversationChannelWrapper);
                            }
                        })
                .sorted(this.mConversationComparator)
                .map(
                        new Function() { // from class:
                                         // com.android.settings.notification.app.ConversationListPreferenceController$$ExternalSyntheticLambda1
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                return ConversationListPreferenceController
                                        .$r8$lambda$wI0yZPbjwmretWfozxHBppIYuVo(
                                                ConversationListPreferenceController.this,
                                                (ConversationChannelWrapper) obj);
                            }
                        })
                .forEachOrdered(
                        new Consumer() { // from class:
                                         // com.android.settings.notification.app.ConversationListPreferenceController$$ExternalSyntheticLambda2
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                ConversationListPreferenceController
                                        conversationListPreferenceController =
                                                ConversationListPreferenceController.this;
                                AtomicInteger atomicInteger2 = atomicInteger;
                                Preference preference = (Preference) obj;
                                conversationListPreferenceController.getClass();
                                preference.setOrder(atomicInteger2.getAndIncrement());
                                conversationListPreferenceController.mPreferenceGroup.addPreference(
                                        preference);
                            }
                        });
    }

    public final void updateList$1(List list) {
        this.mPreferenceGroup.setVisible(false);
        this.mPreferenceGroup.removeAll();
        if (list != null) {
            populateConversations(list);
        }
        if (this.mPreferenceGroup.getPreferenceCount() != 0) {
            SecUnclickablePreference summaryPreference = getSummaryPreference();
            summaryPreference.setKey(getPreferenceKey() + "_summary");
            this.mPreferenceGroup.addPreference(summaryPreference);
            this.mPreferenceGroup.setVisible(true);
        }
    }
}
