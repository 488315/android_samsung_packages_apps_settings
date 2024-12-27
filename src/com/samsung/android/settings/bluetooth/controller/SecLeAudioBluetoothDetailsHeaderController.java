package com.samsung.android.settings.bluetooth.controller;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothLeAudio;
import android.bluetooth.BluetoothUuid;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceScreen;

import com.android.internal.util.ArrayUtils;
import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.Utils;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.HearingAidProfile;
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
import com.samsung.android.settingslib.bluetooth.SemBluetoothCallback;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecLeAudioBluetoothDetailsHeaderController extends BasePreferenceController
        implements LifecycleObserver,
                OnStart,
                OnStop,
                OnDestroy,
                CachedBluetoothDevice.Callback,
                SemBluetoothCallback {
    static final int INVALID_RESOURCE_ID = -1;
    static final int LEFT_DEVICE_ID = 88413265;
    static final int RIGHT_DEVICE_ID = 176826530;
    private boolean hasBatteryService;
    private boolean isBatterySupported;
    private List<CachedBluetoothDevice> mAllOfCachedDevices;
    private CachedBluetoothDevice mCachedDevice;
    Handler mHandler;
    boolean mIsRegisterCallback;
    LayoutPreference mLayoutPreference;
    LocalBluetoothManager mManager;
    private LocalBluetoothProfileManager mProfileManager;
    private static final String TAG = "SecLeAudioHaHeaderCtrl";
    private static final boolean DEBUG = Log.isLoggable(TAG, 3);

    public SecLeAudioBluetoothDetailsHeaderController(Context context, String str) {
        super(context, str);
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mIsRegisterCallback = false;
    }

    private int getBatteryProgressResource(int i) {
        if (i == R.id.ha_battery_left) {
            return R.id.sec_ha_progress_left;
        }
        if (i == R.id.ha_battery_right) {
            return R.id.sec_ha_progress_right;
        }
        return -1;
    }

    private int getBatterySummaryResource(int i) {
        if (i == R.id.ha_battery_left) {
            return R.id.bt_battery_summary_left;
        }
        if (i == R.id.ha_battery_right) {
            return R.id.bt_battery_summary_right;
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i, "No summary resource id. The containerId is ", TAG);
        return -1;
    }

    private int getBatteryTitleResource(int i) {
        if (i == R.id.ha_battery_left) {
            return R.id.battery_title_left;
        }
        if (i == R.id.ha_battery_right) {
            return R.id.battery_title_right;
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i, "No summary resource id. The containerId is ", TAG);
        return -1;
    }

    private int getHaIconResource(int i) {
        if (i == R.id.ha_battery_left) {
            return R.id.sec_ha_icon_left;
        }
        if (i == R.id.ha_battery_right) {
            return R.id.sec_ha_icon_right;
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onStart$0(Integer num) {
        return num.intValue() == 30;
    }

    private void manageNoBatteryLayout(boolean z) {
        View findViewById = this.mLayoutPreference.mRootView.findViewById(R.id.ha_battery);
        if (z) {
            findViewById.setVisibility(8);
        } else {
            findViewById.setVisibility(0);
        }
    }

    private void setBatteryArcProgress(int i, int i2) {
        ProgressBar progressBar;
        View findViewById = this.mLayoutPreference.mRootView.findViewById(i);
        if (findViewById == null
                || (progressBar =
                                (ProgressBar)
                                        findViewById.requireViewById(getBatteryProgressResource(i)))
                        == null) {
            return;
        }
        progressBar.setProgress(i2);
        if (i2 < 30) {
            progressBar.setProgressTintList(
                    this.mContext.getColorStateList(
                            R.color.sec_bluetooth_battery_progress_low_color));
        } else {
            progressBar.setProgressTintList(
                    this.mContext.getColorStateList(R.color.sec_bluetooth_battery_progress_color));
        }
    }

    private void setHeaderIcon(boolean z) {
        View findViewById = this.mLayoutPreference.mRootView.findViewById(R.id.header_icon_bg);
        ImageView imageView =
                (ImageView)
                        this.mLayoutPreference.mRootView.findViewById(R.id.sec_bt_ha_header_icon);
        if (findViewById != null) {
            if (z) {
                findViewById.setBackground(
                        this.mContext.getResources().getDrawable(R.drawable.ha_bg));
            } else {
                findViewById.setBackground(
                        this.mContext.getResources().getDrawable(R.drawable.ha_bg_disconnect));
            }
        }
        if (imageView != null) {
            if (z) {
                imageView.setColorFilter(
                        this.mContext.getColor(
                                R.color.sec_bluetooth_header_icon_connected_bg_color),
                        PorterDuff.Mode.SRC_IN);
            } else {
                imageView.setColorFilter(
                        this.mContext.getColor(
                                R.color.sec_bluetooth_header_icon_disconnected_bg_color),
                        PorterDuff.Mode.SRC_IN);
            }
        }
    }

    private void updateBatteryLayout() {
        int deviceSide;
        if (this.mAllOfCachedDevices.isEmpty()) {
            Log.w(TAG, "updateBatteryLayout no devices");
            return;
        }
        for (CachedBluetoothDevice cachedBluetoothDevice : this.mAllOfCachedDevices) {
            LeAudioProfile leAudioProfile = this.mProfileManager.mLeAudioProfile;
            if (leAudioProfile != null) {
                BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
                BluetoothLeAudio bluetoothLeAudio = leAudioProfile.mService;
                deviceSide = 0;
                int audioLocation =
                        (bluetoothLeAudio == null || bluetoothDevice == null)
                                ? 0
                                : bluetoothLeAudio.getAudioLocation(bluetoothDevice);
                if ((LEFT_DEVICE_ID & audioLocation) == 0) {
                    deviceSide = (audioLocation & RIGHT_DEVICE_ID) != 0 ? 1 : -1;
                }
            } else {
                deviceSide = cachedBluetoothDevice.getDeviceSide();
            }
            Log.d(
                    TAG,
                    "LeAudioDevices:"
                            + cachedBluetoothDevice.mDevice.getAnonymizedAddress()
                            + ", deviceSide:"
                            + deviceSide);
            if (deviceSide == 0) {
                updateBatteryLayout(
                        R.id.ha_battery_left, cachedBluetoothDevice.mDevice.getBatteryLevel());
            } else if (deviceSide == 1) {
                updateBatteryLayout(
                        R.id.ha_battery_right, cachedBluetoothDevice.mDevice.getBatteryLevel());
            } else {
                Log.d(TAG, "The device id is other Audio Location. Do nothing.");
            }
        }
    }

    private void updateConnectLayout(int i, int i2) {
        View findViewById = this.mLayoutPreference.mRootView.findViewById(i);
        if (findViewById == null) {
            return;
        }
        setBatteryArcProgress(i, i2);
        ImageView imageView = (ImageView) findViewById.requireViewById(getHaIconResource(i));
        if (imageView != null) {
            imageView.getDrawable().setAlpha(255);
        }
        TextView textView = (TextView) findViewById.requireViewById(getBatteryTitleResource(i));
        if (textView != null) {
            textView.setAlpha(1.0f);
        }
        TextView textView2 = (TextView) findViewById.requireViewById(getBatterySummaryResource(i));
        if (textView2 != null) {
            textView2.setVisibility(0);
            textView2.setText(Utils.formatPercentage(i2));
        }
    }

    private void updateDisconnectLayout() {
        updateDisconnectLayout(R.id.ha_battery_left);
        updateDisconnectLayout(R.id.ha_battery_right);
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
        return (cachedBluetoothDevice == null
                        || this.mProfileManager == null
                        || BluetoothUtils.isAdvancedDetailsHeader(cachedBluetoothDevice.mDevice))
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
                com.android.settings.bluetooth.Utils.getAllOfCachedBluetoothDevices(
                        cachedBluetoothDevice, localBluetoothManager);
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
                com.android.settings.bluetooth.Utils.getAllOfCachedBluetoothDevices(
                        this.mCachedDevice, this.mManager);
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

    @Override // com.samsung.android.settingslib.bluetooth.SemBluetoothCallback
    public void onProfileStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice,
            LocalBluetoothProfile localBluetoothProfile,
            int i,
            int i2) {
        if (this.mAllOfCachedDevices.contains(cachedBluetoothDevice)) {
            return;
        }
        if (((localBluetoothProfile instanceof LeAudioProfile)
                        || (localBluetoothProfile instanceof HearingAidProfile))
                && i == 2) {
            onDeviceAttributesChanged();
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        if (isAvailable()) {
            this.mIsRegisterCallback = true;
            Iterator<CachedBluetoothDevice> it = this.mAllOfCachedDevices.iterator();
            while (it.hasNext()) {
                it.next().registerCallback(this);
            }
            LocalBluetoothManager localBluetoothManager = this.mManager;
            if (localBluetoothManager != null) {
                localBluetoothManager.mEventManager.registerSemCallback(this);
            }
            this.isBatterySupported =
                    ArrayUtils.contains(
                            this.mCachedDevice.mDevice.getUuids(), BluetoothUuid.BATTERY);
            this.hasBatteryService =
                    BluetoothAdapter.getDefaultAdapter().getSupportedProfiles().stream()
                            .anyMatch(
                                    new SecLeAudioBluetoothDetailsHeaderController$$ExternalSyntheticLambda0());
            Log.d(
                    TAG,
                    "isBatterySupported : "
                            + this.isBatterySupported
                            + ", hasBatteryService : "
                            + this.hasBatteryService);
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
            LocalBluetoothManager localBluetoothManager = this.mManager;
            if (localBluetoothManager != null) {
                localBluetoothManager.mEventManager.unregisterSemCallback(this);
            }
            this.mIsRegisterCallback = false;
        }
    }

    public void refresh() {
        TextView textView =
                (TextView)
                        this.mLayoutPreference.mRootView.findViewById(R.id.sec_bt_ha_header_title);
        if (textView != null) {
            textView.setText(this.mCachedDevice.getName());
        }
        r1 = null;
        for (CachedBluetoothDevice cachedBluetoothDevice : this.mCachedDevice.mMemberDevices) {}
        TextView textView2 =
                (TextView)
                        this.mLayoutPreference.mRootView.findViewById(
                                R.id.sec_bt_ha_connection_state);
        if (textView2 != null) {
            if (this.mCachedDevice.isConnected()) {
                setHeaderIcon(true);
                textView2.setText(this.mContext.getString(R.string.sec_bluetooth_connected));
            } else if (cachedBluetoothDevice == null || !cachedBluetoothDevice.isConnected()) {
                setHeaderIcon(false);
                textView2.setText(this.mContext.getString(R.string.bluetooth_disconnected));
            } else {
                setHeaderIcon(true);
                textView2.setText(this.mContext.getString(R.string.sec_bluetooth_connected));
            }
        }
        if (!this.hasBatteryService || !this.isBatterySupported) {
            manageNoBatteryLayout(true);
            return;
        }
        manageNoBatteryLayout(false);
        updateDisconnectLayout();
        updateBatteryLayout();
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

    private void updateDisconnectLayout(int i) {
        View findViewById = this.mLayoutPreference.mRootView.findViewById(i);
        if (findViewById == null) {
            return;
        }
        ProgressBar progressBar =
                (ProgressBar) findViewById.requireViewById(getBatteryProgressResource(i));
        if (progressBar != null) {
            progressBar.setProgress(0);
        }
        ImageView imageView = (ImageView) findViewById.requireViewById(getHaIconResource(i));
        if (imageView != null) {
            imageView.getDrawable().setAlpha(114);
        }
        TextView textView = (TextView) findViewById.requireViewById(getBatteryTitleResource(i));
        if (textView != null) {
            textView.setAlpha(0.45f);
        }
        TextView textView2 = (TextView) findViewById.requireViewById(getBatterySummaryResource(i));
        if (textView2 != null) {
            textView2.setVisibility(8);
        }
    }

    private void updateBatteryLayout(int i, int i2) {
        if (this.mLayoutPreference.mRootView.findViewById(i) == null) {
            return;
        }
        if (i2 != -1) {
            updateConnectLayout(i, i2);
        } else {
            Log.d(
                    TAG,
                    "updateBatteryLayout: Show disconnected battery layout if it doesn't have"
                        + " battery information.");
            updateDisconnectLayout(i);
        }
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}

    @Override // com.android.settingslib.core.lifecycle.events.OnDestroy
    public void onDestroy() {}

    @Override // com.samsung.android.settingslib.bluetooth.SemBluetoothCallback
    public void onResourceUpdated() {}

    @Override // com.samsung.android.settingslib.bluetooth.SemBluetoothCallback
    public void onSyncDeviceAdded(CachedBluetoothDevice cachedBluetoothDevice) {}
}
