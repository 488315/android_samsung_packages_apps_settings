package com.android.settings.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.location.BluetoothScanningFragment;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.widget.SwitchWidgetController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;
import com.android.settingslib.widget.FooterPreference;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothSwitchPreferenceController
        implements LifecycleObserver,
                OnStart,
                OnStop,
                SwitchWidgetController.OnSwitchChangeListener,
                View.OnClickListener {
    AlwaysDiscoverable mAlwaysDiscoverable;
    public final BluetoothAdapter mBluetoothAdapter;
    public final BluetoothEnabler mBluetoothEnabler;
    public final Context mContext;
    public final FooterPreference mFooterPreference;
    public boolean mIsAlwaysDiscoverable;
    public final SwitchWidgetController mSwitch;

    public BluetoothSwitchPreferenceController(
            Context context,
            RestrictionUtils restrictionUtils,
            SwitchWidgetController switchWidgetController,
            FooterPreference footerPreference) {
        this.mSwitch = switchWidgetController;
        this.mContext = context;
        this.mFooterPreference = footerPreference;
        switchWidgetController.setupView();
        updateText(switchWidgetController.isChecked());
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl.getMetricsFeatureProvider();
        this.mBluetoothEnabler = new BluetoothEnabler();
        this.mAlwaysDiscoverable = new AlwaysDiscoverable(context);
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = BluetoothScanningFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = 1390;
        subSettingLauncher.launch();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public final void onStart() {
        this.mBluetoothEnabler.resume(this.mContext);
        if (this.mIsAlwaysDiscoverable) {
            this.mAlwaysDiscoverable.start();
        }
        SwitchWidgetController switchWidgetController = this.mSwitch;
        if (switchWidgetController != null) {
            updateText(switchWidgetController.isChecked());
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public final void onStop() {
        this.mBluetoothEnabler.pause();
        if (this.mIsAlwaysDiscoverable) {
            this.mAlwaysDiscoverable.stop();
        }
    }

    @Override // com.android.settings.widget.SwitchWidgetController.OnSwitchChangeListener
    public final boolean onSwitchToggled(boolean z) {
        updateText(z);
        return true;
    }

    public void updateText(boolean z) {
        boolean z2 = false;
        if (!z) {
            Context context = this.mContext;
            boolean z3 = Utils.DEBUG;
            if (Settings.Global.getInt(context.getContentResolver(), "ble_scan_always_enabled", 0)
                    == 1) {
                BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
                if (bluetoothAdapter != null) {
                    try {
                        z2 = bluetoothAdapter.isAutoOnSupported();
                    } catch (Exception e) {
                        Log.e(
                                "BluetoothSwitchPrefCtrl",
                                "Error calling isAutoOnFeatureAvailable()",
                                e);
                    }
                }
                if (z2) {
                    this.mFooterPreference.setTitle(
                            R.string.bluetooth_scanning_on_info_message_auto_on_available);
                } else {
                    this.mFooterPreference.setTitle(R.string.bluetooth_scanning_on_info_message);
                }
                this.mFooterPreference.setLearnMoreText(
                        this.mContext.getString(R.string.bluetooth_scan_change));
                this.mFooterPreference.setLearnMoreAction(
                        new View
                                .OnClickListener() { // from class:
                                                     // com.android.settings.bluetooth.BluetoothSwitchPreferenceController$$ExternalSyntheticLambda0
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                BluetoothSwitchPreferenceController.this.onClick(view);
                            }
                        });
                return;
            }
        }
        BluetoothAdapter bluetoothAdapter2 = this.mBluetoothAdapter;
        if (bluetoothAdapter2 != null) {
            try {
                z2 = bluetoothAdapter2.isAutoOnSupported();
            } catch (Exception e2) {
                Log.e("BluetoothSwitchPrefCtrl", "Error calling isAutoOnFeatureAvailable()", e2);
            }
        }
        if (z2) {
            this.mFooterPreference.setTitle(
                    R.string.bluetooth_empty_list_bluetooth_off_auto_on_available);
        } else {
            this.mFooterPreference.setTitle(R.string.bluetooth_empty_list_bluetooth_off);
        }
        this.mFooterPreference.setLearnMoreText(ApnSettings.MVNO_NONE);
        this.mFooterPreference.setLearnMoreAction(null);
    }
}
