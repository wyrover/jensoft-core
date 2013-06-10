/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.binding;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jensoft.core.plugin.AbstractPlugin;

/**
 * <code>AbstractX2DPluginDeflater</code>
 * 
 * @author Sebastien Janaud
 */
public abstract class AbstractX2DPluginDeflater<P extends AbstractPlugin> extends DeflaterUtil{
	
	/** X2D document */
	private Document x2dDocument;

    /** inflate plug in */
    private P plugin;
    
    /** xsi type */
    private String XSIType;
    
    /**
     * create inflater
     */
    public AbstractX2DPluginDeflater(){
    }
    
    /**
     * create inflater for the given plugin
     * @param plugin
     */
    public AbstractX2DPluginDeflater(P plugin){
        this.plugin=plugin;
    }
    
    
    protected Element createPluginRootElement(){
    	Element pluginElement = getX2dDocument().createElement("plugin");
		pluginElement.setAttribute("xsi:type", getXSIType());
		return pluginElement;
    }

    /**
	 * @return the xSIType
	 */
	public String getXSIType() {
		return XSIType;
	}

	/**
	 * @param xSIType the xSIType to set
	 */
	public void setXSIType(String xSIType) {
		XSIType = xSIType;
	}
	
	

	/**
	 * @return the x2dDocument
	 */
	public Document getX2dDocument() {
		return x2dDocument;
	}

	/**
	 * @param x2dDocument the x2dDocument to set
	 */
	public void setX2dDocument(Document x2dDocument) {
		this.x2dDocument = x2dDocument;
	}

	/**
     * @return the plug in
     */
    public P getPlugin() {
        return plugin;
    }

   
    /**
     * @param plugin
     *            the plug in to set
     */
    public void setPlugin(P plugin) {
        this.plugin = plugin;
    }

    /**
     * deflate the T plugin mean read the given plugin root element and configure plugin
     */
    public abstract Element deflate();


}
