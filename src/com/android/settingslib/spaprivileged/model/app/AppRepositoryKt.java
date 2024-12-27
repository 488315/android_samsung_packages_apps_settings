package com.android.settingslib.spaprivileged.model.app;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;

import com.android.settingslib.spa.framework.compose.RuntimeUtilsKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AppRepositoryKt {
    public static final AppRepositoryImpl rememberAppRepository(Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-270608827);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        AppRepositoryImpl appRepositoryImpl =
                (AppRepositoryImpl)
                        RuntimeUtilsKt.rememberContext(
                                AppRepositoryKt$rememberAppRepository$1.INSTANCE, composerImpl);
        composerImpl.end(false);
        return appRepositoryImpl;
    }
}
