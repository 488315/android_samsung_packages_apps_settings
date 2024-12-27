package com.android.settings.connecteddevice.usb;

import android.content.Context;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbPort;
import android.hardware.usb.UsbPortStatus;
import android.net.TetheringManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class UsbBackend {
    public final boolean mFileTransferRestricted;
    public final boolean mFileTransferRestrictedBySystem;
    public final boolean mIsAdminUser;
    public final boolean mMidiSupported;
    public UsbPort mPort;
    public UsbPortStatus mPortStatus;
    public final boolean mTetheringRestricted;
    public final boolean mTetheringRestrictedBySystem;
    public final boolean mTetheringSupported;
    public final boolean mUVCEnabled;
    public final UsbManager mUsbManager;

    public UsbBackend(Context context) {
        this(context, (UserManager) context.getSystemService("user"));
    }

    public final boolean areAllRolesSupported() {
        UsbPortStatus usbPortStatus;
        return this.mPort != null
                && (usbPortStatus = this.mPortStatus) != null
                && usbPortStatus.isRoleCombinationSupported(2, 2)
                && this.mPortStatus.isRoleCombinationSupported(2, 1)
                && this.mPortStatus.isRoleCombinationSupported(1, 2)
                && this.mPortStatus.isRoleCombinationSupported(1, 1);
    }

    public boolean areFunctionsDisallowedByNonAdminUser(long j) {
        return (this.mIsAdminUser || (j & 32) == 0) ? false : true;
    }

    public final boolean areFunctionsSupported(long j) {
        if ((!this.mMidiSupported && (8 & j) != 0)
                || (!this.mTetheringSupported && (j & 32) != 0)) {
            return false;
        }
        if (this.mFileTransferRestricted && ((j & 4) != 0 || (j & 16) != 0)) {
            return false;
        }
        if (this.mTetheringRestricted && (j & 32) != 0) {
            return false;
        }
        if (this.mFileTransferRestrictedBySystem && ((4 & j) != 0 || (16 & j) != 0)) {
            return false;
        }
        if (!this.mTetheringRestrictedBySystem || (32 & j) == 0) {
            return (this.mUVCEnabled || (128 & j) == 0) && !areFunctionsDisallowedByNonAdminUser(j);
        }
        return false;
    }

    public final int getDataRole() {
        updatePorts();
        UsbPortStatus usbPortStatus = this.mPortStatus;
        if (usbPortStatus == null) {
            return 0;
        }
        return usbPortStatus.getCurrentDataRole();
    }

    public final boolean supportUSBDebuggingMenu(Context context) {
        String str = SystemProperties.get("persist.sys.usb.config", ApnSettings.MVNO_NONE);
        String str2 = SystemProperties.get("sys.usb.config", ApnSettings.MVNO_NONE);
        String str3 = SystemProperties.get("sys.usb.state", ApnSettings.MVNO_NONE);
        int i = Settings.Global.getInt(context.getContentResolver(), "adb_enabled", 0);
        StringBuilder m =
                SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                        "supportUSBDebuggingMenu : ", str, " : ", str2, " : ");
        m.append(str3);
        m.append(" : ");
        m.append(i);
        Log.d("UsbBackend", m.toString());
        return "adb".equals(str)
                && "adb".equals(str2)
                && "adb".equals(str3)
                && i == 1
                && this.mUsbManager.getScreenUnlockedFunctions() == 0;
    }

    public final void updatePorts() {
        this.mPort = null;
        this.mPortStatus = null;
        List ports = this.mUsbManager.getPorts();
        int size = ports.size();
        for (int i = 0; i < size; i++) {
            UsbPortStatus status = ((UsbPort) ports.get(i)).getStatus();
            if (status.isConnected()) {
                this.mPort = (UsbPort) ports.get(i);
                this.mPortStatus = status;
                return;
            }
        }
    }

    public UsbBackend(Context context, UserManager userManager) {
        this.mUsbManager = (UsbManager) context.getSystemService(UsbManager.class);
        this.mFileTransferRestricted = userManager.hasUserRestriction("no_usb_file_transfer");
        this.mFileTransferRestrictedBySystem =
                userManager.hasBaseUserRestriction(
                        "no_usb_file_transfer", UserHandle.of(UserHandle.myUserId()));
        this.mTetheringRestricted = userManager.hasUserRestriction("no_config_tethering");
        this.mTetheringRestrictedBySystem =
                userManager.hasBaseUserRestriction(
                        "no_config_tethering", UserHandle.of(UserHandle.myUserId()));
        this.mUVCEnabled = UsbManager.isUvcSupportEnabled();
        this.mIsAdminUser = userManager.isAdminUser();
        this.mMidiSupported = context.getPackageManager().hasSystemFeature("android.software.midi");
        this.mTetheringSupported =
                ((TetheringManager) context.getSystemService(TetheringManager.class))
                        .isTetheringSupported();
        updatePorts();
    }
}
