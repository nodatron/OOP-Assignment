//Base class for all the drawing
abstract class Draw 
{
	float border;
	float vertRange;
	float horRange;
	float textWidth;
	float textOffset;

	Draw() 
	{
		border = width * 0.1f;
		vertRange = height - (border * 2.0f);
		horRange = width - (border * 2.0f);
		textWidth = 0;
		textOffset = 0;
	}

	Draw(float border)
	{
		this.border = border;
		vertRange = height - (this.border * 2.0f);
		horRange = width - (this.border * 2.0f);
		textWidth = 0;
		textOffset = 0;
	}

	abstract void drawVis();
	
}