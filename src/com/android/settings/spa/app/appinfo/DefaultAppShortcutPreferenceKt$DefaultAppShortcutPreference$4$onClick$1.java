package com.android.settings.spa.app.appinfo;

import android.content.Intent;

import com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt;

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
final /* synthetic */ class DefaultAppShortcutPreferenceKt$DefaultAppShortcutPreference$4$onClick$1
        extends FunctionReferenceImpl implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    /* renamed from: invoke */
    public final Object mo1068invoke() {
        DefaultAppShortcutPresenter defaultAppShortcutPresenter =
                (DefaultAppShortcutPresenter) this.receiver;
        defaultAppShortcutPresenter.getClass();
        Intent intent = new Intent("android.intent.action.MANAGE_DEFAULT_APP");
        intent.putExtra("android.intent.extra.ROLE_NAME", defaultAppShortcutPresenter.roleName);
        defaultAppShortcutPresenter.context.startActivityAsUser(
                intent, ApplicationInfosKt.getUserHandle(defaultAppShortcutPresenter.app));
        return Unit.INSTANCE;
    }
}
