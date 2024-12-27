package com.android.settings.spa.app.appinfo;

import android.content.Context;

import androidx.compose.runtime.ParcelableSnapshotMutableState;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.StructuralEqualityPolicy;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppCreateButton {
    public final Context context;
    public final ParcelableSnapshotMutableState enabledState;

    public AppCreateButton(PackageInfoPresenter packageInfoPresenter) {
        Intrinsics.checkNotNullParameter(packageInfoPresenter, "packageInfoPresenter");
        this.context = packageInfoPresenter.context;
        this.enabledState =
                SnapshotStateKt.mutableStateOf(Boolean.TRUE, StructuralEqualityPolicy.INSTANCE);
    }
}
