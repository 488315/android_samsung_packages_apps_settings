package com.samsung.android.settings.analyzestorage.presenter.dataloaders;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;

import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.analyzestorage.data.model.AnalyzeStorageFileInfo;
import com.samsung.android.settings.analyzestorage.data.model.CommonFileInfo;
import com.samsung.android.settings.analyzestorage.domain.entity.FileInfo;
import com.samsung.android.settings.analyzestorage.domain.entity.FileInfoFactory;
import com.samsung.android.settings.analyzestorage.domain.exception.UnsupportedArgsException;
import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.presenter.ActivityInfoStore;
import com.samsung.android.settings.analyzestorage.presenter.utils.ContentResolverWrapper;

import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class AnalyzeStorageLoaderTask extends AbsDataLoaderTask {
    public Bundle groupInfo;
    public long start;

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.samsung.android.settings.analyzestorage.presenter.dataloaders.AbsDataLoaderTask
    public final void loadInBackground(LoadResult loadResult) {
        FileInfoFactory.FileInfoGenerator fileInfoGenerator;
        int i = this.pageInfo.mExtra.getInt("asType");
        if (i == 1) {
            Log.d(
                    "AnalyzeStorageLoaderTask",
                    "[Performance Test] Analyze storage > Duplicate files start");
            this.start = SystemClock.elapsedRealtime();
        }
        int i2 = this.pageInfo.mExtra.getInt("asType");
        Bundle bundle = new Bundle();
        bundle.putString("calling_package", KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
        bundle.putInt("asType", i2);
        bundle.putBoolean("needDbUpdate", this.needRefresh);
        Log.d("AnalyzeStorageLoaderTask", "loadListItem()] call type : " + i2);
        Context context = ActivityInfoStore.context;
        Bundle bundle2 = null;
        if (context == null) {
            Intrinsics.throwUninitializedPropertyAccessException("context");
            throw null;
        }
        Bundle call =
                ContentResolverWrapper.call(
                        context, Uri.parse("content://myfiles/"), "GET_AS_SIZE", bundle);
        if (call != null) {
            call.putInt("asType", i2);
            Log.d(
                    "AnalyzeStorageLoaderTask",
                    "loadListItem()] type : " + i2 + ", size : " + call.getLong("size"));
            bundle2 = call;
        }
        this.groupInfo = bundle2;
        loadResult.groupInfo = bundle2;
        List arrayList = new ArrayList();
        Bundle bundle3 = this.groupInfo;
        if (bundle3 != null && bundle3.getLong("size", 0L) != 0) {
            AnalyzeStorageFileInfo[] analyzeStorageFileInfoArr = new AnalyzeStorageFileInfo[1];
            Object[] objArr = {Integer.valueOf(this.pageInfo.mExtra.getInt("asType"))};
            FileInfoFactory.FileInfoGenerator fileInfoGenerator2 =
                    FileInfoFactory.sDefaultGenerator;
            FileInfoFactory.Args args = new FileInfoFactory.Args(objArr);
            synchronized (FileInfoFactory.class) {
                try {
                    if (FileInfoFactory.sGeneratorMap.size() == 0) {
                        Iterator it =
                                ((ArrayList) FileInfoFactory.sGeneratorSupplierList).iterator();
                        while (it.hasNext()) {
                            FileInfoFactory.FileInfoGenerator fileInfoGenerator3 =
                                    (FileInfoFactory.FileInfoGenerator)
                                            ((Supplier) it.next()).get();
                            if (fileInfoGenerator3 != null) {
                                for (int i3 : fileInfoGenerator3.supportDomainType()) {
                                    FileInfoFactory.sGeneratorMap.put(i3, fileInfoGenerator3);
                                }
                            }
                        }
                        FileInfoFactory.sDefaultGenerator =
                                (FileInfoFactory.FileInfoGenerator)
                                        FileInfoFactory.sDefaultGeneratorSupplier.get();
                    }
                    fileInfoGenerator =
                            (FileInfoFactory.FileInfoGenerator)
                                    Optional.ofNullable(
                                                    (FileInfoFactory.FileInfoGenerator)
                                                            FileInfoFactory.sGeneratorMap.get(306))
                                            .orElse(FileInfoFactory.sDefaultGenerator);
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (fileInfoGenerator == null) {
                throw new IllegalStateException("There is no FileInfo generator for 306");
            }
            try {
                fileInfoGenerator.checkArgs(args);
                FileInfo create = fileInfoGenerator.create(args);
                if (create == null) {
                    throw new IllegalStateException(
                            Anchor$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("Abnormal Arguments Pattern (306 , true , "),
                                    args.mArgsPattern,
                                    ")"));
                }
                CommonFileInfo commonFileInfo = (CommonFileInfo) create;
                commonFileInfo.setExt(commonFileInfo.getExt());
                analyzeStorageFileInfoArr[0] = create;
                arrayList = CollectionsKt__CollectionsKt.mutableListOf(analyzeStorageFileInfoArr);
            } catch (UnsupportedArgsException unused) {
                throw new IllegalStateException("Unsupported Arguments for 306");
            }
        }
        loadResult.appDataList = arrayList;
        if (i == 1) {
            Log.d(
                    "AnalyzeStorageLoaderTask",
                    "[Performance Test] Analyze storage > Duplicate files end , time = "
                            + (SystemClock.elapsedRealtime() - this.start)
                            + "ms");
        }
    }
}
