package com.google.common.util.concurrent;

import java.util.concurrent.Executor;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
final class DirectExecutor implements Executor {
    public static final /* synthetic */ DirectExecutor[] $VALUES;
    public static final DirectExecutor INSTANCE;

    static {
        DirectExecutor directExecutor = new DirectExecutor("INSTANCE", 0);
        INSTANCE = directExecutor;
        $VALUES = new DirectExecutor[] {directExecutor};
    }

    public static DirectExecutor valueOf(String str) {
        return (DirectExecutor) Enum.valueOf(DirectExecutor.class, str);
    }

    public static DirectExecutor[] values() {
        return (DirectExecutor[]) $VALUES.clone();
    }

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        runnable.run();
    }

    @Override // java.lang.Enum
    public final String toString() {
        return "MoreExecutors.directExecutor()";
    }
}
