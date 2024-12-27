package com.samsung.android.settings.widget;

import android.R;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.preference.PreferenceViewHolder;
import androidx.preference.SecSwitchPreferenceScreen;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SecDisabledAppearanceSwitchPreferenceScreen extends SecSwitchPreferenceScreen {
    public final Context mContext;
    public final String mMsg;
    public TextView mSummary;
    public TextView mTitle;

    public SecDisabledAppearanceSwitchPreferenceScreen(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMsg = ApnSettings.MVNO_NONE;
        this.mContext = context;
    }

    @Override // androidx.preference.SecSwitchPreferenceScreen,
              // androidx.preference.SeslSwitchPreferenceScreen,
              // androidx.preference.SwitchPreferenceCompat, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        this.mTitle = (TextView) preferenceViewHolder.itemView.findViewById(R.id.title);
        this.mSummary = (TextView) preferenceViewHolder.itemView.findViewById(R.id.summary);
        this.mTitle.setEnabled(false);
        this.mSummary.setEnabled(false);
        View findViewById = preferenceViewHolder.itemView.findViewById(R.id.switch_widget);
        if (findViewById != null) {
            findViewById.setEnabled(false);
            findViewById.setClickable(false);
        }
        View findViewById2 =
                preferenceViewHolder.itemView.findViewById(com.android.settings.R.id.switch_widget);
        if (findViewById2 != null) {
            findViewById2.setEnabled(false);
            findViewById2.setClickable(false);
        }
    }

    @Override // androidx.preference.SeslSwitchPreferenceScreen,
              // androidx.preference.TwoStatePreference, androidx.preference.Preference
    public final void onClick() {
        if (TextUtils.isEmpty(this.mMsg)) {
            return;
        }
        Toast.makeText(this.mContext, this.mMsg, 0).show();
    }
}
