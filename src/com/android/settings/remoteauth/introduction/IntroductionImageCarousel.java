package com.android.settings.remoteauth.introduction;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.android.settings.R;
import com.android.settingslib.widget.LottieColorUtils;

import com.airbnb.lottie.LottieAnimationView;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.sec.ims.settings.ImsProfile;

import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.ClosedFloatRange;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u00008\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0005\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0004\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0007\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b"
                + "\t\b\u0007\u0018\u00002\u00020\u0001:\u0003\"#$B\u0011\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0004\b\u0004\u0010\u0005B\u001b\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\u0004\b\u0004\u0010\bR\u001b\u0010\u000e\u001a\u00020"
                + "\t8BX\u0082\u0084\u0002¢\u0006\f\n"
                + "\u0004\b\n"
                + "\u0010\u000b\u001a\u0004\b\f\u0010\r"
                + "R\u001b\u0010\u0013\u001a\u00020\u000f8BX\u0082\u0084\u0002¢\u0006\f\n"
                + "\u0004\b\u0010\u0010\u000b\u001a\u0004\b\u0011\u0010\u0012R\u001b\u0010\u0018\u001a\u00020\u00148BX\u0082\u0084\u0002¢\u0006\f\n"
                + "\u0004\b\u0015\u0010\u000b\u001a\u0004\b\u0016\u0010\u0017R\u001b\u0010\u001b\u001a\u00020\u00148BX\u0082\u0084\u0002¢\u0006\f\n"
                + "\u0004\b\u0019\u0010\u000b\u001a\u0004\b\u001a\u0010\u0017R$\u0010\u001e\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c8\u0002@BX\u0082\u000e¢\u0006\f\n"
                + "\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!¨\u0006%"
        },
        d2 = {
            "Lcom/android/settings/remoteauth/introduction/IntroductionImageCarousel;",
            "Landroidx/constraintlayout/widget/ConstraintLayout;",
            "Landroid/content/Context;",
            "context",
            "<init>",
            "(Landroid/content/Context;)V",
            "Landroid/util/AttributeSet;",
            "attrSet",
            "(Landroid/content/Context;Landroid/util/AttributeSet;)V",
            "Landroidx/viewpager2/widget/ViewPager2;",
            "carousel$delegate",
            "Lkotlin/Lazy;",
            "getCarousel",
            "()Landroidx/viewpager2/widget/ViewPager2;",
            "carousel",
            "Landroidx/recyclerview/widget/RecyclerView;",
            "progressIndicator$delegate",
            "getProgressIndicator",
            "()Landroidx/recyclerview/widget/RecyclerView;",
            "progressIndicator",
            "Landroid/widget/ImageView;",
            "backArrow$delegate",
            "getBackArrow",
            "()Landroid/widget/ImageView;",
            "backArrow",
            "forwardArrow$delegate",
            "getForwardArrow",
            "forwardArrow",
            ApnSettings.MVNO_NONE,
            "value",
            "currentPage",
            ImsProfile.TIMER_NAME_I,
            "setCurrentPage",
            "(I)V",
            "AnimationViewHolder",
            "ImageCarouselAdapter",
            "ProgressIndicatorAdapter",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class IntroductionImageCarousel extends ConstraintLayout {
    public static final List ANIMATION_LIST =
            CollectionsKt__CollectionsKt.listOf(
                    (Object[])
                            new Pair[] {
                                new Pair(
                                        Integer.valueOf(
                                                R.raw.remoteauth_explanation_swipe_animation),
                                        Integer.valueOf(
                                                R.string
                                                        .security_settings_remoteauth_enroll_introduction_animation_swipe_up)),
                                new Pair(
                                        Integer.valueOf(
                                                R.raw
                                                        .remoteauth_explanation_notification_animation),
                                        Integer.valueOf(
                                                R.string
                                                        .security_settings_remoteauth_enroll_introduction_animation_tap_notification))
                            });

    /* renamed from: backArrow$delegate, reason: from kotlin metadata */
    public final Lazy backArrow;

    /* renamed from: carousel$delegate, reason: from kotlin metadata */
    public final Lazy carousel;
    public int currentPage;

    /* renamed from: forwardArrow$delegate, reason: from kotlin metadata */
    public final Lazy forwardArrow;

    /* renamed from: progressIndicator$delegate, reason: from kotlin metadata */
    public final Lazy progressIndicator;
    public final ProgressIndicatorAdapter progressIndicatorAdapter;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AnimationViewHolder extends RecyclerView.ViewHolder {
        public final LottieAnimationView animationView;
        public final Context context;
        public final TextView descriptionText;

        public AnimationViewHolder(Context context, View view) {
            super(view);
            this.context = context;
            View requireViewById = view.requireViewById(R.id.explanation_animation);
            Intrinsics.checkNotNullExpressionValue(requireViewById, "requireViewById(...)");
            this.animationView = (LottieAnimationView) requireViewById;
            View requireViewById2 = view.requireViewById(R.id.carousel_text);
            Intrinsics.checkNotNullExpressionValue(requireViewById2, "requireViewById(...)");
            this.descriptionText = (TextView) requireViewById2;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ImageCarouselAdapter extends RecyclerView.Adapter {
        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final int getItemCount() {
            List list = IntroductionImageCarousel.ANIMATION_LIST;
            return IntroductionImageCarousel.ANIMATION_LIST.size();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            AnimationViewHolder animationViewHolder = (AnimationViewHolder) viewHolder;
            List list = IntroductionImageCarousel.ANIMATION_LIST;
            int intValue = ((Number) ((Pair) list.get(i)).getFirst()).intValue();
            LottieAnimationView lottieAnimationView = animationViewHolder.animationView;
            lottieAnimationView.setAnimation(intValue);
            LottieColorUtils.applyDynamicColors(animationViewHolder.context, lottieAnimationView);
            animationViewHolder.descriptionText.setText(
                    ((Number) ((Pair) list.get(i)).getSecond()).intValue());
            View view = animationViewHolder.itemView;
            view.setContentDescription(
                    view.getContext()
                            .getString(((Number) ((Pair) list.get(i)).getSecond()).intValue()));
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
            Intrinsics.checkNotNullParameter(parent, "parent");
            Context context = parent.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            View inflate =
                    LayoutInflater.from(parent.getContext())
                            .inflate(
                                    R.layout.remote_auth_introduction_image_carousel_item,
                                    parent,
                                    false);
            Intrinsics.checkNotNullExpressionValue(inflate, "inflate(...)");
            return new AnimationViewHolder(context, inflate);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ProgressIndicatorAdapter extends RecyclerView.Adapter {
        public int currentIndex;

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final int getItemCount() {
            List list = IntroductionImageCarousel.ANIMATION_LIST;
            return IntroductionImageCarousel.ANIMATION_LIST.size();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            viewHolder.itemView.setSelected(i == this.currentIndex);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
            Intrinsics.checkNotNullParameter(parent, "parent");
            return new IntroductionImageCarousel$ProgressIndicatorAdapter$onCreateViewHolder$1(
                    LayoutInflater.from(parent.getContext())
                            .inflate(
                                    R.layout.remote_auth_introduction_image_carousel_progress_icon,
                                    parent,
                                    false));
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IntroductionImageCarousel(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.carousel =
                LazyKt__LazyJVMKt.lazy(
                        new Function0() { // from class:
                                          // com.android.settings.remoteauth.introduction.IntroductionImageCarousel$carousel$2
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                View requireViewById =
                                        IntroductionImageCarousel.this.requireViewById(
                                                R.id.image_carousel);
                                Intrinsics.checkNotNullExpressionValue(
                                        requireViewById, "requireViewById(...)");
                                return (ViewPager2) requireViewById;
                            }
                        });
        this.progressIndicator =
                LazyKt__LazyJVMKt.lazy(
                        new Function0() { // from class:
                                          // com.android.settings.remoteauth.introduction.IntroductionImageCarousel$progressIndicator$2
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                View requireViewById =
                                        IntroductionImageCarousel.this.requireViewById(
                                                R.id.carousel_progress_indicator);
                                Intrinsics.checkNotNullExpressionValue(
                                        requireViewById, "requireViewById(...)");
                                return (RecyclerView) requireViewById;
                            }
                        });
        this.backArrow =
                LazyKt__LazyJVMKt.lazy(
                        new Function0() { // from class:
                                          // com.android.settings.remoteauth.introduction.IntroductionImageCarousel$backArrow$2
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                View requireViewById =
                                        IntroductionImageCarousel.this.requireViewById(
                                                R.id.carousel_back_arrow);
                                Intrinsics.checkNotNullExpressionValue(
                                        requireViewById, "requireViewById(...)");
                                return (ImageView) requireViewById;
                            }
                        });
        this.forwardArrow =
                LazyKt__LazyJVMKt.lazy(
                        new Function0() { // from class:
                                          // com.android.settings.remoteauth.introduction.IntroductionImageCarousel$forwardArrow$2
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                View requireViewById =
                                        IntroductionImageCarousel.this.requireViewById(
                                                R.id.carousel_forward_arrow);
                                Intrinsics.checkNotNullExpressionValue(
                                        requireViewById, "requireViewById(...)");
                                return (ImageView) requireViewById;
                            }
                        });
        ProgressIndicatorAdapter progressIndicatorAdapter = new ProgressIndicatorAdapter();
        this.progressIndicatorAdapter = progressIndicatorAdapter;
        ViewPager2.OnPageChangeCallback onPageChangeCallback =
                new ViewPager2
                        .OnPageChangeCallback() { // from class:
                                                  // com.android.settings.remoteauth.introduction.IntroductionImageCarousel$onPageChangeCallback$1
                    @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
                    public final void onPageSelected(int i) {
                        IntroductionImageCarousel.this.setCurrentPage(i);
                    }
                };
        LayoutInflater.from(getContext())
                .inflate(R.layout.remote_auth_introduction_image_carousel, this);
        ViewPager2 carousel = getCarousel();
        carousel.setPageTransformer(
                new MarginPageTransformer(
                        (int)
                                carousel.getContext()
                                        .getResources()
                                        .getDimension(
                                                R.dimen
                                                        .remoteauth_introduction_fragment_padding_horizontal)));
        carousel.setAdapter(new ImageCarouselAdapter());
        carousel.registerOnPageChangeCallback(onPageChangeCallback);
        RecyclerView progressIndicator = getProgressIndicator();
        progressIndicator.getContext();
        progressIndicator.setLayoutManager(new LinearLayoutManager(0));
        progressIndicator.setAdapter(progressIndicatorAdapter);
        final int i = 0;
        getBackArrow()
                .setOnClickListener(
                        new View.OnClickListener(
                                this) { // from class:
                                        // com.android.settings.remoteauth.introduction.IntroductionImageCarousel.3
                            public final /* synthetic */ IntroductionImageCarousel this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                switch (i) {
                                    case 0:
                                        this.this$0.setCurrentPage(r0.currentPage - 1);
                                        break;
                                    default:
                                        IntroductionImageCarousel introductionImageCarousel =
                                                this.this$0;
                                        introductionImageCarousel.setCurrentPage(
                                                introductionImageCarousel.currentPage + 1);
                                        break;
                                }
                            }
                        });
        final int i2 = 1;
        getForwardArrow()
                .setOnClickListener(
                        new View.OnClickListener(
                                this) { // from class:
                                        // com.android.settings.remoteauth.introduction.IntroductionImageCarousel.3
                            public final /* synthetic */ IntroductionImageCarousel this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                switch (i2) {
                                    case 0:
                                        this.this$0.setCurrentPage(r0.currentPage - 1);
                                        break;
                                    default:
                                        IntroductionImageCarousel introductionImageCarousel =
                                                this.this$0;
                                        introductionImageCarousel.setCurrentPage(
                                                introductionImageCarousel.currentPage + 1);
                                        break;
                                }
                            }
                        });
    }

    private final ImageView getBackArrow() {
        return (ImageView) this.backArrow.getValue();
    }

    private final ViewPager2 getCarousel() {
        return (ViewPager2) this.carousel.getValue();
    }

    private final ImageView getForwardArrow() {
        return (ImageView) this.forwardArrow.getValue();
    }

    private final RecyclerView getProgressIndicator() {
        return (RecyclerView) this.progressIndicator.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void setCurrentPage(int i) {
        Number number;
        IntRange intRange = new IntRange(0, ANIMATION_LIST.size() - 1, 1);
        if (intRange instanceof ClosedFloatRange) {
            Integer valueOf = Integer.valueOf(i);
            ClosedFloatRange closedFloatRange = (ClosedFloatRange) intRange;
            if (closedFloatRange.isEmpty()) {
                throw new IllegalArgumentException(
                        "Cannot coerce value to an empty range: " + closedFloatRange + '.');
            }
            float f = closedFloatRange._start;
            if (!ClosedFloatRange.lessThanOrEquals(valueOf, Float.valueOf(f))
                    || ClosedFloatRange.lessThanOrEquals(Float.valueOf(f), valueOf)) {
                float f2 = closedFloatRange._endInclusive;
                boolean lessThanOrEquals =
                        ClosedFloatRange.lessThanOrEquals(Float.valueOf(f2), valueOf);
                number = valueOf;
                if (lessThanOrEquals) {
                    boolean lessThanOrEquals2 =
                            ClosedFloatRange.lessThanOrEquals(valueOf, Float.valueOf(f2));
                    number = valueOf;
                    if (!lessThanOrEquals2) {
                        number = Float.valueOf(f2);
                    }
                }
            } else {
                number = Float.valueOf(f);
            }
            i = number.intValue();
        } else {
            if (intRange.isEmpty()) {
                throw new IllegalArgumentException(
                        "Cannot coerce value to an empty range: " + intRange + '.');
            }
            Integer num = 0;
            if (i < num.intValue()) {
                Integer num2 = 0;
                i = num2.intValue();
            } else if (i > Integer.valueOf(intRange.last).intValue()) {
                i = Integer.valueOf(intRange.last).intValue();
            }
        }
        this.currentPage = i;
        getBackArrow().setEnabled(this.currentPage > 0);
        getForwardArrow().setEnabled(this.currentPage < intRange.last);
        getCarousel().setCurrentItem(this.currentPage, true);
        ProgressIndicatorAdapter progressIndicatorAdapter = this.progressIndicatorAdapter;
        int i2 = this.currentPage;
        int i3 = progressIndicatorAdapter.currentIndex;
        progressIndicatorAdapter.currentIndex =
                RangesKt.coerceIn(i2, 0, progressIndicatorAdapter.getItemCount() - 1);
        progressIndicatorAdapter.notifyItemChanged(i3);
        progressIndicatorAdapter.notifyItemChanged(progressIndicatorAdapter.currentIndex);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IntroductionImageCarousel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        this.carousel =
                LazyKt__LazyJVMKt.lazy(
                        new Function0() { // from class:
                                          // com.android.settings.remoteauth.introduction.IntroductionImageCarousel$carousel$2
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                View requireViewById =
                                        IntroductionImageCarousel.this.requireViewById(
                                                R.id.image_carousel);
                                Intrinsics.checkNotNullExpressionValue(
                                        requireViewById, "requireViewById(...)");
                                return (ViewPager2) requireViewById;
                            }
                        });
        this.progressIndicator =
                LazyKt__LazyJVMKt.lazy(
                        new Function0() { // from class:
                                          // com.android.settings.remoteauth.introduction.IntroductionImageCarousel$progressIndicator$2
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                View requireViewById =
                                        IntroductionImageCarousel.this.requireViewById(
                                                R.id.carousel_progress_indicator);
                                Intrinsics.checkNotNullExpressionValue(
                                        requireViewById, "requireViewById(...)");
                                return (RecyclerView) requireViewById;
                            }
                        });
        this.backArrow =
                LazyKt__LazyJVMKt.lazy(
                        new Function0() { // from class:
                                          // com.android.settings.remoteauth.introduction.IntroductionImageCarousel$backArrow$2
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                View requireViewById =
                                        IntroductionImageCarousel.this.requireViewById(
                                                R.id.carousel_back_arrow);
                                Intrinsics.checkNotNullExpressionValue(
                                        requireViewById, "requireViewById(...)");
                                return (ImageView) requireViewById;
                            }
                        });
        this.forwardArrow =
                LazyKt__LazyJVMKt.lazy(
                        new Function0() { // from class:
                                          // com.android.settings.remoteauth.introduction.IntroductionImageCarousel$forwardArrow$2
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                View requireViewById =
                                        IntroductionImageCarousel.this.requireViewById(
                                                R.id.carousel_forward_arrow);
                                Intrinsics.checkNotNullExpressionValue(
                                        requireViewById, "requireViewById(...)");
                                return (ImageView) requireViewById;
                            }
                        });
        ProgressIndicatorAdapter progressIndicatorAdapter = new ProgressIndicatorAdapter();
        this.progressIndicatorAdapter = progressIndicatorAdapter;
        ViewPager2.OnPageChangeCallback onPageChangeCallback =
                new ViewPager2
                        .OnPageChangeCallback() { // from class:
                                                  // com.android.settings.remoteauth.introduction.IntroductionImageCarousel$onPageChangeCallback$1
                    @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
                    public final void onPageSelected(int i) {
                        IntroductionImageCarousel.this.setCurrentPage(i);
                    }
                };
        LayoutInflater.from(getContext())
                .inflate(R.layout.remote_auth_introduction_image_carousel, this);
        ViewPager2 carousel = getCarousel();
        carousel.setPageTransformer(
                new MarginPageTransformer(
                        (int)
                                carousel.getContext()
                                        .getResources()
                                        .getDimension(
                                                R.dimen
                                                        .remoteauth_introduction_fragment_padding_horizontal)));
        carousel.setAdapter(new ImageCarouselAdapter());
        carousel.registerOnPageChangeCallback(onPageChangeCallback);
        RecyclerView progressIndicator = getProgressIndicator();
        progressIndicator.getContext();
        progressIndicator.setLayoutManager(new LinearLayoutManager(0));
        progressIndicator.setAdapter(progressIndicatorAdapter);
        final int i = 0;
        getBackArrow()
                .setOnClickListener(
                        new View.OnClickListener(
                                this) { // from class:
                                        // com.android.settings.remoteauth.introduction.IntroductionImageCarousel.3
                            public final /* synthetic */ IntroductionImageCarousel this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                switch (i) {
                                    case 0:
                                        this.this$0.setCurrentPage(r0.currentPage - 1);
                                        break;
                                    default:
                                        IntroductionImageCarousel introductionImageCarousel =
                                                this.this$0;
                                        introductionImageCarousel.setCurrentPage(
                                                introductionImageCarousel.currentPage + 1);
                                        break;
                                }
                            }
                        });
        final int i2 = 1;
        getForwardArrow()
                .setOnClickListener(
                        new View.OnClickListener(
                                this) { // from class:
                                        // com.android.settings.remoteauth.introduction.IntroductionImageCarousel.3
                            public final /* synthetic */ IntroductionImageCarousel this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                switch (i2) {
                                    case 0:
                                        this.this$0.setCurrentPage(r0.currentPage - 1);
                                        break;
                                    default:
                                        IntroductionImageCarousel introductionImageCarousel =
                                                this.this$0;
                                        introductionImageCarousel.setCurrentPage(
                                                introductionImageCarousel.currentPage + 1);
                                        break;
                                }
                            }
                        });
    }
}
