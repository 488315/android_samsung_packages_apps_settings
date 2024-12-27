package com.samsung.android.settings.analyzestorage.ui.widget.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;

import com.samsung.android.settings.analyzestorage.ui.view.analyzestorage.UsageDetailItem;
import com.samsung.android.settings.analyzestorage.ui.view.analyzestorage.UsageProgressBar;

import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ViewPagerViewHolder extends RecyclerView.ViewHolder {
    public final Lazy accountAddress$delegate;
    public final TextView asUsageLabel;
    public final TextView asUsageRate;
    public final Lazy divider$delegate;
    public final Lazy moreOptionIcon$delegate;
    public final MotionLayout motionLayout;
    public final Lazy mountButton$delegate;
    public final Lazy mountButtonText$delegate;
    public final Lazy mountSubTitle$delegate;
    public final LinearLayout mountView;
    public final LinearLayout progressContainer;
    public final Lazy showMore$delegate;
    public final Lazy showMoreContainer$delegate;
    public final TextView storageName;
    public final LinearLayout summaryContainer;
    public final TextView totalSize;
    public final UsageDetailItem usageDetailItem;
    public final UsageProgressBar usageProgressBar;
    public final LinearLayout usageRateContainer;
    public final TextView usedSize;

    public ViewPagerViewHolder(View view) {
        super(view);
        View requireViewById = view.requireViewById(R.id.viewpager_storage_name);
        Intrinsics.checkNotNullExpressionValue(requireViewById, "requireViewById(...)");
        this.storageName = (TextView) requireViewById;
        View requireViewById2 = view.requireViewById(R.id.as_usage_rate);
        Intrinsics.checkNotNullExpressionValue(requireViewById2, "requireViewById(...)");
        this.asUsageRate = (TextView) requireViewById2;
        View requireViewById3 = view.requireViewById(R.id.as_usage_label);
        Intrinsics.checkNotNullExpressionValue(requireViewById3, "requireViewById(...)");
        this.asUsageLabel = (TextView) requireViewById3;
        View requireViewById4 = view.requireViewById(R.id.used_space_size);
        Intrinsics.checkNotNullExpressionValue(requireViewById4, "requireViewById(...)");
        this.usedSize = (TextView) requireViewById4;
        View requireViewById5 = view.requireViewById(R.id.total_space_size);
        Intrinsics.checkNotNullExpressionValue(requireViewById5, "requireViewById(...)");
        this.totalSize = (TextView) requireViewById5;
        View requireViewById6 = view.requireViewById(R.id.constraintLayout);
        Intrinsics.checkNotNullExpressionValue(requireViewById6, "requireViewById(...)");
        this.motionLayout = (MotionLayout) requireViewById6;
        View requireViewById7 = view.requireViewById(R.id.as_usage_detail_item);
        Intrinsics.checkNotNullExpressionValue(requireViewById7, "requireViewById(...)");
        this.usageDetailItem = (UsageDetailItem) requireViewById7;
        View requireViewById8 = view.requireViewById(R.id.as_usage_progressbar);
        Intrinsics.checkNotNullExpressionValue(requireViewById8, "requireViewById(...)");
        this.usageProgressBar = (UsageProgressBar) requireViewById8;
        View requireViewById9 = view.requireViewById(R.id.as_usage_summary_container);
        Intrinsics.checkNotNullExpressionValue(requireViewById9, "requireViewById(...)");
        this.summaryContainer = (LinearLayout) requireViewById9;
        View requireViewById10 = view.requireViewById(R.id.as_usage_progress_container);
        Intrinsics.checkNotNullExpressionValue(requireViewById10, "requireViewById(...)");
        this.progressContainer = (LinearLayout) requireViewById10;
        View requireViewById11 = view.requireViewById(R.id.as_usage_rate_container);
        Intrinsics.checkNotNullExpressionValue(requireViewById11, "requireViewById(...)");
        this.usageRateContainer = (LinearLayout) requireViewById11;
        View requireViewById12 = view.requireViewById(R.id.mount_view);
        Intrinsics.checkNotNullExpressionValue(requireViewById12, "requireViewById(...)");
        this.mountView = (LinearLayout) requireViewById12;
        this.accountAddress$delegate =
                LazyKt__LazyJVMKt.lazy(
                        new Function0() { // from class:
                                          // com.samsung.android.settings.analyzestorage.ui.widget.viewholder.ViewPagerViewHolder$accountAddress$2
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                View requireViewById13 =
                                        ViewPagerViewHolder.this.itemView.requireViewById(
                                                R.id.account_address);
                                Intrinsics.checkNotNullExpressionValue(
                                        requireViewById13, "requireViewById(...)");
                                return (TextView) requireViewById13;
                            }
                        });
        this.mountSubTitle$delegate =
                LazyKt__LazyJVMKt.lazy(
                        new Function0() { // from class:
                                          // com.samsung.android.settings.analyzestorage.ui.widget.viewholder.ViewPagerViewHolder$mountSubTitle$2
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                View requireViewById13 =
                                        ViewPagerViewHolder.this.itemView.requireViewById(
                                                R.id.mount_subtitle);
                                Intrinsics.checkNotNullExpressionValue(
                                        requireViewById13, "requireViewById(...)");
                                return (TextView) requireViewById13;
                            }
                        });
        this.mountButton$delegate =
                LazyKt__LazyJVMKt.lazy(
                        new Function0() { // from class:
                                          // com.samsung.android.settings.analyzestorage.ui.widget.viewholder.ViewPagerViewHolder$mountButton$2
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                View requireViewById13 =
                                        ViewPagerViewHolder.this.itemView.requireViewById(
                                                R.id.mount_button);
                                Intrinsics.checkNotNullExpressionValue(
                                        requireViewById13, "requireViewById(...)");
                                return (LinearLayout) requireViewById13;
                            }
                        });
        this.moreOptionIcon$delegate =
                LazyKt__LazyJVMKt.lazy(
                        new Function0() { // from class:
                                          // com.samsung.android.settings.analyzestorage.ui.widget.viewholder.ViewPagerViewHolder$moreOptionIcon$2
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                View requireViewById13 =
                                        ViewPagerViewHolder.this.itemView.requireViewById(
                                                R.id.more_option_icon);
                                Intrinsics.checkNotNullExpressionValue(
                                        requireViewById13, "requireViewById(...)");
                                return (ImageView) requireViewById13;
                            }
                        });
        this.mountButtonText$delegate =
                LazyKt__LazyJVMKt.lazy(
                        new Function0() { // from class:
                                          // com.samsung.android.settings.analyzestorage.ui.widget.viewholder.ViewPagerViewHolder$mountButtonText$2
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                View requireViewById13 =
                                        ViewPagerViewHolder.this.itemView.requireViewById(
                                                R.id.mount_button_text);
                                Intrinsics.checkNotNullExpressionValue(
                                        requireViewById13, "requireViewById(...)");
                                return (TextView) requireViewById13;
                            }
                        });
        this.divider$delegate =
                LazyKt__LazyJVMKt.lazy(
                        new Function0() { // from class:
                                          // com.samsung.android.settings.analyzestorage.ui.widget.viewholder.ViewPagerViewHolder$divider$2
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                View requireViewById13 =
                                        ViewPagerViewHolder.this.itemView.requireViewById(
                                                R.id.divider);
                                Intrinsics.checkNotNullExpressionValue(
                                        requireViewById13, "requireViewById(...)");
                                return requireViewById13;
                            }
                        });
        this.showMoreContainer$delegate =
                LazyKt__LazyJVMKt.lazy(
                        new Function0() { // from class:
                                          // com.samsung.android.settings.analyzestorage.ui.widget.viewholder.ViewPagerViewHolder$showMoreContainer$2
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                View requireViewById13 =
                                        ViewPagerViewHolder.this.itemView.requireViewById(
                                                R.id.show_more_container);
                                Intrinsics.checkNotNullExpressionValue(
                                        requireViewById13, "requireViewById(...)");
                                return (LinearLayout) requireViewById13;
                            }
                        });
        this.showMore$delegate =
                LazyKt__LazyJVMKt.lazy(
                        new Function0() { // from class:
                                          // com.samsung.android.settings.analyzestorage.ui.widget.viewholder.ViewPagerViewHolder$showMore$2
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                View requireViewById13 =
                                        ViewPagerViewHolder.this.itemView.requireViewById(
                                                R.id.show_more);
                                Intrinsics.checkNotNullExpressionValue(
                                        requireViewById13, "requireViewById(...)");
                                return (TextView) requireViewById13;
                            }
                        });
    }

    public final TextView getShowMore() {
        return (TextView) this.showMore$delegate.getValue();
    }
}
