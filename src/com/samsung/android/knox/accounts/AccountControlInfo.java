package com.samsung.android.knox.accounts;

import android.os.Parcel;
import android.os.Parcelable;

import com.samsung.android.knox.ControlInfo;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AccountControlInfo extends ControlInfo {
    public static final Parcelable.Creator<AccountControlInfo> CREATOR = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.accounts.AccountControlInfo$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<AccountControlInfo> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AccountControlInfo createFromParcel(Parcel parcel) {
            return new AccountControlInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AccountControlInfo[] newArray(int i) {
            return new AccountControlInfo[i];
        }
    }

    public AccountControlInfo() {}

    public AccountControlInfo(Parcel parcel) {
        readFromParcel(parcel);
    }
}
