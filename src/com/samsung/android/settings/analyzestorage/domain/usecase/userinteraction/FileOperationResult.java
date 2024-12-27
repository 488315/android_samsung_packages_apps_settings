package com.samsung.android.settings.analyzestorage.domain.usecase.userinteraction;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class FileOperationResult {
    public final Bundle mBundle = new Bundle();
    public boolean mIsSuccess;
    public boolean mNeedRefresh;

    public final String toString() {
        StringBuilder sb = new StringBuilder("(Success:");
        sb.append(this.mIsSuccess);
        sb.append(" | Canceled:false | NeedRefresh:");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(
                sb, this.mNeedRefresh, " | Exception:null)");
    }
}
