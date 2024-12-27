package com.google.android.gms.common.api;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class ApiException extends Exception {

    @Deprecated protected final Status mStatus;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ApiException(com.google.android.gms.common.api.Status r5) {
        /*
            r4 = this;
            int r0 = r5.zzc
            java.lang.String r1 = r5.zzd
            if (r1 == 0) goto L7
            goto L9
        L7:
            java.lang.String r1 = ""
        L9:
            java.lang.String r2 = java.lang.String.valueOf(r1)
            int r2 = r2.length()
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            int r2 = r2 + 13
            r3.<init>(r2)
            r3.append(r0)
            java.lang.String r0 = ": "
            r3.append(r0)
            r3.append(r1)
            java.lang.String r0 = r3.toString()
            r4.<init>(r0)
            r4.mStatus = r5
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.android.gms.common.api.ApiException.<init>(com.google.android.gms.common.api.Status):void");
    }

    public final Status getStatus() {
        return this.mStatus;
    }
}
