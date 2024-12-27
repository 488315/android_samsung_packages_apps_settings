package com.android.settingslib.fuelgauge;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.Flags;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.IDeviceIdleController;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.telecom.DefaultDialerManager;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;

import com.android.internal.telephony.SmsApplication;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PowerAllowlistBackend {
    public static PowerAllowlistBackend sInstance;
    public final ArraySet mAllowlistedApps;
    public final Object mAllowlistedAppsLock;
    public final Context mAppContext;
    public final ArraySet mDefaultActiveApps;
    public final Object mDefaultActiveAppsLock;
    public final IDeviceIdleController mDeviceIdleService;
    public final ArraySet mSysAllowlistedApps;
    public final Object mSysAllowlistedAppsLock;

    public PowerAllowlistBackend(Context context) {
        this(
                context,
                IDeviceIdleController.Stub.asInterface(ServiceManager.getService("deviceidle")));
    }

    public static PowerAllowlistBackend getInstance(Context context) {
        PowerAllowlistBackend powerAllowlistBackend;
        synchronized (PowerAllowlistBackend.class) {
            try {
                if (sInstance == null) {
                    sInstance = new PowerAllowlistBackend(context);
                }
                powerAllowlistBackend = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return powerAllowlistBackend;
    }

    public final synchronized void addApp(int i, String str) {
        try {
            try {
                if (Flags.appRestrictionsApi()) {
                    if (i == -1) {
                        i =
                                ((PackageManager)
                                                this.mAppContext.getSystemService(
                                                        PackageManager.class))
                                        .getPackageUid(str, 0);
                    }
                    int i2 = i;
                    if (!isAllowlisted(i2, str)) {
                        ((ActivityManager) this.mAppContext.getSystemService(ActivityManager.class))
                                .noteAppRestrictionEnabled(str, i2, 20, true, 4, "settings", 1, 0L);
                    }
                }
                this.mDeviceIdleService.addPowerSaveWhitelistApp(str);
                synchronized (this.mAllowlistedAppsLock) {
                    this.mAllowlistedApps.add(str);
                }
            } catch (PackageManager.NameNotFoundException e) {
                Log.w("PowerAllowlistBackend", "Unable to find package", e);
            } catch (RemoteException e2) {
                Log.w("PowerAllowlistBackend", "Unable to reach IDeviceIdleController", e2);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final boolean isAllowlisted(int i, String str) {
        synchronized (this.mAllowlistedAppsLock) {
            try {
                if (this.mAllowlistedApps.contains(str)) {
                    return true;
                }
                return isDefaultActiveApp(i, str);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isDefaultActiveApp(int i, String str) {
        synchronized (this.mDefaultActiveAppsLock) {
            try {
                if (this.mDefaultActiveApps.contains(str)) {
                    return true;
                }
                if (((DevicePolicyManager)
                                this.mAppContext.getSystemService(DevicePolicyManager.class))
                        .packageHasActiveAdmins(str)) {
                    return true;
                }
                AppOpsManager appOpsManager =
                        (AppOpsManager) this.mAppContext.getSystemService(AppOpsManager.class);
                if (DeviceConfig.getBoolean(
                                "activity_manager",
                                "system_exempt_power_restrictions_enabled",
                                true)
                        && appOpsManager.checkOpNoThrow(128, i, str) == 0) {
                    return true;
                }
                return this.mAppContext
                        .getPackageManager()
                        .isPackageStateProtected(str, UserHandle.getUserId(i));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isSysAllowlisted(String str) {
        boolean contains;
        synchronized (this.mSysAllowlistedAppsLock) {
            contains = this.mSysAllowlistedApps.contains(str);
        }
        return contains;
    }

    public synchronized void refreshList() {
        int i;
        try {
            synchronized (this.mSysAllowlistedAppsLock) {
                this.mSysAllowlistedApps.clear();
            }
            synchronized (this.mAllowlistedAppsLock) {
                this.mAllowlistedApps.clear();
            }
            synchronized (this.mDefaultActiveAppsLock) {
                this.mDefaultActiveApps.clear();
            }
            IDeviceIdleController iDeviceIdleController = this.mDeviceIdleService;
            if (iDeviceIdleController == null) {
                return;
            }
            try {
                String[] fullPowerWhitelist = iDeviceIdleController.getFullPowerWhitelist();
                synchronized (this.mAllowlistedAppsLock) {
                    try {
                        for (String str : fullPowerWhitelist) {
                            this.mAllowlistedApps.add(str);
                        }
                    } finally {
                    }
                }
                String[] systemPowerWhitelist = this.mDeviceIdleService.getSystemPowerWhitelist();
                synchronized (this.mSysAllowlistedAppsLock) {
                    try {
                        for (String str2 : systemPowerWhitelist) {
                            this.mSysAllowlistedApps.add(str2);
                        }
                    } finally {
                    }
                }
                boolean hasSystemFeature =
                        this.mAppContext
                                .getPackageManager()
                                .hasSystemFeature("android.hardware.telephony");
                ComponentName defaultSmsApplication =
                        SmsApplication.getDefaultSmsApplication(this.mAppContext, true);
                String defaultDialerApplication =
                        DefaultDialerManager.getDefaultDialerApplication(this.mAppContext);
                if (hasSystemFeature) {
                    if (defaultSmsApplication != null) {
                        synchronized (this.mDefaultActiveAppsLock) {
                            this.mDefaultActiveApps.add(defaultSmsApplication.getPackageName());
                        }
                    }
                    if (!TextUtils.isEmpty(defaultDialerApplication)) {
                        synchronized (this.mDefaultActiveAppsLock) {
                            this.mDefaultActiveApps.add(defaultDialerApplication);
                        }
                    }
                }
            } catch (Exception e) {
                Log.e("PowerAllowlistBackend", "Failed to invoke refreshList()", e);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized void removeApp(int i, String str) {
        try {
            try {
                if (Flags.appRestrictionsApi()) {
                    if (i == -1) {
                        i =
                                ((PackageManager)
                                                this.mAppContext.getSystemService(
                                                        PackageManager.class))
                                        .getPackageUid(str, 0);
                    }
                    int i2 = i;
                    if (isAllowlisted(i2, str)) {
                        ((ActivityManager) this.mAppContext.getSystemService(ActivityManager.class))
                                .noteAppRestrictionEnabled(
                                        str, i2, 20, false, 4, "settings", 1, 0L);
                    }
                }
                this.mDeviceIdleService.removePowerSaveWhitelistApp(str);
                synchronized (this.mAllowlistedAppsLock) {
                    this.mAllowlistedApps.remove(str);
                }
            } catch (PackageManager.NameNotFoundException e) {
                Log.w("PowerAllowlistBackend", "Unable to find package", e);
            } catch (RemoteException e2) {
                Log.w("PowerAllowlistBackend", "Unable to reach IDeviceIdleController", e2);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public PowerAllowlistBackend(Context context, IDeviceIdleController iDeviceIdleController) {
        this.mAllowlistedAppsLock = new Object();
        this.mSysAllowlistedAppsLock = new Object();
        this.mDefaultActiveAppsLock = new Object();
        this.mAllowlistedApps = new ArraySet();
        this.mSysAllowlistedApps = new ArraySet();
        this.mDefaultActiveApps = new ArraySet();
        this.mAppContext = context.getApplicationContext();
        this.mDeviceIdleService = iDeviceIdleController;
        refreshList();
    }
}
