package com.google.android.setupdesign.items;

import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.InflateException;

import com.android.settings.sim.ChooseSimActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class SimpleInflater {
    public final Resources resources;

    public SimpleInflater(Resources resources) {
        this.resources = resources;
    }

    public final Object createItemFromTag(String str, AttributeSet attributeSet) {
        try {
            return onCreateItem(str, attributeSet);
        } catch (InflateException e) {
            throw e;
        } catch (Exception e2) {
            throw new InflateException(
                    attributeSet.getPositionDescription() + ": Error inflating class " + str, e2);
        }
    }

    public final Object inflate(XmlPullParser xmlPullParser) {
        int next;
        int next2;
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
        Object createItemFromTag = createItemFromTag(xmlPullParser.getName(), asAttributeSet);
        int depth = xmlPullParser.getDepth();
        do {
            next2 = xmlPullParser.next();
            if ((next2 == 3 && xmlPullParser.getDepth() <= depth) || next2 == 1) {
                return createItemFromTag;
            }
        } while (next2 != 2);
        throw new IllegalArgumentException(
                "Cannot add child item to "
                        + ((ChooseSimActivity.DisableableItem) createItemFromTag));
    }

    public abstract Object onCreateItem(String str, AttributeSet attributeSet);
}
