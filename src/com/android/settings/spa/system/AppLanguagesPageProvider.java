package com.android.settings.spa.system;

import android.os.Bundle;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.res.StringResources_androidKt;

import com.android.settings.R;
import com.android.settingslib.spa.framework.common.SettingsPageProvider;
import com.android.settingslib.spa.framework.compose.NavControllerWrapperKt;
import com.android.settingslib.spa.framework.compose.RuntimeUtilsKt;
import com.android.settingslib.spa.widget.preference.PreferenceKt;
import com.android.settingslib.spa.widget.preference.PreferenceModel;
import com.android.settingslib.spaprivileged.model.app.AppListModel;
import com.android.settingslib.spaprivileged.template.app.AppListPageKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppLanguagesPageProvider implements SettingsPageProvider {
    public static final AppLanguagesPageProvider INSTANCE = new AppLanguagesPageProvider();

    public final void EntryItem(Composer composer, final int i) {
        final ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(2048242449);
        if ((i & 1) == 0 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            final String stringResource =
                    StringResources_androidKt.stringResource(
                            composerImpl, R.string.app_locale_picker_summary);
            PreferenceKt.Preference(
                    new PreferenceModel(
                            composerImpl,
                            stringResource) { // from class:
                                              // com.android.settings.spa.system.AppLanguagesPageProvider$EntryItem$1
                        public final Lambda onClick;
                        public final Function0 summary;
                        public final String title;

                        {
                            this.title =
                                    StringResources_androidKt.stringResource(
                                            composerImpl, R.string.app_locales_picker_menu_title);
                            this.summary =
                                    new Function0() { // from class:
                                                      // com.android.settings.spa.system.AppLanguagesPageProvider$EntryItem$1$summary$1
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(0);
                                        }

                                        @Override // kotlin.jvm.functions.Function0
                                        /* renamed from: invoke */
                                        public final Object mo1068invoke() {
                                            return stringResource;
                                        }
                                    };
                            this.onClick =
                                    (Lambda)
                                            NavControllerWrapperKt.navigator(
                                                    composerImpl, "AppLanguages");
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
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.system.AppLanguagesPageProvider$EntryItem$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            AppLanguagesPageProvider.this.EntryItem(
                                    (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final void Page(final Bundle bundle, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-463755787);
        if ((i & 1) == 0 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            AppListPageKt.AppListPage(
                    StringResources_androidKt.stringResource(
                            composerImpl, R.string.app_locales_picker_menu_title),
                    (AppListModel)
                            RuntimeUtilsKt.rememberContext(
                                    AppLanguagesPageProvider$Page$1.INSTANCE, composerImpl),
                    false,
                    true,
                    false,
                    null,
                    null,
                    ComposableSingletons$AppLanguagesKt.f62lambda1,
                    null,
                    composerImpl,
                    12586048,
                    372);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.system.AppLanguagesPageProvider$Page$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            AppLanguagesPageProvider.this.Page(
                                    bundle,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final String getName() {
        return "AppLanguages";
    }
}
