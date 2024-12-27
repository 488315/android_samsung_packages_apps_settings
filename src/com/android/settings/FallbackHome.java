package com.android.settings;

import android.R;
import android.app.Activity;
import android.app.WallpaperColors;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.samsung.android.knox.ex.peripheral.PeripheralConstants;

import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class FallbackHome extends Activity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int mProgressTimeout;
    public boolean mProvisioned;
    public WallpaperManager mWallManager;
    public final FallbackHome$$ExternalSyntheticLambda0 mProgressTimeoutRunnable =
            new Runnable() { // from class:
                             // com.android.settings.FallbackHome$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    FallbackHome fallbackHome = FallbackHome.this;
                    int i = FallbackHome.$r8$clinit;
                    View inflate =
                            fallbackHome
                                    .getLayoutInflater()
                                    .inflate(
                                            R.layout.fallback_home_finishing_boot,
                                            (ViewGroup) null);
                    fallbackHome.setContentView(inflate);
                    inflate.setAlpha(0.0f);
                    inflate.animate()
                            .alpha(1.0f)
                            .setDuration(500L)
                            .setInterpolator(
                                    AnimationUtils.loadInterpolator(
                                            fallbackHome, R.interpolator.fast_out_slow_in))
                            .start();
                    fallbackHome.getWindow().addFlags(128);
                }
            };
    public final AnonymousClass1 mColorsChangedListener =
            new WallpaperManager
                    .OnColorsChangedListener() { // from class: com.android.settings.FallbackHome.1
                @Override // android.app.WallpaperManager.OnColorsChangedListener
                public final void onColorsChanged(WallpaperColors wallpaperColors, int i) {
                    if (wallpaperColors != null) {
                        View decorView = FallbackHome.this.getWindow().getDecorView();
                        FallbackHome fallbackHome = FallbackHome.this;
                        int systemUiVisibility = decorView.getSystemUiVisibility();
                        int i2 = FallbackHome.$r8$clinit;
                        fallbackHome.getClass();
                        decorView.setSystemUiVisibility(
                                (wallpaperColors.getColorHints() & 1) != 0
                                        ? systemUiVisibility | 8208
                                        : systemUiVisibility & (-8209));
                        FallbackHome.this.mWallManager.removeOnColorsChangedListener(this);
                        FallbackHome.this.setWallpaperOffset();
                    }
                }
            };
    public final AnonymousClass2 mReceiver =
            new BroadcastReceiver() { // from class: com.android.settings.FallbackHome.2
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    FallbackHome fallbackHome = FallbackHome.this;
                    int i = FallbackHome.$r8$clinit;
                    fallbackHome.maybeFinish();
                }
            };
    public final AnonymousClass4 mHandler =
            new Handler() { // from class: com.android.settings.FallbackHome.4
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    int i = FallbackHome.$r8$clinit;
                    FallbackHome.this.maybeFinish();
                }
            };

    public final void maybeFinish() {
        if (((UserManager) getSystemService(UserManager.class)).isUserUnlocked()) {
            if (Objects.equals(
                    getPackageName(),
                    getPackageManager()
                            .resolveActivity(
                                    new Intent("android.intent.action.MAIN")
                                            .addCategory("android.intent.category.HOME")
                                            .addCategory("android.intent.category.DEFAULT"),
                                    0)
                            .activityInfo
                            .packageName)) {
                Log.d(
                        "FallbackHome",
                        "User unlocked but no home; let's hope someone enables one soon?");
                sendEmptyMessageDelayed(0, 500L);
            } else {
                Log.d("FallbackHome", "User unlocked and real home found; let's go!");
                ((PowerManager) getSystemService(PowerManager.class))
                        .userActivity(SystemClock.uptimeMillis(), false);
                finish();
            }
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        setWallpaperOffset();
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        final int i;
        super.onCreate(bundle);
        this.mProgressTimeout = 500;
        boolean z = Settings.Global.getInt(getContentResolver(), "device_provisioned", 0) != 0;
        this.mProvisioned = z;
        if (z) {
            i = 1536;
        } else {
            setTheme(2132083278);
            i = PeripheralConstants.ErrorCode.ERROR_PERIPHERAL_CONNECTIVITY_NOT_SUPPORTED;
        }
        WallpaperManager wallpaperManager =
                (WallpaperManager) getSystemService(WallpaperManager.class);
        this.mWallManager = wallpaperManager;
        if (wallpaperManager == null) {
            Log.w("FallbackHome", "Wallpaper manager isn't ready, can't listen to color changes!");
        } else {
            new AsyncTask() { // from class: com.android.settings.FallbackHome.3
                @Override // android.os.AsyncTask
                public final Object doInBackground(Object[] objArr) {
                    WallpaperColors wallpaperColors =
                            FallbackHome.this.mWallManager.getWallpaperColors(1);
                    if (wallpaperColors == null) {
                        FallbackHome fallbackHome = FallbackHome.this;
                        fallbackHome.mWallManager.addOnColorsChangedListener(
                                fallbackHome.mColorsChangedListener, null);
                        return null;
                    }
                    FallbackHome.this.setWallpaperOffset();
                    FallbackHome fallbackHome2 = FallbackHome.this;
                    int i2 = i;
                    fallbackHome2.getClass();
                    return Integer.valueOf(
                            (wallpaperColors.getColorHints() & 1) != 0 ? i2 | 8208 : i2 & (-8209));
                }

                @Override // android.os.AsyncTask
                public final void onPostExecute(Object obj) {
                    Integer num = (Integer) obj;
                    if (num == null) {
                        return;
                    }
                    FallbackHome.this
                            .getWindow()
                            .getDecorView()
                            .setSystemUiVisibility(num.intValue());
                }
            }.execute(new Object[0]);
        }
        getWindow().getDecorView().setSystemUiVisibility(i);
        registerReceiver(this.mReceiver, new IntentFilter("android.intent.action.USER_UNLOCKED"));
        maybeFinish();
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.mReceiver);
        WallpaperManager wallpaperManager = this.mWallManager;
        if (wallpaperManager != null) {
            wallpaperManager.removeOnColorsChangedListener(this.mColorsChangedListener);
        }
    }

    @Override // android.app.Activity
    public final void onPause() {
        super.onPause();
        removeCallbacks(this.mProgressTimeoutRunnable);
    }

    @Override // android.app.Activity
    public final void onResume() {
        super.onResume();
        setWallpaperOffset();
        if (this.mProvisioned) {
            postDelayed(this.mProgressTimeoutRunnable, this.mProgressTimeout);
        }
    }

    public final void setWallpaperOffset() {
        WallpaperManager wallpaperManager;
        View peekDecorView = getWindow().peekDecorView();
        if (peekDecorView == null || (wallpaperManager = this.mWallManager) == null) {
            return;
        }
        try {
            wallpaperManager.setWallpaperOffsets(peekDecorView.getWindowToken(), 0.5f, 0.5f);
            Log.i("FallbackHome", "setWallpaperOffsets:" + peekDecorView.getWindowToken());
        } catch (IllegalArgumentException unused) {
            Log.i("FallbackHome", "IllegalArgumentException occured");
        } catch (Exception unused2) {
            Log.i("FallbackHome", "Exception occured");
        }
    }
}
