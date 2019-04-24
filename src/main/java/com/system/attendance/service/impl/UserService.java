package com.system.attendance.service.impl;

import com.system.attendance.mapper.UserMapper;
import com.system.attendance.model.User;
import com.system.attendance.service.IUserService;
import com.system.attendance.utils.TimeUtil;
import com.system.attendance.utils.UUIDUtil;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService implements IUserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;

    /**
     *  获取全部用户信息
     * @return
     */
    @Override
    public List<User> getAll() {
        return userMapper.selectAll();
    }

    //新增用户
    @Transactional
    @Override
    public int addUser(User user) {
        return userMapper.insertSelective(user);
    }
    //删除用户
    @Transactional
    @Override
    public int deleteUser(String userId) {
        return userMapper.deleteByPrimaryKey(userId);
    }
    //修改用户
    @Transactional
    @Override
    public int updateUser(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }
    //查询用户是否存在
    @Override
    public int selectByUserId(String userId) {
        return userMapper.countByUserId(userId);
    }
    //模糊查询
    @Override
    public List<User> getByLike(User user) {
        return userMapper.selectByLike(user);
    }
    //通过密码进行登录验证
    @Override
    public String verifyUserByPassword(String userId) {
        return userMapper.userPassword(userId);
    }

    //通过id获取一个用户的信息
    @Override
    public User getOneUserById(String userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    /**
     * excel表批量导入用户
     * @param fileName
     * @param file
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    @Override
    public boolean bulkImport(String fileName, MultipartFile file) throws Exception {

        boolean status = false;
        List<User> userList = new ArrayList<>();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            LOG.info("上传文件格式不正确");
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream is = file.getInputStream();
        Workbook wb;
        if (isExcel2003) {
            //操作Excel2003以前（包括2003）的版本，扩展名是.xls
            wb = new HSSFWorkbook(is);
        } else {
            //操作Excel2007后的版本，扩展名是.xlsx
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if(sheet!=null){
            status = true;
        }
        User user;
        //循环每行，获取各列的值
        for(int r=1;r<=sheet.getLastRowNum();r++){
            Row row = sheet.getRow(r);
            if (row == null){
                continue;
            }
            user = new User();

            if (row.getCell(0).getCellType() != 1){
                LOG.info("导入失败:(第"+(r+1)+"行)");
            }

            row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);

            String id = UUIDUtil.createUserId();
            String password = "abcd1234";
            String name = row.getCell(0).getStringCellValue();
            String sex = row.getCell(1).getStringCellValue();
            String age = row.getCell(2).getStringCellValue();
            String phone = row.getCell(3).getStringCellValue();
            String dept = row.getCell(4).getStringCellValue();
            String create_time = TimeUtil.createTime();
            String address = row.getCell(5).getStringCellValue();
            String image = "image";

            user.setUserId(id);
            user.setUserPassword(password);
            user.setUserName(name);
            user.setUserSex(sex);
            user.setUserAge(age);
            user.setUserPhone(phone);
            user.setDept(dept);
            user.setUserCreateTime(create_time);
            user.setUserAddress(address);
            user.setUserImage(image);

            userList.add(user);
        }
        for (User userRecord : userList){
            String userId = userRecord.getUserId();
            int cnt = userMapper.countByUserId(userId);
            if(cnt == 0){
                userMapper.insert(userRecord);
                LOG.info("新增一个用户");
            }else{
                userMapper.updateByPrimaryKey(userRecord);
                LOG.info("UUID中奖啦！更新一个用户");
            }
        }
        return status;
    }

    /**
     * 数据库用户excel表导出
     * @param response
     * @return
     * @throws Exception
     */
    public String exportUserFromDB(HttpServletResponse response) throws Exception {
        String status = "success";
        String time = TimeUtil.todayStringTime();
        String filename = "user_info_"+time+".xlsx"; //XSSFWorkbook为导出excel2007版及以上的
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("用户信息");
        createTitle(workbook,sheet);
        List<User> allUser = userMapper.selectAll();

        //新增数据行，并且设置单元格数据
        int rowNum=1;
        for (User user: allUser) {
            XSSFRow row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(user.getUserId());
            row.createCell(1).setCellValue(user.getUserPassword());
            row.createCell(2).setCellValue(user.getUserName());
            row.createCell(3).setCellValue(user.getUserSex());
            row.createCell(4).setCellValue(user.getUserAge());
            row.createCell(5).setCellValue(user.getUserPhone());
            row.createCell(6).setCellValue(user.getDept());
            row.createCell(7).setCellValue(user.getUserCreateTime());
            row.createCell(8).setCellValue(user.getUserAddress());
            row.createCell(9).setCellValue(user.getUserImage());
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
        sheet.setColumnWidth(0,10*256);
        sheet.setColumnWidth(1,18*256);
        sheet.setColumnWidth(2,12*256);
        sheet.setColumnWidth(3,10*256);
        sheet.setColumnWidth(4,10*256);
        sheet.setColumnWidth(5,15*256);
        sheet.setColumnWidth(6,10*256);
        sheet.setColumnWidth(7,20*256);
        sheet.setColumnWidth(8,20*256);
        sheet.setColumnWidth(9,20*256);

        //设置为加粗
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        style.setFont(font);

        XSSFCell cell;
        cell = row.createCell(0);
        cell.setCellValue("user_id");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue("user_password");
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue("user_name");
        cell.setCellStyle(style);

        cell = row.createCell(3);
        cell.setCellValue("user_sex");
        cell.setCellStyle(style);

        cell = row.createCell(4);
        cell.setCellValue("user_age");
        cell.setCellStyle(style);

        cell = row.createCell(5);
        cell.setCellValue("user_phone");
        cell.setCellStyle(style);

        cell = row.createCell(6);
        cell.setCellValue("dept");
        cell.setCellStyle(style);

        cell = row.createCell(7);
        cell.setCellValue("user_create_time");
        cell.setCellStyle(style);

        cell = row.createCell(8);
        cell.setCellValue("user_address");
        cell.setCellStyle(style);

        cell = row.createCell(9);
        cell.setCellValue("user_image");
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
    }


}
