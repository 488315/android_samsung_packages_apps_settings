package com.android.settingslib.spaprivileged.template.scaffold;

import android.content.Context;

import com.android.settingslib.spaprivileged.model.enterprise.Restrictions;
import com.android.settingslib.spaprivileged.model.enterprise.RestrictionsProviderImpl;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final /* synthetic */ class RestrictedMenuItemKt$RestrictedMenuItem$1 extends FunctionReferenceImpl
        implements Function2 {
    public static final RestrictedMenuItemKt$RestrictedMenuItem$1 INSTANCE =
            new RestrictedMenuItemKt$RestrictedMenuItem$1();

    public RestrictedMenuItemKt$RestrictedMenuItem$1() {
        super(
                2,
                RestrictionsProviderImpl.class,
                "<init>",
                "<init>(Landroid/content/Context;Lcom/android/settingslib/spaprivileged/model/enterprise/Restrictions;)V",
                0);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Context p0 = (Context) obj;
        Restrictions p1 = (Restrictions) obj2;
        Intrinsics.checkNotNullParameter(p0, "p0");
        Intrinsics.checkNotNullParameter(p1, "p1");
        return new RestrictionsProviderImpl(p0, p1);
    }
}
