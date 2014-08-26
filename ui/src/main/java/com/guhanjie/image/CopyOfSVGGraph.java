package com.guhanjie.image;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.SVGHints;
import org.jfree.graphics2d.svg.SVGUtils;

import com.guhanjie.image.model.App;
import com.guhanjie.image.model.Model;
import com.guhanjie.image.model.Version;

/**
 * 采用JFreeSVG技术生成网状和树状结构的SVG图
 * @author guhanjie
 * 2014-05-28 17:28:15
 */
public class CopyOfSVGGraph {

	private Random random = new Random();
	private int imgWidth = 800;
	private int imgHeight = 800;
	private int masterWidth = 90;
	private int masterHeight = 60;
	private int moduleWidth = 90;
	private int moduleHeight = 60;
	private int itemWidth = 100;
	private int itemHeight = 80;
	private Point masterPoint;
	private int masterLineLength = 200;
	private int bracketBash = 20;
	private int bracketSize = 5;
	private SVGGraphics2D g;
	private Font masterFont = new Font(Font.SANS_SERIF, Font.BOLD, 24);
	private Font moduleFont = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
	private Font itemFont = new Font("Courier", Font.PLAIN, 12);
	private Model model;
	private Stroke masterLineStroke = new BasicStroke(3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
	private Stroke bracketStroke = new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
	private boolean autoResizeByText = true;
	private Color masterTextColor = Color.WHITE;
	private Color moduleTextColor = Color.WHITE;
	private Color bracketLineColor = Color.GRAY;
	private Color itemIconColor = Color.GRAY;
	private Color itemTextColor = Color.BLUE;
	private int paddingPixel = 10;
	private int itemSize = 5; // 子项编号图标的大小
	private Color halvingLineColor = Color.DARK_GRAY;
	private Stroke halvingLineStroke = new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 5.0f, new float[]{3.0f}, 0.0f);
	private Font halvingFont = new Font("Courier", Font.BOLD, 24);

	public CopyOfSVGGraph(Model model) {
		this.model = model;
	}

	/**
	 * 创建一个树状结构的SVG图
	 * @return
	 */
	public String createTreeGraph() {
		// 创建图形上下文
		g = new SVGGraphics2D(imgWidth, imgHeight);
		optimizeQuality();
		
		
		
		g.dispose();// 释放系统资源

		return g.getSVGElement();
	}
	
