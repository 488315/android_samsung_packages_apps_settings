package com.att.iqi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

import com.att.iqi.lib.Metric;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public interface IMetricQueryCallback extends IInterface {
    public static final String DESCRIPTOR = "com.att.iqi.IMetricQueryCallback";

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class _Parcel {
        /* JADX INFO: Access modifiers changed from: private */
        public static <T> T readTypedObject(Parcel parcel, Parcelable.Creator<T> creator) {
            if (parcel.readInt() != 0) {
                return creator.createFromParcel(parcel);
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static <T extends Parcelable> void writeTypedObject(Parcel parcel, T t, int i) {
            if (t == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                t.writeToParcel(parcel, i);
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class Stub extends Binder implements IMetricQueryCallback {
        static final int TRANSACTION_onMetricQueried = 1;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public static class Proxy implements IMetricQueryCallback {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMetricQueryCallback.DESCRIPTOR;
            }

            public void onMetricQueried(Metric.ID id, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMetricQueryCallback.DESCRIPTOR);
                    _Parcel.writeTypedObject(obtain, id, 0);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IMetricQueryCallback.DESCRIPTOR);
        }

        public static IMetricQueryCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface =
                    iBinder.queryLocalInterface(IMetricQueryCallback.DESCRIPTOR);
            return (queryLocalInterface == null
                            || !(queryLocalInterface instanceof IMetricQueryCallback))
                    ? new Proxy(iBinder)
                    : (IMetricQueryCallback) queryLocalInterface;
        }

        public abstract /* synthetic */ void onMetricQueried(Metric.ID id, byte[] bArr)
                throws RemoteException;

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2)
                throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IMetricQueryCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMetricQueryCallback.DESCRIPTOR);
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            onMetricQueried(
                    (Metric.ID) _Parcel.readTypedObject(parcel, Metric.ID.CREATOR),
                    parcel.createByteArray());
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class Default implements IMetricQueryCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        public void onMetricQueried(Metric.ID id, byte[] bArr) throws RemoteException {}
    }
}
