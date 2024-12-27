package com.samsung.android.knox.kiosk;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import com.samsung.android.knox.ContextInfo;

import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public interface IKioskMode extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.kiosk.IKioskMode";

    boolean allowAirCommandMode(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowAirViewMode(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowEdgeScreen(ContextInfo contextInfo, int i, boolean z) throws RemoteException;

    int[] allowHardwareKeys(ContextInfo contextInfo, int[] iArr, boolean z) throws RemoteException;

    boolean allowMultiWindowMode(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowTaskManager(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean clearAllNotifications(ContextInfo contextInfo) throws RemoteException;

    void disableKioskMode(ContextInfo contextInfo) throws RemoteException;

    void enableKioskMode(ContextInfo contextInfo, String str) throws RemoteException;

    List getAllBlockedHardwareKeys(ContextInfo contextInfo) throws RemoteException;

    int getBlockedEdgeScreen(ContextInfo contextInfo) throws RemoteException;

    Map getBlockedHwKeysCache() throws RemoteException;

    List getHardwareKeyList(ContextInfo contextInfo) throws RemoteException;

    String getKioskHomePackage(ContextInfo contextInfo) throws RemoteException;

    String getKioskHomePackageAsUser(int i) throws RemoteException;

    boolean hideNavigationBar(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean hideStatusBar(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean hideSystemBar(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isAirCommandModeAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isAirViewModeAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isEnableKioskModeAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isHardwareKeyAllowed(ContextInfo contextInfo, int i, boolean z) throws RemoteException;

    boolean isKioskModeEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isKioskModeEnabledAsUser(int i) throws RemoteException;

    boolean isMultiWindowModeAllowed(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isMultiWindowModeAllowedAsUser(int i) throws RemoteException;

    boolean isNavigationBarHidden(ContextInfo contextInfo) throws RemoteException;

    boolean isStatusBarHidden(ContextInfo contextInfo) throws RemoteException;

    boolean isStatusBarHiddenAsUser(int i) throws RemoteException;

    boolean isSystemBarHidden(ContextInfo contextInfo) throws RemoteException;

    boolean isTaskManagerAllowed(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean isTaskManagerAllowedAsUser(boolean z, int i) throws RemoteException;

    boolean wipeRecentTasks(ContextInfo contextInfo) throws RemoteException;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract static class Stub extends Binder implements IKioskMode {
        public static final int TRANSACTION_allowAirCommandMode = 29;
        public static final int TRANSACTION_allowAirViewMode = 31;
        public static final int TRANSACTION_allowEdgeScreen = 32;
        public static final int TRANSACTION_allowHardwareKeys = 16;
        public static final int TRANSACTION_allowMultiWindowMode = 24;
        public static final int TRANSACTION_allowTaskManager = 12;
        public static final int TRANSACTION_clearAllNotifications = 27;
        public static final int TRANSACTION_disableKioskMode = 2;
        public static final int TRANSACTION_enableKioskMode = 1;
        public static final int TRANSACTION_getAllBlockedHardwareKeys = 18;
        public static final int TRANSACTION_getBlockedEdgeScreen = 33;
        public static final int TRANSACTION_getBlockedHwKeysCache = 19;
        public static final int TRANSACTION_getHardwareKeyList = 15;
        public static final int TRANSACTION_getKioskHomePackage = 5;
        public static final int TRANSACTION_getKioskHomePackageAsUser = 6;
        public static final int TRANSACTION_hideNavigationBar = 22;
        public static final int TRANSACTION_hideStatusBar = 20;
        public static final int TRANSACTION_hideSystemBar = 8;
        public static final int TRANSACTION_isAirCommandModeAllowed = 28;
        public static final int TRANSACTION_isAirViewModeAllowed = 30;
        public static final int TRANSACTION_isEnableKioskModeAllowed = 7;
        public static final int TRANSACTION_isHardwareKeyAllowed = 17;
        public static final int TRANSACTION_isKioskModeEnabled = 3;
        public static final int TRANSACTION_isKioskModeEnabledAsUser = 4;
        public static final int TRANSACTION_isMultiWindowModeAllowed = 25;
        public static final int TRANSACTION_isMultiWindowModeAllowedAsUser = 26;
        public static final int TRANSACTION_isNavigationBarHidden = 23;
        public static final int TRANSACTION_isStatusBarHidden = 21;
        public static final int TRANSACTION_isStatusBarHiddenAsUser = 10;
        public static final int TRANSACTION_isSystemBarHidden = 9;
        public static final int TRANSACTION_isTaskManagerAllowed = 13;
        public static final int TRANSACTION_isTaskManagerAllowedAsUser = 14;
        public static final int TRANSACTION_wipeRecentTasks = 11;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public static class Proxy implements IKioskMode {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean allowAirCommandMode(ContextInfo contextInfo, boolean z)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean allowAirViewMode(ContextInfo contextInfo, boolean z)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean allowEdgeScreen(ContextInfo contextInfo, int i, boolean z)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public int[] allowHardwareKeys(ContextInfo contextInfo, int[] iArr, boolean z)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeIntArray(iArr);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean allowMultiWindowMode(ContextInfo contextInfo, boolean z)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean allowTaskManager(ContextInfo contextInfo, boolean z)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(12, obtain, obtain2, 0);
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

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean clearAllNotifications(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public void disableKioskMode(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public void enableKioskMode(ContextInfo contextInfo, String str)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public List getAllBlockedHardwareKeys(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public int getBlockedEdgeScreen(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public Map getBlockedHwKeysCache() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public List getHardwareKeyList(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IKioskMode.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public String getKioskHomePackage(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public String getKioskHomePackageAsUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean hideNavigationBar(ContextInfo contextInfo, boolean z)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean hideStatusBar(ContextInfo contextInfo, boolean z)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean hideSystemBar(ContextInfo contextInfo, boolean z)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
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

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean isAirCommandModeAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean isAirViewModeAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean isEnableKioskModeAllowed(ContextInfo contextInfo)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean isHardwareKeyAllowed(ContextInfo contextInfo, int i, boolean z)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean isKioskModeEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean isKioskModeEnabledAsUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean isMultiWindowModeAllowed(ContextInfo contextInfo, boolean z)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean isMultiWindowModeAllowedAsUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean isNavigationBarHidden(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean isStatusBarHidden(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean isStatusBarHiddenAsUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean isSystemBarHidden(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean isTaskManagerAllowed(ContextInfo contextInfo, boolean z)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean isTaskManagerAllowedAsUser(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kiosk.IKioskMode
            public boolean wipeRecentTasks(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKioskMode.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IKioskMode.DESCRIPTOR);
        }

        public static IKioskMode asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IKioskMode.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IKioskMode))
                    ? new Proxy(iBinder)
                    : (IKioskMode) queryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "enableKioskMode";
                case 2:
                    return "disableKioskMode";
                case 3:
                    return "isKioskModeEnabled";
                case 4:
                    return "isKioskModeEnabledAsUser";
                case 5:
                    return "getKioskHomePackage";
                case 6:
                    return "getKioskHomePackageAsUser";
                case 7:
                    return "isEnableKioskModeAllowed";
                case 8:
                    return "hideSystemBar";
                case 9:
                    return "isSystemBarHidden";
                case 10:
                    return "isStatusBarHiddenAsUser";
                case 11:
                    return "wipeRecentTasks";
                case 12:
                    return "allowTaskManager";
                case 13:
                    return "isTaskManagerAllowed";
                case 14:
                    return "isTaskManagerAllowedAsUser";
                case 15:
                    return "getHardwareKeyList";
                case 16:
                    return "allowHardwareKeys";
                case 17:
                    return "isHardwareKeyAllowed";
                case 18:
                    return "getAllBlockedHardwareKeys";
                case 19:
                    return "getBlockedHwKeysCache";
                case 20:
                    return "hideStatusBar";
                case 21:
                    return "isStatusBarHidden";
                case 22:
                    return "hideNavigationBar";
                case 23:
                    return "isNavigationBarHidden";
                case 24:
                    return "allowMultiWindowMode";
                case 25:
                    return "isMultiWindowModeAllowed";
                case 26:
                    return "isMultiWindowModeAllowedAsUser";
                case 27:
                    return "clearAllNotifications";
                case 28:
                    return "isAirCommandModeAllowed";
                case 29:
                    return "allowAirCommandMode";
                case 30:
                    return "isAirViewModeAllowed";
                case 31:
                    return "allowAirViewMode";
                case 32:
                    return "allowEdgeScreen";
                case 33:
                    return "getBlockedEdgeScreen";
                default:
                    return null;
            }
        }

        public int getMaxTransactionId() {
            return 32;
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2)
                throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IKioskMode.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IKioskMode.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ContextInfo contextInfo =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    enableKioskMode(contextInfo, readString);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    ContextInfo contextInfo2 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    disableKioskMode(contextInfo2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    ContextInfo contextInfo3 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isKioskModeEnabled = isKioskModeEnabled(contextInfo3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isKioskModeEnabled);
                    return true;
                case 4:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isKioskModeEnabledAsUser = isKioskModeEnabledAsUser(readInt);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isKioskModeEnabledAsUser);
                    return true;
                case 5:
                    ContextInfo contextInfo4 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String kioskHomePackage = getKioskHomePackage(contextInfo4);
                    parcel2.writeNoException();
                    parcel2.writeString(kioskHomePackage);
                    return true;
                case 6:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String kioskHomePackageAsUser = getKioskHomePackageAsUser(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeString(kioskHomePackageAsUser);
                    return true;
                case 7:
                    ContextInfo contextInfo5 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isEnableKioskModeAllowed = isEnableKioskModeAllowed(contextInfo5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isEnableKioskModeAllowed);
                    return true;
                case 8:
                    ContextInfo contextInfo6 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean hideSystemBar = hideSystemBar(contextInfo6, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hideSystemBar);
                    return true;
                case 9:
                    ContextInfo contextInfo7 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isSystemBarHidden = isSystemBarHidden(contextInfo7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSystemBarHidden);
                    return true;
                case 10:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isStatusBarHiddenAsUser = isStatusBarHiddenAsUser(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isStatusBarHiddenAsUser);
                    return true;
                case 11:
                    ContextInfo contextInfo8 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean wipeRecentTasks = wipeRecentTasks(contextInfo8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(wipeRecentTasks);
                    return true;
                case 12:
                    ContextInfo contextInfo9 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowTaskManager = allowTaskManager(contextInfo9, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowTaskManager);
                    return true;
                case 13:
                    ContextInfo contextInfo10 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isTaskManagerAllowed =
                            isTaskManagerAllowed(contextInfo10, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isTaskManagerAllowed);
                    return true;
                case 14:
                    boolean readBoolean4 = parcel.readBoolean();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isTaskManagerAllowedAsUser =
                            isTaskManagerAllowedAsUser(readBoolean4, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isTaskManagerAllowedAsUser);
                    return true;
                case 15:
                    ContextInfo contextInfo11 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List hardwareKeyList = getHardwareKeyList(contextInfo11);
                    parcel2.writeNoException();
                    parcel2.writeList(hardwareKeyList);
                    return true;
                case 16:
                    ContextInfo contextInfo12 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int[] createIntArray = parcel.createIntArray();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int[] allowHardwareKeys =
                            allowHardwareKeys(contextInfo12, createIntArray, readBoolean5);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(allowHardwareKeys);
                    return true;
                case 17:
                    ContextInfo contextInfo13 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt5 = parcel.readInt();
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isHardwareKeyAllowed =
                            isHardwareKeyAllowed(contextInfo13, readInt5, readBoolean6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isHardwareKeyAllowed);
                    return true;
                case 18:
                    ContextInfo contextInfo14 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List allBlockedHardwareKeys = getAllBlockedHardwareKeys(contextInfo14);
                    parcel2.writeNoException();
                    parcel2.writeList(allBlockedHardwareKeys);
                    return true;
                case 19:
                    Map blockedHwKeysCache = getBlockedHwKeysCache();
                    parcel2.writeNoException();
                    parcel2.writeMap(blockedHwKeysCache);
                    return true;
                case 20:
                    ContextInfo contextInfo15 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean hideStatusBar = hideStatusBar(contextInfo15, readBoolean7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hideStatusBar);
                    return true;
                case 21:
                    ContextInfo contextInfo16 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isStatusBarHidden = isStatusBarHidden(contextInfo16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isStatusBarHidden);
                    return true;
                case 22:
                    ContextInfo contextInfo17 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean hideNavigationBar = hideNavigationBar(contextInfo17, readBoolean8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hideNavigationBar);
                    return true;
                case 23:
                    ContextInfo contextInfo18 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isNavigationBarHidden = isNavigationBarHidden(contextInfo18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNavigationBarHidden);
                    return true;
                case 24:
                    ContextInfo contextInfo19 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowMultiWindowMode =
                            allowMultiWindowMode(contextInfo19, readBoolean9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowMultiWindowMode);
                    return true;
                case 25:
                    ContextInfo contextInfo20 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isMultiWindowModeAllowed =
                            isMultiWindowModeAllowed(contextInfo20, readBoolean10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isMultiWindowModeAllowed);
                    return true;
                case 26:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isMultiWindowModeAllowedAsUser =
                            isMultiWindowModeAllowedAsUser(readInt6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isMultiWindowModeAllowedAsUser);
                    return true;
                case 27:
                    ContextInfo contextInfo21 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean clearAllNotifications = clearAllNotifications(contextInfo21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearAllNotifications);
                    return true;
                case 28:
                    ContextInfo contextInfo22 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isAirCommandModeAllowed = isAirCommandModeAllowed(contextInfo22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAirCommandModeAllowed);
                    return true;
                case 29:
                    ContextInfo contextInfo23 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowAirCommandMode = allowAirCommandMode(contextInfo23, readBoolean11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowAirCommandMode);
                    return true;
                case 30:
                    ContextInfo contextInfo24 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isAirViewModeAllowed = isAirViewModeAllowed(contextInfo24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAirViewModeAllowed);
                    return true;
                case 31:
                    ContextInfo contextInfo25 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowAirViewMode = allowAirViewMode(contextInfo25, readBoolean12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowAirViewMode);
                    return true;
                case 32:
                    ContextInfo contextInfo26 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt7 = parcel.readInt();
                    boolean readBoolean13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowEdgeScreen =
                            allowEdgeScreen(contextInfo26, readInt7, readBoolean13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowEdgeScreen);
                    return true;
                case 33:
                    ContextInfo contextInfo27 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int blockedEdgeScreen = getBlockedEdgeScreen(contextInfo27);
                    parcel2.writeNoException();
                    parcel2.writeInt(blockedEdgeScreen);
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
    public static class Default implements IKioskMode {
        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean allowAirCommandMode(ContextInfo contextInfo, boolean z)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean allowAirViewMode(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean allowEdgeScreen(ContextInfo contextInfo, int i, boolean z)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public int[] allowHardwareKeys(ContextInfo contextInfo, int[] iArr, boolean z)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean allowMultiWindowMode(ContextInfo contextInfo, boolean z)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean allowTaskManager(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean clearAllNotifications(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public List getAllBlockedHardwareKeys(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public int getBlockedEdgeScreen(ContextInfo contextInfo) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public Map getBlockedHwKeysCache() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public List getHardwareKeyList(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public String getKioskHomePackage(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public String getKioskHomePackageAsUser(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean hideNavigationBar(ContextInfo contextInfo, boolean z)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean hideStatusBar(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean hideSystemBar(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean isAirCommandModeAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean isAirViewModeAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean isEnableKioskModeAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean isHardwareKeyAllowed(ContextInfo contextInfo, int i, boolean z)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean isKioskModeEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean isKioskModeEnabledAsUser(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean isMultiWindowModeAllowed(ContextInfo contextInfo, boolean z)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean isMultiWindowModeAllowedAsUser(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean isNavigationBarHidden(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean isStatusBarHidden(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean isStatusBarHiddenAsUser(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean isSystemBarHidden(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean isTaskManagerAllowed(ContextInfo contextInfo, boolean z)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean isTaskManagerAllowedAsUser(boolean z, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public boolean wipeRecentTasks(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public void disableKioskMode(ContextInfo contextInfo) throws RemoteException {}

        @Override // com.samsung.android.knox.kiosk.IKioskMode
        public void enableKioskMode(ContextInfo contextInfo, String str) throws RemoteException {}
    }
}
