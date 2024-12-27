package com.samsung.android.knox.integrity;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public interface IEnhancedAttestationPolicyCallback extends IInterface {
    public static final String DESCRIPTOR =
            "com.samsung.android.knox.integrity.IEnhancedAttestationPolicyCallback";

    void onAttestationFinished(EnhancedAttestationResult enhancedAttestationResult)
            throws RemoteException;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract static class Stub extends Binder implements IEnhancedAttestationPolicyCallback {
        public static final int TRANSACTION_onAttestationFinished = 1;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public static class Proxy implements IEnhancedAttestationPolicyCallback {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IEnhancedAttestationPolicyCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.integrity.IEnhancedAttestationPolicyCallback
            public void onAttestationFinished(EnhancedAttestationResult enhancedAttestationResult)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnhancedAttestationPolicyCallback.DESCRIPTOR);
                    obtain.writeTypedObject(enhancedAttestationResult, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IEnhancedAttestationPolicyCallback.DESCRIPTOR);
        }

        public static IEnhancedAttestationPolicyCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface =
                    iBinder.queryLocalInterface(IEnhancedAttestationPolicyCallback.DESCRIPTOR);
            return (queryLocalInterface == null
                            || !(queryLocalInterface instanceof IEnhancedAttestationPolicyCallback))
                    ? new Proxy(iBinder)
                    : (IEnhancedAttestationPolicyCallback) queryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onAttestationFinished";
        }

        public int getMaxTransactionId() {
            return 0;
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2)
                throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IEnhancedAttestationPolicyCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IEnhancedAttestationPolicyCallback.DESCRIPTOR);
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            EnhancedAttestationResult enhancedAttestationResult =
                    (EnhancedAttestationResult)
                            parcel.readTypedObject(EnhancedAttestationResult.CREATOR);
            parcel.enforceNoDataAvail();
            onAttestationFinished(enhancedAttestationResult);
            parcel2.writeNoException();
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class Default implements IEnhancedAttestationPolicyCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.integrity.IEnhancedAttestationPolicyCallback
        public void onAttestationFinished(EnhancedAttestationResult enhancedAttestationResult)
                throws RemoteException {}
    }
}
