/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.jensoft.core.view.View2D;
import com.jensoft.core.x2d.inflater.AbstractX2DPluginInflater;
import com.jensoft.core.x2d.inflater.X2DViewInflater;
import com.jensoft.core.x2d.inflater.donut2d.Donut2DInflater;
import com.jensoft.core.x2d.inflater.donut3d.Donut3DInflater;
import com.jensoft.core.x2d.inflater.function.AreaFunctionInflater;
import com.jensoft.core.x2d.inflater.function.CurveFunctionInflater;
import com.jensoft.core.x2d.inflater.function.ScatterCurveInflater;
import com.jensoft.core.x2d.inflater.grid.GridInflater;
import com.jensoft.core.x2d.inflater.legend.LegendInflater;
import com.jensoft.core.x2d.inflater.metrics.AxisMetricsInflater;
import com.jensoft.core.x2d.inflater.outline.OutlineInflater;
import com.jensoft.core.x2d.inflater.pie.PieInflater;
import com.jensoft.core.x2d.inflater.translate.TranslateInflater;
import com.jensoft.core.x2d.inflater.zoom.ZoomBoxInflater;
import com.jensoft.core.x2d.inflater.zoom.ZoomObjectifInflater;
import com.jensoft.core.x2d.inflater.zoom.ZoomWheelInflater;
import com.jensoft.core.x2d.lang.X2DSchema;
import com.jensoft.core.x2d.lang.X2DSchemaErrorHandler;

/**
 * <code>X2D</code>
 * <p>
 * takes the responsibility to register X2D source and produce {@link View2D}
 * </p>
 * 
 * @author Sebastien Janaud
 */
public class X2D {

    /** observer clients errors handlers */
    private List<X2DErrorHandler> errorHandlers;

    /** x2d template document */
    private Document x2dDocument;

    /** X2D inflater */
    private X2DViewInflater x2dInflater;

    /** the view2D */
    private View2D view2D;

    /** core annotated inflaters */
    private List<AbstractX2DPluginInflater<?>> coreInflaters = new ArrayList<AbstractX2DPluginInflater<?>>();

    /** user inflaters */
    private List<AbstractX2DPluginInflater<?>> inflaters = new ArrayList<AbstractX2DPluginInflater<?>>();

    /** lookup native core inflaters */
    private boolean lookupCoreInflaters = true;

    /**
     * create <code>X2D</code>
     */
    public X2D() {
        initHandlers();
        initCoreInflaters();
    }

    /**
     * initialize errors handlers related objects
     */
    private void initHandlers() {
        errorHandlers = new ArrayList<X2DErrorHandler>();
    }

    /**
     * initialize core inflaters
     */
    private void initCoreInflaters() {
//        System.err.println("--X2D initCoreInflater--");
//        List<Class<?>> inflaters = PluginPlatform.scanX2DInflater(X2DInflater.class.getPackage().getName());
//        System.err.println("--X2D found --:"+inflaters.size());
//        for (Class<?> inflaterClass : inflaters) {
//            try {
//                AbstractX2DPluginInflater<?> inflater = (AbstractX2DPluginInflater<?>) inflaterClass.newInstance();
//                coreInflaters.add(inflater);
//            }
//            catch (InstantiationException e) {
//                e.printStackTrace();
//            }
//            catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        }
    	
    	//scan trows file permission exception in applet mode, via findClasses in patlformPlugin
    	//TODO find proper way to scan annotation in applet mode (via url and not file maybe!)
    	
    	coreInflaters.add(new OutlineInflater());

		// legend
		coreInflaters.add(new LegendInflater());

		// pie - donut2D - donut3D
		coreInflaters.add(new Donut3DInflater());
		coreInflaters.add(new Donut2DInflater());
		coreInflaters.add(new PieInflater());

		// metrics
		coreInflaters.add(new AxisMetricsInflater.FreeMetricsInflater());
		coreInflaters.add(new AxisMetricsInflater.FlowMetricsInflater());
		coreInflaters.add(new AxisMetricsInflater.StaticMetricsInflater());
		coreInflaters.add(new AxisMetricsInflater.ModeledMetricsInflater());
		coreInflaters.add(new AxisMetricsInflater.TimeMetricsInflater());
		coreInflaters.add(new AxisMetricsInflater.MultiplierMetricsInflater());
		coreInflaters.add(new AxisMetricsInflater.MultiMultiplierMetricsInflater());

		// grids
		coreInflaters.add(new GridInflater.FreeGridInflater());
		coreInflaters.add(new GridInflater.FlowGridInflater());
		coreInflaters.add(new GridInflater.StaticGridInflater());
		coreInflaters.add(new GridInflater.ModeledGridInflater());
		coreInflaters.add(new GridInflater.MultiplierGridInflater());
		// registerInflater(new GridInflater.TimeGridInflater());

		// functions
		coreInflaters.add(new CurveFunctionInflater());
		coreInflaters.add(new AreaFunctionInflater());
		coreInflaters.add(new ScatterCurveInflater());

		// zooms
		coreInflaters.add(new ZoomWheelInflater());
		coreInflaters.add(new ZoomBoxInflater());
		coreInflaters.add(new ZoomObjectifInflater());

		// translate
		coreInflaters.add(new TranslateInflater());
    }

