package com.android.settingslib.widget;

import android.R;
import android.content.res.Resources;
import android.graphics.Path;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.util.AttributeSet;
import android.util.PathParser;

import org.xmlpull.v1.XmlPullParser;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AdaptiveIconShapeDrawable extends ShapeDrawable {
    public AdaptiveIconShapeDrawable() {}

    @Override // android.graphics.drawable.ShapeDrawable, android.graphics.drawable.Drawable
    public final void inflate(
            Resources resources,
            XmlPullParser xmlPullParser,
            AttributeSet attributeSet,
            Resources.Theme theme) {
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        setShape(
                new PathShape(
                        new Path(
                                PathParser.createPathFromPathData(
                                        resources.getString(
                                                R.string.elapsed_time_short_format_h_mm_ss))),
                        100.0f,
                        100.0f));
    }

    public AdaptiveIconShapeDrawable(Resources resources) {
        setShape(
                new PathShape(
                        new Path(
                                PathParser.createPathFromPathData(
                                        resources.getString(
                                                R.string.elapsed_time_short_format_h_mm_ss))),
                        100.0f,
                        100.0f));
    }
}
