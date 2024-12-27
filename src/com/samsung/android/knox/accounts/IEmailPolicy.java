package com.samsung.android.knox.accounts;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import com.samsung.android.knox.ContextInfo;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public interface IEmailPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.accounts.IEmailPolicy";

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class Default implements IEmailPolicy {
        @Override // com.samsung.android.knox.accounts.IEmailPolicy
        public boolean allowAccountAddition(ContextInfo contextInfo, boolean z)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailPolicy
        public boolean allowEmailSettingsChange(ContextInfo contextInfo, boolean z, long j)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailPolicy
        public boolean allowPopImapEmail(ContextInfo contextInfo, boolean z)
                throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.accounts.IEmailPolicy
        public boolean getAllowEmailForwarding(ContextInfo contextInfo, String str)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailPolicy
        public boolean getAllowHTMLEmail(ContextInfo contextInfo, String str)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailPolicy
        public boolean isAccountAdditionAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailPolicy
        public boolean isEmailNotificationsEnabled(ContextInfo contextInfo, long j)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailPolicy
        public boolean isEmailSettingsChangeAllowed(ContextInfo contextInfo, long j)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailPolicy
        public boolean isPopImapEmailAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailPolicy
        public boolean setAllowEmailForwarding(ContextInfo contextInfo, String str, boolean z)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailPolicy
        public boolean setAllowHTMLEmail(ContextInfo contextInfo, String str, boolean z)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.accounts.IEmailPolicy
        public boolean setEmailNotificationsState(ContextInfo contextInfo, boolean z, long j)
                throws RemoteException {
            return false;
        }
    }

    boolean allowAccountAddition(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowEmailSettingsChange(ContextInfo contextInfo, boolean z, long j)
            throws RemoteException;

    boolean allowPopImapEmail(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean getAllowEmailForwarding(ContextInfo contextInfo, String str) throws RemoteException;

    boolean getAllowHTMLEmail(ContextInfo contextInfo, String str) throws RemoteException;

    boolean isAccountAdditionAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isEmailNotificationsEnabled(ContextInfo contextInfo, long j) throws RemoteException;

    boolean isEmailSettingsChangeAllowed(ContextInfo contextInfo, long j) throws RemoteException;

    boolean isPopImapEmailAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean setAllowEmailForwarding(ContextInfo contextInfo, String str, boolean z)
            throws RemoteException;

    boolean setAllowHTMLEmail(ContextInfo contextInfo, String str, boolean z)
            throws RemoteException;

    boolean setEmailNotificationsState(ContextInfo contextInfo, boolean z, long j)
            throws RemoteException;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract static class Stub extends Binder implements IEmailPolicy {
        public static final int TRANSACTION_allowAccountAddition = 1;
        public static final int TRANSACTION_allowEmailSettingsChange = 11;
        public static final int TRANSACTION_allowPopImapEmail = 3;
        public static final int TRANSACTION_getAllowEmailForwarding = 6;
        public static final int TRANSACTION_getAllowHTMLEmail = 8;
        public static final int TRANSACTION_isAccountAdditionAllowed = 2;
        public static final int TRANSACTION_isEmailNotificationsEnabled = 10;
        public static final int TRANSACTION_isEmailSettingsChangeAllowed = 12;
        public static final int TRANSACTION_isPopImapEmailAllowed = 4;
        public static final int TRANSACTION_setAllowEmailForwarding = 5;
        public static final int TRANSACTION_setAllowHTMLEmail = 7;
        public static final int TRANSACTION_setEmailNotificationsState = 9;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public static class Proxy implements IEmailPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.accounts.IEmailPolicy
            public boolean allowAccountAddition(ContextInfo contextInfo, boolean z)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailPolicy
            public boolean allowEmailSettingsChange(ContextInfo contextInfo, boolean z, long j)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeLong(j);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailPolicy
            public boolean allowPopImapEmail(ContextInfo contextInfo, boolean z)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.accounts.IEmailPolicy
            public boolean getAllowEmailForwarding(ContextInfo contextInfo, String str)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailPolicy
            public boolean getAllowHTMLEmail(ContextInfo contextInfo, String str)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IEmailPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.accounts.IEmailPolicy
            public boolean isAccountAdditionAllowed(ContextInfo contextInfo)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailPolicy
            public boolean isEmailNotificationsEnabled(ContextInfo contextInfo, long j)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailPolicy
            public boolean isEmailSettingsChangeAllowed(ContextInfo contextInfo, long j)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailPolicy
            public boolean isPopImapEmailAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailPolicy
            public boolean setAllowEmailForwarding(ContextInfo contextInfo, String str, boolean z)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailPolicy
            public boolean setAllowHTMLEmail(ContextInfo contextInfo, String str, boolean z)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.accounts.IEmailPolicy
            public boolean setEmailNotificationsState(ContextInfo contextInfo, boolean z, long j)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEmailPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeLong(j);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IEmailPolicy.DESCRIPTOR);
        }

        public static IEmailPolicy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IEmailPolicy.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IEmailPolicy))
                    ? new Proxy(iBinder)
                    : (IEmailPolicy) queryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "allowAccountAddition";
                case 2:
                    return "isAccountAdditionAllowed";
                case 3:
                    return "allowPopImapEmail";
                case 4:
                    return "isPopImapEmailAllowed";
                case 5:
                    return "setAllowEmailForwarding";
                case 6:
                    return "getAllowEmailForwarding";
                case 7:
                    return "setAllowHTMLEmail";
                case 8:
                    return "getAllowHTMLEmail";
                case 9:
                    return "setEmailNotificationsState";
                case 10:
                    return "isEmailNotificationsEnabled";
                case 11:
                    return "allowEmailSettingsChange";
                case 12:
                    return "isEmailSettingsChangeAllowed";
                default:
                    return null;
            }
        }

        public int getMaxTransactionId() {
            return 11;
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2)
                throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IEmailPolicy.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IEmailPolicy.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ContextInfo contextInfo =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowAccountAddition = allowAccountAddition(contextInfo, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowAccountAddition);
                    return true;
                case 2:
                    ContextInfo contextInfo2 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isAccountAdditionAllowed = isAccountAdditionAllowed(contextInfo2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAccountAdditionAllowed);
                    return true;
                case 3:
                    ContextInfo contextInfo3 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowPopImapEmail = allowPopImapEmail(contextInfo3, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowPopImapEmail);
                    return true;
                case 4:
                    ContextInfo contextInfo4 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isPopImapEmailAllowed = isPopImapEmailAllowed(contextInfo4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPopImapEmailAllowed);
                    return true;
                case 5:
                    ContextInfo contextInfo5 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowEmailForwarding =
                            setAllowEmailForwarding(contextInfo5, readString, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowEmailForwarding);
                    return true;
                case 6:
                    ContextInfo contextInfo6 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean allowEmailForwarding2 =
                            getAllowEmailForwarding(contextInfo6, readString2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowEmailForwarding2);
                    return true;
                case 7:
                    ContextInfo contextInfo7 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString3 = parcel.readString();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowHTMLEmail =
                            setAllowHTMLEmail(contextInfo7, readString3, readBoolean4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowHTMLEmail);
                    return true;
                case 8:
                    ContextInfo contextInfo8 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean allowHTMLEmail2 = getAllowHTMLEmail(contextInfo8, readString4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowHTMLEmail2);
                    return true;
                case 9:
                    ContextInfo contextInfo9 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean5 = parcel.readBoolean();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean emailNotificationsState =
                            setEmailNotificationsState(contextInfo9, readBoolean5, readLong);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(emailNotificationsState);
                    return true;
                case 10:
                    ContextInfo contextInfo10 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean isEmailNotificationsEnabled =
                            isEmailNotificationsEnabled(contextInfo10, readLong2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isEmailNotificationsEnabled);
                    return true;
                case 11:
                    ContextInfo contextInfo11 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean6 = parcel.readBoolean();
                    long readLong3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean allowEmailSettingsChange =
                            allowEmailSettingsChange(contextInfo11, readBoolean6, readLong3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowEmailSettingsChange);
                    return true;
                case 12:
                    ContextInfo contextInfo12 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean isEmailSettingsChangeAllowed =
                            isEmailSettingsChangeAllowed(contextInfo12, readLong4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isEmailSettingsChangeAllowed);
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
