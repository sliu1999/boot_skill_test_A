package com.sliu.util;


import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.annotation.XmlElement;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Excel导入工具类 使用方法：在需要导入或者导出的属性上添加@XmlElement(name = "xxx")xxx代表列名
 *
 * @author liupan
 *
 */
public class ExcelUtil {

    /**
     *
     * 注此类现在只能对单表作用，以后会完善此功能 导入方法 通过反射自动注入
     * @param entityClass List中对象的类型（Excel中的每一行都要转化为该类型的对象）
     * @param excel
     * @param uniqueFields
     * @param num 从第几行开始读取
     * @return
     *
     */
	public static <T> List<T> excelImport(Class<T> entityClass, Workbook excel,String[] uniqueFields,int num) throws Exception, ParseException, IllegalAccessException, InstantiationException {
        List<T> resultList = new ArrayList<T>();
        Sheet sheet = excel.getSheetAt(0);
        Field field[] = entityClass.getDeclaredFields();

        //获取工作表的有效行数
        int realRows=0;
        for(int i=0;i<sheet.getPhysicalNumberOfRows();i++){
            Row row = sheet.getRow(i);
            if(row == null){
                continue;
            }
            int columnSize = row.getPhysicalNumberOfCells();
            int nullCols=0;
            for(int j = 0;j < columnSize;j++){
                Cell cell = row.getCell(j);
                if(cell == null){
                    nullCols++;
                }
            }

            if(nullCols == columnSize){
                break;
            }else{
                realRows++;
            }
        }
        //如果Excel中没有数据则提示错误
        if(realRows<=1){
            throw new Exception("Excel文件中没有任何数据");
        }

        int m = num;//从num 行读取
        while(sheet.getRow(m) == null){
            m++;
        }
        Row firstRow=sheet.getRow(m);
        int columnSize = firstRow.getPhysicalNumberOfCells();


//        String[] excelFieldNames=new String[columnSize];
        List<String> excelFieldNames = new ArrayList<String>();
        //获取Excel中的列名
        for(int i=0;i<columnSize;i++){
//        	if("".equals(firstRow.getCell(i)) || firstRow.getCell(i) == null){
//        		 throw new Exception("Excel中缺少必要的字段或字段名称有误");
//        	}
        	if(firstRow.getCell(i) != null && !"".equals(firstRow.getCell(i).getStringCellValue().trim())) {
                firstRow.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
//                excelFieldNames[i]=firstRow.getCell(i).getStringCellValue().trim();
                excelFieldNames.add(firstRow.getCell(i).getStringCellValue().trim());
        	}

        }
     
        //判断需要的字段在Excel中是否都存在 key:excel中的列名,value:类中的列名
        Map<String,String> fieldMap = new HashMap<String,String>();
        boolean isExist=true;
        for(int j = 0; j < field.length; j++){
            field[j].setAccessible(true);
            Annotation annotation = field[j].getAnnotation(XmlElement.class);
            if(annotation != null){
                XmlElement xmlElement = (XmlElement) annotation;

                if(!xmlElement.name().equals("##default")){
                    fieldMap.put(xmlElement.name(),field[j].getName());
                }else{
                    fieldMap.put(field[j].getName(),field[j].getName());
                }
            }

        }

//        List<String> excelFieldList= Arrays.asList(excelFieldNames);
        for(String cnName : fieldMap.keySet()){
            if(excelFieldNames.isEmpty() || !excelFieldNames.contains(cnName)){
                isExist=false;
                break;
            }
        }

        //如果有列名不存在，则抛出异常，提示错误
        if(!isExist){
            throw new Exception("Excel中缺少必要的字段或字段名称有误");
        }

        //将列名和列号放入Map中,这样通过列名就可以拿到列号
        Map<String, Integer> colMap=new HashMap<String, Integer>();
        for(int i = 0;i < excelFieldNames.size();i++){
            colMap.put(excelFieldNames.get(i), firstRow.getCell(i).getColumnIndex());
        }


        //将sheet转换为list
        for(int i=num+1;i<realRows;i++){
            //新建要转换的对象
            T entity=entityClass.newInstance();
            Row row = sheet.getRow(i);
            //给对象中的字段赋值
            for(Map.Entry<String, String> entry : fieldMap.entrySet()){
                //获取Excel中的列名(Excel 中文)
                String excelPropertiesName = entry.getKey();
                //获取类中的字段名(Class 英文)
                String classPropertiesName = entry.getValue();

                System.out.println("**************excel"+excelPropertiesName+"**********类"+classPropertiesName);
                //根据Excel中字段名获取列号
                int col=colMap.get(excelPropertiesName);

                //获取当前单元格中的内容
                Cell cell = row.getCell(col);
//                    String content=sheet.getCell(col, i).getContents().toString().trim();

//                    System.out.println("***************ExcelUtils content:"+content);
                //给对象赋值
                setFieldValueByName(classPropertiesName, cell, entity);
            }
            //遍历结果集
            for(T obj : resultList){
                for(int n = 0 ; n < uniqueFields.length; n++){
                    String fieldName = fieldMap.get(uniqueFields[n]);
                    Field currentField = getFieldByName(fieldName,obj.getClass());
                    currentField.setAccessible(true);
                    String value = currentField.get(obj) == null ? "" : (String)currentField.get(obj);
                    String excelValue = currentField.get(obj) == null ? "" : (String) currentField.get(entity);
                    if(value != "" && excelValue != "" && value.equals(excelValue)){
                        throw new Exception("Excel中"+value+"有重复!");
                    }

                }
            }
            resultList.add(entity);
        }
        return resultList;
    }

