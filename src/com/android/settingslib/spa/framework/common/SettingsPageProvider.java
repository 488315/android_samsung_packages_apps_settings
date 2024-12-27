package com.android.settingslib.spa.framework.common;

import android.os.Bundle;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;

import com.android.settingslib.spa.widget.scaffold.RegularScaffoldKt;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function2;

import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public interface SettingsPageProvider {

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            d1 = {
                "\u0000\f\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u0010\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0000\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001¨\u0006\u0003"
            },
            d2 = {
                "com/android/settingslib/spa/framework/common/SettingsPageProvider$NavType",
                ApnSettings.MVNO_NONE,
                "Lcom/android/settingslib/spa/framework/common/SettingsPageProvider$NavType;",
                "frameworks__base__packages__SettingsLib__Spa__spa__android_common__SeslSpaLib"
            },
            k = 1,
            mv = {1, 9, 0})
    public final class NavType {
        public static final /* synthetic */ NavType[] $VALUES;

        static {
            NavType[] navTypeArr = {new NavType("Page", 0), new NavType("Dialog", 1)};
            $VALUES = navTypeArr;
            EnumEntriesKt.enumEntries(navTypeArr);
        }

        public static NavType valueOf(String str) {
            return (NavType) Enum.valueOf(NavType.class, str);
        }

        public static NavType[] values() {
            return (NavType[]) $VALUES.clone();
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.settingslib.spa.framework.common.SettingsPageProvider$Page$1, kotlin.jvm.internal.Lambda] */
    default void Page(final Bundle bundle, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-317826612);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        composerImpl.startReplaceGroup(-249295453);
        Object rememberedValue = composerImpl.rememberedValue();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (rememberedValue == composer$Companion$Empty$1) {
            rememberedValue = getTitle();
            composerImpl.updateRememberedValue(rememberedValue);
        }
        String str = (String) rememberedValue;
        composerImpl.end(false);
        composerImpl.startReplaceGroup(-249295398);
        Object rememberedValue2 = composerImpl.rememberedValue();
        if (rememberedValue2 == composer$Companion$Empty$1) {
            rememberedValue2 = buildEntry(bundle);
            composerImpl.updateRememberedValue(rememberedValue2);
        }
        final List list = (List) rememberedValue2;
        composerImpl.end(false);
        RegularScaffoldKt.RegularScaffold(
                str,
                null,
                ComposableLambdaKt.rememberComposableLambda(
                        1907891409,
                        new Function2() { // from class:
                                          // com.android.settingslib.spa.framework.common.SettingsPageProvider$Page$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                Composer composer2 = (Composer) obj;
                                if ((((Number) obj2).intValue() & 11) == 2) {
                                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                    if (composerImpl2.getSkipping()) {
                                        composerImpl2.skipToGroupEnd();
                                        return Unit.INSTANCE;
                                    }
                                }
                                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                Iterator<SettingsEntry> it = list.iterator();
                                while (it.hasNext()) {
                                    it.next().UiLayout(null, composer2, 64, 1);
                                }
                                OpaqueKey opaqueKey3 = ComposerKt.invocation;
                                return Unit.INSTANCE;
                            }
                        },
                        composerImpl),
                composerImpl,
                390,
                2);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.framework.common.SettingsPageProvider$Page$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            SettingsPageProvider.this.Page(
                                    bundle,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    default List buildEntry(Bundle bundle) {
        return EmptyList.INSTANCE;
    }

    default int getMetricsCategory() {
        return 0;
    }

    String getName();

    default List getParameter() {
        return EmptyList.INSTANCE;
    }

    default String getTitle() {
        return getName();
    }

    default boolean isEnabled() {
        return true;
    }
}
