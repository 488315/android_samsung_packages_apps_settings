package com.samsung.android.settings.bluetooth.adapter;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.picker.widget.SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.bluetooth.DevicePickerActivity;
import com.android.settings.bluetooth.Utils;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;

import com.samsung.android.settings.bluetooth.BluetoothListController;
import com.samsung.android.settings.bluetooth.SecBluetoothDevicePreference;
import com.samsung.android.settingslib.bluetooth.detector.BluetoothRetryDetector;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BluetoothPairedListAdapter extends BluetoothListAdapter {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PairedViewHolder extends RecyclerView.ViewHolder {
        public View mBadgeView;
        public ImageView mDetails;
        public ImageView mIcon;
        public RelativeLayout mRelativeLayout;
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
        return "BluetoothPairedListAdapter";
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator.ItemAnimatorFinishedListener
    public final void onAnimationsFinished() {
        Log.d("BluetoothPairedListAdapter", "BluetoothPairedListAdapter: onAnimationsFinished");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        RelativeLayout relativeLayout;
        if (i < 0 || i > this.mPreferenceList.size()) {
            Log.d("BluetoothPairedListAdapter", "onBindViewHolder failed - invalid index");
            return;
        }
        PairedViewHolder pairedViewHolder = (PairedViewHolder) viewHolder;
        final SecBluetoothDevicePreference secBluetoothDevicePreference =
                (SecBluetoothDevicePreference) this.mPreferenceList.get(i);
        CachedBluetoothDevice cachedBluetoothDevice = secBluetoothDevicePreference.mCachedDevice;
        String name = secBluetoothDevicePreference.getName();
        pairedViewHolder.mView.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.bluetooth.adapter.BluetoothPairedListAdapter$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        BluetoothPairedListAdapter bluetoothPairedListAdapter =
                                BluetoothPairedListAdapter.this;
                        bluetoothPairedListAdapter.mController.mListener.onItemClick(
                                secBluetoothDevicePreference);
                    }
                });
        StringBuilder sb = new StringBuilder("onBindViewHolder: ");
        sb.append(cachedBluetoothDevice.getNameForLog());
        sb.append("isBonded = ");
        sb.append(cachedBluetoothDevice.mBondState);
        sb.append(", groupId = ");
        Preference$$ExternalSyntheticOutline0.m(
                sb, cachedBluetoothDevice.mGroupId, "BluetoothPairedListAdapter");
        pairedViewHolder.mTitle.setText(Html.fromHtml(name));
        CachedBluetoothDevice deviceForGroupConnectionState =
                BluetoothUtils.getDeviceForGroupConnectionState(cachedBluetoothDevice);
        if (deviceForGroupConnectionState.getMaxConnectionState() > 0) {
            pairedViewHolder.mTitle.setTextAppearance(
                    this.mContext, R.style.BluetoothDeviceConnectedHighlight);
        } else if (isDialogType()) {
            pairedViewHolder.mTitle.setTextAppearance(
                    this.mContext, R.style.BluetoothDeviceDialogTitleTextStyle);
        } else {
            pairedViewHolder.mTitle.setTextAppearance(
                    this.mContext, R.style.BluetoothDeviceTitleTextStyle);
        }
        boolean isBusyForList = deviceForGroupConnectionState.isBusyForList();
        pairedViewHolder.mTitle.setEnabled(!isBusyForList);
        if (isBusyForList) {
            TextView textView = pairedViewHolder.mTitle;
            textView.setTextColor(textView.getTextColors().withAlpha(94));
        } else {
            TextView textView2 = pairedViewHolder.mTitle;
            textView2.setTextColor(textView2.getTextColors().withAlpha(255));
        }
        String connectionSummary = deviceForGroupConnectionState.getConnectionSummary();
        if (connectionSummary != null) {
            pairedViewHolder.mSummary.setText(connectionSummary);
            pairedViewHolder.mSummary.setVisibility(0);
        } else {
            pairedViewHolder.mSummary.setVisibility(8);
        }
        Context context = this.mContext;
        boolean z = Utils.DEBUG;
        pairedViewHolder.mIcon.setImageDrawable(
                BluetoothUtils.getHostOverlayIconDrawable(context, cachedBluetoothDevice));
        if ((cachedBluetoothDevice.mBondState == 12 || cachedBluetoothDevice.mIsRestored)
                && !(this.mContext instanceof DevicePickerActivity)
                && !isDialogType()) {
            RelativeLayout relativeLayout2 = pairedViewHolder.mRelativeLayout;
            if (relativeLayout2 != null) {
                relativeLayout2.setVisibility(0);
            }
            ImageView imageView = pairedViewHolder.mDetails;
            if (imageView != null) {
                imageView.setOnClickListener(secBluetoothDevicePreference);
                pairedViewHolder.mDetails.setTag(R.id.bluetooth_profile, cachedBluetoothDevice);
                String m =
                        SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0
                                .m(
                                        this.mContext,
                                        R.string.bluetooth_device_details,
                                        new StringBuilder(", "));
                pairedViewHolder.mDetails.setContentDescription(((Object) Html.fromHtml(name)) + m);
                pairedViewHolder.mDetails.setFocusable(false);
                pairedViewHolder.mDetails.setFocusableInTouchMode(false);
            }
        } else if (pairedViewHolder.mDetails != null
                && (relativeLayout = pairedViewHolder.mRelativeLayout) != null) {
            relativeLayout.setVisibility(8);
        }
        if (this.mController.mIsSupportTips) {
            BluetoothRetryDetector bluetoothRetryDetector = cachedBluetoothDevice.mBondingDetector;
            if (bluetoothRetryDetector == null
                    || bluetoothRetryDetector.mCount < bluetoothRetryDetector.mMaxCount) {
                ImageView imageView2 = pairedViewHolder.mTipsBtn;
                if (imageView2 != null) {
                    imageView2.setVisibility(8);
                    return;
                }
                return;
            }
            ImageView imageView3 = pairedViewHolder.mTipsBtn;
            if (imageView3 != null) {
                imageView3.setVisibility(0);
                pairedViewHolder.mTipsBtn.setTag(R.id.bluetooth_tips, cachedBluetoothDevice);
                pairedViewHolder.mTipsBtn.setOnClickListener(secBluetoothDevicePreference);
                pairedViewHolder.mTipsBtn.setFocusable(false);
                pairedViewHolder.mTipsBtn.setFocusableInTouchMode(false);
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate =
                LayoutInflater.from(this.mContext)
                        .inflate(
                                R.layout.sec_preference_bt_icon_paired,
                                (ViewGroup) this.mParentView,
                                false);
        PairedViewHolder pairedViewHolder = new PairedViewHolder(inflate);
        pairedViewHolder.mView = inflate;
        pairedViewHolder.mIcon = (ImageView) inflate.findViewById(android.R.id.icon);
        pairedViewHolder.mTitle = (TextView) inflate.findViewById(android.R.id.title);
        pairedViewHolder.mSummary = (TextView) inflate.findViewById(android.R.id.summary);
        pairedViewHolder.mDetails = (ImageView) inflate.findViewById(R.id.deviceDetails);
        pairedViewHolder.mTipsBtn = (ImageView) inflate.findViewById(R.id.tips_btn);
        pairedViewHolder.mRelativeLayout =
                (RelativeLayout) inflate.findViewById(R.id.layout_details);
        pairedViewHolder.mBadgeView = inflate.findViewById(R.id.view_badge);
        return pairedViewHolder;
    }

    @Override // com.samsung.android.settings.bluetooth.adapter.BluetoothListAdapter
    public final void printAddLog(Preference preference) {
        Log.d(
                "BluetoothPairedListAdapter",
                "add: cached = "
                        + ((SecBluetoothDevicePreference) preference).mCachedDevice.getName()
                        + ", size = "
                        + this.mPreferenceList.size());
    }

    @Override // com.samsung.android.settings.bluetooth.adapter.BluetoothListAdapter
    public final void update() {
        Log.d(
                "BluetoothPairedListAdapter",
                "update: mPreferenceList size = " + this.mPreferenceList.size());
        int size = this.mPreferenceList.size();
        BluetoothListController bluetoothListController = this.mController;
        if (size == 0) {
            bluetoothListController.setVisiblePairedGroup(8);
        } else {
            bluetoothListController.setVisiblePairedGroup(0);
        }
    }
}
