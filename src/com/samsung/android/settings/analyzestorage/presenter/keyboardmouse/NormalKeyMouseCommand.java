package com.samsung.android.settings.analyzestorage.presenter.keyboardmouse;

import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class NormalKeyMouseCommand implements KeyMouseCommand {
    public static boolean onMovePageEvent(EventContext eventContext, boolean z) {
        final RecyclerView recyclerView;
        if (eventContext == null
                || eventContext.mPosition != EventContext.EventContextPosition.CONTENTS_PANEL
                || (recyclerView = eventContext.mRecycleView) == null) {
            return false;
        }
        final AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        View focusedChild = recyclerView.getFocusedChild();
        int childCount = z ? 0 : recyclerView.getChildCount() - 1;
        final View childAt = recyclerView.getChildAt(childCount);
        if (childAt != null) {
            if (!childAt.hasFocusable()) {
                childAt = recyclerView.getChildAt(z ? childCount + 1 : childCount - 1);
            }
            if (focusedChild != childAt) {
                Optional.ofNullable(childAt)
                        .ifPresent(
                                new Consumer() { // from class:
                                                 // com.samsung.android.settings.analyzestorage.presenter.keyboardmouse.NormalKeyMouseCommand$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        RecyclerView recyclerView2 = RecyclerView.this;
                                        View view = childAt;
                                        AtomicBoolean atomicBoolean2 = atomicBoolean;
                                        ((View) obj).requestFocus();
                                        recyclerView2.getClass();
                                        int childAdapterPosition =
                                                RecyclerView.getChildAdapterPosition(view);
                                        SparseArray sparseArray =
                                                KeyboardMouseManager.sEventContextMap;
                                        if (childAdapterPosition <= -1) {
                                            childAdapterPosition = 0;
                                        }
                                        ShiftKeyMouseCommand.sStartPosition = childAdapterPosition;
                                        atomicBoolean2.set(false);
                                    }
                                });
            }
        }
        if (!atomicBoolean.get()) {
            return false;
        }
        int lastItemPosition = eventContext.getLastItemPosition();
        boolean z2 = eventContext.mIsExpandable;
        int childAdapterPosition =
                RecyclerView.getChildAdapterPosition(recyclerView.getFocusedChild());
        if (((childAdapterPosition == lastItemPosition || childAdapterPosition == z2)
                        ? -1
                        : z
                                ? Math.max(
                                        (childAdapterPosition - recyclerView.getChildCount()) + 1,
                                        z2 ? 1 : 0)
                                : Math.min(
                                        (recyclerView.getChildCount() + childAdapterPosition) - 1,
                                        lastItemPosition))
                != -1) {
            return onScrollToPosition(eventContext, z);
        }
        return false;
    }

    public static boolean onScrollToPosition(EventContext eventContext, final boolean z) {
        final RecyclerView recyclerView;
        final int i = 0;
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        if (eventContext != null
                && eventContext.mPosition == EventContext.EventContextPosition.CONTENTS_PANEL
                && (recyclerView = eventContext.mRecycleView) != null) {
            if (!z) {
                i = eventContext.getLastItemPosition();
            } else if (eventContext.mIsExpandable) {
                i = 1;
            }
            Optional.ofNullable(recyclerView.getFocusedChild())
                    .ifPresent(new NormalKeyMouseCommand$$ExternalSyntheticLambda1());
            recyclerView.scrollToPosition(i);
            recyclerView
                    .getViewTreeObserver()
                    .addOnGlobalLayoutListener(
                            new ViewTreeObserver
                                    .OnGlobalLayoutListener() { // from class:
                                                                // com.samsung.android.settings.analyzestorage.presenter.keyboardmouse.NormalKeyMouseCommand.1
                                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                                public final void onGlobalLayout() {
                                    int childCount = z ? 0 : recyclerView.getChildCount() - 1;
                                    View childAt = recyclerView.getChildAt(childCount);
                                    if (childAt != null && !childAt.hasFocusable()) {
                                        childAt =
                                                recyclerView.getChildAt(
                                                        z ? childCount + 1 : childCount - 1);
                                    }
                                    Optional ofNullable = Optional.ofNullable(childAt);
                                    final int i2 = i;
                                    final AtomicBoolean atomicBoolean2 = atomicBoolean;
                                    ofNullable.ifPresent(
                                            new Consumer() { // from class:
                                                             // com.samsung.android.settings.analyzestorage.presenter.keyboardmouse.NormalKeyMouseCommand$1$$ExternalSyntheticLambda0
                                                @Override // java.util.function.Consumer
                                                public final void accept(Object obj) {
                                                    int i3 = i2;
                                                    AtomicBoolean atomicBoolean3 = atomicBoolean2;
                                                    ((View) obj).requestFocus();
                                                    SparseArray sparseArray =
                                                            KeyboardMouseManager.sEventContextMap;
                                                    if (i3 <= -1) {
                                                        i3 = 0;
                                                    }
                                                    ShiftKeyMouseCommand.sStartPosition = i3;
                                                    atomicBoolean3.set(true);
                                                }
                                            });
                                    recyclerView
                                            .getViewTreeObserver()
                                            .removeOnGlobalLayoutListener(this);
                                }
                            });
        }
        return atomicBoolean.get();
    }

    @Override // com.samsung.android.settings.analyzestorage.presenter.keyboardmouse.KeyMouseCommand
    public final boolean onKeyDown(
            EventContext eventContext, FragmentActivity fragmentActivity, KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if (keyCode == 67) {
            return true;
        }
        if (keyCode != 112) {
            if (keyCode == 92) {
                return onMovePageEvent(eventContext, true);
            }
            if (keyCode == 93) {
                return onMovePageEvent(eventContext, false);
            }
            if (keyCode == 122) {
                return onScrollToPosition(eventContext, true);
            }
            if (keyCode == 123) {
                return onScrollToPosition(eventContext, false);
            }
        }
        return false;
    }
}
