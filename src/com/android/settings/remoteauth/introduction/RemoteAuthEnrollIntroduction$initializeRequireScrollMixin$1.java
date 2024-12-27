package com.android.settings.remoteauth.introduction;

import android.view.View;

import androidx.navigation.NavController;

import com.android.settings.R;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class RemoteAuthEnrollIntroduction$initializeRequireScrollMixin$1
        implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ RemoteAuthEnrollIntroduction $tmp0;

    public /* synthetic */ RemoteAuthEnrollIntroduction$initializeRequireScrollMixin$1(
            RemoteAuthEnrollIntroduction remoteAuthEnrollIntroduction, int i) {
        this.$r8$classId = i;
        this.$tmp0 = remoteAuthEnrollIntroduction;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View p0) {
        switch (this.$r8$classId) {
            case 0:
                Intrinsics.checkNotNullParameter(p0, "p0");
                ((NavController) this.$tmp0.navController$delegate.getValue())
                        .navigate(R.id.action_introduction_to_enrolling);
                break;
            case 1:
                Intrinsics.checkNotNullParameter(p0, "p0");
                ((NavController) this.$tmp0.navController$delegate.getValue())
                        .navigate(R.id.action_introduction_to_enrolling);
                break;
            default:
                Intrinsics.checkNotNullParameter(p0, "p0");
                ((NavController) this.$tmp0.navController$delegate.getValue()).navigateUp();
                break;
        }
    }
}
