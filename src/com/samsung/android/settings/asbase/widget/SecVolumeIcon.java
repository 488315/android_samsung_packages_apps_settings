package com.samsung.android.settings.asbase.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.android.settings.R;
import com.android.settings.notification.AudioHelper;

import com.samsung.android.settings.asbase.utils.SecAudioHelper;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecVolumeIcon extends FrameLayout {
    public int mBroadcastMode;
    public final Context mContext;
    public int mCurrentMediaIconState;
    public SecAudioHelper mHelper;
    public View mIcon;
    public final ColorStateList mIconActiveColor;
    public final ColorStateList mIconEarShockColor;
    public final ColorStateList mIconMutedColor;
    public int mIconType;
    public boolean mIsAnimatedType;
    public final HashMap mMuteIcons;
    public final HashMap mNormalIcons;
    public SecVolumeIconMotion mSecVolumeIconMotion;
    public boolean mShouldUpdateIcon;
    public int mStream;

    public SecVolumeIcon(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        HashMap hashMap = new HashMap();
        this.mNormalIcons = hashMap;
        HashMap hashMap2 = new HashMap();
        this.mMuteIcons = hashMap2;
        this.mCurrentMediaIconState = -1;
        this.mIconType = -1;
        this.mBroadcastMode = 0;
        this.mContext = context;
        Integer valueOf = Integer.valueOf(R.drawable.tw_ic_audio_sound_ringtone);
        hashMap.put(0, valueOf);
        hashMap.put(2, valueOf);
        hashMap.put(3, Integer.valueOf(R.drawable.tw_ic_audio_media_note));
        hashMap.put(1, Integer.valueOf(R.drawable.tw_ic_audio_system_mtrl));
        hashMap.put(5, Integer.valueOf(R.drawable.tw_ic_audio_noti_mtrl));
        Integer valueOf2 = Integer.valueOf(R.drawable.tw_ic_audio_call_mtrl);
        hashMap.put(8, valueOf2);
        Integer valueOf3 = Integer.valueOf(R.drawable.tw_ic_ai_assistant);
        hashMap.put(11, valueOf3);
        Integer valueOf4 = Integer.valueOf(R.drawable.tw_ic_audio_accessibility_mtrl);
        hashMap.put(10, valueOf4);
        hashMap.put(101, valueOf);
        Integer valueOf5 = Integer.valueOf(R.drawable.ic_wired_headphones);
        hashMap.put(102, valueOf5);
        Integer valueOf6 = Integer.valueOf(R.drawable.tw_ic_audio_bluetooth_mtrl);
        hashMap.put(105, valueOf6);
        Integer valueOf7 = Integer.valueOf(R.drawable.tw_ic_alarm_volume_mtrl);
        hashMap.put(4, valueOf7);
        Integer valueOf8 = Integer.valueOf(R.drawable.tw_ic_audio_mute_mtrl);
        hashMap2.put(0, valueOf8);
        hashMap2.put(2, valueOf8);
        hashMap2.put(3, Integer.valueOf(R.drawable.tw_ic_audio_media_mute_mtrl));
        hashMap2.put(1, Integer.valueOf(R.drawable.tw_ic_audio_system_mute_mtrl));
        hashMap2.put(5, Integer.valueOf(R.drawable.tw_ic_audio_noti_mute_mtrl));
        hashMap2.put(8, valueOf2);
        hashMap2.put(11, valueOf3);
        hashMap2.put(10, valueOf4);
        hashMap2.put(101, valueOf8);
        hashMap2.put(102, valueOf5);
        hashMap2.put(105, valueOf6);
        hashMap2.put(4, valueOf7);
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
        this.mIconEarShockColor =
                new ColorStateList(
                        new int[][] {new int[0]},
                        new int[] {
                            context.getResources()
                                    .getColor(R.color.sec_volume_icon_ear_shock_color, null)
                        });
    }

    public final int getIconType(int i) {
        int i2 = this.mStream;
        if (i2 == 8 || i2 == 0 || i != 0) {
            return 3;
        }
        if (((AudioHelper) this.mHelper).mAudioManager.getRingerModeInternal() != 1) {
            return 1;
        }
        int i3 = this.mStream;
        return (i3 == 2 || i3 == 5) ? 0 : 1;
    }

    public final void initialize(SecVolumeIconMotion secVolumeIconMotion, int i) {
        this.mSecVolumeIconMotion = secVolumeIconMotion;
        this.mStream = i;
        this.mHelper = new SecAudioHelper(this.mContext);
        this.mIconType =
                getIconType(
                        ((AudioHelper) this.mHelper)
                                .mAudioManager.getStreamVolume(
                                        SecVolumeValues.getAudioStream(this.mStream)));
        updateLayout(true);
    }

    public final void setAnimatedIcon(int i, int i2) {
        int i3;
        int i4 = this.mStream;
        int i5 = SecVolumeValues.EAR_PROTECT_LIMIT_LEVEL_FOR_ROUTINE;
        if (!(i4 == 3) && !SecVolumeValues.isSpeakerType(i4)) {
            ImageView imageView = (ImageView) this.mIcon.findViewById(R.id.volume_normal_icon);
            ImageView imageView2 = (ImageView) this.mIcon.findViewById(R.id.volume_mute_icon);
            ImageView imageView3 = (ImageView) this.mIcon.findViewById(R.id.volume_vibrate_icon);
            ImageView imageView4 =
                    (ImageView) this.mIcon.findViewById(R.id.volume_icon_mute_splash);
            int i6 = this.mStream;
            if (i6 == 5) {
                imageView.setImageDrawable(
                        this.mContext.getResources().getDrawable(R.drawable.tw_ic_audio_noti_mtrl));
                imageView2.setImageDrawable(
                        this.mContext
                                .getResources()
                                .getDrawable(R.drawable.tw_ic_audio_noti_mute_mtrl));
                imageView3.setImageDrawable(
                        this.mContext
                                .getResources()
                                .getDrawable(R.drawable.tw_ic_audio_noti_vibrate_mtrl));
            } else if (i6 == 1) {
                imageView.setImageDrawable(
                        this.mContext
                                .getResources()
                                .getDrawable(R.drawable.tw_ic_audio_system_mtrl));
                imageView2.setImageDrawable(
                        this.mContext
                                .getResources()
                                .getDrawable(R.drawable.tw_ic_audio_system_mute_mtrl));
            }
            if (i2 == 0) {
                imageView.setVisibility(8);
                imageView2.setVisibility(8);
                imageView4.setVisibility(8);
                imageView3.setVisibility(0);
                if (this.mIconType != i2) {
                    this.mSecVolumeIconMotion.startVibrationAnimation(imageView3);
                }
            } else if (i2 == 1) {
                imageView.setVisibility(8);
                imageView2.setVisibility(0);
                imageView4.setVisibility(0);
                imageView3.setVisibility(8);
                if (this.mIconType != i2) {
                    this.mSecVolumeIconMotion.getClass();
                    SecVolumeIconMotion.startSplashAnimation(imageView4);
                }
            } else if (i2 == 3) {
                imageView.setVisibility(0);
                imageView2.setVisibility(8);
                imageView4.setVisibility(8);
                imageView3.setVisibility(8);
            }
            this.mIconType = i2;
            return;
        }
        int streamMaxVolume =
                ((AudioHelper) this.mHelper)
                        .mAudioManager.getStreamMaxVolume(
                                SecVolumeValues.getAudioStream(this.mStream));
        int i7 = this.mStream;
        if (i7 == 101) {
            streamMaxVolume *= 10;
        }
        double d = i;
        double d2 = streamMaxVolume;
        int i8 = d > 0.5d * d2 ? 3 : d > d2 * 0.25d ? 2 : i > 0 ? 1 : 0;
        if (!SecVolumeValues.isSpeakerType(i7)) {
            int i9 = this.mCurrentMediaIconState;
            if (i8 != i9 || this.mShouldUpdateIcon) {
                int i10 = (i9 == -1 || i8 == i9) ? i8 : i8 - i9 > 0 ? i9 + 1 : i9 - 1;
                ImageView imageView5 =
                        (ImageView) this.mIcon.findViewById(R.id.volume_icon_mute_splash);
                ImageView imageView6 =
                        (ImageView) this.mIcon.findViewById(R.id.volume_media_icon_mute);
                ImageView imageView7 =
                        (ImageView) this.mIcon.findViewById(R.id.volume_media_icon_note);
                ImageView imageView8 =
                        (ImageView) this.mIcon.findViewById(R.id.volume_media_icon_wave_l);
                ImageView imageView9 =
                        (ImageView) this.mIcon.findViewById(R.id.volume_media_icon_wave_s);
                boolean z = i9 == -1 || i9 == i8;
                if (i10 == 3) {
                    this.mSecVolumeIconMotion.startMaxAnimation(
                            this.mStream,
                            imageView7,
                            imageView9,
                            imageView8,
                            null,
                            imageView6,
                            imageView5,
                            z);
                } else if (i10 == 2) {
                    this.mSecVolumeIconMotion.startMidAnimation(
                            this.mStream,
                            i8,
                            imageView7,
                            imageView9,
                            imageView8,
                            null,
                            imageView6,
                            imageView5,
                            z);
                } else if (i10 == 1) {
                    this.mSecVolumeIconMotion.startMinAnimation(
                            this.mStream,
                            i8,
                            imageView7,
                            imageView9,
                            imageView8,
                            null,
                            imageView6,
                            imageView5,
                            z);
                } else {
                    this.mSecVolumeIconMotion.startMuteAnimation(
                            this.mStream,
                            imageView7,
                            imageView9,
                            imageView8,
                            null,
                            imageView6,
                            imageView5,
                            z);
                }
                this.mShouldUpdateIcon = false;
                this.mCurrentMediaIconState = i8;
                return;
            }
            return;
        }
        int i11 = this.mCurrentMediaIconState;
        if (i8 == i11 && !this.mShouldUpdateIcon && this.mIconType == i2) {
            return;
        }
        if (i11 == -1 || i8 == 0 || i8 == i11) {
            i3 = i8;
        } else {
            i3 = i8 - (i2 == 0 ? 0 : i11) > 0 ? i11 + 1 : i11 - 1;
        }
        ImageView imageView10 = (ImageView) this.mIcon.findViewById(R.id.volume_icon_mute_splash);
        ImageView imageView11 = (ImageView) this.mIcon.findViewById(R.id.volume_mute_icon);
        ImageView imageView12 = (ImageView) this.mIcon.findViewById(R.id.volume_normal_icon);
        ImageView imageView13 = (ImageView) this.mIcon.findViewById(R.id.volume_sound_icon_wave_l);
        ImageView imageView14 = (ImageView) this.mIcon.findViewById(R.id.volume_sound_icon_wave_s);
        ImageView imageView15 = (ImageView) this.mIcon.findViewById(R.id.volume_vibrate_icon);
        boolean z2 = (i11 == -1 || i11 == i8) && this.mIconType == i2;
        if (i8 == 0) {
            if (i2 == 1) {
                this.mSecVolumeIconMotion.startMuteAnimation(
                        this.mStream,
                        imageView12,
                        imageView14,
                        imageView13,
                        imageView15,
                        imageView11,
                        imageView10,
                        z2);
            } else {
                SecVolumeIconMotion secVolumeIconMotion = this.mSecVolumeIconMotion;
                secVolumeIconMotion.getClass();
                imageView15.setVisibility(0);
                imageView11.setVisibility(4);
                imageView12.setVisibility(4);
                imageView10.setVisibility(4);
                imageView14.setVisibility(4);
                imageView13.setVisibility(4);
                secVolumeIconMotion.mHandler.removeCallbacks(secVolumeIconMotion.mIconRunnable);
                int dimensionPixelSize =
                        secVolumeIconMotion.mResources.getDimensionPixelSize(
                                R.dimen.volume_sound_icon_note_min_x);
                ObjectAnimator ofFloat =
                        ObjectAnimator.ofFloat(imageView14, "alpha", imageView14.getAlpha(), 0.0f);
                ObjectAnimator ofFloat2 =
                        ObjectAnimator.ofFloat(imageView13, "alpha", imageView13.getAlpha(), 0.0f);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(ofFloat);
                animatorSet.playTogether(ofFloat2);
                animatorSet.setDuration(z2 ? 0L : 50L);
                animatorSet.setInterpolator(new LinearInterpolator());
                ObjectAnimator ofFloat3 =
                        ObjectAnimator.ofFloat(
                                imageView12, "x", imageView12.getX(), dimensionPixelSize);
                ofFloat3.setDuration(z2 ? 0L : 200L);
                ofFloat3.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
                AnimatorSet animatorSet2 = new AnimatorSet();
                animatorSet2.playTogether(animatorSet);
                animatorSet2.playTogether(ofFloat3);
                animatorSet2.start();
                if (!z2) {
                    secVolumeIconMotion.startVibrationAnimation(imageView15);
                }
            }
        } else if (i3 == 3) {
            this.mSecVolumeIconMotion.startMaxAnimation(
                    this.mStream,
                    imageView12,
                    imageView14,
                    imageView13,
                    imageView15,
                    imageView11,
                    imageView10,
                    z2);
        } else if (i3 == 2) {
            this.mSecVolumeIconMotion.startMidAnimation(
                    this.mStream,
                    i8,
                    imageView12,
                    imageView14,
                    imageView13,
                    imageView15,
                    imageView11,
                    imageView10,
                    z2);
        } else if (i3 == 1) {
            this.mSecVolumeIconMotion.startMinAnimation(
                    this.mStream,
                    i8,
                    imageView12,
                    imageView14,
                    imageView13,
                    imageView15,
                    imageView11,
                    imageView10,
                    z2);
        }
        this.mShouldUpdateIcon = false;
        this.mCurrentMediaIconState = i8;
        this.mIconType = i2;
    }

    public final void setDefaultIcon(int i) {
        int iconType = getIconType(i);
        HashMap hashMap = this.mNormalIcons;
        Integer valueOf = Integer.valueOf(this.mStream);
        Integer valueOf2 = Integer.valueOf(R.drawable.tw_ic_audio_media_note);
        int intValue = ((Integer) hashMap.getOrDefault(valueOf, valueOf2)).intValue();
        if (iconType == 0) {
            intValue =
                    this.mStream == 5
                            ? R.drawable.tw_ic_audio_noti_vibrate_mtrl
                            : R.drawable.tw_ic_audio_vibrate_mtrl;
        } else if (iconType == 1) {
            intValue =
                    ((Integer)
                                    this.mMuteIcons.getOrDefault(
                                            Integer.valueOf(this.mStream),
                                            Integer.valueOf(
                                                    R.drawable.tw_ic_audio_media_mute_mtrl)))
                            .intValue();
        } else if (iconType == 2) {
            intValue = R.drawable.tw_ic_audio_bluetooth_mtrl;
        } else if (iconType == 3) {
            intValue =
                    ((Integer)
                                    this.mNormalIcons.getOrDefault(
                                            Integer.valueOf(this.mStream), valueOf2))
                            .intValue();
        }
        ((ImageView) this.mIcon).setImageDrawable(getResources().getDrawable(intValue, null));
    }

    public final boolean supportAnimatedIcon() {
        int i = this.mStream;
        return i == 3 || i == 2 || i == 5 || i == 1 || i == 0 || i == 101;
    }

    public final void updateIconTintColor(int i) {
        ColorStateList colorStateList;
        boolean isEarDeviceStatusOn = this.mHelper.isEarDeviceStatusOn();
        int iconType = getIconType(i);
        if (iconType == 1) {
            colorStateList = this.mIconMutedColor;
        } else {
            int i2 = this.mStream;
            if ((i2 == 102 || i2 == 105)
                    && i > SecVolumeValues.EAR_PROTECT_LIMIT_LEVEL_FOR_ROUTINE) {
                colorStateList = this.mIconEarShockColor;
            } else {
                if (this.mBroadcastMode != 2) {
                    int i3 = SecVolumeValues.EAR_PROTECT_LIMIT_LEVEL_FOR_ROUTINE;
                    if (i2 == 3 && isEarDeviceStatusOn) {
                        this.mHelper.getClass();
                        int earProtectLevel = SecAudioHelper.getEarProtectLevel();
                        int streamVolume =
                                ((AudioHelper) this.mHelper)
                                        .mAudioManager.getStreamVolume(
                                                SecVolumeValues.getAudioStream(this.mStream));
                        if (earProtectLevel > 0 && earProtectLevel < streamVolume * 100) {
                            colorStateList = this.mIconEarShockColor;
                        }
                    }
                }
                colorStateList = this.mIconActiveColor;
            }
        }
        if (!supportAnimatedIcon()) {
            ((ImageView) this.mIcon).setImageTintList(colorStateList);
            return;
        }
        int i4 = this.mStream;
        int i5 = SecVolumeValues.EAR_PROTECT_LIMIT_LEVEL_FOR_ROUTINE;
        if (i4 != 3 || iconType == 1) {
            return;
        }
        ImageView imageView = (ImageView) this.mIcon.findViewById(R.id.volume_media_icon_note);
        ImageView imageView2 = (ImageView) this.mIcon.findViewById(R.id.volume_media_icon_wave_l);
        ImageView imageView3 = (ImageView) this.mIcon.findViewById(R.id.volume_media_icon_wave_s);
        imageView.setImageTintList(colorStateList);
        imageView2.setImageTintList(colorStateList);
        imageView3.setImageTintList(colorStateList);
    }

    /* JADX WARN: Code restructure failed: missing block: B:62:0x012a, code lost:

       if (com.samsung.android.settings.asbase.utils.SoundUtils.isUseGlobalAlarmVolume(r9.mContext) == false) goto L53;
    */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0132  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0135  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateLayout(boolean r10) {
        /*
            Method dump skipped, instructions count: 316
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.asbase.widget.SecVolumeIcon.updateLayout(boolean):void");
    }

    public final void updateLayoutForRoutine(int i, int i2) {
        int i3;
        int i4 = i != 0 ? 3 : 1;
        if (SecVolumeValues.getAudioStream(this.mStream) != 3) {
            if (i2 != 0) {
                if (i2 == 1) {
                    if (this.mStream != 1) {
                        i4 = 0;
                    }
                }
            }
            i4 = 1;
        }
        if (supportAnimatedIcon() && this.mIsAnimatedType) {
            setAnimatedIcon(i, i4);
        } else {
            setDefaultIcon(i);
        }
        updateIconTintColor(i);
        if (i4 == 3 || i2 == 2 || !((i3 = this.mStream) == 5 || i3 == 1)) {
            setEnabled(true);
            setAlpha(1.0f);
        } else {
            setEnabled(false);
            setAlpha(0.4f);
        }
    }
}
