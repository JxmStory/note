package com.sh.controller;

import com.sh.common.MyException;
import com.sh.common.Result;
import com.sh.entity.User;
import com.sh.entity.UserXls;
import com.sh.excel.ExcelFormat;
import com.sh.excel.ExcelUtils;
import com.sh.juc.UserService;
import com.sh.service.inter.UserServiceInter;
import io.swagger.annotations.*;
import jxl.format.Colour;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Date: 2018-11-24 15:41
 * @Author: micomo
 */
@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
@Api(value = "数据库读写分离", tags = { "数据库读写分离操作Api文档" })
public class UserController {

    @Autowired
    private UserServiceInter userService;

    @Autowired
    private UserService jucUserService;

    /**
     *  service的异常会向上抛到controller
     *  全局异常处理类GlobalException会捕捉controller中的异常
     *  自定义处理异常后直接响应请求
     *
     *  如果没有异常 controller直接响应数据
     */
    @GetMapping("/getMaster")
    @ApiOperation(value = "通过id获取主数据库的用户信息", notes = "通过id获取主数据库的用户信息")
    @ApiImplicitParam(name = "id", value = "用户id", dataType = "int", paramType = "query")
    public Result getMaster(Integer id) {
        return userService.getMaster(id);
    }

    /**
     * controller自行捕捉异常并处理
     * 全局异常处理类GlobalException就捕捉不到controller中的异常
     */
    @GetMapping("/getSlave")
    @ApiOperation(value = "通过id获取从数据库的用户信息", notes = "通过id获取从数据库的用户信息")
    @ApiImplicitParam(name = "id", value = "用户id", dataType = "int", paramType = "query")
    public Result getSlave(Integer id) {
        Result result = new Result();
        try {
            result = userService.getSlave(id);
        } catch (MyException e) {
            result = Result.fail(500, e.getMsg());
        }
        return result;
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改用户信息", notes = "修改用户信息")
    public Result update(User user) {
        return userService.update(user);
    }

    @GetMapping("/list")
    @ApiOperation(value = "获取用户列表", notes = "获取用户列表")
    public Result list(){
        return userService.list();
    }

    @GetMapping(path = "/exportExcel")
    @ApiOperation(value = "导出用户信息Excel", notes = "导出用户信息Excel")
    public void exportExcel(HttpServletResponse response) {
        try {
            String fileName = "用户信息列表";
            ExcelFormat ef = new ExcelFormat(response, fileName);
            String[] titleName = new String[]{"用户id", "用户名", "密码", "年龄" };
            ef.addTitle(titleName, Colour.GREEN);
            Object[] content = new Object[titleName.length];
            List<User> list = userService.findAll();
            for (int i = 0;  i < list.size(); i++) {
                content[0] = list.get(i).getId();
                content[1] = list.get(i).getUsername();
                content[2] = list.get(i).getPassword();
                content[3] = list.get(i).getAge();
                ef.addContent(content);
            }
            ef.export();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @PostMapping(value = "/importExcel",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "导入用户信息Excel", notes = "导入用户信息Excel")
    @ApiParam(name = "file", value = "上传的文件", required = true)
    public void importExcel(@RequestPart("file") MultipartFile file) {
        List<UserXls> list = ExcelUtils.importExcel(file, UserXls.class);
        for(UserXls userXls : list){
            System.out.println(userXls.toString());
        }
    }

    @PostMapping(value = "/add")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "添加用户", notes = "添加用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "age", value = "年龄", dataType = "int", paramType = "query")
    })
    public Result add(User user) {
        return userService.add(user);
    }

    @PostMapping("/array")
    @ApiOperation(value = "springboot传递数组",notes = "springboot传递数组")
    public Result array(@ApiParam(name = "ids") @RequestParam("ids") Integer[] ids,
                      @ApiParam(name = "strs") @RequestParam("strs") String[] strs){
        List<Object> list = new ArrayList<>();
        list.add(ids);
        list.add(strs);
        return Result.success(list);
    }

    @GetMapping("/update1")
    public Result update1() {
        return jucUserService.update1();
    }

    @GetMapping("/update2")
    public Result update2() {
        return jucUserService.update2();
    }
}
