package com.android.settings.spa.app.appinfo;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final /* synthetic */
class HibernationSwitchPreferenceKt$HibernationSwitchPreference$2$1$onCheckedChange$1
        extends FunctionReferenceImpl implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        Boolean bool = (Boolean) obj;
        boolean booleanValue = bool.booleanValue();
        HibernationSwitchPresenter hibernationSwitchPresenter =
                (HibernationSwitchPresenter) this.receiver;
        hibernationSwitchPresenter.getClass();
        try {
            hibernationSwitchPresenter.appOpsManager.setUidMode(
                    97, hibernationSwitchPresenter.app.uid, !booleanValue ? 1 : 0);
            if (!booleanValue) {
                hibernationSwitchPresenter.appHibernationManager.setHibernatingForUser(
                        hibernationSwitchPresenter.app.packageName, false);
                hibernationSwitchPresenter.appHibernationManager.setHibernatingGlobally(
                        hibernationSwitchPresenter.app.packageName, false);
            }
            hibernationSwitchPresenter.isChecked.overrideChannel.mo1469trySendJP2dKIU(bool);
        } catch (RuntimeException unused) {
        }
        return Unit.INSTANCE;
    }
}
