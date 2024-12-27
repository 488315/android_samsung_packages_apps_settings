package com.samsung.android.settings.asbase.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.android.settings.R;

import com.samsung.android.settings.asbase.utils.VibUtils;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecVibrationIcon extends FrameLayout {
    public final Context mContext;
    public int mCurrentMediaIconState;
    public View mIcon;
    public ColorStateList mIconActiveColor;
    public final ColorStateList mIconMutedColor;
    public int mIconType;
    public boolean mIsAnimatedType;
    public final HashMap mMuteIcons;
    public final HashMap mNormalIcons;
    public SecVibrationIconMotion mSecVibrationIconMotion;
    public boolean mShouldUpdateIcon;
    public int mStream;
    public String mSystemDbName;

    public SecVibrationIcon(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCurrentMediaIconState = -1;
        this.mIconType = -1;
        HashMap hashMap = new HashMap();
        this.mNormalIcons = hashMap;
        HashMap hashMap2 = new HashMap();
        this.mMuteIcons = hashMap2;
        this.mContext = context;
        hashMap.put(2, Integer.valueOf(R.drawable.tw_ic_audio_sound_ringtone));
        hashMap.put(3, Integer.valueOf(R.drawable.tw_ic_audio_media_note));
        hashMap.put(5, Integer.valueOf(R.drawable.tw_ic_audio_noti_mtrl));
        Integer valueOf = Integer.valueOf(R.drawable.tw_ic_audio_system_mtrl);
        hashMap.put(1, valueOf);
        hashMap.put(0, valueOf);
        hashMap2.put(2, Integer.valueOf(R.drawable.tw_ic_audio_mute_mtrl));
        hashMap2.put(3, Integer.valueOf(R.drawable.tw_ic_audio_media_mute_mtrl));
        hashMap2.put(5, Integer.valueOf(R.drawable.tw_ic_audio_noti_mute_mtrl));
        Integer valueOf2 = Integer.valueOf(R.drawable.tw_ic_audio_system_mute_mtrl);
        hashMap2.put(1, valueOf2);
        hashMap2.put(0, valueOf2);
        this.mIconActiveColor =
                new ColorStateList(
                        new int[][] {new int[0]},
                        new int[] {
                            context.getResources().getColor(R.color.sec_volume_icon_color, null)
                        });
        this.mIconMutedColor =
                new ColorStateList(
                        new int[][] {new int[0]},
                        new int[] {
                            context.getResources()
                                    .getColor(R.color.sec_volume_icon_no_sound_color, null)
                        });
    }

    public final int getIconType() {
        return Settings.System.getInt(
                                this.mContext.getContentResolver(),
                                this.mSystemDbName,
                                VibUtils.isSupportDcHaptic(this.mContext)
                                        ? 3
                                        : this.mStream == 0 ? 4 : 5)
                        == 0
                ? 1
                : 3;
    }

    public final boolean supportAnimatedIcon() {
        int i = this.mStream;
        return i == 2 || i == 5 || i == 1 || i == 0 || i == 3;
    }

    public final void updateLayout(boolean z) {
        int integer;
        String str;
        int i;
        boolean supportAnimatedIcon = supportAnimatedIcon();
        boolean z2 = z || this.mIsAnimatedType != supportAnimatedIcon;
        this.mShouldUpdateIcon = z2;
        if (z2) {
            if (getChildCount() > 0) {
                removeAllViews();
            }
            if (supportAnimatedIcon) {
                int i2 = this.mStream;
                if (i2 == 3) {
                    this.mIcon =
                            LayoutInflater.from(this.mContext)
                                    .inflate(R.layout.volume_animated_media_icon, (ViewGroup) null);
                } else if (i2 == 2) {
                    this.mIcon =
                            LayoutInflater.from(this.mContext)
                                    .inflate(
                                            R.layout.volume_animated_ringtone_icon,
                                            (ViewGroup) null);
                } else {
                    this.mIcon =
                            LayoutInflater.from(this.mContext)
                                    .inflate(R.layout.volume_animated_icon, (ViewGroup) null);
                }
                this.mIsAnimatedType = true;
            } else {
                this.mIcon =
                        LayoutInflater.from(this.mContext)
                                .inflate(R.layout.volume_default_icon, (ViewGroup) null);
                this.mIsAnimatedType = false;
            }
            addView(this.mIcon);
        }
        if (supportAnimatedIcon() && this.mIsAnimatedType) {
            int i3 = this.mStream;
            int i4 = 5;
            if ((i3 == 3) || i3 == 2) {
                Resources resources = this.mContext.getResources();
                if (i3 == 0) {
                    integer =
                            resources.getInteger(
                                    android.R.integer
                                            .leanback_setup_translation_content_resting_point_v4);
                    str = "SEM_VIBRATION_FORCE_TOUCH_INTENSITY";
                } else if (i3 == 1) {
                    integer =
                            resources.getInteger(
                                    android.R.integer.leanback_setup_translation_content_cliff_v4);
                    str = "VIB_FEEDBACK_MAGNITUDE";
                } else if (i3 == 2) {
                    integer =
                            resources.getInteger(
                                    android.R.integer.lock_pattern_fade_pattern_duration);
                    str = "VIB_RECVCALL_MAGNITUDE";
                } else if (i3 == 3) {
                    integer =
                            resources.getInteger(
                                    android.R.integer
                                            .leanback_setup_translation_forward_in_content_duration);
                    str = "media_vibration_intensity";
                } else if (i3 != 5) {
                    integer = 5;
                    str = null;
                } else {
                    integer =
                            resources.getInteger(android.R.integer.lock_pattern_fade_pattern_delay);
                    str = "SEM_VIBRATION_NOTIFICATION_INTENSITY";
                }
                int i5 = Settings.System.getInt(this.mContext.getContentResolver(), str, integer);
                int i6 = this.mStream;
                if (i6 == 0) {
                    i4 = 4;
                } else if (i6 != 1) {
                    if (i6 != 2 && i6 != 3 && i6 != 5) {
                        i4 = 0;
                    }
                } else if (VibUtils.isSupportDcHaptic(this.mContext)) {
                    i4 = 3;
                }
                double d = i5;
                double d2 = i4;
                int i7 = d > 0.6d * d2 ? 3 : d > d2 * 0.25d ? 2 : i5 > 0 ? 1 : 0;
                if (this.mStream == 3) {
                    int i8 = this.mCurrentMediaIconState;
                    if (i7 != i8 || this.mShouldUpdateIcon) {
                        int i9 = (i8 == -1 || i7 == i8) ? i7 : i7 - i8 > 0 ? i8 + 1 : i8 - 1;
                        ImageView imageView =
                                (ImageView) this.mIcon.findViewById(R.id.volume_icon_mute_splash);
                        ImageView imageView2 =
                                (ImageView) this.mIcon.findViewById(R.id.volume_media_icon_mute);
                        ImageView imageView3 =
                                (ImageView) this.mIcon.findViewById(R.id.volume_media_icon_note);
                        ImageView imageView4 =
                                (ImageView) this.mIcon.findViewById(R.id.volume_media_icon_wave_l);
                        ImageView imageView5 =
                                (ImageView) this.mIcon.findViewById(R.id.volume_media_icon_wave_s);
                        boolean z3 = i8 == -1 || i8 == i7;
                        if (i9 == 3) {
                            this.mSecVibrationIconMotion.startMaxAnimation(
                                    this.mStream,
                                    imageView3,
                                    imageView5,
                                    imageView4,
                                    imageView2,
                                    imageView,
                                    z3);
                        } else if (i9 == 2) {
                            this.mSecVibrationIconMotion.startMidAnimation(
                                    this.mStream,
                                    i7,
                                    imageView3,
                                    imageView5,
                                    imageView4,
                                    imageView2,
                                    imageView,
                                    z3);
                        } else if (i9 == 1) {
                            this.mSecVibrationIconMotion.startMinAnimation(
                                    this.mStream,
                                    i7,
                                    imageView3,
                                    imageView5,
                                    imageView4,
                                    imageView2,
                                    imageView,
                                    z3);
                        } else {
                            this.mSecVibrationIconMotion.startMuteAnimation(
                                    this.mStream,
                                    imageView3,
                                    imageView5,
                                    imageView4,
                                    imageView2,
                                    imageView,
                                    z3);
                        }
                        this.mShouldUpdateIcon = false;
                        this.mCurrentMediaIconState = i7;
                    }
                } else {
                    int iconType = getIconType();
                    int i10 = this.mCurrentMediaIconState;
                    if (i7 != i10 || this.mShouldUpdateIcon || this.mIconType != iconType) {
                        if (i10 == -1 || i7 == 0 || i7 == i10) {
                            i = i7;
                        } else {
                            i = i7 - i10 > 0 ? i10 + 1 : i10 - 1;
                        }
                        ImageView imageView6 =
                                (ImageView) this.mIcon.findViewById(R.id.volume_icon_mute_splash);
                        ImageView imageView7 =
                                (ImageView) this.mIcon.findViewById(R.id.volume_mute_icon);
                        ImageView imageView8 =
                                (ImageView) this.mIcon.findViewById(R.id.volume_normal_icon);
                        ImageView imageView9 =
                                (ImageView) this.mIcon.findViewById(R.id.volume_sound_icon_wave_l);
                        ImageView imageView10 =
                                (ImageView) this.mIcon.findViewById(R.id.volume_sound_icon_wave_s);
                        ((ImageView) this.mIcon.findViewById(R.id.volume_vibrate_icon))
                                .setVisibility(8);
                        boolean z4 = (i10 == -1 || i10 == i7) && this.mIconType == iconType;
                        if (i == 3) {
                            this.mSecVibrationIconMotion.startMaxAnimation(
                                    this.mStream,
                                    imageView8,
                                    imageView10,
                                    imageView9,
                                    imageView7,
                                    imageView6,
                                    z4);
                        } else if (i == 2) {
                            this.mSecVibrationIconMotion.startMidAnimation(
                                    this.mStream,
                                    i7,
                                    imageView8,
                                    imageView10,
                                    imageView9,
                                    imageView7,
                                    imageView6,
                                    z4);
                        } else if (i == 1) {
                            this.mSecVibrationIconMotion.startMinAnimation(
                                    this.mStream,
                                    i7,
                                    imageView8,
                                    imageView10,
                                    imageView9,
                                    imageView7,
                                    imageView6,
                                    z4);
                        } else {
                            this.mSecVibrationIconMotion.startMuteAnimation(
                                    this.mStream,
                                    imageView8,
                                    imageView10,
                                    imageView9,
                                    imageView7,
                                    imageView6,
                                    z4);
                        }
                        this.mShouldUpdateIcon = false;
                        this.mCurrentMediaIconState = i7;
                        this.mIconType = iconType;
                    }
                }
            } else {
                int iconType2 = getIconType();
                ImageView imageView11 =
                        (ImageView) this.mIcon.findViewById(R.id.volume_normal_icon);
                ImageView imageView12 = (ImageView) this.mIcon.findViewById(R.id.volume_mute_icon);
                ImageView imageView13 =
                        (ImageView) this.mIcon.findViewById(R.id.volume_icon_mute_splash);
                int i11 = this.mStream;
                if (i11 == 5) {
                    imageView11.setImageDrawable(
                            this.mContext
                                    .getResources()
                                    .getDrawable(R.drawable.tw_ic_audio_noti_mtrl));
                    imageView12.setImageDrawable(
                            this.mContext
                                    .getResources()
                                    .getDrawable(R.drawable.tw_ic_audio_noti_mute_mtrl));
                } else if (i11 == 1 || i11 == 0) {
                    imageView11.setImageDrawable(
                            this.mContext
                                    .getResources()
                                    .getDrawable(R.drawable.tw_ic_audio_system_mtrl));
                    imageView12.setImageDrawable(
                            this.mContext
                                    .getResources()
                                    .getDrawable(R.drawable.tw_ic_audio_system_mute_mtrl));
                }
                if (iconType2 == 1) {
                    imageView11.setVisibility(8);
                    imageView12.setVisibility(0);
                    imageView13.setVisibility(0);
                    if (this.mIconType != iconType2) {
                        this.mSecVibrationIconMotion.getClass();
                        SecVibrationIconMotion.startSplashAnimation(imageView13);
                    }
                } else if (iconType2 == 3) {
                    imageView11.setVisibility(0);
                    imageView12.setVisibility(8);
                    imageView13.setVisibility(8);
                }
                this.mIconType = iconType2;
            }
        } else {
            int iconType3 = getIconType();
            HashMap hashMap = this.mNormalIcons;
            Integer valueOf = Integer.valueOf(this.mStream);
            Integer valueOf2 = Integer.valueOf(R.drawable.tw_ic_audio_media_note);
            int intValue = ((Integer) hashMap.getOrDefault(valueOf, valueOf2)).intValue();
            if (iconType3 == 1) {
                intValue =
                        ((Integer)
                                        this.mMuteIcons.getOrDefault(
                                                Integer.valueOf(this.mStream),
                                                Integer.valueOf(
                                                        R.drawable.tw_ic_audio_media_mute_mtrl)))
                                .intValue();
            } else if (iconType3 == 3) {
                intValue =
                        ((Integer)
                                        this.mNormalIcons.getOrDefault(
                                                Integer.valueOf(this.mStream), valueOf2))
                                .intValue();
            }
            ((ImageView) this.mIcon).setImageDrawable(getResources().getDrawable(intValue, null));
        }
        int iconType4 = getIconType();
        ColorStateList colorStateList =
                iconType4 == 1 ? this.mIconMutedColor : this.mIconActiveColor;
        if (!supportAnimatedIcon()) {
            ((ImageView) this.mIcon).setImageTintList(colorStateList);
        } else if (iconType4 != 1) {
            int i12 = this.mStream;
            if (i12 == 3) {
                ImageView imageView14 =
                        (ImageView) this.mIcon.findViewById(R.id.volume_media_icon_note);
                ImageView imageView15 =
                        (ImageView) this.mIcon.findViewById(R.id.volume_media_icon_wave_l);
                ImageView imageView16 =
                        (ImageView) this.mIcon.findViewById(R.id.volume_media_icon_wave_s);
                imageView14.setImageTintList(colorStateList);
                imageView15.setImageTintList(colorStateList);
                imageView16.setImageTintList(colorStateList);
            } else if (i12 == 2) {
                ImageView imageView17 =
                        (ImageView) this.mIcon.findViewById(R.id.volume_normal_icon);
                ImageView imageView18 =
                        (ImageView) this.mIcon.findViewById(R.id.volume_sound_icon_wave_l);
                ImageView imageView19 =
                        (ImageView) this.mIcon.findViewById(R.id.volume_sound_icon_wave_s);
                imageView17.setImageTintList(colorStateList);
                imageView18.setImageTintList(colorStateList);
                imageView19.setImageTintList(colorStateList);
            } else {
                ((ImageView) this.mIcon.findViewById(R.id.volume_normal_icon))
                        .setImageTintList(colorStateList);
            }
        }
        setEnabled(true);
        setAlpha(1.0f);
    }
}
