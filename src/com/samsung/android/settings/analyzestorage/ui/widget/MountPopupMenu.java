package com.samsung.android.settings.analyzestorage.ui.widget;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.PopupMenu;

import com.android.settings.R;

import com.samsung.android.settings.analyzestorage.data.constant.DomainType;
import com.samsung.android.settings.analyzestorage.presenter.managers.StorageOperationManager;
import com.samsung.android.settings.analyzestorage.presenter.managers.StorageVolumeManager;

import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class MountPopupMenu implements PopupMenu.OnMenuItemClickListener {
    public final Context context;
    public final PopupMenu popupMenu;

    public MountPopupMenu(Context context, View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        this.context = context;
        boolean z = false;
        PopupMenu popupMenu = new PopupMenu(context, view, 0);
        this.popupMenu = popupMenu;
        popupMenu.inflate(R.menu.as_home_list_more_option_menu);
        MenuBuilder menuBuilder = popupMenu.mMenu;
        Intrinsics.checkNotNullExpressionValue(menuBuilder, "getMenu(...)");
        if (StorageVolumeManager.mounted(1)) {
            menuBuilder.removeItem(R.id.mount);
            if (DomainType.isUsb(1)) {
                synchronized (StorageVolumeManager.class) {
                    try {
                        Iterator it =
                                ((ArrayList) StorageVolumeManager.sStorageMountInfoList).iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            StorageVolumeManager.StorageMountInfo storageMountInfo =
                                    (StorageVolumeManager.StorageMountInfo) it.next();
                            if (1 == storageMountInfo.mDomainType) {
                                z = "ntfs".equalsIgnoreCase(storageMountInfo.mFsType);
                            }
                        }
                    } finally {
                    }
                }
            }
            if (z) {
                menuBuilder.removeItem(R.id.format);
            }
        } else {
            menuBuilder.removeItem(R.id.format);
            menuBuilder.removeItem(R.id.unmount);
        }
        this.popupMenu.mMenuItemClickListener = this;
    }

    @Override // androidx.appcompat.widget.PopupMenu.OnMenuItemClickListener
    public final void onMenuItemClick(MenuItem menuItem) {
        if (menuItem != null) {
            StorageOperationManager storageOperationManager =
                    StorageOperationManager.StorageOperationManagerHolder.INSTANCE;
            Context context = this.context;
            int itemId = menuItem.getItemId();
            storageOperationManager.storageOperation(
                    context,
                    itemId == R.id.format
                            ? 3
                            : itemId == R.id.mount ? 0 : itemId == R.id.unmount ? 1 : 999,
                    1);
        }
        this.popupMenu.mMenuItemClickListener = null;
    }
}
