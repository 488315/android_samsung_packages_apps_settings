package com.samsung.android.knox.license;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ActivationInfo implements Parcelable {
    public static final Parcelable.Creator<ActivationInfo> CREATOR =
            new Parcelable.Creator<
                    ActivationInfo>() { // from class:
                                        // com.samsung.android.knox.license.ActivationInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ActivationInfo createFromParcel(Parcel parcel) {
                    return new ActivationInfo(0, parcel);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ActivationInfo[] newArray(int i) {
                    return new ActivationInfo[i];
                }
            };
    private Date activationDate;
    private String maskedLicenseKey;
    private String packageName;
    private String productType;
    private String sku;
    private State state;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public enum State {
        ACTIVE,
        EXPIRED,
        TERMINATED;

        public static State fromInt(int i) {
            if (i == 1) {
                return ACTIVE;
            }
            if (i == 3) {
                return EXPIRED;
            }
            if (i != 4) {
                return null;
            }
            return TERMINATED;
        }
    }

    public /* synthetic */ ActivationInfo(int i, Parcel parcel) {
        this(parcel);
    }

    public static ActivationInfo fromCursor(Cursor cursor) {
        ActivationInfo activationInfo = new ActivationInfo();
        activationInfo.packageName =
                cursor.getString(cursor.getColumnIndex(LicenseAgentDbContract.COLUMN_PACKAGE_NAME));
        activationInfo.state =
                State.fromInt(
                        cursor.getInt(
                                cursor.getColumnIndex(
                                        LicenseAgentDbContract.COLUMN_LICENSE_STATUS)));
        activationInfo.maskedLicenseKey =
                cursor.getString(cursor.getColumnIndex(LicenseAgentDbContract.COLUMN_LICENSE_KEY));
        activationInfo.sku =
                cursor.getString(cursor.getColumnIndex(LicenseAgentDbContract.COLUMN_SKU));
        activationInfo.productType =
                cursor.getString(cursor.getColumnIndex(LicenseAgentDbContract.COLUMN_PRODUCT_TYPE));
        try {
            activationInfo.activationDate =
                    new Date(
                            Long.valueOf(
                                            cursor.getString(
                                                    cursor.getColumnIndex(
                                                            LicenseAgentDbContract
                                                                    .COLUMN_ACTIVATION_TS)))
                                    .longValue());
        } catch (NumberFormatException unused) {
            activationInfo.activationDate = null;
        }
        return activationInfo;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Date getActivationDate() {
        return this.activationDate;
    }

    public String getMaskedLicenseKey() {
        return this.maskedLicenseKey;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public String getProductType() {
        return this.productType;
    }

    public String getSku() {
        return this.sku;
    }

    public State getState() {
        return this.state;
    }

    public void readFromParcel(Parcel parcel) {
        this.packageName = parcel.readString();
        String readString = parcel.readString();
        if (readString != null) {
            this.state = State.valueOf(readString);
        }
        this.maskedLicenseKey = parcel.readString();
        this.sku = parcel.readString();
        this.productType = parcel.readString();
        long readLong = parcel.readLong();
        this.activationDate = readLong == -1 ? null : new Date(readLong);
    }

    public void setActivationDate(Date date) {
        this.activationDate = date;
    }

    public void setMaskedLicenseKey(String str) {
        this.maskedLicenseKey = str;
    }

    public void setPackageName(String str) {
        this.packageName = str;
    }

    public void setProductType(String str) {
        this.productType = str;
    }

    public void setSku(String str) {
        this.sku = str;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.packageName);
        State state = this.state;
        parcel.writeString(state != null ? state.name() : null);
        parcel.writeString(this.maskedLicenseKey);
        parcel.writeString(this.sku);
        parcel.writeString(this.productType);
        Date date = this.activationDate;
        parcel.writeLong(date != null ? date.getTime() : -1L);
    }

    public ActivationInfo() {}

    private ActivationInfo(Parcel parcel) {
        readFromParcel(parcel);
    }
}
