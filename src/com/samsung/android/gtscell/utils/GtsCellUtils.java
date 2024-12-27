package com.samsung.android.gtscell.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.samsung.android.gtscell.GtsCellProvider;
import com.samsung.android.gtscell.log.GLogger;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        bv = {1, 0, 3},
        d1 = {
            "\u0000,\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u0000\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\u000e\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000b\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0010\u0010"
                + "\t\u001a\u00020\n"
                + "2\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\r"
                + "\u001a\u00020\u0004H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n"
                + "\u0000¨\u0006\u000e"
        },
        d2 = {
            "Lcom/samsung/android/gtscell/utils/GtsCellUtils;",
            ApnSettings.MVNO_NONE,
            "()V",
            "CATEGORY_GTS_CREATE",
            ApnSettings.MVNO_NONE,
            "getIntent",
            "Landroid/content/Intent;",
            "context",
            "Landroid/content/Context;",
            "isGtsAvailable",
            ApnSettings.MVNO_NONE,
            "launchGtsFromApp",
            ApnSettings.MVNO_NONE,
            "category",
            "gtscell_release"
        },
        k = 1,
        mv = {1, 1, 16})
/* loaded from: classes4.dex */
public final class GtsCellUtils {
    private static final String CATEGORY_GTS_CREATE = "com.samsung.android.gts.category.CREATE";
    public static final GtsCellUtils INSTANCE = new GtsCellUtils();

    private GtsCellUtils() {}

    private final Intent getIntent(Context context) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory(CATEGORY_GTS_CREATE);
        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        return intent;
    }

    public static final boolean isGtsAvailable(Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkExpressionValueIsNotNull(
                context.getPackageManager().queryIntentActivities(INSTANCE.getIntent(context), 0),
                "context.packageManager.q…es(getIntent(context), 0)");
        return !r2.isEmpty();
    }

    public static final void launchGtsFromApp(Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        try {
            Intent intent = INSTANCE.getIntent(context);
            intent.addFlags(NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            GLogger.INSTANCE.getGlobal().warning("launchGtsFromApp", e.getMessage());
        }
    }

    public static final void launchGtsFromApp(Context context, String category) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(category, "category");
        try {
            Intent intent = INSTANCE.getIntent(context);
            intent.addFlags(NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
            intent.putExtra(GtsCellProvider.EXTRA_TARGET_CATEGORY, category);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            GLogger.INSTANCE.getGlobal().warning("launchGtsFromApp", e.getMessage());
        }
    }
}
