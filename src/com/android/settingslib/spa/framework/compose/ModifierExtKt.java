package com.android.settingslib.spa.framework.compose;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ModifierExtKt {
    public static final Modifier contentDescription(Modifier modifier, final String str) {
        Intrinsics.checkNotNullParameter(modifier, "<this>");
        return str != null
                ? SemanticsModifierKt.semantics(
                        modifier,
                        false,
                        new Function1() { // from class:
                                          // com.android.settingslib.spa.framework.compose.ModifierExtKt$contentDescription$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                SemanticsPropertyReceiver semantics =
                                        (SemanticsPropertyReceiver) obj;
                                Intrinsics.checkNotNullParameter(semantics, "$this$semantics");
                                SemanticsPropertiesKt.setContentDescription(semantics, str);
                                return Unit.INSTANCE;
                            }
                        })
                : modifier;
    }
}
