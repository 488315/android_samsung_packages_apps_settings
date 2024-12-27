package com.android.settings.dream;

import com.android.settings.search.BaseSearchIndexProvider;

import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class DreamPickerController$DreamItem$$ExternalSyntheticLambda0
        implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        DreamSettings$$ExternalSyntheticLambda3 dreamSettings$$ExternalSyntheticLambda3 =
                (DreamSettings$$ExternalSyntheticLambda3) ((DreamPickerController.Callback) obj);
        dreamSettings$$ExternalSyntheticLambda3.getClass();
        BaseSearchIndexProvider baseSearchIndexProvider = DreamSettings.SEARCH_INDEX_DATA_PROVIDER;
        dreamSettings$$ExternalSyntheticLambda3.f$0.updateComplicationsToggleVisibility();
    }
}
