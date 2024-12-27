package com.android.settings.spa.system;

import androidx.compose.material.icons.outlined.LanguageKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.graphics.vector.PathBuilder;
import androidx.compose.ui.graphics.vector.VectorKt;

import com.android.settingslib.spa.widget.ui.IconKt;

import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ComposableSingletons$LanguageAndInputPageProviderKt {

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f63lambda1 =
            new ComposableLambdaImpl(
                    -886476754,
                    false,
                    new Function2() { // from class:
                                      // com.android.settings.spa.system.ComposableSingletons$LanguageAndInputPageProviderKt$lambda-1$1
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
                            ImageVector imageVector = LanguageKt._language;
                            if (imageVector == null) {
                                ImageVector.Builder builder =
                                        new ImageVector.Builder(
                                                "Outlined.Language",
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
                                pathBuilder.moveTo(11.99f, 2.0f);
                                pathBuilder.curveTo(6.47f, 2.0f, 2.0f, 6.48f, 2.0f, 12.0f);
                                pathBuilder.reflectiveCurveToRelative(4.47f, 10.0f, 9.99f, 10.0f);
                                pathBuilder.curveTo(17.52f, 22.0f, 22.0f, 17.52f, 22.0f, 12.0f);
                                pathBuilder.reflectiveCurveTo(17.52f, 2.0f, 11.99f, 2.0f);
                                pathBuilder.close();
                                pathBuilder.moveTo(18.92f, 8.0f);
                                pathBuilder.horizontalLineToRelative(-2.95f);
                                pathBuilder.curveToRelative(
                                        -0.32f, -1.25f, -0.78f, -2.45f, -1.38f, -3.56f);
                                pathBuilder.curveToRelative(
                                        1.84f, 0.63f, 3.37f, 1.91f, 4.33f, 3.56f);
                                pathBuilder.close();
                                pathBuilder.moveTo(12.0f, 4.04f);
                                pathBuilder.curveToRelative(
                                        0.83f, 1.2f, 1.48f, 2.53f, 1.91f, 3.96f);
                                pathBuilder.horizontalLineToRelative(-3.82f);
                                pathBuilder.curveToRelative(
                                        0.43f, -1.43f, 1.08f, -2.76f, 1.91f, -3.96f);
                                pathBuilder.close();
                                pathBuilder.moveTo(4.26f, 14.0f);
                                pathBuilder.curveTo(4.1f, 13.36f, 4.0f, 12.69f, 4.0f, 12.0f);
                                pathBuilder.reflectiveCurveToRelative(0.1f, -1.36f, 0.26f, -2.0f);
                                pathBuilder.horizontalLineToRelative(3.38f);
                                pathBuilder.curveToRelative(
                                        -0.08f, 0.66f, -0.14f, 1.32f, -0.14f, 2.0f);
                                pathBuilder.reflectiveCurveToRelative(0.06f, 1.34f, 0.14f, 2.0f);
                                pathBuilder.lineTo(4.26f, 14.0f);
                                pathBuilder.close();
                                pathBuilder.moveTo(5.08f, 16.0f);
                                pathBuilder.horizontalLineToRelative(2.95f);
                                pathBuilder.curveToRelative(
                                        0.32f, 1.25f, 0.78f, 2.45f, 1.38f, 3.56f);
                                pathBuilder.curveToRelative(
                                        -1.84f, -0.63f, -3.37f, -1.9f, -4.33f, -3.56f);
                                pathBuilder.close();
                                pathBuilder.moveTo(8.03f, 8.0f);
                                pathBuilder.lineTo(5.08f, 8.0f);
                                pathBuilder.curveToRelative(
                                        0.96f, -1.66f, 2.49f, -2.93f, 4.33f, -3.56f);
                                pathBuilder.curveTo(8.81f, 5.55f, 8.35f, 6.75f, 8.03f, 8.0f);
                                pathBuilder.close();
                                pathBuilder.moveTo(12.0f, 19.96f);
                                pathBuilder.curveToRelative(
                                        -0.83f, -1.2f, -1.48f, -2.53f, -1.91f, -3.96f);
                                pathBuilder.horizontalLineToRelative(3.82f);
                                pathBuilder.curveToRelative(
                                        -0.43f, 1.43f, -1.08f, 2.76f, -1.91f, 3.96f);
                                pathBuilder.close();
                                pathBuilder.moveTo(14.34f, 14.0f);
                                pathBuilder.lineTo(9.66f, 14.0f);
                                pathBuilder.curveToRelative(
                                        -0.09f, -0.66f, -0.16f, -1.32f, -0.16f, -2.0f);
                                pathBuilder.reflectiveCurveToRelative(0.07f, -1.35f, 0.16f, -2.0f);
                                pathBuilder.horizontalLineToRelative(4.68f);
                                pathBuilder.curveToRelative(
                                        0.09f, 0.65f, 0.16f, 1.32f, 0.16f, 2.0f);
                                pathBuilder.reflectiveCurveToRelative(-0.07f, 1.34f, -0.16f, 2.0f);
                                pathBuilder.close();
                                pathBuilder.moveTo(14.59f, 19.56f);
                                pathBuilder.curveToRelative(
                                        0.6f, -1.11f, 1.06f, -2.31f, 1.38f, -3.56f);
                                pathBuilder.horizontalLineToRelative(2.95f);
                                pathBuilder.curveToRelative(
                                        -0.96f, 1.65f, -2.49f, 2.93f, -4.33f, 3.56f);
                                pathBuilder.close();
                                pathBuilder.moveTo(16.36f, 14.0f);
                                pathBuilder.curveToRelative(
                                        0.08f, -0.66f, 0.14f, -1.32f, 0.14f, -2.0f);
                                pathBuilder.reflectiveCurveToRelative(
                                        -0.06f, -1.34f, -0.14f, -2.0f);
                                pathBuilder.horizontalLineToRelative(3.38f);
                                pathBuilder.curveToRelative(
                                        0.16f, 0.64f, 0.26f, 1.31f, 0.26f, 2.0f);
                                pathBuilder.reflectiveCurveToRelative(-0.1f, 1.36f, -0.26f, 2.0f);
                                pathBuilder.horizontalLineToRelative(-3.38f);
                                pathBuilder.close();
                                ImageVector.Builder.m390addPathoIyEayM$default(
                                        builder, pathBuilder._nodes, solidColor);
                                imageVector = builder.build();
                                LanguageKt._language = imageVector;
                            }
                            IconKt.SettingsIcon(imageVector, composer, 0);
                            return Unit.INSTANCE;
                        }
                    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static final ComposableLambdaImpl f64lambda2 =
            new ComposableLambdaImpl(
                    -1288430359,
                    false,
                    new Function2() { // from class:
                                      // com.android.settings.spa.system.ComposableSingletons$LanguageAndInputPageProviderKt$lambda-2$1
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
                            AppLanguagesPageProvider.INSTANCE.EntryItem(composer, 6);
                            return Unit.INSTANCE;
                        }
                    });
}
