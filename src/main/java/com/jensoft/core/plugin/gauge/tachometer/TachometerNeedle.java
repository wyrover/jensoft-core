/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.tachometer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.List;

import com.jensoft.core.gauge.core.RadialGauge;
import com.jensoft.core.gauge.core.painter.NeedleGaugePainter;
import com.jensoft.core.glyphmetrics.GeneralMetricsPath;
import com.jensoft.core.glyphmetrics.Side;
import com.jensoft.core.palette.ColorPalette;
import com.jensoft.core.palette.NanoChromatique;
import com.jensoft.core.plugin.donut2d.Donut2D;
import com.jensoft.core.plugin.donut2d.Donut2DSlice;

public class TachometerNeedle extends NeedleGaugePainter {
	

	private void paintNeedle(Graphics2D g2d) {

		double centerX = getGauge().getWindow2D().userToPixel(new Point2D.Double(getGauge().getX(), 0)).getX();// (int)getGauge().getX();
		double centerY = getGauge().getWindow2D().userToPixel(new Point2D.Double(0, getGauge().getY())).getY();// (int)getGauge().getY();
		int radius = getGauge().getRadius() - 10;

		GeneralMetricsPath pathManager = getPathManager();
		pathManager.setFontRenderContext(g2d.getFontRenderContext());

		getPathManager().setWindow2d(getGauge().getWindow2D());

		Point2D center = new Point2D.Double(centerX, centerY);

		Point2D pNeedle2 = getPathManager().getRadialPoint(getCurentValue(), 40, Side.SideRight);
		Point2D pNeedle2TT = getGauge().getWindow2D().userToPixel(pNeedle2);
		Line2D lNeedle = new Line2D.Double(center.getX(), center.getY(), pNeedle2.getX(), pNeedle2.getY());

		BasicStroke stroke = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		BasicStroke stroke2 = new BasicStroke(8, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		Shape tShape = stroke.createStrokedShape(lNeedle);
		Shape tShape2 = stroke2.createStrokedShape(lNeedle);

		Area area = new Area(tShape);
		Area area2 = new Area(tShape2);

		int w = 20;
		Ellipse2D cacheCenter = new Ellipse2D.Double(centerX - w, centerY - w, 2 * w, 2 * w);
		Area area3 = new Area(cacheCenter);

		g2d.setColor(ColorPalette.alpha(NanoChromatique.GREEN, 120));

		g2d.fill(area2);

		g2d.setColor(NanoChromatique.GREEN);

		g2d.fill(area);

		// deco gradient
		// int w0 = 25;
		// int r = NanoChromatique.PINK.getRed();
		// int g = NanoChromatique.PINK.getGreen();
		// int b = NanoChromatique.PINK.getBlue();

		// //Ellipse2D cacheCenter0 = new
		// Ellipse2D.Double(centerX-w0,centerY-w0,2*w0,2*w0);
		// RoundGradientPaint rgp0 = new RoundGradientPaint(centerX, centerY,new
		// Color(r,g,b,250), new Point2D.Double(5, 25), new Color(r,g,b,40));
		// //RadialGradientPaint rgp0 = new RadialGradientPaint(new
		// Point2D.Double(centerX,centerY), 25, new float[]{0f,1f},new
		// Color[]{new Color(r,g,b,250),new Color(r,g,b,40)});
		//
		// g2d.setPaint(rgp0);

		// RoundGradientPaint rgp = new RoundGradientPaint(centerX,
		// centerY,Color.BLACK,new Point2D.Double(0, 20),
		// Color.DARK_GRAY.darker());
		RadialGradientPaint rgp = new RadialGradientPaint(new Point2D.Double(centerX, centerY), 20, new float[] { 0f, 1f }, new Color[] { ColorPalette.alpha(Color.BLACK,180), ColorPalette.alpha(Color.BLACK,100) });

		g2d.setPaint(rgp);
		g2d.fill(cacheCenter);
		g2d.setStroke(new BasicStroke(2f));

		g2d.setColor(NanoChromatique.GREEN);

		g2d.draw(cacheCenter);

	}

	private void paintDeco1(Graphics2D g2d) {

		double centerX = getGauge().getWindow2D().userToPixel(new Point2D.Double(getGauge().getX(), 0)).getX();// (int)getGauge().getX();
		double centerY = getGauge().getWindow2D().userToPixel(new Point2D.Double(0, getGauge().getY())).getY();// (int)getGauge().getY();
		int radius = 15;

		Donut2D donut1 = new Donut2D();
		donut1.setCenterX((int) centerX);
		donut1.setCenterY((int) centerY);
		donut1.setInnerRadius(10);
		donut1.setOuterRadius(15);
		donut1.setStartAngleDegree(32);
		donut1.setExplose(0);

		Donut2DSlice s1 = new Donut2DSlice("D1", new Color(255, 255, 255, 80));
		s1.setAlpha(1f);
		s1.setValue(10);

		Donut2DSlice s2 = new Donut2DSlice("D2", new Color(255, 255, 255, 80));
		s2.setValue(5);
		s2.setAlpha(0f);

		Donut2DSlice s3 = new Donut2DSlice("D3", new Color(255, 255, 255, 0));
		s3.setValue(10);
		s3.setAlpha(1f);
		Donut2DSlice s4 = new Donut2DSlice("D4", new Color(255, 255, 255, 0));
		s4.setValue(5);
		s4.setAlpha(0f);
		Donut2DSlice s5 = new Donut2DSlice("D5", new Color(255, 255, 255, 0));
		s5.setValue(10);
		s5.setAlpha(1f);
		Donut2DSlice s6 = new Donut2DSlice("D6", new Color(255, 255, 255, 0));
		s6.setValue(5);
		s6.setAlpha(0f);
		Donut2DSlice s7 = new Donut2DSlice("D7", new Color(255, 255, 255, 0));
		s7.setValue(10);
		s7.setAlpha(1f);
		Donut2DSlice s8 = new Donut2DSlice("D8", new Color(255, 255, 255, 0));
		s8.setValue(5);
		s8.setAlpha(0f);

		donut1.addSlice(s1);
		donut1.addSlice(s2);
		donut1.addSlice(s3);
		donut1.addSlice(s4);
		donut1.addSlice(s5);
		donut1.addSlice(s6);
		donut1.addSlice(s7);
		donut1.addSlice(s8);

		// donut.buildDonut();

		Point2D gcenter = new Point2D.Double(centerX, centerY);
		float gradius = (float) donut1.getOuterRadius();
		float[] dist = { 0.0f, 0.5f, 1.0f };
		int r = Color.BLACK.getRed();
		int g = Color.BLACK.getGreen();
		int b = Color.BLACK.getBlue();
		Color[] colors = { new Color(r, g, b, 100), new Color(r, g, b, 250), new Color(r, g, b, 100) };
		RadialGradientPaint p = new RadialGradientPaint(gcenter, gradius, dist, colors);

		// RoundGradientPaint rgp2 = new RoundGradientPaint(centerX, centerY,
		// new Color(255,255,255,200),
		//
		// new Point2D.Double(5, donut1.getExternalRadius()), new
		// Color(255,255,255,0));
		donut1.solveGeometry();
		g2d.setPaint(p);
		List<Donut2DSlice> sections = donut1.getSlices();

		for (int j = 0; j < sections.size(); j++) {

			Donut2DSlice s = sections.get(j);

			// section

			g2d.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, s.getAlpha()));
			// g2d.draw(s.getPath());

			// g2d.setComposite(java.awt.AlphaComposite.getInstance(
			// java.awt.AlphaComposite.SRC_OVER, s.getAlphaTransparence()));

			g2d.fill(s.getSlicePath());
			g2d.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 1f));

		}
	}

	@Override
	public void paintNeedle(Graphics2D g2d, RadialGauge radialGauge) {
		paintNeedle(g2d);
		//paintDeco1(g2d);

	}

}