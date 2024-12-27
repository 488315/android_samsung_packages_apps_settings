package com.android.settingslib.spa.framework.common;

import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;

import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.MapsKt__MapsKt;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SettingsPageProviderRepository {
    public final Map pageProviderMap;
    public final List rootPages;

    public SettingsPageProviderRepository(List list, List list2) {
        this.rootPages = list2;
        int mapCapacity =
                MapsKt__MapsKt.mapCapacity(
                        CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        LinkedHashMap linkedHashMap = new LinkedHashMap(mapCapacity < 16 ? 16 : mapCapacity);
        for (Object obj : list) {
            linkedHashMap.put(((SettingsPageProvider) obj).getName(), obj);
        }
        this.pageProviderMap = linkedHashMap;
        MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(
                linkedHashMap.size(), "Initialize Completed: ", " spp", "SppRepository");
    }
}
