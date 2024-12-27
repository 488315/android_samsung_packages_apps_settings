package com.android.settingslib.spaprivileged.template.app;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.core.os.BundleKt;

import com.android.settingslib.spa.framework.common.SettingsEntryBuilder;
import com.android.settingslib.spa.framework.common.SettingsPage;
import com.android.settingslib.spa.framework.compose.NavControllerWrapperKt;
import com.android.settingslib.spa.framework.compose.RuntimeUtilsKt;
import com.android.settingslib.spa.widget.preference.PreferenceKt;
import com.android.settingslib.spa.widget.preference.PreferenceModel;

import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public interface TogglePermissionAppListProvider {
    default void InfoPageEntryItem(final ApplicationInfo app, Composer composer, final int i) {
        Intrinsics.checkNotNullParameter(app, "app");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-615234141);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        TogglePermissionAppInfoPageKt.TogglePermissionAppInfoPageEntryItem(
                (TogglePermissionAppListModel)
                        RuntimeUtilsKt.rememberContext(
                                new TogglePermissionAppListProvider$InfoPageEntryItem$listModel$1(
                                        1,
                                        this,
                                        TogglePermissionAppListProvider.class,
                                        "createModel",
                                        "createModel(Landroid/content/Context;)Lcom/android/settingslib/spaprivileged/template/app/TogglePermissionAppListModel;",
                                        0),
                                composerImpl),
                getPermissionType(),
                app,
                composerImpl,
                512);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListProvider$InfoPageEntryItem$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            TogglePermissionAppListProvider.this.InfoPageEntryItem(
                                    app,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    default SettingsEntryBuilder buildAppListInjectEntry() {
        final String permissionType = getPermissionType();
        final Function1 function1 =
                new Function1() { // from class:
                                  // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListProvider$buildAppListInjectEntry$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        Context it = (Context) obj;
                        Intrinsics.checkNotNullParameter(it, "it");
                        return TogglePermissionAppListProvider.this.createModel(it);
                    }
                };
        SettingsEntryBuilder createInject$default =
                SettingsEntryBuilder.Companion.createInject$default(
                        SettingsPage.Companion.create$default(
                                "TogglePermissionAppList",
                                TogglePermissionAppListPageKt.PAGE_PARAMETER,
                                BundleKt.bundleOf(new Pair("permission", permissionType))));
        createInject$default.uiLayoutFn =
                new ComposableLambdaImpl(
                        1788736393,
                        true,
                        new Function3() { // from class:
                                          // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListPageProvider$Companion$buildInjectEntry$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(3);
                            }

                            @Override // kotlin.jvm.functions.Function3
                            public final Object invoke(Object obj, Object obj2, Object obj3) {
                                Composer composer = (Composer) obj2;
                                ((Number) obj3).intValue();
                                OpaqueKey opaqueKey = ComposerKt.invocation;
                                PreferenceKt.Preference(
                                        new PreferenceModel(
                                                (TogglePermissionAppListModel)
                                                        RuntimeUtilsKt.rememberContext(
                                                                function1, composer),
                                                composer,
                                                permissionType) { // from class:
                                                                  // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListPageProvider$Companion$buildInjectEntry$1.1
                                            public final Lambda onClick;
                                            public final String title;

                                            {
                                                this.title =
                                                        StringResources_androidKt.stringResource(
                                                                composer, r1.getPageTitleResId());
                                                Intrinsics.checkNotNullParameter(
                                                        permissionType, "permissionType");
                                                this.onClick =
                                                        (Lambda)
                                                                NavControllerWrapperKt.navigator(
                                                                        composer,
                                                                        "TogglePermissionAppList/"
                                                                                .concat(
                                                                                        permissionType));
                                            }

                                            /* JADX WARN: Type inference failed for: r0v1, types: [kotlin.jvm.functions.Function0, kotlin.jvm.internal.Lambda] */
                                            @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                                            public final Function0 getOnClick() {
                                                return this.onClick;
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
        return createInject$default;
    }

    TogglePermissionAppListModel createModel(Context context);

    String getPermissionType();
}
