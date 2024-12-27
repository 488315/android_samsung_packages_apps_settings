package com.samsung.android.settings.knox;

import android.app.Activity;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ChooseLockGenericBridge extends Activity {
    /* JADX WARN: Code restructure failed: missing block: B:8:0x001f, code lost:

       if (r5.equals(com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG) != false) goto L9;
    */
    @Override // android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onCreate(android.os.Bundle r5) {
        /*
            r4 = this;
            super.onCreate(r5)
            android.app.IActivityManager r5 = android.app.ActivityManager.getService()     // Catch: java.lang.Exception -> L7d
            android.os.IBinder r0 = r4.getActivityToken()     // Catch: java.lang.Exception -> L7d
            java.lang.String r5 = r5.getLaunchedFromPackage(r0)     // Catch: java.lang.Exception -> L7d
            if (r5 == 0) goto L7d
            java.lang.String r0 = "com.samsung.knox.securefolder"
            boolean r0 = r5.equals(r0)     // Catch: java.lang.Exception -> L7d
            if (r0 != 0) goto L21
            java.lang.String r0 = "com.android.settings"
            boolean r5 = r5.equals(r0)     // Catch: java.lang.Exception -> L7d
            if (r5 == 0) goto L7d
        L21:
            android.content.Intent r5 = new android.content.Intent
            java.lang.Class<com.android.settings.password.ChooseLockGeneric$InternalActivity> r0 = com.android.settings.password.ChooseLockGeneric.InternalActivity.class
            r5.<init>(r4, r0)
            r0 = 33554432(0x2000000, float:9.403955E-38)
            r5.setFlags(r0)
            android.content.Intent r0 = r4.getIntent()
            java.lang.String r0 = r0.getAction()
            if (r0 == 0) goto L42
            android.content.Intent r0 = r4.getIntent()
            java.lang.String r0 = r0.getAction()
            r5.setAction(r0)
        L42:
            android.content.Intent r0 = r4.getIntent()
            android.os.Bundle r0 = r0.getExtras()
            if (r0 == 0) goto L57
            android.content.Intent r0 = r4.getIntent()
            android.os.Bundle r0 = r0.getExtras()
            r5.putExtras(r0)
        L57:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Calling from knox, user id = "
            r0.<init>(r1)
            android.content.Intent r1 = r4.getIntent()
            java.lang.String r2 = "android.intent.extra.USER_ID"
            r3 = -10000(0xffffffffffffd8f0, float:NaN)
            int r1 = r1.getIntExtra(r2, r3)
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "KKG::ChooseLockGenericBridge"
            android.util.Log.d(r1, r0)
            r4.startActivity(r5)
            r4.finish()
            return
        L7d:
            r4.finish()
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.knox.ChooseLockGenericBridge.onCreate(android.os.Bundle):void");
    }
}
