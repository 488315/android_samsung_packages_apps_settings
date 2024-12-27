package com.android.settings.widget;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class RtlCompatibleViewPager extends ViewPager {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class RtlSavedState extends View.BaseSavedState {
        public static final Parcelable.ClassLoaderCreator<RtlSavedState> CREATOR =
                new AnonymousClass1();
        public int position;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.android.settings.widget.RtlCompatibleViewPager$RtlSavedState$1, reason: invalid class name */
        public final class AnonymousClass1 implements Parcelable.ClassLoaderCreator {
            @Override // android.os.Parcelable.ClassLoaderCreator
            public final Object createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new RtlSavedState(parcel, classLoader);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new RtlSavedState[i];
            }

            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new RtlSavedState(parcel, null);
            }
        }

        public RtlSavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.position = parcel.readInt();
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState,
                  // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.position);
        }
    }

    public RtlCompatibleViewPager(Context context) {
        this(context, null);
    }

    @Override // androidx.viewpager.widget.ViewPager
    public int getCurrentItem() {
        return getRtlAwareIndex(super.getCurrentItem());
    }

    public final int getRtlAwareIndex(int i) {
        return TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1
                ? (getAdapter().getCount() - i) - 1
                : i;
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        RtlSavedState rtlSavedState = (RtlSavedState) parcelable;
        super.onRestoreInstanceState(rtlSavedState.getSuperState());
        setCurrentItem(rtlSavedState.position);
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.View
    public final Parcelable onSaveInstanceState() {
        RtlSavedState rtlSavedState = new RtlSavedState(super.onSaveInstanceState());
        rtlSavedState.position = getCurrentItem();
        return rtlSavedState;
    }

    @Override // androidx.viewpager.widget.ViewPager
    public void setCurrentItem(int i) {
        super.setCurrentItem(getRtlAwareIndex(i));
    }

    public RtlCompatibleViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
