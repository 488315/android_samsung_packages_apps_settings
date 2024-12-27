package com.android.settings.notification.app;

import android.view.View;

import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settingslib.widget.TwoTargetPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RecentConversationPreference extends TwoTargetPreference {
    public View mClearView;

    public int getClearId() {
        return R.id.clear_button;
    }

    public View getClearView() {
        return this.mClearView;
    }

    @Override // com.android.settingslib.widget.TwoTargetPreference
    public final int getSecondTargetResId() {
        return R.layout.preference_widget_clear;
    }

    public boolean hasClearListener() {
        return false;
    }

    @Override // com.android.settingslib.widget.TwoTargetPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.findViewById(android.R.id.widget_frame).setVisibility(8);
        this.mClearView = preferenceViewHolder.findViewById(getClearId());
        throw null;
    }
}
