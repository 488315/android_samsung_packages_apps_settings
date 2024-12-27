package com.android.settings.homepage;

import android.content.DialogInterface;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.settings.widget.HighlightableTopLevelPreferenceAdapter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class TopLevelHighlightMixin
        implements Parcelable,
                DialogInterface.OnShowListener,
                DialogInterface.OnCancelListener,
                DialogInterface.OnDismissListener {
    public static final Parcelable.Creator<TopLevelHighlightMixin> CREATOR = new AnonymousClass1();
    public boolean mActivityEmbedded;
    public String mCurrentKey;
    public DialogInterface mDialog;
    public String mHiddenKey;
    public String mPreviousKey;
    public HighlightableTopLevelPreferenceAdapter mTopLevelAdapter;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.homepage.TopLevelHighlightMixin$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            TopLevelHighlightMixin topLevelHighlightMixin = new TopLevelHighlightMixin();
            topLevelHighlightMixin.mCurrentKey = parcel.readString();
            topLevelHighlightMixin.mPreviousKey = parcel.readString();
            topLevelHighlightMixin.mHiddenKey = parcel.readString();
            topLevelHighlightMixin.mActivityEmbedded = parcel.readBoolean();
            return topLevelHighlightMixin;
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new TopLevelHighlightMixin[i];
        }
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public String getHighlightPreferenceKey() {
        return this.mCurrentKey;
    }

    @Override // android.content.DialogInterface.OnCancelListener
    public final void onCancel(DialogInterface dialogInterface) {
        HighlightableTopLevelPreferenceAdapter highlightableTopLevelPreferenceAdapter =
                this.mTopLevelAdapter;
        if (highlightableTopLevelPreferenceAdapter != null) {
            String str = this.mPreviousKey;
            this.mCurrentKey = str;
            this.mPreviousKey = null;
            highlightableTopLevelPreferenceAdapter.highlightPreference(str, false);
        }
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        this.mDialog = null;
    }

    @Override // android.content.DialogInterface.OnShowListener
    public final void onShow(DialogInterface dialogInterface) {
        this.mDialog = dialogInterface;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mCurrentKey);
        parcel.writeString(this.mPreviousKey);
        parcel.writeString(this.mHiddenKey);
        parcel.writeBoolean(this.mActivityEmbedded);
    }
}
