package com.samsung.android.settings.analyzestorage.external.bnr;

import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.presenter.utils.StoragePathUtils;
import com.samsung.android.settings.analyzestorage.presenter.utils.fileutils.FileWrapper;

import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;

import java.io.IOException;
import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BnrUtils$isValidDestinationPath$1 implements Function {
    public static final BnrUtils$isValidDestinationPath$1 INSTANCE =
            new BnrUtils$isValidDestinationPath$1();

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        boolean z;
        String targetPath = (String) obj;
        Intrinsics.checkNotNullParameter(targetPath, "targetPath");
        try {
            String canonicalPath = new FileWrapper(targetPath).getCanonicalPath();
            Intrinsics.checkNotNullExpressionValue(canonicalPath, "getCanonicalPath(...)");
            String internalStoragePath = StoragePathUtils.getInternalStoragePath();
            Intrinsics.checkNotNullExpressionValue(
                    internalStoragePath, "getInternalStoragePath(...)");
            z = StringsKt__StringsJVMKt.startsWith$default(canonicalPath, internalStoragePath);
        } catch (IOException e) {
            Log.e("BnrUtils", "isValidDestinationPath() ] IOException e : " + e.getMessage());
            z = false;
        }
        return Boolean.valueOf(z);
    }
}
