package com.samsung.android.settings.analyzestorage.ui.menu;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManagerImpl;

import com.android.settings.R;

import com.samsung.android.settings.analyzestorage.presenter.ActivityInfoStore;
import com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.ListItemHandler;
import com.samsung.android.settings.analyzestorage.ui.dialog.AbsDialog;
import com.samsung.android.settings.analyzestorage.ui.dialog.ConfirmAppInfoDialogFragment;
import com.samsung.android.settings.analyzestorage.ui.dialog.ConfirmAppInfoInterface;
import com.samsung.android.settings.analyzestorage.ui.dialog.ConfirmArchive;
import com.samsung.android.settings.analyzestorage.ui.dialog.ConfirmClearAppCache;
import com.samsung.android.settings.analyzestorage.ui.dialog.ConfirmUninstall;
import com.samsung.android.settings.analyzestorage.ui.dialog.FormatDialogFragment;
import com.samsung.android.settings.analyzestorage.ui.dialog.SpinnerProgressDialogFragment;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class PrepareMenu {
    public int instance;

    public final ConfirmAppInfoDialogFragment getConfirmAppInfoDialog(
            ListItemHandler listItemHandler, ConfirmAppInfoInterface confirmAppInfoInterface) {
        List list = listItemHandler != null ? listItemHandler.mCheckedItemList : null;
        if (!(list instanceof List)) {
            list = null;
        }
        if (list == null) {
            return null;
        }
        ConfirmAppInfoDialogFragment confirmAppInfoDialogFragment =
                new ConfirmAppInfoDialogFragment();
        confirmAppInfoDialogFragment.appList = list;
        confirmAppInfoDialogFragment.confirmApp = confirmAppInfoInterface;
        confirmAppInfoDialogFragment.baseFragmentManager = getFragmentManager();
        return confirmAppInfoDialogFragment;
    }

    public final AbsDialog getDialog(int i, int i2, ListItemHandler listItemHandler) {
        this.instance = i2;
        if (i == 340) {
            return getConfirmAppInfoDialog(listItemHandler, new ConfirmUninstall());
        }
        if (i == 350) {
            return getConfirmAppInfoDialog(listItemHandler, new ConfirmClearAppCache());
        }
        if (i != 400) {
            if (i != 750) {
                return null;
            }
            return getConfirmAppInfoDialog(listItemHandler, new ConfirmArchive());
        }
        FormatDialogFragment formatDialogFragment = new FormatDialogFragment();
        formatDialogFragment.baseFragmentManager = getFragmentManager();
        return formatDialogFragment;
    }

    public final FragmentManagerImpl getFragmentManager() {
        Context context = ActivityInfoStore.context;
        FragmentActivity fragmentActivity =
                ActivityInfoStore.Companion.getInstance(this.instance).getFragmentActivity();
        if (fragmentActivity != null) {
            return fragmentActivity.getSupportFragmentManager();
        }
        return null;
    }

    public final SpinnerProgressDialogFragment getProgressDialog(int i) {
        int i2 = i == 750 ? R.string.as_archiving : R.string.as_deleting;
        SpinnerProgressDialogFragment spinnerProgressDialogFragment =
                new SpinnerProgressDialogFragment();
        spinnerProgressDialogFragment.textResId = i2;
        spinnerProgressDialogFragment.baseFragmentManager = getFragmentManager();
        return spinnerProgressDialogFragment;
    }
}
