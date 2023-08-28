package com.project.voting.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ExcelUtil {

    public String getCellValue(Cell cell) {

        String value = "";

        if(cell == null){
            return value;
        }

        switch (cell.getCellType()) {
            case STRING:
                value = cell.getStringCellValue();
                break;
            case NUMERIC:
                value = (int) cell.getNumericCellValue() + "";
                break;
//            case BLANK:
//                value = null;
            default:
                break;
        }
        return value;
    }

    public List<Map<String, String>> getListData(MultipartFile file, int startRowNum, int columnLength) {

        List<Map<String, String>> excelList = new ArrayList<>();


        try {
            Workbook workbook  = WorkbookFactory.create(file.getInputStream());

            Sheet sheet = workbook.getSheetAt(0);

            int rowIndex = 0;
            int columnIndex = 0;

            for (rowIndex = startRowNum; rowIndex < sheet.getLastRowNum() + 1; rowIndex++) {
                Row row = sheet.getRow(rowIndex);

                if (row.getCell(0) != null && !row.getCell(0).toString().isBlank()) {

                    Map<String, String> map = new HashMap<>();

                    int cells = columnLength;

                    for (columnIndex = 0; columnIndex <= cells; columnIndex++) {
                        Cell cell = row.getCell(columnIndex);
                        map.put(String.valueOf(columnIndex), getCellValue(cell));
                        log.info(rowIndex + " 행 : " + columnIndex+ " 열 = " + getCellValue(cell));
                    }

                    excelList.add(map);

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return excelList;

    }
}