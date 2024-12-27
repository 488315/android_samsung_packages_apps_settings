package com.samsung.android.settings.analyzestorage.data.model;

import android.net.Uri;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.analyzestorage.domain.entity.FileInfo;
import com.samsung.android.settings.analyzestorage.domain.log.Log;

import java.io.File;
import java.util.Optional;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class CommonFileInfo implements FileInfo, Cloneable {
    private static final long serialVersionUID = 1;
    private long mDate;
    private int mDomainType;
    private String mExt;
    protected String mFileId;
    private int mFileType;
    protected String mFullPath;
    private long mId;
    private boolean mIsHidden;
    private int mItemCount;
    private int mItemCountHidden;
    private String mMimeType;
    private String mName;
    private int mParentHash;
    protected String mParentId;
    private String mPath;
    private long mSize;
    private boolean mTrashed;
    private Uri mUri;

    public CommonFileInfo() {}

    public final boolean equals(Object obj) {
        String fileId;
        if (!(obj instanceof FileInfo)) {
            return false;
        }
        FileInfo fileInfo = (FileInfo) obj;
        return ((CommonFileInfo) fileInfo).mDomainType == this.mDomainType
                && (fileId = ((CommonFileInfo) fileInfo).getFileId()) != null
                && fileId.equals(getFileId());
    }

    public final int getDomainType() {
        return this.mDomainType;
    }

    public final String getExt() {
        String str;
        String str2 = this.mExt;
        if (str2 != null && !str2.isEmpty()) {
            return this.mExt;
        }
        int i = this.mFileType;
        String str3 = ApnSettings.MVNO_NONE;
        if (i != 12289 && (str = this.mFullPath) != null) {
            int lastIndexOf = str.lastIndexOf(File.separatorChar);
            int lastIndexOf2 = this.mFullPath.lastIndexOf(46);
            if (lastIndexOf < lastIndexOf2 && lastIndexOf2 > 0) {
                str3 = this.mFullPath.substring(lastIndexOf2 + 1);
            }
            this.mExt = str3;
        }
        return str3;
    }

    public final String getFileId() {
        String str = this.mFileId;
        return (str == null || str.isEmpty()) ? this.mFullPath : this.mFileId;
    }

    @Override // com.samsung.android.settings.analyzestorage.domain.entity.DataInfo
    public final long getSize() {
        return this.mSize;
    }

    public final int hashCode() {
        return (((String) Optional.ofNullable(getFileId()).orElse(ApnSettings.MVNO_NONE)).hashCode()
                        * 31)
                + this.mDomainType;
    }

    public final void setDomainType(int i) {
        this.mDomainType = i;
    }

    public final void setExt(String str) {
        this.mExt = str;
    }

    public final void setIsDirectory(boolean z) {
        this.mFileType = z ? 12289 : this.mFileType;
    }

    public CommonFileInfo(String str) {
        this.mFullPath = str;
        if (str != null) {
            int lastIndexOf = str.lastIndexOf(File.separatorChar);
            if (lastIndexOf >= 0) {
                this.mName = this.mFullPath.substring(lastIndexOf + 1);
            } else {
                this.mName = this.mFullPath;
            }
        } else {
            Log.e("CommonFileInfo", "setFullPath() ] fullPath is null");
        }
        String str2 = this.mFullPath;
        if (str2 != null) {
            int lastIndexOf2 = str2.lastIndexOf(File.separatorChar);
            if (lastIndexOf2 >= 0) {
                this.mPath = this.mFullPath.substring(0, lastIndexOf2);
            }
        } else {
            this.mPath = null;
        }
        this.mPath = this.mPath;
    }
}
