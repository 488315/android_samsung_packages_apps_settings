package com.samsung.android.settings.analyzestorage.external.bnr;

import kotlin.jvm.internal.Intrinsics;

import java.io.File;
import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class BnrUtils$getWorkingDirectoryPath$cacheDirRoot$1
        implements Function {
    public static final BnrUtils$getWorkingDirectoryPath$cacheDirRoot$1 INSTANCE =
            new BnrUtils$getWorkingDirectoryPath$cacheDirRoot$1();

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        File p0 = (File) obj;
        Intrinsics.checkNotNullParameter(p0, "p0");
        return p0.getAbsolutePath();
    }
}
