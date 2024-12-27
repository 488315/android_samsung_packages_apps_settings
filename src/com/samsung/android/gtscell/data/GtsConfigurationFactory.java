package com.samsung.android.gtscell.data;

import android.content.Context;
import android.os.Build;
import android.os.SemSystemProperties;

import com.samsung.android.gtscell.utils.PackageManagerUtils;
import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        bv = {1, 0, 3},
        d1 = {
            "\u0000 \n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u0000\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b\u0005\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010"
                + "\t\u001a\u00020\n"
                + "2\u0006\u0010\u000b\u001a\u00020\fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n"
                + "\u0000¨\u0006\r"
        },
        d2 = {
            "Lcom/samsung/android/gtscell/data/GtsConfigurationFactory;",
            ApnSettings.MVNO_NONE,
            "()V",
            "RO_BUILD_CHARACTERISTICS",
            ApnSettings.MVNO_NONE,
            "RO_CSC_COUNTRY_CODE",
            "RO_CSC_SALES_CODE",
            "RO_DEVICE_NAME",
            "RO_PRODUCT_NAME",
            "make",
            "Lcom/samsung/android/gtscell/data/GtsConfiguration;",
            "context",
            "Landroid/content/Context;",
            "gtscell_release"
        },
        k = 1,
        mv = {1, 1, 16})
/* loaded from: classes4.dex */
public final class GtsConfigurationFactory {
    public static final GtsConfigurationFactory INSTANCE = new GtsConfigurationFactory();
    private static final String RO_BUILD_CHARACTERISTICS = "ro.build.characteristics";
    private static final String RO_CSC_COUNTRY_CODE = "ro.csc.country_code";
    private static final String RO_CSC_SALES_CODE = "ro.csc.sales_code";
    private static final String RO_DEVICE_NAME = "ro.product.vendor.device";
    private static final String RO_PRODUCT_NAME = "ro.product.name";

    private GtsConfigurationFactory() {}

    public final GtsConfiguration make(Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        String packageName = context.getPackageName();
        PackageManagerUtils packageManagerUtils = PackageManagerUtils.INSTANCE;
        Intrinsics.checkExpressionValueIsNotNull(packageName, "packageName");
        long versionCode = packageManagerUtils.getVersionCode(context, packageName);
        String versionName = packageManagerUtils.getVersionName(context, packageName);
        String str = SemSystemProperties.get(RO_PRODUCT_NAME);
        String str2 = str != null ? str : ApnSettings.MVNO_NONE;
        String str3 = SemSystemProperties.get(RO_DEVICE_NAME);
        String str4 = str3 != null ? str3 : ApnSettings.MVNO_NONE;
        String str5 = SemSystemProperties.get(RO_CSC_COUNTRY_CODE);
        String str6 = str5 != null ? str5 : ApnSettings.MVNO_NONE;
        String str7 = SemSystemProperties.get(RO_CSC_SALES_CODE);
        String str8 = str7 != null ? str7 : ApnSettings.MVNO_NONE;
        String str9 = SemSystemProperties.get(RO_BUILD_CHARACTERISTICS, ApnSettings.MVNO_NONE);
        Intrinsics.checkExpressionValueIsNotNull(
                str9, "SemSystemProperties.get(…UILD_CHARACTERISTICS, \"\")");
        return new GtsConfiguration(
                packageName,
                versionCode,
                versionName,
                str2,
                str4,
                str6,
                str8,
                str9,
                Build.VERSION.SDK_INT,
                Build.VERSION.SEM_PLATFORM_INT,
                null,
                1024,
                null);
    }
}
