package com.samsung.android.settings.analyzestorage.presenter.managers.broadcast;

import android.content.IntentFilter;

import java.util.Objects;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class BroadcastReceiveCenter$$ExternalSyntheticLambda3
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return Objects.nonNull((IntentFilter) obj);
    }
}