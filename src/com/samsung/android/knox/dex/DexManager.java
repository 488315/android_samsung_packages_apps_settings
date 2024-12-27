package com.samsung.android.knox.dex;

import android.content.ComponentName;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.custom.CustomDeviceManager;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.knox.net.apn.ApnSettings;

import java.io.InputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class DexManager {
    public static final int DEX_APP_ALREADY_SET_POLICY = 3;
    public static final int DEX_APP_NOT_INSTALLED = 2;
    public static final int DEX_APP_NOT_SET_POLICY = 4;
    public static final int DEX_POLICY_FAIL = 1;
    public static final int DEX_POLICY_SUCCESS = 0;
    public static final int FLAG_DEX = 8;
    public static final int FLAG_LOCK = 2;
    public static final int FLAG_PHONE = 4;
    public static final int FLAG_SYSTEM = 1;
    public static String TAG = "DexPolicy";
    public static DexManager sDexManager;
    public ContextInfo mContextInfo;
    public IKnoxCustomManager mKnoxCustomService;
    public IDexPolicy mService;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Retention(RetentionPolicy.SOURCE)
    public @interface SetWallpaperFlags {}

    public DexManager(ContextInfo contextInfo) {
        this.mContextInfo = contextInfo;
    }

    public static synchronized DexManager getInstance() {
        DexManager dexManager;
        synchronized (DexManager.class) {
            try {
                if (sDexManager == null) {
                    sDexManager = new DexManager();
                }
                dexManager = sDexManager;
            } catch (Throwable th) {
                throw th;
            }
        }
        return dexManager;
    }

    public int addPackageToDisableList(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DexManager.addPackageToDisableList");
        if (getService() == null) {
            return 1;
        }
        try {
            return this.mService.addPackageToDisableList(this.mContextInfo, str);
        } catch (Exception e) {
            Log.w(TAG, "Failed talking with Dex policy", e);
            return 1;
        }
    }

    public int addShortcut(int i, int i2, ComponentName componentName) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DexManager.addShortcut");
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_3_1)) {
            return -6;
        }
        if (getKnoxCustomService() == null) {
            return -1;
        }
        try {
            return this.mKnoxCustomService.addDexShortcut(i, i2, componentName);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int addURLShortcut(int i, int i2, String str, String str2, ComponentName componentName) {
        EnterpriseLicenseManager.log(
                this.mContextInfo,
                "DexManager.addURLShortcut(int, int, String, String, ComponentName)");
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_3_1)) {
            return -6;
        }
        if (getKnoxCustomService() == null) {
            return -1;
        }
        try {
            return this.mKnoxCustomService.addDexURLShortcut(i, i2, str, str2, componentName);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int allowAutoOpenLastApp(int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DexManager.allowAutoOpenLastApp");
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_3_3)) {
            return -6;
        }
        if (getKnoxCustomService() == null) {
            return -1;
        }
        try {
            return this.mKnoxCustomService.allowDexAutoOpenLastApp(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public boolean allowScreenTimeoutChange(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DexManager.allowScreenTimeoutChange");
        try {
            if (getService() != null) {
                return this.mService.allowScreenTimeoutChange(this.mContextInfo, z);
            }
            return true;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at dex policy API", e);
            return true;
        }
    }

    public int clearLoadingLogo() {
        EnterpriseLicenseManager.log(this.mContextInfo, "DexManager.clearLoadingLogo");
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_3_1)) {
            return -6;
        }
        if (getKnoxCustomService() == null) {
            return -1;
        }
        try {
            return this.mKnoxCustomService.clearDexLoadingLogo();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public boolean enforceEthernetOnly(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DexManager.enforceEthernetOnly");
        try {
            if (getService() != null) {
                return this.mService.enforceEthernetOnly(this.mContextInfo, z);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at dex policy API", e);
            return false;
        }
    }

    public boolean enforceVirtualMacAddress(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DexManager.enforceVirtualMacAddress");
        try {
            if (getService() != null) {
                return this.mService.enforceVirtualMacAddress(this.mContextInfo, z);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at dex policy API", e);
            return false;
        }
    }

    public List<String> getForegroundModePackageList() {
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_3_1)) {
            return new ArrayList();
        }
        if (getKnoxCustomService() != null) {
            try {
                return this.mKnoxCustomService.getDexForegroundModePackageList();
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            }
        }
        return new ArrayList();
    }

    public int getHDMIAutoEnterState() {
        if (getKnoxCustomService() == null) {
            return -1;
        }
        try {
            return this.mKnoxCustomService.getDexHDMIAutoEnterState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int getHomeAlignment() {
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_3_1)) {
            return -6;
        }
        if (getKnoxCustomService() == null) {
            return -1;
        }
        try {
            return this.mKnoxCustomService.getDexHomeAlignment();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public final IKnoxCustomManager getKnoxCustomService() {
        if (this.mKnoxCustomService == null) {
            this.mKnoxCustomService =
                    IKnoxCustomManager.Stub.asInterface(ServiceManager.getService("knoxcustom"));
        }
        return this.mKnoxCustomService;
    }

    public List<String> getPackagesFromDisableList() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getPackagesFromDisableList(this.mContextInfo);
        } catch (Exception e) {
            Log.w(TAG, "Failed talking with Dex policy", e);
            return null;
        }
    }

    public int getScreenTimeout() {
        int dexScreenTimeout;
        if (getKnoxCustomService() != null) {
            try {
                dexScreenTimeout = this.mKnoxCustomService.getDexScreenTimeout();
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            }
            return dexScreenTimeout / 1000;
        }
        dexScreenTimeout = 0;
        return dexScreenTimeout / 1000;
    }

    public final IDexPolicy getService() {
        if (this.mService == null) {
            this.mService = IDexPolicy.Stub.asInterface(ServiceManager.getService("dex_policy"));
        }
        return this.mService;
    }

    public int getShowIMEWithHardKeyboard() {
        if (getKnoxCustomService() == null) {
            return -1;
        }
        try {
            return this.mKnoxCustomService.getShowIMEWithHardKeyboard();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public String getVirtualMacAddress() {
        try {
            return getService() != null
                    ? this.mService.getVirtualMacAddress()
                    : ApnSettings.MVNO_NONE;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at dex policy API", e);
            return ApnSettings.MVNO_NONE;
        }
    }

    public int isAutoOpenLastAppAllowed() {
        if (getKnoxCustomService() == null) {
            return -1;
        }
        try {
            return this.mKnoxCustomService.isDexAutoOpenLastAppAllowed();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public boolean isDexActivated() {
        try {
            if (getService() != null) {
                return this.mService.isDexActivated();
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at dex policy API", e);
            return false;
        }
    }

    public boolean isDexDisabled() {
        try {
            if (getService() != null) {
                return this.mService.isDexDisabled();
            }
            return true;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at dex policy API", e);
            return true;
        }
    }

    public boolean isEthernetOnlyEnforced() {
        try {
            if (getService() != null) {
                return this.mService.isEthernetOnlyEnforced();
            }
            return true;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at dex policy API", e);
            return true;
        }
    }

    public boolean isScreenTimeoutChangeAllowed() {
        try {
            if (getService() != null) {
                return this.mService.isScreenTimeoutChangeAllowed();
            }
            return true;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at dex policy API", e);
            return true;
        }
    }

    public boolean isVirtualMacAddressEnforced() {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "DexManager.isVirtualMacAddressEnforced", true);
        try {
            if (getService() != null) {
                return this.mService.isVirtualMacAddressEnforced();
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at dex policy API", e);
            return false;
        }
    }

    public int removePackageFromDisableList(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DexManager.removePackageFromDisableList");
        if (getService() == null) {
            return 1;
        }
        try {
            return this.mService.removePackageFromDisableList(this.mContextInfo, str);
        } catch (Exception e) {
            Log.w(TAG, "Failed talking with Dex policy", e);
            return 1;
        }
    }

    public int removeShortcut(ComponentName componentName) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DexManager.removeShortcut");
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_3_1)) {
            return -6;
        }
        if (getKnoxCustomService() == null) {
            return -1;
        }
        try {
            return this.mKnoxCustomService.removeDexShortcut(componentName);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int removeURLShortcut(String str, ComponentName componentName) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DexManager.removeURLShortcut");
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_3_1)) {
            return -6;
        }
        if (getKnoxCustomService() == null) {
            return -1;
        }
        try {
            return this.mKnoxCustomService.removeDexURLShortcut(str, componentName);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public boolean setDexDisabled(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DexManager.setDexDisabled");
        try {
            if (getService() != null) {
                return this.mService.setDexDisabled(this.mContextInfo, z);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at dex policy API", e);
            return false;
        }
    }

    public int setForegroundModePackageList(int i, List<String> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DexManager.setForegroundModePackageList");
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_3_1)) {
            return -6;
        }
        if (getKnoxCustomService() == null) {
            return -1;
        }
        try {
            return this.mKnoxCustomService.setDexForegroundModePackageList(i, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setHDMIAutoEnterState(int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DexManager.setHDMIAutoEnterState");
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_3_3)) {
            return -6;
        }
        if (getKnoxCustomService() == null) {
            return -1;
        }
        try {
            return this.mKnoxCustomService.setDexHDMIAutoEnterState(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setHomeAlignment(int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DexManager.setHomeAlignment");
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_3_1)) {
            return -6;
        }
        if (getKnoxCustomService() == null) {
            return -1;
        }
        try {
            return this.mKnoxCustomService.setDexHomeAlignment(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setLoadingLogo(ParcelFileDescriptor parcelFileDescriptor) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DexManager.setLoadingLogo");
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_3_1)) {
            return -6;
        }
        if (getKnoxCustomService() == null) {
            return -1;
        }
        try {
            return this.mKnoxCustomService.setDexLoadingLogo(parcelFileDescriptor);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setScreenTimeout(int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DexManager.setScreenTimeout");
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_3_1)) {
            return -6;
        }
        if (getKnoxCustomService() == null) {
            return -1;
        }
        try {
            return this.mKnoxCustomService.setDexScreenTimeout(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setShowIMEWithHardKeyboard(int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DexManager.setShowIMEWithHardKeyboard");
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_3_3)) {
            return -6;
        }
        if (getKnoxCustomService() == null) {
            return -1;
        }
        try {
            return this.mKnoxCustomService.setShowIMEWithHardKeyboard(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }

    public int setWallpaper(Context context, InputStream inputStream, Rect rect, boolean z, int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DexManager.setWallpaper");
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_3_3)) {
            return -6;
        }
        if ((i & 8) == 0) {
            return -50;
        }
        if (getKnoxCustomService() != null) {
            try {
                Bitmap decodeStream = BitmapFactory.decodeStream(inputStream);
                Bundle bundle = new Bundle();
                bundle.putParcelable("bitmapData", decodeStream);
                return this.mKnoxCustomService.setWallpaper(bundle, rect, z, i);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with EnterpriseDeviceManager service", e);
                return -1;
            } catch (SecurityException e2) {
                Log.w(TAG, "The calling process does not have the knox custom dex permission", e2);
            }
        }
        return -1;
    }

    public DexManager() {
        this.mContextInfo = new ContextInfo();
    }

    public int addURLShortcut(
            int i,
            int i2,
            String str,
            String str2,
            String str3,
            ComponentName componentName,
            ParcelFileDescriptor parcelFileDescriptor) {
        EnterpriseLicenseManager.log(
                this.mContextInfo,
                "DexManager.addURLShortcut(int, int, String, String, String, ComponentName,"
                    + " ParcelFileDescriptor)");
        if (CustomDeviceManager.getInstance()
                .earlierSdk(CustomDeviceManager.SdkVersion.SDK_VERSION_3_1)) {
            return -6;
        }
        if (getKnoxCustomService() == null) {
            return -1;
        }
        try {
            return this.mKnoxCustomService.addDexURLShortcutExtend(
                    i, i2, str, str2, str3, componentName, parcelFileDescriptor);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with KnoxCustomManager service", e);
            return -1;
        }
    }
}
