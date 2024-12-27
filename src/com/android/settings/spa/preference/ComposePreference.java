package com.android.settings.spa.preference;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.platform.ComposeView;
import androidx.compose.ui.platform.ViewCompositionStrategy$DisposeOnViewTreeLifecycleDestroyed;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settingslib.spa.framework.theme.SettingsThemeKt;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000$\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0004\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\b\u0003\b\u0017\u0018\u00002\u00020\u0001B1\b\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\n"
                + "\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0004\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0006\u0012\b\b\u0002\u0010\b\u001a\u00020\u0006¢\u0006\u0004\b"
                + "\t\u0010\n"
                + "J\u000f\u0010\f\u001a\u00020\u000bH\u0007¢\u0006\u0004\b\f\u0010\r"
                + "¨\u0006\u000e"
        },
        d2 = {
            "Lcom/android/settings/spa/preference/ComposePreference;",
            "Landroidx/preference/Preference;",
            "Landroid/content/Context;",
            "context",
            "Landroid/util/AttributeSet;",
            "attrs",
            ApnSettings.MVNO_NONE,
            "defStyleAttr",
            "defStyleRes",
            "<init>",
            "(Landroid/content/Context;Landroid/util/AttributeSet;II)V",
            ApnSettings.MVNO_NONE,
            "Content",
            "(Landroidx/compose/runtime/Composer;I)V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public class ComposePreference extends Preference {
    public Function2 content;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ComposePreference(Context context) {
        this(context, null, 0, 0, 14, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public final void Content(Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(886305529);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        this.content.invoke(composerImpl, 0);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.preference.ComposePreference$Content$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            ComposePreference.this.Content(
                                    (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.mDividerAllowedAbove = false;
        preferenceViewHolder.mDividerAllowedBelow = false;
        View view = preferenceViewHolder.itemView;
        Intrinsics.checkNotNull(
                view,
                "null cannot be cast to non-null type androidx.compose.ui.platform.ComposeView");
        ComposeView composeView = (ComposeView) view;
        ViewCompositionStrategy$DisposeOnViewTreeLifecycleDestroyed
                viewCompositionStrategy$DisposeOnViewTreeLifecycleDestroyed =
                        ViewCompositionStrategy$DisposeOnViewTreeLifecycleDestroyed.INSTANCE;
        Function0 function0 = composeView.disposeViewCompositionStrategy;
        if (function0 != null) {
            function0.mo1068invoke();
        }
        composeView.disposeViewCompositionStrategy =
                viewCompositionStrategy$DisposeOnViewTreeLifecycleDestroyed.installFor(composeView);
        composeView.setContent(
                new ComposableLambdaImpl(
                        -528742937,
                        true,
                        new Function2() { // from class:
                                          // com.android.settings.spa.preference.ComposePreference$onBindViewHolder$1$1
                            {
                                super(2);
                            }

                            /* JADX WARN: Type inference failed for: r3v5, types: [com.android.settings.spa.preference.ComposePreference$onBindViewHolder$1$1$1, kotlin.jvm.internal.Lambda] */
                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                Composer composer = (Composer) obj;
                                if ((((Number) obj2).intValue() & 11) == 2) {
                                    ComposerImpl composerImpl = (ComposerImpl) composer;
                                    if (composerImpl.getSkipping()) {
                                        composerImpl.skipToGroupEnd();
                                        return Unit.INSTANCE;
                                    }
                                }
                                OpaqueKey opaqueKey = ComposerKt.invocation;
                                final ComposePreference composePreference = ComposePreference.this;
                                SettingsThemeKt.SettingsTheme(
                                        ComposableLambdaKt.rememberComposableLambda(
                                                930574370,
                                                new Function2() { // from class:
                                                                  // com.android.settings.spa.preference.ComposePreference$onBindViewHolder$1$1.1
                                                    {
                                                        super(2);
                                                    }

                                                    @Override // kotlin.jvm.functions.Function2
                                                    public final Object invoke(
                                                            Object obj3, Object obj4) {
                                                        Composer composer2 = (Composer) obj3;
                                                        if ((((Number) obj4).intValue() & 11)
                                                                == 2) {
                                                            ComposerImpl composerImpl2 =
                                                                    (ComposerImpl) composer2;
                                                            if (composerImpl2.getSkipping()) {
                                                                composerImpl2.skipToGroupEnd();
                                                                return Unit.INSTANCE;
                                                            }
                                                        }
                                                        OpaqueKey opaqueKey2 =
                                                                ComposerKt.invocation;
                                                        ComposePreference.this.content.invoke(
                                                                composer2, 0);
                                                        return Unit.INSTANCE;
                                                    }
                                                },
                                                composer),
                                        composer,
                                        6);
                                return Unit.INSTANCE;
                            }
                        }));
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ComposePreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ComposePreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public /* synthetic */ ComposePreference(
            Context context,
            AttributeSet attributeSet,
            int i,
            int i2,
            int i3,
            DefaultConstructorMarker defaultConstructorMarker) {
        this(
                context,
                (i3 & 2) != 0 ? null : attributeSet,
                (i3 & 4) != 0 ? 0 : i,
                (i3 & 8) != 0 ? 0 : i2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ComposePreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        Intrinsics.checkNotNullParameter(context, "context");
        this.content = ComposableSingletons$ComposePreferenceKt.f61lambda1;
        setLayoutResource(R.layout.preference_compose);
        setSelectable(false);
    }
}
