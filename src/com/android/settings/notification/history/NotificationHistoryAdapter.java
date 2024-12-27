package com.android.settings.notification.history;

import android.app.INotificationManager;
import android.app.NotificationHistory;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Slog;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.TextView;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.logging.UiEventLogger;
import com.android.settings.R;
import com.android.settings.notification.history.NotificationHistoryActivity;
import com.android.settings.notification.history.NotificationHistoryRecyclerView;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class NotificationHistoryAdapter extends RecyclerView.Adapter implements NotificationHistoryRecyclerView.OnItemSwipeDeleteListener {
    public NotificationHistoryActivity$$ExternalSyntheticLambda7 mListener;
    public INotificationManager mNm;
    public UiEventLogger mUiEventLogger;
    public List mValues;

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.mValues.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final long getItemId(int i) {
        return ((NotificationHistory.HistoricalNotification) this.mValues.get(i)).hashCode();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v5, types: [android.view.View$OnClickListener, com.android.settings.notification.history.NotificationHistoryAdapter$$ExternalSyntheticLambda1] */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        final NotificationHistoryViewHolder notificationHistoryViewHolder = (NotificationHistoryViewHolder) viewHolder;
        final NotificationHistory.HistoricalNotification historicalNotification = (NotificationHistory.HistoricalNotification) this.mValues.get(i);
        String title = historicalNotification.getTitle();
        notificationHistoryViewHolder.mTitle.setText(title);
        notificationHistoryViewHolder.mTitle.setVisibility(title != null ? 0 : 4);
        String text = historicalNotification.getText();
        notificationHistoryViewHolder.mSummary.setText(text);
        notificationHistoryViewHolder.mSummary.setVisibility(text == null ? 8 : 0);
        notificationHistoryViewHolder.mTime.setTime(historicalNotification.getPostedTimeMs());
        final ?? r1 = new View.OnClickListener() { // from class: com.android.settings.notification.history.NotificationHistoryAdapter$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NotificationHistoryAdapter notificationHistoryAdapter = NotificationHistoryAdapter.this;
                NotificationHistory.HistoricalNotification historicalNotification2 = historicalNotification;
                int i2 = i;
                NotificationHistoryViewHolder notificationHistoryViewHolder2 = notificationHistoryViewHolder;
                notificationHistoryAdapter.mUiEventLogger.logWithPosition(NotificationHistoryActivity.NotificationHistoryEvent.NOTIFICATION_HISTORY_OLDER_ITEM_CLICK, historicalNotification2.getUid(), historicalNotification2.getPackage(), i2);
                notificationHistoryViewHolder2.itemView.getContext().startActivityAsUser(new Intent("android.settings.CHANNEL_NOTIFICATION_SETTINGS").setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG).putExtra("android.provider.extra.APP_PACKAGE", historicalNotification2.getPackage()).putExtra("android.provider.extra.CHANNEL_ID", historicalNotification2.getChannelId()).putExtra("android.provider.extra.CONVERSATION_ID", historicalNotification2.getConversationId()), UserHandle.of(historicalNotification2.getUserId()));
            }
        };
        notificationHistoryViewHolder.itemView.setOnClickListener(r1);
        notificationHistoryViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.settings.notification.history.NotificationHistoryAdapter$$ExternalSyntheticLambda2
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                r1.onClick(view);
                return true;
            }
        });
        notificationHistoryViewHolder.itemView.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.settings.notification.history.NotificationHistoryAdapter.1
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, view.getResources().getText(R.string.notification_history_view_settings)));
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_DISMISS);
            }

            @Override // android.view.View.AccessibilityDelegate
            public final boolean performAccessibilityAction(View view, int i2, Bundle bundle) {
                super.performAccessibilityAction(view, i2, bundle);
                if (i2 != AccessibilityNodeInfo.AccessibilityAction.ACTION_DISMISS.getId()) {
                    return false;
                }
                NotificationHistoryAdapter.this.onItemSwipeDeleted(NotificationHistoryAdapter.this.mValues.indexOf(historicalNotification));
                return true;
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new NotificationHistoryViewHolder(MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(viewGroup, R.layout.notification_history_log_row, viewGroup, false));
    }

    public final void onItemSwipeDeleted(int i) {
        if (i > this.mValues.size() - 1) {
            StringBuilder m = ListPopupWindow$$ExternalSyntheticOutline0.m(i, "Tried to swipe element out of list: position: ", " size? ");
            m.append(this.mValues.size());
            Slog.d("NotiHistoryAdapter", m.toString());
            return;
        }
        NotificationHistory.HistoricalNotification historicalNotification = (NotificationHistory.HistoricalNotification) this.mValues.remove(i);
        if (historicalNotification != null) {
            try {
                this.mNm.deleteNotificationHistoryItem(historicalNotification.getPackage(), historicalNotification.getUid(), historicalNotification.getPostedTimeMs());
            } catch (RemoteException e) {
                Slog.e("NotiHistoryAdapter", "Failed to delete item", e);
            }
            this.mUiEventLogger.logWithPosition(NotificationHistoryActivity.NotificationHistoryEvent.NOTIFICATION_HISTORY_OLDER_ITEM_DELETE, historicalNotification.getUid(), historicalNotification.getPackage(), i);
        }
        int size = this.mValues.size();
        NotificationHistoryActivity$$ExternalSyntheticLambda7 notificationHistoryActivity$$ExternalSyntheticLambda7 = this.mListener;
        TextView textView = notificationHistoryActivity$$ExternalSyntheticLambda7.f$1;
        View view = notificationHistoryActivity$$ExternalSyntheticLambda7.f$2;
        StatusLogger$StatusLoggingProvider statusLogger$StatusLoggingProvider = NotificationHistoryActivity.STATUS_LOGGING_PROVIDER;
        NotificationHistoryActivity notificationHistoryActivity = notificationHistoryActivity$$ExternalSyntheticLambda7.f$0;
        textView.setText(notificationHistoryActivity.getResources().getQuantityString(R.plurals.notification_history_count, size, Integer.valueOf(size)));
        if (size == 0) {
            view.setVisibility(8);
            int i2 = notificationHistoryActivity.mTodayAppCount - 1;
            notificationHistoryActivity.mTodayAppCount = i2;
            if (i2 <= 0) {
                notificationHistoryActivity.findViewById(R.id.today_list).setVisibility(8);
            }
        }
        notifyItemRemoved(i);
    }
}
