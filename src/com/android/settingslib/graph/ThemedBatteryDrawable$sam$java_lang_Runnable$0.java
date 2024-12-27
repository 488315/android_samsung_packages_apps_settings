package com.android.settingslib.graph;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class ThemedBatteryDrawable$sam$java_lang_Runnable$0
        implements Runnable {
    public final /* synthetic */ Function0 function;

    public ThemedBatteryDrawable$sam$java_lang_Runnable$0(Function0 function) {
        Intrinsics.checkNotNullParameter(function, "function");
        this.function = function;
    }

    @Override // java.lang.Runnable
    public final /* synthetic */ void run() {
        this.function.mo1068invoke();
    }
}
