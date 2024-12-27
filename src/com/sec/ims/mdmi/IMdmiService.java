package com.sec.ims.mdmi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public interface IMdmiService extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.mdmi.IMdmiService";

    void registerMdmiEventListener(IMdmiEventListener iMdmiEventListener) throws RemoteException;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract static class Stub extends Binder implements IMdmiService {
        static final int TRANSACTION_registerMdmiEventListener = 1;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public static class Proxy implements IMdmiService {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMdmiService.DESCRIPTOR;
            }

            @Override // com.sec.ims.mdmi.IMdmiService
            public void registerMdmiEventListener(IMdmiEventListener iMdmiEventListener)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMdmiService.DESCRIPTOR);
                    obtain.writeStrongInterface(iMdmiEventListener);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IMdmiService.DESCRIPTOR);
        }

        public static IMdmiService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IMdmiService.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IMdmiService))
                    ? new Proxy(iBinder)
                    : (IMdmiService) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2)
                throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IMdmiService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMdmiService.DESCRIPTOR);
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            IMdmiEventListener asInterface =
                    IMdmiEventListener.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            registerMdmiEventListener(asInterface);
            parcel2.writeNoException();
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class Default implements IMdmiService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.mdmi.IMdmiService
        public void registerMdmiEventListener(IMdmiEventListener iMdmiEventListener)
                throws RemoteException {}
    }
}
