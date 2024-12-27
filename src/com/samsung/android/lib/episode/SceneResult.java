package com.samsung.android.lib.episode;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.compose.runtime.ComposerKt$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SceneResult implements Parcelable {
    public static final Parcelable.Creator<SceneResult> CREATOR = new AnonymousClass1();
    public ErrorType mErrorType;
    public String mKey;
    public ResultType mResultType;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.lib.episode.SceneResult$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            SceneResult sceneResult = new SceneResult();
            sceneResult.mKey = parcel.readString();
            int readInt = parcel.readInt();
            sceneResult.mResultType = readInt == -1 ? null : ResultType.values()[readInt];
            int readInt2 = parcel.readInt();
            sceneResult.mErrorType = readInt2 != -1 ? ErrorType.values()[readInt2] : null;
            return sceneResult;
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new SceneResult[i];
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder {
        public ErrorType mErrorType;
        public final String mKey;
        public ResultType mResultType = ResultType.RESULT_UNDEFINED;

        public Builder(String str) {
            this.mKey = str;
        }

        public final SceneResult build() {
            ErrorType errorType;
            ResultType resultType = this.mResultType;
            ResultType resultType2 = ResultType.RESULT_UNDEFINED;
            String str = this.mKey;
            if (resultType == resultType2) {
                Log.e("Eternal/SceneResult", "[" + str + "] mResultType is undefined");
                throw new IllegalArgumentException(
                        ComposerKt$$ExternalSyntheticOutline0.m(
                                "[",
                                str,
                                "] mResultType is undefined. Need setResultType(ResultType)"));
            }
            if (resultType == ResultType.RESULT_FAIL) {
                ErrorType errorType2 = this.mErrorType;
                if (errorType2 == null) {
                    Log.e("Eternal/SceneResult", "[" + str + "] ErrorType is null");
                    throw new IllegalArgumentException(
                            ComposerKt$$ExternalSyntheticOutline0.m(
                                    "[", str, "] ErrorType is null. Need setErrorType(ErrorType)"));
                }
                if ((errorType2 == ErrorType.NO_PERMISSION
                                || errorType2 == ErrorType.TEMPORARY_BLOCK)
                        && (errorType2.mErrorReasonList == null
                                || this.mErrorType.mErrorReasonList.isEmpty())) {
                    Log.e("Eternal/SceneResult", "[" + str + "] mErrorReasonList is null");
                    throw new IllegalArgumentException(
                            ComposerKt$$ExternalSyntheticOutline0.m(
                                    "[",
                                    str,
                                    "] mErrorReasonList is null. Need setErrorReason(List)"));
                }
            } else if (resultType == ResultType.RESULT_SKIP
                    && (errorType = this.mErrorType) != ErrorType.DEFAULT_VALUE
                    && errorType != ErrorType.FAST_TRACK) {
                throw new IllegalArgumentException(
                        ComposerKt$$ExternalSyntheticOutline0.m(
                                "[",
                                str,
                                "] If result type is RESULT_SKIP, ErrorType must be one of"
                                    + " DEFAULT_VALUE or FAST_TRACK"));
            }
            ResultType resultType3 = this.mResultType;
            ErrorType errorType3 = this.mErrorType;
            SceneResult sceneResult = new SceneResult();
            sceneResult.mKey = str;
            sceneResult.mResultType = resultType3;
            sceneResult.mErrorType = errorType3;
            return sceneResult;
        }

        public final void setErrorType(ErrorType errorType) {
            this.mErrorType = errorType;
        }

        public final void setResult(ResultType resultType) {
            this.mResultType = resultType;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public enum ErrorType {
        FAST_TRACK("FAST_TRACK"),
        INVALID_DATA("INVALID_DATA"),
        /* JADX INFO: Fake field, exist only in values array */
        STORAGE_FULL("STORAGE_FULL"),
        UNKNOWN_ERROR("UNKNOWN_ERROR"),
        DEFAULT_VALUE("DEFAULT_VALUE"),
        NOT_SUPPORTED("NOT_SUPPORTED"),
        NO_PERMISSION("NO_PERMISSION"),
        TEMPORARY_BLOCK("TEMPORARY_BLOCK"),
        DEVICE_TYPE_MISMATCH("DEVICE_TYPE_MISMATCH");

        public static final ErrorType STORAGE_FULL = null;
        private List<String> mErrorReasonList = new ArrayList();
        private String mReason;

        ErrorType(String str) {
            this.mReason = str;
        }

        public final List getErrorMessageReason() {
            return this.mErrorReasonList;
        }

        @Override // java.lang.Enum
        public final String toString() {
            return this.mReason;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public enum ResultType {
        RESULT_OK("RESULT_OK"),
        RESULT_FAIL("RESULT_FAIL"),
        RESULT_SKIP("RESULT_SKIP"),
        RESULT_UNDEFINED("RESULT_UNDEFINED");

        private String mResultType;

        ResultType(String str) {
            this.mResultType = str;
        }

        @Override // java.lang.Enum
        public final String toString() {
            return this.mResultType;
        }
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mKey);
        ResultType resultType = this.mResultType;
        parcel.writeInt(resultType == null ? -1 : resultType.ordinal());
        ErrorType errorType = this.mErrorType;
        parcel.writeInt(errorType != null ? errorType.ordinal() : -1);
    }
}
