package com.samsung.android.settings.biometrics;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.glance.session.SessionManagerImpl$scope$1$$ExternalSyntheticOutline0;
import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.SettingsBaseActivity;

import java.io.IOException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BiometricsRotationGuide extends SettingsBaseActivity
        implements TextureView.SurfaceTextureListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mFromSetupWizard;
    public MediaPlayer mMediaPlayer;
    public int mOrientation;
    public int mRequestBiometricsType;
    public int mScreenWidth;
    public Surface mSurface;
    public BiometricsRotationGuide mContext = null;
    public boolean mIsFinished = false;
    public TextureView mGuideVideoView = null;
    public FrameLayout mGuideVideoContainer = null;
    public ScrollView mGuideScrollView = null;
    public Button mContinueButton = null;
    public LinearLayout mContinueButtonArea = null;
    public RelativeLayout mSetupWizardButtonContainer = null;
    public WindowManager mWindowManager = null;

    public final void finishRotationGuide(int i) {
        Log.d("BiometricsRotationGuide", "finishRotationGuide");
        this.mIsFinished = true;
        setResult(i);
        finish();
    }

    public final void initBiometricsRotationGuideGUI() {
        Log.d("BiometricsRotationGuide", "initBiometricsRotationGuideGUI");
        int i = this.mOrientation;
        boolean z = true;
        if (i == 1 || i == 3) {
            Log.d("BiometricsRotationGuide", "isPortrait : false");
            z = false;
        } else {
            Log.d("BiometricsRotationGuide", "isPortrait : true");
        }
        double screenWidth = BiometricsGenericHelper.getScreenWidth(this.mContext);
        double fullScreenHeight = BiometricsGenericHelper.getFullScreenHeight(this.mContext);
        if (this.mFromSetupWizard) {
            LinearLayout linearLayout = this.mContinueButtonArea;
            if (linearLayout != null) {
                linearLayout.setVisibility(8);
            }
        } else {
            RelativeLayout relativeLayout = this.mSetupWizardButtonContainer;
            if (relativeLayout != null) {
                relativeLayout.setVisibility(8);
            }
        }
        FrameLayout frameLayout = this.mGuideVideoContainer;
        if (frameLayout != null) {
            frameLayout.semSetRoundedCorners(15);
            this.mGuideVideoContainer.semSetRoundedCornerColor(
                    15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        }
        FrameLayout frameLayout2 = this.mGuideVideoContainer;
        if (frameLayout2 != null) {
            RelativeLayout.LayoutParams layoutParams =
                    (RelativeLayout.LayoutParams) frameLayout2.getLayoutParams();
            BiometricsRotationGuide biometricsRotationGuide = this.mContext;
            int screenWidth2 =
                    (int)
                            ((z
                                            ? BiometricsGenericHelper.getScreenWidth(
                                                    biometricsRotationGuide)
                                            : BiometricsGenericHelper.getFullScreenHeight(
                                                    biometricsRotationGuide))
                                    * 0.6d);
            BiometricsRotationGuide biometricsRotationGuide2 = this.mContext;
            int fullScreenHeight2 =
                    (int)
                            ((z
                                            ? BiometricsGenericHelper.getFullScreenHeight(
                                                    biometricsRotationGuide2)
                                            : BiometricsGenericHelper.getScreenWidth(
                                                    biometricsRotationGuide2))
                                    * 0.357d);
            if (z) {
                int i2 = (int) (fullScreenHeight * 0.18d);
                ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                        i2, "marginTop : ", "BiometricsRotationGuide");
                layoutParams.topMargin = i2;
            } else {
                screenWidth2 = (int) (screenWidth2 * 0.75d);
                fullScreenHeight2 = (int) (fullScreenHeight2 * 0.75d);
                int fullScreenHeight3 =
                        ((BiometricsGenericHelper.getFullScreenHeight(this.mContext) / 2)
                                        - fullScreenHeight2)
                                / 2;
                ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                        fullScreenHeight3, "margin : ", "BiometricsRotationGuide");
                layoutParams.topMargin = fullScreenHeight3;
                layoutParams.bottomMargin = fullScreenHeight3;
            }
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    "VideoSize - width : ",
                    ", height : ",
                    screenWidth2,
                    fullScreenHeight2,
                    "BiometricsRotationGuide");
            layoutParams.height = fullScreenHeight2;
            layoutParams.width = screenWidth2;
            this.mGuideVideoContainer.setLayoutParams(layoutParams);
        }
        ScrollView scrollView = this.mGuideScrollView;
        if (scrollView != null) {
            int i3 = (int) (screenWidth * (z ? 0.12d : 0.20625d));
            scrollView.setPadding(i3, 0, i3, 0);
        }
        Log.d("BiometricsRotationGuide", "setButtonSize");
        this.mScreenWidth = getWindowManager().getDefaultDisplay().getWidth();
        Preference$$ExternalSyntheticOutline0.m(
                new StringBuilder("mScreenWidth : "), this.mScreenWidth, "BiometricsRotationGuide");
        Button button = this.mContinueButton;
        if (button != null) {
            button.setMinimumWidth(
                    (int)
                            this.mContext
                                    .getResources()
                                    .getDimension(
                                            R.dimen
                                                    .sec_biometrics_guide_common_continue_button_width));
            this.mContinueButton.setMaxWidth((int) (this.mScreenWidth * 0.6d));
        }
        LinearLayout linearLayout2 = this.mContinueButtonArea;
        if (linearLayout2 != null) {
            LinearLayout.LayoutParams layoutParams2 =
                    (LinearLayout.LayoutParams) linearLayout2.getLayoutParams();
            layoutParams2.topMargin =
                    (int)
                            this.mContext
                                    .getResources()
                                    .getDimension(
                                            R.dimen
                                                    .sec_biometrics_guide_common_continue_button_area_margin_top);
            layoutParams2.bottomMargin =
                    (int)
                            this.mContext
                                    .getResources()
                                    .getDimension(
                                            R.dimen
                                                    .sec_biometrics_guide_common_continue_button_area_margin_bottom);
            this.mContinueButtonArea.setLayoutParams(layoutParams2);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // android.app.Activity
    public final void onBackPressed() {
        Log.d("BiometricsRotationGuide", "onBackPressed");
        finishRotationGuide(0);
    }

    public void onClickContinue(View view) {
        Log.d("BiometricsRotationGuide", "onClickContinue");
        finishRotationGuide(-1);
    }

    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity,
              // android.app.Activity, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (BiometricsGenericHelper.isBlockedInMultiWindowMode(this, configuration)) {
            finish();
            return;
        }
        int rotation = this.mWindowManager.getDefaultDisplay().getRotation();
        Log.d(
                "BiometricsRotationGuide",
                "onConfigurationChanged : past = "
                        + this.mOrientation
                        + " , current = "
                        + rotation);
        if (this.mOrientation != rotation) {
            BiometricsGenericHelper.isLandscapeDefaultModel();
            if (rotation == 0) {
                Log.d("BiometricsRotationGuide", "Rotation changed! Finish the activity");
                finishRotationGuide(-1);
            } else {
                this.mOrientation = rotation;
                initBiometricsRotationGuideGUI();
                Log.d("BiometricsRotationGuide", "Change the guide video!");
                setMediaPlayer(this.mGuideVideoView.getSurfaceTexture());
            }
        }
    }

    @Override // com.android.settings.core.SettingsBaseActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d("BiometricsRotationGuide", "onCreate");
        this.mContext = this;
        this.mOrientation =
                ((WindowManager) getSystemService("window")).getDefaultDisplay().getRotation();
        Preference$$ExternalSyntheticOutline0.m(
                new StringBuilder("rotation : "), this.mOrientation, "BiometricsRotationGuide");
        int i = this.mOrientation;
        BiometricsGenericHelper.isLandscapeDefaultModel();
        if (i == 0) {
            Log.d("BiometricsRotationGuide", "The right direction! Finish the rotation guide.");
            finishRotationGuide(-1);
            return;
        }
        this.mFromSetupWizard = getIntent().getBooleanExtra("fromSetupWizard", false);
        this.mRequestBiometricsType = getIntent().getIntExtra("BIOMETRICS_LOCK_TYPE", 0);
        StringBuilder m =
                MainClearConfirm$$ExternalSyntheticOutline0.m(
                        new StringBuilder("mFromSetupWizard : "),
                        this.mFromSetupWizard,
                        "BiometricsRotationGuide",
                        "mRequestBiometricsType : ");
        m.append(this.mRequestBiometricsType);
        Log.d("BiometricsRotationGuide", m.toString());
        int windowingMode =
                getResources().getConfiguration().windowConfiguration.getWindowingMode();
        if (windowingMode == 5 || windowingMode == 6) {
            Toast.makeText(this, getString(R.string.sec_biometrics_fullscreen_register_face), 0)
                    .show();
            finishRotationGuide(0);
            return;
        }
        setContentView(R.layout.sec_biometrics_rotation_guide);
        this.mGuideVideoContainer =
                (FrameLayout) findViewById(R.id.biometrics_rotation_guide_view_container);
        this.mGuideVideoView =
                (TextureView) findViewById(R.id.biometrics_rotation_guide_view_pager);
        this.mGuideScrollView = (ScrollView) findViewById(R.id.guide_text_scroll);
        this.mContinueButton = (Button) findViewById(R.id.continue_button);
        this.mContinueButtonArea = (LinearLayout) findViewById(R.id.continueButtonContainer);
        this.mSetupWizardButtonContainer =
                (RelativeLayout) findViewById(R.id.setupwizard_bottom_button_bar);
        TextureView textureView = this.mGuideVideoView;
        if (textureView != null && this.mGuideVideoContainer != null) {
            textureView.setSurfaceTextureListener(this);
        }
        if (this.mWindowManager == null) {
            this.mWindowManager = (WindowManager) getSystemService("window");
        }
        Log.d("BiometricsRotationGuide", "onCreate : hide the action bar");
        hideAppBar();
        if (Utils.isTablet()) {
            BiometricsGenericHelper.removeSideMargin(this);
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity,
              // android.app.Activity
    public final void onDestroy() {
        Log.i("BiometricsRotationGuide", "onDestroy");
        TextureView textureView = this.mGuideVideoView;
        if (textureView != null) {
            textureView.destroyDrawingCache();
            this.mGuideVideoView.setSurfaceTextureListener(null);
            this.mGuideVideoView = null;
        }
        this.mIsFinished = false;
        super.onDestroy();
    }

    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onPause() {
        Log.d("BiometricsRotationGuide", "onPause");
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
        }
        Surface surface = this.mSurface;
        if (surface != null) {
            surface.release();
            this.mSurface = null;
        }
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                new StringBuilder("Finish called : "), this.mIsFinished, "BiometricsRotationGuide");
        if (!this.mIsFinished && !hasWindowFocus()) {
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
        Log.d("BiometricsRotationGuide", "onResume");
        initBiometricsRotationGuideGUI();
        TextureView textureView = this.mGuideVideoView;
        if (textureView == null || !textureView.isAvailable()) {
            return;
        }
        setMediaPlayer(this.mGuideVideoView.getSurfaceTexture());
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public final void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        Log.d("BiometricsRotationGuide", "onSurfaceTextureAvailable: setMediaPlayer");
        setMediaPlayer(surfaceTexture);
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public final boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return false;
    }

    public final void setMediaPlayer(SurfaceTexture surfaceTexture) {
        String str;
        int i = this.mOrientation;
        if (i == 0) {
            str = "/raw/rotation_guide_help_horizontal_0";
        } else if (i == 1) {
            str = "/raw/rotation_guide_help_90";
        } else if (i == 2) {
            BiometricsGenericHelper.isLandscapeDefaultModel();
            str = "/raw/rotation_guide_help_180";
        } else if (i != 3) {
            str = null;
        } else {
            BiometricsGenericHelper.isLandscapeDefaultModel();
            str = "/raw/rotation_guide_help_270";
        }
        Uri parse =
                Uri.parse("android.resource://com.samsung.android.biometrics.app.setting" + str);
        this.mSurface = new Surface(surfaceTexture);
        if (parse != null) {
            try {
                MediaPlayer mediaPlayer = this.mMediaPlayer;
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    this.mMediaPlayer.release();
                    this.mMediaPlayer = null;
                }
                MediaPlayer mediaPlayer2 = new MediaPlayer();
                this.mMediaPlayer = mediaPlayer2;
                mediaPlayer2.setDataSource(this, parse);
                this.mMediaPlayer.setSurface(this.mSurface);
                this.mMediaPlayer.setLooping(true);
                this.mMediaPlayer.prepareAsync();
                this.mMediaPlayer.setOnPreparedListener(
                        new MediaPlayer
                                .OnPreparedListener() { // from class:
                                                        // com.samsung.android.settings.biometrics.BiometricsRotationGuide$$ExternalSyntheticLambda0
                            @Override // android.media.MediaPlayer.OnPreparedListener
                            public final void onPrepared(MediaPlayer mediaPlayer3) {
                                BiometricsRotationGuide biometricsRotationGuide =
                                        BiometricsRotationGuide.this;
                                int i2 = BiometricsRotationGuide.$r8$clinit;
                                biometricsRotationGuide.getClass();
                                Log.d(
                                        "BiometricsRotationGuide",
                                        "onSurfaceTextureAvailable : onPrepared");
                                if (mediaPlayer3 != null) {
                                    mediaPlayer3.start();
                                } else {
                                    Log.w(
                                            "BiometricsRotationGuide",
                                            "onPrepared : mediaPlayer == null");
                                    biometricsRotationGuide.finishRotationGuide(0);
                                }
                            }
                        });
            } catch (IOException
                    | IllegalArgumentException
                    | IllegalStateException
                    | SecurityException e) {
                SessionManagerImpl$scope$1$$ExternalSyntheticOutline0.m(
                        "Exception : ", e, "BiometricsRotationGuide");
            }
        }
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public final void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {}

    @Override // android.view.TextureView.SurfaceTextureListener
    public final void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {}
}
