package com.android.settings.applications.manageapplications;

import android.view.View;
import android.view.ViewGroup;

import com.android.settingslib.applications.ApplicationsState;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class ManageApplications$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ ManageApplications$$ExternalSyntheticLambda1(
            int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                View view = (View) this.f$0;
                ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams) this.f$1;
                boolean z = ManageApplications.DEBUG;
                view.setLayoutParams(layoutParams);
                view.requestLayout();
                break;
            default:
                ManageApplications.ApplicationsAdapter applicationsAdapter =
                        (ManageApplications.ApplicationsAdapter) this.f$0;
                applicationsAdapter.mSession.rebuild(
                        (ApplicationsState.AppFilter) this.f$1,
                        applicationsAdapter.getComparatorByLastSortMode(),
                        false);
                break;
        }
    }
}
