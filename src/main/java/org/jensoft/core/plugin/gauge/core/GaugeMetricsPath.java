/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.gauge.core;

import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.util.List;

import org.jensoft.core.glyphmetrics.GeneralMetricsPath;
import org.jensoft.core.glyphmetrics.GlyphMetric;
import org.jensoft.core.plugin.gauge.core.binder.AnchorBinder;
import org.jensoft.core.plugin.gauge.core.binder.PathBinder;
import org.jensoft.core.plugin.gauge.core.needle.GaugeNeedlePainter;

/**
 * <code>GaugeMetricsPath</code> takes the responsibility to draw metrics along
 * path and manage needle for this metrics path
 * 
 * @since 1.0
 * @author sebastien janaud
 * 
 */
public class GaugeMetricsPath extends GeneralMetricsPath {

	/** current value */
	private double currentValue;

	/** needle base anchor binder */
	private AnchorBinder needleBaseAnchorBinder;

	/** needle value anchor binder */
	private AnchorBinder needleValueAnchorBinder;

	/** gauge body this metrics path */
	private GaugeBody body;

	/** gauge part buffer to paint reuse */
	private GaugePartBuffer partBuffer;

	/** path binder */
	private PathBinder pathBinder;

	/** gauge needle painter */
	private GaugeNeedlePainter gaugeNeedlePainter;

	/**
	 * process to force calculate arc at runtime in device coordinate system.
	 * because projected center is required.
	 * 
	 */
	public static interface ArcBinder {

		/**
		 * Process to force calculate arc at runtime in device coordinate
		 * system. because projected center is required.
		 * 
		 * @param gauge
		 *            the gauge
		 * @return the given geometry path
		 */
		Arc2D bindArc(RadialGauge gauge);
	}

	/**
	 * create gauge metrics path
	 */
	public GaugeMetricsPath() {
		super();

		// gauge can not be zoomed in simple linear zooming mode
		// only gauge center is projected.all expression is relative to the
		// center gauge in pixel dimension.
		// then all vector transforms or evaluation
		// should be achieve and evaluate by another process, from radius
		// reference for example.
		setProjectionNature(ProjectionNature.DEVICE);
	}

	/**
	 * get gauge needle painter
	 * 
	 * @return needle painter
	 */
	public GaugeNeedlePainter getGaugeNeedlePainter() {
		return gaugeNeedlePainter;
	}

	/**
	 * set gauge needle painter
	 * 
	 * @param gaugeNeedlePainter
	 *            the needle painter to set
	 */
	public void setGaugeNeedlePainter(GaugeNeedlePainter gaugeNeedlePainter) {
		this.gaugeNeedlePainter = gaugeNeedlePainter;
	}

	/**
	 * get the current user value
	 * 
	 * @return current value
	 */
	public double getCurrentValue() {
		return currentValue;
	}

	/**
	 * set current user value
	 * 
	 * @param currentValue
	 */
	public void setCurrentValue(double currentValue) {
		if (currentValue < getMin() || currentValue > getMax())
			throw new IllegalArgumentException("metrics of of range. " + currentValue + " is outside [min,max] path range. ");
		this.currentValue = currentValue;
	}

	/**
	 * get path binder
	 * 
	 * @return path binder
	 */
	public PathBinder getPathBinder() {
		return pathBinder;
	}

	/**
	 * get path binder
	 * 
	 * @param pathBinder
	 */
	public void setPathBinder(PathBinder pathBinder) {
		pathBinder.setMetricsPath(this);
		this.pathBinder = pathBinder;
	}

	/**
	 * get needle base anchor binder
	 * 
	 * @return needle base anchor binder
	 */
	public AnchorBinder getNeedleBaseAnchorBinder() {
		return needleBaseAnchorBinder;
	}

	/**
	 * set needle anchor binder
	 * 
	 * @param needleAnchorBinder
	 */
	public void setNeedleBaseAnchorBinder(AnchorBinder needleAnchorBinder) {
		needleAnchorBinder.setMetricsPath(this);
		this.needleBaseAnchorBinder = needleAnchorBinder;
	}

	/**
	 * get needle value anchor binder
	 * 
	 * @return needle value anchor binder
	 */
	public AnchorBinder getNeedleValueAnchorBinder() {
		return needleValueAnchorBinder;
	}

	/**
	 * set needle value anchor binder
	 * 
	 * @param needleValueAnchorBinder
	 */
	public void setNeedleValueAnchorBinder(AnchorBinder needleValueAnchorBinder) {
		needleValueAnchorBinder.setMetricsPath(this);
		this.needleValueAnchorBinder = needleValueAnchorBinder;
	}

	/**
	 * @return the body
	 */
	public GaugeBody getBody() {
		return body;
	}

	/**
	 * @param body
	 *            the body to set
	 */
	public void setBody(GaugeBody body) {
		this.body = body;
	}

	/**
	 * @param partBuffer
	 *            the partBuffer to set
	 */
	public void setPartBuffer(GaugePartBuffer partBuffer) {
		this.partBuffer = partBuffer;
	}

	/**
	 * get part buffer of this metrics path
	 * 
	 * @return part buffer
	 */
	public GaugePartBuffer getPartBuffer() {
		return partBuffer;
	}

	/**
	 * invalidate the part buffer
	 */
	public void invalidate() {
		setPartBuffer(null);
	}

	/**
	 * create part buffer of this metrics path from original context.
	 * 
	 * @param g2d
	 */
	public void createPartBuffer(Graphics2D g2d) {
		partBuffer = new GaugePartBuffer(getBody().getGauge());

		Graphics2D g2dPart = partBuffer.getGraphics();
		g2dPart.setRenderingHints(g2d.getRenderingHints());
		setFontRenderContext(g2d.getFontRenderContext());
		setSolveGeometryRequest(true);
		

		if (getPathPainter() != null) {
			getPathPainter().paintPath(g2dPart, this);
		}

		List<GlyphMetric> metrics = getMetrics();
		for (GlyphMetric m : metrics) {

			if (m.getGlyphMetricMarkerPainter() != null) {
				m.getGlyphMetricMarkerPainter().paintGlyphMetric(g2dPart, m);
			}
			if (m.getGlyphMetricFill() != null) {
				m.getGlyphMetricFill().paintGlyphMetric(g2dPart, m);
			}
			if (m.getGlyphMetricDraw() != null) {
				m.getGlyphMetricDraw().paintGlyphMetric(g2dPart, m);
			}
			if (m.getGlyphMetricEffect() != null) {
				m.getGlyphMetricEffect().paintGlyphMetric(g2dPart, m);
			}
		}
	}

	

}
