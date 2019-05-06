package com.system.attendance.service.impl;

import com.system.attendance.mapper.AttendanceMapper;
import com.system.attendance.model.Attendance;
import com.system.attendance.utils.TimeUtil;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

@Service
public class AttendExcelService{

    private static final Logger LOG = LoggerFactory.getLogger(AttendExcelService.class);

    @Autowired
    private AttendanceMapper attendanceMapper;

    /**
     * 数据库用户excel表导出
     * @param response
     * @return
     * @throws Exception
     */
    public String exportAttendFromDB(HttpServletResponse response) throws Exception {
        String status = "success";
        String time = TimeUtil.todayStringTime();
        String filename = "attend_info_"+time+".xlsx"; //XSSFWorkbook为导出excel2007版及以上的
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("考勤信息");
        createTitle(workbook,sheet);
        List<Attendance> attendances = attendanceMapper.getMonth(TimeUtil.getMonth());

        //新增数据行，并且设置单元格数据
        int rowNum=1;
        for (Attendance attend: attendances) {
            XSSFRow row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(attend.getAttendanceId());
            row.createCell(1).setCellValue(attend.getUserId());
            row.createCell(2).setCellValue(attend.getUserName());
            row.createCell(3).setCellValue(attend.getDept());
            row.createCell(4).setCellValue(attend.getTime());
            row.createCell(5).setCellValue(attend.getSignInTime());
            row.createCell(6).setCellValue(attend.getSignOutTime());
            row.createCell(7).setCellValue(attend.getAttendanceStatus());
            row.createCell(8).setCellValue(attend.getAttendanceType());
            rowNum++;
        }
        //生成excel文件
        buildExcelFile(filename,workbook);

        //浏览器下载excel
        buildExcelDocument(filename,workbook,response);

        return status;
    }

    //创建表头
    private void createTitle(XSSFWorkbook workbook, XSSFSheet sheet){
        XSSFRow row = sheet.createRow(0);
        //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
        sheet.setColumnWidth(0,25*256);
        sheet.setColumnWidth(1,15*256);
        sheet.setColumnWidth(2,10*256);
        sheet.setColumnWidth(3,10*256);
        sheet.setColumnWidth(4,18*256);
        sheet.setColumnWidth(5,10*256);
        sheet.setColumnWidth(6,10*256);
        sheet.setColumnWidth(7,10*256);
        sheet.setColumnWidth(8,20*256);

        //设置为加粗
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        style.setFont(font);

        XSSFCell cell;
        cell = row.createCell(0);
        cell.setCellValue("考勤编号");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue("用户编号");
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue("姓名");
        cell.setCellStyle(style);

        cell = row.createCell(3);
        cell.setCellValue("部门");
        cell.setCellStyle(style);

        cell = row.createCell(4);
        cell.setCellValue("考勤日期");
        cell.setCellStyle(style);

        cell = row.createCell(5);
        cell.setCellValue("签到时间");
        cell.setCellStyle(style);

        cell = row.createCell(6);
        cell.setCellValue("签退时间");
        cell.setCellStyle(style);

        cell = row.createCell(7);
        cell.setCellValue("考勤状态");
        cell.setCellStyle(style);

        cell = row.createCell(8);
        cell.setCellValue("考勤类型");
        cell.setCellStyle(style);


    }

    //生成excel文件
    protected  void buildExcelFile(String filename, XSSFWorkbook workbook) throws Exception {
        FileOutputStream fos = new FileOutputStream(filename);
        workbook.write(fos);
        fos.flush();
        fos.close();
    }

    //浏览器下载excel
    protected void buildExcelDocument(String filename,XSSFWorkbook workbook,HttpServletResponse response) throws Exception{
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(filename, "utf-8"));
        OutputStream out = response.getOutputStream();
        workbook.write(out);
        out.flush();
        out.close();
        LOG.info("----浏览器下载考勤信息");
    }

}
