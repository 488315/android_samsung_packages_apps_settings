package com.samsung.android.settings.wifi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.SettingsActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.samsung.android.settings.logging.SALogging;
import com.sec.ims.settings.ImsProfile;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class QrCodeFragment extends Fragment {
    public static final boolean DBG = Debug.semIsProductDev();
    public final String TAG = getLogTag();
    public FragmentActivity mActivity;
    public Context mContext;
    public boolean mIsSetupWizard;
    public Bitmap mQrBmp;
    public WifiQrCodeGenerator mQrCodeGenerator;
    public ImageView mQrImageView;
    public Button mQuickShareButton;
    public String mSsid;
    public TextView mSsidView;
    public TextView mSummaryView;
    public Toast mToast;
    public View mView;
    public WifiManager mWifiManager;

    public final Bitmap addWhiteBorder(Bitmap bitmap) {
        int width = bitmap.getWidth() < bitmap.getHeight() ? bitmap.getWidth() : bitmap.getHeight();
        int dimensionPixelSize =
                getResources()
                        .getDimensionPixelSize(R.dimen.sec_wifi_qrcode_download_image_border_size);
        int i = width + dimensionPixelSize;
        Bitmap createBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawColor(-1);
        float f = dimensionPixelSize / 2.0f;
        canvas.drawBitmap(bitmap, f, f, (Paint) null);
        return createBitmap;
    }

    public abstract String getLogTag();

    public final Bitmap getQrBitmap() {
        LinearLayout linearLayout = (LinearLayout) this.mView.findViewById(R.id.qr_save_area);
        if (linearLayout == null) {
            return null;
        }
        TextView textView = this.mSsidView;
        if (textView != null) {
            textView.setTextColor(getResources().getColor(R.color.sec_wifi_qr_black));
        }
        Bitmap createBitmap =
                Bitmap.createBitmap(
                        linearLayout.getWidth(), linearLayout.getHeight(), Bitmap.Config.ARGB_8888);
        linearLayout.draw(new Canvas(createBitmap));
        TextView textView2 = this.mSsidView;
        if (textView2 != null) {
            textView2.setTextColor(getResources().getColor(R.color.wifi_ap_qrcode_page_ssid_color));
        }
        return addWhiteBorder(createBitmap);
    }

    public abstract String getQrCodeString();

    public abstract void initValuesFromIntent(Bundle bundle);

    @Override // androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.mWifiManager =
                (WifiManager)
                        this.mActivity
                                .getApplicationContext()
                                .getSystemService(ImsProfile.PDN_WIFI);
        this.mQrImageView = (ImageView) this.mView.findViewById(R.id.qr_image);
        this.mSsidView = (TextView) this.mView.findViewById(R.id.ap_ssid);
        this.mSummaryView = (TextView) this.mView.findViewById(R.id.qr_summary);
        this.mQuickShareButton = (Button) this.mView.findViewById(R.id.quick_share_button);
        this.mQrCodeGenerator = new WifiQrCodeGenerator(this.mActivity);
    }

    @Override // androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentActivity activity = getActivity();
        this.mActivity = activity;
        this.mContext = activity.getApplicationContext();
        Bundle extras = this.mActivity.getIntent().getExtras();
        if (extras.getParcelable("key_config") != null) {
            initValuesFromIntent(extras);
            return;
        }
        Log.d(this.TAG, "Finish - no config to show");
        Log.d(this.TAG, "popOrFinishThisActivity");
        FragmentActivity activity2 = getActivity();
        if (activity2 instanceof SettingsActivity) {
            ((SettingsActivity) activity2).finishPreferencePanel(null);
        } else {
            activity2.finish();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        Bitmap bitmap = this.mQrBmp;
        if (bitmap != null) {
            bitmap.recycle();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        SALogging.insertSALog("WIFI_280");
        updateView();
        if (this.mIsSetupWizard) {
            if (DBG) {
                Log.d(this.TAG, "skip init bottom bar for setup wizard");
            }
        } else {
            BottomNavigationView bottomNavigationView =
                    (BottomNavigationView) this.mView.findViewById(R.id.bottom_navigation);
            if (bottomNavigationView != null) {
                bottomNavigationView.menu.clear();
                bottomNavigationView.inflateMenu(R.menu.wifi_qrcode_bottom_menu);
                bottomNavigationView.selectedListener =
                        new BottomNavigationView.OnNavigationItemSelectedListener() { // from class:
                            // com.samsung.android.settings.wifi.QrCodeFragment$$ExternalSyntheticLambda0
                            /* JADX WARN: Removed duplicated region for block: B:33:0x011a  */
                            @Override // com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public final boolean onNavigationItemSelected(
                                    android.view.MenuItem r13) {
                                /*
                                    Method dump skipped, instructions count: 446
                                    To view this dump change 'Code comments level' option to 'DEBUG'
                                */
                                throw new UnsupportedOperationException(
                                        "Method not decompiled:"
                                            + " com.samsung.android.settings.wifi.QrCodeFragment$$ExternalSyntheticLambda0.onNavigationItemSelected(android.view.MenuItem):boolean");
                            }
                        };
            }
        }
    }

    public final void setupQrImageView() {
        ImageView imageView;
        Bitmap bitmap = this.mQrBmp;
        if (bitmap != null) {
            bitmap.recycle();
        }
        Bitmap createQrcodeImage =
                this.mQrCodeGenerator.createQrcodeImage(
                        getQrCodeString(), this instanceof WifiQrCodeFragment);
        this.mQrBmp = createQrcodeImage;
        if (createQrcodeImage == null || (imageView = this.mQrImageView) == null) {
            return;
        }
        imageView.setImageBitmap(createQrcodeImage);
    }

    public abstract void updateView();
}
