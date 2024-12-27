package com.samsung.android.settings.analyzestorage.presenter.utils;

import android.os.Looper;

import androidx.lifecycle.MutableLiveData;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class ExtensionFunctionsKt {
    public static final void updateValue(MutableLiveData mutableLiveData, Object obj) {
        Intrinsics.checkNotNullParameter(mutableLiveData, "<this>");
        if (Intrinsics.areEqual(Looper.myLooper(), Looper.getMainLooper())) {
            mutableLiveData.setValue(obj);
        } else {
            mutableLiveData.postValue(obj);
        }
    }
}
