package com.android.settings.connecteddevice.usb;

import android.content.Context;
import android.os.Handler;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.wifi.dpp.WifiDppUtils;
import com.android.settingslib.core.AbstractPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class UsbDetailsController extends AbstractPreferenceController
        implements PreferenceControllerMixin {
    public final Context mContext;
    public final UsbDetailsFragment mFragment;
    Handler mHandler;
    public final UsbBackend mUsbBackend;

    public UsbDetailsController(
            Context context, UsbBackend usbBackend, UsbDetailsFragment usbDetailsFragment) {
        super(context);
        this.mContext = context;
        this.mFragment = usbDetailsFragment;
        this.mUsbBackend = usbBackend;
        this.mHandler = new Handler(context.getMainLooper());
    }

    public abstract void refresh(boolean z, long j, int i, int i2);

    public final void requireAuthAndExecute(final Runnable runnable) {
        if (this.mFragment.mUserAuthenticated) {
            runnable.run();
        } else {
            WifiDppUtils.showLockScreen(
                    this.mContext,
                    new Runnable() { // from class:
                                     // com.android.settings.connecteddevice.usb.UsbDetailsController$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            UsbDetailsController usbDetailsController = UsbDetailsController.this;
                            Runnable runnable2 = runnable;
                            usbDetailsController.mFragment.mUserAuthenticated = true;
                            runnable2.run();
                        }
                    });
        }
    }
}
