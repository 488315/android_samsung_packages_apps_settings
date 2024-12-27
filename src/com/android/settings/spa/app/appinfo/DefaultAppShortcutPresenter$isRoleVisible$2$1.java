package com.android.settings.spa.app.appinfo;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.SafeContinuation;

import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DefaultAppShortcutPresenter$isRoleVisible$2$1 implements Consumer {
    public final /* synthetic */ Continuation $continuation;
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ DefaultAppShortcutPresenter$isRoleVisible$2$1(
            SafeContinuation safeContinuation, int i) {
        this.$r8$classId = i;
        this.$continuation = safeContinuation;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                this.$continuation.resumeWith((Boolean) obj);
                break;
            default:
                this.$continuation.resumeWith((Boolean) obj);
                break;
        }
    }
}
