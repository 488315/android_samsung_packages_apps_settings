package com.samsung.android.settings.analyzestorage.presenter.keyboardmouse;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.presenter.controllers.AbsPageController;
import com.samsung.android.settings.analyzestorage.ui.pages.filelist.AppListBehavior;

import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class EventContext {
    public AbsPageController mController;
    public boolean mIsExpandable;
    public AppListBehavior mListBehavior;
    public EventContextPosition mPosition;
    public RecyclerView mRecycleView;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class EventContextPosition {
        public static final /* synthetic */ EventContextPosition[] $VALUES;
        public static final EventContextPosition CONTENTS_PANEL;

        /* JADX INFO: Fake field, exist only in values array */
        EventContextPosition EF0;

        static {
            EventContextPosition eventContextPosition = new EventContextPosition("NONE", 0);
            EventContextPosition eventContextPosition2 =
                    new EventContextPosition("CONTENTS_PANEL", 1);
            CONTENTS_PANEL = eventContextPosition2;
            $VALUES = new EventContextPosition[] {eventContextPosition, eventContextPosition2};
        }

        public static EventContextPosition valueOf(String str) {
            return (EventContextPosition) Enum.valueOf(EventContextPosition.class, str);
        }

        public static EventContextPosition[] values() {
            return (EventContextPosition[]) $VALUES.clone();
        }
    }

    public final int getFocusedFilePosition() {
        RecyclerView recyclerView = this.mRecycleView;
        if (recyclerView == null) {
            Log.e("EventContext", "can't get selected record position. list view is null");
        } else {
            if (this.mController != null) {
                View focusedChild = recyclerView.getFocusedChild();
                this.mRecycleView.getClass();
                return RecyclerView.getChildAdapterPosition(focusedChild);
            }
            Log.e("EventContext", "can't get selected record position. mController is null");
        }
        return -1;
    }

    public final int getLastItemPosition() {
        if (this.mPosition == EventContextPosition.CONTENTS_PANEL) {
            try {
                Objects.requireNonNull(this.mRecycleView.getAdapter());
                return r2.getItemCount() - 1;
            } catch (NullPointerException unused) {
                Log.e("EventContext", "getLastItemPosition - adapter is null");
            }
        }
        return 0;
    }
}
