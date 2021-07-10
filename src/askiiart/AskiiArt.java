/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class AskiiArt {

	//TODO:
	//take a pic of the result to make a gf, and then turn gifs into askii gifs
	//get the average of an area, instead of only reading every certain pixel
	public static void main(String[] args) throws IOException {//must use Courier, *Menlo*, and Consolas, as they are monospaced
		//														- will look best on Menlo, where i tested

//<editor-fold defaultstate="collapsed" desc="Find Pics">
		File folder = new File("pics");
		File[] listOfFiles = folder.listFiles();
		File[] onlyFiles = new File[listOfFiles.length];
		int count = 0;

		//getting rid of any folders and any non pic files
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				if (listOfFiles[i].getName().contains("jpg") || listOfFiles[i].getName().contains("JPG") || listOfFiles[i].getName().contains("png") || listOfFiles[i].getName().contains("jpeg")) {
					onlyFiles[count] = listOfFiles[i];
					count++;
				}
			}
		}
		File tempF;
		for (int i = 0; i < count - 1; i++) {
			for (int j = i + 1; j < count; j++) {
				if (onlyFiles[i].getName().compareToIgnoreCase(onlyFiles[j].getName()) > 0) {
					tempF = onlyFiles[i];
					onlyFiles[i] = onlyFiles[j];
					onlyFiles[j] = tempF;
				}
			}
		}

		for (int i = 0; i < count; i++) {
			System.out.println((i + 1) + ") " + onlyFiles[i].getName());

		}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Choose Pic">
		System.out.println("\nType the number of the entry you want to create");
		System.out.println("");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int num = Integer.parseInt(in.readLine()) - 1;
		String name = "pics/" + onlyFiles[num].getName();
		BufferedImage temp = ImageIO.read(new File(name));
		System.out.println("");
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Choose Dimensions">
		int width = temp.getWidth();
		int height = temp.getHeight();

		System.out.println("Dimensions:");
		System.out.println("Height: " + height + " pixels");
		System.out.println("Width: " + width + " pixels");
		System.out.println("How would you like to size the new image?");
		System.out.println("1) set the new height");
		System.out.println("2) set the new width");
		System.out.println("3) set the factor the picture is reduced by (2 means it has the origional pixels / 2)\n");
		num = Integer.parseInt(in.readLine());
		System.out.println("");
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Set new Dimensions">
		double factor = 0;//how many times its reduced (pixels wise)
		int[][] pixel = null;
		int nHeight = 0;
		int nWidth = 0;

		switch (num) {
			case 1://set height

				System.out.println("Enter the amount of characters you want going down");
				System.out.println("");
				nHeight = Integer.parseInt(in.readLine());
				System.out.println("");
				factor = (double) height / (double) nHeight;
				nWidth = (int) Math.round(2 * width / factor);

				pixel = new int[nWidth][nHeight];

				//faster
				for (int j = 0; j < nHeight; j++) {
					for (int i = 0; i < nWidth; i++) {
						Color c = new Color(temp.getRGB((int) Math.round(i * factor / 2), (int) Math.round(j * factor)));

						int grey = Math.round((c.getBlue() + c.getGreen() + c.getRed()) / 3);

						pixel[i][j] = grey;

					}

				}
				break;
			case 2://set width

				System.out.println("Enter the amount of characters you want going across");
				System.out.println("");
				nWidth = Integer.parseInt(in.readLine());
				System.out.println("");
				factor = (double) width / (double) nWidth;
				nHeight = (int) Math.round(height / (factor * 2));

				pixel = new int[nWidth][nHeight];

				//faster
				for (int j = 0; j < nHeight; j++) {
					for (int i = 0; i < nWidth; i++) {
						Color c = new Color(temp.getRGB((int) Math.round(i * factor), (int) Math.round(2 * j * factor)));

						int grey = Math.round((c.getBlue() + c.getGreen() + c.getRed()) / 3);

						pixel[i][j] = grey;

					}

				}
				break;
			case 3://set factor

				System.out.println("Enter the factor to reduce the picture by (can be a decimal) - the lowest is 1.1");
				System.out.println("A factor of 3 means the height = (height/3) , but the width = (width/3)*2");
				System.out.println("");
				factor = Double.parseDouble(in.readLine());
				System.out.println("");

				nHeight = (int) Math.round(height / (factor));
				nWidth = (int) Math.round(2 * width / factor);

				pixel = new int[nWidth][nHeight];

				//faster
				for (int j = 0; j < nHeight; j++) {
					for (int i = 0; i < nWidth; i++) {
						Color c = new Color(temp.getRGB((int) Math.round(i * factor / 2), (int) Math.round(j * factor)));

						int grey = Math.round((c.getBlue() + c.getGreen() + c.getRed()) / 3);

						pixel[i][j] = grey;

					}

				}

				break;
			default:

				break;
		}
		//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Display new Dimensions">
		System.out.println("New Dimensions:");
		System.out.println("New Height: " + nHeight + " pixels");
		System.out.println("New Width: " + nWidth + " pixels");
		System.out.println("Height Reduction Factor: " + Math.round(factor * 100) / 100.0);
		System.out.println("Width Reduction Factor: " + Math.round(((double) width / (double) nWidth) * 100) / 100.0);
		System.out.println("");
		System.out.println("");
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="get new Pixels">
		StringBuilder tempSB = getPixels(nHeight, nWidth, pixel);
