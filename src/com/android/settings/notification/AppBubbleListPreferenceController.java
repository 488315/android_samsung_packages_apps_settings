package com.android.settings.notification;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.content.pm.ShortcutInfo;
import android.graphics.drawable.Drawable;
import android.service.notification.ConversationChannelWrapper;
import android.view.View;
import android.widget.ImageView;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settings.notification.app.AppConversationListPreferenceController;
import com.android.settings.notification.app.NotificationPreferenceController;
import com.android.settingslib.RestrictedLockUtils;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppBubbleListPreferenceController
        extends AppConversationListPreferenceController {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ConversationPreference extends Preference implements View.OnClickListener {
        public boolean mOnClickBubbles;
        public View.OnClickListener mOnClickListener;

        @Override // androidx.preference.Preference
        public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
            super.onBindViewHolder(preferenceViewHolder);
            ImageView imageView =
                    (ImageView) preferenceViewHolder.itemView.findViewById(R.id.button);
            imageView.setContentDescription(
                    this.mOnClickBubbles
                            ? getContext()
                                    .getString(R.string.bubble_app_setting_bubble_conversation)
                            : getContext()
                                    .getString(R.string.bubble_app_setting_unbubble_conversation));
            imageView.setOnClickListener(this.mOnClickListener);
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            View.OnClickListener onClickListener = this.mOnClickListener;
            if (onClickListener != null) {
                onClickListener.onClick(view);
            }
        }
    }

    @Override // com.android.settings.notification.app.AppConversationListPreferenceController
    public Preference createConversationPref(
            final ConversationChannelWrapper conversationChannelWrapper) {
        final ConversationPreference conversationPreference =
                new ConversationPreference(((NotificationPreferenceController) this).mContext);
        conversationPreference.setWidgetLayoutResource(R.layout.bubble_conversation_remove_button);
        populateConversationPreference(conversationChannelWrapper, conversationPreference);
        conversationPreference.mOnClickBubbles = this.mAppRow.bubblePreference == 1;
        conversationPreference.mOnClickListener =
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.notification.AppBubbleListPreferenceController$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        AppBubbleListPreferenceController appBubbleListPreferenceController =
                                AppBubbleListPreferenceController.this;
                        ConversationChannelWrapper conversationChannelWrapper2 =
                                conversationChannelWrapper;
                        AppBubbleListPreferenceController.ConversationPreference
                                conversationPreference2 = conversationPreference;
                        appBubbleListPreferenceController.getClass();
                        conversationChannelWrapper2.getNotificationChannel().setAllowBubbles(-1);
                        NotificationBackend.AppRow appRow =
                                appBubbleListPreferenceController.mAppRow;
                        String str = appRow.pkg;
                        int i = appRow.uid;
                        NotificationChannel notificationChannel =
                                conversationChannelWrapper2.getNotificationChannel();
                        appBubbleListPreferenceController.mBackend.getClass();
                        NotificationBackend.updateChannel(str, i, notificationChannel);
                        appBubbleListPreferenceController.mPreference.removePreference(
                                conversationPreference2);
                        if (appBubbleListPreferenceController.mPreference.getPreferenceCount()
                                == 0) {
                            appBubbleListPreferenceController.mPreference.setVisible(false);
                        }
                    }
                };
        return conversationPreference;
    }

    @Override // com.android.settings.notification.app.AppConversationListPreferenceController
    public List<ConversationChannelWrapper> filterAndSortConversations(
            List<ConversationChannelWrapper> list) {
        return (List)
                list.stream()
                        .sorted(this.mConversationComparator)
                        .filter(
                                new Predicate() { // from class:
                                                  // com.android.settings.notification.AppBubbleListPreferenceController$$ExternalSyntheticLambda1
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        ConversationChannelWrapper conversationChannelWrapper =
                                                (ConversationChannelWrapper) obj;
                                        int i =
                                                AppBubbleListPreferenceController.this
                                                        .mAppRow
                                                        .bubblePreference;
                                        return i == 2
                                                ? conversationChannelWrapper
                                                        .getNotificationChannel()
                                                        .canBubble()
                                                : i == 1
                                                        && conversationChannelWrapper
                                                                        .getNotificationChannel()
                                                                        .getAllowBubbles()
                                                                == 0;
                                    }
                                })
                        .collect(Collectors.toList());
    }

    @Override // com.android.settings.notification.app.AppConversationListPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bubble_conversations";
    }

    @Override // com.android.settings.notification.app.AppConversationListPreferenceController
    public final int getTitleResId() {
        return this.mAppRow.bubblePreference == 2
                ? R.string.bubble_app_setting_selected_conversation_title
                : R.string.bubble_app_setting_excluded_conversation_title;
    }

    @Override // com.android.settings.notification.app.AppConversationListPreferenceController,
              // com.android.settings.notification.app.NotificationPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        NotificationBackend.AppRow appRow = this.mAppRow;
        if (appRow == null || appRow.banned) {
            return false;
        }
        if (this.mChannel != null) {
            String str = appRow.pkg;
            int i = appRow.uid;
            this.mBackend.getClass();
            if (NotificationBackend.onlyHasDefaultChannel(i, str)
                    || "miscellaneous".equals(this.mChannel.getId())) {
                return false;
            }
        }
        return this.mAppRow.bubblePreference != 0;
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController
    public final void onResume(
            NotificationBackend.AppRow appRow,
            NotificationChannel notificationChannel,
            NotificationChannelGroup notificationChannelGroup,
            Drawable drawable,
            ShortcutInfo shortcutInfo,
            RestrictedLockUtils.EnforcedAdmin enforcedAdmin,
            List list) {
        super.onResume(
                appRow,
                notificationChannel,
                notificationChannelGroup,
                drawable,
                shortcutInfo,
                enforcedAdmin,
                list);
        if (this.mAppRow == null) {
            return;
        }
        new AppConversationListPreferenceController.AnonymousClass1().execute(new Void[0]);
    }

    @Override // com.android.settings.notification.app.AppConversationListPreferenceController
    public final void populateList$1() {
        super.populateList$1();
        PreferenceCategory preferenceCategory = this.mPreference;
        if (preferenceCategory == null) {
            return;
        }
        preferenceCategory.setVisible(preferenceCategory.getPreferenceCount() > 0);
    }

    @Override // com.android.settings.notification.app.AppConversationListPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        preference.setVisible(false);
        super.updateState(preference);
    }
}
