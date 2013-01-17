/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.stripe.manager;

import java.util.List;

import com.jensoft.core.plugin.stripe.Stripe;
import com.jensoft.core.plugin.stripe.StripePlugin;
import com.jensoft.core.plugin.stripe.StripePlugin.StripeOrientation;
import com.jensoft.core.window.Window2D;

/**
 * <code>StripeManager</code> defines the interface for stripe manager
 * 
 * @see StripePlugin
 * @see StripeOrientation
 * @see Stripe
 * @author Sebastien Janaud
 */
public interface StripeManager {

    /**
     * a manager define a policy to create stripe
     * 
     * @return the generated stripes
     */
    public List<Stripe> getStripes();

    /**
     * set the window2D
     * 
     * @param w2d
     *            the window2D to set
     */
    public void setWindow2D(Window2D w2d);

    /**
     * get the window2D
     * 
     * @return the window2D
     */
    public Window2D getWindow2D();

    /**
     * set the stripe orientation
     * 
     * @param orientation
     *            the orientation to set
     */
    public void setStripeOrientation(StripeOrientation orientation);

    /**
     * get stripe orientation
     * 
     * @return the stripe orientation
     */
    public StripeOrientation getStripeOrientation();

}
