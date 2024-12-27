package com.google.android.gms.common.api;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.samsung.android.knox.foresight.KnoxForesight;

import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class Status extends AbstractSafeParcelable implements Result, ReflectedParcelable {
    public final int zzb;
    public final int zzc;
    public final String zzd;
    public final PendingIntent zze;
    public final ConnectionResult zzf;
    public static final Status RESULT_SUCCESS = new Status(0, null);
    public static final Status RESULT_INTERRUPTED = new Status(14, null);
    public static final Status RESULT_TIMEOUT = new Status(15, null);
    public static final Status RESULT_CANCELED = new Status(16, null);
    public static final Parcelable.Creator<Status> CREATOR = new zzb();

    public Status(
            int i,
            int i2,
            String str,
            PendingIntent pendingIntent,
            ConnectionResult connectionResult) {
        this.zzb = i;
        this.zzc = i2;
        this.zzd = str;
        this.zze = pendingIntent;
        this.zzf = connectionResult;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof Status)) {
            return false;
        }
        Status status = (Status) obj;
        return this.zzb == status.zzb
                && this.zzc == status.zzc
                && Objects.equal(this.zzd, status.zzd)
                && Objects.equal(this.zze, status.zze)
                && Objects.equal(this.zzf, status.zzf);
    }

    public boolean hasResolution() {
        return this.zze != null;
    }

    public final int hashCode() {
        return Arrays.hashCode(
                new Object[] {
                    Integer.valueOf(this.zzb),
                    Integer.valueOf(this.zzc),
                    this.zzd,
                    this.zze,
                    this.zzf
                });
    }

    public final String toString() {
        Objects.ToStringHelper toStringHelper = new Objects.ToStringHelper(this);
        String str = this.zzd;
        if (str == null) {
            int i = this.zzc;
            switch (i) {
                case -1:
                    str = "SUCCESS_CACHE";
                    break;
                case 0:
                    str = KnoxForesight.SUCCESS;
                    break;
                case 1:
                case 9:
                case 11:
                case 12:
                default:
                    StringBuilder sb = new StringBuilder(32);
                    sb.append("unknown status code: ");
                    sb.append(i);
                    str = sb.toString();
                    break;
                case 2:
                    str = "SERVICE_VERSION_UPDATE_REQUIRED";
                    break;
                case 3:
                    str = "SERVICE_DISABLED";
                    break;
                case 4:
                    str = "SIGN_IN_REQUIRED";
                    break;
                case 5:
                    str = "INVALID_ACCOUNT";
                    break;
                case 6:
                    str = "RESOLUTION_REQUIRED";
                    break;
                case 7:
                    str = "NETWORK_ERROR";
                    break;
                case 8:
                    str = "INTERNAL_ERROR";
                    break;
                case 10:
                    str = "DEVELOPER_ERROR";
                    break;
                case 13:
                    str = "ERROR";
                    break;
                case 14:
                    str = "INTERRUPTED";
                    break;
                case 15:
                    str = "TIMEOUT";
                    break;
                case 16:
                    str = "CANCELED";
                    break;
                case 17:
                    str = "API_NOT_CONNECTED";
                    break;
                case 18:
                    str = "DEAD_CLIENT";
                    break;
                case 19:
                    str = "REMOTE_EXCEPTION";
                    break;
                case 20:
                    str = "CONNECTION_SUSPENDED_DURING_CALL";
                    break;
                case 21:
                    str = "RECONNECTION_TIMED_OUT_DURING_UPDATE";
                    break;
                case 22:
                    str = "RECONNECTION_TIMED_OUT";
                    break;
            }
        }
        toStringHelper.add(str, "statusCode");
        toStringHelper.add(this.zze, "resolution");
        return toStringHelper.toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int zza = SafeParcelWriter.zza(20293, parcel);
        int i2 = this.zzc;
        SafeParcelWriter.zzc(parcel, 1, 4);
        parcel.writeInt(i2);
        SafeParcelWriter.writeString(parcel, 2, this.zzd);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zze, i);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzf, i);
        int i3 = this.zzb;
        SafeParcelWriter.zzc(parcel, 1000, 4);
        parcel.writeInt(i3);
        SafeParcelWriter.zzb(zza, parcel);
    }

    public Status(int i, String str) {
        this(1, i, str, null, null);
    }

    @Override // com.google.android.gms.common.api.Result
    public final Status getStatus() {
        return this;
    }
}
