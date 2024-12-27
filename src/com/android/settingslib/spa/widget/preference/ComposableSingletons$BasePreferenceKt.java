package com.android.settingslib.spa.widget.preference;

import androidx.compose.material.icons.outlined.BatteryChargingFullKt;
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

import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ComposableSingletons$BasePreferenceKt {

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f78lambda1 =
            new ComposableLambdaImpl(
                    -770959786,
                    false,
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.preference.ComposableSingletons$BasePreferenceKt$lambda-1$1
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
                            return Unit.INSTANCE;
                        }
                    });

    /* renamed from: lambda-3, reason: not valid java name */
    public static final ComposableLambdaImpl f79lambda3;

    static {
        int i = ComposableSingletons$BasePreferenceKt$lambda2$1.$r8$clinit;
        f79lambda3 =
                new ComposableLambdaImpl(
                        1561129344,
                        false,
                        new Function2() { // from class:
                                          // com.android.settingslib.spa.widget.preference.ComposableSingletons$BasePreferenceKt$lambda-3$1
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
                                ImageVector imageVector =
                                        BatteryChargingFullKt._batteryChargingFull;
                                if (imageVector == null) {
                                    ImageVector.Builder builder =
                                            new ImageVector.Builder(
                                                    "Outlined.BatteryChargingFull",
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
                                    pathBuilder.moveTo(15.67f, 4.0f);
                                    pathBuilder.horizontalLineTo(14.0f);
                                    pathBuilder.verticalLineTo(2.0f);
                                    pathBuilder.horizontalLineToRelative(-4.0f);
                                    pathBuilder.verticalLineToRelative(2.0f);
                                    pathBuilder.horizontalLineTo(8.33f);
                                    pathBuilder.curveTo(7.6f, 4.0f, 7.0f, 4.6f, 7.0f, 5.33f);
                                    pathBuilder.verticalLineToRelative(15.33f);
                                    pathBuilder.curveTo(7.0f, 21.4f, 7.6f, 22.0f, 8.33f, 22.0f);
                                    pathBuilder.horizontalLineToRelative(7.33f);
                                    pathBuilder.curveToRelative(
                                            0.74f, 0.0f, 1.34f, -0.6f, 1.34f, -1.33f);
                                    pathBuilder.verticalLineTo(5.33f);
                                    pathBuilder.curveTo(17.0f, 4.6f, 16.4f, 4.0f, 15.67f, 4.0f);
                                    pathBuilder.close();
                                    pathBuilder.moveTo(11.0f, 20.0f);
                                    pathBuilder.verticalLineToRelative(-5.5f);
                                    pathBuilder.horizontalLineTo(9.0f);
                                    pathBuilder.lineTo(13.0f, 7.0f);
                                    pathBuilder.verticalLineToRelative(5.5f);
                                    pathBuilder.horizontalLineToRelative(2.0f);
                                    pathBuilder.lineTo(11.0f, 20.0f);
                                    pathBuilder.close();
                                    ImageVector.Builder.m390addPathoIyEayM$default(
                                            builder, pathBuilder._nodes, solidColor);
                                    imageVector = builder.build();
                                    BatteryChargingFullKt._batteryChargingFull = imageVector;
                                }
                                IconKt.m188Iconww6aTOc(
                                        imageVector,
                                        (String) null,
                                        (Modifier) null,
                                        0L,
                                        composer,
                                        48,
                                        12);
                                return Unit.INSTANCE;
                            }
                        });
        int i2 = ComposableSingletons$BasePreferenceKt$lambda4$1.$r8$clinit;
    }
}
