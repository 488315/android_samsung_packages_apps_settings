package com.samsung.android.settings.analyzestorage.presenter.execution;

import android.os.Bundle;
import android.util.SparseArray;

import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.settings.analyzestorage.domain.exception.AbsMyFilesException;
import com.samsung.android.settings.analyzestorage.domain.usecase.userinteraction.FileOperationResult;
import com.samsung.android.settings.analyzestorage.presenter.managers.ConvertManager;
import com.samsung.android.settings.analyzestorage.presenter.page.PageInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class MenuExecuteManager implements IExecutable {
    public static final HashMap sCallbackListener = new HashMap();
    public static final SparseArray sExecutionMap;
    public ResultListener mResultListener;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Result {
        public int mCurrentCompletedCount;
        public AbsMyFilesException.ErrorType mErrorType;
        public boolean mIsSuccess;
        public int mMenuType;
        public boolean mNeedRefresh;
        public PageInfo mSrcPageInfo;
        public Bundle mExtra = new Bundle();
        public final List mPackageSuccessList = new ArrayList();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface ResultListener {
        void onResult(Result result);
    }

    static {
        SparseArray sparseArray = new SparseArray();
        sExecutionMap = sparseArray;
        sparseArray.put(FileType.CELL, new MenuExecuteManager$$ExternalSyntheticLambda0(0));
        sparseArray.put(FileType.CHM, new MenuExecuteManager$$ExternalSyntheticLambda0(1));
        sparseArray.put(390, new MenuExecuteManager$$ExternalSyntheticLambda0(2));
        sparseArray.put(400, new MenuExecuteManager$$ExternalSyntheticLambda0(3));
        sparseArray.put(210, new MenuExecuteManager$$ExternalSyntheticLambda0(4));
        sparseArray.put(750, new MenuExecuteManager$$ExternalSyntheticLambda0(5));
    }

    public final void onResult(
            FileOperationResult fileOperationResult,
            int i,
            ExecutionParams executionParams,
            List list) {
        final Result result = new Result();
        result.mIsSuccess = fileOperationResult.mIsSuccess;
        result.mNeedRefresh = fileOperationResult.mNeedRefresh;
        result.mMenuType = i;
        ((ArrayList) result.mPackageSuccessList).addAll(list);
        result.mExtra = fileOperationResult.mBundle;
        result.mErrorType = AbsMyFilesException.ErrorType.ERROR_NONE;
        SparseArray sparseArray = ConvertManager.sSaTypeToPageInfo;
        if (executionParams != null) {
            result.mSrcPageInfo = executionParams.mPageInfo;
        }
        Optional.ofNullable(executionParams)
                .ifPresent(new MenuExecuteManager$$ExternalSyntheticLambda7());
        ResultListener resultListener = this.mResultListener;
        if (resultListener != null) {
            resultListener.onResult(result);
        }
        sCallbackListener.forEach(
                new BiConsumer() { // from class:
                                   // com.samsung.android.settings.analyzestorage.presenter.execution.MenuExecuteManager$$ExternalSyntheticLambda8
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        MenuExecuteManager menuExecuteManager = MenuExecuteManager.this;
                        MenuExecuteManager.Result result2 = result;
                        MenuExecuteManager.ResultListener resultListener2 =
                                (MenuExecuteManager.ResultListener) obj;
                        menuExecuteManager.getClass();
                        boolean booleanValue = ((Boolean) obj2).booleanValue();
                        if (resultListener2 == null
                                || resultListener2.equals(menuExecuteManager.mResultListener)
                                || booleanValue) {
                            return;
                        }
                        resultListener2.onResult(result2);
                    }
                });
        this.mResultListener = null;
    }
}