    public static String DateFormat(Date date, String formatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        return sdf.format(date);
    }

    /**
     * @MethodName  : setFieldValueByName
     * @Description : 根据字段名给对象的字段赋值
     * @param fieldName  字段名
     * @param cell    单元格
     * @param o 对象
     */
    private static void setFieldValueByName(String fieldName,Cell cell,Object o) throws IllegalAccessException, ParseException, Exception{

        String cellValue = null;
        if(cell == null){
            cellValue = "";
        }else{
            int cellType = cell.getCellType();
            switch(cellType) {
                case Cell.CELL_TYPE_STRING: //文本
                    cellValue = cell.getStringCellValue().trim();
                    break;
                case Cell.CELL_TYPE_NUMERIC: //数字、日期
                    if(DateUtil.isCellDateFormatted(cell)) {
                        cellValue = DateFormat(cell.getDateCellValue(),"yyyy-MM-dd"); //日期型
                    }
                    else {
                        DecimalFormat df = new DecimalFormat("#");
                        cellValue = df.format(cell.getNumericCellValue()); //数字
                    }
                    break;
                case Cell.CELL_TYPE_BOOLEAN: //布尔型
                    cellValue = String.valueOf(cell.getBooleanCellValue()).trim();
                    break;
                case Cell.CELL_TYPE_BLANK: //空白
                    cellValue = cell.getStringCellValue().trim();
                    break;
                case Cell.CELL_TYPE_ERROR: //错误
                    throw new Exception("单元格数据类型错误!");
                case Cell.CELL_TYPE_FORMULA: //公式
                    throw new Exception("单元格数据类型错误!");
            }
        }


        Field field=getFieldByName(fieldName, o.getClass());

        if(StringUtils.isEmpty(cellValue)){
        	return;
        }

        if(field == null){
            throw new Exception(o.getClass().getSimpleName() + "类不存在字段名 "+fieldName);
        }
        field.setAccessible(true);
        //获取字段类型
        Class<?> fieldType = field.getType();

        //根据字段类型给字段赋值
        if (String.class == fieldType) {
            field.set(o, String.valueOf(cellValue));
        } else if ((Integer.TYPE == fieldType)
                || (Integer.class == fieldType)) {
            field.set(o, Integer.parseInt(cellValue.toString().trim()));
        } else if ((Long.TYPE == fieldType)
                || (Long.class == fieldType)) {
            field.set(o, Long.valueOf(cellValue.toString().trim()));
        } else if ((Float.TYPE == fieldType)
                || (Float.class == fieldType)) {
            field.set(o, Float.valueOf(cellValue.toString().trim()));
        } else if ((Short.TYPE == fieldType)
                || (Short.class == fieldType)) {
            field.set(o, Short.valueOf(cellValue.toString().trim()));
        } else if ((Double.TYPE == fieldType)
                || (Double.class == fieldType)) {
        	System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa:"+cellValue.toString().trim());
            field.set(o, Double.valueOf(cellValue.toString().trim()));
        } else if (Character.TYPE == fieldType) {
            if ((cellValue!= null) && (cellValue.toString().length() > 0)) {
                field.set(o, Character.valueOf(cellValue.toString().charAt(0)));
            }
        }else if(Date.class==fieldType){
            System.out.println("*****************************日期为:" + cellValue.toString());
            Date date = new SimpleDateFormat("yy-MM-dd").parse(cellValue.toString());
            String d = new SimpleDateFormat("yyyy-MM-dd").format(date);
            System.out.println("****************************格式化后日期为:"+d);
            field.set(o, date);
        }else{
            field.set(o, cellValue);
        }
    }

