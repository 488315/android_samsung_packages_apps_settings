package com.sec.ims.cmc;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.core.text.PrecomputedTextCompat$Params$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class CmcRecordingInfo implements Parcelable {
    public static final Parcelable.Creator<CmcRecordingInfo> CREATOR =
            new Parcelable.Creator<
                    CmcRecordingInfo>() { // from class: com.sec.ims.cmc.CmcRecordingInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public CmcRecordingInfo createFromParcel(Parcel parcel) {
                    return new CmcRecordingInfo(parcel);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public CmcRecordingInfo[] newArray(int i) {
                    return new CmcRecordingInfo[i];
                }
            };
    int mAudioChannels;
    int mAudioEncoder;
    int mAudioEncodingBitRate;
    int mAudioSamplingRate;
    int mAudioSource;
    String mAuthor;
    int mDurationInterval;
    long mFileSizeInterval;
    int mMaxDuration;
    long mMaxFileSize;
    int mOutputFormat;
    String mOutputPath;

    public CmcRecordingInfo() {}

    private void readFromParcel(Parcel parcel) {
        this.mAudioSource = parcel.readInt();
        this.mOutputFormat = parcel.readInt();
        this.mMaxFileSize = parcel.readLong();
        this.mMaxDuration = parcel.readInt();
        this.mOutputPath = parcel.readString();
        this.mAudioEncodingBitRate = parcel.readInt();
        this.mAudioChannels = parcel.readInt();
        this.mAudioSamplingRate = parcel.readInt();
        this.mAudioEncoder = parcel.readInt();
        this.mDurationInterval = parcel.readInt();
        this.mFileSizeInterval = parcel.readLong();
        this.mAuthor = parcel.readString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getAudioChannels() {
        return this.mAudioChannels;
    }

    public int getAudioEncoder() {
        return this.mAudioEncoder;
    }

    public int getAudioEncodingBitRate() {
        return this.mAudioEncodingBitRate;
    }

    public int getAudioSamplingRate() {
        return this.mAudioSamplingRate;
    }

    public int getAudioSource() {
        return this.mAudioSource;
    }

    public String getAuthor() {
        return this.mAuthor;
    }

    public int getDurationInterval() {
        return this.mDurationInterval;
    }

    public long getFileSizeInterval() {
        return this.mFileSizeInterval;
    }

    public int getMaxDuration() {
        return this.mMaxDuration;
    }

    public long getMaxFileSize() {
        return this.mMaxFileSize;
    }

    public int getOutputFormat() {
        return this.mOutputFormat;
    }

    public String getOutputPath() {
        return this.mOutputPath;
    }

    public void setAudioChannels(int i) {
        this.mAudioChannels = i;
    }

    public void setAudioEncoder(int i) {
        this.mAudioEncoder = i;
    }

    public void setAudioEncodingBitRate(int i) {
        this.mAudioEncodingBitRate = i;
    }

    public void setAudioSamplingRate(int i) {
        this.mAudioSamplingRate = i;
    }

    public void setAudioSource(int i) {
        this.mAudioSource = i;
    }

    public void setAuthor(String str) {
        this.mAuthor = str;
    }

    public void setDurationInterval(int i) {
        this.mDurationInterval = i;
    }

    public void setFileSizeInterval(long j) {
        this.mFileSizeInterval = j;
    }

    public void setMaxDuration(int i) {
        this.mMaxDuration = i;
    }

    public void setMaxFileSize(long j) {
        this.mMaxFileSize = j;
    }

    public void setOutputFormat(int i) {
        this.mOutputFormat = i;
    }

    public void setOutputPath(String str) {
        this.mOutputPath = str;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("CmcRecordingInfo = {");
        StringBuilder m =
                PrecomputedTextCompat$Params$$ExternalSyntheticOutline0.m(
                        PrecomputedTextCompat$Params$$ExternalSyntheticOutline0.m(
                                new StringBuilder("mAudioSource : "),
                                this.mAudioSource,
                                sb,
                                ", mOutputFormat : "),
                        this.mOutputFormat,
                        sb,
                        ", mMaxFileSize : ");
        m.append(this.mMaxFileSize);
        sb.append(m.toString());
        StringBuilder m2 =
                PrecomputedTextCompat$Params$$ExternalSyntheticOutline0.m(
                        new StringBuilder(", mMaxDuration : "),
                        this.mMaxDuration,
                        sb,
                        ", mOutputPath : ");
        m2.append(this.mOutputPath);
        sb.append(m2.toString());
        StringBuilder m3 =
                PrecomputedTextCompat$Params$$ExternalSyntheticOutline0.m(
                        PrecomputedTextCompat$Params$$ExternalSyntheticOutline0.m(
                                PrecomputedTextCompat$Params$$ExternalSyntheticOutline0.m(
                                        PrecomputedTextCompat$Params$$ExternalSyntheticOutline0.m(
                                                PrecomputedTextCompat$Params$$ExternalSyntheticOutline0
                                                        .m(
                                                                new StringBuilder(
                                                                        ", mAudioEncodingBitRate"
                                                                            + " : "),
                                                                this.mAudioEncodingBitRate,
                                                                sb,
                                                                ", mAudioChannels : "),
                                                this.mAudioChannels,
                                                sb,
                                                ", mAudioSamplingRate : "),
                                        this.mAudioSamplingRate,
                                        sb,
                                        ", mAudioEncoder : "),
                                this.mAudioEncoder,
                                sb,
                                ", mDurationInterval : "),
                        this.mDurationInterval,
                        sb,
                        ", mFileSizeInterval : ");
        m3.append(this.mFileSizeInterval);
        sb.append(m3.toString());
        sb.append(", mAuthor : " + this.mAuthor);
        sb.append("}");
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mAudioSource);
        parcel.writeInt(this.mOutputFormat);
        parcel.writeLong(this.mMaxFileSize);
        parcel.writeInt(this.mMaxDuration);
        parcel.writeString(this.mOutputPath);
        parcel.writeInt(this.mAudioEncodingBitRate);
        parcel.writeInt(this.mAudioChannels);
        parcel.writeInt(this.mAudioSamplingRate);
        parcel.writeInt(this.mAudioEncoder);
        parcel.writeInt(this.mDurationInterval);
        parcel.writeLong(this.mFileSizeInterval);
        parcel.writeString(this.mAuthor);
    }

    public CmcRecordingInfo(Parcel parcel) {
        readFromParcel(parcel);
    }
}
