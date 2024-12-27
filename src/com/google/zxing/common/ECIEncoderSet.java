package com.google.zxing.common;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ECIEncoderSet {
    public static final List ENCODERS = new ArrayList();
    public final CharsetEncoder[] encoders;
    public final int priorityEncoderIndex;

    static {
        String[] strArr = {
            "IBM437",
            "ISO-8859-2",
            "ISO-8859-3",
            "ISO-8859-4",
            "ISO-8859-5",
            "ISO-8859-6",
            "ISO-8859-7",
            "ISO-8859-8",
            "ISO-8859-9",
            "ISO-8859-10",
            "ISO-8859-11",
            "ISO-8859-13",
            "ISO-8859-14",
            "ISO-8859-15",
            "ISO-8859-16",
            "windows-1250",
            "windows-1251",
            "windows-1252",
            "windows-1256",
            "Shift_JIS"
        };
        for (int i = 0; i < 20; i++) {
            String str = strArr[i];
            if (((CharacterSetECI) ((HashMap) CharacterSetECI.NAME_TO_ECI).get(str)) != null) {
                try {
                    ENCODERS.add(Charset.forName(str).newEncoder());
                } catch (UnsupportedCharsetException unused) {
                }
            }
        }
    }

    public ECIEncoderSet(String str, Charset charset, int i) {
        boolean z;
        ArrayList arrayList = new ArrayList();
        arrayList.add(StandardCharsets.ISO_8859_1.newEncoder());
        int i2 = 0;
        boolean z2 = charset != null && charset.name().startsWith("UTF");
        for (int i3 = 0; i3 < str.length(); i3++) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                CharsetEncoder charsetEncoder = (CharsetEncoder) it.next();
                char charAt = str.charAt(i3);
                if (charAt == i || charsetEncoder.canEncode(charAt)) {
                    z = true;
                    break;
                }
            }
            z = false;
            if (!z) {
                Iterator it2 = ((ArrayList) ENCODERS).iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    CharsetEncoder charsetEncoder2 = (CharsetEncoder) it2.next();
                    if (charsetEncoder2.canEncode(str.charAt(i3))) {
                        arrayList.add(charsetEncoder2);
                        z = true;
                        break;
                    }
                }
            }
            if (!z) {
                z2 = true;
            }
        }
        if (arrayList.size() != 1 || z2) {
            this.encoders = new CharsetEncoder[arrayList.size() + 2];
            Iterator it3 = arrayList.iterator();
            int i4 = 0;
            while (it3.hasNext()) {
                this.encoders[i4] = (CharsetEncoder) it3.next();
                i4++;
            }
            this.encoders[i4] = StandardCharsets.UTF_8.newEncoder();
            this.encoders[i4 + 1] = StandardCharsets.UTF_16BE.newEncoder();
        } else {
            this.encoders = new CharsetEncoder[] {(CharsetEncoder) arrayList.get(0)};
        }
        if (charset != null) {
            while (true) {
                CharsetEncoder[] charsetEncoderArr = this.encoders;
                if (i2 >= charsetEncoderArr.length) {
                    break;
                }
                if (charsetEncoderArr[i2] != null
                        && charset.name().equals(this.encoders[i2].charset().name())) {
                    break;
                } else {
                    i2++;
                }
            }
        }
        i2 = -1;
        this.priorityEncoderIndex = i2;
    }

    public final boolean canEncode(char c, int i) {
        return this.encoders[i].canEncode(ApnSettings.MVNO_NONE + c);
    }
}
