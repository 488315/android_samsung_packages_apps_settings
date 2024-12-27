package com.sec.ims.ft;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public interface IImsOngoingFtEventListener extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.ft.IImsOngoingFtEventListener";

    void onFtStateChanged(boolean z) throws RemoteException;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract static class Stub extends Binder implements IImsOngoingFtEventListener {
        static final int TRANSACTION_onFtStateChanged = 1;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public static class Proxy implements IImsOngoingFtEventListener {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IImsOngoingFtEventListener.DESCRIPTOR;
            }

            @Override // com.sec.ims.ft.IImsOngoingFtEventListener
            public void onFtStateChanged(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsOngoingFtEventListener.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IImsOngoingFtEventListener.DESCRIPTOR);
        }

        public static IImsOngoingFtEventListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface =
                    iBinder.queryLocalInterface(IImsOngoingFtEventListener.DESCRIPTOR);
            return (queryLocalInterface == null
                            || !(queryLocalInterface instanceof IImsOngoingFtEventListener))
                    ? new Proxy(iBinder)
                    : (IImsOngoingFtEventListener) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2)
                throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IImsOngoingFtEventListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IImsOngoingFtEventListener.DESCRIPTOR);
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            boolean readBoolean = parcel.readBoolean();
            parcel.enforceNoDataAvail();
            onFtStateChanged(readBoolean);
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class Default implements IImsOngoingFtEventListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.ft.IImsOngoingFtEventListener
        public void onFtStateChanged(boolean z) throws RemoteException {}
    }
}
