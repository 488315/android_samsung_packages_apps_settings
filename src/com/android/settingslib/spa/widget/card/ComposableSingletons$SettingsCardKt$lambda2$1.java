package com.android.settingslib.spa.widget.card;

import androidx.compose.material.icons.outlined.WarningAmberKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.jvm.functions.Function0;
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
/* renamed from: com.android.settingslib.spa.widget.card.ComposableSingletons$SettingsCardKt$lambda-2$1, reason: invalid class name */
/* loaded from: classes2.dex */
final class ComposableSingletons$SettingsCardKt$lambda2$1 extends Lambda implements Function2 {
    public static final /* synthetic */ int $r8$clinit = 0;

    static {
        new ComposableSingletons$SettingsCardKt$lambda2$1();
    }

    public ComposableSingletons$SettingsCardKt$lambda2$1() {
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
        SettingsCardKt.SettingsCard(
                new CardModel(
                        "Lorem ipsum",
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                        WarningAmberKt.getWarningAmber(),
                        null,
                        CollectionsKt__CollectionsJVMKt.listOf(
                                new CardButton(
                                        "Action",
                                        null,
                                        new Function0() { // from class:
                                                          // com.android.settingslib.spa.widget.card.ComposableSingletons$SettingsCardKt$lambda-2$1.1
                                            @Override // kotlin.jvm.functions.Function0
                                            /* renamed from: invoke */
                                            public final /* bridge */ /* synthetic */ Object
                                                    mo1068invoke() {
                                                return Unit.INSTANCE;
                                            }
                                        })),
                        472),
                composer,
                8);
        return Unit.INSTANCE;
    }
}
