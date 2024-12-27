package com.android.settings.spa.app;

import androidx.compose.material.icons.outlined.AppsKt;
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
import com.android.settings.spa.app.backgroundinstall.BackgroundInstalledAppsPageProvider;
import com.android.settings.spa.app.specialaccess.SpecialAppAccessPageProvider;
import com.android.settingslib.spa.framework.common.SettingsEntryBuilder;
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
public abstract class ComposableSingletons$AppsMainKt {

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f34lambda1 =
            new ComposableLambdaImpl(
                    1919886774,
                    false,
                    new Function2() { // from class:
                                      // com.android.settings.spa.app.ComposableSingletons$AppsMainKt$lambda-1$1
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
                            AllAppListPageProvider allAppListPageProvider =
                                    AllAppListPageProvider.INSTANCE;
                            SettingsEntryBuilder createInject$default =
                                    SettingsEntryBuilder.Companion.createInject$default(
                                            AllAppListPageProvider.owner);
                            AllAppListPageProvider$buildInjectEntry$1 fn =
                                    AllAppListPageProvider$buildInjectEntry$1.INSTANCE;
                            Intrinsics.checkNotNullParameter(fn, "fn");
                            createInject$default.searchDataFn = fn;
                            createInject$default.isAllowSearch = true;
                            createInject$default.setUiLayoutFn(
                                    ComposableSingletons$AllAppListKt.f32lambda1);
                            createInject$default.build().UiLayout(null, composer, 64, 1);
                            SpecialAppAccessPageProvider.INSTANCE.EntryItem(composer, 8);
                            BackgroundInstalledAppsPageProvider.INSTANCE.EntryItem(composer, 8);
                            return Unit.INSTANCE;
                        }
                    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static final ComposableLambdaImpl f35lambda2 =
            new ComposableLambdaImpl(
                    1099645475,
                    false,
                    new Function2() { // from class:
                                      // com.android.settings.spa.app.ComposableSingletons$AppsMainKt$lambda-2$1
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
                            ImageVector imageVector = AppsKt._apps;
                            if (imageVector == null) {
                                ImageVector.Builder builder =
                                        new ImageVector.Builder(
                                                "Outlined.Apps",
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
                                pathBuilder.moveTo(4.0f, 8.0f);
                                pathBuilder.horizontalLineToRelative(4.0f);
                                pathBuilder.lineTo(8.0f, 4.0f);
                                pathBuilder.lineTo(4.0f, 4.0f);
                                pathBuilder.verticalLineToRelative(4.0f);
                                pathBuilder.close();
                                pathBuilder.moveTo(10.0f, 20.0f);
                                pathBuilder.horizontalLineToRelative(4.0f);
                                pathBuilder.verticalLineToRelative(-4.0f);
                                pathBuilder.horizontalLineToRelative(-4.0f);
                                pathBuilder.verticalLineToRelative(4.0f);
                                pathBuilder.close();
                                pathBuilder.moveTo(4.0f, 20.0f);
                                pathBuilder.horizontalLineToRelative(4.0f);
                                pathBuilder.verticalLineToRelative(-4.0f);
                                pathBuilder.lineTo(4.0f, 16.0f);
                                pathBuilder.verticalLineToRelative(4.0f);
                                pathBuilder.close();
                                pathBuilder.moveTo(4.0f, 14.0f);
                                pathBuilder.horizontalLineToRelative(4.0f);
                                pathBuilder.verticalLineToRelative(-4.0f);
                                pathBuilder.lineTo(4.0f, 10.0f);
                                pathBuilder.verticalLineToRelative(4.0f);
                                pathBuilder.close();
                                pathBuilder.moveTo(10.0f, 14.0f);
                                pathBuilder.horizontalLineToRelative(4.0f);
                                pathBuilder.verticalLineToRelative(-4.0f);
                                pathBuilder.horizontalLineToRelative(-4.0f);
                                pathBuilder.verticalLineToRelative(4.0f);
                                pathBuilder.close();
                                pathBuilder.moveTo(16.0f, 4.0f);
                                pathBuilder.verticalLineToRelative(4.0f);
                                pathBuilder.horizontalLineToRelative(4.0f);
                                pathBuilder.lineTo(20.0f, 4.0f);
                                pathBuilder.horizontalLineToRelative(-4.0f);
                                pathBuilder.close();
                                pathBuilder.moveTo(10.0f, 8.0f);
                                pathBuilder.horizontalLineToRelative(4.0f);
                                pathBuilder.lineTo(14.0f, 4.0f);
                                pathBuilder.horizontalLineToRelative(-4.0f);
                                pathBuilder.verticalLineToRelative(4.0f);
                                pathBuilder.close();
                                pathBuilder.moveTo(16.0f, 14.0f);
                                pathBuilder.horizontalLineToRelative(4.0f);
                                pathBuilder.verticalLineToRelative(-4.0f);
                                pathBuilder.horizontalLineToRelative(-4.0f);
                                pathBuilder.verticalLineToRelative(4.0f);
                                pathBuilder.close();
                                pathBuilder.moveTo(16.0f, 20.0f);
                                pathBuilder.horizontalLineToRelative(4.0f);
                                pathBuilder.verticalLineToRelative(-4.0f);
                                pathBuilder.horizontalLineToRelative(-4.0f);
                                pathBuilder.verticalLineToRelative(4.0f);
                                pathBuilder.close();
                                ImageVector.Builder.m390addPathoIyEayM$default(
                                        builder, pathBuilder._nodes, solidColor);
                                imageVector = builder.build();
                                AppsKt._apps = imageVector;
                            }
                            IconKt.SettingsIcon(imageVector, composer, 0);
                            return Unit.INSTANCE;
                        }
                    });

    /* renamed from: lambda-3, reason: not valid java name */
    public static final ComposableLambdaImpl f36lambda3 =
            new ComposableLambdaImpl(
                    -892455806,
                    false,
                    new Function3() { // from class:
                                      // com.android.settings.spa.app.ComposableSingletons$AppsMainKt$lambda-3$1
                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            Composer composer = (Composer) obj2;
                            ((Number) obj3).intValue();
                            OpaqueKey opaqueKey = ComposerKt.invocation;
                            PreferenceKt.Preference(
                                    new PreferenceModel(
                                            composer,
                                            StringResources_androidKt.stringResource(
                                                    composer,
                                                    R.string
                                                            .app_and_notification_dashboard_summary)) { // from class: com.android.settings.spa.app.ComposableSingletons$AppsMainKt$lambda-3$1.1
                                        public final ComposableLambdaImpl icon;
                                        public final Lambda onClick;
                                        public final Function0 summary;
                                        public final String title;

                                        {
                                            this.title =
                                                    StringResources_androidKt.stringResource(
                                                            composer,
                                                            R.string.apps_dashboard_title);
                                            this.summary =
                                                    new Function0() { // from class:
                                                                      // com.android.settings.spa.app.ComposableSingletons$AppsMainKt$lambda-3$1$1$summary$1
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
                                            AppsMainPageProvider appsMainPageProvider =
                                                    AppsMainPageProvider.INSTANCE;
                                            this.onClick =
                                                    (Lambda)
                                                            NavControllerWrapperKt.navigator(
                                                                    composer, "AppsMain");
                                            this.icon = ComposableSingletons$AppsMainKt.f35lambda2;
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
