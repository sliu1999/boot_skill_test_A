package com.sliu.Controller;

import com.sliu.domain.TbPartyLearnStatistics;
import com.sliu.util.ExcelUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class ExcelImport {


    @PostMapping("/import")
    public void importStatistics(MultipartHttpServletRequest request, HttpServletResponse response)throws IOException {
        MultipartFile file = request.getFile("file");
        response.setContentType("text/html");
        String[] uniqueFields = {};
        List<TbPartyLearnStatistics> tbPartyLearnStatisticsList = null;
        Boolean b = false;
        int num = 2;//从第三行开始读取
        try {
            Workbook workbook = ExcelUtil.checkExcelType(file);
            tbPartyLearnStatisticsList = ExcelUtil.excelImport(TbPartyLearnStatistics.class, workbook, uniqueFields, num);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
