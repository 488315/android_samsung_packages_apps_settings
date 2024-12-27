package com.android.settings.notification.history;

import android.view.View;
import android.widget.DateTimeView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class NotificationSbnViewHolder extends RecyclerView.ViewHolder {
    public final View mDivider;
    public final ImageView mIcon;
    public final TextView mPkgName;
    public final ImageView mProfileBadge;
    public final TextView mSummary;
    public final DateTimeView mTime;
    public final TextView mTitle;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.notification.history.NotificationSbnViewHolder$1, reason: invalid class name */
    public final class AnonymousClass1 extends AccessibilityDelegateCompat {
        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final void onInitializeAccessibilityNodeInfo(
                View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(
                    view, accessibilityNodeInfoCompat.mInfo);
            accessibilityNodeInfoCompat.addAction(
                    new AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                            16,
                            view.getResources()
                                    .getText(R.string.notification_history_open_notification)));
        }
    }

    public NotificationSbnViewHolder(View view) {
        super(view);
        this.mPkgName = (TextView) view.findViewById(R.id.pkgname);
        this.mIcon = (ImageView) view.findViewById(R.id.icon);
        this.mTime = view.findViewById(R.id.timestamp);
        this.mTitle = (TextView) view.findViewById(R.id.title);
        this.mSummary = (TextView) view.findViewById(R.id.text);
        this.mProfileBadge = (ImageView) view.findViewById(R.id.profile_badge);
        this.mDivider = view.findViewById(R.id.divider);
    }
}
