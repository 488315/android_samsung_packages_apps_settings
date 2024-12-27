package com.android.settingslib.spa.framework.common;

import android.os.Bundle;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;

import com.android.settings.spa.SettingsSpaEnvironment;
import com.android.settingslib.spa.framework.util.ParameterKt;
import com.android.settingslib.spa.framework.util.UniqueIdKt;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

import java.util.LinkedHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SettingsPageProviderKt {
    public static final SettingsPage createSettingsPage(
            SettingsPageProvider settingsPageProvider, Bundle bundle) {
        Intrinsics.checkNotNullParameter(settingsPageProvider, "<this>");
        return new SettingsPage(
                UniqueIdKt.genPageId(
                        settingsPageProvider.getName(),
                        settingsPageProvider.getParameter(),
                        bundle),
                settingsPageProvider.getName(),
                settingsPageProvider.getMetricsCategory(),
                AbstractResolvableFuture$$ExternalSyntheticOutline0.m(
                        settingsPageProvider.getName(),
                        CollectionsKt___CollectionsKt.joinToString$default(
                                ParameterKt.normalizeArgList(
                                        settingsPageProvider.getParameter(), bundle, true),
                                ApnSettings.MVNO_NONE,
                                null,
                                null,
                                new Function1() { // from class:
                                                  // com.android.settingslib.spa.framework.common.SettingsPageProviderKt$createSettingsPage$1
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj) {
                                        String arg = (String) obj;
                                        Intrinsics.checkNotNullParameter(arg, "arg");
                                        return "/".concat(arg);
                                    }
                                },
                                30)),
                settingsPageProvider.getParameter(),
                bundle);
    }

    public static final SettingsPageProvider getPageProvider(String sppName) {
        Intrinsics.checkNotNullParameter(sppName, "sppName");
        SpaEnvironment spaEnvironment = SpaEnvironmentFactory.spaEnvironment;
        if (spaEnvironment == null) {
            return null;
        }
        if (spaEnvironment == null) {
            throw new UnsupportedOperationException("Spa environment is not set");
        }
        SettingsPageProviderRepository settingsPageProviderRepository =
                (SettingsPageProviderRepository)
                        ((SettingsSpaEnvironment) spaEnvironment).pageProviderRepository.getValue();
        settingsPageProviderRepository.getClass();
        return (SettingsPageProvider)
                ((LinkedHashMap) settingsPageProviderRepository.pageProviderMap).get(sppName);
    }
}
