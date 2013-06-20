/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.imageio.ImageIO;

import com.jensoft.core.demo.nature.JenSoftAPIDemo;
import com.jensoft.core.view.Portfolio;
import com.jensoft.core.view.Portfolio.ImageType;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.Window2D;
import com.jensoft.core.x2d.binding.AbstractX2DPluginInflater;
import com.jensoft.core.x2d.binding.X2DBinding;

/**
 * <code>PluginPlatform</code>
 * 
 * @author Sebastien Janaud
 * @since 1.0
 */
public class PluginPlatform {

    /** plugin plateform instance */
    private static PluginPlatform pluginUI;

    /** plugins registry */
    private List<AbstractPlugin> plugins;

    /** window registry */
    private List<Window2D> windows;

    /**
     * create new platform instance
     */
    private PluginPlatform() {
        plugins = new ArrayList<AbstractPlugin>();
        windows = new ArrayList<Window2D>();
    }

    /**
     * get the plug in plateform
     * 
     * @return the plugin PluginPlatform
     */
    public static PluginPlatform instance() {
        if (pluginUI == null) {
            pluginUI = new PluginPlatform();
        }
        return pluginUI;
    }

    /**
     * get the plugin registry
     * 
     * @return the registered plug ins
     */
    public List<AbstractPlugin> getPlugins() {
        return plugins;
    }

    /**
     * get the windows registry
     * 
     * @return the registered windows
     */
    public List<Window2D> getWindows() {
        return windows;
    }

    /**
     * register the specified plug in into platform
     * 
     * @param plugin
     *            the plug in to register
     */
    public void register(AbstractPlugin plugin) {
        if (!plugins.contains(plugin)) {
            plugins.add(plugin);
        }
    }

    /**
     * register the specified window2d into platform
     * 
     * @param window2d
     *            the window2d to register
     */
    public void register(Window2D window2d) {
        if (!windows.contains(window2d)) {
            windows.add(window2d);
        }
    }

    /**
     * get the plug in registry by class
     * 
     * @param <T>
     *            plug in type
     * @param c
     *            the plug in class
     * @return the register plug
     */
    public <T extends AbstractPlugin> List<T> getPlugins(Class<T> c) {
        List<T> ps = new ArrayList<T>();
        for (AbstractPlugin plugin : plugins) {
            if (plugin.getClass().getName().equals(c.getName())) {
                ps.add((T) plugin);
            }
        }
        return ps;
    }

    /**
     * get the plug in registry by class for specified window
     * 
     * @param <T>
     *            plug in type
     * @param pluginClass
     *            the plug in class
     * @param window2D
     *            the window2D
     * @return the register plug
     */
    public <T extends AbstractPlugin> List<T> getPlugins(Class<T> pluginClass,
            Window2D window2D) {
        List<T> ps = new ArrayList<T>();
        for (AbstractPlugin plugin : plugins) {
            if (plugin.getWindow2D() != null
                    && plugin.getWindow2D().equals(window2D)) {
                if (plugin.getClass().getName().equals(pluginClass.getName())) {
                    ps.add((T) plugin);
                }
            }
        }
        return ps;
    }

    /**
     * Create the {@link Portfolio} for the given package in the specified system directory
     * 
     * @param packageName
     *            the package name to scan for lookup portfolio
     * @param portfolioDirectory
     *            the output directory
     */
    public static void createPortfolio(String packageName, String portfolioDirectory) {
        createPortfolio(packageName, portfolioDirectory, -1, -1);
    }

