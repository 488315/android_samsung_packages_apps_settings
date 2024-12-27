package com.samsung.android.settings.analyzestorage.presenter.controllers;

import androidx.lifecycle.ViewModel;

import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.domain.usecase.userinteraction.UserInteractionDialog$Callback;

import kotlin.jvm.internal.Intrinsics;

import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class DialogViewModel extends ViewModel {
    public final Map dialogDataMap = new LinkedHashMap();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DialogViewData {
        public final UserInteractionDialog$Callback dialogCallback;

        public DialogViewData(UserInteractionDialog$Callback userInteractionDialog$Callback) {
            this.dialogCallback = userInteractionDialog$Callback;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof DialogViewData)
                    && Intrinsics.areEqual(
                            this.dialogCallback, ((DialogViewData) obj).dialogCallback);
        }

        public final int hashCode() {
            UserInteractionDialog$Callback userInteractionDialog$Callback = this.dialogCallback;
            if (userInteractionDialog$Callback == null) {
                return 0;
            }
            return userInteractionDialog$Callback.hashCode();
        }

        public final String toString() {
            return "DialogViewData(dialogCallback=" + this.dialogCallback + ")";
        }
    }

    @Override // androidx.lifecycle.ViewModel
    public final void onCleared() {
        Log.v("DialogViewModel", "onCleared, size : " + this.dialogDataMap.size());
        ((LinkedHashMap) this.dialogDataMap).clear();
    }
}
