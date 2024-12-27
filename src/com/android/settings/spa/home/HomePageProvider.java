package com.android.settings.spa.home;

import android.os.Bundle;

import com.android.settings.R;
import com.android.settings.spa.about.AboutPhonePageProvider;
import com.android.settings.spa.about.ComposableSingletons$AboutPhoneKt;
import com.android.settings.spa.app.AppsMainPageProvider;
import com.android.settings.spa.app.ComposableSingletons$AppsMainKt;
import com.android.settings.spa.network.ComposableSingletons$NetworkAndInternetKt;
import com.android.settings.spa.network.NetworkAndInternetPageProvider;
import com.android.settings.spa.notification.ComposableSingletons$NotificationMainKt;
import com.android.settings.spa.notification.NotificationMainPageProvider;
import com.android.settings.spa.system.ComposableSingletons$SystemMainKt;
import com.android.settings.spa.system.SystemMainPageProvider;
import com.android.settingslib.spa.framework.common.SettingsEntry;
import com.android.settingslib.spa.framework.common.SettingsEntryBuilder;
import com.android.settingslib.spa.framework.common.SettingsPage;
import com.android.settingslib.spa.framework.common.SettingsPageProvider;
import com.android.settingslib.spa.framework.common.SettingsPageProviderKt;
import com.android.settingslib.spa.framework.common.SpaEnvironment;
import com.android.settingslib.spa.framework.common.SpaEnvironmentFactory;

import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class HomePageProvider implements SettingsPageProvider {
    public static final HomePageProvider INSTANCE;
    public static final SettingsPage owner;

    static {
        HomePageProvider homePageProvider = new HomePageProvider();
        INSTANCE = homePageProvider;
        owner = SettingsPageProviderKt.createSettingsPage(homePageProvider, null);
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final List buildEntry(Bundle bundle) {
        SettingsEntryBuilder createInject$default =
                SettingsEntryBuilder.Companion.createInject$default(
                        NetworkAndInternetPageProvider.owner);
        createInject$default.setUiLayoutFn(ComposableSingletons$NetworkAndInternetKt.f56lambda3);
        SettingsPage settingsPage = owner;
        SettingsEntryBuilder.setLink$default(createInject$default, settingsPage, null, 2);
        SettingsEntry build = createInject$default.build();
        SettingsEntryBuilder createInject$default2 =
                SettingsEntryBuilder.Companion.createInject$default(AppsMainPageProvider.owner);
        createInject$default2.setUiLayoutFn(ComposableSingletons$AppsMainKt.f36lambda3);
        SettingsEntryBuilder.setLink$default(createInject$default2, settingsPage, null, 2);
        SettingsEntry build2 = createInject$default2.build();
        SettingsEntryBuilder createInject$default3 =
                SettingsEntryBuilder.Companion.createInject$default(
                        NotificationMainPageProvider.owner);
        createInject$default3.setUiLayoutFn(ComposableSingletons$NotificationMainKt.f60lambda3);
        SettingsEntryBuilder.setLink$default(createInject$default3, settingsPage, null, 2);
        SettingsEntry build3 = createInject$default3.build();
        SettingsEntryBuilder createInject$default4 =
                SettingsEntryBuilder.Companion.createInject$default(SystemMainPageProvider.owner);
        createInject$default4.setUiLayoutFn(ComposableSingletons$SystemMainKt.f67lambda3);
        SettingsEntryBuilder.setLink$default(createInject$default4, settingsPage, null, 2);
        SettingsEntry build4 = createInject$default4.build();
        SettingsEntryBuilder createInject$default5 =
                SettingsEntryBuilder.Companion.createInject$default(AboutPhonePageProvider.owner);
        createInject$default5.setUiLayoutFn(ComposableSingletons$AboutPhoneKt.f30lambda3);
        SettingsEntryBuilder.setLink$default(createInject$default5, settingsPage, null, 2);
        return CollectionsKt__CollectionsKt.listOf(
                (Object[])
                        new SettingsEntry[] {
                            build, build2, build3, build4, createInject$default5.build()
                        });
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final String getName() {
        return "Home";
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final String getTitle() {
        SpaEnvironment spaEnvironment = SpaEnvironmentFactory.spaEnvironment;
        if (spaEnvironment == null) {
            throw new UnsupportedOperationException("Spa environment is not set");
        }
        String string = spaEnvironment.appContext.getString(R.string.settings_label);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        return string;
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final boolean isEnabled() {
        return false;
    }
}
