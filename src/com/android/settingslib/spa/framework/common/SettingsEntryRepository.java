package com.android.settingslib.spa.framework.common;

import android.util.Log;

import androidx.compose.foundation.text.CoreTextFieldKt$CoreTextField$semanticsModifier$1$1$3$$ExternalSyntheticOutline0;

import kotlin.jvm.internal.Intrinsics;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SettingsEntryRepository {
    public final Map entryMap;
    public final Map pageWithEntryMap;

    public SettingsEntryRepository(SettingsPageProviderRepository sppRepository) {
        Intrinsics.checkNotNullParameter(sppRepository, "sppRepository");
        Log.d("EntryRepository", "Initialize");
        this.entryMap = new LinkedHashMap();
        this.pageWithEntryMap = new LinkedHashMap();
        SettingsPage createSettingsPage =
                SettingsPageProviderKt.createSettingsPage(NullPageProvider.INSTANCE, null);
        LinkedList linkedList = new LinkedList();
        for (SettingsPage settingsPage : sppRepository.rootPages) {
            String label = "ROOT_" + settingsPage.displayName;
            Intrinsics.checkNotNullParameter(label, "label");
            SettingsEntryBuilder settingsEntryBuilder =
                    new SettingsEntryBuilder("ROOT", settingsPage);
            SettingsEntryBuilder.setLink$default(settingsEntryBuilder, null, settingsPage, 1);
            settingsEntryBuilder.label = label;
            SettingsEntryBuilder.setLink$default(settingsEntryBuilder, createSettingsPage, null, 2);
            SettingsEntry build = settingsEntryBuilder.build();
            Map map = this.entryMap;
            String str = build.id;
            if (!map.containsKey(str)) {
                linkedList.push(build);
                this.entryMap.put(str, build);
            }
        }
        while ((!linkedList.isEmpty()) && this.entryMap.size() < 5000) {
            SettingsEntry settingsEntry = (SettingsEntry) linkedList.pop();
            SettingsPage settingsPage2 = settingsEntry.toPage;
            if (settingsPage2 != null) {
                Map map2 = this.pageWithEntryMap;
                String str2 = settingsPage2.id;
                if (!map2.containsKey(str2)) {
                    String name = settingsPage2.sppName;
                    Intrinsics.checkNotNullParameter(name, "name");
                    SettingsPageProvider settingsPageProvider =
                            (SettingsPageProvider)
                                    ((LinkedHashMap) sppRepository.pageProviderMap).get(name);
                    if (settingsPageProvider != null) {
                        List<SettingsEntry> buildEntry =
                                settingsPageProvider.buildEntry(settingsPage2.arguments);
                        this.pageWithEntryMap.put(
                                str2,
                                new SettingsPageWithEntry(
                                        settingsPage2, buildEntry, settingsEntry));
                        for (SettingsEntry settingsEntry2 : buildEntry) {
                            if (!this.entryMap.containsKey(settingsEntry2.id)) {
                                linkedList.push(settingsEntry2);
                                this.entryMap.put(settingsEntry2.id, settingsEntry2);
                            }
                        }
                    }
                }
            }
        }
        Log.d(
                "EntryRepository",
                CoreTextFieldKt$CoreTextField$semanticsModifier$1$1$3$$ExternalSyntheticOutline0.m(
                        "Initialize Completed: ",
                        " entries in ",
                        this.entryMap.size(),
                        this.pageWithEntryMap.size(),
                        " pages"));
    }
}
