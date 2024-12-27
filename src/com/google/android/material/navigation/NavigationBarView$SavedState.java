package com.google.android.material.navigation;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.customview.view.AbsSavedState;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class NavigationBarView$SavedState extends AbsSavedState {
    public static final Parcelable.Creator<NavigationBarView$SavedState> CREATOR =
            new AnonymousClass1();
    public Bundle menuPresenterState;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.google.android.material.navigation.NavigationBarView$SavedState$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.ClassLoaderCreator {
        @Override // android.os.Parcelable.ClassLoaderCreator
        public final Object createFromParcel(Parcel parcel, ClassLoader classLoader) {
            return new NavigationBarView$SavedState(parcel, classLoader);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new NavigationBarView$SavedState[i];
        }

        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new NavigationBarView$SavedState(parcel, null);
        }
    }

    public NavigationBarView$SavedState(Parcel parcel, ClassLoader classLoader) {
        super(parcel, classLoader);
        this.menuPresenterState =
                parcel.readBundle(
                        classLoader == null
                                ? NavigationBarView$SavedState.class.getClassLoader()
                                : classLoader);
    }

    @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeBundle(this.menuPresenterState);
    }
}
