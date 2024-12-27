package com.samsung.android.settings.analyzestorage.presenter.keyboardmouse;

import android.view.KeyEvent;

import androidx.fragment.app.FragmentActivity;

import com.samsung.android.settings.analyzestorage.presenter.controllers.AbsPageController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public interface KeyMouseCommand {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface IMouseScroll {}

    boolean onKeyDown(
            EventContext eventContext, FragmentActivity fragmentActivity, KeyEvent keyEvent);

    default boolean onMouseScroll(IMouseScroll iMouseScroll) {
        return false;
    }

    default void onMouseDown(AbsPageController absPageController, int i) {}
}
