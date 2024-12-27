package com.samsung.android.scloud.oem.lib.backup.record;

import android.content.Context;
import android.os.Bundle;
import android.util.JsonReader;

import com.samsung.android.scloud.oem.lib.common.IClientHelper;
import com.samsung.android.scloud.oem.lib.common.IServiceHandler;
import com.samsung.android.scloud.oem.lib.utils.SCloudParser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class RecordClientManager extends IClientHelper {
    public String dataDirectory;
    public final Map pfdMap;
    public final Map processedKeyMap;
    public final Map serviceHandlerMap;

    public RecordClientManager() {
        HashMap hashMap = new HashMap();
        this.serviceHandlerMap = hashMap;
        this.processedKeyMap = new HashMap();
        this.pfdMap = new HashMap();
        final int i = 0;
        hashMap.put(
                "getKeyAndDate",
                new IServiceHandler(this) { // from class:
                    // com.samsung.android.scloud.oem.lib.backup.record.RecordClientManager.1
                    public final /* synthetic */ RecordClientManager this$0;

                    {
                        this.this$0 = this;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:113:0x0315  */
                    /* JADX WARN: Removed duplicated region for block: B:125:0x033e  */
                    /* JADX WARN: Removed duplicated region for block: B:129:0x034d A[Catch: FileNotFoundException -> 0x0353, TryCatch #15 {FileNotFoundException -> 0x0353, blocks: (B:127:0x0341, B:129:0x034d, B:130:0x0355), top: B:126:0x0341 }] */
                    /* JADX WARN: Removed duplicated region for block: B:82:0x02a5  */
                    /* JADX WARN: Removed duplicated region for block: B:94:0x02ce  */
                    /* JADX WARN: Removed duplicated region for block: B:98:0x02dd A[Catch: FileNotFoundException -> 0x02e3, TryCatch #5 {FileNotFoundException -> 0x02e3, blocks: (B:96:0x02d1, B:98:0x02dd, B:99:0x02e5), top: B:95:0x02d1 }] */
                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final android.os.Bundle handleServiceAction(
                            android.content.Context r19,
                            java.lang.Object r20,
                            java.lang.String r21,
                            android.os.Bundle r22) {
                        /*
                            Method dump skipped, instructions count: 1436
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.scloud.oem.lib.backup.record.RecordClientManager.AnonymousClass1.handleServiceAction(android.content.Context,"
                                    + " java.lang.Object, java.lang.String,"
                                    + " android.os.Bundle):android.os.Bundle");
                    }
                });
        final int i2 = 3;
        hashMap.put(
                "getRecord",
                new IServiceHandler(this) { // from class:
                    // com.samsung.android.scloud.oem.lib.backup.record.RecordClientManager.1
                    public final /* synthetic */ RecordClientManager this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        /*
                            Method dump skipped, instructions count: 1436
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.scloud.oem.lib.backup.record.RecordClientManager.AnonymousClass1.handleServiceAction(android.content.Context,"
                                    + " java.lang.Object, java.lang.String,"
                                    + " android.os.Bundle):android.os.Bundle");
                    }
                });
        final int i3 = 4;
        hashMap.put(
                "putRecord",
                new IServiceHandler(this) { // from class:
                    // com.samsung.android.scloud.oem.lib.backup.record.RecordClientManager.1
                    public final /* synthetic */ RecordClientManager this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        /*
                            Method dump skipped, instructions count: 1436
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.scloud.oem.lib.backup.record.RecordClientManager.AnonymousClass1.handleServiceAction(android.content.Context,"
                                    + " java.lang.Object, java.lang.String,"
                                    + " android.os.Bundle):android.os.Bundle");
                    }
                });
        final int i4 = 5;
        hashMap.put(
                "backupPrepare",
                new IServiceHandler(this) { // from class:
                    // com.samsung.android.scloud.oem.lib.backup.record.RecordClientManager.1
                    public final /* synthetic */ RecordClientManager this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        /*
                            Method dump skipped, instructions count: 1436
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.scloud.oem.lib.backup.record.RecordClientManager.AnonymousClass1.handleServiceAction(android.content.Context,"
                                    + " java.lang.Object, java.lang.String,"
                                    + " android.os.Bundle):android.os.Bundle");
                    }
                });
        final int i5 = 6;
        hashMap.put(
                "backupComplete",
                new IServiceHandler(this) { // from class:
                    // com.samsung.android.scloud.oem.lib.backup.record.RecordClientManager.1
                    public final /* synthetic */ RecordClientManager this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        /*
                            Method dump skipped, instructions count: 1436
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.scloud.oem.lib.backup.record.RecordClientManager.AnonymousClass1.handleServiceAction(android.content.Context,"
                                    + " java.lang.Object, java.lang.String,"
                                    + " android.os.Bundle):android.os.Bundle");
                    }
                });
        final int i6 = 7;
        hashMap.put(
                "restorePrepare",
                new IServiceHandler(this) { // from class:
                    // com.samsung.android.scloud.oem.lib.backup.record.RecordClientManager.1
                    public final /* synthetic */ RecordClientManager this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        /*
                            Method dump skipped, instructions count: 1436
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.scloud.oem.lib.backup.record.RecordClientManager.AnonymousClass1.handleServiceAction(android.content.Context,"
                                    + " java.lang.Object, java.lang.String,"
                                    + " android.os.Bundle):android.os.Bundle");
                    }
                });
        final int i7 = 8;
        hashMap.put(
                "restoreComplete",
                new IServiceHandler(this) { // from class:
                    // com.samsung.android.scloud.oem.lib.backup.record.RecordClientManager.1
                    public final /* synthetic */ RecordClientManager this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        /*
                            Method dump skipped, instructions count: 1436
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.scloud.oem.lib.backup.record.RecordClientManager.AnonymousClass1.handleServiceAction(android.content.Context,"
                                    + " java.lang.Object, java.lang.String,"
                                    + " android.os.Bundle):android.os.Bundle");
                    }
                });
        final int i8 = 0;
        hashMap.put(
                "deleteRestoreFile",
                new IServiceHandler() { // from class:
                    // com.samsung.android.scloud.oem.lib.backup.record.RecordClientManager.8
                    /* JADX WARN: Removed duplicated region for block: B:55:0x0221  */
                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final android.os.Bundle handleServiceAction(
                            android.content.Context r22,
                            java.lang.Object r23,
                            java.lang.String r24,
                            android.os.Bundle r25) {
                        /*
                            Method dump skipped, instructions count: 692
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.scloud.oem.lib.backup.record.RecordClientManager.AnonymousClass8.handleServiceAction(android.content.Context,"
                                    + " java.lang.Object, java.lang.String,"
                                    + " android.os.Bundle):android.os.Bundle");
                    }
                });
        final int i9 = 3;
        hashMap.put(
                "completeFile",
                new IServiceHandler() { // from class:
                    // com.samsung.android.scloud.oem.lib.backup.record.RecordClientManager.8
                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        /*
                            Method dump skipped, instructions count: 692
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.scloud.oem.lib.backup.record.RecordClientManager.AnonymousClass8.handleServiceAction(android.content.Context,"
                                    + " java.lang.Object, java.lang.String,"
                                    + " android.os.Bundle):android.os.Bundle");
                    }
                });
        final int i10 = 1;
        hashMap.put(
                "restoreFile",
                new IServiceHandler(this) { // from class:
                    // com.samsung.android.scloud.oem.lib.backup.record.RecordClientManager.1
                    public final /* synthetic */ RecordClientManager this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        /*
                            Method dump skipped, instructions count: 1436
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.scloud.oem.lib.backup.record.RecordClientManager.AnonymousClass1.handleServiceAction(android.content.Context,"
                                    + " java.lang.Object, java.lang.String,"
                                    + " android.os.Bundle):android.os.Bundle");
                    }
                });
        final int i11 = 1;
        hashMap.put(
                "checkAndUpdateReuseDB",
                new IServiceHandler() { // from class:
                    // com.samsung.android.scloud.oem.lib.backup.record.RecordClientManager.8
                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        /*
                            Method dump skipped, instructions count: 692
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.scloud.oem.lib.backup.record.RecordClientManager.AnonymousClass8.handleServiceAction(android.content.Context,"
                                    + " java.lang.Object, java.lang.String,"
                                    + " android.os.Bundle):android.os.Bundle");
                    }
                });
        final int i12 = 2;
        hashMap.put(
                "clearReuseFileDB",
                new IServiceHandler(this) { // from class:
                    // com.samsung.android.scloud.oem.lib.backup.record.RecordClientManager.1
                    public final /* synthetic */ RecordClientManager this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        /*
                            Method dump skipped, instructions count: 1436
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.scloud.oem.lib.backup.record.RecordClientManager.AnonymousClass1.handleServiceAction(android.content.Context,"
                                    + " java.lang.Object, java.lang.String,"
                                    + " android.os.Bundle):android.os.Bundle");
                    }
                });
        final int i13 = 2;
        hashMap.put(
                "requestCancel",
                new IServiceHandler() { // from class:
                    // com.samsung.android.scloud.oem.lib.backup.record.RecordClientManager.8
                    @Override // com.samsung.android.scloud.oem.lib.common.IServiceHandler
                    public final Bundle handleServiceAction(
                            Context context, Object obj, String str, Bundle bundle) {
                        /*
                            Method dump skipped, instructions count: 692
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.scloud.oem.lib.backup.record.RecordClientManager.AnonymousClass8.handleServiceAction(android.content.Context,"
                                    + " java.lang.Object, java.lang.String,"
                                    + " android.os.Bundle):android.os.Bundle");
                    }
                });
    }

    public static ArrayList access$200(
            RecordClientManager recordClientManager, JsonReader jsonReader) {
        recordClientManager.getClass();
        ArrayList arrayList = new ArrayList();
        try {
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            try {
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    String str = null;
                    try {
                        if (SCloudParser.AnonymousClass1.$SwitchMap$android$util$JsonToken[
                                        jsonReader.peek().ordinal()]
                                != 1) {
                            jsonReader.skipValue();
                        } else {
                            str = jsonReader.nextString();
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    arrayList.add(str);
                }
                jsonReader.endArray();
                jsonReader.close();
            } catch (IOException e3) {
                e3.printStackTrace();
                jsonReader.endArray();
                jsonReader.close();
            }
            return arrayList;
        } catch (Throwable th) {
            try {
                jsonReader.endArray();
                jsonReader.close();
            } catch (IOException e4) {
                e4.printStackTrace();
            }
            throw th;
        }
    }

    public static File access$300(RecordClientManager recordClientManager, Context context) {
        if (recordClientManager.dataDirectory == null) {
            return context.getFilesDir();
        }
        File file = new File(recordClientManager.dataDirectory);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    @Override // com.samsung.android.scloud.oem.lib.common.IClientHelper
    public final Object getClient() {
        return null;
    }

    @Override // com.samsung.android.scloud.oem.lib.common.IClientHelper
    public final IServiceHandler getServiceHandler(String str) {
        return (IServiceHandler) ((HashMap) this.serviceHandlerMap).get(str);
    }
}
