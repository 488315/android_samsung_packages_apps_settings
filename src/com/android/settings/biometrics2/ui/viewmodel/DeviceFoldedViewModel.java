package com.android.settings.biometrics2.ui.viewmodel;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.android.systemui.unfold.compat.ScreenSizeFoldProvider;
import com.android.systemui.unfold.updates.FoldProvider$FoldCallback;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DeviceFoldedViewModel extends ViewModel {
    public final DeviceFoldedViewModel$$ExternalSyntheticLambda0 mIsFoldedCallback;
    public final MutableLiveData mLiveData = new MutableLiveData(null);
    public final ScreenSizeFoldProvider mScreenSizeFoldProvider;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settings.biometrics2.ui.viewmodel.DeviceFoldedViewModel$$ExternalSyntheticLambda0, com.android.systemui.unfold.updates.FoldProvider$FoldCallback] */
    public DeviceFoldedViewModel(ScreenSizeFoldProvider screenSizeFoldProvider, Executor executor) {
        ?? r0 = new FoldProvider$FoldCallback() { // from class: com.android.settings.biometrics2.ui.viewmodel.DeviceFoldedViewModel$$ExternalSyntheticLambda0
            @Override // com.android.systemui.unfold.updates.FoldProvider$FoldCallback
            public final void onFoldUpdated(boolean z) {
                DeviceFoldedViewModel deviceFoldedViewModel = DeviceFoldedViewModel.this;
                deviceFoldedViewModel.getClass();
                Log.d("DeviceFoldedViewModel", "onFoldUpdated= " + z);
                deviceFoldedViewModel.mLiveData.postValue(Boolean.valueOf(z));
            }
        };
        this.mIsFoldedCallback = r0;
        this.mScreenSizeFoldProvider = screenSizeFoldProvider;
        screenSizeFoldProvider.registerCallback(r0, executor);
    }

    @Override // androidx.lifecycle.ViewModel
    public final void onCleared() {
        ScreenSizeFoldProvider screenSizeFoldProvider = this.mScreenSizeFoldProvider;
        screenSizeFoldProvider.getClass();
        DeviceFoldedViewModel$$ExternalSyntheticLambda0 callback = this.mIsFoldedCallback;
        Intrinsics.checkNotNullParameter(callback, "callback");
        ((ArrayList) screenSizeFoldProvider.callbacks).remove(callback);
    }
}
