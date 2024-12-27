package com.android.settings.homepage.contextualcards;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;

import androidx.slice.Slice;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.scs.ai.sdkcommon.feature.FeatureConfig;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ContextualCard {
    public final Builder mBuilder;
    public final int mCardType;
    public final int mCategory;
    public final boolean mHasInlineAction;
    public final Drawable mIconDrawable;
    public final boolean mIsLargeCard;
    public final boolean mIsPendingDismiss;
    public final String mName;
    public final double mRankingScore;
    public final Slice mSlice;
    public final String mSliceUri;
    public final String mSummaryText;
    public final String mTitleText;
    public final int mViewType;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class Builder {
        public long mAppVersion;
        public int mCardType;
        public int mCategory;
        public boolean mHasInlineAction;
        public Drawable mIconDrawable;
        public boolean mIsLargeCard;
        public boolean mIsPendingDismiss;
        public String mName;
        public String mPackageName;
        public double mRankingScore;
        public Slice mSlice;
        public String mSliceUri;
        public String mSummaryText;
        public String mTitleText;
        public int mViewType;

        public ContextualCard build() {
            return new ContextualCard(this);
        }
    }

    public ContextualCard(Builder builder) {
        this.mBuilder = builder;
        this.mName = builder.mName;
        this.mCardType = builder.mCardType;
        this.mRankingScore = builder.mRankingScore;
        this.mSliceUri = builder.mSliceUri;
        this.mCategory = builder.mCategory;
        this.mTitleText = builder.mTitleText;
        this.mSummaryText = builder.mSummaryText;
        this.mIconDrawable = builder.mIconDrawable;
        this.mIsLargeCard = builder.mIsLargeCard;
        this.mViewType = builder.mViewType;
        this.mIsPendingDismiss = builder.mIsPendingDismiss;
        this.mHasInlineAction = builder.mHasInlineAction;
        this.mSlice = builder.mSlice;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ContextualCard) {
            return TextUtils.equals(this.mName, ((ContextualCard) obj).mName);
        }
        return false;
    }

    public int getCardType() {
        return this.mCardType;
    }

    public int hashCode() {
        return this.mName.hashCode();
    }

    public ContextualCard(Cursor cursor) {
        Builder builder = new Builder();
        this.mBuilder = builder;
        String string = cursor.getString(cursor.getColumnIndex("name"));
        this.mName = string;
        builder.mName = string;
        int i = cursor.getInt(cursor.getColumnIndex("type"));
        this.mCardType = i;
        builder.mCardType = i;
        double d = cursor.getDouble(cursor.getColumnIndex("score"));
        this.mRankingScore = d;
        builder.mRankingScore = d;
        String string2 = cursor.getString(cursor.getColumnIndex("slice_uri"));
        this.mSliceUri = string2;
        builder.mSliceUri = Uri.parse(string2).toString();
        int i2 = cursor.getInt(cursor.getColumnIndex("category"));
        this.mCategory = i2;
        builder.mCategory = i2;
        cursor.getString(cursor.getColumnIndex("package_name"));
        cursor.getLong(cursor.getColumnIndex(FeatureConfig.JSON_KEY_APP_VERSION));
        this.mTitleText = ApnSettings.MVNO_NONE;
        this.mSummaryText = ApnSettings.MVNO_NONE;
        builder.mTitleText = ApnSettings.MVNO_NONE;
        this.mIsLargeCard = false;
        builder.mIsLargeCard = false;
        this.mIconDrawable = null;
        builder.mIconDrawable = null;
        int i3 = i == 1 ? R.layout.contextual_slice_full_tile : 0;
        this.mViewType = i3;
        builder.mViewType = i3;
        this.mIsPendingDismiss = false;
        builder.mIsPendingDismiss = false;
        this.mHasInlineAction = false;
        builder.mHasInlineAction = false;
        this.mSlice = null;
        builder.mSlice = null;
    }
}
