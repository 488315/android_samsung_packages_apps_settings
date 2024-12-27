package com.samsung.android.nfc.adapter;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SamsungNfcAdapter {
    public static final HashMap mAdapter = new HashMap();
    public static ISamsungNfcAdapter sService;

    public static void attemptDeadServiceRecovery(Exception exc) {
        Log.e("SamsungNfcAdapter", "Nfc Samsung service dead - attempting to recover", exc);
        ISamsungNfcAdapter serviceInterface = getServiceInterface();
        if (serviceInterface == null) {
            Log.e(
                    "SamsungNfcAdapter",
                    "Could not retrieve Nfc Samsung service during service recovery");
        } else {
            sService = serviceInterface;
        }
    }

    public static List getDefaultRoutingDestination() {
        try {
            ISamsungNfcAdapter.Stub.Proxy proxy = (ISamsungNfcAdapter.Stub.Proxy) sService;
            Parcel obtain = Parcel.obtain(proxy.mRemote);
            Parcel obtain2 = Parcel.obtain();
            try {
                obtain.writeInterfaceToken("com.samsung.android.nfc.adapter.ISamsungNfcAdapter");
                proxy.mRemote.transact(13, obtain, obtain2, 0);
                obtain2.readException();
                return obtain2.createStringArrayList();
            } finally {
                obtain2.recycle();
                obtain.recycle();
            }
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
            return null;
        }
    }

    public static ISamsungNfcAdapter getServiceInterface() {
        IBinder service = ServiceManager.getService("samsungnfc");
        if (service == null) {
            return null;
        }
        int i = ISamsungNfcAdapter.Stub.$r8$clinit;
        IInterface queryLocalInterface =
                service.queryLocalInterface("com.samsung.android.nfc.adapter.ISamsungNfcAdapter");
        if (queryLocalInterface != null && (queryLocalInterface instanceof ISamsungNfcAdapter)) {
            return (ISamsungNfcAdapter) queryLocalInterface;
        }
        ISamsungNfcAdapter.Stub.Proxy proxy = new ISamsungNfcAdapter.Stub.Proxy();
        proxy.mRemote = service;
        return proxy;
    }

    public static boolean isAutoChangeEnabled() {
        try {
            ISamsungNfcAdapter.Stub.Proxy proxy = (ISamsungNfcAdapter.Stub.Proxy) sService;
            Parcel obtain = Parcel.obtain(proxy.mRemote);
            Parcel obtain2 = Parcel.obtain();
            try {
                obtain.writeInterfaceToken("com.samsung.android.nfc.adapter.ISamsungNfcAdapter");
                proxy.mRemote.transact(15, obtain, obtain2, 0);
                obtain2.readException();
                return obtain2.readBoolean();
            } finally {
                obtain2.recycle();
                obtain.recycle();
            }
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
            return false;
        }
    }

    public static void setDefaultRoutingDestination(String str, String str2, String str3) {
        try {
            ((ISamsungNfcAdapter.Stub.Proxy) sService)
                    .setDefaultRoutingDestination(str, str2, str3);
        } catch (RemoteException e) {
            attemptDeadServiceRecovery(e);
        }
    }
}
