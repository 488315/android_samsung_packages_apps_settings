package com.google.android.setupcompat;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public interface ISetupCompatService extends IInterface {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class Stub extends Binder implements ISetupCompatService {
        public static final /* synthetic */ int $r8$clinit = 0;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class Proxy implements ISetupCompatService {
            public IBinder mRemote;

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            public final void logMetric(int i, Bundle bundle, Bundle bundle2) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(
                            "com.google.android.setupcompat.ISetupCompatService");
                    obtain.writeInt(i);
                    _Parcel.m1065$$Nest$smwriteTypedObject(obtain, bundle);
                    _Parcel.m1065$$Nest$smwriteTypedObject(obtain, bundle2);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public final void onFocusStatusChanged(Bundle bundle) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(
                            "com.google.android.setupcompat.ISetupCompatService");
                    _Parcel.m1065$$Nest$smwriteTypedObject(obtain, bundle);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public final void validateActivity(Bundle bundle, String str) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(
                            "com.google.android.setupcompat.ISetupCompatService");
                    obtain.writeString(str);
                    _Parcel.m1065$$Nest$smwriteTypedObject(obtain, bundle);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class _Parcel {
        /* renamed from: -$$Nest$smwriteTypedObject, reason: not valid java name */
        public static void m1065$$Nest$smwriteTypedObject(Parcel parcel, Parcelable parcelable) {
            if (parcelable == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                parcelable.writeToParcel(parcel, 0);
            }
        }
    }
}
