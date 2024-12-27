package com.android.settings.connecteddevice;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.BidiFormatter;
import android.text.TextUtils;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.bluetooth.AlwaysDiscoverable;
import com.android.settings.bluetooth.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;
import com.android.settingslib.widget.FooterPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DiscoverableFooterPreferenceController extends BasePreferenceController
        implements LifecycleObserver, OnStart, OnStop {
    private static final String KEY = "discoverable_footer_preference";
    private AlwaysDiscoverable mAlwaysDiscoverable;
    private BluetoothAdapter mBluetoothAdapter;
    BroadcastReceiver mBluetoothChangedReceiver;
    private boolean mIsAlwaysDiscoverable;
    LocalBluetoothManager mLocalManager;
    private FooterPreference mPreference;

    public DiscoverableFooterPreferenceController(Context context, String str) {
        super(context, str);
        LocalBluetoothManager localBluetoothManager =
                LocalBluetoothManager.getInstance(context, Utils.mOnInitCallback);
        this.mLocalManager = localBluetoothManager;
        if (localBluetoothManager == null) {
            return;
        }
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mAlwaysDiscoverable = new AlwaysDiscoverable(context);
        initReceiver();
    }

    private CharSequence getPreferenceTitle() {
        String name = this.mBluetoothAdapter.getName();
        if (TextUtils.isEmpty(name)) {
            return null;
        }
        return TextUtils.expandTemplate(
                this.mContext.getText(R.string.bluetooth_device_name_summary),
                BidiFormatter.getInstance().unicodeWrap(name));
    }

    private void initReceiver() {
        this.mBluetoothChangedReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.settings.connecteddevice.DiscoverableFooterPreferenceController.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        if (intent.getAction()
                                .equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
                            DiscoverableFooterPreferenceController.this.updateFooterPreferenceTitle(
                                    intent.getIntExtra(
                                            "android.bluetooth.adapter.extra.STATE",
                                            Integer.MIN_VALUE));
                        }
                    }
                };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateFooterPreferenceTitle(int i) {
        if (i == 12) {
            this.mPreference.setTitle(getPreferenceTitle());
        } else {
            this.mPreference.setTitle(R.string.bluetooth_off_footer);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = (FooterPreference) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mContext.getPackageManager().hasSystemFeature("android.hardware.bluetooth")
                ? 1
                : 3;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        if (this.mLocalManager == null) {
            return;
        }
        this.mContext.registerReceiver(
                this.mBluetoothChangedReceiver,
                new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
        if (this.mIsAlwaysDiscoverable) {
            this.mAlwaysDiscoverable.start();
        }
        updateFooterPreferenceTitle(this.mBluetoothAdapter.getState());
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        if (this.mLocalManager == null) {
            return;
        }
        this.mContext.unregisterReceiver(this.mBluetoothChangedReceiver);
        if (this.mIsAlwaysDiscoverable) {
            this.mAlwaysDiscoverable.stop();
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setAlwaysDiscoverable(boolean z) {
        this.mIsAlwaysDiscoverable = z;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
