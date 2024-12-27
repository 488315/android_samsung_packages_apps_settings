package com.samsung.android.knox.custom;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class WidgetItem implements Parcelable {
    public static final Parcelable.Creator<WidgetItem> CREATOR = new AnonymousClass1();
    public int mCellX;
    public int mCellY;
    public Intent mIntent;
    public int mMoreItems;
    public String mParent;
    public int mSizeX;
    public int mSizeY;
    public int mWidgetId;
    public int mWidgetType;
    public final String TAG = "WidgetItem";
    public final String mmWidgetType_KEY = "WIDGET";
    public final String mIntent_KEY = "INTENT";
    public final String mWidgetId_KEY = "ID";
    public final String mParent_KEY = "PARENT";
    public final String mCellX_KEY = "CELLX";
    public final String mCellY_KEY = "CELLY";
    public final String mSizeX_KEY = "SIZEX";
    public final String mSizeY_KEY = "SIZEY";
    public final String mMoreItems_KEY = "MORE";

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.custom.WidgetItem$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<WidgetItem> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WidgetItem createFromParcel(Parcel parcel) {
            return new WidgetItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WidgetItem[] newArray(int i) {
            return new WidgetItem[i];
        }
    }

    public WidgetItem(
            int i, Intent intent, int i2, String str, int i3, int i4, int i5, int i6, int i7) {
        this.mWidgetType = i;
        this.mIntent = intent;
        this.mWidgetId = i2;
        this.mParent = str;
        this.mCellX = i3;
        this.mCellY = i4;
        this.mSizeX = i5;
        this.mSizeY = i6;
        this.mMoreItems = i7;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getCellX() {
        return this.mCellX;
    }

    public int getCellY() {
        return this.mCellY;
    }

    public Intent getIntent() {
        return this.mIntent;
    }

    public int getMoreItems() {
        return this.mMoreItems;
    }

    public String getParent() {
        return this.mParent;
    }

    public int getSizeX() {
        return this.mSizeX;
    }

    public int getSizeY() {
        return this.mSizeY;
    }

    public int getWidgetId() {
        return this.mWidgetId;
    }

    public int getWidgetType() {
        return this.mWidgetType;
    }

    public String toString() {
        return "descr:"
                + describeContents()
                + " widgetType:"
                + this.mWidgetType
                + " parent:"
                + this.mParent
                + " intent:"
                + this.mIntent;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("intent", this.mIntent);
        parcel.writeBundle(bundle);
        parcel.writeInt(this.mWidgetType);
        parcel.writeInt(this.mWidgetId);
        parcel.writeString(this.mParent);
        parcel.writeInt(this.mCellX);
        parcel.writeInt(this.mCellY);
        parcel.writeInt(this.mSizeX);
        parcel.writeInt(this.mSizeY);
        parcel.writeInt(this.mMoreItems);
    }

    public WidgetItem(Parcel parcel) {
        this.mIntent = (Intent) parcel.readBundle().getParcelable("intent");
        this.mWidgetType = parcel.readInt();
        this.mWidgetId = parcel.readInt();
        this.mParent = parcel.readString();
        this.mCellX = parcel.readInt();
        this.mCellY = parcel.readInt();
        this.mSizeX = parcel.readInt();
        this.mSizeY = parcel.readInt();
        this.mMoreItems = parcel.readInt();
    }
}
