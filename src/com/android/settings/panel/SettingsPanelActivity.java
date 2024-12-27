package com.android.settings.panel;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;

import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManagerImpl;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settingslib.core.lifecycle.HideNonSystemOverlayMixin;

import java.util.WeakHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Deprecated(forRemoval = true)
/* loaded from: classes2.dex */
public class SettingsPanelActivity extends FragmentActivity {
    public static final /* synthetic */ int $r8$clinit = 0;

    @VisibleForTesting final Bundle mBundle = new Bundle();

    @VisibleForTesting boolean mForceCreation = false;

    @VisibleForTesting PanelFragment mPanelFragment;

    public final void createOrUpdatePanel(boolean z) {
        Intent intent = getIntent();
        if (intent == null) {
            Log.e("SettingsPanelActivity", "Null intent, closing Panel Activity");
            finish();
            return;
        }
        String action = intent.getAction();
        if (TextUtils.isEmpty(action)) {
            Log.e("SettingsPanelActivity", "action is null, so finishing");
            finish();
            return;
        }
        String stringExtra = intent.getStringExtra("package_name");
        this.mBundle.putString("PANEL_TYPE_ARGUMENT", action);
        this.mBundle.putString("PANEL_CALLING_PACKAGE_NAME", getCallingPackage());
        this.mBundle.putString("PANEL_MEDIA_PACKAGE_NAME", stringExtra);
        FragmentManagerImpl supportFragmentManager = getSupportFragmentManager();
        Fragment findFragmentById = supportFragmentManager.findFragmentById(R.id.main_content);
        if (z || findFragmentById == null || !(findFragmentById instanceof PanelFragment)) {
            setContentView(R.layout.settings_panel);
            Window window = getWindow();
            window.setGravity(80);
            window.setLayout(-1, -2);
            View decorView = getWindow().getDecorView();
            SettingsPanelActivity$$ExternalSyntheticLambda0
                    settingsPanelActivity$$ExternalSyntheticLambda0 =
                            new SettingsPanelActivity$$ExternalSyntheticLambda0();
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api21Impl.setOnApplyWindowInsetsListener(
                    decorView, settingsPanelActivity$$ExternalSyntheticLambda0);
            WindowInsetsControllerCompat windowInsetsController =
                    ViewCompat.Api30Impl.getWindowInsetsController(getWindow().getDecorView());
            if (windowInsetsController != null) {
                windowInsetsController.setAppearanceLightNavigationBars(!Utils.isNightMode(this));
            }
            PanelFragment panelFragment = new PanelFragment();
            this.mPanelFragment = panelFragment;
            panelFragment.setArguments(new Bundle(this.mBundle));
            BackStackRecord backStackRecord = new BackStackRecord(supportFragmentManager);
            backStackRecord.doAddOp(R.id.main_content, this.mPanelFragment, null, 1);
            backStackRecord.commitInternal(false);
            return;
        }
        PanelFragment panelFragment2 = (PanelFragment) findFragmentById;
        this.mPanelFragment = panelFragment2;
        if (panelFragment2.mPanelCreating) {
            MotionLayout$$ExternalSyntheticOutline0.m(
                    "A panel is creating, skip ", action, "SettingsPanelActivity");
            return;
        }
        Bundle arguments = findFragmentById.getArguments();
        if (arguments != null
                && TextUtils.equals(action, arguments.getString("PANEL_TYPE_ARGUMENT"))) {
            MotionLayout$$ExternalSyntheticOutline0.m(
                    "Panel is showing the same action, skip ", action, "SettingsPanelActivity");
            return;
        }
        this.mPanelFragment.setArguments(new Bundle(this.mBundle));
        final PanelFragment panelFragment3 = this.mPanelFragment;
        panelFragment3.mPanelCreating = true;
        AnimatorSet buildAnimatorSet =
                PanelFragment.buildAnimatorSet(
                        panelFragment3.mLayoutView,
                        0.0f,
                        panelFragment3.mLayoutView.findViewById(R.id.panel_container).getHeight(),
                        1.0f,
                        0.0f,
                        200);
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setFloatValues(0.0f, 1.0f);
        buildAnimatorSet.play(valueAnimator);
        buildAnimatorSet.addListener(
                new AnimatorListenerAdapter() { // from class:
                                                // com.android.settings.panel.PanelFragment.3
                    @Override // android.animation.AnimatorListenerAdapter,
                              // android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        PanelFragment.this.createPanelContent();
                    }
                });
        buildAnimatorSet.start();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mForceCreation = true;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getApplicationContext().getTheme().rebase();
        createOrUpdatePanel(true);
        getLifecycle().addObserver(new HideNonSystemOverlayMixin(this));
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public final void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        createOrUpdatePanel(this.mForceCreation);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        super.onResume();
        this.mForceCreation = false;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onStop() {
        super.onStop();
        PanelFragment panelFragment = this.mPanelFragment;
        if (panelFragment == null || panelFragment.mPanelCreating) {
            return;
        }
        this.mForceCreation = true;
    }
}
