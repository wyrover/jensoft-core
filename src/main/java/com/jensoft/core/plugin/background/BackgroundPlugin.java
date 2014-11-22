/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.background;

import java.awt.Graphics2D;
import java.awt.Image;

import com.jensoft.core.device.Device;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.view.View;
import com.jensoft.core.view.ViewPart;

/**
 * <code>BackgroundPlugin</code> takes the responsibility to paint device background with the given image
 * 
 * @author sebastien janaud
 */
public class BackgroundPlugin extends AbstractPlugin {

    /** the background image */
    private Image deviceImage;

    /** rescale image, default is true */
    private boolean rescale = true;

    /**
     * create the device background plug in with given image to paint in the device background
     */
    public BackgroundPlugin(Image deviceImage) {
        setName("BackgroundPlugin");
        this.deviceImage = deviceImage;
        setPriority(-10000);
    }

    /**
     * @return the deviceImage
     */
    public Image getDeviceImage() {
        return deviceImage;
    }

    /**
     * @param deviceImage
     *            the deviceImage to set
     */
    public void setDeviceImage(Image deviceImage) {
        this.deviceImage = deviceImage;
    }

    /**
     * @return the rescale
     */
    public boolean isRescale() {
        return rescale;
    }

    /**
     * @param rescale
     *            the rescale to set
     */
    public void setRescale(boolean rescale) {
        this.rescale = rescale;
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin#paintPlugin(com.jensoft.core.view.View, java.awt.Graphics2D, com.jensoft.core.view.ViewPart)
     */
    @Override
    protected void paintPlugin(View view, Graphics2D g2d, ViewPart viewPart) {
        if (viewPart != ViewPart.Device)
            return;

        Device d2d = view.getDevice2D();
        if (rescale) {
            g2d.drawImage(deviceImage, 0, 0, d2d.getDeviceWidth(), d2d.getDeviceHeight(), null);
        }
        else {
            g2d.drawImage(deviceImage, d2d.getDeviceWidth() / 2 - deviceImage.getWidth(null) / 2, d2d.getDeviceHeight()
                    / 2 - deviceImage.getHeight(null) / 2, null);
        }
    }

}
