package com.android.settings.notification.history;

import android.view.View;
import android.widget.DateTimeView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class NotificationHistoryViewHolder extends RecyclerView.ViewHolder {
    public final TextView mSummary;
    public final DateTimeView mTime;
    public final TextView mTitle;

    public NotificationHistoryViewHolder(View view) {
        super(view);
        this.mTime = view.findViewById(R.id.timestamp);
        this.mTitle = (TextView) view.findViewById(R.id.title);
        this.mSummary = (TextView) view.findViewById(R.id.text);
    }
}
