package com.guhanjie.image;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.SVGHints;
import org.jfree.graphics2d.svg.SVGUtils;

import com.guhanjie.image.model.App;
import com.guhanjie.image.model.Model;

/**
 * 采用JFreeSVG技术生成网状和树状结构的SVG图
 * @author guhanjie
 * 2014-05-28 17:28:15
 */
public class SVGGraph {

	private Random random;
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
	private Font masterFont;
	private Font itemFont;
	private Font moduleFont;
	private Model model;
	private Stroke masterLineStroke;
	private Stroke bracketStroke;
	private boolean autoResizeByText = true;
	private Color masterTextColor;
	private Color moduleTextColor;
	private Color bracketLineColor;
	private Color itemIconColor;
	private Color itemTextColor;
	private int paddingPixel = 10;
	private int itemSize = 5; // 子项编号图标的大小

	public SVGGraph(Model model) {
		random = new Random();
		this.model = model;
		masterFont = new Font(Font.SANS_SERIF, Font.BOLD, 24);
		moduleFont = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
		itemFont = new Font("Courier", Font.PLAIN, 12);
		masterLineStroke = new BasicStroke(3.0f, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_MITER);
		bracketStroke = new BasicStroke(2.0f, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_MITER);
		masterTextColor = Color.WHITE;
		moduleTextColor = Color.WHITE;
		bracketLineColor = Color.GRAY;
		itemIconColor = Color.GRAY;
		itemTextColor = Color.BLUE;
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
		// 获取各模块位置
		HashMap<Object, Point> map = calcRandomPoints(
				model.getAppList().size(), masterLineLength);
		for (App app : model.getAppList()) {
			// 画主体和模块之间的连线
			Point point = map.get(app);
			g.setColor(getRandomColor());
			g.setStroke(masterLineStroke);
			g.drawLine(masterPoint.x, masterPoint.y, point.x, point.y);
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
			// 画当前模块对应的所有子项
			List<String> items = app.getAuthenrizeItemList();
			if (items.size() > 0) {
				// 计算item列表的高度，每个item算一行
				int blockHeight = items.size() * (itemFont.getSize()+itemSize) - itemSize;
				// 画包围所有子项的括弧
				int[] xPoints = {
						point.x + moduleWidth / 2 + bracketBash + bracketSize,
						point.x + moduleWidth / 2 + bracketBash,
						point.x + moduleWidth / 2 + bracketBash,
						point.x + moduleWidth / 2 + bracketBash + bracketSize };
				int[] yPoints = { point.y - blockHeight / 2 - bracketSize,
						point.y - blockHeight / 2, point.y + blockHeight / 2,
						point.y + blockHeight / 2 + bracketSize };
				g.setStroke(bracketStroke);
				g.setPaint(bracketLineColor);
				g.drawPolyline(xPoints, yPoints, xPoints.length);
				g.drawLine(point.x + moduleWidth / 2, point.y, point.x
						+ moduleWidth / 2 + bracketBash, point.y);
				// 画所有子项组成的列表
				int itemPosX = point.x + moduleWidth / 2 + bracketBash
						+ bracketSize;
				int itemPosY = point.y - blockHeight / 2;
				for (String item : items) {
					// 画子项编号图标（小圆点）
					g.setColor(itemIconColor);
					g.fillOval(itemPosX, itemPosY, itemSize, itemSize);
					// 画子项文本信息
					g.setFont(itemFont);
					g.setPaint(itemTextColor);
					g.drawString(item, itemPosX + bracketSize * 2, itemPosY
							+ bracketSize + 1);
					itemPosY += itemFont.getSize() + itemSize;
				}
			}
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
	public HashMap<Object, Point> calcRandomPoints(int appNums, int lineLength) {
		int maxModuleWidth = 0;
		HashMap<Object, Point> map = new HashMap<Object, Point>();
		List<App> appList = model.getAppList();
		double[] angles = getRandomAngles(appNums);
		for (int i = 0; i < appNums; i++) {
			App app = appList.get(i);
			int textLength = getLength(app.getAppName(), moduleFont) + paddingPixel*2;
			maxModuleWidth = (maxModuleWidth < textLength) ? textLength
					: maxModuleWidth;
			double dx, dy;
			double radian = angles[i] / 180.0 * Math.PI;
			dx = lineLength * Math.sin(radian);
			dy = lineLength * Math.cos(radian);
			int pointX = (int) Math.round(masterPoint.x + dx);
			int pointY = (int) Math.round(masterPoint.y + dy);
			map.put(app, new Point(pointX, pointY));
		}
		if (this.isAutoResizeByText()) {
			double ratio = (double) this.moduleHeight / (double) this.moduleWidth;
			this.moduleWidth = (maxModuleWidth<moduleWidth) ? moduleWidth : maxModuleWidth;
			this.moduleHeight = (int) (this.moduleWidth * ratio);
		}
		return map;
	}

	/**
	 * 获取均分角度序列
	 * 
	 * @param n
	 * @return
	 */
	private double[] getRandomAngles(int n) {
		double avg = 360.0 / n;
		double init = random.nextInt(360);
		double[] angles = new double[n];
		for (int i = 0; i < n; i++) {
			angles[i] = (init + i * avg) % 360;
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

	public int getImgWidth() {
		return imgWidth;
	}

	public void setImgWidth(int imgWidth) {
		this.imgWidth = imgWidth;
	}

	public int getImgHeight() {
		return imgHeight;
	}

	public void setImgHeight(int imgHeight) {
		this.imgHeight = imgHeight;
	}

	public int getMasterWidth() {
		return masterWidth;
	}

	public void setMasterWidth(int masterWidth) {
		this.masterWidth = masterWidth;
	}

	public int getMasterHeight() {
		return masterHeight;
	}

	public void setMasterHeight(int masterHeight) {
		this.masterHeight = masterHeight;
	}

	public int getModuleWidth() {
		return moduleWidth;
	}

	public void setModuleWidth(int moduleWidth) {
		this.moduleWidth = moduleWidth;
	}

	public int getModuleHeight() {
		return moduleHeight;
	}

	public void setModuleHeight(int moduleHeight) {
		this.moduleHeight = moduleHeight;
	}

	public int getItemWidth() {
		return itemWidth;
	}

	public void setItemWidth(int itemWidth) {
		this.itemWidth = itemWidth;
	}

	public int getItemHeight() {
		return itemHeight;
	}

	public void setItemHeight(int itemHeight) {
		this.itemHeight = itemHeight;
	}

	public Point getMasterPoint() {
		return masterPoint;
	}

	public void setMasterPoint(Point masterPoint) {
		this.masterPoint = masterPoint;
	}

	public int getMasterLineLength() {
		return masterLineLength;
	}

	public void setMasterLineLength(int masterLineLength) {
		this.masterLineLength = masterLineLength;
	}

	public int getBracketBash() {
		return bracketBash;
	}

	public void setBracketBash(int bracketBash) {
		this.bracketBash = bracketBash;
	}

	public int getBracketSize() {
		return bracketSize;
	}

	public void setBracketSize(int bracketSize) {
		this.bracketSize = bracketSize;
	}

	public Font getMasterFont() {
		return masterFont;
	}

	public void setMasterFont(Font masterFont) {
		this.masterFont = masterFont;
	}

	public Font getItemFont() {
		return itemFont;
	}

	public void setItemFont(Font itemFont) {
		this.itemFont = itemFont;
	}

	public Font getModuleFont() {
		return moduleFont;
	}

	public void setModuleFont(Font moduleFont) {
		this.moduleFont = moduleFont;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public Stroke getMasterLineStroke() {
		return masterLineStroke;
	}

	public void setMasterLineStroke(Stroke masterLineStroke) {
		this.masterLineStroke = masterLineStroke;
	}

	public boolean isAutoResizeByText() {
		return autoResizeByText;
	}

	public void setAutoResizeByText(boolean autoTextResize) {
		this.autoResizeByText = autoTextResize;
	}

	public Color getMasterTextColor() {
		return masterTextColor;
	}

	public void setMasterTextColor(Color masterTextColor) {
		this.masterTextColor = masterTextColor;
	}

	public Color getModuleTextColor() {
		return moduleTextColor;
	}

	public void setModuleTextColor(Color moduleTextColor) {
		this.moduleTextColor = moduleTextColor;
	}

	public Stroke getBracketStroke() {
		return bracketStroke;
	}

	public void setBracketStroke(Stroke bracketStroke) {
		this.bracketStroke = bracketStroke;
	}

	public Color getBracketLineColor() {
		return bracketLineColor;
	}

	public void setBracketLineColor(Color bracketLineColor) {
		this.bracketLineColor = bracketLineColor;
	}

	public Color getItemIconColor() {
		return itemIconColor;
	}

	public void setItemIconColor(Color itemIconColor) {
		this.itemIconColor = itemIconColor;
	}

	public Color getItemTextColor() {
		return itemTextColor;
	}

	public void setItemTextColor(Color itemTextColor) {
		this.itemTextColor = itemTextColor;
	}

	public int getPaddingPixel() {
		return paddingPixel;
	}

	public void setPaddingPixel(int paddingPixel) {
		this.paddingPixel = paddingPixel;
	}

	public static void main(String[] args) {
		String svgElement = new SVGGraph(new Model()).createNetGraph();
		try {
			File out = new File("f:\\test\\" + new Date().getTime() + ".svg");
			SVGUtils.writeToSVG(out, svgElement);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
