package com.google.android.setupcompat;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.Button;

import androidx.compose.runtime.ComposerKt$$ExternalSyntheticOutline0;

import com.android.settings.R;

import com.google.android.setupcompat.internal.ClockProvider;
import com.google.android.setupcompat.internal.LifecycleFragment;
import com.google.android.setupcompat.internal.PersistableBundles;
import com.google.android.setupcompat.internal.Preconditions;
import com.google.android.setupcompat.internal.SetupCompatServiceInvoker;
import com.google.android.setupcompat.internal.SetupCompatServiceInvoker$$ExternalSyntheticLambda0;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.logging.CustomEvent;
import com.google.android.setupcompat.logging.LoggingObserver;
import com.google.android.setupcompat.logging.MetricKey;
import com.google.android.setupcompat.logging.SetupMetricsLogger;
import com.google.android.setupcompat.logging.internal.FooterBarMixinMetrics;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupcompat.template.StatusBarMixin;
import com.google.android.setupcompat.template.SystemNavBarMixin;
import com.google.android.setupcompat.util.Logger;
import com.google.android.setupcompat.util.WizardManagerHelper;
import com.google.android.setupdesign.GlifLayout;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentService;

import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class PartnerCustomizationLayout extends TemplateLayout {
    public static final Logger LOG = new Logger("PartnerCustomizationLayout");
    public Activity activity;
    public PersistableBundle layoutTypeBundle;
    public boolean useDynamicColor;
    public boolean useFullDynamicColorAttr;
    public boolean usePartnerResourceAttr;
    final ViewTreeObserver.OnWindowFocusChangeListener windowFocusChangeListener;

    public static void $r8$lambda$LkXYrnw5DYvjSfWXKSuSlNqcyss(GlifLayout glifLayout, boolean z) {
        SetupCompatServiceInvoker setupCompatServiceInvoker =
                SetupCompatServiceInvoker.get(glifLayout.getContext());
        String shortString = glifLayout.activity.getComponentName().toShortString();
        Activity activity = glifLayout.activity;
        Bundle bundle = new Bundle();
        bundle.putString("packageName", activity.getComponentName().getPackageName());
        bundle.putString("screenName", activity.getComponentName().getShortClassName());
        bundle.putInt("hash", glifLayout.hashCode());
        bundle.putBoolean("focus", z);
        bundle.putLong("timeInMillis", System.currentTimeMillis());
        setupCompatServiceInvoker.getClass();
        try {
            setupCompatServiceInvoker.loggingExecutor.execute(
                    new SetupCompatServiceInvoker$$ExternalSyntheticLambda0(
                            setupCompatServiceInvoker, shortString, bundle, 1));
        } catch (RejectedExecutionException e) {
            SetupCompatServiceInvoker.LOG.e(
                    ComposerKt$$ExternalSyntheticOutline0.m(
                            "Screen ", shortString, " report focus changed failed."),
                    e);
        }
    }

    public PartnerCustomizationLayout(Context context, int i, int i2) {
        super(context, i, i2);
        final GlifLayout glifLayout = (GlifLayout) this;
        this.windowFocusChangeListener =
                new ViewTreeObserver
                        .OnWindowFocusChangeListener() { // from class:
                                                         // com.google.android.setupcompat.PartnerCustomizationLayout$$ExternalSyntheticLambda0
                    @Override // android.view.ViewTreeObserver.OnWindowFocusChangeListener
                    public final void onWindowFocusChanged(boolean z) {
                        PartnerCustomizationLayout.$r8$lambda$LkXYrnw5DYvjSfWXKSuSlNqcyss(
                                (GlifLayout) glifLayout, z);
                    }
                };
        init$3(null, R.attr.sucLayoutTheme);
    }

    public PersistableBundle getLayoutTypeMetrics() {
        return this.layoutTypeBundle;
    }

    public final void init$3(AttributeSet attributeSet, int i) {
        if (isInEditMode()) {
            return;
        }
        TypedArray obtainStyledAttributes =
                getContext()
                        .obtainStyledAttributes(
                                attributeSet, R$styleable.SucPartnerCustomizationLayout, i, 0);
        boolean z = obtainStyledAttributes.getBoolean(1, true);
        obtainStyledAttributes.recycle();
        if (z) {
            setSystemUiVisibility(1024);
        }
        registerMixin(
                StatusBarMixin.class,
                new StatusBarMixin(this, this.activity.getWindow(), attributeSet, i));
        registerMixin(
                SystemNavBarMixin.class, new SystemNavBarMixin(this, this.activity.getWindow()));
        registerMixin(FooterBarMixin.class, new FooterBarMixin(this, attributeSet, i));
        ((SystemNavBarMixin) getMixin(SystemNavBarMixin.class))
                .applyPartnerCustomizations(attributeSet, i);
        this.activity.getWindow().addFlags(Integer.MIN_VALUE);
        this.activity.getWindow().clearFlags(67108864);
        this.activity.getWindow().clearFlags(UcmAgentService.ERROR_APPLET_UNKNOWN);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        Activity activity = this.activity;
        int i = LifecycleFragment.$r8$clinit;
        if (WizardManagerHelper.isAnySetupWizard(activity.getIntent())) {
            SetupCompatServiceInvoker setupCompatServiceInvoker =
                    SetupCompatServiceInvoker.get(activity.getApplicationContext());
            String componentName = activity.getComponentName().toString();
            Bundle bundle = new Bundle();
            bundle.putString("screenName", activity.getComponentName().toString());
            bundle.putString("intentAction", activity.getIntent().getAction());
            setupCompatServiceInvoker.getClass();
            try {
                setupCompatServiceInvoker.loggingExecutor.execute(
                        new SetupCompatServiceInvoker$$ExternalSyntheticLambda0(
                                setupCompatServiceInvoker, componentName, bundle, 0));
            } catch (RejectedExecutionException e) {
                SetupCompatServiceInvoker.LOG.e(
                        ComposerKt$$ExternalSyntheticOutline0.m(
                                "Screen ", componentName, " bind back fail."),
                        e);
            }
            FragmentManager fragmentManager = activity.getFragmentManager();
            if (fragmentManager != null && !fragmentManager.isDestroyed()) {
                Fragment findFragmentByTag = fragmentManager.findFragmentByTag("lifecycle_monitor");
                if (findFragmentByTag == null) {
                    LifecycleFragment lifecycleFragment = new LifecycleFragment();
                    try {
                        fragmentManager
                                .beginTransaction()
                                .add(lifecycleFragment, "lifecycle_monitor")
                                .commitNow();
                        findFragmentByTag = lifecycleFragment;
                    } catch (IllegalStateException e2) {
                        Log.e(
                                "LifecycleFragment",
                                "Error occurred when attach to Activity:"
                                        + activity.getComponentName(),
                                e2);
                    }
                } else if (!(findFragmentByTag instanceof LifecycleFragment)) {
                    Log.wtf(
                            "LifecycleFragment",
                            activity.getClass()
                                    .getSimpleName()
                                    .concat(" Incorrect instance on lifecycle fragment."));
                }
            }
        }
        if (WizardManagerHelper.isAnySetupWizard(this.activity.getIntent())) {
            getViewTreeObserver().addOnWindowFocusChangeListener(this.windowFocusChangeListener);
        }
        FooterBarMixin footerBarMixin = (FooterBarMixin) getMixin(FooterBarMixin.class);
        FooterBarMixinMetrics footerBarMixinMetrics = footerBarMixin.metrics;
        String str = "Invisible";
        footerBarMixinMetrics.primaryButtonVisibility =
                footerBarMixinMetrics.primaryButtonVisibility.equals("Unknown")
                        ? footerBarMixin.isPrimaryButtonVisible() ? "Visible" : "Invisible"
                        : footerBarMixinMetrics.primaryButtonVisibility;
        FooterBarMixinMetrics footerBarMixinMetrics2 = footerBarMixin.metrics;
        boolean isSecondaryButtonVisible = footerBarMixin.isSecondaryButtonVisible();
        if (!footerBarMixinMetrics2.secondaryButtonVisibility.equals("Unknown")) {
            str = footerBarMixinMetrics2.secondaryButtonVisibility;
        } else if (isSecondaryButtonVisible) {
            str = "Visible";
        }
        footerBarMixinMetrics2.secondaryButtonVisibility = str;
    }

    @Override // com.google.android.setupcompat.internal.TemplateLayout
    public final void onBeforeTemplateInflated(AttributeSet attributeSet, int i) {
        boolean z = true;
        this.usePartnerResourceAttr = true;
        Activity lookupActivityFromContext =
                PartnerConfigHelper.lookupActivityFromContext(getContext());
        this.activity = lookupActivityFromContext;
        boolean isAnySetupWizard =
                WizardManagerHelper.isAnySetupWizard(lookupActivityFromContext.getIntent());
        TypedArray obtainStyledAttributes =
                getContext()
                        .obtainStyledAttributes(
                                attributeSet, R$styleable.SucPartnerCustomizationLayout, i, 0);
        if (!obtainStyledAttributes.hasValue(2)) {
            LOG.e(
                    "Attribute sucUsePartnerResource not found in "
                            + this.activity.getComponentName());
        }
        if (!isAnySetupWizard && !obtainStyledAttributes.getBoolean(2, true)) {
            z = false;
        }
        this.usePartnerResourceAttr = z;
        this.useDynamicColor = obtainStyledAttributes.hasValue(0);
        this.useFullDynamicColorAttr = obtainStyledAttributes.getBoolean(0, false);
        obtainStyledAttributes.recycle();
        Logger logger = LOG;
        String str =
                "activity="
                        + this.activity.getClass().getSimpleName()
                        + " isSetupFlow="
                        + isAnySetupWizard
                        + " enablePartnerResourceLoading=true usePartnerResourceAttr="
                        + this.usePartnerResourceAttr
                        + " useDynamicColor="
                        + this.useDynamicColor
                        + " useFullDynamicColorAttr="
                        + this.useFullDynamicColorAttr;
        logger.getClass();
        if (Log.isLoggable("SetupLibrary", 3)) {
            Log.d("SetupLibrary", logger.prefix.concat(str));
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (WizardManagerHelper.isAnySetupWizard(this.activity.getIntent())) {
            FooterBarMixin footerBarMixin = (FooterBarMixin) getMixin(FooterBarMixin.class);
            FooterBarMixinMetrics footerBarMixinMetrics = footerBarMixin.metrics;
            boolean isPrimaryButtonVisible = footerBarMixin.isPrimaryButtonVisible();
            boolean isSecondaryButtonVisible = footerBarMixin.isSecondaryButtonVisible();
            footerBarMixinMetrics.primaryButtonVisibility =
                    FooterBarMixinMetrics.updateButtonVisibilityState(
                            footerBarMixinMetrics.primaryButtonVisibility, isPrimaryButtonVisible);
            footerBarMixinMetrics.secondaryButtonVisibility =
                    FooterBarMixinMetrics.updateButtonVisibilityState(
                            footerBarMixinMetrics.secondaryButtonVisibility,
                            isSecondaryButtonVisible);
            FooterButton footerButton = footerBarMixin.primaryButton;
            FooterButton footerButton2 = footerBarMixin.secondaryButton;
            PersistableBundle metrics =
                    footerButton != null
                            ? footerButton.getMetrics("PrimaryFooterButton")
                            : PersistableBundle.EMPTY;
            PersistableBundle metrics2 =
                    footerButton2 != null
                            ? footerButton2.getMetrics("SecondaryFooterButton")
                            : PersistableBundle.EMPTY;
            PersistableBundle persistableBundle = this.layoutTypeBundle;
            if (persistableBundle == null) {
                persistableBundle = PersistableBundle.EMPTY;
            }
            FooterBarMixinMetrics footerBarMixinMetrics2 = footerBarMixin.metrics;
            footerBarMixinMetrics2.getClass();
            PersistableBundle persistableBundle2 = new PersistableBundle();
            persistableBundle2.putString(
                    FooterBarMixinMetrics.EXTRA_PRIMARY_BUTTON_VISIBILITY,
                    footerBarMixinMetrics2.primaryButtonVisibility);
            persistableBundle2.putString(
                    FooterBarMixinMetrics.EXTRA_SECONDARY_BUTTON_VISIBILITY,
                    footerBarMixinMetrics2.secondaryButtonVisibility);
            PersistableBundle[] persistableBundleArr = {metrics2, persistableBundle};
            Logger logger = PersistableBundles.LOG;
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(Arrays.asList(persistableBundle2, metrics));
            Collections.addAll(arrayList, persistableBundleArr);
            PersistableBundle persistableBundle3 = new PersistableBundle();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                PersistableBundle persistableBundle4 = (PersistableBundle) it.next();
                for (String str : persistableBundle4.keySet()) {
                    Preconditions.checkArgument(
                            "Found duplicate key [" + str + "] while attempting to merge bundles.",
                            !persistableBundle3.containsKey(str));
                }
                persistableBundle3.putAll(persistableBundle4);
            }
            Context context = getContext();
            MetricKey metricKey = MetricKey.get(this.activity, "SetupCompatMetrics");
            Parcelable.Creator<CustomEvent> creator = CustomEvent.CREATOR;
            PersistableBundle persistableBundle5 = PersistableBundle.EMPTY;
            long millis = TimeUnit.NANOSECONDS.toMillis(ClockProvider.ticker.read());
            PersistableBundles.assertIsValid(persistableBundle3);
            PersistableBundles.assertIsValid(persistableBundle5);
            SetupMetricsLogger.logCustomEvent(
                    context,
                    new CustomEvent(millis, metricKey, persistableBundle3, persistableBundle5));
        }
        getViewTreeObserver().removeOnWindowFocusChangeListener(this.windowFocusChangeListener);
    }

    public void setLayoutTypeMetrics(PersistableBundle persistableBundle) {
        this.layoutTypeBundle = persistableBundle;
    }

    public void setLoggingObserver(LoggingObserver loggingObserver) {
        FooterBarMixin footerBarMixin = (FooterBarMixin) getMixin(FooterBarMixin.class);
        if (footerBarMixin.primaryButtonId != 0) {
            Button view = footerBarMixin.getPrimaryButtonView();
            LoggingObserver.ButtonType[] buttonTypeArr = LoggingObserver.ButtonType.$VALUES;
            Intrinsics.checkNotNullParameter(view, "view");
            throw null;
        }
        if (footerBarMixin.secondaryButtonId == 0) {
            loggingObserver.log();
            return;
        }
        Button view2 = footerBarMixin.getSecondaryButtonView();
        LoggingObserver.ButtonType[] buttonTypeArr2 = LoggingObserver.ButtonType.$VALUES;
        Intrinsics.checkNotNullParameter(view2, "view");
        throw null;
    }

    public final boolean shouldApplyDynamicColor() {
        return this.useDynamicColor && PartnerConfigHelper.get(getContext()).isAvailable();
    }

    public final boolean shouldApplyPartnerResource() {
        return this.usePartnerResourceAttr && PartnerConfigHelper.get(getContext()).isAvailable();
    }

    public final boolean useFullDynamicColor() {
        return shouldApplyDynamicColor() && this.useFullDynamicColorAttr;
    }

    public PartnerCustomizationLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        final GlifLayout glifLayout = (GlifLayout) this;
        this.windowFocusChangeListener =
                new ViewTreeObserver
                        .OnWindowFocusChangeListener() { // from class:
                                                         // com.google.android.setupcompat.PartnerCustomizationLayout$$ExternalSyntheticLambda0
                    @Override // android.view.ViewTreeObserver.OnWindowFocusChangeListener
                    public final void onWindowFocusChanged(boolean z) {
                        PartnerCustomizationLayout.$r8$lambda$LkXYrnw5DYvjSfWXKSuSlNqcyss(
                                (GlifLayout) glifLayout, z);
                    }
                };
        init$3(attributeSet, R.attr.sucLayoutTheme);
    }

    public PartnerCustomizationLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        final GlifLayout glifLayout = (GlifLayout) this;
        this.windowFocusChangeListener =
                new ViewTreeObserver
                        .OnWindowFocusChangeListener() { // from class:
                                                         // com.google.android.setupcompat.PartnerCustomizationLayout$$ExternalSyntheticLambda0
                    @Override // android.view.ViewTreeObserver.OnWindowFocusChangeListener
                    public final void onWindowFocusChanged(boolean z) {
                        PartnerCustomizationLayout.$r8$lambda$LkXYrnw5DYvjSfWXKSuSlNqcyss(
                                (GlifLayout) glifLayout, z);
                    }
                };
        init$3(attributeSet, i);
    }
}
