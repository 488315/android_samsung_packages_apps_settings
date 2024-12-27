package com.samsung.android.settings.bluetooth.adapter;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.bluetooth.Utils;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothAdapter;

import com.samsung.android.settings.bluetooth.BluetoothListController;
import com.samsung.android.settings.bluetooth.SecBluetoothDevicePreference;
import com.samsung.android.settingslib.bluetooth.detector.BluetoothRetryDetector;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BluetoothAvailableListAdapter extends BluetoothListAdapter {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AvailableViewHolder extends RecyclerView.ViewHolder {
        public ImageView mIcon;
        public TextView mSummary;
        public ImageView mTipsBtn;
        public TextView mTitle;
        public View mView;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.mPreferenceList.size();
    }

    @Override // com.samsung.android.settings.bluetooth.adapter.BluetoothListAdapter
    public final String getLogTag() {
        return "BluetoothAvailableListAdapter";
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator.ItemAnimatorFinishedListener
    public final void onAnimationsFinished() {
        Log.d(
                "BluetoothAvailableListAdapter",
                "BluetoothAvailableListAdapter: onAnimationsFinished");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (this.mPreferenceList.size() <= 0 || i < 0 || i > this.mPreferenceList.size()) {
            Log.d("BluetoothAvailableListAdapter", "onBindViewHolder: failed - invalid index");
            return;
        }
        AvailableViewHolder availableViewHolder = (AvailableViewHolder) viewHolder;
        final SecBluetoothDevicePreference secBluetoothDevicePreference =
                (SecBluetoothDevicePreference) this.mPreferenceList.get(i);
        CachedBluetoothDevice cachedBluetoothDevice = secBluetoothDevicePreference.mCachedDevice;
        String name = secBluetoothDevicePreference.getName();
        availableViewHolder.mView.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.bluetooth.adapter.BluetoothAvailableListAdapter$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        BluetoothAvailableListAdapter bluetoothAvailableListAdapter =
                                BluetoothAvailableListAdapter.this;
                        bluetoothAvailableListAdapter.mController.mListener.onItemClick(
                                secBluetoothDevicePreference);
                    }
                });
        StringBuilder sb = new StringBuilder("onBindViewHolder: ");
        sb.append(cachedBluetoothDevice.getNameForLog());
        sb.append("isBonded = ");
        sb.append(cachedBluetoothDevice.mBondState);
        sb.append(", groupId = ");
        Preference$$ExternalSyntheticOutline0.m(
                sb, cachedBluetoothDevice.mGroupId, "BluetoothAvailableListAdapter");
        availableViewHolder.mTitle.setText(Html.fromHtml(name));
        CachedBluetoothDevice deviceForGroupConnectionState =
                BluetoothUtils.getDeviceForGroupConnectionState(cachedBluetoothDevice);
        if (deviceForGroupConnectionState.getMaxConnectionState() > 0) {
            availableViewHolder.mTitle.setTextAppearance(
                    this.mContext, R.style.BluetoothDeviceConnectedHighlight);
        } else if (isDialogType()) {
            availableViewHolder.mTitle.setTextAppearance(
                    this.mContext, R.style.BluetoothDeviceDialogTitleTextStyle);
        } else {
            availableViewHolder.mTitle.setTextAppearance(
                    this.mContext, R.style.BluetoothDeviceTitleTextStyle);
        }
        boolean isBusyForList = deviceForGroupConnectionState.isBusyForList();
        availableViewHolder.mTitle.setEnabled(!isBusyForList);
        if (isBusyForList) {
            TextView textView = availableViewHolder.mTitle;
            textView.setTextColor(textView.getTextColors().withAlpha(94));
        } else {
            TextView textView2 = availableViewHolder.mTitle;
            textView2.setTextColor(textView2.getTextColors().withAlpha(255));
        }
        String connectionSummary = deviceForGroupConnectionState.getConnectionSummary();
        if (connectionSummary != null) {
            availableViewHolder.mSummary.setText(connectionSummary);
            availableViewHolder.mSummary.setVisibility(0);
        } else {
            availableViewHolder.mSummary.setVisibility(8);
        }
        Context context = this.mContext;
        boolean z = Utils.DEBUG;
        availableViewHolder.mIcon.setImageDrawable(
                BluetoothUtils.getHostOverlayIconDrawable(context, cachedBluetoothDevice));
        if (this.mController.mIsSupportTips) {
            BluetoothRetryDetector bluetoothRetryDetector = cachedBluetoothDevice.mBondingDetector;
            if (bluetoothRetryDetector == null
                    || bluetoothRetryDetector.mCount < bluetoothRetryDetector.mMaxCount) {
                ImageView imageView = availableViewHolder.mTipsBtn;
                if (imageView != null) {
                    imageView.setVisibility(8);
                    return;
                }
                return;
            }
            ImageView imageView2 = availableViewHolder.mTipsBtn;
            if (imageView2 != null) {
                imageView2.setVisibility(0);
                availableViewHolder.mTipsBtn.setTag(R.id.bluetooth_tips, cachedBluetoothDevice);
                availableViewHolder.mTipsBtn.setOnClickListener(secBluetoothDevicePreference);
                availableViewHolder.mTipsBtn.setFocusable(false);
                availableViewHolder.mTipsBtn.setFocusableInTouchMode(false);
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate =
                LayoutInflater.from(this.mContext)
                        .inflate(
                                R.layout.sec_preference_bt_icon,
                                (ViewGroup) this.mParentView,
                                false);
        AvailableViewHolder availableViewHolder = new AvailableViewHolder(inflate);
        availableViewHolder.mView = inflate;
        availableViewHolder.mIcon = (ImageView) inflate.findViewById(android.R.id.icon);
        availableViewHolder.mTitle = (TextView) inflate.findViewById(android.R.id.title);
        availableViewHolder.mSummary = (TextView) inflate.findViewById(android.R.id.summary);
        availableViewHolder.mTipsBtn = (ImageView) inflate.findViewById(R.id.tips_btn);
        return availableViewHolder;
    }

    @Override // com.samsung.android.settings.bluetooth.adapter.BluetoothListAdapter
    public final void printAddLog(Preference preference) {
        Log.d(
                "BluetoothAvailableListAdapter",
                "add: cached = "
                        + ((SecBluetoothDevicePreference) preference).mCachedDevice.getName()
                        + ", size = "
                        + this.mPreferenceList.size());
    }

    @Override // com.samsung.android.settings.bluetooth.adapter.BluetoothListAdapter
    public final void update() {
        Log.d(
                "BluetoothAvailableListAdapter",
                "update: itemCount = " + this.mPreferenceList.size());
        LocalBluetoothAdapter localBluetoothAdapter = this.mLocalAdapter;
        boolean isDiscovering = localBluetoothAdapter.mAdapter.isDiscovering();
        BluetoothListController bluetoothListController = this.mController;
        if (isDiscovering || localBluetoothAdapter.getBluetoothState() != 12) {
            bluetoothListController.mNoItemList.setVisibility(8);
            bluetoothListController.mNoItemListAdapter.notifyDataSetChanged();
        } else if (this.mPreferenceList.size() == 0) {
            bluetoothListController.mNoItemList.setVisibility(0);
            bluetoothListController.mNoItemListAdapter.notifyDataSetChanged();
        } else {
            bluetoothListController.mNoItemList.setVisibility(8);
            bluetoothListController.mNoItemListAdapter.notifyDataSetChanged();
        }
    }
}
