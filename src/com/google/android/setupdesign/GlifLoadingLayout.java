package com.google.android.setupdesign;

import android.animation.Animator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.settings.R;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.LottieCompositionFactory$$ExternalSyntheticLambda1;
import com.airbnb.lottie.LottieCompositionFactory$$ExternalSyntheticLambda2;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.SimpleColorFilter;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieValueCallback;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.partnerconfig.ResourceEntry;
import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.util.ForceTwoPaneHelper;
import com.google.android.setupcompat.util.Logger;
import com.google.android.setupdesign.util.LayoutStyler;
import com.google.android.setupdesign.util.LottieAnimationHelper;
import com.google.android.setupdesign.view.IllustrationVideoView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class GlifLoadingLayout extends GlifLayout {
    static final float MIN_ALLOWED_ILLUSTRATION_HEIGHT_RATIO = 0.25f;
    LottieAnimationConfig animationConfig;
    public List<LottieAnimationFinishListener> animationFinishListeners;
    public AnonymousClass1 animatorListener;
    int customLottieResource;
    String illustrationType;
    public View inflatedView;
    public boolean runRunnable;
    public boolean workFinished;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public enum LottieAnimationConfig {
        CONFIG_DEFAULT(
                PartnerConfig.CONFIG_PROGRESS_ILLUSTRATION_DEFAULT,
                PartnerConfig.CONFIG_LOADING_LOTTIE_DEFAULT,
                PartnerConfig.CONFIG_LOTTIE_LIGHT_THEME_CUSTOMIZATION_DEFAULT,
                PartnerConfig.CONFIG_LOTTIE_DARK_THEME_CUSTOMIZATION_DEFAULT),
        CONFIG_ACCOUNT(
                PartnerConfig.CONFIG_PROGRESS_ILLUSTRATION_ACCOUNT,
                PartnerConfig.CONFIG_LOADING_LOTTIE_ACCOUNT,
                PartnerConfig.CONFIG_LOTTIE_LIGHT_THEME_CUSTOMIZATION_ACCOUNT,
                PartnerConfig.CONFIG_LOTTIE_DARK_THEME_CUSTOMIZATION_ACCOUNT),
        CONFIG_CONNECTION(
                PartnerConfig.CONFIG_PROGRESS_ILLUSTRATION_CONNECTION,
                PartnerConfig.CONFIG_LOADING_LOTTIE_CONNECTION,
                PartnerConfig.CONFIG_LOTTIE_LIGHT_THEME_CUSTOMIZATION_CONNECTION,
                PartnerConfig.CONFIG_LOTTIE_DARK_THEME_CUSTOMIZATION_CONNECTION),
        CONFIG_UPDATE(
                PartnerConfig.CONFIG_PROGRESS_ILLUSTRATION_UPDATE,
                PartnerConfig.CONFIG_LOADING_LOTTIE_UPDATE,
                PartnerConfig.CONFIG_LOTTIE_LIGHT_THEME_CUSTOMIZATION_UPDATE,
                PartnerConfig.CONFIG_LOTTIE_DARK_THEME_CUSTOMIZATION_UPDATE),
        CONFIG_FINAL_HOLD(
                PartnerConfig.CONFIG_PROGRESS_ILLUSTRATION_FINAL_HOLD,
                PartnerConfig.CONFIG_LOADING_LOTTIE_FINAL_HOLD,
                PartnerConfig.CONFIG_LOTTIE_LIGHT_THEME_CUSTOMIZATION_FINAL_HOLD,
                PartnerConfig.CONFIG_LOTTIE_DARK_THEME_CUSTOMIZATION_FINAL_HOLD);

        private final PartnerConfig darkThemeCustomization;
        private final PartnerConfig illustrationConfig;
        private final PartnerConfig lightThemeCustomization;
        private final PartnerConfig lottieConfig;

        LottieAnimationConfig(
                PartnerConfig partnerConfig,
                PartnerConfig partnerConfig2,
                PartnerConfig partnerConfig3,
                PartnerConfig partnerConfig4) {
            PartnerConfig.ResourceType resourceType = partnerConfig.getResourceType();
            PartnerConfig.ResourceType resourceType2 = PartnerConfig.ResourceType.ILLUSTRATION;
            if (resourceType != resourceType2
                    || partnerConfig2.getResourceType() != resourceType2) {
                throw new IllegalArgumentException(
                        "Illustration progress only allow illustration resource");
            }
            this.illustrationConfig = partnerConfig;
            this.lottieConfig = partnerConfig2;
            this.lightThemeCustomization = partnerConfig3;
            this.darkThemeCustomization = partnerConfig4;
        }

        public final PartnerConfig getDarkThemeCustomization() {
            return this.darkThemeCustomization;
        }

        public final PartnerConfig getIllustrationConfig() {
            return this.illustrationConfig;
        }

        public final PartnerConfig getLightThemeCustomization() {
            return this.lightThemeCustomization;
        }

        public final PartnerConfig getLottieConfig() {
            return this.lottieConfig;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class LottieAnimationFinishListener {
        Animator.AnimatorListener animatorListener =
                new Animator
                        .AnimatorListener() { // from class:
                                              // com.google.android.setupdesign.GlifLoadingLayout.LottieAnimationFinishListener.1
                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        LottieAnimationFinishListener.this.onAnimationFinished();
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator) {}

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationRepeat(Animator animator) {}

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator) {}
                };
        public final GlifLoadingLayout glifLoadingLayout;
        public final LottieAnimationView lottieAnimationView;
        public final Runnable runnable;

        public LottieAnimationFinishListener(
                GlifLoadingLayout glifLoadingLayout, Runnable runnable) {
            if (runnable == null) {
                throw new NullPointerException("Runnable can not be null");
            }
            this.glifLoadingLayout = glifLoadingLayout;
            this.runnable = runnable;
            float f = GlifLoadingLayout.MIN_ALLOWED_ILLUSTRATION_HEIGHT_RATIO;
            LottieAnimationView lottieAnimationView =
                    (LottieAnimationView) glifLoadingLayout.findViewById(R.id.sud_lottie_view);
            this.lottieAnimationView = lottieAnimationView;
            boolean z =
                    PartnerConfigHelper.get(glifLoadingLayout.getContext())
                            .getBoolean(
                                    glifLoadingLayout.getContext(),
                                    PartnerConfig.CONFIG_LOADING_LAYOUT_WAIT_FOR_ANIMATION_FINISHED,
                                    true);
            if (!glifLoadingLayout.isLottieLayoutVisible()
                    || !lottieAnimationView.lottieDrawable.isAnimating()
                    || isZeroAnimatorDurationScale()
                    || !z) {
                onAnimationFinished();
                return;
            }
            Log.i("GlifLoadingLayout", "Register animation finish.");
            lottieAnimationView.lottieDrawable.animator.addListener(this.animatorListener);
            lottieAnimationView.setRepeatCount(0);
        }

        public boolean isZeroAnimatorDurationScale() {
            try {
                return Settings.Global.getFloat(
                                this.glifLoadingLayout.getContext().getContentResolver(),
                                "animator_duration_scale")
                        == 0.0f;
            } catch (Settings.SettingNotFoundException unused) {
                return false;
            }
        }

        public void onAnimationFinished() {
            this.runnable.run();
            LottieAnimationView lottieAnimationView = this.lottieAnimationView;
            if (lottieAnimationView != null) {
                lottieAnimationView.lottieDrawable.animator.removeListener(this.animatorListener);
            }
            this.glifLoadingLayout.animationFinishListeners.remove(this);
        }
    }

    public GlifLoadingLayout(Context context) {
        this(context, 0, 0);
    }

    @Override // com.google.android.setupdesign.GlifLayout,
              // com.google.android.setupcompat.internal.TemplateLayout
    public final ViewGroup findContainer(int i) {
        if (i == 0) {
            i = R.id.sud_layout_content;
        }
        return super.findContainer(i);
    }

    public synchronized void finishRunnable(Runnable runnable) {
        try {
            if (this.runRunnable) {
                runnable.run();
            }
            this.runRunnable = false;
        } catch (Throwable th) {
            throw th;
        }
    }

    public final void inflateLottieView() {
        ViewStub viewStub;
        if (findViewById(R.id.sud_layout_lottie_illustration) != null
                || (viewStub = (ViewStub) findViewById(R.id.sud_loading_layout_lottie_stub))
                        == null) {
            return;
        }
        View inflate = viewStub.inflate();
        this.inflatedView = inflate;
        if (inflate instanceof LinearLayout) {
            updateContentPadding((LinearLayout) inflate);
        }
        setLottieResource();
    }

    public final void init(AttributeSet attributeSet, int i) {
        registerMixin(FooterBarMixin.class, new LoadingFooterBarMixin(this, attributeSet, i));
        TypedArray obtainStyledAttributes =
                getContext()
                        .obtainStyledAttributes(
                                attributeSet,
                                com.google.android.setupdesign.lottieloadinglayout.R$styleable
                                        .SudGlifLoadingLayout,
                                i,
                                0);
        this.customLottieResource = obtainStyledAttributes.getResourceId(1, 0);
        String string = obtainStyledAttributes.getString(0);
        obtainStyledAttributes.recycle();
        int i2 = this.customLottieResource;
        if (i2 != 0) {
            inflateLottieView();
            findContainer(0).setVisibility(0);
        } else {
            if (string != null) {
                if (i2 != 0) {
                    throw new IllegalStateException(
                            "custom illustration already applied, should not set illustration.");
                }
                if (!this.illustrationType.equals(string)) {
                    this.illustrationType = string;
                }
                switch (string) {
                    case "final_hold":
                        this.animationConfig = LottieAnimationConfig.CONFIG_FINAL_HOLD;
                        break;
                    case "account":
                        this.animationConfig = LottieAnimationConfig.CONFIG_ACCOUNT;
                        break;
                    case "update":
                        this.animationConfig = LottieAnimationConfig.CONFIG_UPDATE;
                        break;
                    case "connection":
                        this.animationConfig = LottieAnimationConfig.CONFIG_CONNECTION;
                        break;
                    default:
                        this.animationConfig = LottieAnimationConfig.CONFIG_DEFAULT;
                        break;
                }
                setLottieResource();
            }
            inflateLottieView();
        }
        View findViewById = findViewById(R.id.sud_layout_loading_content);
        if (findViewById != null) {
            if (shouldApplyPartnerResource()) {
                LayoutStyler.applyPartnerCustomizationExtraPaddingStyle(findViewById);
            }
            tryApplyPartnerCustomizationContentPaddingTopStyle(findViewById);
        }
        View findViewById2 = findViewById(R.id.sud_header_scroll_view);
        Configuration configuration = getResources().getConfiguration();
        if (findViewById2 != null) {
            PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(getContext());
            PartnerConfig partnerConfig = PartnerConfig.CONFIG_LOADING_LAYOUT_HEADER_HEIGHT;
            if (partnerConfigHelper.isPartnerConfigAvailable(partnerConfig)
                    && configuration.orientation != 2) {
                findViewById2.getLayoutParams().height =
                        (int)
                                PartnerConfigHelper.get(getContext())
                                        .getDimension(getContext(), partnerConfig, 0.0f);
            }
        }
        updateLandscapeMiddleHorizontalSpacing();
        this.workFinished = false;
        this.runRunnable = true;
        LottieAnimationView lottieAnimationView =
                (LottieAnimationView) findViewById(R.id.sud_lottie_view);
        if (lottieAnimationView != null) {
            lottieAnimationView.lottieDrawable.animator.addListener(
                    new Animator.AnimatorListener() { // from class:
                        // com.google.android.setupdesign.GlifLoadingLayout.1
                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) {
                            StringBuilder sb = new StringBuilder("Animate enable:");
                            GlifLoadingLayout glifLoadingLayout = GlifLoadingLayout.this;
                            float f = GlifLoadingLayout.MIN_ALLOWED_ILLUSTRATION_HEIGHT_RATIO;
                            glifLoadingLayout.getClass();
                            boolean z = true;
                            try {
                                if (Settings.Global.getFloat(
                                                glifLoadingLayout.getContext().getContentResolver(),
                                                "animator_duration_scale")
                                        == 0.0f) {
                                    z = false;
                                }
                            } catch (Settings.SettingNotFoundException unused) {
                            }
                            sb.append(z);
                            sb.append(". Animation end.");
                            Log.i("GlifLoadingLayout", sb.toString());
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationRepeat(Animator animator) {
                            if (GlifLoadingLayout.this.workFinished) {
                                Log.i(
                                        "GlifLoadingLayout",
                                        "Animation repeat but work finished, run the register"
                                            + " runnable.");
                                GlifLoadingLayout glifLoadingLayout = GlifLoadingLayout.this;
                                glifLoadingLayout.getClass();
                                glifLoadingLayout.finishRunnable(null);
                                GlifLoadingLayout.this.workFinished = false;
                            }
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationCancel(Animator animator) {}

                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationStart(Animator animator) {}
                    });
        }
    }

    public boolean isLottieLayoutVisible() {
        View findViewById = findViewById(R.id.sud_layout_lottie_illustration);
        return findViewById != null && findViewById.getVisibility() == 0;
    }

    @Override // com.google.android.setupcompat.PartnerCustomizationLayout, android.view.ViewGroup,
              // android.view.View
    public final void onDetachedFromWindow() {
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putString("GlifLayoutType", "LoadingLayout");
        setLayoutTypeMetrics(persistableBundle);
        super.onDetachedFromWindow();
    }

    @Override // com.google.android.setupdesign.GlifLayout,
              // com.google.android.setupcompat.internal.TemplateLayout
    public final View onInflateTemplate(LayoutInflater layoutInflater, int i) {
        int i2;
        Context context = getContext();
        if (i == 0) {
            if (PartnerConfigHelper.get(context)
                    .getBoolean(
                            context,
                            PartnerConfig.CONFIG_LOADING_LAYOUT_FULL_SCREEN_ILLUSTRATION_ENABLED,
                            false)) {
                if (GlifLayout.isEmbeddedActivityOnePaneEnabled(context)) {
                    i = R.layout.sud_glif_fullscreen_loading_embedded_template;
                } else {
                    Context context2 = getContext();
                    Logger logger = ForceTwoPaneHelper.LOG;
                    boolean isForceTwoPaneEnabled =
                            PartnerConfigHelper.isForceTwoPaneEnabled(context2);
                    i2 = R.layout.sud_glif_fullscreen_loading_template;
                    if (isForceTwoPaneEnabled) {
                        i =
                                ForceTwoPaneHelper.getForceTwoPaneStyleLayout(
                                        getContext(),
                                        R.layout.sud_glif_fullscreen_loading_template);
                    }
                    i = i2;
                }
            } else if (GlifLayout.isEmbeddedActivityOnePaneEnabled(context)) {
                i = R.layout.sud_glif_loading_embedded_template;
            } else {
                Context context3 = getContext();
                Logger logger2 = ForceTwoPaneHelper.LOG;
                boolean isForceTwoPaneEnabled2 =
                        PartnerConfigHelper.isForceTwoPaneEnabled(context3);
                i2 = R.layout.sud_glif_loading_template;
                if (isForceTwoPaneEnabled2) {
                    i =
                            ForceTwoPaneHelper.getForceTwoPaneStyleLayout(
                                    getContext(), R.layout.sud_glif_loading_template);
                }
                i = i2;
            }
        }
        return inflateTemplate(layoutInflater, 2132083860, i);
    }

    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        View view = this.inflatedView;
        if (view instanceof LinearLayout) {
            updateContentPadding((LinearLayout) view);
        }
    }

    public final void setLottieResource() {
        ViewStub viewStub;
        int i;
        LottieAnimationView lottieAnimationView =
                (LottieAnimationView) findViewById(R.id.sud_lottie_view);
        if (lottieAnimationView == null) {
            Log.w(
                    "GlifLoadingLayout",
                    "Lottie view not found, skip set resource. Wait for layout inflated.");
            return;
        }
        if (this.customLottieResource != 0) {
            InputStream openRawResource = getResources().openRawResource(this.customLottieResource);
            lottieAnimationView.setCompositionTask(
                    LottieCompositionFactory.cache(
                            null,
                            new LottieCompositionFactory$$ExternalSyntheticLambda1(openRawResource),
                            new LottieCompositionFactory$$ExternalSyntheticLambda2(
                                    openRawResource)));
            lottieAnimationView.playAnimation$1();
            return;
        }
        ResourceEntry illustrationResourceEntry =
                PartnerConfigHelper.get(getContext())
                        .getIllustrationResourceEntry(
                                getContext(), this.animationConfig.getLottieConfig());
        if (illustrationResourceEntry == null) {
            View findViewById = findViewById(R.id.sud_layout_lottie_illustration);
            if (findViewById != null) {
                findViewById.setVisibility(8);
            }
            View findViewById2 = findViewById(R.id.sud_layout_progress_illustration);
            if (findViewById2 != null) {
                findViewById2.setVisibility(0);
            }
            if (findViewById(R.id.sud_layout_progress_illustration) != null
                    || (viewStub =
                                    (ViewStub)
                                            findViewById(R.id.sud_loading_layout_illustration_stub))
                            == null) {
                return;
            }
            View inflate = viewStub.inflate();
            this.inflatedView = inflate;
            if (inflate instanceof LinearLayout) {
                updateContentPadding((LinearLayout) inflate);
            }
            if (findViewById(R.id.sud_layout_progress_illustration) == null) {
                Log.i("GlifLoadingLayout", "Illustration stub not inflated, skip set resource");
                return;
            }
            IllustrationVideoView illustrationVideoView =
                    (IllustrationVideoView) findViewById(R.id.sud_progress_illustration);
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.sud_progress_bar);
            ResourceEntry illustrationResourceEntry2 =
                    PartnerConfigHelper.get(getContext())
                            .getIllustrationResourceEntry(
                                    getContext(), this.animationConfig.getIllustrationConfig());
            if (illustrationResourceEntry2 == null) {
                progressBar.setVisibility(0);
                illustrationVideoView.setVisibility(8);
                return;
            }
            progressBar.setVisibility(8);
            illustrationVideoView.setVisibility(0);
            int i2 = illustrationVideoView.videoResId;
            String str = illustrationResourceEntry2.packageName;
            int i3 = illustrationResourceEntry2.resourceId;
            if (i3 == i2
                    && (str == null || str.equals(illustrationVideoView.videoResPackageName))) {
                return;
            }
            illustrationVideoView.videoResId = i3;
            illustrationVideoView.videoResPackageName = str;
            illustrationVideoView.createMediaPlayer();
            return;
        }
        InputStream openRawResource2 =
                illustrationResourceEntry.resources.openRawResource(
                        illustrationResourceEntry.resourceId);
        lottieAnimationView.setCompositionTask(
                LottieCompositionFactory.cache(
                        null,
                        new LottieCompositionFactory$$ExternalSyntheticLambda1(openRawResource2),
                        new LottieCompositionFactory$$ExternalSyntheticLambda2(openRawResource2)));
        lottieAnimationView.playAnimation$1();
        View findViewById3 = findViewById(R.id.sud_layout_lottie_illustration);
        if (findViewById3 != null) {
            findViewById3.setVisibility(0);
        }
        View findViewById4 = findViewById(R.id.sud_layout_progress_illustration);
        if (findViewById4 != null) {
            findViewById4.setVisibility(8);
        }
        if (LottieAnimationHelper.instance == null) {
            LottieAnimationHelper.instance = new LottieAnimationHelper();
        }
        LottieAnimationHelper lottieAnimationHelper = LottieAnimationHelper.instance;
        Context context = getContext();
        LottieAnimationView lottieAnimationView2 =
                (LottieAnimationView) findViewById(R.id.sud_lottie_view);
        PartnerConfig darkThemeCustomization =
                (getResources().getConfiguration().uiMode & 48) == 32
                        ? this.animationConfig.getDarkThemeCustomization()
                        : this.animationConfig.getLightThemeCustomization();
        lottieAnimationHelper.getClass();
        PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(context);
        partnerConfigHelper.getClass();
        if (darkThemeCustomization.getResourceType() != PartnerConfig.ResourceType.STRING_ARRAY) {
            throw new IllegalArgumentException("Not a string array resource");
        }
        ArrayList arrayList = new ArrayList();
        try {
            ResourceEntry resourceEntryFromKey =
                    partnerConfigHelper.getResourceEntryFromKey(
                            context, darkThemeCustomization.getResourceName());
            Collections.addAll(
                    arrayList,
                    resourceEntryFromKey.resources.getStringArray(resourceEntryFromKey.resourceId));
        } catch (NullPointerException unused) {
        }
        HashMap hashMap = new HashMap();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            String[] split = str2.split(":");
            if (split.length != 2) {
                Log.w(
                        "LottieAnimationHelper",
                        "incorrect format customization, value=".concat(str2));
            } else if (split[1].charAt(0) == '#') {
                try {
                    hashMap.put(
                            new KeyPath("**", split[0], "**"),
                            Integer.valueOf(Color.parseColor(split[1])));
                } catch (IllegalArgumentException unused2) {
                    Log.e("LottieAnimationHelper", "Unknown color, value=".concat(str2));
                }
            } else if (split[1].charAt(0) == '@') {
                if (lottieAnimationHelper.colorResourceMapping.containsKey(split[1])) {
                    i = lottieAnimationHelper.colorResourceMapping.get(split[1]).intValue();
                } else {
                    int identifier =
                            context.getResources()
                                    .getIdentifier(
                                            split[1].substring(1),
                                            "color",
                                            context.getPackageName());
                    lottieAnimationHelper.colorResourceMapping.put(
                            split[1], Integer.valueOf(identifier));
                    i = identifier;
                }
                try {
                    hashMap.put(
                            new KeyPath("**", split[0], "**"),
                            Integer.valueOf(context.getResources().getColor(i, null)));
                } catch (Resources.NotFoundException unused3) {
                    Log.e(
                            "LottieAnimationHelper",
                            "Resource Not found, resource value=".concat(str2));
                }
            } else {
                Log.w(
                        "LottieAnimationHelper",
                        "incorrect format customization, value=".concat(str2));
            }
        }
        for (KeyPath keyPath : hashMap.keySet()) {
            lottieAnimationView2.lottieDrawable.addValueCallback(
                    keyPath,
                    LottieProperty.COLOR_FILTER,
                    new LottieValueCallback(
                            new SimpleColorFilter(((Integer) hashMap.get(keyPath)).intValue())));
        }
    }

    public final void updateContentPadding(LinearLayout linearLayout) {
        int paddingTop = linearLayout.getPaddingTop();
        int paddingLeft = linearLayout.getPaddingLeft();
        int paddingRight = linearLayout.getPaddingRight();
        int paddingBottom = linearLayout.getPaddingBottom();
        PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(getContext());
        PartnerConfig partnerConfig = PartnerConfig.CONFIG_LOADING_LAYOUT_PADDING_TOP;
        if (partnerConfigHelper.isPartnerConfigAvailable(partnerConfig)) {
            float dimension =
                    PartnerConfigHelper.get(getContext())
                            .getDimension(getContext(), partnerConfig, 0.0f);
            if (dimension >= 0.0f) {
                paddingTop = (int) dimension;
            }
        }
        PartnerConfigHelper partnerConfigHelper2 = PartnerConfigHelper.get(getContext());
        PartnerConfig partnerConfig2 = PartnerConfig.CONFIG_LOADING_LAYOUT_PADDING_START;
        if (partnerConfigHelper2.isPartnerConfigAvailable(partnerConfig2)) {
            float dimension2 =
                    PartnerConfigHelper.get(getContext())
                            .getDimension(getContext(), partnerConfig2, 0.0f);
            if (dimension2 >= 0.0f) {
                paddingLeft = (int) dimension2;
            }
        }
        PartnerConfigHelper partnerConfigHelper3 = PartnerConfigHelper.get(getContext());
        PartnerConfig partnerConfig3 = PartnerConfig.CONFIG_LOADING_LAYOUT_PADDING_END;
        if (partnerConfigHelper3.isPartnerConfigAvailable(partnerConfig3)) {
            float dimension3 =
                    PartnerConfigHelper.get(getContext())
                            .getDimension(getContext(), partnerConfig3, 0.0f);
            if (dimension3 >= 0.0f) {
                paddingRight = (int) dimension3;
            }
        }
        PartnerConfigHelper partnerConfigHelper4 = PartnerConfigHelper.get(getContext());
        PartnerConfig partnerConfig4 = PartnerConfig.CONFIG_LOADING_LAYOUT_PADDING_BOTTOM;
        if (partnerConfigHelper4.isPartnerConfigAvailable(partnerConfig4)) {
            float dimension4 =
                    PartnerConfigHelper.get(getContext())
                            .getDimension(getContext(), partnerConfig4, 0.0f);
            if (dimension4 >= 0.0f) {
                FooterBarMixin footerBarMixin = (FooterBarMixin) getMixin(FooterBarMixin.class);
                if (footerBarMixin == null || footerBarMixin.getButtonContainer() == null) {
                    paddingBottom = (int) dimension4;
                } else {
                    LinearLayout buttonContainer = footerBarMixin.getButtonContainer();
                    buttonContainer.measure(
                            View.MeasureSpec.makeMeasureSpec(
                                    buttonContainer.getMeasuredWidth(), 1073741824),
                            View.MeasureSpec.makeMeasureSpec(
                                    buttonContainer.getMeasuredHeight(), 1073741824));
                    paddingBottom =
                            ((int) dimension4)
                                    - ((int)
                                            Math.min(
                                                    dimension4,
                                                    buttonContainer.getMeasuredHeight()));
                }
            }
        }
        linearLayout.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    public GlifLoadingLayout(Context context, int i) {
        this(context, i, 0);
    }

    public GlifLoadingLayout(Context context, int i, int i2) {
        super(context, i, i2);
        this.illustrationType = "default";
        this.animationConfig = LottieAnimationConfig.CONFIG_DEFAULT;
        this.customLottieResource = 0;
        this.animationFinishListeners = new ArrayList();
        init(null, R.attr.sudLayoutTheme);
    }

    public GlifLoadingLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.illustrationType = "default";
        this.animationConfig = LottieAnimationConfig.CONFIG_DEFAULT;
        this.customLottieResource = 0;
        this.animationFinishListeners = new ArrayList();
        init(attributeSet, R.attr.sudLayoutTheme);
    }

    public GlifLoadingLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.illustrationType = "default";
        this.animationConfig = LottieAnimationConfig.CONFIG_DEFAULT;
        this.customLottieResource = 0;
        this.animationFinishListeners = new ArrayList();
        init(attributeSet, i);
    }
}
