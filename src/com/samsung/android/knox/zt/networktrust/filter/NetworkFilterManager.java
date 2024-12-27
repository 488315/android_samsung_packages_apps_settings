package com.samsung.android.knox.zt.networktrust.filter;

import android.annotation.NonNull;
import android.annotation.Nullable;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;

import com.samsung.android.knox.ContextInfo;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class NetworkFilterManager {
    public static final String ACTION_NOTIFY_NETWORK_FILTER_STATUS =
            "com.samsung.android.knox.intent.action.NOTIFY_NETWORK_FILTER_STATUS";
    public static final int ERROR_INTERNAL = -8;
    public static final int ERROR_INVALID_CALLER = -7;
    public static final int ERROR_NONE = 0;
    public static final int ERROR_NULL_PARAMETER = -10;
    public static final int ERROR_PACKAGE_ALREADY_REGISTERED = -9;
    public static final int ERROR_PACKAGE_NOT_INSTALLED = -6;
    public static final int ERROR_PACKAGE_NOT_REGISTERED = -2;
    public static final int ERROR_PACKAGE_SIGNATURE_MISMATCH = -3;
    public static final int ERROR_PROFILE_LIMIT_REACHED = -4;
    public static final int ERROR_PROFILE_NOT_FOUND = -5;
    public static final int ERROR_UNKNOWN = -1;
    public static final String EXTRA_STATUS = "com.samsung.android.knox.intent.extra.STATUS";
    public static final int STATUS_PROFILE_PAUSED = 2;
    public static final int STATUS_PROFILE_RUNNING = 1;
    public static final int STATUS_PROFILE_STOPPED = 3;
    public static final String TAG = "knoxNwFilter-NetworkFilterManager";
    public Context mContext;
    public ContextInfo mContextInfo;
    public IKnoxNetworkFilterService mNwFilterMgrService;

    public NetworkFilterManager(ContextInfo contextInfo, Context context) {
        this.mContextInfo = contextInfo;
        this.mContext = context;
    }

    public static NetworkFilterManager getInstance(ContextInfo contextInfo, Context context) {
        return new NetworkFilterManager(contextInfo, context);
    }

    public List<String> getRegisteredPackageList() {
        if (getService() != null) {
            try {
                return this.mNwFilterMgrService.getRegisteredPackageList(this.mContextInfo);
            } catch (RemoteException unused) {
                Log.w(TAG, "Failed to getRegisteredPackageList");
            }
        }
        return null;
    }

    public final IKnoxNetworkFilterService getService() {
        if (this.mNwFilterMgrService == null) {
            this.mNwFilterMgrService =
                    IKnoxNetworkFilterService.Stub.asInterface(
                            ServiceManager.getService("knox_nwFilterMgr_policy"));
        }
        return this.mNwFilterMgrService;
    }

    public int registerNetworkFilter(
            @NonNull String str, @Nullable String str2, @NonNull Bundle bundle) {
        int i = 0;
        if (getService() != null) {
            try {
                i =
                        this.mNwFilterMgrService.registerApplication(
                                this.mContextInfo, str, str2, bundle);
            } catch (RemoteException unused) {
                Log.w(TAG, "Failed to registerNetworkFilter");
            }
            if (i == 0) {
                Intent intent = new Intent();
                int userId = UserHandle.getUserId(Binder.getCallingUid());
                intent.setClassName(
                        "com.android.vpndialogs", "com.android.vpndialogs.KnoxVpnPPDialog");
                intent.addFlags(1350565888);
                if (this.mContext != null) {
                    ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                            userId, "startActivityAsUser  KnoxVpnPPDialog userId = ", TAG);
                    this.mContext.startActivityAsUser(intent, new UserHandle(userId));
                }
            }
        }
        return i;
    }

    public int unregisterNetworkFilter(@NonNull String str) {
        if (getService() != null) {
            try {
                return this.mNwFilterMgrService.unregisterApplication(this.mContextInfo, str);
            } catch (RemoteException unused) {
                Log.w(TAG, "Failed to unregisterNetworkFilter");
            }
        }
        return 0;
    }
}
