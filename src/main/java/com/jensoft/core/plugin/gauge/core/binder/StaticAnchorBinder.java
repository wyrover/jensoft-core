/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.core.binder;

import java.awt.geom.Point2D;

import com.jensoft.core.plugin.gauge.core.RadialGauge;

/**
 * <code>GaugeBaseAnchorBinder</code> binds to a static anchor point build from
 * radius and angle degree.
 * 
 * @since 1.0
 * @author sebastien janaud
 * 
 */
public class StaticAnchorBinder extends AnchorBinder {

	private int radius;
	private float angleDegree;

	/**
	 * create binder to the gauge center (default radius = 0)
	 */
	public StaticAnchorBinder() {
		radius = 0;
		angleDegree = 0;
	}

	/**
	 * create base anchor with given anchor parameters
	 * 
	 * @param radius
	 * @param angleDegree
	 */
	public StaticAnchorBinder(int radius, float angleDegree) {
		super();
		this.radius = radius;
		this.angleDegree = angleDegree;
	}

	/**
	 * @return the radius
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * @param radius
	 *            the radius to set
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}

	/**
	 * @return the angleDegree
	 */
	public float getAngleDegree() {
		return angleDegree;
	}

	/**
	 * @param angleDegree
	 *            the angleDegree to set
	 */
	public void setAngleDegree(float angleDegree) {
		this.angleDegree = angleDegree;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.core.plugin.gauge.core.binder.AnchorBinder#bindAnchor(com
	 * .jensoft.core.plugin.gauge.core.RadialGauge)
	 */
	@Override
	public Point2D bindAnchor(RadialGauge gauge) {
		double anchorX =  gauge.getCenterDevice().getX() + radius*Math.cos(Math.toRadians(angleDegree));
		double anchorY = gauge.getCenterDevice().getY() - radius*Math.sin(Math.toRadians(angleDegree));
		return new Point2D.Double(anchorX, anchorY);
	}

}