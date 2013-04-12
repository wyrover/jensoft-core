/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function;

import java.awt.Color;
import java.awt.Stroke;

import com.jensoft.core.plugin.Toolkit;
import com.jensoft.core.plugin.function.area.AreaFunction;
import com.jensoft.core.plugin.function.area.painter.draw.AbstractAreaDraw;
import com.jensoft.core.plugin.function.curve.CurveFunction;
import com.jensoft.core.plugin.function.curve.painter.draw.CurveDefaultDraw;
import com.jensoft.core.plugin.function.scatter.ScatterFunction;
import com.jensoft.core.plugin.function.scatter.morphe.RectangleMorphe;
import com.jensoft.core.plugin.function.scatter.morphe.ScatterMorphe;
import com.jensoft.core.plugin.function.scatter.painter.ScatterDraw;
import com.jensoft.core.plugin.function.scatter.painter.ScatterFill;
import com.jensoft.core.plugin.function.scatter.painter.fill.ScatterDefaultFill;
import com.jensoft.core.plugin.function.source.SourceFunction;

public class FunctionToolkit extends Toolkit {

    /**
     * create default curve with specified parameters
     * 
     * @param source
     *            the curve source
     * @return curve
     */
    public static CurveFunction createCurveFunction(SourceFunction source) {
        CurveFunction curve = new CurveFunction(source);
        curve.setThemeColor(Color.BLACK);
        return curve;
    }

    /**
     * create default curve with specified parameters
     * 
     * @param source
     *            the curve source
     * @param color
     *            the curve color
     * @return curve
     */
    public static CurveFunction createCurveFunction(SourceFunction source, Color color) {
        CurveFunction curve = new CurveFunction(source);
        curve.setThemeColor(color);
        return curve;
    }

    /**
     * create default curve with specified parameters
     * 
     * @param source
     *            the curve source
     * @param color
     *            the curve color
     * @param stroke
     *            the curve stroke
     * @return curve
     */
    public static CurveFunction createCurveFunction(SourceFunction source, Color color,
            Stroke curveStroke) {
        CurveFunction curve = new CurveFunction(source);
        curve.setThemeColor(color);
        curve.setCurveDraw(new CurveDefaultDraw(curveStroke));
        return curve;
    }

    /**
     * create default curve with specified parameters
     * 
     * @param source
     *            the curve source
     * @param color
     *            the curve color
     * @param curveDraw
     *            the curve draw
     * @return curve
     */
    public static CurveFunction createCurveFunction(SourceFunction source, Color color,CurveDefaultDraw curveDraw) {
        CurveFunction curve = new CurveFunction(source);
        curve.setThemeColor(color);
        curve.setCurveDraw(curveDraw);
        return curve;
    }
    
    /**
     * create default area curve with specified parameters
     * 
     * @param source
     *            the area curve source
     * @return area curve
     */
    public static AreaFunction createArea(SourceFunction source) {
        AreaFunction curve = new AreaFunction(source);
        curve.setThemeColor(Color.BLACK);
        return curve;
    }

    /**
     * create default curve with specified parameters
     * 
     * @param source
     *            the curve source
     * @param color
     *            the curve color
     * @return curve
     */
    public static AreaFunction createArea(SourceFunction source, Color color) {
        AreaFunction curve = new AreaFunction(source);
        curve.setThemeColor(color);
        return curve;
    }

    /**
     * create default curve with specified parameters
     * 
     * @param source
     *            the curve source
     * @param color
     *            the curve color
     * @return curve
     */
    public static AreaFunction createArea(SourceFunction source, Color color,
            AbstractAreaDraw curveAreaDraw) {
        AreaFunction curve = new AreaFunction(source);
        curve.setAreaDraw(curveAreaDraw);
        curve.setThemeColor(color);
        return curve;
    }
    
    /**
     * create default curve with specified parameters
     * 
     * @param source
     *            the curve source
     * @return curve
     */
    public static ScatterFunction createScatterFunction(SourceFunction source) {
        ScatterFunction curve = new ScatterFunction(source);
        curve.setScatterMorphe(new RectangleMorphe(3, 3));
        curve.setScatterFill(new ScatterDefaultFill());
        curve.setThemeColor(Color.BLACK);
        return curve;
    }

    /**
     * create default curve with specified parameters
     * 
     * @param source
     *            the curve source
     * @param color
     *            the curve color
     * @return curve
     */
    public static ScatterFunction createScatterFunction(SourceFunction source, Color color) {
        ScatterFunction curve = new ScatterFunction(source);
        curve.setScatterMorphe(new RectangleMorphe(3, 3));
        curve.setScatterFill(new ScatterDefaultFill());
        curve.setThemeColor(color);
        return curve;
    }

    /**
     * create default curve with specified parameters
     * 
     * @param source
     *            the curve source
     * @param color
     *            the curve color
     * @param morphe
     *            the scatter morphe
     * @return curve
     */
    public static ScatterFunction createScatterFunction(SourceFunction source, Color color,
            ScatterMorphe morphe) {
        ScatterFunction curve = new ScatterFunction(source);
        curve.setThemeColor(color);
        curve.setScatterMorphe(morphe);
        curve.setScatterFill(new ScatterDefaultFill());
        return curve;
    }

    /**
     * create default curve with specified parameters
     * 
     * @param source
     *            the curve source
     * @param color
     *            the curve color
     * @param morphe
     *            the scatter morphe
     * @param scatterFill
     *            the scatter fill
     * @return curve
     */
    public static ScatterFunction createScatterFunction(SourceFunction source, Color color,
            ScatterMorphe morphe, ScatterFill scatterFill) {
        ScatterFunction curve = new ScatterFunction(source);
        curve.setThemeColor(color);
        curve.setScatterMorphe(morphe);
        curve.setScatterFill(scatterFill);
        return curve;
    }

    /**
     * create default curve with specified parameters
     * 
     * @param source
     *            the curve source
     * @param color
     *            the curve color
     * @param morphe
     *            the scatter morphe
     * @param scatterFill
     *            the scatter fill
     * @param scatterDraw
     * @return curve
     */
    public static ScatterFunction createScatterFunction(SourceFunction source, Color color,
            ScatterMorphe morphe, ScatterFill scatterFill,
            ScatterDraw scatterDraw) {
        ScatterFunction curve = new ScatterFunction(source);
        curve.setThemeColor(color);
        curve.setScatterMorphe(morphe);
        curve.setScatterFill(scatterFill);
        curve.setScatterDraw(scatterDraw);
        return curve;
    }

}
