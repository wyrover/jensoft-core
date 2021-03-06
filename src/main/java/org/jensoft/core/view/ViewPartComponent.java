/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Collections;
import java.util.List;

import javax.swing.JComponent;

import org.jensoft.core.plugin.AbstractPlugin;
import org.jensoft.core.projection.Projection;

/**
 * <code>WindowPartComponent</code> defines a base view part component
 * 
 * @since 1.0
 * 
 * @author Sebastien Janaud
 */
public class ViewPartComponent extends JComponent implements MouseListener,
        MouseMotionListener, MouseWheelListener {

    /** serial version uid */
    private final static long serialVersionUID = 651449492959746328L;

    /** zone */
    private ViewPart viewPart;

    /** host view */
    private View view;

    /** lock plugin */
    boolean lockPlugins = true;

    /**
     * create window part component
     * 
     * @param windowPart
     *            the window part to set
     * @param view
     *            the view to set
     */
    public ViewPartComponent(ViewPart viewPart, View view) {
        this.viewPart = viewPart;
        this.view = view;
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
    }

    /**
     * unlock all plugins for this component
     */
    public void unlockPlugins() {
        lockPlugins = false;
    }

    /**
     * lock all plugins for this component
     */
    public void lockPlugins() {
        lockPlugins = true;
    }

    /**
     * Paint device backdrop
     * 
     * @param g2d
     *            the graphics2D context
     */
    private void paintBackdrop(Graphics2D g2d) {
        if (!isOpaque()) {
            return;
        }

        if (getBackground() != null) {
            g2d.setColor(getBackground());
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // System.out.println("mouse moved in window border component!!!");

    }

    /**
     * paint part component
     */
    @Override
    public void paintComponent(Graphics g) {
        if (!lockPlugins) {
            return;
        }
        Graphics2D g2d = (Graphics2D) g.create();
        paintBackdrop(g2d);
        paintPlugins(g2d);
        g2d.dispose();
    }

    /**
     * Paint plugins
     * 
     * @param g2d
     *            the graphics2D context
     */
    protected void paintPlugins(Graphics2D g2d) {

        List<Projection> projections = view.getProjections();

        // paint all window, exlude active window
        for (Projection proj : projections) {

            if (!proj.isVisible()) {
                continue;
            }

            if (!proj.equals(view.getActiveProjection())) {
                // System.out.println("paint NON ACTIVE window part for window :"+w2d.getName());
                List<AbstractPlugin> plugins = proj.getPluginRegistry();
                Collections.sort(plugins,
                                 AbstractPlugin.getPriorityComparator());
                if (plugins != null) {
                    for (int j = 0; j < plugins.size(); j++) {
                        AbstractPlugin plugin = plugins.get(j);
                        // System.out.println("paint non active plugin : "+plugin.getName());
                        plugin.paint(view, g2d, viewPart);
                    }
                }
            }

        }

        // paint active window
        if (view.getActiveProjection() != null && view.getActiveProjection().isVisible()) {

            // System.out.println("paint ACTIVE window part for window :"+v2d.getActiveWindow().getName());
            List<AbstractPlugin> plugins = view.getActiveProjection()
                    .getPluginRegistry();
            if (plugins != null) {
                Collections.sort(plugins,
                                 AbstractPlugin.getPriorityComparator());
                for (int j = 0; j < plugins.size(); j++) {
                    AbstractPlugin plugin = plugins.get(j);
                    // System.out.println("paint active plugin : "+plugin.getName());
                    plugin.paint(view, g2d, viewPart);
                }
            }
        }

        try {
            view.getWidgetPlugin().paint(view, g2d, viewPart);
        }
        catch (Throwable e) {
            e.printStackTrace();
        }

    }

}
