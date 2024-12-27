package com.samsung.android.settings.wifi.develop.homewifi;

import java.util.HashSet;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final /* synthetic */ class AdviserFragment$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ StringBuilder f$0;

    public /* synthetic */ AdviserFragment$$ExternalSyntheticLambda1(int i, StringBuilder sb) {
        this.$r8$classId = i;
        this.f$0 = sb;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        StringBuilder sb = this.f$0;
        String str = (String) obj;
        switch (i) {
            case 0:
                HashSet hashSet = AdviserFragment.mDefaultNames;
                sb.append(str);
                sb.append("\n");
                break;
            case 1:
                HashSet hashSet2 = AdviserFragment.mDefaultNames;
                sb.append(str);
                sb.append("\n");
                break;
            case 2:
                HashSet hashSet3 = AdviserFragment.mDefaultNames;
                sb.append("[");
                sb.append(str);
                sb.append("] ");
                break;
            case 3:
                HashSet hashSet4 = AdviserFragment.mDefaultNames;
                sb.append(str);
                sb.append("\n");
                break;
            default:
                HashSet hashSet5 = AdviserFragment.mDefaultNames;
                sb.append(str);
                sb.append("\n");
                break;
        }
    }
}
