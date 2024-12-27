package com.samsung.android.settings.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothDump;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.view.SeslContentViewInsetsCallback$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.RestrictedSettingsFragment;
import com.android.settings.activityembedding.ActivityEmbeddingUtils;
import com.android.settings.bluetooth.Utils;
import com.android.settings.homepage.SettingsHomepageActivity;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.BluetoothDeviceFilter;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.BluetoothUtils$$ExternalSyntheticLambda6;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.CachedBluetoothDeviceManager;
import com.android.settingslib.bluetooth.LocalBluetoothAdapter;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfile;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.settingslib.bluetooth.PanProfile;

import com.google.android.material.appbar.AppBarLayout;
import com.samsung.android.bluetooth.SemBluetoothAudioCast;
import com.samsung.android.bluetooth.SemBluetoothCastAdapter;
import com.samsung.android.bluetooth.SemBluetoothCastDevice;
import com.samsung.android.knox.ex.peripheral.PeripheralConstants;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.bluetooth.adapter.BluetoothAvailableListAdapter;
import com.samsung.android.settings.bluetooth.adapter.BluetoothCastListAdapter;
import com.samsung.android.settings.bluetooth.adapter.BluetoothListAdapter;
import com.samsung.android.settings.bluetooth.adapter.BluetoothNoItemListAdapter;
import com.samsung.android.settings.bluetooth.adapter.BluetoothPairedListAdapter;
import com.samsung.android.settings.bluetooth.bluetoothcast.BluetoothCastDevicePreference;
import com.samsung.android.settings.core.SecSettingsBaseActivity;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.wifi.WifiTipsUtils;
import com.samsung.android.settingslib.bluetooth.SemBluetoothCallback;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.AudioCastProfile;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastCallback;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastEventManager;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.CachedBluetoothCastDevice;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.CachedBluetoothCastDevice.ToastRunnable;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.LocalBluetoothCastAdapter;
import com.sec.ims.volte2.data.VolteConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.WeakHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SecDeviceListPreferenceFragment extends RestrictedSettingsFragment
        implements BluetoothCallback, SemBluetoothCallback, BluetoothCastCallback {
    public static final boolean DEBUG = Debug.semIsProductDev();
    public final WeakHashMap mCastDevicePreferenceMap;
    public final WeakHashMap mDevicePreferenceMap;
    public BluetoothDeviceFilter.Filter[] mFilter;
    public boolean mIsPickerDialog;
    public BluetoothListController mListController;
    public final AnonymousClass2 mListEventListener;
    public View mListView;
    public LocalBluetoothAdapter mLocalAdapter;
    public LocalBluetoothCastAdapter mLocalCastAdapter;
    public LocalBluetoothManager mLocalManager;
    public String mScanFinishAction;
    public long mScanStartTime;
    public BluetoothListAdapter mSelectedAdapter;
    public BluetoothDevice mSelectedDevice;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.bluetooth.SecDeviceListPreferenceFragment$2, reason: invalid class name */
    public final class AnonymousClass2 {
        public AnonymousClass2() {}

        public final void onItemClick(SecBluetoothDevicePreference secBluetoothDevicePreference) {
            CachedBluetoothDevice cachedBluetoothDevice =
                    secBluetoothDevicePreference.mCachedDevice;
            StringBuilder sb = new StringBuilder("onDevicePreferenceClick: ");
            sb.append(cachedBluetoothDevice.getNameForLog());
            sb.append(", mIsPickerDialog = ");
            SecDeviceListPreferenceFragment secDeviceListPreferenceFragment =
                    SecDeviceListPreferenceFragment.this;
            SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(
                    sb,
                    secDeviceListPreferenceFragment.mIsPickerDialog,
                    "DeviceListPreferenceFragment");
            secDeviceListPreferenceFragment.mLocalAdapter.stopScanning();
            secDeviceListPreferenceFragment.mSelectedDevice = cachedBluetoothDevice.mDevice;
            if (!secDeviceListPreferenceFragment.mIsPickerDialog) {
                secDeviceListPreferenceFragment.onDevicePreferenceClick(
                        secBluetoothDevicePreference);
                return;
            }
            Log.d("DeviceListPreferenceFragment", "inside onDevicePreferenceClickForHeadset");
            CachedBluetoothDevice cachedBluetoothDevice2 =
                    secBluetoothDevicePreference.mCachedDevice;
            if (cachedBluetoothDevice2.mBondState != 11) {
                cachedBluetoothDevice2.connect$1();
            }
        }

        public final void putDevicePreferenceMap(Preference preference) {
            boolean z = preference instanceof SecBluetoothDevicePreference;
            SecDeviceListPreferenceFragment secDeviceListPreferenceFragment =
                    SecDeviceListPreferenceFragment.this;
            if (z) {
                SecBluetoothDevicePreference secBluetoothDevicePreference =
                        (SecBluetoothDevicePreference) preference;
                Log.i(
                        "DeviceListPreferenceFragment",
                        "putDevicePreferenceMap: "
                                + secBluetoothDevicePreference.mCachedDevice.getNameForLog());
                secDeviceListPreferenceFragment.mDevicePreferenceMap.put(
                        secBluetoothDevicePreference.mCachedDevice, secBluetoothDevicePreference);
                return;
            }
            if (preference instanceof BluetoothCastDevicePreference) {
                BluetoothCastDevicePreference bluetoothCastDevicePreference =
                        (BluetoothCastDevicePreference) preference;
                Log.i(
                        "DeviceListPreferenceFragment",
                        "putCastDevicePreferenceMap: "
                                + bluetoothCastDevicePreference.mCachedCastDevice.getName());
                secDeviceListPreferenceFragment.mCastDevicePreferenceMap.put(
                        bluetoothCastDevicePreference.mCachedCastDevice,
                        bluetoothCastDevicePreference);
            }
        }
    }

    public SecDeviceListPreferenceFragment(String str) {
        super(str);
        this.mIsPickerDialog = false;
        this.mDevicePreferenceMap = new WeakHashMap();
        this.mListController = null;
        this.mScanStartTime = 0L;
        this.mScanFinishAction = "Callback";
        this.mCastDevicePreferenceMap = new WeakHashMap();
        this.mListEventListener = new AnonymousClass2();
        setFilter(0);
    }

    public final void addCachedDevices$1() {
        Collection cachedDevicesCopy =
                this.mLocalManager.mCachedDeviceManager.getCachedDevicesCopy();
        this.mSelectedAdapter.setNeedVi(false);
        Iterator it = ((ArrayList) cachedDevicesCopy).iterator();
        while (it.hasNext()) {
            onDeviceAdded((CachedBluetoothDevice) it.next());
        }
        this.mSelectedAdapter.setNeedVi(true);
        this.mSelectedAdapter.update();
        Collections.sort(this.mSelectedAdapter.mPreferenceList);
        this.mSelectedAdapter.notifyDataSetChanged();
    }

    public abstract void addPreferencesForActivity();

    public final void createDevicePreference(
            CachedBluetoothDevice cachedBluetoothDevice,
            BluetoothListAdapter bluetoothListAdapter) {
        Log.d("DeviceListPreferenceFragment", "createDevicePreference: ");
        SecBluetoothDevicePreference secBluetoothDevicePreference =
                new SecBluetoothDevicePreference(
                        getActivity(), cachedBluetoothDevice, this.mListController);
        if (bluetoothListAdapter == null) {
            Log.w(
                    "DeviceListPreferenceFragment",
                    "Trying to create a device preference before the list group/category exists!");
            return;
        }
        if (cachedBluetoothDevice.mBondState != 12
                || !BluetoothUtils.isExclusivelyManagedBluetoothDevice(
                        getActivity(), cachedBluetoothDevice.mDevice)) {
            bluetoothListAdapter.add(secBluetoothDevicePreference);
            initDevicePreference(secBluetoothDevicePreference);
        } else {
            Log.w(
                    "DeviceListPreferenceFragment",
                    "Trying to create preference for a exclusively managed device "
                            + cachedBluetoothDevice.mDevice.getAddress());
        }
    }

    public final boolean isSupportBluetoothCast() {
        return !this.mIsPickerDialog && SemBluetoothCastAdapter.isBluetoothCastSupported();
    }

    @Override // com.android.settings.RestrictedSettingsFragment,
              // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        addPreferencesForActivity();
        if (((AppBarLayout) getActivity().getWindow().getDecorView().findViewById(R.id.app_bar))
                == null) {
            Log.d("DeviceListPreferenceFragment", "onActivityCreated(): AppBarLayout is null.");
            return;
        }
        if (getActivity() instanceof SecSettingsBaseActivity) {
            SecSettingsBaseActivity secSettingsBaseActivity =
                    (SecSettingsBaseActivity) getActivity();
            if (!secSettingsBaseActivity.mIsEmbeddingActivityEnabled
                    || (secSettingsBaseActivity instanceof SettingsHomepageActivity)) {
                return;
            }
            ActivityEmbeddingUtils.isAlreadyEmbedded(secSettingsBaseActivity);
        }
    }

    @Override // com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastCallback
    public void onCastDeviceAdded(CachedBluetoothCastDevice cachedBluetoothCastDevice) {
        Log.d("DeviceListPreferenceFragment", "onCastDeviceAdded");
        if (this.mCastDevicePreferenceMap.get(cachedBluetoothCastDevice) != null) {
            return;
        }
        Log.d(
                "DeviceListPreferenceFragment",
                "onCastDeviceAdded >> " + cachedBluetoothCastDevice.getName());
        Log.d("DeviceListPreferenceFragment", "createCastDevicePreference: ");
        this.mListController.mCastListAdapter.add(
                new BluetoothCastDevicePreference(
                        getActivity(), cachedBluetoothCastDevice, this.mListController));
        Collections.sort(this.mListController.mCastListAdapter.mPreferenceList);
        this.mListController.mCastListAdapter.setNeedUpdatePreference();
    }

    public void onCastDevicePreferenceClick(
            BluetoothCastDevicePreference bluetoothCastDevicePreference) {
        String addressForLog =
                bluetoothCastDevicePreference.mCachedCastDevice.mCastDevice.getAddressForLog();
        Log.d(bluetoothCastDevicePreference.TAG, "onClicked: " + addressForLog);
        BluetoothDump.BtLog(bluetoothCastDevicePreference.TAG + " -- onClicked: " + addressForLog);
        CachedBluetoothCastDevice cachedBluetoothCastDevice =
                bluetoothCastDevicePreference.mCachedCastDevice;
        Iterator it = cachedBluetoothCastDevice.mCastProfiles.iterator();
        while (it.hasNext()) {
            if (cachedBluetoothCastDevice.getCastProfileConnectionState(
                            (AudioCastProfile) it.next())
                    == 2) {
                CachedBluetoothCastDevice cachedBluetoothCastDevice2 =
                        bluetoothCastDevicePreference.mCachedCastDevice;
                Iterator it2 = cachedBluetoothCastDevice2.mCastProfiles.iterator();
                while (it2.hasNext()) {
                    AudioCastProfile audioCastProfile = (AudioCastProfile) it2.next();
                    SemBluetoothCastDevice semBluetoothCastDevice =
                            cachedBluetoothCastDevice2.mCastDevice;
                    Log.d(audioCastProfile.TAG, "disconnect");
                    SemBluetoothAudioCast semBluetoothAudioCast = audioCastProfile.mService;
                    if (semBluetoothAudioCast != null) {
                        semBluetoothAudioCast.disconnect(semBluetoothCastDevice);
                    }
                }
                return;
            }
        }
        CachedBluetoothCastDevice cachedBluetoothCastDevice3 =
                bluetoothCastDevicePreference.mCachedCastDevice;
        if (Settings.System.getInt(
                        cachedBluetoothCastDevice3.mContext.getContentResolver(),
                        "dexonpc_connection_state",
                        0)
                == 3) {
            Log.d(
                    "CachedBluetoothCastDevice",
                    "Dex is enabled, so connect request will be rejected");
            cachedBluetoothCastDevice3.toastHandler.post(
                    cachedBluetoothCastDevice3
                    .new ToastRunnable(
                            cachedBluetoothCastDevice3.mContext.getString(
                                    R.string.bluetoothcast_warn_dlg_msg_dex)));
            cachedBluetoothCastDevice3.dispatchAttributesChanged();
            return;
        }
        Iterator it3 = cachedBluetoothCastDevice3.mCastProfiles.iterator();
        while (it3.hasNext()) {
            AudioCastProfile audioCastProfile2 = (AudioCastProfile) it3.next();
            StringBuilder sb = new StringBuilder();
            sb.append(String.valueOf(cachedBluetoothCastDevice3.mCastProfiles.size()));
            sb.append("/");
            sb.append(String.valueOf(audioCastProfile2 == null));
            Log.d("CachedBluetoothCastDevice", sb.toString());
            if (audioCastProfile2 != null) {
                SemBluetoothCastDevice semBluetoothCastDevice2 =
                        cachedBluetoothCastDevice3.mCastDevice;
                Log.d(audioCastProfile2.TAG, "connect");
                SemBluetoothAudioCast semBluetoothAudioCast2 = audioCastProfile2.mService;
                if (semBluetoothAudioCast2 != null) {
                    semBluetoothAudioCast2.connect(semBluetoothCastDevice2);
                }
            }
        }
    }

    @Override // com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastCallback
    public void onCastDeviceRemoved(CachedBluetoothCastDevice cachedBluetoothCastDevice) {
        if (DEBUG) {
            Log.d(
                    "DeviceListPreferenceFragment",
                    "onCastDeviceRemoved() name - " + cachedBluetoothCastDevice.getName());
        }
        BluetoothCastDevicePreference bluetoothCastDevicePreference =
                (BluetoothCastDevicePreference)
                        this.mCastDevicePreferenceMap.remove(cachedBluetoothCastDevice);
        if (bluetoothCastDevicePreference == null) {
            bluetoothCastDevicePreference =
                    new BluetoothCastDevicePreference(getActivity(), cachedBluetoothCastDevice);
        }
        this.mListController.mCastListAdapter.delete(bluetoothCastDevicePreference);
    }

    @Override // com.android.settings.RestrictedSettingsFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        LocalBluetoothManager localBluetoothManager =
                LocalBluetoothManager.getInstance(getActivity(), Utils.mOnInitCallback);
        this.mLocalManager = localBluetoothManager;
        if (localBluetoothManager == null) {
            Log.e("DeviceListPreferenceFragment", "Bluetooth is not supported on this device");
            return;
        }
        Log.d("DeviceListPreferenceFragment", "onCreate");
        this.mLocalAdapter = this.mLocalManager.mLocalAdapter;
        BluetoothAdapter.getDefaultAdapter();
        if (SemBluetoothCastAdapter.isBluetoothCastSupported()) {
            this.mLocalCastAdapter = this.mLocalManager.mLocalCastAdapter;
        }
        this.mIsPickerDialog = getActivity() instanceof SecDevicePickerDialog;
        FragmentActivity activity = getActivity();
        LocalBluetoothAdapter localBluetoothAdapter = this.mLocalAdapter;
        LocalBluetoothManager localBluetoothManager2 = this.mLocalManager;
        BluetoothListController bluetoothListController = new BluetoothListController();
        bluetoothListController.mContext = activity;
        bluetoothListController.mLocalAdapter = localBluetoothAdapter;
        localBluetoothManager2.mCachedDeviceManager.getCachedDevicesCopy();
        bluetoothListController.mIsSupportTips = WifiTipsUtils.isSupportWifiTips(activity);
        this.mListController = bluetoothListController;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        super.onCreateView(layoutInflater, viewGroup, bundle);
        Log.d("DeviceListPreferenceFragment", "onCreateView");
        BluetoothListController bluetoothListController = this.mListController;
        bluetoothListController.mContainer = viewGroup;
        View inflate =
                layoutInflater.inflate(
                        bluetoothListController.isDialogType()
                                ? R.layout.sec_preference_list_fragment_bluetooth2_dialog
                                : R.layout.sec_preference_list_fragment_bluetooth2,
                        viewGroup,
                        false);
        NestedScrollView nestedScrollView =
                (NestedScrollView) inflate.findViewById(R.id.bluetooth_settings_nested_scroll_view);
        bluetoothListController.mScrollView = nestedScrollView;
        nestedScrollView.seslSetFillHorizontalPaddingEnabled(
                bluetoothListController
                        .mContext
                        .getResources()
                        .getColor(R.color.sec_widget_round_and_bgcolor));
        if (!bluetoothListController.isDialogType()) {
            View findViewById = inflate.findViewById(R.id.rounded_frame);
            bluetoothListController.mRounded_frame = findViewById;
            bluetoothListController.setRoundedCorner(findViewById);
        }
        bluetoothListController.mGuideView = (TextView) inflate.findViewById(android.R.id.title);
        bluetoothListController.mEmptyView = (TextView) inflate.findViewById(R.id.empty);
        bluetoothListController.setLayoutParams();
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.mSupportsChangeAnimations = false;
        bluetoothListController.mPairedLayout =
                (LinearLayout) inflate.findViewById(R.id.paired_layout);
        bluetoothListController.mPairedDeviceCategory =
                (TextView) inflate.findViewById(R.id.paired_device_category);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.paired_list);
        bluetoothListController.mPairedList = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(1));
        bluetoothListController.mPairedList.setItemAnimator(defaultItemAnimator);
        if (bluetoothListController.mPairedListAdapter == null) {
            bluetoothListController.mPairedListAdapter =
                    new BluetoothPairedListAdapter(
                            bluetoothListController.mContext,
                            bluetoothListController.mPairedList,
                            bluetoothListController,
                            bluetoothListController.mLocalAdapter);
        }
        bluetoothListController.mPairedList.setAdapter(bluetoothListController.mPairedListAdapter);
        bluetoothListController.setRoundedCorner(bluetoothListController.mPairedList);
        DefaultItemAnimator defaultItemAnimator2 = new DefaultItemAnimator();
        defaultItemAnimator2.mSupportsChangeAnimations = false;
        bluetoothListController.mAvailableLayout =
                (LinearLayout) inflate.findViewById(R.id.available_layout);
        bluetoothListController.mAvailableDeviceCategory =
                (TextView) inflate.findViewById(R.id.available_device_category);
        RecyclerView recyclerView2 = (RecyclerView) inflate.findViewById(R.id.available_list);
        bluetoothListController.mAvailableList = recyclerView2;
        recyclerView2.setLayoutManager(new LinearLayoutManager(1));
        bluetoothListController.mAvailableList.setItemAnimator(defaultItemAnimator2);
        if (bluetoothListController.mAvailableListAdapter == null) {
            bluetoothListController.mAvailableListAdapter =
                    new BluetoothAvailableListAdapter(
                            bluetoothListController.mContext,
                            bluetoothListController.mAvailableList,
                            bluetoothListController,
                            bluetoothListController.mLocalAdapter);
        }
        bluetoothListController.mAvailableList.setAdapter(
                bluetoothListController.mAvailableListAdapter);
        bluetoothListController.setRoundedCorner(bluetoothListController.mAvailableList);
        bluetoothListController.mCastLayout = (LinearLayout) inflate.findViewById(R.id.cast_layout);
        bluetoothListController.mCastDeviceCategory =
                (TextView) inflate.findViewById(R.id.cast_device_category);
        RecyclerView recyclerView3 = (RecyclerView) inflate.findViewById(R.id.cast_list);
        bluetoothListController.mCastList = recyclerView3;
        recyclerView3.setLayoutManager(new LinearLayoutManager(1));
        bluetoothListController.mCastList.setItemAnimator(new DefaultItemAnimator());
        if (bluetoothListController.mCastListAdapter == null) {
            bluetoothListController.mCastListAdapter =
                    new BluetoothCastListAdapter(
                            bluetoothListController.mContext,
                            bluetoothListController.mCastList,
                            bluetoothListController,
                            bluetoothListController.mLocalAdapter);
        }
        bluetoothListController.mCastList.setAdapter(bluetoothListController.mCastListAdapter);
        bluetoothListController.setRoundedCorner(bluetoothListController.mCastList);
        RecyclerView recyclerView4 = (RecyclerView) inflate.findViewById(R.id.no_item_list);
        bluetoothListController.mNoItemList = recyclerView4;
        recyclerView4.setLayoutManager(new LinearLayoutManager(1));
        if (bluetoothListController.mNoItemListAdapter == null) {
            bluetoothListController.mNoItemListAdapter =
                    new BluetoothNoItemListAdapter(
                            bluetoothListController.mContext,
                            bluetoothListController.mNoItemList,
                            bluetoothListController,
                            bluetoothListController.mLocalAdapter);
        }
        bluetoothListController.mNoItemList.setAdapter(bluetoothListController.mNoItemListAdapter);
        bluetoothListController.setRoundedCorner(bluetoothListController.mNoItemList);
        bluetoothListController.mBottomArea = inflate.findViewById(R.id.bottom_area);
        if (bluetoothListController.isDialogType()) {
            int color =
                    bluetoothListController
                            .mContext
                            .getResources()
                            .getColor(android.R.color.transparent);
            bluetoothListController.mCastDeviceCategory.setBackgroundColor(color);
            bluetoothListController.mAvailableDeviceCategory.setBackgroundColor(color);
            bluetoothListController.mPairedDeviceCategory.setBackgroundColor(color);
            bluetoothListController.mBottomArea.setBackgroundColor(color);
            bluetoothListController.mGuideView.setBackgroundColor(color);
        }
        this.mListView = inflate;
        this.mListController.mListener = this.mListEventListener;
        return inflate;
    }

    @Override // com.android.settings.RestrictedSettingsFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public void onDestroy() {
        CachedBluetoothDeviceManager cachedBluetoothDeviceManager;
        super.onDestroy();
        int[] iArr = {0, 0};
        LocalBluetoothManager localBluetoothManager =
                LocalBluetoothManager.getInstance(getActivity(), Utils.mOnInitCallback);
        if (localBluetoothManager != null
                && (cachedBluetoothDeviceManager = localBluetoothManager.mCachedDeviceManager)
                        != null) {
            Iterator it =
                    ((ArrayList) cachedBluetoothDeviceManager.getCachedDevicesCopy()).iterator();
            while (it.hasNext()) {
                CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) it.next();
                if (cachedBluetoothDevice.mBondState == 12) {
                    iArr[0] = iArr[0] + 1;
                    if (cachedBluetoothDevice.isConnected()) {
                        iArr[1] = iArr[1] + 1;
                    }
                }
            }
        }
        Log.d(
                "DeviceListPreferenceFragment",
                "onDestroy(): device count logging, bondedCount = "
                        + iArr[0]
                        + " , connectedCount = "
                        + iArr[1]);
        String string = getActivity().getResources().getString(R.string.screen_bluetooth_global);
        String string2 =
                getActivity()
                        .getResources()
                        .getString(R.string.event_bluetooth_setting_connected_device_count);
        getActivity()
                .getResources()
                .getString(R.string.event_bluetooth_setting_paired_device_count);
        SALogging.insertSALog(string, string2, String.valueOf(iArr[1]));
        getActivity()
                .getSharedPreferences("sa_logging_bluetooth", 0)
                .edit()
                .putInt("bluetooth_paired_devices_number", iArr[0])
                .apply();
        if (this.mLocalManager != null) {
            removeAllGroupDevices(false);
        }
        BluetoothListController bluetoothListController = this.mListController;
        bluetoothListController.getClass();
        Log.d("BluetoothListController", "removeAll() - Triggered");
        bluetoothListController.mAvailableListAdapter.removeAll();
        bluetoothListController.mPairedListAdapter.removeAll();
        bluetoothListController.mCastListAdapter.removeAll();
        bluetoothListController.mPairedList.setAdapter(null);
        bluetoothListController.mAvailableList.setAdapter(null);
        bluetoothListController.mCastList.setAdapter(null);
        bluetoothListController.mNoItemList.setAdapter(null);
        View view = this.mListView;
        if (view != null) {
            ((ViewGroup) view).removeAllViews();
            this.mListView = null;
        }
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public void onDeviceAdded(CachedBluetoothDevice cachedBluetoothDevice) {
        boolean z;
        StringBuilder sb = new StringBuilder("onDeviceAdded: cached = ");
        sb.append(cachedBluetoothDevice.getName());
        sb.append(", bonded = ");
        Preference$$ExternalSyntheticOutline0.m(
                sb, cachedBluetoothDevice.mBondState, "DeviceListPreferenceFragment");
        if (this.mDevicePreferenceMap.get(cachedBluetoothDevice) != null) {
            Log.d(
                    "DeviceListPreferenceFragment",
                    "onDeviceAdded: " + cachedBluetoothDevice.getName() + ", contains already");
            return;
        }
        if (this.mLocalAdapter.mAdapter.getState() != 12) {
            return;
        }
        int i = 0;
        if (this.mIsPickerDialog) {
            BluetoothDeviceFilter.Filter filter = BluetoothDeviceFilter.getFilter(5);
            z = false;
            while (true) {
                BluetoothDeviceFilter.Filter[] filterArr = this.mFilter;
                if (i >= filterArr.length) {
                    break;
                }
                if (filterArr[i].matches(cachedBluetoothDevice)
                        && filter.matches(cachedBluetoothDevice)) {
                    createDevicePreference(cachedBluetoothDevice, this.mSelectedAdapter);
                    z = true;
                }
                i++;
            }
        } else {
            boolean z2 = false;
            while (true) {
                BluetoothDeviceFilter.Filter[] filterArr2 = this.mFilter;
                if (i >= filterArr2.length) {
                    break;
                }
                if (filterArr2[i].matches(cachedBluetoothDevice)) {
                    createDevicePreference(cachedBluetoothDevice, this.mSelectedAdapter);
                    z2 = true;
                }
                i++;
            }
            z = z2;
        }
        if (!z
                && cachedBluetoothDevice.isHearingAidDevice()
                && cachedBluetoothDevice.isConnected()) {
            SecBluetoothDevicePreference secBluetoothDevicePreference =
                    new SecBluetoothDevicePreference(
                            getActivity(), cachedBluetoothDevice, this.mListController);
            this.mListController.mPairedListAdapter.add(secBluetoothDevicePreference);
            this.mDevicePreferenceMap.put(
                    secBluetoothDevicePreference.mCachedDevice, secBluetoothDevicePreference);
        }
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceDeleted(CachedBluetoothDevice cachedBluetoothDevice) {
        Log.d(
                "DeviceListPreferenceFragment",
                "onDeviceDeleted() name - " + cachedBluetoothDevice.getNameForLog());
        SecBluetoothDevicePreference secBluetoothDevicePreference =
                (SecBluetoothDevicePreference)
                        this.mDevicePreferenceMap.remove(cachedBluetoothDevice);
        if (secBluetoothDevicePreference == null) {
            secBluetoothDevicePreference =
                    new SecBluetoothDevicePreference(
                            getActivity(), cachedBluetoothDevice, this.mListController);
        }
        this.mListController.mPairedListAdapter.delete(secBluetoothDevicePreference);
        this.mListController.mAvailableListAdapter.delete(secBluetoothDevicePreference);
    }

    public void onDevicePreferenceClick(SecBluetoothDevicePreference secBluetoothDevicePreference) {
        PanProfile panProfile;
        NetworkCapabilities networkCapabilities;
        final Context context = secBluetoothDevicePreference.getContext();
        int i = secBluetoothDevicePreference.mCachedDevice.mBondState;
        Log.d(
                "SecBluetoothDevicePreference",
                "onClicked: " + secBluetoothDevicePreference.mCachedDevice.describeDetail());
        BluetoothDump.BtLog(
                "DevPref -- onClicked: "
                        + secBluetoothDevicePreference.mCachedDevice.describeDetail());
        if (secBluetoothDevicePreference.mCachedDevice.isConnectedForGroup()) {
            SALogging.insertSALog(
                    secBluetoothDevicePreference
                            .getContext()
                            .getString(R.string.screen_bluetooth_global),
                    secBluetoothDevicePreference
                            .getContext()
                            .getString(R.string.event_bluetooth_bdsc),
                    secBluetoothDevicePreference
                            .getContext()
                            .getString(R.string.detail_bluetooth_bdsc_connect_to_pair));
            Log.d(
                    "SecBluetoothDevicePreference",
                    "Calling disconnect device with "
                            + secBluetoothDevicePreference.mCachedDevice.getNameForLog());
            secBluetoothDevicePreference.mCachedDevice.disconnect();
            return;
        }
        if (i != 11) {
            boolean z = false;
            if (i == 12) {
                SALogging.insertSALog(
                        secBluetoothDevicePreference
                                .getContext()
                                .getString(R.string.screen_bluetooth_global),
                        secBluetoothDevicePreference
                                .getContext()
                                .getString(R.string.event_bluetooth_bdsc),
                        secBluetoothDevicePreference
                                .getContext()
                                .getString(R.string.detail_bluetooth_bdsc_pair_to_connect));
            } else {
                SALogging.insertSALog(
                        secBluetoothDevicePreference
                                .getContext()
                                .getString(R.string.screen_bluetooth_global),
                        secBluetoothDevicePreference
                                .getContext()
                                .getString(R.string.event_bluetooth_bdsc),
                        secBluetoothDevicePreference
                                .getContext()
                                .getString(R.string.detail_bluetooth_bdsc_unpair_to_connect));
                String name = secBluetoothDevicePreference.mCachedDevice.getName();
                if (name != null) {
                    int indexOf = name.indexOf("(");
                    if (indexOf > 0) {
                        name = name.substring(0, indexOf);
                    }
                    String replaceAll = name.replaceAll("[^0-9a-zA-Z ]", ApnSettings.MVNO_NONE);
                    if (ApnSettings.MVNO_NONE.equals(replaceAll)) {
                        replaceAll = "ETC";
                    }
                    SALogging.insertSALog(
                            secBluetoothDevicePreference
                                    .getContext()
                                    .getString(R.string.screen_bluetooth_global),
                            secBluetoothDevicePreference
                                    .getContext()
                                    .getString(
                                            R.string
                                                    .event_bluetooth_setting_parse_available_device_name),
                            replaceAll);
                }
            }
            final CachedBluetoothDevice cachedBluetoothDevice =
                    secBluetoothDevicePreference.mCachedDevice;
            if (i == 12 && cachedBluetoothDevice.isPanProfileOnlySupported()) {
                boolean z2 = BluetoothUtils.DEBUG;
                ConnectivityManager connectivityManager =
                        (ConnectivityManager) context.getSystemService("connectivity");
                if (connectivityManager != null
                        && (networkCapabilities =
                                        connectivityManager.getNetworkCapabilities(
                                                connectivityManager.getActiveNetwork()))
                                != null
                        && networkCapabilities.hasTransport(1)) {
                    Log.i("SecBluetoothDevicePreference", "isPanProfileDenied: isWiFiConnected");
                    cachedBluetoothDevice.mErrorMsg =
                            BluetoothUtils.getTetheringErrorWithWifi(context);
                    cachedBluetoothDevice.refresh();
                    return;
                }
                LocalBluetoothProfileManager localBluetoothProfileManager =
                        cachedBluetoothDevice.mProfileManager;
                if (localBluetoothProfileManager != null
                        && (panProfile = localBluetoothProfileManager.mPanProfile) != null) {
                    z = panProfile.isNapEnabled();
                }
                if (z) {
                    Log.i("SecBluetoothDevicePreference", "isPanProfileDenied: isNapEnabled");
                    new Handler(Looper.getMainLooper())
                            .post(
                                    new BluetoothUtils$$ExternalSyntheticLambda6(
                                            context,
                                            R.string
                                                    .bluetooth_tethering_turn_off_bluetooth_tethering_title,
                                            R.string
                                                    .bluetooth_tethering_turn_off_bluetooth_tethering_message,
                                            R.string.bluetooth_tethering_turn_off,
                                            new Runnable() { // from class:
                                                             // com.samsung.android.settings.bluetooth.SecBluetoothDevicePreference$$ExternalSyntheticLambda0
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    PanProfile panProfile2;
                                                    CachedBluetoothDevice cachedBluetoothDevice2 =
                                                            cachedBluetoothDevice;
                                                    Context context2 = context;
                                                    int i2 = SecBluetoothDevicePreference.sDimAlpha;
                                                    LocalBluetoothProfileManager
                                                            localBluetoothProfileManager2 =
                                                                    cachedBluetoothDevice2
                                                                            .mProfileManager;
                                                    boolean z3 = false;
                                                    if (localBluetoothProfileManager2 != null
                                                            && (panProfile2 =
                                                                            localBluetoothProfileManager2
                                                                                    .mPanProfile)
                                                                    != null) {
                                                        z3 = panProfile2.isNapEnabled();
                                                    }
                                                    if (z3) {
                                                        BluetoothUtils.stopTethering(context2);
                                                    }
                                                    cachedBluetoothDevice2.connect$1();
                                                }
                                            },
                                            new SecBluetoothDevicePreference$$ExternalSyntheticLambda1()));
                    return;
                }
            }
            secBluetoothDevicePreference.mCachedDevice.connect$1();
        }
    }

    @Override // com.samsung.android.settingslib.bluetooth.SemBluetoothCallback
    public final void onResourceUpdated() {
        if (DEBUG) {
            Log.d("DeviceListPreferenceFragment", "onResourceUpdated()");
        }
        BluetoothListController bluetoothListController = this.mListController;
        if (bluetoothListController != null) {
            bluetoothListController.notifyDataSetChanged();
        }
    }

    public void onScanningStateChanged(boolean z) {
        if (z) {
            this.mScanStartTime = System.currentTimeMillis();
        } else {
            BluetoothListAdapter bluetoothListAdapter = this.mSelectedAdapter;
            if (bluetoothListAdapter != null) {
                bluetoothListAdapter.notifyDataSetChanged();
            }
            sendBsdcDataForSaLogging();
        }
        this.mListController.mAvailableListAdapter.update();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        LocalBluetoothManager localBluetoothManager = this.mLocalManager;
        if (localBluetoothManager != null) {
            localBluetoothManager.mEventManager.registerCallback(this);
            this.mLocalManager.mEventManager.registerSemCallback(this);
            this.mLocalManager.setForegroundActivity(getActivity());
            if (SemBluetoothCastAdapter.isBluetoothCastSupported()) {
                BluetoothCastEventManager bluetoothCastEventManager =
                        this.mLocalManager.mCastEventManager;
                synchronized (bluetoothCastEventManager.mCallbacks) {
                    ((ArrayList) bluetoothCastEventManager.mCallbacks).add(this);
                }
            }
            isUiRestricted();
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        LocalBluetoothManager localBluetoothManager = this.mLocalManager;
        if (localBluetoothManager != null) {
            localBluetoothManager.mEventManager.unregisterCallback(this);
            this.mLocalManager.mEventManager.unregisterSemCallback(this);
            this.mLocalManager.setForegroundActivity(null);
            if (SemBluetoothCastAdapter.isBluetoothCastSupported()) {
                BluetoothCastEventManager bluetoothCastEventManager =
                        this.mLocalManager.mCastEventManager;
                synchronized (bluetoothCastEventManager.mCallbacks) {
                    ((ArrayList) bluetoothCastEventManager.mCallbacks).remove(this);
                }
            }
            if (isUiRestricted()) {
                return;
            }
            if (!this.mLocalManager.semIsForegroundActivity()) {
                Log.d(
                        "DeviceListPreferenceFragment",
                        "onStop(): bluetooth settings is not foreground. So, stop scanning");
                if (this.mLocalAdapter.mAdapter.isDiscovering()) {
                    this.mLocalAdapter.cancelDiscovery();
                    this.mScanFinishAction = "Stop_Activity";
                    sendBsdcDataForSaLogging();
                }
            }
            Iterator it =
                    ((ArrayList) this.mLocalManager.mCachedDeviceManager.getCachedDevicesCopy())
                            .iterator();
            while (it.hasNext()) {
                ((CachedBluetoothDevice) it.next()).mErrorMsg = null;
            }
            if (SemBluetoothCastAdapter.isBluetoothCastSupported()) {
                Iterator it2 =
                        ((ArrayList)
                                        this.mLocalManager.mCachedCastDeviceManager
                                                .getCachedCastDevicesCopy())
                                .iterator();
                while (it2.hasNext()) {
                    ((CachedBluetoothCastDevice) it2.next()).mErrorMsg = null;
                }
            }
            this.mScanFinishAction = "Callback";
        }
    }

    @Override // com.samsung.android.settingslib.bluetooth.SemBluetoothCallback
    public final void onSyncDeviceAdded(CachedBluetoothDevice cachedBluetoothDevice) {
        if (DEBUG) {
            Log.d(
                    "DeviceListPreferenceFragment",
                    "onSyncDeviceAdded() - " + cachedBluetoothDevice.getName());
        }
        int i = 0;
        this.mListController.mPairedListAdapter.setNeedVi(false);
        if (this.mDevicePreferenceMap.get(cachedBluetoothDevice) != null
                || this.mLocalAdapter.getBluetoothState() != 12) {
            return;
        }
        while (true) {
            BluetoothDeviceFilter.Filter[] filterArr = this.mFilter;
            if (i >= filterArr.length) {
                this.mListController.mPairedListAdapter.setNeedVi(true);
                this.mListController.mPairedListAdapter.update();
                Collections.sort(this.mListController.mPairedListAdapter.mPreferenceList);
                this.mListController.mPairedListAdapter.notifyDataSetChanged();
                return;
            }
            if (filterArr[i].matches(cachedBluetoothDevice)) {
                createDevicePreference(
                        cachedBluetoothDevice, this.mListController.mPairedListAdapter);
            }
            i++;
        }
    }

    public final void removeAllGroupDevices(boolean z) {
        try {
            this.mDevicePreferenceMap.clear();
            this.mLocalManager.mCachedDeviceManager.clearNonBondedDevices();
            if (SemBluetoothCastAdapter.isBluetoothCastSupported() && z) {
                this.mLocalManager.mCachedCastDeviceManager.clearAllCastedDevices();
            }
        } catch (RuntimeException e) {
            Log.e("DeviceListPreferenceFragment", "RuntimeException: " + e);
        }
    }

    public final void removeSelectedGroupDevices() {
        this.mDevicePreferenceMap.clear();
        this.mLocalManager.mCachedDeviceManager.clearNonBondedDevices();
        this.mSelectedAdapter.removeAll();
    }

    public final void sendBsdcDataForSaLogging() {
        final String string =
                getActivity().getResources().getString(R.string.screen_bluetooth_global);
        final String string2 =
                getActivity().getResources().getString(R.string.event_bluetooth_setting_bsdc);
        AsyncTask.execute(
                new Runnable() { // from class:
                                 // com.samsung.android.settings.bluetooth.SecDeviceListPreferenceFragment.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        CachedBluetoothDeviceManager cachedBluetoothDeviceManager;
                        long currentTimeMillis =
                                SecDeviceListPreferenceFragment.this.mScanStartTime != 0
                                        ? System.currentTimeMillis()
                                                - SecDeviceListPreferenceFragment.this
                                                        .mScanStartTime
                                        : 0L;
                        if (BluetoothScanDialog.IS_ACTION_BUTTON
                                || SecDevicePickerDialog.IS_ACTION_BUTTON) {
                            SecDeviceListPreferenceFragment.this.mScanFinishAction = "Button";
                            if (BluetoothScanDialog.IS_ACTION_BUTTON) {
                                BluetoothScanDialog.IS_ACTION_BUTTON = false;
                            }
                            if (SecDevicePickerDialog.IS_ACTION_BUTTON) {
                                SecDevicePickerDialog.IS_ACTION_BUTTON = false;
                            }
                        }
                        FragmentActivity activity =
                                SecDeviceListPreferenceFragment.this.getActivity();
                        String str = SecDeviceListPreferenceFragment.this.mScanFinishAction;
                        LocalBluetoothManager localBluetoothManager =
                                LocalBluetoothManager.getInstance(activity, Utils.mOnInitCallback);
                        HashMap hashMap = null;
                        if (localBluetoothManager != null
                                && (cachedBluetoothDeviceManager =
                                                localBluetoothManager.mCachedDeviceManager)
                                        != null) {
                            int i = 2;
                            int[] iArr = {0, 0, 0, 0, 0};
                            int[] iArr2 = {0, 0, 0, 0};
                            int[] iArr3 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                            Iterator it =
                                    ((ArrayList)
                                                    cachedBluetoothDeviceManager
                                                            .getCachedDevicesCopyForSalogging())
                                            .iterator();
                            while (it.hasNext()) {
                                CachedBluetoothDevice cachedBluetoothDevice =
                                        (CachedBluetoothDevice) it.next();
                                if (cachedBluetoothDevice.mBondState != 12) {
                                    iArr[0] = iArr[0] + 1;
                                    int i2 = cachedBluetoothDevice.mType;
                                    if (i2 == 0) {
                                        iArr[1] = iArr[1] + 1;
                                    } else if (i2 == 1) {
                                        iArr[i] = iArr[i] + 1;
                                    } else if (i2 == i) {
                                        iArr[3] = iArr[3] + 1;
                                    } else if (i2 != 3) {
                                        iArr[1] = iArr[1] + 1;
                                    } else {
                                        iArr[4] = iArr[4] + 1;
                                    }
                                    String name = cachedBluetoothDevice.getName();
                                    BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
                                    if (name.equals(bluetoothDevice.getAddress())) {
                                        iArr2[3] = iArr2[3] + 1;
                                    } else if (name.equals(bluetoothDevice.semGetAliasName())) {
                                        iArr2[0] = iArr2[0] + 1;
                                    } else {
                                        iArr2[1] = iArr2[1] + 1;
                                    }
                                    BluetoothClass bluetoothClass = cachedBluetoothDevice.mBtClass;
                                    if (bluetoothClass != null) {
                                        switch (bluetoothClass.getMajorDeviceClass()) {
                                            case 0:
                                                iArr3[0] = iArr3[0] + 1;
                                                break;
                                            case 256:
                                                iArr3[1] = iArr3[1] + 1;
                                                break;
                                            case 512:
                                                iArr3[2] = iArr3[2] + 1;
                                                break;
                                            case 768:
                                                iArr3[3] = iArr3[3] + 1;
                                                break;
                                            case 1024:
                                                iArr3[4] = iArr3[4] + 1;
                                                break;
                                            case PeripheralConstants.ErrorCode
                                                    .ERROR_PLUGIN_CUSTOM_BASE /* 1280 */:
                                                iArr3[5] = iArr3[5] + 1;
                                                break;
                                            case 1536:
                                                iArr3[6] = iArr3[6] + 1;
                                                break;
                                            case 1792:
                                                iArr3[7] = iArr3[7] + 1;
                                                break;
                                            case 2048:
                                                iArr3[8] = iArr3[8] + 1;
                                                break;
                                            case VolteConstants.ErrorCode
                                                    .CALL_FORBIDDEN_RSN_TEMPORARY_DISABILITY /* 2304 */:
                                                iArr3[9] = iArr3[9] + 1;
                                                break;
                                            case 7936:
                                                iArr3[10] = iArr3[10] + 1;
                                                break;
                                            default:
                                                iArr3[0] = iArr3[0] + 1;
                                                break;
                                        }
                                    }
                                }
                                i = 2;
                            }
                            hashMap = new HashMap();
                            hashMap.put("DeviceCount", String.valueOf(iArr[0]));
                            hashMap.put("Action", str);
                            hashMap.put("ScanDelay", String.valueOf(currentTimeMillis));
                            hashMap.put("DeviceType_Unknown", String.valueOf(iArr[1]));
                            hashMap.put("DeviceType_Classic", String.valueOf(iArr[2]));
                            hashMap.put("DeviceType_BLE", String.valueOf(iArr[3]));
                            hashMap.put("DeviceType_Dual", String.valueOf(iArr[4]));
                            hashMap.put("Name_Alias", String.valueOf(iArr2[0]));
                            hashMap.put("Name_Original", String.valueOf(iArr2[1]));
                            hashMap.put("Name_Contact", String.valueOf(iArr2[2]));
                            hashMap.put("Name_Address", String.valueOf(iArr2[3]));
                            hashMap.put("COD_MISC", String.valueOf(iArr3[0]));
                            hashMap.put("COD_COMPUTER", String.valueOf(iArr3[1]));
                            hashMap.put("COD_PHONE", String.valueOf(iArr3[2]));
                            hashMap.put("COD_NETWORKING", String.valueOf(iArr3[3]));
                            hashMap.put("COD_AUDIO_VIDEO", String.valueOf(iArr3[4]));
                            hashMap.put("COD_PERIPHERAL", String.valueOf(iArr3[5]));
                            hashMap.put("COD_IMAGING", String.valueOf(iArr3[6]));
                            hashMap.put("COD_WEARABLE", String.valueOf(iArr3[7]));
                            hashMap.put("COD_TOY", String.valueOf(iArr3[8]));
                            hashMap.put("COD_HEALTH", String.valueOf(iArr3[9]));
                            hashMap.put("COD_UNCATEGORIZED", String.valueOf(iArr3[10]));
                        }
                        SecDeviceListPreferenceFragment secDeviceListPreferenceFragment =
                                SecDeviceListPreferenceFragment.this;
                        secDeviceListPreferenceFragment.mScanFinishAction = "Callback";
                        secDeviceListPreferenceFragment.mScanStartTime = 0L;
                        SALogging.insertSALog(string, string2, hashMap, 0);
                    }
                });
    }

    public final void setFilter(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i, "setFilter: filterType = ", "DeviceListPreferenceFragment");
        if (i != 20) {
            this.mFilter = new BluetoothDeviceFilter.Filter[] {BluetoothDeviceFilter.getFilter(i)};
            return;
        }
        BluetoothDeviceFilter.Filter[] filterArr = new BluetoothDeviceFilter.Filter[2];
        this.mFilter = filterArr;
        filterArr[0] = BluetoothDeviceFilter.getFilter(1);
        this.mFilter[1] = BluetoothDeviceFilter.getFilter(7);
    }

    public void initDevicePreference(SecBluetoothDevicePreference secBluetoothDevicePreference) {}

    public void onCastDiscoveryStateChanged(boolean z) {}

    public void onCastProfileStateChanged(
            CachedBluetoothCastDevice cachedBluetoothCastDevice, int i) {}

    public void onProfileStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice,
            LocalBluetoothProfile localBluetoothProfile,
            int i,
            int i2) {}
}
