package com.sh.excel;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Auther: admin
 * @Date: 2019/3/1 11:11
 * @Description:
 */
public class ExcelUtils {

    /**
     * 功能描述: Excel导入
     * @param:
     * @return:
     * @auther: admin
     * @date: 2019/3/1 11:17
     */
    public static <T> List<T> importExcel(MultipartFile file, Class<T> bean) {
        List<T> list = new ArrayList<>();
        List<String[]> dataList = readExcel(file);
        if(!dataList.isEmpty()){
            list = getBeanFromStringArray(dataList, bean);
        }
        return list;
    }

    /**
     * 功能描述: 读取Excel数据
     * @param: file filePath fileName
     * @return: 数组集合
     * @auther: admin
     * @date: 2019/3/4 14:39
     */
    public static List<String[]> readExcel(MultipartFile file){
        List<String[]> l = new ArrayList<String[]>();
        if (file != null) {
            try {
                if (file.getOriginalFilename().indexOf(".xlsx") > 0) {
                    XSSFWorkbook workBook = new XSSFWorkbook(file.getInputStream());
                    XSSFSheet sheet = workBook.getSheetAt(0);
                    if (sheet != null) {
                        for (int i = 0 ; i < sheet.getPhysicalNumberOfRows() ; i++) {
                            XSSFRow row = sheet.getRow(i);
                            String[] data = new String[row.getPhysicalNumberOfCells()];
                            for (int j = 0 ; j < row.getPhysicalNumberOfCells() ; j++) {
                                XSSFCell cell = row.getCell(j);
                                String cellStr = cell.toString();
                                data[j] = cellStr;
                            }
                            l.add(data);
                        }
                    }
                }
                else {
                    HSSFWorkbook workBook = new HSSFWorkbook(file.getInputStream());
                    HSSFSheet sheet = workBook.getSheetAt(0);
                    if (sheet != null) {
                        for (int i = 0 ; i < sheet.getPhysicalNumberOfRows() ; i++) {
                            HSSFRow row = sheet.getRow(i);
                            String[] data = new String[row.getPhysicalNumberOfCells()];
                            for (int j = 0 ; j < row.getPhysicalNumberOfCells() ; j++) {
                                HSSFCell cell = row.getCell(j);
                                String cellStr = cell.toString();
                                data[j] = cellStr;
                            }
                            l.add(data);
                        }
                    }
                }
            }
            catch (IllegalStateException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return l;
    }


    /**
     * 数组转为对象集合
     *
     * @param dataList
     *          集合数据
     * @param bean
     *          类类型
     * @return List<T>
     */
    private static <T> List<T> getBeanFromStringArray(List<String[]> dataList, Class<T> bean) {
        List<T> list = new ArrayList<>();
        List<Map<String, String>> titles = getTitles(dataList);
        Map<String, Field> fields = getFields(bean);
        try {
            for (Map<String, String> map : titles) {
                T t = bean.newInstance();
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    if (fields.containsKey(entry.getKey())) {
                        Field field = fields.get(entry.getKey());
                        Class<?> valType = field.getType();
                        Object value = getType(entry.getValue(), valType);
                        String fieldName = field.getName();
                        String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                        Method method = ReflectionUtils.findMethod(bean, methodName, valType);
                        if (method != null) {
                            ReflectionUtils.invokeMethod(method, t, value);
                        }
                    }
                }
                list.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 数组标题与值的对应关系
     *
     * @param dataList
     *          集合数据
     * @return
     */
    private static <T> List<Map<String, String>> getTitles(List<String[]> dataList) {
        List<Map<String, String>> list = new ArrayList<>();
        String[] titles = dataList.get(0);
        dataList.remove(0);
        for (String[] values : dataList) {
            Map<String, String> titleMap = new HashMap<>();
            for (int i = 0; i < values.length; i++) {
                titleMap.put(titles[i], values[i]);
            }
            list.add(titleMap);
        }
        return list;
    }

    /**
     * 注解名称与字段属性的对应关系
     *
     * @param clazz
     *          实体对象类类型
     * @param <T>
     *          泛型类型
     * @return Map<String,Field>
     */
    private static <T> Map<String, Field> getFields(Class<T> clazz) {
        Map<String, Field> annoMap = new HashMap<>();
        Field[] fileds = clazz.getDeclaredFields();
        for (Field filed : fileds) {
            XlsField anno = filed.getAnnotation(XlsField.class);
            if (anno != null) {
                // 获取name属性值
                if (StringUtils.isNotBlank(anno.name())) {
                    annoMap.put(anno.name(), filed);
                }
            }
        }
        return annoMap;
    }

    /**
     * 转换成实体属性对应的类型
     *
     * @param value
     *          每一格的数值
     * @param valType
     *          实体属性类型
     * @return Object 转换为对应类型以obj返回
     */
    private static <T> Object getType(String value, Class<T> valType) {
        try {
            if (valType == Date.class) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                return sdf.parse(value);
            } else if (valType == Double.class) {
                return Double.parseDouble(value);
            } else if (valType == BigDecimal.class) {
                return new BigDecimal(value);
            } else if (valType == Integer.class) {
                return Integer.parseInt(value);
            } else if (valType == Long.class) {
                return Long.parseLong(value);
            } else if (valType == Boolean.class) {
                return Boolean.parseBoolean(value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

}
