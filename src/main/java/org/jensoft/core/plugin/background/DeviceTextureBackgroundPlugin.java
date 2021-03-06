/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.background;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

import org.jensoft.core.plugin.AbstractPlugin;
import org.jensoft.core.view.View;
import org.jensoft.core.view.ViewPart;

/**
 * <code>TexturePlugin</code>
 * <p>
 * knows how to paint texture in the device background.
 * </p>
 */
public class DeviceTextureBackgroundPlugin extends AbstractPlugin {

    /** texture paint */
    private TexturePaint texture;

    /** texture alpha */
    private float textureAlpha = 1f;

    public DeviceTextureBackgroundPlugin(TexturePaint texture) {
        this.texture = texture;
    }

    /**
     * @return the texture
     */
    public TexturePaint getTexture() {
        return texture;
    }

    /**
     * @param texture
     *            the texture to set
     */
    public void setTexture(TexturePaint texture) {
        this.texture = texture;
    }

    /**
     * @return the textureAlpha
     */
    public float getTextureAlpha() {
        return textureAlpha;
    }

    /**
     * @param textureAlpha
     *            the textureAlpha to set
     */
    public void setTextureAlpha(float textureAlpha) {
        this.textureAlpha = textureAlpha;
    }

   
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.AbstractPlugin#paintPlugin(org.jensoft.core.view.View, java.awt.Graphics2D, org.jensoft.core.view.ViewPart)
     */
    @Override
    public void paintPlugin(View view, Graphics2D g2d, ViewPart viewPart) {

        if (viewPart != ViewPart.Device) {
            return;
        }

        JComponent comp = view.getViewPartComponent(viewPart);
        Rectangle2D device = new Rectangle2D.Double(0, 0, comp.getWidth(),
                                                    comp.getHeight());

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                    textureAlpha));
        g2d.setPaint(texture);
        g2d.fill(device);
        g2d.setComposite(AlphaComposite
                .getInstance(AlphaComposite.SRC_OVER, 1f));
    }

}
