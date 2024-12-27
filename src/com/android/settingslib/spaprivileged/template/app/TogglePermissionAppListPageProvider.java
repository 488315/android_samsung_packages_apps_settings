package com.android.settingslib.spaprivileged.template.app;

import android.os.Bundle;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.core.os.BundleKt;
import androidx.navigation.NamedNavArgument;
import androidx.navigation.NavType;

import com.android.settingslib.spa.framework.common.SettingsEntryBuilder;
import com.android.settingslib.spa.framework.common.SettingsPage;
import com.android.settingslib.spa.framework.common.SettingsPageProvider;

import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class TogglePermissionAppListPageProvider implements SettingsPageProvider {
    public final TogglePermissionAppListTemplate appListTemplate;
    public final List parameter = TogglePermissionAppListPageKt.PAGE_PARAMETER;

    public TogglePermissionAppListPageProvider(
            TogglePermissionAppListTemplate togglePermissionAppListTemplate) {
        this.appListTemplate = togglePermissionAppListTemplate;
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final void Page(final Bundle bundle, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1559288062);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        String string = bundle != null ? bundle.getString("permission") : null;
        Intrinsics.checkNotNull(string);
        TogglePermissionAppListPageKt.TogglePermissionAppList(
                this.appListTemplate
                        .rememberModel$frameworks__base__packages__SettingsLib__SpaPrivileged__android_common__SeslSpaPrivilegedLib(
                                composerImpl, string),
                string,
                null,
                null,
                composerImpl,
                0,
                6);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListPageProvider$Page$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            TogglePermissionAppListPageProvider.this.Page(
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
        String str;
        List list = this.parameter;
        Intrinsics.checkNotNullParameter(list, "<this>");
        Iterator it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            NamedNavArgument namedNavArgument = (NamedNavArgument) it.next();
            if (Intrinsics.areEqual(namedNavArgument.argument.type, NavType.StringType)
                    && Intrinsics.areEqual(namedNavArgument.name, "permission")) {
                if (bundle != null) {
                    str = bundle.getString("permission");
                }
            }
        }
        str = null;
        Intrinsics.checkNotNull(str);
        SettingsPage create$default =
                SettingsPage.Companion.create$default(
                        "TogglePermissionAppList", this.parameter, bundle);
        SettingsPage create$default2 =
                SettingsPage.Companion.create$default(
                        "TogglePermissionAppInfoPage",
                        TogglePermissionAppInfoPageProvider.PAGE_PARAMETER,
                        BundleKt.bundleOf(new Pair("permission", str)));
        List listOf = CollectionsKt__CollectionsJVMKt.listOf("personal");
        ArrayList arrayList =
                new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listOf, 10));
        Iterator it2 = listOf.iterator();
        while (it2.hasNext()) {
            String entryName = "AppList_" + ((String) it2.next());
            Intrinsics.checkNotNullParameter(entryName, "entryName");
            SettingsEntryBuilder settingsEntryBuilder =
                    new SettingsEntryBuilder(entryName, create$default);
            SettingsEntryBuilder.setLink$default(settingsEntryBuilder, create$default, null, 2);
            SettingsEntryBuilder.setLink$default(settingsEntryBuilder, null, create$default2, 1);
            arrayList.add(settingsEntryBuilder.build());
        }
        return arrayList;
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final String getName() {
        return "TogglePermissionAppList";
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final List getParameter() {
        return this.parameter;
    }
}