    /**
     * @return the lookupCoreInflaters
     */
    public boolean isLookupCoreInflaters() {
        return lookupCoreInflaters;
    }

    /**
     * @param lookupCoreInflaters
     *            the lookupCoreInflaters to set
     */
    public void setLookupCoreInflaters(boolean lookupCoreInflaters) {
        this.lookupCoreInflaters = lookupCoreInflaters;
    }

    /**
     * add jet error handler
     * 
     * @param handler
     *            the handler to add
     */
    public void addX2DErrorHandler(X2DErrorHandler handler) {
        errorHandlers.add(handler);
    }

    /**
     * register the source template
     * 
     * @param xlmlSource
     *            the xml source as string
     */
    public void registerX2DSource(String xmlSource) throws X2DException {

        X2DSchemaErrorHandler errorHandler = new X2DSchemaErrorHandler();
        try {

            X2DSchema.validX2D(xmlSource, errorHandler);
            if (!errorHandler.hasErrors()) {
                x2dDocument = X2DSchema.parseX2D(xmlSource);
                x2dDocument.normalize();
                x2dInflater = new X2DViewInflater();
                x2dInflater.getInflaters().addAll(inflaters);
                if (lookupCoreInflaters) {
                    x2dInflater.getInflaters().addAll(coreInflaters);
                }
                x2dInflater.setX2D(x2dDocument);
                view2D = x2dInflater.inflate();
            }
            else {
                X2DException x2dException = new X2DException(
                                                             "X2D could not register template source , see exception errors.");

                x2dException.setErrors(errorHandler.getErrors());
                throw x2dException;
            }

        }
        catch (SAXException e) {
            throw new X2DException(e);
        }
        catch (IOException e) {
            throw new X2DException(e);
        }
        catch (ParserConfigurationException e) {
            throw new X2DException(e);
        }
    }

    /**
     * register the source template
     * 
     * @param xmlDocument
     *            the xml source document
     */
    public void registerX2DDocument(Document xmlDocument) throws X2DException {
        System.out.println("register document");
        X2DSchemaErrorHandler errorHandler = new X2DSchemaErrorHandler();

        X2DSchema.validX2D(xmlDocument, errorHandler);
        if (!errorHandler.hasErrors()) {
            x2dDocument = xmlDocument;
            x2dDocument.normalize();
            x2dInflater = new X2DViewInflater();
            x2dInflater.getInflaters().addAll(inflaters);
            if (lookupCoreInflaters) {
                x2dInflater.getInflaters().addAll(coreInflaters);
            }
            x2dInflater.setX2D(x2dDocument);
            view2D = x2dInflater.inflate();
        }
        else {
            X2DException x2dException = new X2DException(
                                                         "X2D could not register template source , see exception errors.");

            x2dException.setErrors(errorHandler.getErrors());
            throw x2dException;
        }

    }

