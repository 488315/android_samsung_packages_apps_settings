package com.android.settingslib.spa.widget.scaffold;

import androidx.compose.material.icons.outlined.ClearKt;
import androidx.compose.material.icons.outlined.FindInPageKt;
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

import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ComposableSingletons$ActionsKt {

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f80lambda1 =
            new ComposableLambdaImpl(
                    53951704,
                    false,
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.scaffold.ComposableSingletons$ActionsKt$lambda-1$1
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
                            ImageVector imageVector = FindInPageKt._findInPage;
                            if (imageVector == null) {
                                ImageVector.Builder builder =
                                        new ImageVector.Builder(
                                                "Outlined.FindInPage",
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
                                pathBuilder.moveTo(14.0f, 2.0f);
                                pathBuilder.lineTo(6.0f, 2.0f);
                                pathBuilder.curveToRelative(
                                        -1.1f, 0.0f, -1.99f, 0.9f, -1.99f, 2.0f);
                                pathBuilder.lineTo(4.0f, 20.0f);
                                pathBuilder.curveToRelative(0.0f, 1.1f, 0.89f, 2.0f, 1.99f, 2.0f);
                                pathBuilder.lineTo(18.0f, 22.0f);
                                pathBuilder.curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f);
                                pathBuilder.lineTo(20.0f, 8.0f);
                                pathBuilder.lineToRelative(-6.0f, -6.0f);
                                pathBuilder.close();
                                pathBuilder.moveTo(6.0f, 4.0f);
                                pathBuilder.horizontalLineToRelative(7.0f);
                                pathBuilder.lineToRelative(5.0f, 5.0f);
                                pathBuilder.verticalLineToRelative(8.58f);
                                pathBuilder.lineToRelative(-1.84f, -1.84f);
                                pathBuilder.curveToRelative(
                                        1.28f, -1.94f, 1.07f, -4.57f, -0.64f, -6.28f);
                                pathBuilder.curveTo(14.55f, 8.49f, 13.28f, 8.0f, 12.0f, 8.0f);
                                pathBuilder.curveToRelative(
                                        -1.28f, 0.0f, -2.55f, 0.49f, -3.53f, 1.46f);
                                pathBuilder.curveToRelative(
                                        -1.95f, 1.95f, -1.95f, 5.11f, 0.0f, 7.05f);
                                pathBuilder.curveToRelative(
                                        0.97f, 0.97f, 2.25f, 1.46f, 3.53f, 1.46f);
                                pathBuilder.curveToRelative(
                                        0.96f, 0.0f, 1.92f, -0.28f, 2.75f, -0.83f);
                                pathBuilder.lineTo(17.6f, 20.0f);
                                pathBuilder.lineTo(6.0f, 20.0f);
                                pathBuilder.lineTo(6.0f, 4.0f);
                                pathBuilder.close();
                                pathBuilder.moveTo(14.11f, 15.1f);
                                pathBuilder.curveToRelative(
                                        -0.56f, 0.56f, -1.31f, 0.88f, -2.11f, 0.88f);
                                pathBuilder.reflectiveCurveToRelative(
                                        -1.55f, -0.31f, -2.11f, -0.88f);
                                pathBuilder.curveToRelative(
                                        -0.56f, -0.56f, -0.88f, -1.31f, -0.88f, -2.11f);
                                pathBuilder.reflectiveCurveToRelative(0.31f, -1.55f, 0.88f, -2.11f);
                                pathBuilder.curveToRelative(
                                        0.56f, -0.57f, 1.31f, -0.88f, 2.11f, -0.88f);
                                pathBuilder.reflectiveCurveToRelative(1.55f, 0.31f, 2.11f, 0.88f);
                                pathBuilder.curveToRelative(
                                        0.56f, 0.56f, 0.88f, 1.31f, 0.88f, 2.11f);
                                pathBuilder.reflectiveCurveToRelative(-0.31f, 1.55f, -0.88f, 2.11f);
                                pathBuilder.close();
                                ImageVector.Builder.m390addPathoIyEayM$default(
                                        builder, pathBuilder._nodes, solidColor);
                                ImageVector build = builder.build();
                                FindInPageKt._findInPage = build;
                                imageVector = build;
                            }
                            IconKt.m188Iconww6aTOc(
                                    imageVector,
                                    StringResources_androidKt.stringResource(
                                            composer, R.string.search_menu_title),
                                    (Modifier) null,
                                    0L,
                                    composer,
                                    0,
                                    12);
                            return Unit.INSTANCE;
                        }
                    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static final ComposableLambdaImpl f81lambda2 =
            new ComposableLambdaImpl(
                    1684726549,
                    false,
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.scaffold.ComposableSingletons$ActionsKt$lambda-2$1
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
                            ImageVector imageVector = ClearKt._clear;
                            if (imageVector == null) {
                                ImageVector.Builder builder =
                                        new ImageVector.Builder(
                                                "Outlined.Clear",
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
                                ClearKt._clear = imageVector;
                            }
                            IconKt.m188Iconww6aTOc(
                                    imageVector,
                                    StringResources_androidKt.stringResource(
                                            composer, R.string.abc_searchview_description_clear),
                                    (Modifier) null,
                                    0L,
                                    composer,
                                    0,
                                    12);
                            return Unit.INSTANCE;
                        }
                    });
}
