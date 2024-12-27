package com.android.settings.dashboard;

import androidx.lifecycle.LifecycleObserver;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;

import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class DashboardFragment$$ExternalSyntheticLambda6 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ DashboardFragment$$ExternalSyntheticLambda6(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                ((AbstractPreferenceController) obj).displayPreference((PreferenceScreen) obj2);
                break;
            default:
                Lifecycle lifecycle = (Lifecycle) obj2;
                Object obj3 = (BasePreferenceController) obj;
                if (obj3 instanceof LifecycleObserver) {
                    lifecycle.addObserver((LifecycleObserver) obj3);
                    break;
                }
                break;
        }
    }
}
