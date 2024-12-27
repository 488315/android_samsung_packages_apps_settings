package com.android.settingslib.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AppPreference extends Preference {
    public int mProgress;
    public boolean mProgressVisible;

    public AppPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        setLayoutResource(R.layout.sec_preference_app);
    }

    @Override // androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder
                .findViewById(R.id.summary_container)
                .setVisibility(TextUtils.isEmpty(getSummary()) ? 8 : 0);
        ProgressBar progressBar =
                (ProgressBar) preferenceViewHolder.findViewById(android.R.id.progress);
        if (!this.mProgressVisible) {
            progressBar.setVisibility(8);
        } else {
            progressBar.setProgress(this.mProgress);
            progressBar.setVisibility(0);
        }
    }

    public AppPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setLayoutResource(R.layout.sec_preference_app);
    }

    public AppPreference(Context context) {
        super(context);
        setLayoutResource(R.layout.sec_preference_app);
    }

    public AppPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setLayoutResource(R.layout.sec_preference_app);
    }
}
