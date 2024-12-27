package com.samsung.android.settings.analyzestorage.presenter.execution;

import android.content.Context;

import com.samsung.android.settings.analyzestorage.domain.usecase.fileoperation.FileOperationArgs;
import com.samsung.android.settings.analyzestorage.domain.usecase.fileoperation.ProgressListener;
import com.samsung.android.settings.analyzestorage.presenter.page.PageInfo;
import com.samsung.android.settings.analyzestorage.ui.dialog.AbsDialog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ExecutionParams {
    public final Context mContext;
    public AbsDialog mDialog;
    public FileOperationArgs mFileOperationArgs;
    public int mInstanceId = -1;
    public PageInfo mPageInfo;
    public ProgressListener mProgressListener;

    public ExecutionParams(Context context) {
        this.mContext = context;
    }
}