	/**
	 * 创建一个网状结构的SVG图
	 * @return
	 */
	public String createNetGraph() {
		// 创建图形上下文
		g = new SVGGraphics2D(imgWidth, imgHeight);
		optimizeQuality();
		masterPoint = new Point(imgWidth / 2, imgHeight / 2);
		
		//画分割线
		g.setColor(halvingLineColor);
		g.setStroke(halvingLineStroke);
		g.drawLine(0, imgHeight/2, imgWidth, imgHeight/2);
		//写分区文本信息
		g.setFont(halvingFont);
		g.drawString("应用", 50, imgHeight/4);
		g.drawString("版本", 50, imgHeight*3/4);
		
		// 获取各模块位置
		HashMap<Object, Point> map = calcPosition(model.getAppList(), masterLineLength, 0);
		for (App app : model.getAppList()) {
			// 画主体和模块之间的连线
			Point point = map.get(app);
			g.setColor(getRandomColor());
			g.setStroke(masterLineStroke);
			g.drawLine(masterPoint.x, masterPoint.y, point.x, point.y);
			// 画当前模块对应的所有子项
			List<String> items = app.getAuthenrizeItemList();
			if (items.size() > 0) {
				// 计算item列表的高度，每个item算一行
				int blockHeight = items.size() * (itemFont.getSize()+itemSize) - itemSize;
				// 画包围所有子项的括弧
				Point bashStartPoint = calcPoint(90, 120, point);
				Point bashEndPoint = new Point(bashStartPoint.x+bracketBash, bashStartPoint.y);
				int[] xPoints = {
						bashEndPoint.x + bracketSize,
						bashEndPoint.x,
						bashEndPoint.x,
						bashEndPoint.x + bracketSize };
				int[] yPoints = { bashEndPoint.y - blockHeight / 2 - bracketSize,
						bashEndPoint.y - blockHeight / 2, bashEndPoint.y + blockHeight / 2,
						bashEndPoint.y + blockHeight / 2 + bracketSize };
				g.setStroke(bracketStroke);
				g.setPaint(bracketLineColor);
				g.drawPolyline(xPoints, yPoints, xPoints.length);
				g.drawLine(point.x, point.y, bashStartPoint.x, bashStartPoint.y);
				g.drawLine(bashStartPoint.x, bashStartPoint.y, bashEndPoint.x, bashEndPoint.y);
				// 画所有子项组成的列表
				int itemPosX = bashEndPoint.x+ bracketSize;
				int itemPosY = bashEndPoint.y - blockHeight / 2 + 2;
				for (String item : items) {
					// 画子项编号图标（小圆点）
					g.setColor(itemIconColor);
					g.fillOval(itemPosX, itemPosY, itemSize, itemSize);
					// 画子项文本信息
					g.setFont(itemFont);
					g.setPaint(itemTextColor);
					g.drawString(item, itemPosX + itemSize * 2, itemPosY + itemSize + 1);
					itemPosY += itemFont.getSize() + itemSize;
				}
			}
			// 画模块（椭圆）
			Color color = getRandomColor();
			g.setColor(color);
			g.fillOval(point.x - moduleWidth / 2, point.y - moduleHeight / 2,
					moduleWidth, moduleHeight);
			g.setPaint(color.darker()); // 边框颜色加深，以有凸显的视觉感受
			g.drawOval(point.x - moduleWidth / 2, point.y - moduleHeight / 2,
					moduleWidth, moduleHeight);
			// 画模块的文本信息
			g.setFont(moduleFont);
			g.setPaint(moduleTextColor);
			g.drawString(app.getAppName(),
					point.x - getLength(app.getAppName(), moduleFont) / 2,
					point.y + moduleFont.getSize() / 4);
		}
		
		
		// 获取各模块位置
		HashMap<Object, Point> versionMap = calcPosition(model.getVersionList(), masterLineLength, 180);
		for (Version version : model.getVersionList()) {
			// 画主体和模块之间的连线
			Point point = versionMap.get(version);
			g.setColor(getRandomColor());
			g.setStroke(masterLineStroke);
			g.drawLine(masterPoint.x, masterPoint.y, point.x, point.y);
			// 画当前模块对应的所有子项
			List<App> apps = version.getAppList();
			List<String> items = new ArrayList<String>();
			for(App app: apps) {
				for(String str : app.getAuthenrizeItemList()) {
					items.add(app.getAppName()+": "+str);
				}
			}
			if (items.size() > 0) {
				// 计算item列表的高度，每个item算一行
				int blockHeight = items.size() * (itemFont.getSize()+itemSize) - itemSize;
				// 画包围所有子项的括弧
				Point bashStartPoint = new Point(point.x+moduleWidth/2, point.y);
				Point bashEndPoint = new Point(bashStartPoint.x+bracketBash, bashStartPoint.y);
				int[] xPoints = {
						bashEndPoint.x + bracketSize,
						bashEndPoint.x,
						bashEndPoint.x,
						bashEndPoint.x + bracketSize };
				int[] yPoints = { bashEndPoint.y - blockHeight / 2 - bracketSize,
						bashEndPoint.y - blockHeight / 2, bashEndPoint.y + blockHeight / 2,
						bashEndPoint.y + blockHeight / 2 + bracketSize };
				g.setStroke(bracketStroke);
				g.setPaint(bracketLineColor);
				g.drawPolyline(xPoints, yPoints, xPoints.length);
				g.drawLine(point.x, point.y, bashStartPoint.x, bashStartPoint.y);
				g.drawLine(bashStartPoint.x, bashStartPoint.y, bashEndPoint.x, bashEndPoint.y);
				// 画所有子项组成的列表
				int itemPosX = bashEndPoint.x+ bracketSize;
				int itemPosY = bashEndPoint.y - blockHeight / 2 + 2;
				for (String item : items) {
					// 画子项编号图标（小圆点）
					g.setColor(getRandomColor());
					g.fillRect(itemPosX, itemPosY, itemSize, itemSize);
					// 画子项文本信息
					g.setFont(itemFont);
					g.setPaint(itemTextColor);
					g.drawString(item, itemPosX + itemSize * 2, itemPosY + itemSize + 1);
					itemPosY += itemFont.getSize() + itemSize;
				}
			}
			// 画模块（椭圆）
			Color color = getRandomColor();
			g.setColor(color);
			g.fillOval(point.x - moduleWidth / 2, point.y - moduleHeight / 2,
					moduleWidth, moduleHeight);
			g.setPaint(color.darker()); // 边框颜色加深，以有凸显的视觉感受
			g.drawOval(point.x - moduleWidth / 2, point.y - moduleHeight / 2,
					moduleWidth, moduleHeight);
			// 画模块的文本信息
			g.setFont(moduleFont);
			g.setPaint(moduleTextColor);
			g.drawString(version.getVersionName(),
					point.x - getLength(version.getVersionName(), moduleFont) / 2,
					point.y + moduleFont.getSize() / 4);
		}
		
		
		
		
		
		// 画主体（圆角矩形）
		if (this.isAutoResizeByText()) {
			double ratio = (double) this.masterHeight / (double) this.masterWidth;
			int textLength = getLength(model.getIsvName(), masterFont) + paddingPixel*2;
			masterWidth = (textLength < masterWidth) ? masterWidth : textLength;
			this.masterHeight = (int) (this.masterWidth * ratio);
		}
		Color color = getRandomColor();
		g.setColor(color);
		g.fillRoundRect(masterPoint.x - masterWidth / 2, masterPoint.y
				- masterHeight / 2, masterWidth, masterHeight, 20, 20);
		g.setPaint(color.darker());// 边框颜色加深，以有凸显的视觉感受
		g.drawRoundRect(masterPoint.x - masterWidth / 2, masterPoint.y
				- masterHeight / 2, masterWidth, masterHeight, 20, 20);
		// 画主体的文本信息
		g.setFont(masterFont);
		g.setPaint(masterTextColor);
		g.drawString(model.getIsvName(),
				masterPoint.x - getLength(model.getIsvName(), masterFont) / 2,
				masterPoint.y + masterFont.getSize() / 4);

		g.dispose();// 释放系统资源

		return g.getSVGElement();
	}

