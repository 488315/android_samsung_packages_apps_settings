package com.android.settings.spa.app.appinfo;

import android.content.Intent;
import android.net.Uri;

import com.android.settings.localepicker.AppLocalePickerActivity;
import com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

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
final /* synthetic */ class AppLocalePreferenceKt$AppLocalePreference$2$onClick$1
        extends FunctionReferenceImpl implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    /* renamed from: invoke */
    public final Object mo1068invoke() {
        AppLocalePresenter appLocalePresenter = (AppLocalePresenter) this.receiver;
        appLocalePresenter.getClass();
        Intent intent =
                new Intent(appLocalePresenter.context, (Class<?>) AppLocalePickerActivity.class);
        intent.setData(Uri.parse("package:" + appLocalePresenter.app.packageName));
        intent.putExtra(NetworkAnalyticsConstants.DataPoints.UID, appLocalePresenter.app.uid);
        appLocalePresenter.context.startActivityAsUser(
                intent, ApplicationInfosKt.getUserHandle(appLocalePresenter.app));
        return Unit.INSTANCE;
    }
}
