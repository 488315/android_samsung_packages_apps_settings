package com.samsung.android.knox.threatdefense;

import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

import com.samsung.android.knox.ContextInfo;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ThreatDefensePolicy {
    public static final String ACTION_PACKAGE_RULES_REMOVED =
            "com.samsung.android.knox.intent.action.MTDL_PACKAGE_RULES_REMOVED";
    public static final int ERROR_CAST_CLASS = -104;
    public static final int ERROR_INIT_JSON_OBJECT = -107;
    public static final int ERROR_INVALID_ARGUMENT = -106;
    public static final int ERROR_INVALID_PKG = -101;
    public static final int ERROR_INVALID_PROC = -105;
    public static final int ERROR_NO_PACKAGE_RULES = -102;
    public static final int ERROR_POLICY_VERSION = -100;
    public static final int ERROR_RESTRICT_CHAR = -103;
    public static final int ERROR_SIGNATURE = -108;
    public static final String KNOX_MOBILE_THREAT_DEFENSE_PERMISSION =
            "com.samsung.android.knox.permission.KNOX_MOBILE_THREAT_DEFENSE";
    public static final String TAG = "ThreatDefensePolicy";
    public static final Object mSynchronized = new Object();
    public Context mContext;
    public ContextInfo mContextInfo;
    public IThreatDefenseService mThreatDefenseService = null;

    public ThreatDefensePolicy(ContextInfo contextInfo, Context context) {
        this.mContextInfo = contextInfo;
        this.mContext = context;
    }

    public List<Integer> getProcessId(String str) {
        if (getThreatDefenseService() == null) {
            return null;
        }
        try {
            ArrayList arrayList = new ArrayList();
            int[] processId = this.mThreatDefenseService.getProcessId(this.mContextInfo, str);
            if (processId != null) {
                for (int i : processId) {
                    arrayList.add(Integer.valueOf(i));
                }
            }
            return arrayList;
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to call getProcessId()");
            e.printStackTrace();
            return null;
        }
    }

    public final synchronized IThreatDefenseService getThreatDefenseService() {
        try {
            if (this.mThreatDefenseService == null) {
                this.mThreatDefenseService =
                        IThreatDefenseService.Stub.asInterface(
                                ServiceManager.getService("threat_defense_service"));
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.mThreatDefenseService;
    }

    public boolean hasPackageRules() {
        if (getThreatDefenseService() == null) {
            return false;
        }
        try {
            return this.mThreatDefenseService.hasPackageRules(this.mContextInfo);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to call hasPackageRules()");
            e.printStackTrace();
            return false;
        }
    }

    public String procReader(String str) {
        if (getThreatDefenseService() == null) {
            return null;
        }
        try {
            return this.mThreatDefenseService.procReader(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to call procReader()");
            e.printStackTrace();
            return null;
        }
    }

    public String processProcReader(String str, int i) {
        if (getThreatDefenseService() == null) {
            return null;
        }
        try {
            return this.mThreatDefenseService.processProcReader(this.mContextInfo, str, i);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to call processProcReader()");
            e.printStackTrace();
            return null;
        }
    }

    public int setPackageRules(String str) {
        if (getThreatDefenseService() == null) {
            return -1;
        }
        try {
            return this.mThreatDefenseService.setPackageRules(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to call setPackageRules()");
            e.printStackTrace();
            return -1;
        }
    }
}
