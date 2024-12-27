package com.samsung.android.settings.widget;

import android.R;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.preference.PreferenceViewHolder;
import androidx.preference.SecSwitchPreference;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SecDisabledAppearanceSwitchPreference extends SecSwitchPreference {
    public final Context mContext;
    public boolean mEnabledAppearance;
    public String mMsg;
    public TextView mSummary;
    public TextView mTitle;

    public SecDisabledAppearanceSwitchPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mEnabledAppearance = false;
        this.mMsg = ApnSettings.MVNO_NONE;
        this.mContext = context;
    }

    @Override // androidx.preference.SecSwitchPreference,
              // androidx.preference.SwitchPreferenceCompat, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        boolean z = isEnabled() && this.mEnabledAppearance;
        this.mTitle = (TextView) preferenceViewHolder.itemView.findViewById(R.id.title);
        this.mSummary = (TextView) preferenceViewHolder.itemView.findViewById(R.id.summary);
        this.mTitle.setEnabled(z);
        this.mSummary.setEnabled(z);
        View findViewById = preferenceViewHolder.itemView.findViewById(R.id.switch_widget);
        if (findViewById != null) {
            findViewById.setEnabled(z);
            findViewById.setClickable(z);
        }
        View findViewById2 =
                preferenceViewHolder.itemView.findViewById(com.android.settings.R.id.switch_widget);
        if (findViewById2 != null) {
            findViewById2.setEnabled(z);
            findViewById2.setClickable(z);
        }
    }

    @Override // androidx.preference.TwoStatePreference, androidx.preference.Preference
    public final void onClick() {
        if (this.mEnabledAppearance) {
            super.onClick();
        } else {
            if (TextUtils.isEmpty(this.mMsg)) {
                return;
            }
            Toast.makeText(this.mContext, this.mMsg, 0).show();
        }
    }
}
