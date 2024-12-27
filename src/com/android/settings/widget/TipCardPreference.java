package com.android.settings.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.res.VectorResources_androidKt;

import com.android.settings.spa.preference.ComposePreference;
import com.android.settingslib.spa.widget.card.CardButton;
import com.android.settingslib.spa.widget.card.CardModel;
import com.android.settingslib.spa.widget.card.SettingsCardKt;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0016\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u001d\b\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\n"
                + "\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0004¢\u0006\u0004\b\u0006\u0010\u0007¨\u0006\b"
        },
        d2 = {
            "Lcom/android/settings/widget/TipCardPreference;",
            "Lcom/android/settings/spa/preference/ComposePreference;",
            "Landroid/content/Context;",
            "context",
            "Landroid/util/AttributeSet;",
            "attr",
            "<init>",
            "(Landroid/content/Context;Landroid/util/AttributeSet;)V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class TipCardPreference extends ComposePreference {
    public Integer iconResId;
    public Function0 onDismiss;
    public Function0 primaryButtonAction;
    public String primaryButtonContentDescription;
    public String primaryButtonText;
    public boolean primaryButtonVisibility;
    public Function0 secondaryButtonAction;
    public String secondaryButtonText;
    public boolean secondaryButtonVisibility;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public TipCardPreference(Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public final void buildContent() {
        this.content =
                new ComposableLambdaImpl(
                        -310043838,
                        true,
                        new Function2() { // from class:
                                          // com.android.settings.widget.TipCardPreference$buildContent$1
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                String obj3;
                                String obj4;
                                Composer composer = (Composer) obj;
                                if ((((Number) obj2).intValue() & 11) == 2) {
                                    ComposerImpl composerImpl = (ComposerImpl) composer;
                                    if (composerImpl.getSkipping()) {
                                        composerImpl.skipToGroupEnd();
                                        return Unit.INSTANCE;
                                    }
                                }
                                OpaqueKey opaqueKey = ComposerKt.invocation;
                                CharSequence title = TipCardPreference.this.getTitle();
                                String str =
                                        (title == null || (obj4 = title.toString()) == null)
                                                ? ApnSettings.MVNO_NONE
                                                : obj4;
                                CharSequence summary = TipCardPreference.this.getSummary();
                                String str2 =
                                        (summary == null || (obj3 = summary.toString()) == null)
                                                ? ApnSettings.MVNO_NONE
                                                : obj3;
                                TipCardPreference tipCardPreference = TipCardPreference.this;
                                CardButton cardButton =
                                        tipCardPreference.primaryButtonVisibility
                                                ? new CardButton(
                                                        tipCardPreference.primaryButtonText,
                                                        tipCardPreference
                                                                .primaryButtonContentDescription,
                                                        tipCardPreference.primaryButtonAction)
                                                : null;
                                TipCardPreference tipCardPreference2 = TipCardPreference.this;
                                List filterNotNull =
                                        ArraysKt___ArraysKt.filterNotNull(
                                                new CardButton[] {
                                                    cardButton,
                                                    tipCardPreference2.secondaryButtonVisibility
                                                            ? new CardButton(
                                                                    tipCardPreference2
                                                                            .secondaryButtonText,
                                                                    null,
                                                                    tipCardPreference2
                                                                            .secondaryButtonAction)
                                                            : null
                                                });
                                TipCardPreference tipCardPreference3 = TipCardPreference.this;
                                Function0 function0 = tipCardPreference3.onDismiss;
                                Integer num = tipCardPreference3.iconResId;
                                if (num == null || num.intValue() == 0) {
                                    num = null;
                                }
                                ComposerImpl composerImpl2 = (ComposerImpl) composer;
                                composerImpl2.startReplaceGroup(-1988736348);
                                ImageVector vectorResource =
                                        num == null
                                                ? null
                                                : VectorResources_androidKt.vectorResource(
                                                        composerImpl2, num.intValue());
                                composerImpl2.end(false);
                                TipCardPreference.this.getClass();
                                SettingsCardKt.SettingsCard(
                                        new CardModel(
                                                str,
                                                str2,
                                                vectorResource,
                                                function0,
                                                filterNotNull,
                                                200),
                                        composerImpl2,
                                        8);
                                return Unit.INSTANCE;
                            }
                        });
    }

    @Override // androidx.preference.Preference
    public final void notifyChanged() {
        buildContent();
        super.notifyChanged();
    }

    public /* synthetic */ TipCardPreference(
            Context context,
            AttributeSet attributeSet,
            int i,
            DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TipCardPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0, 0, 12, null);
        Intrinsics.checkNotNullParameter(context, "context");
        this.primaryButtonText = ApnSettings.MVNO_NONE;
        this.primaryButtonAction =
                new Function0() { // from class:
                                  // com.android.settings.widget.TipCardPreference$primaryButtonAction$1
                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final /* bridge */ /* synthetic */ Object mo1068invoke() {
                        return Unit.INSTANCE;
                    }
                };
        this.secondaryButtonText = ApnSettings.MVNO_NONE;
        this.secondaryButtonAction =
                new Function0() { // from class:
                                  // com.android.settings.widget.TipCardPreference$secondaryButtonAction$1
                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final /* bridge */ /* synthetic */ Object mo1068invoke() {
                        return Unit.INSTANCE;
                    }
                };
    }
}
