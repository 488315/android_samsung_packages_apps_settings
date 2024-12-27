package com.samsung.android.settings.homepage;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.SettingsApplication;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.homepage.SettingsHomepageActivity;
import com.android.settings.homepage.TopLevelHighlightMixin;
import com.android.settings.widget.HighlightableTopLevelPreferenceAdapter;
import com.android.settings.widget.HomepagePreference;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.mde.MDEServiceBindingManager;
import com.samsung.android.settings.mde.SuggestionInfo;
import com.samsung.android.settings.widget.HomepagePreferenceHelper;
import com.samsung.android.settings.widget.SecInsetCategoryPreference;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class TopLevelMDESuggestionsPreferenceController extends TopLevelPreferenceController
        implements LifecycleObserver {
    private static final String TAG = "TopLevelMDESuggestionsPreferenceController";
    private static final String TOP_INSET_CATEGORY_KEY =
            "top_level_mde_suggestions_top_inset_category";
    private static final String TOP_LEVEL_SAMSUNG_ACCOUNT_KEY = "top_level_samsung_account";
    private Preference mDependencyPreference;
    private List<SuggestionInfo> mDepthInSuggestionInfoList;
    private boolean mHighlightRequired;
    private MDEServiceBindingManager mMDEServiceBindingManager;
    private final MDEServiceBindingManager.Callback mMDEServiceBindingManagerCallback;
    private List<SuggestionInfo> mOnBodySuggestionInfoList;
    private Preference mPreference;
    private SecInsetCategoryPreference mTopInsetCategoryPreference;

    public TopLevelMDESuggestionsPreferenceController(Context context, String str) {
        super(context, str);
        this.mDepthInSuggestionInfoList = new ArrayList();
        this.mOnBodySuggestionInfoList = new ArrayList();
        this.mMDEServiceBindingManagerCallback =
                new MDEServiceBindingManager
                        .Callback() { // from class:
                                      // com.samsung.android.settings.homepage.TopLevelMDESuggestionsPreferenceController.1
                    @Override // com.samsung.android.settings.mde.MDEServiceBindingManager.Callback
                    public final void onRequestUpdated() {
                        TopLevelMDESuggestionsPreferenceController
                                topLevelMDESuggestionsPreferenceController =
                                        TopLevelMDESuggestionsPreferenceController.this;
                        if (topLevelMDESuggestionsPreferenceController.mMDEServiceBindingManager
                                != null) {
                            topLevelMDESuggestionsPreferenceController.mMDEServiceBindingManager
                                    .sendMessage(1, null);
                        }
                    }

                    @Override // com.samsung.android.settings.mde.MDEServiceBindingManager.Callback
                    public final void onSuggestionsUpdated(List list) {
                        TopLevelMDESuggestionsPreferenceController
                                topLevelMDESuggestionsPreferenceController =
                                        TopLevelMDESuggestionsPreferenceController.this;
                        topLevelMDESuggestionsPreferenceController.separateSuggestionInfoList(list);
                        topLevelMDESuggestionsPreferenceController.refreshMenu();
                    }
                };
        this.mMDEServiceBindingManager = new MDEServiceBindingManager(context);
        this.mHighlightRequired = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendSuggestionLaunchedMessage$0(Bundle bundle) {
        try {
            Thread.sleep(1000L);
            MDEServiceBindingManager mDEServiceBindingManager = this.mMDEServiceBindingManager;
            if (mDEServiceBindingManager != null) {
                mDEServiceBindingManager.sendMessage(1, bundle);
            }
        } catch (InterruptedException e) {
            Log.d(TAG, "sendSuggestionLaunchedMessage: " + e);
            Thread.currentThread().interrupt();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshMenu() {
        SettingsHomepageActivity homeActivity;
        final TopLevelHighlightMixin topLevelHighlightMixin;
        boolean isAvailable = isAvailable();
        if (this.mPreference != null) {
            if (isAvailable) {
                if (!this.mOnBodySuggestionInfoList.isEmpty()) {
                    updateOnBodySuggestionInfoList();
                } else if (this.mDepthInSuggestionInfoList.isEmpty()) {
                    Log.i(
                            TAG,
                            "refreshMenu: change available is false, because suggestion info is"
                                + " empty.");
                    isAvailable = false;
                } else {
                    updateDepthInSuggestionInfoList();
                }
            }
            this.mPreference.setVisible(isAvailable);
        }
        SecInsetCategoryPreference secInsetCategoryPreference = this.mTopInsetCategoryPreference;
        if (secInsetCategoryPreference != null) {
            secInsetCategoryPreference.setVisible(isAvailable);
        }
        if (isAvailable
                && this.mHighlightRequired
                && (homeActivity =
                                ((SettingsApplication) this.mContext.getApplicationContext())
                                        .getHomeActivity())
                        != null
                && (topLevelHighlightMixin = homeActivity.mMainFragment.mHighlightMixin) != null) {
            final String highlightPreferenceKey =
                    topLevelHighlightMixin.getHighlightPreferenceKey();
            AbsAdapter$$ExternalSyntheticOutline0.m(
                    "setHighlightPreferenceKeyByForce : ", highlightPreferenceKey, TAG);
            this.mHighlightRequired = false;
            new Handler()
                    .postDelayed(
                            new Runnable() { // from class:
                                             // com.samsung.android.settings.homepage.TopLevelMDESuggestionsPreferenceController.2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    TopLevelHighlightMixin topLevelHighlightMixin2 =
                                            TopLevelHighlightMixin.this;
                                    String str = highlightPreferenceKey;
                                    HighlightableTopLevelPreferenceAdapter
                                            highlightableTopLevelPreferenceAdapter =
                                                    topLevelHighlightMixin2.mTopLevelAdapter;
                                    if (highlightableTopLevelPreferenceAdapter != null) {
                                        topLevelHighlightMixin2.mCurrentKey = str;
                                        highlightableTopLevelPreferenceAdapter.highlightPreference(
                                                str, true);
                                    }
                                }
                            },
                            100L);
        }
        updateFromGoodSettings();
    }

    private void sendSuggestionLaunchedMessage() {
        final Bundle bundle = new Bundle();
        bundle.putString("id", this.mOnBodySuggestionInfoList.get(0).mId);
        MDEServiceBindingManager mDEServiceBindingManager = this.mMDEServiceBindingManager;
        if (mDEServiceBindingManager != null) {
            mDEServiceBindingManager.sendMessage(3, bundle);
        }
        new Thread(
                        new Runnable() { // from class:
                                         // com.samsung.android.settings.homepage.TopLevelMDESuggestionsPreferenceController$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                TopLevelMDESuggestionsPreferenceController.this
                                        .lambda$sendSuggestionLaunchedMessage$0(bundle);
                            }
                        })
                .start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void separateSuggestionInfoList(List<SuggestionInfo> list) {
        this.mDepthInSuggestionInfoList.clear();
        this.mOnBodySuggestionInfoList.clear();
        for (SuggestionInfo suggestionInfo : list) {
            String str = suggestionInfo.mUiType;
            str.getClass();
            if (str.equals("on_body")) {
                this.mOnBodySuggestionInfoList.add(suggestionInfo);
            } else if (str.equals("depth_in")) {
                this.mDepthInSuggestionInfoList.add(suggestionInfo);
            }
        }
    }

    private void updateDepthInSuggestionInfoList() {
        this.mPreference.setFragment("com.samsung.android.settings.mde.MDESuggestionsSettings");
        this.mPreference.setIntent(null);
        this.mPreference.setTitle(this.mDepthInSuggestionInfoList.get(0).mTitle);
        if (this.mDepthInSuggestionInfoList.size() > 1) {
            this.mPreference.setSummary(
                    this.mContext
                            .getResources()
                            .getQuantityString(
                                    R.plurals.sec_mde_suggestions_summary,
                                    this.mDepthInSuggestionInfoList.size() - 1,
                                    Integer.valueOf(this.mDepthInSuggestionInfoList.size() - 1)));
        } else {
            this.mPreference.setSummary(
                    this.mContext
                            .getResources()
                            .getString(R.string.sec_mde_suggestions_default_summary));
        }
        this.mPreference.setIcon(this.mContext.getDrawable(R.drawable.sec_suggestions_icon));
    }

    private void updateOnBodySuggestionInfoList() {
        SuggestionInfo suggestionInfo = this.mOnBodySuggestionInfoList.get(0);
        this.mPreference.setFragment(null);
        Intent intent = suggestionInfo.mIntent;
        intent.addFlags(268435456);
        this.mPreference.setIntent(intent);
        this.mPreference.setTitle(suggestionInfo.mTitle);
        this.mPreference.setSummary(suggestionInfo.mSummary);
        Icon icon = suggestionInfo.mIcon;
        if (icon != null) {
            this.mPreference.setIcon(icon.loadDrawable(this.mContext));
        }
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = findPreference;
        if (findPreference != null) {
            findPreference.setVisible(false);
        }
        SecInsetCategoryPreference secInsetCategoryPreference =
                (SecInsetCategoryPreference)
                        preferenceScreen.findPreference(TOP_INSET_CATEGORY_KEY);
        this.mTopInsetCategoryPreference = secInsetCategoryPreference;
        if (secInsetCategoryPreference != null) {
            secInsetCategoryPreference.setVisible(false);
        }
        if (Rune.supportGoodSettings(this.mContext)) {
            this.mDependencyPreference =
                    preferenceScreen.findPreference(TOP_LEVEL_SAMSUNG_ACCOUNT_KEY);
        }
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 3;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        boolean handlePreferenceTreeClick = super.handlePreferenceTreeClick(preference);
        if (handlePreferenceTreeClick && !this.mOnBodySuggestionInfoList.isEmpty()) {
            Utils$$ExternalSyntheticOutline0.m(
                    new StringBuilder("handlePreferenceTreeClick: Click on-body suggestion = "),
                    this.mOnBodySuggestionInfoList.get(0).mId,
                    TAG);
            sendSuggestionLaunchedMessage();
        }
        return handlePreferenceTreeClick;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        MDEServiceBindingManager mDEServiceBindingManager = this.mMDEServiceBindingManager;
        if (mDEServiceBindingManager != null) {
            mDEServiceBindingManager.mCallbacks.add(this.mMDEServiceBindingManagerCallback);
            this.mMDEServiceBindingManager.startServiceBind();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        MDEServiceBindingManager mDEServiceBindingManager = this.mMDEServiceBindingManager;
        if (mDEServiceBindingManager != null) {
            mDEServiceBindingManager.stopServiceBind();
            MDEServiceBindingManager mDEServiceBindingManager2 = this.mMDEServiceBindingManager;
            mDEServiceBindingManager2.mCallbacks.remove(this.mMDEServiceBindingManagerCallback);
        }
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    public void updateFromGoodSettings() {
        if (Rune.supportGoodSettings(this.mContext)) {
            boolean isAvailable = isAvailable();
            Preference preference = this.mDependencyPreference;
            if ((preference instanceof HomepagePreference)
                    && (this.mPreference instanceof HomepagePreference)) {
                HomepagePreferenceHelper preferenceHelper =
                        ((HomepagePreference) preference).getPreferenceHelper();
                boolean isVisible = preferenceHelper.isVisible();
                int order = preferenceHelper.getOrder();
                boolean z = false;
                ((HomepagePreference) this.mPreference)
                        .setVisibleByGoodSettings(Boolean.valueOf(isAvailable && isVisible));
                HomepagePreference homepagePreference = (HomepagePreference) this.mPreference;
                int i = order + 2;
                if (Rune.supportGoodSettings(homepagePreference.getContext())) {
                    HomepagePreferenceHelper preferenceHelper2 =
                            homepagePreference.getPreferenceHelper();
                    preferenceHelper2.getClass();
                    preferenceHelper2.mOrder = Optional.of(Integer.valueOf(i));
                    homepagePreference.setOrder(i);
                }
                SecInsetCategoryPreference secInsetCategoryPreference =
                        this.mTopInsetCategoryPreference;
                if (secInsetCategoryPreference != null) {
                    if (isAvailable && isVisible) {
                        z = true;
                    }
                    secInsetCategoryPreference.setVisible(z);
                    this.mTopInsetCategoryPreference.setOrder(order + 1);
                }
            }
        }
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
