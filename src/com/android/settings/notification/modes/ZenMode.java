package com.android.settings.notification.modes;

import android.app.AutomaticZenRule;
import android.content.Context;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.service.notification.ZenPolicy;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.content.res.AppCompatResources;

import com.android.settings.R;
import com.android.settingslib.applications.RecentAppOpsAccess;

import com.google.common.base.Function;
import com.google.common.util.concurrent.AbstractCatchingFuture$CatchingFuture;
import com.google.common.util.concurrent.FluentFuture;
import com.google.common.util.concurrent.ForwardingFluentFuture;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.Objects;
import java.util.concurrent.Callable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenMode {
    public final String mId;
    public final boolean mIsActive;
    public final boolean mIsManualDnd;
    public final AutomaticZenRule mRule;
    public static final ZenPolicy POLICY_INTERRUPTION_FILTER_ALL =
            new ZenPolicy.Builder()
                    .allowChannels(-1)
                    .allowAllSounds()
                    .showAllVisualEffects()
                    .build();
    public static final ZenPolicy POLICY_INTERRUPTION_FILTER_ALARMS =
            new ZenPolicy.Builder()
                    .disallowAllSounds()
                    .allowAlarms(true)
                    .allowMedia(true)
                    .allowPriorityChannels(false)
                    .build();
    public static final ZenPolicy POLICY_INTERRUPTION_FILTER_NONE =
            new ZenPolicy.Builder()
                    .disallowAllSounds()
                    .hideAllVisualEffects()
                    .allowPriorityChannels(false)
                    .build();

    public ZenMode(String str, AutomaticZenRule automaticZenRule, boolean z, boolean z2) {
        this.mId = str;
        this.mRule = automaticZenRule;
        this.mIsActive = z;
        this.mIsManualDnd = z2;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof ZenMode) {
            ZenMode zenMode = (ZenMode) obj;
            if (this.mId.equals(zenMode.mId)
                    && this.mRule.equals(zenMode.mRule)
                    && this.mIsActive == zenMode.mIsActive) {
                return true;
            }
        }
        return false;
    }

    public final ListenableFuture getIcon(final Context context, final IconLoader iconLoader) {
        ListenableFuture transform;
        if (this.mIsManualDnd) {
            Drawable drawable = context.getDrawable(R.drawable.ic_do_not_disturb_on_24dp);
            Objects.requireNonNull(drawable);
            return Futures.immediateFuture(drawable);
        }
        final AutomaticZenRule automaticZenRule = this.mRule;
        iconLoader.getClass();
        if (automaticZenRule.getIconResId() == 0) {
            return Futures.immediateFuture(
                    IconLoader.getFallbackIcon(context, automaticZenRule.getType()));
        }
        final String packageName = automaticZenRule.getPackageName();
        final int iconResId = automaticZenRule.getIconResId();
        final String str = packageName + ":" + iconResId;
        synchronized (iconLoader.mCache) {
            try {
                Drawable drawable2 = (Drawable) iconLoader.mCache.get(str);
                if (drawable2 != null) {
                    if (drawable2 == IconLoader.MISSING) {
                        drawable2 = null;
                    }
                    transform = Futures.immediateFuture(drawable2);
                } else {
                    ListenableFuture submit =
                            ((MoreExecutors.ListeningDecorator) iconLoader.mBackgroundExecutor)
                                    .submit(
                                            new Callable() { // from class:
                                                             // com.android.settings.notification.modes.IconLoader$$ExternalSyntheticLambda1
                                                @Override // java.util.concurrent.Callable
                                                public final Object call() {
                                                    Context context2 = context;
                                                    String str2 = packageName;
                                                    boolean isEmpty = TextUtils.isEmpty(str2);
                                                    int i = iconResId;
                                                    if (isEmpty
                                                            || RecentAppOpsAccess
                                                                    .ANDROID_SYSTEM_PACKAGE_NAME
                                                                    .equals(str2)) {
                                                        return context2.getDrawable(i);
                                                    }
                                                    Drawable drawable3 =
                                                            AppCompatResources.getDrawable(
                                                                    context2.createPackageContext(
                                                                            str2, 0),
                                                                    i);
                                                    if (!(drawable3
                                                            instanceof AdaptiveIconDrawable)) {
                                                        return drawable3;
                                                    }
                                                    AdaptiveIconDrawable adaptiveIconDrawable =
                                                            (AdaptiveIconDrawable) drawable3;
                                                    return adaptiveIconDrawable.getMonochrome()
                                                                    != null
                                                            ? new InsetDrawable(
                                                                    adaptiveIconDrawable
                                                                            .getMonochrome(),
                                                                    AdaptiveIconDrawable
                                                                                    .getExtraInsetFraction()
                                                                            * (-2.0f))
                                                            : drawable3;
                                                }
                                            });
                    int i = FluentFuture.$r8$clinit;
                    FluentFuture forwardingFluentFuture =
                            submit instanceof FluentFuture
                                    ? (FluentFuture) submit
                                    : new ForwardingFluentFuture(submit);
                    Function function =
                            new Function() { // from class:
                                             // com.android.settings.notification.modes.IconLoader$$ExternalSyntheticLambda2
                                @Override // com.google.common.base.Function
                                public final Object apply(Object obj) {
                                    Log.e(
                                            "ZenIconLoader",
                                            "Error while loading icon " + str,
                                            (Exception) obj);
                                    return null;
                                }
                            };
                    MoreExecutors.directExecutor();
                    forwardingFluentFuture.getClass();
                    AbstractCatchingFuture$CatchingFuture abstractCatchingFuture$CatchingFuture =
                            new AbstractCatchingFuture$CatchingFuture();
                    abstractCatchingFuture$CatchingFuture.inputFuture = forwardingFluentFuture;
                    abstractCatchingFuture$CatchingFuture.exceptionType = Exception.class;
                    abstractCatchingFuture$CatchingFuture.fallback = function;
                    forwardingFluentFuture.addListener(
                            abstractCatchingFuture$CatchingFuture,
                            MoreExecutors.rejectionPropagatingExecutor(
                                    abstractCatchingFuture$CatchingFuture));
                    final int i2 = 1;
                    Function function2 =
                            new Function() { // from class:
                                             // com.android.settings.notification.modes.IconLoader$$ExternalSyntheticLambda0
                                @Override // com.google.common.base.Function
                                public final Object apply(Object obj) {
                                    switch (i2) {
                                        case 0:
                                            Drawable drawable3 = (Drawable) obj;
                                            return drawable3 != null
                                                    ? drawable3
                                                    : IconLoader.getFallbackIcon(
                                                            (Context) iconLoader,
                                                            ((AutomaticZenRule) str).getType());
                                        default:
                                            IconLoader iconLoader2 = (IconLoader) iconLoader;
                                            String str2 = (String) str;
                                            Drawable drawable4 = (Drawable) obj;
                                            synchronized (iconLoader2.mCache) {
                                                iconLoader2.mCache.put(
                                                        str2,
                                                        drawable4 != null
                                                                ? drawable4
                                                                : IconLoader.MISSING);
                                            }
                                            return drawable4;
                                    }
                                }
                            };
                    MoreExecutors.directExecutor();
                    transform = abstractCatchingFuture$CatchingFuture.transform(function2);
                }
            } finally {
            }
        }
        int i3 = FluentFuture.$r8$clinit;
        FluentFuture forwardingFluentFuture2 =
                transform instanceof FluentFuture
                        ? (FluentFuture) transform
                        : new ForwardingFluentFuture(transform);
        final int i4 = 0;
        Function function3 =
                new Function() { // from class:
                                 // com.android.settings.notification.modes.IconLoader$$ExternalSyntheticLambda0
                    @Override // com.google.common.base.Function
                    public final Object apply(Object obj) {
                        switch (i4) {
                            case 0:
                                Drawable drawable3 = (Drawable) obj;
                                return drawable3 != null
                                        ? drawable3
                                        : IconLoader.getFallbackIcon(
                                                (Context) context,
                                                ((AutomaticZenRule) automaticZenRule).getType());
                            default:
                                IconLoader iconLoader2 = (IconLoader) context;
                                String str2 = (String) automaticZenRule;
                                Drawable drawable4 = (Drawable) obj;
                                synchronized (iconLoader2.mCache) {
                                    iconLoader2.mCache.put(
                                            str2,
                                            drawable4 != null ? drawable4 : IconLoader.MISSING);
                                }
                                return drawable4;
                        }
                    }
                };
        MoreExecutors.directExecutor();
        return forwardingFluentFuture2.transform(function3);
    }

    public final ZenPolicy getPolicy() {
        int interruptionFilter = this.mRule.getInterruptionFilter();
        if (interruptionFilter == 1) {
            return POLICY_INTERRUPTION_FILTER_ALL;
        }
        if (interruptionFilter == 2) {
            ZenPolicy zenPolicy = this.mRule.getZenPolicy();
            Objects.requireNonNull(zenPolicy);
            return zenPolicy;
        }
        if (interruptionFilter == 3) {
            return POLICY_INTERRUPTION_FILTER_NONE;
        }
        if (interruptionFilter == 4) {
            return POLICY_INTERRUPTION_FILTER_ALARMS;
        }
        Log.wtf(
                "ZenMode",
                "Rule "
                        + this.mId
                        + " with unexpected interruptionFilter "
                        + this.mRule.getInterruptionFilter());
        ZenPolicy zenPolicy2 = this.mRule.getZenPolicy();
        Objects.requireNonNull(zenPolicy2);
        return zenPolicy2;
    }

    public final int hashCode() {
        return Objects.hash(this.mId, this.mRule, Boolean.valueOf(this.mIsActive));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mId);
        sb.append("(");
        sb.append(this.mIsActive ? "active" : "inactive");
        sb.append(") -> ");
        sb.append(this.mRule);
        return sb.toString();
    }
}
