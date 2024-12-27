package com.google.android.setupcompat.logging;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;

import com.google.android.setupcompat.internal.Preconditions;
import com.google.android.setupcompat.internal.Validations;

import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class CustomEvent implements Parcelable {
    public static final Parcelable.Creator<CustomEvent> CREATOR = new AnonymousClass1();
    static final int MAX_STR_LENGTH = 50;
    static final int MIN_BUNDLE_KEY_LENGTH = 3;
    public final MetricKey metricKey;
    public final PersistableBundle persistableBundle;
    public final PersistableBundle piiValues;
    public final long timestampMillis;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.google.android.setupcompat.logging.CustomEvent$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new CustomEvent(
                    parcel.readLong(),
                    (MetricKey) parcel.readParcelable(MetricKey.class.getClassLoader()),
                    parcel.readPersistableBundle(MetricKey.class.getClassLoader()),
                    parcel.readPersistableBundle(MetricKey.class.getClassLoader()));
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new CustomEvent[i];
        }
    }

    public CustomEvent(
            long j,
            MetricKey metricKey,
            PersistableBundle persistableBundle,
            PersistableBundle persistableBundle2) {
        Preconditions.checkArgument("Timestamp cannot be negative.", j >= 0);
        Preconditions.checkNotNull(metricKey, "MetricKey cannot be null.");
        Preconditions.checkNotNull(persistableBundle, "Bundle cannot be null.");
        Preconditions.checkArgument("Bundle cannot be empty.", !persistableBundle.isEmpty());
        Preconditions.checkNotNull(persistableBundle2, "piiValues cannot be null.");
        for (String str : persistableBundle.keySet()) {
            Validations.assertLengthInRange(3, 50, str, "bundle key");
            Object obj = persistableBundle.get(str);
            if (obj instanceof String) {
                Preconditions.checkArgument(
                        "Maximum length of string value for key='" + str + "' cannot exceed 50.",
                        ((String) obj).length() <= 50);
            }
        }
        this.timestampMillis = j;
        this.metricKey = metricKey;
        this.persistableBundle = new PersistableBundle(persistableBundle);
        this.piiValues = new PersistableBundle(persistableBundle2);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0034, code lost:

       if (com.google.android.setupcompat.internal.PersistableBundles.toMap(r1).equals(com.google.android.setupcompat.internal.PersistableBundles.toMap(r3)) != false) goto L21;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean equals(java.lang.Object r8) {
        /*
            r7 = this;
            r0 = 1
            if (r7 != r8) goto L4
            return r0
        L4:
            boolean r1 = r8 instanceof com.google.android.setupcompat.logging.CustomEvent
            r2 = 0
            if (r1 != 0) goto La
            return r2
        La:
            com.google.android.setupcompat.logging.CustomEvent r8 = (com.google.android.setupcompat.logging.CustomEvent) r8
            long r3 = r7.timestampMillis
            long r5 = r8.timestampMillis
            int r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r1 != 0) goto L4e
            com.google.android.setupcompat.logging.MetricKey r1 = r7.metricKey
            com.google.android.setupcompat.logging.MetricKey r3 = r8.metricKey
            if (r1 == r3) goto L22
            if (r1 == 0) goto L4e
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L4e
        L22:
            android.os.PersistableBundle r1 = r7.persistableBundle
            android.os.PersistableBundle r3 = r8.persistableBundle
            if (r1 == r3) goto L37
            android.util.ArrayMap r1 = com.google.android.setupcompat.internal.PersistableBundles.toMap(r1)
            android.util.ArrayMap r3 = com.google.android.setupcompat.internal.PersistableBundles.toMap(r3)
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L4e
            goto L39
        L37:
            com.google.android.setupcompat.util.Logger r1 = com.google.android.setupcompat.internal.PersistableBundles.LOG
        L39:
            android.os.PersistableBundle r7 = r7.piiValues
            android.os.PersistableBundle r8 = r8.piiValues
            if (r7 == r8) goto L4f
            android.util.ArrayMap r7 = com.google.android.setupcompat.internal.PersistableBundles.toMap(r7)
            android.util.ArrayMap r8 = com.google.android.setupcompat.internal.PersistableBundles.toMap(r8)
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L4e
            goto L4f
        L4e:
            r0 = r2
        L4f:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.android.setupcompat.logging.CustomEvent.equals(java.lang.Object):boolean");
    }

    public final int hashCode() {
        return Arrays.hashCode(
                new Object[] {
                    Long.valueOf(this.timestampMillis),
                    this.metricKey,
                    this.persistableBundle,
                    this.piiValues
                });
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.timestampMillis);
        parcel.writeParcelable(this.metricKey, i);
        parcel.writePersistableBundle(this.persistableBundle);
        parcel.writePersistableBundle(this.piiValues);
    }
}
