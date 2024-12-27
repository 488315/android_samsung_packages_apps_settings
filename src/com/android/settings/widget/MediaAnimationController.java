package com.android.settings.widget;

import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class MediaAnimationController implements VideoPreference.AnimationController {
    public MediaPlayer mMediaPlayer;
    public Surface mSurface;
    public boolean mVideoReady;

    @Override // com.android.settings.widget.VideoPreference.AnimationController
    public final void attachView(TextureView textureView, final View view, final View view2) {
        updateViewStates(view, view2);
        textureView.setSurfaceTextureListener(
                new TextureView
                        .SurfaceTextureListener() { // from class:
                                                    // com.android.settings.widget.MediaAnimationController.1
                    @Override // android.view.TextureView.SurfaceTextureListener
                    public final void onSurfaceTextureAvailable(
                            SurfaceTexture surfaceTexture, int i, int i2) {
                        MediaAnimationController mediaAnimationController =
                                MediaAnimationController.this;
                        if (mediaAnimationController.mMediaPlayer == null
                                || mediaAnimationController.mSurface != null) {
                            return;
                        }
                        Surface surface = new Surface(surfaceTexture);
                        mediaAnimationController.mSurface = surface;
                        mediaAnimationController.mMediaPlayer.setSurface(surface);
                    }

                    @Override // android.view.TextureView.SurfaceTextureListener
                    public final boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                        view.setVisibility(0);
                        MediaAnimationController.this.mSurface = null;
                        return false;
                    }

                    @Override // android.view.TextureView.SurfaceTextureListener
                    public final void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
                        MediaAnimationController mediaAnimationController =
                                MediaAnimationController.this;
                        if (mediaAnimationController.mMediaPlayer != null
                                && mediaAnimationController.mSurface == null) {
                            Surface surface = new Surface(surfaceTexture);
                            mediaAnimationController.mSurface = surface;
                            mediaAnimationController.mMediaPlayer.setSurface(surface);
                        }
                        if (MediaAnimationController.this.mVideoReady) {
                            if (view.getVisibility() == 0) {
                                view.setVisibility(8);
                            }
                            MediaPlayer mediaPlayer = MediaAnimationController.this.mMediaPlayer;
                            if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                                MediaAnimationController.this.mMediaPlayer.start();
                                view2.setVisibility(8);
                            }
                        }
                        MediaPlayer mediaPlayer2 = MediaAnimationController.this.mMediaPlayer;
                        if (mediaPlayer2 == null
                                || mediaPlayer2.isPlaying()
                                || view2.getVisibility() == 0) {
                            return;
                        }
                        view2.setVisibility(0);
                    }

                    @Override // android.view.TextureView.SurfaceTextureListener
                    public final void onSurfaceTextureSizeChanged(
                            SurfaceTexture surfaceTexture, int i, int i2) {}
                });
        textureView.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.widget.MediaAnimationController$$ExternalSyntheticLambda2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view3) {
                        MediaAnimationController.this.updateViewStates(view, view2);
                    }
                });
    }

    @Override // com.android.settings.widget.VideoPreference.AnimationController
    public final int getDuration() {
        return this.mMediaPlayer.getDuration();
    }

    @Override // com.android.settings.widget.VideoPreference.AnimationController
    public final int getVideoHeight() {
        return this.mMediaPlayer.getVideoHeight();
    }

    @Override // com.android.settings.widget.VideoPreference.AnimationController
    public final int getVideoWidth() {
        return this.mMediaPlayer.getVideoWidth();
    }

    @Override // com.android.settings.widget.VideoPreference.AnimationController
    public final void release() {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            this.mMediaPlayer.reset();
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
            this.mVideoReady = false;
        }
    }

    public final void updateViewStates(View view, View view2) {
        if (this.mMediaPlayer.isPlaying()) {
            this.mMediaPlayer.pause();
            view2.setVisibility(0);
            view.setVisibility(0);
        } else {
            view.setVisibility(8);
            view2.setVisibility(8);
            this.mMediaPlayer.start();
        }
    }
}
