package com.samsung.android.knox.deviceinfo;

import android.annotation.NonNull;
import android.annotation.Nullable;
import android.annotation.RequiresPermission;
import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.util.Log;

import androidx.glance.session.SessionManagerImpl$scope$1$$ExternalSyntheticOutline0;

import com.samsung.android.knox.AccessController;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceAdminInfo;
import com.samsung.android.knox.ExternalDependencyInjector;
import com.samsung.android.knox.IMiscPolicy;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.Collections;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class DeviceInventory {
    public static final String ACTION_SIM_CARD_CHANGED =
            "com.samsung.android.knox.intent.action.SIM_CARD_CHANGED";
    public static final String EXTRA_SIM_CHANGE_INFO =
            "com.samsung.android.knox.intent.extra.SIM_CHANGE_INFO";
    public static String TAG = "DeviceInventory";
    public final Context mContext;
    public ContextInfo mContextInfo;
    public ExternalDependencyInjector mExternalDependencyInjector;
    public IMiscPolicy mMiscService;
    public IDeviceInfo mService;

    public DeviceInventory(
            @NonNull ContextInfo contextInfo,
            @NonNull Context context,
            @NonNull ExternalDependencyInjector externalDependencyInjector) {
        this.mContextInfo = contextInfo;
        this.mContext = context;
        this.mExternalDependencyInjector = externalDependencyInjector;
    }

    public void dataUsageTimerActivation() {
        if (getService() != null) {
            try {
                this.mService.dataUsageTimerActivation(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with device info policy", e);
            }
        }
    }

    public final boolean externalSdCardAvailable() {
        String volumeState;
        String externalSdCardDirectory = getExternalSdCardDirectory();
        if (externalSdCardDirectory == null
                || (volumeState =
                                ((StorageManager) this.mContext.getSystemService("storage"))
                                        .getVolumeState(externalSdCardDirectory))
                        == null) {
            return false;
        }
        return volumeState.equals("mounted");
    }

    public long getAvailableCapacityExternal() {
        String externalSdCardDirectory;
        EnterpriseLicenseManager.log(
                this.mContextInfo, "DeviceInventory.getAvailableCapacityExternal");
        try {
            if (!externalSdCardAvailable()
                    || (externalSdCardDirectory = getExternalSdCardDirectory()) == null) {
                return -1L;
            }
            return getAvailableMemorySize(new StatFs(externalSdCardDirectory));
        } catch (Exception e) {
            SessionManagerImpl$scope$1$$ExternalSyntheticOutline0.m(
                    "getAvailableCapacityExternal", e, TAG);
        }
        return -1L;
    }

    public long getAvailableCapacityInternal() {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "DeviceInventory.getAvailableCapacityInternal");
        try {
            String internalSdCardPath = getInternalSdCardPath();
            if (internalSdCardPath == null) {
                return -1L;
            }
            return getAvailableMemorySize(new StatFs(internalSdCardPath));
        } catch (Exception e) {
            SessionManagerImpl$scope$1$$ExternalSyntheticOutline0.m(
                    "getAvailableCapacityInternal", e, TAG);
            return -1L;
        }
    }

    public final long getAvailableMemorySize(@NonNull StatFs statFs) {
        return statFs.getBlockSizeLong() * statFs.getAvailableBlocksLong();
    }

    @Nullable
    public String getDeviceOS() {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceInventory.getDeviceOS");
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getDeviceOS(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device info policy", e);
            return null;
        }
    }

    @Nullable
    public String getDeviceOSVersion() {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceInventory.getDeviceOSVersion");
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getDeviceOSVersion(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device info policy", e);
            return null;
        }
    }

    @RequiresPermission(EnterpriseDeviceAdminInfo.USES_POLICY_MDM_DEVICE_INVENTORY_TAG)
    public int getDroppedCallsCount() {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceInventory.getDroppedCallsCount");
        IDeviceInfo iDeviceInfo = this.mService;
        if (iDeviceInfo == null) {
            return -1;
        }
        try {
            return iDeviceInfo.getDroppedCallsCount(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device info policy", e);
            return -1;
        }
    }

    @Nullable
    public final String getExternalSdCardDirectory() {
        StorageVolume[] volumeList =
                ((StorageManager) this.mContext.getSystemService("storage")).getVolumeList();
        if (volumeList == null || volumeList.length <= 1 || volumeList[1].getPath() == null) {
            return null;
        }
        StorageVolume storageVolume = volumeList[1];
        if (this.mExternalDependencyInjector != null) {
            Log.d(
                    TAG,
                    "Subsystem : "
                            + this.mExternalDependencyInjector.storageVolumeGetSubSystem(
                                    storageVolume));
        }
        Log.d(TAG, "Path : " + storageVolume.getPath());
        return storageVolume.getPath();
    }

    @Nullable
    public final String getInternalSdCardPath() {
        StorageVolume[] volumeList =
                ((StorageManager) this.mContext.getSystemService("storage")).getVolumeList();
        if (volumeList.length > 0) {
            return volumeList[0].getPath();
        }
        return null;
    }

    public String getKnoxServiceId() {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceInventory.getKnoxServiceId", true);
        if (getService() == null) {
            return ApnSettings.MVNO_NONE;
        }
        try {
            return this.mService.getKnoxServiceId(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device info policy", e);
            return ApnSettings.MVNO_NONE;
        }
    }

    @RequiresPermission("com.samsung.android.knox.permission.KNOX_INTERNAL_EXCEPTION")
    public List<String> getKnoxServicePackageList() {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "DeviceInventory.getKnoxServicePackageList", true);
        if (getService() != null) {
            try {
                return this.mService.getKnoxServicePackageList(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with device info policy", e);
            }
        }
        return Collections.emptyList();
    }

    @RequiresPermission(EnterpriseDeviceAdminInfo.USES_POLICY_MDM_DEVICE_INVENTORY_TAG)
    public SimChangeInfo getLastSimChangeInfo() {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceInventory.getLastSimChangeInfo");
        SimChangeInfo simChangeInfo = new SimChangeInfo();
        if (getMiscService() != null) {
            try {
                return this.mMiscService.getLastSimChangeInfo(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with misc policy", e);
            }
        }
        return simChangeInfo;
    }

    public final IMiscPolicy getMiscService() {
        if (this.mMiscService == null) {
            this.mMiscService =
                    IMiscPolicy.Stub.asInterface(ServiceManager.getService("misc_policy"));
        }
        return this.mMiscService;
    }

    @RequiresPermission(EnterpriseDeviceAdminInfo.USES_POLICY_MDM_DEVICE_INVENTORY_TAG)
    public int getMissedCallsCount() {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceInventory.getMissedCallsCount");
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.getMissedCallsCount(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device info policy", e);
            return -1;
        }
    }

    @Nullable
    public String getSalesCode() {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceInventory.getSalesCode");
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getSalesCode(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device info policy", e);
            return null;
        }
    }

    @RequiresPermission("android.permission.READ_PRIVILEGED_PHONE_STATE")
    @Nullable
    public String getSerialNumber() {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceInventory.getSerialNumber");
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getSerialNumber(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device info policy", e);
            return null;
        }
    }

    public final IDeviceInfo getService() {
        if (this.mService == null) {
            this.mService = IDeviceInfo.Stub.asInterface(ServiceManager.getService("device_info"));
        }
        return this.mService;
    }

    @RequiresPermission(EnterpriseDeviceAdminInfo.USES_POLICY_MDM_DEVICE_INVENTORY_TAG)
    public int getSuccessCallsCount() {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceInventory.getSuccessCallsCount");
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.getSuccessCallsCount(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device info policy", e);
            return -1;
        }
    }

    public long getTotalCapacityExternal() {
        String externalSdCardDirectory;
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceInventory.getTotalCapacityExternal");
        try {
            if (!externalSdCardAvailable()
                    || (externalSdCardDirectory = getExternalSdCardDirectory()) == null) {
                return -1L;
            }
            return getTotalMemorySize(new StatFs(externalSdCardDirectory));
        } catch (Exception e) {
            SessionManagerImpl$scope$1$$ExternalSyntheticOutline0.m(
                    "getTotalCapacityExternal", e, TAG);
        }
        return -1L;
    }

    public long getTotalCapacityInternal() {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceInventory.getTotalCapacityInternal");
        try {
            String internalSdCardPath = getInternalSdCardPath();
            if (internalSdCardPath == null) {
                return -1L;
            }
            return getTotalMemorySize(new StatFs(internalSdCardPath));
        } catch (Exception e) {
            SessionManagerImpl$scope$1$$ExternalSyntheticOutline0.m(
                    "getTotalCapacityInternal", e, TAG);
            return -1L;
        }
    }

    public final long getTotalMemorySize(@NonNull StatFs statFs) {
        return statFs.getBlockSizeLong() * statFs.getBlockCountLong();
    }

    @RequiresPermission(EnterpriseDeviceAdminInfo.USES_POLICY_MDM_DEVICE_INVENTORY_TAG)
    public boolean isDeviceLocked() {
        AccessController.throwIfParentInstance(this.mContextInfo, "isDeviceLocked");
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceInventory.isDeviceLocked");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isDeviceLocked(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device info policy", e);
            return false;
        }
    }

    @RequiresPermission(EnterpriseDeviceAdminInfo.USES_POLICY_MDM_DEVICE_INVENTORY_TAG)
    public boolean isDeviceSecure() {
        AccessController.throwIfParentInstance(this.mContextInfo, "isDeviceSecure");
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceInventory.isDeviceSecure");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isDeviceSecure(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device info policy", e);
            return false;
        }
    }

    @RequiresPermission(EnterpriseDeviceAdminInfo.USES_POLICY_MDM_DEVICE_INVENTORY_TAG)
    public boolean resetCallsCount() {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceInventory.resetCallsCount");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.resetCallsCount(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device info policy", e);
            return false;
        }
    }

    @RequiresPermission("com.samsung.android.knox.permission.KNOX_INTERNAL_EXCEPTION")
    public boolean setKnoxServiceId(List<String> list, String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DeviceInventory.setKnoxServiceID");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setKnoxServiceId(this.mContextInfo, list, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device info policy", e);
            return false;
        }
    }

    public boolean storeCalling(String str, String str2, String str3, String str4, boolean z) {
        if (getService() == null) {
            return false;
        }
        try {
            this.mService.storeCalling(str, str2, str3, str4, z);
            return true;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device inventory policy", e);
            return false;
        }
    }

    public void storeMMS(String str, String str2, String str3, boolean z) {
        if (getService() != null) {
            try {
                this.mService.storeMMS(str, str2, str3, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with device info policy", e);
            }
        }
    }

    public void storeSMS(String str, String str2, String str3, boolean z) {
        if (getService() != null) {
            try {
                this.mService.storeSMS(str, str2, str3, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with device info policy", e);
            }
        }
    }
}
