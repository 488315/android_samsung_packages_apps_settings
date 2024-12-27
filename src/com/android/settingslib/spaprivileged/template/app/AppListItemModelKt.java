package com.android.settingslib.spaprivileged.template.app;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;

import com.android.settingslib.spa.framework.theme.SettingsDimension;
import com.android.settingslib.spa.widget.preference.PreferenceKt;
import com.android.settingslib.spa.widget.preference.PreferenceModel;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AppListItemModelKt {
    public static final void AppListItem(
            final AppListItemModel appListItemModel,
            final Function0 onClick,
            Composer composer,
            final int i) {
        int i2;
        Intrinsics.checkNotNullParameter(appListItemModel, "<this>");
        Intrinsics.checkNotNullParameter(onClick, "onClick");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1906618182);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changed(appListItemModel) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 112) == 0) {
            i2 |= composerImpl.changedInstance(onClick) ? 32 : 16;
        }
        if ((i2 & 91) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            composerImpl.startReplaceGroup(-1401064084);
            Object rememberedValue = composerImpl.rememberedValue();
            if (rememberedValue == Composer.Companion.Empty) {
                rememberedValue =
                        new PreferenceModel(
                                appListItemModel,
                                onClick) { // from class:
                                           // com.android.settingslib.spaprivileged.template.app.AppListItemModelKt$AppListItem$1$1
                            public final ComposableLambdaImpl icon;
                            public final Function0 onClick;
                            public final Function0 summary;
                            public final String title;

                            {
                                this.title = appListItemModel.label;
                                this.summary = appListItemModel.summary;
                                this.icon =
                                        new ComposableLambdaImpl(
                                                150725380,
                                                true,
                                                new Function2() { // from class:
                                                                  // com.android.settingslib.spaprivileged.template.app.AppListItemModelKt$AppListItem$1$1$icon$1
                                                    {
                                                        super(2);
                                                    }

                                                    @Override // kotlin.jvm.functions.Function2
                                                    public final Object invoke(
                                                            Object obj, Object obj2) {
                                                        Composer composer2 = (Composer) obj;
                                                        if ((((Number) obj2).intValue() & 11)
                                                                == 2) {
                                                            ComposerImpl composerImpl2 =
                                                                    (ComposerImpl) composer2;
                                                            if (composerImpl2.getSkipping()) {
                                                                composerImpl2.skipToGroupEnd();
                                                                return Unit.INSTANCE;
                                                            }
                                                        }
                                                        OpaqueKey opaqueKey2 =
                                                                ComposerKt.invocation;
                                                        AppInfoKt.m1050AppIconziNgDLE(
                                                                AppListItemModel.this.record
                                                                        .getApp(),
                                                                SettingsDimension.appIconItemSize,
                                                                composer2,
                                                                8);
                                                        return Unit.INSTANCE;
                                                    }
                                                });
                                this.onClick = onClick;
                            }

                            @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                            public final Function2 getIcon() {
                                return this.icon;
                            }

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
                        };
                composerImpl.updateRememberedValue(rememberedValue);
            }
            composerImpl.end(false);
            PreferenceKt.Preference(
                    (AppListItemModelKt$AppListItem$1$1) rememberedValue,
                    false,
                    composerImpl,
                    6,
                    2);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spaprivileged.template.app.AppListItemModelKt$AppListItem$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            AppListItemModelKt.AppListItem(
                                    AppListItemModel.this,
                                    onClick,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
