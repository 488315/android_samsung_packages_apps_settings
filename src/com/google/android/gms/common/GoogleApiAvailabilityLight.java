package com.google.android.gms.common;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;

import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.common.util.DeviceProperties;
import com.google.android.gms.common.wrappers.PackageManagerWrapper;
import com.google.android.gms.common.wrappers.Wrappers;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class GoogleApiAvailabilityLight {
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE;

    static {
        AtomicBoolean atomicBoolean = GooglePlayServicesUtilLight.zzc;
        GOOGLE_PLAY_SERVICES_VERSION_CODE = 12451000;
    }

    public Intent getErrorResolutionIntent(Context context, int i, String str) {
        if (i != 1 && i != 2) {
            if (i != 3) {
                return null;
            }
            int i2 = zzt.$r8$clinit;
            Uri fromParts = Uri.fromParts("package", "com.google.android.gms", null);
            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(fromParts);
            return intent;
        }
        if (context != null) {
            PackageManager packageManager = context.getPackageManager();
            if (DeviceProperties.zzd == null) {
                DeviceProperties.zzd =
                        Boolean.valueOf(
                                packageManager.hasSystemFeature("android.hardware.type.watch"));
            }
            if (DeviceProperties.zzd.booleanValue()) {
                if (DeviceProperties.zze == null) {
                    DeviceProperties.zze =
                            Boolean.valueOf(
                                    context.getPackageManager().hasSystemFeature("cn.google"));
                }
                DeviceProperties.zze.booleanValue();
            }
        }
        StringBuilder sb = new StringBuilder("gcore_");
        sb.append(GOOGLE_PLAY_SERVICES_VERSION_CODE);
        sb.append("-");
        if (!TextUtils.isEmpty(str)) {
            sb.append(str);
        }
        sb.append("-");
        if (context != null) {
            sb.append(context.getPackageName());
        }
        sb.append("-");
        if (context != null) {
            try {
                PackageManagerWrapper zza = Wrappers.zza.zza(context);
                sb.append(
                        zza.zza
                                .getPackageManager()
                                .getPackageInfo(context.getPackageName(), 0)
                                .versionCode);
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        String sb2 = sb.toString();
        int i3 = zzt.$r8$clinit;
        Intent intent2 = new Intent("android.intent.action.VIEW");
        Uri.Builder appendQueryParameter =
                Uri.parse("market://details")
                        .buildUpon()
                        .appendQueryParameter("id", "com.google.android.gms");
        if (!TextUtils.isEmpty(sb2)) {
            appendQueryParameter.appendQueryParameter("pcampaignid", sb2);
        }
        intent2.setData(appendQueryParameter.build());
        intent2.setPackage("com.android.vending");
        intent2.addFlags(NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
        return intent2;
    }

    /* JADX WARN: Removed duplicated region for block: B:87:0x023f  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0249 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x024a A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int isGooglePlayServicesAvailable(android.content.Context r12, int r13) {
        /*
            Method dump skipped, instructions count: 593
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.android.gms.common.GoogleApiAvailabilityLight.isGooglePlayServicesAvailable(android.content.Context,"
                    + " int):int");
    }
}
