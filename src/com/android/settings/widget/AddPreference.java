package com.android.settings.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settingslib.RestrictedPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AddPreference extends RestrictedPreference implements View.OnClickListener {
    public View mAddWidget;
    public View mWidgetFrame;

    public AddPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public int getAddWidgetResId() {
        return R.id.add_preference_widget;
    }

    @Override // com.android.settingslib.widget.TwoTargetPreference
    public final int getSecondTargetResId() {
        return R.layout.preference_widget_add;
    }

    @Override // com.android.settingslib.RestrictedPreference,
              // com.android.settingslib.widget.TwoTargetPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        this.mWidgetFrame = preferenceViewHolder.findViewById(android.R.id.widget_frame);
        View findViewById = preferenceViewHolder.findViewById(getAddWidgetResId());
        this.mAddWidget = findViewById;
        findViewById.setEnabled(true);
        this.mAddWidget.setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        view.getId();
        getAddWidgetResId();
    }

    @Override // com.android.settingslib.widget.TwoTargetPreference
    public final boolean shouldHideSecondTarget() {
        return true;
    }
}
