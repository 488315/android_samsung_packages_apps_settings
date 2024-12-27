package com.samsung.android.settings.deviceinfo.legalinfo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ChinaWireRegulation extends SettingsPreferenceFragment {
    public TextView mLegalTextView;

    public static String convertStreamToString(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
        } catch (IOException e) {
            Log.e("ChinaWireRegulation", e.getMessage());
        }
        try {
            try {
                int read = inputStream.read();
                while (read != -1) {
                    byteArrayOutputStream.write(read);
                    read = inputStream.read();
                }
                inputStream.close();
            } catch (IOException e2) {
                Log.e("ChinaWireRegulation", e2.getMessage());
                inputStream.close();
            }
            return byteArrayOutputStream.toString();
        } catch (Throwable th) {
            try {
                inputStream.close();
            } catch (IOException e3) {
                Log.e("ChinaWireRegulation", e3.getMessage());
            }
            throw th;
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 9560;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        String str;
        InputStream inputStream = null;
        inputStream = null;
        View inflate =
                layoutInflater.inflate(R.layout.sec_china_wireless_regulations, (ViewGroup) null);
        this.mLegalTextView = (TextView) inflate.findViewById(R.id.china_wireless_regulations_text);
        try {
            try {
                inputStream =
                        getContext().getAssets().open("chinese_wireless_regulations/legal.txt");
                str = convertStreamToString(inputStream);
                inputStream = inputStream;
                if (inputStream != null) {
                    try {
                        inputStream.close();
                        inputStream = inputStream;
                    } catch (IOException e) {
                        String message = e.getMessage();
                        Log.e("ChinaWireRegulation", message);
                        inputStream = message;
                    }
                }
            } catch (Exception e2) {
                Log.e("ChinaWireRegulation", e2.getMessage());
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e3) {
                        Log.e("ChinaWireRegulation", e3.getMessage());
                    }
                }
                str = ApnSettings.MVNO_NONE;
                inputStream = inputStream;
            }
            this.mLegalTextView.setText(str);
            return inflate;
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e4) {
                    Log.e("ChinaWireRegulation", e4.getMessage());
                }
            }
            throw th;
        }
    }
}
