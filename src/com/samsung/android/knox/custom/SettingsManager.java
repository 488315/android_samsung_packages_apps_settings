package com.samsung.android.knox.custom;

import android.hardware.usb.UsbDevice;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.license.EnterpriseLicenseManager;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SettingsManager {
    public static final String TAG = "SettingsManager";
    public static ContextInfo sContextInfo;
    public static SettingsManager sSettingsManager;
    public IKnoxCustomManager mService;

    public static synchronized SettingsManager getInstance() {
        SettingsManager settingsManager;
        synchronized (SettingsManager.class) {
            try {
                if (sSettingsManager == null) {
                    sSettingsManager = new SettingsManager();
                }
                if (sContextInfo == null) {
                    sContextInfo = new ContextInfo();
                }
                settingsManager = sSettingsManager;
            } catch (Throwable th) {
                throw th;
            }
        }
        return settingsManager;
    }

    public boolean addRoleHolder(String str, String str2) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.addRoleHolder(str, str2);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return false;
        }
    }

    public int clearForcedDisplaySizeDensity() {
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_3_7)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.clearForcedDisplaySizeDensity();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public boolean getAirGestureOptionState(int i) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.getAirGestureOptionState(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return true;
        }
    }

    public boolean getBackupRestoreState(int i) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.getBackupRestoreState(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return false;
        }
    }

    public int getBluetoothVisibilityTimeout() {
        return 0;
    }

    public boolean getChargingLEDState() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.getChargingLEDState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return false;
        }
    }

    public int getEthernetConfigurationType() {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getEthernetConfigurationType();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return 0;
        }
    }

    public boolean getEthernetState() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.getEthernetState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return true;
        }
    }

    public boolean getForceSingleView() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.getForceSingleView();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return false;
        }
    }

    public boolean getLTESettingState() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.getLTESettingState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return false;
        }
    }

    public boolean getMotionControlState(int i) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.getMotionControlState(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return false;
        }
    }

    public boolean getPackageVerifierState() {
        return true;
    }

    public int getPowerSavingMode() {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getPowerSavingMode();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return 0;
        }
    }

    public boolean getProtectBatteryState() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.getProtectBatteryState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return false;
        }
    }

    public List<String> getRoleHolders(String str) {
        if (getService() != null) {
            try {
                return this.mService.getRoleHolders(str);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            }
        }
        return new ArrayList();
    }

    public boolean getScreenWakeupOnPowerState() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.getScreenWakeupOnPowerState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return true;
        }
    }

    public final IKnoxCustomManager getService() {
        if (this.mService == null) {
            this.mService =
                    IKnoxCustomManager.Stub.asInterface(ServiceManager.getService("knoxcustom"));
        }
        return this.mService;
    }

    public int getSettingsHiddenState() {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getSettingsHiddenState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return 0;
        }
    }

    public boolean getWifiConnectionMonitorState() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.getWifiConnectionMonitorState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return true;
        }
    }

    public int getWifiFrequencyBand() {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getWifiFrequencyBand();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return 0;
        }
    }

    public boolean removeRoleHolder(String str, String str2) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.removeRoleHolder(str, str2);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return false;
        }
    }

    public int setAdbState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "SettingsManager.setAdbState");
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_1)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setAdbState(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setAirGestureOptionState(int i, boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "SettingsManager.setAirGestureOptionState");
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_5)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setAirGestureOptionState(i, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setBackupRestoreState(int i, boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "SettingsManager.setBackupRestoreState");
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setBackupRestoreState(i, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setBluetoothState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "SettingsManager.setBluetoothState");
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_1)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setBluetoothState(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setBluetoothVisibilityTimeout(int i) {
        return -6;
    }

    public int setBrightness(int i) {
        EnterpriseLicenseManager.log(sContextInfo, "SettingsManager.setBrightness");
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_8)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setBrightness(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setChargingLEDState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "SettingsManager.setChargingLEDState");
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_4)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setChargingLEDState(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setDeveloperOptionsHidden() {
        EnterpriseLicenseManager.log(sContextInfo, "SettingsManager.setDeveloperOptionsHidden");
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setDeveloperOptionsHidden();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setEthernetConfiguration(int i, String str, String str2, String str3, String str4) {
        EnterpriseLicenseManager.log(sContextInfo, "SettingsManager.setEthernetConfiguration");
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_4)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setEthernetConfiguration(i, str, str2, str3, str4);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setEthernetState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "SettingsManager.setEthernetState");
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_4)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setEthernetState(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setFlightModeState(int i) {
        EnterpriseLicenseManager.log(sContextInfo, "SettingsManager.setFlightModeState");
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_6)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setFlightModeState(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setForceSingleView(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "SettingsManager.setForceSingleView");
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_3_9)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setForceSingleView(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setForcedDisplaySizeDensity(int i, int i2, int i3) {
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_3_7)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setForcedDisplaySizeDensity(i, i2, i3);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setGpsState(boolean z) {
        return -6;
    }

    public int setInputMethod(String str, boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "SettingsManager.setInputMethod");
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_1)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setInputMethod(str, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setLTESettingState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "SettingsManager.setLTESettingState");
        CustomDeviceManager customDeviceManager = CustomDeviceManager.getInstance();
        if (customDeviceManager.earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_4)
                || customDeviceManager.laterSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_3_2)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setLTESettingState(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setMobileDataRoamingState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "SettingsManager.setMobileDataRoamingState");
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setMobileDataRoamingState(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setMobileDataState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "SettingsManager.setMobileDataState");
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_1)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setMobileDataState(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setMotionControlState(int i, boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "SettingsManager.setMotionControlState");
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setMotionControlState(i, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setPackageVerifierState(boolean z) {
        return -6;
    }

    public int setPowerSavingMode(int i) {
        EnterpriseLicenseManager.log(sContextInfo, "SettingsManager.setPowerSavingMode");
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_4)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setPowerSavingMode(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setProtectBatteryState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "SettingsManager.setProtectBatteryState");
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_3_3)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setProtectBatteryState(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setScreenWakeupOnPowerState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "SettingsManager.setScreenWakeupOnPowerState");
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_4)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setScreenWakeupOnPowerState(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setSettingsHiddenState(boolean z, int i) {
        EnterpriseLicenseManager.log(sContextInfo, "SettingsManager.setSettingsHiddenState");
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setSettingsHiddenState(z, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setStayAwakeState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "SettingsManager.setStayAwakeState");
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setStayAwakeState(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setSystemLocale(String str, String str2) {
        EnterpriseLicenseManager.log(sContextInfo, "SettingsManager.setSystemLocale");
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_1)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setSystemLocale(str, str2);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setUnknownSourcesState(boolean z) {
        return -6;
    }

    public int setUsbDeviceDefaultPackage(UsbDevice usbDevice, String str, int i) {
        EnterpriseLicenseManager.log(sContextInfo, "SettingsManager.setUsbDeviceDefaultPackage");
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_1)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setUsbDeviceDefaultPackage(usbDevice, str, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setWifiConnectionMonitorState(boolean z) {
        EnterpriseLicenseManager.log(sContextInfo, "SettingsManager.setWifiConnectionMonitorState");
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setWifiConnectionMonitorState(z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setWifiFrequencyBand(int i) {
        EnterpriseLicenseManager.log(sContextInfo, "SettingsManager.setWifiFrequencyBand");
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_5)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setWifiFrequencyBand(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setWifiNetworkNotificationState(boolean z) {
        return -6;
    }

    public int setWifiState(boolean z, String str, String str2) {
        EnterpriseLicenseManager.log(sContextInfo, "SettingsManager.setWifiState");
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_1)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setWifiState(z, str, str2);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int startSmartView() {
        EnterpriseLicenseManager.log(sContextInfo, "SettingsManager.startSmartView");
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_3_8)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.startSmartView();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setWifiState(boolean z, String str, String str2, String str3) {
        EnterpriseLicenseManager.log(sContextInfo, "SettingsManager.setWifiState");
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_2_4)) {
            return -6;
        }
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.setWifiStateEap(z, str, str2, str3);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public static synchronized SettingsManager getInstance(int i) {
        SettingsManager settingsManager;
        synchronized (SettingsManager.class) {
            try {
                if (sSettingsManager == null) {
                    sSettingsManager = new SettingsManager();
                }
                sContextInfo = new ContextInfo(Process.myUid(), false, i);
                settingsManager = sSettingsManager;
            } catch (Throwable th) {
                throw th;
            }
        }
        return settingsManager;
    }
}
