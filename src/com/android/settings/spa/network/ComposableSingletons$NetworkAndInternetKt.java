package com.android.settings.spa.network;

import android.content.Context;
import android.os.UserHandle;
import android.os.UserManager;

import androidx.compose.material.icons.outlined.WifiKt;
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
import androidx.compose.ui.res.StringResources_androidKt;

import com.android.settings.R;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.Utils;
import com.android.settingslib.spa.framework.common.SpaEnvironment;
import com.android.settingslib.spa.framework.common.SpaEnvironmentFactory;
import com.android.settingslib.spa.framework.compose.NavControllerWrapperKt;
import com.android.settingslib.spa.widget.preference.PreferenceKt;
import com.android.settingslib.spa.widget.preference.PreferenceModel;
import com.android.settingslib.spa.widget.ui.IconKt;

import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ComposableSingletons$NetworkAndInternetKt {

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f54lambda1 =
            new ComposableLambdaImpl(
                    1066171497,
                    false,
                    new Function2() { // from class:
                                      // com.android.settings.spa.network.ComposableSingletons$NetworkAndInternetKt$lambda-1$1
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
                            AirplaneModePreferenceKt.AirplaneModePreference(composer, 0);
                            return Unit.INSTANCE;
                        }
                    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static final ComposableLambdaImpl f55lambda2 =
            new ComposableLambdaImpl(
                    195387862,
                    false,
                    new Function2() { // from class:
                                      // com.android.settings.spa.network.ComposableSingletons$NetworkAndInternetKt$lambda-2$1
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
                            ImageVector imageVector = WifiKt._wifi;
                            if (imageVector == null) {
                                ImageVector.Builder builder =
                                        new ImageVector.Builder(
                                                "Outlined.Wifi",
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
                                pathBuilder.moveTo(1.0f, 9.0f);
                                pathBuilder.lineToRelative(2.0f, 2.0f);
                                pathBuilder.curveToRelative(
                                        4.97f, -4.97f, 13.03f, -4.97f, 18.0f, 0.0f);
                                pathBuilder.lineToRelative(2.0f, -2.0f);
                                pathBuilder.curveTo(16.93f, 2.93f, 7.08f, 2.93f, 1.0f, 9.0f);
                                pathBuilder.close();
                                pathBuilder.moveTo(9.0f, 17.0f);
                                pathBuilder.lineToRelative(3.0f, 3.0f);
                                pathBuilder.lineToRelative(3.0f, -3.0f);
                                pathBuilder.curveToRelative(
                                        -1.65f, -1.66f, -4.34f, -1.66f, -6.0f, 0.0f);
                                pathBuilder.close();
                                pathBuilder.moveTo(5.0f, 13.0f);
                                pathBuilder.lineToRelative(2.0f, 2.0f);
                                pathBuilder.curveToRelative(
                                        2.76f, -2.76f, 7.24f, -2.76f, 10.0f, 0.0f);
                                pathBuilder.lineToRelative(2.0f, -2.0f);
                                pathBuilder.curveTo(15.14f, 9.14f, 8.87f, 9.14f, 5.0f, 13.0f);
                                pathBuilder.close();
                                ImageVector.Builder.m390addPathoIyEayM$default(
                                        builder, pathBuilder._nodes, solidColor);
                                imageVector = builder.build();
                                WifiKt._wifi = imageVector;
                            }
                            IconKt.SettingsIcon(imageVector, composer, 0);
                            return Unit.INSTANCE;
                        }
                    });

    /* renamed from: lambda-3, reason: not valid java name */
    public static final ComposableLambdaImpl f56lambda3 =
            new ComposableLambdaImpl(
                    631170869,
                    false,
                    new Function3() { // from class:
                        // com.android.settings.spa.network.ComposableSingletons$NetworkAndInternetKt$lambda-3$1
                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            Composer composer = (Composer) obj2;
                            ((Number) obj3).intValue();
                            OpaqueKey opaqueKey = ComposerKt.invocation;
                            NetworkAndInternetPageProvider networkAndInternetPageProvider =
                                    NetworkAndInternetPageProvider.INSTANCE;
                            ComposerImpl composerImpl = (ComposerImpl) composer;
                            composerImpl.startReplaceGroup(-167896141);
                            composerImpl.startReplaceGroup(-602427729);
                            Object rememberedValue = composerImpl.rememberedValue();
                            if (rememberedValue == Composer.Companion.Empty) {
                                SpaEnvironment spaEnvironment =
                                        SpaEnvironmentFactory.spaEnvironment;
                                if (spaEnvironment == null) {
                                    throw new UnsupportedOperationException(
                                            "Spa environment is not set");
                                }
                                Context context = spaEnvironment.appContext;
                                Object systemService =
                                        context.getSystemService((Class<Object>) UserManager.class);
                                Intrinsics.checkNotNull(systemService);
                                rememberedValue =
                                        Boolean.valueOf(
                                                (!((UserManager) systemService).isAdminUser()
                                                                || RestrictedLockUtilsInternal
                                                                        .hasBaseUserRestriction(
                                                                                context,
                                                                                UserHandle
                                                                                        .myUserId(),
                                                                                "no_config_mobile_networks")
                                                                || Utils.isWifiOnly(context))
                                                        ? false
                                                        : true);
                                composerImpl.updateRememberedValue(rememberedValue);
                            }
                            boolean booleanValue = ((Boolean) rememberedValue).booleanValue();
                            composerImpl.end(false);
                            int i =
                                    booleanValue
                                            ? R.string.network_dashboard_summary_mobile
                                            : R.string.network_dashboard_summary_no_mobile;
                            composerImpl.end(false);
                            PreferenceKt.Preference(
                                    new PreferenceModel(
                                            composer,
                                            StringResources_androidKt.stringResource(
                                                    composer, i)) { // from class:
                                        // com.android.settings.spa.network.ComposableSingletons$NetworkAndInternetKt$lambda-3$1.1
                                        public final ComposableLambdaImpl icon;
                                        public final Lambda onClick;
                                        public final Function0 summary;
                                        public final String title;

                                        {
                                            this.title =
                                                    StringResources_androidKt.stringResource(
                                                            composer,
                                                            R.string.network_dashboard_title);
                                            this.summary =
                                                    new Function0() { // from class:
                                                        // com.android.settings.spa.network.ComposableSingletons$NetworkAndInternetKt$lambda-3$1$1$summary$1
                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                        {
                                                            super(0);
                                                        }

                                                        @Override // kotlin.jvm.functions.Function0
                                                        /* renamed from: invoke */
                                                        public final Object mo1068invoke() {
                                                            return r1;
                                                        }
                                                    };
                                            NetworkAndInternetPageProvider
                                                    networkAndInternetPageProvider2 =
                                                            NetworkAndInternetPageProvider.INSTANCE;
                                            this.onClick =
                                                    (Lambda)
                                                            NavControllerWrapperKt.navigator(
                                                                    composer, "NetworkAndInternet");
                                            this.icon =
                                                    ComposableSingletons$NetworkAndInternetKt
                                                            .f55lambda2;
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
                                    composer,
                                    0,
                                    2);
                            return Unit.INSTANCE;
                        }
                    });
}
