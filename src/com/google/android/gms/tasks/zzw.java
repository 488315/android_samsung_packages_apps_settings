package com.google.android.gms.tasks;

import com.google.android.gms.common.internal.Preconditions;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zzw extends Task {
    public final Object zza = new Object();
    public final zzr zzb = new zzr();
    public boolean zzc;
    public Object zze;
    public Exception zzf;

    @Override // com.google.android.gms.tasks.Task
    public final Object getResult() {
        Object obj;
        synchronized (this.zza) {
            try {
                Preconditions.checkState("Task is not yet complete", this.zzc);
                Exception exc = this.zzf;
                if (exc != null) {
                    throw new RuntimeExecutionException(exc);
                }
                obj = this.zze;
            } catch (Throwable th) {
                throw th;
            }
        }
        return obj;
    }

    @Override // com.google.android.gms.tasks.Task
    public final boolean isSuccessful() {
        boolean z;
        synchronized (this.zza) {
            try {
                z = false;
                if (this.zzc && this.zzf == null) {
                    z = true;
                }
            } finally {
            }
        }
        return z;
    }

    public final void zzh() {
        boolean z;
        Exception exc;
        if (this.zzc) {
            int i = DuplicateTaskCompletionException.$r8$clinit;
            synchronized (this.zza) {
                z = this.zzc;
            }
            if (!z) {
                throw new IllegalStateException(
                        "DuplicateTaskCompletionException can only be created from completed"
                            + " Task.");
            }
            synchronized (this.zza) {
                exc = this.zzf;
            }
            String concat =
                    exc != null
                            ? "failure"
                            : isSuccessful()
                                    ? "result ".concat(String.valueOf(getResult()))
                                    : "unknown issue";
        }
    }
}
