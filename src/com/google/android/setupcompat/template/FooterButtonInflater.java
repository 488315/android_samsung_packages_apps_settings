package com.google.android.setupcompat.template;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.InflateException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class FooterButtonInflater {
    public final Context context;

    public FooterButtonInflater(Context context) {
        this.context = context;
    }

    public final FooterButton inflate(XmlPullParser xmlPullParser) {
        int next;
        AttributeSet asAttributeSet = Xml.asAttributeSet(xmlPullParser);
        do {
            try {
                next = xmlPullParser.next();
                if (next == 2) {
                    break;
                }
            } catch (IOException e) {
                throw new InflateException(
                        xmlPullParser.getPositionDescription() + ": " + e.getMessage(), e);
            } catch (XmlPullParserException e2) {
                throw new InflateException(e2.getMessage(), e2);
            }
        } while (next != 1);
        if (next != 2) {
            throw new InflateException(
                    xmlPullParser.getPositionDescription() + ": No start tag found!");
        }
        if (xmlPullParser.getName().equals("FooterButton")) {
            return new FooterButton(this.context, asAttributeSet);
        }
        throw new InflateException(xmlPullParser.getPositionDescription() + ": not a FooterButton");
    }
}
