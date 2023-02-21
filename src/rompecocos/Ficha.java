
/*
 * Programación Interactiva 
 * Autor: Carolain Jimenez Bedoya - 2071368 
 * Caso 3: Juego Rompecocos. 
 */

package rompecocos;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Ficha extends JButton {
	//Cualquier modificacion que se haga será para todos los objetos de la clase 


	private static int fichaSize=0; // el tamaño de la imagen 
	private static int maxFichas=0; //Es una constante para luego poder manejarlas
	private int row;
	private int col;
	private int idImage;
	private ImageIcon image;

	public Ficha (ImageIcon image,int idImage, int row, int col) {
		this.row= row;
		this.col= col;
		setImage(image,idImage);
		Dimension size = new Dimension(fichaSize,fichaSize);
		this.setPreferredSize(size); //eL TAMAÑO QUE SE QUIERE DE LA FICHA
		this.setBackground(Color.WHITE);
		this.setBorder(null);
		this.setFocusPainted(false);
	}

	private void setImage (ImageIcon image, int idImage) {
		this.image= image; 
		this.idImage=idImage;

		if (idImage<maxFichas-1) {
			setIcon(image);

		}
		else {
			setIcon(null);
		}
	}


	public static void setFichazSizeMaxFichas(int tamaño, int numeroFichas ) {
		maxFichas= numeroFichas;
		fichaSize= tamaño;

	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public int getIdImage() {
		return idImage;
	}

	public ImageIcon getImage() {
		return image;
	}

	public void mostrarImage() {
		setIcon(image);
	}


	// pieza es de objeto ficha y se le dice que intercambie con ficha 2zx
	public void intercambiar (Ficha otraFicha) {
		ImageIcon otraImagen= otraFicha.getImage();
		int idOtraImage = otraFicha.getIdImage();

		otraFicha.setImage(image, idImage);
		this.setImage(otraImagen, idOtraImage);
	}


	public boolean hasNotImage() {

		if (getIcon()==null) {
			return true;
		}
		return false;

	}
}
