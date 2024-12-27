package com.android.settings.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothLeAudio;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.fuelgauge.BatteryMeterView;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LeAudioProfile;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfile;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnDestroy;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class LeAudioBluetoothDetailsHeaderController extends BasePreferenceController
        implements LifecycleObserver, OnStart, OnStop, OnDestroy, CachedBluetoothDevice.Callback {
    static final int INVALID_RESOURCE_ID = -1;
    static final int LEFT_DEVICE_ID = 88413265;
    static final int RIGHT_DEVICE_ID = 176826530;
    private List<CachedBluetoothDevice> mAllOfCachedDevices;
    private CachedBluetoothDevice mCachedDevice;
    Handler mHandler;
    boolean mIsRegisterCallback;
    LayoutPreference mLayoutPreference;
    LocalBluetoothManager mManager;
    private LocalBluetoothProfileManager mProfileManager;
    private static final String TAG = "LeAudioBtHeaderCtrl";
    private static final boolean DEBUG = Log.isLoggable(TAG, 3);

    public LeAudioBluetoothDetailsHeaderController(Context context, String str) {
        super(context, str);
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mIsRegisterCallback = false;
    }

    private int getBatterySummaryResource(int i) {
        if (i == R.id.bt_battery_case) {
            return R.id.bt_battery_case_summary;
        }
        if (i == R.id.bt_battery_left) {
            return R.id.bt_battery_left_summary;
        }
        if (i == R.id.bt_battery_right) {
            return R.id.bt_battery_right_summary;
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i, "No summary resource id. The containerId is ", TAG);
        return -1;
    }

    private void hideAllOfBatteryLayouts() {
        updateBatteryLayout(R.id.bt_battery_case, -1);
        updateBatteryLayout(R.id.bt_battery_left, -1);
        updateBatteryLayout(R.id.bt_battery_right, -1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getAvailabilityStatus$0(
            LocalBluetoothProfile localBluetoothProfile) {
        return localBluetoothProfile.getProfileId() == 22;
    }

    private void updateBatteryLayout() {
        hideAllOfBatteryLayouts();
        LeAudioProfile leAudioProfile = this.mProfileManager.mLeAudioProfile;
        if (this.mAllOfCachedDevices.isEmpty()) {
            Log.e(TAG, "There is no LeAudioProfile.");
            return;
        }
        if (!leAudioProfile.isEnabled(this.mCachedDevice.mDevice)) {
            Log.d(TAG, "Show the legacy battery style if the LeAudio is not enabled.");
            TextView textView =
                    (TextView)
                            this.mLayoutPreference.mRootView.findViewById(
                                    R.id.entity_header_summary);
            if (textView != null) {
                textView.setText(this.mCachedDevice.getConnectionSummary());
                return;
            }
            return;
        }
        for (CachedBluetoothDevice cachedBluetoothDevice : this.mAllOfCachedDevices) {
            BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
            BluetoothLeAudio bluetoothLeAudio = leAudioProfile.mService;
            int audioLocation =
                    (bluetoothLeAudio == null || bluetoothDevice == null)
                            ? 0
                            : bluetoothLeAudio.getAudioLocation(bluetoothDevice);
            Log.d(
                    TAG,
                    "LeAudioDevices:"
                            + cachedBluetoothDevice.mDevice.getAnonymizedAddress()
                            + ", deviceId:"
                            + audioLocation);
            if (audioLocation == 0) {
                Log.d(TAG, "The device does not support the AUDIO_LOCATION.");
                return;
            }
            boolean z = (LEFT_DEVICE_ID & audioLocation) != 0;
            boolean z2 = (audioLocation & RIGHT_DEVICE_ID) != 0;
            if (z && z2) {
                Log.d(TAG, "Show the legacy battery style if the device id is left+right.");
                TextView textView2 =
                        (TextView)
                                this.mLayoutPreference.mRootView.findViewById(
                                        R.id.entity_header_summary);
                if (textView2 != null) {
                    textView2.setText(this.mCachedDevice.getConnectionSummary());
                }
            } else if (z) {
                updateBatteryLayout(
                        R.id.bt_battery_left, cachedBluetoothDevice.mDevice.getBatteryLevel());
            } else if (z2) {
                updateBatteryLayout(
                        R.id.bt_battery_right, cachedBluetoothDevice.mDevice.getBatteryLevel());
            } else {
                Log.d(TAG, "The device id is other Audio Location. Do nothing.");
            }
        }
    }

    public Drawable createBtBatteryIcon(Context context, int i) {
        BatteryMeterView.BatteryMeterDrawable batteryMeterDrawable =
                new BatteryMeterView.BatteryMeterDrawable(
                        context,
                        context.getColor(R.color.meter_background_color),
                        context.getResources()
                                .getDimensionPixelSize(
                                        R.dimen.advanced_bluetooth_battery_meter_width),
                        context.getResources()
                                .getDimensionPixelSize(
                                        R.dimen.advanced_bluetooth_battery_meter_height));
        batteryMeterDrawable.setBatteryLevel(i);
        batteryMeterDrawable.setColorFilter(
                new PorterDuffColorFilter(
                        com.android.settingslib.Utils.getColorAttrDefaultColor(
                                context, android.R.attr.colorControlNormal),
                        PorterDuff.Mode.SRC));
        return batteryMeterDrawable;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        LayoutPreference layoutPreference =
                (LayoutPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mLayoutPreference = layoutPreference;
        layoutPreference.setVisible(isAvailable());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        if (cachedBluetoothDevice == null || this.mProfileManager == null) {
            return 2;
        }
        return (BluetoothUtils.isAdvancedDetailsHeader(this.mCachedDevice.mDevice)
                        || !cachedBluetoothDevice.getConnectableProfiles().stream()
                                .anyMatch(
                                        new LeAudioBluetoothDetailsHeaderController$$ExternalSyntheticLambda0()))
                ? 2
                : 0;
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

    public void init(
            CachedBluetoothDevice cachedBluetoothDevice,
            LocalBluetoothManager localBluetoothManager) {
        this.mCachedDevice = cachedBluetoothDevice;
        this.mManager = localBluetoothManager;
        this.mProfileManager = localBluetoothManager.mProfileManager;
        this.mAllOfCachedDevices =
                Utils.getAllOfCachedBluetoothDevices(cachedBluetoothDevice, localBluetoothManager);
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

    @Override // com.android.settingslib.bluetooth.CachedBluetoothDevice.Callback
    public void onDeviceAttributesChanged() {
        Iterator<CachedBluetoothDevice> it = this.mAllOfCachedDevices.iterator();
        while (it.hasNext()) {
            it.next().unregisterCallback(this);
        }
        List<CachedBluetoothDevice> allOfCachedBluetoothDevices =
                Utils.getAllOfCachedBluetoothDevices(this.mCachedDevice, this.mManager);
        this.mAllOfCachedDevices = allOfCachedBluetoothDevices;
        Iterator<CachedBluetoothDevice> it2 = allOfCachedBluetoothDevices.iterator();
        while (it2.hasNext()) {
            it2.next().registerCallback(this);
        }
        if (this.mAllOfCachedDevices.isEmpty()) {
            return;
        }
        refresh();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        if (isAvailable()) {
            this.mIsRegisterCallback = true;
            Iterator<CachedBluetoothDevice> it = this.mAllOfCachedDevices.iterator();
            while (it.hasNext()) {
                it.next().registerCallback(this);
            }
            refresh();
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        if (this.mIsRegisterCallback) {
            Iterator<CachedBluetoothDevice> it = this.mAllOfCachedDevices.iterator();
            while (it.hasNext()) {
                it.next().unregisterCallback(this);
            }
            this.mIsRegisterCallback = false;
        }
    }

    public void refresh() {
        LayoutPreference layoutPreference = this.mLayoutPreference;
        if (layoutPreference == null || this.mCachedDevice == null) {
            return;
        }
        ImageView imageView =
                (ImageView) layoutPreference.mRootView.findViewById(R.id.entity_header_icon);
        if (imageView != null) {
            Pair btRainbowDrawableWithDescription =
                    BluetoothUtils.getBtRainbowDrawableWithDescription(
                            this.mContext, this.mCachedDevice);
            imageView.setImageDrawable((Drawable) btRainbowDrawableWithDescription.first);
            imageView.setContentDescription((CharSequence) btRainbowDrawableWithDescription.second);
        }
        TextView textView =
                (TextView) this.mLayoutPreference.mRootView.findViewById(R.id.entity_header_title);
        if (textView != null) {
            textView.setText(this.mCachedDevice.getName());
        }
        TextView textView2 =
                (TextView)
                        this.mLayoutPreference.mRootView.findViewById(R.id.entity_header_summary);
        if (textView2 != null) {
            textView2.setText(this.mCachedDevice.getConnectionSummary(true));
        }
        if (!this.mCachedDevice.isConnected() || this.mCachedDevice.isBusy()) {
            hideAllOfBatteryLayouts();
        } else {
            updateBatteryLayout();
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
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

    @Override // com.android.settingslib.core.lifecycle.events.OnDestroy
    public void onDestroy() {}

    private void updateBatteryLayout(int i, int i2) {
        View findViewById = this.mLayoutPreference.mRootView.findViewById(i);
        if (findViewById == null) {
            Log.e(TAG, "updateBatteryLayout: No View");
            return;
        }
        if (i2 == -1) {
            Log.d(TAG, "updateBatteryLayout: Hide it if it doesn't have battery information.");
            findViewById.setVisibility(8);
            return;
        }
        findViewById.setVisibility(0);
        TextView textView = (TextView) findViewById.requireViewById(getBatterySummaryResource(i));
        String formatPercentage = com.android.settingslib.Utils.formatPercentage(i2);
        textView.setText(formatPercentage);
        textView.setContentDescription(
                this.mContext.getString(R.string.bluetooth_battery_level, formatPercentage));
        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                createBtBatteryIcon(this.mContext, i2),
                (Drawable) null,
                (Drawable) null,
                (Drawable) null);
    }
}