	public Color getRandomColor() {
		return new Color(random.nextInt(128) + 90, random.nextInt(128) + 90,
				random.nextInt(128) + 128);
	}

	/**
	 * 计算主体块的各个模块的随机位置（这些随机点是以主体块为圆心lineLength为半径的圆上的均匀分布点）
	 * 
	 * @param appNums
	 * @param lineLength
	 * @return
	 */
	public HashMap<Object, Point> calcPosition(List list, int lineLength, int rotate) {
//		int maxModuleWidth = 0;
		HashMap<Object, Point> map = new HashMap<Object, Point>();
		double[] angles = getAVGAngles(list.size(), rotate);
		for (int i = 0; i < list.size(); i++) {
			Object app = list.get(i);
//			int textLength = getLength(app.getAppName(), moduleFont) + paddingPixel*2;
//			maxModuleWidth = (maxModuleWidth < textLength) ? textLength : maxModuleWidth;
			map.put(app, calcPoint(angles[i], lineLength, masterPoint));
		}
//		if (this.isAutoResizeByText()) {
//			double ratio = (double) this.moduleHeight / (double) this.moduleWidth;
//			this.moduleWidth = (maxModuleWidth<moduleWidth) ? moduleWidth : maxModuleWidth;
//			this.moduleHeight = (int) (this.moduleWidth * ratio);
//		}
		return map;
	}

	/**
	 *  计算点的坐标位置
	 * @param angle 相对于水平坐标的极坐标角度
	 * @param lineLength 半径长度
	 * @param relativePoint 相对的起始点
	 * @return
	 */
	private Point calcPoint(double angle, int lineLength, Point relativePoint) {
		double dx, dy;
		double radian = angle / 180.0 * Math.PI;
		dx = lineLength * Math.cos(radian);
		dy = lineLength * Math.sin(radian);
		int pointX = (int) Math.round(relativePoint.x + dx);
		int pointY = (int) Math.round(relativePoint.y - dy);
		return new Point(pointX, pointY);
	}
	
	/**
	 * 获取均分角度序列
	 * 
	 * @param n
	 * @param rotate 旋转多少度
	 * @return
	 */
	private double[] getAVGAngles(int n, int rotate) {
		double avg = 180.0 / (n+1);
		double[] angles = new double[n];
		for (int i = 0; i < n; i++) {
			angles[i] = 180-avg*(i+1)+rotate;
		}
		return angles;
	}

	/**
	 * 获取字符长度，一个汉字作为 1 个字符, 一个英文字母作为 0.5 个字符
	 * 
	 * @param text
	 * @return 字符长度，如：text="中国",返回 2；text="test",返回 2；text="中国ABC",返回 4.
	 */
	public int getLength(String text, Font font) {
		int textLength = text.length();
		int length = textLength;
		for (int i = 0; i < textLength; i++) {
			if (String.valueOf(text.charAt(i)).getBytes().length > 1) {
				length++;
			}
		}
		length = (length % 2 == 0) ? length / 2 : length / 2 + 1;
		return length * font.getSize();
	}

	/**
	 * 优化绘图质量，可能会牺牲部分性能
	 */
	public void optimizeQuality() {
		// 抗锯齿
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		// 抗文本锯齿
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		// SVG中提高文本清晰度
		g.setRenderingHint(SVGHints.KEY_TEXT_RENDERING,
				SVGHints.VALUE_TEXT_RENDERING_LEGIBILITY);
		g.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
	}

	/**
	 * 设置画图时的线段虚线样式
	 * 
	 * @param dash
	 */
	public void setLineStyle(float[] dash) {
		if (dash == null)
			dash = new float[] { 6.0f, 4.0f, 2.0f, 4.0f, 2.0f, 4.0f };
		g.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_MITER, 5.0f, dash, 0.0f));
	}

	/**
	 * 设置透明
	 * 
	 * @param rule
	 *            可取值： <li>AlphaComposite.SRC</li> <li>AlphaComposite.SRC_IN <li>
	 *            AlphaComposite.SRC_OUT</li> <li>AlphaComposite.SRC_OVER</li>等
	 * @param alpha
	 *            透明度，取值范围0-1，值越小越透明
	 */
	public void setTransparency(int rule, float alpha) {
		AlphaComposite ac = AlphaComposite.getInstance(rule, alpha);
		g.setComposite(ac);
	}

	public boolean isAutoResizeByText() {
		return autoResizeByText;
	}

	public static void main(String[] args) {
		String svgElement = new CopyOfSVGGraph(new Model()).createNetGraph();
		try {
			File out = new File("f:\\test\\" + new Date().getTime() + ".svg");
			SVGUtils.writeToSVG(out, svgElement);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
