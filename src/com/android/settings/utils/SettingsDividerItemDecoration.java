package com.android.settings.utils;

import androidx.preference.PreferenceViewHolder;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.setupdesign.DividerItemDecoration;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SettingsDividerItemDecoration extends DividerItemDecoration {
    @Override // com.google.android.setupdesign.DividerItemDecoration
    public final boolean isDividerAllowedAbove(RecyclerView.ViewHolder viewHolder) {
        return viewHolder instanceof PreferenceViewHolder
                ? ((PreferenceViewHolder) viewHolder).mDividerAllowedAbove
                : super.isDividerAllowedAbove(viewHolder);
    }

    @Override // com.google.android.setupdesign.DividerItemDecoration
    public final boolean isDividerAllowedBelow(RecyclerView.ViewHolder viewHolder) {
        return viewHolder instanceof PreferenceViewHolder
                ? ((PreferenceViewHolder) viewHolder).mDividerAllowedBelow
                : super.isDividerAllowedBelow(viewHolder);
    }
}
