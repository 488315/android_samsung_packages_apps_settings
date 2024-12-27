package com.google.android.setupdesign.items;

import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.sim.ChooseSimActivity;

import com.google.android.setupdesign.DividerItemDecoration;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ItemViewHolder extends RecyclerView.ViewHolder
        implements DividerItemDecoration.DividedViewHolder {
    public boolean isEnabled;
    public ChooseSimActivity.DisableableItem item;

    @Override // com.google.android.setupdesign.DividerItemDecoration.DividedViewHolder
    public final boolean isDividerAllowedAbove() {
        ChooseSimActivity.DisableableItem disableableItem = this.item;
        if (!(disableableItem instanceof ChooseSimActivity.DisableableItem)) {
            return this.isEnabled;
        }
        disableableItem.getClass();
        return true;
    }

    @Override // com.google.android.setupdesign.DividerItemDecoration.DividedViewHolder
    public final boolean isDividerAllowedBelow() {
        ChooseSimActivity.DisableableItem disableableItem = this.item;
        if (!(disableableItem instanceof ChooseSimActivity.DisableableItem)) {
            return this.isEnabled;
        }
        disableableItem.getClass();
        return true;
    }
}
