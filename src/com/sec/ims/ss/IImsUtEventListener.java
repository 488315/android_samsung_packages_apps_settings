package com.sec.ims.ss;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public interface IImsUtEventListener extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.ss.IImsUtEventListener";

    void onUtConfigurationCallBarringQueried(int i, Bundle[] bundleArr) throws RemoteException;

    void onUtConfigurationCallForwardQueried(int i, Bundle[] bundleArr) throws RemoteException;

    void onUtConfigurationCallWaitingQueried(int i, boolean z) throws RemoteException;

    void onUtConfigurationQueried(int i, Bundle bundle) throws RemoteException;

    void onUtConfigurationQueryFailed(int i, Bundle bundle) throws RemoteException;

    void onUtConfigurationUpdateFailed(int i, Bundle bundle) throws RemoteException;

    void onUtConfigurationUpdated(int i) throws RemoteException;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract static class Stub extends Binder implements IImsUtEventListener {
        static final int TRANSACTION_onUtConfigurationCallBarringQueried = 7;
        static final int TRANSACTION_onUtConfigurationCallForwardQueried = 6;
        static final int TRANSACTION_onUtConfigurationCallWaitingQueried = 5;
        static final int TRANSACTION_onUtConfigurationQueried = 3;
        static final int TRANSACTION_onUtConfigurationQueryFailed = 2;
        static final int TRANSACTION_onUtConfigurationUpdateFailed = 1;
        static final int TRANSACTION_onUtConfigurationUpdated = 4;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public static class Proxy implements IImsUtEventListener {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IImsUtEventListener.DESCRIPTOR;
            }

            @Override // com.sec.ims.ss.IImsUtEventListener
            public void onUtConfigurationCallBarringQueried(int i, Bundle[] bundleArr)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsUtEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(bundleArr, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ss.IImsUtEventListener
            public void onUtConfigurationCallForwardQueried(int i, Bundle[] bundleArr)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsUtEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(bundleArr, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ss.IImsUtEventListener
            public void onUtConfigurationCallWaitingQueried(int i, boolean z)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsUtEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ss.IImsUtEventListener
            public void onUtConfigurationQueried(int i, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsUtEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ss.IImsUtEventListener
            public void onUtConfigurationQueryFailed(int i, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsUtEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ss.IImsUtEventListener
            public void onUtConfigurationUpdateFailed(int i, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsUtEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.ss.IImsUtEventListener
            public void onUtConfigurationUpdated(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsUtEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IImsUtEventListener.DESCRIPTOR);
        }

        public static IImsUtEventListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface =
                    iBinder.queryLocalInterface(IImsUtEventListener.DESCRIPTOR);
            return (queryLocalInterface == null
                            || !(queryLocalInterface instanceof IImsUtEventListener))
                    ? new Proxy(iBinder)
                    : (IImsUtEventListener) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2)
                throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IImsUtEventListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IImsUtEventListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onUtConfigurationUpdateFailed(readInt, bundle);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onUtConfigurationQueryFailed(readInt2, bundle2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onUtConfigurationQueried(readInt3, bundle3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUtConfigurationUpdated(readInt4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onUtConfigurationCallWaitingQueried(readInt5, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt6 = parcel.readInt();
                    Bundle[] bundleArr = (Bundle[]) parcel.createTypedArray(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onUtConfigurationCallForwardQueried(readInt6, bundleArr);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt7 = parcel.readInt();
                    Bundle[] bundleArr2 = (Bundle[]) parcel.createTypedArray(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onUtConfigurationCallBarringQueried(readInt7, bundleArr2);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class Default implements IImsUtEventListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.ss.IImsUtEventListener
        public void onUtConfigurationUpdated(int i) throws RemoteException {}

        @Override // com.sec.ims.ss.IImsUtEventListener
        public void onUtConfigurationCallBarringQueried(int i, Bundle[] bundleArr)
                throws RemoteException {}

        @Override // com.sec.ims.ss.IImsUtEventListener
        public void onUtConfigurationCallForwardQueried(int i, Bundle[] bundleArr)
                throws RemoteException {}

        @Override // com.sec.ims.ss.IImsUtEventListener
        public void onUtConfigurationCallWaitingQueried(int i, boolean z) throws RemoteException {}

        @Override // com.sec.ims.ss.IImsUtEventListener
        public void onUtConfigurationQueried(int i, Bundle bundle) throws RemoteException {}

        @Override // com.sec.ims.ss.IImsUtEventListener
        public void onUtConfigurationQueryFailed(int i, Bundle bundle) throws RemoteException {}

        @Override // com.sec.ims.ss.IImsUtEventListener
        public void onUtConfigurationUpdateFailed(int i, Bundle bundle) throws RemoteException {}
    }
}
