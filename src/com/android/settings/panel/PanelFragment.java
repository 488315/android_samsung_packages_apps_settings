package com.android.settings.panel;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.FeatureFlagUtils;
import android.util.Property;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.collection.ArraySet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.slice.ArrayUtils;
import androidx.slice.Slice;
import androidx.slice.SliceMetadata;
import androidx.slice.widget.SliceLiveData;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;

import com.google.android.setupdesign.DividerItemDecoration;
import com.samsung.android.knox.custom.IKnoxCustomManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Deprecated(forRemoval = true)
/* loaded from: classes2.dex */
public class PanelFragment extends Fragment {
    public PanelSlicesAdapter mAdapter;
    public Button mDoneButton;

    @VisibleForTesting View mLayoutView;
    public int mMaxHeight;
    public SettingsMetricsFeatureProvider mMetricsProvider;
    public final AnonymousClass1 mOnGlobalLayoutListener;
    public PanelContent mPanel;
    public String mPanelClosedKey;
    public boolean mPanelCreating;
    public LinearLayout mPanelHeader;
    public final AnonymousClass1 mPanelLayoutListener;
    public RecyclerView mPanelSlices;

    @VisibleForTesting PanelSlicesLoaderCountdownLatch mPanelSlicesLoaderCountdownLatch;
    public ProgressBar mProgressBar;
    public Button mSeeMoreButton;
    public TextView mTitleView;
    public final Map mSliceLiveData = new LinkedHashMap();
    public final PanelFragment$$ExternalSyntheticLambda0 mOnPreDrawListener =
            new PanelFragment$$ExternalSyntheticLambda0();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class LocalPanelCallback {
        public LocalPanelCallback() {}

        @VisibleForTesting
        public FragmentActivity getFragmentActivity() {
            return PanelFragment.this.getActivity();
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.settings.panel.PanelFragment$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.settings.panel.PanelFragment$1] */
    public PanelFragment() {
        final int i = 0;
        this.mPanelLayoutListener =
                new ViewTreeObserver.OnGlobalLayoutListener(
                        this) { // from class: com.android.settings.panel.PanelFragment.1
                    public final /* synthetic */ PanelFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                    public final void onGlobalLayout() {
                        switch (i) {
                            case 0:
                                int height = this.this$0.mLayoutView.getHeight();
                                PanelFragment panelFragment = this.this$0;
                                if (height > panelFragment.mMaxHeight) {
                                    ViewGroup.LayoutParams layoutParams =
                                            panelFragment.mLayoutView.getLayoutParams();
                                    PanelFragment panelFragment2 = this.this$0;
                                    layoutParams.height = panelFragment2.mMaxHeight;
                                    panelFragment2.mLayoutView.setLayoutParams(layoutParams);
                                    break;
                                }
                                break;
                            default:
                                PanelFragment panelFragment3 = this.this$0;
                                AnimatorSet buildAnimatorSet =
                                        PanelFragment.buildAnimatorSet(
                                                panelFragment3.mLayoutView,
                                                panelFragment3
                                                        .mLayoutView
                                                        .findViewById(R.id.panel_container)
                                                        .getHeight(),
                                                0.0f,
                                                0.0f,
                                                1.0f,
                                                IKnoxCustomManager.Stub
                                                        .TRANSACTION_addDexURLShortcutExtend);
                                ValueAnimator valueAnimator = new ValueAnimator();
                                valueAnimator.setFloatValues(0.0f, 1.0f);
                                buildAnimatorSet.play(valueAnimator);
                                buildAnimatorSet.start();
                                panelFragment3
                                        .mLayoutView
                                        .getViewTreeObserver()
                                        .removeOnPreDrawListener(panelFragment3.mOnPreDrawListener);
                                RecyclerView recyclerView = this.this$0.mPanelSlices;
                                if (recyclerView != null) {
                                    recyclerView
                                            .getViewTreeObserver()
                                            .removeOnGlobalLayoutListener(this);
                                }
                                this.this$0.mPanelCreating = false;
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mOnGlobalLayoutListener =
                new ViewTreeObserver.OnGlobalLayoutListener(
                        this) { // from class: com.android.settings.panel.PanelFragment.1
                    public final /* synthetic */ PanelFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                    public final void onGlobalLayout() {
                        switch (i2) {
                            case 0:
                                int height = this.this$0.mLayoutView.getHeight();
                                PanelFragment panelFragment = this.this$0;
                                if (height > panelFragment.mMaxHeight) {
                                    ViewGroup.LayoutParams layoutParams =
                                            panelFragment.mLayoutView.getLayoutParams();
                                    PanelFragment panelFragment2 = this.this$0;
                                    layoutParams.height = panelFragment2.mMaxHeight;
                                    panelFragment2.mLayoutView.setLayoutParams(layoutParams);
                                    break;
                                }
                                break;
                            default:
                                PanelFragment panelFragment3 = this.this$0;
                                AnimatorSet buildAnimatorSet =
                                        PanelFragment.buildAnimatorSet(
                                                panelFragment3.mLayoutView,
                                                panelFragment3
                                                        .mLayoutView
                                                        .findViewById(R.id.panel_container)
                                                        .getHeight(),
                                                0.0f,
                                                0.0f,
                                                1.0f,
                                                IKnoxCustomManager.Stub
                                                        .TRANSACTION_addDexURLShortcutExtend);
                                ValueAnimator valueAnimator = new ValueAnimator();
                                valueAnimator.setFloatValues(0.0f, 1.0f);
                                buildAnimatorSet.play(valueAnimator);
                                buildAnimatorSet.start();
                                panelFragment3
                                        .mLayoutView
                                        .getViewTreeObserver()
                                        .removeOnPreDrawListener(panelFragment3.mOnPreDrawListener);
                                RecyclerView recyclerView = this.this$0.mPanelSlices;
                                if (recyclerView != null) {
                                    recyclerView
                                            .getViewTreeObserver()
                                            .removeOnGlobalLayoutListener(this);
                                }
                                this.this$0.mPanelCreating = false;
                                break;
                        }
                    }
                };
    }

    public static AnimatorSet buildAnimatorSet(
            View view, float f, float f2, float f3, float f4, int i) {
        View findViewById = view.findViewById(R.id.panel_container);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(i);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(
                        findViewById, (Property<View, Float>) View.TRANSLATION_Y, f, f2),
                ObjectAnimator.ofFloat(findViewById, (Property<View, Float>) View.ALPHA, f3, f4));
        return animatorSet;
    }

    public final void createPanelContent() {
        String string;
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        View view = this.mLayoutView;
        if (view == null) {
            activity.finish();
            return;
        }
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = -2;
        this.mLayoutView.setLayoutParams(layoutParams);
        this.mPanelSlices = (RecyclerView) this.mLayoutView.findViewById(R.id.panel_parent_layout);
        this.mSeeMoreButton = (Button) this.mLayoutView.findViewById(R.id.see_more);
        this.mDoneButton = (Button) this.mLayoutView.findViewById(R.id.done);
        this.mTitleView = (TextView) this.mLayoutView.findViewById(R.id.panel_title);
        this.mPanelHeader = (LinearLayout) this.mLayoutView.findViewById(R.id.panel_header);
        this.mProgressBar = (ProgressBar) this.mLayoutView.findViewById(R.id.progress_bar);
        this.mPanelSlices.setVisibility(8);
        PanelContent panelContent = null;
        this.mPanelSlices.setItemAnimator(null);
        Bundle arguments = getArguments();
        String string2 = arguments.getString("PANEL_CALLING_PACKAGE_NAME");
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        ((PanelFeatureProviderImpl) featureFactoryImpl.panelFeatureProvider$delegate.getValue())
                .getClass();
        string = arguments.getString("PANEL_TYPE_ARGUMENT");
        arguments.getString("PANEL_MEDIA_PACKAGE_NAME");
        string.getClass();
        switch (string) {
            case "android.settings.panel.action.NFC":
                panelContent = new NfcPanel(activity);
                break;
            case "android.settings.panel.action.INTERNET_CONNECTIVITY":
                Intent intent = new Intent("android.settings.panel.action.INTERNET_CONNECTIVITY");
                intent.addFlags(268435456).setPackage("com.android.systemui");
                activity.sendBroadcast(intent);
                break;
            case "android.settings.panel.action.VOLUME":
                if (!FeatureFlagUtils.isEnabled(activity, "settings_volume_panel_in_systemui")) {
                    panelContent = new VolumePanel(activity);
                    break;
                } else {
                    Intent intent2 = new Intent("android.settings.panel.action.VOLUME");
                    intent2.addFlags(268435456).setPackage("com.android.systemui");
                    activity.sendBroadcast(intent2);
                    break;
                }
            case "android.settings.panel.action.WIFI":
                panelContent = new WifiPanel(activity);
                break;
            default:
                throw new IllegalStateException("No matching panel for: ".concat(string));
        }
        this.mPanel = panelContent;
        if (panelContent == null) {
            activity.finish();
            return;
        }
        panelContent.registerCallback(new LocalPanelCallback());
        if (this.mPanel instanceof LifecycleObserver) {
            getLifecycle().addObserver((LifecycleObserver) this.mPanel);
        }
        FeatureFactoryImpl featureFactoryImpl2 = FeatureFactoryImpl._factory;
        if (featureFactoryImpl2 == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsProvider = featureFactoryImpl2.getMetricsFeatureProvider();
        this.mPanel.getClass();
        this.mProgressBar.setVisibility(8);
        this.mPanelSlices.setLayoutManager(new LinearLayoutManager(1));
        this.mLayoutView.getViewTreeObserver().addOnPreDrawListener(this.mOnPreDrawListener);
        ((LinkedHashMap) this.mSliceLiveData).clear();
        ArrayList arrayList = (ArrayList) this.mPanel.getSlices();
        this.mPanelSlicesLoaderCountdownLatch =
                new PanelSlicesLoaderCountdownLatch(arrayList.size());
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            final Uri uri = (Uri) it.next();
            FragmentActivity activity2 = getActivity();
            SliceLiveData.OnErrorListener onErrorListener =
                    new SliceLiveData
                            .OnErrorListener() { // from class:
                                                 // com.android.settings.panel.PanelFragment$$ExternalSyntheticLambda4
                        @Override // androidx.slice.widget.SliceLiveData.OnErrorListener
                        public final void onSliceError(int i, Throwable th) {
                            Uri uri2 = uri;
                            PanelFragment panelFragment = PanelFragment.this;
                            if (!Arrays.asList(
                                            panelFragment
                                                    .getResources()
                                                    .getStringArray(
                                                            R.array.config_panel_keep_observe_uri))
                                    .contains(uri2.toString())) {
                                panelFragment.mSliceLiveData.remove(uri2);
                            }
                            panelFragment.mPanelSlicesLoaderCountdownLatch.markSliceLoaded(uri2);
                        }
                    };
            ArraySet arraySet = SliceLiveData.SUPPORTED_SPECS;
            SliceLiveData.SliceLiveDataImpl sliceLiveDataImpl =
                    new SliceLiveData.SliceLiveDataImpl(
                            activity2.getApplicationContext(), uri, onErrorListener);
            this.mSliceLiveData.put(uri, sliceLiveDataImpl);
            sliceLiveDataImpl.observe(
                    getViewLifecycleOwner(),
                    new Observer() { // from class:
                                     // com.android.settings.panel.PanelFragment$$ExternalSyntheticLambda5
                        @Override // androidx.lifecycle.Observer
                        public final void onChanged(Object obj) {
                            final Uri uri2 = uri;
                            Slice slice = (Slice) obj;
                            final PanelFragment panelFragment = PanelFragment.this;
                            if (((HashSet)
                                            panelFragment
                                                    .mPanelSlicesLoaderCountdownLatch
                                                    .mLoadedSlices)
                                    .contains(uri2)) {
                                if (panelFragment.mAdapter != null) {
                                    panelFragment.mAdapter.notifyItemChanged(
                                            new ArrayList(
                                                            ((LinkedHashMap)
                                                                            panelFragment
                                                                                    .mSliceLiveData)
                                                                    .keySet())
                                                    .indexOf(uri2));
                                    return;
                                }
                                return;
                            }
                            SliceMetadata from =
                                    SliceMetadata.from(panelFragment.getActivity(), slice);
                            if (slice == null || ArrayUtils.contains("error", from.mSlice.mHints)) {
                                if (!Arrays.asList(
                                                panelFragment
                                                        .getResources()
                                                        .getStringArray(
                                                                R.array
                                                                        .config_panel_keep_observe_uri))
                                        .contains(uri2.toString())) {
                                    panelFragment.mSliceLiveData.remove(uri2);
                                }
                                panelFragment.mPanelSlicesLoaderCountdownLatch.markSliceLoaded(
                                        uri2);
                            } else if (from.getLoadingState() == 2) {
                                panelFragment.mPanelSlicesLoaderCountdownLatch.markSliceLoaded(
                                        uri2);
                            } else {
                                new Handler()
                                        .postDelayed(
                                                new Runnable() { // from class:
                                                                 // com.android.settings.panel.PanelFragment$$ExternalSyntheticLambda6
                                                    @Override // java.lang.Runnable
                                                    public final void run() {
                                                        PanelFragment panelFragment2 =
                                                                PanelFragment.this;
                                                        panelFragment2
                                                                .mPanelSlicesLoaderCountdownLatch
                                                                .markSliceLoaded(uri2);
                                                        panelFragment2.loadPanelWhenReady();
                                                    }
                                                },
                                                250L);
                            }
                            panelFragment.loadPanelWhenReady();
                        }
                    });
        }
        this.mPanel.getClass();
        CharSequence title = this.mPanel.getTitle();
        this.mPanel.getClass();
        this.mPanelHeader.setVisibility(8);
        this.mTitleView.setVisibility(0);
        this.mTitleView.setAccessibilityPaneTitle(title);
        this.mTitleView.setText(title);
        this.mSeeMoreButton.setOnClickListener(getSeeMoreListener());
        this.mDoneButton.setOnClickListener(getCloseListener());
        this.mPanel.getClass();
        if (this.mPanel.getSeeMoreIntent() == null) {
            this.mSeeMoreButton.setVisibility(8);
        }
        this.mMetricsProvider.action(0, 1, this.mPanel.getMetricsCategory(), 0, string2);
    }

    @VisibleForTesting
    public View.OnClickListener getCloseListener() {
        return new PanelFragment$$ExternalSyntheticLambda1(this, 0);
    }

    @VisibleForTesting
    public View.OnClickListener getHeaderIconListener() {
        return new PanelFragment$$ExternalSyntheticLambda1(this, 1);
    }

    @VisibleForTesting
    public View.OnClickListener getSeeMoreListener() {
        return new PanelFragment$$ExternalSyntheticLambda1(this, 2);
    }

    public final void loadPanelWhenReady() {
        PanelSlicesLoaderCountdownLatch panelSlicesLoaderCountdownLatch =
                this.mPanelSlicesLoaderCountdownLatch;
        if (panelSlicesLoaderCountdownLatch.mCountDownLatch.getCount() != 0
                || panelSlicesLoaderCountdownLatch.slicesReadyToLoad) {
            return;
        }
        panelSlicesLoaderCountdownLatch.slicesReadyToLoad = true;
        PanelSlicesAdapter panelSlicesAdapter =
                new PanelSlicesAdapter(this, this.mSliceLiveData, this.mPanel.getMetricsCategory());
        this.mAdapter = panelSlicesAdapter;
        this.mPanelSlices.setAdapter(panelSlicesAdapter);
        this.mPanelSlices
                .getViewTreeObserver()
                .addOnGlobalLayoutListener(this.mOnGlobalLayoutListener);
        this.mPanelSlices.setVisibility(0);
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(activity);
        dividerItemDecoration.dividerCondition = 1;
        if (this.mPanelSlices.getItemDecorationCount() == 0) {
            this.mPanelSlices.addItemDecoration(dividerItemDecoration);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.panel_layout, viewGroup, false);
        this.mLayoutView = inflate;
        inflate.getViewTreeObserver().addOnGlobalLayoutListener(this.mPanelLayoutListener);
        this.mMaxHeight =
                getResources().getDimensionPixelSize(R.dimen.output_switcher_slice_max_height);
        this.mPanelCreating = true;
        createPanelContent();
        return this.mLayoutView;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        if (TextUtils.isEmpty(this.mPanelClosedKey)) {
            this.mPanelClosedKey = "others";
        }
        View view = this.mLayoutView;
        if (view != null) {
            view.getViewTreeObserver().removeOnGlobalLayoutListener(this.mPanelLayoutListener);
        }
        PanelContent panelContent = this.mPanel;
        if (panelContent != null) {
            this.mMetricsProvider.action(
                    0, 2, panelContent.getMetricsCategory(), 0, this.mPanelClosedKey);
        }
    }
}
