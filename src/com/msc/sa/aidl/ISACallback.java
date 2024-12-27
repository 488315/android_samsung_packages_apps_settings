package com.msc.sa.aidl;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public interface ISACallback extends IInterface {
    void onReceiveAccessToken(int i, boolean z, Bundle bundle);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class Stub extends Binder implements ISACallback {
        private static final String DESCRIPTOR = "com.msc.sa.aidl.ISACallback";
        static final int TRANSACTION_onReceiveAccessToken = 1;
        static final int TRANSACTION_onReceiveAuthCode = 4;
        static final int TRANSACTION_onReceiveChecklistValidation = 2;
        static final int TRANSACTION_onReceiveDisclaimerAgreement = 3;
        static final int TRANSACTION_onReceivePasswordConfirmation = 6;
        static final int TRANSACTION_onReceiveRLControlFMM = 7;
        static final int TRANSACTION_onReceiveRubinRequest = 8;
        static final int TRANSACTION_onReceiveSCloudAccessToken = 5;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    parcel.enforceInterface(DESCRIPTOR);
                    onReceiveAccessToken(
                            parcel.readInt(),
                            parcel.readInt() != 0,
                            parcel.readInt() != 0
                                    ? (Bundle) Bundle.CREATOR.createFromParcel(parcel)
                                    : null);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    parcel.enforceInterface(DESCRIPTOR);
                    parcel.readInt();
                    parcel.readInt();
                    if (parcel.readInt() != 0) {}
                    parcel2.writeNoException();
                    return true;
                case 3:
                    parcel.enforceInterface(DESCRIPTOR);
                    parcel.readInt();
                    parcel.readInt();
                    if (parcel.readInt() != 0) {}
                    parcel2.writeNoException();
                    return true;
                case 4:
                    parcel.enforceInterface(DESCRIPTOR);
                    parcel.readInt();
                    parcel.readInt();
                    if (parcel.readInt() != 0) {}
                    parcel2.writeNoException();
                    return true;
                case 5:
                    parcel.enforceInterface(DESCRIPTOR);
                    parcel.readInt();
                    parcel.readInt();
                    if (parcel.readInt() != 0) {}
                    parcel2.writeNoException();
                    return true;
                case 6:
                    parcel.enforceInterface(DESCRIPTOR);
                    parcel.readInt();
                    parcel.readInt();
                    if (parcel.readInt() != 0) {}
                    parcel2.writeNoException();
                    return true;
                case 7:
                    parcel.enforceInterface(DESCRIPTOR);
                    parcel.readInt();
                    parcel.readInt();
                    if (parcel.readInt() != 0) {}
                    parcel2.writeNoException();
                    return true;
                case 8:
                    parcel.enforceInterface(DESCRIPTOR);
                    parcel.readInt();
                    parcel.readInt();
                    if (parcel.readInt() != 0) {}
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
