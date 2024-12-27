package com.google.android.gms.safetynet;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.safetynet.zzad;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zzo extends com.google.android.gms.internal.safetynet.zzd {
    public final /* synthetic */ TaskCompletionSource zza;

    public zzo(TaskCompletionSource taskCompletionSource) {
        this.zza = taskCompletionSource;
    }

    @Override // com.google.android.gms.internal.safetynet.zzd,
              // com.google.android.gms.internal.safetynet.zzg
    public final void zzb(Status status, boolean z) {
        zzad zzadVar = new zzad();
        zzadVar.zza = status;
        zzadVar.zzb = z;
        SafetyNetApi$VerifyAppsUserResponse safetyNetApi$VerifyAppsUserResponse =
                new SafetyNetApi$VerifyAppsUserResponse();
        safetyNetApi$VerifyAppsUserResponse.zza = zzadVar;
        TaskCompletionSource taskCompletionSource = this.zza;
        if (status.zzc <= 0) {
            taskCompletionSource.setResult(safetyNetApi$VerifyAppsUserResponse);
        } else {
            taskCompletionSource.setException(new ApiException(status));
        }
    }
}
