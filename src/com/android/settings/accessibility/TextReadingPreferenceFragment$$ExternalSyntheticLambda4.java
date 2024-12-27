package com.android.settings.accessibility;

import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.core.AbstractPreferenceController;

import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class TextReadingPreferenceFragment$$ExternalSyntheticLambda4
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        BaseSearchIndexProvider baseSearchIndexProvider =
                TextReadingPreferenceFragment.SEARCH_INDEX_DATA_PROVIDER;
        return (TextReadingResetController.ResetStateListener) ((AbstractPreferenceController) obj);
    }
}
