package com.android.settingslib.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AppHeaderPreference extends Preference {
    public boolean mIsInstantApp;

    public AppHeaderPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init$14();
    }

    public final void init$14() {
        setLayoutResource(R.layout.app_header_preference);
        setSelectable(false);
        if (this.mIsInstantApp) {
            this.mIsInstantApp = false;
            notifyChanged();
        }
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        TextView textView = (TextView) preferenceViewHolder.findViewById(R.id.install_type);
        if (textView != null) {
            if (this.mIsInstantApp) {
                textView.setVisibility(0);
            } else {
                textView.setVisibility(8);
            }
        }
        TextView textView2 = (TextView) preferenceViewHolder.findViewById(R.id.second_summary);
        if (textView2 != null) {
            if (TextUtils.isEmpty(null)) {
                textView2.setVisibility(8);
            } else {
                textView2.setText((CharSequence) null);
                textView2.setVisibility(0);
            }
        }
    }

    public AppHeaderPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init$14();
    }

    public AppHeaderPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init$14();
    }
}
