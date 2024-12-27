package com.samsung.android.settings.analyzestorage.external.bnr;

import android.content.Context;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class RestoreThread extends Thread {
    public final Context mContext;
    public final int mSecurityLevel;
    public final String mSessionTime;
    public final String mSource;
    public final String mSourcePath;

    public RestoreThread(Context context, BnrParam bnrParam) {
        this.mContext = context;
        this.mSourcePath = bnrParam.destinationPath;
        this.mSecurityLevel = bnrParam.securityLevel;
        this.mSource = bnrParam.requestFrom;
        this.mSessionTime = bnrParam.exportSessionTime;
        EncryptionUtils.streamCrypt(bnrParam.encryptionKey);
    }

    /* JADX WARN: Removed duplicated region for block: B:104:? A[Catch: SQLiteException | IllegalArgumentException -> 0x026b, SQLiteException | IllegalArgumentException -> 0x026b, SYNTHETIC, TRY_LEAVE, TryCatch #7 {SQLiteException | IllegalArgumentException -> 0x026b, blocks: (B:45:0x0267, B:103:0x0294, B:103:0x0294, B:102:0x0291, B:102:0x0291), top: B:29:0x01b8 }] */
    /* JADX WARN: Removed duplicated region for block: B:96:0x028b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // java.lang.Thread, java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void run() {
        /*
            Method dump skipped, instructions count: 751
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.analyzestorage.external.bnr.RestoreThread.run():void");
    }
}
