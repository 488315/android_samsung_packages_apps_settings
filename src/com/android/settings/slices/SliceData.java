package com.android.settings.slices;

import android.net.Uri;
import android.text.TextUtils;

import com.android.settings.MainClear$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SliceData {
    public final String mFragmentClassName;
    public final int mHighlightMenuRes;
    public final int mIconResource;
    public final boolean mIsPublicSlice;
    public final String mKey;
    public final String mKeywords;
    public final String mPreferenceController;
    public final CharSequence mScreenTitle;
    public final int mSliceType;
    public final String mSummary;
    public final String mTitle;
    public final String mUnavailableSliceSubtitle;
    public final Uri mUri;
    public final String mUserRestriction;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder {
        public String mFragmentClassName;
        public int mHighlightMenuRes;
        public int mIconResource;
        public boolean mIsPublicSlice;
        public String mKey;
        public String mKeywords;
        public String mPrefControllerClassName;
        public CharSequence mScreenTitle;
        public int mSliceType;
        public String mSummary;
        public String mTitle;
        public String mUnavailableSliceSubtitle;
        public Uri mUri;
        public String mUserRestriction;

        public final SliceData build() {
            if (TextUtils.isEmpty(this.mKey)) {
                throw new InvalidSliceDataException("Key cannot be empty");
            }
            if (TextUtils.isEmpty(this.mTitle)) {
                throw new InvalidSliceDataException("Title cannot be empty");
            }
            if (TextUtils.isEmpty(this.mFragmentClassName)) {
                throw new InvalidSliceDataException("Fragment Name cannot be empty");
            }
            if (TextUtils.isEmpty(this.mPrefControllerClassName)) {
                throw new InvalidSliceDataException("Preference Controller cannot be empty");
            }
            if (this.mHighlightMenuRes == 0) {
                MainClear$$ExternalSyntheticOutline0.m$1(
                        new StringBuilder("Highlight menu key res is empty: "),
                        this.mPrefControllerClassName,
                        "SliceData");
            }
            return new SliceData(this);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class InvalidSliceDataException extends RuntimeException {
        public InvalidSliceDataException(String str) {
            super(str);
        }
    }

    public SliceData(Builder builder) {
        this.mKey = builder.mKey;
        this.mTitle = builder.mTitle;
        this.mSummary = builder.mSummary;
        this.mScreenTitle = builder.mScreenTitle;
        this.mKeywords = builder.mKeywords;
        this.mIconResource = builder.mIconResource;
        this.mFragmentClassName = builder.mFragmentClassName;
        this.mUri = builder.mUri;
        this.mPreferenceController = builder.mPrefControllerClassName;
        this.mSliceType = builder.mSliceType;
        this.mUnavailableSliceSubtitle = builder.mUnavailableSliceSubtitle;
        this.mIsPublicSlice = builder.mIsPublicSlice;
        this.mHighlightMenuRes = builder.mHighlightMenuRes;
        this.mUserRestriction = builder.mUserRestriction;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof SliceData) {
            return TextUtils.equals(this.mKey, ((SliceData) obj).mKey);
        }
        return false;
    }

    public final int hashCode() {
        return this.mKey.hashCode();
    }
}
