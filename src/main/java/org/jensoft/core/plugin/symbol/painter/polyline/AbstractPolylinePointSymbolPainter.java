/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.symbol.painter.polyline;

import java.awt.Graphics2D;

import org.jensoft.core.plugin.symbol.PolylinePointSymbol;
import org.jensoft.core.plugin.symbol.SymbolComponent;
import org.jensoft.core.plugin.symbol.painter.AbstractSymbolPainter;
import org.jensoft.core.view.ViewPart;

/**
 * <code>PolylinePointSymbolPainter</code>
 * 
 * @author sebastien janaud
 */
public abstract class AbstractPolylinePointSymbolPainter extends AbstractSymbolPainter {

    protected abstract void paintPolylinePointSymbol(Graphics2D g2d, PolylinePointSymbol point);

    @Override
    public final void paintSymbol(Graphics2D g2d, SymbolComponent symbol,
            ViewPart viewPart) {
        if (symbol.isVisible()) {
            paintPolylinePointSymbol(g2d, (PolylinePointSymbol) symbol);
        }
    }

}
