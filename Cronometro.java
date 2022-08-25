import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer.*;
import java.io.*;

public class Cronometro extends JFrame implements ActionListener{
	int hora, min, seg;
	int ladoLienzo;
	JButton bStart, bStop;
	JLabel h0, hF, m0, mF, s0, sF;
	JTextField cH0, cHF, cM0, cMF, cS0, cSF;
	BorderLayout layout;
	Timer cronometro;
	Lienzo lH, lM, lS;
	Container contenedor;
	JPanel panelCenter, panelSouth;
	String stringH, stringM, stringS;
	long tiempo;
	boolean estaDetenido = false;
		
	//Listeners de los botones y el menú
	ManejaBotones accionBoton;
	ManejaMenu accionMenu;
	
	//stream para guardar datos
	ObjectOutputStream out;
	ObjectInputStream in;
	
	JMenuItem guardar, cargar, terminar;
	JMenu menu;
	JMenuBar menuBar;

	Cronometro(){
		//Creación del menú
		accionMenu = new ManejaMenu();
		guardar = new JMenuItem("Guardar");
		guardar.addActionListener(accionMenu);
		cargar = new JMenuItem("Cargar");
		cargar.addActionListener(accionMenu);
		terminar = new JMenuItem("Terminar");
		terminar.addActionListener(accionMenu);
		menu = new JMenu("Menú");
		menu.add(guardar);
		menu.add(cargar);
		menu.addSeparator();
		menu.add(terminar);		
		menuBar = new JMenuBar();
		menuBar.add(menu);
		setJMenuBar(menuBar);
		//Fin de la creación del menú.	
	
		contenedor = getContentPane();
		hora =0; min =0; seg =0;
		layout = new BorderLayout();
		contenedor.setLayout(layout);		
		ladoLienzo = 250;
		lH = new Lienzo(ladoLienzo,ladoLienzo); lH.setVisible(true);
		lM = new Lienzo(ladoLienzo,ladoLienzo); lM.setVisible(true);
		lS = new Lienzo(ladoLienzo,ladoLienzo); lS.setVisible(true);
		lH.radio = (ladoLienzo/2)-45;
		lM.radio = (ladoLienzo/2)-30;
		lS.radio = (ladoLienzo/2)-10;		
		h0 = new JLabel("H");	cH0 = new JTextField(2);
		hF = new JLabel("H"); 	cHF = new JTextField(2);	cHF.setEditable(false); cHF.setBackground(Color.white);
		m0 = new JLabel("M");	cM0 = new JTextField(2);
		mF = new JLabel("M");	cMF = new JTextField(2);	cMF.setEditable(false); cMF.setBackground(Color.white);
		s0 = new JLabel("S"); 	cS0 = new JTextField(2);
		sF = new JLabel("S");	cSF = new JTextField(2);	cSF.setEditable(false); cSF.setBackground(Color.white);
		rotular();
		cH0.setText(stringH+hora);
		cM0.setText(stringM+min);
		cS0.setText(stringS+seg);
		cHF.setText(stringH+hora);
		cMF.setText(stringM+min);
		cSF.setText(stringS+seg);
		
		accionBoton = new ManejaBotones();
		bStart = new JButton("Start");
		bStart.addActionListener(accionBoton);
		bStop = new JButton("Stop");
		bStop.addActionListener(accionBoton);
		
		panelCenter = new JPanel();
		panelCenter.setSize(3*ladoLienzo+10,ladoLienzo+10);
		panelCenter.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
		panelCenter.add(lH);
		panelCenter.add(lM);
		panelCenter.add(lS);
		contenedor.add(panelCenter, BorderLayout.CENTER);
		panelSouth = new JPanel();
		panelSouth.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
		panelSouth.add(h0);	panelSouth.add(cH0);
		panelSouth.add(m0);	panelSouth.add(cM0);
		panelSouth.add(s0);	panelSouth.add(cS0);
		panelSouth.add(bStart);	panelSouth.add(bStop);
		panelSouth.add(hF);	panelSouth.add(cHF);
		panelSouth.add(mF);	panelSouth.add(cMF);
		panelSouth.add(sF);	panelSouth.add(cSF);
		contenedor.add(panelSouth, BorderLayout.SOUTH);
		contenedor.validate();
		cronometro = new Timer(0,this);
	}
	
