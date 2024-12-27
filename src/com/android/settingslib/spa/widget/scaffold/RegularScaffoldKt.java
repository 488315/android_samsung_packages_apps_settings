package com.android.settingslib.spa.widget.scaffold;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class RegularScaffoldKt {
    /* JADX WARN: Removed duplicated region for block: B:10:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:21:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x004f  */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.settingslib.spa.widget.scaffold.RegularScaffoldKt$RegularScaffold$1, kotlin.jvm.internal.Lambda] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void RegularScaffold(
            final java.lang.String r11,
            kotlin.jvm.functions.Function3 r12,
            final kotlin.jvm.functions.Function2 r13,
            androidx.compose.runtime.Composer r14,
            final int r15,
            final int r16) {
        /*
            r6 = r11
            r7 = r13
            r8 = r15
            r0 = 2
            r1 = 4
            java.lang.String r2 = "title"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r11, r2)
            java.lang.String r2 = "content"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r13, r2)
            r9 = r14
            androidx.compose.runtime.ComposerImpl r9 = (androidx.compose.runtime.ComposerImpl) r9
            r2 = -83667852(0xfffffffffb035474, float:-6.819038E35)
            r9.startRestartGroup(r2)
            r2 = r16 & 1
            if (r2 == 0) goto L1f
            r2 = r8 | 6
            goto L2f
        L1f:
            r2 = r8 & 14
            if (r2 != 0) goto L2e
            boolean r2 = r9.changed(r11)
            if (r2 == 0) goto L2b
            r2 = r1
            goto L2c
        L2b:
            r2 = r0
        L2c:
            r2 = r2 | r8
            goto L2f
        L2e:
            r2 = r8
        L2f:
            r0 = r16 & 2
            if (r0 == 0) goto L37
            r2 = r2 | 48
        L35:
            r3 = r12
            goto L48
        L37:
            r3 = r8 & 112(0x70, float:1.57E-43)
            if (r3 != 0) goto L35
            r3 = r12
            boolean r4 = r9.changedInstance(r12)
            if (r4 == 0) goto L45
            r4 = 32
            goto L47
        L45:
            r4 = 16
        L47:
            r2 = r2 | r4
        L48:
            r1 = r16 & 4
            if (r1 == 0) goto L4f
            r2 = r2 | 384(0x180, float:5.38E-43)
            goto L5f
        L4f:
            r1 = r8 & 896(0x380, float:1.256E-42)
            if (r1 != 0) goto L5f
            boolean r1 = r9.changedInstance(r13)
            if (r1 == 0) goto L5c
            r1 = 256(0x100, float:3.59E-43)
            goto L5e
        L5c:
            r1 = 128(0x80, float:1.794E-43)
        L5e:
            r2 = r2 | r1
        L5f:
            r1 = r2 & 731(0x2db, float:1.024E-42)
            r4 = 146(0x92, float:2.05E-43)
            if (r1 != r4) goto L71
            boolean r1 = r9.getSkipping()
            if (r1 != 0) goto L6c
            goto L71
        L6c:
            r9.skipToGroupEnd()
            r2 = r3
            goto L97
        L71:
            if (r0 == 0) goto L77
            androidx.compose.runtime.internal.ComposableLambdaImpl r0 = com.android.settingslib.spa.widget.scaffold.ComposableSingletons$RegularScaffoldKt.f89lambda1
            r10 = r0
            goto L78
        L77:
            r10 = r3
        L78:
            androidx.compose.runtime.OpaqueKey r0 = androidx.compose.runtime.ComposerKt.invocation
            com.android.settingslib.spa.widget.scaffold.RegularScaffoldKt$RegularScaffold$1 r0 = new com.android.settingslib.spa.widget.scaffold.RegularScaffoldKt$RegularScaffold$1
            r0.<init>()
            r1 = 646001087(0x268131bf, float:8.964657E-16)
            androidx.compose.runtime.internal.ComposableLambdaImpl r3 = androidx.compose.runtime.internal.ComposableLambdaKt.rememberComposableLambda(r1, r0, r9)
            r0 = r2 & 14
            r0 = r0 | 384(0x180, float:5.38E-43)
            r1 = r2 & 112(0x70, float:1.57E-43)
            r4 = r0 | r1
            r5 = 0
            r0 = r11
            r1 = r10
            r2 = r3
            r3 = r9
            com.android.settingslib.spa.widget.scaffold.SettingsScaffoldKt.SettingsScaffold(r0, r1, r2, r3, r4, r5)
            r2 = r10
        L97:
            androidx.compose.runtime.RecomposeScopeImpl r9 = r9.endRestartGroup()
            if (r9 == 0) goto Laa
            com.android.settingslib.spa.widget.scaffold.RegularScaffoldKt$RegularScaffold$2 r10 = new com.android.settingslib.spa.widget.scaffold.RegularScaffoldKt$RegularScaffold$2
            r0 = r10
            r1 = r11
            r3 = r13
            r4 = r15
            r5 = r16
            r0.<init>()
            r9.block = r10
        Laa:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.spa.widget.scaffold.RegularScaffoldKt.RegularScaffold(java.lang.String,"
                    + " kotlin.jvm.functions.Function3, kotlin.jvm.functions.Function2,"
                    + " androidx.compose.runtime.Composer, int, int):void");
    }
}
