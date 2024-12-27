package com.samsung.android.settings.biometrics;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts$StartActivityForResult;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.SettingsBaseActivity;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.biometrics.face.FaceSettingsHelper;
import com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsUtils;
import com.samsung.android.settings.lockscreen.LockUtils;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.security.SecurityUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BiometricsDisclaimerActivity extends SettingsBaseActivity
        implements View.OnClickListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public RelativeLayout mBiometricsDisclaimerTotal;
    public ImageView mBiometricsIconView;
    public TextView mBiometricsTitleTextView;
    public LinearLayout mContinueButtonArea;
    public int mContinueEventId;
    public boolean mFromSettingsOption;
    public boolean mIsClickedLearnMore;
    public boolean mIsWaitConfirmLock;
    public FrameLayout mKnoxIcon;
    public ActivityResultRegistry.AnonymousClass2 mLeanMoreResult;
    public TextView mLearnMoreTextView;
    public TextView mMainGuideTextView;
    public RelativeLayout mMoreAboutGuideDesc;
    public RelativeLayout mMoreAboutGuideFace;
    public RelativeLayout mMoreAboutGuideFingerprint;
    public LinearLayout mMoreAboutSpinner;
    public ImageView mMoreAboutSpinnerImage;
    public TextView mMoreAboutSpinnerText;
    public int mRequestBiometricsType;
    public String mScreenId;
    public ActivityResultRegistry.AnonymousClass2 mSetScreenLockResult;
    public LinearLayout mTitleArea;
    public int mUserId;
    public BiometricsDisclaimerActivity mContext = null;
    public boolean mIsDescExpanded = true;
    public boolean mIsSecured = false;
    public boolean mIsFinished = false;
    public long mGkPwHandle = 0;

    public final void finishBiometricsDisclaimer(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i, "finishBiometricsDisclaimer : ", "BiometricsDisclaimerActivity");
        this.mIsFinished = true;
        Intent intent = new Intent();
        intent.putExtra("gk_pw_handle", this.mGkPwHandle);
        setResult(i, intent);
        finish();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // android.app.Activity
    public final void onBackPressed() {
        Log.d("BiometricsDisclaimerActivity", "onBackPressed");
        finishBiometricsDisclaimer(0);
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int id = view.getId();
        if (id == R.id.biometrics_disclaimer_more_about_face
                || id == R.id.biometrics_disclaimer_more_about_finger) {
            if (this.mMoreAboutGuideDesc.getVisibility() == 8) {
                setMoreAboutDesc(true);
            } else {
                setMoreAboutDesc(false);
            }
        }
    }

    public void onClickContinue(View view) {
        Log.d("BiometricsDisclaimerActivity", "onClickContinue");
        int i = this.mRequestBiometricsType;
        if (i == 256 || i == 1) {
            LoggingHelper.insertEventLogging(this.mScreenId, this.mContinueEventId);
        }
        if (this.mSetScreenLockResult == null || this.mIsSecured) {
            finishBiometricsDisclaimer(-1);
            return;
        }
        Intent intent = new Intent();
        intent.setClassName(getPackageName(), BiometricsSetScreenLockActivity.class.getName());
        intent.putExtra("BIOMETRICS_LOCK_TYPE", this.mRequestBiometricsType);
        this.mSetScreenLockResult.launch(intent);
    }

    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity,
              // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        setBiometricsDisclaimerLayout();
    }

    @Override // com.android.settings.core.SettingsBaseActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d("BiometricsDisclaimerActivity", "onCreate");
        this.mContext = this;
        Intent intent = getIntent();
        this.mRequestBiometricsType = intent.getIntExtra("BIOMETRICS_LOCK_TYPE", 0);
        Preference$$ExternalSyntheticOutline0.m(
                new StringBuilder("Request Biometrics Type : "),
                this.mRequestBiometricsType,
                "BiometricsDisclaimerActivity");
        if (this.mRequestBiometricsType == 0) {
            Log.e("BiometricsDisclaimerActivity", "Wrong Request");
            finishBiometricsDisclaimer(0);
            return;
        }
        Log.d("BiometricsDisclaimerActivity", "setTitleForVoiceAssistant");
        int i = this.mRequestBiometricsType;
        if (i == 1) {
            setTitle(
                    this.mContext.getString(
                            R.string.sec_biometrics_disclaimer_fingerprint_voice_title));
        } else if (i == 256) {
            setTitle(this.mContext.getString(R.string.sec_biometrics_disclaimer_face_voice_title));
        } else {
            Log.e("BiometricsDisclaimerActivity", "setTitleForVoiceAssistant : Wrong case");
        }
        this.mUserId = intent.getIntExtra("android.intent.extra.USER_ID", UserHandle.myUserId());
        Log.d("BiometricsDisclaimerActivity", "User ID : " + this.mUserId);
        this.mIsSecured = new LockPatternUtils(this.mContext).isSecure(this.mUserId);
        this.mFromSettingsOption = intent.getBooleanExtra("fromSettingsOption", false);
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                new StringBuilder("From SettingsOption : "),
                this.mFromSettingsOption,
                "BiometricsDisclaimerActivity");
        this.mGkPwHandle = intent.getLongExtra("gk_pw_handle", 0L);
        if (bundle != null) {
            this.mIsDescExpanded = bundle.getBoolean("is_desc_expanded");
            this.mGkPwHandle = bundle.getLong("gk_pw_handle", 0L);
            this.mIsWaitConfirmLock = bundle.getBoolean("IsWaitConfirmLock");
        }
        if (SecurityUtils.isNotAvailableBiometricsWithDexAndMultiWindow(
                this,
                BiometricsGenericHelper.getAppId(this.mRequestBiometricsType),
                "BiometricsDisclaimerActivity")) {
            finishBiometricsDisclaimer(0);
            return;
        }
        if (this.mFromSettingsOption) {
            setTheme(R.style.SecBiometricsCommonStyle);
        }
        final int i2 = 0;
        this.mLeanMoreResult =
                (ActivityResultRegistry.AnonymousClass2)
                        registerForActivityResult(
                                new ActivityResultContracts$StartActivityForResult(0),
                                new ActivityResultCallback(
                                        this) { // from class:
                                                // com.samsung.android.settings.biometrics.BiometricsDisclaimerActivity$$ExternalSyntheticLambda0
                                    public final /* synthetic */ BiometricsDisclaimerActivity f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // androidx.activity.result.ActivityResultCallback
                                    public final void onActivityResult(Object obj) {
                                        BiometricsDisclaimerActivity biometricsDisclaimerActivity =
                                                this.f$0;
                                        ActivityResult activityResult = (ActivityResult) obj;
                                        switch (i2) {
                                            case 0:
                                                biometricsDisclaimerActivity.mIsClickedLearnMore =
                                                        false;
                                                Intent intent2 = activityResult.mData;
                                                if (intent2 != null
                                                        && intent2.getBooleanExtra(
                                                                "biometrics_settings_destroy",
                                                                false)) {
                                                    biometricsDisclaimerActivity.setResult(
                                                            0, intent2);
                                                    biometricsDisclaimerActivity.finish();
                                                    break;
                                                }
                                                break;
                                            default:
                                                int i3 = BiometricsDisclaimerActivity.$r8$clinit;
                                                biometricsDisclaimerActivity.getClass();
                                                int i4 = activityResult.mResultCode;
                                                if (i4 == -1) {
                                                    biometricsDisclaimerActivity
                                                            .overridePendingTransition(
                                                                    R.anim.sud_slide_next_in,
                                                                    R.anim.sud_slide_next_out);
                                                }
                                                biometricsDisclaimerActivity
                                                        .finishBiometricsDisclaimer(i4);
                                                break;
                                        }
                                    }
                                });
        final int i3 = 1;
        this.mSetScreenLockResult =
                (ActivityResultRegistry.AnonymousClass2)
                        registerForActivityResult(
                                new ActivityResultContracts$StartActivityForResult(0),
                                new ActivityResultCallback(
                                        this) { // from class:
                                                // com.samsung.android.settings.biometrics.BiometricsDisclaimerActivity$$ExternalSyntheticLambda0
                                    public final /* synthetic */ BiometricsDisclaimerActivity f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // androidx.activity.result.ActivityResultCallback
                                    public final void onActivityResult(Object obj) {
                                        BiometricsDisclaimerActivity biometricsDisclaimerActivity =
                                                this.f$0;
                                        ActivityResult activityResult = (ActivityResult) obj;
                                        switch (i3) {
                                            case 0:
                                                biometricsDisclaimerActivity.mIsClickedLearnMore =
                                                        false;
                                                Intent intent2 = activityResult.mData;
                                                if (intent2 != null
                                                        && intent2.getBooleanExtra(
                                                                "biometrics_settings_destroy",
                                                                false)) {
                                                    biometricsDisclaimerActivity.setResult(
                                                            0, intent2);
                                                    biometricsDisclaimerActivity.finish();
                                                    break;
                                                }
                                                break;
                                            default:
                                                int i32 = BiometricsDisclaimerActivity.$r8$clinit;
                                                biometricsDisclaimerActivity.getClass();
                                                int i4 = activityResult.mResultCode;
                                                if (i4 == -1) {
                                                    biometricsDisclaimerActivity
                                                            .overridePendingTransition(
                                                                    R.anim.sud_slide_next_in,
                                                                    R.anim.sud_slide_next_out);
                                                }
                                                biometricsDisclaimerActivity
                                                        .finishBiometricsDisclaimer(i4);
                                                break;
                                        }
                                    }
                                });
        setBiometricsDisclaimerLayout();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity,
              // android.app.Activity
    public final void onDestroy() {
        Log.d("BiometricsDisclaimerActivity", "onDestroy");
        this.mIsFinished = false;
        super.onDestroy();
    }

    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.activity.ComponentActivity, android.app.Activity
    public final void onMultiWindowModeChanged(boolean z) {
        super.onMultiWindowModeChanged(z);
        StringBuilder m =
                RowView$$ExternalSyntheticOutline0.m("onMultiWindowModeChanged: ", ", ", z);
        m.append(isResumed());
        Log.d("BiometricsDisclaimerActivity", m.toString());
        if (LockUtils.isInMultiWindow(this)) {
            Toast.makeText(
                            this,
                            getString(R.string.sec_biometrics_common_not_use_multi_window_view),
                            0)
                    .show();
            finishBiometricsDisclaimer(0);
        }
    }

    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onPause() {
        Log.d("BiometricsDisclaimerActivity", "onPause");
        if (this.mIsWaitConfirmLock) {
            super.onPause();
            return;
        }
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                new StringBuilder("mIsFinished : "),
                this.mIsFinished,
                "BiometricsDisclaimerActivity");
        if (this.mIsSecured
                && !this.mIsFinished
                && !isChangingConfigurations()
                && !this.mIsClickedLearnMore) {
            Log.d(
                    "BiometricsDisclaimerActivity",
                    "onPause : Device is secured! So finish the activity.");
            Intent intent = new Intent();
            intent.putExtra("biometrics_settings_destroy", true);
            setResult(0, intent);
            finish();
        }
        super.onPause();
    }

    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        Log.d("BiometricsDisclaimerActivity", "onResume");
        if (!this.mFromSettingsOption) {
            BiometricsGenericHelper.removeSideMargin(this);
        }
        int i = this.mRequestBiometricsType;
        if (i == 256 || i == 1) {
            SALogging.insertSALog(this.mScreenId);
        }
    }

    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity,
              // android.app.Activity
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("is_desc_expanded", this.mIsDescExpanded);
        bundle.putLong("gk_pw_handle", this.mGkPwHandle);
        bundle.putBoolean("IsWaitConfirmLock", this.mIsWaitConfirmLock);
    }

    public final void setBiometricsDisclaimerLayout() {
        View findViewById;
        View findViewById2;
        View findViewById3;
        setContentView(R.layout.sec_biometrics_disclaimer_layout);
        this.mBiometricsDisclaimerTotal =
                (RelativeLayout) findViewById(R.id.biometrics_disclaimer_total);
        this.mTitleArea = (LinearLayout) findViewById(R.id.biometrics_disclaimer_title);
        this.mBiometricsIconView = (ImageView) findViewById(R.id.biometrics_disclaimer_icon_image);
        this.mBiometricsTitleTextView =
                (TextView) findViewById(R.id.biometrics_disclaimer_title_text);
        this.mMainGuideTextView =
                (TextView) findViewById(R.id.biometrics_disclaimer_main_guide_text);
        this.mMoreAboutGuideFace =
                (RelativeLayout) findViewById(R.id.biometrics_disclaimer_more_desc_face);
        this.mMoreAboutGuideFingerprint =
                (RelativeLayout) findViewById(R.id.biometrics_disclaimer_more_desc_fingerprint);
        this.mContinueButtonArea = (LinearLayout) findViewById(R.id.continueButtonContainer);
        this.mKnoxIcon = (FrameLayout) findViewById(R.id.knox_icon);
        int i = this.mRequestBiometricsType;
        if (i == 256) {
            this.mScreenId = BiometricsGenericHelper.getSaLogIdByDisplayType(this.mContext, 8409);
            this.mContinueEventId = 8410;
            this.mMoreAboutSpinner =
                    (LinearLayout) findViewById(R.id.biometrics_disclaimer_more_about_face);
            this.mMoreAboutSpinnerText =
                    (TextView) findViewById(R.id.sec_biometrics_disclaimer_more_about_text_face);
            this.mMoreAboutSpinnerImage =
                    (ImageView) findViewById(R.id.biometrics_disclaimer_more_about_image_face);
            this.mMoreAboutGuideDesc =
                    (RelativeLayout) findViewById(R.id.biometrics_disclaimer_more_desc_layout_face);
        } else if (i == 1) {
            this.mScreenId = BiometricsGenericHelper.getSaLogIdByDisplayType(this.mContext, 8247);
            this.mContinueEventId = 8248;
            this.mMoreAboutSpinner =
                    (LinearLayout) findViewById(R.id.biometrics_disclaimer_more_about_finger);
            this.mMoreAboutSpinnerText =
                    (TextView) findViewById(R.id.sec_biometrics_disclaimer_more_about_text_finger);
            this.mMoreAboutSpinnerImage =
                    (ImageView) findViewById(R.id.biometrics_disclaimer_more_about_image_finger);
            this.mMoreAboutGuideDesc =
                    (RelativeLayout)
                            findViewById(R.id.biometrics_disclaimer_more_desc_layout_fingerprint);
        }
        LinearLayout linearLayout = this.mMoreAboutSpinner;
        if (linearLayout != null) {
            linearLayout.setOnClickListener(this);
        }
        TextView textView = this.mMoreAboutSpinnerText;
        if (textView != null) {
            textView.setSelected(true);
        }
        if (!this.mFromSettingsOption) {
            Log.d("BiometricsDisclaimerActivity", "Hide the action bar");
            hideAppBar();
        }
        try {
            Button button = (Button) findViewById(R.id.continue_button);
            if (button != null) {
                button.semSetButtonShapeEnabled(true);
                Utils.setMaxFontScale$1(this, button);
            }
            LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.disclaimer_layout);
            View findViewById4 = findViewById(R.id.biometrics_disclaimer_body);
            if (this.mFromSettingsOption) {
                if (linearLayout2 != null) {
                    linearLayout2.setLayoutParams(new LinearLayout.LayoutParams(0, -1, 1.0f));
                }
                this.mTitleArea.setVisibility(8);
                this.mContinueButtonArea.setVisibility(8);
                View findViewById5 = findViewById(R.id.title_margin_top);
                if (findViewById5 != null) {
                    findViewById5.setVisibility(8);
                }
                View findViewById6 = findViewById(R.id.title_margin_bottom);
                if (findViewById6 != null) {
                    findViewById6.setVisibility(8);
                }
                int dimension =
                        (int)
                                getResources()
                                        .getDimension(
                                                R.dimen.sec_biometrics_disclaimer_option_padding);
                if (findViewById4 != null) {
                    findViewById4.setPadding(dimension, 0, dimension, 0);
                }
                if (getResources().getConfiguration().semIsPopOver()) {
                    this.mBiometricsDisclaimerTotal.setPadding(0, dimension, 0, 0);
                    this.mBiometricsDisclaimerTotal.semSetRoundedCorners(3);
                    this.mBiometricsDisclaimerTotal.semSetRoundedCornerColor(
                            3, getColor(R.color.sec_biometrics_guide_common_round_background));
                    this.mBiometricsDisclaimerTotal.setBackgroundColor(
                            getColor(R.color.sec_biometrics_guide_common_guide_background));
                }
                this.mMoreAboutGuideDesc.setPadding(
                        (int)
                                getResources()
                                        .getDimension(
                                                R.dimen
                                                        .sec_biometrics_disclaimer_option_sub_padding),
                        this.mMoreAboutGuideDesc.getPaddingTop(),
                        0,
                        0);
                this.mBiometricsDisclaimerTotal.setLayoutParams(
                        new LinearLayout.LayoutParams(-1, 0, 1.0f));
                this.mMainGuideTextView.setTextColor(
                        this.mContext
                                .getResources()
                                .getColor(
                                        R.color.sec_biometrics_choose_lock_header_description_color,
                                        null));
                this.mMoreAboutSpinner.setVisibility(8);
            } else if (findViewById4 != null) {
                int rotation = getDisplay().getRotation();
                int dimension2 =
                        (int)
                                getResources()
                                        .getDimension(
                                                R.dimen
                                                        .sec_biometrics_disclaimer_land_padding_start);
                int dimension3 =
                        (int)
                                getResources()
                                        .getDimension(
                                                R.dimen.sec_biometrics_disclaimer_land_padding_end);
                if (rotation == 1) {
                    findViewById4.setPadding(dimension2, 0, dimension3, 0);
                } else if (rotation == 3) {
                    findViewById4.setPadding(dimension3, 0, dimension2, 0);
                }
            }
            if (this.mFromSettingsOption) {
                this.mKnoxIcon.setVisibility(8);
            }
            int i2 = this.mRequestBiometricsType;
            if (i2 == 1) {
                this.mLearnMoreTextView =
                        (TextView) findViewById(R.id.biometrics_disclaimer_finger_learn_more_text);
                this.mMoreAboutGuideFace.setVisibility(8);
                ImageView imageView = this.mBiometricsIconView;
                if (imageView != null) {
                    imageView.setImageResource(R.drawable.sec_ic_biometric_type_fingerprint);
                }
                this.mBiometricsTitleTextView.setText(R.string.sec_fingerprint);
                String string =
                        this.mContext.getString(
                                R.string.sec_biometrics_disclaimer_main_guide_fingerprint);
                if (!this.mFromSettingsOption) {
                    string =
                            string
                                    + " "
                                    + this.mContext.getString(
                                            R.string
                                                    .sec_biometrics_disclaimer_main_guide_fingerprint_secured_by_knox);
                }
                this.mMainGuideTextView.setText(string);
                boolean z = FingerprintSettingsUtils.SUB_DISPLAY_IS_LARGE_SCREEN;
                setInDisplayFingerprintDisclaimerTips();
                if (Rune.isChinaModel()
                        && (findViewById =
                                        findViewById(R.id.biometrics_disclaimer_more_finger_info))
                                != null) {
                    findViewById.setVisibility(0);
                }
            } else if (i2 != 256) {
                Log.e("BiometricsDisclaimerActivity", "Wrong case");
            } else {
                this.mLearnMoreTextView =
                        (TextView) findViewById(R.id.biometrics_disclaimer_face_learn_more_text);
                this.mMoreAboutGuideFingerprint.setVisibility(8);
                ImageView imageView2 = this.mBiometricsIconView;
                if (imageView2 != null) {
                    imageView2.setImageResource(R.drawable.sec_ic_biometric_type_face);
                    Log.d("BiometricsDisclaimerActivity", "setResource");
                }
                this.mBiometricsTitleTextView.setText(
                        R.string.sec_biometrics_disclaimer_title_face);
                String string2 =
                        this.mContext.getString(R.string.sec_biometrics_disclaimer_main_guide_face);
                if (!this.mFromSettingsOption) {
                    string2 =
                            string2
                                    + " "
                                    + this.mContext.getString(
                                            R.string
                                                    .sec_biometrics_disclaimer_main_guide_face_secured_by_knox);
                }
                this.mMainGuideTextView.setText(string2);
                if (FaceSettingsHelper.isSupportBioFaceRecognizeWithMask(this)
                        && (findViewById3 =
                                        findViewById(R.id.biometrics_disclaimer_more_desc_face_4))
                                != null) {
                    findViewById3.setVisibility(0);
                }
                if (Rune.isChinaModel()
                        && (findViewById2 = findViewById(R.id.biometrics_disclaimer_more_face_info))
                                != null) {
                    findViewById2.setVisibility(0);
                }
            }
            if (this.mLearnMoreTextView != null) {
                BiometricsGenericHelper.makeLinkText(
                        this,
                        this.mLearnMoreTextView,
                        getString(R.string.sec_biometrics_disclaimer_learn_more),
                        new ClickableSpan() { // from class:
                            // com.samsung.android.settings.biometrics.BiometricsDisclaimerActivity.1
                            @Override // android.text.style.ClickableSpan
                            public final void onClick(View view) {
                                BiometricsDisclaimerActivity.this.mIsClickedLearnMore = true;
                                view.playSoundEffect(0);
                                Intent intent = new Intent();
                                intent.setClassName(
                                        BiometricsDisclaimerActivity.this.getPackageName(),
                                        "com.samsung.android.settings.biometrics.BiometricsCommonSecurityNoticeActivity");
                                ActivityResultRegistry.AnonymousClass2 anonymousClass2 =
                                        BiometricsDisclaimerActivity.this.mLeanMoreResult;
                                if (anonymousClass2 != null) {
                                    anonymousClass2.launch(intent);
                                }
                            }
                        });
            }
            if (!this.mFromSettingsOption && !this.mIsDescExpanded) {
                setMoreAboutDesc(false);
                return;
            }
            setMoreAboutDesc(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void setInDisplayFingerprintDisclaimerTips() {
        View findViewById = findViewById(R.id.biometrics_disclaimer_more_desc_fingerprint_1);
        View findViewById2 = findViewById(R.id.biometrics_disclaimer_more_desc_fingerprint_2);
        if (findViewById == null || findViewById2 == null) {
            return;
        }
        findViewById.setVisibility(0);
        findViewById2.setVisibility(0);
        try {
            Resources resourcesForApplication =
                    getPackageManager()
                            .getResourcesForApplication(
                                    "com.samsung.android.biometrics.app.setting");
            TextView textView =
                    (TextView)
                            findViewById(
                                    R.id.biometrics_disclaimer_more_desc_fingerprint_1_textView);
            TextView textView2 =
                    (TextView)
                            findViewById(
                                    R.id.biometrics_disclaimer_more_desc_fingerprint_2_textView);
            textView.setText(
                    resourcesForApplication.getString(
                            resourcesForApplication.getIdentifier(
                                    "fingerprint_dialog_make_sure_that_you_dont_use",
                                    "string",
                                    "com.samsung.android.biometrics.app.setting")));
            textView2.setText(
                    resourcesForApplication.getString(
                            resourcesForApplication.getIdentifier(
                                    "fingerprint_dialog_if_you_use_a_screen_protector",
                                    "string",
                                    "com.samsung.android.biometrics.app.setting")));
        } catch (PackageManager.NameNotFoundException | Resources.NotFoundException e) {
            Log.w(
                    "BiometricsDisclaimerActivity",
                    "setInDisplayFingerprintDisclaimerTips : exception = " + e);
        }
    }

    public final void setMoreAboutDesc(boolean z) {
        int i;
        ImageView imageView = this.mMoreAboutSpinnerImage;
        if (imageView == null || this.mMoreAboutGuideDesc == null) {
            return;
        }
        this.mIsDescExpanded = z;
        if (z) {
            imageView.setScaleY(-1.0f);
            this.mMoreAboutGuideDesc.setVisibility(0);
            i = R.string.sec_biometrics_disclaimer_expanded;
        } else {
            imageView.setScaleY(1.0f);
            this.mMoreAboutGuideDesc.setVisibility(8);
            i = R.string.sec_biometrics_disclaimer_collapsed;
        }
        TextView textView = this.mMoreAboutSpinnerText;
        if (textView == null || this.mMoreAboutSpinner == null) {
            return;
        }
        StringBuilder sb = new StringBuilder(textView.getText());
        sb.append(", ");
        sb.append(getString(i));
        sb.append(", ");
        sb.append(getString(R.string.button_tts));
        this.mMoreAboutSpinner.setContentDescription(sb);
        this.mMoreAboutSpinner.announceForAccessibility(getString(i));
    }
}
