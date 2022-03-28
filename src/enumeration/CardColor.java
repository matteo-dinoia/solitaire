package enumeration;

import java.awt.Color;

public enum CardColor {
	
	//VALUE  --------------------------------
	RED(Color.RED), BLACK(Color.black);

	
	//CONSTRUCTOR  ---------------------------
	private CardColor(Color color) {
		this.colorUI=color;
	}
	
	//GETTER AND SETTER  ---------------------
	private Color colorUI;
	public Color getColorUI() {
		return colorUI;
	}
	//----------------------------------------
}
