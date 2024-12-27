package com.samsung.android.settings.languagepack.manager;

import com.samsung.android.settings.languagepack.data.LanguageInfo;
import com.samsung.android.settings.languagepack.data.PackageInfo;

import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class LanguagePackManager$$ExternalSyntheticLambda5
        implements Consumer {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ LanguagePackManager$$ExternalSyntheticLambda5(Stack stack) {
        this.f$0 = stack;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                ((LanguageInfo) obj).mDisplayOrder = ((AtomicInteger) obj2).getAndIncrement();
                break;
            default:
                ((Stack) obj2).add((PackageInfo) obj);
                break;
        }
    }

    public /* synthetic */ LanguagePackManager$$ExternalSyntheticLambda5(
            AtomicInteger atomicInteger) {
        this.f$0 = atomicInteger;
    }
}
