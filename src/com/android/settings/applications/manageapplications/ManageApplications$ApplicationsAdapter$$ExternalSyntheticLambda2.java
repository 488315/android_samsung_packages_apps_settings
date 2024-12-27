package com.android.settings.applications.manageapplications;

import android.widget.LinearLayout;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class ManageApplications$ApplicationsAdapter$$ExternalSyntheticLambda2
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ManageApplications$ApplicationsAdapter$$ExternalSyntheticLambda2(
            int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                LinearLayout linearLayout = (LinearLayout) obj;
                if (linearLayout.getBackground() != null) {
                    linearLayout
                            .getBackground()
                            .setHotspot(linearLayout.getWidth() / 2, linearLayout.getHeight() / 2);
                }
                linearLayout.setPressed(true);
                break;
            case 1:
                ((LinearLayout) obj).setPressed(false);
                break;
            case 2:
                LinearLayout linearLayout2 = (LinearLayout) obj;
                if (linearLayout2.getBackground() != null) {
                    linearLayout2
                            .getBackground()
                            .setHotspot(
                                    linearLayout2.getWidth() / 2, linearLayout2.getHeight() / 2);
                }
                linearLayout2.setPressed(true);
                break;
            case 3:
                ((LinearLayout) obj).setPressed(false);
                break;
            default:
                ApplicationViewHolder applicationViewHolder = (ApplicationViewHolder) obj;
                if (applicationViewHolder.itemView.isPressed()) {
                    applicationViewHolder.itemView.setPressed(false);
                    break;
                }
                break;
        }
    }
}
