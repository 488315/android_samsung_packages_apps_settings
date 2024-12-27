package com.android.settings.network;

import com.android.settings.datausage.CellDataPreference;

import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class ProxySubscriptionManager$$ExternalSyntheticLambda4
        implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        CellDataPreference cellDataPreference = CellDataPreference.this;
        cellDataPreference.setEnabled(
                cellDataPreference.getActiveSubscriptionInfo(cellDataPreference.mSubId) != null);
    }
}
