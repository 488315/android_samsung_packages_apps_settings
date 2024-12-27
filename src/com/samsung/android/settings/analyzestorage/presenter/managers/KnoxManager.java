package com.samsung.android.settings.analyzestorage.presenter.managers;

import android.content.Context;
import android.os.Bundle;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class KnoxManager {
    public static volatile KnoxManager instance;
    public static Bundle knoxInfoBundle;
    public final int[] containerArray;
    public final Context context;
    public final boolean isKnoxMode;
    public static final Companion Companion = new Companion();
    public static final Lock knoxInfoLocker = new ReentrantLock();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Companion {}

    /* JADX WARN: Removed duplicated region for block: B:15:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:35:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public KnoxManager(android.content.Context r11) {
        /*
            r10 = this;
            r10.<init>()
            r0 = 5
            int[] r0 = new int[r0]
            r10.containerArray = r0
            android.content.Context r11 = r11.getApplicationContext()
            java.lang.String r1 = "getApplicationContext(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r11, r1)
            r10.context = r11
            java.lang.String r1 = "persona"
            java.lang.Object r1 = r11.getSystemService(r1)
            java.lang.String r2 = "null cannot be cast to non-null type com.samsung.android.knox.SemPersonaManager"
            kotlin.jvm.internal.Intrinsics.checkNotNull(r1, r2)
            com.samsung.android.knox.SemPersonaManager r1 = (com.samsung.android.knox.SemPersonaManager) r1
            java.util.ArrayList r2 = r1.getMoveToKnoxMenuList(r11)
            r3 = 0
            if (r2 == 0) goto L30
            java.lang.Object r2 = kotlin.collections.CollectionsKt___CollectionsKt.firstOrNull(r2)
            android.os.Bundle r2 = (android.os.Bundle) r2
            goto L31
        L30:
            r2 = r3
        L31:
            java.lang.String r4 = "com.sec.knox.moveto.containerId"
            java.lang.String r5 = "com.sec.knox.moveto.containerType"
            r6 = 1
            java.lang.String r7 = "KnoxManager"
            if (r2 == 0) goto L6e
            int r8 = r2.getInt(r5)
            r9 = 1003(0x3eb, float:1.406E-42)
            if (r8 == r9) goto L4a
            r9 = 1004(0x3ec, float:1.407E-42)
            if (r8 == r9) goto L48
            r9 = -1
            goto L4b
        L48:
            r9 = 2
            goto L4b
        L4a:
            r9 = 3
        L4b:
            if (r9 < 0) goto L6e
            int r2 = r2.getInt(r4)
            r0[r9] = r2
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = "isKnoxModeOn() ] getContainerType : "
            r0.<init>(r2)
            r0.append(r8)
            java.lang.String r2 = " index : "
            r0.append(r2)
            r0.append(r9)
            java.lang.String r0 = r0.toString()
            com.samsung.android.settings.analyzestorage.domain.log.Log.d(r7, r0)
            r0 = r6
            goto L6f
        L6e:
            r0 = 0
        L6f:
            r10.isKnoxMode = r0
            java.util.ArrayList r11 = r1.getMoveToKnoxMenuList(r11)
            java.lang.String r0 = "getMoveToKnoxMenuList(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r11, r0)
            boolean r0 = r11.isEmpty()
            if (r0 != 0) goto Lc0
            java.util.Iterator r11 = r11.iterator()
        L84:
            boolean r0 = r11.hasNext()
            if (r0 == 0) goto L9c
            java.lang.Object r0 = r11.next()
            r1 = r0
            android.os.Bundle r1 = (android.os.Bundle) r1
            if (r1 == 0) goto L84
            int r1 = r1.getInt(r5)
            r2 = 1002(0x3ea, float:1.404E-42)
            if (r1 != r2) goto L84
            goto L9d
        L9c:
            r0 = r3
        L9d:
            android.os.Bundle r0 = (android.os.Bundle) r0
            if (r0 == 0) goto Laf
            java.lang.String r11 = "com.sec.knox.moveto.name"
            java.lang.String r3 = r0.getString(r11)
            int[] r10 = r10.containerArray
            int r11 = r0.getInt(r4)
            r10[r6] = r11
        Laf:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            java.lang.String r11 = "getKnoxName ] moveToKnoxMenuList is not null and knox name is "
            r10.<init>(r11)
            r10.append(r3)
            java.lang.String r10 = r10.toString()
            com.samsung.android.settings.analyzestorage.domain.log.Log.d(r7, r10)
        Lc0:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.analyzestorage.presenter.managers.KnoxManager.<init>(android.content.Context):void");
    }
}
