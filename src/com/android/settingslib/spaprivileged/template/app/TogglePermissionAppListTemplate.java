package com.android.settingslib.spaprivileged.template.app;

import android.content.Context;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;

import com.android.settingslib.spa.framework.compose.RuntimeUtilsKt;

import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class TogglePermissionAppListTemplate {
    public final Map listModelProviderMap;

    public TogglePermissionAppListTemplate(List allProviders) {
        Intrinsics.checkNotNullParameter(allProviders, "allProviders");
        List list = allProviders;
        int mapCapacity =
                MapsKt__MapsKt.mapCapacity(
                        CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        LinkedHashMap linkedHashMap = new LinkedHashMap(mapCapacity < 16 ? 16 : mapCapacity);
        for (Object obj : list) {
            linkedHashMap.put(((TogglePermissionAppListProvider) obj).getPermissionType(), obj);
        }
        this.listModelProviderMap = linkedHashMap;
    }

    public final TogglePermissionAppListModel
            rememberModel$frameworks__base__packages__SettingsLib__SpaPrivileged__android_common__SeslSpaPrivilegedLib(
                    Composer composer, final String str) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-1589681780);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        TogglePermissionAppListModel togglePermissionAppListModel =
                (TogglePermissionAppListModel)
                        RuntimeUtilsKt.rememberContext(
                                new Function1() { // from class:
                                                  // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListTemplate$rememberModel$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(1);
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj) {
                                        Context context = (Context) obj;
                                        Intrinsics.checkNotNullParameter(context, "context");
                                        return ((TogglePermissionAppListProvider)
                                                        MapsKt__MapsKt.getValue(
                                                                str,
                                                                TogglePermissionAppListTemplate.this
                                                                        .listModelProviderMap))
                                                .createModel(context);
                                    }
                                },
                                composerImpl);
        composerImpl.end(false);
        return togglePermissionAppListModel;
    }
}
