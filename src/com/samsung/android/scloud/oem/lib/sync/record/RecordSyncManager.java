package com.samsung.android.scloud.oem.lib.sync.record;

import android.content.Context;
import android.os.Bundle;

import com.samsung.android.scloud.oem.lib.common.IClientHelper;
import com.samsung.android.scloud.oem.lib.common.IServiceHandler;

import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class RecordSyncManager extends IClientHelper {
    public static final Map SERVICE_HANDLER_MAP;

    static {
        HashMap hashMap = new HashMap();
        SERVICE_HANDLER_MAP = hashMap;
        final int i = 0;
        hashMap.put(
                "isSyncable",
                new IServiceHandler() { // from class:
                    // com.samsung.android.scloud.oem.lib.sync.record.RecordSyncManager.1
                    /* JADX WARN: Multi-variable type inference failed */
                    /* JADX WARN: Removed duplicated region for block: B:91:0x01e8  */
                    /* JADX WARN: Type inference failed for: r9v15 */
                    /* JADX WARN: Type inference failed for: r9v16 */
                    /* JADX WARN: Type inference failed for: r9v17 */
                    /* JADX WARN: Type inference failed for: r9v18 */
                    /* JADX WARN: Type inference failed for: r9v19 */
                    /* JADX WARN: Type inference failed for: r9v20 */
                    /* JADX WARN: Type inference failed for: r9v21 */
                    /* JADX WARN: Type inference failed for: r9v22, types: [java.io.File] */
                    /* JADX WARN: Type inference failed for: r9v23 */
                    /* JADX WARN: Type inference failed for: r9v24, types: [java.io.File] */
                    /* JADX WARN: Type inference failed for: r9v25 */
                    /* JADX WARN: Type inference failed for: r9v26, types: [int] */
                    /* JADX WARN: Type inference failed for: r9v28 */
                    /* JADX WARN: Type inference failed for: r9v29 */
                    /* JADX WARN: Type inference failed for: r9v30 */
                    /* JADX WARN: Type inference failed for: r9v31 */
                    /* JADX WARN: Type inference failed for: r9v32 */
                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final android.os.Bundle handleServiceAction(
                            android.content.Context r23,
                            java.lang.Object r24,
                            java.lang.String r25,
                            android.os.Bundle r26) {
                        /*
                            Method dump skipped, instructions count: 1472
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.scloud.oem.lib.sync.record.RecordSyncManager.AnonymousClass1.handleServiceAction(android.content.Context,"
                                    + " java.lang.Object, java.lang.String,"
                                    + " android.os.Bundle):android.os.Bundle");
                    }
                });
        final int i2 = 1;
        hashMap.put(
                "isColdStartable",
                new IServiceHandler() { // from class:
                    // com.samsung.android.scloud.oem.lib.sync.record.RecordSyncManager.1
                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        /*
                            Method dump skipped, instructions count: 1472
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.scloud.oem.lib.sync.record.RecordSyncManager.AnonymousClass1.handleServiceAction(android.content.Context,"
                                    + " java.lang.Object, java.lang.String,"
                                    + " android.os.Bundle):android.os.Bundle");
                    }
                });
        final int i3 = 2;
        hashMap.put(
                "lastSyncTime",
                new IServiceHandler() { // from class:
                    // com.samsung.android.scloud.oem.lib.sync.record.RecordSyncManager.1
                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        /*
                            Method dump skipped, instructions count: 1472
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.scloud.oem.lib.sync.record.RecordSyncManager.AnonymousClass1.handleServiceAction(android.content.Context,"
                                    + " java.lang.Object, java.lang.String,"
                                    + " android.os.Bundle):android.os.Bundle");
                    }
                });
        final int i4 = 3;
        hashMap.put(
                "ready",
                new IServiceHandler() { // from class:
                    // com.samsung.android.scloud.oem.lib.sync.record.RecordSyncManager.1
                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        /*
                            Method dump skipped, instructions count: 1472
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.scloud.oem.lib.sync.record.RecordSyncManager.AnonymousClass1.handleServiceAction(android.content.Context,"
                                    + " java.lang.Object, java.lang.String,"
                                    + " android.os.Bundle):android.os.Bundle");
                    }
                });
        final int i5 = 4;
        hashMap.put(
                "getLocalFiles",
                new IServiceHandler() { // from class:
                    // com.samsung.android.scloud.oem.lib.sync.record.RecordSyncManager.1
                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        /*
                            Method dump skipped, instructions count: 1472
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.scloud.oem.lib.sync.record.RecordSyncManager.AnonymousClass1.handleServiceAction(android.content.Context,"
                                    + " java.lang.Object, java.lang.String,"
                                    + " android.os.Bundle):android.os.Bundle");
                    }
                });
        final int i6 = 5;
        hashMap.put(
                "fileWriteDone",
                new IServiceHandler() { // from class:
                    // com.samsung.android.scloud.oem.lib.sync.record.RecordSyncManager.1
                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        /*
                            Method dump skipped, instructions count: 1472
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.scloud.oem.lib.sync.record.RecordSyncManager.AnonymousClass1.handleServiceAction(android.content.Context,"
                                    + " java.lang.Object, java.lang.String,"
                                    + " android.os.Bundle):android.os.Bundle");
                    }
                });
        final int i7 = 6;
        hashMap.put(
                "complete",
                new IServiceHandler() { // from class:
                    // com.samsung.android.scloud.oem.lib.sync.record.RecordSyncManager.1
                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        /*
                            Method dump skipped, instructions count: 1472
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.scloud.oem.lib.sync.record.RecordSyncManager.AnonymousClass1.handleServiceAction(android.content.Context,"
                                    + " java.lang.Object, java.lang.String,"
                                    + " android.os.Bundle):android.os.Bundle");
                    }
                });
        final int i8 = 7;
        hashMap.put(
                "getLocalInfo",
                new IServiceHandler() { // from class:
                    // com.samsung.android.scloud.oem.lib.sync.record.RecordSyncManager.1
                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        /*
                            Method dump skipped, instructions count: 1472
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.scloud.oem.lib.sync.record.RecordSyncManager.AnonymousClass1.handleServiceAction(android.content.Context,"
                                    + " java.lang.Object, java.lang.String,"
                                    + " android.os.Bundle):android.os.Bundle");
                    }
                });
    }

    @Override // com.samsung.android.scloud.oem.lib.common.IClientHelper
    public final Object getClient() {
        return null;
    }

    @Override // com.samsung.android.scloud.oem.lib.common.IClientHelper
    public final IServiceHandler getServiceHandler(String str) {
        return (IServiceHandler) ((HashMap) SERVICE_HANDLER_MAP).get(str);
    }
}
