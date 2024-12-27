package com.samsung.android.settings.bluetooth;

import android.content.Context;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settingslib.bluetooth.LocalBluetoothAdapter;

import com.samsung.android.settings.bluetooth.adapter.BluetoothAvailableListAdapter;
import com.samsung.android.settings.bluetooth.adapter.BluetoothCastListAdapter;
import com.samsung.android.settings.bluetooth.adapter.BluetoothListAdapter;
import com.samsung.android.settings.bluetooth.adapter.BluetoothNoItemListAdapter;
import com.samsung.android.settings.bluetooth.adapter.BluetoothPairedListAdapter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BluetoothListController {
    public TextView mAvailableDeviceCategory;
    public LinearLayout mAvailableLayout;
    public RecyclerView mAvailableList;
    public BluetoothAvailableListAdapter mAvailableListAdapter;
    public View mBottomArea;
    public TextView mCastDeviceCategory;
    public LinearLayout mCastLayout;
    public RecyclerView mCastList;
    public BluetoothCastListAdapter mCastListAdapter;
    public ViewGroup mContainer;
    public Context mContext;
    public TextView mEmptyView;
    public Fragment mFragment;
    public TextView mGuideView;
    public boolean mIsSupportTips;
    public SecDeviceListPreferenceFragment.AnonymousClass2 mListener;
    public LocalBluetoothAdapter mLocalAdapter;
    public RecyclerView mNoItemList;
    public BluetoothNoItemListAdapter mNoItemListAdapter;
    public TextView mPairedDeviceCategory;
    public LinearLayout mPairedLayout;
    public RecyclerView mPairedList;
    public BluetoothPairedListAdapter mPairedListAdapter;
    public View mRounded_frame;
    public NestedScrollView mScrollView;

    static {
        Debug.semIsProductDev();
    }

    public final BluetoothListAdapter getSelectedAdapter(
            SecBluetoothDevicePreference secBluetoothDevicePreference) {
        return this.mCastListAdapter.mPreferenceList.contains(secBluetoothDevicePreference)
                ? this.mCastListAdapter
                : this.mPairedListAdapter.mPreferenceList.contains(secBluetoothDevicePreference)
                        ? this.mPairedListAdapter
                        : this.mAvailableListAdapter;
    }

    public final boolean isDialogType() {
        Context context = this.mContext;
        return (context instanceof BluetoothScanDialog)
                || (context instanceof SecDevicePickerDialog);
    }

    public final void notifyDataSetChanged() {
        BluetoothAvailableListAdapter bluetoothAvailableListAdapter = this.mAvailableListAdapter;
        if (bluetoothAvailableListAdapter != null) {
            bluetoothAvailableListAdapter.notifyDataSetChanged();
        }
        BluetoothCastListAdapter bluetoothCastListAdapter = this.mCastListAdapter;
        if (bluetoothCastListAdapter != null) {
            bluetoothCastListAdapter.notifyDataSetChanged();
        }
        BluetoothPairedListAdapter bluetoothPairedListAdapter = this.mPairedListAdapter;
        if (bluetoothPairedListAdapter != null) {
            bluetoothPairedListAdapter.notifyDataSetChanged();
        }
    }

    public final void setLayoutParams() {
        if (this.mContext == null) {
            Log.d("BluetoothListController", "setLayoutParams: mContext is null");
            return;
        }
        if (isDialogType()) {
            return;
        }
        int listHorizontalPadding = Utils.getListHorizontalPadding(this.mContext);
        this.mScrollView.setPadding(listHorizontalPadding, 0, listHorizontalPadding, 0);
        FrameLayout.LayoutParams layoutParams =
                (FrameLayout.LayoutParams) this.mRounded_frame.getLayoutParams();
        layoutParams.leftMargin = listHorizontalPadding;
        layoutParams.rightMargin = listHorizontalPadding;
        this.mRounded_frame.setLayoutParams(layoutParams);
    }

    public final void setRoundedCorner(View view) {
        if (isDialogType()) {
            return;
        }
        view.semSetRoundedCorners(15);
        view.semSetRoundedCornerColor(
                15, this.mContext.getResources().getColor(R.color.sec_widget_round_and_bgcolor));
    }

    public final void setVisibleCastGroup(int i) {
        if (isDialogType()) {
            this.mCastLayout.setVisibility(i);
        }
        this.mCastDeviceCategory.setVisibility(i);
        this.mCastList.setVisibility(i);
    }

    public final void setVisiblePairedGroup(int i) {
        if (isDialogType()) {
            this.mPairedLayout.setVisibility(i);
        }
        this.mPairedDeviceCategory.setVisibility(i);
        this.mPairedList.setVisibility(i);
    }

    public final void updateEmptyView(boolean z) {
        if (!z) {
            this.mEmptyView.setVisibility(8);
            if (isDialogType()) {
                this.mAvailableLayout.setVisibility(0);
            }
            this.mAvailableDeviceCategory.setVisibility(0);
            this.mAvailableList.setVisibility(0);
            return;
        }
        this.mEmptyView.setVisibility(8);
        if (isDialogType()) {
            this.mAvailableLayout.setVisibility(8);
        }
        this.mAvailableDeviceCategory.setVisibility(8);
        this.mAvailableList.setVisibility(8);
        this.mAvailableListAdapter.removeAll();
        setVisiblePairedGroup(8);
        this.mPairedListAdapter.removeAll();
        setVisibleCastGroup(8);
        this.mCastListAdapter.removeAll();
        this.mNoItemList.setVisibility(8);
        this.mNoItemListAdapter.notifyDataSetChanged();
    }
}
