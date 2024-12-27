package com.android.settings.applications.appinfo;

import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */
class DefaultAppShortcutPreferenceControllerBase$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DefaultAppShortcutPreferenceControllerBase f$0;

    public /* synthetic */ DefaultAppShortcutPreferenceControllerBase$$ExternalSyntheticLambda0(
            DefaultAppShortcutPreferenceControllerBase defaultAppShortcutPreferenceControllerBase,
            int i) {
        this.$r8$classId = i;
        this.f$0 = defaultAppShortcutPreferenceControllerBase;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        DefaultAppShortcutPreferenceControllerBase defaultAppShortcutPreferenceControllerBase =
                this.f$0;
        Boolean bool = (Boolean) obj;
        switch (i) {
            case 0:
                defaultAppShortcutPreferenceControllerBase.lambda$new$0(bool);
                break;
            case 1:
                defaultAppShortcutPreferenceControllerBase.lambda$new$1(bool);
                break;
            case 2:
                defaultAppShortcutPreferenceControllerBase.lambda$updateControllerVisibility$2(
                        bool);
                break;
            default:
                defaultAppShortcutPreferenceControllerBase.lambda$updateControllerVisibility$3(
                        bool);
                break;
        }
    }
}
