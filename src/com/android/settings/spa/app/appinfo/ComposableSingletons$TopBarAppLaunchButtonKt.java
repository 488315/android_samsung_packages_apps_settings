package com.android.settings.spa.app.appinfo;

import androidx.compose.material.icons.automirrored.outlined.LaunchKt;
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
public abstract class ComposableSingletons$TopBarAppLaunchButtonKt {

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f46lambda1 =
            new ComposableLambdaImpl(
                    -1444901628,
                    false,
                    new Function2() { // from class:
                                      // com.android.settings.spa.app.appinfo.ComposableSingletons$TopBarAppLaunchButtonKt$lambda-1$1
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
                            ImageVector imageVector = LaunchKt._launch;
                            if (imageVector == null) {
                                ImageVector.Builder builder =
                                        new ImageVector.Builder(
                                                "AutoMirrored.Outlined.Launch",
                                                24.0f,
                                                24.0f,
                                                24.0f,
                                                24.0f,
                                                0L,
                                                0,
                                                true,
                                                96);
                                EmptyList emptyList = VectorKt.EmptyPath;
                                SolidColor solidColor = new SolidColor(Color.Black);
                                PathBuilder pathBuilder = new PathBuilder();
                                pathBuilder.moveTo(19.0f, 19.0f);
                                pathBuilder.horizontalLineTo(5.0f);
                                pathBuilder.verticalLineTo(5.0f);
                                pathBuilder.horizontalLineToRelative(7.0f);
                                pathBuilder.verticalLineTo(3.0f);
                                pathBuilder.horizontalLineTo(5.0f);
                                pathBuilder.curveToRelative(-1.11f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f);
                                pathBuilder.verticalLineToRelative(14.0f);
                                pathBuilder.curveToRelative(0.0f, 1.1f, 0.89f, 2.0f, 2.0f, 2.0f);
                                pathBuilder.horizontalLineToRelative(14.0f);
                                pathBuilder.curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f);
                                pathBuilder.verticalLineToRelative(-7.0f);
                                pathBuilder.horizontalLineToRelative(-2.0f);
                                pathBuilder.verticalLineToRelative(7.0f);
                                pathBuilder.close();
                                pathBuilder.moveTo(14.0f, 3.0f);
                                pathBuilder.verticalLineToRelative(2.0f);
                                pathBuilder.horizontalLineToRelative(3.59f);
                                pathBuilder.lineToRelative(-9.83f, 9.83f);
                                pathBuilder.lineToRelative(1.41f, 1.41f);
                                pathBuilder.lineTo(19.0f, 6.41f);
                                pathBuilder.verticalLineTo(10.0f);
                                pathBuilder.horizontalLineToRelative(2.0f);
                                pathBuilder.verticalLineTo(3.0f);
                                pathBuilder.horizontalLineToRelative(-7.0f);
                                pathBuilder.close();
                                ImageVector.Builder.m390addPathoIyEayM$default(
                                        builder, pathBuilder._nodes, solidColor);
                                imageVector = builder.build();
                                LaunchKt._launch = imageVector;
                            }
                            IconKt.m188Iconww6aTOc(
                                    imageVector,
                                    StringResources_androidKt.stringResource(
                                            composer, R.string.launch_instant_app),
                                    (Modifier) null,
                                    0L,
                                    composer,
                                    0,
                                    12);
                            return Unit.INSTANCE;
                        }
                    });
}
