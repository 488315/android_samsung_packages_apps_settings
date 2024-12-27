package com.samsung.android.settings.lockscreen.controller;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.compose.foundation.text.CoreTextFieldKt$CoreTextField$semanticsModifier$1$1$3$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.lockscreen.NotificationTemplateView;
import com.samsung.android.settings.lockscreen.SecConceptBehavior;
import com.samsung.android.settings.lockscreen.SecConceptControllerBehavior;
import com.samsung.android.view.SemWindowManager;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class NotiPreviewPreferenceController extends BasePreferenceController
        implements SecConceptControllerBehavior {
    static final String KEY = "noti_preview";
    private static final String TAG = "NotiPreviewPreferenceController";
    public static final int VIEW_STYLE_DETAILED = 0;
    public static final int VIEW_STYLE_ICON = 1;
    public static final int VIEW_STYLE_ICON_TYPO = 2;
    SecConceptBehavior mContextDashBoard;
    LockPatternUtils mLockPatternUtils;
    private FrameLayout mNotiCardLayout;
    private NotificationTemplateView mNotiCardView;
    private ImageView mNotificationPreview;
    private LinearLayout mNotificationView;
    private int mOrientation;
    LayoutPreference mPreference;
    private SemWindowManager mSemWindowManager;
    private int mUiMode;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class KeyguardWallpaperLoadTask extends AsyncTask {
        public KeyguardWallpaperLoadTask() {}

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            StringBuilder sb = new StringBuilder("content://com.android.systemui.keyguard.image");
            sb.append(
                    NotiPreviewPreferenceController.this.isLandscape()
                            ? "/landscape?"
                            : "/portrait?");
            sb.append("type=wallpaper");
            Bitmap bitmap = null;
            try {
                Bitmap decodeBitmap =
                        ImageDecoder.decodeBitmap(
                                ImageDecoder.createSource(
                                        ((AbstractPreferenceController)
                                                        NotiPreviewPreferenceController.this)
                                                .mContext.getContentResolver(),
                                        Uri.parse(sb.toString())));
                if (decodeBitmap == null) {
                    Log.d(
                            NotiPreviewPreferenceController.TAG,
                            "Null wallpaper recieved from KeyguardProvider");
                } else {
                    bitmap = NotiPreviewPreferenceController.this.cropBitmap(decodeBitmap);
                }
            } catch (Exception unused) {
                Log.d(
                        NotiPreviewPreferenceController.TAG,
                        "Caught Exception while fetching wallpaper from KeyguardProvider");
            }
            return bitmap;
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            Bitmap bitmap = (Bitmap) obj;
            super.onPostExecute(bitmap);
            Log.d(
                    NotiPreviewPreferenceController.TAG,
                    "set lockscreen notification preview wallpaper");
            NotiPreviewPreferenceController.this.mNotificationPreview.setImageBitmap(bitmap);
            NotiPreviewPreferenceController.this.mNotificationPreview.setScaleType(
                    ImageView.ScaleType.FIT_XY);
            NotiPreviewPreferenceController.this.mNotificationPreview.setClipToOutline(true);
        }

        @Override // android.os.AsyncTask
        public final void onPreExecute() {
            super.onPreExecute();
        }
    }

    public NotiPreviewPreferenceController(Context context, SecConceptBehavior secConceptBehavior) {
        super(context, KEY);
        this.mContextDashBoard = secConceptBehavior;
        this.mOrientation = 0;
        this.mUiMode = context.getResources().getConfiguration().uiMode;
        this.mLockPatternUtils = new LockPatternUtils(context);
    }

    private void addNotificationPreView() {
        if (this.mNotiCardView == null || this.mNotiCardLayout == null) {
            this.mNotiCardView = new NotificationTemplateView(this.mContext);
            LayoutPreference layoutPreference = this.mPreference;
            if (layoutPreference != null) {
                this.mNotiCardLayout =
                        (FrameLayout)
                                layoutPreference.mRootView.findViewById(
                                        R.id.notification_preview_layout);
            }
        }
        LayoutPreference layoutPreference2 = this.mPreference;
        if (layoutPreference2 != null) {
            ImageView imageView =
                    (ImageView) layoutPreference2.mRootView.findViewById(R.id.notification_preview);
            this.mNotificationPreview = imageView;
            imageView.setImageBitmap(getLockscreenWallpaperImg());
            this.mNotificationPreview.setScaleType(ImageView.ScaleType.FIT_XY);
            this.mNotificationPreview.setClipToOutline(true);
            this.mNotificationView =
                    (LinearLayout) this.mPreference.mRootView.findViewById(R.id.notification_view);
            setNotificationViewParams();
            this.mNotificationView.addView(this.mNotiCardView);
        }
        refreshNotiCardLayout();
        updateNotiCardOpacity();
    }

    private Bitmap centerCropBitmap(Bitmap bitmap, int i, int i2) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width < i && height < i2) {
            return bitmap;
        }
        int i3 = width > i ? (width - i) / 2 : 0;
        int i4 = height > i2 ? (height - i2) / 2 : 0;
        if (i > width) {
            i = width;
        }
        if (i2 > height) {
            i2 = height;
        }
        return Bitmap.createBitmap(bitmap, i3, i4, i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bitmap cropBitmap(Bitmap bitmap) {
        float f;
        float f2;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Point point = new Point();
        this.mSemWindowManager.getUserDisplaySize(point);
        int i = point.x;
        int i2 = point.y;
        Log.d(
                TAG,
                CoreTextFieldKt$CoreTextField$semanticsModifier$1$1$3$$ExternalSyntheticOutline0.m(
                        "original width [", "], height [", width, height, "]"));
        StringBuilder sb = new StringBuilder("display width [");
        sb.append(i);
        sb.append("], height [");
        RecyclerView$$ExternalSyntheticOutline0.m(sb, i2, "]", TAG);
        if (!isLandscape()) {
            if (i2 > i) {
                f = i2;
                f2 = height;
            } else {
                f = i;
                f2 = width;
            }
            float f3 = f / f2;
            int max = Math.max((int) (width * f3), i);
            int max2 = Math.max((int) (height * f3), i2);
            Log.d(
                    TAG,
                    CoreTextFieldKt$CoreTextField$semanticsModifier$1$1$3$$ExternalSyntheticOutline0
                            .m("rate width [", "], height [", max, max2, "]"));
            if (width < max || height < max2) {
                bitmap = Bitmap.createScaledBitmap(bitmap, max, max2, true);
                Log.d(
                        TAG,
                        "scaled bitmap width ["
                                + bitmap.getWidth()
                                + "], height ["
                                + bitmap.getHeight()
                                + "]");
            }
            bitmap = centerCropBitmap(bitmap, i, i2);
            Log.d(
                    TAG,
                    "center crop bitmap width ["
                            + bitmap.getWidth()
                            + "], height ["
                            + bitmap.getHeight()
                            + "]");
        }
        int i3 = (int) (i2 * 0.2d);
        int dimensionPixelSize =
                this.mContext
                        .getResources()
                        .getDimensionPixelSize(
                                R.dimen.sec_lock_setting_notification_preview_layout_height);
        if (isLandscape()) {
            bitmap = Bitmap.createScaledBitmap(bitmap, i2, i, true);
            i3 = (int) (i * 0.2d);
            i = i2;
        }
        Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, i3, i, dimensionPixelSize);
        Log.d(
                TAG,
                "result bitmap width ["
                        + createBitmap.getWidth()
                        + "], height ["
                        + createBitmap.getHeight()
                        + "]");
        return createBitmap;
    }

    private Bitmap getLockscreenWallpaperImg() {
        new KeyguardWallpaperLoadTask().execute(new Void[0]);
        return null;
    }

    private int getNotificationViewWidth() {
        float min;
        float f;
        int screenWidth = getScreenWidth(this.mContext);
        int screenHeight = getScreenHeight(this.mContext);
        Context context = this.mContext;
        boolean z = context.getResources().getConfiguration().orientation == 1;
        if (Utils.isTablet()) {
            min = Math.min(screenWidth, screenHeight);
            f = 0.735f;
        } else {
            String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            if (z) {
                return Math.min(screenWidth, screenHeight);
            }
            min = Math.max(screenWidth, screenHeight);
            f = 0.7f;
        }
        return (int) (min * f);
    }

    private int getScreenHeight(Context context) {
        return context.getResources().getConfiguration().windowConfiguration.getBounds().height();
    }

    private int getScreenWidth(Context context) {
        return context.getResources().getConfiguration().windowConfiguration.getBounds().width();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isLandscape() {
        try {
            return this.mContext.getResources().getConfiguration().orientation == 2;
        } catch (Exception unused) {
            Log.secE(TAG, "landscape check is failed.");
            return false;
        }
    }

    private void refreshNotiCardLayout() {
        Context context = this.mContext;
        if (context == null || this.mNotiCardLayout == null) {
            return;
        }
        boolean z =
                Settings.System.getInt(
                                context.getContentResolver(), "white_lockscreen_wallpaper", 0)
                        == 1;
        Settings.System.getStringForUser(
                this.mContext.getContentResolver(), "current_sec_active_themepackage", -2);
        Settings.System.getIntForUser(
                this.mContext.getContentResolver(), "lockscreen_wallpaper_transparent", 0, -2);
        NotificationTemplateView notificationTemplateView = this.mNotiCardView;
        notificationTemplateView.getClass();
        notificationTemplateView.mCurrentBehavior.getClass();
        NotificationTemplateView notificationTemplateView2 = this.mNotiCardView;
        notificationTemplateView2.getClass();
        Log.d("NTV", " White Wallpaper = " + z);
        notificationTemplateView2.mIsWhiteWallpaper = z;
        notificationTemplateView2.mCurrentBehavior.mIsWhiteWallpaper = z;
        if (notificationTemplateView2.mIsInOpenTheme
                || notificationTemplateView2.mIsQuickColoring) {
            return;
        }
        notificationTemplateView2.mDimmedBGView.setBackgroundTintList(
                ColorStateList.valueOf(notificationTemplateView2.getDimmedBgColor()));
        notificationTemplateView2.mStackScrollerBGView.setBackgroundTintList(
                ColorStateList.valueOf(notificationTemplateView2.getStackBGColor()));
        notificationTemplateView2.setViewOpacity(notificationTemplateView2.mViewOpacity, true);
    }

    private void setNotificationViewParams() {
        FrameLayout.LayoutParams layoutParams =
                (FrameLayout.LayoutParams) this.mNotificationView.getLayoutParams();
        layoutParams.width =
                getNotificationViewWidth()
                        - (this.mContext
                                        .getResources()
                                        .getDimensionPixelSize(
                                                R.dimen
                                                        .sec_lock_setting_notification_preview_notification_side_margin)
                                * 2);
        this.mNotificationView.setLayoutParams(layoutParams);
    }

    private void updateNotiCardOpacity() {
        if (this.mContext != null) {
            this.mNotiCardView.setViewOpacity(
                    Settings.System.getInt(
                                    r0.getContentResolver(),
                                    "lock_noticard_opacity",
                                    this.mContext
                                            .getResources()
                                            .getInteger(
                                                    R.integer.config_default_lock_noticard_opacity))
                            / 100.0f,
                    false);
        }
    }

    private void updatePreview(int i) {
        boolean z =
                Settings.Secure.getInt(
                                this.mContext.getContentResolver(),
                                "lock_screen_allow_private_notifications",
                                0)
                        == 0;
        if (i == 0) {
            NotificationTemplateView notificationTemplateView = this.mNotiCardView;
            if (notificationTemplateView.mCurrentMode != 2) {
                Log.d("NTV", " Change currentmode = 2");
                notificationTemplateView.changeState();
                notificationTemplateView.mCurrentMode = 2;
            }
            if (z) {
                this.mNotiCardView.setSensitiveMode(1);
            } else {
                this.mNotiCardView.setSensitiveMode(0);
            }
        }
        refreshNotiCardLayout();
    }

    @Override // com.samsung.android.settings.lockscreen.SecConceptControllerBehavior
    public void accept(String str, Object obj) {
        str.getClass();
        switch (str) {
            case "set_visibility":
                if (!((Boolean) obj).booleanValue()) {
                    this.mNotiCardView.setSensitiveMode(0);
                    break;
                } else {
                    this.mNotiCardView.setSensitiveMode(1);
                    break;
                }
            case "notification_details":
                updatePreview(0);
                break;
            case "noti_card_seekbar":
                float floatValue = ((Float) obj).floatValue();
                NotificationTemplateView notificationTemplateView = this.mNotiCardView;
                if (notificationTemplateView != null) {
                    notificationTemplateView.setViewOpacity(floatValue, false);
                    break;
                }
                break;
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = (LayoutPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mSemWindowManager = SemWindowManager.getInstance();
        if (this.mLockPatternUtils.isLockScreenDisabled(UserHandle.semGetMyUserId())) {
            return;
        }
        addNotificationPreView();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return !this.mLockPatternUtils.isLockScreenDisabled(UserHandle.semGetMyUserId()) ? 0 : 2;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.samsung.android.settings.lockscreen.SecConceptControllerBehavior
    public void updateConfigurationChanged(Configuration configuration) {
        int i = this.mOrientation;
        int i2 = configuration.orientation;
        if (i != i2) {
            this.mOrientation = i2;
            this.mNotificationPreview.setImageBitmap(getLockscreenWallpaperImg());
            if (this.mUiMode != configuration.uiMode) {
                NotificationTemplateView notificationTemplateView = this.mNotiCardView;
                notificationTemplateView.mDimmedBGView.setBackgroundTintList(
                        ColorStateList.valueOf(notificationTemplateView.getDimmedBgColor()));
                this.mUiMode = configuration.uiMode;
            }
            setNotificationViewParams();
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {}
}
