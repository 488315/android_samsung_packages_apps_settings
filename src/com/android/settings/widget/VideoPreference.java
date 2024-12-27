package com.android.settings.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import com.android.settings.R;
import com.android.settings.R$styleable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class VideoPreference extends Preference {
    boolean mAnimationAvailable;
    AnimationController mAnimationController;
    public final int mAnimationId;
    public float mAspectRatio;
    public final Context mContext;
    public final int mHeight;
    public ImageView mPlayButton;
    public final int mPreviewId;
    public ImageView mPreviewImage;
    public final int mVectorAnimationId;
    public TextureView mVideo;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface AnimationController {
        void attachView(TextureView textureView, View view, View view2);

        int getDuration();

        int getVideoHeight();

        int getVideoWidth();

        void release();
    }

    public VideoPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mAspectRatio = 1.0f;
        this.mHeight = -2;
        this.mContext = context;
        TypedArray obtainStyledAttributes =
                context.getTheme()
                        .obtainStyledAttributes(attributeSet, R$styleable.VideoPreference, 0, 0);
        try {
            try {
                this.mAnimationAvailable = false;
                int i = this.mAnimationId;
                this.mAnimationId = i == 0 ? obtainStyledAttributes.getResourceId(0, 0) : i;
                int i2 = this.mPreviewId;
                this.mPreviewId = i2 == 0 ? obtainStyledAttributes.getResourceId(1, 0) : i2;
                int resourceId = obtainStyledAttributes.getResourceId(2, 0);
                this.mVectorAnimationId = resourceId;
                if (this.mPreviewId == 0 && this.mAnimationId == 0 && resourceId == 0) {
                    setVisible(false);
                } else {
                    initAnimationController();
                    AnimationController animationController = this.mAnimationController;
                    if (animationController == null || animationController.getDuration() <= 0) {
                        setVisible(false);
                    } else {
                        setVisible(true);
                        setLayoutResource(R.layout.video_preference);
                        this.mAnimationAvailable = true;
                        updateAspectRatio();
                    }
                }
            } catch (Exception unused) {
                Log.w("VideoPreference", "Animation resource not found. Will not show animation.");
            }
            obtainStyledAttributes.recycle();
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }

    /* JADX WARN: Type inference failed for: r0v10, types: [com.android.settings.widget.VectorAnimationController$1] */
    public final void initAnimationController() {
        int i = this.mVectorAnimationId;
        if (i != 0) {
            Context context = this.mContext;
            final VectorAnimationController vectorAnimationController =
                    new VectorAnimationController();
            vectorAnimationController.mAnimatedVectorDrawableCompat =
                    AnimatedVectorDrawableCompat.create(context, i);
            vectorAnimationController.mAnimationCallback =
                    new Animatable2Compat
                            .AnimationCallback() { // from class:
                                                   // com.android.settings.widget.VectorAnimationController.1
                        @Override // androidx.vectordrawable.graphics.drawable.Animatable2Compat.AnimationCallback
                        public final void onAnimationEnd(Drawable drawable) {
                            VectorAnimationController.this.mAnimatedVectorDrawableCompat.start();
                        }
                    };
            this.mAnimationController = vectorAnimationController;
            return;
        }
        int i2 = this.mAnimationId;
        if (i2 != 0) {
            Context context2 = this.mContext;
            final MediaAnimationController mediaAnimationController =
                    new MediaAnimationController();
            MediaPlayer create =
                    MediaPlayer.create(
                            context2,
                            new Uri.Builder()
                                    .scheme("android.resource")
                                    .authority(context2.getPackageName())
                                    .appendPath(String.valueOf(i2))
                                    .build());
            mediaAnimationController.mMediaPlayer = create;
            if (create != null) {
                create.seekTo(0);
                mediaAnimationController.mMediaPlayer.setOnSeekCompleteListener(
                        new MediaPlayer
                                .OnSeekCompleteListener() { // from class:
                                                            // com.android.settings.widget.MediaAnimationController$$ExternalSyntheticLambda0
                            @Override // android.media.MediaPlayer.OnSeekCompleteListener
                            public final void onSeekComplete(MediaPlayer mediaPlayer) {
                                MediaAnimationController.this.mVideoReady = true;
                            }
                        });
                mediaAnimationController.mMediaPlayer.setOnPreparedListener(
                        new MediaAnimationController$$ExternalSyntheticLambda1());
            }
            this.mAnimationController = mediaAnimationController;
            TextureView textureView = this.mVideo;
            if (textureView != null) {
                mediaAnimationController.attachView(
                        textureView, this.mPreviewImage, this.mPlayButton);
            }
        }
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        if (this.mAnimationAvailable) {
            this.mVideo = (TextureView) preferenceViewHolder.findViewById(R.id.video_texture_view);
            this.mPreviewImage =
                    (ImageView) preferenceViewHolder.findViewById(R.id.video_preview_image);
            this.mPlayButton =
                    (ImageView) preferenceViewHolder.findViewById(R.id.video_play_button);
            AspectRatioFrameLayout aspectRatioFrameLayout =
                    (AspectRatioFrameLayout)
                            preferenceViewHolder.findViewById(R.id.video_container);
            this.mPreviewImage.setImageResource(this.mPreviewId);
            aspectRatioFrameLayout.setAspectRatio(this.mAspectRatio);
            if (this.mHeight >= -1) {
                aspectRatioFrameLayout.setLayoutParams(
                        new LinearLayout.LayoutParams(-1, this.mHeight));
            }
            AnimationController animationController = this.mAnimationController;
            if (animationController != null) {
                animationController.attachView(this.mVideo, this.mPreviewImage, this.mPlayButton);
            }
        }
    }

    @Override // androidx.preference.Preference
    public final void onDetached() {
        releaseAnimationController();
        super.onDetached();
    }

    public final void releaseAnimationController() {
        AnimationController animationController = this.mAnimationController;
        if (animationController != null) {
            animationController.release();
            this.mAnimationController = null;
        }
    }

    public void updateAspectRatio() {
        this.mAspectRatio =
                this.mAnimationController.getVideoWidth()
                        / this.mAnimationController.getVideoHeight();
    }
}
