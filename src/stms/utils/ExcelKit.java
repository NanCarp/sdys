package stms.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelKit {
	 public static List<String[]> getExcelData(File file){
	        return getData(file).get(0);//选择sheet1
	    }
	    private static List<List<String[]>> getData(File file){
	        HSSFWorkbook workbook;
	        List<List<String[]>> data = new ArrayList<List<String[]>>();
	        try {
	            workbook = new HSSFWorkbook(new FileInputStream(file));
	            HSSFSheet sheet=null;
	            //循环sheet
	            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
	                sheet=workbook.getSheetAt(i);
	                List<String[]> rows = new ArrayList<String[]>();
	                int colsnum = 0;
	                //循环每一行	                              
	                for (int j = 1; j <= sheet.getLastRowNum(); j++) {
	                	//j=1;从第2行开始录入数据
	                    HSSFRow row=sheet.getRow(j);
	                    if(null != row){
	                        //getRow(0)，已第一列为基准，读取第一行的列数，即标题为导入基准
	                        colsnum = sheet.getRow(0).getPhysicalNumberOfCells();
	                        String[] cols = new String[colsnum];
	                        //循环每一个单元格，以一行为单位，组成一个数组
	                        for (int k = 0; k < colsnum; k++) {
	                            //判断单元格是否为null，若为null，则置空
	                            if(null != row.getCell((short) k)) {
	                                int type = row.getCell((short) k).getCellType();
	                                //判断单元格数据是否为数字
	                                if(type == HSSFCell.CELL_TYPE_NUMERIC){
	                                    //判断该数字的计数方法是否为科学计数法，若是，则转化为普通计数法
	                                    if(String.valueOf(row.getCell((short) k).getNumericCellValue()).matches(".*[E|e].*")) {
	                                        DecimalFormat df = new DecimalFormat("#.#");
	                                        //指定最长的小数点位为10
	                                        df.setMaximumFractionDigits(10);
	                                        cols[k] = df.format(row.getCell((short) k).getNumericCellValue());
	                                    //判断该数字是否是日期,若是则转成字符串
	                                    } else if(row.getCell((short) k).getCellStyle().getDataFormat()==176){
	                                        Date d = row.getCell((short) k).getDateCellValue();
	                                        DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
	                                        cols[k] = formater.format(d).toString(); 
	                                    }else{
	                                        cols[k] = (row.getCell((short) k).getNumericCellValue()+"").trim(); 
	                                    }
	                                } else {
	                                    cols[k] = (row.getCell((short) k).getStringCellValue()+"").trim();          
	                                }
	                            } else {
	                               cols[k] = "";
	                            }
	                        }
	                        //以一行为单位，加入list
	                        rows.add(cols);
	                    }
	                }
	                //返回所有数据，第一个list表示sheet，第二个list表示sheet内所有行数据，第三个string[]表示单元格数据
	                data.add(rows);
	            }
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return  data;
	    }
}
