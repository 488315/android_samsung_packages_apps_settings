package com.android.settings.spa.app.appinfo;

import com.android.settings.applications.appinfo.AppInfoDashboardFragment;
import com.android.settings.applications.specialaccess.interactacrossprofiles.InteractAcrossProfilesDetails;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final /* synthetic */
class InteractAcrossProfilesDetailsPreferenceKt$InteractAcrossProfilesDetailsPreference$2$onClick$1
        extends FunctionReferenceImpl implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    /* renamed from: invoke */
    public final Object mo1068invoke() {
        InteractAcrossProfilesDetailsPresenter interactAcrossProfilesDetailsPresenter =
                (InteractAcrossProfilesDetailsPresenter) this.receiver;
        interactAcrossProfilesDetailsPresenter.getClass();
        AppInfoDashboardFragment.startAppInfoFragment(
                InteractAcrossProfilesDetails.class,
                interactAcrossProfilesDetailsPresenter.app,
                interactAcrossProfilesDetailsPresenter.context,
                20);
        return Unit.INSTANCE;
    }
}
