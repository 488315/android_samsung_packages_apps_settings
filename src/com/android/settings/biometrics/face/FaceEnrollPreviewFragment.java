package com.android.settings.biometrics.face;

import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Size;
import android.util.TypedValue;
import android.view.Surface;
import android.view.TextureView;
import android.widget.ImageView;

import com.android.settings.R;
import com.android.settings.biometrics.BiometricEnrollSidecar;
import com.android.settings.core.InstrumentedPreferenceFragment;

import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FaceEnrollPreviewFragment extends InstrumentedPreferenceFragment
        implements BiometricEnrollSidecar.Listener {
    public FaceEnrollAnimationDrawable mAnimationDrawable;
    public CameraDevice mCameraDevice;
    public String mCameraId;
    public CameraManager mCameraManager;
    public CameraCaptureSession mCaptureSession;
    public ImageView mCircleView;
    public ParticleCollection.Listener mListener;
    public CaptureRequest mPreviewRequest;
    public CaptureRequest.Builder mPreviewRequestBuilder;
    public Size mPreviewSize;
    public FaceSquareTextureView mTextureView;
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public final AnonymousClass1 mAnimationListener =
            new ParticleCollection
                    .Listener() { // from class:
                                  // com.android.settings.biometrics.face.FaceEnrollPreviewFragment.1
                @Override // com.android.settings.biometrics.face.ParticleCollection.Listener
                public final void onEnrolled() {
                    FaceEnrollPreviewFragment.this.mListener.onEnrolled();
                }
            };
    public final AnonymousClass2 mSurfaceTextureListener =
            new TextureView
                    .SurfaceTextureListener() { // from class:
                                                // com.android.settings.biometrics.face.FaceEnrollPreviewFragment.2
                @Override // android.view.TextureView.SurfaceTextureListener
                public final void onSurfaceTextureAvailable(
                        SurfaceTexture surfaceTexture, int i, int i2) {
                    FaceEnrollPreviewFragment.this.openCamera(i, i2);
                }

                @Override // android.view.TextureView.SurfaceTextureListener
                public final boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                    return true;
                }

                @Override // android.view.TextureView.SurfaceTextureListener
                public final void onSurfaceTextureSizeChanged(
                        SurfaceTexture surfaceTexture, int i, int i2) {
                    FaceEnrollPreviewFragment.this.configureTransform(i, i2);
                }

                @Override // android.view.TextureView.SurfaceTextureListener
                public final void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {}
            };
    public final AnonymousClass3 mCameraStateCallback =
            new CameraDevice
                    .StateCallback() { // from class:
                                       // com.android.settings.biometrics.face.FaceEnrollPreviewFragment.3
                @Override // android.hardware.camera2.CameraDevice.StateCallback
                public final void onDisconnected(CameraDevice cameraDevice) {
                    cameraDevice.close();
                    FaceEnrollPreviewFragment.this.mCameraDevice = null;
                }

                @Override // android.hardware.camera2.CameraDevice.StateCallback
                public final void onError(CameraDevice cameraDevice, int i) {
                    cameraDevice.close();
                    FaceEnrollPreviewFragment.this.mCameraDevice = null;
                }

                @Override // android.hardware.camera2.CameraDevice.StateCallback
                public final void onOpened(CameraDevice cameraDevice) {
                    FaceEnrollPreviewFragment faceEnrollPreviewFragment =
                            FaceEnrollPreviewFragment.this;
                    faceEnrollPreviewFragment.mCameraDevice = cameraDevice;
                    try {
                        SurfaceTexture surfaceTexture =
                                faceEnrollPreviewFragment.mTextureView.getSurfaceTexture();
                        surfaceTexture.setDefaultBufferSize(
                                FaceEnrollPreviewFragment.this.mPreviewSize.getWidth(),
                                FaceEnrollPreviewFragment.this.mPreviewSize.getHeight());
                        Surface surface = new Surface(surfaceTexture);
                        FaceEnrollPreviewFragment faceEnrollPreviewFragment2 =
                                FaceEnrollPreviewFragment.this;
                        faceEnrollPreviewFragment2.mPreviewRequestBuilder =
                                faceEnrollPreviewFragment2.mCameraDevice.createCaptureRequest(1);
                        FaceEnrollPreviewFragment.this.mPreviewRequestBuilder.addTarget(surface);
                        FaceEnrollPreviewFragment.this.mCameraDevice.createCaptureSession(
                                Arrays.asList(surface),
                                new CameraCaptureSession
                                        .StateCallback() { // from class:
                                                           // com.android.settings.biometrics.face.FaceEnrollPreviewFragment.3.1
                                    @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
                                    public final void onConfigureFailed(
                                            CameraCaptureSession cameraCaptureSession) {
                                        Log.e(
                                                "FaceEnrollPreviewFragment",
                                                "Unable to configure camera");
                                    }

                                    @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
                                    public final void onConfigured(
                                            CameraCaptureSession cameraCaptureSession) {
                                        FaceEnrollPreviewFragment faceEnrollPreviewFragment3 =
                                                FaceEnrollPreviewFragment.this;
                                        if (faceEnrollPreviewFragment3.mCameraDevice == null) {
                                            return;
                                        }
                                        faceEnrollPreviewFragment3.mCaptureSession =
                                                cameraCaptureSession;
                                        try {
                                            faceEnrollPreviewFragment3.mPreviewRequestBuilder.set(
                                                    CaptureRequest.CONTROL_AF_MODE, 4);
                                            FaceEnrollPreviewFragment faceEnrollPreviewFragment4 =
                                                    FaceEnrollPreviewFragment.this;
                                            faceEnrollPreviewFragment4.mPreviewRequest =
                                                    faceEnrollPreviewFragment4
                                                            .mPreviewRequestBuilder.build();
                                            FaceEnrollPreviewFragment faceEnrollPreviewFragment5 =
                                                    FaceEnrollPreviewFragment.this;
                                            faceEnrollPreviewFragment5.mCaptureSession
                                                    .setRepeatingRequest(
                                                            faceEnrollPreviewFragment5
                                                                    .mPreviewRequest,
                                                            null,
                                                            faceEnrollPreviewFragment5.mHandler);
                                        } catch (CameraAccessException e) {
                                            Log.e(
                                                    "FaceEnrollPreviewFragment",
                                                    "Unable to access camera",
                                                    e);
                                        }
                                    }
                                },
                                null);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }
            };

    public final void configureTransform(int i, int i2) {
        if (this.mTextureView == null) {
            return;
        }
        float width = i / this.mPreviewSize.getWidth();
        float height = i2 / this.mPreviewSize.getHeight();
        float min = Math.min(width, height);
        float f = width / min;
        float f2 = height / min;
        TypedValue typedValue = new TypedValue();
        TypedValue typedValue2 = new TypedValue();
        TypedValue typedValue3 = new TypedValue();
        getResources().getValue(R.dimen.face_preview_translate_x, typedValue, true);
        getResources().getValue(R.dimen.face_preview_translate_y, typedValue2, true);
        getResources().getValue(R.dimen.face_preview_scale, typedValue3, true);
        Matrix matrix = new Matrix();
        this.mTextureView.getTransform(matrix);
        matrix.setScale(typedValue3.getFloat() * f, typedValue3.getFloat() * f2);
        matrix.postTranslate(typedValue.getFloat(), typedValue2.getFloat());
        this.mTextureView.setTransform(matrix);
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1554;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mTextureView = (FaceSquareTextureView) getActivity().findViewById(R.id.texture_view);
        ImageView imageView = (ImageView) getActivity().findViewById(R.id.circle_view);
        this.mCircleView = imageView;
        imageView.setLayerType(1, null);
        FaceEnrollAnimationDrawable faceEnrollAnimationDrawable =
                new FaceEnrollAnimationDrawable(getContext(), this.mAnimationListener);
        this.mAnimationDrawable = faceEnrollAnimationDrawable;
        this.mCircleView.setImageDrawable(faceEnrollAnimationDrawable);
        this.mCameraManager = (CameraManager) getContext().getSystemService("camera");
    }

    @Override // com.android.settings.biometrics.BiometricEnrollSidecar.Listener
    public final void onEnrollmentError(int i, CharSequence charSequence) {
        this.mAnimationDrawable.onEnrollmentError(i, charSequence);
    }

    @Override // com.android.settings.biometrics.BiometricEnrollSidecar.Listener
    public final void onEnrollmentHelp(int i, CharSequence charSequence) {
        this.mAnimationDrawable.onEnrollmentHelp(i, charSequence);
    }

    @Override // com.android.settings.biometrics.BiometricEnrollSidecar.Listener
    public final void onEnrollmentProgressChange(int i, int i2) {
        this.mAnimationDrawable.onEnrollmentProgressChange(i, i2);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        CameraCaptureSession cameraCaptureSession = this.mCaptureSession;
        if (cameraCaptureSession != null) {
            cameraCaptureSession.close();
            this.mCaptureSession = null;
        }
        CameraDevice cameraDevice = this.mCameraDevice;
        if (cameraDevice != null) {
            cameraDevice.close();
            this.mCameraDevice = null;
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        if (this.mTextureView.isAvailable()) {
            openCamera(this.mTextureView.getWidth(), this.mTextureView.getHeight());
        } else {
            this.mTextureView.setSurfaceTextureListener(this.mSurfaceTextureListener);
        }
    }

    public final void openCamera(int i, int i2) {
        try {
            setUpCameraOutputs();
            this.mCameraManager.openCamera(
                    this.mCameraId, this.mCameraStateCallback, this.mHandler);
            configureTransform(i, i2);
        } catch (CameraAccessException e) {
            Log.e("FaceEnrollPreviewFragment", "Unable to open camera", e);
        }
    }

    public final void setUpCameraOutputs() {
        Size size;
        try {
            for (String str : this.mCameraManager.getCameraIdList()) {
                CameraCharacteristics cameraCharacteristics =
                        this.mCameraManager.getCameraCharacteristics(str);
                Integer num =
                        (Integer) cameraCharacteristics.get(CameraCharacteristics.LENS_FACING);
                if (num != null && num.intValue() == 0) {
                    this.mCameraId = str;
                    Size[] outputSizes =
                            ((StreamConfigurationMap)
                                            cameraCharacteristics.get(
                                                    CameraCharacteristics
                                                            .SCALER_STREAM_CONFIGURATION_MAP))
                                    .getOutputSizes(SurfaceTexture.class);
                    int i = 0;
                    while (true) {
                        if (i >= outputSizes.length) {
                            Log.w("FaceEnrollPreviewFragment", "Unable to find a good resolution");
                            size = outputSizes[0];
                            break;
                        } else {
                            if (outputSizes[i].getHeight() == 1080
                                    && outputSizes[i].getWidth() == 1920) {
                                size = outputSizes[i];
                                break;
                            }
                            i++;
                        }
                    }
                    this.mPreviewSize = size;
                    return;
                }
            }
        } catch (CameraAccessException e) {
            Log.e("FaceEnrollPreviewFragment", "Unable to access camera", e);
        }
    }
}
