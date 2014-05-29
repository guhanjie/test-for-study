package com.guhanjie.image;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.awt.image.DirectColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.guhanjie.image.model.App;
import com.guhanjie.image.model.Model;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class CreateImg {
	
	public static Random random = new Random();
	private int imgWidth = 600;
	private int imgHeight = 600;
	private int isvWidth = 80;
	private int isvHeight = 60;
	private int appWidth = 60;
	private int appHeight = 40;
	private Point centerPoint;
	private int lineLength = 200;
	private Model model;
	
	public CreateImg(Model model) {
		this.model = model;
		centerPoint = new Point(imgWidth/2, imgHeight/2);
	}
	
	public void create() {
//		// �趨 BufferedImage �Ŀ����
//		int width = 600, height = 600;
//		int size = width * height;
//		// �������飬���Ա����Ӧ BufferedImage �����ؼ���
//		int [] pixels = new int [size];
//		// ��ָ�����鴴����ָ����С�� DataBuffer
//		DataBuffer dataBuffer = new DataBufferInt(pixels, size);
//		// ����һ�� WritableRaster �������� �����դ
//		WritableRaster raster = Raster.createPackedRaster (dataBuffer, width, height,width, new int [] { 0xFF0000, 0xFF00, 0xFF }, null );
//		// ����һ�� 24 λ�� RGB ɫ��ģ�ͣ��������Ӧ�� R �� G �� B ����
//		DirectColorModel directColorModel = new DirectColorModel(24, 0xFF0000, 0xFF00, 0xFF);
//		// ����Ϊ 32 λ RGB ɫ��ģ��
//		// DirectColorModel directColorModel = new DirectColorModel(32, 0xFF000000, 0xFF0000, 0xFF00, 0xFF);
//		// ���� BufferedImage, Ԥ�� Alpha ��������
//		BufferedImage bi = new BufferedImage(directColorModel, raster, true , null );
		BufferedImage bi = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bi.createGraphics();
        g.setBackground(Color.WHITE);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);		
		g.setRenderingHint(RenderingHints.KEY_TEXT_LCD_CONTRAST, 200);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g.setColor(new Color(255, 255, 255));
		g.fillRect(0, 0, imgWidth, imgHeight);
		
		HashMap<App, Point> map = getRandomPoints();
		for(App app : model.getAppList()) {
			Point point = map.get(app);
			g.setColor(getRandomColor());
			g.drawLine(centerPoint.x, centerPoint.y, point.x, point.y);
			g.setColor(getRandomColor());
			g.fillOval(point.x-appWidth/2, point.y-appHeight/2, appWidth, appHeight);
			g.setColor(getRandomColor());
			g.setFont(new Font("����", Font.ROMAN_BASELINE, 12));
			g.drawString(app.getAppName(), point.x-appWidth/2, point.y);
		}
		
		g.setColor(getRandomColor());
		g.drawString(model.getIsvName(), centerPoint.x-isvWidth/2, centerPoint.y);
		g.drawString("asdfsadfasfsadfsafsadf", 50, 50);
		g.setColor(getRandomColor());
		g.fillRoundRect(centerPoint.x-isvWidth/2, centerPoint.y-isvHeight/2, isvWidth, isvHeight, 8, 8);
		
		g.dispose();
		try {
		FileOutputStream out = new FileOutputStream("f:\\test\\"+new Date().getTime()+".jpg");
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(bi);
		out.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Color getRandomColor() {
		return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
	}
	
	private double[] getRandomAngles(int n) {
		double avg = 360.0/n;
		double init = random.nextInt(360);
		double[] angles = new double[n]; 
		for(int i=0; i<n; i++) {
			angles[i] = (init+i*avg) % 360;
			System.out.println(angles[i]);
		}
		return angles;
	}
	
	public HashMap<App, Point> getRandomPoints() {
		HashMap<App, Point> map = new HashMap<App, Point>();
		int appNums = model.getAppList().size();
		List<App> appList = model.getAppList();
		double[] angles = getRandomAngles(appNums);
		for(int i=0; i<appNums; i++) {
			double dx,dy;
			double radian = angles[i]/180.0* Math.PI;
			dx = lineLength * Math.sin(radian);
			dy = lineLength * Math.cos(radian);
			int pointX = (int)Math.round(centerPoint.x+dx);
			int pointY = (int)Math.round(centerPoint.y+dy);
			System.out.println("pointX="+pointX);
			System.out.println("pointY="+pointY);
			System.out.println(Math.sqrt(Math.pow(centerPoint.x-pointX, 2.0) + Math.pow(centerPoint.y-pointY, 2.0)));
			map.put(appList.get(i), new Point(pointX, pointY));
		}
		return map;
	}
	
	public static void main(String[] args) {
		new CreateImg(new Model()).create( );
	}
}
