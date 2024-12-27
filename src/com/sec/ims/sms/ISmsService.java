package com.sec.ims.sms;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public interface ISmsService extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.sms.ISmsService";

    void deRegisterForSMSStateChange(int i, ISmsServiceEventListener iSmsServiceEventListener)
            throws RemoteException;

    boolean getSmsFallback(int i) throws RemoteException;

    void registerForSMSStateChange(int i, ISmsServiceEventListener iSmsServiceEventListener)
            throws RemoteException;

    void sendDeliverReport(int i, byte[] bArr) throws RemoteException;

    void sendSMSOverIMS(int i, byte[] bArr, String str, String str2, int i2) throws RemoteException;

    void sendSMSResponse(boolean z, int i) throws RemoteException;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract static class Stub extends Binder implements ISmsService {
        static final int TRANSACTION_deRegisterForSMSStateChange = 2;
        static final int TRANSACTION_getSmsFallback = 3;
        static final int TRANSACTION_registerForSMSStateChange = 1;
        static final int TRANSACTION_sendDeliverReport = 6;
        static final int TRANSACTION_sendSMSOverIMS = 4;
        static final int TRANSACTION_sendSMSResponse = 5;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public static class Proxy implements ISmsService {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.sec.ims.sms.ISmsService
            public void deRegisterForSMSStateChange(
                    int i, ISmsServiceEventListener iSmsServiceEventListener)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISmsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iSmsServiceEventListener);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return ISmsService.DESCRIPTOR;
            }

            @Override // com.sec.ims.sms.ISmsService
            public boolean getSmsFallback(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISmsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.sms.ISmsService
            public void registerForSMSStateChange(
                    int i, ISmsServiceEventListener iSmsServiceEventListener)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISmsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iSmsServiceEventListener);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.sms.ISmsService
            public void sendDeliverReport(int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISmsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.sms.ISmsService
            public void sendSMSOverIMS(int i, byte[] bArr, String str, String str2, int i2)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISmsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.sms.ISmsService
            public void sendSMSResponse(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISmsService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, ISmsService.DESCRIPTOR);
        }

        public static ISmsService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISmsService.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof ISmsService))
                    ? new Proxy(iBinder)
                    : (ISmsService) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2)
                throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISmsService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISmsService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    ISmsServiceEventListener asInterface =
                            ISmsServiceEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerForSMSStateChange(readInt, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    ISmsServiceEventListener asInterface2 =
                            ISmsServiceEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    deRegisterForSMSStateChange(readInt2, asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean smsFallback = getSmsFallback(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(smsFallback);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendSMSOverIMS(readInt4, createByteArray, readString, readString2, readInt5);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    boolean readBoolean = parcel.readBoolean();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendSMSResponse(readBoolean, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt7 = parcel.readInt();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    sendDeliverReport(readInt7, createByteArray2);
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
    public static class Default implements ISmsService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.sms.ISmsService
        public boolean getSmsFallback(int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.sms.ISmsService
        public void deRegisterForSMSStateChange(
                int i, ISmsServiceEventListener iSmsServiceEventListener) throws RemoteException {}

        @Override // com.sec.ims.sms.ISmsService
        public void registerForSMSStateChange(
                int i, ISmsServiceEventListener iSmsServiceEventListener) throws RemoteException {}

        @Override // com.sec.ims.sms.ISmsService
        public void sendDeliverReport(int i, byte[] bArr) throws RemoteException {}

        @Override // com.sec.ims.sms.ISmsService
        public void sendSMSResponse(boolean z, int i) throws RemoteException {}

        @Override // com.sec.ims.sms.ISmsService
        public void sendSMSOverIMS(int i, byte[] bArr, String str, String str2, int i2)
                throws RemoteException {}
    }
}
