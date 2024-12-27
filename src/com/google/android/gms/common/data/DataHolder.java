package com.google.android.gms.common.data;

import android.database.CursorWindow;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@KeepName
/* loaded from: classes3.dex */
public final class DataHolder extends AbstractSafeParcelable implements Closeable {
    public static final Parcelable.Creator<DataHolder> CREATOR = new zaf();
    public final int zaa;
    public Bundle zab;
    public int[] zac;
    public final String[] zag;
    public final CursorWindow[] zah;
    public final int zai;
    public final Bundle zaj;
    public boolean zae = false;
    public final boolean zak = true;

    static {
        new ArrayList();
        new HashMap();
    }

    public DataHolder(
            int i, String[] strArr, CursorWindow[] cursorWindowArr, int i2, Bundle bundle) {
        this.zaa = i;
        this.zag = strArr;
        this.zah = cursorWindowArr;
        this.zai = i2;
        this.zaj = bundle;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        synchronized (this) {
            try {
                if (!this.zae) {
                    this.zae = true;
                    int i = 0;
                    while (true) {
                        CursorWindow[] cursorWindowArr = this.zah;
                        if (i >= cursorWindowArr.length) {
                            break;
                        }
                        cursorWindowArr[i].close();
                        i++;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void finalize() {
        boolean z;
        try {
            if (this.zak && this.zah.length > 0) {
                synchronized (this) {
                    z = this.zae;
                }
                if (!z) {
                    close();
                    String obj = toString();
                    StringBuilder sb = new StringBuilder(String.valueOf(obj).length() + 178);
                    sb.append(
                            "Internal data leak within a DataBuffer object detected!  Be sure to"
                                + " explicitly call release() on all DataBuffer extending objects"
                                + " when you are done with them. (internal object: ");
                    sb.append(obj);
                    sb.append(")");
                    Log.e("DataBuffer", sb.toString());
                }
            }
        } finally {
            super.finalize();
        }
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int zza = SafeParcelWriter.zza(20293, parcel);
        String[] strArr = this.zag;
        if (strArr != null) {
            int zza2 = SafeParcelWriter.zza(1, parcel);
            parcel.writeStringArray(strArr);
            SafeParcelWriter.zzb(zza2, parcel);
        }
        SafeParcelWriter.writeTypedArray(parcel, 2, this.zah, i);
        int i2 = this.zai;
        SafeParcelWriter.zzc(parcel, 3, 4);
        parcel.writeInt(i2);
        SafeParcelWriter.writeBundle(parcel, 4, this.zaj);
        int i3 = this.zaa;
        SafeParcelWriter.zzc(parcel, 1000, 4);
        parcel.writeInt(i3);
        SafeParcelWriter.zzb(zza, parcel);
        if ((i & 1) != 0) {
            close();
        }
    }
}
