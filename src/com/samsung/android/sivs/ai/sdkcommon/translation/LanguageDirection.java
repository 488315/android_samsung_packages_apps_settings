package com.samsung.android.sivs.ai.sdkcommon.translation;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class LanguageDirection implements Parcelable {
    public static final Parcelable.Creator<LanguageDirection> CREATOR = new AnonymousClass1();
    private String sourceLanguage;
    private String targetLanguage;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.sivs.ai.sdkcommon.translation.LanguageDirection$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new LanguageDirection(0, parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new LanguageDirection[i];
        }
    }

    public /* synthetic */ LanguageDirection(int i, Parcel parcel) {
        this(parcel);
    }

    private void readFromParcel(Parcel parcel) {
        this.sourceLanguage = parcel.readString();
        this.targetLanguage = parcel.readString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        LanguageDirection languageDirection = (LanguageDirection) obj;
        return Objects.equals(this.sourceLanguage, languageDirection.sourceLanguage)
                && Objects.equals(this.targetLanguage, languageDirection.targetLanguage);
    }

    public String getSourceLanguage() {
        return this.sourceLanguage;
    }

    public String getTargetLanguage() {
        return this.targetLanguage;
    }

    public int hashCode() {
        return Objects.hash(this.sourceLanguage, this.targetLanguage);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.sourceLanguage);
        parcel.writeString(this.targetLanguage);
    }

    public LanguageDirection(String str, String str2) {
        this.sourceLanguage = str;
        this.targetLanguage = str2;
    }

    private LanguageDirection(Parcel parcel) {
        readFromParcel(parcel);
    }
}
