package com.android.settings.homepage;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.FeatureFlagUtils;
import android.util.Log;

import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.core.util.Consumer;
import androidx.window.embedding.ActivityWindowInfoCallbackController$$ExternalSyntheticLambda1;
import androidx.window.embedding.SplitController;
import androidx.window.java.embedding.SplitControllerCallbackAdapter;

import com.android.settings.R;
import com.android.settings.Settings;
import com.android.settings.SettingsApplication;
import com.android.settings.activityembedding.ActivityEmbeddingRulesController;
import com.android.settings.activityembedding.ActivityEmbeddingUtils;
import com.android.settings.core.CategoryMixin;

import com.samsung.android.knox.custom.CustomDeviceManager;
import com.samsung.android.settings.PkgUtils;
import com.samsung.android.settings.Trace;
import com.samsung.android.settings.core.SecSettingsBaseActivity;
import com.samsung.android.settings.homepage.HomepageUtils;
import com.samsung.android.settings.homepage.SecTopLevelFeature;
import com.samsung.android.util.SemLog;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SettingsHomepageActivity extends SecSettingsBaseActivity
        implements CategoryMixin.CategoryHandler {
    static final String EXTRA_INITIAL_REFERRER = "initial_referrer";
    public SplitInfoCallback mCallback;
    public CategoryMixin mCategoryMixin;
    public boolean mIsRegularLayout = true;
    public TopLevelSettings mMainFragment;
    public SplitControllerCallbackAdapter mSplitControllerAdapter;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SplitInfoCallback implements Consumer {
        public final SettingsHomepageActivity mActivity;
        public boolean mIsSplitUpdatedUI = false;

        public SplitInfoCallback(SettingsHomepageActivity settingsHomepageActivity) {
            this.mActivity = settingsHomepageActivity;
        }

        @Override // androidx.core.util.Consumer
        public final void accept(Object obj) {
            if (((List) obj).isEmpty() || this.mIsSplitUpdatedUI) {
                return;
            }
            SettingsHomepageActivity settingsHomepageActivity = this.mActivity;
            if (settingsHomepageActivity.isFinishing()
                    || !ActivityEmbeddingUtils.isAlreadyEmbedded(settingsHomepageActivity)) {
                return;
            }
            this.mIsSplitUpdatedUI = true;
            String str = SettingsHomepageActivity.EXTRA_INITIAL_REFERRER;
            settingsHomepageActivity.updateSplitLayout();
        }
    }

    @Override // com.android.settings.core.CategoryMixin.CategoryHandler
    public final CategoryMixin getCategoryMixin() {
        return this.mCategoryMixin;
    }

    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity
    public final int getContentViewResId() {
        return R.layout.sec_settings_homepage_base_layout;
    }

    public String getCurrentReferrer() {
        Intent intent = getIntent();
        intent.removeExtra("android.intent.extra.REFERRER");
        intent.removeExtra("android.intent.extra.REFERRER_NAME");
        Uri referrer = getReferrer();
        if (referrer != null) {
            return referrer.getHost();
        }
        return null;
    }

    public String getInitialReferrer() {
        String currentReferrer = getCurrentReferrer();
        if (!TextUtils.equals(currentReferrer, getPackageName())) {
            return currentReferrer;
        }
        String stringExtra = getIntent().getStringExtra(EXTRA_INITIAL_REFERRER);
        return TextUtils.isEmpty(stringExtra) ? currentReferrer : stringExtra;
    }

    public void initSplitPairRules() {
        new ActivityEmbeddingRulesController(getApplicationContext()).initRules();
    }

    public boolean isCallingAppPermitted(String str, int i) {
        return TextUtils.isEmpty(str) || checkPermission(str, -1, i) == 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:52:0x00e3, code lost:

       if (r7 == 1000) goto L58;
    */
    /* JADX WARN: Removed duplicated region for block: B:39:0x018a  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x018f  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0193  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void launchDeepLinkIntentToRight() {
        /*
            Method dump skipped, instructions count: 451
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.homepage.SettingsHomepageActivity.launchDeepLinkIntentToRight():void");
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        boolean z;
        super.onActivityResult(i, i2, intent);
        StringBuilder m =
                RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(
                        "Request Code : ", "  , Result Code : ", i, i2, " , Intent : ");
        m.append(intent);
        Log.i("SettingsHomepageActivity", m.toString());
        if (this.mIsEmbeddingActivityEnabled && i == 65536) {
            if (!(this instanceof DeepLinkHomepageActivity)
                    && !(this instanceof DeepLinkHomepageActivityInternal)) {
                Log.d("SettingsHomepageActivity", "Not a deep link component.");
                return;
            }
            if (isFinishing()) {
                Log.d("SettingsHomepageActivity", "Finishing this activity.");
                return;
            }
            try {
                z = ActivityManager.getService().isTopOfTask(getActivityToken());
            } catch (Exception e) {
                e.printStackTrace();
                z = true;
            }
            if (!z) {
                Log.d("SettingsHomepageActivity", "Not a top.");
                return;
            }
            Intent intent2 = new Intent();
            ComponentName componentName =
                    new ComponentName(this, (Class<?>) Settings.ConnectionsSettingsActivity.class);
            intent2.setComponent(componentName);
            Bundle bundle = new Bundle();
            bundle.putBoolean("need_search_icon_in_action_bar", true);
            intent2.putExtra(":settings:show_fragment_args", bundle);
            intent2.putExtra(":settings:is_second_layer_page", true);
            ActivityEmbeddingRulesController.registerTwoPanePairRuleForSettingsHome(
                    this, componentName, null, true, true);
            HomepageUtils.startActivity(this, intent2, CustomDeviceManager.QUICK_PANEL_ALL, null);
        }
    }

    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity,
              // android.app.Activity, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateSplitLayout();
    }

    /* JADX WARN: Code restructure failed: missing block: B:55:0x01eb, code lost:

       if (android.text.TextUtils.isEmpty(r12) == false) goto L59;
    */
    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onCreate(android.os.Bundle r12) {
        /*
            Method dump skipped, instructions count: 677
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.homepage.SettingsHomepageActivity.onCreate(android.os.Bundle):void");
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public final void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (!isFinishing() && shouldLaunchDeepLinkIntentToRight()) {
            launchDeepLinkIntentToRight();
        }
    }

    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        Log.i("VerificationLog", "onResume");
        super.onResume();
        updateSplitLayout();
        Log.i("VerificationLog", "Executed");
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity,
              // android.app.Activity
    public final void onStart() {
        SettingsApplication settingsApplication = (SettingsApplication) getApplication();
        settingsApplication.getClass();
        settingsApplication.mHomeActivity = new WeakReference(this);
        Trace.beginSection("SettingsHomepageActivity#PkgUtils_clearAllCaches");
        HashMap hashMap = PkgUtils.mHasPackageMap;
        SemLog.i("PkgUtil", "clearAllCaches");
        PkgUtils.mHasPackageMap.clear();
        PkgUtils.mPackageEnabledMap.clear();
        Trace.endSection();
        super.onStart();
        if (this.mIsEmbeddingActivityEnabled) {
            SplitControllerCallbackAdapter splitControllerCallbackAdapter =
                    new SplitControllerCallbackAdapter(SplitController.getInstance(this));
            this.mSplitControllerAdapter = splitControllerCallbackAdapter;
            SplitInfoCallback splitInfoCallback = new SplitInfoCallback(this);
            this.mCallback = splitInfoCallback;
            splitControllerCallbackAdapter.addSplitListener(
                    this,
                    new ActivityWindowInfoCallbackController$$ExternalSyntheticLambda1(),
                    splitInfoCallback);
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity,
              // android.app.Activity
    public final void onStop() {
        SplitInfoCallback splitInfoCallback;
        super.onStop();
        SplitControllerCallbackAdapter splitControllerCallbackAdapter =
                this.mSplitControllerAdapter;
        if (splitControllerCallbackAdapter == null
                || (splitInfoCallback = this.mCallback) == null) {
            return;
        }
        splitControllerCallbackAdapter.removeSplitListener(splitInfoCallback);
        this.mCallback = null;
        this.mSplitControllerAdapter = null;
    }

    public final boolean shouldLaunchDeepLinkIntentToRight() {
        boolean isSettingsSplitEnabled;
        boolean z = ActivityEmbeddingUtils.SHOULD_ENABLE_LARGE_SCREEN_OPTIMIZATION;
        if (SecTopLevelFeature.getInstance().getBoolean("isSettingsSplitEnabled")) {
            isSettingsSplitEnabled = true;
        } else {
            isSettingsSplitEnabled = ActivityEmbeddingUtils.isSettingsSplitEnabled(this);
            if (isSettingsSplitEnabled) {
                SecTopLevelFeature.getInstance().setBoolean("isSettingsSplitEnabled", true);
            }
        }
        if (!isSettingsSplitEnabled
                || !FeatureFlagUtils.isEnabled(this, "settings_support_large_screen")) {
            return false;
        }
        Intent intent = getIntent();
        return intent != null
                && TextUtils.equals(
                        intent.getAction(), "android.settings.SETTINGS_EMBED_DEEP_LINK_ACTIVITY");
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x006e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x006f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateSplitLayout() {
        /*
            r5 = this;
            boolean r0 = r5.mIsEmbeddingActivityEnabled
            if (r0 != 0) goto L5
            return
        L5:
            boolean r0 = com.android.settings.activityembedding.ActivityEmbeddingUtils.SHOULD_ENABLE_LARGE_SCREEN_OPTIMIZATION
            androidx.window.embedding.ActivityEmbeddingController r0 = androidx.window.embedding.ActivityEmbeddingController.getInstance(r5)
            boolean r0 = r0.isActivityEmbedded(r5)
            r0 = r0 ^ 1
            if (r0 != 0) goto L5c
            android.content.res.Resources r1 = r5.getResources()
            r2 = 2131165841(0x7f070291, float:1.794591E38)
            float r1 = r1.getFloat(r2)
            android.content.res.Resources r2 = r5.getResources()
            android.content.res.Configuration r2 = r2.getConfiguration()
            int r2 = r2.screenWidthDp
            float r3 = (float) r2
            float r3 = r3 / r1
            r4 = 1065353216(0x3f800000, float:1.0)
            float r4 = r4 - r1
            float r4 = r4 * r3
            int r1 = java.lang.Math.round(r4)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "primaryScreenWidthDp : "
            r3.<init>(r4)
            r3.append(r2)
            java.lang.String r2 = r3.toString()
            java.lang.String r3 = "SettingsHomepageActivity"
            android.util.Log.d(r3, r2)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r4 = "secondaryScreenWidthDp : "
            r2.<init>(r4)
            r2.append(r1)
            java.lang.String r2 = r2.toString()
            android.util.Log.d(r3, r2)
            r2 = 589(0x24d, float:8.25E-43)
            if (r1 < r2) goto L5c
            r1 = 0
            goto L5e
        L5c:
            r1 = 8
        L5e:
            r2 = 2131362841(0x7f0a0419, float:1.8345474E38)
            android.view.View r2 = r5.findViewById(r2)
            if (r2 == 0) goto L6a
            r2.setVisibility(r1)
        L6a:
            boolean r1 = r5.mIsRegularLayout
            if (r1 != r0) goto L6f
            return
        L6f:
            r5.mIsRegularLayout = r0
            androidx.fragment.app.FragmentManagerImpl r0 = r5.getSupportFragmentManager()
            androidx.fragment.app.FragmentStore r0 = r0.mFragmentStore
            java.util.List r0 = r0.getFragments()
            com.android.settings.homepage.SettingsHomepageActivity$$ExternalSyntheticLambda0 r1 = new com.android.settings.homepage.SettingsHomepageActivity$$ExternalSyntheticLambda0
            r1.<init>()
            r0.forEach(r1)
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.homepage.SettingsHomepageActivity.updateSplitLayout():void");
    }
}
