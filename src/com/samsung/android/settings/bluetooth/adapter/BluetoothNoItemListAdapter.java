package com.samsung.android.settings.bluetooth.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BluetoothNoItemListAdapter extends BluetoothListAdapter {
    public ViewGroup mViewGroup;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class NoItemViewHolder extends RecyclerView.ViewHolder {
        public View mView;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return 1;
    }

    @Override // com.samsung.android.settings.bluetooth.adapter.BluetoothListAdapter
    public final String getLogTag() {
        return "BluetoothNoItemListAdapter";
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator.ItemAnimatorFinishedListener
    public final void onAnimationsFinished() {
        Log.d("BluetoothNoItemListAdapter", "onAnimationsFinished:");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        StringBuilder m =
                ListPopupWindow$$ExternalSyntheticOutline0.m(
                        i, "onBindViewHolder position = ", ", measureHeight = ");
        m.append(this.mViewGroup.getMeasuredHeight());
        Log.d("BluetoothNoItemListAdapter", m.toString());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        this.mViewGroup = viewGroup;
        View inflate =
                LayoutInflater.from(this.mContext)
                        .inflate(
                                isDialogType()
                                        ? R.layout.sec_bluetooth_available_no_item_dialog
                                        : R.layout.sec_bluetooth_available_no_item,
                                (ViewGroup) this.mParentView,
                                false);
        NoItemViewHolder noItemViewHolder = new NoItemViewHolder(inflate);
        noItemViewHolder.mView = inflate;
        return noItemViewHolder;
    }

    @Override // com.samsung.android.settings.bluetooth.adapter.BluetoothListAdapter
    public final void update() {}

    @Override // com.samsung.android.settings.bluetooth.adapter.BluetoothListAdapter
    public final void printAddLog(Preference preference) {}
}
