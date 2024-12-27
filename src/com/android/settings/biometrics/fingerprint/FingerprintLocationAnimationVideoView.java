package com.android.settings.biometrics.fingerprint;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FingerprintLocationAnimationVideoView extends TextureView
        implements FingerprintFindSensorAnimation {
    public float mAspect;
    public MediaPlayer mMediaPlayer;

    public FingerprintLocationAnimationVideoView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mAspect = 1.0f;
    }

    public MediaPlayer createMediaPlayer(Context context, Uri uri) {
        return MediaPlayer.create(((TextureView) this).mContext, uri);
    }

    public Uri getFingerprintLocationAnimation() {
        Resources resources = getContext().getResources();
        return Uri.parse(
                "android.resource://"
                        + resources.getResourcePackageName(R.raw.fingerprint_location_animation)
                        + '/'
                        + resources.getResourceTypeName(R.raw.fingerprint_location_animation)
                        + '/'
                        + resources.getResourceEntryName(R.raw.fingerprint_location_animation));
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        setSurfaceTextureListener(
                new TextureView
                        .SurfaceTextureListener() { // from class:
                                                    // com.android.settings.biometrics.fingerprint.FingerprintLocationAnimationVideoView.1
                    public SurfaceTexture mTextureToDestroy = null;

                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                    /* renamed from: com.android.settings.biometrics.fingerprint.FingerprintLocationAnimationVideoView$1$1, reason: invalid class name and collision with other inner class name */
                    public final class C00201 implements MediaPlayer.OnPreparedListener {
                        @Override // android.media.MediaPlayer.OnPreparedListener
                        public final void onPrepared(MediaPlayer mediaPlayer) {
                            mediaPlayer.setLooping(true);
                        }
                    }

                    @Override // android.view.TextureView.SurfaceTextureListener
                    public final void onSurfaceTextureAvailable(
                            SurfaceTexture surfaceTexture, int i, int i2) {
                        FingerprintLocationAnimationVideoView.this.setVisibility(4);
                        Uri fingerprintLocationAnimation =
                                FingerprintLocationAnimationVideoView.this
                                        .getFingerprintLocationAnimation();
                        MediaPlayer mediaPlayer =
                                FingerprintLocationAnimationVideoView.this.mMediaPlayer;
                        if (mediaPlayer != null) {
                            mediaPlayer.release();
                        }
                        SurfaceTexture surfaceTexture2 = this.mTextureToDestroy;
                        if (surfaceTexture2 != null) {
                            surfaceTexture2.release();
                            this.mTextureToDestroy = null;
                        }
                        FingerprintLocationAnimationVideoView
                                fingerprintLocationAnimationVideoView =
                                        FingerprintLocationAnimationVideoView.this;
                        fingerprintLocationAnimationVideoView.mMediaPlayer =
                                fingerprintLocationAnimationVideoView.createMediaPlayer(
                                        ((TextureView) fingerprintLocationAnimationVideoView)
                                                .mContext,
                                        fingerprintLocationAnimation);
                        MediaPlayer mediaPlayer2 =
                                FingerprintLocationAnimationVideoView.this.mMediaPlayer;
                        if (mediaPlayer2 == null) {
                            return;
                        }
                        mediaPlayer2.setSurface(new Surface(surfaceTexture));
                        FingerprintLocationAnimationVideoView.this.mMediaPlayer
                                .setOnPreparedListener(new C00201());
                        FingerprintLocationAnimationVideoView.this.mMediaPlayer.setOnInfoListener(
                                new MediaPlayer
                                        .OnInfoListener() { // from class:
                                                            // com.android.settings.biometrics.fingerprint.FingerprintLocationAnimationVideoView.1.2
                                    @Override // android.media.MediaPlayer.OnInfoListener
                                    public final boolean onInfo(
                                            MediaPlayer mediaPlayer3, int i3, int i4) {
                                        if (i3 == 3) {
                                            FingerprintLocationAnimationVideoView.this
                                                    .setVisibility(0);
                                        }
                                        return false;
                                    }
                                });
                        FingerprintLocationAnimationVideoView.this.mAspect =
                                r2.mMediaPlayer.getVideoHeight()
                                        / FingerprintLocationAnimationVideoView.this.mMediaPlayer
                                                .getVideoWidth();
                        FingerprintLocationAnimationVideoView.this.requestLayout();
                        FingerprintLocationAnimationVideoView.this.startAnimation();
                    }

                    @Override // android.view.TextureView.SurfaceTextureListener
                    public final boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                        this.mTextureToDestroy = surfaceTexture;
                        return false;
                    }

                    @Override // android.view.TextureView.SurfaceTextureListener
                    public final void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {}

                    @Override // android.view.TextureView.SurfaceTextureListener
                    public final void onSurfaceTextureSizeChanged(
                            SurfaceTexture surfaceTexture, int i, int i2) {}
                });
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(
                i,
                View.MeasureSpec.makeMeasureSpec(
                        Math.round(this.mAspect * View.MeasureSpec.getSize(i)), 1073741824));
    }

    @Override // com.android.settings.biometrics.fingerprint.FingerprintFindSensorAnimation
    public final void pauseAnimation() {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer == null || !mediaPlayer.isPlaying()) {
            return;
        }
        this.mMediaPlayer.pause();
    }

    @Override // com.android.settings.biometrics.fingerprint.FingerprintFindSensorAnimation
    public final void startAnimation() {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer == null || mediaPlayer.isPlaying()) {
            return;
        }
        this.mMediaPlayer.start();
    }

    @Override // com.android.settings.biometrics.fingerprint.FingerprintFindSensorAnimation
    public final void stopAnimation() {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
        }
    }
}
