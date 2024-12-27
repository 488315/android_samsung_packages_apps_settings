package com.msc.sa.aidl;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public interface ISAService extends IInterface {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class Stub extends Binder implements ISAService {
        public static final /* synthetic */ int $r8$clinit = 0;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class Proxy implements ISAService {
            public IBinder mRemote;

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            public final String registerCallback(
                    String str, String str2, String str3, ISACallback iSACallback) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.msc.sa.aidl.ISAService");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeStrongBinder(iSACallback != null ? iSACallback.asBinder() : null);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        int i = Stub.$r8$clinit;
                    }
                    obtain2.readException();
                    String readString = obtain2.readString();
                    obtain2.recycle();
                    obtain.recycle();
                    return readString;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                    throw th;
                }
            }

            public final boolean requestAccessToken(int i, String str, Bundle bundle) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.msc.sa.aidl.ISAService");
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(1);
                    bundle.writeToParcel(obtain, 0);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        int i2 = Stub.$r8$clinit;
                    }
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final boolean unregisterCallback(String str) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.msc.sa.aidl.ISAService");
                    obtain.writeString(str);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        int i = Stub.$r8$clinit;
                    }
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
