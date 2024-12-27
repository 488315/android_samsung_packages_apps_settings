package com.samsung.android.knox.custom;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.sec.ims.settings.ImsSettings;

import java.io.ByteArrayOutputStream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class PowerItem implements Parcelable {
    public static final int ACTION_SEND_BROADCAST = 1;
    public static final int ACTION_SEND_STICKY_BROADCAST = 2;
    public static final int ACTION_START_ACTIVITY = 4;
    public static final Parcelable.Creator<PowerItem> CREATOR = new AnonymousClass1();
    public BitmapDrawable mIcon;
    public int mId;
    public Intent mIntent;
    public int mIntentAction;
    public String mText;
    public final String TAG = "PowerItem";
    public final String mId_KEY = "ID";
    public final String mIcon_KEY = "NAME";
    public final String mIntent_KEY = "INTENT";
    public final String mIntentAction_KEY = "INTENT_ACTION";
    public final String mText_KEY = ImsSettings.TYPE_TEXT;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.custom.PowerItem$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<PowerItem> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PowerItem createFromParcel(Parcel parcel) {
            return new PowerItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PowerItem[] newArray(int i) {
            return new PowerItem[i];
        }
    }

    public PowerItem(int i, BitmapDrawable bitmapDrawable, Intent intent, int i2, String str) {
        this.mId = i;
        this.mIcon = bitmapDrawable;
        this.mIntent = intent;
        this.mIntentAction = i2;
        this.mText = str;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BitmapDrawable getIcon() {
        return this.mIcon;
    }

    public int getId() {
        return this.mId;
    }

    public Intent getIntent() {
        return this.mIntent;
    }

    public int getIntentAction() {
        return this.mIntentAction;
    }

    public String getText() {
        return this.mText;
    }

    public String toString() {
        return "descr:"
                + describeContents()
                + " id:"
                + this.mId
                + " icon:"
                + this.mIcon.hashCode()
                + " intent:"
                + this.mIntent.toString()
                + " intentAction:"
                + this.mIntentAction
                + " text:"
                + this.mText;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("intent", this.mIntent);
        parcel.writeBundle(bundle);
        parcel.writeInt(this.mIntentAction);
        parcel.writeInt(this.mId);
        parcel.writeString(this.mText);
        Bitmap bitmap = this.mIcon.getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        parcel.writeInt(byteArray.length);
        parcel.writeByteArray(byteArray);
    }

    public PowerItem(Parcel parcel) {
        this.mIntent = (Intent) parcel.readBundle().getParcelable("intent");
        this.mIntentAction = parcel.readInt();
        this.mId = parcel.readInt();
        this.mText = parcel.readString();
        int readInt = parcel.readInt();
        byte[] bArr = new byte[readInt];
        parcel.readByteArray(bArr);
        this.mIcon = new BitmapDrawable(BitmapFactory.decodeByteArray(bArr, 0, readInt));
    }
}
