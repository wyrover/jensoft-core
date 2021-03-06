/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.function.area.painter.fill;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.jensoft.core.graphics.Shader;
import org.jensoft.core.palette.color.ColorPalette;
import org.jensoft.core.plugin.function.area.Area;
import org.jensoft.core.plugin.function.source.FunctionNature;

/***
 * define curve linear gradient fill<br>
 * 
 * @author Sebastien Janaud
 */
public class AreaGradientFill extends AbstractAreaFill {

	private Color color1;
	private Color color2;

	/** shade fractions */
	private float[] shadeFractions;

	/** shade colors */
	private Color[] shadeColors;

	/**
	 * create default gradient fill
	 */
	public AreaGradientFill() {
	}

	/**
	 * create area gradient with specified color
	 * 
	 * @param color1
	 * @param color2
	 */
	public AreaGradientFill(Color color1, Color color2) {
		super();
		this.color1 = color1;
		this.color2 = color2;
	}

	/**
	 * create area gradient with specified parameters
	 * 
	 * @param shadeFractions
	 *            the shading fraction
	 * @param shadeColors
	 *            the shading color
	 */
	public AreaGradientFill(float[] shadeFractions, Color[] shadeColors) {
		super();
		this.shadeFractions = shadeFractions;
		this.shadeColors = shadeColors;
	}

	/**
	 * create area gradient with specified parameters
	 * 
	 * @param shader
	 *            the shader
	 */
	public AreaGradientFill(Shader shader) {
		super();
		this.shadeFractions = shader.getFractions();
		this.shadeColors = shader.getColors();
	}

	/**
	 * @return the color1
	 */
	public Color getColor1() {
		return color1;
	}

	/**
	 * @param color1
	 *            the color1 to set
	 */
	public void setColor1(Color color1) {
		this.color1 = color1;
	}

	/**
	 * @return the color2
	 */
	public Color getColor2() {
		return color2;
	}

	/**
	 * @param color2
	 *            the color2 to set
	 */
	public void setColor2(Color color2) {
		this.color2 = color2;
	}

	/**
	 * @return the shadeFractions
	 */
	public float[] getShadeFractions() {
		return shadeFractions;
	}

	/**
	 * @return the shadeColors
	 */
	public Color[] getShadeColors() {
		return shadeColors;
	}

	/**
	 * set the shadow parameters
	 * 
	 * @param fractions
	 * @param colors
	 */
	public void setShader(float[] fractions, Color[] colors) {
		if (fractions.length != colors.length) {
			throw new IllegalArgumentException("length array does not match");
		}
		shadeFractions = fractions;
		shadeColors = colors;
	}

	/**
	 * set the shader
	 * 
	 * @param shader
	 */
	public void setShader(Shader shader) {
		if (shader.getFractions().length != shader.getColors().length) {
			throw new IllegalArgumentException("length array does not match");
		}
		shadeFractions = shader.getFractions();
		shadeColors = shader.getColors();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jensoft.core.plugin.function.area.painter.fill.AbstractAreaFill#
	 * paintAreaFill(java.awt.Graphics2D,
	 * org.jensoft.core.plugin.function.area.AreaFunction)
	 */
	@Override
	public final void paintAreaFill(Graphics2D g2d, Area area) {

		// Shape shape = area.getAreaPath();
		Shape shape = area.getArea();
		if (shape == null) {
			return;
		}

		Rectangle2D rect = shape.getBounds2D();

		LinearGradientPaint gradient = null;

		if (color1 != null && color2 == null) {
			color2 = color1;
		}

		if (color1 != null && color2 != null && shadeFractions == null) {
			float[] f = { 0f, 1f };
			Color[] c = { color1, color2 };

			if (area.getSourceFunction().getNature() == FunctionNature.XFunction) {
				gradient = new LinearGradientPaint(new Point2D.Double(rect.getCenterX(), rect.getMinY()), new Point2D.Double(rect.getCenterX(), rect.getMaxY()), f, c);
			} else {
				gradient = new LinearGradientPaint(new Point2D.Double(rect.getMaxX(), rect.getCenterY()), new Point2D.Double(rect.getMinX(), rect.getCenterY()), f, c);
			}
		} else if (shadeFractions != null && shadeColors != null && shadeFractions.length == shadeColors.length) {
			if (area.getSourceFunction().getNature() == FunctionNature.XFunction) {
				gradient = new LinearGradientPaint(new Point2D.Double(rect.getCenterX(), rect.getMinY()), new Point2D.Double(rect.getCenterX(), rect.getMaxY()), shadeFractions, shadeColors);
			} else {
				gradient = new LinearGradientPaint(new Point2D.Double(rect.getMaxX(), rect.getCenterY()), new Point2D.Double(rect.getMinX(), rect.getCenterY()), shadeFractions, shadeColors);
			}

		} else {
			float[] f = { 0f, 1f };
			Color[] c = { area.getThemeColor(), ColorPalette.alpha(area.getThemeColor(), 80) };
			if (area.getSourceFunction().getNature() == FunctionNature.XFunction) {
				Point2D start = new Point2D.Double(rect.getCenterX(), rect.getMinY());
				Point2D end = new Point2D.Double(rect.getCenterX(), rect.getMaxY());
				if (!start.equals(end)) {
					gradient = new LinearGradientPaint(start, end, f, c);
				}
			}else{
				Point2D start = new Point2D.Double(rect.getMaxX(), rect.getCenterY());
				Point2D end = new Point2D.Double(rect.getMinX(), rect.getCenterY());
				if (!start.equals(end)) {
					gradient = new LinearGradientPaint(start, end, f, c);
				}
			}
			

		}

		if (gradient != null) {
			g2d.setPaint(gradient);

			g2d.fill(area.getArea());
		}
	}

}
