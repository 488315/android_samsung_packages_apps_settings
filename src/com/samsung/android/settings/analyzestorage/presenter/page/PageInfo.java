package com.samsung.android.settings.analyzestorage.presenter.page;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.analyzestorage.domain.entity.FileInfo;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class PageInfo implements Parcelable {
    public static final Parcelable.Creator<PageInfo> CREATOR = new AnonymousClass1();
    public static int sPageIdValue;
    public int mActivityType;
    public final Bundle mExtra;
    public final FileInfo mFileInfo;
    public NavigationMode mNavigationMode;
    public final int mPageId;
    public final PageType mPageType;
    public final int mParentPageId;
    public final boolean mUseIndicator;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.analyzestorage.presenter.page.PageInfo$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new PageInfo(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new PageInfo[i];
        }
    }

    public PageInfo(PageType pageType) {
        this.mUseIndicator = true;
        this.mNavigationMode = NavigationMode.Normal;
        this.mExtra = new Bundle();
        this.mPageType = pageType;
        int i = sPageIdValue;
        sPageIdValue = i + 1;
        this.mPageId = i;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final String getPath() {
        return this.mExtra.getString("path", ApnSettings.MVNO_NONE);
    }

    public final void putExtra(int i, String str) {
        this.mExtra.putInt(str, i);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mPageType.toString());
        parcel.writeInt(this.mUseIndicator ? 1 : 0);
        parcel.writeString(this.mNavigationMode.toString());
        parcel.writeBundle(this.mExtra);
        parcel.writeInt(this.mPageId);
        parcel.writeInt(this.mParentPageId);
        parcel.writeInt(this.mActivityType);
    }

    public PageInfo(PageInfo pageInfo) {
        this.mUseIndicator = true;
        this.mNavigationMode = NavigationMode.Normal;
        Bundle bundle = new Bundle();
        this.mExtra = bundle;
        this.mPageType = pageInfo.mPageType;
        this.mNavigationMode = pageInfo.mNavigationMode;
        bundle.putAll(pageInfo.mExtra);
        this.mPageId = pageInfo.mPageId;
        this.mActivityType = pageInfo.mActivityType;
        this.mFileInfo = pageInfo.mFileInfo;
    }

    public PageInfo(Parcel parcel) {
        this.mUseIndicator = true;
        this.mNavigationMode = NavigationMode.Normal;
        Bundle bundle = new Bundle();
        this.mExtra = bundle;
        this.mPageType = PageType.valueOf(parcel.readString());
        this.mUseIndicator = parcel.readInt() == 1;
        this.mNavigationMode = NavigationMode.valueOf(parcel.readString());
        bundle.clear();
        bundle.putAll(parcel.readBundle());
        this.mPageId = parcel.readInt();
        this.mParentPageId = parcel.readInt();
        this.mActivityType = parcel.readInt();
    }
}
