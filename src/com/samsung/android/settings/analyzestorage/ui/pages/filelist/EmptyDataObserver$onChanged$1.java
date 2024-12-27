package com.samsung.android.settings.analyzestorage.ui.pages.filelist;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class EmptyDataObserver$onChanged$1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ EmptyDataObserver this$0;

    public /* synthetic */ EmptyDataObserver$onChanged$1(
            EmptyDataObserver emptyDataObserver, int i) {
        this.$r8$classId = i;
        this.this$0 = emptyDataObserver;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                EmptyDataObserver.access$waitRecyclerViewAnimationToFinish(this.this$0);
                break;
            default:
                EmptyDataObserver.access$waitRecyclerViewAnimationToFinish(this.this$0);
                break;
        }
    }
}
