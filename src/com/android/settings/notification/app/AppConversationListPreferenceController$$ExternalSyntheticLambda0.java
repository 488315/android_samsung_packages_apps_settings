package com.android.settings.notification.app;

import android.service.notification.ConversationChannelWrapper;

import java.util.Comparator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class AppConversationListPreferenceController$$ExternalSyntheticLambda0
        implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        ConversationChannelWrapper conversationChannelWrapper = (ConversationChannelWrapper) obj;
        ConversationChannelWrapper conversationChannelWrapper2 = (ConversationChannelWrapper) obj2;
        return conversationChannelWrapper.getNotificationChannel().isImportantConversation()
                        != conversationChannelWrapper2
                                .getNotificationChannel()
                                .isImportantConversation()
                ? Boolean.compare(
                        conversationChannelWrapper2
                                .getNotificationChannel()
                                .isImportantConversation(),
                        conversationChannelWrapper
                                .getNotificationChannel()
                                .isImportantConversation())
                : conversationChannelWrapper
                        .getNotificationChannel()
                        .getId()
                        .compareTo(conversationChannelWrapper2.getNotificationChannel().getId());
    }
}
