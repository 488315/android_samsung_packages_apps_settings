package com.android.settings.notification.app;

import android.content.Context;
import android.service.notification.ConversationChannelWrapper;
import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.notification.NotificationBackend;

import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.samsung.android.settings.widget.SecUnclickablePreference;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AllConversationsPreferenceController
        extends ConversationListPreferenceController {
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.notification.app.AllConversationsPreferenceController$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            List list;
            ArrayList arrayList = new ArrayList();
            try {
                list = NotificationBackend.sINM.getConversations(false).getList();
            } catch (Exception e) {
                Log.w("AllConversationsPreferenceController", "Error calling NoMan", e);
                list = null;
            }
            String valueOf = String.valueOf(36405);
            String valueOf2 = String.valueOf(list.size());
            StatusData statusData = new StatusData();
            statusData.mStatusValue = valueOf2;
            statusData.mStatusKey = valueOf;
            arrayList.add(statusData);
            return arrayList;
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "other_conversations";
    }

    @Override // com.android.settings.notification.app.ConversationListPreferenceController
    public final SecUnclickablePreference getSummaryPreference() {
        SecUnclickablePreference secUnclickablePreference =
                new SecUnclickablePreference(this.mContext);
        secUnclickablePreference.setOrder(1000);
        secUnclickablePreference.setTitle(R.string.other_conversations_summary);
        return secUnclickablePreference;
    }

    @Override // com.android.settings.notification.app.ConversationListPreferenceController
    public final boolean matchesFilter(ConversationChannelWrapper conversationChannelWrapper) {
        return !conversationChannelWrapper.getNotificationChannel().isImportantConversation();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        this.mBackend.getClass();
        updateList$1(NotificationBackend.getConversations(false).getList());
    }
}
