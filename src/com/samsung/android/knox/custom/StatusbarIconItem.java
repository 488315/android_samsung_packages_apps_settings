package com.samsung.android.knox.custom;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class StatusbarIconItem implements Parcelable {
    public static final Parcelable.Creator<StatusbarIconItem> CREATOR = new AnonymousClass1();
    public static final int STATUSBAR_ICON_BATTERY_BARS = 2;
    public static final int STATUSBAR_ICON_BATTERY_TEXT = 3;
    public static final int STATUSBAR_ICON_CLOCK_TEXT = 1;
    public static final int STATUSBAR_ICON_MOBILE_BARS = 4;
    public static final int STATUSBAR_ICON_SMART_STAY = 6;
    public static final int STATUSBAR_ICON_WIFI_BARS = 5;
    public AttributeColour[] mAttributeColour;
    public int mIcon;
    public final String TAG = "StatusbarIconItem";
    public final String mIcon_KEY = "ICON";
    public final String mAttributeColour_KEY = "ATTRIBUTE_COLOUR";

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.custom.StatusbarIconItem$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<StatusbarIconItem> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StatusbarIconItem createFromParcel(Parcel parcel) {
            return new StatusbarIconItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StatusbarIconItem[] newArray(int i) {
            return new StatusbarIconItem[i];
        }
    }

    public StatusbarIconItem(int i, AttributeColour[] attributeColourArr) {
        this.mIcon = i;
        this.mAttributeColour = attributeColourArr;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AttributeColour getAttributeColour(int i) {
        AttributeColour[] attributeColourArr = this.mAttributeColour;
        if (attributeColourArr == null || attributeColourArr.length <= i) {
            return null;
        }
        return attributeColourArr[i];
    }

    public AttributeColour[] getAttributeColourArray() {
        return this.mAttributeColour;
    }

    public int getIcon() {
        return this.mIcon;
    }

    public void setAttributeColour(int i, int i2, int i3) {
        AttributeColour[] attributeColourArr = this.mAttributeColour;
        if (attributeColourArr == null || attributeColourArr.length <= i) {
            return;
        }
        attributeColourArr[i] = new AttributeColour(i2, i3);
    }

    public String toString() {
        return "descr:"
                + describeContents()
                + " icon:"
                + this.mIcon
                + " attributeColour:"
                + Arrays.toString(this.mAttributeColour);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mIcon);
        AttributeColour[] attributeColourArr = this.mAttributeColour;
        int length = attributeColourArr != null ? attributeColourArr.length : 0;
        parcel.writeInt(length);
        if (length > 0) {
            for (AttributeColour attributeColour : this.mAttributeColour) {
                parcel.writeInt(attributeColour.getAttribute());
                parcel.writeInt(attributeColour.getColour());
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class AttributeColour {
        public int mAttribute;
        public int mColour;

        public AttributeColour() {
            this.mAttribute = 0;
            this.mColour = 0;
        }

        public int getAttribute() {
            return this.mAttribute;
        }

        public int getColour() {
            return this.mColour;
        }

        public void setAttributeColour(int i, int i2) {
            this.mAttribute = i;
            this.mColour = i2;
        }

        public AttributeColour(int i, int i2) {
            this.mAttribute = i;
            this.mColour = i2;
        }
    }

    public StatusbarIconItem(Parcel parcel) {
        this.mIcon = parcel.readInt();
        int readInt = parcel.readInt();
        this.mAttributeColour = null;
        if (readInt > 0) {
            this.mAttributeColour = new AttributeColour[readInt];
            for (int i = 0; i < readInt; i++) {
                this.mAttributeColour[i] = new AttributeColour(parcel.readInt(), parcel.readInt());
            }
        }
    }
}
