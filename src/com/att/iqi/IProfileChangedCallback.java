package com.att.iqi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public interface IProfileChangedCallback extends IInterface {
    public static final String DESCRIPTOR = "com.att.iqi.IProfileChangedCallback";

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class Default implements IProfileChangedCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        public void onProfileChanged() throws RemoteException {}
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class Stub extends Binder implements IProfileChangedCallback {
        static final int TRANSACTION_onProfileChanged = 1;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public static class Proxy implements IProfileChangedCallback {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IProfileChangedCallback.DESCRIPTOR;
            }

            public void onProfileChanged() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IProfileChangedCallback.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IProfileChangedCallback.DESCRIPTOR);
        }

        public static IProfileChangedCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface =
                    iBinder.queryLocalInterface(IProfileChangedCallback.DESCRIPTOR);
            return (queryLocalInterface == null
                            || !(queryLocalInterface instanceof IProfileChangedCallback))
                    ? new Proxy(iBinder)
                    : (IProfileChangedCallback) queryLocalInterface;
        }

        public abstract /* synthetic */ void onProfileChanged() throws RemoteException;

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2)
                throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IProfileChangedCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IProfileChangedCallback.DESCRIPTOR);
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            onProfileChanged();
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }
}
