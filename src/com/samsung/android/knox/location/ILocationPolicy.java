package com.samsung.android.knox.location;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import com.samsung.android.knox.ContextInfo;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public interface ILocationPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.location.ILocationPolicy";

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class Default implements ILocationPolicy {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.location.ILocationPolicy
        public List<String> getAllLocationProviders(ContextInfo contextInfo)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.location.ILocationPolicy
        public boolean getIndividualLocationProvider(ContextInfo contextInfo, String str)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.location.ILocationPolicy
        public boolean isGPSOn(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.location.ILocationPolicy
        public boolean isGPSStateChangeAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.location.ILocationPolicy
        public boolean isLocationProviderBlocked(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.location.ILocationPolicy
        public boolean isLocationProviderBlockedAsUser(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.location.ILocationPolicy
        public boolean setGPSStateChangeAllowed(ContextInfo contextInfo, boolean z)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.location.ILocationPolicy
        public boolean setIndividualLocationProvider(ContextInfo contextInfo, String str, boolean z)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.location.ILocationPolicy
        public boolean startGPS(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }
    }

    List<String> getAllLocationProviders(ContextInfo contextInfo) throws RemoteException;

    boolean getIndividualLocationProvider(ContextInfo contextInfo, String str)
            throws RemoteException;

    boolean isGPSOn(ContextInfo contextInfo) throws RemoteException;

    boolean isGPSStateChangeAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isLocationProviderBlocked(String str) throws RemoteException;

    boolean isLocationProviderBlockedAsUser(String str, int i) throws RemoteException;

    boolean setGPSStateChangeAllowed(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setIndividualLocationProvider(ContextInfo contextInfo, String str, boolean z)
            throws RemoteException;

    boolean startGPS(ContextInfo contextInfo, boolean z) throws RemoteException;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract static class Stub extends Binder implements ILocationPolicy {
        public static final int TRANSACTION_getAllLocationProviders = 5;
        public static final int TRANSACTION_getIndividualLocationProvider = 2;
        public static final int TRANSACTION_isGPSOn = 9;
        public static final int TRANSACTION_isGPSStateChangeAllowed = 7;
        public static final int TRANSACTION_isLocationProviderBlocked = 3;
        public static final int TRANSACTION_isLocationProviderBlockedAsUser = 4;
        public static final int TRANSACTION_setGPSStateChangeAllowed = 6;
        public static final int TRANSACTION_setIndividualLocationProvider = 1;
        public static final int TRANSACTION_startGPS = 8;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public static class Proxy implements ILocationPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.location.ILocationPolicy
            public List<String> getAllLocationProviders(ContextInfo contextInfo)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILocationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.location.ILocationPolicy
            public boolean getIndividualLocationProvider(ContextInfo contextInfo, String str)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILocationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return ILocationPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.location.ILocationPolicy
            public boolean isGPSOn(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILocationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.location.ILocationPolicy
            public boolean isGPSStateChangeAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILocationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.location.ILocationPolicy
            public boolean isLocationProviderBlocked(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILocationPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.location.ILocationPolicy
            public boolean isLocationProviderBlockedAsUser(String str, int i)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILocationPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.location.ILocationPolicy
            public boolean setGPSStateChangeAllowed(ContextInfo contextInfo, boolean z)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILocationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.location.ILocationPolicy
            public boolean setIndividualLocationProvider(
                    ContextInfo contextInfo, String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILocationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.location.ILocationPolicy
            public boolean startGPS(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILocationPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, ILocationPolicy.DESCRIPTOR);
        }

        public static ILocationPolicy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface =
                    iBinder.queryLocalInterface(ILocationPolicy.DESCRIPTOR);
            return (queryLocalInterface == null
                            || !(queryLocalInterface instanceof ILocationPolicy))
                    ? new Proxy(iBinder)
                    : (ILocationPolicy) queryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setIndividualLocationProvider";
                case 2:
                    return "getIndividualLocationProvider";
                case 3:
                    return "isLocationProviderBlocked";
                case 4:
                    return "isLocationProviderBlockedAsUser";
                case 5:
                    return "getAllLocationProviders";
                case 6:
                    return "setGPSStateChangeAllowed";
                case 7:
                    return "isGPSStateChangeAllowed";
                case 8:
                    return "startGPS";
                case 9:
                    return "isGPSOn";
                default:
                    return null;
            }
        }

        public int getMaxTransactionId() {
            return 8;
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2)
                throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ILocationPolicy.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ILocationPolicy.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ContextInfo contextInfo =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean individualLocationProvider =
                            setIndividualLocationProvider(contextInfo, readString, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(individualLocationProvider);
                    return true;
                case 2:
                    ContextInfo contextInfo2 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean individualLocationProvider2 =
                            getIndividualLocationProvider(contextInfo2, readString2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(individualLocationProvider2);
                    return true;
                case 3:
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isLocationProviderBlocked = isLocationProviderBlocked(readString3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isLocationProviderBlocked);
                    return true;
                case 4:
                    String readString4 = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isLocationProviderBlockedAsUser =
                            isLocationProviderBlockedAsUser(readString4, readInt);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isLocationProviderBlockedAsUser);
                    return true;
                case 5:
                    ContextInfo contextInfo3 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> allLocationProviders = getAllLocationProviders(contextInfo3);
                    parcel2.writeNoException();
                    parcel2.writeStringList(allLocationProviders);
                    return true;
                case 6:
                    ContextInfo contextInfo4 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean gPSStateChangeAllowed =
                            setGPSStateChangeAllowed(contextInfo4, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(gPSStateChangeAllowed);
                    return true;
                case 7:
                    ContextInfo contextInfo5 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isGPSStateChangeAllowed = isGPSStateChangeAllowed(contextInfo5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isGPSStateChangeAllowed);
                    return true;
                case 8:
                    ContextInfo contextInfo6 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean startGPS = startGPS(contextInfo6, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startGPS);
                    return true;
                case 9:
                    ContextInfo contextInfo7 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isGPSOn = isGPSOn(contextInfo7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isGPSOn);
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
}