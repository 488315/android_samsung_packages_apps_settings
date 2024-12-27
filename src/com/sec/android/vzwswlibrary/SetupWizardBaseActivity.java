package com.sec.android.vzwswlibrary;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.settings.R;

import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupdesign.GlifLayout;
import com.samsung.android.knox.container.EnterpriseContainerConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class SetupWizardBaseActivity extends AppCompatActivity {
    public SetupWizardBaseActivity mContext;
    public FooterButton mMainActionButton;
    public FooterButton mManualSetupButton;
    public GlifLayout mRootLayout;
    public TextView mShortDescription;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        String str;
        super.onCreate(bundle);
        if (getActionBar() != null) {
            getActionBar().hide();
        }
        super.setContentView(R.layout.vswl_base_layout);
        this.mRootLayout = (GlifLayout) findViewById(R.id.vswl_glif_root);
        this.mShortDescription = (TextView) findViewById(R.id.sud_layout_description);
        SetupWizardBaseActivity setupWizardBaseActivity = this.mContext;
        if (setupWizardBaseActivity == null
                || !(setupWizardBaseActivity
                                .getPackageManager()
                                .hasSystemFeature("com.samsung.feature.samsung_experience_mobile")
                        || setupWizardBaseActivity
                                .getPackageManager()
                                .hasSystemFeature(
                                        "com.samsung.feature.samsung_experience_mobile_lite"))) {
            str = "1.0";
        } else {
            int i = Build.VERSION.SEM_PLATFORM_INT - 90000;
            str =
                    String.valueOf(i / EnterpriseContainerConstants.SYSTEM_SIGNED_APP)
                            + "."
                            + String.valueOf(
                                    (i % EnterpriseContainerConstants.SYSTEM_SIGNED_APP) / 100);
        }
        if (str.equals("3.1")) {
            this.mRootLayout.getHeaderTextView().setTextAppearance(R.style.vswl_header_text);
            this.mShortDescription.setTextSize(2, 14.0f);
        }
        this.mRootLayout.setIcon(getResources().getDrawable(R.drawable.header_ic_transparent));
        int dimensionPixelSize =
                getResources().getDimensionPixelSize(R.dimen.vswl_title_area_bottom_margin);
        TextView headerTextView = this.mRootLayout.getHeaderTextView();
        ViewGroup.MarginLayoutParams marginLayoutParams =
                (ViewGroup.MarginLayoutParams) headerTextView.getLayoutParams();
        marginLayoutParams.bottomMargin = dimensionPixelSize;
        headerTextView.setLayoutParams(marginLayoutParams);
        this.mRootLayout.getHeaderTextView().setVisibility(8);
        getWindow()
                .getDecorView()
                .setOnSystemUiVisibilityChangeListener(
                        new View
                                .OnSystemUiVisibilityChangeListener() { // from class:
                                                                        // com.sec.android.vzwswlibrary.SetupWizardBaseActivity.1
                            @Override // android.view.View.OnSystemUiVisibilityChangeListener
                            public final void onSystemUiVisibilityChange(int i2) {
                                SetupWizardBaseActivity setupWizardBaseActivity2 =
                                        SetupWizardBaseActivity.this;
                                int dimension =
                                        (int)
                                                setupWizardBaseActivity2
                                                        .getResources()
                                                        .getDimension(
                                                                R.dimen.vswl_navigation_bar_height);
                                if ((i2 & 2) == 0) {
                                    setupWizardBaseActivity2.mRootLayout.setPadding(0, 0, 0, 0);
                                } else {
                                    setupWizardBaseActivity2.mRootLayout.setPadding(
                                            0, 0, 0, dimension);
                                }
                            }
                        });
        this.mRootLayout.getScrollView().setScrollIndicators(0);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity,
              // android.app.Activity
    public final void setContentView(int i) {
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.vswl_scroll_view);
        if (viewGroup != null) {
            viewGroup.removeAllViews();
        }
        LayoutInflater.from(this).inflate(i, viewGroup);
    }

    public final void setMainActionButtonVisibility(Boolean bool) {
        FooterButton footerButton = this.mMainActionButton;
        if (footerButton != null) {
            footerButton.setVisibility(bool.booleanValue() ? 0 : 8);
        }
    }

    public final void setManualSetupButtonVisibility(Boolean bool) {
        FooterButton footerButton = this.mManualSetupButton;
        if (footerButton != null) {
            footerButton.setVisibility(bool.booleanValue() ? 0 : 8);
        }
    }
}
