package com.web.sys.utils;

import org.apache.commons.io.FileExistsException;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import javax.validation.constraints.NotNull;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Excel {

    public static HSSFCellStyle createCellStyle(@NotNull HSSFWorkbook workbook){

        HSSFCellStyle style = workbook.createCellStyle();

        //设置上下左右四个边框宽度
        style.setBorderTop(HSSFBorderFormatting.BORDER_THIN);
        style.setBorderBottom(HSSFBorderFormatting.BORDER_THIN);
        style.setBorderLeft(HSSFBorderFormatting.BORDER_THIN);
        style.setBorderRight(HSSFBorderFormatting.BORDER_THIN);

        //设置上下左右四个边框颜色
//        style.setTopBorderColor(HSSFColor.RED.index);
//        style.setBottomBorderColor(HSSFColor.RED.index);
//        style.setLeftBorderColor(HSSFColor.RED.index);
//        style.setRightBorderColor(HSSFColor.RED.index);

        //设置单元格背景色
//        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
//        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        //设置字体格式
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)9);
        font.setFontHeight((short) 300);
//        font.setColor(HSSFColor.YELLOW.index);
        font.setBoldweight(font.BOLDWEIGHT_BOLD);
//        font.setItalic(true);
//        font.setStrikeout(true);
//        font.setUnderline((byte)1);
        //将字体格式设置到HSSFCellStyle上
        style.setFont(font);

        // 设置字体对齐方式
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中

        return style;

    }

    public static boolean createExcel(String fileName, Object[][] data)throws FileExistsException {return createExcel(fileName,data,null);}

    public static boolean createExcel(String fileName, Object[][] data,String sheetName)throws  FileExistsException { return createExcel(fileName, data, sheetName,true); }

    public static boolean createExcel(String fileName,Object[][] data,String sheetName,boolean overwrite)throws FileExistsException{
        //创建excel文件
        if(FileUtil.fileExist(fileName)){
            if(overwrite){
                FileUtil.deleteFile(fileName);
            }else{
                throw new FileExistsException("文件已存在");
            }
        }
        if(data == null || data.length < 1) return false;
        //创建excel工作簿
        HSSFWorkbook workbook=new HSSFWorkbook();
        //创建工作表sheet
        HSSFSheet sheet = null;
        if(T.isNullOrWhite(sheetName)){
            sheet = workbook.createSheet();
        }else{
            sheet = workbook.createSheet(sheetName);
        }
        HSSFCellStyle style = createCellStyle(workbook);
        double[] colWidths = new double[data[0].length];
        //写入数据
        for (int i=0;i<data.length;i++){
            HSSFRow nrow=sheet.createRow(i);
            nrow.setHeight((short)(15.625*25));
//            nrow.setHeightInPoints((float)40);
            for(int j =0;j<data[i].length;j++){
                String v = "";
                if(data[i][j] != null){
                    v = data[i][j].toString();
                }
                double len =0;
                for(String s:v.split("")){
                    if(T.isChinese(s)) len += 2.1;
                    else len += 1;
                }
                colWidths[j] = Math.max(colWidths[j],len);
                HSSFCell ncell=nrow.createCell(j);
                ncell.setCellValue(v==null?"":v.toString());
                ncell.setCellStyle(style);
            }
        }
        //设置列宽
        for(int i=0;i<colWidths.length;i++){
            sheet.setColumnWidth(i, Math.max((int)colWidths[i],4)*450);
        }
        File file=new File(fileName);
        try {
            file.createNewFile();
            FileOutputStream stream= FileUtil.openOutputStream(file);
            workbook.write(stream);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public static String[][] readExcel(String fileName,int ignoreRows)throws Exception{
        if(!FileUtil.fileExist(fileName)){
            throw new Exception("file not exists.");
        }
        File file = new File(fileName);
        List<String[]> result = new ArrayList<String[]>();

        int rowSize = 0;

        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));

        // 打开HSSFWorkbook
        POIFSFileSystem fs = new POIFSFileSystem(in);
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        HSSFCell cell = null;

        for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
            HSSFSheet st = wb.getSheetAt(sheetIndex);

            // 第一行为标题，不取
            for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
                HSSFRow row = st.getRow(rowIndex);
                if (row == null) {
                    continue;
                }
                int tempRowSize = row.getLastCellNum() + 1;
                if (tempRowSize > rowSize) {
                    rowSize = tempRowSize;
                }

                String[] values = new String[rowSize];
                Arrays.fill(values, "");
                boolean hasValue = false;

                for (int columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
                    String value = "";
                    cell = row.getCell(columnIndex);
                    if (cell != null) {
                        // 注意：一定要设成这个，否则可能会出现乱码

//                        cell.setEncoding(HSSFCell.ENCODING_UTF_16);

                        switch (cell.getCellType()) {

                            case HSSFCell.CELL_TYPE_STRING:
                                value = cell.getStringCellValue();
                                break;

                            case HSSFCell.CELL_TYPE_NUMERIC:
                                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                    Date date = cell.getDateCellValue();
                                    if (date != null) {
                                        value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                                .format(date);
                                    } else {
                                        value = "";
                                    }
                                } else {
                                    value = new DecimalFormat("0").format(cell
                                            .getNumericCellValue());
                                }
                                break;

                            case HSSFCell.CELL_TYPE_FORMULA:
                                // 导入时如果为公式生成的数据则无值
                                if (!cell.getStringCellValue().equals("")) {
                                    value = cell.getStringCellValue();
                                } else {
                                    value = cell.getNumericCellValue() + "";
                                }
                                break;

                            case HSSFCell.CELL_TYPE_BLANK:
                                break;

                            case HSSFCell.CELL_TYPE_ERROR:
                                value = "";
                                break;

                            case HSSFCell.CELL_TYPE_BOOLEAN:
                                value = (cell.getBooleanCellValue() == true ? "Y"
                                        : "N");
                                break;

                            default:
                                value = "";
                        }

                    }

                    if (columnIndex == 0 && value.trim().equals("")) {
//                        break;
                    }

                    values[columnIndex] = T.rightTrim(value);
                    hasValue = true;
                    System.out.print(value + "     ");
                }
                T.print();
                if (hasValue) {
                    result.add(values);
                }
            }
        }

        in.close();

        String[][] returnArray = new String[result.size()][rowSize];

        for (int i = 0; i < returnArray.length; i++) {

            returnArray[i] = (String[]) result.get(i);

        }

        return returnArray;
    }

    public static void main(String[] args) throws Exception,IOException {
        Object[][] data = {
                {"URL","所属者TLKSDJF","调用者123","1","123","123456789887654","DFSDFDFSFSDF","123","124567893","123","123"},
                {"URL","所属者TLKSDJF","调用者123","1","123","123456789887654","DFSDFDFSFSDF","123","123","123","123"},
                {"URL","所属者TLKSDJF","调用者123","1","123","123456789887654","DFSDFDFSFSDF","123","123","123","123"},
                {"URL","所属者TLKSDJF","调用者123","1","123","123456789887654","DFSDFDFSFSDF","123","123","123","123"},
                {"URL","所属者TLKSDJF","调用者123","1","123","123456789887654","DFSDFDFSFSDF","123","123","123","123"},
        };
//        createExcel("123.xls",data,"汇总");
        // T.print("123");
        String[][] dread = readExcel("123.xls",0);
        for(Object[] item :dread){
            // T.print(Arrays.toString(item));
        }
        // T.print("123");
    }
}