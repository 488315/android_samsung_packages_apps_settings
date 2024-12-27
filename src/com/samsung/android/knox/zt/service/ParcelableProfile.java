package com.samsung.android.knox.zt.service;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ParcelableProfile implements Parcelable {
    public static final Parcelable.Creator<ParcelableProfile> CREATOR = new AnonymousClass1();
    public String mChallengePassword;
    public int mClientIdentifierType;
    public List<String> mClientIdentifiers;
    public String mKeyAlias;
    public List<String> mKeyExtendedPurposes;
    public int mKeyOwner;
    public String mKeyProvider;
    public String mProtocol;
    public String mProvisionType;
    public String mRootCA;
    public String mServerHost;
    public String mServerPath;
    public String mServerPort;
    public Bundle mSubject;
    public Bundle mSubjectAltName;
    public int mSystemKeyPurposes;
    public int mSystemKeySize;
    public String mSystemKeyType;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.zt.service.ParcelableProfile$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<ParcelableProfile> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelableProfile createFromParcel(Parcel parcel) {
            return new ParcelableProfile(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelableProfile[] newArray(int i) {
            return new ParcelableProfile[i];
        }
    }

    public ParcelableProfile(
            String str,
            String str2,
            String str3,
            String str4,
            int i,
            String str5,
            Bundle bundle,
            String str6,
            String str7,
            String str8,
            Bundle bundle2,
            List<String> list,
            String str9,
            int i2,
            List<String> list2,
            String str10,
            int i3,
            int i4) {
        this.mKeyExtendedPurposes = new ArrayList();
        this.mClientIdentifiers = new ArrayList();
        this.mRootCA = nonEmptyOf(str);
        this.mProtocol = nonEmptyOf(str2);
        this.mProvisionType = nonEmptyOf(str3);
        this.mKeyProvider = nonEmptyOf(str4);
        this.mKeyOwner = i;
        this.mKeyAlias = nonEmptyOf(str5);
        this.mSubject = nonEmptyOf(bundle);
        this.mServerHost = nonEmptyOf(str6);
        this.mServerPort = nonEmptyOf(str7);
        this.mServerPath = nonEmptyOf(str8);
        this.mSubjectAltName = nonEmptyOf(bundle2);
        this.mKeyExtendedPurposes = nonEmptyOf(list);
        this.mChallengePassword = nonEmptyOf(str9);
        this.mClientIdentifierType = i2;
        this.mClientIdentifiers = nonEmptyOf(list2);
        this.mSystemKeyType = nonEmptyOf(str10);
        this.mSystemKeyPurposes = i3;
        this.mSystemKeySize = i4;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getChallengePassword() {
        return this.mChallengePassword;
    }

    public int getClientIdentifierType() {
        return this.mClientIdentifierType;
    }

    public List<String> getClientIdentifiers() {
        return this.mClientIdentifiers;
    }

    public String getKeyAlias() {
        return this.mKeyAlias;
    }

    public List<String> getKeyExtendedPurposes() {
        return this.mKeyExtendedPurposes;
    }

    public int getKeyOwner() {
        return this.mKeyOwner;
    }

    public String getKeyProvider() {
        return this.mKeyProvider;
    }

    public String getProtocol() {
        return this.mProtocol;
    }

    public String getProvisionType() {
        return this.mProvisionType;
    }

    public String getRootCA() {
        return this.mRootCA;
    }

    public String getServerHost() {
        return this.mServerHost;
    }

    public String getServerPath() {
        return this.mServerPath;
    }

    public String getServerPort() {
        return this.mServerPort;
    }

    public Bundle getSubject() {
        return this.mSubject;
    }

    public Bundle getSubjectAltName() {
        return this.mSubjectAltName;
    }

    public int getSystemKeyPurposes() {
        return this.mSystemKeyPurposes;
    }

    public int getSystemKeySize() {
        return this.mSystemKeySize;
    }

    public String getSystemKeyType() {
        return this.mSystemKeyType;
    }

    public final String nonEmptyOf(String str) {
        return TextUtils.isEmpty(str) ? ApnSettings.MVNO_NONE : str;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mRootCA);
        parcel.writeString(this.mProtocol);
        parcel.writeString(this.mProvisionType);
        parcel.writeString(this.mKeyProvider);
        parcel.writeInt(this.mKeyOwner);
        parcel.writeString(this.mKeyAlias);
        parcel.writeBundle(this.mSubject);
        parcel.writeString(this.mServerHost);
        parcel.writeString(this.mServerPort);
        parcel.writeString(this.mServerPath);
        parcel.writeBundle(this.mSubjectAltName);
        parcel.writeStringList(this.mKeyExtendedPurposes);
        parcel.writeString(this.mChallengePassword);
        parcel.writeInt(this.mClientIdentifierType);
        parcel.writeStringList(this.mClientIdentifiers);
        parcel.writeString(this.mSystemKeyType);
        parcel.writeInt(this.mSystemKeyPurposes);
        parcel.writeInt(this.mSystemKeySize);
    }

    public final <T> List<T> nonEmptyOf(List<T> list) {
        return list == null ? new ArrayList() : list;
    }

    public final Bundle nonEmptyOf(Bundle bundle) {
        return bundle == null ? new Bundle() : bundle;
    }

    public ParcelableProfile(Parcel parcel) {
        this.mKeyExtendedPurposes = new ArrayList();
        this.mClientIdentifiers = new ArrayList();
        this.mRootCA = parcel.readString();
        this.mProtocol = parcel.readString();
        this.mProvisionType = parcel.readString();
        this.mKeyProvider = parcel.readString();
        this.mKeyOwner = parcel.readInt();
        this.mKeyAlias = parcel.readString();
        this.mSubject = parcel.readBundle(getClass().getClassLoader());
        this.mServerHost = parcel.readString();
        this.mServerPort = parcel.readString();
        this.mServerPath = parcel.readString();
        this.mSubjectAltName = parcel.readBundle(getClass().getClassLoader());
        parcel.readStringList(this.mKeyExtendedPurposes);
        this.mChallengePassword = parcel.readString();
        this.mClientIdentifierType = parcel.readInt();
        parcel.readStringList(this.mClientIdentifiers);
        this.mSystemKeyType = parcel.readString();
        this.mSystemKeyPurposes = parcel.readInt();
        this.mSystemKeySize = parcel.readInt();
    }
}
