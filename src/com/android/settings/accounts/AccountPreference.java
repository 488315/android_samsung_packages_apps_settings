package com.android.settings.accounts;

import android.R;
import android.widget.ImageView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AccountPreference extends Preference {
    public ImageView mSyncStatusIcon;

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        ImageView imageView = (ImageView) preferenceViewHolder.findViewById(R.id.icon);
        this.mSyncStatusIcon = imageView;
        imageView.setImageResource(com.android.settings.R.drawable.ic_settings_sync);
        this.mSyncStatusIcon.setContentDescription(
                getContext().getString(com.android.settings.R.string.accessibility_sync_enabled));
    }
}
