package com.android.settings.homepage;

import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.settings.homepage.TopLevelMDESuggestionsPreferenceController;

import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class TopLevelSettings$$ExternalSyntheticLambda1 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        BaseSearchIndexProvider baseSearchIndexProvider =
                TopLevelSettings.SEARCH_INDEX_DATA_PROVIDER;
        ((TopLevelMDESuggestionsPreferenceController) ((AbstractPreferenceController) obj))
                .updateFromGoodSettings();
    }
}