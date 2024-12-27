package com.android.settings.connecteddevice.stylus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class UsbStylusBroadcastReceiver extends BroadcastReceiver {
    public Context mContext;
    public boolean mListeningToUsbEvents;
    public UsbStylusConnectionListener mUsbConnectionListener;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface UsbStylusConnectionListener {}

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        UsbDevice usbDevice = (UsbDevice) intent.getParcelableExtra("device", UsbDevice.class);
        if (StylusUsbFirmwareController.hasUsbStylusFirmwareUpdateFeature(usbDevice)) {
            UsbStylusConnectionListener usbStylusConnectionListener = this.mUsbConnectionListener;
            ((StylusUsbFirmwareController$$ExternalSyntheticLambda0) usbStylusConnectionListener)
                    .f$0.lambda$new$0(
                            usbDevice,
                            intent.getAction()
                                    .equals("android.hardware.usb.action.USB_DEVICE_ATTACHED"));
        }
    }
}
