package com.samsung.android.settings.cube.index;

import android.net.Uri;
import android.text.TextUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ControlData {
    public int mControlType;
    public String mControllerClassName;
    public String mFragmentClassName;
    public Uri mIconUri;
    public String mKey;
    public String mKeywords;
    public boolean mRecoverable;
    public String mRestoreKey;
    public CharSequence mScreenTitle;
    public String mSummary;
    public String mTitle;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder {
        public int mControlType;
        public String mControllerClassName;
        public String mFragmentClassName;
        public Uri mIconUri;
        public String mKey;
        public String mKeywords;
        public boolean mRecoverable;
        public String mRestoreKey;
        public CharSequence mScreenTitle;
        public String mSummary;
        public String mTitle;

        public final ControlData build() {
            if (TextUtils.isEmpty(this.mKey)) {
                throw new InvalidControlDataException(
                        "Key cannot be empty : " + this.mFragmentClassName);
            }
            TextUtils.isEmpty(this.mTitle);
            TextUtils.isEmpty(this.mFragmentClassName);
            if (TextUtils.isEmpty(this.mControllerClassName)) {
                throw new InvalidControlDataException(
                        "Preference Controller cannot be empty : "
                                + this.mKey
                                + ", class name : "
                                + this.mFragmentClassName);
            }
            ControlData controlData = new ControlData();
            controlData.mKey = this.mKey;
            controlData.mTitle = this.mTitle;
            controlData.mSummary = this.mSummary;
            controlData.mScreenTitle = this.mScreenTitle;
            controlData.mKeywords = this.mKeywords;
            controlData.mIconUri = this.mIconUri;
            controlData.mFragmentClassName = this.mFragmentClassName;
            controlData.mControllerClassName = this.mControllerClassName;
            controlData.mControlType = this.mControlType;
            controlData.mRecoverable = this.mRecoverable;
            controlData.mRestoreKey = this.mRestoreKey;
            return controlData;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class InvalidControlDataException extends RuntimeException {
        public InvalidControlDataException(String str) {
            super(str);
        }
    }
}
