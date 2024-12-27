package com.att.iqi.lib;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import androidx.annotation.Keep;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class Metric implements Parcelable {
    public final String mClassName;

    @Keep private final int mMetricId;
    public static final Map sLoadedClasses = new HashMap();

    @Keep public static final Parcelable.Creator<Metric> CREATOR = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.att.iqi.lib.Metric$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<Metric> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Metric createFromParcel(Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            if (parcel.readInt() == 1) {
                throw new IllegalArgumentException("API 1 not supported");
            }
            parcel.readInt();
            String readString = parcel.readString();
            String packageName = Metric.class.getPackageName();
            if (readString == null || !readString.startsWith(packageName)) {
                throw new IllegalArgumentException("Invalid or null package name found");
            }
            parcel.setDataPosition(dataPosition);
            try {
                Map map = Metric.sLoadedClasses;
                Constructor<?> constructor = (Constructor) ((HashMap) map).get(readString);
                if (constructor == null) {
                    constructor = Class.forName(readString).getDeclaredConstructor(Parcel.class);
                    constructor.setAccessible(true);
                    ((HashMap) map).put(readString, constructor);
                }
                return (Metric) constructor.newInstance(parcel);
            } catch (Exception unused) {
                return new AnonymousClass2();
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Metric[] newArray(int i) {
            return new Metric[i];
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.att.iqi.lib.Metric$2, reason: invalid class name */
    class AnonymousClass2 extends Metric {
        @Override // com.att.iqi.lib.Metric
        public int serialize(ByteBuffer byteBuffer) throws BufferOverflowException {
            return 0;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static final class ID implements Parcelable {
        public final int mID;
        public final String mStringID;
        public static final Pattern sPattern = Pattern.compile("[A-Z0-9_]{4}");
        public static final Parcelable.Creator<ID> CREATOR = new AnonymousClass1();

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.att.iqi.lib.Metric$ID$1, reason: invalid class name */
        public class AnonymousClass1 implements Parcelable.Creator<ID> {
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ID createFromParcel(Parcel parcel) {
                return new ID(0, parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ID[] newArray(int i) {
                return new ID[i];
            }
        }

        public /* synthetic */ ID(int i, Parcel parcel) {
            this(parcel);
        }

        public int asInt() {
            return this.mID;
        }

        public String asString() {
            return this.mStringID;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object obj) {
            if (obj == null || ID.class != obj.getClass()) {
                return false;
            }
            ID id = (ID) obj;
            return id.mID == this.mID && TextUtils.equals(id.mStringID, this.mStringID);
        }

        public int hashCode() {
            String str = this.mStringID;
            return 3349 + (str != null ? str.hashCode() : 0);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(14);
            parcel.writeInt(this.mID);
            parcel.writeString(this.mStringID);
        }

        public ID(String str) {
            if (!sPattern.matcher(str).matches()) {
                throw new IllegalArgumentException("Invalid Metric ID");
            }
            this.mStringID = str;
            this.mID =
                    str.length() == 4
                            ? ((str.charAt(0) & 255) << 24)
                                    | ((str.charAt(1) & 255) << 16)
                                    | ((str.charAt(2) & 255) << 8)
                                    | (str.charAt(3) & 255)
                            : 0;
        }

        public ID(int i) {
            this.mID = i;
            this.mStringID =
                    new String(
                            new char[] {
                                (char) ((i >> 24) & 255),
                                (char) ((i >> 16) & 255),
                                (char) ((i >> 8) & 255),
                                (char) (i & 255)
                            });
            if (!sPattern.matcher(r7).matches()) {
                throw new IllegalArgumentException("Invalid Metric ID");
            }
        }

        @Keep
        private ID(Parcel parcel) {
            parcel.readInt();
            this.mID = parcel.readInt();
            this.mStringID = parcel.readString();
        }
    }

    public Metric() {
        this.mClassName = getClass().getCanonicalName();
        this.mMetricId = enforceDeclarationAndGetMetricID();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final int enforceDeclarationAndGetMetricID() {
        try {
            Field field = getClass().getField("ID");
            Object obj = field.get(field);
            if (obj instanceof ID) {
                return ((ID) obj).asInt();
            }
            throw new IllegalArgumentException("ID was null");
        } catch (IllegalAccessException unused) {
            throw new IllegalArgumentException("Can't read ID field from sub class");
        } catch (NoSuchFieldException unused2) {
            throw new IllegalArgumentException("Sub class must define an ID field");
        }
    }

    public final int getId() {
        return this.mMetricId;
    }

    @Keep
    public abstract int serialize(ByteBuffer byteBuffer) throws BufferOverflowException;

    public void stringOut(ByteBuffer byteBuffer, String str) throws BufferOverflowException {
        if (str != null) {
            byteBuffer.put(str.replace((char) 0, '_').getBytes(Charset.defaultCharset()));
        }
        byteBuffer.put((byte) 0);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(14);
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.mClassName);
        parcel.writeLong(0L);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2);
        parcel.setDataPosition(dataPosition2);
        parcel.writeInt(14);
    }

    public Metric(Parcel parcel) {
        if (parcel.readInt() != 1) {
            int readInt = parcel.readInt();
            this.mClassName = parcel.readString();
            parcel.readLong();
            parcel.setDataPosition(readInt);
            this.mMetricId = enforceDeclarationAndGetMetricID();
            return;
        }
        throw new IllegalArgumentException("API 1 not supported");
    }
}
