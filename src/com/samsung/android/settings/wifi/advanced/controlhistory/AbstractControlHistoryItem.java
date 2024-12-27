package com.samsung.android.settings.wifi.advanced.controlhistory;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.text.DateFormat;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class AbstractControlHistoryItem {
    public final Context mContext;
    public final DateFormat mDateFormat;
    public final int mLastEvent;
    public final long mLastEventTime;
    public final String mPackageName;
    public final DateFormat mTimeFormat;

    public AbstractControlHistoryItem(Context context, String str, int i, long j) {
        this.mContext = context;
        this.mPackageName = str;
        this.mLastEvent = i;
        this.mLastEventTime = j;
        this.mDateFormat = android.text.format.DateFormat.getDateFormat(context);
        this.mTimeFormat = android.text.format.DateFormat.getTimeFormat(context);
    }

    public abstract Drawable getIcon();

    public abstract String getTitle();
}
