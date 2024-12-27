package com.samsung.android.settings.widget;

import android.R;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;
import android.widget.Toast;

import androidx.preference.PreferenceViewHolder;
import androidx.preference.SecPreference;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SecDisabledAppearancePreference extends SecPreference {
    public final Context mContext;
    public boolean mIsEnabled;
    public String mMsg;
    public TextView mSummary;
    public TextView mTitle;

    public SecDisabledAppearancePreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsEnabled = true;
        this.mMsg = ApnSettings.MVNO_NONE;
        this.mContext = context;
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        boolean z = isEnabled() && this.mIsEnabled;
        this.mTitle = (TextView) preferenceViewHolder.itemView.findViewById(R.id.title);
        this.mSummary = (TextView) preferenceViewHolder.itemView.findViewById(R.id.summary);
        this.mTitle.setEnabled(z);
        this.mSummary.setEnabled(z);
    }

    @Override // androidx.preference.Preference
    public final void onClick() {
        if (this.mIsEnabled || TextUtils.isEmpty(this.mMsg)) {
            return;
        }
        Toast.makeText(this.mContext, this.mMsg, 0).show();
    }

    public final void setEnabledAppearance(boolean z) {
        this.mIsEnabled = z;
        notifyChanged();
    }
}
