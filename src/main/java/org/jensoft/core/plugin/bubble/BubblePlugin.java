/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.bubble;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import org.jensoft.core.graphics.Antialiasing;
import org.jensoft.core.plugin.AbstractPlugin;
import org.jensoft.core.plugin.bubble.painter.BubbleDraw;
import org.jensoft.core.plugin.bubble.painter.BubbleEffect;
import org.jensoft.core.plugin.bubble.painter.BubbleFill;
import org.jensoft.core.view.View;
import org.jensoft.core.view.ViewPart;

/**
 * <code>BubblePlugin</code>
 * 
 * @see BubblePlugin
 * @see BubbleDraw
 * @see BubbleFill
 * @see BubbleEffect
 * @author sebastien janaud
 */
public class BubblePlugin extends AbstractPlugin {

    /** bubbles registry */
    private List<Bubble> bubbles;

    /**
     * create bubble plugin
     */
    public BubblePlugin() {
        setName("BubblePlugin");
        setPriority(100);
        bubbles = new ArrayList<Bubble>();
        setAntialiasing(Antialiasing.On);
    }

    /**
     * add bubble
     * 
     * @param bubble
     *            the bubble to add
     */
    public void addBubble(Bubble bubble) {
        bubble.setHost(this);
        bubbles.add(bubble);
    }

    /**
     * remove the bubble
     * 
     * @param bubble
     *            the bubble to remove
     */
    public void removeBubble(Bubble bubble) {
        bubbles.remove(bubble);
    }

    /**
     * paint bubble
     * 
     * @param v2d
     * @param g2d
     * @param bubble
     */
    private void paintBubble(View v2d, Graphics2D g2d, Bubble bubble) {

        if (bubble.getBubbleFill() != null) {
            bubble.getBubbleFill().paintBubble(g2d, bubble);
        }

        if (bubble.getBubbleDraw() != null) {
            bubble.getBubbleDraw().paintBubble(g2d, bubble);
        }

        if (bubble.getBubbleEffect() != null) {
            bubble.getBubbleEffect().paintBubble(g2d, bubble);
        }

    }

    /**
     * solve bubble geometry
     */
    private void solveGeometry() {
        for (Bubble bubble : bubbles) {
            Point2D p2dUserCenter = new Point2D.Double(bubble.getX(),
                                                       bubble.getY());
            Point2D p2dDeviceCenter = bubble.getHost().getProjection()
                    .userToPixel(p2dUserCenter);

            Ellipse2D bubbleShape = new Ellipse2D.Double(p2dDeviceCenter.getX()
                    - bubble.getRadius(), p2dDeviceCenter.getY()
                    - bubble.getRadius(), 2 * bubble.getRadius(),
                                                         2 * bubble.getRadius());

            bubble.setBubbleShape(bubbleShape);
        }

    }

   
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.AbstractPlugin#paintPlugin(org.jensoft.core.view.View, java.awt.Graphics2D, org.jensoft.core.view.ViewPart)
     */
    @Override
    protected void paintPlugin(View view, Graphics2D g2d, ViewPart viewPart) {
        if (viewPart != ViewPart.Device) {
            return;
        }
        solveGeometry();
        for (Bubble bubble : bubbles) {
            paintBubble(view, g2d, bubble);
        }
    }

}