    /**
     * @MethodName  : getFieldByName
     * @Description : 根据字段名获取字段
     * @param fieldName 字段名
     * @param clazz 包含该字段的类
     * @return 字段
     */
    private static Field getFieldByName(String fieldName, Class<?>  clazz){
        //拿到本类的所有字段
        Field[] selfFields=clazz.getDeclaredFields();

        //如果本类中存在该字段，则返回
        for(Field field : selfFields){
            if(field.getName().equals(fieldName)){
                return field;
            }
        }

        //否则，查看父类中是否存在此字段，如果有则返回
        Class<?> superClazz=clazz.getSuperclass();
        if(superClazz!=null  &&  superClazz !=Object.class){
            return getFieldByName(fieldName, superClazz);
        }

        //如果本类和父类都没有，则返回空
        return null;
    }


    /**
     * 1.创建 workbook
     * @return
     */
    public XSSFWorkbook getXSSFWorkbook(){
        return new XSSFWorkbook();
    }

    /**
     * 创建sheet
     * @param xssfWorkbook
     * @param sheetName sheetName sheet 名称
     * @return
     */
    public XSSFSheet getHSSFSheet(XSSFWorkbook xssfWorkbook, String sheetName){
        return xssfWorkbook.createSheet(sheetName);
    }

    /**
     * 3.写入表头信息
     * @param xssfWorkbook
     * @param xssfSheet
     * @param headInfoList List<Map<String, Object>>
     *              key: title         列标题
     *                   columnWidth   列宽
     *                   dataKey       列对应的 dataList item key
     */
    public void writeHeader(XSSFWorkbook xssfWorkbook,XSSFSheet xssfSheet ,List<Map<String, Object>> headInfoList){
        XSSFCellStyle cs = xssfWorkbook.createCellStyle();
        XSSFFont font = xssfWorkbook.createFont();
        font.setFontHeightInPoints((short)11);
        font.setFontName("宋体");
//        font.setBoldweight(font.BOLDWEIGHT_BOLD);
        cs.setFont(font);
        cs.setAlignment(CellStyle.ALIGN_CENTER);

        XSSFRow r = xssfSheet.createRow(0);
        r.setHeight((short) 300);
        XSSFCell c = null;
        Map<String, Object> headInfo = null;
        //处理excel表头
        for(int i=0, len = headInfoList.size(); i < len; i++){
            headInfo = headInfoList.get(i);
            c = r.createCell(i);
            c.setCellValue(headInfo.get("title").toString());
            c.setCellStyle(cs);
            if(headInfo.containsKey("columnWidth")){
                xssfSheet.setColumnWidth(i, (short)(((Integer)headInfo.get("columnWidth") * 8) / ((double) 1 / 20)));
            }
        }
    }

