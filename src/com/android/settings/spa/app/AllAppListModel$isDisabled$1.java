package com.android.settings.spa.app;

import android.content.pm.ApplicationInfo;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0010\n"
                + "\u0000\n"
                + "\u0002\u0010\u000b\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n"
                + "¢\u0006\u0004\b\u0004\u0010\u0005"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "it",
            "Lcom/android/settings/spa/app/AppRecordWithSize;",
            "invoke",
            "(Lcom/android/settings/spa/app/AppRecordWithSize;)Ljava/lang/Boolean;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class AllAppListModel$isDisabled$1 extends Lambda implements Function1 {
    public static final AllAppListModel$isDisabled$1 INSTANCE = new AllAppListModel$isDisabled$1();

    public AllAppListModel$isDisabled$1() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        AppRecordWithSize it = (AppRecordWithSize) obj;
        Intrinsics.checkNotNullParameter(it, "it");
        ApplicationInfo applicationInfo = it.app;
        return Boolean.valueOf(
                (applicationInfo.enabled || applicationInfo.isInstantApp()) ? false : true);
    }
}
