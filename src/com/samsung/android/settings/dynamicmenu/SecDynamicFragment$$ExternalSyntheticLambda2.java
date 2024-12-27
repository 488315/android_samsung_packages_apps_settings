package com.samsung.android.settings.dynamicmenu;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class SecDynamicFragment$$ExternalSyntheticLambda2
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SecDynamicFragment f$0;

    public /* synthetic */ SecDynamicFragment$$ExternalSyntheticLambda2(
            SecDynamicFragment secDynamicFragment, int i) {
        this.$r8$classId = i;
        this.f$0 = secDynamicFragment;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        SecDynamicFragment secDynamicFragment = this.f$0;
        switch (i) {
            case 0:
                secDynamicFragment.updateMenuFromProvider();
                break;
            default:
                secDynamicFragment.setLoading(false, false);
                break;
        }
    }
}
