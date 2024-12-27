package com.samsung.android.knox.license;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class LicenseResult implements Parcelable {
    public static final Parcelable.Creator<LicenseResult> CREATOR =
            new Parcelable.Creator<
                    LicenseResult>() { // from class:
                                       // com.samsung.android.knox.license.LicenseResult.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public LicenseResult createFromParcel(Parcel parcel) {
                    return new LicenseResult(0, parcel);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public LicenseResult[] newArray(int i) {
                    return new LicenseResult[i];
                }
            };
    private int errorCode;
    private ArrayList<String> grantedPermissions;
    private String licenseKey;
    private String packageName;
    private Status status;
    private Type type;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public enum Status {
        SUCCESS("success"),
        FAILURE(null);

        String value;

        Status(String str) {
            this.value = str;
        }

        public static Status fromStatusString(String str) {
            Status status = SUCCESS;
            return status.value.equals(str) ? status : FAILURE;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public enum Type {
        ELM_ACTIVATION(800),
        ELM_VALIDATION(801),
        ELM_DEACTIVATION(802),
        KLM_ACTIVATION(800),
        KLM_VALIDATION(801),
        KLM_DEACTIVATION(802),
        UNDEFINED(-100);

        int status;

        Type(int i) {
            this.status = i;
        }

        public static Type fromElmStatus(int i) {
            return (Type)
                    Arrays.stream(values())
                            .filter(new LicenseResult$Type$$ExternalSyntheticLambda0(i, 1))
                            .findFirst()
                            .orElse(UNDEFINED);
        }

        public static Type fromKlmStatus(int i) {
            return (Type)
                    Arrays.stream(values())
                            .filter(new LicenseResult$Type$$ExternalSyntheticLambda0(i, 0))
                            .findFirst()
                            .orElse(UNDEFINED);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$fromElmStatus$0(int i, Type type) {
            return type.status == i && type.name().startsWith("ELM");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$fromKlmStatus$1(int i, Type type) {
            return type.status == i && type.name().startsWith("KLM");
        }
    }

    public /* synthetic */ LicenseResult(int i, Parcel parcel) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public ArrayList<String> getGrantedPermissions() {
        return this.grantedPermissions;
    }

    public String getLicenseKey() {
        return this.licenseKey;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public Type getType() {
        return this.type;
    }

    public boolean isActivation() {
        Type type = this.type;
        return type == Type.ELM_ACTIVATION || type == Type.KLM_ACTIVATION;
    }

    public boolean isSuccess() {
        return this.status == Status.SUCCESS;
    }

    public void readFromParcel(Parcel parcel) {
        this.packageName = parcel.readString();
        this.status = Status.valueOf(parcel.readString());
        this.type = Type.valueOf(parcel.readString());
        this.errorCode = parcel.readInt();
        this.grantedPermissions = parcel.readArrayList(null);
        this.licenseKey = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.packageName);
        parcel.writeString(this.status.name());
        parcel.writeString(this.type.name());
        parcel.writeInt(this.errorCode);
        parcel.writeList(this.grantedPermissions);
        parcel.writeString(this.licenseKey);
    }

    private LicenseResult(Parcel parcel) {
        readFromParcel(parcel);
    }

    public LicenseResult(
            String str, String str2, int i, Type type, ArrayList<String> arrayList, String str3) {
        this(str, Status.fromStatusString(str2), i, type, arrayList, str3);
    }

    public LicenseResult(
            String str, Status status, int i, Type type, ArrayList<String> arrayList, String str2) {
        this.packageName = str;
        this.status = status;
        this.type = type;
        this.errorCode = i;
        this.grantedPermissions = arrayList;
        this.licenseKey = str2;
    }
}
