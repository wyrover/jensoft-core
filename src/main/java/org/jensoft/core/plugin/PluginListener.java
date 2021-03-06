/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin;

import java.util.EventListener;

/**
 * <code>PluginListener</code> takes the responsibility to broadcast a plug
 * lock/unlock event
 * 
 * @since 1.0
 * @author sebastien janaud
 */
public interface PluginListener<T extends AbstractPlugin> extends EventListener {

	/**
	 * call when the plugin is selected
	 * 
	 * @param pluginEvent
	 */
	public void pluginSelected(PluginEvent<T> pluginEvent);

	/**
	 * call when a plugin is unlock selected
	 * 
	 * @param pluginEvent
	 */
	public void pluginUnlockSelected(PluginEvent<T> pluginEvent);
}
