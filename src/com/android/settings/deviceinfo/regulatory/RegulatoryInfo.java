package com.android.settings.deviceinfo.regulatory;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.android.settings.R;

import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class RegulatoryInfo {
    public static Drawable getRegulatoryInfo(Context context, String str) {
        String string =
                context.getResources()
                        .getString(R.string.config_regulatory_info_overlay_package_name);
        if (StringsKt__StringsJVMKt.isBlank(string)) {
            string = context.getPackageName();
        }
        Resources resourcesForApplication =
                context.getPackageManager().getResourcesForApplication(string);
        Intrinsics.checkNotNullExpressionValue(
                resourcesForApplication, "getResourcesForApplication(...)");
        int identifier = resourcesForApplication.getIdentifier(str, "drawable", string);
        if (identifier <= 0) {
            return null;
        }
        try {
            Drawable drawable = resourcesForApplication.getDrawable(identifier, null);
            if (drawable.getIntrinsicWidth() <= 10) {
                return null;
            }
            if (drawable.getIntrinsicHeight() > 10) {
                return drawable;
            }
            return null;
        } catch (Resources.NotFoundException unused) {
            return null;
        }
    }

    public static /* synthetic */ void getKEY_COO$annotations() {}

    public static /* synthetic */ void getKEY_SKU$annotations() {}
}
