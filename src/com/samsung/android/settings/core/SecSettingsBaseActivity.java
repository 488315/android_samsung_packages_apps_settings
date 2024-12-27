package com.samsung.android.settings.core;

import android.app.Activity;
import android.app.ActivityClient;
import android.app.ActivityOptions;
import android.app.ActivityThread;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.accessibility.AccessibilityManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;

import com.android.settings.R;
import com.android.settings.Settings;
import com.android.settings.SubSettings;
import com.android.settings.Utils;
import com.android.settings.activityembedding.ActivityEmbeddingUtils;
import com.android.settings.core.InstrumentedPreferenceFragment;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.homepage.SettingsHomepageActivity;
import com.android.settings.homepage.TopLevelSettings;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.search.actionbar.SearchMenuController$$ExternalSyntheticLambda1;
import com.android.settingslib.core.instrumentation.Instrumentable;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.setupcompat.util.WizardManagerHelper;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.custom.ProKioskManager;
import com.samsung.android.settings.homepage.HomepageUtils;
import com.samsung.android.settings.homepage.SecTopLevelFeature;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SecSettingsBaseActivity extends AppCompatActivity {
    public AccessibilityManager mAccessibilityManager;
    public AppBarLayout mAppBarLayout;
    public CollapsingToolbarLayout mCollapsingToolbarLayout;
    public boolean mIsEmbeddingActivityEnabled;
    public AssistantReceiver mReceiver;
    public boolean mIsDisabledAppBar = false;
    public SearchMenuController$$ExternalSyntheticLambda1 mOnConfigurationChangedListener = null;
    public onKeyEventListener mOnKeyEventListener = null;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AssistantReceiver extends BroadcastReceiver {
        public AssistantReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.android.settings.Search")) {
                SecSettingsBaseActivity secSettingsBaseActivity = SecSettingsBaseActivity.this;
                secSettingsBaseActivity.getClass();
                FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                if (featureFactoryImpl == null) {
                    throw new UnsupportedOperationException("No feature factory configured");
                }
                secSettingsBaseActivity.startActivityForResult(
                        featureFactoryImpl
                                .getSearchFeatureProvider()
                                .buildSearchIntent(secSettingsBaseActivity, 0),
                        501);
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface onKeyEventListener {
        boolean onKeyDown(int i, KeyEvent keyEvent);
    }

    public static Activity findActivityBelow(Activity activity) {
        IBinder activityTokenBelow =
                ActivityClient.getInstance().getActivityTokenBelow(activity.getActivityToken());
        if (activityTokenBelow != null) {
            return ActivityThread.currentActivityThread().getActivity(activityTokenBelow);
        }
        return null;
    }

    public static boolean isAllSameUid(Activity activity) {
        Activity findActivityBelow = findActivityBelow(activity);
        if (findActivityBelow == null) {
            return false;
        }
        if (findActivityBelow instanceof SettingsHomepageActivity) {
            return true;
        }
        return isAllSameUid(findActivityBelow);
    }

    public final void applyExtendedAppBar() {
        AppBarLayout appBarLayout = this.mAppBarLayout;
        if (appBarLayout == null) {
            return;
        }
        AppBarLayout.Behavior behavior =
                (AppBarLayout.Behavior)
                        ((CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams()).mBehavior;
        behavior.onDragCallback =
                new AppBarLayout.Behavior
                        .DragCallback() { // from class:
                                          // com.samsung.android.settings.core.SecSettingsBaseActivity.1
                    @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior.BaseDragCallback
                    public final boolean canDrag(AppBarLayout appBarLayout2) {
                        SecSettingsBaseActivity secSettingsBaseActivity =
                                SecSettingsBaseActivity.this;
                        return !((secSettingsBaseActivity.mIsEmbeddingActivityEnabled
                                        && !(secSettingsBaseActivity
                                                instanceof SettingsHomepageActivity)
                                        && ActivityEmbeddingUtils.isAlreadyEmbedded(
                                                secSettingsBaseActivity))
                                ? true
                                : secSettingsBaseActivity.mIsDisabledAppBar);
                    }
                };
        if (behavior instanceof DisableableAppBarLayoutBehavior) {
            ((DisableableAppBarLayoutBehavior) behavior).mEnabled =
                    !((this.mIsEmbeddingActivityEnabled
                                    && !(this instanceof SettingsHomepageActivity)
                                    && ActivityEmbeddingUtils.isAlreadyEmbedded(this))
                            ? true
                            : this.mIsDisabledAppBar);
        }
    }

    public boolean canUsePathProvider() {
        return (this instanceof SubSettings)
                || getIntent().getBooleanExtra(":settings:show_fragment_as_subsetting", false);
    }

    public final void disableExtendedAppBar() {
        this.mIsDisabledAppBar = true;
        applyExtendedAppBar();
    }

    public int getContentViewResId() {
        return R.layout.sec_settings_base_layout;
    }

    public final void hideAppBar() {
        AppBarLayout appBarLayout = this.mAppBarLayout;
        if (appBarLayout != null) {
            appBarLayout.setVisibility(8);
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity,
              // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        AppBarLayout appBarLayout;
        super.onConfigurationChanged(configuration);
        applyExtendedAppBar();
        if (this.mIsEmbeddingActivityEnabled
                && !(this instanceof SettingsHomepageActivity)
                && ActivityEmbeddingUtils.isAlreadyEmbedded(this)
                && (appBarLayout = this.mAppBarLayout) != null) {
            appBarLayout.setExpanded(false);
        }
        Utils.applyLandscapeFullScreen(this, getWindow());
        if (this.mOnConfigurationChangedListener != null) {
            Log.d(
                    "SecSettingsBaseActivity",
                    "onConfigurationChanged : " + this.mOnConfigurationChangedListener.toString());
            this.mOnConfigurationChangedListener.f$0.updateSearchItemVisibility();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        System.currentTimeMillis();
        this.mIsEmbeddingActivityEnabled = ActivityEmbeddingUtils.isEmbeddingActivityEnabled(this);
        SecTopLevelFeature.getInstance()
                .setBoolean(
                        "feature_is_embedding_activity_enabled", this.mIsEmbeddingActivityEnabled);
        super.setContentView(getContentViewResId());
        this.mCollapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_app_bar);
        this.mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        boolean z = this instanceof SettingsHomepageActivity;
        if (bundle != null) {
            this.mIsDisabledAppBar = bundle.getBoolean("disabled_app_bar", false);
            z = bundle.getBoolean("expanded_app_bar", z);
        }
        setSupportActionBar((Toolbar) findViewById(R.id.action_bar));
        applyExtendedAppBar();
        AppBarLayout appBarLayout = this.mAppBarLayout;
        if (appBarLayout != null) {
            appBarLayout.setExpanded(z);
        }
        this.mAccessibilityManager = (AccessibilityManager) getSystemService("accessibility");
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0081 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0082  */
    @Override // android.app.Activity, android.view.KeyEvent.Callback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onKeyDown(int r8, android.view.KeyEvent r9) {
        /*
            r7 = this;
            com.samsung.android.settings.core.SecSettingsBaseActivity$onKeyEventListener r0 = r7.mOnKeyEventListener
            r1 = 1
            if (r0 == 0) goto Lc
            boolean r0 = r0.onKeyDown(r8, r9)
            if (r0 == 0) goto Lc
            return r1
        Lc:
            r0 = 84
            if (r8 == r0) goto L1a
            boolean r0 = r9.isCtrlPressed()
            if (r0 == 0) goto L76
            r0 = 34
            if (r8 != r0) goto L76
        L1a:
            android.content.Intent r0 = r7.getIntent()
            boolean r2 = com.android.settings.activityembedding.ActivityEmbeddingUtils.SHOULD_ENABLE_LARGE_SCREEN_OPTIMIZATION
            androidx.window.embedding.ActivityEmbeddingController r2 = androidx.window.embedding.ActivityEmbeddingController.getInstance(r7)
            boolean r2 = r2.isActivityEmbedded(r7)
            r2 = r2 ^ r1
            r3 = 0
            if (r0 == 0) goto L66
            java.lang.String r4 = ":settings:show_fragment_args"
            android.os.Bundle r4 = r0.getBundleExtra(r4)
            android.content.ComponentName r5 = r0.getComponent()
            if (r5 == 0) goto L60
            android.content.ComponentName r5 = r0.getComponent()
            java.lang.String r5 = r5.getClassName()
            java.lang.Class<com.android.settings.Settings> r6 = com.android.settings.Settings.class
            java.lang.String r6 = r6.getName()
            boolean r5 = r5.equals(r6)
            if (r5 != 0) goto L62
            android.content.ComponentName r0 = r0.getComponent()
            java.lang.String r0 = r0.getClassName()
            java.lang.Class<com.android.settings.homepage.SettingsHomepageActivity> r5 = com.android.settings.homepage.SettingsHomepageActivity.class
            java.lang.String r5 = r5.getName()
            boolean r0 = r0.equals(r5)
            if (r0 != 0) goto L62
        L60:
            if (r2 != 0) goto L64
        L62:
            r0 = r1
            goto L68
        L64:
            r0 = r3
            goto L68
        L66:
            r4 = 0
            goto L64
        L68:
            if (r0 != 0) goto L7b
            if (r4 == 0) goto L76
            java.lang.String r0 = "need_search_icon_in_action_bar"
            boolean r0 = r4.getBoolean(r0, r3)
            if (r0 == 0) goto L76
            goto L7b
        L76:
            boolean r7 = super.onKeyDown(r8, r9)
            return r7
        L7b:
            boolean r8 = r7.semIsResumed()
            if (r8 != 0) goto L82
            return r1
        L82:
            com.android.settings.overlay.FeatureFactoryImpl r8 = com.android.settings.overlay.FeatureFactoryImpl._factory
            if (r8 == 0) goto L94
            com.android.settings.search.SearchFeatureProviderImpl r8 = r8.getSearchFeatureProvider()
            android.content.Intent r8 = r8.buildSearchIntent(r7, r3)
            r9 = 501(0x1f5, float:7.02E-43)
            r7.startActivityForResult(r8, r9)
            return r1
        L94:
            java.lang.UnsupportedOperationException r7 = new java.lang.UnsupportedOperationException
            java.lang.String r8 = "No feature factory configured"
            r7.<init>(r8)
            throw r7
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.core.SecSettingsBaseActivity.onKeyDown(int,"
                    + " android.view.KeyEvent):boolean");
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onMultiWindowModeChanged(boolean z) {
        super.onMultiWindowModeChanged(z);
        if (z) {
            unregisterAssistant();
        } else if (hasWindowFocus()) {
            registerAssistant();
        }
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332 && WizardManagerHelper.isDeviceProvisioned(this)) {
            boolean isKnoxId =
                    SemPersonaManager.isKnoxId(
                            getIntent()
                                    .getIntExtra(
                                            "android.intent.extra.USER_ID", UserHandle.myUserId()));
            ProKioskManager proKioskManager = ProKioskManager.getInstance();
            boolean proKioskState =
                    (proKioskManager == null || !proKioskManager.getProKioskState())
                            ? false
                            : proKioskManager.getProKioskState();
            if (isKnoxId) {
                finish();
                return true;
            }
            if (proKioskState) {
                finish();
                return true;
            }
            Intent parentActivityIntent = getParentActivityIntent();
            if (parentActivityIntent != null) {
                if (parentActivityIntent.getComponent() != null
                        && (TextUtils.equals(
                                        Settings.class.getName(),
                                        parentActivityIntent.getComponent().getClassName())
                                || TextUtils.equals(
                                        SettingsHomepageActivity.class.getName(),
                                        parentActivityIntent.getComponent().getClassName()))) {
                    parentActivityIntent.addFlags(603979776);
                }
                finishAfterTransition();
                ActivityOptions makeCustomAnimation =
                        ActivityOptions.makeCustomAnimation(
                                this,
                                R.anim.sesl_hierarchy_up_enter,
                                R.anim.sesl_hierarchy_up_exit);
                try {
                    makeCustomAnimation.setDisableSplashScreen();
                    startActivity(parentActivityIntent, makeCustomAnimation.toBundle());
                } catch (ActivityNotFoundException e) {
                    Log.d("SecSettingsBaseActivity", "Failed hierarchy up : " + e);
                }
            } else if (canUsePathProvider()) {
                for (LifecycleOwner lifecycleOwner :
                        getSupportFragmentManager().mFragmentStore.getFragments()) {
                    if (lifecycleOwner instanceof InstrumentedPreferenceFragment) {
                        String hierarchicalParentFragment =
                                ((InstrumentedPreferenceFragment) lifecycleOwner)
                                        .getHierarchicalParentFragment(this);
                        if (TextUtils.isEmpty(hierarchicalParentFragment)) {
                            continue;
                        } else if (!TextUtils.equals(
                                TopLevelSettings.class.getName(), hierarchicalParentFragment)) {
                            Activity findActivityBelow = findActivityBelow(this);
                            if (findActivityBelow instanceof FragmentActivity) {
                                Iterator it =
                                        ((FragmentActivity) findActivityBelow)
                                                .getSupportFragmentManager()
                                                .mFragmentStore
                                                .getFragments()
                                                .iterator();
                                while (it.hasNext()) {
                                    if (TextUtils.equals(
                                            ((Fragment) it.next()).getClass().getName(),
                                            hierarchicalParentFragment)) {
                                        break;
                                    }
                                }
                            }
                            finishAfterTransition();
                            SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this);
                            SubSettingLauncher.LaunchRequest launchRequest =
                                    subSettingLauncher.mLaunchRequest;
                            launchRequest.mDestinationName = hierarchicalParentFragment;
                            launchRequest.mSourceMetricsCategory =
                                    lifecycleOwner instanceof Instrumentable
                                            ? ((Instrumentable) lifecycleOwner).getMetricsCategory()
                                            : 0;
                            Intent intent = subSettingLauncher.toIntent();
                            ActivityOptions makeCustomAnimation2 =
                                    ActivityOptions.makeCustomAnimation(
                                            this,
                                            R.anim.sesl_hierarchy_up_enter,
                                            R.anim.sesl_hierarchy_up_exit);
                            makeCustomAnimation2.setDisableSplashScreen();
                            startActivity(intent, makeCustomAnimation2.toBundle());
                        } else if (!(findActivityBelow(this) instanceof SettingsHomepageActivity)) {
                            Intent intent2 = new Intent();
                            intent2.setAction("android.settings.SETTINGS");
                            intent2.addFlags(603979776);
                            startActivity(intent2);
                            finish();
                        }
                    }
                }
            }
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        unregisterAssistant();
        super.onPause();
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0044, code lost:

       if (android.text.TextUtils.isEmpty(r1) == false) goto L36;
    */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onResume() {
        /*
            r4 = this;
            super.onResume()
            boolean r0 = r4.hasWindowFocus()
            if (r0 == 0) goto Lc
            r4.registerAssistant()
        Lc:
            android.view.Window r0 = r4.getWindow()
            com.android.settings.Utils.applyLandscapeFullScreen(r4, r0)
            boolean r0 = r4 instanceof com.android.settings.homepage.SettingsHomepageActivity
            if (r0 != 0) goto Lc4
            boolean r0 = r4.mIsEmbeddingActivityEnabled
            if (r0 != 0) goto L1d
            goto Lc4
        L1d:
            boolean r0 = isAllSameUid(r4)
            if (r0 != 0) goto L25
            goto Lc4
        L25:
            r0 = 0
            android.content.pm.PackageManager r1 = r4.getPackageManager()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L47
            android.content.ComponentName r2 = r4.getComponentName()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L47
            r3 = 128(0x80, float:1.794E-43)
            android.content.pm.ActivityInfo r1 = r1.getActivityInfo(r2, r3)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L47
            if (r1 == 0) goto L62
            android.os.Bundle r1 = r1.metaData     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L47
            if (r1 == 0) goto L62
            java.lang.String r2 = "com.android.settings.HIGHLIGHT_MENU_KEY"
            java.lang.String r1 = r1.getString(r2)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L47
            boolean r2 = android.text.TextUtils.isEmpty(r1)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L47
            if (r2 != 0) goto L62
            goto La1
        L47:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "Cannot get Metadata for: "
            r1.<init>(r2)
            android.content.ComponentName r2 = r4.getComponentName()
            java.lang.String r2 = r2.toString()
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = "SecSettingsBaseActivity"
            android.util.Log.d(r2, r1)
        L62:
            boolean r1 = r4 instanceof com.android.settings.SubSettings
            if (r1 != 0) goto L73
            android.content.Intent r1 = r4.getIntent()
            java.lang.String r2 = ":settings:show_fragment_as_subsetting"
            r3 = 0
            boolean r1 = r1.getBooleanExtra(r2, r3)
            if (r1 == 0) goto La0
        L73:
            androidx.fragment.app.FragmentManagerImpl r1 = r4.getSupportFragmentManager()
            androidx.fragment.app.FragmentStore r1 = r1.mFragmentStore
            java.util.List r1 = r1.getFragments()
            java.util.Iterator r1 = r1.iterator()
        L81:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto La0
            java.lang.Object r2 = r1.next()
            androidx.fragment.app.Fragment r2 = (androidx.fragment.app.Fragment) r2
            boolean r3 = r2 instanceof com.android.settings.core.InstrumentedPreferenceFragment
            if (r3 != 0) goto L92
            goto L81
        L92:
            com.android.settings.core.InstrumentedPreferenceFragment r2 = (com.android.settings.core.InstrumentedPreferenceFragment) r2
            java.lang.String r2 = r2.getTopLevelPreferenceKey(r4)
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            if (r3 != 0) goto L81
            r1 = r2
            goto La1
        La0:
            r1 = r0
        La1:
            android.content.Context r4 = r4.getApplicationContext()
            com.android.settings.SettingsApplication r4 = (com.android.settings.SettingsApplication) r4
            com.android.settings.homepage.SettingsHomepageActivity r4 = r4.getHomeActivity()
            if (r4 == 0) goto Lc4
            com.android.settings.homepage.TopLevelSettings r2 = r4.mMainFragment
            if (r2 == 0) goto Lc4
            com.android.settings.homepage.TopLevelHighlightMixin r2 = r2.mHighlightMixin
            if (r2 == 0) goto Lb9
            java.lang.String r0 = r2.getHighlightPreferenceKey()
        Lb9:
            boolean r0 = android.text.TextUtils.equals(r0, r1)
            if (r0 != 0) goto Lc4
            com.android.settings.homepage.TopLevelSettings r4 = r4.mMainFragment
            r4.setHighlightMenuKey(r1)
        Lc4:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.core.SecSettingsBaseActivity.onResume():void");
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity,
              // android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("disabled_app_bar", this.mIsDisabledAppBar);
        AppBarLayout appBarLayout = this.mAppBarLayout;
        bundle.putBoolean(
                "expanded_app_bar", (appBarLayout == null || appBarLayout.lifted) ? false : true);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final boolean onSearchRequested() {
        View findViewById = findViewById(R.id.search_bar);
        if (findViewById == null || findViewById.getVisibility() != 0) {
            return false;
        }
        Intent intent = new Intent("com.android.settings.action.SETTINGS_SEARCH");
        intent.setPackage("com.android.settings.intelligence");
        startActivity(intent);
        return false;
    }

    @Override // androidx.appcompat.app.AppCompatActivity
    public boolean onSupportNavigateUp() {
        if (super.onSupportNavigateUp()) {
            return true;
        }
        finish();
        return true;
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (z) {
            registerAssistant();
        } else {
            unregisterAssistant();
        }
    }

    public final void registerAssistant() {
        if (this.mReceiver == null) {
            ArrayList arrayList = HomepageUtils.SEPARATORS;
            if (Settings.System.getInt(getContentResolver(), "assistant_menu", 0) != 0) {
                Bundle bundle = new Bundle();
                bundle.putString(
                        "ActivityName", "com.android.settings.homepage.SettingsHomepageActivity");
                bundle.putString("IconName", "Search");
                bundle.putBoolean("register", true);
                this.mAccessibilityManager.semUpdateAssitantMenu(bundle);
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("com.android.settings.Favorite");
                intentFilter.addAction("com.android.settings.Search");
                AssistantReceiver assistantReceiver = new AssistantReceiver();
                this.mReceiver = assistantReceiver;
                registerReceiver(assistantReceiver, intentFilter, 2);
                Log.i("SecSettingsBaseActivity", "AssistantReceiver registered");
            }
        }
    }

    @Override // android.app.Activity
    public final void setTitle(CharSequence charSequence) {
        super.setTitle(charSequence);
        CollapsingToolbarLayout collapsingToolbarLayout = this.mCollapsingToolbarLayout;
        if (collapsingToolbarLayout != null) {
            collapsingToolbarLayout.setTitle(charSequence);
        }
    }

    public final void unregisterAssistant() {
        if (this.mReceiver == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("ActivityName", "com.android.settings.homepage.SettingsHomepageActivity");
        bundle.putBoolean("register", false);
        this.mAccessibilityManager.semUpdateAssitantMenu(bundle);
        try {
            unregisterReceiver(this.mReceiver);
            this.mReceiver = null;
            Log.i("SecSettingsBaseActivity", "AssistantReceiver unregistered");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override // android.app.Activity
    public final void setTitle(int i) {
        super.setTitle(getText(i));
        CollapsingToolbarLayout collapsingToolbarLayout = this.mCollapsingToolbarLayout;
        if (collapsingToolbarLayout != null) {
            collapsingToolbarLayout.setTitle(getText(i));
        }
    }
}
