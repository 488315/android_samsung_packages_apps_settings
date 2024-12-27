package com.android.settingslib.core.instrumentation;

import android.content.Context;
import android.util.Pair;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public interface LogWriter {
    void action(int i, int i2, int i3, int i4, String str);

    void action(int i, String str);

    void action(int i, Pair... pairArr);

    void action(Context context, int i, int i2);

    void action(Context context, int i, boolean z);

    void changed(int i, int i2, String str);

    void clicked(int i, String str);

    void hidden(int i, int i2);

    void visible(int i, int i2, int i3);
}
