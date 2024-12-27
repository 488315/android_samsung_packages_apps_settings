package com.android.settingslib.utils;

import androidx.loader.content.AsyncTaskLoader;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AsyncLoaderCompat extends AsyncTaskLoader {
    public Object mResult;

    @Override // androidx.loader.content.Loader
    public final void deliverResult(Object obj) {
        if (this.mReset) {
            if (obj != null) {
                onDiscardResult(obj);
                return;
            }
            return;
        }
        Object obj2 = this.mResult;
        this.mResult = obj;
        if (this.mStarted) {
            super.deliverResult(obj);
        }
        if (obj2 == null || obj2 == this.mResult) {
            return;
        }
        onDiscardResult(obj2);
    }

    @Override // androidx.loader.content.AsyncTaskLoader
    public final void onCanceled(Object obj) {
        if (obj != null) {
            onDiscardResult(obj);
        }
    }

    public abstract void onDiscardResult(Object obj);

    @Override // androidx.loader.content.Loader
    public final void onReset() {
        onStopLoading();
        Object obj = this.mResult;
        if (obj != null) {
            onDiscardResult(obj);
        }
        this.mResult = null;
    }

    @Override // androidx.loader.content.Loader
    public void onStartLoading() {
        Object obj = this.mResult;
        if (obj != null) {
            deliverResult(obj);
        }
        boolean z = this.mContentChanged;
        this.mContentChanged = false;
        this.mProcessingChange |= z;
        if (z || this.mResult == null) {
            onForceLoad();
        }
    }

    @Override // androidx.loader.content.Loader
    public void onStopLoading() {
        onCancelLoad();
    }
}
