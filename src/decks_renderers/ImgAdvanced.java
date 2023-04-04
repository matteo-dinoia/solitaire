package decks_renderers;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImgAdvanced{
	private BufferedImage img;
	private Integer rowGrid = null;
	private Integer colGrid = null;

	public static ImgAdvanced getFromFile(String pathName){
		BufferedImage img = null;

		try {
			img = ImageIO.read(new File(pathName));
		} catch (IOException e) {
			return null;
		}

		return new ImgAdvanced(img);
	}

	public void writeOnFile(String pathNameResult){
		try {
			ImageIO.write(this.img, "png", new File(pathNameResult));
		} catch (IOException e) {}
	}

	public ImgAdvanced(BufferedImage img){
		this.img = img;
	}

	public Image getImage(){
		return img;
	}

	public void addGrid(int colGrid, int rowGrid){
		this.rowGrid = rowGrid;
		this.colGrid = colGrid;
	}

	public ImgAdvanced getGridElement(int col, int row, int paddingX, int paddingY){
		return getGridElement(col, row, paddingX, paddingX, paddingY, paddingY);
	}

	public ImgAdvanced getGridElement(int col, int row, int paddingL, int paddingR, int paddingT, int paddingB){
		int hSize = img.getWidth() / colGrid;
		int vSize = img.getHeight() / rowGrid;

		return getCropped(hSize * col + paddingL, vSize * row + paddingT,
				hSize - (paddingL + paddingR), vSize - (paddingT + paddingB));
	}

	public ImgAdvanced getCropped(int x, int y, int width, int height){
		if(rowGrid == null || colGrid == null)
			return null;

		return new ImgAdvanced(img.getSubimage(x, y, width, height));
	}


}
