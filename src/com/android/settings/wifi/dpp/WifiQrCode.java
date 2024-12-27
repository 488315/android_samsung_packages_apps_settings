package com.android.settings.wifi.dpp;

import android.net.wifi.UriParserResults;
import android.net.wifi.WifiUriParser;
import android.text.TextUtils;
import android.util.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiQrCode {
    public final String mQrCode;
    public final UriParserResults mUriParserResults;

    public WifiQrCode(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Empty QR code");
        }
        this.mQrCode = str;
        try {
            UriParserResults parseUri = WifiUriParser.parseUri(str);
            this.mUriParserResults = parseUri;
            Log.i("WifiQrCode", "mUriParserResults = " + parseUri);
        } catch (IllegalArgumentException unused) {
            throw new IllegalArgumentException("Invalid scheme");
        }
    }
}
