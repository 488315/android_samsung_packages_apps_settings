package com.samsung.android.lib.episode;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class Scene implements Parcelable {
    public static final Parcelable.Creator<Scene> CREATOR = new AnonymousClass1();
    public byte mDefaultType;
    public Boolean mIsDefault;
    public String mSceneKey;
    public Bundle mSceneValue;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.lib.episode.Scene$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            Scene scene = new Scene();
            scene.mSceneKey = parcel.readString();
            scene.mSceneValue = parcel.readBundle();
            byte readByte = parcel.readByte();
            scene.mDefaultType = readByte;
            scene.mIsDefault = Boolean.valueOf(readByte > 0);
            return scene;
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new Scene[i];
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder {
        public final Bundle mBundle = new Bundle();
        public final ArrayList mCompressedAttributes = new ArrayList();
        public byte mDefaultType;
        public Boolean mIsDefault;
        public final String mKey;

        public Builder(String str) {
            this.mKey = str;
        }

        public final void addAttribute(Object obj, String str) {
            String valueOf = String.valueOf(obj);
            if (this.mBundle.containsKey(str)) {
                Log.e(
                        "Eternal/Scene.Builder",
                        "the value of the attribute (" + str + ") will be replaced with a new one");
                Log.e(
                        "Eternal/Scene.Builder",
                        "old : " + this.mBundle.getString(str) + ", new : " + obj);
            }
            this.mBundle.putString(str, valueOf);
        }

        public final Scene build() {
            ArrayList arrayList = this.mCompressedAttributes;
            if (arrayList != null && !arrayList.isEmpty()) {
                this.mBundle.putString(
                        "compressedEternalItems",
                        EpisodeUtils.convertListToString(this.mCompressedAttributes));
            }
            Bundle bundle = this.mBundle;
            if (bundle != null && !bundle.isEmpty()) {
                String str = this.mKey;
                if (!TextUtils.isEmpty(str)) {
                    Scene scene = new Scene();
                    scene.mSceneKey = str;
                    scene.mSceneValue = this.mBundle;
                    scene.mIsDefault = this.mIsDefault;
                    scene.mDefaultType = this.mDefaultType;
                    return scene;
                }
            }
            return null;
        }

        public final void setDefault(boolean z) {
            this.mIsDefault = Boolean.valueOf(z);
            if (z) {
                this.mDefaultType = (byte) 1;
            }
        }

        public final void setValue(Object obj) {
            setValue(obj, false);
        }

        public final void setValue(Object obj, boolean z) {
            String valueOf = String.valueOf(obj);
            if (this.mBundle.containsKey("value")) {
                Log.e("Eternal/Scene.Builder", "the element value will be replaced with a new one");
                Log.e(
                        "Eternal/Scene.Builder",
                        "old : " + this.mBundle.getString("value") + ", new : " + obj);
            }
            if (z) {
                boolean z2 = obj instanceof String;
                String str = this.mKey;
                if (z2) {
                    String compressString = EpisodeUtils.compressString(valueOf);
                    if (compressString != null) {
                        this.mCompressedAttributes.add("value");
                        valueOf = compressString;
                    } else {
                        Log.e(
                                "Eternal/Scene.Builder",
                                str + " : Compress failed / compressString() failed");
                    }
                } else {
                    Log.e(
                            "Eternal/Scene.Builder",
                            str + " : Compress failed / instance is not String type");
                }
            }
            this.mBundle.putString("value", valueOf);
        }

        public Builder(Scene scene) {
            this.mKey = scene.mSceneKey;
            Bundle bundle = scene.mSceneValue;
            if (bundle != null) {
                for (String str : bundle.keySet()) {
                    this.mBundle.putString(str, scene.mSceneValue.getString(str));
                }
            }
            this.mIsDefault = Boolean.valueOf(scene.isDefault());
            this.mDefaultType = scene.mDefaultType;
        }
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final String getAttribute(String str) {
        Bundle bundle = this.mSceneValue;
        if (bundle == null || !bundle.containsKey(str)) {
            return null;
        }
        return this.mSceneValue.getString(str);
    }

    public final boolean getAttributeBoolean(String str) {
        Bundle bundle = this.mSceneValue;
        if (bundle == null || !bundle.containsKey(str)) {
            return false;
        }
        String string = this.mSceneValue.getString(str);
        if (TextUtils.isEmpty(string)) {
            return false;
        }
        return Boolean.valueOf(string).booleanValue();
    }

    public final int getAttributeInt(int i, String str) {
        Bundle bundle = this.mSceneValue;
        if (bundle != null && bundle.containsKey(str)) {
            String string = this.mSceneValue.getString(str);
            try {
                if (!TextUtils.isEmpty(string)) {
                    return Integer.valueOf(string).intValue();
                }
            } catch (NumberFormatException e) {
                Log.e("Eternal/Scene", e.getStackTrace()[0].toString());
            }
        }
        return i;
    }

    public final String getValue(String str, boolean z) {
        Bundle bundle = this.mSceneValue;
        if (bundle == null || !bundle.containsKey("value")) {
            return str;
        }
        String string = this.mSceneValue.getString("value");
        if (!z) {
            return string;
        }
        ArrayList arrayList = new ArrayList();
        Bundle bundle2 = this.mSceneValue;
        if (bundle2 != null && bundle2.containsKey("compressedEternalItems")) {
            arrayList.addAll(
                    EpisodeUtils.convertStringToArrayList(
                            this.mSceneValue.getString("compressedEternalItems")));
        }
        if (!arrayList.contains("value")) {
            Log.e(
                    "Eternal/Scene",
                    this.mSceneKey + " : Decompress failed / not in compressed attribute");
            return string;
        }
        String decompressString = EpisodeUtils.decompressString(string);
        if (decompressString != null) {
            return decompressString;
        }
        Log.e("Eternal/Scene", this.mSceneKey + " : Decompress failed / decompressString() failed");
        return str;
    }

    public final boolean isDefault() {
        Boolean bool = this.mIsDefault;
        return bool != null && bool.booleanValue();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mSceneKey);
        parcel.writeBundle(this.mSceneValue);
        parcel.writeByte(isDefault() ? this.mDefaultType : (byte) 0);
    }
}
