package com.android.settings.widget;

import android.content.Context;
import android.text.TextUtils;

import com.android.settings.network.InternetPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SummaryUpdater {
    public final Context mContext;
    public final OnSummaryChangeListener mListener;
    public String mSummary;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnSummaryChangeListener {}

    public SummaryUpdater(Context context, OnSummaryChangeListener onSummaryChangeListener) {
        this.mContext = context;
        this.mListener = onSummaryChangeListener;
    }

    public abstract String getSummary();

    public final void notifyChangeIfNeeded() {
        String summary = getSummary();
        if (TextUtils.equals(this.mSummary, summary)) {
            return;
        }
        this.mSummary = summary;
        OnSummaryChangeListener onSummaryChangeListener = this.mListener;
        if (onSummaryChangeListener != null) {
            InternetPreferenceController internetPreferenceController =
                    (InternetPreferenceController) onSummaryChangeListener;
            if (internetPreferenceController.mInternetType == 2) {
                internetPreferenceController.updateState(internetPreferenceController.mPreference);
            }
        }
    }
}
