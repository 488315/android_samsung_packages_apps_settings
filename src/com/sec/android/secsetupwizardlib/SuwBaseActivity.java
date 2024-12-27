package com.sec.android.secsetupwizardlib;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.settings.R;

import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupdesign.GlifLayout;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class SuwBaseActivity extends AppCompatActivity {
    public final SuwBaseActivity mContext = this;
    public boolean mIsNeedScrollView = true;
    public FooterButton mPrimaryButton;
    public GlifLayout mRootLayout;
    public FooterButton mSecondaryButton;

    public final boolean isScrollBottomReached() {
        ScrollView scrollView = this.mRootLayout.getScrollView();
        if (scrollView != null) {
            return scrollView.getChildAt(scrollView.getChildCount() - 1).getBottom()
                            <= getResources()
                                            .getDimensionPixelSize(
                                                    R.dimen.sswl_scroll_bottom_margin_ignored)
                                    + (scrollView.getScrollY() + scrollView.getHeight())
                    && scrollView.getHeight() != 0;
        }
        return false;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            bundle.getBoolean("isFirstTime", true);
        }
        if (getActionBar() != null) {
            getActionBar().hide();
        }
        super.setContentView(R.layout.sswl_base_layout);
        this.mRootLayout = (GlifLayout) findViewById(R.id.sswl_glif_root);
        setHeaderIcon(getResources().getDrawable(R.drawable.header_ic_transparent));
        ScrollView scrollView = this.mRootLayout.getScrollView();
        if (scrollView != null) {
            scrollView.setScrollIndicators(0);
            scrollView.setOnScrollChangeListener(
                    new View
                            .OnScrollChangeListener() { // from class:
                                                        // com.sec.android.secsetupwizardlib.SuwBaseActivity.1
                        @Override // android.view.View.OnScrollChangeListener
                        public final void onScrollChange(View view, int i, int i2, int i3, int i4) {
                            SuwBaseActivity.this.isScrollBottomReached();
                        }
                    });
        }
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity,
              // android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("isFirstTime", false);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity,
              // android.app.Activity
    public final void setContentView(int i) {
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.sswl_scroll_view);
        if (viewGroup != null) {
            viewGroup.removeAllViews();
        }
        LayoutInflater.from(this).inflate(i, viewGroup);
    }

    public final void setDescriptionText(int i) {
        this.mRootLayout.setDescriptionText(i);
    }

    public final void setHeaderIcon(Drawable drawable) {
        this.mRootLayout.setIcon(drawable);
    }

    public final void setHeaderTitle(int i) {
        this.mRootLayout.setHeaderText(i);
    }

    public final void setPrimaryActionButton(int i, View.OnClickListener onClickListener) {
        FooterButton footerButton = this.mPrimaryButton;
        if (footerButton == null) {
            this.mPrimaryButton =
                    new FooterButton(this.mContext.getString(i), onClickListener, 5, 0);
        } else {
            footerButton.setVisibility(0);
            this.mPrimaryButton.setText(this.mContext, i);
            this.mPrimaryButton.onClickListener = onClickListener;
        }
        ((FooterBarMixin) this.mRootLayout.getMixin(FooterBarMixin.class))
                .setPrimaryButton(this.mPrimaryButton);
    }

    public final void setSecondaryActionButton(int i, View.OnClickListener onClickListener) {
        if (this.mSecondaryButton == null) {
            this.mSecondaryButton =
                    new FooterButton(this.mContext.getString(i), onClickListener, 0, 0);
        }
        ((FooterBarMixin) this.mRootLayout.getMixin(FooterBarMixin.class))
                .setSecondaryButton(this.mSecondaryButton, false);
    }

    public final void setContentView(int i, boolean z) {
        View findViewById;
        this.mIsNeedScrollView = z;
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.sswl_scroll_view);
        if (!this.mIsNeedScrollView) {
            viewGroup = (ViewGroup) findViewById(R.id.sswl_layout_content);
            View findViewById2 = this.mRootLayout.findViewById(R.id.sud_landscape_content_area);
            if (findViewById2 != null
                    && (findViewById = this.mRootLayout.findViewById(R.id.sud_layout_content))
                            != null) {
                int paddingTop = findViewById.getPaddingTop();
                findViewById.setPadding(
                        findViewById.getPaddingStart(),
                        0,
                        findViewById.getPaddingEnd(),
                        findViewById.getPaddingBottom());
                findViewById2.setPadding(
                        findViewById2.getPaddingStart(),
                        paddingTop,
                        findViewById2.getPaddingEnd(),
                        findViewById2.getPaddingBottom());
            }
        }
        if (viewGroup != null) {
            viewGroup.removeAllViews();
        }
        LayoutInflater.from(this).inflate(i, viewGroup);
    }
}
