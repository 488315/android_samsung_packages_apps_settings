package com.samsung.android.settings.analyzestorage.presenter.controllers.menu;

import android.os.Bundle;

import com.samsung.android.settings.analyzestorage.data.model.CommonFileInfo;
import com.samsung.android.settings.analyzestorage.domain.entity.FileInfo;
import com.samsung.android.settings.analyzestorage.domain.exception.AbsMyFilesException;
import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.presenter.constant.MenuType;
import com.samsung.android.settings.analyzestorage.presenter.controllers.AbsPageController;
import com.samsung.android.settings.analyzestorage.presenter.execution.AbsExecute;
import com.samsung.android.settings.analyzestorage.presenter.execution.ExecutionParams;
import com.samsung.android.settings.analyzestorage.presenter.execution.MenuExecuteManager;
import com.samsung.android.settings.analyzestorage.presenter.execution.MenuExecuteManager$$ExternalSyntheticLambda0;
import com.samsung.android.settings.analyzestorage.presenter.execution.MenuExecuteManager$$ExternalSyntheticLambda6;
import com.samsung.android.settings.analyzestorage.presenter.managers.SamsungAnalyticsConvertManager;
import com.samsung.android.settings.analyzestorage.presenter.managers.StorageVolumeManager;
import com.samsung.android.settings.analyzestorage.presenter.page.NavigationMode;
import com.samsung.android.settings.analyzestorage.presenter.page.PageInfo;
import com.samsung.android.settings.analyzestorage.presenter.page.PageType;
import com.samsung.android.settings.analyzestorage.ui.exception.ExceptionHandler;
import com.samsung.android.settings.analyzestorage.ui.menu.PrepareMenu;

import kotlin.jvm.internal.Intrinsics;

import java.util.EnumMap;
import java.util.Optional;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class MenuDirector {
    public final AbsPageController controller;
    public AbsMenu menu;

    public MenuDirector(AbsPageController controller) {
        Intrinsics.checkNotNullParameter(controller, "controller");
        this.controller = controller;
    }

    public final boolean execute(int i, PrepareMenu prepareMenu) {
        AbsMenu cancelMenu;
        AbsPageController controller = this.controller;
        if (i == 210) {
            Intrinsics.checkNotNullParameter(controller, "controller");
            cancelMenu = new CancelMenu(controller);
        } else if (i == 340) {
            cancelMenu = new UninstallMenu(controller, prepareMenu);
        } else if (i == 350) {
            cancelMenu = new ClearAppCacheMenu(controller, prepareMenu);
        } else if (i == 390) {
            Intrinsics.checkNotNullParameter(controller, "controller");
            cancelMenu = new UnmountMenu(controller);
        } else if (i == 400) {
            cancelMenu = new FormatMenu(controller, prepareMenu);
        } else if (i != 750) {
            cancelMenu = null;
        } else {
            Intrinsics.checkNotNullParameter(controller, "controller");
            cancelMenu = new ArchiveMenu(controller, prepareMenu);
        }
        this.menu = cancelMenu;
        ExecutionParams params =
                cancelMenu != null
                        ? cancelMenu.getParams()
                        : new ExecutionParams(controller.mContext);
        MenuExecuteManager menuExecuteManager = new MenuExecuteManager();
        menuExecuteManager.mResultListener = null;
        controller.getClass();
        Log.d("MenuExecuteManager", "execute() ] menuType : " + MenuType.getMenuName(i));
        AbsExecute absExecute =
                (AbsExecute)
                        Optional.ofNullable(
                                        (MenuExecuteManager$$ExternalSyntheticLambda0)
                                                MenuExecuteManager.sExecutionMap.get(i))
                                .map(new MenuExecuteManager$$ExternalSyntheticLambda6())
                                .orElse(null);
        if (absExecute == null) {
            return true;
        }
        menuExecuteManager.mResultListener = controller;
        return absExecute.onExecute(i, params, menuExecuteManager);
    }

    public final void showOpResult(
            MenuExecuteManager.Result result, ExceptionHandler exceptionHandler) {
        AbsMyFilesException.ErrorType errorType;
        FileInfo fileInfo;
        if (exceptionHandler != null) {
            Bundle bundle = result.mExtra;
            Intrinsics.checkNotNullExpressionValue(bundle, "getExtra(...)");
            AbsPageController absPageController = this.controller;
            Intrinsics.checkNotNullExpressionValue(absPageController.mContext, "getContext(...)");
            PageInfo pageInfo = absPageController.mPageInfo;
            Intrinsics.checkNotNullExpressionValue(pageInfo, "getPageInfo(...)");
            bundle.putInt("selectedType", 0);
            if (result.mIsSuccess
                    || (errorType = result.mErrorType) == null
                    || errorType == AbsMyFilesException.ErrorType.ERROR_NONE) {
                return;
            }
            bundle.putString("path", pageInfo.getPath());
            bundle.putInt("menuType", result.mMenuType);
            PageInfo pageInfo2 = result.mSrcPageInfo;
            bundle.putInt(
                    "targetStorage",
                    (pageInfo2 == null || (fileInfo = pageInfo2.mFileInfo) == null)
                            ? result.mExtra.getInt("targetStorage", -1)
                            : ((CommonFileInfo) fileInfo).getDomainType());
            EnumMap enumMap = SamsungAnalyticsConvertManager.sOverViewInternalExpandToSAEventId;
            PageType pageType = pageInfo.mPageType;
            NavigationMode navigationMode = pageInfo.mNavigationMode;
            navigationMode.getClass();
            if (navigationMode == NavigationMode.AnalyzeStorageFilesFromDC) {
                pageType = PageType.ANALYZE_STORAGE_DEVICE_CARE;
            } else if (PageType.LOCAL_SDCARD == pageType
                    && !StorageVolumeManager.sIsSdcardMounted) {
                pageType = PageType.LOCAL_SDCARD_UNMOUNT;
            } else if (PageType.LOCAL_USB == pageType
                    && !StorageVolumeManager.sIsUsbStorageMounted) {
                pageType = PageType.LOCAL_USB_UNMOUNT;
            }
            bundle.putString("pageType", pageType.toString());
            AbsMyFilesException.ErrorType mErrorType = result.mErrorType;
            Intrinsics.checkNotNullExpressionValue(mErrorType, "mErrorType");
        }
    }
}
