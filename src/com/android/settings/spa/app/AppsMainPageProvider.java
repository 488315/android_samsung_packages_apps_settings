package com.android.settings.spa.app;

import android.os.Bundle;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;

import com.android.settings.R;
import com.android.settings.spa.app.backgroundinstall.BackgroundInstalledAppsPageProvider;
import com.android.settings.spa.app.specialaccess.SpecialAppAccessPageProvider;
import com.android.settingslib.spa.framework.common.SettingsEntry;
import com.android.settingslib.spa.framework.common.SettingsEntryBuilder;
import com.android.settingslib.spa.framework.common.SettingsPage;
import com.android.settingslib.spa.framework.common.SettingsPageProvider;
import com.android.settingslib.spa.framework.common.SettingsPageProviderKt;
import com.android.settingslib.spa.framework.common.SpaEnvironment;
import com.android.settingslib.spa.framework.common.SpaEnvironmentFactory;
import com.android.settingslib.spa.widget.scaffold.RegularScaffoldKt;

import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppsMainPageProvider implements SettingsPageProvider {
    public static final AppsMainPageProvider INSTANCE;
    public static final SettingsPage owner;

    static {
        AppsMainPageProvider appsMainPageProvider = new AppsMainPageProvider();
        INSTANCE = appsMainPageProvider;
        owner = SettingsPageProviderKt.createSettingsPage(appsMainPageProvider, null);
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final void Page(final Bundle bundle, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-469952207);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        RegularScaffoldKt.RegularScaffold(
                getTitle(), null, ComposableSingletons$AppsMainKt.f34lambda1, composerImpl, 384, 2);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.app.AppsMainPageProvider$Page$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            AppsMainPageProvider.this.Page(
                                    bundle,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final List buildEntry(Bundle bundle) {
        AllAppListPageProvider allAppListPageProvider = AllAppListPageProvider.INSTANCE;
        SettingsEntryBuilder createInject$default =
                SettingsEntryBuilder.Companion.createInject$default(AllAppListPageProvider.owner);
        AllAppListPageProvider$buildInjectEntry$1 fn =
                AllAppListPageProvider$buildInjectEntry$1.INSTANCE;
        Intrinsics.checkNotNullParameter(fn, "fn");
        createInject$default.searchDataFn = fn;
        createInject$default.isAllowSearch = true;
        createInject$default.setUiLayoutFn(ComposableSingletons$AllAppListKt.f32lambda1);
        SettingsPage settingsPage = owner;
        SettingsEntryBuilder.setLink$default(createInject$default, settingsPage, null, 2);
        SettingsEntry build = createInject$default.build();
        SettingsEntryBuilder createInject$default2 =
                SettingsEntryBuilder.Companion.createInject$default(
                        SpecialAppAccessPageProvider.owner);
        SettingsEntryBuilder.setLink$default(createInject$default2, settingsPage, null, 2);
        SettingsEntry build2 = createInject$default2.build();
        SettingsEntryBuilder buildInjectEntry =
                BackgroundInstalledAppsPageProvider.INSTANCE.buildInjectEntry();
        SettingsEntryBuilder.setLink$default(buildInjectEntry, settingsPage, null, 2);
        return CollectionsKt__CollectionsKt.listOf(
                (Object[]) new SettingsEntry[] {build, build2, buildInjectEntry.build()});
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final String getName() {
        return "AppsMain";
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final String getTitle() {
        SpaEnvironment spaEnvironment = SpaEnvironmentFactory.spaEnvironment;
        if (spaEnvironment == null) {
            throw new UnsupportedOperationException("Spa environment is not set");
        }
        String string = spaEnvironment.appContext.getString(R.string.apps_dashboard_title);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        return string;
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final boolean isEnabled() {
        return false;
    }
}