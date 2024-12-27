package com.android.settings.spa.about;

import android.os.Bundle;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;

import com.android.settings.R;
import com.android.settingslib.spa.framework.common.SettingsPage;
import com.android.settingslib.spa.framework.common.SettingsPageProvider;
import com.android.settingslib.spa.framework.common.SettingsPageProviderKt;
import com.android.settingslib.spa.framework.common.SpaEnvironment;
import com.android.settingslib.spa.framework.common.SpaEnvironmentFactory;
import com.android.settingslib.spa.widget.scaffold.RegularScaffoldKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AboutPhonePageProvider implements SettingsPageProvider {
    public static final AboutPhonePageProvider INSTANCE;
    public static final SettingsPage owner;

    static {
        AboutPhonePageProvider aboutPhonePageProvider = new AboutPhonePageProvider();
        INSTANCE = aboutPhonePageProvider;
        owner = SettingsPageProviderKt.createSettingsPage(aboutPhonePageProvider, null);
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final void Page(final Bundle bundle, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-721382715);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        RegularScaffoldKt.RegularScaffold(
                getTitle(),
                null,
                ComposableSingletons$AboutPhoneKt.f28lambda1,
                composerImpl,
                384,
                2);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.about.AboutPhonePageProvider$Page$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            AboutPhonePageProvider.this.Page(
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
        return "AboutPhone";
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final String getTitle() {
        SpaEnvironment spaEnvironment = SpaEnvironmentFactory.spaEnvironment;
        if (spaEnvironment == null) {
            throw new UnsupportedOperationException("Spa environment is not set");
        }
        String string = spaEnvironment.appContext.getString(R.string.about_settings);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        return string;
    }
}
