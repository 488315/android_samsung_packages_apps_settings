package com.samsung.android.settings.bluetooth;

import android.app.Activity;
import android.app.KeyguardManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.UserHandle;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;

import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SeslSwitchBar;
import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;

import com.android.internal.logging.MetricsLogger;
import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.accounts.ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.bluetooth.BluetoothEnabler;
import com.android.settings.bluetooth.Utils;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.homepage.TopLevelSettings;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.bluetooth.BluetoothDeviceFilter;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.CachedBluetoothDeviceManager;
import com.android.settingslib.bluetooth.LocalBluetoothAdapter;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfile;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;

import com.samsung.android.bluetooth.SemBluetoothCastAdapter;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.knox.ex.peripheral.PeripheralConstants;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.bixby.AppContext;
import com.samsung.android.settings.bluetooth.adapter.BluetoothCastListAdapter;
import com.samsung.android.settings.bluetooth.adapter.BluetoothListAdapter;
import com.samsung.android.settings.bluetooth.bluetoothcast.BluetoothCastDevicePreference;
import com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastSourceSetting;
import com.samsung.android.settings.connection.ConnectionsSettings;
import com.samsung.android.settings.general.BaseResetSettingsData;
import com.samsung.android.settings.general.OnResetSettingsDataListener;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.samsung.android.settings.wifi.WifiTipsUtils;
import com.samsung.android.settingslib.bluetooth.SppProfile;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.CachedBluetoothCastDevice;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.LocalBluetoothCastAdapter;
import com.samsung.android.settingslib.bluetooth.detector.BluetoothRetryDetector;
import com.samsung.android.widget.SemTipPopup;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BluetoothSettings extends SecDeviceListPreferenceFragment {
    public static boolean mIsDeviceProfileShown = false;
    public static boolean mIsForegroundBtSettings = false;
    public static String mLocalAdapterName;
    public AccessibilityManager mAccessibilityManager;
    public View mAppBarView;
    public final AnonymousClass2 mBluetoothCastAdapterCallback;
    public BluetoothEnabler mBluetoothEnabler;
    public final AnonymousClass4 mDeviceProfilesListener;
    public final AnonymousClass3 mHandler;
    public boolean mInitiateDiscoverable;
    public boolean mInitiateScan;
    public boolean mIsBtScanDialog;
    public boolean mIsEmergencyMode;
    public boolean mIsRegistered;
    public boolean mIsSupportTips;
    public boolean mPolicyEnabled;
    public final AnonymousClass1 mReceiver;
    public long mResumeTime;
    public MenuItem mScan;
    public BluetoothRetryDetector mScanDetector;
    public MenuItem mScanTipsMark;
    public String mScreenId;
    public final SettingsObserver mSettingsObserver;
    public SettingsMainSwitchBar mSwitchBar;
    public Handler mTipHandler;
    public SemTipPopup mTipsDescriptionPopup;
    public final AnonymousClass7 tipTimerRunnable;
    public static final List mAdvancedDeltaTargets =
            new ArrayList(Arrays.asList("SPR", "VMU", "BST", "XAS", "VZW"));
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass5();
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass6();
    public static final OnResetSettingsDataListener RESET_SETTINGS_DATA = new AnonymousClass8();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.bluetooth.BluetoothSettings$2, reason: invalid class name */
    public final class AnonymousClass2 {
        public AnonymousClass2() {}

        public final void onBluetoothCastAdapterStateChanged(boolean z) {
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            BluetoothSettings bluetoothSettings = BluetoothSettings.this;
            if (defaultAdapter != null && defaultAdapter.isDiscovering()) {
                bluetoothSettings.mLocalCastAdapter.startDiscovery();
            }
            if (z) {
                return;
            }
            BluetoothCastListAdapter bluetoothCastListAdapter =
                    bluetoothSettings.mListController.mCastListAdapter;
            if (bluetoothCastListAdapter != null) {
                bluetoothCastListAdapter.removeAll();
            }
            bluetoothSettings.mListController.setVisibleCastGroup(8);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.bluetooth.BluetoothSettings$5, reason: invalid class name */
    public final class AnonymousClass5 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            ((ArrayList) nonIndexableKeys).add("bluetooth_more_advanced");
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.className = BluetoothSettings.class.getName();
            searchIndexableResource.xmlResId = R.xml.bluetooth_settings;
            arrayList.add(searchIndexableResource);
            SearchIndexableResource searchIndexableResource2 = new SearchIndexableResource(context);
            searchIndexableResource2.className = BluetoothSettings.class.getName();
            searchIndexableResource2.xmlResId = R.xml.sec_bluetooth_more_settings;
            arrayList.add(searchIndexableResource2);
            return arrayList;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.bluetooth.BluetoothSettings$6, reason: invalid class name */
    public final class AnonymousClass6 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            int i =
                    context.getSharedPreferences("sa_logging_bluetooth", 0)
                            .getInt("bluetooth_paired_devices_number", 0);
            String valueOf = String.valueOf(1181);
            String valueOf2 = String.valueOf(i);
            StatusData statusData = new StatusData();
            statusData.mStatusValue = valueOf2;
            statusData.mStatusKey = valueOf;
            return Arrays.asList(statusData);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.bluetooth.BluetoothSettings$8, reason: invalid class name */
    public final class AnonymousClass8 extends BaseResetSettingsData {
        @Override // com.samsung.android.settings.general.OnResetSettingsDataListener
        public final void resetSettings(Context context) {
            Log.i("BluetoothSettings", "BT settings reset");
            Settings.Secure.putInt(context.getContentResolver(), "bluetooth_bubble_tips_count", 0);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SettingsObserver extends ContentObserver {
        public final Uri BLUETOOTH_CAST_DISALLOWED_URI;
        public final Uri BLUETOOTH_CAST_MODE_URI;

        public SettingsObserver(Handler handler) {
            super(handler);
            this.BLUETOOTH_CAST_MODE_URI = Settings.Secure.getUriFor("bluetooth_cast_mode");
            this.BLUETOOTH_CAST_DISALLOWED_URI =
                    Settings.Secure.getUriFor("bluetooth_cast_disallowed_devices");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            if (this.BLUETOOTH_CAST_MODE_URI.equals(uri)) {
                BluetoothSettings.this.mListController.notifyDataSetChanged();
            } else if (this.BLUETOOTH_CAST_DISALLOWED_URI.equals(uri)) {
                BluetoothSettings.this.mListController.notifyDataSetChanged();
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.samsung.android.settings.bluetooth.BluetoothSettings$1] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.samsung.android.settings.bluetooth.BluetoothSettings$3] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.samsung.android.settings.bluetooth.BluetoothSettings$4] */
    /* JADX WARN: Type inference failed for: r0v7, types: [com.samsung.android.settings.bluetooth.BluetoothSettings$7] */
    public BluetoothSettings() {
        super("no_config_bluetooth");
        this.mInitiateDiscoverable = false;
        this.mInitiateScan = false;
        this.mIsRegistered = false;
        this.mIsBtScanDialog = false;
        this.mIsEmergencyMode = false;
        this.mPolicyEnabled = false;
        this.mReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.samsung.android.settings.bluetooth.BluetoothSettings.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        BluetoothSettings bluetoothSettings;
                        LocalBluetoothAdapter localBluetoothAdapter;
                        String action = intent.getAction();
                        if (action == null) {
                            Log.d(
                                    "BluetoothSettings",
                                    "onReceive: Intent.getAction() is return null");
                            return;
                        }
                        Log.d("BluetoothSettings", "onReceive: getAction = ".concat(action));
                        if (action.equals("com.android.settings.DEVICE_NAME_CHANGED")) {
                            LocalBluetoothAdapter localBluetoothAdapter2 =
                                    BluetoothSettings.this.mLocalAdapter;
                            if (localBluetoothAdapter2 != null
                                    && localBluetoothAdapter2.getBluetoothState() == 12
                                    && BluetoothSettings.mIsForegroundBtSettings) {
                                BluetoothSettings bluetoothSettings2 = BluetoothSettings.this;
                                bluetoothSettings2.updateContent(
                                        bluetoothSettings2.mLocalAdapter.getBluetoothState(),
                                        false,
                                        false);
                                return;
                            }
                            return;
                        }
                        if (!"android.bluetooth.adapter.action.SCAN_MODE_CHANGED".equals(action)
                                || BluetoothSettings.this.mLocalManager == null) {
                            return;
                        }
                        int intExtra =
                                intent.getIntExtra("android.bluetooth.adapter.extra.SCAN_MODE", 20);
                        if (!BluetoothSettings.this.mLocalManager.semIsForegroundActivity()
                                || intExtra == 23
                                || (localBluetoothAdapter =
                                                (bluetoothSettings = BluetoothSettings.this)
                                                        .mLocalAdapter)
                                        == null
                                || bluetoothSettings.mPolicyEnabled
                                || bluetoothSettings.mIsPickerDialog) {
                            return;
                        }
                        localBluetoothAdapter.mAdapter.setScanMode(23);
                    }
                };
        this.mBluetoothCastAdapterCallback = new AnonymousClass2();
        this.mHandler =
                new Handler() { // from class:
                                // com.samsung.android.settings.bluetooth.BluetoothSettings.3
                    @Override // android.os.Handler
                    public final void handleMessage(Message message) {
                        if (message.what != 0) {
                            return;
                        }
                        removeMessages(0);
                        BluetoothSettings bluetoothSettings = BluetoothSettings.this;
                        if (bluetoothSettings.mLocalManager.semIsForegroundActivity()
                                && !bluetoothSettings.mLocalAdapter.mAdapter.isDiscovering()
                                && !bluetoothSettings.startScanning()) {
                            bluetoothSettings.addCachedDevices$1();
                        }
                        bluetoothSettings.mInitiateScan = false;
                    }
                };
        this.mDeviceProfilesListener =
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.bluetooth.BluetoothSettings.4
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        String concat;
                        if (!(view.getTag(R.id.bluetooth_profile)
                                instanceof CachedBluetoothDevice)) {
                            if (!(view.getTag(R.id.bluetooth_tips)
                                    instanceof CachedBluetoothDevice)) {
                                Log.w(
                                        "BluetoothSettings",
                                        "onClick() called for other View: " + view);
                                return;
                            }
                            Log.w("BluetoothSettings", "onClick() called for tips");
                            CachedBluetoothDevice cachedBluetoothDevice =
                                    (CachedBluetoothDevice) view.getTag(R.id.bluetooth_tips);
                            if (cachedBluetoothDevice == null) {
                                Log.w("BluetoothSettings", "Device is null");
                                return;
                            }
                            Context context = BluetoothSettings.this.getContext();
                            BluetoothRetryDetector bluetoothRetryDetector =
                                    cachedBluetoothDevice.mBondingDetector;
                            if (bluetoothRetryDetector != null) {
                                bluetoothRetryDetector.fireTips(context);
                            }
                            BluetoothSettings bluetoothSettings = BluetoothSettings.this;
                            String str = bluetoothSettings.mScreenId;
                            String string =
                                    bluetoothSettings
                                            .getResources()
                                            .getString(R.string.event_bluetooth_setting_list_tips);
                            String str2 =
                                    cachedBluetoothDevice.mIsRestored
                                            ? "RESTORED_"
                                            : ApnSettings.MVNO_NONE;
                            if (cachedBluetoothDevice.mBondingDetector != null) {
                                StringBuilder m =
                                        EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0
                                                .m(str2);
                                m.append(cachedBluetoothDevice.mBondingDetector.mFailCase.name());
                                concat = m.toString();
                            } else {
                                concat = str2.concat("CONNECTION_FAILURE");
                            }
                            SALogging.insertSALog(str, string, concat);
                            return;
                        }
                        BluetoothSettings bluetoothSettings2 = BluetoothSettings.this;
                        SALogging.insertSALog(
                                bluetoothSettings2.mScreenId,
                                bluetoothSettings2
                                        .getResources()
                                        .getString(
                                                R.string
                                                        .event_bluetooth_setting_profile_setting_button));
                        CachedBluetoothDevice cachedBluetoothDevice2 =
                                (CachedBluetoothDevice) view.getTag(R.id.bluetooth_profile);
                        BluetoothSettings bluetoothSettings3 = BluetoothSettings.this;
                        BluetoothDevice bluetoothDevice = cachedBluetoothDevice2.mDevice;
                        bluetoothSettings3.getClass();
                        Bundle bundle = new Bundle(2);
                        bundle.putString("device_address", bluetoothDevice.getAddress());
                        bundle.putParcelable("device", bluetoothDevice);
                        bundle.putBoolean("isCalledFromSetting", true);
                        BluetoothSettings.mIsDeviceProfileShown = true;
                        if (bluetoothSettings3.getActivity() instanceof SettingsActivity) {
                            SubSettingLauncher subSettingLauncher =
                                    new SubSettingLauncher(bluetoothSettings3.getActivity());
                            SubSettingLauncher.LaunchRequest launchRequest =
                                    subSettingLauncher.mLaunchRequest;
                            launchRequest.mSourceMetricsCategory = 24;
                            launchRequest.mDestinationName =
                                    SecBluetoothDeviceDetailsSettings.class.getCanonicalName();
                            launchRequest.mArguments = bundle;
                            subSettingLauncher.launch();
                            return;
                        }
                        if (Utils.DEBUG) {
                            Log.d(
                                    "BluetoothSettings",
                                    "moveToBtOptionMenu() :: Launching with intent");
                        }
                        Intent intent = new Intent();
                        intent.setAction("com.samsung.settings.DEVICE_PROFILES_SETTINGS");
                        intent.putExtra("device", bundle);
                        Context applicationContext =
                                bluetoothSettings3.getActivity().getApplicationContext();
                        intent.setFlags(335544320);
                        try {
                            applicationContext.startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                };
        this.mSettingsObserver = new SettingsObserver(new Handler());
        this.tipTimerRunnable =
                new Runnable() { // from class:
                                 // com.samsung.android.settings.bluetooth.BluetoothSettings.7
                    @Override // java.lang.Runnable
                    public final void run() {
                        SemTipPopup semTipPopup = BluetoothSettings.this.mTipsDescriptionPopup;
                        if (semTipPopup == null || !semTipPopup.isShowing()) {
                            return;
                        }
                        BluetoothSettings.this.mTipsDescriptionPopup.dismiss(true);
                    }
                };
    }

    public final void addDeviceCategory(
            BluetoothListAdapter bluetoothListAdapter,
            BluetoothDeviceFilter.Filter filter,
            boolean z) {
        this.mFilter = new BluetoothDeviceFilter.Filter[] {filter};
        this.mSelectedAdapter = bluetoothListAdapter;
        if (z) {
            addCachedDevices$1();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final AppContext getAppContext() {
        if (this.mSwitchBar == null) {
            return null;
        }
        AppContext.Builder builder = new AppContext.Builder();
        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(
                "bluetoothEnabled",
                ((SeslSwitchBar) this.mSwitchBar).mSwitch.isChecked() ? "true" : "false");
        jSONArray.put(jSONObject2);
        JSONArray jSONArray2 = builder.mLLMContext;
        jSONObject.put(
                "type",
                jSONArray.length() > 1
                        ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m(
                                "BluetoothSettings", "[]")
                        : "BluetoothSettings");
        int length = jSONArray.length();
        JSONObject jSONObject3 = jSONArray;
        if (length <= 1) {
            jSONObject3 = jSONObject2;
        }
        jSONObject.put("value", jSONObject3);
        jSONArray2.put(jSONObject);
        return builder.build();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return SemEmergencyManager.isEmergencyMode(context)
                ? TopLevelSettings.class.getName()
                : ConnectionsSettings.class.getName();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 24;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.bluetooth_settings;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return SemEmergencyManager.isEmergencyMode(context)
                ? "top_level_bluetooth_upsm"
                : "top_level_connections";
    }

    @Override // com.samsung.android.settings.bluetooth.SecDeviceListPreferenceFragment
    public final void initDevicePreference(
            SecBluetoothDevicePreference secBluetoothDevicePreference) {
        if (Utils.DEBUG) {
            Log.d("BluetoothSettings", "init pref:" + secBluetoothDevicePreference.getName());
        }
        secBluetoothDevicePreference.mOnSettingsClickListener = this.mDeviceProfilesListener;
    }

    public final void moveToBluetoothOptionMenu(int i, String str, String str2) {
        if (getActivity() instanceof SettingsActivity) {
            SubSettingLauncher subSettingLauncher = new SubSettingLauncher(getActivity());
            SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
            launchRequest.mSourceMetricsCategory = 24;
            launchRequest.mDestinationName = str;
            subSettingLauncher.setTitleRes(i, null);
            subSettingLauncher.launch();
            return;
        }
        if (Utils.DEBUG) {
            Log.d("BluetoothSettings", "moveToBtOptionMenu() :: Launching with intent");
        }
        Context applicationContext = getActivity().getApplicationContext();
        Intent intent = new Intent(str2);
        intent.setFlags(335544320);
        try {
            applicationContext.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override // com.samsung.android.settings.bluetooth.SecDeviceListPreferenceFragment,
              // com.android.settings.RestrictedSettingsFragment,
              // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        LocalBluetoothAdapter localBluetoothAdapter;
        Object obj;
        super.onActivityCreated(bundle);
        this.mInitiateDiscoverable = true;
        this.mInitiateScan = bundle == null;
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                new StringBuilder("onActivityCreated :: mInitiateScan : "),
                this.mInitiateScan,
                "BluetoothSettings");
        if (!this.mIsBtScanDialog) {
            SettingsActivity settingsActivity = (SettingsActivity) getActivity();
            SettingsMainSwitchBar settingsMainSwitchBar = settingsActivity.mMainSwitch;
            this.mSwitchBar = settingsMainSwitchBar;
            BluetoothEnabler bluetoothEnabler =
                    new BluetoothEnabler(settingsActivity, settingsMainSwitchBar);
            this.mBluetoothEnabler = bluetoothEnabler;
            BluetoothEnabler.BluetoothSwitchType bluetoothSwitchType = bluetoothEnabler.mSwitchType;
            if (bluetoothSwitchType != null
                    && (obj = bluetoothSwitchType.mSwitchObject) != null
                    && (obj instanceof SettingsMainSwitchBar)) {
                ((SettingsMainSwitchBar) obj).show();
                if (!BluetoothEnabler.this.mPolicyEnabled) {
                    bluetoothSwitchType.setChecked(false);
                    bluetoothSwitchType.setEnabled(false);
                }
            }
            LocalBluetoothAdapter localBluetoothAdapter2 = this.mLocalAdapter;
            if (localBluetoothAdapter2 != null
                    && localBluetoothAdapter2.mAdapter.getBondedDevices() != null
                    && this.mLocalAdapter.mAdapter.getBondedDevices().size() == 0) {
                this.mSwitchBar.getSwitch().requestFocus();
            }
            setHasOptionsMenu(true);
        }
        if (this.mInitiateScan
                && (localBluetoothAdapter = this.mLocalAdapter) != null
                && localBluetoothAdapter.mAdapter.isDiscovering()) {
            this.mLocalAdapter.stopScanning();
            if (isSupportBluetoothCast()) {
                this.mLocalCastAdapter.suspendDiscovery();
            }
        }
        this.mAccessibilityManager =
                (AccessibilityManager) getActivity().getSystemService("accessibility");
    }

    @Override // androidx.fragment.app.Fragment
    public final void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mIsBtScanDialog =
                (activity instanceof BluetoothScanDialog)
                        || (activity instanceof SecDevicePickerDialog);
        Log.w("BluetoothSettings", "onAttach, mIsBtScanDialog : " + this.mIsBtScanDialog);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onBluetoothStateChanged(int i) {
        if (i == 12 && !this.mIsBtScanDialog && this.mIsSupportTips) {
            SemTipPopup semTipPopup = this.mTipsDescriptionPopup;
            if (semTipPopup != null && semTipPopup.isShowing()) {
                this.mTipsDescriptionPopup.dismiss(false);
            }
            showSemTipPopup(true);
        }
        MenuItem menuItem = this.mScan;
        if (menuItem != null) {
            if (i == 12) {
                menuItem.setVisible(true);
            } else {
                menuItem.setVisible(false);
            }
        }
        if (i == 10 && this.mIsSupportTips) {
            BluetoothRetryDetector bluetoothRetryDetector = this.mScanDetector;
            if (bluetoothRetryDetector != null) {
                bluetoothRetryDetector.reset();
            }
            this.mLocalManager.mRestoredRetryDetector.reset();
            SemTipPopup semTipPopup2 = this.mTipsDescriptionPopup;
            if (semTipPopup2 != null && semTipPopup2.isShowing()) {
                this.mTipsDescriptionPopup.dismiss(false);
            }
        }
        updateContent(i, this.mInitiateScan, true);
    }

    @Override // com.samsung.android.settings.bluetooth.SecDeviceListPreferenceFragment
    public final void onCastDevicePreferenceClick(
            BluetoothCastDevicePreference bluetoothCastDevicePreference) {
        this.mLocalCastAdapter.suspendDiscovery();
        this.mLocalAdapter.stopScanning();
        super.onCastDevicePreferenceClick(bluetoothCastDevicePreference);
    }

    @Override // com.samsung.android.settings.bluetooth.SecDeviceListPreferenceFragment,
              // com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastCallback
    public final void onCastDiscoveryStateChanged(boolean z) {
        if (z) {
            return;
        }
        this.mListController.mCastListAdapter.update();
    }

    @Override // com.samsung.android.settings.bluetooth.SecDeviceListPreferenceFragment,
              // com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastCallback
    public final void onCastProfileStateChanged(
            CachedBluetoothCastDevice cachedBluetoothCastDevice, int i) {
        if (i != 2 || cachedBluetoothCastDevice.isBusy()) {
            return;
        }
        cachedBluetoothCastDevice.mErrorMsg = null;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        SemTipPopup semTipPopup;
        super.onConfigurationChanged(configuration);
        if (!this.mIsBtScanDialog
                && (semTipPopup = this.mTipsDescriptionPopup) != null
                && semTipPopup.isShowing()) {
            this.mTipsDescriptionPopup.dismiss(false);
            showSemTipPopup(false);
        }
        BluetoothListController bluetoothListController = this.mListController;
        if (bluetoothListController != null) {
            bluetoothListController.setLayoutParams();
        }
    }

    @Override // com.samsung.android.settings.bluetooth.SecDeviceListPreferenceFragment,
              // com.android.settings.RestrictedSettingsFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        Resources resources;
        int i;
        CachedBluetoothDeviceManager cachedBluetoothDeviceManager;
        SemEmergencyManager semEmergencyManager;
        Log.w("BluetoothSettings", "onCreate");
        super.onCreate(bundle);
        boolean z = false;
        mIsDeviceProfileShown = false;
        if (BluetoothUtils.isEnabledUltraPowerSaving(getActivity())
                && (semEmergencyManager = SemEmergencyManager.getInstance(getActivity())) != null
                && semEmergencyManager.isEmergencyMode()) {
            this.mIsEmergencyMode = true;
        }
        if (this.mIsBtScanDialog) {
            resources = getResources();
            i = R.string.screen_bluetooth_scan_dialog;
        } else {
            resources = getResources();
            i = R.string.screen_bluetooth_setting;
        }
        this.mScreenId = resources.getString(i);
        this.mTipHandler = new Handler();
        if (WifiTipsUtils.isSupportWifiTips(getContext()) && !this.mIsEmergencyMode) {
            z = true;
        }
        this.mIsSupportTips = z;
        if (z) {
            this.mScanDetector =
                    new BluetoothRetryDetector(BluetoothRetryDetector.FailCase.SCANNING_FAILURE);
            if (!this.mIsBtScanDialog) {
                showSemTipPopup(true);
            }
            LocalBluetoothManager localBluetoothManager = this.mLocalManager;
            if (localBluetoothManager == null
                    || (cachedBluetoothDeviceManager = localBluetoothManager.mCachedDeviceManager)
                            == null) {
                return;
            }
            cachedBluetoothDeviceManager.resetCachedDevices();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x00d8  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00f6  */
    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onCreateOptionsMenu(android.view.Menu r9, android.view.MenuInflater r10) {
        /*
            Method dump skipped, instructions count: 288
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.bluetooth.BluetoothSettings.onCreateOptionsMenu(android.view.Menu,"
                    + " android.view.MenuInflater):void");
    }

    @Override // com.samsung.android.settings.bluetooth.SecDeviceListPreferenceFragment,
              // com.android.settings.RestrictedSettingsFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        if (this.mIsSupportTips) {
            this.mScanDetector.reset();
            this.mLocalManager.mRestoredRetryDetector.reset();
        }
        super.onDestroy();
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        BluetoothEnabler bluetoothEnabler;
        BluetoothEnabler.BluetoothSwitchType bluetoothSwitchType;
        Object obj;
        super.onDestroyView();
        if (!this.mIsBtScanDialog
                && (bluetoothEnabler = this.mBluetoothEnabler) != null
                && (bluetoothSwitchType = bluetoothEnabler.mSwitchType) != null
                && (obj = bluetoothSwitchType.mSwitchObject) != null
                && (obj instanceof SettingsMainSwitchBar)) {
            ((SettingsMainSwitchBar) obj).hide();
        }
        if (isSupportBluetoothCast()) {
            LocalBluetoothCastAdapter localBluetoothCastAdapter = this.mLocalCastAdapter;
            SemBluetoothCastAdapter semBluetoothCastAdapter =
                    localBluetoothCastAdapter.mCastAdapter;
            String str = localBluetoothCastAdapter.TAG;
            if (semBluetoothCastAdapter == null) {
                Log.d(str, "Cannot cancelDiscovery");
            } else {
                Log.d(str, "cancelDiscovery");
                localBluetoothCastAdapter.mCastAdapter.cancelDiscovery();
            }
        }
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceBondStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        BluetoothRetryDetector bluetoothRetryDetector;
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i, "onDeviceBondStateChanged :: bondState = ", "BluetoothSettings");
        if (this.mLocalAdapter.getBluetoothState() != 12 || (i != 12 && i != 10)) {
            this.mListController.mCastListAdapter.setNeedUpdatePreference();
            this.mListController.mPairedListAdapter.setNeedUpdatePreference();
            this.mListController.mAvailableListAdapter.setNeedUpdatePreference();
            return;
        }
        if (getActivity() == null) {
            Log.e(
                    "BluetoothSettings",
                    "Activity is null, cannot create SecBluetoothDevicePreference");
            return;
        }
        SecBluetoothDevicePreference secBluetoothDevicePreference =
                new SecBluetoothDevicePreference(
                        getActivity(), cachedBluetoothDevice, this.mListController);
        if (i == 12
                || ((bluetoothRetryDetector = cachedBluetoothDevice.mBondingDetector) != null
                        && bluetoothRetryDetector.mCount >= bluetoothRetryDetector.mMaxCount)) {
            initDevicePreference(secBluetoothDevicePreference);
        }
        if (cachedBluetoothDevice.mIsRestored) {
            return;
        }
        if (i == 12) {
            this.mListController.mPairedListAdapter.add(secBluetoothDevicePreference);
            this.mListController.mAvailableListAdapter.delete(secBluetoothDevicePreference);
        } else if (i == 10) {
            this.mListController.mPairedListAdapter.delete(secBluetoothDevicePreference);
        }
    }

    @Override // com.samsung.android.settings.bluetooth.SecDeviceListPreferenceFragment
    public final void onDevicePreferenceClick(
            SecBluetoothDevicePreference secBluetoothDevicePreference) {
        LocalBluetoothProfileManager localBluetoothProfileManager;
        SppProfile sppProfile;
        if (isSupportBluetoothCast()) {
            this.mLocalCastAdapter.suspendDiscovery();
        }
        CachedBluetoothDevice cachedBluetoothDevice = secBluetoothDevicePreference.mCachedDevice;
        if (cachedBluetoothDevice == null) {
            Log.d("BluetoothSettings", "onDevicePreferenceClick() - cachedDevice is null");
            return;
        }
        BluetoothClass bluetoothClass = cachedBluetoothDevice.mDevice.getBluetoothClass();
        int majorDeviceClass = bluetoothClass == null ? 7936 : bluetoothClass.getMajorDeviceClass();
        String nameForSAlogging =
                (majorDeviceClass == 256 || majorDeviceClass == 512)
                        ? "Personal Device"
                        : cachedBluetoothDevice.getNameForSAlogging();
        if (!cachedBluetoothDevice.isConnected()) {
            SALogging.insertSALog(
                    getResources().getString(R.string.screen_bluetooth_global),
                    this.mIsBtScanDialog
                            ? getResources().getString(R.string.event_bluetooth_bdcc_scan_dialog)
                            : getResources().getString(R.string.event_bluetooth_bdcc_setting));
        } else if (cachedBluetoothDevice.isConnected()) {
            if (cachedBluetoothDevice.getProfiles().size() == 1
                    && (localBluetoothProfileManager = cachedBluetoothDevice.mProfileManager)
                            != null) {
                synchronized (localBluetoothProfileManager) {
                    sppProfile = localBluetoothProfileManager.mSppProfile;
                }
                if (cachedBluetoothDevice.hasProfile(sppProfile)) {
                    Log.d("CachedBluetoothDevice", "isSppOnlyDevice :: true");
                }
            }
            SALogging.insertSALog(
                    getResources().getString(R.string.screen_bluetooth_global),
                    getResources().getString(R.string.event_bluetooth_bddc),
                    getResources().getString(R.string.detail_bluetooth_bddc_settings));
        }
        SALogging.insertSALog(
                this.mScreenId,
                this.mIsBtScanDialog
                        ? cachedBluetoothDevice.mBondState == 12
                                ? getResources()
                                        .getString(
                                                R.string
                                                        .event_bluetooth_scan_dialog_device_list_paired)
                                : getResources()
                                        .getString(
                                                R.string
                                                        .event_bluetooth_scan_dialog_device_list_available)
                        : cachedBluetoothDevice.mBondState == 12
                                ? getResources()
                                        .getString(
                                                R.string.event_bluetooth_setting_device_list_paired)
                                : getResources()
                                        .getString(
                                                R.string
                                                        .event_bluetooth_setting_device_list_available),
                nameForSAlogging);
        super.onDevicePreferenceClick(secBluetoothDevicePreference);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        String string;
        int itemId = menuItem.getItemId();
        if (itemId == 1) {
            Log.w("BluetoothSettings", "onOptionItemSelected() called for tips");
            BluetoothRetryDetector bluetoothRetryDetector = this.mScanDetector;
            if (bluetoothRetryDetector != null) {
                bluetoothRetryDetector.fireTips(getContext());
            }
            SALogging.insertSALog(
                    this.mScreenId,
                    getResources().getString(R.string.event_bluetooth_setting_scan_button_tips));
            return true;
        }
        if (itemId == 2) {
            if (this.mLocalAdapter.getBluetoothState() == 12) {
                if (this.mLocalAdapter.mAdapter.isDiscovering()) {
                    Log.e("BluetoothSettings", "onOptionsItemSelected :: Stop scanning");
                    string = getResources().getString(R.string.detail_bluetooth_scan_stop);
                    this.mLocalAdapter.stopScanning();
                    if (isSupportBluetoothCast()) {
                        this.mLocalCastAdapter.suspendDiscovery();
                    }
                    this.mScanFinishAction = "Button";
                } else {
                    Log.e("BluetoothSettings", "onOptionsItemSelected :: Start scanning");
                    MetricsLogger.action(getActivity(), 160);
                    string = getResources().getString(R.string.detail_bluetooth_scan_start);
                    startScanning();
                }
                SALogging.insertSALog(
                        this.mScreenId,
                        getResources().getString(R.string.event_bluetooth_setting_scan_button),
                        string);
            }
            return true;
        }
        if (itemId == 4) {
            SALogging.insertSALog(
                    this.mScreenId,
                    getResources().getString(R.string.event_bluetooth_setting_more_contact_us));
            getActivity();
            boolean z = Utils.DEBUG;
            try {
                Intent contactUsIntent =
                        com.android.settings.Utils.getContactUsIntent(
                                "com.android.bluetooth", "Bluetooth", "22o85f4b60");
                contactUsIntent.putExtra("faqUrl", "voc://view/categories");
                startActivityForResult(contactUsIntent, 1001);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
            return true;
        }
        if (itemId == 5) {
            if (this.mIsBtScanDialog) {
                Log.e("BluetoothSettings", "It is not user action!");
                return false;
            }
            SALogging.insertSALog(
                    this.mScreenId,
                    getResources().getString(R.string.event_bluetooth_setting_more_advanced));
            moveToBluetoothOptionMenu(
                    R.string.sec_bluetooth_advanced_title,
                    BluetoothAdvancedSettings.class.getCanonicalName(),
                    "com.samsung.settings.BLUETOOTH_ADVANCED_SETTINGS");
            return true;
        }
        if (itemId != 6) {
            if (itemId != 7) {
                if (itemId == 16908332) {
                    SALogging.insertSALog(
                            this.mScreenId,
                            getResources()
                                    .getString(R.string.event_bluetooth_setting_navigate_button));
                }
                return super.onOptionsItemSelected(menuItem);
            }
            SALogging.insertSALog(
                    this.mScreenId,
                    getResources()
                            .getString(
                                    R.string.event_bluetooth_setting_more_launch_broadcast_source));
            moveToBluetoothOptionMenu(
                    R.string.sec_bluetooth_source_start_title,
                    SecBluetoothLeBroadcastSourceSetting.class.getCanonicalName(),
                    "com.samsung.settings.BLUETOOTH_BROADCAST_SOURCE");
            return true;
        }
        BluetoothRetryDetector bluetoothRetryDetector2 = this.mScanDetector;
        if (bluetoothRetryDetector2 != null) {
            Context context = getContext();
            bluetoothRetryDetector2.mTipsIntent.putExtra("disableReason", "MORE_TIPS");
            bluetoothRetryDetector2.mTipsIntent.setFlags(67108864);
            context.startActivity(bluetoothRetryDetector2.mTipsIntent);
        }
        SALogging.insertSALog(
                this.mScreenId,
                getResources().getString(R.string.event_bluetooth_setting_more_tips));
        return true;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        Log.d("BluetoothSettings", "onPause");
        if (!this.mIsBtScanDialog) {
            SALogging.insertSALog(
                    getResources().getString(R.string.screen_bluetooth_global),
                    getResources().getString(R.string.event_bluetooth_bsrt),
                    String.valueOf(System.currentTimeMillis() - this.mResumeTime));
        }
        SettingsObserver settingsObserver = this.mSettingsObserver;
        BluetoothSettings.this.getContentResolver().unregisterContentObserver(settingsObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPrepareOptionsMenu(Menu menu) {
        if (this.mIsSupportTips && this.mScanTipsMark != null) {
            BluetoothRetryDetector bluetoothRetryDetector = this.mScanDetector;
            if (bluetoothRetryDetector == null
                    || bluetoothRetryDetector.mCount < bluetoothRetryDetector.mMaxCount
                    || this.mLocalAdapter.getBluetoothState() != 12) {
                this.mScanTipsMark.setVisible(false);
            } else {
                this.mScanTipsMark.setVisible(true);
            }
        }
        MenuItemImpl menuItemImpl = (MenuItemImpl) menu.findItem(7);
        if (menuItemImpl != null) {
            if (Settings.Secure.getString(getContentResolver(), "bluetooth_le_broadcast_name")
                    == null) {
                menuItemImpl.setBadgeText(ApnSettings.MVNO_NONE);
            } else {
                menuItemImpl.setBadgeText(null);
            }
        }
        super.onPrepareOptionsMenu(menu);
    }

    @Override // com.samsung.android.settings.bluetooth.SecDeviceListPreferenceFragment,
              // com.samsung.android.settingslib.bluetooth.SemBluetoothCallback
    public final void onProfileStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice,
            LocalBluetoothProfile localBluetoothProfile,
            int i,
            int i2) {
        Log.d(
                "BluetoothSettings",
                "onProfileStateChanged :: cachedDevice - "
                        + cachedBluetoothDevice
                        + ", profile - "
                        + localBluetoothProfile
                        + ", newState - "
                        + i
                        + ", oldState - "
                        + i2);
        if (this.mLocalAdapter.mAdapter.isDiscovering()) {
            return;
        }
        if (i2 != 1 || i != 0) {
            if (i == 2) {
                if (!cachedBluetoothDevice.isBusy()) {
                    cachedBluetoothDevice.mErrorMsg = null;
                }
                cachedBluetoothDevice.resetRetryDetector();
                return;
            }
            return;
        }
        if (cachedBluetoothDevice.isBusy()
                || cachedBluetoothDevice.isConnected()
                || cachedBluetoothDevice.isConnectedMembers()) {
            return;
        }
        BluetoothRetryDetector bluetoothRetryDetector = cachedBluetoothDevice.mBondingDetector;
        if (bluetoothRetryDetector != null) {
            bluetoothRetryDetector.mCount++;
        }
        if (localBluetoothProfile.toString().equals("PAN")
                || localBluetoothProfile
                        .toString()
                        .equals(PeripheralConstants.ConnectionProfile.HID)) {
            return;
        }
        cachedBluetoothDevice.mErrorMsg =
                getResources()
                        .getString(
                                R.string.bluetooth_settings_guide_pairing_fail_remote_down,
                                cachedBluetoothDevice.getName());
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x008e  */
    @Override // com.android.settings.RestrictedSettingsFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onResume() {
        /*
            Method dump skipped, instructions count: 260
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.bluetooth.BluetoothSettings.onResume():void");
    }

    @Override // com.samsung.android.settings.bluetooth.SecDeviceListPreferenceFragment,
              // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onScanningStateChanged(boolean z) {
        super.onScanningStateChanged(z);
        Log.d("BluetoothSettings", "onScanningStateChanged :: is start scanning " + z);
        if (com.android.settings.Utils.isTalkBackEnabled(getActivity())) {
            int i = z ? R.string.sec_bluetooth_scan_inprogress : R.string.sec_bluetooth_scan_stop;
            AccessibilityEvent obtain =
                    AccessibilityEvent.obtain(
                            NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT);
            if (obtain != null && obtain.getText() != null) {
                obtain.getText().clear();
                obtain.getText().add(getResources().getString(i));
                AccessibilityManager accessibilityManager = this.mAccessibilityManager;
                if (accessibilityManager != null) {
                    try {
                        accessibilityManager.sendAccessibilityEvent(obtain);
                    } catch (IllegalStateException e) {
                        Log.e("BluetoothSettings", "IllegalStateException" + e);
                    }
                }
            }
        }
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        if (settingsMainSwitchBar != null && !this.mIsBtScanDialog) {
            settingsMainSwitchBar.setProgressBarVisible(z);
        }
        MenuItem menuItem = this.mScan;
        if (menuItem != null && !this.mIsBtScanDialog) {
            menuItem.setTitle(z ? R.string.service_stop : R.string.bluetooth_scan_for_devices);
        }
        if (z) {
            this.mSelectedAdapter = this.mListController.mAvailableListAdapter;
            removeSelectedGroupDevices();
        } else if (!isUiRestricted() && !this.mIsBtScanDialog && getActivity() != null) {
            getActivity().invalidateOptionsMenu();
        }
        if (getActivity() != null) {
            getActivity().invalidateOptionsMenu();
        }
    }

    @Override // com.samsung.android.settings.bluetooth.SecDeviceListPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        if (mIsDeviceProfileShown) {
            this.mInitiateScan = false;
            mIsDeviceProfileShown = false;
        }
        if (!this.mIsBtScanDialog) {
            mIsForegroundBtSettings = true;
        }
        BluetoothEnabler bluetoothEnabler = this.mBluetoothEnabler;
        if (bluetoothEnabler != null) {
            bluetoothEnabler.resume(getActivity());
        }
        if (isSupportBluetoothCast()) {
            LocalBluetoothCastAdapter localBluetoothCastAdapter = this.mLocalCastAdapter;
            AnonymousClass2 anonymousClass2 = this.mBluetoothCastAdapterCallback;
            Log.d(localBluetoothCastAdapter.TAG, "callback added");
            localBluetoothCastAdapter.mCallbacks.add(anonymousClass2);
        }
        if (isUiRestricted() || this.mIsRegistered) {
            return;
        }
        getActivity()
                .registerReceiver(
                        this.mReceiver,
                        ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0.m(
                                "com.android.settings.DEVICE_NAME_CHANGED",
                                "android.bluetooth.adapter.action.SCAN_MODE_CHANGED"));
        this.mIsRegistered = true;
    }

    @Override // com.samsung.android.settings.bluetooth.SecDeviceListPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        Log.d("BluetoothSettings", "onStop");
        this.mPolicyEnabled = false;
        FragmentActivity activity = getActivity();
        UserHandle.getCallingUserId();
        Utils.insertMDMEventLog(activity, 27);
        if (!this.mIsBtScanDialog) {
            mIsForegroundBtSettings = false;
            SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
            if (settingsMainSwitchBar != null) {
                settingsMainSwitchBar.setProgressBarVisible(false);
            }
            MenuItem menuItem = this.mScan;
            if (menuItem != null) {
                menuItem.setTitle(R.string.bluetooth_scan_for_devices);
            }
        }
        BluetoothEnabler bluetoothEnabler = this.mBluetoothEnabler;
        if (bluetoothEnabler != null) {
            bluetoothEnabler.pause();
        }
        if (this.mIsRegistered) {
            getActivity().unregisterReceiver(this.mReceiver);
            this.mIsRegistered = false;
        }
        this.mLocalAdapter.mAdapter.setScanMode(21);
        if (isSupportBluetoothCast()) {
            this.mLocalCastAdapter.suspendDiscovery();
            LocalBluetoothCastAdapter localBluetoothCastAdapter = this.mLocalCastAdapter;
            localBluetoothCastAdapter.mCallbacks.remove(this.mBluetoothCastAdapterCallback);
        }
    }

    public final void showSemTipPopup(final boolean z) {
        final int i;
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m("showTipPopup_", "BluetoothSettings", z);
        LocalBluetoothAdapter localBluetoothAdapter = this.mLocalAdapter;
        if ((localBluetoothAdapter == null || localBluetoothAdapter.getBluetoothState() == 12)
                && (i =
                                Settings.Secure.getInt(
                                        getActivity().getContentResolver(),
                                        "bluetooth_bubble_tips_count",
                                        0))
                        <= 2) {
            new Handler()
                    .postDelayed(
                            new Runnable() { // from class:
                                             // com.samsung.android.settings.bluetooth.BluetoothSettings$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    final BluetoothSettings bluetoothSettings =
                                            BluetoothSettings.this;
                                    boolean z2 = z;
                                    int i2 = i;
                                    boolean z3 = BluetoothSettings.mIsForegroundBtSettings;
                                    if (!bluetoothSettings.isResumed()) {
                                        Log.e(
                                                "BluetoothSettings",
                                                "activity not created, can't create popup");
                                        return;
                                    }
                                    if (z2) {
                                        Settings.Secure.putInt(
                                                bluetoothSettings
                                                        .getActivity()
                                                        .getContentResolver(),
                                                "bluetooth_bubble_tips_count",
                                                i2 + 1);
                                    }
                                    bluetoothSettings.mAppBarView =
                                            bluetoothSettings
                                                    .getActivity()
                                                    .getWindow()
                                                    .getDecorView()
                                                    .findViewById(R.id.app_bar);
                                    SemTipPopup semTipPopup =
                                            new SemTipPopup(bluetoothSettings.mAppBarView);
                                    bluetoothSettings.mTipsDescriptionPopup = semTipPopup;
                                    semTipPopup.setExpanded(true);
                                    int[] iArr = new int[2];
                                    int i3 =
                                            (int)
                                                    ((bluetoothSettings
                                                                            .getContext()
                                                                            .getResources()
                                                                            .getDisplayMetrics()
                                                                            .densityDpi
                                                                    / 160.0f)
                                                            * 35.0f);
                                    int i4 =
                                            (int)
                                                    ((bluetoothSettings
                                                                            .getContext()
                                                                            .getResources()
                                                                            .getDisplayMetrics()
                                                                            .densityDpi
                                                                    / 160.0f)
                                                            * 18.0f);
                                    bluetoothSettings.mTipsDescriptionPopup.setMessage(
                                            bluetoothSettings
                                                    .getResources()
                                                    .getString(
                                                            R.string
                                                                    .sec_bluetooth_tips_bubble_text));
                                    bluetoothSettings.mAppBarView.getLocationInWindow(iArr);
                                    bluetoothSettings.mTipsDescriptionPopup.setTargetPosition(
                                            bluetoothSettings.mAppBarView.getLayoutDirection() == 0
                                                    ? (bluetoothSettings.mAppBarView.getWidth()
                                                                    + iArr[0])
                                                            - i3
                                                    : iArr[0] + i3,
                                            (bluetoothSettings.mAppBarView.getHeight() + iArr[1])
                                                    - i4);
                                    bluetoothSettings.mTipsDescriptionPopup.setAction(
                                            bluetoothSettings
                                                    .getResources()
                                                    .getString(
                                                            R.string
                                                                    .sec_bluetooth_tips_bubble_link),
                                            new View
                                                    .OnClickListener() { // from class:
                                                                         // com.samsung.android.settings.bluetooth.BluetoothSettings$$ExternalSyntheticLambda1
                                                @Override // android.view.View.OnClickListener
                                                public final void onClick(View view) {
                                                    BluetoothSettings bluetoothSettings2 =
                                                            BluetoothSettings.this;
                                                    BluetoothRetryDetector bluetoothRetryDetector =
                                                            bluetoothSettings2.mScanDetector;
                                                    Context context =
                                                            bluetoothSettings2.getContext();
                                                    bluetoothRetryDetector.mTipsIntent.putExtra(
                                                            "disableReason", "MORE_TIPS");
                                                    bluetoothRetryDetector.mTipsIntent.setFlags(
                                                            67108864);
                                                    context.startActivity(
                                                            bluetoothRetryDetector.mTipsIntent);
                                                    Settings.Secure.putInt(
                                                            bluetoothSettings2
                                                                    .getActivity()
                                                                    .getContentResolver(),
                                                            "bluetooth_bubble_tips_count",
                                                            10);
                                                    SALogging.insertSALog(
                                                            bluetoothSettings2.mScreenId,
                                                            bluetoothSettings2
                                                                    .getResources()
                                                                    .getString(
                                                                            R.string
                                                                                    .event_bluetooth_setting_launch_more_tips));
                                                }
                                            });
                                    bluetoothSettings.mTipsDescriptionPopup.show(
                                            bluetoothSettings.mAppBarView.getLayoutDirection() != 0
                                                    ? 3
                                                    : 2);
                                }
                            },
                            z ? 600L : 0L);
            if (z) {
                if (!this.mTipHandler.hasCallbacks(this.tipTimerRunnable)) {
                    this.mTipHandler.postDelayed(this.tipTimerRunnable, 7000L);
                } else {
                    this.mTipHandler.removeCallbacks(this.tipTimerRunnable);
                    this.mTipHandler.postDelayed(this.tipTimerRunnable, 7000L);
                }
            }
        }
    }

    public final boolean startScanning() {
        BluetoothRetryDetector bluetoothRetryDetector;
        if (isUiRestricted()) {
            return false;
        }
        if (isSupportBluetoothCast()) {
            this.mLocalCastAdapter.startDiscovery();
        }
        boolean startScanning = this.mLocalAdapter.startScanning(true);
        if (startScanning && (bluetoothRetryDetector = this.mScanDetector) != null) {
            bluetoothRetryDetector.mCount++;
        }
        return startScanning;
    }

    public final void updateContent(int i, boolean z, boolean z2) {
        Log.w("BluetoothSettings", "updateContent : " + i + ", startScan = " + z);
        int i2 = R.string.bluetooth_empty_list_user_restricted;
        switch (i) {
            case 10:
                int i3 =
                        Rune.isChinaModel()
                                ? R.string.sec_bluetooth_empty_list_bluetooth_off_chn
                                : R.string.sec_bluetooth_empty_list_bluetooth_off;
                if (!isUiRestricted()) {
                    i2 = i3;
                    break;
                }
                break;
            case 11:
                if (!this.mLocalManager.isForegroundActivityQP()) {
                    this.mInitiateScan = true;
                }
                this.mInitiateDiscoverable = true;
                i2 = R.string.bluetooth_scanning_for_bluetooth_devices;
                break;
            case 12:
                BluetoothListController bluetoothListController = this.mListController;
                bluetoothListController.getClass();
                Log.d("BluetoothListController", "removeAll() - Triggered");
                bluetoothListController.mAvailableListAdapter.removeAll();
                bluetoothListController.mPairedListAdapter.removeAll();
                bluetoothListController.mCastListAdapter.removeAll();
                this.mListController.updateEmptyView(false);
                this.mDevicePreferenceMap.clear();
                this.mCastDevicePreferenceMap.clear();
                if (!isUiRestricted()) {
                    String string = Settings.Global.getString(getContentResolver(), "device_name");
                    LocalBluetoothAdapter localBluetoothAdapter = this.mLocalAdapter;
                    if (localBluetoothAdapter != null
                            && string != null
                            && !string.equals(localBluetoothAdapter.mAdapter.getName())) {
                        this.mLocalAdapter.mAdapter.setName(string);
                        Log.d(
                                "BluetoothSettings",
                                "updateDeviceName :: change device name to ".concat(string));
                    }
                    LocalBluetoothAdapter localBluetoothAdapter2 = this.mLocalAdapter;
                    if (localBluetoothAdapter2 != null
                            && localBluetoothAdapter2.mAdapter.getName() != null) {
                        mLocalAdapterName =
                                "\u200e"
                                        + Html.escapeHtml(this.mLocalAdapter.mAdapter.getName())
                                        + "\u200e";
                    }
                    if (mLocalAdapterName == null) {
                        mLocalAdapterName =
                                "\u200e"
                                        + Html.escapeHtml(
                                                Settings.Global.getString(
                                                        getContentResolver(), "device_name"))
                                        + "\u200e";
                    }
                    if (z2) {
                        this.mLocalManager.mEventManager.readRestoredDevices();
                    }
                    StringBuilder sb = new StringBuilder();
                    if (getActivity() == null
                            || !(getActivity() instanceof SecDevicePickerDialog)) {
                        sb.append(
                                getResources()
                                        .getString(R.string.bluetooth_settings_guide_pairing_mode));
                        sb.append(" ");
                        if (this.mPolicyEnabled) {
                            if (com.android.settings.Utils.isTablet()) {
                                sb.append(
                                        getResources()
                                                .getString(
                                                        R.string
                                                                .bluetooth_is_visible_message_mdm_policy_enable_tablet,
                                                        mLocalAdapterName));
                            } else {
                                sb.append(
                                        getResources()
                                                .getString(
                                                        R.string
                                                                .bluetooth_is_visible_message_mdm_policy_enable,
                                                        mLocalAdapterName));
                            }
                        } else if (com.android.settings.Utils.isTablet()) {
                            sb.append(
                                    getResources()
                                            .getString(
                                                    R.string.bluetooth_is_visible_message_tablet,
                                                    mLocalAdapterName));
                        } else {
                            sb.append(
                                    getResources()
                                            .getString(
                                                    R.string.bluetooth_is_visible_message,
                                                    mLocalAdapterName));
                        }
                    } else {
                        sb.append(
                                getResources()
                                        .getString(R.string.bluetooth_check_visibility_help_text));
                    }
                    Log.d(
                            "BluetoothSettings",
                            "getGuideMessage :: my device name set to " + mLocalAdapterName);
                    this.mListController.mGuideView.setText(
                            Html.fromHtml(sb.toString()).toString());
                    if (isSupportBluetoothCast()
                            && ((ArrayList)
                                                    this.mLocalManager.mCachedCastDeviceManager
                                                            .getCachedCastDevicesCopy())
                                            .size()
                                    > 0
                            && isSupportBluetoothCast()) {
                        Collection cachedCastDevicesCopy =
                                this.mLocalManager.mCachedCastDeviceManager
                                        .getCachedCastDevicesCopy();
                        StringBuilder sb2 = new StringBuilder("addCachedCastDevices ");
                        ArrayList arrayList = (ArrayList) cachedCastDevicesCopy;
                        sb2.append(String.valueOf(arrayList.size()));
                        Log.d("DeviceListPreferenceFragment", sb2.toString());
                        this.mListController.mCastListAdapter.setNeedVi(false);
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            super.onCastDeviceAdded((CachedBluetoothCastDevice) it.next());
                        }
                        this.mListController.mCastListAdapter.setNeedVi(true);
                        this.mListController.mCastListAdapter.update();
                        Collections.sort(this.mListController.mCastListAdapter.mPreferenceList);
                        this.mListController.mCastListAdapter.notifyDataSetChanged();
                    }
                    addDeviceCategory(
                            this.mListController.mPairedListAdapter,
                            BluetoothDeviceFilter.BONDED_DEVICE_FILTER,
                            true);
                    addDeviceCategory(
                            this.mListController.mPairedListAdapter,
                            BluetoothDeviceFilter.RESTORED_DEVICE_FILTER,
                            true);
                    addDeviceCategory(
                            this.mListController.mAvailableListAdapter,
                            BluetoothDeviceFilter.UNBONDED_DEVICE_FILTER,
                            !z);
                    if (z) {
                        Log.d("BluetoothSettings", "updateContent :: startScanning()");
                        if (((KeyguardManager) getActivity().getSystemService("keyguard"))
                                .isKeyguardLocked()) {
                            removeSelectedGroupDevices();
                            Message message = new Message();
                            message.what = 0;
                            sendMessageDelayed(message, 500L);
                        } else {
                            if (!this.mLocalAdapter.mAdapter.isDiscovering() && !startScanning()) {
                                addCachedDevices$1();
                            }
                            this.mInitiateScan = false;
                        }
                    }
                    if (this.mPolicyEnabled || this.mIsPickerDialog) {
                        FragmentActivity activity = getActivity();
                        UserHandle.getCallingUserId();
                        Utils.insertMDMEventLog(activity, 70);
                        if (this.mLocalAdapter.mAdapter.getScanMode() == 23) {
                            this.mLocalAdapter.mAdapter.setScanMode(21);
                        }
                    } else if (this.mInitiateDiscoverable) {
                        Log.d(
                                "BluetoothSettings",
                                "updateContent :: set Discoverable mode to"
                                    + " SCAN_MODE_CONNECTABLE_DISCOVERABLE");
                        this.mLocalAdapter.mAdapter.setScanMode(23);
                        this.mInitiateDiscoverable = false;
                        FragmentActivity activity2 = getActivity();
                        UserHandle.getCallingUserId();
                        Utils.insertMDMEventLog(activity2, 26);
                    }
                    getActivity().invalidateOptionsMenu();
                    return;
                }
                break;
            case 13:
                i2 = R.string.bluetooth_turning_off;
                break;
            default:
                i2 = 0;
                break;
        }
        removeAllGroupDevices(true);
        if (i2 != 0) {
            this.mListController.mGuideView.setText(i2);
            this.mListController.updateEmptyView(true);
        }
        if (!isUiRestricted() && !this.mIsBtScanDialog && getActivity() != null) {
            getActivity().invalidateOptionsMenu();
        }
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        if (settingsMainSwitchBar == null || this.mIsBtScanDialog) {
            return;
        }
        settingsMainSwitchBar.setProgressBarVisible(false);
    }

    @Override // com.samsung.android.settings.bluetooth.SecDeviceListPreferenceFragment
    public final void addPreferencesForActivity() {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onConnectionStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i) {}
}
