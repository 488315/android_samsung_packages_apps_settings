package com.android.settingslib.media;

import android.media.RouteListingPreference;

import java.util.Map;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class InfoMediaManager$Api34Impl$$ExternalSyntheticLambda0
        implements Consumer {
    public final /* synthetic */ Map f$0;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        RouteListingPreference.Item item = (RouteListingPreference.Item) obj;
        this.f$0.put(item.getRouteId(), item);
    }
}
