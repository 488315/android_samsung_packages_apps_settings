package com.android.settingslib.spa.framework.compose;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import androidx.compose.runtime.OpaqueKey;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class NavControllerWrapperKt {
    public static final DynamicProvidableCompositionLocal LocalNavController =
            CompositionLocalKt.compositionLocalOf$default(
                    new Function0() { // from class:
                                      // com.android.settingslib.spa.framework.compose.NavControllerWrapperKt$LocalNavController$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            return new AnonymousClass1();
                        }

                        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                        /* renamed from: com.android.settingslib.spa.framework.compose.NavControllerWrapperKt$LocalNavController$2$1, reason: invalid class name */
                        public final class AnonymousClass1 implements NavControllerWrapper {
                            @Override // com.android.settingslib.spa.framework.compose.NavControllerWrapper
                            public final void navigate(String route, boolean z) {
                                Intrinsics.checkNotNullParameter(route, "route");
                            }

                            @Override // com.android.settingslib.spa.framework.compose.NavControllerWrapper
                            public final void navigateBack() {}
                        }
                    });

    public static final Function0 navigator(Composer composer, final String str) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-227307818);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        final boolean z = false;
        if (str == null) {
            NavControllerWrapperKt$navigator$1 navControllerWrapperKt$navigator$1 =
                    new Function0() { // from class:
                                      // com.android.settingslib.spa.framework.compose.NavControllerWrapperKt$navigator$1
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final /* bridge */ /* synthetic */ Object mo1068invoke() {
                            return Unit.INSTANCE;
                        }
                    };
            composerImpl.end(false);
            return navControllerWrapperKt$navigator$1;
        }
        final NavControllerWrapper navControllerWrapper =
                (NavControllerWrapper) composerImpl.consume(LocalNavController);
        Function0 function0 =
                new Function0() { // from class:
                                  // com.android.settingslib.spa.framework.compose.NavControllerWrapperKt$navigator$2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        NavControllerWrapper.this.navigate(str, z);
                        return Unit.INSTANCE;
                    }
                };
        composerImpl.end(false);
        return function0;
    }
}