//</editor-fold>

//		FileWriter b = new FileWriter("Askii art copy", true);
//		b.append(tempSB.toString());
//		b.close();
		System.out.println(tempSB.toString());

//<editor-fold defaultstate="collapsed" desc="Display origional pic">
		if (false) {
			JFrame frame = new JFrame();
			ImageIcon icon = new ImageIcon(temp);
			JLabel label = new JLabel(icon);
			frame.add(label);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.pack();
			frame.setVisible(true);
		}
		//</editor-fold>

	}

	public static StringBuilder getPixels(int h, int w, int[][] pixel) {
		StringBuilder sb = new StringBuilder();
		for (int j = 0; j < h; j++) {//make this more optimised
//			sb.append((j + 1) + ")");
			for (int i = 0; i < w; i++) {

//17 FROM 0 - 256
//best case: you enter characters, and it finds those with most pixels
//second best: i enter any number of characters, it determines the colour range and then gives the correct character
//add an invert colours options (swap the order of characters in the string if i get this ^ working)
				if (pixel[i][j] < 15) {
					sb.append("@");//want some here
				} else if (pixel[i][j] < 30) {
					sb.append("N");
				} else if (pixel[i][j] < 45) {
					sb.append("M");
				} else if (pixel[i][j] < 60) {
					sb.append("#");
				} else if (pixel[i][j] < 75) {
					sb.append("&");
				} else if (pixel[i][j] < 90) {
					sb.append("§");
				} else if (pixel[i][j] < 105) {
					sb.append("$");
				} else if (pixel[i][j] < 120) {
					sb.append("%");//want sum here
				} else if (pixel[i][j] < 135) {
					sb.append("X");
				} else if (pixel[i][j] < 150) {
					sb.append("I");
				} else if (pixel[i][j] < 165) {
					sb.append("±");
				} else if (pixel[i][j] < 180) {
					sb.append("+");
				} else if (pixel[i][j] < 195) {
					sb.append("^");
				} else if (pixel[i][j] < 210) {
					sb.append(",");
				} else if (pixel[i][j] < 225) {
					sb.append("-");
				} else if (pixel[i][j] < 240) {
					sb.append(".");
				} else if (pixel[i][j] < 257) {
					sb.append(" ");
				} else {
					System.out.println("Error");
				}

			}
			sb.append("\r\n");
//			sb.append(System.getProperty("line.separator"));

		}
		return sb;
	}

}
//resize all pics to a certain height, with the origional scale of the pic, make a new array with the set height
//scale down

/*
 * 		//255 max //0-50
 *
 * .
 * ,
 * -
 * ^
 * +
 * ±
 * *
 * %
 * $
 * §
 * # swap this and next maybe? & @
 */

 /*
 * //	JLabel label = new JLabel(icon); //	frame.add(label); //
 * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //	frame.pack(); //
 * frame.setVisible(true);
 *
 */
