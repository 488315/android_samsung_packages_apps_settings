package com.att.iqi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public interface IServiceStateChangeCallback extends IInterface {
    public static final String DESCRIPTOR = "com.att.iqi.IServiceStateChangeCallback";

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class Stub extends Binder implements IServiceStateChangeCallback {
        static final int TRANSACTION_onServiceChange = 1;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public static class Proxy implements IServiceStateChangeCallback {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IServiceStateChangeCallback.DESCRIPTOR;
            }

            public void onServiceChange(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IServiceStateChangeCallback.DESCRIPTOR);
                    obtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IServiceStateChangeCallback.DESCRIPTOR);
        }

        public static IServiceStateChangeCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface =
                    iBinder.queryLocalInterface(IServiceStateChangeCallback.DESCRIPTOR);
            return (queryLocalInterface == null
                            || !(queryLocalInterface instanceof IServiceStateChangeCallback))
                    ? new Proxy(iBinder)
                    : (IServiceStateChangeCallback) queryLocalInterface;
        }

        public abstract /* synthetic */ void onServiceChange(boolean z) throws RemoteException;

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2)
                throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IServiceStateChangeCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IServiceStateChangeCallback.DESCRIPTOR);
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            onServiceChange(parcel.readInt() != 0);
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class Default implements IServiceStateChangeCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        public void onServiceChange(boolean z) throws RemoteException {}
    }
}
