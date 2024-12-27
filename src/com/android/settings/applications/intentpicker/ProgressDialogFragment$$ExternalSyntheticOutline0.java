package com.android.settings.applications.intentpicker;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.ViewModelProviderImpl;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract /* synthetic */ class ProgressDialogFragment$$ExternalSyntheticOutline0 {
    public static ViewModelProviderImpl m(
            CreationExtras creationExtras,
            String str,
            ViewModelStore viewModelStore,
            ViewModelProvider.Factory factory,
            CreationExtras creationExtras2) {
        Intrinsics.checkNotNullParameter(creationExtras, str);
        return new ViewModelProviderImpl(viewModelStore, factory, creationExtras2);
    }
}
