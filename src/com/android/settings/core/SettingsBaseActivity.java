package com.android.settings.core;

import android.R;
import android.app.ActivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.view.ViewCompat;

import com.android.settings.Utils;
import com.android.settings.Utils$$ExternalSyntheticLambda3;
import com.android.settingslib.core.lifecycle.HideNonSystemOverlayMixin;
import com.android.window.flags.Flags;

import com.google.android.material.resources.TextAppearanceConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.util.BuildCompatUtils;
import com.google.android.setupcompat.util.WizardManagerHelper;
import com.google.android.setupdesign.transition.TransitionHelper;
import com.samsung.android.settings.Trace;
import com.samsung.android.settings.core.SecSettingsBaseActivity;

import java.util.WeakHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SettingsBaseActivity extends SecSettingsBaseActivity
        implements CategoryMixin.CategoryHandler {
    public CategoryMixin mCategoryMixin;

    public boolean enforceEdgeToEdge() {
        return false;
    }

    @Override // com.android.settings.core.CategoryMixin.CategoryHandler
    public final CategoryMixin getCategoryMixin() {
        return this.mCategoryMixin;
    }

    public boolean isLaunchableInTaskModePinned() {
        return false;
    }

    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        Trace.beginSection("SettingsBaseActivity#onCreate");
        if (WizardManagerHelper.isAnySetupWizard(getIntent())) {
            TransitionHelper.applyForwardTransition(this, 5);
            if (!BuildCompatUtils.isAtLeastU()
                    || !PartnerConfigHelper.isGlifThemeControlledTransitionApplied(this)) {
                BuildCompatUtils.isAtLeastU();
            }
        }
        super.onCreate(bundle);
        if (isFinishing()) {
            Trace.endSection();
            return;
        }
        if (((ActivityManager) getApplicationContext().getSystemService(ActivityManager.class))
                        .getLockTaskModeState()
                == 2) {
            if (!TextUtils.equals(
                            getPackageName(),
                            ((ActivityManager)
                                            getApplicationContext()
                                                    .getSystemService(ActivityManager.class))
                                    .getRunningTasks(1)
                                    .get(0)
                                    .baseActivity
                                    .getPackageName())
                    && !isLaunchableInTaskModePinned()) {
                Utils.showPinWindowToast(getApplicationContext());
                Log.w("SettingsBaseActivity", "Devices lock task mode pinned.");
                finish();
            }
        }
        System.currentTimeMillis();
        if (enforceEdgeToEdge()) {
            Log.d("SettingsBaseActivity", "KnoxWorkChallenge Case");
        } else if (Flags.enforceEdgeToEdge()) {
            StringBuilder sb = Utils.sBuilder;
            View findViewById = findViewById(R.id.content);
            Utils$$ExternalSyntheticLambda3 utils$$ExternalSyntheticLambda3 =
                    new Utils$$ExternalSyntheticLambda3();
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api21Impl.setOnApplyWindowInsetsListener(
                    findViewById, utils$$ExternalSyntheticLambda3);
            View findViewById2 = findViewById(R.id.action_bar_spinner);
            if (findViewById2 != null) {
                findViewById2.setVisibility(8);
            }
        }
        getLifecycle().addObserver(new HideNonSystemOverlayMixin(this));
        TextAppearanceConfig.shouldLoadFontSynchronously = true;
        this.mCategoryMixin = new CategoryMixin(this);
        getLifecycle().addObserver(this.mCategoryMixin);
        Trace.endSection();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity,
              // android.app.Activity
    public final void setContentView(int i) {
        ViewGroup viewGroup = (ViewGroup) findViewById(com.android.settings.R.id.content_frame);
        if (viewGroup != null) {
            viewGroup.removeAllViews();
        }
        LayoutInflater.from(this).inflate(i, viewGroup);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity,
              // android.app.Activity
    public void setContentView(View view) {
        ((ViewGroup) findViewById(com.android.settings.R.id.content_frame)).addView(view);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity,
              // android.app.Activity
    public final void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        ((ViewGroup) findViewById(com.android.settings.R.id.content_frame))
                .addView(view, layoutParams);
    }
}
