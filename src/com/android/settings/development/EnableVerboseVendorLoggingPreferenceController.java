package com.android.settings.development;

import android.hardware.dumpstate.IDumpstateDevice;
import android.hardware.dumpstate.V1_1.IDumpstateDevice$Proxy;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.glance.session.SessionManagerImpl$scope$1$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;
import com.android.settingslib.utils.ThreadUtils;

import java.util.NoSuchElementException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class EnableVerboseVendorLoggingPreferenceController
        extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    public static final boolean DBG =
            Log.isLoggable("EnableVerboseVendorLoggingPreferenceController", 3);
    public static final String DUMP_STATE_AIDL_SERVICE_NAME =
            ComponentActivity$1$$ExternalSyntheticOutline0.m(
                    new StringBuilder(), IDumpstateDevice.DESCRIPTOR, "/default");
    public int mDumpstateHalVersion;

    public IDumpstateDevice getDumpstateDeviceAidlService() {
        IDumpstateDevice iDumpstateDevice;
        IDumpstateDevice iDumpstateDevice2 = null;
        try {
            IBinder waitForDeclaredService =
                    ServiceManager.waitForDeclaredService(DUMP_STATE_AIDL_SERVICE_NAME);
            int i = IDumpstateDevice.Stub.$r8$clinit;
            if (waitForDeclaredService != null) {
                IInterface queryLocalInterface =
                        waitForDeclaredService.queryLocalInterface(IDumpstateDevice.DESCRIPTOR);
                if (queryLocalInterface == null
                        || !(queryLocalInterface instanceof IDumpstateDevice)) {
                    IDumpstateDevice.Stub.Proxy proxy = new IDumpstateDevice.Stub.Proxy();
                    proxy.mRemote = waitForDeclaredService;
                    iDumpstateDevice = proxy;
                } else {
                    iDumpstateDevice = (IDumpstateDevice) queryLocalInterface;
                }
                iDumpstateDevice2 = iDumpstateDevice;
            }
        } catch (NoSuchElementException e) {
            if (DBG) {
                Log.e(
                        "EnableVerboseVendorLoggingPreferenceController",
                        "Get AIDL service fail: " + e);
            }
        }
        if (iDumpstateDevice2 != null) {
            this.mDumpstateHalVersion = 2;
        }
        return iDumpstateDevice2;
    }

    public android.hardware.dumpstate.V1_0.IDumpstateDevice getDumpstateDeviceService() {
        boolean z = DBG;
        android.hardware.dumpstate.V1_0.IDumpstateDevice iDumpstateDevice = null;
        try {
            iDumpstateDevice = IDumpstateDevice$Proxy.getService();
            this.mDumpstateHalVersion = 1;
        } catch (RemoteException | NoSuchElementException e) {
            if (z) {
                SessionManagerImpl$scope$1$$ExternalSyntheticOutline0.m(
                        "Get HIDL v1.1 service fail: ",
                        e,
                        "EnableVerboseVendorLoggingPreferenceController");
            }
        }
        if (iDumpstateDevice == null) {
            try {
                iDumpstateDevice = android.hardware.dumpstate.V1_0.IDumpstateDevice.getService();
                this.mDumpstateHalVersion = 0;
            } catch (RemoteException | NoSuchElementException e2) {
                if (z) {
                    SessionManagerImpl$scope$1$$ExternalSyntheticOutline0.m(
                            "Get HIDL v1.0 service fail: ",
                            e2,
                            "EnableVerboseVendorLoggingPreferenceController");
                }
            }
        }
        if (iDumpstateDevice == null) {
            this.mDumpstateHalVersion = -1;
        }
        return iDumpstateDevice;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "enable_verbose_vendor_logging";
    }

    public boolean getVerboseLoggingEnabled() {
        IDumpstateDevice dumpstateDeviceAidlService = getDumpstateDeviceAidlService();
        boolean z = DBG;
        if (dumpstateDeviceAidlService != null) {
            try {
                return ((IDumpstateDevice.Stub.Proxy) dumpstateDeviceAidlService)
                        .getVerboseLoggingEnabled();
            } catch (RemoteException e) {
                if (z) {
                    Log.e(
                            "EnableVerboseVendorLoggingPreferenceController",
                            "aidlService.getVerboseLoggingEnabled fail: " + e);
                }
            }
        }
        android.hardware.dumpstate.V1_0.IDumpstateDevice dumpstateDeviceService =
                this.getDumpstateDeviceService();
        if (dumpstateDeviceService == null || this.mDumpstateHalVersion < 1) {
            if (z) {
                Log.d(
                        "EnableVerboseVendorLoggingPreferenceController",
                        "getVerboseLoggingEnabled not supported.");
            }
            return false;
        }
        try {
            return ((IDumpstateDevice$Proxy) dumpstateDeviceService).getVerboseLoggingEnabled();
        } catch (RemoteException | RuntimeException e2) {
            if (z) {
                SessionManagerImpl$scope$1$$ExternalSyntheticOutline0.m(
                        "HIDL v1.1 getVerboseLoggingEnabled fail: ",
                        e2,
                        "EnableVerboseVendorLoggingPreferenceController");
            }
            return false;
        }
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return isIDumpstateDeviceAidlServiceAvailable() || isIDumpstateDeviceV1_1ServiceAvailable();
    }

    public boolean isIDumpstateDeviceAidlServiceAvailable() {
        return getDumpstateDeviceAidlService() != null;
    }

    public boolean isIDumpstateDeviceV1_1ServiceAvailable() {
        android.hardware.dumpstate.V1_0.IDumpstateDevice dumpstateDeviceService =
                getDumpstateDeviceService();
        if (dumpstateDeviceService == null && DBG) {
            Log.d(
                    "EnableVerboseVendorLoggingPreferenceController",
                    "IDumpstateDevice v1.1 service is not available.");
        }
        return dumpstateDeviceService != null && this.mDumpstateHalVersion == 1;
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        ThreadUtils.postOnBackgroundThread(
                new EnableVerboseVendorLoggingPreferenceController$$ExternalSyntheticLambda1(
                        this, 0));
        ((TwoStatePreference) this.mPreference).setChecked(false);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        ThreadUtils.postOnBackgroundThread(
                new EnableVerboseVendorLoggingPreferenceController$$ExternalSyntheticLambda0(
                        this, ((Boolean) obj).booleanValue(), 0));
        return true;
    }

    public void setVerboseLoggingEnabled(boolean z) {
        IDumpstateDevice dumpstateDeviceAidlService = getDumpstateDeviceAidlService();
        boolean z2 = DBG;
        if (dumpstateDeviceAidlService != null) {
            try {
                ((IDumpstateDevice.Stub.Proxy) dumpstateDeviceAidlService)
                        .setVerboseLoggingEnabled(z);
            } catch (RemoteException e) {
                if (z2) {
                    Log.e(
                            "EnableVerboseVendorLoggingPreferenceController",
                            "aidlService.setVerboseLoggingEnabled fail: " + e);
                }
            }
        }
        android.hardware.dumpstate.V1_0.IDumpstateDevice dumpstateDeviceService =
                getDumpstateDeviceService();
        if (dumpstateDeviceService == null || this.mDumpstateHalVersion < 1) {
            if (z2) {
                Log.d(
                        "EnableVerboseVendorLoggingPreferenceController",
                        "setVerboseLoggingEnabled not supported.");
            }
        } else {
            try {
                ((IDumpstateDevice$Proxy) dumpstateDeviceService).setVerboseLoggingEnabled(z);
            } catch (RemoteException | RuntimeException e2) {
                if (z2) {
                    SessionManagerImpl$scope$1$$ExternalSyntheticOutline0.m(
                            "HIDL v1.1 setVerboseLoggingEnabled fail: ",
                            e2,
                            "EnableVerboseVendorLoggingPreferenceController");
                }
            }
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        ThreadUtils.postOnBackgroundThread(
                new EnableVerboseVendorLoggingPreferenceController$$ExternalSyntheticLambda1(
                        this, 1));
    }
}
