package zoom;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Zoom {
	public static void main(String[] args) {
		int len = 17280;
		int width = 8640;
		int height = 7482;
		int limit = 10;
		int inc = 576;
		double yScale = 4.618; //4.618 -> 9.23
		
		for(int i = 0; i <= 30; i++) {
			int xOffset = 17280 / 4 + i * (inc / 4);
			int yOffset = (int)(-17280 / yScale - i * (inc / yScale));
			
			BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = bi.createGraphics();
			g2d.setColor(new Color(255, 255, 255));
			g2d.fillRect(0,  0,  width, height);
			g2d.setColor(new Color(0, 0, 0));
			sierpinski(width / 2 + xOffset,
					(height - Math.sqrt(Math.pow(len, 2) - Math.pow(.5 * len, 2))) / 2 + yOffset,
					(width - len) / 2 + xOffset,
					height - (height - Math.sqrt(Math.pow(len, 2) - Math.pow(.5 * len, 2))) / 2 + yOffset,
					width - (width - len) / 2 + xOffset,
					height - (height - Math.sqrt(Math.pow(len, 2) - Math.pow(.5 * len, 2))) / 2 + yOffset,
					limit, g2d);
			
//			g2d.drawLine(width / 2, 0, width / 2, height);
//			g2d.drawLine(0, height / 2, width, height / 2);
			
			try {
				ImageIO.write(bi, "png", new File("frames/frame" + i + ".png"));
			}	catch(IOException e) {
				e.printStackTrace();
				
				}
			len += inc;
			yScale *= 1.02337389;
			
		}
		
	}
	
	public static void sierpinski(double p0x, double p0y, double p1x, double p1y, double p2x, double p2y, int limit, Graphics2D g2d) {
		double pax = (p0x + p1x) / 2;
		double pay = ((p0y + p1y) / 2);
		double pbx = (p1x + p2x) / 2;
		double pby = ((p1y + p2y)) / 2;
		double pcx = (p2x + p0x) / 2;
		double pcy = ((p2y + p0y)) / 2;
		
		g2d.setColor(Color.BLACK);
		//System.out.println(limit + "pax: " + pax + " pay: " + pay + " pbx: " + pbx + " pby: " + pby + " pcx: " + pcx + " pcy: " + pcy);
		
		if(limit == 1) {
			Path2D path1 = new Path2D.Double();
			path1.moveTo(p0x, p0y);
			path1.lineTo(pax, pay);
			path1.lineTo(pcx, pcy);
			path1.lineTo(p0x, p0y);
			path1.closePath();
			g2d.fill(path1);
			
			Path2D path2 = new Path2D.Double();
			path2.moveTo(pax, pay);
			path2.lineTo(p1x, p1y);
			path2.lineTo(pbx, pby);
			path2.lineTo(pax, pay);
			path2.closePath();
			g2d.fill(path2);
			
			Path2D path3 = new Path2D.Double();
			path3.moveTo(pcx, pcy);
			path3.lineTo(pbx, pby);
			path3.lineTo(p2x, p2y);
			path3.lineTo(pcx, pcy);
			path3.closePath();
			g2d.fill(path3);
			
		}
		
		if(!(limit == 1)) {
			sierpinski(p0x, p0y, pax, pay, pcx, pcy, limit - 1, g2d);
			sierpinski(pax, pay, p1x, p1y, pbx, pby, limit - 1, g2d);
			sierpinski(pcx, pcy, pbx, pby, p2x, p2y, limit - 1 , g2d);
			
			
		}
		
	}
	
}
