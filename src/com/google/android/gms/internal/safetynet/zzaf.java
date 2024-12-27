package com.google.android.gms.internal.safetynet;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.GmsClient;
import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zzaf extends GmsClient {
    public final Context zze;

    public zzaf(
            Context context,
            Looper looper,
            ClientSettings clientSettings,
            GoogleApiClient.ConnectionCallbacks connectionCallbacks,
            GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 45, clientSettings, connectionCallbacks, onConnectionFailedListener);
        this.zze = context;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public final /* bridge */ /* synthetic */ IInterface createServiceInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface =
                iBinder.queryLocalInterface(
                        "com.google.android.gms.safetynet.internal.ISafetyNetService");
        return queryLocalInterface instanceof zzh ? (zzh) queryLocalInterface : new zzh(iBinder);
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient,
              // com.google.android.gms.common.api.Api.Client
    public final int getMinApkVersion() {
        return 12200000;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public final String getServiceDescriptor() {
        return "com.google.android.gms.safetynet.internal.ISafetyNetService";
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public final String getStartServiceAction() {
        return "com.google.android.gms.safetynet.service.START";
    }

    public final String zzp(String str) {
        ApplicationInfo applicationInfo;
        Bundle bundle;
        try {
            PackageManager packageManager = this.zze.getPackageManager();
            if (packageManager == null
                    || (applicationInfo =
                                    packageManager.getApplicationInfo(
                                            this.zze.getPackageName(), 128))
                            == null
                    || (bundle = applicationInfo.metaData) == null) {
                return ApnSettings.MVNO_NONE;
            }
            String str2 = (String) bundle.get(str);
            return str2 == null ? ApnSettings.MVNO_NONE : str2;
        } catch (PackageManager.NameNotFoundException unused) {
            return ApnSettings.MVNO_NONE;
        }
    }
}
