package com.samsung.android.settings.privacy;

import android.content.Context;

import androidx.core.util.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class SecurityDashboardUtils$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ Context f$0;
    public final /* synthetic */ Consumer f$1;

    public /* synthetic */ SecurityDashboardUtils$$ExternalSyntheticLambda0(
            Context context, Consumer consumer) {
        this.f$0 = context;
        this.f$1 = consumer;
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0058, code lost:

       if (r2 == 1) goto L24;
    */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void run() {
        /*
            r6 = this;
            android.content.Context r0 = r6.f$0
            androidx.core.util.Consumer r6 = r6.f$1
            java.lang.String r1 = "Samsung Account Get Security Info Authority need security alert"
            boolean r2 = com.samsung.android.settings.account.AccountUtils.isSamsungAccountExists(r0)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "getSamsungAccountSecurityInfo: checkIfSALoggedIn = "
            r3.<init>(r4)
            r3.append(r2)
            java.lang.String r3 = r3.toString()
            java.lang.String r4 = "SecurityDashboardUtils"
            com.samsung.android.util.SemLog.d(r4, r3)
            r3 = -1
            if (r2 == 0) goto L85
            android.content.ContentResolver r0 = r0.getContentResolver()
            java.lang.String r2 = "content://com.samsung.android.samsungaccount.securityinfoprovider/security_info"
            android.net.Uri r2 = android.net.Uri.parse(r2)     // Catch: java.lang.Exception -> L65
            r5 = 0
            android.database.Cursor r0 = r0.query(r2, r5, r5, r5)     // Catch: java.lang.Exception -> L65
            if (r0 == 0) goto L67
            int r2 = r0.getCount()     // Catch: java.lang.Throwable -> L5b
            if (r2 == 0) goto L67
            boolean r2 = r0.moveToFirst()     // Catch: java.lang.Throwable -> L5b
            if (r2 == 0) goto L67
            java.lang.String r2 = "need_security_alert"
            int r2 = r0.getColumnIndex(r2)     // Catch: java.lang.Throwable -> L5b
            int r2 = r0.getInt(r2)     // Catch: java.lang.Throwable -> L5b
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L5b
            r5.<init>(r1)     // Catch: java.lang.Throwable -> L5b
            r5.append(r2)     // Catch: java.lang.Throwable -> L5b
            java.lang.String r1 = r5.toString()     // Catch: java.lang.Throwable -> L5b
            com.samsung.android.util.SemLog.d(r4, r1)     // Catch: java.lang.Throwable -> L5b
            r1 = 1
            if (r2 != r1) goto L67
            goto L68
        L5b:
            r1 = move-exception
            r0.close()     // Catch: java.lang.Throwable -> L60
            goto L64
        L60:
            r0 = move-exception
            r1.addSuppressed(r0)     // Catch: java.lang.Exception -> L65
        L64:
            throw r1     // Catch: java.lang.Exception -> L65
        L65:
            r0 = move-exception
            goto L6e
        L67:
            r1 = 0
        L68:
            if (r0 == 0) goto L86
            r0.close()     // Catch: java.lang.Exception -> L65
            goto L86
        L6e:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "Samsung Account Get Security Info Authority is Invalid"
            r1.<init>(r2)
            java.lang.String r0 = r0.getMessage()
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            com.samsung.android.util.SemLog.e(r4, r0)
            r1 = r3
            goto L86
        L85:
            r1 = 2
        L86:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = "getSamsungAccountSecurityInfo: resultStatus = "
            r0.<init>(r2)
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            com.samsung.android.util.SemLog.d(r4, r0)
            if (r1 != r3) goto La4
            java.lang.String r0 = "getStatusForSamsungAccountCheckupMenu returned Status FAILED"
            com.samsung.android.util.SemLog.d(r4, r0)
            com.samsung.android.settings.privacy.SecurityDashboardConstants$Status r0 = com.samsung.android.settings.privacy.SecurityDashboardConstants$Status.NONE
            r6.accept(r0)
            goto Lbb
        La4:
            if (r1 != 0) goto Lb1
            java.lang.String r0 = "getStatusForSamsungAccountCheckupMenu returned Status OK"
            com.samsung.android.util.SemLog.d(r4, r0)
            com.samsung.android.settings.privacy.SecurityDashboardConstants$Status r0 = com.samsung.android.settings.privacy.SecurityDashboardConstants$Status.OK
            r6.accept(r0)
            goto Lbb
        Lb1:
            java.lang.String r0 = "getStatusForSamsungAccountCheckupMenu returned Status WARNING"
            com.samsung.android.util.SemLog.d(r4, r0)
            com.samsung.android.settings.privacy.SecurityDashboardConstants$Status r0 = com.samsung.android.settings.privacy.SecurityDashboardConstants$Status.WARNING
            r6.accept(r0)
        Lbb:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.privacy.SecurityDashboardUtils$$ExternalSyntheticLambda0.run():void");
    }
}
