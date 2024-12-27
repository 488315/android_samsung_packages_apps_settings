package com.android.settings;

import android.app.Activity;

import kotlin.Metadata;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"
        },
        d2 = {
            "Lcom/android/settings/RegulatoryInfoDisplayActivity;",
            "Landroid/app/Activity;",
            "<init>",
            "()V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes.dex */
public final class RegulatoryInfoDisplayActivity extends Activity {
    /* JADX WARN: Code restructure failed: missing block: B:29:0x007a, code lost:

       if (r3 != null) goto L13;
    */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x006f, code lost:

       if (r3 != null) goto L13;
    */
    @Override // android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onCreate(android.os.Bundle r8) {
        /*
            r7 = this;
            super.onCreate(r8)
            androidx.appcompat.app.AlertDialog$Builder r8 = new androidx.appcompat.app.AlertDialog$Builder
            r8.<init>(r7)
            r0 = 2132024914(0x7f141e52, float:1.9688318E38)
            r8.setTitle(r0)
            com.android.settings.RegulatoryInfoDisplayActivity$onCreate$builder$1 r0 = new com.android.settings.RegulatoryInfoDisplayActivity$onCreate$builder$1
            r0.<init>()
            androidx.appcompat.app.AlertController$AlertParams r1 = r8.P
            r1.mOnDismissListener = r0
            r0 = 17039370(0x104000a, float:2.42446E-38)
            r2 = 0
            r8.setPositiveButton(r0, r2)
            java.lang.String r0 = "ro.boot.hardware.sku"
            java.lang.String r0 = android.os.SystemProperties.get(r0)
            java.lang.String r3 = "get(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r3)
            java.util.Locale r4 = java.util.Locale.ROOT
            java.lang.String r0 = r0.toLowerCase(r4)
            java.lang.String r5 = "toLowerCase(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r5)
            boolean r6 = kotlin.text.StringsKt__StringsJVMKt.isBlank(r0)
            r6 = r6 ^ 1
            if (r6 == 0) goto L7d
            java.lang.String r6 = "ro.boot.hardware.coo"
            java.lang.String r6 = android.os.SystemProperties.get(r6)
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r6, r3)
            java.lang.String r3 = r6.toLowerCase(r4)
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r5)
            boolean r4 = kotlin.text.StringsKt__StringsJVMKt.isBlank(r3)
            r4 = r4 ^ 1
            java.lang.String r5 = "regulatory_info_"
            if (r4 == 0) goto L72
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>(r5)
            r4.append(r0)
            java.lang.String r6 = "_"
            r4.append(r6)
            r4.append(r3)
            java.lang.String r3 = r4.toString()
            android.graphics.drawable.Drawable r3 = com.android.settings.deviceinfo.regulatory.RegulatoryInfo.getRegulatoryInfo(r7, r3)
            if (r3 == 0) goto L72
            goto L83
        L72:
            java.lang.String r0 = r5.concat(r0)
            android.graphics.drawable.Drawable r3 = com.android.settings.deviceinfo.regulatory.RegulatoryInfo.getRegulatoryInfo(r7, r0)
            if (r3 == 0) goto L7d
            goto L83
        L7d:
            java.lang.String r0 = "regulatory_info"
            android.graphics.drawable.Drawable r3 = com.android.settings.deviceinfo.regulatory.RegulatoryInfo.getRegulatoryInfo(r7, r0)
        L83:
            if (r3 == 0) goto La8
            android.view.LayoutInflater r7 = r7.getLayoutInflater()
            r0 = 2131559879(0x7f0d05c7, float:1.8745115E38)
            android.view.View r7 = r7.inflate(r0, r2)
            r0 = 2131364946(0x7f0a0c52, float:1.8349743E38)
            android.view.View r0 = r7.requireViewById(r0)
            java.lang.String r1 = "requireViewById(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r1)
            android.widget.ImageView r0 = (android.widget.ImageView) r0
            r0.setImageDrawable(r3)
            r8.setView(r7)
            r8.show()
            return
        La8:
            android.content.res.Resources r0 = r7.getResources()
            r3 = 2132024911(0x7f141e4f, float:1.9688311E38)
            java.lang.CharSequence r0 = r0.getText(r3)
            java.lang.String r3 = "getText(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r3)
            boolean r3 = kotlin.text.StringsKt__StringsJVMKt.isBlank(r0)
            r3 = r3 ^ 1
            if (r3 == 0) goto Lc2
            r2 = r0
            goto Lc6
        Lc2:
            com.android.settings.overlay.FeatureFactoryImpl r0 = com.android.settings.overlay.FeatureFactoryImpl._factory
            if (r0 == 0) goto Leb
        Lc6:
            if (r2 == 0) goto Le7
            int r0 = r2.length()
            if (r0 != 0) goto Lcf
            goto Le7
        Lcf:
            r1.mMessage = r2
            androidx.appcompat.app.AlertDialog r7 = r8.show()
            r8 = 16908299(0x102000b, float:2.387726E-38)
            android.view.View r7 = r7.findViewById(r8)
            android.widget.TextView r7 = (android.widget.TextView) r7
            if (r7 != 0) goto Le1
            goto Lea
        Le1:
            r8 = 17
            r7.setGravity(r8)
            goto Lea
        Le7:
            r7.finish()
        Lea:
            return
        Leb:
            java.lang.UnsupportedOperationException r7 = new java.lang.UnsupportedOperationException
            java.lang.String r8 = "No feature factory configured"
            r7.<init>(r8)
            throw r7
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.RegulatoryInfoDisplayActivity.onCreate(android.os.Bundle):void");
    }
}
