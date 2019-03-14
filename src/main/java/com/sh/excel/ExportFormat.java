package com.sh.excel;

import jxl.format.Colour;
import jxl.write.WritableFont;

public abstract class ExportFormat {
	public abstract void addTitle(String[] titleName);
	public abstract void addTitle(String[] titleName,Colour colour);
	public abstract void addTitleAll(String titleName,int row);
	public abstract void addTitleAll(String titleName,int row,Colour colour);
	public void addContent(Object[] value){
		addContent(value,null);
	}
	public abstract void addContent(Object[] value,String headInfo,Colour colour,WritableFont font1);
	public abstract void addContent(Object[] value,String headInfo);
	public abstract void export();
}
