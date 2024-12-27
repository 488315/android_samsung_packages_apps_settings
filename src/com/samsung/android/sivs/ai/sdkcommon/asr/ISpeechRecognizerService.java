package com.samsung.android.sivs.ai.sdkcommon.asr;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public interface ISpeechRecognizerService extends IInterface {
    public static final String DESCRIPTOR =
            "com.samsung.android.sivs.ai.sdkcommon.asr.ISpeechRecognizerService";

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class Default implements ISpeechRecognizerService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.sivs.ai.sdkcommon.asr.ISpeechRecognizerService
        public ISpeechRecognizer create(Bundle bundle) throws RemoteException {
            return null;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class _Parcel {
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

    ISpeechRecognizer create(Bundle bundle) throws RemoteException;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract static class Stub extends Binder implements ISpeechRecognizerService {
        static final int TRANSACTION_create = 1;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public static class Proxy implements ISpeechRecognizerService {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.sivs.ai.sdkcommon.asr.ISpeechRecognizerService
            public ISpeechRecognizer create(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpeechRecognizerService.DESCRIPTOR);
                    _Parcel.writeTypedObject(obtain, bundle, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return ISpeechRecognizer.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return ISpeechRecognizerService.DESCRIPTOR;
            }
        }

        public Stub() {
            attachInterface(this, ISpeechRecognizerService.DESCRIPTOR);
        }

        public static ISpeechRecognizerService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface =
                    iBinder.queryLocalInterface(ISpeechRecognizerService.DESCRIPTOR);
            return (queryLocalInterface == null
                            || !(queryLocalInterface instanceof ISpeechRecognizerService))
                    ? new Proxy(iBinder)
                    : (ISpeechRecognizerService) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2)
                throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISpeechRecognizerService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISpeechRecognizerService.DESCRIPTOR);
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            ISpeechRecognizer create =
                    create((Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
            parcel2.writeNoException();
            parcel2.writeStrongInterface(create);
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }
}
