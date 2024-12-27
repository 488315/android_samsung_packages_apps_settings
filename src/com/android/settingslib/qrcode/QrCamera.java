package com.android.settingslib.qrcode;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Size;
import android.view.WindowManager;

import androidx.preference.Preference;
import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.InvertedLuminanceSource;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class QrCamera extends Handler {
    public static final Map HINTS;
    Camera mCamera;
    public int mCameraOrientation;
    public final WeakReference mContext;
    public DecodingTask mDecodeTask;
    Camera.Parameters mParameters;
    public Size mPreviewSize;
    public final MultiFormatReader mReader;
    public final ScannerCallback mScannerCallback;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DecodingTask extends AsyncTask {
        public QrYuvLuminanceSource mImage;
        public final SurfaceTexture mSurface;

        public DecodingTask(SurfaceTexture surfaceTexture) {
            this.mSurface = surfaceTexture;
        }

        public final Result decodeQrCode(LuminanceSource luminanceSource) {
            try {
                MultiFormatReader multiFormatReader = QrCamera.this.mReader;
                BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(luminanceSource));
                if (multiFormatReader.readers == null) {
                    multiFormatReader.setHints(null);
                }
                return multiFormatReader.decodeInternal(binaryBitmap);
            } catch (ReaderException unused) {
                return null;
            } finally {
                QrCamera.this.mReader.reset();
            }
        }

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            SurfaceTexture surfaceTexture = this.mSurface;
            int numberOfCameras = Camera.getNumberOfCameras();
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            int i = 0;
            while (true) {
                if (i >= numberOfCameras) {
                    break;
                }
                try {
                    Camera.getCameraInfo(i, cameraInfo);
                    if (cameraInfo.facing == 0) {
                        QrCamera qrCamera = QrCamera.this;
                        Camera camera = qrCamera.mCamera;
                        if (camera != null) {
                            camera.release();
                            qrCamera.mCamera = null;
                        }
                        QrCamera.this.mCamera = Camera.open(i);
                        QrCamera.this.mCameraOrientation = cameraInfo.orientation;
                    } else {
                        i++;
                    }
                } catch (RuntimeException e) {
                    Log.e("QrCamera", "Fail to open camera: " + e);
                    QrCamera qrCamera2 = QrCamera.this;
                    qrCamera2.mCamera = null;
                    qrCamera2.mScannerCallback.handleCameraFailure();
                    return null;
                }
            }
            if (QrCamera.this.mCamera == null && numberOfCameras > 0) {
                Log.i("QrCamera", "Can't find back camera. Opening a different camera");
                Camera.getCameraInfo(0, cameraInfo);
                QrCamera qrCamera3 = QrCamera.this;
                Camera camera2 = qrCamera3.mCamera;
                if (camera2 != null) {
                    camera2.release();
                    qrCamera3.mCamera = null;
                }
                QrCamera.this.mCamera = Camera.open(0);
                QrCamera.this.mCameraOrientation = cameraInfo.orientation;
            }
            try {
                Camera camera3 = QrCamera.this.mCamera;
                if (camera3 == null) {
                    throw new IOException("Cannot find available camera");
                }
                camera3.setPreviewTexture(surfaceTexture);
                QrCamera.this.setCameraParameter();
                QrCamera.m1033$$Nest$msetTransformationMatrix(QrCamera.this);
                if (!QrCamera.m1034$$Nest$mstartPreview(QrCamera.this)) {
                    throw new IOException("Lost contex");
                }
                final Semaphore semaphore = new Semaphore(0);
                while (true) {
                    QrCamera.this.mCamera.setOneShotPreviewCallback(
                            new Camera.PreviewCallback() { // from class:
                                // com.android.settingslib.qrcode.QrCamera$DecodingTask$$ExternalSyntheticLambda1
                                @Override // android.hardware.Camera.PreviewCallback
                                public final void onPreviewFrame(byte[] bArr, Camera camera4) {
                                    QrCamera.DecodingTask decodingTask = QrCamera.DecodingTask.this;
                                    Semaphore semaphore2 = semaphore;
                                    QrCamera qrCamera4 = QrCamera.this;
                                    Rect framePosition =
                                            qrCamera4.mScannerCallback.getFramePosition(
                                                    qrCamera4.mPreviewSize);
                                    int width = qrCamera4.mPreviewSize.getWidth();
                                    int height = qrCamera4.mPreviewSize.getHeight();
                                    int i2 = framePosition.left;
                                    int i3 = framePosition.top;
                                    int width2 = framePosition.width();
                                    int height2 = framePosition.height();
                                    byte[] bArr2 = new byte[width2 * height2];
                                    int i4 = (i3 * width) + i2;
                                    if (i2 + width2 > width || i3 + height2 > height) {
                                        throw new IllegalArgumentException(
                                                "cropped rectangle does not fit within image"
                                                    + " data.");
                                    }
                                    for (int i5 = 0; i5 < height2; i5++) {
                                        System.arraycopy(bArr, i4, bArr2, i5 * width2, width2);
                                        i4 += width;
                                    }
                                    decodingTask.mImage =
                                            new QrYuvLuminanceSource(bArr2, width2, height2);
                                    semaphore2.release();
                                }
                            });
                    try {
                        semaphore.acquire();
                        Result decodeQrCode = decodeQrCode(this.mImage);
                        if (decodeQrCode == null) {
                            QrYuvLuminanceSource qrYuvLuminanceSource = this.mImage;
                            qrYuvLuminanceSource.getClass();
                            decodeQrCode =
                                    decodeQrCode(new InvertedLuminanceSource(qrYuvLuminanceSource));
                        }
                        if (decodeQrCode != null) {
                            String str = decodeQrCode.text;
                            if (QrCamera.this.mScannerCallback.isValid(str)) {
                                return str;
                            }
                        }
                    } catch (InterruptedException unused) {
                        Thread.currentThread().interrupt();
                        return null;
                    }
                }
            } catch (IOException e2) {
                QrCamera$DecodingTask$$ExternalSyntheticOutline0.m(
                        "Fail to startPreview camera: ", e2, "QrCamera");
                QrCamera qrCamera4 = QrCamera.this;
                qrCamera4.mCamera = null;
                qrCamera4.mScannerCallback.handleCameraFailure();
                return null;
            }
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            String str = (String) obj;
            if (str != null) {
                QrCamera.this.mScannerCallback.handleSuccessfulResult(str);
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface ScannerCallback {
        Rect getFramePosition(Size size);

        Size getViewSize();

        void handleCameraFailure();

        void handleSuccessfulResult(String str);

        boolean isValid(String str);

        void setTransform(Matrix matrix);
    }

    /* renamed from: -$$Nest$msetTransformationMatrix, reason: not valid java name */
    public static void m1033$$Nest$msetTransformationMatrix(QrCamera qrCamera) {
        float f;
        boolean z =
                ((Context) qrCamera.mContext.get()).getResources().getConfiguration().orientation
                        == 1;
        Size size = qrCamera.mPreviewSize;
        int width = z ? size.getWidth() : size.getHeight();
        int height = z ? qrCamera.mPreviewSize.getHeight() : qrCamera.mPreviewSize.getWidth();
        float ratio = (float) getRatio(width, height);
        float f2 = 1.0f;
        if (width > height) {
            f = 1.0f / ratio;
        } else {
            f2 = 1.0f / ratio;
            f = 1.0f;
        }
        Matrix matrix = new Matrix();
        matrix.setScale(f2, f);
        qrCamera.mScannerCallback.setTransform(matrix);
    }

    /* renamed from: -$$Nest$mstartPreview, reason: not valid java name */
    public static boolean m1034$$Nest$mstartPreview(QrCamera qrCamera) {
        int i = 0;
        if (qrCamera.mContext.get() == null) {
            return false;
        }
        int rotation =
                ((WindowManager) ((Context) qrCamera.mContext.get()).getSystemService("window"))
                        .getDefaultDisplay()
                        .getRotation();
        if (rotation != 0) {
            if (rotation == 1) {
                i = 90;
            } else if (rotation == 2) {
                i = 180;
            } else if (rotation == 3) {
                i = 270;
            }
        }
        qrCamera.mCamera.setDisplayOrientation(((qrCamera.mCameraOrientation - i) + 360) % 360);
        qrCamera.mCamera.startPreview();
        if ("auto".equals(qrCamera.mParameters.getFocusMode())) {
            qrCamera.mCamera.autoFocus(null);
            qrCamera.sendMessageDelayed(qrCamera.obtainMessage(1), 1500L);
        }
        return true;
    }

    static {
        ArrayMap arrayMap = new ArrayMap();
        HINTS = arrayMap;
        ArrayList arrayList = new ArrayList();
        arrayList.add(BarcodeFormat.QR_CODE);
        arrayMap.put(DecodeHintType.POSSIBLE_FORMATS, arrayList);
    }

    public QrCamera(Context context, ScannerCallback scannerCallback) {
        this.mContext = new WeakReference(context);
        this.mScannerCallback = scannerCallback;
        MultiFormatReader multiFormatReader = new MultiFormatReader();
        this.mReader = multiFormatReader;
        multiFormatReader.setHints(HINTS);
    }

    public static double getRatio(double d, double d2) {
        return d < d2 ? d / d2 : d2 / d;
    }

    public void decodeImage(BinaryBitmap binaryBitmap) {
        MultiFormatReader multiFormatReader = this.mReader;
        Result result = null;
        try {
            if (multiFormatReader.readers == null) {
                multiFormatReader.setHints(null);
            }
            result = multiFormatReader.decodeInternal(binaryBitmap);
        } catch (ReaderException unused) {
        } catch (Throwable th) {
            multiFormatReader.reset();
            throw th;
        }
        multiFormatReader.reset();
        if (result != null) {
            this.mScannerCallback.handleSuccessfulResult(result.text);
        }
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        if (message.what != 1) {
            Preference$$ExternalSyntheticOutline0.m(
                    new StringBuilder("Unexpected Message: "), message.what, "QrCamera");
        } else {
            this.mCamera.autoFocus(null);
            sendMessageDelayed(obtainMessage(1), 1500L);
        }
    }

    public void setCameraParameter() {
        Size size;
        Camera.Parameters parameters = this.mCamera.getParameters();
        this.mParameters = parameters;
        Size viewSize = this.mScannerCallback.getViewSize();
        double ratio = getRatio(viewSize.getWidth(), viewSize.getHeight());
        Size size2 = new Size(0, 0);
        double d = 0.0d;
        for (Camera.Size size3 : parameters.getSupportedPreviewSizes()) {
            double ratio2 = getRatio(size3.width, size3.height);
            if (size3.height * size3.width > size2.getHeight() * size2.getWidth()
                    && (Math.abs(d - ratio) / ratio > 0.1d
                            || Math.abs(ratio2 - ratio) / ratio <= 0.1d)) {
                size2 = new Size(size3.width, size3.height);
                d = getRatio(size3.width, size3.height);
            }
        }
        this.mPreviewSize = size2;
        this.mParameters.setPreviewSize(size2.getWidth(), this.mPreviewSize.getHeight());
        Camera.Parameters parameters2 = this.mParameters;
        Camera.Size previewSize = parameters2.getPreviewSize();
        double ratio3 = getRatio(previewSize.width, previewSize.height);
        ArrayList<Size> arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Camera.Size size4 : parameters2.getSupportedPictureSizes()) {
            double ratio4 = getRatio(size4.width, size4.height);
            if (ratio4 == ratio3) {
                arrayList.add(new Size(size4.width, size4.height));
            } else if (Math.abs(ratio4 - ratio3) < 0.1d) {
                arrayList2.add(new Size(size4.width, size4.height));
            }
        }
        if (arrayList.size() == 0 && arrayList2.size() == 0) {
            Log.d("QrCamera", "No proper picture size, return default picture size");
            Camera.Size pictureSize = parameters2.getPictureSize();
            size = new Size(pictureSize.width, pictureSize.height);
        } else {
            if (arrayList.size() == 0) {
                arrayList = arrayList2;
            }
            int i = previewSize.width * previewSize.height;
            int i2 = Preference.DEFAULT_ORDER;
            Size size5 = null;
            for (Size size6 : arrayList) {
                int abs = Math.abs((size6.getHeight() * size6.getWidth()) - i);
                if (abs < i2) {
                    size5 = size6;
                    i2 = abs;
                }
            }
            size = size5;
        }
        this.mParameters.setPictureSize(size.getWidth(), size.getHeight());
        List<String> supportedFlashModes = this.mParameters.getSupportedFlashModes();
        if (supportedFlashModes != null && supportedFlashModes.contains("off")) {
            this.mParameters.setFlashMode("off");
        }
        List<String> supportedFocusModes = this.mParameters.getSupportedFocusModes();
        if (supportedFocusModes.contains("continuous-picture")) {
            this.mParameters.setFocusMode("continuous-picture");
        } else if (supportedFocusModes.contains("auto")) {
            this.mParameters.setFocusMode("auto");
        }
        this.mCamera.setParameters(this.mParameters);
    }

    public final void start(SurfaceTexture surfaceTexture) {
        if (this.mDecodeTask == null) {
            DecodingTask decodingTask = new DecodingTask(surfaceTexture);
            this.mDecodeTask = decodingTask;
            decodingTask.executeOnExecutor(Executors.newSingleThreadExecutor(), new Void[0]);
        }
    }

    public final void stop() {
        removeMessages(1);
        DecodingTask decodingTask = this.mDecodeTask;
        if (decodingTask != null) {
            decodingTask.cancel(true);
            this.mDecodeTask = null;
        }
        Camera camera = this.mCamera;
        if (camera != null) {
            try {
                camera.stopPreview();
                Camera camera2 = this.mCamera;
                if (camera2 != null) {
                    camera2.release();
                    this.mCamera = null;
                }
            } catch (RuntimeException e) {
                Log.e("QrCamera", "Stop previewing camera failed:" + e);
                this.mCamera = null;
            }
        }
    }
}
