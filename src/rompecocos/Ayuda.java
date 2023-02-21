
/*
 * Programación Interactiva 
 * Autor: Carolain Jimenez Bedoya - 2071368 
 * Caso 3: Juego Rompecocos. 
 */


package rompecocos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import misComponentes.Titulos;

public class Ayuda extends JFrame {

	private BufferedImage originalImage;
	private JLabel image; 
	private ImageIcon adaptImage;
	private JButton volver; 
	private Escuchas escucha; 
	private JFrame rompecocos;

	public Ayuda(JFrame rompecocos) {
		try {
			originalImage = ImageIO.read(new File (Rompecocos.rutaFile));
			this.rompecocos=rompecocos;

			initGUI();
			//Ventana

			this.setUndecorated(true);
			pack();
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setVisible(false);


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initGUI() {
		// TODO Auto-generated method stub
		//contenedor y layout

		//escucha 
		escucha= new Escuchas();

		//crearGUI

		Titulos titulo= new Titulos ("Imagen Original",27,Color.BLUE);
		add(titulo,BorderLayout.NORTH);

		BufferedImage subImagen=originalImage.getSubimage(0, 0, 400, 400);
		adaptImage= new ImageIcon (subImagen);
		image= new JLabel(adaptImage);
		add(image, BorderLayout.CENTER);

		volver= new JButton("Volver");
		volver.addActionListener(escucha);
		add(volver, BorderLayout.SOUTH);
	}

	private class Escuchas implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			//evento volver 
			//Activar la ventana rompecocos y poner invisible esta ventana 
			rompecocos.setEnabled(true);
			setVisible(false);
		}

	}

}
