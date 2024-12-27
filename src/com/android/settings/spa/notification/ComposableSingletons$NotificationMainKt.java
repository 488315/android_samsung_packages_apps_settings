package com.android.settings.spa.notification;

import androidx.compose.material.icons.outlined.NotificationsKt;
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
public abstract class ComposableSingletons$NotificationMainKt {

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f58lambda1 =
            new ComposableLambdaImpl(
                    649954048,
                    false,
                    new Function2() { // from class:
                                      // com.android.settings.spa.notification.ComposableSingletons$NotificationMainKt$lambda-1$1
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
                            AppListNotificationsPageProvider.INSTANCE.EntryItem(composer, 6);
                            return Unit.INSTANCE;
                        }
                    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static final ComposableLambdaImpl f59lambda2 =
            new ComposableLambdaImpl(
                    -574534861,
                    false,
                    new Function2() { // from class:
                                      // com.android.settings.spa.notification.ComposableSingletons$NotificationMainKt$lambda-2$1
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
                            ImageVector imageVector = NotificationsKt._notifications;
                            if (imageVector == null) {
                                ImageVector.Builder builder =
                                        new ImageVector.Builder(
                                                "Outlined.Notifications",
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
                                pathBuilder.moveTo(12.0f, 22.0f);
                                pathBuilder.curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f);
                                pathBuilder.horizontalLineToRelative(-4.0f);
                                pathBuilder.curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f);
                                pathBuilder.close();
                                pathBuilder.moveTo(18.0f, 16.0f);
                                pathBuilder.verticalLineToRelative(-5.0f);
                                pathBuilder.curveToRelative(
                                        0.0f, -3.07f, -1.63f, -5.64f, -4.5f, -6.32f);
                                pathBuilder.lineTo(13.5f, 4.0f);
                                pathBuilder.curveToRelative(
                                        0.0f, -0.83f, -0.67f, -1.5f, -1.5f, -1.5f);
                                pathBuilder.reflectiveCurveToRelative(-1.5f, 0.67f, -1.5f, 1.5f);
                                pathBuilder.verticalLineToRelative(0.68f);
                                pathBuilder.curveTo(7.64f, 5.36f, 6.0f, 7.92f, 6.0f, 11.0f);
                                pathBuilder.verticalLineToRelative(5.0f);
                                pathBuilder.lineToRelative(-2.0f, 2.0f);
                                pathBuilder.verticalLineToRelative(1.0f);
                                pathBuilder.horizontalLineToRelative(16.0f);
                                pathBuilder.verticalLineToRelative(-1.0f);
                                pathBuilder.lineToRelative(-2.0f, -2.0f);
                                pathBuilder.close();
                                pathBuilder.moveTo(16.0f, 17.0f);
                                pathBuilder.lineTo(8.0f, 17.0f);
                                pathBuilder.verticalLineToRelative(-6.0f);
                                pathBuilder.curveToRelative(
                                        0.0f, -2.48f, 1.51f, -4.5f, 4.0f, -4.5f);
                                pathBuilder.reflectiveCurveToRelative(4.0f, 2.02f, 4.0f, 4.5f);
                                pathBuilder.verticalLineToRelative(6.0f);
                                pathBuilder.close();
                                ImageVector.Builder.m390addPathoIyEayM$default(
                                        builder, pathBuilder._nodes, solidColor);
                                imageVector = builder.build();
                                NotificationsKt._notifications = imageVector;
                            }
                            IconKt.SettingsIcon(imageVector, composer, 0);
                            return Unit.INSTANCE;
                        }
                    });

    /* renamed from: lambda-3, reason: not valid java name */
    public static final ComposableLambdaImpl f60lambda3 =
            new ComposableLambdaImpl(
                    49836468,
                    false,
                    new Function3() { // from class:
                                      // com.android.settings.spa.notification.ComposableSingletons$NotificationMainKt$lambda-3$1
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
                                                            .notification_dashboard_summary)) { // from class: com.android.settings.spa.notification.ComposableSingletons$NotificationMainKt$lambda-3$1.1
                                        public final ComposableLambdaImpl icon;
                                        public final Lambda onClick;
                                        public final Function0 summary;
                                        public final String title;

                                        {
                                            this.title =
                                                    StringResources_androidKt.stringResource(
                                                            composer,
                                                            R.string
                                                                    .configure_notification_settings);
                                            this.summary =
                                                    new Function0() { // from class:
                                                                      // com.android.settings.spa.notification.ComposableSingletons$NotificationMainKt$lambda-3$1$1$summary$1
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
                                            NotificationMainPageProvider
                                                    notificationMainPageProvider =
                                                            NotificationMainPageProvider.INSTANCE;
                                            this.onClick =
                                                    (Lambda)
                                                            NavControllerWrapperKt.navigator(
                                                                    composer, "NotificationMain");
                                            this.icon =
                                                    ComposableSingletons$NotificationMainKt
                                                            .f59lambda2;
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
