package com.google.zxing.common;

import com.google.zxing.FormatException;
import com.samsung.android.knox.net.apn.ApnSettings;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ECIStringBuilder {
    public StringBuilder currentBytes;
    public Charset currentCharset;
    public StringBuilder result;

    public ECIStringBuilder() {
        this.currentCharset = StandardCharsets.ISO_8859_1;
        this.currentBytes = new StringBuilder();
    }

    public final void append(char c) {
        this.currentBytes.append((char) (c & 255));
    }

    public final void appendECI(int i) {
        encodeCurrentBytesIfAny();
        CharacterSetECI characterSetECIByValue = CharacterSetECI.getCharacterSetECIByValue(i);
        if (characterSetECIByValue == null) {
            throw FormatException.getFormatInstance();
        }
        this.currentCharset = Charset.forName(characterSetECIByValue.name());
    }

    public final void encodeCurrentBytesIfAny() {
        Charset charset = this.currentCharset;
        Charset charset2 = StandardCharsets.ISO_8859_1;
        if (charset.equals(charset2)) {
            if (this.currentBytes.length() > 0) {
                StringBuilder sb = this.result;
                if (sb == null) {
                    this.result = this.currentBytes;
                    this.currentBytes = new StringBuilder();
                    return;
                } else {
                    sb.append((CharSequence) this.currentBytes);
                    this.currentBytes = new StringBuilder();
                    return;
                }
            }
            return;
        }
        if (this.currentBytes.length() > 0) {
            byte[] bytes = this.currentBytes.toString().getBytes(charset2);
            this.currentBytes = new StringBuilder();
            StringBuilder sb2 = this.result;
            if (sb2 == null) {
                this.result = new StringBuilder(new String(bytes, this.currentCharset));
            } else {
                sb2.append(new String(bytes, this.currentCharset));
            }
        }
    }

    public final String toString() {
        encodeCurrentBytesIfAny();
        StringBuilder sb = this.result;
        return sb == null ? ApnSettings.MVNO_NONE : sb.toString();
    }

    public ECIStringBuilder(int i) {
        this.currentCharset = StandardCharsets.ISO_8859_1;
        this.currentBytes = new StringBuilder(i);
    }
}
