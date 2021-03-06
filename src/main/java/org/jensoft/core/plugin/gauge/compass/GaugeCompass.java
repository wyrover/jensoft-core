/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.gauge.compass;

import java.awt.Color;
import java.awt.Font;

import org.jensoft.core.glyphmetrics.GlyphMetric;
import org.jensoft.core.glyphmetrics.GlyphMetricsNature;
import org.jensoft.core.glyphmetrics.StylePosition;
import org.jensoft.core.glyphmetrics.painter.fill.GlyphFill;
import org.jensoft.core.palette.InputFonts;
import org.jensoft.core.palette.TexturePalette;
import org.jensoft.core.palette.color.NanoChromatique;
import org.jensoft.core.plugin.gauge.core.GaugeBackground;
import org.jensoft.core.plugin.gauge.core.GaugeBody;
import org.jensoft.core.plugin.gauge.core.GaugeEnvelope;
import org.jensoft.core.plugin.gauge.core.GaugeGlass;
import org.jensoft.core.plugin.gauge.core.GaugeMetricsPath;
import org.jensoft.core.plugin.gauge.core.RadialGauge;
import org.jensoft.core.plugin.gauge.core.binder.path.PathArcManualBinder;
import org.jensoft.core.plugin.pie.painter.effect.CubicEffectFrame;

/**
 * <code>GaugeCompass</code> base model helps developer to learn gauge modeling.
 * 
 * @since1.0
 * @author sebastien janaud
 * 
 */
public class GaugeCompass extends RadialGauge {

	/**gauge body*/
	private GaugeBody body;
	
	/** primary gauge path */
	private GaugeMetricsPath secondaryPathManager;

	/** secondary gauge path */
	private GaugeMetricsPath primaryPathManager;

	/** gauge radius parameters */
	private static int gaugeRadius = 110;

	/** gauge center x coordinate in user  projection */
	private static int centerUserX = 0;

	/** gauge center y coordinate in user  projection */
	private static int centerUserY = 0;

	/**
	 * create gauge compass
	 */
	public GaugeCompass() {
		super(centerUserX, centerUserY, gaugeRadius);

		GaugeEnvelope cisero = new GaugeEnvelope.Cisero();
		setEnvelop(cisero);

		
		GaugeBackground bg = new GaugeBackground.Circular.Texture(TexturePalette.getSquareCarbonFiber());
		addBackground(bg);

		GaugeCompassBackground compass = new GaugeCompassBackground(0, 0, 150);
		addBackground(compass);

		GaugeGlass cubicEffect = new GaugeGlass.GlassCubicEffect(CubicEffectFrame.Moon1);
		GaugeGlass linearEffect = new GaugeGlass.GlassLinearEffect();
		GaugeGlass radialEffect = new GaugeGlass.GlassRadialEffect();
		GaugeGlass donutEffect = new GaugeGlass.Donut2DGlass();
		GaugeGlass textEffect = new GaugeGlass.GlassTextPath();
		GaugeGlass glassLabel = new GaugeGlass.JenSoftAPILabel();
		addGlass(cubicEffect,glassLabel);

		
		body = new GaugeBody();
		addBody(body);
		
		createPrimaryMetrics();
		createSecondaryMetrics();
	}

	/**
	 * create primary metrics labels
	 */
	private void createPrimaryMetrics() {
		primaryPathManager = new GaugeMetricsPath();
		primaryPathManager.setAutoReverseGlyph(false);
		primaryPathManager.setReverseAll(true);
		primaryPathManager.setRange(0, 360);
		primaryPathManager.setPathBinder(new PathArcManualBinder(gaugeRadius - 10, 0, 360));
		body.registerGaugeMetricsPath(primaryPathManager);

		GlyphMetric metric;
		Font f = InputFonts.getFont(InputFonts.ELEMENT, 40);

		// east
		metric = new GlyphMetric();
		metric.setValue(0);
		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsNature(GlyphMetricsNature.Major);
		metric.setMetricsLabel("E");
		metric.setDivergence(-15);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.YELLOW.brighter()));
		metric.setFont(f);
		primaryPathManager.addMetric(metric);

		// north
		metric = new GlyphMetric();
		metric.setValue(90);
		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsNature(GlyphMetricsNature.Major);
		metric.setMetricsLabel("N");
		metric.setDivergence(-15);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.BLUE));
		metric.setFont(f);
		primaryPathManager.addMetric(metric);

		// west
		metric = new GlyphMetric();
		metric.setValue(180);
		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsNature(GlyphMetricsNature.Major);
		metric.setMetricsLabel("W");
		metric.setDivergence(-15);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.ORANGE));
		metric.setFont(f);
		primaryPathManager.addMetric(metric);

		// south
		metric = new GlyphMetric();
		metric.setValue(270);
		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsNature(GlyphMetricsNature.Major);
		metric.setMetricsLabel("S");
		metric.setDivergence(-15);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.RED));
		metric.setFont(f);
		primaryPathManager.addMetric(metric);
	}

	/**
	 * create secondary metrics label
	 */
	private void createSecondaryMetrics() {

		secondaryPathManager = new GaugeMetricsPath();
		secondaryPathManager.setAutoReverseGlyph(false);
		secondaryPathManager.setReverseAll(true);
		secondaryPathManager.setRange(0, 360);
		secondaryPathManager.setPathBinder(new PathArcManualBinder(gaugeRadius - 50, 0, 360));
		body.registerGaugeMetricsPath(secondaryPathManager);

		GlyphMetric metric;
		Font f = InputFonts.getFont(InputFonts.ELEMENT, 12);
		
		metric = new GlyphMetric();
		metric.setValue(30);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsNature(GlyphMetricsNature.Median);
		metric.setMetricsLabel("30");
		metric.setDivergence(0);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.YELLOW.brighter()));
		metric.setFont(f);
		secondaryPathManager.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(60);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsNature(GlyphMetricsNature.Median);
		metric.setMetricsLabel("60");
		metric.setDivergence(0);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.BLUE));
		metric.setFont(f);
		secondaryPathManager.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(120);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsNature(GlyphMetricsNature.Median);
		metric.setMetricsLabel("120");
		metric.setDivergence(0);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.BLUE));
		metric.setFont(f);
		secondaryPathManager.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(150);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsNature(GlyphMetricsNature.Median);
		metric.setMetricsLabel("150");
		metric.setDivergence(0);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.ORANGE.brighter()));
		metric.setFont(f);
		secondaryPathManager.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(210);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsNature(GlyphMetricsNature.Median);
		metric.setMetricsLabel("210");
		metric.setDivergence(0);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.ORANGE.brighter()));
		metric.setFont(f);
		secondaryPathManager.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(240);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsNature(GlyphMetricsNature.Median);
		metric.setMetricsLabel("240");
		metric.setDivergence(0);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.RED));
		metric.setFont(f);
		secondaryPathManager.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(300);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsNature(GlyphMetricsNature.Median);
		metric.setMetricsLabel("300");
		metric.setDivergence(0);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.RED));
		metric.setFont(f);
		secondaryPathManager.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(330);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsNature(GlyphMetricsNature.Median);
		metric.setMetricsLabel("330");
		metric.setDivergence(0);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.YELLOW.brighter()));
		metric.setFont(f);
		secondaryPathManager.addMetric(metric);
	}

}
