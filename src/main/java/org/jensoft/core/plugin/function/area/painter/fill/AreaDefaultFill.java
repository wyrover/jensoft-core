/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.function.area.painter.fill;

import java.awt.Color;
import java.awt.Graphics2D;

import org.jensoft.core.plugin.function.area.Area;

/**
 * AreaClassicFill
 * 
 * @author Sebastien Janaud
 */
public class AreaDefaultFill extends AbstractAreaFill {

    /** fill color */
    private Color color;

    /**
     * default fill color, area theme color will be used.
     */
    public AreaDefaultFill() {
    }

    /**
     * create area fill with specified color
     * 
     * @param color
     *            color to set
     */
    public AreaDefaultFill(Color color) {
        super();
        this.color = color;
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color
     *            the color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }

   
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.function.area.painter.fill.AbstractAreaFill#paintAreaFill(java.awt.Graphics2D, org.jensoft.core.plugin.function.area.AreaFunction)
     */
    @Override
    public final void paintAreaFill(Graphics2D g2d, Area area) {
        if(color != null){
            g2d.setPaint(color);
        }else{
            if(area.getThemeColor() != null){
                g2d.setPaint(area.getThemeColor());
            }
            else{
                g2d.setPaint(area.getHost().getProjection().getThemeColor());
            }
        }
        g2d.fill(area.getArea());

    }

}
