package com.sh.excel;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExcelFormat extends ExportFormat { 
	jxl.write.WritableWorkbook wwb;
	jxl.write.WritableSheet ws; 
	int currline = 0;
	public ExcelFormat(HttpServletResponse response,String fileTitle){
		try {
			wwb = Workbook.createWorkbook(response.getOutputStream());
			ws = wwb.createSheet(fileTitle,0);
			response.setHeader("Content-Type","application/msexcel");
			response.setContentType("application/octet-stream;charset=UTF-8;");
			response.setHeader("Content-Disposition", "attachment;filename="+new String(fileTitle.getBytes(),"ISO-8859-1")+".xls");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addTitle(String[] titleName){
		addTitle(titleName,Colour.WHITE);
	}
	public void addTitle(String[] titleName,Colour colour){
		try {
			WritableFont font1= new WritableFont(WritableFont.ARIAL, 12,WritableFont.NO_BOLD, false);
 //设置字体格式为excel支持的格式 WritableFont font3=new WritableFont(WritableFont.createFont("楷体 _GB2312"),12,WritableFont.NO_BOLD ); 
			WritableCellFormat format1=new WritableCellFormat(font1); 
			format1.setAlignment(jxl.format.Alignment.CENTRE); 
			format1.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			format1.setBackground(colour);  //添加颜色
			//设置顶部边框线为实线(默认是黑色－－也可以设置其他颜色)
			//format1.setBorder(jxl.format.Border.RIGHT, BorderLineStyle.MEDIUM);
			//format1.setBorder(jxl.format.Border.TOP, BorderLineStyle.MEDIUM);
			format1.setBorder(jxl.format.Border.ALL, BorderLineStyle.THIN);
			ws.setRowView(currline,450);
			ws.setColumnView(0, 7);
			for(int i=0;i<titleName.length;i++){
				ws.addCell(new jxl.write.Label(i,currline,titleName[i],format1));
				ws.setColumnView(i, 17);
			}
			currline++;
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	public void addTitleAll(String titleName,int row){
		addTitleAll( titleName, row,Colour.WHITE);
	}
	public void addTitleAll(String titleName,int row,Colour colour){
		try {
			WritableFont font1= new WritableFont(WritableFont.TIMES,12,WritableFont.NO_BOLD); //设置字体格式为excel支持的格式 WritableFont font3=new WritableFont(WritableFont.createFont("楷体 _GB2312"),12,WritableFont.NO_BOLD ); 
			
			WritableCellFormat format1=new WritableCellFormat(font1); 
			format1.setAlignment(jxl.format.Alignment.CENTRE); 
			format1.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			format1.setBorder(jxl.format.Border.ALL, BorderLineStyle.THIN);
			format1.setBackground(colour);  //添加颜色
			//format1.setBackground(Colour.YELLOW);  //添加颜色

			ws.addCell(new jxl.write.Label(0,currline,titleName,format1));
			ws.setRowView(0,500);
			ws.mergeCells(0,0,row,0);  //合并单元格，参数格式（开始列，开始行，结束列，结束行）
			currline++;
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addContent(Object[] value,String headInfo){
		WritableFont font1= new WritableFont(WritableFont.createFont("宋体"), 10,WritableFont.NO_BOLD, false);
		addContent(value,headInfo,Colour.WHITE,font1);
	}
	
	/**
	 * 添加内容
	 * 
	 */
	public void addContent(Object[] value,String headInfo,Colour colour,WritableFont font1){ 
		jxl.write.WritableCell cell;
		try {
			//WritableFont font1= new WritableFont(WritableFont.ARIAL, 12,WritableFont.NO_BOLD, false);
			WritableCellFormat format1=new WritableCellFormat(font1); 
			format1.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			format1.setBorder(jxl.format.Border.ALL, BorderLineStyle.THIN);
			format1.setBackground(colour);  //添加颜色
			format1.setWrap(true);
			format1.setAlignment(Alignment.CENTRE);
			format1.setVerticalAlignment(VerticalAlignment.CENTRE); 
			
			for(int i=0;i<value.length;i++){
				if(value[i] instanceof Integer)
					cell = new jxl.write.Number(i,currline,(Integer)value[i],format1);
				else if(value[i] instanceof Float)
					cell = new jxl.write.Number(i,currline,(Float)value[i],format1);
				else if(value[i] instanceof Double)
					cell = new jxl.write.Number(i,currline,(Double)value[i],format1);
				else
					cell = new jxl.write.Label(i,currline,(String)value[i],format1);
				ws.addCell(cell);
			}
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		currline++;
	}
	
	public void export(){
		  try {
			wwb.write();
			wwb.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	  
	}
}
