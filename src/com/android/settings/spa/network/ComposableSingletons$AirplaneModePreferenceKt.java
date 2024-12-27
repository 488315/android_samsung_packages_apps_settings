package com.android.settings.spa.network;

import androidx.compose.material.icons.outlined.AirplanemodeActiveKt;
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
public abstract class ComposableSingletons$AirplaneModePreferenceKt {

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f53lambda1 =
            new ComposableLambdaImpl(
                    1309605632,
                    false,
                    new Function2() { // from class:
                                      // com.android.settings.spa.network.ComposableSingletons$AirplaneModePreferenceKt$lambda-1$1
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
                            ImageVector imageVector = AirplanemodeActiveKt._airplanemodeActive;
                            if (imageVector == null) {
                                ImageVector.Builder builder =
                                        new ImageVector.Builder(
                                                "Outlined.AirplanemodeActive",
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
                                pathBuilder.moveTo(22.0f, 16.0f);
                                pathBuilder.verticalLineToRelative(-2.0f);
                                pathBuilder.lineToRelative(-8.5f, -5.0f);
                                pathBuilder.verticalLineTo(3.5f);
                                pathBuilder.curveTo(13.5f, 2.67f, 12.83f, 2.0f, 12.0f, 2.0f);
                                pathBuilder.reflectiveCurveToRelative(-1.5f, 0.67f, -1.5f, 1.5f);
                                pathBuilder.verticalLineTo(9.0f);
                                pathBuilder.lineTo(2.0f, 14.0f);
                                pathBuilder.verticalLineToRelative(2.0f);
                                pathBuilder.lineToRelative(8.5f, -2.5f);
                                pathBuilder.verticalLineTo(19.0f);
                                pathBuilder.lineTo(8.0f, 20.5f);
                                pathBuilder.lineTo(8.0f, 22.0f);
                                pathBuilder.lineToRelative(4.0f, -1.0f);
                                pathBuilder.lineToRelative(4.0f, 1.0f);
                                pathBuilder.lineToRelative(0.0f, -1.5f);
                                pathBuilder.lineTo(13.5f, 19.0f);
                                pathBuilder.verticalLineToRelative(-5.5f);
                                pathBuilder.lineTo(22.0f, 16.0f);
                                pathBuilder.close();
                                ImageVector.Builder.m390addPathoIyEayM$default(
                                        builder, pathBuilder._nodes, solidColor);
                                imageVector = builder.build();
                                AirplanemodeActiveKt._airplanemodeActive = imageVector;
                            }
                            IconKt.SettingsIcon(imageVector, composer, 0);
                            return Unit.INSTANCE;
                        }
                    });
}
