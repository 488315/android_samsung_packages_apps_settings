package com.samsung.android.settings.analyzestorage.external.bnr;

import kotlin.text.StringsKt__StringsJVMKt;

import java.io.File;
import java.io.FilenameFilter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BnrUtils$getTargetRestoreFiles$targetSourceList$1 implements FilenameFilter {
    public static final BnrUtils$getTargetRestoreFiles$targetSourceList$1 INSTANCE =
            new BnrUtils$getTargetRestoreFiles$targetSourceList$1();

    @Override // java.io.FilenameFilter
    public final boolean accept(File file, String str) {
        return (file == null
                        || str == null
                        || !StringsKt__StringsJVMKt.startsWith$default(str, "BACKUP#"))
                ? false
                : true;
    }
}
