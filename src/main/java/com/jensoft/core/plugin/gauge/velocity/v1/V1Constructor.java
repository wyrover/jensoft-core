/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.velocity.v1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;

import com.jensoft.core.glyphmetrics.GeometryPath;
import com.jensoft.core.glyphmetrics.GlyphUtil;
import com.jensoft.core.palette.InputFonts;
import com.jensoft.core.palette.TangoPalette;
import com.jensoft.core.plugin.gauge.RadialGauge;
import com.jensoft.core.plugin.gauge.core.ConstructorGaugePainter;

public class V1Constructor extends ConstructorGaugePainter {

    public V1Constructor() {

    }

    @Override
    public void paintConstructor(Graphics2D g2d, RadialGauge radialGauge) {

        double centerX = getGauge().getWindow2D()
                .userToPixel(new Point2D.Double(getGauge().getX(), 0)).getX();
        double centerY = getGauge().getWindow2D()
                .userToPixel(new Point2D.Double(0, getGauge().getY())).getY();
        int radius = getGauge().getRadius();

        Arc2D arc2d = new Arc2D.Double(centerX - radius, centerY - radius,
                                       2 * radius, 2 * radius, 90, 360, Arc2D.OPEN);

        g2d.setColor(TangoPalette.PLUM2);

        GeometryPath geometry = new GeometryPath(arc2d);

        Font f = InputFonts.getFont(InputFonts.SANSATION_BOLD, 10);
        String copyright = "Racing Black/JENSOFT INSTRUMENT/all right reserved.";

        GlyphVector glyphVector = f.createGlyphVector(
                                                      g2d.getFontRenderContext(), copyright);

        AffineTransform af = new AffineTransform();

        float gvWidth = GlyphUtil.getGlyphWidth(glyphVector);

        float startLength = geometry.lengthOfPath() / 2 - gvWidth / 2;

        int c_p = copyright.indexOf("JENSOFT");

        for (int j = 0; j < glyphVector.getNumGlyphs(); j++) {

            Point2D p = glyphVector.getGlyphPosition(j);
            float px = (float) p.getX();
            float py = (float) p.getY();
            Point2D pointGlyph;

            pointGlyph = geometry.pointAtLength(startLength
                    + GlyphUtil.getGlyphWidthAtToken(glyphVector, j));

            if (pointGlyph == null) {
                continue;
            }
            Shape glyph = glyphVector.getGlyphOutline(j);

            float angle = geometry.angleAtLength(startLength
                    + GlyphUtil.getGlyphWidthAtToken(glyphVector, j));
            af.setToTranslation(pointGlyph.getX(), pointGlyph.getY());
            af.rotate(angle);
            af.translate(-px, -py + glyphVector.getVisualBounds().getHeight()
                    / 2 - 10);

            Shape ts = af.createTransformedShape(glyph);

            g2d.setColor(Color.WHITE);

            if (j >= c_p && j < c_p + 15) {
                g2d.setColor(TangoPalette.CHAMELEON1);
            }

            g2d.fill(ts);

        }

    }

}