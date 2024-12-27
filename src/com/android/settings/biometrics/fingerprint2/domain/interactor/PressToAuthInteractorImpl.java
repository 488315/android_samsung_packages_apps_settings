package com.android.settings.biometrics.fingerprint2.domain.interactor;

import android.R;
import android.content.Context;
import android.provider.Settings;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PressToAuthInteractorImpl {
    public final Context context;

    public PressToAuthInteractorImpl(Context context, CoroutineDispatcher backgroundDispatcher) {
        Intrinsics.checkNotNullParameter(backgroundDispatcher, "backgroundDispatcher");
        this.context = context;
        FlowKt.flowOn(FlowKt.callbackFlow(new PressToAuthInteractorImpl$isEnabled$1(this, null)), backgroundDispatcher);
    }

    /* JADX WARN: Type inference failed for: r0v6, types: [boolean, int] */
    public static final boolean access$getPressToAuth(PressToAuthInteractorImpl pressToAuthInteractorImpl) {
        int intForUser = Settings.Secure.getIntForUser(pressToAuthInteractorImpl.context.getContentResolver(), "sfps_performant_auth_enabled_v2", -1, pressToAuthInteractorImpl.context.getUserId());
        int i = intForUser;
        if (intForUser == -1) {
            ?? r0 = pressToAuthInteractorImpl.context.getResources().getBoolean(R.bool.config_reduceBrightColorsAvailable);
            Settings.Secure.putIntForUser(pressToAuthInteractorImpl.context.getContentResolver(), "sfps_performant_auth_enabled_v2", r0, pressToAuthInteractorImpl.context.getUserId());
            i = r0;
        }
        return i == 1;
    }
}
