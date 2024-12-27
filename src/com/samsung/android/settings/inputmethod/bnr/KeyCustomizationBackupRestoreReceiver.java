package com.samsung.android.settings.inputmethod.bnr;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.util.Log;
import android.view.WindowManagerGlobal;

import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class KeyCustomizationBackupRestoreReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        final List list;
        String action = intent.getAction();
        DialogFragment$$ExternalSyntheticOutline0.m(
                "onReceive : ", action, "KeyCustomizationBackupRestore");
        if (action == null) {
            return;
        }
        final KeyCustomizationBackupRestoreManager keyCustomizationBackupRestoreManager =
                new KeyCustomizationBackupRestoreManager(context);
        boolean equals =
                "com.samsung.android.intent.action.REQUEST_BACKUP_HOT_KEY"
                        .equals(intent.getAction());
        Constants$ERROR_CODE constants$ERROR_CODE = Constants$ERROR_CODE.UNKNOWN_ERROR;
        Constants$RESULT_CODE constants$RESULT_CODE = Constants$RESULT_CODE.RESULT_FAIL;
        if (!equals) {
            if ("com.samsung.android.intent.action.REQUEST_RESTORE_HOT_KEY"
                    .equals(intent.getAction())) {
                final List pathUris = DocumentStorageAccessHelper.getPathUris(context, intent);
                if (pathUris == null || ((ArrayList) pathUris).size() <= 0) {
                    keyCustomizationBackupRestoreManager.sendResponseToSmartSwitch(
                            "com.samsung.android.intent.action.RESPONSE_BACKUP_HOT_KEY",
                            constants$RESULT_CODE,
                            constants$ERROR_CODE,
                            intent.getStringExtra("SOURCE"),
                            intent.getStringExtra("EXPORT_SESSION_TIME"));
                    return;
                }
                final String stringExtra = intent.getStringExtra("SOURCE");
                final String stringExtra2 = intent.getStringExtra("SAVE_PATH");
                final String stringExtra3 = intent.getStringExtra("SESSION_KEY");
                final int intExtra = intent.getIntExtra("SECURITY_LEVEL", 0);
                new Thread(
                                new Runnable() { // from class:
                                    // com.samsung.android.settings.inputmethod.bnr.KeyCustomizationBackupRestoreManager$$ExternalSyntheticLambda1
                                    /* JADX WARN: Can't wrap try/catch for region: R(14:(26:125|(2:127|(1:129)(1:130))(1:132)|131|25|26|27|28|29|(2:30|(2:32|33)(1:34))|35|36|37|38|39|40|41|42|(2:43|(2:47|48))|51|52|(9:61|(4:63|64|65|66)|69|(1:71)(1:(1:75))|72|55|(1:57)(1:60)|58|59)|54|55|(0)(0)|58|59)|39|40|41|42|(2:43|(1:80)(3:45|47|48))|51|52|(0)|54|55|(0)(0)|58|59) */
                                    /* JADX WARN: Code restructure failed: missing block: B:100:0x015d, code lost:

                                       r2 = null;
                                    */
                                    /* JADX WARN: Code restructure failed: missing block: B:101:0x0159, code lost:

                                       r0 = th;
                                    */
                                    /* JADX WARN: Code restructure failed: missing block: B:83:0x017e, code lost:

                                       android.util.Log.d("KeyCustomizationBackupRestore", "File not found " + r11);
                                    */
                                    /* JADX WARN: Code restructure failed: missing block: B:85:0x017a, code lost:

                                       r2.close();
                                    */
                                    /* JADX WARN: Code restructure failed: missing block: B:88:0x0150, code lost:

                                       r0 = e;
                                    */
                                    /* JADX WARN: Code restructure failed: missing block: B:89:0x0161, code lost:

                                       android.util.Log.wtf("KeyCustomizationBackupRestore", "Unable to parse " + r11 + ". ", r0);
                                    */
                                    /* JADX WARN: Code restructure failed: missing block: B:98:0x015f, code lost:

                                       r2 = null;
                                    */
                                    /* JADX WARN: Code restructure failed: missing block: B:99:0x015c, code lost:

                                       r0 = e;
                                    */
                                    /* JADX WARN: Multi-variable type inference failed */
                                    /* JADX WARN: Removed duplicated region for block: B:32:0x00f0 A[Catch: all -> 0x00f6, TRY_LEAVE, TryCatch #14 {all -> 0x00f6, blocks: (B:29:0x00e7, B:30:0x00e9, B:32:0x00f0), top: B:28:0x00e7, outer: #8 }] */
                                    /* JADX WARN: Removed duplicated region for block: B:34:0x00fa A[EDGE_INSN: B:34:0x00fa->B:35:0x00fa BREAK  A[LOOP:0: B:30:0x00e9->B:33:0x00f3], SYNTHETIC] */
                                    /* JADX WARN: Removed duplicated region for block: B:57:0x01d5  */
                                    /* JADX WARN: Removed duplicated region for block: B:60:0x01d7  */
                                    /* JADX WARN: Removed duplicated region for block: B:61:0x0197  */
                                    /* JADX WARN: Type inference failed for: r0v2, types: [java.lang.Throwable] */
                                    /* JADX WARN: Type inference failed for: r15v0 */
                                    /* JADX WARN: Type inference failed for: r15v1, types: [java.io.BufferedReader] */
                                    /* JADX WARN: Type inference failed for: r15v2 */
                                    @Override // java.lang.Runnable
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                                    */
                                    public final void run() {
                                        /*
                                            Method dump skipped, instructions count: 549
                                            To view this dump change 'Code comments level' option to 'DEBUG'
                                        */
                                        throw new UnsupportedOperationException(
                                                "Method not decompiled:"
                                                    + " com.samsung.android.settings.inputmethod.bnr.KeyCustomizationBackupRestoreManager$$ExternalSyntheticLambda1.run():void");
                                    }
                                })
                        .start();
                return;
            }
            return;
        }
        final String stringExtra4 = intent.getStringExtra("SAVE_PATH");
        final String stringExtra5 = intent.getStringExtra("SOURCE");
        final String stringExtra6 = intent.getStringExtra("EXPORT_SESSION_TIME");
        int intExtra2 = intent.getIntExtra("ACTION", 0);
        final String stringExtra7 = intent.getStringExtra("SESSION_KEY");
        final int intExtra3 = intent.getIntExtra("SECURITY_LEVEL", 0);
        if (intExtra2 == 2) {
            Thread thread = keyCustomizationBackupRestoreManager.mBackupThread;
            if (thread == null || !thread.isAlive()) {
                return;
            }
            keyCustomizationBackupRestoreManager.mBackupThread.interrupt();
            keyCustomizationBackupRestoreManager.mBackupThread = null;
            Log.d("KeyCustomizationBackupRestore", "canceled backupThread");
            return;
        }
        try {
            list =
                    WindowManagerGlobal.getWindowManagerService()
                            .getBackupKeyCustomizationInfoList();
        } catch (RemoteException e) {
            Log.d("KeyCustomizationBackupRestore", "Cant getBackupKeyCustomizatioInfoList. ", e);
            list = null;
        }
        if (list == null || list.size() == 0) {
            Log.d("KeyCustomizationBackupRestore", "There is no key info.");
            keyCustomizationBackupRestoreManager.sendResponseToSmartSwitch(
                    "com.samsung.android.intent.action.RESPONSE_BACKUP_HOT_KEY",
                    constants$RESULT_CODE,
                    Constants$ERROR_CODE.INVALID_DATA,
                    stringExtra5,
                    stringExtra6);
            return;
        }
        final List pathUris2 =
                DocumentStorageAccessHelper.getPathUris(
                        keyCustomizationBackupRestoreManager.mContext, intent);
        if (pathUris2 == null || ((ArrayList) pathUris2).isEmpty()) {
            keyCustomizationBackupRestoreManager.sendResponseToSmartSwitch(
                    "com.samsung.android.intent.action.RESPONSE_BACKUP_HOT_KEY",
                    constants$RESULT_CODE,
                    constants$ERROR_CODE,
                    intent.getStringExtra("SOURCE"),
                    intent.getStringExtra("EXPORT_SESSION_TIME"));
            return;
        }
        Thread thread2 =
                new Thread(
                        new Runnable() { // from class:
                            // com.samsung.android.settings.inputmethod.bnr.KeyCustomizationBackupRestoreManager$$ExternalSyntheticLambda0
                            /* JADX WARN: Multi-variable type inference failed */
                            /* JADX WARN: Removed duplicated region for block: B:54:0x01a2  */
                            /* JADX WARN: Removed duplicated region for block: B:57:0x01a5  */
                            /* JADX WARN: Type inference failed for: r11v0 */
                            /* JADX WARN: Type inference failed for: r11v1, types: [java.io.FileOutputStream] */
                            /* JADX WARN: Type inference failed for: r11v10 */
                            /* JADX WARN: Type inference failed for: r11v11, types: [int] */
                            /* JADX WARN: Type inference failed for: r11v12 */
                            /* JADX WARN: Type inference failed for: r11v13 */
                            /* JADX WARN: Type inference failed for: r11v16 */
                            /* JADX WARN: Type inference failed for: r11v17 */
                            /* JADX WARN: Type inference failed for: r11v18 */
                            /* JADX WARN: Type inference failed for: r11v19 */
                            /* JADX WARN: Type inference failed for: r11v2 */
                            /* JADX WARN: Type inference failed for: r11v3, types: [java.io.FileOutputStream] */
                            /* JADX WARN: Type inference failed for: r11v4 */
                            /* JADX WARN: Type inference failed for: r11v5, types: [java.io.FileOutputStream] */
                            /* JADX WARN: Type inference failed for: r11v6 */
                            /* JADX WARN: Type inference failed for: r11v8 */
                            /* JADX WARN: Type inference failed for: r11v9 */
                            /* JADX WARN: Type inference failed for: r14v1, types: [java.lang.String] */
                            /* JADX WARN: Type inference failed for: r14v10 */
                            /* JADX WARN: Type inference failed for: r14v11 */
                            /* JADX WARN: Type inference failed for: r14v12 */
                            /* JADX WARN: Type inference failed for: r14v13 */
                            /* JADX WARN: Type inference failed for: r14v16 */
                            /* JADX WARN: Type inference failed for: r14v17 */
                            /* JADX WARN: Type inference failed for: r14v18 */
                            /* JADX WARN: Type inference failed for: r14v19 */
                            /* JADX WARN: Type inference failed for: r14v2, types: [java.lang.String] */
                            /* JADX WARN: Type inference failed for: r14v3 */
                            /* JADX WARN: Type inference failed for: r14v4 */
                            /* JADX WARN: Type inference failed for: r14v5 */
                            /* JADX WARN: Type inference failed for: r14v7 */
                            /* JADX WARN: Type inference failed for: r14v8 */
                            /* JADX WARN: Type inference failed for: r14v9 */
                            /* JADX WARN: Type inference failed for: r2v8, types: [android.util.AtomicFile, java.lang.Object] */
                            /* JADX WARN: Type inference failed for: r3v3, types: [java.lang.StringBuilder] */
                            /* JADX WARN: Type inference failed for: r3v8, types: [java.io.OutputStream] */
                            /* JADX WARN: Type inference failed for: r9v10 */
                            /* JADX WARN: Type inference failed for: r9v11, types: [java.io.FileInputStream, java.io.InputStream] */
                            /* JADX WARN: Type inference failed for: r9v14 */
                            /* JADX WARN: Type inference failed for: r9v15 */
                            /* JADX WARN: Type inference failed for: r9v16 */
                            /* JADX WARN: Type inference failed for: r9v17 */
                            /* JADX WARN: Type inference failed for: r9v4, types: [java.lang.String] */
                            /* JADX WARN: Type inference failed for: r9v5, types: [java.lang.String] */
                            /* JADX WARN: Type inference failed for: r9v6 */
                            /* JADX WARN: Type inference failed for: r9v7 */
                            /* JADX WARN: Type inference failed for: r9v8 */
                            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:95:0x0164 -> B:55:0x0161). Please report as a decompilation issue!!! */
                            @Override // java.lang.Runnable
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public final void run() {
                                /*
                                    Method dump skipped, instructions count: 430
                                    To view this dump change 'Code comments level' option to 'DEBUG'
                                */
                                throw new UnsupportedOperationException(
                                        "Method not decompiled:"
                                            + " com.samsung.android.settings.inputmethod.bnr.KeyCustomizationBackupRestoreManager$$ExternalSyntheticLambda0.run():void");
                            }
                        });
        keyCustomizationBackupRestoreManager.mBackupThread = thread2;
        thread2.start();
    }
}
