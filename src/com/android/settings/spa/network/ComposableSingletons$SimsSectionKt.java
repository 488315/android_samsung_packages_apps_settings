package com.android.settings.spa.network;

import androidx.compose.material.icons.outlined.AddKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.internal.ComposableLambdaImpl;

import com.android.settingslib.spa.widget.ui.IconKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ComposableSingletons$SimsSectionKt {

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f57lambda1 =
            new ComposableLambdaImpl(
                    -400661839,
                    false,
                    new Function2() { // from class:
                                      // com.android.settings.spa.network.ComposableSingletons$SimsSectionKt$lambda-1$1
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
                            IconKt.SettingsIcon(AddKt.getAdd(), composer, 0);
                            return Unit.INSTANCE;
                        }
                    });
}