    /**
     * create portfolio for the given class
     * @param classPortfolio
     * @param portfolioDirectory
     * @param imageWidth
     * @param imageHeight
     */
    public static void createPortfolioClass(Class classPortfolio, String portfolioDirectory, int imageWidth, int imageHeight) {

        for (Method method : classPortfolio.getMethods()) {
            if (method.isAnnotationPresent(Portfolio.class)) {

                if (Modifier.isStatic(method.getModifiers())) {
                    Portfolio p = method.getAnnotation(Portfolio.class);
                    Class<?> returnType = method.getReturnType();
                    if (View2D.class.isAssignableFrom(returnType)) {
                        String name = p.name();
                        ImageType imageType = p.imageType();
                        try {
                            View2D v = (View2D) method.invoke(null);

                            int w = 0;
                            int h = 0;
                            if (imageWidth > 0 && imageHeight > 0) {
                                w = imageWidth;
                                h = imageHeight;
                            }
                            else {
                                w = p.width();
                                h = p.height();
                            }

                            BufferedImage image = v.getImageView(w, h);

                            File portfolioDir = new File(portfolioDirectory);
                            portfolioDir.mkdirs();
                            try {
                                System.out
                                        .println(classPortfolio.getSimpleName() + " generate portfolio : "
                                                + classPortfolio.getCanonicalName() + "#" + method.getName()
                                                + " with name :" + name);
                                ImageIO.write(image, imageType.name()
                                        .toLowerCase(), new FileOutputStream(
                                                                             portfolioDirectory
                                                                                     + File.separator
                                                                                     + name
                                                                                     + "."
                                                                                     + imageType.name()
                                                                                             .toLowerCase()));
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        catch (Throwable e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
                else {
                    System.err.println("Portfolio image not processed, portofolio Method " + method.getName()
                            + " should be 'static'");
                }

            }
        }
    }
    
    public static void createPortfolio(String packageName, String portfolioDirectory, int imageWidth, int imageHeight) {
        List<Class<?>> portofolioClass = scanPortfolio(packageName);
        for (Class<?> c : portofolioClass) {
            createPortfolioClass(c, portfolioDirectory, imageWidth, imageHeight);            
        }
    }

    /**
     * return all {@link AbstractX2DPluginInflater} annotated with {@link X2DBinding} for the given package
     * 
     * @param packageName
     * @return scanned portfolio
     */
    public static List<Class<?>> scanPortfolio(String packageName) {
        List<Class<?>> portfolios = new ArrayList<Class<?>>();
        try {
            Iterable<Class<?>> classes = getClasses(packageName);
            for (Class<?> testClass : classes) {
                for (Method method : testClass.getMethods()) {
                    if (method.isAnnotationPresent(Portfolio.class)) {
                        portfolios.add(testClass);
                        break;
                    }
                }
            }
        }
        catch (ClassNotFoundException e) {

            e.printStackTrace();
        }
        catch (IOException e) {

            e.printStackTrace();
        }
        return portfolios;

    }

    /**
     * return all {@link JenSoftAPIDemo} and inherits from {@link ViewDemo} classes for the given package
     * 
     * @param packageName
     * @return scanned jensoft demo
     */
    public static List<Class<?>> scanJenSoftViewDemo(String packageName) {
        List<Class<?>> classes = scanJenSoftDemo(packageName);       
        return classes;
    }

   

    /**
     * return jensoft demo
     * 
     * @param packageName
     * @return jensoft demos
     */
    public static List<Class<?>> scanJenSoftDemo(String packageName) {
        List<Class<?>> demoClasses = new ArrayList<Class<?>>();
        try {
            Iterable<Class<?>> classes = getClasses(packageName);
            for (Class<?> c : classes) {
                if (c.isAnnotationPresent(JenSoftAPIDemo.class)) {
                    demoClasses.add(c);
                }
            }
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return demoClasses;

    }

    /**
     * return all {@link AbstractX2DPluginInflater} annotated with {@link X2DBinding} for the given package
     * 
     * @param packageName
     * @return x2d demo
     */
    public static List<Class<?>> scanX2DInflater(String packageName) {
        List<Class<?>> inflaters = new ArrayList<Class<?>>();
        try {
            Iterable<Class<?>> classes = getClasses(packageName);
            for (Class<?> testClass : classes) {
                if (testClass.isAnnotationPresent(X2DBinding.class)) {
                    System.err.println("x2d annotation found : " + testClass);
                    if (AbstractX2DPluginInflater.class.isAssignableFrom(testClass)) {
                        inflaters.add(testClass);
                    }
                }
            }
            // for (Class<?> c : inflaters) {
            // System.out.println("[JenSoft API - "+PluginPlatform.class.getSimpleName()+"]"+"found @X2DInflater : " +
            // c.getCanonicalName());
            //
            // X2DInflater xsdInflater = c.getAnnotation(X2DInflater.class);
            // String inflaterXSI = xsdInflater.xsi();
            //
            // System.out.println("Find x2d inflater for XSI type : "+inflaterXSI);
            // System.out.println("class--> : "+c.getName());
            //
            // }
        }
        catch (ClassNotFoundException e) {

            e.printStackTrace();
        }
        catch (IOException e) {

            e.printStackTrace();
        }
        return inflaters;

    }
    

    /**
     * @param packageName
     *            The base package
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    private static Iterable<Class<?>> getClasses(String packageName)
            throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread()
                .getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        List<Class<?>> classes = new ArrayList<Class<?>>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }

        return classes;
    }

    /**
     * Recursive method used to find all classes in a given directory and
     * subdirs.
     * 
     * @param directory
     *            The base directory
     * @param packageName
     *            The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    private static List<Class<?>> findClasses(File directory, String packageName)
            throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(findClasses(file,
                                           packageName + "." + file.getName()));
            }
            else if (file.getName().endsWith(".class")) {
                try {
                    Class<?> c = Class.forName(packageName
                            + '.'
                            + file.getName().substring(0,
                                                       file.getName().length() - 6));
                    classes.add(c);
                }
                catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
        return classes;
    }

    public static void main(String[] args) {
        List<Class<?>> demos = PluginPlatform.scanJenSoftDemo("com.jensoft.demo");
        System.out.println("found demo number : " + demos.size());
        List<Class<?>> viewdemos = PluginPlatform.scanJenSoftViewDemo("com.jensoft.demo");
        System.out.println("found view demo number : " + viewdemos.size());
        
      
        List<Class<?>> x2dinflaters = PluginPlatform.scanX2DInflater(X2DBinding.class.getPackage().getName());
        System.out.println("found x2d inflaters number : " + x2dinflaters.size());
        
    }

}
