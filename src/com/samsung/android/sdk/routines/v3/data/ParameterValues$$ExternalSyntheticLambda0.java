package com.samsung.android.sdk.routines.v3.data;

import java.util.Map;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class ParameterValues$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ Map f$0;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        Map.Entry entry = (Map.Entry) obj;
        this.f$0.put(
                (String) entry.getKey(), ((ParameterValues.ParameterValue) entry.getValue()).b());
    }
}
