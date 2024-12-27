package com.android.settings.spa.app.appinfo;

import android.content.Context;

import androidx.compose.runtime.MutableState;

import com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags;
import com.android.settings.R;
import com.android.settingslib.spa.widget.preference.SwitchPreferenceModel;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KFunction;

import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class HibernationSwitchPreferenceKt$HibernationSwitchPreference$2$1
        implements SwitchPreferenceModel {
    public final Function0 changeable;
    public final Function0 checked;
    public final KFunction onCheckedChange;
    public final Function0 summary;
    public final String title;

    public HibernationSwitchPreferenceKt$HibernationSwitchPreference$2$1(
            final Context context,
            HibernationSwitchPresenter hibernationSwitchPresenter,
            MutableState mutableState,
            final MutableState mutableState2,
            final MutableStateFlow mutableStateFlow) {
        String string =
                Flags.archiving()
                        ? context.getString(R.string.unused_apps_switch_v2)
                        : context.getString(R.string.unused_apps_switch);
        Intrinsics.checkNotNull(string);
        this.title = string;
        this.summary =
                new Function0() { // from class:
                                  // com.android.settings.spa.app.appinfo.HibernationSwitchPreferenceKt$HibernationSwitchPreference$2$1$summary$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        return Flags.archiving()
                                ? context.getString(R.string.unused_apps_switch_summary_v2)
                                : context.getString(R.string.unused_apps_switch_summary);
                    }
                };
        this.changeable =
                new HibernationSwitchPreferenceKt$HibernationSwitchPreference$2$1$changeable$1(
                        mutableState);
        this.checked =
                new Function0() { // from class:
                                  // com.android.settings.spa.app.appinfo.HibernationSwitchPreferenceKt$HibernationSwitchPreference$2$1$checked$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        Boolean bool =
                                ((Boolean)
                                                        ((HibernationSwitchPreferenceKt$HibernationSwitchPreference$2$1$changeable$1)
                                                                        HibernationSwitchPreferenceKt$HibernationSwitchPreference$2$1
                                                                                .this
                                                                                .changeable)
                                                                .mo1068invoke())
                                                .booleanValue()
                                        ? (Boolean) mutableState2.getValue()
                                        : Boolean.FALSE;
                        MutableStateFlow mutableStateFlow2 = mutableStateFlow;
                        if (bool != null) {
                            ((StateFlowImpl) mutableStateFlow2).updateState(null, bool);
                        }
                        return bool;
                    }
                };
        this.onCheckedChange =
                new HibernationSwitchPreferenceKt$HibernationSwitchPreference$2$1$onCheckedChange$1(
                        1,
                        hibernationSwitchPresenter,
                        HibernationSwitchPresenter.class,
                        "onCheckedChange",
                        "onCheckedChange(Z)V",
                        0);
    }

    @Override // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel
    public final Function0 getChangeable() {
        return this.changeable;
    }

    @Override // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel
    public final Function0 getChecked() {
        return this.checked;
    }

    @Override // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel
    public final Function1 getOnCheckedChange() {
        return (Function1) this.onCheckedChange;
    }

    @Override // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel
    public final Function0 getSummary() {
        return this.summary;
    }

    @Override // com.android.settingslib.spa.widget.preference.SwitchPreferenceModel
    public final String getTitle() {
        return this.title;
    }
}
