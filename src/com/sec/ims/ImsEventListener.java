package com.sec.ims;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public interface ImsEventListener extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.ImsEventListener";

    void onEvent(Bundle bundle) throws RemoteException;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract static class Stub extends Binder implements ImsEventListener {
        static final int TRANSACTION_onEvent = 1;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public static class Proxy implements ImsEventListener {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ImsEventListener.DESCRIPTOR;
            }

            @Override // com.sec.ims.ImsEventListener
            public void onEvent(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ImsEventListener.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, ImsEventListener.DESCRIPTOR);
        }

        public static ImsEventListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface =
                    iBinder.queryLocalInterface(ImsEventListener.DESCRIPTOR);
            return (queryLocalInterface == null
                            || !(queryLocalInterface instanceof ImsEventListener))
                    ? new Proxy(iBinder)
                    : (ImsEventListener) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2)
                throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ImsEventListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ImsEventListener.DESCRIPTOR);
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
            parcel.enforceNoDataAvail();
            onEvent(bundle);
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class Default implements ImsEventListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.ImsEventListener
        public void onEvent(Bundle bundle) throws RemoteException {}
    }
}
