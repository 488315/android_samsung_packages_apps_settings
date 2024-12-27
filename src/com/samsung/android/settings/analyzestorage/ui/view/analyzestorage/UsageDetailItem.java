package com.samsung.android.settings.analyzestorage.ui.view.analyzestorage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.settings.R;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.samsung.android.settings.analyzestorage.data.constant.AnalyzeStorageConstants$UiItemType;
import com.samsung.android.settings.analyzestorage.data.model.OverViewItemInfo;
import com.samsung.android.settings.analyzestorage.presenter.ActivityInfo$ActivityInfoListener;
import com.samsung.android.settings.analyzestorage.presenter.ActivityInfoStore;
import com.samsung.android.settings.analyzestorage.presenter.controllers.OverViewController;
import com.samsung.android.settings.analyzestorage.presenter.managers.FeatureManager$UiFeature;
import com.samsung.android.settings.analyzestorage.presenter.managers.SamsungAnalyticsConvertManager;
import com.samsung.android.settings.analyzestorage.presenter.page.PageType;
import com.samsung.android.settings.analyzestorage.presenter.utils.StringConverter;
import com.samsung.android.settings.analyzestorage.ui.manager.AsUsageInfoManager;
import com.samsung.android.settings.analyzestorage.ui.utils.UiUtils;
import com.samsung.android.settings.logging.LoggingHelper;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0018\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001:\u0001"
                + "\tB\u0013\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002¢\u0006\u0004\b\u0004\u0010\u0005B\u001d\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\u0004\b\u0004\u0010\b¨\u0006\n"
        },
        d2 = {
            "Lcom/samsung/android/settings/analyzestorage/ui/view/analyzestorage/UsageDetailItem;",
            "Landroid/widget/GridLayout;",
            "Landroid/content/Context;",
            "context",
            "<init>",
            "(Landroid/content/Context;)V",
            "Landroid/util/AttributeSet;",
            "attrs",
            "(Landroid/content/Context;Landroid/util/AttributeSet;)V",
            "UsageDetailViewHolder",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes4.dex */
public final class UsageDetailItem extends GridLayout {
    public final AsUsageInfoManager asUsageInfoManager;
    public OverViewController controller;
    public final List detailItemViewMap;
    public View divider;
    public int domainType;
    public final Handler handler;
    public boolean isLandScape;
    public boolean isRTL;
    public boolean isShowMoreItem;
    public final int summaryCount;
    public final UsageDetailItem$usageDetailClickListener$1 usageDetailClickListener;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class UsageDetailViewHolder {
        public final ImageView categoryBtn;
        public final LinearLayout container;
        public final View detailColor;
        public final TextView detailName;
        public final TextView detailSize;
        public AnalyzeStorageConstants$UiItemType item;
        public final View itemView;
        public final ShimmerFrameLayout loadingView;
        public final /* synthetic */ UsageDetailItem this$0;

        public UsageDetailViewHolder(
                final UsageDetailItem usageDetailItem,
                LifecycleOwner activity,
                View view,
                AnalyzeStorageConstants$UiItemType item) {
            MutableLiveData mutableLiveData;
            Intrinsics.checkNotNullParameter(activity, "activity");
            Intrinsics.checkNotNullParameter(item, "item");
            this.this$0 = usageDetailItem;
            this.itemView = view;
            this.item = item;
            this.detailColor = view.findViewById(R.id.detail_color);
            TextView textView = (TextView) view.findViewById(R.id.detail_name);
            this.detailName = textView;
            TextView textView2 = (TextView) view.findViewById(R.id.detail_size);
            this.detailSize = textView2;
            ImageView imageView = (ImageView) view.findViewById(R.id.category_btn);
            this.categoryBtn = imageView;
            View requireViewById = view.requireViewById(R.id.loading_as_over_view);
            Intrinsics.checkNotNullExpressionValue(requireViewById, "requireViewById(...)");
            this.loadingView = (ShimmerFrameLayout) requireViewById;
            View requireViewById2 = view.requireViewById(R.id.container);
            Intrinsics.checkNotNullExpressionValue(requireViewById2, "requireViewById(...)");
            this.container = (LinearLayout) requireViewById2;
            Context context = usageDetailItem.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            if (!FeatureManager$UiFeature.isDefaultTheme(context)) {
                int color = usageDetailItem.getContext().getColor(R.color.as_theme_main_text_color);
                int color2 = usageDetailItem.getContext().getColor(R.color.as_theme_icon_color);
                if (textView != null) {
                    textView.setTextColor(color);
                }
                if (textView2 != null) {
                    textView2.setTextColor(color);
                }
                if (imageView != null) {
                    imageView.setColorFilter(color2);
                }
            }
            OverViewController overViewController = usageDetailItem.controller;
            if (overViewController == null
                    || (mutableLiveData = overViewController.mIsLoadingData) == null) {
                return;
            }
            mutableLiveData.observe(
                    activity,
                    new Observer() { // from class:
                                     // com.samsung.android.settings.analyzestorage.ui.view.analyzestorage.UsageDetailItem$UsageDetailViewHolder$observeLoadingView$1
                        @Override // androidx.lifecycle.Observer
                        public final void onChanged(Object obj) {
                            int i;
                            Map map = (Map) obj;
                            UsageDetailItem usageDetailItem2 = UsageDetailItem.this;
                            if (map.containsKey(Integer.valueOf(usageDetailItem2.domainType))) {
                                Integer num =
                                        (Integer)
                                                MapsKt__MapsKt.getValue(
                                                        Integer.valueOf(
                                                                usageDetailItem2.domainType),
                                                        map);
                                UsageDetailItem.UsageDetailViewHolder usageDetailViewHolder = this;
                                if (num != null && num.intValue() == 1) {
                                    LinearLayout linearLayout = usageDetailViewHolder.container;
                                    Intrinsics.checkNotNullParameter(linearLayout, "<this>");
                                    linearLayout.setVisibility(8);
                                    return;
                                }
                                Integer num2 =
                                        (Integer)
                                                MapsKt__MapsKt.getValue(
                                                        Integer.valueOf(
                                                                usageDetailItem2.domainType),
                                                        map);
                                if (num2 != null && num2.intValue() == 2) {
                                    AnalyzeStorageConstants$UiItemType item2 =
                                            usageDetailViewHolder.item;
                                    AsUsageInfoManager asUsageInfoManager =
                                            usageDetailItem2.asUsageInfoManager;
                                    asUsageInfoManager.getClass();
                                    Intrinsics.checkNotNullParameter(item2, "item");
                                    boolean z =
                                            (!((EnumSet)
                                                                            asUsageInfoManager
                                                                                    .clickableTypeSet$delegate
                                                                                    .getValue())
                                                                    .contains(item2)
                                                            || 3
                                                                    == (i =
                                                                            usageDetailItem2
                                                                                    .domainType)
                                                            || 4 == i)
                                                    ? false
                                                    : true;
                                    TextView textView3 = usageDetailViewHolder.detailSize;
                                    if (textView3 != null) {
                                        textView3.setVisibility(0);
                                    }
                                    usageDetailViewHolder.itemView.setOnClickListener(
                                            z ? usageDetailItem2.usageDetailClickListener : null);
                                    usageDetailViewHolder.itemView.setClickable(z);
                                    usageDetailViewHolder.itemView.setFocusable(true);
                                    ImageView imageView2 = usageDetailViewHolder.categoryBtn;
                                    if (imageView2 != null) {
                                        imageView2.setVisibility(z ? 0 : 4);
                                    }
                                    ShimmerFrameLayout shimmerFrameLayout =
                                            usageDetailViewHolder.loadingView;
                                    Intrinsics.checkNotNullParameter(shimmerFrameLayout, "<this>");
                                    shimmerFrameLayout.setVisibility(8);
                                    LinearLayout linearLayout2 = usageDetailViewHolder.container;
                                    Intrinsics.checkNotNullParameter(linearLayout2, "<this>");
                                    linearLayout2.setVisibility(0);
                                }
                            }
                        }
                    });
        }
    }

    /* JADX WARN: Type inference failed for: r3v7, types: [com.samsung.android.settings.analyzestorage.ui.view.analyzestorage.UsageDetailItem$usageDetailClickListener$1] */
    public UsageDetailItem(Context context) {
        super(context);
        this.domainType = -1;
        AsUsageInfoManager.Companion companion = AsUsageInfoManager.Companion;
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        this.asUsageInfoManager = companion.getInstance(context2);
        this.detailItemViewMap = new ArrayList();
        new ArrayList();
        this.summaryCount = 4;
        this.usageDetailClickListener =
                new View.OnClickListener() { // from class:
                    // com.samsung.android.settings.analyzestorage.ui.view.analyzestorage.UsageDetailItem$usageDetailClickListener$1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        AnalyzeStorageConstants$UiItemType analyzeStorageConstants$UiItemType;
                        final int i = 2;
                        final int i2 = 1;
                        Intrinsics.checkNotNullParameter(view, "view");
                        Object tag = view.getTag();
                        Activity activity = null;
                        Integer num = tag instanceof Integer ? (Integer) tag : null;
                        int intValue = num != null ? num.intValue() : -1;
                        UsageDetailItem usageDetailItem = UsageDetailItem.this;
                        OverViewController overViewController = usageDetailItem.controller;
                        if (overViewController != null) {
                            if (intValue == R.string.as_menu_apps) {
                                analyzeStorageConstants$UiItemType =
                                        AnalyzeStorageConstants$UiItemType.APPS;
                            } else if (intValue == R.string.as_images) {
                                analyzeStorageConstants$UiItemType =
                                        AnalyzeStorageConstants$UiItemType.IMAGES;
                            } else if (intValue == R.string.as_videos) {
                                analyzeStorageConstants$UiItemType =
                                        AnalyzeStorageConstants$UiItemType.VIDEOS;
                            } else if (intValue == R.string.as_audio) {
                                analyzeStorageConstants$UiItemType =
                                        AnalyzeStorageConstants$UiItemType.AUDIO;
                            } else if (intValue == R.string.as_compressed_files) {
                                analyzeStorageConstants$UiItemType =
                                        AnalyzeStorageConstants$UiItemType.COMPRESSED;
                            } else if (intValue == R.string.as_documents) {
                                analyzeStorageConstants$UiItemType =
                                        AnalyzeStorageConstants$UiItemType.DOCUMENTS;
                            } else if (intValue == R.string.as_installation_files) {
                                analyzeStorageConstants$UiItemType =
                                        AnalyzeStorageConstants$UiItemType.APK;
                            } else if (intValue == R.string.as_secure_folder) {
                                analyzeStorageConstants$UiItemType =
                                        AnalyzeStorageConstants$UiItemType.SECURE_FOLDER;
                            } else {
                                if (intValue != R.string.as_work_profile) {
                                    throw new IllegalArgumentException(
                                            "Unsupported OverView Items");
                                }
                                analyzeStorageConstants$UiItemType =
                                        AnalyzeStorageConstants$UiItemType.WORK_PROFILE;
                            }
                            int i3 = usageDetailItem.domainType;
                            boolean z = usageDetailItem.isShowMoreItem;
                            int indexOf =
                                    ((List)
                                                    ((HashMap) overViewController.UiItemTypeMap)
                                                            .get(Integer.valueOf(i3)))
                                            .indexOf(analyzeStorageConstants$UiItemType);
                            Integer num2 =
                                    i3 != 0
                                            ? i3 != 1
                                                    ? i3 != 101
                                                            ? z
                                                                    ? (Integer)
                                                                            SamsungAnalyticsConvertManager
                                                                                    .sOverViewOnedriveCloudExpandToSAEventId
                                                                                    .get(
                                                                                            analyzeStorageConstants$UiItemType)
                                                                    : (Integer)
                                                                            SamsungAnalyticsConvertManager
                                                                                    .sOverViewOnedriveCloudCollapseToSAEventId
                                                                                    .get(indexOf)
                                                            : z
                                                                    ? (Integer)
                                                                            SamsungAnalyticsConvertManager
                                                                                    .sOverViewGoogleCloudExpandToSAEventId
                                                                                    .get(
                                                                                            analyzeStorageConstants$UiItemType)
                                                                    : (Integer)
                                                                            SamsungAnalyticsConvertManager
                                                                                    .sOverViewGoogleCloudCollapseToSAEventId
                                                                                    .get(indexOf)
                                                    : z
                                                            ? (Integer)
                                                                    SamsungAnalyticsConvertManager
                                                                            .sOverViewSdCardExpandToSAEventId
                                                                            .get(
                                                                                    analyzeStorageConstants$UiItemType)
                                                            : (Integer)
                                                                    SamsungAnalyticsConvertManager
                                                                            .sOverViewSdCardCollapseToSAEventId
                                                                            .get(indexOf)
                                            : z
                                                    ? (Integer)
                                                            SamsungAnalyticsConvertManager
                                                                    .sOverViewInternalExpandToSAEventId
                                                                    .get(
                                                                            analyzeStorageConstants$UiItemType)
                                                    : (Integer)
                                                            SamsungAnalyticsConvertManager
                                                                    .sOverViewInternalCollapseToSAEventId
                                                                    .get(indexOf);
                            if (num2 != null) {
                                LoggingHelper.insertEventLogging(
                                        Integer.parseInt(
                                                PageType.ANALYZE_STORAGE_HOME.getScreenIdForSA()),
                                        num2.intValue(),
                                        analyzeStorageConstants$UiItemType.name());
                            }
                            AnalyzeStorageConstants$UiItemType analyzeStorageConstants$UiItemType2 =
                                    AnalyzeStorageConstants$UiItemType.APPS;
                            int i4 = overViewController.mInstanceId;
                            if (analyzeStorageConstants$UiItemType
                                    == analyzeStorageConstants$UiItemType2) {
                                final Intent intent =
                                        new Intent("android.intent.action.MANAGE_PACKAGE_STORAGE");
                                Context context3 = ActivityInfoStore.context;
                                ActivityInfoStore companion2 =
                                        ActivityInfoStore.Companion.getInstance(i4);
                                if (!((ArrayList) companion2.activityListenerList).isEmpty()) {
                                    List list = companion2.activityListenerList;
                                    activity =
                                            ((ActivityInfo$ActivityInfoListener)
                                                            ((ArrayList) list)
                                                                    .get(
                                                                            CollectionsKt__CollectionsKt
                                                                                    .getLastIndex(
                                                                                            list)))
                                                    .getActivity();
                                }
                                final int i5 = 0;
                                Optional.ofNullable(activity)
                                        .ifPresent(
                                                new Consumer() { // from class:
                                                    // com.samsung.android.settings.analyzestorage.presenter.controllers.OverViewController$$ExternalSyntheticLambda4
                                                    @Override // java.util.function.Consumer
                                                    public final void accept(Object obj) {
                                                        int i6 = i5;
                                                        Intent intent2 = intent;
                                                        switch (i6) {
                                                            case 0:
                                                                ((Activity) obj)
                                                                        .startActivityForResult(
                                                                                intent2, 0);
                                                                break;
                                                            case 1:
                                                                ((Activity) obj)
                                                                        .startActivity(intent2);
                                                                break;
                                                            default:
                                                                ((ActivityResultLauncher) obj)
                                                                        .launch(intent2);
                                                                break;
                                                        }
                                                    }
                                                });
                                return;
                            }
                            AnalyzeStorageConstants$UiItemType analyzeStorageConstants$UiItemType3 =
                                    AnalyzeStorageConstants$UiItemType.SECURE_FOLDER;
                            if (analyzeStorageConstants$UiItemType
                                            == analyzeStorageConstants$UiItemType3
                                    || analyzeStorageConstants$UiItemType
                                            == AnalyzeStorageConstants$UiItemType.WORK_PROFILE) {
                                r7 =
                                        analyzeStorageConstants$UiItemType
                                                        != analyzeStorageConstants$UiItemType3
                                                ? 3
                                                : 4;
                                final Intent intent2 =
                                        new Intent(
                                                "com.samsung.android.settings.analyzestorage.ENTER_SUB_USER");
                                intent2.putExtra("instanceId", i4);
                                intent2.putExtra("domainType", r7);
                                Context context4 = ActivityInfoStore.context;
                                ActivityInfoStore companion3 =
                                        ActivityInfoStore.Companion.getInstance(i4);
                                if (!((ArrayList) companion3.activityListenerList).isEmpty()) {
                                    List list2 = companion3.activityListenerList;
                                    activity =
                                            ((ActivityInfo$ActivityInfoListener)
                                                            ((ArrayList) list2)
                                                                    .get(
                                                                            CollectionsKt__CollectionsKt
                                                                                    .getLastIndex(
                                                                                            list2)))
                                                    .getActivity();
                                }
                                Optional.ofNullable(activity)
                                        .ifPresent(
                                                new Consumer() { // from class:
                                                    // com.samsung.android.settings.analyzestorage.presenter.controllers.OverViewController$$ExternalSyntheticLambda4
                                                    @Override // java.util.function.Consumer
                                                    public final void accept(Object obj) {
                                                        int i6 = i2;
                                                        Intent intent22 = intent2;
                                                        switch (i6) {
                                                            case 0:
                                                                ((Activity) obj)
                                                                        .startActivityForResult(
                                                                                intent22, 0);
                                                                break;
                                                            case 1:
                                                                ((Activity) obj)
                                                                        .startActivity(intent22);
                                                                break;
                                                            default:
                                                                ((ActivityResultLauncher) obj)
                                                                        .launch(intent22);
                                                                break;
                                                        }
                                                    }
                                                });
                                return;
                            }
                            overViewController.mPageInfo.mExtra.putInt("from_usage_type", i3);
                            final Intent intent3 =
                                    new Intent(
                                            "com.sec.android.app.myfiles.VIEW_ANALYZE_STORAGE_SUB_LIST");
                            intent3.setPackage("com.sec.android.app.myfiles");
                            int ordinal = analyzeStorageConstants$UiItemType.ordinal();
                            if (ordinal == 1) {
                                r7 = 3;
                            } else if (ordinal != 2) {
                                r7 = ordinal != 3 ? ordinal != 4 ? ordinal != 5 ? 2 : 8 : 6 : 5;
                            }
                            intent3.putExtra("SELECTOR_CATEGORY_TYPE", r7);
                            intent3.putExtra("domainType", i3);
                            Context context5 = ActivityInfoStore.context;
                            Optional.ofNullable(
                                            ActivityInfoStore.Companion.getInstance(i4)
                                                    .getActivityResultLauncher())
                                    .ifPresent(
                                            new Consumer() { // from class:
                                                // com.samsung.android.settings.analyzestorage.presenter.controllers.OverViewController$$ExternalSyntheticLambda4
                                                @Override // java.util.function.Consumer
                                                public final void accept(Object obj) {
                                                    int i6 = i;
                                                    Intent intent22 = intent3;
                                                    switch (i6) {
                                                        case 0:
                                                            ((Activity) obj)
                                                                    .startActivityForResult(
                                                                            intent22, 0);
                                                            break;
                                                        case 1:
                                                            ((Activity) obj)
                                                                    .startActivity(intent22);
                                                            break;
                                                        default:
                                                            ((ActivityResultLauncher) obj)
                                                                    .launch(intent22);
                                                            break;
                                                    }
                                                }
                                            });
                        }
                    }
                };
        this.handler = new Handler(Looper.getMainLooper());
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.handler.removeCallbacksAndMessages(null);
    }

    public final void setAppearAnimation(Animation animation, boolean z) {
        Iterator it = ((ArrayList) this.detailItemViewMap).iterator();
        int i = 0;
        while (it.hasNext()) {
            Object next = it.next();
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
                throw null;
            }
            UsageDetailViewHolder usageDetailViewHolder = (UsageDetailViewHolder) next;
            if (i >= this.summaryCount) {
                usageDetailViewHolder.itemView.startAnimation(animation);
                usageDetailViewHolder.itemView.setVisibility(z ? 0 : 4);
            }
            i = i2;
        }
    }

    public final void setChildVisible() {
        int childCount = getChildCount();
        int i = 0;
        while (i < childCount) {
            getChildAt(i).setVisibility(this.isShowMoreItem || i < this.summaryCount ? 0 : 8);
            i++;
        }
    }

    public final void setDividerLayoutParams(boolean z) {
        GridLayout.LayoutParams layoutParams =
                new GridLayout.LayoutParams(
                        GridLayout.spec(Integer.MIN_VALUE),
                        GridLayout.spec(Integer.MIN_VALUE, z ? 2 : 1, 1.0f));
        layoutParams.height =
                getResources().getDimensionPixelSize(R.dimen.as_basic_list_divider_height);
        layoutParams.width = 0;
        layoutParams.setMargins(
                getResources().getDimensionPixelSize(R.dimen.as_viewpager_padding_horizontal),
                getResources().getDimensionPixelSize(R.dimen.as_home_usage_detail_padding_vertical),
                getResources().getDimensionPixelSize(R.dimen.as_viewpager_padding_end),
                getResources()
                        .getDimensionPixelSize(R.dimen.as_home_usage_detail_padding_vertical));
        View view = this.divider;
        if (view == null) {
            return;
        }
        view.setLayoutParams(layoutParams);
    }

    public final void setInitItem(
            LifecycleOwner activity,
            List list,
            int i,
            boolean z,
            OverViewController overViewController,
            boolean z2,
            boolean z3) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(overViewController, "overViewController");
        this.controller = overViewController;
        this.domainType = i;
        this.isLandScape = z;
        setColumnCount(z ? 2 : 1);
        this.isShowMoreItem = z3;
        this.isRTL = z2;
        int i2 = 0;
        for (Object obj : list) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
                throw null;
            }
            AnalyzeStorageConstants$UiItemType analyzeStorageConstants$UiItemType =
                    (AnalyzeStorageConstants$UiItemType) obj;
            if (this.asUsageInfoManager.dividerPosition.get(this.domainType, Integer.MIN_VALUE)
                    == i2) {
                boolean z4 = this.isLandScape;
                this.divider =
                        LayoutInflater.from(getContext())
                                .inflate(R.layout.as_divider, (ViewGroup) null, false);
                setDividerLayoutParams(z4);
                addView(this.divider);
            }
            View inflate =
                    LayoutInflater.from(getContext())
                            .inflate(R.layout.as_usage_detail_item, (ViewGroup) null);
            Intrinsics.checkNotNull(inflate);
            UsageDetailViewHolder usageDetailViewHolder =
                    new UsageDetailViewHolder(
                            this, activity, inflate, analyzeStorageConstants$UiItemType);
            ((ArrayList) this.detailItemViewMap).add(usageDetailViewHolder);
            GridLayout.LayoutParams layoutParams =
                    new GridLayout.LayoutParams(
                            GridLayout.spec(Integer.MIN_VALUE),
                            GridLayout.spec(Integer.MIN_VALUE, 1.0f));
            layoutParams.height = -2;
            layoutParams.width = 0;
            inflate.setLayoutParams(layoutParams);
            ImageView imageView = usageDetailViewHolder.categoryBtn;
            if (imageView != null) {
                imageView.setRotation(this.isRTL ? 180.0f : 0.0f);
            }
            if (this.isLandScape) {
                if (i2 % 2 == 0) {
                    Context context = inflate.getContext();
                    Intrinsics.checkNotNull(context);
                    inflate.setPaddingRelative(
                            UiUtils.getDimenSize(context, R.dimen.as_viewpager_padding_horizontal),
                            UiUtils.getDimenSize(
                                    context, R.dimen.as_home_usage_detail_padding_vertical),
                            UiUtils.getDimenSize(
                                    context, R.dimen.as_home_usage_detail_item_padding_gap),
                            UiUtils.getDimenSize(
                                    context, R.dimen.as_home_usage_detail_padding_vertical));
                } else {
                    Context context2 = inflate.getContext();
                    Intrinsics.checkNotNull(context2);
                    inflate.setPaddingRelative(
                            UiUtils.getDimenSize(
                                    context2, R.dimen.as_home_usage_detail_item_padding_gap),
                            UiUtils.getDimenSize(
                                    context2, R.dimen.as_home_usage_detail_padding_vertical),
                            UiUtils.getDimenSize(context2, R.dimen.as_viewpager_padding_horizontal),
                            UiUtils.getDimenSize(
                                    context2, R.dimen.as_home_usage_detail_padding_vertical));
                }
            }
            addView(inflate);
            i2 = i3;
        }
        setChildVisible();
    }

    public final void updateColumnCount(boolean z) {
        int i = z ? 2 : 1;
        if (getColumnCount() != i) {
            Iterator it = ((ArrayList) this.detailItemViewMap).iterator();
            while (it.hasNext()) {
                UsageDetailViewHolder usageDetailViewHolder = (UsageDetailViewHolder) it.next();
                GridLayout.LayoutParams layoutParams =
                        new GridLayout.LayoutParams(
                                GridLayout.spec(Integer.MIN_VALUE),
                                GridLayout.spec(Integer.MIN_VALUE, 1.0f));
                layoutParams.height = -2;
                layoutParams.width = 0;
                usageDetailViewHolder.itemView.setLayoutParams(layoutParams);
            }
            if (z) {
                setColumnCount(i);
                setDividerLayoutParams(true);
            } else {
                setDividerLayoutParams(false);
                setColumnCount(i);
            }
        }
    }

    public final void updateSize(ArrayList usageSizeList) {
        String formatFileSize;
        TextView textView;
        Intrinsics.checkNotNullParameter(usageSizeList, "usageSizeList");
        int i = 0;
        for (Object obj : this.detailItemViewMap) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
                throw null;
            }
            UsageDetailViewHolder usageDetailViewHolder = (UsageDetailViewHolder) obj;
            OverViewItemInfo overViewItemInfo =
                    i < usageSizeList.size() ? (OverViewItemInfo) usageSizeList.get(i) : null;
            if (overViewItemInfo != null) {
                AnalyzeStorageConstants$UiItemType mType = overViewItemInfo.mType;
                Intrinsics.checkNotNullExpressionValue(mType, "mType");
                usageDetailViewHolder.getClass();
                usageDetailViewHolder.item = mType;
                if (mType.getColorResId() != -1) {
                    View view = usageDetailViewHolder.detailColor;
                    if (view != null) {
                        view.setBackgroundTintList(
                                ColorStateList.valueOf(
                                        usageDetailViewHolder
                                                .this$0
                                                .getContext()
                                                .getColor(
                                                        usageDetailViewHolder.item
                                                                .getColorResId())));
                    }
                    TextView textView2 = usageDetailViewHolder.detailName;
                    if (textView2 != null) {
                        textView2.setText(usageDetailViewHolder.item.getTitleResId());
                    }
                    usageDetailViewHolder.itemView.setTag(
                            Integer.valueOf(usageDetailViewHolder.item.getTitleResId()));
                }
                TextView textView3 = usageDetailViewHolder.detailSize;
                if (textView3 != null) {
                    Long l = overViewItemInfo.mSize;
                    Intrinsics.checkNotNull(l);
                    if (l.longValue() < 0) {
                        formatFileSize = getContext().getString(R.string.as_couldnt_calculate);
                    } else {
                        if (((int) l.longValue()) == 0
                                && (textView = usageDetailViewHolder.detailSize) != null) {
                            textView.setContentDescription(
                                    ((int) l.longValue())
                                            + " "
                                            + getContext().getString(R.string.as_byte));
                        }
                        formatFileSize =
                                StringConverter.formatFileSize(0, l.longValue(), getContext());
                    }
                    textView3.setText(formatFileSize);
                }
            }
            i = i2;
        }
    }

    /* JADX WARN: Type inference failed for: r2v7, types: [com.samsung.android.settings.analyzestorage.ui.view.analyzestorage.UsageDetailItem$usageDetailClickListener$1] */
    public UsageDetailItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.domainType = -1;
        AsUsageInfoManager.Companion companion = AsUsageInfoManager.Companion;
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        this.asUsageInfoManager = companion.getInstance(context2);
        this.detailItemViewMap = new ArrayList();
        new ArrayList();
        this.summaryCount = 4;
        this.usageDetailClickListener =
                new View.OnClickListener() { // from class:
                    // com.samsung.android.settings.analyzestorage.ui.view.analyzestorage.UsageDetailItem$usageDetailClickListener$1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        AnalyzeStorageConstants$UiItemType analyzeStorageConstants$UiItemType;
                        final int i = 2;
                        final int i2 = 1;
                        Intrinsics.checkNotNullParameter(view, "view");
                        Object tag = view.getTag();
                        Activity activity = null;
                        Integer num = tag instanceof Integer ? (Integer) tag : null;
                        int intValue = num != null ? num.intValue() : -1;
                        UsageDetailItem usageDetailItem = UsageDetailItem.this;
                        OverViewController overViewController = usageDetailItem.controller;
                        if (overViewController != null) {
                            if (intValue == R.string.as_menu_apps) {
                                analyzeStorageConstants$UiItemType =
                                        AnalyzeStorageConstants$UiItemType.APPS;
                            } else if (intValue == R.string.as_images) {
                                analyzeStorageConstants$UiItemType =
                                        AnalyzeStorageConstants$UiItemType.IMAGES;
                            } else if (intValue == R.string.as_videos) {
                                analyzeStorageConstants$UiItemType =
                                        AnalyzeStorageConstants$UiItemType.VIDEOS;
                            } else if (intValue == R.string.as_audio) {
                                analyzeStorageConstants$UiItemType =
                                        AnalyzeStorageConstants$UiItemType.AUDIO;
                            } else if (intValue == R.string.as_compressed_files) {
                                analyzeStorageConstants$UiItemType =
                                        AnalyzeStorageConstants$UiItemType.COMPRESSED;
                            } else if (intValue == R.string.as_documents) {
                                analyzeStorageConstants$UiItemType =
                                        AnalyzeStorageConstants$UiItemType.DOCUMENTS;
                            } else if (intValue == R.string.as_installation_files) {
                                analyzeStorageConstants$UiItemType =
                                        AnalyzeStorageConstants$UiItemType.APK;
                            } else if (intValue == R.string.as_secure_folder) {
                                analyzeStorageConstants$UiItemType =
                                        AnalyzeStorageConstants$UiItemType.SECURE_FOLDER;
                            } else {
                                if (intValue != R.string.as_work_profile) {
                                    throw new IllegalArgumentException(
                                            "Unsupported OverView Items");
                                }
                                analyzeStorageConstants$UiItemType =
                                        AnalyzeStorageConstants$UiItemType.WORK_PROFILE;
                            }
                            int i3 = usageDetailItem.domainType;
                            boolean z = usageDetailItem.isShowMoreItem;
                            int indexOf =
                                    ((List)
                                                    ((HashMap) overViewController.UiItemTypeMap)
                                                            .get(Integer.valueOf(i3)))
                                            .indexOf(analyzeStorageConstants$UiItemType);
                            Integer num2 =
                                    i3 != 0
                                            ? i3 != 1
                                                    ? i3 != 101
                                                            ? z
                                                                    ? (Integer)
                                                                            SamsungAnalyticsConvertManager
                                                                                    .sOverViewOnedriveCloudExpandToSAEventId
                                                                                    .get(
                                                                                            analyzeStorageConstants$UiItemType)
                                                                    : (Integer)
                                                                            SamsungAnalyticsConvertManager
                                                                                    .sOverViewOnedriveCloudCollapseToSAEventId
                                                                                    .get(indexOf)
                                                            : z
                                                                    ? (Integer)
                                                                            SamsungAnalyticsConvertManager
                                                                                    .sOverViewGoogleCloudExpandToSAEventId
                                                                                    .get(
                                                                                            analyzeStorageConstants$UiItemType)
                                                                    : (Integer)
                                                                            SamsungAnalyticsConvertManager
                                                                                    .sOverViewGoogleCloudCollapseToSAEventId
                                                                                    .get(indexOf)
                                                    : z
                                                            ? (Integer)
                                                                    SamsungAnalyticsConvertManager
                                                                            .sOverViewSdCardExpandToSAEventId
                                                                            .get(
                                                                                    analyzeStorageConstants$UiItemType)
                                                            : (Integer)
                                                                    SamsungAnalyticsConvertManager
                                                                            .sOverViewSdCardCollapseToSAEventId
                                                                            .get(indexOf)
                                            : z
                                                    ? (Integer)
                                                            SamsungAnalyticsConvertManager
                                                                    .sOverViewInternalExpandToSAEventId
                                                                    .get(
                                                                            analyzeStorageConstants$UiItemType)
                                                    : (Integer)
                                                            SamsungAnalyticsConvertManager
                                                                    .sOverViewInternalCollapseToSAEventId
                                                                    .get(indexOf);
                            if (num2 != null) {
                                LoggingHelper.insertEventLogging(
                                        Integer.parseInt(
                                                PageType.ANALYZE_STORAGE_HOME.getScreenIdForSA()),
                                        num2.intValue(),
                                        analyzeStorageConstants$UiItemType.name());
                            }
                            AnalyzeStorageConstants$UiItemType analyzeStorageConstants$UiItemType2 =
                                    AnalyzeStorageConstants$UiItemType.APPS;
                            int i4 = overViewController.mInstanceId;
                            if (analyzeStorageConstants$UiItemType
                                    == analyzeStorageConstants$UiItemType2) {
                                final Intent intent =
                                        new Intent("android.intent.action.MANAGE_PACKAGE_STORAGE");
                                Context context3 = ActivityInfoStore.context;
                                ActivityInfoStore companion2 =
                                        ActivityInfoStore.Companion.getInstance(i4);
                                if (!((ArrayList) companion2.activityListenerList).isEmpty()) {
                                    List list = companion2.activityListenerList;
                                    activity =
                                            ((ActivityInfo$ActivityInfoListener)
                                                            ((ArrayList) list)
                                                                    .get(
                                                                            CollectionsKt__CollectionsKt
                                                                                    .getLastIndex(
                                                                                            list)))
                                                    .getActivity();
                                }
                                final int i5 = 0;
                                Optional.ofNullable(activity)
                                        .ifPresent(
                                                new Consumer() { // from class:
                                                    // com.samsung.android.settings.analyzestorage.presenter.controllers.OverViewController$$ExternalSyntheticLambda4
                                                    @Override // java.util.function.Consumer
                                                    public final void accept(Object obj) {
                                                        int i6 = i5;
                                                        Intent intent22 = intent;
                                                        switch (i6) {
                                                            case 0:
                                                                ((Activity) obj)
                                                                        .startActivityForResult(
                                                                                intent22, 0);
                                                                break;
                                                            case 1:
                                                                ((Activity) obj)
                                                                        .startActivity(intent22);
                                                                break;
                                                            default:
                                                                ((ActivityResultLauncher) obj)
                                                                        .launch(intent22);
                                                                break;
                                                        }
                                                    }
                                                });
                                return;
                            }
                            AnalyzeStorageConstants$UiItemType analyzeStorageConstants$UiItemType3 =
                                    AnalyzeStorageConstants$UiItemType.SECURE_FOLDER;
                            if (analyzeStorageConstants$UiItemType
                                            == analyzeStorageConstants$UiItemType3
                                    || analyzeStorageConstants$UiItemType
                                            == AnalyzeStorageConstants$UiItemType.WORK_PROFILE) {
                                r7 =
                                        analyzeStorageConstants$UiItemType
                                                        != analyzeStorageConstants$UiItemType3
                                                ? 3
                                                : 4;
                                final Intent intent2 =
                                        new Intent(
                                                "com.samsung.android.settings.analyzestorage.ENTER_SUB_USER");
                                intent2.putExtra("instanceId", i4);
                                intent2.putExtra("domainType", r7);
                                Context context4 = ActivityInfoStore.context;
                                ActivityInfoStore companion3 =
                                        ActivityInfoStore.Companion.getInstance(i4);
                                if (!((ArrayList) companion3.activityListenerList).isEmpty()) {
                                    List list2 = companion3.activityListenerList;
                                    activity =
                                            ((ActivityInfo$ActivityInfoListener)
                                                            ((ArrayList) list2)
                                                                    .get(
                                                                            CollectionsKt__CollectionsKt
                                                                                    .getLastIndex(
                                                                                            list2)))
                                                    .getActivity();
                                }
                                Optional.ofNullable(activity)
                                        .ifPresent(
                                                new Consumer() { // from class:
                                                    // com.samsung.android.settings.analyzestorage.presenter.controllers.OverViewController$$ExternalSyntheticLambda4
                                                    @Override // java.util.function.Consumer
                                                    public final void accept(Object obj) {
                                                        int i6 = i2;
                                                        Intent intent22 = intent2;
                                                        switch (i6) {
                                                            case 0:
                                                                ((Activity) obj)
                                                                        .startActivityForResult(
                                                                                intent22, 0);
                                                                break;
                                                            case 1:
                                                                ((Activity) obj)
                                                                        .startActivity(intent22);
                                                                break;
                                                            default:
                                                                ((ActivityResultLauncher) obj)
                                                                        .launch(intent22);
                                                                break;
                                                        }
                                                    }
                                                });
                                return;
                            }
                            overViewController.mPageInfo.mExtra.putInt("from_usage_type", i3);
                            final Intent intent3 =
                                    new Intent(
                                            "com.sec.android.app.myfiles.VIEW_ANALYZE_STORAGE_SUB_LIST");
                            intent3.setPackage("com.sec.android.app.myfiles");
                            int ordinal = analyzeStorageConstants$UiItemType.ordinal();
                            if (ordinal == 1) {
                                r7 = 3;
                            } else if (ordinal != 2) {
                                r7 = ordinal != 3 ? ordinal != 4 ? ordinal != 5 ? 2 : 8 : 6 : 5;
                            }
                            intent3.putExtra("SELECTOR_CATEGORY_TYPE", r7);
                            intent3.putExtra("domainType", i3);
                            Context context5 = ActivityInfoStore.context;
                            Optional.ofNullable(
                                            ActivityInfoStore.Companion.getInstance(i4)
                                                    .getActivityResultLauncher())
                                    .ifPresent(
                                            new Consumer() { // from class:
                                                // com.samsung.android.settings.analyzestorage.presenter.controllers.OverViewController$$ExternalSyntheticLambda4
                                                @Override // java.util.function.Consumer
                                                public final void accept(Object obj) {
                                                    int i6 = i;
                                                    Intent intent22 = intent3;
                                                    switch (i6) {
                                                        case 0:
                                                            ((Activity) obj)
                                                                    .startActivityForResult(
                                                                            intent22, 0);
                                                            break;
                                                        case 1:
                                                            ((Activity) obj)
                                                                    .startActivity(intent22);
                                                            break;
                                                        default:
                                                            ((ActivityResultLauncher) obj)
                                                                    .launch(intent22);
                                                            break;
                                                    }
                                                }
                                            });
                        }
                    }
                };
        this.handler = new Handler(Looper.getMainLooper());
    }
}
