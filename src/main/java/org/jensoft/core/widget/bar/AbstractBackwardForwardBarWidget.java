/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.widget.bar;

import org.jensoft.core.plugin.AbstractPlugin;
import org.jensoft.core.widget.bar.AbstractBarGeometry.BarWidgetOrientation;

/**
 * BackwardForwardBarWidget
 * 
 * @author Sebastien Janaud
 */
public abstract class AbstractBackwardForwardBarWidget<P extends AbstractPlugin> extends
        AbstractBarWidget<P> {

    /**
     * create BackwardForwardBarWidget with specified parameters
     * 
     * @param id
     *            widget ID
     * @param width
     *            widget width
     * @param height
     *            widget height
     * @param xIndex
     *            widget x folder index
     * @param yIndex
     *            widget y folder index
     * @param barWidgetOrientation
     *            widget bat orientation
     */
    public AbstractBackwardForwardBarWidget(String id, double width,
            double height, int xIndex, int yIndex,
            BarWidgetOrientation barWidgetOrientation) {
        super(id, width, height, xIndex, yIndex,
              new BackwardForwardBarGeometry(barWidgetOrientation),
              barWidgetOrientation);
    }

}
