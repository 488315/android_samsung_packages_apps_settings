package com.android.settingslib.spa.widget.card;

import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.material.icons.outlined.CloseKt;
import androidx.compose.material3.IconKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.graphics.vector.PathBuilder;
import androidx.compose.ui.graphics.vector.VectorKt;
import androidx.compose.ui.res.StringResources_androidKt;

import com.android.settings.R;
import com.android.settingslib.spa.framework.theme.SettingsDimension;

import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ComposableSingletons$SettingsCardKt {

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f73lambda1 =
            new ComposableLambdaImpl(
                    -225941367,
                    false,
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.card.ComposableSingletons$SettingsCardKt$lambda-1$1
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
                            ImageVector imageVector = CloseKt._close;
                            if (imageVector == null) {
                                ImageVector.Builder builder =
                                        new ImageVector.Builder(
                                                "Outlined.Close",
                                                24.0f,
                                                24.0f,
                                                24.0f,
                                                24.0f,
                                                0L,
                                                0,
                                                false,
                                                96);
                                EmptyList emptyList = VectorKt.EmptyPath;
                                SolidColor solidColor = new SolidColor(Color.Black);
                                PathBuilder pathBuilder = new PathBuilder();
                                pathBuilder.moveTo(19.0f, 6.41f);
                                pathBuilder.lineTo(17.59f, 5.0f);
                                pathBuilder.lineTo(12.0f, 10.59f);
                                pathBuilder.lineTo(6.41f, 5.0f);
                                pathBuilder.lineTo(5.0f, 6.41f);
                                pathBuilder.lineTo(10.59f, 12.0f);
                                pathBuilder.lineTo(5.0f, 17.59f);
                                pathBuilder.lineTo(6.41f, 19.0f);
                                pathBuilder.lineTo(12.0f, 13.41f);
                                pathBuilder.lineTo(17.59f, 19.0f);
                                pathBuilder.lineTo(19.0f, 17.59f);
                                pathBuilder.lineTo(13.41f, 12.0f);
                                pathBuilder.lineTo(19.0f, 6.41f);
                                pathBuilder.close();
                                ImageVector.Builder.m390addPathoIyEayM$default(
                                        builder, pathBuilder._nodes, solidColor);
                                imageVector = builder.build();
                                CloseKt._close = imageVector;
                            }
                            IconKt.m188Iconww6aTOc(
                                    imageVector,
                                    StringResources_androidKt.stringResource(
                                            composer, R.string.m3c_snackbar_dismiss),
                                    PaddingKt.m85padding3ABfNKs(
                                            Modifier.Companion.$$INSTANCE,
                                            SettingsDimension.paddingSmall),
                                    0L,
                                    composer,
                                    384,
                                    8);
                            return Unit.INSTANCE;
                        }
                    });

    static {
        int i = ComposableSingletons$SettingsCardKt$lambda2$1.$r8$clinit;
    }
}
