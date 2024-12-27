package com.sec.ims;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class DialogEvent implements Parcelable {
    public static final Parcelable.Creator<DialogEvent> CREATOR =
            new Parcelable.Creator<DialogEvent>() { // from class: com.sec.ims.DialogEvent.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public DialogEvent createFromParcel(Parcel parcel) {
                    return new DialogEvent(0, parcel);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public DialogEvent[] newArray(int i) {
                    return new DialogEvent[i];
                }
            };
    private List<Dialog> mDialogList;
    private String mMSISDN;
    private int mPhoneId;
    private int mRegId;

    public /* synthetic */ DialogEvent(int i, Parcel parcel) {
        this(parcel);
    }

    public void clearDialogList() {
        this.mDialogList.clear();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public List<Dialog> getDialogList() {
        return Collections.unmodifiableList(this.mDialogList);
    }

    public String getMsisdn() {
        return this.mMSISDN;
    }

    public int getPhoneId() {
        return this.mPhoneId;
    }

    public int getRegId() {
        return this.mRegId;
    }

    public void setPhoneId(int i) {
        this.mPhoneId = i;
    }

    public void setRegId(int i) {
        this.mRegId = i;
    }

    public String toString() {
        return "DialogEvent#"
                + this.mPhoneId
                + " [mMSISDN="
                + this.mMSISDN
                + ", mDialogList="
                + this.mDialogList
                + "]";
    }

    public String toXmlString() {
        String m =
                ComponentActivity$1$$ExternalSyntheticOutline0.m(
                        new StringBuilder(
                                "<?xml version=\"1.0\"?>\n"
                                    + "\t<dialog-info"
                                    + " xmlns=\"urn:ietf:params:xml:ns:dialog-info\"\n"
                                    + "\t\tversion=\"0\" state=\"full\" entity=\""),
                        this.mMSISDN,
                        "\">\n");
        for (Dialog dialog : this.mDialogList) {
            StringBuilder m2 =
                    EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(m);
            m2.append(dialog.toXmlString());
            m = m2.toString();
        }
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(m, "</dialog-info>");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (parcel == null) {
            return;
        }
        parcel.writeTypedList(this.mDialogList);
        parcel.writeString(this.mMSISDN);
        parcel.writeInt(this.mRegId);
        parcel.writeInt(this.mPhoneId);
    }

    public DialogEvent(String str, List<Dialog> list) {
        this.mMSISDN = str;
        this.mDialogList = list;
    }

    private DialogEvent(Parcel parcel) {
        ArrayList arrayList = new ArrayList();
        this.mDialogList = arrayList;
        parcel.readTypedList(arrayList, Dialog.CREATOR);
        this.mMSISDN = parcel.readString();
        this.mRegId = parcel.readInt();
        this.mPhoneId = parcel.readInt();
    }
}
