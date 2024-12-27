package com.samsung.android.settings.share.structure;

import android.content.ComponentName;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;

import androidx.picker.model.AppInfo;

import com.samsung.android.settings.share.common.ResolveInfoTargetPresentationGetter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ShareComponent implements Comparable, Parcelable {
    public static final Parcelable.Creator<ShareComponent> CREATOR = new AnonymousClass1();
    public CachedInfo mCachedInfo;
    public final ComponentName mComponentName;
    public Pair mLabelPair;
    public final int mUserId;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.share.structure.ShareComponent$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new ShareComponent(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new ShareComponent[i];
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CachedInfo {
        public Drawable mIconDrawable;
        public Pair mLabelPair;
        public final ResolveInfoTargetPresentationGetter mTargetPresentationGetter;

        public CachedInfo(
                ResolveInfoTargetPresentationGetter resolveInfoTargetPresentationGetter,
                Pair pair) {
            this.mTargetPresentationGetter = resolveInfoTargetPresentationGetter;
            this.mLabelPair = pair;
        }
    }

    public ShareComponent(ComponentName componentName, int i, Pair pair) {
        this.mCachedInfo = null;
        this.mComponentName = componentName;
        this.mUserId = i;
        this.mLabelPair = pair;
        String packageName = componentName.getPackageName();
        String className = componentName.getClassName();
        AppInfo.Companion companion = AppInfo.Companion;
        AppInfo.Companion.obtain(i, packageName, className);
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        return this.mComponentName.compareTo(((ShareComponent) obj).mComponentName);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof ShareComponent) {
            ShareComponent shareComponent = (ShareComponent) obj;
            return this.mComponentName.equals(shareComponent.mComponentName)
                    && this.mUserId == shareComponent.mUserId;
        }
        if (obj instanceof ComponentName) {
            return this.mComponentName.equals(obj);
        }
        return false;
    }

    public final int hashCode() {
        return this.mComponentName.hashCode();
    }

    public final String toString() {
        return "pkg:"
                + this.mComponentName.getPackageName()
                + ", class:"
                + this.mComponentName.getClassName()
                + ", label:"
                + this.mLabelPair
                + ", userid: "
                + this.mUserId;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        if (parcel == null) {
            return;
        }
        parcel.writeString(this.mComponentName.getPackageName());
        parcel.writeString(this.mComponentName.getClassName());
        parcel.writeInt(this.mUserId);
    }

    public ShareComponent(Parcel parcel) {
        this.mLabelPair = null;
        this.mCachedInfo = null;
        ComponentName componentName = new ComponentName(parcel.readString(), parcel.readString());
        this.mComponentName = componentName;
        int readInt = parcel.readInt();
        this.mUserId = readInt;
        String packageName = componentName.getPackageName();
        String className = componentName.getClassName();
        AppInfo.Companion companion = AppInfo.Companion;
        AppInfo.Companion.obtain(readInt, packageName, className);
    }
}
