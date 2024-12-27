package com.samsung.android.settings.bluetooth;

import android.content.Context;
import android.content.res.Configuration;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceFragmentCompat;

import com.android.settings.R;
import com.android.settings.bluetooth.Utils;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

import com.samsung.android.settings.logging.SALogging;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BluetoothPairingBlockSettings extends PreferenceFragmentCompat
        implements BluetoothCallback {
    public static final /* synthetic */ int $r8$clinit = 0;
    public View layoutView;
    public BluetoothBlockListAdapter mBluetoothBlockListAdapter;
    public FragmentActivity mContext;
    public final AnonymousClass1 mDataSetObserver =
            new DataSetObserver() { // from class:
                                    // com.samsung.android.settings.bluetooth.BluetoothPairingBlockSettings.1
                @Override // android.database.DataSetObserver
                public final void onChanged() {
                    super.onChanged();
                    BluetoothPairingBlockSettings bluetoothPairingBlockSettings =
                            BluetoothPairingBlockSettings.this;
                    int i = BluetoothPairingBlockSettings.$r8$clinit;
                    bluetoothPairingBlockSettings.updateListView$2();
                }
            };
    public ListView mListView;
    public LocalBluetoothManager mLocalManager;
    public View mNoItemView;
    public View mRounded_frame;
    public String mScreenId;
    public NestedScrollView mScrollView;

    static {
        Debug.semIsProductDev();
    }

    public final void initLayout(View view) {
        this.mListView = (ListView) view.findViewById(R.id.item_list);
        this.mNoItemView = view.findViewById(R.id.layout_no_item);
        Context context = getContext();
        BluetoothBlockListAdapter bluetoothBlockListAdapter = new BluetoothBlockListAdapter();
        bluetoothBlockListAdapter.mBlockNumbersList = new ArrayList();
        bluetoothBlockListAdapter.mContext = context;
        bluetoothBlockListAdapter.mInflater = LayoutInflater.from(context);
        bluetoothBlockListAdapter.updateBlockDeviceList();
        this.mBluetoothBlockListAdapter = bluetoothBlockListAdapter;
        this.mListView.setAdapter((ListAdapter) bluetoothBlockListAdapter);
        this.mListView.setItemsCanFocus(true);
        updateListView$2();
        this.mBluetoothBlockListAdapter.registerDataSetObserver(this.mDataSetObserver);
    }

    public final void initRoundedCorners() {
        this.mNoItemView.semSetRoundedCorners(15);
        this.mNoItemView.semSetRoundedCornerColor(
                15, getContext().getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        this.mListView.semSetRoundedCorners(15);
        this.mListView.semSetRoundedCornerColor(
                15, getContext().getResources().getColor(R.color.sec_widget_round_and_bgcolor));
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        setLayoutParams();
        initLayout(this.layoutView);
        initRoundedCorners();
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentActivity activity = getActivity();
        this.mContext = activity;
        this.mLocalManager = LocalBluetoothManager.getInstance(activity, Utils.mOnInitCallback);
        this.mScreenId =
                getResources().getString(R.string.sec_bluetooth_pairing_block_request_title);
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate =
                layoutInflater.inflate(
                        R.layout.sec_preference_bluetooth_pairing_block_device, viewGroup, false);
        this.layoutView = inflate;
        NestedScrollView nestedScrollView =
                (NestedScrollView)
                        inflate.findViewById(R.id.bluetooth_pairing_block_nested_scroll_view);
        this.mScrollView = nestedScrollView;
        nestedScrollView.seslSetFillHorizontalPaddingEnabled(
                this.mContext.getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        View findViewById = this.layoutView.findViewById(R.id.rounded_frame);
        this.mRounded_frame = findViewById;
        findViewById.semSetRoundedCorners(15);
        findViewById.semSetRoundedCornerColor(
                15, this.mContext.getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        setLayoutParams();
        initLayout(this.layoutView);
        initRoundedCorners();
        return this.layoutView;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        BluetoothBlockListAdapter bluetoothBlockListAdapter = this.mBluetoothBlockListAdapter;
        if (bluetoothBlockListAdapter != null) {
            bluetoothBlockListAdapter.unregisterDataSetObserver(this.mDataSetObserver);
        }
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceBondStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i, "onDeviceBondStateChanged() :: bondState=", "BluetoothPairingBlockSettings");
        BluetoothBlockListAdapter bluetoothBlockListAdapter = this.mBluetoothBlockListAdapter;
        if (bluetoothBlockListAdapter == null || i != 12) {
            return;
        }
        bluetoothBlockListAdapter.updateBlockDeviceList();
    }

    @Override // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        menuItem.getItemId();
        return false;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        LocalBluetoothManager localBluetoothManager = this.mLocalManager;
        if (localBluetoothManager != null) {
            localBluetoothManager.mEventManager.unregisterCallback(this);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        LocalBluetoothManager localBluetoothManager = this.mLocalManager;
        if (localBluetoothManager == null) {
            Log.e("BluetoothPairingBlockSettings", "onResume() :: mLocalManager is null");
            return;
        }
        localBluetoothManager.mEventManager.registerCallback(this);
        BluetoothBlockListAdapter bluetoothBlockListAdapter = this.mBluetoothBlockListAdapter;
        if (bluetoothBlockListAdapter != null) {
            bluetoothBlockListAdapter.updateBlockDeviceList();
        }
        SALogging.insertSALog(this.mScreenId);
    }

    public final void setLayoutParams() {
        FragmentActivity fragmentActivity = this.mContext;
        if (fragmentActivity == null) {
            Log.d("BluetoothPairingBlockSettings", "setLayoutParams: mContext is null");
            return;
        }
        int listHorizontalPadding =
                com.android.settings.Utils.getListHorizontalPadding(fragmentActivity);
        this.mScrollView.setPadding(listHorizontalPadding, 0, listHorizontalPadding, 0);
        FrameLayout.LayoutParams layoutParams =
                (FrameLayout.LayoutParams) this.mRounded_frame.getLayoutParams();
        layoutParams.leftMargin = listHorizontalPadding;
        layoutParams.rightMargin = listHorizontalPadding;
        this.mRounded_frame.setLayoutParams(layoutParams);
    }

    public final void updateListView$2() {
        int count = this.mBluetoothBlockListAdapter.getCount();
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                count, "updateListView() rejectItemSize : ", "BluetoothPairingBlockSettings");
        if (count > 0) {
            this.mListView.setVisibility(0);
            this.mNoItemView.setVisibility(8);
        } else {
            this.mListView.setVisibility(8);
            this.mNoItemView.setVisibility(0);
        }
    }

    @Override // androidx.preference.PreferenceFragmentCompat
    public final void onCreatePreferences(Bundle bundle, String str) {}
}