    /**
     * register the new x2d file template
     * 
     * @param x2dFile
     */
    public void registerX2DFile(File x2dFile) throws X2DException {

        X2DSchemaErrorHandler errorHandler = new X2DSchemaErrorHandler();
        try {

            X2DSchema.validX2D(x2dFile, errorHandler);
            if (!errorHandler.hasErrors()) {
                x2dDocument = X2DSchema.parseX2D(x2dFile);
                x2dDocument.normalize();
                x2dInflater = new X2DViewInflater();
                x2dInflater.getInflaters().addAll(inflaters);
                if (lookupCoreInflaters) {
                    x2dInflater.getInflaters().addAll(coreInflaters);
                }
                x2dInflater.setX2D(x2dDocument);
                view2D = x2dInflater.inflate();
            }
            else {
                X2DException x2dException = new X2DException("X2D could not register template file "
                        + x2dFile.getName() + ", see exception errors.");
                x2dException.setErrors(errorHandler.getErrors());
                throw x2dException;
            }

        }
        catch (SAXException e) {
            throw new X2DException(e);
        }
        catch (IOException e) {
            throw new X2DException(e);
        }
        catch (ParserConfigurationException e) {
            throw new X2DException(e);
        }

        System.out.println("X2D : register x2d template : " + x2dFile.getName() + " completed");
    }

    /**
     * register the new x2d input stream
     * 
     * @param x2dInputStream
     */
    public void registerX2D(InputStream x2dInputStream) throws X2DException {
        if (x2dInputStream == null)
            throw new X2DException("X2D input stream cannot be null.");

        X2DSchemaErrorHandler errorHandler = new X2DSchemaErrorHandler();
        try {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = x2dInputStream.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();

            InputStream is1 = new ByteArrayInputStream(baos.toByteArray());
            InputStream is2 = new ByteArrayInputStream(baos.toByteArray());

            x2dInputStream.close();
            baos.close();

            X2DSchema.validX2D(is1, errorHandler);
            if (!errorHandler.hasErrors()) {
                x2dDocument = X2DSchema.parseX2D(is2);
                x2dDocument.normalize();
                x2dInflater = new X2DViewInflater();
                x2dInflater.getInflaters().addAll(inflaters);
                if (lookupCoreInflaters) {
                    x2dInflater.getInflaters().addAll(coreInflaters);
                }
                x2dInflater.setX2D(x2dDocument);
                view2D = x2dInflater.inflate();
                is1.close();
                is2.close();
            }
            else {
                X2DException x2dException = new X2DException("X2D could not register input stream template file "
                        + ", see exception errors.");
                x2dException.setErrors(errorHandler.getErrors());
                throw x2dException;
            }

        }
        catch (SAXException e) {
            throw new X2DException(e);
        }
        catch (IOException e) {
            throw new X2DException(e);
        }
        catch (ParserConfigurationException e) {
            throw new X2DException(e);
        }

        System.out.println("X2D : register x2d input stream template  completed");
    }

    /**
     * @return the inflaters
     */
    public List<AbstractX2DPluginInflater<?>> getInflaters() {
        return inflaters;
    }

    /**
     * @param inflaters
     *            the inflaters to set
     */
    public void setInflaters(List<AbstractX2DPluginInflater<?>> inflaters) {
        this.inflaters = inflaters;
    }

    /**
     * register plugin inflater for this view emitter
     * 
     * @param inflater
     *            the inflater to register
     */
    public void registerInflater(AbstractX2DPluginInflater<?> inflater) {
        if (inflater.getXSIType() == null) {
            throw new IllegalArgumentException("XSI Type for Inflater :" + inflater.getClass()
                    + " is null. it should be provided");
        }
        inflaters.add(inflater);
    }

    /**
     * @return the x2dDocument
     */
    public Document getX2dDocument() {
        return x2dDocument;
    }

    /**
     * @return the view2D
     */
    public View2D getView2D() {
        return view2D;
    }

    /**
     * get view key
     * 
     * @return view key
     */
    public String getViewKey() {
        return x2dInflater.getViewKey();
    }
    
    /**
     * get view key
     * 
     * @return view key
     */
    public String getApiKey() {
        return x2dInflater.getAPIKey();
    }

}
