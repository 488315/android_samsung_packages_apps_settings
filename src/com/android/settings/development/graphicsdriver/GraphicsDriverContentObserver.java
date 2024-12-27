package com.android.settings.development.graphicsdriver;

import android.database.ContentObserver;
import android.os.Handler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class GraphicsDriverContentObserver extends ContentObserver {
    OnGraphicsDriverContentChangedListener mListener;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnGraphicsDriverContentChangedListener {
        void onGraphicsDriverContentChanged();
    }

    public GraphicsDriverContentObserver(
            Handler handler,
            OnGraphicsDriverContentChangedListener onGraphicsDriverContentChangedListener) {
        super(handler);
        this.mListener = onGraphicsDriverContentChangedListener;
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z) {
        super.onChange(z);
        this.mListener.onGraphicsDriverContentChanged();
    }
}
