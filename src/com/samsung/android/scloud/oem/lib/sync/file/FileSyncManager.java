package com.samsung.android.scloud.oem.lib.sync.file;

import android.content.Context;
import android.os.Bundle;

import com.samsung.android.scloud.oem.lib.common.IClientHelper;
import com.samsung.android.scloud.oem.lib.common.IServiceHandler;

import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class FileSyncManager extends IClientHelper {
    public static final Map SERVICE_HANDLER_MAP;

    static {
        HashMap hashMap = new HashMap();
        SERVICE_HANDLER_MAP = hashMap;
        final int i = 0;
        hashMap.put(
                "isColdStartable",
                new IServiceHandler() { // from class:
                    // com.samsung.android.scloud.oem.lib.sync.file.FileSyncManager.1
                    /* JADX WARN: Removed duplicated region for block: B:24:0x00f4 A[Catch: UnsupportedOperationException -> 0x00f8, TRY_ENTER, TRY_LEAVE, TryCatch #5 {UnsupportedOperationException -> 0x00f8, blocks: (B:24:0x00f4, B:26:0x00fa), top: B:22:0x00f2 }] */
                    /* JADX WARN: Removed duplicated region for block: B:26:0x00fa A[Catch: UnsupportedOperationException -> 0x00f8, TRY_ENTER, TRY_LEAVE, TryCatch #5 {UnsupportedOperationException -> 0x00f8, blocks: (B:24:0x00f4, B:26:0x00fa), top: B:22:0x00f2 }] */
                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final android.os.Bundle handleServiceAction(
                            android.content.Context r4,
                            java.lang.Object r5,
                            java.lang.String r6,
                            android.os.Bundle r7) {
                        /*
                            Method dump skipped, instructions count: 470
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.scloud.oem.lib.sync.file.FileSyncManager.AnonymousClass1.handleServiceAction(android.content.Context,"
                                    + " java.lang.Object, java.lang.String,"
                                    + " android.os.Bundle):android.os.Bundle");
                    }
                });
        final int i2 = 1;
        hashMap.put(
                "prepare",
                new IServiceHandler() { // from class:
                    // com.samsung.android.scloud.oem.lib.sync.file.FileSyncManager.1
                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        /*
                            Method dump skipped, instructions count: 470
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.scloud.oem.lib.sync.file.FileSyncManager.AnonymousClass1.handleServiceAction(android.content.Context,"
                                    + " java.lang.Object, java.lang.String,"
                                    + " android.os.Bundle):android.os.Bundle");
                    }
                });
        final int i3 = 2;
        hashMap.put(
                "getAttachmentInfo",
                new IServiceHandler() { // from class:
                    // com.samsung.android.scloud.oem.lib.sync.file.FileSyncManager.1
                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        /*
                            Method dump skipped, instructions count: 470
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.scloud.oem.lib.sync.file.FileSyncManager.AnonymousClass1.handleServiceAction(android.content.Context,"
                                    + " java.lang.Object, java.lang.String,"
                                    + " android.os.Bundle):android.os.Bundle");
                    }
                });
        final int i4 = 3;
        hashMap.put(
                "upload",
                new IServiceHandler() { // from class:
                    // com.samsung.android.scloud.oem.lib.sync.file.FileSyncManager.1
                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        /*
                            Method dump skipped, instructions count: 470
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.scloud.oem.lib.sync.file.FileSyncManager.AnonymousClass1.handleServiceAction(android.content.Context,"
                                    + " java.lang.Object, java.lang.String,"
                                    + " android.os.Bundle):android.os.Bundle");
                    }
                });
        final int i5 = 4;
        hashMap.put(
                "download",
                new IServiceHandler() { // from class:
                    // com.samsung.android.scloud.oem.lib.sync.file.FileSyncManager.1
                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        /*
                            Method dump skipped, instructions count: 470
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.scloud.oem.lib.sync.file.FileSyncManager.AnonymousClass1.handleServiceAction(android.content.Context,"
                                    + " java.lang.Object, java.lang.String,"
                                    + " android.os.Bundle):android.os.Bundle");
                    }
                });
        final int i6 = 5;
        hashMap.put(
                "deleteItem",
                new IServiceHandler() { // from class:
                    // com.samsung.android.scloud.oem.lib.sync.file.FileSyncManager.1
                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        /*
                            Method dump skipped, instructions count: 470
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.scloud.oem.lib.sync.file.FileSyncManager.AnonymousClass1.handleServiceAction(android.content.Context,"
                                    + " java.lang.Object, java.lang.String,"
                                    + " android.os.Bundle):android.os.Bundle");
                    }
                });
        final int i7 = 6;
        hashMap.put(
                "complete",
                new IServiceHandler() { // from class:
                    // com.samsung.android.scloud.oem.lib.sync.file.FileSyncManager.1
                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        /*
                            Method dump skipped, instructions count: 470
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.scloud.oem.lib.sync.file.FileSyncManager.AnonymousClass1.handleServiceAction(android.content.Context,"
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
