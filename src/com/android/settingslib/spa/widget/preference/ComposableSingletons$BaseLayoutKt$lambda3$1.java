package com.android.settingslib.spa.widget.preference;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\n\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001H\u000b¢\u0006\u0004\b\u0002\u0010\u0003"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "invoke",
            "(Landroidx/compose/runtime/Composer;I)V"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* renamed from: com.android.settingslib.spa.widget.preference.ComposableSingletons$BaseLayoutKt$lambda-3$1, reason: invalid class name */
/* loaded from: classes2.dex */
final class ComposableSingletons$BaseLayoutKt$lambda3$1 extends Lambda implements Function2 {
    public static final /* synthetic */ int $r8$clinit = 0;

    static {
        new ComposableSingletons$BaseLayoutKt$lambda3$1();
    }

    public ComposableSingletons$BaseLayoutKt$lambda3$1() {
        super(2);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Composer composer = (Composer) obj;
        if ((((Number) obj2).intValue() & 11) == 2) {
            ComposerImpl composerImpl = (ComposerImpl) composer;
            if (composerImpl.getSkipping()) {
                composerImpl.skipToGroupEnd();
                return Unit.INSTANCE;
            }
        }
        OpaqueKey opaqueKey = ComposerKt.invocation;
        BaseLayoutKt.m1043BaseLayoutIBxwOmc(
                "Title",
                ComposableSingletons$BaseLayoutKt.f77lambda2,
                null,
                null,
                null,
                null,
                0.0f,
                0.0f,
                0.0f,
                null,
                composer,
                54,
                1020);
        return Unit.INSTANCE;
    }
}
