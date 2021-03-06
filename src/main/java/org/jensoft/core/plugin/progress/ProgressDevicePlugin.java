/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.progress;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.jensoft.core.plugin.AbstractPlugin;
import org.jensoft.core.projection.Projection;
import org.jensoft.core.view.View;
import org.jensoft.core.view.ViewPart;

/**
 * <code>ProgressPlugin</code>
 * <p>
 * progress plug in provides progress support for tasks.
 * </p>
 * 
 * @author Sebastien Janaud
 */
public class ProgressDevicePlugin extends AbstractPlugin {

    /** lock progress flag */
    boolean lockProgress = false;

    /** current progress value */
    private double currentProgress = 0;

    /**
     * create progress plugin
     */
    public ProgressDevicePlugin() {
        setName("ProgressPlugin");
    }

    /**
     * start progress
     */
    public void start() {
        lockProgress = true;
    }

    /**
     * stop progress
     */
    public void stop() {
        lockProgress = false;
    }

    /**
     * set current progress
     * 
     * @param currentScanValue
     */
    public void setCurentProcess(double currentScanValue) {
        this.currentProgress = currentScanValue;
    }

    /**
     * paint current progress
     * 
     * @param g2d
     *            the graphics context
     */
    private void paintScanningProcess(Graphics2D g2d) {
        Projection w2d = getProjection();

        Point2D p2dUser = new Point2D.Double(currentProgress, 0);
        Point2D p2ddevice = w2d.userToPixel(p2dUser);

        // AREA
        Rectangle2D recZone2D = new Rectangle2D.Double(0, 0, p2ddevice.getX(),
                                                       getProjection().getDevice2D().getDeviceHeight());

        // g2d.setPaint(new Color(51,153,255));

        g2d.setPaint(getProjection().getThemeColor());

        g2d.setComposite(java.awt.AlphaComposite.getInstance(
                                                             java.awt.AlphaComposite.SRC_OVER, 0.3f));
        g2d.fill(recZone2D);

        // ANNOTATION
        g2d.setComposite(java.awt.AlphaComposite.getInstance(
                                                             java.awt.AlphaComposite.SRC_OVER, 1f));
        String annotation = "Current Process : " + currentProgress;
        g2d.setFont(new Font("Tahoma", Font.PLAIN, 10));
        g2d.setColor(Color.BLACK);
        g2d.drawString(annotation, 5, 20);

    }

   
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.AbstractPlugin#paintPlugin(org.jensoft.core.view.View, java.awt.Graphics2D, org.jensoft.core.view.ViewPart)
     */
    @Override
    protected void paintPlugin(View v2d, Graphics2D g2d, ViewPart viewPart) {
        if (viewPart != ViewPart.Device) {
            return;
        }
        if (lockProgress) {
            paintScanningProcess(g2d);
        }
    }
}
