package com.samsung.android.knox.restriction;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.license.EnterpriseLicenseManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class RoamingPolicy {
    public static String TAG = "RoamingPolicy";
    public ContextInfo mContextInfo;
    public IRoamingPolicy mService;

    public RoamingPolicy(ContextInfo contextInfo) {
        this.mContextInfo = contextInfo;
    }

    public final IRoamingPolicy getService() {
        if (this.mService == null) {
            this.mService =
                    IRoamingPolicy.Stub.asInterface(ServiceManager.getService("roaming_policy"));
        }
        return this.mService;
    }

    public boolean isRoamingDataEnabled() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isRoamingDataEnabled(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with roaming policy", e);
            return true;
        }
    }

    public boolean isRoamingPushEnabled() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isRoamingPushEnabled(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with roaming policy", e);
            return true;
        }
    }

    public boolean isRoamingSyncEnabled() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isRoamingSyncEnabled(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with roaming policy", e);
            return true;
        }
    }

    public boolean isRoamingVoiceCallsEnabled() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isRoamingVoiceCallsEnabled(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with roaming policy", e);
            return true;
        }
    }

    public void setRoamingData(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RoamingPolicy.setRoamingData");
        if (getService() != null) {
            try {
                this.mService.setRoamingData(this.mContextInfo, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with roaming policy", e);
            }
        }
    }

    public void setRoamingPush(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RoamingPolicy.setRoamingPush");
        if (getService() != null) {
            try {
                this.mService.setRoamingPush(this.mContextInfo, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with roaming policy", e);
            }
        }
    }

    public void setRoamingSync(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RoamingPolicy.setRoamingSync");
        if (getService() != null) {
            try {
                this.mService.setRoamingSync(this.mContextInfo, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with roaming policy", e);
            }
        }
    }

    public void setRoamingVoiceCalls(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RoamingPolicy.setRoamingVoiceCalls");
        if (getService() != null) {
            try {
                this.mService.setRoamingVoiceCalls(this.mContextInfo, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with roaming policy", e);
            }
        }
    }
}
