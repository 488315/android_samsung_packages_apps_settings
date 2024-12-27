package com.android.settingslib.spa.widget.editor;

import androidx.compose.material3.AndroidMenu_androidKt;
import androidx.compose.material3.ExposedDropdownMenuDefaults;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.sec.ims.volte2.data.VolteConstants;

import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SettingsDropdownBoxKt {
    /* JADX WARN: Type inference failed for: r6v3, types: [com.android.settingslib.spa.widget.editor.SettingsDropdownBoxKt$SettingsDropdownBox$2, kotlin.jvm.internal.Lambda] */
    public static final void SettingsDropdownBox(
            final String label,
            final List options,
            final int i,
            boolean z,
            final Function1 onSelectedOptionChange,
            Composer composer,
            final int i2,
            final int i3) {
        Intrinsics.checkNotNullParameter(label, "label");
        Intrinsics.checkNotNullParameter(options, "options");
        Intrinsics.checkNotNullParameter(onSelectedOptionChange, "onSelectedOptionChange");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-255986066);
        final boolean z2 = (i3 & 8) != 0 ? true : z;
        OpaqueKey opaqueKey = ComposerKt.invocation;
        DropdownTextBoxKt.DropdownTextBox(
                label,
                (String)
                        ((i < 0 || i > CollectionsKt__CollectionsKt.getLastIndex(options))
                                ? ApnSettings.MVNO_NONE
                                : options.get(i)),
                z2 && (options.isEmpty() ^ true),
                null,
                false,
                ComposableLambdaKt.rememberComposableLambda(
                        1621707686,
                        new Function3() { // from class:
                                          // com.android.settingslib.spa.widget.editor.SettingsDropdownBoxKt$SettingsDropdownBox$2
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(3);
                            }

                            /* JADX WARN: Type inference failed for: r4v0, types: [com.android.settingslib.spa.widget.editor.SettingsDropdownBoxKt$SettingsDropdownBox$2$1$1, kotlin.jvm.internal.Lambda] */
                            @Override // kotlin.jvm.functions.Function3
                            public final Object invoke(Object obj, Object obj2, Object obj3) {
                                final DropdownTextBoxScope DropdownTextBox =
                                        (DropdownTextBoxScope) obj;
                                Composer composer2 = (Composer) obj2;
                                int intValue = ((Number) obj3).intValue();
                                Intrinsics.checkNotNullParameter(
                                        DropdownTextBox, "$this$DropdownTextBox");
                                if ((intValue & 14) == 0) {
                                    intValue |=
                                            ((ComposerImpl) composer2).changed(DropdownTextBox)
                                                    ? 4
                                                    : 2;
                                }
                                if ((intValue & 91) == 18) {
                                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                    if (composerImpl2.getSkipping()) {
                                        composerImpl2.skipToGroupEnd();
                                        return Unit.INSTANCE;
                                    }
                                }
                                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                List<String> list = options;
                                final Function1 function1 = onSelectedOptionChange;
                                final int i4 = 0;
                                for (Object obj4 : list) {
                                    int i5 = i4 + 1;
                                    if (i4 < 0) {
                                        CollectionsKt__CollectionsKt.throwIndexOverflow();
                                        throw null;
                                    }
                                    final String str = (String) obj4;
                                    AndroidMenu_androidKt.DropdownMenuItem(
                                            ComposableLambdaKt.rememberComposableLambda(
                                                    -885257168,
                                                    new Function2() { // from class:
                                                                      // com.android.settingslib.spa.widget.editor.SettingsDropdownBoxKt$SettingsDropdownBox$2$1$1
                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                        {
                                                            super(2);
                                                        }

                                                        @Override // kotlin.jvm.functions.Function2
                                                        public final Object invoke(
                                                                Object obj5, Object obj6) {
                                                            Composer composer3 = (Composer) obj5;
                                                            if ((((Number) obj6).intValue() & 11)
                                                                    == 2) {
                                                                ComposerImpl composerImpl3 =
                                                                        (ComposerImpl) composer3;
                                                                if (composerImpl3.getSkipping()) {
                                                                    composerImpl3.skipToGroupEnd();
                                                                    return Unit.INSTANCE;
                                                                }
                                                            }
                                                            OpaqueKey opaqueKey3 =
                                                                    ComposerKt.invocation;
                                                            TextKt.m210Text4IGK_g(
                                                                    str, null, 0L, 0L, null, null,
                                                                    null, 0L, null, null, 0L, 0,
                                                                    false, 0, 0, null, null,
                                                                    composer3, 0, 0, 131070);
                                                            return Unit.INSTANCE;
                                                        }
                                                    },
                                                    composer2),
                                            new Function0() { // from class:
                                                              // com.android.settingslib.spa.widget.editor.SettingsDropdownBoxKt$SettingsDropdownBox$2$1$2
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(0);
                                                }

                                                @Override // kotlin.jvm.functions.Function0
                                                /* renamed from: invoke */
                                                public final Object mo1068invoke() {
                                                    MutableState mutableState =
                                                            ((DropdownTextBoxKt$DropdownTextBox$scope$1$1)
                                                                            DropdownTextBoxScope
                                                                                    .this)
                                                                    .$expanded$delegate;
                                                    float f = DropdownTextBoxKt.Width;
                                                    mutableState.setValue(Boolean.FALSE);
                                                    function1.invoke(Integer.valueOf(i4));
                                                    return Unit.INSTANCE;
                                                }
                                            },
                                            null,
                                            null,
                                            null,
                                            false,
                                            null,
                                            ExposedDropdownMenuDefaults.ItemContentPadding,
                                            null,
                                            composer2,
                                            6,
                                            VolteConstants.ErrorCode.ALTERNATIVE_SERVICES);
                                    i4 = i5;
                                }
                                OpaqueKey opaqueKey3 = ComposerKt.invocation;
                                return Unit.INSTANCE;
                            }
                        },
                        composerImpl),
                composerImpl,
                (i2 & 14) | 196608,
                24);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.editor.SettingsDropdownBoxKt$SettingsDropdownBox$3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            SettingsDropdownBoxKt.SettingsDropdownBox(
                                    label,
                                    options,
                                    i,
                                    z2,
                                    onSelectedOptionChange,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i2 | 1),
                                    i3);
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
