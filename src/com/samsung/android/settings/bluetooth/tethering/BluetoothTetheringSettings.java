package com.samsung.android.settings.bluetooth.tethering;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.widget.SeslSwitchBar;
import androidx.fragment.app.FragmentActivity;
import androidx.picker.widget.SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceScreen;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.Utils;
import com.android.settings.core.OnActivityResultListener;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.datausage.DataSaverBackend;
import com.android.settings.datetime.timezone.TimeZoneInfoPreferenceController$$ExternalSyntheticOutline0;
import com.android.settings.fuelgauge.datasaver.DynamicDenylistManager$$ExternalSyntheticOutline0;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.widget.LayoutPreference;
import com.samsung.android.settings.bluetooth.tethering.BluetoothTetheringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BluetoothTetheringSettings extends DashboardFragment implements OnActivityResultListener {
    public static final boolean DBG = !SystemProperties.getBoolean("ro.product_ship", true);
    public static final int XML_PREFERENCE_SCREEN = R.xml.sec_bluetooth_tethering_settings;
    public Context mContext = null;
    public FragmentActivity mActivity = null;
    public LayoutPreference mDescription = null;
    public RecyclerView mDevicesView = null;
    public SettingsMainSwitchBar mSettingsMainSwitchBar = null;
    public BluetoothTetheringSwitchEnabler mSwitchEnabler = null;
    public ConnectedDevicesAdapter mListAdapter = null;
    public BluetoothTetheringUtils$$ExternalSyntheticLambda0 mDataSaverListener = null;
    public BluetoothTetheringReceiver mBroadcastReceiver = null;
    public BluetoothTetheringUtils.AnonymousClass3 mSatelliteModeContentObserver = null;
    public final Handler mHandler = new Handler(Looper.getMainLooper());

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class BluetoothTetheringReceiver extends BroadcastReceiver {
        public BluetoothTetheringReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String str;
            String action = intent.getAction();
            boolean z = BluetoothTetheringSettings.DBG;
            if (z) {
                Log.v("BluetoothTetheringSettings", "onReceive: " + action);
            }
            if (action == null) {
            }
            str = "on";
            switch (action) {
                case "android.bluetooth.adapter.action.STATE_CHANGED":
                    int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE);
                    if (z) {
                        AtomicReference atomicReference = BluetoothTetheringUtils.mDataSaverBackend;
                        switch (intExtra) {
                            case 10:
                                str = "off";
                                break;
                            case 11:
                                str = "turning on";
                                break;
                            case 12:
                                break;
                            case 13:
                                str = "turning off";
                                break;
                            default:
                                str = "unknown";
                                break;
                        }
                        Log.d("BluetoothTetheringSettings", "BT state: ".concat(str));
                    }
                    if (intExtra == 12 || intExtra == 10) {
                        BluetoothTetheringSettings.this.updateConnectedDevices();
                        break;
                    }
                    break;
                case "android.bluetooth.pan.profile.action.CONNECTION_STATE_CHANGED":
                    int intExtra2 = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0);
                    Log.d("BluetoothTetheringSettings", "PAN connection state: ".concat(BluetoothTetheringUtils.getPanStateMessage(intExtra2)));
                    if (intExtra2 == 2 || intExtra2 == 0) {
                        BluetoothTetheringSettings.m1155$$Nest$msetSwitchBarEnabled(BluetoothTetheringSettings.this, true);
                    } else if (intExtra2 == 1 || intExtra2 == 3) {
                        BluetoothTetheringSettings.m1155$$Nest$msetSwitchBarEnabled(BluetoothTetheringSettings.this, false);
                    }
                    BluetoothTetheringSettings.this.updateConnectedDevices();
                    break;
                case "android.bluetooth.action.TETHERING_STATE_CHANGED":
                    Log.d("BluetoothTetheringSettings", "BT tethering state: ".concat(intent.getIntExtra("android.bluetooth.extra.TETHERING_STATE", 1) == 1 ? "off" : "on"));
                    BluetoothTetheringSettings bluetoothTetheringSettings = BluetoothTetheringSettings.this;
                    boolean isNapEnabled = LocalBluetoothManager.getInstance(bluetoothTetheringSettings.mContext, BluetoothUtils.mOnInitCallback).mProfileManager.mPanProfile.isNapEnabled();
                    if (z) {
                        Log.v("BluetoothTetheringSettings", "setSwitchBarChecked: " + isNapEnabled);
                    }
                    SettingsMainSwitchBar settingsMainSwitchBar = bluetoothTetheringSettings.mSettingsMainSwitchBar;
                    if (settingsMainSwitchBar != null && ((SeslSwitchBar) settingsMainSwitchBar).mSwitch.isChecked() != isNapEnabled) {
                        bluetoothTetheringSettings.mSettingsMainSwitchBar.setChecked(isNapEnabled);
                    }
                    BluetoothTetheringSettings.this.updateConnectedDevices();
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ConnectedDevicesAdapter extends RecyclerView.Adapter {
        public final Context context;
        public final ArrayList deviceList = new ArrayList();
        public final TextView noDevicesTextView;
        public final RecyclerView recyclerView;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class ViewHolder extends RecyclerView.ViewHolder {
            public final TextView name;
            public final View view;

            public ViewHolder(View view) {
                super(view);
                this.view = view;
                this.name = (TextView) view.findViewById(R.id.name);
            }
        }

        public ConnectedDevicesAdapter(Context context, RecyclerView recyclerView, TextView textView) {
            this.context = context;
            this.recyclerView = recyclerView;
            this.noDevicesTextView = textView;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final int getItemCount() {
            if (this.deviceList.isEmpty()) {
                this.noDevicesTextView.setVisibility(0);
            } else {
                this.noDevicesTextView.setVisibility(8);
            }
            return this.deviceList.size();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            if (this.deviceList.isEmpty() || i < 0 || i > this.deviceList.size()) {
                return;
            }
            ((ViewHolder) viewHolder).name.setText((String) this.deviceList.get(i));
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.sec_bluetooth_tethering_device_item, (ViewGroup) this.recyclerView, false));
        }
    }

    /* renamed from: -$$Nest$msetSwitchBarEnabled, reason: not valid java name */
    public static void m1155$$Nest$msetSwitchBarEnabled(BluetoothTetheringSettings bluetoothTetheringSettings, boolean z) {
        if (DBG) {
            bluetoothTetheringSettings.getClass();
            Log.v("BluetoothTetheringSettings", "setSwitchBarEnabled: " + z);
        }
        SettingsMainSwitchBar settingsMainSwitchBar = bluetoothTetheringSettings.mSettingsMainSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.setEnabled(z);
        }
    }

    public final void finishSettings() {
        if (Utils.isTablet()) {
            finishFragment();
        } else {
            finish();
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "BluetoothTetheringSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 90;
    }

    @Override // com.android.settings.dashboard.DashboardFragment, com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return XML_PREFERENCE_SCREEN;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        if (DBG) {
            Log.v("BluetoothTetheringSettings", "onActivityCreated");
        }
        this.mSettingsMainSwitchBar = ((SettingsActivity) this.mActivity).mMainSwitch;
        this.mSwitchEnabler = new BluetoothTetheringSwitchEnabler(this.mActivity, this.mSettingsMainSwitchBar);
        getSettingsLifecycle().addObserver(this.mSwitchEnabler);
    }

    @Override // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        DynamicDenylistManager$$ExternalSyntheticOutline0.m("onActivityResult: requestCode: ", ", resultCode: ", i, i2, "BluetoothTetheringSettings");
        BluetoothTetheringSwitchEnabler bluetoothTetheringSwitchEnabler = this.mSwitchEnabler;
        if (bluetoothTetheringSwitchEnabler == null) {
            Log.i("BluetoothTetheringSettings", "onActivityResult: SwitchEnabler is null");
            return;
        }
        if (i == 0) {
            if (i2 == -1) {
                Log.i("BluetoothTetheringSwitchEnabler", "Provisioning success");
                bluetoothTetheringSwitchEnabler.setTethering(true);
            } else {
                bluetoothTetheringSwitchEnabler.setSwitchChecked(false);
                bluetoothTetheringSwitchEnabler.setTethering(false);
            }
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment, com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (DBG) {
            Log.v("BluetoothTetheringSettings", "onCreate");
        }
        this.mContext = getContext();
        this.mActivity = getActivity();
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        if (DBG) {
            Log.v("BluetoothTetheringSettings", "onCreateView");
        }
        return onCreateView;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onDestroy() {
        if (DBG) {
            Log.v("BluetoothTetheringSettings", "onDestroy");
        }
        AtomicReference atomicReference = BluetoothTetheringUtils.mDataSaverBackend;
        atomicReference.set(null);
        AtomicReference atomicReference2 = BluetoothTetheringUtils.mRestrictionUtils;
        atomicReference2.set(null);
        this.mSettingsMainSwitchBar = null;
        BluetoothTetheringSwitchEnabler bluetoothTetheringSwitchEnabler = this.mSwitchEnabler;
        if (BluetoothTetheringSwitchEnabler.DBG) {
            bluetoothTetheringSwitchEnabler.getClass();
            Log.v("BluetoothTetheringSwitchEnabler", "onDestroy");
        }
        bluetoothTetheringSwitchEnabler.mConnectivityManager = null;
        bluetoothTetheringSwitchEnabler.mSettingsMainSwitchBar = null;
        bluetoothTetheringSwitchEnabler.mSwitchPreferenceScreen = null;
        atomicReference.set(null);
        atomicReference2.set(null);
        bluetoothTetheringSwitchEnabler.mActivity = null;
        bluetoothTetheringSwitchEnabler.mContext = null;
        this.mSwitchEnabler = null;
        this.mActivity = null;
        this.mContext = null;
        super.onDestroy();
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        if (DBG) {
            Log.v("BluetoothTetheringSettings", "onDestroyView");
        }
        getSettingsLifecycle().removeObserver(this.mSwitchEnabler);
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSettingsMainSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.hide();
        }
        super.onDestroyView();
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onDetach() {
        if (DBG) {
            Log.v("BluetoothTetheringSettings", "onDetach");
        }
        super.onDetach();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onPause() {
        if (DBG) {
            Log.v("BluetoothTetheringSettings", "onPause");
        }
        this.mSwitchEnabler.getClass();
        if (BluetoothTetheringSwitchEnabler.DBG) {
            Log.v("BluetoothTetheringSwitchEnabler", "onPause");
        }
        BluetoothTetheringSwitchEnabler.setScanMode(21);
        super.onPause();
    }

    @Override // com.android.settings.dashboard.DashboardFragment, com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        if (DBG) {
            Log.v("BluetoothTetheringSettings", "onResume");
        }
        updateConnectedDevices();
        BluetoothTetheringSwitchEnabler bluetoothTetheringSwitchEnabler = this.mSwitchEnabler;
        if (BluetoothTetheringSwitchEnabler.DBG) {
            bluetoothTetheringSwitchEnabler.getClass();
            Log.v("BluetoothTetheringSwitchEnabler", "onResume");
        }
        if (bluetoothTetheringSwitchEnabler.isTetheringEnabled()) {
            BluetoothTetheringSwitchEnabler.setScanMode(23);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.samsung.android.settings.bluetooth.tethering.BluetoothTetheringSettings$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r3v19, types: [android.database.ContentObserver, com.samsung.android.settings.bluetooth.tethering.BluetoothTetheringUtils$3] */
    @Override // com.android.settings.dashboard.DashboardFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        boolean restrictBackground;
        super.onStart();
        if (DBG) {
            Log.v("BluetoothTetheringSettings", "onStart");
        }
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSettingsMainSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.show();
        }
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        if (preferenceScreen == null) {
            Log.w("BluetoothTetheringSettings", "Can't get preference screen");
        } else {
            LayoutPreference layoutPreference = (LayoutPreference) preferenceScreen.findPreference("bluetooth_tethering_description");
            this.mDescription = layoutPreference;
            if (layoutPreference != null) {
                layoutPreference.seslSetSubheaderRoundedBackground(0);
                LayoutPreference layoutPreference2 = this.mDescription;
                ((TextView) layoutPreference2.mRootView.findViewById(R.id.description)).setText(this.mContext.getString(R.string.sec_bluetooth_tethering_description_header));
                StringBuilder sb = new StringBuilder();
                TimeZoneInfoPreferenceController$$ExternalSyntheticOutline0.m(this.mContext, R.string.wifi_ap_instruction_num1, sb, " ");
                ((TextView) layoutPreference2.mRootView.findViewById(R.id.how_to_use_description_step_1)).setText(SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0.m(this.mContext, R.string.sec_bluetooth_tethering_how_to_use_description_step_1, sb));
                StringBuilder sb2 = new StringBuilder();
                TimeZoneInfoPreferenceController$$ExternalSyntheticOutline0.m(this.mContext, R.string.wifi_ap_instruction_num2, sb2, " ");
                ((TextView) layoutPreference2.mRootView.findViewById(R.id.how_to_use_description_step_2)).setText(SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0.m(this.mContext, R.string.sec_bluetooth_tethering_how_to_use_description_step_2, sb2));
                RecyclerView recyclerView = (RecyclerView) layoutPreference2.mRootView.findViewById(R.id.currently_connected_devices);
                this.mDevicesView = recyclerView;
                recyclerView.setLayoutManager(new LinearLayoutManager(1));
                this.mDevicesView.setItemAnimator(new DefaultItemAnimator());
                if (this.mListAdapter == null) {
                    this.mListAdapter = new ConnectedDevicesAdapter(this.mContext, this.mDevicesView, (TextView) layoutPreference2.mRootView.findViewById(R.id.no_devices));
                }
                this.mDevicesView.setAdapter(this.mListAdapter);
            }
        }
        Context context = this.mContext;
        BluetoothTetheringUtils$$ExternalSyntheticLambda0 bluetoothTetheringUtils$$ExternalSyntheticLambda0 = null;
        if (context == null) {
            AtomicReference atomicReference = BluetoothTetheringUtils.mDataSaverBackend;
            restrictBackground = false;
        } else {
            AtomicReference atomicReference2 = BluetoothTetheringUtils.mDataSaverBackend;
            atomicReference2.compareAndSet(null, new DataSaverBackend(context));
            restrictBackground = ((DataSaverBackend) atomicReference2.get()).mPolicyManager.getRestrictBackground();
        }
        if (restrictBackground) {
            Log.i("BluetoothTetheringSettings", "Finish by data saver");
            finishSettings();
        }
        Context context2 = this.mContext;
        Runnable runnable = new Runnable() { // from class: com.samsung.android.settings.bluetooth.tethering.BluetoothTetheringSettings$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BluetoothTetheringSettings bluetoothTetheringSettings = BluetoothTetheringSettings.this;
                boolean z = BluetoothTetheringSettings.DBG;
                bluetoothTetheringSettings.finishSettings();
            }
        };
        if (context2 != null) {
            AtomicReference atomicReference3 = BluetoothTetheringUtils.mDataSaverBackend;
            atomicReference3.compareAndSet(null, new DataSaverBackend(context2));
            BluetoothTetheringUtils$$ExternalSyntheticLambda0 bluetoothTetheringUtils$$ExternalSyntheticLambda02 = new BluetoothTetheringUtils$$ExternalSyntheticLambda0(runnable, null);
            ((DataSaverBackend) atomicReference3.get()).addListener(bluetoothTetheringUtils$$ExternalSyntheticLambda02);
            bluetoothTetheringUtils$$ExternalSyntheticLambda0 = bluetoothTetheringUtils$$ExternalSyntheticLambda02;
        }
        this.mDataSaverListener = bluetoothTetheringUtils$$ExternalSyntheticLambda0;
        BluetoothTetheringReceiver bluetoothTetheringReceiver = new BluetoothTetheringReceiver();
        this.mBroadcastReceiver = bluetoothTetheringReceiver;
        Context context3 = this.mContext;
        IntentFilter intentFilter = new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED");
        intentFilter.addAction("android.bluetooth.pan.profile.action.CONNECTION_STATE_CHANGED");
        intentFilter.addAction("android.bluetooth.action.TETHERING_STATE_CHANGED");
        context3.registerReceiver(bluetoothTetheringReceiver, intentFilter);
        Handler handler = this.mHandler;
        final ?? r1 = new Runnable() { // from class: com.samsung.android.settings.bluetooth.tethering.BluetoothTetheringSettings$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BluetoothTetheringSettings bluetoothTetheringSettings = BluetoothTetheringSettings.this;
                boolean z = BluetoothTetheringSettings.DBG;
                bluetoothTetheringSettings.finishSettings();
            }
        };
        ?? r3 = new ContentObserver(handler) { // from class: com.samsung.android.settings.bluetooth.tethering.BluetoothTetheringUtils.3
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                Runnable runnable2;
                if (z || (runnable2 = r1) == null) {
                    return;
                }
                runnable2.run();
            }
        };
        this.mSatelliteModeContentObserver = r3;
        Context context4 = this.mContext;
        if (context4 != null) {
            Uri uriFor = Settings.Global.getUriFor("satellite_mode_enabled");
            if (uriFor == null) {
                Log.i("BluetoothTetheringUtils", "registerSatelliteModeObserver: Key does not exist in Settings");
            } else {
                context4.getContentResolver().registerContentObserver(uriFor, false, r3);
            }
        }
        this.mSwitchEnabler.onStart();
    }

    @Override // com.android.settings.dashboard.DashboardFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        if (DBG) {
            Log.v("BluetoothTetheringSettings", "onStop");
        }
        this.mSwitchEnabler.onStop();
        Context context = this.mContext;
        BluetoothTetheringUtils.AnonymousClass3 anonymousClass3 = this.mSatelliteModeContentObserver;
        if (context != null && anonymousClass3 != null) {
            context.getContentResolver().unregisterContentObserver(anonymousClass3);
        }
        this.mSatelliteModeContentObserver = null;
        Context context2 = this.mContext;
        BluetoothTetheringReceiver bluetoothTetheringReceiver = this.mBroadcastReceiver;
        if (bluetoothTetheringReceiver != null) {
            context2.unregisterReceiver(bluetoothTetheringReceiver);
        }
        this.mBroadcastReceiver = null;
        Context context3 = this.mContext;
        BluetoothTetheringUtils$$ExternalSyntheticLambda0 bluetoothTetheringUtils$$ExternalSyntheticLambda0 = this.mDataSaverListener;
        if (context3 != null && bluetoothTetheringUtils$$ExternalSyntheticLambda0 != null) {
            AtomicReference atomicReference = BluetoothTetheringUtils.mDataSaverBackend;
            atomicReference.compareAndSet(null, new DataSaverBackend(context3));
            ((DataSaverBackend) atomicReference.get()).remListener(bluetoothTetheringUtils$$ExternalSyntheticLambda0);
        }
        this.mDataSaverListener = null;
        this.mDevicesView = null;
        this.mListAdapter = null;
        this.mDescription = null;
        super.onStop();
    }

    public final void updateConnectedDevices() {
        List list;
        if (DBG) {
            Log.v("BluetoothTetheringSettings", "updateConnectedDevices");
        }
        if (this.mSwitchEnabler == null) {
            Log.i("BluetoothTetheringSettings", "updateConnectedDevices: SwitchEnabler is null");
            updateDevices(new ArrayList());
        } else {
            if (!LocalBluetoothManager.getInstance(this.mContext, BluetoothUtils.mOnInitCallback).mProfileManager.mPanProfile.isNapEnabled()) {
                updateDevices(new ArrayList());
                return;
            }
            if (this.mSwitchEnabler == null) {
                Log.i("BluetoothTetheringSettings", "getConnectedDeviceNames: SwitchEnabler is null");
                list = new ArrayList();
            } else {
                list = (List) BluetoothTetheringUtils.getConnectedDevices(this.mContext).stream().map(new BluetoothTetheringSettings$$ExternalSyntheticLambda1()).collect(Collectors.toList());
            }
            updateDevices(list);
        }
    }

    public final void updateDevices(List list) {
        ConnectedDevicesAdapter connectedDevicesAdapter = this.mListAdapter;
        if (connectedDevicesAdapter != null) {
            connectedDevicesAdapter.deviceList.clear();
            connectedDevicesAdapter.deviceList.addAll(list);
            connectedDevicesAdapter.notifyDataSetChanged();
        }
    }
}