    /**
     * 4.写入内容部分
     * @param xssfWorkbook
     * @param xssfSheet
     * @param startIndex 从1开始，多次调用需要加上前一次的dataList.size()
     * @param headInfoList List<Map<String, Object>>
     *              key: title         列标题
     *                   columnWidth   列宽
     *                   dataKey       列对应的 dataList item key
     * @param dataList
     */
    public void writeContent(XSSFWorkbook xssfWorkbook,XSSFSheet xssfSheet ,int startIndex,
                             List<Map<String, Object>> headInfoList, List<Map<String, Object>> dataList){
        Map<String, Object> headInfo = null;
        XSSFRow r = null;
        XSSFCell c = null;
        //处理数据
        Map<String, Object> dataItem = null;
        Object v = null;
        for (int i=0, rownum = startIndex, len = (startIndex + dataList.size()); rownum < len; i++,rownum++){
            r = xssfSheet.createRow(rownum);
            r.setHeightInPoints(16);
            dataItem = dataList.get(i);
            for(int j=0, jlen = headInfoList.size(); j < jlen; j++){
                headInfo = headInfoList.get(j);
                c = r.createCell(j);
                v = dataItem.get(headInfo.get("dataKey").toString());

                if (v instanceof String) {
                    c.setCellValue((String)v);
                }else if (v instanceof Boolean) {
                    c.setCellValue((Boolean)v);
                }else if (v instanceof Calendar) {
                    c.setCellValue((Calendar)v);
                }else if (v instanceof Double) {
                    c.setCellValue((Double)v);
                }else if (v instanceof Integer
                        || v instanceof Long
                        || v instanceof Short
                        || v instanceof Float) {
                    c.setCellValue(Double.parseDouble(v.toString()));
                }else if (v instanceof HSSFRichTextString) {
                    c.setCellValue((HSSFRichTextString)v);
                }else {
                    c.setCellValue(v == null ? "" : v.toString());
                }
            }
        }
    }

    public void write2FilePath(XSSFWorkbook xssfWorkbook, OutputStream outputStream) throws IOException {
        try{
            xssfWorkbook.write(outputStream);
        }finally{
            if(outputStream != null){
                outputStream.close();
            }
        }
    }

    /*
     * @param sheetName   sheet名称
     * @param filePath   文件存储路径， 如：f:/a.xls
     * @param headInfoList List<Map<String, Object>>
     *                           key: title         列标题
     *                                columnWidth   列宽
     *                                dataKey       列对应的 dataList item key
     * @param dataList  List<Map<String, Object>> 导出的数据
     * @throws java.io.IOException
     *
     */
    public static void exportExcel2FilePath(OutputStream outputStream,String sheetName,
                                            List<Map<String, Object>> headInfoList,
                                            List<Map<String, Object>> dataList) throws IOException {
    	ExcelUtil poiUtil = new ExcelUtil();
        //1.创建 Workbook
        XSSFWorkbook xssfWorkbook = poiUtil.getXSSFWorkbook();
        //2.创建 Sheet
        XSSFSheet xssfSheet = poiUtil.getHSSFSheet(xssfWorkbook, sheetName);
        //3.写入 head
        poiUtil.writeHeader(xssfWorkbook, xssfSheet, headInfoList);
        if(dataList != null){
            //4.写入内容
            poiUtil.writeContent(xssfWorkbook, xssfSheet, 1, headInfoList, dataList);
        }
        //5.保存文件到filePath中
        poiUtil.write2FilePath(xssfWorkbook, outputStream);
    }
    
    public static Workbook checkExcelType(MultipartFile excelFile) throws Exception {
        // 获取文件输入流
        InputStream inputStream = excelFile.getInputStream();
        // 获取文件内容
        String fileName = excelFile.getOriginalFilename();

        // 获取文件类型
        String fileType = fileName.substring(fileName.indexOf("."));
        // 判断文件类型
        if ((".xls").equalsIgnoreCase(fileType)) {
            return new HSSFWorkbook(inputStream);
        } else if ((".xlsx").equalsIgnoreCase(fileType)) {
            return new XSSFWorkbook(inputStream);
        } else {
            throw new Exception("Excel 文件解析失败，格式错误！");
        }
    }

}
