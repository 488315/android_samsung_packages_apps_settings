package com.samsung.android.settings.bluetooth;

import android.app.AlertDialog;
import android.app.StatusBarManager;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.InstrumentedPreferenceFragment;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.logging.SALogging;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class BluetoothFunctionSettingsBase extends InstrumentedPreferenceFragment
        implements BluetoothCallback {
    public AlertDialog mAlertDialog;
    public Drawable mAnimResource;
    public AlertDialog.Builder mDialogBuilder;
    public BluetoothIBRSettings.AnonymousClass1 mDialogCallback;
    public FunctionEnabler mFunctionEnabler;
    public LocalBluetoothManager mLocalBluetoothManager;
    public final AnonymousClass2 mHandler =
            new Handler() { // from class:
                            // com.samsung.android.settings.bluetooth.BluetoothFunctionSettingsBase.2
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    if (message.what != 0) {
                        return;
                    }
                    BluetoothFunctionSettingsBase bluetoothFunctionSettingsBase =
                            BluetoothFunctionSettingsBase.this;
                    if (bluetoothFunctionSettingsBase.getView() != null) {
                        bluetoothFunctionSettingsBase.updateContentsView();
                    }
                }
            };
    public final AnonymousClass3 mClickListener =
            new DialogInterface
                    .OnClickListener() { // from class:
                                         // com.samsung.android.settings.bluetooth.BluetoothFunctionSettingsBase.3
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    if (i == -2) {
                        BluetoothIBRSettings.AnonymousClass1 anonymousClass1 =
                                BluetoothFunctionSettingsBase.this.mDialogCallback;
                        if (anonymousClass1 == null) {
                            Log.e(
                                    "BluetoothFunctionSettingsBase",
                                    "onClick :: mDialogCallback is null");
                            return;
                        }
                        Log.d("BluetoothIBRSettings", "onDialogNegativeClicked ::");
                        BluetoothIBRSettings bluetoothIBRSettings = BluetoothIBRSettings.this;
                        SALogging.insertSALog(
                                bluetoothIBRSettings.mScreenId,
                                bluetoothIBRSettings
                                        .getResources()
                                        .getString(
                                                R.string
                                                        .event_in_band_ringtone_setting_dialog_negative));
                        return;
                    }
                    if (i != -1) {
                        return;
                    }
                    BluetoothIBRSettings.AnonymousClass1 anonymousClass12 =
                            BluetoothFunctionSettingsBase.this.mDialogCallback;
                    if (anonymousClass12 == null) {
                        Log.e(
                                "BluetoothFunctionSettingsBase",
                                "onClick :: mDialogCallback is null");
                        return;
                    }
                    Log.d("BluetoothIBRSettings", "onDialogPositiveClicked ::");
                    BluetoothIBRSettings bluetoothIBRSettings2 = BluetoothIBRSettings.this;
                    SALogging.insertSALog(
                            bluetoothIBRSettings2.mScreenId,
                            bluetoothIBRSettings2
                                    .getResources()
                                    .getString(
                                            R.string
                                                    .event_in_band_ringtone_setting_dialog_positive));
                    int state = bluetoothIBRSettings2.mLocalBluetoothAdapter.mAdapter.getState();
                    if ((state == 11 || state == 12)
                            && bluetoothIBRSettings2.mLocalBluetoothAdapter.mAdapter.disable()) {
                        SharedPreferences.Editor edit =
                                bluetoothIBRSettings2
                                        .getContext()
                                        .getSharedPreferences("bluetooth_restart", 0)
                                        .edit();
                        edit.putBoolean("key_bluetooth_restart", true);
                        edit.apply();
                    }
                    bluetoothIBRSettings2.setInBandRingtone(
                            !BluetoothIBRSettings.isInBandRingtoneEnabled$1());
                }
            };
    public final AnonymousClass4 mCancelListener =
            new DialogInterface
                    .OnCancelListener() { // from class:
                                          // com.samsung.android.settings.bluetooth.BluetoothFunctionSettingsBase.4
                @Override // android.content.DialogInterface.OnCancelListener
                public final void onCancel(DialogInterface dialogInterface) {
                    BluetoothIBRSettings.AnonymousClass1 anonymousClass1 =
                            BluetoothFunctionSettingsBase.this.mDialogCallback;
                    if (anonymousClass1 == null) {
                        Log.e(
                                "BluetoothFunctionSettingsBase",
                                "onCancel :: mDialogCallback is null");
                    } else {
                        anonymousClass1.getClass();
                        Log.d("BluetoothIBRSettings", "onDialogCanceled ::");
                    }
                }
            };
    public final AnonymousClass5 mDismissListener =
            new DialogInterface
                    .OnDismissListener() { // from class:
                                           // com.samsung.android.settings.bluetooth.BluetoothFunctionSettingsBase.5
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    BluetoothFunctionSettingsBase.this.mDialogCallback = null;
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class FunctionEnabler implements CompoundButton.OnCheckedChangeListener {
        public boolean isSwitchListenerRegistered = false;
        public final SettingsMainSwitchBar mSwitchBar;

        public FunctionEnabler(SettingsMainSwitchBar settingsMainSwitchBar) {
            this.mSwitchBar = settingsMainSwitchBar;
        }

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            BluetoothFunctionSettingsBase.this.switchStateChange(z);
        }

        public final void resume() {
            SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
            if (settingsMainSwitchBar == null) {
                Log.e("BluetoothFunctionSettingsBase", "resume :: mSwitchBar is null");
            } else if (this.isSwitchListenerRegistered) {
                Log.e(
                        "BluetoothFunctionSettingsBase",
                        "resume :: Switch change listener is already registered");
            } else {
                settingsMainSwitchBar.addOnSwitchChangeListener(this);
                this.isSwitchListenerRegistered = true;
            }
        }
    }

    static {
        Debug.semIsProductDev();
    }

    public final AlertDialog.Builder getDialogBuilder() {
        if (this.mDialogBuilder == null) {
            this.mDialogBuilder = new AlertDialog.Builder(getContext());
        }
        return this.mDialogBuilder;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 24;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        SettingsMainSwitchBar settingsMainSwitchBar =
                (SettingsMainSwitchBar) getView().findViewById(R.id.switch_bar);
        this.mFunctionEnabler = new FunctionEnabler(settingsMainSwitchBar);
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.show();
        } else {
            Log.e("BluetoothFunctionSettingsBase", "setupSwitchBar :: mSwitchBar is null");
        }
        FrameLayout frameLayout = (FrameLayout) getView().findViewById(R.id.frame_layout);
        if (frameLayout != null) {
            frameLayout.semSetRoundedCorners(15);
            frameLayout.semSetRoundedCornerColor(
                    15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        }
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onBluetoothStateChanged(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i, "onBluetoothStateChanged :: state =", "BluetoothFunctionSettingsBase");
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Log.d("BluetoothFunctionSettingsBase", "onConfigurationChanged :: ");
        ViewGroup viewGroup = (ViewGroup) getView();
        viewGroup.removeAllViewsInLayout();
        LayoutInflater layoutInflater =
                (LayoutInflater) getContext().getSystemService("layout_inflater");
        if ((!Utils.isTablet() || Rune.isSamsungDexMode(getContext()))
                && configuration.orientation == 2) {
            layoutInflater.inflate(
                    R.layout.sec_bluetooth_function_settings_content_land, viewGroup);
        } else {
            layoutInflater.inflate(R.layout.sec_bluetooth_function_settings_content, viewGroup);
        }
        FrameLayout frameLayout = (FrameLayout) getView().findViewById(R.id.frame_layout);
        if (frameLayout != null) {
            frameLayout.semSetRoundedCorners(15);
            frameLayout.semSetRoundedCornerColor(
                    15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        }
        SettingsMainSwitchBar settingsMainSwitchBar =
                (SettingsMainSwitchBar) getView().findViewById(R.id.switch_bar);
        this.mFunctionEnabler = new FunctionEnabler(settingsMainSwitchBar);
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.show();
        } else {
            Log.e("BluetoothFunctionSettingsBase", "setupSwitchBar :: mSwitchBar is null");
        }
        this.mFunctionEnabler.resume();
        updateContentsView();
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onConnectionStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i,
                "onConnectionStateChanged :: connection state =",
                "BluetoothFunctionSettingsBase");
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mLocalBluetoothManager =
                LocalBluetoothManager.getInstance(
                        getActivity().getApplicationContext(),
                        com.android.settings.bluetooth.Utils.mOnInitCallback);
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Log.d("BluetoothFunctionSettingsBase", "onCreateView()");
        return ((!Utils.isTablet() || Rune.isSamsungDexMode(getContext()))
                        && getResources().getConfiguration().orientation == 2)
                ? layoutInflater.inflate(
                        R.layout.sec_bluetooth_function_settings_content_land, viewGroup, false)
                : layoutInflater.inflate(
                        R.layout.sec_bluetooth_function_settings_content, viewGroup, false);
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        AlertDialog alertDialog = this.mAlertDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        FunctionEnabler functionEnabler = this.mFunctionEnabler;
        if (functionEnabler != null) {
            SettingsMainSwitchBar settingsMainSwitchBar = functionEnabler.mSwitchBar;
            if (settingsMainSwitchBar != null) {
                settingsMainSwitchBar.hide();
            } else {
                Log.e("BluetoothFunctionSettingsBase", "teardownSwitchBar :: mSwitchBar is null");
            }
        }
        this.mAnimResource = null;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        if (localBluetoothManager != null) {
            localBluetoothManager.mEventManager.unregisterCallback(this);
        } else {
            Log.e(
                    "BluetoothFunctionSettingsBase",
                    "onPause :: mLocalBluetoothManager is null, can not unregister bluetooth"
                        + " callback");
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        StatusBarManager statusBarManager =
                (StatusBarManager) getContext().getSystemService("statusbar");
        if (statusBarManager != null) {
            statusBarManager.collapsePanels();
        }
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        if (localBluetoothManager != null) {
            localBluetoothManager.mEventManager.registerCallback(this);
        } else {
            Log.e(
                    "BluetoothFunctionSettingsBase",
                    "onResume :: mLocalBluetoothManager is null, can not register bluetooth"
                        + " callback");
        }
    }

    public abstract void switchStateChange(boolean z);

    public abstract void updateContentsView();

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onAudioModeChanged() {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceAdded(CachedBluetoothDevice cachedBluetoothDevice) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceDeleted(CachedBluetoothDevice cachedBluetoothDevice) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onScanningStateChanged(boolean z) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onActiveDeviceChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceBondStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i) {}
}
