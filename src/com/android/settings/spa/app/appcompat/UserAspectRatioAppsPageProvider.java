package com.android.settings.spa.app.appcompat;

import android.os.Build;
import android.os.Bundle;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.res.StringResources_androidKt;

import com.android.settings.R;
import com.android.settings.applications.appcompat.UserAspectRatioManager;
import com.android.settingslib.spa.framework.common.SettingsEntryBuilder;
import com.android.settingslib.spa.framework.common.SettingsPage;
import com.android.settingslib.spa.framework.common.SettingsPageProvider;
import com.android.settingslib.spa.framework.common.SettingsPageProviderKt;
import com.android.settingslib.spa.framework.common.SpaEnvironment;
import com.android.settingslib.spa.framework.common.SpaEnvironmentFactory;
import com.android.settingslib.spa.framework.compose.NavControllerWrapperKt;
import com.android.settingslib.spa.widget.preference.PreferenceKt;
import com.android.settingslib.spa.widget.preference.PreferenceModel;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class UserAspectRatioAppsPageProvider implements SettingsPageProvider {
    public static final UserAspectRatioAppsPageProvider INSTANCE;
    public static final SettingsPage owner;

    static {
        UserAspectRatioAppsPageProvider userAspectRatioAppsPageProvider =
                new UserAspectRatioAppsPageProvider();
        INSTANCE = userAspectRatioAppsPageProvider;
        owner = SettingsPageProviderKt.createSettingsPage(userAspectRatioAppsPageProvider, null);
    }

    public final void EntryItem(Composer composer, final int i) {
        int i2;
        final ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(692265610);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changed(this) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 11) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            final String summary = getSummary(composerImpl, (i2 & 14) | 8);
            PreferenceKt.Preference(
                    new PreferenceModel(
                            composerImpl,
                            summary) { // from class:
                                       // com.android.settings.spa.app.appcompat.UserAspectRatioAppsPageProvider$EntryItem$1
                        public final Lambda onClick;
                        public final Function0 summary;
                        public final String title;

                        {
                            this.title =
                                    StringResources_androidKt.stringResource(
                                            composerImpl, R.string.aspect_ratio_experimental_title);
                            this.summary =
                                    new Function0() { // from class:
                                                      // com.android.settings.spa.app.appcompat.UserAspectRatioAppsPageProvider$EntryItem$1$summary$1
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(0);
                                        }

                                        @Override // kotlin.jvm.functions.Function0
                                        /* renamed from: invoke */
                                        public final Object mo1068invoke() {
                                            return summary;
                                        }
                                    };
                            UserAspectRatioAppsPageProvider userAspectRatioAppsPageProvider =
                                    UserAspectRatioAppsPageProvider.INSTANCE;
                            this.onClick =
                                    (Lambda)
                                            NavControllerWrapperKt.navigator(
                                                    composerImpl, "UserAspectRatioAppsPage");
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
                                      // com.android.settings.spa.app.appcompat.UserAspectRatioAppsPageProvider$EntryItem$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            UserAspectRatioAppsPageProvider.this.EntryItem(
                                    (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final void Page(final Bundle bundle, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1397627942);
        if ((i & 1) == 0 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            UserAspectRatioAppsPageProviderKt.UserAspectRatioAppList(null, composerImpl, 0, 1);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.app.appcompat.UserAspectRatioAppsPageProvider$Page$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            UserAspectRatioAppsPageProvider.this.Page(
                                    bundle,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public final SettingsEntryBuilder buildInjectEntry() {
        SettingsEntryBuilder createInject$default =
                SettingsEntryBuilder.Companion.createInject$default(owner);
        UserAspectRatioAppsPageProvider$buildInjectEntry$1 fn =
                new Function1() { // from class:
                                  // com.android.settings.spa.app.appcompat.UserAspectRatioAppsPageProvider$buildInjectEntry$1
                    @Override // kotlin.jvm.functions.Function1
                    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                        return null;
                    }
                };
        Intrinsics.checkNotNullParameter(fn, "fn");
        createInject$default.searchDataFn = fn;
        createInject$default.isAllowSearch = true;
        createInject$default.setUiLayoutFn(
                ComposableSingletons$UserAspectRatioAppsPageProviderKt.f38lambda1);
        return createInject$default;
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final String getName() {
        return "UserAspectRatioAppsPage";
    }

    public final String getSummary(Composer composer, int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(1660677814);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        String MODEL = Build.MODEL;
        Intrinsics.checkNotNullExpressionValue(MODEL, "MODEL");
        String stringResource =
                StringResources_androidKt.stringResource(
                        R.string.aspect_ratio_summary_text, new Object[] {MODEL}, composerImpl);
        composerImpl.end(false);
        return stringResource;
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final boolean isEnabled() {
        SpaEnvironment spaEnvironment = SpaEnvironmentFactory.spaEnvironment;
        if (spaEnvironment != null) {
            return UserAspectRatioManager.isFeatureEnabled(spaEnvironment.appContext);
        }
        throw new UnsupportedOperationException("Spa environment is not set");
    }
}
