import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import javax.swing.*;

public class Lienzo extends JComponent{
	int ancho, alto, radio;
	double x, y, angulo =0;
	Graphics2D gBuffer = null;
	Image imag;
	
	Lienzo(int width, int height){
		setBackground(Color.white);
		setPreferredSize(new Dimension(width, height));
		ancho = width;
		alto = height;
	}
	
	public void update(Graphics g){
		paint(g);
	}
	
	public void paint(Graphics g){
		if(gBuffer == null){
			imag = createImage(ancho,alto);
			gBuffer = (Graphics2D)imag.getGraphics();
			gBuffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		}		
		gBuffer.setColor(Color.white);
		gBuffer.fillRect(0,0,ancho,alto);
		gBuffer.setColor(Color.black);
		gBuffer.drawRect(0,0,ancho-1,alto-1);
		for(int i=0;i<=360;i+=6){
			x = (ancho/2) + (((ancho/2)-4) *Math.cos(Math.toRadians(i)));
			y =  (alto/2) + (( (alto/2)-4) *Math.sin(Math.toRadians(i)));
			if(i % 30 == 0){
				gBuffer.setColor(Color.black);
				gBuffer.fill(new Ellipse2D.Double(x-2,y-2,4,4));
			}
			else{
				gBuffer.setColor(new Color(49,123,176,150));
				gBuffer.fill(new Ellipse2D.Double(x-1,y-1,2,2));
			}
		}
		gBuffer.setColor(Color.black);
		gBuffer.draw(new Line2D.Double(ancho/2,alto/2,
										(ancho/2)+ radio*Math.cos(Math.toRadians(angulo-90)),
										(alto/2)+ radio*Math.sin(Math.toRadians(angulo-90))));
		gBuffer.fill(new Ellipse2D.Double(-4+(ancho/2)+ (radio-10)*(Math.cos(Math.toRadians(angulo-90))),
										  -4+(ancho/2)+ (radio-10)*(Math.sin(Math.toRadians(angulo-90))),
										  8,8));
		gBuffer.setColor(Color.red);
		gBuffer.fill(new Ellipse2D.Double((ancho/2)-1,(alto/2)-1,3,3));										  
		g.drawImage(imag,0,0,this);										
	}
}