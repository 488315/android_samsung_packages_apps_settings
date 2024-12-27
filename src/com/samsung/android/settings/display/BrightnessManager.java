package com.samsung.android.settings.display;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.core.util.Consumer;
import androidx.preference.Preference$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BrightnessManager {
    public final AnonymousClass1 mBrightnessObserver;
    public final ContentResolver mContentResolver;
    public final Context mContext;
    public final DisplayManager mDisplayManager;
    public final AnonymousClass1 mHBM_PMS_EnterObserver;
    public boolean mIsHBMOnOff;
    public final Consumer mOnBrightnessChangeListener;
    public final Consumer mOnHBMListener;
    public final int mScreenBrightnessMaximum;
    public final int mScreenBrightnessMinimum;
    public int mMaxBrightness = -1;
    public int mChangeType = 0;
    public final AnonymousClass2 mMaxBrightnessChangedReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.samsung.android.settings.display.BrightnessManager.2
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    Bundle extras;
                    if (!"com.samsung.intent.action.MAX_BRIGHTNESS_CHANGED"
                                    .equals(intent.getAction())
                            || (extras = intent.getExtras()) == null) {
                        return;
                    }
                    int i = extras.getInt("max_brightness", -1);
                    BrightnessManager.this.mChangeType = extras.getInt("change_type", 0);
                    Log.d("BrightnessSeekBarPreference", "received mMaxBrightness : " + i);
                    Preference$$ExternalSyntheticOutline0.m(
                            new StringBuilder("received mChangeType : "),
                            BrightnessManager.this.mChangeType,
                            "BrightnessSeekBarPreference");
                    BrightnessManager brightnessManager = BrightnessManager.this;
                    brightnessManager.getClass();
                    if (i == -1) {
                        i = 255;
                    }
                    brightnessManager.mMaxBrightness = i;
                    int i2 = brightnessManager.mScreenBrightnessMaximum;
                    if (i > i2) {
                        brightnessManager.mMaxBrightness = i2;
                    }
                    int i3 = brightnessManager.mMaxBrightness;
                    int i4 = brightnessManager.mScreenBrightnessMinimum;
                    if (i3 < i4) {
                        brightnessManager.mMaxBrightness = i4;
                    }
                }
            };

    /* JADX WARN: Type inference failed for: r1v0, types: [com.samsung.android.settings.display.BrightnessManager$1] */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.samsung.android.settings.display.BrightnessManager$2] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.samsung.android.settings.display.BrightnessManager$1] */
    public BrightnessManager(
            Context context,
            BrightnessSeekBarPreference$$ExternalSyntheticLambda0
                    brightnessSeekBarPreference$$ExternalSyntheticLambda0,
            BrightnessSeekBarPreference$$ExternalSyntheticLambda0
                    brightnessSeekBarPreference$$ExternalSyntheticLambda02) {
        final int i = 0;
        this.mBrightnessObserver =
                new ContentObserver(
                        this,
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.samsung.android.settings.display.BrightnessManager.1
                    public final /* synthetic */ BrightnessManager this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        switch (i) {
                            case 0:
                                this.this$0.mOnBrightnessChangeListener.accept(
                                        Integer.valueOf(
                                                Settings.System.getInt(
                                                        this.this$0.mContentResolver,
                                                        "screen_brightness",
                                                        100)));
                                break;
                            default:
                                BrightnessManager brightnessManager = this.this$0;
                                brightnessManager.mIsHBMOnOff =
                                        Settings.System.getInt(
                                                        brightnessManager.mContext
                                                                .getContentResolver(),
                                                        "high_brightness_mode_pms_enter",
                                                        0)
                                                != 0;
                                ActionBarContextView$$ExternalSyntheticOutline0.m(
                                        new StringBuilder("hbm is enabled = "),
                                        this.this$0.mIsHBMOnOff,
                                        "BrightnessSeekBarPreference");
                                BrightnessManager brightnessManager2 = this.this$0;
                                brightnessManager2.mOnHBMListener.accept(
                                        Boolean.valueOf(brightnessManager2.mIsHBMOnOff));
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mHBM_PMS_EnterObserver =
                new ContentObserver(
                        this,
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.samsung.android.settings.display.BrightnessManager.1
                    public final /* synthetic */ BrightnessManager this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        switch (i2) {
                            case 0:
                                this.this$0.mOnBrightnessChangeListener.accept(
                                        Integer.valueOf(
                                                Settings.System.getInt(
                                                        this.this$0.mContentResolver,
                                                        "screen_brightness",
                                                        100)));
                                break;
                            default:
                                BrightnessManager brightnessManager = this.this$0;
                                brightnessManager.mIsHBMOnOff =
                                        Settings.System.getInt(
                                                        brightnessManager.mContext
                                                                .getContentResolver(),
                                                        "high_brightness_mode_pms_enter",
                                                        0)
                                                != 0;
                                ActionBarContextView$$ExternalSyntheticOutline0.m(
                                        new StringBuilder("hbm is enabled = "),
                                        this.this$0.mIsHBMOnOff,
                                        "BrightnessSeekBarPreference");
                                BrightnessManager brightnessManager2 = this.this$0;
                                brightnessManager2.mOnHBMListener.accept(
                                        Boolean.valueOf(brightnessManager2.mIsHBMOnOff));
                                break;
                        }
                    }
                };
        this.mContext = context;
        ContentResolver contentResolver = context.getContentResolver();
        this.mContentResolver = contentResolver;
        this.mOnBrightnessChangeListener = brightnessSeekBarPreference$$ExternalSyntheticLambda0;
        this.mOnHBMListener = brightnessSeekBarPreference$$ExternalSyntheticLambda02;
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        this.mDisplayManager = (DisplayManager) context.getSystemService("display");
        this.mScreenBrightnessMinimum = powerManager.getMinimumScreenBrightnessSetting();
        this.mScreenBrightnessMaximum = powerManager.getMaximumScreenBrightnessSetting();
        this.mIsHBMOnOff =
                Settings.System.getInt(contentResolver, "high_brightness_mode_pms_enter", 0) != 0;
    }
}
