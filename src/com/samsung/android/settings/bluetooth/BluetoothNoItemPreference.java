package com.samsung.android.settings.bluetooth;

import android.view.ViewGroup;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BluetoothNoItemPreference extends Preference {
    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.itemView.setLayoutParams(new ViewGroup.LayoutParams(-1, 0));
        preferenceViewHolder.mDividerAllowedBelow = false;
    }
}
