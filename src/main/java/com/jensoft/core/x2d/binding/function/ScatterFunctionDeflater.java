/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.binding.function;

import org.w3c.dom.Element;

import com.jensoft.core.plugin.function.FunctionPlugin.ScatterFunction;
import com.jensoft.core.x2d.binding.AbstractX2DPluginDeflater;
import com.jensoft.core.x2d.binding.X2DBinding;

/**
 * <code>ScatterFunctionDeflater</code>
 * @author sebastien janaud
 *
 */
@X2DBinding(xsi="ScatterPlugin",plugin=ScatterFunction.class)
public class ScatterFunctionDeflater extends AbstractX2DPluginDeflater<ScatterFunction> {

	/* (non-Javadoc)
	 * @see com.jensoft.core.x2d.binding.AbstractX2DPluginDeflater#deflate()
	 */
	@Override
	public Element deflate(ScatterFunction plugin) {
		Element pluginElement = createPluginRootElement();
		return pluginElement;
	}
	
	

}
