/*
 * Programación Interactiva 
 * Autor: Carolain Jimenez Bedoya - 2071368 
 * Caso 3: Juego Rompecocos. 
 */

package rompecocos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import misComponentes.Titulos;

public class Rompecocos extends JFrame {

	public static final String rutaFile=("src/imagenes/rompecocos.jpg");


	private int fichaSize=100;
	private int gridSize=4;
	private BufferedImage bufferImage= null;
	private Ficha [][] tablero = new Ficha[gridSize][gridSize];
	private JPanel centralPanel, panelBotones;
	private JButton ayuda, revolver,salir;
	private Escuchas escucha;
	private JFrame miMisma= this;
	private Ayuda ventanAyuda= new Ayuda(miMisma);



	public Rompecocos() {
		try {
			bufferImage= ImageIO.read(new File(rutaFile)); 
			Ficha.setFichazSizeMaxFichas(fichaSize, gridSize*gridSize);
			initGUI();

			//configuración por defecto de la ventana 

			this.setUndecorated(true);
			pack();
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setVisible(true);


		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "No se ha encontrado el archivo rompecocos.jpg");
		}



	}

	private void initGUI() {
		// TODO Auto-generated method stub
		//Define el contenedor y layaout

		//crear el escucha
		escucha = new Escuchas();

		//crear la GUI 
		//titulo
		Titulos titulo =new Titulos("Rompecocos",30,Color.BLACK);
		add(titulo,BorderLayout.NORTH);

		//zona de juego-centralPanel

		dividirImage();

		//panelBotones
		panelBotones= new JPanel();
		ayuda = new JButton ("Ayuda");
		ayuda.addActionListener(escucha);
		panelBotones.add(ayuda);

		revolver = new JButton ("Revolver");
		revolver.addActionListener(escucha);
		panelBotones.add(revolver);

		salir = new JButton ("Salir");
		salir.addActionListener(escucha);
		panelBotones.add(salir);

		add(panelBotones,BorderLayout.SOUTH);



	} 

	private void dividirImage() {
		// TODO Auto-generated method stub
		centralPanel= new JPanel();
		centralPanel.setLayout(new GridLayout(gridSize, gridSize));
		add(centralPanel,BorderLayout.CENTER);

		int id=0;

		for (int row=0; row<gridSize; row++ ) {
			for (int col=0; col<gridSize; col++) {
				//calcular la porcion de imagen y idmage para crear la ficha 
				//bufferImage.getSubimage(col, id, row, col)

				int x= col*fichaSize;
				int y= row*fichaSize;
				BufferedImage subImage=bufferImage.getSubimage(x, y, fichaSize, fichaSize);

				ImageIcon buttonImage= new ImageIcon(subImage);

				tablero[row][col]= new Ficha (buttonImage,id,row,col);
				tablero[row][col].addMouseListener(escucha);
				centralPanel.add(tablero[row][col]);
				id++;

			}
		}
		revolverFichas();
	}

	private void clickedFicha(Ficha fichaClick) {
		int row= fichaClick.getRow();
		int col= fichaClick.getCol();

		if (row>0 && tablero[row-1][col].hasNotImage()) {
			fichaClick.intercambiar(tablero[row-1][col]);
		}
		else {
			if (row<gridSize-1 && tablero[row+1][col].hasNotImage()) {
				fichaClick.intercambiar(tablero[row+1][col]);
			}
			else {
				if(col>0 && tablero[row][col-1].hasNotImage()) {
					fichaClick.intercambiar(tablero[row][col-1]);

				}
				else {
					if (col<gridSize-1 && tablero[row][col+1].hasNotImage()) {
						fichaClick.intercambiar(tablero[row][col+1]);
					}
				}
			}
		}
		if(validarOrden()) {
			tablero[gridSize-1][gridSize-1].mostrarImage();
		}
	}

	private void revolverFichas() {
		int initRow= gridSize-1;
		int initCol= gridSize-1;

		Random random = new Random();


		for (int i=0; i<12*gridSize*gridSize; i++) {
			int direction= random.nextInt(4);

			switch (direction) {
			case 0:  
				if (initRow>0) {
					tablero[initRow][initCol].intercambiar(tablero[initRow-1][initCol]);
					initRow--;
				}
				break; //arriba

			case 1:
				if (initRow<gridSize-1) {
					tablero[initRow][initCol].intercambiar(tablero[initRow+1][initCol]);
					initRow++;
				}

				break; //abajo
			case 2: 
				if (initCol>0) {
					tablero[initRow][initCol].intercambiar(tablero[initRow][initCol-1]);
					initCol--;
				}

				break; //izquierda
			case 3: 

				if (initCol<gridSize-1) {
					tablero[initRow][initCol].intercambiar(tablero[initRow][initCol+1]);
					initCol++;
				}
				break; //Derecha
			}
		}



	}

	private boolean validarOrden() {
		int id=0;
		boolean orden=true;

		for (int row=0; row<gridSize && orden; row++) {
			for (int col=0; col<gridSize && orden; col++) {
				if(tablero[row][col].getIdImage()== id) {
					id++;
				}
				else {
					orden= false;

				}
			}
		}
		return orden;
	}

	private class Escuchas extends MouseAdapter implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent eventAction) {
			// TODO Auto-generated method stub
			//botones, ayuda, salir, revolver

			if(eventAction.getSource()== salir) {
				System.exit(0);
			}else {

				if (eventAction.getSource()==ayuda) {
					//Llamar a la ventana ayuda

					ventanAyuda.setVisible(true);
					miMisma.setEnabled(false);

				}
				else {
					//Llamar a la funcion revolver Ficha
					revolverFichas();
				}

			}	

		}

		@Override
		public void mouseClicked(MouseEvent eventMouse) { //Algo es clickeado 
			// TODO Auto-generated method stub
			//Intercambiar fichas
			Ficha fichaClick= (Ficha) eventMouse.getSource();
			clickedFicha(fichaClick);

		}


	}
}
