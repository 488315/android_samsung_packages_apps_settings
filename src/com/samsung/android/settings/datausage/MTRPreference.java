package com.samsung.android.settings.datausage;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.preference.PreferenceViewHolder;
import androidx.preference.SecPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class MTRPreference extends SecPreference {
    public View.OnClickListener mListener;

    public MTRPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, R.attr.preferenceStyle);
        this.mListener = null;
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View findViewById = preferenceViewHolder.findViewById(R.id.widget_frame);
        findViewById.setVisibility(0);
        findViewById.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.datausage.MTRPreference.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        Log.d("MTRPreference", "click");
                        View.OnClickListener onClickListener = MTRPreference.this.mListener;
                        if (onClickListener != null) {
                            onClickListener.onClick(view);
                        }
                    }
                });
    }
}