	public void rotular(){
		if(hora >= 10){
			stringH = "";
		}
		else{
			stringH = "0";
		}
		
		if(min >= 10){
			stringM = "";
		}
		else{
			stringM = "0";
		}

		if(seg >= 10){
			stringS = "";
		}
		else{
			stringS = "0";
		}
	}
	
	public void actionPerformed(ActionEvent e){
		tiempo = System.currentTimeMillis();
		lS.angulo += 6;
		lM.angulo = 6*min + lS.angulo/60;
		lH.angulo = 30*hora + lM.angulo/12;
		seg++;		
		if(seg >= 60){
			lS.angulo=0;
			seg =0;
			min++;
		}
		if(min >= 60){
			lM.angulo =0;
			min = 0;
			hora++;
		}
		if(hora >= 25){
			lH.angulo=30;
			hora =0;
			hora++;
		}
		lH.repaint();
		lM.repaint();
		lS.repaint();
		rotular();
		cHF.setText(stringH+hora);
		cMF.setText(stringM+min);
		cSF.setText(stringS+seg);
		cronometro.setDelay(1000 - (int)(System.currentTimeMillis()- tiempo));
	}
	
	//Clase auxiliar ManejaBotones
	public class ManejaBotones implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == bStart){					
				hora = Integer.parseInt(cH0.getText());
				min = Integer.parseInt(cM0.getText());
				seg = Integer.parseInt(cS0.getText());
				lS.angulo = 6*seg;
				lM.angulo = 6*min;
				lH.angulo = 30*hora;
				estaDetenido = false;
				cronometro.start();
			}
			else{
				cronometro.stop();
				estaDetenido = true;
				rotular();
				cH0.setText(stringH+hora);
				cM0.setText(stringM+min);
				cS0.setText(stringS+seg);
			}
		}
	}//Fin de la clase auxiliar ManejaBotones
	
	//Clase auxiliar ManejaMenu
	public class ManejaMenu implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == guardar){
				try{
				    out = new ObjectOutputStream(new FileOutputStream("tiempo.dat"));
			        out.writeObject(cH0.getText());
			        out.writeObject(cM0.getText());
			        out.writeObject(cS0.getText());
			        out.writeObject(cHF.getText());
			        out.writeObject(cMF.getText());
			        out.writeObject(cSF.getText());
			        out.writeObject(""+estaDetenido);
		            out.close();
				}
				catch (IOException ioe){
					System.out.println(ioe);
				}								
			}
			else{
				if(e.getSource() == cargar){
					try{
						cronometro.stop();
					    in = new ObjectInputStream(new FileInputStream("tiempo.dat"));
				        cH0.setText((String)in.readObject());
				        cM0.setText((String)in.readObject());
				        cS0.setText((String)in.readObject());
				        cHF.setText((String)in.readObject());
				        cMF.setText((String)in.readObject());
				        cSF.setText((String)in.readObject());				        
				        String cadena = (String)in.readObject();				        
				        in.close();
				        hora=Integer.parseInt(cHF.getText());
				        min=Integer.parseInt(cMF.getText());
				        seg=Integer.parseInt(cSF.getText());
						lS.angulo = 6*seg;
						lM.angulo = 6*min + lS.angulo/60;
						lH.angulo = 30*hora + lM.angulo/12;				        
				        if(cadena.equals("false")){
				        	estaDetenido=false;				        
					        cronometro.start();
				        }
				        else{
				        	estaDetenido= true;
				        	lH.repaint();
				        	lM.repaint();
				        	lS.repaint();
				        }
					}
					catch (IOException ioe){
						System.out.println(ioe);
					}
					catch (ClassNotFoundException cnfe){
						System.out.println(cnfe);
					}
				}
				else{
					cronometro.stop();
					System.out.println("Ultimo tiempo marcado "+hora+":"+min+":"+seg);
					System.exit(0);
				}
			}
		}
	}//fin de la clase auxiliar ManejaMenu
	
	public static void main(String[] args){
		Cronometro miCronometro = new Cronometro();
		miCronometro.pack();
		miCronometro.setLocationRelativeTo(null);
		miCronometro.setTitle(" .:| EL CRONÓMETRO |:. ");
		miCronometro.show();
		miCronometro.setResizable(false);
		miCronometro.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}