package com.android.settings.connecteddevice.stylus;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;

import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.BasePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class StylusUsbFirmwareController extends BasePreferenceController
        implements LifecycleObserver, OnStart, OnStop {
    private static final String TAG = "StylusUsbFirmwareController";
    private PreferenceCategory mPreference;
    private PreferenceScreen mPreferenceScreen;
    private UsbDevice mStylusUsbDevice;
    UsbStylusBroadcastReceiver.UsbStylusConnectionListener mUsbConnectionListener;
    private final UsbStylusBroadcastReceiver mUsbStylusBroadcastReceiver;

    public StylusUsbFirmwareController(Context context, String str) {
        super(context, str);
        this.mUsbConnectionListener =
                new StylusUsbFirmwareController$$ExternalSyntheticLambda0(this);
        UsbStylusBroadcastReceiver.UsbStylusConnectionListener usbStylusConnectionListener =
                this.mUsbConnectionListener;
        UsbStylusBroadcastReceiver usbStylusBroadcastReceiver = new UsbStylusBroadcastReceiver();
        usbStylusBroadcastReceiver.mContext = context;
        usbStylusBroadcastReceiver.mUsbConnectionListener = usbStylusConnectionListener;
        this.mUsbStylusBroadcastReceiver = usbStylusBroadcastReceiver;
    }

    private UsbDevice getStylusUsbDevice() {
        UsbManager usbManager = (UsbManager) this.mContext.getSystemService(UsbManager.class);
        if (usbManager == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(usbManager.getDeviceList().values());
        if (arrayList.isEmpty()) {
            return null;
        }
        UsbDevice usbDevice = (UsbDevice) arrayList.get(0);
        if (hasUsbStylusFirmwareUpdateFeature(usbDevice)) {
            return usbDevice;
        }
        return null;
    }

    public static boolean hasUsbStylusFirmwareUpdateFeature(UsbDevice usbDevice) {
        if (usbDevice == null) {
            return false;
        }
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        ((StylusFeatureProviderImpl) featureFactoryImpl.stylusFeatureProvider$delegate.getValue())
                .getClass();
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(UsbDevice usbDevice, boolean z) {
        refresh();
    }

    private void refresh() {
        UsbDevice stylusUsbDevice;
        if (this.mPreferenceScreen == null
                || (stylusUsbDevice = getStylusUsbDevice()) == this.mStylusUsbDevice) {
            return;
        }
        this.mStylusUsbDevice = stylusUsbDevice;
        PreferenceCategory preferenceCategory =
                (PreferenceCategory) this.mPreferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = preferenceCategory;
        if (preferenceCategory != null) {
            this.mPreferenceScreen.removePreference(preferenceCategory);
        }
        if (hasUsbStylusFirmwareUpdateFeature(this.mStylusUsbDevice)) {
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            ((StylusFeatureProviderImpl)
                            featureFactoryImpl.stylusFeatureProvider$delegate.getValue())
                    .getClass();
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        this.mPreferenceScreen = preferenceScreen;
        refresh();
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
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
        UsbStylusBroadcastReceiver usbStylusBroadcastReceiver = this.mUsbStylusBroadcastReceiver;
        if (usbStylusBroadcastReceiver.mListeningToUsbEvents) {
            return;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_ATTACHED");
        intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
        intentFilter.addAction("android.hardware.usb.action.USB_STATE");
        Intent registerReceiver =
                usbStylusBroadcastReceiver.mContext.registerReceiver(
                        usbStylusBroadcastReceiver, intentFilter);
        if (registerReceiver != null) {
            usbStylusBroadcastReceiver.onReceive(
                    usbStylusBroadcastReceiver.mContext, registerReceiver);
        }
        usbStylusBroadcastReceiver.mListeningToUsbEvents = true;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        UsbStylusBroadcastReceiver usbStylusBroadcastReceiver = this.mUsbStylusBroadcastReceiver;
        if (usbStylusBroadcastReceiver.mListeningToUsbEvents) {
            usbStylusBroadcastReceiver.mContext.unregisterReceiver(usbStylusBroadcastReceiver);
            usbStylusBroadcastReceiver.mListeningToUsbEvents = false;
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
}
