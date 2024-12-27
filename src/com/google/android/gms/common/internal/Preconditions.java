package com.google.android.gms.common.internal;

import android.os.Handler;
import android.os.Looper;

import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class Preconditions {
    public static void checkHandlerThread(Handler handler) {
        Looper myLooper = Looper.myLooper();
        if (myLooper != handler.getLooper()) {
            String name = myLooper != null ? myLooper.getThread().getName() : "null current looper";
            String name2 = handler.getLooper().getThread().getName();
            StringBuilder sb =
                    new StringBuilder(
                            String.valueOf(name2).length() + 36 + String.valueOf(name).length());
            ConstraintWidget$$ExternalSyntheticOutline0.m(
                    sb, "Must be called on ", name2, " thread, but got ", name);
            sb.append(".");
            throw new IllegalStateException(sb.toString());
        }
    }

    public static void checkNotNull(Object obj) {
        if (obj == null) {
            throw new NullPointerException("null reference");
        }
    }

    public static void checkState(Object obj, boolean z) {
        if (!z) {
            throw new IllegalStateException(String.valueOf(obj));
        }
    }

    public static void checkNotNull(Object obj, Object obj2) {
        if (obj == null) {
            throw new NullPointerException(String.valueOf(obj2));
        }
    }
}
