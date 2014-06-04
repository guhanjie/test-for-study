package com.guhanjie.image;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.DefaultImageHandler;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;


/**
 * Using batik-svg to add the url image to SVG file
 * @author guhanjie
 *
 */
public class URLImageToSVGByBatik {
	
	public void paint(Graphics2D g2d) {
		String url = "http://d.hiphotos.baidu.com/image/pic/item/38dbb6fd5266d0165a902328952bd40735fa3520.jpg";
		Image image = new ImageFromURL(url, 271, 271);
		g2d.drawImage(image, 0, 0, 150, 150, null);
	}

	public static void main(String[] args) throws IOException {

		// Get a DOMImplementation.
		DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();

		// Create an instance of org.w3c.dom.Document.
		String svgNS = "http://www.w3.org/2000/svg";
		Document document = domImpl.createDocument(svgNS, "svg", null);		
		
		// Create an instance of the SVG Generator.
		SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
		// Set ImageHandler to process the SVG image element
		svgGenerator.getGeneratorContext().setImageHandler(new DefaultImageHandler());
		svgGenerator.setSVGCanvasSize(new Dimension(500, 500));

		// Ask the test to render into the SVG Graphics2D implementation.
		URLImageToSVGByBatik test = new URLImageToSVGByBatik();
		test.paint(svgGenerator);
					
		// Finally, stream out SVG to the standard output using
		// UTF-8 encoding.
		boolean useCSS = false; // we want to use CSS style attributes
		Writer out = new OutputStreamWriter(System.out, "UTF-8");
		svgGenerator.stream(out, useCSS);
	}
	
}

class ImageFromURL extends Image {
	private String url;
	private int width,height;
	public ImageFromURL(String url, int width, int height) {
		this.url = url;
		this.width = width;
		this.height = height;
	}
	@Override
	public String toString() {
		return url;
	}
	@Override
	public int getWidth(ImageObserver observer) {
		// TODO Auto-generated method stub
		return this.width;
	}
	@Override
	public int getHeight(ImageObserver observer) {
		// TODO Auto-generated method stub
		return this.height;
	}
	@Override
	public ImageProducer getSource() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Graphics getGraphics() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object getProperty(String name, ImageObserver observer) {
		// TODO Auto-generated method stub
		return null;
	}
}
