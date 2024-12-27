package com.samsung.android.settings.analyzestorage.external.injection.fileinfo;

import com.samsung.android.settings.analyzestorage.data.model.AnalyzeStorageFileInfo;
import com.samsung.android.settings.analyzestorage.domain.entity.FileInfo;
import com.samsung.android.settings.analyzestorage.domain.entity.FileInfoFactory;
import com.samsung.android.settings.analyzestorage.domain.exception.UnsupportedArgsException;
import com.samsung.android.settings.analyzestorage.presenter.utils.StoragePathUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class AnalyzeStorageFileInfoGenerator implements FileInfoFactory.FileInfoGenerator {
    @Override // com.samsung.android.settings.analyzestorage.domain.entity.FileInfoFactory.FileInfoGenerator
    public final void checkArgs(FileInfoFactory.Args args) {
        if (args.mArgsPattern == 2008) {
            Class[] clsArr = new Class[1];
            Object[] objArr = args.mArgs;
            if (objArr == null || objArr.length != 1 || !Integer.class.isInstance(objArr[0])) {
                throw new UnsupportedArgsException();
            }
        }
    }

    @Override // com.samsung.android.settings.analyzestorage.domain.entity.FileInfoFactory.FileInfoGenerator
    public final FileInfo create(FileInfoFactory.Args args) {
        int i = args.mArgsPattern;
        Object[] objArr = args.mArgs;
        if (i == -1) {
            String str = (String) objArr[0];
            return new AnalyzeStorageFileInfo(str, StoragePathUtils.getDomainType(str));
        }
        if (i != 2008) {
            return null;
        }
        AnalyzeStorageFileInfo analyzeStorageFileInfo = new AnalyzeStorageFileInfo();
        analyzeStorageFileInfo.setGroupHeader();
        analyzeStorageFileInfo.setGroupType(((Integer) objArr[0]).intValue());
        return analyzeStorageFileInfo;
    }

    @Override // com.samsung.android.settings.analyzestorage.domain.entity.FileInfoFactory.FileInfoGenerator
    public final int[] supportDomainType() {
        return new int[] {306};
    }
}
