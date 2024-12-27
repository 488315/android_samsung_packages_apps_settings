package com.samsung.android.settings.analyzestorage.external.injection.fileinfo;

import com.samsung.android.settings.analyzestorage.data.model.CommonFileInfo;
import com.samsung.android.settings.analyzestorage.domain.entity.FileInfo;
import com.samsung.android.settings.analyzestorage.domain.entity.FileInfoFactory;
import com.samsung.android.settings.analyzestorage.domain.exception.UnsupportedArgsException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class DefaultFileInfoGenerator implements FileInfoFactory.FileInfoGenerator {
    @Override // com.samsung.android.settings.analyzestorage.domain.entity.FileInfoFactory.FileInfoGenerator
    public final void checkArgs(FileInfoFactory.Args args) {
        Object[] objArr = args.mArgs;
        if (objArr == null || objArr.length == 0) {
            throw new UnsupportedArgsException();
        }
    }

    @Override // com.samsung.android.settings.analyzestorage.domain.entity.FileInfoFactory.FileInfoGenerator
    public final FileInfo create(FileInfoFactory.Args args) {
        if (args.mArgsPattern != -1) {
            return null;
        }
        CommonFileInfo commonFileInfo = new CommonFileInfo((String) args.mArgs[0]);
        commonFileInfo.setIsDirectory(false);
        return commonFileInfo;
    }

    @Override // com.samsung.android.settings.analyzestorage.domain.entity.FileInfoFactory.FileInfoGenerator
    public final int[] supportDomainType() {
        return new int[] {-1};
    }
}
