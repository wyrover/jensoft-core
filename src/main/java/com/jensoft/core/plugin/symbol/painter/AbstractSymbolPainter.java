/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.symbol.painter;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.symbol.SymbolComponent;
import com.jensoft.core.window.WindowPart;

public abstract class AbstractSymbolPainter implements SymbolPainter {

    @Override
    public void paintSymbol(Graphics2D g2d, SymbolComponent symbol,
            WindowPart windowPart) {
    }

}
