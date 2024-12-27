package com.samsung.android.settings.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import androidx.preference.PreferenceViewHolder;
import androidx.preference.SecPreference;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SecProgressPreference extends SecPreference {
    public SecProgressPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setLayoutResource(R.layout.sec_widget_preference_progress);
        setWidgetLayoutResource(R.layout.sec_widget_progress_bar);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        ((ProgressBar) preferenceViewHolder.itemView.findViewById(android.R.id.progress))
                .setProgress(0);
    }
}
