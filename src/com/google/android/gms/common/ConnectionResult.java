package com.google.android.gms.common;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.samsung.android.knox.foresight.KnoxForesight;

import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ConnectionResult extends AbstractSafeParcelable {
    public final int zza;
    public final int zzb;
    public final PendingIntent zzc;
    public final String zzd;
    public static final ConnectionResult RESULT_SUCCESS = new ConnectionResult(0);
    public static final Parcelable.Creator<ConnectionResult> CREATOR = new zzb();

    public ConnectionResult(int i, int i2, PendingIntent pendingIntent, String str) {
        this.zza = i;
        this.zzb = i2;
        this.zzc = pendingIntent;
        this.zzd = str;
    }

    public static String zza(int i) {
        if (i == 99) {
            return "UNFINISHED";
        }
        if (i == 1500) {
            return "DRIVE_EXTERNAL_STORAGE_REQUIRED";
        }
        switch (i) {
            case -1:
                return "UNKNOWN";
            case 0:
                return KnoxForesight.SUCCESS;
            case 1:
                return "SERVICE_MISSING";
            case 2:
                return "SERVICE_VERSION_UPDATE_REQUIRED";
            case 3:
                return "SERVICE_DISABLED";
            case 4:
                return "SIGN_IN_REQUIRED";
            case 5:
                return "INVALID_ACCOUNT";
            case 6:
                return "RESOLUTION_REQUIRED";
            case 7:
                return "NETWORK_ERROR";
            case 8:
                return "INTERNAL_ERROR";
            case 9:
                return "SERVICE_INVALID";
            case 10:
                return "DEVELOPER_ERROR";
            case 11:
                return "LICENSE_CHECK_FAILED";
            default:
                switch (i) {
                    case 13:
                        return "CANCELED";
                    case 14:
                        return "TIMEOUT";
                    case 15:
                        return "INTERRUPTED";
                    case 16:
                        return "API_UNAVAILABLE";
                    case 17:
                        return "SIGN_IN_FAILED";
                    case 18:
                        return "SERVICE_UPDATING";
                    case 19:
                        return "SERVICE_MISSING_PERMISSION";
                    case 20:
                        return "RESTRICTED_PROFILE";
                    case 21:
                        return "API_VERSION_UPDATE_REQUIRED";
                    case 22:
                        return "RESOLUTION_ACTIVITY_NOT_FOUND";
                    case 23:
                        return "API_DISABLED";
                    case 24:
                        return "API_DISABLED_FOR_CONNECTION";
                    default:
                        StringBuilder sb = new StringBuilder(31);
                        sb.append("UNKNOWN_ERROR_CODE(");
                        sb.append(i);
                        sb.append(")");
                        return sb.toString();
                }
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ConnectionResult)) {
            return false;
        }
        ConnectionResult connectionResult = (ConnectionResult) obj;
        return this.zzb == connectionResult.zzb
                && Objects.equal(this.zzc, connectionResult.zzc)
                && Objects.equal(this.zzd, connectionResult.zzd);
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[] {Integer.valueOf(this.zzb), this.zzc, this.zzd});
    }

    public final String toString() {
        Objects.ToStringHelper toStringHelper = new Objects.ToStringHelper(this);
        toStringHelper.add(zza(this.zzb), "statusCode");
        toStringHelper.add(this.zzc, "resolution");
        toStringHelper.add(this.zzd, "message");
        return toStringHelper.toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int zza = SafeParcelWriter.zza(20293, parcel);
        int i2 = this.zza;
        SafeParcelWriter.zzc(parcel, 1, 4);
        parcel.writeInt(i2);
        int i3 = this.zzb;
        SafeParcelWriter.zzc(parcel, 2, 4);
        parcel.writeInt(i3);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzc, i);
        SafeParcelWriter.writeString(parcel, 4, this.zzd);
        SafeParcelWriter.zzb(zza, parcel);
    }

    public ConnectionResult(int i) {
        this(1, i, null, null);
    }

    public ConnectionResult(int i, PendingIntent pendingIntent) {
        this(1, i, pendingIntent, null);
    }
}
