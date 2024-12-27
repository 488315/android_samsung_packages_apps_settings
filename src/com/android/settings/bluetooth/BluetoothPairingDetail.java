package com.android.settings.bluetooth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.satellite.SatelliteManager;
import android.util.Log;
import android.view.View;

import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.glance.session.SessionManagerImpl$scope$1$$ExternalSyntheticOutline0;
import androidx.lifecycle.LifecycleCoroutineScopeImpl;

import com.android.settings.R;
import com.android.settings.network.SatelliteRepository$requestIsEnabled$1;
import com.android.settings.network.SatelliteWarningDialogActivity;
import com.android.settingslib.bluetooth.BluetoothDeviceFilter;
import com.android.settingslib.widget.FooterPreference;

import com.google.common.util.concurrent.Futures;

import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.BuildersKt;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BluetoothPairingDetail extends BluetoothDevicePairingDetailBase {
    static final String KEY_AVAIL_DEVICES = "available_devices";
    static final String KEY_FOOTER_PREF = "footer_preference";
    AlwaysDiscoverable mAlwaysDiscoverable;
    FooterPreference mFooterPreference;

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "BluetoothPairingDetail";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1018;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.bluetooth_pairing_detail;
    }

    @Override // com.android.settings.bluetooth.BluetoothDevicePairingDetailBase,
              // com.android.settings.bluetooth.DeviceListPreferenceFragment
    public final void initPreferencesFromPreferenceScreen() {
        super.initPreferencesFromPreferenceScreen();
        FooterPreference footerPreference = (FooterPreference) findPreference(KEY_FOOTER_PREF);
        this.mFooterPreference = footerPreference;
        footerPreference.setSelectable(false);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        boolean z;
        Future future;
        super.onAttach(context);
        Context context2 = getContext();
        Intrinsics.checkNotNullParameter(context2, "context");
        boolean z2 = true;
        try {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Intrinsics.checkNotNullParameter(executor, "executor");
            SatelliteManager satelliteManager =
                    (SatelliteManager) context2.getSystemService(SatelliteManager.class);
            if (satelliteManager == null) {
                Log.w("SatelliteRepository", "SatelliteManager is null");
                future = Futures.immediateFuture(Boolean.FALSE);
            } else {
                future =
                        CallbackToFutureAdapter.getFuture(
                                new SatelliteRepository$requestIsEnabled$1(
                                        satelliteManager, executor));
            }
            z = ((Boolean) future.get(3000L, TimeUnit.MILLISECONDS)).booleanValue();
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            SessionManagerImpl$scope$1$$ExternalSyntheticOutline0.m(
                    "Error to get satellite status : ", e, "BluetoothPairingDetail");
            z = true;
        }
        if (z) {
            startActivity(
                    new Intent(getContext(), (Class<?>) SatelliteWarningDialogActivity.class)
                            .putExtra("extra_type_of_satellite_warning_dialog", 1));
        } else {
            z2 = false;
        }
        if (z2) {
            finish();
        } else {
            ((BluetoothDeviceRenamePreferenceController)
                            use(BluetoothDeviceRenamePreferenceController.class))
                    .setFragment(this);
        }
    }

    @Override // com.android.settings.bluetooth.DeviceListPreferenceFragment,
              // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onScanningStateChanged(boolean z) {
        super.onScanningStateChanged(z);
        boolean z2 = z | this.mScanEnabled;
        BluetoothProgressCategory bluetoothProgressCategory = this.mAvailableDevicesCategory;
        bluetoothProgressCategory.mProgress = z2;
        bluetoothProgressCategory.notifyChanged();
    }

    @Override // com.android.settings.bluetooth.BluetoothDevicePairingDetailBase,
              // com.android.settings.bluetooth.DeviceListPreferenceFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        BluetoothProgressCategory bluetoothProgressCategory = this.mAvailableDevicesCategory;
        bluetoothProgressCategory.mProgress = this.mBluetoothAdapter.isDiscovering();
        bluetoothProgressCategory.notifyChanged();
    }

    @Override // com.android.settings.bluetooth.BluetoothDevicePairingDetailBase,
              // com.android.settings.bluetooth.DeviceListPreferenceFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        this.mAlwaysDiscoverable.stop();
    }

    @Override // com.android.settings.bluetooth.BluetoothDevicePairingDetailBase,
              // com.android.settings.bluetooth.DeviceListPreferenceFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.mAlwaysDiscoverable = new AlwaysDiscoverable(getContext());
    }

    @Override // com.android.settings.bluetooth.BluetoothDevicePairingDetailBase
    public void updateContent(int i) {
        super.updateContent(i);
        if (i == 12) {
            if (this.mInitialScanStarted) {
                BluetoothDeviceFilter.AllFilter allFilter =
                        BluetoothDeviceFilter.UNBONDED_DEVICE_FILTER;
                LifecycleCoroutineScopeImpl lifecycleCoroutineScopeImpl = this.lifecycleScope;
                if (lifecycleCoroutineScopeImpl != null) {
                    BuildersKt.launch$default(
                            lifecycleCoroutineScopeImpl,
                            null,
                            null,
                            new DeviceListPreferenceFragment$addCachedDevices$1(
                                    this, allFilter, null),
                            3);
                }
            }
            updateFooterPreference(this.mFooterPreference);
            this.mAlwaysDiscoverable.start();
        }
    }
}
