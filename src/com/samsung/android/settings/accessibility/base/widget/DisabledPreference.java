package com.samsung.android.settings.accessibility.base.widget;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class DisabledPreference extends Preference {
    public final Context mContext;
    public final boolean mIsEnabled;
    public final String mMsg;

    public DisabledPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsEnabled = true;
        this.mMsg = ApnSettings.MVNO_NONE;
        this.mContext = context;
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        boolean z = isEnabled() && this.mIsEnabled;
        TextView textView = (TextView) preferenceViewHolder.itemView.findViewById(R.id.title);
        if (textView != null) {
            textView.setEnabled(z);
        }
        TextView textView2 = (TextView) preferenceViewHolder.itemView.findViewById(R.id.summary);
        if (textView2 != null) {
            textView2.setEnabled(z);
        }
    }

    @Override // androidx.preference.Preference
    public final void onClick() {
        String str;
        if (this.mIsEnabled || (str = this.mMsg) == null || ApnSettings.MVNO_NONE.equals(str)) {
            return;
        }
        Toast.makeText(this.mContext, this.mMsg, 0).show();
    }
}
