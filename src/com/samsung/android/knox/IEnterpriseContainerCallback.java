package com.samsung.android.knox;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public interface IEnterpriseContainerCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.IEnterpriseContainerCallback";

    void updateStatus(int i, Bundle bundle) throws RemoteException;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract static class Stub extends Binder implements IEnterpriseContainerCallback {
        public static final int TRANSACTION_updateStatus = 1;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public static class Proxy implements IEnterpriseContainerCallback {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IEnterpriseContainerCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.IEnterpriseContainerCallback
            public void updateStatus(int i, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IEnterpriseContainerCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IEnterpriseContainerCallback.DESCRIPTOR);
        }

        public static IEnterpriseContainerCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface =
                    iBinder.queryLocalInterface(IEnterpriseContainerCallback.DESCRIPTOR);
            return (queryLocalInterface == null
                            || !(queryLocalInterface instanceof IEnterpriseContainerCallback))
                    ? new Proxy(iBinder)
                    : (IEnterpriseContainerCallback) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2)
                throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IEnterpriseContainerCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IEnterpriseContainerCallback.DESCRIPTOR);
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            int readInt = parcel.readInt();
            Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
            parcel.enforceNoDataAvail();
            updateStatus(readInt, bundle);
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class Default implements IEnterpriseContainerCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.IEnterpriseContainerCallback
        public void updateStatus(int i, Bundle bundle) throws RemoteException {}
    }
}
