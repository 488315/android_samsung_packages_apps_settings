package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

import com.google.android.gms.internal.common.zzc;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zzv implements IAccountAccessor, IInterface {
    public final IBinder zza;

    public zzv(IBinder iBinder) {
        this.zza = iBinder;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.zza;
    }

    public final Account zzb() {
        Parcel obtain = Parcel.obtain();
        obtain.writeInterfaceToken("com.google.android.gms.common.internal.IAccountAccessor");
        obtain = Parcel.obtain();
        try {
            this.zza.transact(2, obtain, obtain, 0);
            obtain.readException();
            obtain.recycle();
            return (Account) zzc.zza(obtain, Account.CREATOR);
        } catch (RuntimeException e) {
            throw e;
        } finally {
            obtain.recycle();
        }
    }
}
