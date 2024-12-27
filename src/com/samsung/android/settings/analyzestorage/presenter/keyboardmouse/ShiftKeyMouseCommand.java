package com.samsung.android.settings.analyzestorage.presenter.keyboardmouse;

import android.view.KeyEvent;

import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.presenter.controllers.AbsPageController;
import com.samsung.android.settings.analyzestorage.presenter.controllers.FileListController;
import com.samsung.android.settings.analyzestorage.presenter.controllers.filelist.ListItemHandler;

import java.lang.reflect.Field;
import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ShiftKeyMouseCommand implements KeyMouseCommand {
    public static int sStartPosition;
    public int mShiftSelectionStartPos;

    public static void clearMetaState(KeyEvent keyEvent) {
        try {
            Field declaredField = keyEvent.getClass().getDeclaredField("mMetaState");
            declaredField.setAccessible(true);
            declaredField.setInt(keyEvent, 0);
        } catch (IllegalAccessException e) {
            Log.e("ShiftKeyMouseCommand", "IllegalAccessException:" + e.getMessage());
        } catch (NoSuchFieldException e2) {
            Log.e("ShiftKeyMouseCommand", "NoSuchFieldException:" + e2.getMessage());
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0048, code lost:

       if ((r13 % r1) == (r1 - 1)) goto L43;
    */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x007e, code lost:

       r14 = r13 + 1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x004d, code lost:

       if ((r13 % r1) == 0) goto L43;
    */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0083, code lost:

       r14 = r13 - 1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x007c, code lost:

       if (r13 < (r5.getItemCount() - 1)) goto L46;
    */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0081, code lost:

       if (r13 > 0) goto L48;
    */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00e5 A[SYNTHETIC] */
    @Override // com.samsung.android.settings.analyzestorage.presenter.keyboardmouse.KeyMouseCommand
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onKeyDown(
            com.samsung.android.settings.analyzestorage.presenter.keyboardmouse.EventContext r13,
            androidx.fragment.app.FragmentActivity r14,
            android.view.KeyEvent r15) {
        /*
            Method dump skipped, instructions count: 270
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.analyzestorage.presenter.keyboardmouse.ShiftKeyMouseCommand.onKeyDown(com.samsung.android.settings.analyzestorage.presenter.keyboardmouse.EventContext,"
                    + " androidx.fragment.app.FragmentActivity, android.view.KeyEvent):boolean");
    }

    @Override // com.samsung.android.settings.analyzestorage.presenter.keyboardmouse.KeyMouseCommand
    public final void onMouseDown(AbsPageController absPageController, int i) {
        if (i <= -1) {
            Log.e("ShiftKeyMouseCommand", "shiftMouseClick - lastPosition " + i);
            return;
        }
        if (absPageController == null || !absPageController.isFileListController()) {
            return;
        }
        int i2 = sStartPosition;
        ListItemHandler listItemHandler = ((FileListController) absPageController).mListItemHandler;
        listItemHandler.resetCheckedItemInfo();
        if (i == i2) {
            listItemHandler.setItemChecked(i, true);
            return;
        }
        int min = Math.min(i2, i);
        int max = Math.max(i2, i);
        Log.d("ShiftKeyMouseCommand", "onShiftMouseClick from : " + min + "  to : " + max);
        while (min < max) {
            ((ArrayList) listItemHandler.mCheckedItemList).add(listItemHandler.getItemAt(min));
            listItemHandler.mIsSelectedMousePoint =
                    !((ArrayList) listItemHandler.mCheckedItemList).isEmpty();
            min++;
        }
        listItemHandler.setItemChecked(max, true);
    }
}
