package com.samsung.android.settings.wifi;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SecWifiProgressButtonPreference extends SecWifiButtonPreference {
    public boolean mEnabled;

    public SecWifiProgressButtonPreference(Context context) {
        this(context, null);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        initPreference(preferenceViewHolder);
        boolean z = this.mEnabled;
        LinearLayout linearLayout = this.mButton;
        if (linearLayout == null || this.mTitleView == null) {
            return;
        }
        linearLayout.setAlpha(z ? 1.0f : 0.4f);
        this.mTitleView.setAlpha(z ? 1.0f : 0.4f);
    }

    @Override // androidx.preference.Preference
    public final void performClick() {
        LinearLayout linearLayout = this.mButton;
        if (linearLayout != null) {
            linearLayout.callOnClick();
        }
    }

    @Override // androidx.preference.Preference
    public final void setEnabled(boolean z) {
        super.setEnabled(z);
        this.mEnabled = z;
        if (z) {
            this.mProgressVisible = false;
        }
        notifyChanged();
    }

    public SecWifiProgressButtonPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SecWifiProgressButtonPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SecWifiProgressButtonPreference(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        setLayoutResource(R.layout.sec_wifi_progress_button_preference);
        setSelectable(false);
    }
}
