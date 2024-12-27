package com.android.settingslib.spa.widget.ui;

import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.ColorScheme;
import androidx.compose.material3.ColorSchemeKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.vector.ImageVector;

import com.android.settingslib.spa.framework.theme.SettingsDimension;

import com.samsung.android.settings.analyzestorage.data.constant.FileType;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class IconKt {
    public static final void SettingsIcon(
            final ImageVector imageVector, Composer composer, final int i) {
        int i2;
        Intrinsics.checkNotNullParameter(imageVector, "imageVector");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1393565987);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changed(imageVector) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 11) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            androidx.compose.material3.IconKt.m188Iconww6aTOc(
                    imageVector,
                    (String) null,
                    SizeKt.m96size3ABfNKs(
                            Modifier.Companion.$$INSTANCE, SettingsDimension.itemIconSize),
                    ((ColorScheme) composerImpl.consume(ColorSchemeKt.LocalColorScheme)).onSurface,
                    composerImpl,
                    (i2 & 14) | FileType.CRT,
                    0);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.ui.IconKt$SettingsIcon$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            IconKt.SettingsIcon(
                                    ImageVector.this,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
