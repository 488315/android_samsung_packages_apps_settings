package com.samsung.android.settings.display;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.ContentObserver;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.SearchIndexableData;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.secutil.Log;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.window.embedding.ActivityEmbeddingController;

import com.android.settings.DisplaySettings;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexableRaw;

import com.airbnb.lottie.LottieAnimationView;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.hardware.display.SemMdnieManager;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.core.SecSettingsBaseActivity;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.widget.SecRoundButtonView;
import com.samsung.android.settings.widget.SecWrapContentHeightViewPager;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class NewModePreview extends SettingsPreferenceFragment implements View.OnClickListener {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass7();
    public ScreenModeAdapter mAdapter;
    public SecRoundButtonView mAdvancedSettingButton;
    public final AnonymousClass8 mBatteryInfoReceiver;
    public final AnonymousClass9 mBluelightFilterObserver;
    public Context mContext;
    public DisplayMetrics mDisplayMetrics;
    public final AnonymousClass9 mEadObserver;
    public SemMdnieManager mMdnie;
    public NestedScrollView mNestedScrollView;
    public LinearLayout mPointArea;
    public TextView mPresetCool;
    public SeekBar mPresetSeekBar;
    public TextView mPresetWarm;
    public RelativeLayout mPreviewLayout;
    public AnimationDrawable mProgressExpandAnimation;
    public AnimationDrawable mProgressShrinkAnimation;
    public LottieAnimationView mQuestionIcon;
    public final ScreenModeItems mScreenModeItems;
    public RecyclerView mScreenModeList;
    public View mScreenModeView;
    public LinearLayout mSeekBarLayout;
    public final AnonymousClass9 mSeekBarObserver;
    public AnimationDrawable mThumbFadeInAnimation;
    public AnimationDrawable mThumbFadeOutAnimation;
    public AnimationDrawable mTickFadeInAnimation;
    public AnimationDrawable mTickFadeOutAnimation;
    public SecWrapContentHeightViewPager mViewPager;
    public ScreenModePreviewPagerAdapter mViewPagerAdapter;
    public LinearLayout mVividContainer;
    public AlertDialog mIncompatibleSettingDialog = null;
    public boolean mSupportWcgModeOnAmoled = false;
    public boolean mSupportVividPlusMode = false;
    public boolean mSupportNaturalModeWithoutWcgMode = false;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.display.NewModePreview$7, reason: invalid class name */
    public final class AnonymousClass7 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            boolean z = Rune.isSamsungDexMode(context) && Utils.isDesktopDualMode(context);
            boolean isAccessibilityVisionEnabled =
                    SecDisplayUtils.isAccessibilityVisionEnabled(context);
            if (Settings.System.getInt(
                                    context.getContentResolver(),
                                    "screen_mode_automatic_setting",
                                    1)
                            != 1
                    || z
                    || isAccessibilityVisionEnabled) {
                ((ArrayList) nonIndexableKeys).add("seekbar_white_balance");
            }
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            Resources resources = context.getResources();
            ArrayList arrayList = new ArrayList();
            new SearchIndexableRaw(context);
            String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            searchIndexableRaw.title = String.valueOf(R.string.sec_screen_mode_white_balance);
            searchIndexableRaw.screenTitle = resources.getString(R.string.sec_screen_mode_setting);
            ((SearchIndexableData) searchIndexableRaw).key = "seekbar_white_balance";
            arrayList.add(searchIndexableRaw);
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return Rune.supportScreenMode();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ScreenModeAdapter extends RecyclerView.Adapter {
        public final Context mContext;
        public final List mListItems = new ArrayList();
        public final ScreenModeItems mScreenModeItems;
        public final int mSelectValue;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.samsung.android.settings.display.NewModePreview$ScreenModeAdapter$1, reason: invalid class name */
        public final class AnonymousClass1 implements View.OnClickListener {
            public final /* synthetic */ RecyclerView.ViewHolder val$holder;
            public final /* synthetic */ int val$position;

            public AnonymousClass1(RecyclerView.ViewHolder viewHolder, int i) {
                this.val$holder = viewHolder;
                this.val$position = i;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int currentScreenMode =
                        NewModePreview.getCurrentScreenMode(
                                ScreenModeAdapter.this.mContext.getContentResolver());
                ScreenModeAdapter screenModeAdapter = ScreenModeAdapter.this;
                long j = currentScreenMode;
                int i = 0;
                while (true) {
                    if (i
                            >= ((ArrayList) screenModeAdapter.mScreenModeItems.mNameStringId)
                                    .size()) {
                        i = -1;
                        break;
                    } else if (((Integer) ((ArrayList) r6.mDBValue).get(i)).intValue() == j) {
                        break;
                    } else {
                        i++;
                    }
                }
                if (SecDisplayUtils.isAccessibilityVisionEnabled(
                        NewModePreview.this.getContext())) {
                    String accessibilityVisionMessage =
                            SecDisplayUtils.getAccessibilityVisionMessage(
                                    ScreenModeAdapter.this.mContext);
                    Context context = ScreenModeAdapter.this.mContext;
                    Toast.makeText(
                                    ScreenModeAdapter.this.mContext,
                                    context.getString(
                                            R.string.blue_light_disable_reason,
                                            accessibilityVisionMessage,
                                            context.getString(R.string.sec_screen_mode_setting)),
                                    0)
                            .show();
                    return;
                }
                ScreenModeAdapter screenModeAdapter2 = ScreenModeAdapter.this;
                RecyclerView.ViewHolder viewHolder = this.val$holder;
                int i2 = this.val$position;
                for (int i3 = 0;
                        i3 < ((ArrayList) screenModeAdapter2.mScreenModeItems.mNameStringId).size();
                        i3++) {
                    if (i3 == i2) {
                        ((ScreenModeList) ((ArrayList) screenModeAdapter2.mListItems).get(i3))
                                        .mIsChecked =
                                true;
                        ((RecyclerViewHolder) viewHolder)
                                .mCheckBoxView.setChecked(
                                        ((ScreenModeList)
                                                        ((ArrayList) screenModeAdapter2.mListItems)
                                                                .get(i3))
                                                .mIsChecked);
                    } else {
                        ((ScreenModeList) ((ArrayList) screenModeAdapter2.mListItems).get(i3))
                                        .mIsChecked =
                                false;
                    }
                    screenModeAdapter2.notifyItemChanged(i3);
                }
                boolean z =
                        Settings.System.getInt(
                                        ScreenModeAdapter.this.mContext.getContentResolver(),
                                        "blue_light_filter",
                                        0)
                                != 0;
                if (!z) {
                    View view2 = this.val$holder.itemView;
                    final int i4 = this.val$position;
                    view2.postDelayed(
                            new Runnable() { // from class:
                                             // com.samsung.android.settings.display.NewModePreview$ScreenModeAdapter$1$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    NewModePreview.ScreenModeAdapter.AnonymousClass1
                                            anonymousClass1 =
                                                    NewModePreview.ScreenModeAdapter.AnonymousClass1
                                                            .this;
                                    int i5 = i4;
                                    NewModePreview.ScreenModeAdapter screenModeAdapter3 =
                                            NewModePreview.ScreenModeAdapter.this;
                                    int i6 =
                                            ((NewModePreview.ScreenModeAdapter.ScreenModeList)
                                                            ((ArrayList)
                                                                            screenModeAdapter3
                                                                                    .mListItems)
                                                                    .get(i5))
                                                    .mListItemValue;
                                    NewModePreview newModePreview = NewModePreview.this;
                                    if (i6 == 4) {
                                        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
                                        newModePreview.mVividContainer.setVisibility(0);
                                        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
                                        Settings.System.putInt(
                                                screenModeAdapter3.mContext.getContentResolver(),
                                                "screen_mode_automatic_setting",
                                                1);
                                        Settings.System.putInt(
                                                screenModeAdapter3.mContext.getContentResolver(),
                                                "screen_mode_setting",
                                                4);
                                    } else {
                                        newModePreview.mVividContainer.setVisibility(8);
                                        Signature[] signatureArr2 = SecDisplayUtils.SIGNATURES;
                                        Settings.System.putInt(
                                                screenModeAdapter3.mContext.getContentResolver(),
                                                "screen_mode_automatic_setting",
                                                0);
                                        Settings.System.putInt(
                                                screenModeAdapter3.mContext.getContentResolver(),
                                                "screen_mode_setting",
                                                i6);
                                    }
                                    newModePreview.getMetricsCategory();
                                    LoggingHelper.insertEventLogging(4217, 4218, i5);
                                    Log.secE("ModePreview", "onItemClick setScreenMode : " + i6);
                                    newModePreview.setScreenMode(i6);
                                }
                            },
                            200L);
                    return;
                }
                Log.d(
                        "ModePreview",
                        "onClick() position: "
                                + this.val$position
                                + " isBlueLightFilterOn: "
                                + z
                                + " currentValue: "
                                + currentScreenMode);
                int i5 = this.val$position;
                if (i5 != i) {
                    NewModePreview newModePreview = NewModePreview.this;
                    Context context2 = newModePreview.mContext;
                    Toast.makeText(
                                    context2,
                                    context2.getResources()
                                            .getString(
                                                    R.string
                                                            .sec_screen_mode_toast_turn_off_blue_light_filter),
                                    0)
                            .show();
                    Settings.System.putInt(
                            newModePreview.mContext.getContentResolver(), "blue_light_filter", 0);
                    Intent intent = new Intent();
                    intent.setComponent(
                            new ComponentName(
                                    "com.samsung.android.bluelightfilter",
                                    "com.samsung.android.bluelightfilter.BlueLightFilterService"));
                    intent.putExtra("BLUE_LIGHT_FILTER_SERVICE_TYPE", 22);
                    newModePreview.mContext.startService(intent);
                    int intValue =
                            ((Integer)
                                            ((ArrayList) newModePreview.mScreenModeItems.mDBValue)
                                                    .get(i5))
                                    .intValue();
                    if (intValue == 4) {
                        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
                        newModePreview.mVividContainer.setVisibility(0);
                        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
                        Settings.System.putInt(
                                newModePreview.mContext.getContentResolver(),
                                "screen_mode_automatic_setting",
                                1);
                        Settings.System.putInt(
                                newModePreview.mContext.getContentResolver(),
                                "screen_mode_setting",
                                4);
                    } else {
                        newModePreview.mVividContainer.setVisibility(8);
                        Signature[] signatureArr2 = SecDisplayUtils.SIGNATURES;
                        Settings.System.putInt(
                                newModePreview.mContext.getContentResolver(),
                                "screen_mode_automatic_setting",
                                0);
                        Settings.System.putInt(
                                newModePreview.mContext.getContentResolver(),
                                "screen_mode_setting",
                                intValue);
                    }
                    newModePreview.getMetricsCategory();
                    LoggingHelper.insertEventLogging(4217, 4218, i5);
                    Log.secE("ModePreview", "onItemClick setScreenMode : " + intValue);
                    newModePreview.setScreenMode(intValue);
                }
            }
        }

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class RecyclerViewHolder extends RecyclerView.ViewHolder {
            public RadioButton mCheckBoxView;
            public TextView mTitleView;
        }

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class ScreenModeList {
            public boolean mIsChecked;
            public String mListItemTitle;
            public int mListItemValue;
        }

        public ScreenModeAdapter(Context context, ScreenModeItems screenModeItems, int i) {
            this.mContext = context;
            this.mScreenModeItems = screenModeItems;
            this.mSelectValue = i;
            if (screenModeItems == null) {
                Log.e("ModePreview", "addScreenModeListView failed");
                return;
            }
            for (int i2 = 0; i2 < ((ArrayList) screenModeItems.mNameStringId).size(); i2++) {
                String string =
                        this.mContext
                                .getResources()
                                .getString(
                                        ((Integer)
                                                        ((ArrayList) screenModeItems.mNameStringId)
                                                                .get(i2))
                                                .intValue());
                int intValue =
                        ((Integer) ((ArrayList) screenModeItems.mDBValue).get(i2)).intValue();
                ScreenModeList screenModeList = new ScreenModeList();
                screenModeList.mListItemTitle = string;
                screenModeList.mListItemValue = intValue;
                if (intValue == this.mSelectValue) {
                    screenModeList.mIsChecked = true;
                }
                ((ArrayList) this.mListItems).add(screenModeList);
                notifyDataSetChanged();
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final int getItemCount() {
            return ((ArrayList) this.mListItems).size();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) viewHolder;
            recyclerViewHolder.mTitleView.setText(
                    ((ScreenModeList) ((ArrayList) this.mListItems).get(i)).mListItemTitle);
            recyclerViewHolder.mCheckBoxView.setChecked(
                    ((ScreenModeList) ((ArrayList) this.mListItems).get(i)).mIsChecked);
            viewHolder.itemView.setOnClickListener(new AnonymousClass1(viewHolder, i));
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View m =
                    MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(
                            viewGroup, R.layout.sec_screen_mode_list_item, viewGroup, false);
            RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(m);
            recyclerViewHolder.mTitleView = (TextView) m.findViewById(R.id.screen_mode_title);
            recyclerViewHolder.mCheckBoxView = (RadioButton) m.findViewById(R.id.checkbox);
            return recyclerViewHolder;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ScreenModeItems {
        public List mDBValue;
        public List mNameStringId;

        public final void addItem(int i, int i2) {
            ((ArrayList) this.mNameStringId).add(Integer.valueOf(i));
            ((ArrayList) this.mDBValue).add(Integer.valueOf(i2));
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class VerticalLineItemDecoration extends RecyclerView.ItemDecoration {
        public final Drawable mDivider;

        public VerticalLineItemDecoration(Context context) {
            TypedArray obtainStyledAttributes =
                    context.obtainStyledAttributes(new int[] {android.R.attr.listDivider});
            this.mDivider = obtainStyledAttributes.getDrawable(0);
            obtainStyledAttributes.recycle();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public final void getItemOffsets(
                Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            if (recyclerView.getAdapter() == null
                    || RecyclerView.getChildAdapterPosition(view)
                            == recyclerView.getAdapter().getItemCount() - 1) {
                return;
            }
            rect.bottom = this.mDivider.getIntrinsicHeight();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public final void onDraw(Canvas canvas, RecyclerView recyclerView) {
            int childCount = recyclerView.getChildCount();
            Resources resources = NewModePreview.this.mContext.getResources();
            int dimensionPixelSize =
                    resources.getDimensionPixelSize(
                                    R.dimen
                                            .sec_widget_list_checkbox_width_for_divider_padding_inset)
                            + resources.getDimensionPixelSize(
                                    R.dimen.sec_widget_list_checkbox_width_for_divider_inset);
            for (int i = 0; i < childCount - 1; i++) {
                View childAt = recyclerView.getChildAt(i);
                RecyclerView.LayoutParams layoutParams =
                        (RecyclerView.LayoutParams) childAt.getLayoutParams();
                int paddingLeft = recyclerView.getPaddingLeft() + dimensionPixelSize;
                int width = recyclerView.getWidth() - recyclerView.getPaddingRight();
                int bottom =
                        childAt.getBottom()
                                + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
                int intrinsicHeight = this.mDivider.getIntrinsicHeight() + bottom;
                if (recyclerView.getLayoutDirection() == 1) {
                    paddingLeft = recyclerView.getPaddingLeft();
                    width =
                            (recyclerView.getWidth() - recyclerView.getPaddingRight())
                                    - dimensionPixelSize;
                }
                this.mDivider.setBounds(paddingLeft, bottom, width, intrinsicHeight);
                this.mDivider.draw(canvas);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.samsung.android.settings.display.NewModePreview$8] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.samsung.android.settings.display.NewModePreview$9] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.samsung.android.settings.display.NewModePreview$9] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.samsung.android.settings.display.NewModePreview$9] */
    public NewModePreview() {
        ScreenModeItems screenModeItems = new ScreenModeItems();
        screenModeItems.mNameStringId = new ArrayList();
        screenModeItems.mDBValue = new ArrayList();
        this.mScreenModeItems = screenModeItems;
        this.mBatteryInfoReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.samsung.android.settings.display.NewModePreview.8
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        if ("android.intent.action.BATTERY_CHANGED".equals(intent.getAction())) {
                            NewModePreview newModePreview = NewModePreview.this;
                            com.android.settingslib.Utils.getBatteryLevel(intent);
                            BaseSearchIndexProvider baseSearchIndexProvider =
                                    NewModePreview.SEARCH_INDEX_DATA_PROVIDER;
                            newModePreview.getClass();
                        }
                    }
                };
        final int i = 0;
        this.mBluelightFilterObserver =
                new ContentObserver(
                        this,
                        new Handler()) { // from class:
                                         // com.samsung.android.settings.display.NewModePreview.9
                    public final /* synthetic */ NewModePreview this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        switch (i) {
                            case 0:
                                NewModePreview newModePreview = this.this$0;
                                BaseSearchIndexProvider baseSearchIndexProvider =
                                        NewModePreview.SEARCH_INDEX_DATA_PROVIDER;
                                newModePreview.setEnabledExclusiveSettings();
                                break;
                            case 1:
                                NewModePreview newModePreview2 = this.this$0;
                                BaseSearchIndexProvider baseSearchIndexProvider2 =
                                        NewModePreview.SEARCH_INDEX_DATA_PROVIDER;
                                newModePreview2.setEnabledExclusiveSettings();
                                break;
                            default:
                                if (this.this$0.mPresetSeekBar.getProgress()
                                        != 4
                                                - Settings.System.getIntForUser(
                                                        this.this$0.mContext.getContentResolver(),
                                                        "sec_display_preset_index",
                                                        2,
                                                        0)) {
                                    this.this$0.createScreenModeView(
                                            LayoutInflater.from(this.this$0.mContext),
                                            (ViewGroup)
                                                    this.this$0
                                                            .getActivity()
                                                            .findViewById(R.id.main_content));
                                    break;
                                }
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mEadObserver =
                new ContentObserver(
                        this,
                        new Handler()) { // from class:
                                         // com.samsung.android.settings.display.NewModePreview.9
                    public final /* synthetic */ NewModePreview this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        switch (i2) {
                            case 0:
                                NewModePreview newModePreview = this.this$0;
                                BaseSearchIndexProvider baseSearchIndexProvider =
                                        NewModePreview.SEARCH_INDEX_DATA_PROVIDER;
                                newModePreview.setEnabledExclusiveSettings();
                                break;
                            case 1:
                                NewModePreview newModePreview2 = this.this$0;
                                BaseSearchIndexProvider baseSearchIndexProvider2 =
                                        NewModePreview.SEARCH_INDEX_DATA_PROVIDER;
                                newModePreview2.setEnabledExclusiveSettings();
                                break;
                            default:
                                if (this.this$0.mPresetSeekBar.getProgress()
                                        != 4
                                                - Settings.System.getIntForUser(
                                                        this.this$0.mContext.getContentResolver(),
                                                        "sec_display_preset_index",
                                                        2,
                                                        0)) {
                                    this.this$0.createScreenModeView(
                                            LayoutInflater.from(this.this$0.mContext),
                                            (ViewGroup)
                                                    this.this$0
                                                            .getActivity()
                                                            .findViewById(R.id.main_content));
                                    break;
                                }
                                break;
                        }
                    }
                };
        final int i3 = 2;
        this.mSeekBarObserver =
                new ContentObserver(
                        this,
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.samsung.android.settings.display.NewModePreview.9
                    public final /* synthetic */ NewModePreview this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        switch (i3) {
                            case 0:
                                NewModePreview newModePreview = this.this$0;
                                BaseSearchIndexProvider baseSearchIndexProvider =
                                        NewModePreview.SEARCH_INDEX_DATA_PROVIDER;
                                newModePreview.setEnabledExclusiveSettings();
                                break;
                            case 1:
                                NewModePreview newModePreview2 = this.this$0;
                                BaseSearchIndexProvider baseSearchIndexProvider2 =
                                        NewModePreview.SEARCH_INDEX_DATA_PROVIDER;
                                newModePreview2.setEnabledExclusiveSettings();
                                break;
                            default:
                                if (this.this$0.mPresetSeekBar.getProgress()
                                        != 4
                                                - Settings.System.getIntForUser(
                                                        this.this$0.mContext.getContentResolver(),
                                                        "sec_display_preset_index",
                                                        2,
                                                        0)) {
                                    this.this$0.createScreenModeView(
                                            LayoutInflater.from(this.this$0.mContext),
                                            (ViewGroup)
                                                    this.this$0
                                                            .getActivity()
                                                            .findViewById(R.id.main_content));
                                    break;
                                }
                                break;
                        }
                    }
                };
    }

    public static int getCurrentScreenMode(ContentResolver contentResolver) {
        if (Settings.System.getInt(contentResolver, "screen_mode_automatic_setting", 1) == 1) {
            return 4;
        }
        return Settings.System.getInt(contentResolver, "screen_mode_setting", 1);
    }

    public final View createScreenModeView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        View findViewById;
        if (viewGroup != null) {
            viewGroup.removeAllViewsInLayout();
        }
        if (Utils.isTablet()
                && ActivityEmbeddingController.getInstance(getActivity())
                        .isActivityEmbedded(getActivity())) {
            this.mScreenModeView =
                    layoutInflater.inflate(R.layout.sec_new_mode_preview_tablet, viewGroup);
        } else {
            this.mScreenModeView = layoutInflater.inflate(R.layout.sec_new_mode_preview, viewGroup);
        }
        this.mScreenModeView.semSetRoundedCorners(15);
        this.mScreenModeView.semSetRoundedCornerColor(
                15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        this.mViewPager =
                (SecWrapContentHeightViewPager) this.mScreenModeView.findViewById(R.id.pager);
        ScreenModePreviewPagerAdapter screenModePreviewPagerAdapter =
                new ScreenModePreviewPagerAdapter(this.mContext);
        this.mViewPagerAdapter = screenModePreviewPagerAdapter;
        this.mViewPager.setAdapter(screenModePreviewPagerAdapter);
        this.mViewPager.setOffscreenPageLimit(this.mViewPagerAdapter.mDescriptions.size());
        this.mViewPager.setOnPageChangeListener(
                new ViewPager
                        .OnPageChangeListener() { // from class:
                                                  // com.samsung.android.settings.display.NewModePreview.1
                    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                    public final void onPageSelected(int i) {
                        int i2 = 0;
                        while (true) {
                            NewModePreview newModePreview = NewModePreview.this;
                            if (i2 >= newModePreview.mPointArea.getChildCount()) {
                                ((ImageView) newModePreview.mPointArea.getChildAt(i))
                                        .setImageResource(
                                                R.drawable.sec_screen_mode_page_indicator_selected);
                                return;
                            } else {
                                ((ImageView) newModePreview.mPointArea.getChildAt(i2))
                                        .setImageResource(
                                                R.drawable
                                                        .sec_screen_mode_page_indicator_unselected);
                                i2++;
                            }
                        }
                    }

                    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                    public final void onPageScrollStateChanged(int i) {}

                    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                    public final void onPageScrolled(int i, int i2, float f) {}
                });
        boolean z = getContext().getResources().getConfiguration().orientation == 2;
        int listHorizontalPadding = Utils.getListHorizontalPadding(this.mContext);
        if (!(Utils.isTablet()
                        && ActivityEmbeddingController.getInstance(getActivity())
                                .isActivityEmbedded(getActivity()))
                && z) {
            ((LinearLayout) this.mScreenModeView.findViewById(R.id.new_mode_preview_layout))
                    .setPaddingRelative(listHorizontalPadding, 0, 0, 0);
            this.mNestedScrollView =
                    (NestedScrollView)
                            this.mScreenModeView.findViewById(R.id.screen_mode_text_container);
        } else {
            this.mNestedScrollView =
                    (NestedScrollView)
                            this.mScreenModeView.findViewById(R.id.new_mode_preview_layout);
        }
        this.mNestedScrollView.setPaddingRelative(
                listHorizontalPadding, 0, listHorizontalPadding, 0);
        this.mNestedScrollView.semSetRoundedCorners(15);
        this.mNestedScrollView.semSetRoundedCornerColor(
                15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        this.mNestedScrollView.semSetRoundedCornerOffset(listHorizontalPadding);
        if ((getActivity() instanceof SecSettingsBaseActivity)
                && (findViewById = getActivity().findViewById(R.id.round_corner)) != null) {
            CoordinatorLayout.LayoutParams layoutParams =
                    (CoordinatorLayout.LayoutParams) findViewById.getLayoutParams();
            layoutParams.setMarginsRelative(listHorizontalPadding, 0, listHorizontalPadding, 0);
            findViewById.setLayoutParams(layoutParams);
            findViewById.semSetRoundedCorners(12);
            findViewById.semSetRoundedCornerColor(
                    12, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        }
        RelativeLayout relativeLayout =
                (RelativeLayout) this.mScreenModeView.findViewById(R.id.preview_layout);
        this.mPreviewLayout = relativeLayout;
        if (relativeLayout != null) {
            ViewGroup.LayoutParams layoutParams2 = relativeLayout.getLayoutParams();
            if (Utils.isTablet()) {
                layoutParams2.height = Math.round(this.mDisplayMetrics.heightPixels * 0.45f);
                this.mPreviewLayout.setLayoutParams(layoutParams2);
            } else {
                layoutParams2.height = Math.round(this.mDisplayMetrics.heightPixels * 0.35f);
                this.mPreviewLayout.setLayoutParams(layoutParams2);
            }
            this.mPreviewLayout.semSetRoundedCorners(15);
            this.mPreviewLayout.semSetRoundedCornerColor(
                    15,
                    this.mContext.getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        }
        this.mPointArea = (LinearLayout) this.mScreenModeView.findViewById(R.id.point_area);
        int size = this.mViewPagerAdapter.mDescriptions.size();
        final int i = 0;
        while (i < size) {
            ImageView imageView =
                    (ImageView)
                            layoutInflater.inflate(
                                    R.layout.sec_screen_mode_pager_circle, (ViewGroup) null);
            int i2 = i + 1;
            imageView.setContentDescription(
                    String.format(
                            this.mContext
                                    .getResources()
                                    .getString(R.string.page_description_template),
                            Integer.valueOf(i2),
                            Integer.valueOf(size)));
            imageView.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.settings.display.NewModePreview.2
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            NewModePreview.this.mViewPager.setCurrentItem(i);
                        }
                    });
            this.mPointArea.addView(imageView);
            i = i2;
        }
        if (this.mPointArea.getChildCount() > 0) {
            ((ImageView) this.mPointArea.getChildAt(0))
                    .setImageResource(R.drawable.sec_screen_mode_page_indicator_selected);
        }
        if (this.mPointArea.getChildCount() == 1) {
            this.mPointArea.setVisibility(8);
        }
        if (Utils.isRTL(this.mContext)) {
            this.mViewPager.setCurrentItem(size);
        }
        RecyclerView recyclerView =
                (RecyclerView) this.mScreenModeView.findViewById(R.id.mode_list);
        this.mScreenModeList = recyclerView;
        recyclerView.setAdapter(this.mAdapter);
        this.mScreenModeList.setSelected(true);
        this.mScreenModeList.setClickable(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(1);
        linearLayoutManager.mAutoMeasure = true;
        this.mScreenModeList.setLayoutManager(linearLayoutManager);
        this.mScreenModeList.setHasFixedSize(true);
        this.mScreenModeList.setNestedScrollingEnabled(false);
        this.mScreenModeList.addItemDecoration(new VerticalLineItemDecoration(this.mContext));
        this.mScreenModeList.semSetRoundedCorners(15);
        this.mScreenModeList.semSetRoundedCornerColor(
                15, this.mContext.getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        this.mVividContainer =
                (LinearLayout) this.mScreenModeView.findViewById(R.id.vivid_container);
        if (Settings.System.getInt(
                        this.mContext.getContentResolver(), "screen_mode_automatic_setting", 1)
                == 1) {
            String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            this.mVividContainer.setVisibility(0);
            Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        } else {
            this.mVividContainer.setVisibility(8);
            Signature[] signatureArr2 = SecDisplayUtils.SIGNATURES;
        }
        if (!Utils.isTablet() && this.mContext.getResources().getConfiguration().orientation == 2) {
            NestedScrollView nestedScrollView =
                    (NestedScrollView)
                            this.mScreenModeView.findViewById(R.id.screen_mode_text_container);
            nestedScrollView.semSetRoundedCorners(15);
            nestedScrollView.semSetRoundedCornerColor(
                    15,
                    this.mContext.getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        }
        LinearLayout linearLayout =
                (LinearLayout) this.mScreenModeView.findViewById(R.id.seekbar_area);
        this.mSeekBarLayout = linearLayout;
        if (linearLayout != null) {
            linearLayout.semSetRoundedCorners(15);
            this.mSeekBarLayout.semSetRoundedCornerColor(
                    15,
                    this.mContext.getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        }
        this.mPresetCool = (TextView) this.mScreenModeView.findViewById(R.id.preset_cool);
        this.mPresetWarm = (TextView) this.mScreenModeView.findViewById(R.id.preset_warm);
        LottieAnimationView lottieAnimationView =
                (LottieAnimationView) this.mScreenModeView.findViewById(R.id.question_icon);
        this.mQuestionIcon = lottieAnimationView;
        lottieAnimationView.setContentDescription(
                this.mContext.getString(R.string.sec_screen_mode_double_tap_for_more_information));
        this.mQuestionIcon.setOnClickListener(
                new View.OnClickListener() { // from class:
                    // com.samsung.android.settings.display.NewModePreview.3
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i3;
                        int i4;
                        if (SecDisplayUtils.isAccessibilityVisionEnabled(
                                NewModePreview.this.mContext)) {
                            String accessibilityVisionMessage =
                                    SecDisplayUtils.getAccessibilityVisionMessage(
                                            NewModePreview.this.mContext);
                            Context context = NewModePreview.this.mContext;
                            Toast.makeText(
                                            NewModePreview.this.mContext,
                                            context.getString(
                                                    R.string.blue_light_disable_reason,
                                                    accessibilityVisionMessage,
                                                    context.getString(
                                                            R.string.sec_screen_mode_setting)),
                                            0)
                                    .show();
                            return;
                        }
                        final NewModePreview newModePreview = NewModePreview.this;
                        AlertDialog alertDialog = newModePreview.mIncompatibleSettingDialog;
                        if (alertDialog == null || !alertDialog.isShowing()) {
                            newModePreview.mIncompatibleSettingDialog = null;
                            DialogInterface.OnClickListener onClickListener =
                                    new DialogInterface.OnClickListener() { // from class:
                                        // com.samsung.android.settings.display.NewModePreview.6
                                        @Override // android.content.DialogInterface.OnClickListener
                                        public final void onClick(
                                                DialogInterface dialogInterface, int i5) {
                                            if (Settings.System.getInt(
                                                            NewModePreview.this.mContext
                                                                    .getContentResolver(),
                                                            "blue_light_filter",
                                                            0)
                                                    != 0) {
                                                Settings.System.putInt(
                                                        NewModePreview.this.mContext
                                                                .getContentResolver(),
                                                        "blue_light_filter",
                                                        0);
                                                Intent intent = new Intent();
                                                intent.setComponent(
                                                        new ComponentName(
                                                                "com.samsung.android.bluelightfilter",
                                                                "com.samsung.android.bluelightfilter.BlueLightFilterService"));
                                                intent.putExtra(
                                                        "BLUE_LIGHT_FILTER_SERVICE_TYPE", 22);
                                                NewModePreview.this.mContext.startService(intent);
                                            }
                                            String str2 =
                                                    Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
                                            if (Settings.System.getInt(
                                                            NewModePreview.this
                                                                    .getContentResolver(),
                                                            "ead_enabled",
                                                            0)
                                                    != 0) {
                                                Settings.System.putInt(
                                                        NewModePreview.this.getContentResolver(),
                                                        "ead_enabled",
                                                        0);
                                            }
                                            dialogInterface.dismiss();
                                        }
                                    };
                            boolean z2 =
                                    Settings.System.getInt(
                                                    newModePreview.mContext.getContentResolver(),
                                                    "blue_light_filter",
                                                    0)
                                            != 0;
                            String str2 = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
                            boolean z3 =
                                    Settings.System.getInt(
                                                    newModePreview.getContentResolver(),
                                                    "ead_enabled",
                                                    0)
                                            != 0;
                            if (z2 && z3) {
                                i3 =
                                        R.string
                                                .sec_screen_mode_dlg_turn_off_blue_light_filter_n_ead_title;
                                i4 =
                                        R.string
                                                .sec_screen_mode_dlg_turn_off_blue_light_filter_n_ead_message;
                            } else if (z2 || !z3) {
                                i3 = R.string.sec_screen_mode_dlg_turn_off_blue_light_filter_title;
                                i4 =
                                        R.string
                                                .sec_screen_mode_dlg_turn_off_blue_light_filter_message;
                            } else {
                                i3 = R.string.sec_screen_mode_dlg_turn_off_ead_title;
                                i4 = R.string.sec_screen_mode_dlg_turn_off_ead_message;
                            }
                            AlertDialog create =
                                    new AlertDialog.Builder(newModePreview.mContext)
                                            .setCancelable(true)
                                            .setTitle(i3)
                                            .setMessage(i4)
                                            .setNegativeButton(
                                                    newModePreview
                                                            .mContext
                                                            .getResources()
                                                            .getString(R.string.dlg_cancel),
                                                    (DialogInterface.OnClickListener) null)
                                            .setPositiveButton(
                                                    newModePreview
                                                            .mContext
                                                            .getResources()
                                                            .getString(R.string.turn_off_button),
                                                    onClickListener)
                                            .create();
                            newModePreview.mIncompatibleSettingDialog = create;
                            create.show();
                        }
                    }
                });
        SeekBar seekBar = (SeekBar) this.mScreenModeView.findViewById(R.id.seekbar_white_balance);
        this.mPresetSeekBar = seekBar;
        if (seekBar != null) {
            seekBar.setContentDescription(
                    this.mContext.getString(R.string.sec_screen_mode_white_balance));
            this.mPresetSeekBar.setOnTouchListener(
                    new View
                            .OnTouchListener() { // from class:
                                                 // com.samsung.android.settings.display.NewModePreview.4
                        @Override // android.view.View.OnTouchListener
                        public final boolean onTouch(View view, MotionEvent motionEvent) {
                            if (motionEvent.getAction() == 0) {
                                NewModePreview newModePreview = NewModePreview.this;
                                newModePreview.mPresetSeekBar.setThumb(
                                        newModePreview.mThumbFadeOutAnimation);
                                NewModePreview.this.mThumbFadeOutAnimation.start();
                                NewModePreview newModePreview2 = NewModePreview.this;
                                newModePreview2.mPresetSeekBar.setTickMark(
                                        newModePreview2.mTickFadeInAnimation);
                                NewModePreview.this.mTickFadeInAnimation.start();
                                NewModePreview newModePreview3 = NewModePreview.this;
                                newModePreview3.mPresetSeekBar.setProgressDrawable(
                                        newModePreview3.mProgressExpandAnimation);
                                NewModePreview.this.mProgressExpandAnimation.start();
                                NewModePreview newModePreview4 = NewModePreview.this;
                                newModePreview4.mPresetSeekBar.setThumb(
                                        newModePreview4.mContext.getDrawable(
                                                R.drawable.sec_screen_mode_progress_thumb_end));
                                return false;
                            }
                            if (motionEvent.getAction() != 1 && motionEvent.getAction() != 3) {
                                return false;
                            }
                            NewModePreview newModePreview5 = NewModePreview.this;
                            newModePreview5.mPresetSeekBar.setTickMark(
                                    newModePreview5.mTickFadeOutAnimation);
                            NewModePreview.this.mTickFadeOutAnimation.start();
                            NewModePreview newModePreview6 = NewModePreview.this;
                            newModePreview6.mPresetSeekBar.setProgressDrawable(
                                    newModePreview6.mProgressShrinkAnimation);
                            NewModePreview.this.mProgressShrinkAnimation.start();
                            NewModePreview newModePreview7 = NewModePreview.this;
                            newModePreview7.mPresetSeekBar.setThumb(
                                    newModePreview7.mThumbFadeInAnimation);
                            NewModePreview.this.mThumbFadeInAnimation.start();
                            NewModePreview newModePreview8 = NewModePreview.this;
                            newModePreview8.mProgressExpandAnimation =
                                    (AnimationDrawable)
                                            newModePreview8.mContext.getDrawable(
                                                    R.drawable
                                                            .sec_screen_mode_progress_white_balance_expand_animation);
                            NewModePreview newModePreview9 = NewModePreview.this;
                            newModePreview9.mProgressShrinkAnimation =
                                    (AnimationDrawable)
                                            newModePreview9.mContext.getDrawable(
                                                    R.drawable
                                                            .sec_screen_mode_progress_white_balance_shrink_animation);
                            NewModePreview newModePreview10 = NewModePreview.this;
                            newModePreview10.mThumbFadeInAnimation =
                                    (AnimationDrawable)
                                            newModePreview10.mContext.getDrawable(
                                                    R.drawable
                                                            .sec_screen_mode_progress_thumb_fade_in_animation);
                            NewModePreview newModePreview11 = NewModePreview.this;
                            newModePreview11.mThumbFadeOutAnimation =
                                    (AnimationDrawable)
                                            newModePreview11.mContext.getDrawable(
                                                    R.drawable
                                                            .sec_screen_mode_progress_thumb_fade_out_animation);
                            NewModePreview newModePreview12 = NewModePreview.this;
                            newModePreview12.mTickFadeInAnimation =
                                    (AnimationDrawable)
                                            newModePreview12.mContext.getDrawable(
                                                    R.drawable
                                                            .sec_screen_mode_progress_white_balance_tick_fade_in_animation);
                            NewModePreview newModePreview13 = NewModePreview.this;
                            newModePreview13.mTickFadeOutAnimation =
                                    (AnimationDrawable)
                                            newModePreview13.mContext.getDrawable(
                                                    R.drawable
                                                            .sec_screen_mode_progress_white_balance_tick_fade_out_animation);
                            return false;
                        }
                    });
            int intForUser =
                    Settings.System.getIntForUser(
                            this.mContext.getContentResolver(), "sec_display_preset_index", 2, 0);
            Log.d("ModePreview", "current preset : " + intForUser);
            this.mPresetSeekBar.setProgress(4 - intForUser);
            this.mPresetSeekBar.setOnSeekBarChangeListener(
                    new SeekBar
                            .OnSeekBarChangeListener() { // from class:
                                                         // com.samsung.android.settings.display.NewModePreview.5
                        @Override // android.widget.SeekBar.OnSeekBarChangeListener
                        public final void onProgressChanged(SeekBar seekBar2, int i3, boolean z2) {
                            if (z2) {
                                int i4 = 4 - i3;
                                int intForUser2 =
                                        Settings.System.getIntForUser(
                                                NewModePreview.this.mContext.getContentResolver(),
                                                "sec_display_preset_index",
                                                2,
                                                0);
                                Log.d("ModePreview", "change preset : " + i4);
                                NewModePreview.this.getMetricsCategory();
                                LoggingHelper.insertEventLogging(4217, 7455, (long) i4);
                                Settings.System.putIntForUser(
                                        NewModePreview.this.mContext.getContentResolver(),
                                        "sec_display_preset_index",
                                        i4,
                                        0);
                                if (intForUser2 != i4) {
                                    seekBar2.performHapticFeedback(
                                            HapticFeedbackConstants.semGetVibrationIndex(41));
                                }
                            }
                        }

                        @Override // android.widget.SeekBar.OnSeekBarChangeListener
                        public final void onStopTrackingTouch(SeekBar seekBar2) {
                            NewModePreview newModePreview = NewModePreview.this;
                            newModePreview.mPresetSeekBar.setTickMark(
                                    newModePreview.mTickFadeOutAnimation);
                            NewModePreview.this.mTickFadeOutAnimation.start();
                            NewModePreview newModePreview2 = NewModePreview.this;
                            newModePreview2.mPresetSeekBar.setProgressDrawable(
                                    newModePreview2.mProgressShrinkAnimation);
                            NewModePreview.this.mProgressShrinkAnimation.start();
                            NewModePreview newModePreview3 = NewModePreview.this;
                            newModePreview3.mPresetSeekBar.setThumb(
                                    newModePreview3.mThumbFadeInAnimation);
                            NewModePreview.this.mThumbFadeInAnimation.start();
                            NewModePreview newModePreview4 = NewModePreview.this;
                            newModePreview4.mProgressExpandAnimation =
                                    (AnimationDrawable)
                                            newModePreview4.mContext.getDrawable(
                                                    R.drawable
                                                            .sec_screen_mode_progress_white_balance_expand_animation);
                            NewModePreview newModePreview5 = NewModePreview.this;
                            newModePreview5.mProgressShrinkAnimation =
                                    (AnimationDrawable)
                                            newModePreview5.mContext.getDrawable(
                                                    R.drawable
                                                            .sec_screen_mode_progress_white_balance_shrink_animation);
                            NewModePreview newModePreview6 = NewModePreview.this;
                            newModePreview6.mThumbFadeInAnimation =
                                    (AnimationDrawable)
                                            newModePreview6.mContext.getDrawable(
                                                    R.drawable
                                                            .sec_screen_mode_progress_thumb_fade_in_animation);
                            NewModePreview newModePreview7 = NewModePreview.this;
                            newModePreview7.mThumbFadeOutAnimation =
                                    (AnimationDrawable)
                                            newModePreview7.mContext.getDrawable(
                                                    R.drawable
                                                            .sec_screen_mode_progress_thumb_fade_out_animation);
                            NewModePreview newModePreview8 = NewModePreview.this;
                            newModePreview8.mTickFadeInAnimation =
                                    (AnimationDrawable)
                                            newModePreview8.mContext.getDrawable(
                                                    R.drawable
                                                            .sec_screen_mode_progress_white_balance_tick_fade_in_animation);
                            NewModePreview newModePreview9 = NewModePreview.this;
                            newModePreview9.mTickFadeOutAnimation =
                                    (AnimationDrawable)
                                            newModePreview9.mContext.getDrawable(
                                                    R.drawable
                                                            .sec_screen_mode_progress_white_balance_tick_fade_out_animation);
                        }

                        @Override // android.widget.SeekBar.OnSeekBarChangeListener
                        public final void onStartTrackingTouch(SeekBar seekBar2) {}
                    });
        }
        SecRoundButtonView secRoundButtonView =
                (SecRoundButtonView)
                        this.mScreenModeView.findViewById(R.id.advanced_setting_button);
        this.mAdvancedSettingButton = secRoundButtonView;
        secRoundButtonView.setOnClickListener(this);
        return this.mScreenModeView;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return DisplaySettings.class.getName();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        if (this.mContext == null) {
            this.mContext = getContext();
        }
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        return 4217;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return "top_level_display";
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        if (view.getId() == R.id.advanced_setting_button) {
            Log.d("ModePreview", "Adaptive display settings icon onClick");
            SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
            String name = SecAdaptiveDisplaySettings.class.getName();
            SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
            launchRequest.mDestinationName = name;
            subSettingLauncher.setTitleRes(R.string.sec_screen_mode_advanced_settings, null);
            getMetricsCategory();
            launchRequest.mSourceMetricsCategory = 4217;
            subSettingLauncher.launch();
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mDisplayMetrics = getContext().getResources().getDisplayMetrics();
        createScreenModeView(
                LayoutInflater.from(this.mContext),
                (ViewGroup) getActivity().findViewById(R.id.main_content));
        this.mContext = getContext();
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        setEnabledExclusiveSettings();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getContext();
        getActivity().setTitle(R.string.sec_screen_mode_setting);
        this.mDisplayMetrics = this.mContext.getResources().getDisplayMetrics();
        this.mMdnie = (SemMdnieManager) this.mContext.getSystemService("mDNIe");
        this.mSupportWcgModeOnAmoled = Rune.supportWcgModeOnAmoled();
        this.mSupportVividPlusMode = Rune.supportVividPlusMode();
        this.mSupportNaturalModeWithoutWcgMode = Rune.supportNaturalModeWithoutWcgMode();
        if (Utils.isTablet()) {
            if (Rune.supportAmoledDisplay()) {
                if (this.mSupportWcgModeOnAmoled) {
                    this.mScreenModeItems.addItem(R.string.sec_screen_mode_wcg_vivid, 4);
                    this.mScreenModeItems.addItem(R.string.sec_screen_mode_wcg_natural, 3);
                } else if (this.mSupportNaturalModeWithoutWcgMode) {
                    this.mScreenModeItems.addItem(R.string.sec_screen_mode_wcg_vivid, 4);
                    this.mScreenModeItems.addItem(R.string.sec_screen_mode_wcg_natural, 2);
                } else {
                    this.mScreenModeItems.addItem(R.string.sec_screen_mode_auto_adaptive, 4);
                    this.mScreenModeItems.addItem(R.string.sec_screen_mode_premium_movie, 0);
                    this.mScreenModeItems.addItem(R.string.sec_screen_mode_amoled_photo, 1);
                    this.mScreenModeItems.addItem(R.string.sec_screen_mode_basic, 2);
                }
            } else if (SemFloatingFeature.getInstance()
                            .getInt("SEC_FLOATING_FEATURE_LCD_SUPPORT_SCREEN_MODE_TYPE")
                    == 1) {
                if (this.mSupportWcgModeOnAmoled) {
                    this.mScreenModeItems.addItem(R.string.sec_screen_mode_wcg_vivid, 4);
                    this.mScreenModeItems.addItem(R.string.sec_screen_mode_wcg_natural, 3);
                } else if (this.mSupportNaturalModeWithoutWcgMode) {
                    this.mScreenModeItems.addItem(R.string.sec_screen_mode_wcg_vivid, 4);
                    this.mScreenModeItems.addItem(R.string.sec_screen_mode_wcg_natural, 2);
                }
            }
        } else if (Rune.supportAmoledDisplay()) {
            if (this.mSupportWcgModeOnAmoled) {
                this.mScreenModeItems.addItem(R.string.sec_screen_mode_wcg_vivid, 4);
                this.mScreenModeItems.addItem(R.string.sec_screen_mode_wcg_natural, 3);
            } else if (this.mSupportNaturalModeWithoutWcgMode) {
                this.mScreenModeItems.addItem(R.string.sec_screen_mode_wcg_vivid, 4);
                this.mScreenModeItems.addItem(R.string.sec_screen_mode_wcg_natural, 2);
            } else {
                this.mScreenModeItems.addItem(R.string.sec_screen_mode_auto_adaptive, 4);
                this.mScreenModeItems.addItem(R.string.sec_screen_mode_premium_movie, 0);
                this.mScreenModeItems.addItem(R.string.sec_screen_mode_amoled_photo, 1);
                this.mScreenModeItems.addItem(R.string.sec_screen_mode_basic, 2);
            }
        } else if (SemFloatingFeature.getInstance()
                        .getInt("SEC_FLOATING_FEATURE_LCD_SUPPORT_SCREEN_MODE_TYPE")
                == 2) {
            if (this.mSupportWcgModeOnAmoled) {
                this.mScreenModeItems.addItem(R.string.sec_screen_mode_wcg_vivid, 4);
                this.mScreenModeItems.addItem(R.string.sec_screen_mode_wcg_natural, 3);
            } else if (this.mSupportNaturalModeWithoutWcgMode) {
                this.mScreenModeItems.addItem(R.string.sec_screen_mode_wcg_vivid, 4);
                this.mScreenModeItems.addItem(R.string.sec_screen_mode_wcg_natural, 2);
            } else {
                this.mScreenModeItems.addItem(R.string.sec_screen_mode_auto_adaptive, 4);
                this.mScreenModeItems.addItem(R.string.sec_screen_mode_movie, 0);
                this.mScreenModeItems.addItem(R.string.sec_screen_mode_photo, 1);
                this.mScreenModeItems.addItem(R.string.sec_screen_mode_basic, 2);
            }
        }
        int currentScreenMode = getCurrentScreenMode(this.mContext.getContentResolver());
        Log.secD("ModePreview", "onCreate() selectedMode: " + currentScreenMode);
        this.mAdapter =
                new ScreenModeAdapter(this.mContext, this.mScreenModeItems, currentScreenMode);
        this.mProgressExpandAnimation =
                (AnimationDrawable)
                        this.mContext.getDrawable(
                                R.drawable.sec_screen_mode_progress_white_balance_expand_animation);
        this.mProgressShrinkAnimation =
                (AnimationDrawable)
                        this.mContext.getDrawable(
                                R.drawable.sec_screen_mode_progress_white_balance_shrink_animation);
        this.mThumbFadeInAnimation =
                (AnimationDrawable)
                        this.mContext.getDrawable(
                                R.drawable.sec_screen_mode_progress_thumb_fade_in_animation);
        this.mThumbFadeOutAnimation =
                (AnimationDrawable)
                        this.mContext.getDrawable(
                                R.drawable.sec_screen_mode_progress_thumb_fade_out_animation);
        this.mTickFadeInAnimation =
                (AnimationDrawable)
                        this.mContext.getDrawable(
                                R.drawable
                                        .sec_screen_mode_progress_white_balance_tick_fade_in_animation);
        this.mTickFadeOutAnimation =
                (AnimationDrawable)
                        this.mContext.getDrawable(
                                R.drawable
                                        .sec_screen_mode_progress_white_balance_tick_fade_out_animation);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return createScreenModeView(layoutInflater, null);
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        ViewGroup viewGroup = (ViewGroup) getActivity().findViewById(R.id.main_content);
        if (viewGroup != null) {
            viewGroup.removeView(getView());
            View findViewById = viewGroup.findViewById(R.id.new_mode_preview_layout);
            if (findViewById == null) {
                Log.d("ModePreview", "onDestroyView: layout = null");
                return;
            }
            Log.d("ModePreview", "onDestroyView: layout = " + findViewById);
            viewGroup.removeView(findViewById);
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        setScreenMode(getCurrentScreenMode(this.mContext.getContentResolver()));
        super.onPause();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        if (Rune.isSamsungDexMode(this.mContext) && Utils.isDesktopDualMode(this.mContext)) {
            finish();
        }
        super.onResume();
        setEnabledExclusiveSettings();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        this.mContext.registerReceiver(
                this.mBatteryInfoReceiver,
                new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor("blue_light_filter"),
                        false,
                        this.mBluelightFilterObserver);
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor("ead_enabled"), false, this.mEadObserver);
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor("sec_display_preset_index"),
                        false,
                        this.mSeekBarObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        setScreenMode(getCurrentScreenMode(this.mContext.getContentResolver()));
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        try {
            this.mContext.unregisterReceiver(this.mBatteryInfoReceiver);
            this.mContext
                    .getContentResolver()
                    .unregisterContentObserver(this.mBluelightFilterObserver);
            this.mContext.getContentResolver().unregisterContentObserver(this.mEadObserver);
            this.mContext.getContentResolver().unregisterContentObserver(this.mSeekBarObserver);
        } catch (Exception e) {
            e.printStackTrace();
            Log.secE("ModePreview", "unregisterReceiver exception");
        }
    }

    public final void setEnabledExclusiveSettings() {
        if (getContext() == null) {
            return;
        }
        boolean z =
                Settings.System.getInt(this.mContext.getContentResolver(), "blue_light_filter", 0)
                        != 0;
        boolean isAccessibilityVisionEnabled =
                SecDisplayUtils.isAccessibilityVisionEnabled(getContext());
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        boolean z2 =
                (z
                                || isAccessibilityVisionEnabled
                                || (Settings.System.getInt(getContentResolver(), "ead_enabled", 0)
                                        != 0))
                        ? false
                        : true;
        TextView textView = this.mPresetCool;
        if (textView != null) {
            textView.setEnabled(z2);
        }
        TextView textView2 = this.mPresetWarm;
        if (textView2 != null) {
            textView2.setEnabled(z2);
        }
        SeekBar seekBar = this.mPresetSeekBar;
        if (seekBar != null) {
            seekBar.setEnabled(z2);
        }
        SecRoundButtonView secRoundButtonView = this.mAdvancedSettingButton;
        if (secRoundButtonView != null) {
            secRoundButtonView.setVisibility(z2 ? 0 : 8);
        }
        LottieAnimationView lottieAnimationView = this.mQuestionIcon;
        if (lottieAnimationView != null) {
            lottieAnimationView.setVisibility(z2 ? 8 : 0);
        }
    }

    public final void setScreenMode(int i) {
        if (this.mSupportWcgModeOnAmoled) {
            if (i == 3) {
                Log.secE("ModePreview", "set Mdnie ScreenMode WCG : 3 to 0");
                if (!this.mSupportVividPlusMode) {
                    SecDisplayUtils.setDisplayColor(0);
                }
                this.mMdnie.setScreenMode(0);
                return;
            }
            Log.secE("ModePreview", "set Mdnie ScreenMode : " + i);
            if (!this.mSupportVividPlusMode) {
                SecDisplayUtils.setDisplayColor(1);
            }
            this.mMdnie.setScreenMode(i);
            return;
        }
        if (!this.mSupportNaturalModeWithoutWcgMode) {
            Log.secE("ModePreview", "set Mdnie ScreenMode : " + i);
            this.mMdnie.setScreenMode(i);
            return;
        }
        if (i == 2) {
            Log.secE("ModePreview", "set Mdnie ScreenMode to Natural without WCG : 2");
            this.mMdnie.setScreenMode(2);
        } else {
            Log.secE("ModePreview", "set Mdnie ScreenMode : " + i);
            this.mMdnie.setScreenMode(i);
        }
    }
}
