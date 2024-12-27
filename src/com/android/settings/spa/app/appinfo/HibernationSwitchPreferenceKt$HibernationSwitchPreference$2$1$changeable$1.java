package com.android.settings.spa.app.appinfo;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.State;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\n\n"
                + "\u0000\n"
                + "\u0002\u0010\u000b\n"
                + "\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001H\n"
                + "¢\u0006\u0004\b\u0002\u0010\u0003"
        },
        d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "invoke", "()Ljava/lang/Boolean;"},
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class HibernationSwitchPreferenceKt$HibernationSwitchPreference$2$1$changeable$1
        extends Lambda implements Function0 {
    final /* synthetic */ State $isEligibleState$delegate;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HibernationSwitchPreferenceKt$HibernationSwitchPreference$2$1$changeable$1(
            MutableState mutableState) {
        super(0);
        this.$isEligibleState$delegate = mutableState;
    }

    @Override // kotlin.jvm.functions.Function0
    /* renamed from: invoke */
    public final Object mo1068invoke() {
        Boolean bool = (Boolean) this.$isEligibleState$delegate.getValue();
        bool.booleanValue();
        return bool;
    }
}
