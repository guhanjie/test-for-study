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
 * ����JFreeSVG����������״����״�ṹ��SVGͼ
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
	private int itemSize = 5; // ������ͼ��Ĵ�С
	private Color halvingLineColor = Color.DARK_GRAY;
	private Stroke halvingLineStroke = new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 5.0f, new float[]{3.0f}, 0.0f);
	private Font halvingFont = new Font("Courier", Font.BOLD, 24);

	public CopyOfSVGGraph(Model model) {
		this.model = model;
	}

	/**
	 * ����һ����״�ṹ��SVGͼ
	 * @return
	 */
	public String createTreeGraph() {
		// ����ͼ��������
		g = new SVGGraphics2D(imgWidth, imgHeight);
		optimizeQuality();
		
		
		
		g.dispose();// �ͷ�ϵͳ��Դ

		return g.getSVGElement();
	}
	
	/**
	 * ����һ����״�ṹ��SVGͼ
	 * @return
	 */
	public String createNetGraph() {
		// ����ͼ��������
		g = new SVGGraphics2D(imgWidth, imgHeight);
		optimizeQuality();
		masterPoint = new Point(imgWidth / 2, imgHeight / 2);
		
		//���ָ���
		g.setColor(halvingLineColor);
		g.setStroke(halvingLineStroke);
		g.drawLine(0, imgHeight/2, imgWidth, imgHeight/2);
		//д�����ı���Ϣ
		g.setFont(halvingFont);
		g.drawString("Ӧ��", 50, imgHeight/4);
		g.drawString("�汾", 50, imgHeight*3/4);
		
		// ��ȡ��ģ��λ��
		HashMap<Object, Point> map = calcPosition(model.getAppList(), masterLineLength, 0);
		for (App app : model.getAppList()) {
			// �������ģ��֮�������
			Point point = map.get(app);
			g.setColor(getRandomColor());
			g.setStroke(masterLineStroke);
			g.drawLine(masterPoint.x, masterPoint.y, point.x, point.y);
			// ����ǰģ���Ӧ����������
			List<String> items = app.getAuthenrizeItemList();
			if (items.size() > 0) {
				// ����item�б�ĸ߶ȣ�ÿ��item��һ��
				int blockHeight = items.size() * (itemFont.getSize()+itemSize) - itemSize;
				// ����Χ�������������
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
				// ������������ɵ��б�
				int itemPosX = bashEndPoint.x+ bracketSize;
				int itemPosY = bashEndPoint.y - blockHeight / 2 + 2;
				for (String item : items) {
					// ��������ͼ�꣨СԲ�㣩
					g.setColor(itemIconColor);
					g.fillOval(itemPosX, itemPosY, itemSize, itemSize);
					// �������ı���Ϣ
					g.setFont(itemFont);
					g.setPaint(itemTextColor);
					g.drawString(item, itemPosX + itemSize * 2, itemPosY + itemSize + 1);
					itemPosY += itemFont.getSize() + itemSize;
				}
			}
			// ��ģ�飨��Բ��
			Color color = getRandomColor();
			g.setColor(color);
			g.fillOval(point.x - moduleWidth / 2, point.y - moduleHeight / 2,
					moduleWidth, moduleHeight);
			g.setPaint(color.darker()); // �߿���ɫ�������͹�Ե��Ӿ�����
			g.drawOval(point.x - moduleWidth / 2, point.y - moduleHeight / 2,
					moduleWidth, moduleHeight);
			// ��ģ����ı���Ϣ
			g.setFont(moduleFont);
			g.setPaint(moduleTextColor);
			g.drawString(app.getAppName(),
					point.x - getLength(app.getAppName(), moduleFont) / 2,
					point.y + moduleFont.getSize() / 4);
		}
		
		
		// ��ȡ��ģ��λ��
		HashMap<Object, Point> versionMap = calcPosition(model.getVersionList(), masterLineLength, 180);
		for (Version version : model.getVersionList()) {
			// �������ģ��֮�������
			Point point = versionMap.get(version);
			g.setColor(getRandomColor());
			g.setStroke(masterLineStroke);
			g.drawLine(masterPoint.x, masterPoint.y, point.x, point.y);
			// ����ǰģ���Ӧ����������
			List<App> apps = version.getAppList();
			List<String> items = new ArrayList<String>();
			for(App app: apps) {
				for(String str : app.getAuthenrizeItemList()) {
					items.add(app.getAppName()+": "+str);
				}
			}
			if (items.size() > 0) {
				// ����item�б�ĸ߶ȣ�ÿ��item��һ��
				int blockHeight = items.size() * (itemFont.getSize()+itemSize) - itemSize;
				// ����Χ�������������
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
				// ������������ɵ��б�
				int itemPosX = bashEndPoint.x+ bracketSize;
				int itemPosY = bashEndPoint.y - blockHeight / 2 + 2;
				for (String item : items) {
					// ��������ͼ�꣨СԲ�㣩
					g.setColor(getRandomColor());
					g.fillRect(itemPosX, itemPosY, itemSize, itemSize);
					// �������ı���Ϣ
					g.setFont(itemFont);
					g.setPaint(itemTextColor);
					g.drawString(item, itemPosX + itemSize * 2, itemPosY + itemSize + 1);
					itemPosY += itemFont.getSize() + itemSize;
				}
			}
			// ��ģ�飨��Բ��
			Color color = getRandomColor();
			g.setColor(color);
			g.fillOval(point.x - moduleWidth / 2, point.y - moduleHeight / 2,
					moduleWidth, moduleHeight);
			g.setPaint(color.darker()); // �߿���ɫ�������͹�Ե��Ӿ�����
			g.drawOval(point.x - moduleWidth / 2, point.y - moduleHeight / 2,
					moduleWidth, moduleHeight);
			// ��ģ����ı���Ϣ
			g.setFont(moduleFont);
			g.setPaint(moduleTextColor);
			g.drawString(version.getVersionName(),
					point.x - getLength(version.getVersionName(), moduleFont) / 2,
					point.y + moduleFont.getSize() / 4);
		}
		
		
		
		
		
		// �����壨Բ�Ǿ��Σ�
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
		g.setPaint(color.darker());// �߿���ɫ�������͹�Ե��Ӿ�����
		g.drawRoundRect(masterPoint.x - masterWidth / 2, masterPoint.y
				- masterHeight / 2, masterWidth, masterHeight, 20, 20);
		// ��������ı���Ϣ
		g.setFont(masterFont);
		g.setPaint(masterTextColor);
		g.drawString(model.getIsvName(),
				masterPoint.x - getLength(model.getIsvName(), masterFont) / 2,
				masterPoint.y + masterFont.getSize() / 4);

		g.dispose();// �ͷ�ϵͳ��Դ

		return g.getSVGElement();
	}

	public Color getRandomColor() {
		return new Color(random.nextInt(128) + 90, random.nextInt(128) + 90,
				random.nextInt(128) + 128);
	}

	/**
	 * ���������ĸ���ģ������λ�ã���Щ��������������ΪԲ��lineLengthΪ�뾶��Բ�ϵľ��ȷֲ��㣩
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
	 *  ����������λ��
	 * @param angle �����ˮƽ����ļ�����Ƕ�
	 * @param lineLength �뾶����
	 * @param relativePoint ��Ե���ʼ��
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
	 * ��ȡ���ֽǶ�����
	 * 
	 * @param n
	 * @param rotate ��ת���ٶ�
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
	 * ��ȡ�ַ����ȣ�һ��������Ϊ 1 ���ַ�, һ��Ӣ����ĸ��Ϊ 0.5 ���ַ�
	 * 
	 * @param text
	 * @return �ַ����ȣ��磺text="�й�",���� 2��text="test",���� 2��text="�й�ABC",���� 4.
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
	 * �Ż���ͼ���������ܻ�������������
	 */
	public void optimizeQuality() {
		// �����
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		// ���ı����
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		// SVG������ı�������
		g.setRenderingHint(SVGHints.KEY_TEXT_RENDERING,
				SVGHints.VALUE_TEXT_RENDERING_LEGIBILITY);
		g.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
	}

	/**
	 * ���û�ͼʱ���߶�������ʽ
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
	 * ����͸��
	 * 
	 * @param rule
	 *            ��ȡֵ�� <li>AlphaComposite.SRC</li> <li>AlphaComposite.SRC_IN <li>
	 *            AlphaComposite.SRC_OUT</li> <li>AlphaComposite.SRC_OVER</li>��
	 * @param alpha
	 *            ͸���ȣ�ȡֵ��Χ0-1��ֵԽСԽ͸��
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
