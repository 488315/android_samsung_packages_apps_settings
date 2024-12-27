package com.samsung.android.knox.bluetooth;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import com.samsung.android.knox.ContextInfo;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public interface IBluetoothSecureModePolicy extends IInterface {
    public static final String DESCRIPTOR =
            "com.samsung.android.knox.bluetooth.IBluetoothSecureModePolicy";

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class Default implements IBluetoothSecureModePolicy {
        @Override // com.samsung.android.knox.bluetooth.IBluetoothSecureModePolicy
        public boolean addBluetoothDevicesToWhiteList(
                ContextInfo contextInfo, List<BluetoothSecureModeWhitelistConfig> list)
                throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothSecureModePolicy
        public boolean disableSecureMode(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothSecureModePolicy
        public boolean enableDeviceWhiteList(ContextInfo contextInfo, boolean z)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothSecureModePolicy
        public boolean enableSecureMode(
                ContextInfo contextInfo,
                BluetoothSecureModeConfig bluetoothSecureModeConfig,
                List<BluetoothSecureModeWhitelistConfig> list)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothSecureModePolicy
        public List<BluetoothSecureModeWhitelistConfig> getBluetoothDevicesFromWhiteList(
                ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothSecureModePolicy
        public BluetoothSecureModeConfig getSecureModeConfiguration(ContextInfo contextInfo)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothSecureModePolicy
        public boolean isDeviceWhiteListEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothSecureModePolicy
        public boolean isSecureModeEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.bluetooth.IBluetoothSecureModePolicy
        public boolean removeBluetoothDevicesFromWhiteList(
                ContextInfo contextInfo, List<BluetoothSecureModeWhitelistConfig> list)
                throws RemoteException {
            return false;
        }
    }

    boolean addBluetoothDevicesToWhiteList(
            ContextInfo contextInfo, List<BluetoothSecureModeWhitelistConfig> list)
            throws RemoteException;

    boolean disableSecureMode(ContextInfo contextInfo) throws RemoteException;

    boolean enableDeviceWhiteList(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean enableSecureMode(
            ContextInfo contextInfo,
            BluetoothSecureModeConfig bluetoothSecureModeConfig,
            List<BluetoothSecureModeWhitelistConfig> list)
            throws RemoteException;

    List<BluetoothSecureModeWhitelistConfig> getBluetoothDevicesFromWhiteList(
            ContextInfo contextInfo) throws RemoteException;

    BluetoothSecureModeConfig getSecureModeConfiguration(ContextInfo contextInfo)
            throws RemoteException;

    boolean isDeviceWhiteListEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isSecureModeEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean removeBluetoothDevicesFromWhiteList(
            ContextInfo contextInfo, List<BluetoothSecureModeWhitelistConfig> list)
            throws RemoteException;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract static class Stub extends Binder implements IBluetoothSecureModePolicy {
        public static final int TRANSACTION_addBluetoothDevicesToWhiteList = 8;
        public static final int TRANSACTION_disableSecureMode = 2;
        public static final int TRANSACTION_enableDeviceWhiteList = 5;
        public static final int TRANSACTION_enableSecureMode = 1;
        public static final int TRANSACTION_getBluetoothDevicesFromWhiteList = 7;
        public static final int TRANSACTION_getSecureModeConfiguration = 3;
        public static final int TRANSACTION_isDeviceWhiteListEnabled = 6;
        public static final int TRANSACTION_isSecureModeEnabled = 4;
        public static final int TRANSACTION_removeBluetoothDevicesFromWhiteList = 9;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public static class Proxy implements IBluetoothSecureModePolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothSecureModePolicy
            public boolean addBluetoothDevicesToWhiteList(
                    ContextInfo contextInfo, List<BluetoothSecureModeWhitelistConfig> list)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothSecureModePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
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

            @Override // com.samsung.android.knox.bluetooth.IBluetoothSecureModePolicy
            public boolean disableSecureMode(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothSecureModePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothSecureModePolicy
            public boolean enableDeviceWhiteList(ContextInfo contextInfo, boolean z)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothSecureModePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothSecureModePolicy
            public boolean enableSecureMode(
                    ContextInfo contextInfo,
                    BluetoothSecureModeConfig bluetoothSecureModeConfig,
                    List<BluetoothSecureModeWhitelistConfig> list)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothSecureModePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(bluetoothSecureModeConfig, 0);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothSecureModePolicy
            public List<BluetoothSecureModeWhitelistConfig> getBluetoothDevicesFromWhiteList(
                    ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothSecureModePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(BluetoothSecureModeWhitelistConfig.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IBluetoothSecureModePolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothSecureModePolicy
            public BluetoothSecureModeConfig getSecureModeConfiguration(ContextInfo contextInfo)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothSecureModePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (BluetoothSecureModeConfig)
                            obtain2.readTypedObject(BluetoothSecureModeConfig.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothSecureModePolicy
            public boolean isDeviceWhiteListEnabled(ContextInfo contextInfo)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothSecureModePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothSecureModePolicy
            public boolean isSecureModeEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothSecureModePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.bluetooth.IBluetoothSecureModePolicy
            public boolean removeBluetoothDevicesFromWhiteList(
                    ContextInfo contextInfo, List<BluetoothSecureModeWhitelistConfig> list)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBluetoothSecureModePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedList(list, 0);
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
            attachInterface(this, IBluetoothSecureModePolicy.DESCRIPTOR);
        }

        public static IBluetoothSecureModePolicy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface =
                    iBinder.queryLocalInterface(IBluetoothSecureModePolicy.DESCRIPTOR);
            return (queryLocalInterface == null
                            || !(queryLocalInterface instanceof IBluetoothSecureModePolicy))
                    ? new Proxy(iBinder)
                    : (IBluetoothSecureModePolicy) queryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "enableSecureMode";
                case 2:
                    return "disableSecureMode";
                case 3:
                    return "getSecureModeConfiguration";
                case 4:
                    return "isSecureModeEnabled";
                case 5:
                    return "enableDeviceWhiteList";
                case 6:
                    return "isDeviceWhiteListEnabled";
                case 7:
                    return "getBluetoothDevicesFromWhiteList";
                case 8:
                    return "addBluetoothDevicesToWhiteList";
                case 9:
                    return "removeBluetoothDevicesFromWhiteList";
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
                parcel.enforceInterface(IBluetoothSecureModePolicy.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IBluetoothSecureModePolicy.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ContextInfo contextInfo =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    BluetoothSecureModeConfig bluetoothSecureModeConfig =
                            (BluetoothSecureModeConfig)
                                    parcel.readTypedObject(BluetoothSecureModeConfig.CREATOR);
                    ArrayList createTypedArrayList =
                            parcel.createTypedArrayList(BluetoothSecureModeWhitelistConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean enableSecureMode =
                            enableSecureMode(
                                    contextInfo, bluetoothSecureModeConfig, createTypedArrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableSecureMode);
                    return true;
                case 2:
                    ContextInfo contextInfo2 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean disableSecureMode = disableSecureMode(contextInfo2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(disableSecureMode);
                    return true;
                case 3:
                    ContextInfo contextInfo3 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    BluetoothSecureModeConfig secureModeConfiguration =
                            getSecureModeConfiguration(contextInfo3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(secureModeConfiguration, 1);
                    return true;
                case 4:
                    ContextInfo contextInfo4 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isSecureModeEnabled = isSecureModeEnabled(contextInfo4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSecureModeEnabled);
                    return true;
                case 5:
                    ContextInfo contextInfo5 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean enableDeviceWhiteList =
                            enableDeviceWhiteList(contextInfo5, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableDeviceWhiteList);
                    return true;
                case 6:
                    ContextInfo contextInfo6 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isDeviceWhiteListEnabled = isDeviceWhiteListEnabled(contextInfo6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDeviceWhiteListEnabled);
                    return true;
                case 7:
                    ContextInfo contextInfo7 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<BluetoothSecureModeWhitelistConfig> bluetoothDevicesFromWhiteList =
                            getBluetoothDevicesFromWhiteList(contextInfo7);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(bluetoothDevicesFromWhiteList, 1);
                    return true;
                case 8:
                    ContextInfo contextInfo8 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList createTypedArrayList2 =
                            parcel.createTypedArrayList(BluetoothSecureModeWhitelistConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean addBluetoothDevicesToWhiteList =
                            addBluetoothDevicesToWhiteList(contextInfo8, createTypedArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addBluetoothDevicesToWhiteList);
                    return true;
                case 9:
                    ContextInfo contextInfo9 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList createTypedArrayList3 =
                            parcel.createTypedArrayList(BluetoothSecureModeWhitelistConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean removeBluetoothDevicesFromWhiteList =
                            removeBluetoothDevicesFromWhiteList(
                                    contextInfo9, createTypedArrayList3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeBluetoothDevicesFromWhiteList);
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
