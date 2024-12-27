package com.samsung.android.settings.actions;

import android.text.TextUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ActionData {
    public final int mActionType;
    public final String mFragmentClassName;
    public final int mIconResource;
    public final String mKey;
    public final String mKeywords;
    public final LaunchPayload mPayload;
    public final String mPreferenceController;
    public final boolean mRecoverable;
    public final String mRestoreKey;
    public final CharSequence mScreenTitle;
    public final String mSummary;
    public final String mTargetFragment;
    public final String mTitle;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder {
        public int mActionType;
        public String mFragmentClassName;
        public int mIconResource;
        public String mKey;
        public String mKeywords;
        public LaunchPayload mPayload;
        public String mPrefControllerClassName;
        public boolean mRecoverable;
        public String mRestoreKey;
        public CharSequence mScreenTitle;
        public String mSummary;
        public String mTargetFragment;
        public String mTitle;

        public final ActionData build() {
            if (TextUtils.isEmpty(this.mKey)) {
                throw new InvalidActionDataException(
                        "Key cannot be empty : " + this.mFragmentClassName);
            }
            TextUtils.isEmpty(this.mTitle);
            TextUtils.isEmpty(this.mFragmentClassName);
            if (!TextUtils.isEmpty(this.mPrefControllerClassName)) {
                return new ActionData(this);
            }
            throw new InvalidActionDataException(
                    "Preference Controller cannot be empty : "
                            + this.mKey
                            + ", class name : "
                            + this.mFragmentClassName);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class InvalidActionDataException extends RuntimeException {
        public InvalidActionDataException(String str) {
            super(str);
        }
    }

    public ActionData(Builder builder) {
        this.mKey = builder.mKey;
        this.mTitle = builder.mTitle;
        this.mSummary = builder.mSummary;
        this.mScreenTitle = builder.mScreenTitle;
        this.mKeywords = builder.mKeywords;
        this.mIconResource = builder.mIconResource;
        this.mFragmentClassName = builder.mFragmentClassName;
        this.mPreferenceController = builder.mPrefControllerClassName;
        this.mActionType = builder.mActionType;
        this.mRecoverable = builder.mRecoverable;
        this.mRestoreKey = builder.mRestoreKey;
        this.mTargetFragment = builder.mTargetFragment;
        this.mPayload = builder.mPayload;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof ActionData) {
            return TextUtils.equals(this.mKey, ((ActionData) obj).mKey);
        }
        return false;
    }

    public final int hashCode() {
        return this.mKey.hashCode();
    }
}
