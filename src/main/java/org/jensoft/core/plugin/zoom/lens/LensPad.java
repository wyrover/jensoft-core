/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.zoom.lens;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;

import org.jensoft.core.palette.color.ColorPalette;
import org.jensoft.core.palette.color.RosePalette;
import org.jensoft.core.plugin.zoom.lens.ZoomLensPlugin.ZoomLensType;
import org.jensoft.core.plugin.zoom.lens.ZoomLensPlugin.ZoomNature;
import org.jensoft.core.widget.pad.AbstractPlusMinusPadWidget;

/**
 * <code>LensPad</code>
 * 
 * @author sebastien janaud
 */
public class LensPad extends AbstractPlusMinusPadWidget<ZoomLensPlugin> {

    /** the widget id */
    public final static String objectifPadID = "@widget/objectif/pad";

    /** the widget radius */
    private final static int widgetRadius = 32;

    /** default base draw color */
    private Color baseDrawColor = RosePalette.LEMONPEEL;

    /** default control draw color */
    private Color controlDrawColor = RosePalette.LEMONPEEL;

    /** button draw color */
    private Color buttonDrawColor = RosePalette.AZALEA;

    /** button rollover draw color */
    private Color buttonRolloverDrawColor = RosePalette.LEMONPEEL;

    /** buttons strokes */
    private Stroke buttonStroke = new BasicStroke(1.6f, BasicStroke.CAP_SQUARE,
                                                  BasicStroke.JOIN_MITER);

    /** basic stroke */
    private Stroke basicStroke = new BasicStroke(1.2f);

    /**
     * create objectif pad widget with specified parameters
     * 
     * @param id
     *            objectif pad id
     * @param squarePad
     *            objectif pad square size
     * @param xIndex
     *            x folder index
     * @param yIndex
     *            y folder index
     */
    public LensPad(String id, double squarePad, int xIndex, int yIndex) {
        super(objectifPadID, squarePad, xIndex, yIndex);

        setFillBaseColor(Color.BLACK);
        setFillControlColor(ColorPalette.BLACK);
        setDrawBaseColor(baseDrawColor);
        setDrawControlColor(controlDrawColor);
        setDrawButtonColor(buttonDrawColor);
        setDrawButtonStroke(buttonStroke);
        setDrawButtonRolloverColor(buttonRolloverDrawColor);

    }

    /**
     * create default pad widget
     */
    public LensPad() {
        super(objectifPadID, 2 * widgetRadius, 60, 100);
        setFillBaseColor(Color.BLACK);
        setFillControlColor(ColorPalette.BLACK);
        setDrawBaseColor(baseDrawColor);
        setDrawControlColor(controlDrawColor);
        setDrawButtonColor(buttonDrawColor);
        setDrawButtonStroke(buttonStroke);
        setDrawButtonRolloverColor(buttonRolloverDrawColor);
        getPadGeometry().setInset(6);

    }

  

   
    /* (non-Javadoc)
     * @see org.jensoft.core.widget.Widget#isCompatiblePlugin()
     */
    @Override
    public boolean isCompatiblePlugin() {
        if (getHost() != null && getHost() instanceof ZoomLensPlugin) {
            return true;
        }
        return false;
    }

    
    /* (non-Javadoc)
     * @see org.jensoft.core.widget.pad.AbstractPadWidget#onNorthButtonPress()
     */
    @Override
    public void onNorthButtonPress() {
        getHost().startZoomIn(ZoomNature.ZoomY);
        if (!getHost().isLockSelected()) {
            return;
        }
        super.onNorthButtonPress();
    }

   
    /* (non-Javadoc)
     * @see org.jensoft.core.widget.pad.AbstractPadWidget#onNorthButtonReleased()
     */
    @Override
    public void onNorthButtonReleased() {
        getHost().stopZoomIn();
        super.onNorthButtonReleased();
    }

   
    /* (non-Javadoc)
     * @see org.jensoft.core.widget.pad.AbstractPadWidget#onSouthButtonPress()
     */
    @Override
    public void onSouthButtonPress() {
        if (!getHost().isLockSelected()) {
            return;
        }
        getHost().startZoomOut(ZoomNature.ZoomY);
        super.onSouthButtonPress();
    }

    
    /* (non-Javadoc)
     * @see org.jensoft.core.widget.pad.AbstractPadWidget#onSouthButtonReleased()
     */
    @Override
    public void onSouthButtonReleased() {
        getHost().stopZoomOut();
        super.onSouthButtonReleased();
    }

   
    /* (non-Javadoc)
     * @see org.jensoft.core.widget.pad.AbstractPadWidget#onWestButtonPress()
     */
    @Override
    public void onWestButtonPress() {
        if (!getHost().isLockSelected()) {
            return;
        }
        getHost().startZoomOut(ZoomNature.ZoomX);
        super.onWestButtonPress();
    }

    
    /* (non-Javadoc)
     * @see org.jensoft.core.widget.pad.AbstractPadWidget#onWestButtonReleased()
     */
    @Override
    public void onWestButtonReleased() {
        getHost().stopZoomOut();
        super.onWestButtonReleased();
    }

   
    /* (non-Javadoc)
     * @see org.jensoft.core.widget.pad.AbstractPadWidget#onEastButtonPress()
     */
    @Override
    public void onEastButtonPress() {
        if (! getHost().isLockSelected()) {
            return;
        }
        getHost().startZoomIn(ZoomNature.ZoomX);
        super.onEastButtonPress();
    }

    
    /* (non-Javadoc)
     * @see org.jensoft.core.widget.pad.AbstractPadWidget#onEastButtonReleased()
     */
    @Override
    public void onEastButtonReleased() {
        getHost().stopZoomIn();
        super.onEastButtonReleased();
    }
    
    @Override
    public void onRegister() {
    	if(getHost().getZoomLensType() != ZoomLensType.ZoomXY){
    		throw new IllegalStateException("Pad lens is only compatible with lens type : "+ZoomLensType.class.getName()+"#"+ZoomLensType.ZoomXY);
    	}
    }

}
