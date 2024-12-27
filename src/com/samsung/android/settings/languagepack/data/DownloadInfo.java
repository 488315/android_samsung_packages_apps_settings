package com.samsung.android.settings.languagepack.data;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class DownloadInfo {
    public final String mContentSize;
    public final String mDeltaContentSize;
    public final String mDeltaDownloadURI;
    public final String mDownloadURI;
    public final String mInstalledBinaryHashValue;
    public final String mResultCode;
    public final String mResultMsg;
    public final String mSignature;
    public final String mUpdateBinaryHashValue;

    public DownloadInfo(
            String str,
            String str2,
            String str3,
            String str4,
            String str5,
            String str6,
            String str7,
            String str8,
            String str9) {
        this.mResultCode = str;
        this.mResultMsg = str2;
        this.mDownloadURI = str3;
        this.mContentSize = str4;
        this.mDeltaDownloadURI = str5;
        this.mDeltaContentSize = str6;
        this.mInstalledBinaryHashValue = str7;
        this.mUpdateBinaryHashValue = str8;
        this.mSignature = str9;
    }
}
