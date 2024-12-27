package com.samsung.android.settings.connection.tether;

import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.TetheringManager;
import android.os.Bundle;
import android.os.SystemProperties;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceUtils;
import androidx.preference.SecSwitchPreferenceScreen;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.TogglePreferenceController;
import com.android.settings.datausage.DataSaverBackend;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnCreate;
import com.android.settingslib.core.lifecycle.events.OnDestroy;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.bluetooth.tethering.BluetoothTetheringSwitchEnabler;
import com.samsung.android.settings.bluetooth.tethering.BluetoothTetheringUtils;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecBluetoothTetherPreferenceController extends TogglePreferenceController
        implements SecTetherControllerInterface,
                LifecycleObserver,
                DataSaverBackend.Listener,
                OnCreate,
                OnDestroy,
                OnStart,
                OnStop,
                OnPause,
                OnResume {
    private static final boolean DBG = false;
    private static final String TAG = "SecBluetoothTetherPreferenceController";
    private BroadcastReceiver mBroadcastReceiver;
    private DataSaverBackend mDataSaverBackend;
    private SecTetherSettings mFragment;
    private ConnectivityManager.NetworkCallback mNetworkCallback;
    private SecSwitchPreferenceScreen mPreference;
    private BluetoothTetheringSwitchEnabler mSwitchEnabler;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class BluetoothTetherReceiver extends BroadcastReceiver {
        public BluetoothTetherReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null) {}
            switch (action) {
                case "android.bluetooth.adapter.action.STATE_CHANGED":
                    if (SecBluetoothTetherPreferenceController.this.mFragment.getActivity()
                            != null) {
                        int intExtra =
                                intent.getIntExtra(
                                        "android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE);
                        if (intExtra == 13 || intExtra == 11) {
                            SecBluetoothTetherPreferenceController.this.setPrefEnabled(false);
                        } else if (intExtra == 12 || intExtra == 10) {
                            SecBluetoothTetherPreferenceController.this.setPrefEnabled(true);
                        }
                        SecBluetoothTetherPreferenceController.this.updateController();
                        break;
                    }
                    break;
                case "android.bluetooth.pan.profile.action.CONNECTION_STATE_CHANGED":
                    int intExtra2 = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0);
                    Log.d(
                            SecBluetoothTetherPreferenceController.TAG,
                            "PAN connection state: "
                                    .concat(BluetoothTetheringUtils.getPanStateMessage(intExtra2)));
                    if (intExtra2 == 2 || intExtra2 == 0) {
                        SecBluetoothTetherPreferenceController.this.setPrefEnabled(true);
                    } else if (intExtra2 == 1 || intExtra2 == 3) {
                        SecBluetoothTetherPreferenceController.this.setPrefEnabled(false);
                    }
                    SecBluetoothTetherPreferenceController.this.updateController();
                    break;
                case "android.bluetooth.action.TETHERING_STATE_CHANGED":
                    Log.d(
                            SecBluetoothTetherPreferenceController.TAG,
                            "BT tethering state: "
                                    .concat(
                                            intent.getIntExtra(
                                                                    "android.bluetooth.extra.TETHERING_STATE",
                                                                    1)
                                                            == 1
                                                    ? "off"
                                                    : "on"));
                    SecBluetoothTetherPreferenceController.this.updateController();
                    break;
            }
        }
    }

    public SecBluetoothTetherPreferenceController(Context context, String str) {
        super(context, str);
        this.mFragment = null;
        this.mPreference = null;
        this.mSwitchEnabler = null;
        this.mNetworkCallback = null;
        this.mDataSaverBackend = null;
        this.mBroadcastReceiver = null;
    }

    private boolean isTetheringEnabled() {
        return LocalBluetoothManager.getInstance(this.mContext, BluetoothUtils.mOnInitCallback)
                .mProfileManager
                .mPanProfile
                .isNapEnabled();
    }

    private Intent registerReceiver(Context context, BroadcastReceiver broadcastReceiver) {
        IntentFilter intentFilter =
                new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED");
        intentFilter.addAction("android.bluetooth.pan.profile.action.CONNECTION_STATE_CHANGED");
        intentFilter.addAction("android.bluetooth.action.TETHERING_STATE_CHANGED");
        return context.registerReceiver(broadcastReceiver, intentFilter);
    }

    private void setPrefChecked(boolean z) {
        SecSwitchPreferenceScreen secSwitchPreferenceScreen = this.mPreference;
        if (secSwitchPreferenceScreen == null || secSwitchPreferenceScreen.mChecked == z) {
            return;
        }
        secSwitchPreferenceScreen.setChecked(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPrefEnabled(boolean z) {
        SecSwitchPreferenceScreen secSwitchPreferenceScreen = this.mPreference;
        if (secSwitchPreferenceScreen == null || secSwitchPreferenceScreen.isEnabled() == z) {
            return;
        }
        this.mPreference.setEnabled(z);
    }

    private void unregisterReceiver(Context context, BroadcastReceiver broadcastReceiver) {
        if (broadcastReceiver != null) {
            context.unregisterReceiver(broadcastReceiver);
        }
    }

    private void updateSummary(Context context) {
        String string;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null) {
            Log.w(TAG, "updateSummary: Adapter is null");
            return;
        }
        List connectedDevices = BluetoothTetheringUtils.getConnectedDevices(this.mContext);
        int size = connectedDevices.size();
        boolean z = false;
        switch (defaultAdapter.getState()) {
            case 10:
                if (!Utils.isTablet()) {
                    string = context.getString(R.string.bluetooth_tethering_off_subtext_phone);
                    break;
                } else {
                    string = context.getString(R.string.bluetooth_tethering_off_subtext_tablet);
                    break;
                }
            case 11:
                string = context.getString(R.string.bluetooth_turning_on);
                break;
            case 12:
                if (!isTetheringEnabled()) {
                    if (!Utils.isTablet()) {
                        string = context.getString(R.string.bluetooth_tethering_off_subtext_phone);
                        break;
                    } else {
                        string = context.getString(R.string.bluetooth_tethering_off_subtext_tablet);
                        break;
                    }
                } else if (!BluetoothTetheringUtils.isBluetoothErrored(this.mContext)) {
                    if (size != 0) {
                        if (size != 1) {
                            string =
                                    context.getResources()
                                            .getQuantityString(
                                                    R.plurals
                                                            .bluetooth_tethering_connected_devices_subtext,
                                                    size,
                                                    Integer.valueOf(size));
                            break;
                        } else {
                            string = ((BluetoothDevice) connectedDevices.get(0)).getName();
                            break;
                        }
                    } else if (!Utils.isTablet()) {
                        string =
                                context.getString(
                                        R.string.bluetooth_tethering_available_subtext_phone);
                        break;
                    } else {
                        string =
                                context.getString(
                                        R.string.bluetooth_tethering_available_subtext_tablet);
                        break;
                    }
                } else {
                    string = context.getString(R.string.bluetooth_tethering_errored_subtext);
                    break;
                }
            case 13:
                string = context.getString(R.string.bluetooth_turning_off);
                break;
            default:
                string = ApnSettings.MVNO_NONE;
                break;
        }
        SecSwitchPreferenceScreen secSwitchPreferenceScreen = this.mPreference;
        if (secSwitchPreferenceScreen == null) {
            Log.w(TAG, "updateSummary: Preference is null");
            return;
        }
        secSwitchPreferenceScreen.setSummary(string);
        SecSwitchPreferenceScreen secSwitchPreferenceScreen2 = this.mPreference;
        if (isTetheringEnabled() && size > 0) {
            z = true;
        }
        secSwitchPreferenceScreen2.getClass();
        SecPreferenceUtils.applySummaryColor(secSwitchPreferenceScreen2, z);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (SecSwitchPreferenceScreen) preferenceScreen.findPreference(getPreferenceKey());
        this.mSwitchEnabler =
                new BluetoothTetheringSwitchEnabler(this.mFragment.getActivity(), this.mPreference);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        boolean z =
                ((TetheringManager) this.mContext.getSystemService(TetheringManager.class))
                                .getTetherableBluetoothRegexs()
                                .length
                        != 0;
        if (SemCscFeature.getInstance().getBoolean("CscFeature_Common_EnableSprintExtension")) {
            int i = SystemProperties.getInt("persist.sys.tether_data", -1);
            String str = TAG;
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(i, "tetheredData for sprint: ", str);
            if (i < 2) {
                z = false;
            }
            int i2 = SystemProperties.getInt("persist.sys.tether_data_bt", -1);
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    i2, "tetheredDataBluetooth for sprint: ", str);
            if (i2 != -1) {
                boolean z2 = i2 > 0;
                AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                        "BluetoothTetherAvailable: ", str, z2);
                z = z2;
            }
        }
        if (!z) {
            return 4;
        }
        StringBuilder sb = Utils.sBuilder;
        return ActivityManager.isUserAMonkey() ? 4 : 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        boolean isTetheringEnabled = isTetheringEnabled();
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m("isChecked: ", TAG, isTetheringEnabled);
        return isTetheringEnabled;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    public void onActivityResult(int i, int i2) {
        String str = TAG;
        Log.v(str, "onActivityResult: code: " + i2 + ", result: " + i);
        BluetoothTetheringSwitchEnabler bluetoothTetheringSwitchEnabler = this.mSwitchEnabler;
        if (bluetoothTetheringSwitchEnabler == null) {
            Log.i(str, "onActivityResult: SwitchEnabler is null");
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

    /* JADX WARN: Type inference failed for: r1v0, types: [com.samsung.android.settings.connection.tether.SecBluetoothTetherPreferenceController$$ExternalSyntheticLambda0] */
    @Override // com.android.settingslib.core.lifecycle.events.OnCreate
    public void onCreate(Bundle bundle) {
        this.mNetworkCallback =
                BluetoothTetheringUtils.registerNetworkCallback(
                        this.mContext,
                        new Runnable() { // from class:
                                         // com.samsung.android.settings.connection.tether.SecBluetoothTetherPreferenceController$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                SecBluetoothTetherPreferenceController.this.updateController();
                            }
                        },
                        new Runnable() { // from class:
                                         // com.samsung.android.settings.connection.tether.SecBluetoothTetherPreferenceController$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                SecBluetoothTetherPreferenceController.this.updateController();
                            }
                        });
    }

    @Override // com.samsung.android.settings.connection.tether.SecTetherControllerInterface,
              // com.android.settings.datausage.DataSaverBackend.Listener
    public void onDataSaverChanged(boolean z) {
        if (z) {
            Log.i(TAG, "Data saver is enabled");
            setPrefChecked(false);
        }
        setPrefEnabled(!z);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnDestroy
    public void onDestroy() {
        ConnectivityManager connectivityManager;
        BluetoothTetheringSwitchEnabler bluetoothTetheringSwitchEnabler = this.mSwitchEnabler;
        if (BluetoothTetheringSwitchEnabler.DBG) {
            bluetoothTetheringSwitchEnabler.getClass();
            Log.v("BluetoothTetheringSwitchEnabler", "onDestroy");
        }
        bluetoothTetheringSwitchEnabler.mConnectivityManager = null;
        bluetoothTetheringSwitchEnabler.mSettingsMainSwitchBar = null;
        bluetoothTetheringSwitchEnabler.mSwitchPreferenceScreen = null;
        AtomicReference atomicReference = BluetoothTetheringUtils.mDataSaverBackend;
        atomicReference.set(null);
        AtomicReference atomicReference2 = BluetoothTetheringUtils.mRestrictionUtils;
        atomicReference2.set(null);
        bluetoothTetheringSwitchEnabler.mActivity = null;
        bluetoothTetheringSwitchEnabler.mContext = null;
        this.mSwitchEnabler = null;
        Context context = this.mContext;
        ConnectivityManager.NetworkCallback networkCallback = this.mNetworkCallback;
        if (context != null
                && networkCallback != null
                && (connectivityManager =
                                (ConnectivityManager) context.getSystemService("connectivity"))
                        != null) {
            connectivityManager.unregisterNetworkCallback(networkCallback);
        }
        this.mNetworkCallback = null;
        atomicReference.set(null);
        atomicReference2.set(null);
        this.mPreference = null;
        this.mFragment = null;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        this.mSwitchEnabler.getClass();
        if (BluetoothTetheringSwitchEnabler.DBG) {
            Log.v("BluetoothTetheringSwitchEnabler", "onPause");
        }
        BluetoothTetheringSwitchEnabler.setScanMode(21);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        BluetoothTetheringSwitchEnabler bluetoothTetheringSwitchEnabler = this.mSwitchEnabler;
        if (BluetoothTetheringSwitchEnabler.DBG) {
            bluetoothTetheringSwitchEnabler.getClass();
            Log.v("BluetoothTetheringSwitchEnabler", "onResume");
        }
        if (bluetoothTetheringSwitchEnabler.isTetheringEnabled()) {
            BluetoothTetheringSwitchEnabler.setScanMode(23);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        DataSaverBackend dataSaverBackend = new DataSaverBackend(this.mContext);
        this.mDataSaverBackend = dataSaverBackend;
        dataSaverBackend.addListener(this);
        BluetoothTetherReceiver bluetoothTetherReceiver = new BluetoothTetherReceiver();
        this.mBroadcastReceiver = bluetoothTetherReceiver;
        Intent registerReceiver = registerReceiver(this.mContext, bluetoothTetherReceiver);
        if (registerReceiver != null) {
            this.mBroadcastReceiver.onReceive(this.mContext, registerReceiver);
        }
        this.mSwitchEnabler.onStart();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        this.mSwitchEnabler.onStop();
        this.mDataSaverBackend.remListener(this);
        this.mDataSaverBackend = null;
        unregisterReceiver(this.mContext, this.mBroadcastReceiver);
        this.mBroadcastReceiver = null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        String str = TAG;
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m("setChecked: ", str, z);
        BluetoothTetheringSwitchEnabler bluetoothTetheringSwitchEnabler = this.mSwitchEnabler;
        if (bluetoothTetheringSwitchEnabler == null) {
            Log.i(str, "setChecked: SwitchEnabler is null");
            return false;
        }
        if (z) {
            bluetoothTetheringSwitchEnabler.startTethering();
        } else {
            bluetoothTetheringSwitchEnabler.setTethering(false);
        }
        return false;
    }

    public void setHost(SecTetherSettings secTetherSettings) {
        this.mFragment = secTetherSettings;
    }

    @Override // com.samsung.android.settings.connection.tether.SecTetherControllerInterface
    public /* bridge */ /* synthetic */ void startProvisioningIfNecessary(
            SecTetherSettings secTetherSettings, int i) {
        super.startProvisioningIfNecessary(secTetherSettings, i);
    }

    @Override // com.samsung.android.settings.connection.tether.SecTetherControllerInterface
    public void startTethering() {
        String str = TAG;
        Log.i(str, "startTethering");
        BluetoothTetheringSwitchEnabler bluetoothTetheringSwitchEnabler = this.mSwitchEnabler;
        if (bluetoothTetheringSwitchEnabler == null) {
            Log.i(str, "startTethering: SwitchEnabler is null");
        } else {
            bluetoothTetheringSwitchEnabler.startTethering();
        }
    }

    @Override // com.samsung.android.settings.connection.tether.SecTetherControllerInterface
    public void updateController() {
        boolean isTetheringAllowed;
        Context context = this.mContext;
        if (context == null) {
            AtomicReference atomicReference = BluetoothTetheringUtils.mDataSaverBackend;
            isTetheringAllowed = false;
        } else {
            isTetheringAllowed = BluetoothTetheringUtils.isTetheringAllowed(context, true);
        }
        setPrefEnabled(isTetheringAllowed);
        setPrefChecked(isTetheringEnabled());
        updateSummary(this.mContext);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public void onActivityCreated(Bundle bundle) {}

    public void onSaveInstanceState(Bundle bundle) {}

    @Override // com.android.settings.datausage.DataSaverBackend.Listener
    public /* bridge */ /* synthetic */ void onAllowlistStatusChanged(int i, boolean z) {}

    @Override // com.android.settings.datausage.DataSaverBackend.Listener
    public /* bridge */ /* synthetic */ void onDenylistStatusChanged(int i, boolean z) {}
}
