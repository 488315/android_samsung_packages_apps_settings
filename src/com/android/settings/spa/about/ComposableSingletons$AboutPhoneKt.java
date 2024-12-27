package com.android.settings.spa.about;

import android.content.Context;

import androidx.compose.material.icons.outlined.PermDeviceInformationKt;
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
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;

import com.android.settings.R;
import com.android.settingslib.spa.framework.compose.NavControllerWrapperKt;
import com.android.settingslib.spa.widget.preference.PreferenceKt;
import com.android.settingslib.spa.widget.preference.PreferenceModel;
import com.android.settingslib.spa.widget.ui.IconKt;

import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ComposableSingletons$AboutPhoneKt {

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f28lambda1 =
            new ComposableLambdaImpl(
                    -1940494454,
                    false,
                    new Function2() { // from class:
                                      // com.android.settings.spa.about.ComposableSingletons$AboutPhoneKt$lambda-1$1
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
                            BasicInfoCategory.INSTANCE.CategoryItems(composer, 6);
                            return Unit.INSTANCE;
                        }
                    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static final ComposableLambdaImpl f29lambda2 =
            new ComposableLambdaImpl(
                    1257553463,
                    false,
                    new Function2() { // from class:
                                      // com.android.settings.spa.about.ComposableSingletons$AboutPhoneKt$lambda-2$1
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
                                    PermDeviceInformationKt._permDeviceInformation;
                            if (imageVector == null) {
                                ImageVector.Builder builder =
                                        new ImageVector.Builder(
                                                "Outlined.PermDeviceInformation",
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
                                pathBuilder.moveTo(11.0f, 7.0f);
                                pathBuilder.horizontalLineToRelative(2.0f);
                                pathBuilder.verticalLineToRelative(2.0f);
                                pathBuilder.horizontalLineToRelative(-2.0f);
                                pathBuilder.close();
                                pathBuilder.moveTo(11.0f, 11.0f);
                                pathBuilder.horizontalLineToRelative(2.0f);
                                pathBuilder.verticalLineToRelative(6.0f);
                                pathBuilder.horizontalLineToRelative(-2.0f);
                                pathBuilder.close();
                                pathBuilder.moveTo(17.0f, 1.01f);
                                pathBuilder.lineTo(7.0f, 1.0f);
                                pathBuilder.curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f);
                                pathBuilder.verticalLineToRelative(18.0f);
                                pathBuilder.curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f);
                                pathBuilder.horizontalLineToRelative(10.0f);
                                pathBuilder.curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f);
                                pathBuilder.lineTo(19.0f, 3.0f);
                                pathBuilder.curveToRelative(
                                        0.0f, -1.1f, -0.9f, -1.99f, -2.0f, -1.99f);
                                pathBuilder.close();
                                pathBuilder.moveTo(17.0f, 21.0f);
                                pathBuilder.lineTo(7.0f, 21.0f);
                                pathBuilder.verticalLineToRelative(-1.0f);
                                pathBuilder.horizontalLineToRelative(10.0f);
                                pathBuilder.verticalLineToRelative(1.0f);
                                pathBuilder.close();
                                pathBuilder.moveTo(17.0f, 18.0f);
                                pathBuilder.lineTo(7.0f, 18.0f);
                                pathBuilder.lineTo(7.0f, 6.0f);
                                pathBuilder.horizontalLineToRelative(10.0f);
                                pathBuilder.verticalLineToRelative(12.0f);
                                pathBuilder.close();
                                pathBuilder.moveTo(7.0f, 4.0f);
                                pathBuilder.lineTo(7.0f, 3.0f);
                                pathBuilder.horizontalLineToRelative(10.0f);
                                pathBuilder.verticalLineToRelative(1.0f);
                                pathBuilder.lineTo(7.0f, 4.0f);
                                pathBuilder.close();
                                ImageVector.Builder.m390addPathoIyEayM$default(
                                        builder, pathBuilder._nodes, solidColor);
                                imageVector = builder.build();
                                PermDeviceInformationKt._permDeviceInformation = imageVector;
                            }
                            IconKt.SettingsIcon(imageVector, composer, 0);
                            return Unit.INSTANCE;
                        }
                    });

    /* renamed from: lambda-3, reason: not valid java name */
    public static final ComposableLambdaImpl f30lambda3 =
            new ComposableLambdaImpl(
                    -1607848618,
                    false,
                    new Function3() { // from class:
                                      // com.android.settings.spa.about.ComposableSingletons$AboutPhoneKt$lambda-3$1
                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            ((Number) obj3).intValue();
                            OpaqueKey opaqueKey = ComposerKt.invocation;
                            ComposerImpl composerImpl = (ComposerImpl) ((Composer) obj2);
                            Context context =
                                    (Context)
                                            composerImpl.consume(
                                                    AndroidCompositionLocals_androidKt
                                                            .LocalContext);
                            composerImpl.startReplaceGroup(-1919907229);
                            Object rememberedValue = composerImpl.rememberedValue();
                            if (rememberedValue == Composer.Companion.Empty) {
                                rememberedValue = new DeviceNamePresenter(context);
                                composerImpl.updateRememberedValue(rememberedValue);
                            }
                            composerImpl.end(false);
                            PreferenceKt.Preference(
                                    new PreferenceModel(
                                            composerImpl,
                                            (DeviceNamePresenter)
                                                    rememberedValue) { // from class:
                                                                       // com.android.settings.spa.about.ComposableSingletons$AboutPhoneKt$lambda-3$1.1
                                        public final ComposableLambdaImpl icon;
                                        public final Lambda onClick;
                                        public final Function0 summary;
                                        public final String title;

                                        {
                                            this.title =
                                                    StringResources_androidKt.stringResource(
                                                            composerImpl, R.string.about_settings);
                                            this.summary =
                                                    new Function0() { // from class:
                                                                      // com.android.settings.spa.about.ComposableSingletons$AboutPhoneKt$lambda-3$1$1$summary$1
                                                        {
                                                            super(0);
                                                        }

                                                        @Override // kotlin.jvm.functions.Function0
                                                        /* renamed from: invoke */
                                                        public final Object mo1068invoke() {
                                                            return DeviceNamePresenter.this
                                                                    .deviceNamePreferenceController
                                                                    .getSummary()
                                                                    .toString();
                                                        }
                                                    };
                                            AboutPhonePageProvider aboutPhonePageProvider =
                                                    AboutPhonePageProvider.INSTANCE;
                                            this.onClick =
                                                    (Lambda)
                                                            NavControllerWrapperKt.navigator(
                                                                    composerImpl, "AboutPhone");
                                            this.icon =
                                                    ComposableSingletons$AboutPhoneKt.f29lambda2;
                                        }

                                        @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                                        public final Function2 getIcon() {
                                            return this.icon;
                                        }

                                        /* JADX WARN: Type inference failed for: r0v1, types: [kotlin.jvm.functions.Function0, kotlin.jvm.internal.Lambda] */
                                        @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                                        public final Function0 getOnClick() {
                                            return this.onClick;
                                        }

                                        @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                                        public final Function0 getSummary() {
                                            return this.summary;
                                        }

                                        @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                                        public final String getTitle() {
                                            return this.title;
                                        }
                                    },
                                    false,
                                    composerImpl,
                                    0,
                                    2);
                            return Unit.INSTANCE;
                        }
                    });
}
