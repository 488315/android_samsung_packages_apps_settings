package com.android.settings.connecteddevice.usb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbPortStatus;
import android.util.Log;

import com.android.settings.accounts.ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class UsbConnectionBroadcastReceiver extends BroadcastReceiver
        implements LifecycleObserver, OnResume, OnPause {
    public static final boolean DEBUG = Log.isLoggable("UsbBroadcastReceiver", 3);
    public boolean mConnected;
    public final Context mContext;
    public boolean mListeningToUsbEvents;
    public final UsbBackend mUsbBackend;
    public final UsbConnectionListener mUsbConnectionListener;
    public long mFunctions = 0;
    public int mDataRole = 0;
    public int mPowerRole = 0;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface UsbConnectionListener {
        void onUsbConnectionChanged(boolean z, long j, int i, int i2, boolean z2);
    }

    public UsbConnectionBroadcastReceiver(
            Context context, UsbConnectionListener usbConnectionListener, UsbBackend usbBackend) {
        this.mContext = context;
        this.mUsbConnectionListener = usbConnectionListener;
        this.mUsbBackend = usbBackend;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public final void onPause() {
        if (this.mListeningToUsbEvents) {
            this.mContext.unregisterReceiver(this);
            this.mListeningToUsbEvents = false;
        }
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        UsbPortStatus parcelable;
        if (DEBUG) {
            Log.d("UsbBroadcastReceiver", "onReceive() action : " + intent.getAction());
        }
        boolean z =
                intent.getExtras() != null ? intent.getExtras().getBoolean("configured") : false;
        if ("android.hardware.usb.action.USB_STATE".equals(intent.getAction())) {
            this.mConnected =
                    intent.getExtras().getBoolean("connected")
                            || intent.getExtras().getBoolean("host_connected");
            long j =
                    (intent.getExtras().getBoolean("mtp")
                                    && intent.getExtras().getBoolean("unlocked", false))
                            ? 4L
                            : 0L;
            if (intent.getExtras().getBoolean("ptp")
                    && intent.getExtras().getBoolean("unlocked", false)) {
                j |= 16;
            }
            if (intent.getExtras().getBoolean("midi")) {
                j |= 8;
            }
            if (intent.getExtras().getBoolean("rndis")) {
                j |= 32;
            }
            if (intent.getExtras().getBoolean("accessory")) {
                j |= 2;
            }
            if (intent.getExtras().getBoolean("ncm")) {
                j |= 1024;
            }
            if (intent.getExtras().getBoolean("uvc")) {
                j |= 128;
            }
            if (intent.getExtras().getBoolean("mass_storage")) {
                j |= 524288;
            }
            if (intent.getExtras().getBoolean("sec_charging")) {
                j |= 262144;
            }
            this.mFunctions = j;
            this.mDataRole = this.mUsbBackend.getDataRole();
            UsbBackend usbBackend = this.mUsbBackend;
            usbBackend.updatePorts();
            UsbPortStatus usbPortStatus = usbBackend.mPortStatus;
            this.mPowerRole = usbPortStatus != null ? usbPortStatus.getCurrentPowerRole() : 0;
        } else if ("android.hardware.usb.action.USB_PORT_CHANGED".equals(intent.getAction())
                && (parcelable = intent.getExtras().getParcelable("portStatus")) != null) {
            this.mDataRole = parcelable.getCurrentDataRole();
            this.mPowerRole = parcelable.getCurrentPowerRole();
        }
        UsbConnectionListener usbConnectionListener = this.mUsbConnectionListener;
        if (usbConnectionListener != null) {
            usbConnectionListener.onUsbConnectionChanged(
                    this.mConnected, this.mFunctions, this.mPowerRole, this.mDataRole, z);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public final void onResume() {
        register();
    }

    public final void register() {
        if (this.mListeningToUsbEvents) {
            return;
        }
        this.mConnected = false;
        Intent registerReceiver =
                this.mContext.registerReceiver(
                        this,
                        ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0.m(
                                "android.hardware.usb.action.USB_STATE",
                                "android.hardware.usb.action.USB_PORT_CHANGED"));
        if (registerReceiver != null) {
            onReceive(this.mContext, registerReceiver);
        }
        this.mListeningToUsbEvents = true;
    }
}
