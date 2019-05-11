package com.teacher.judge.demo.controller;

import com.teacher.judge.demo.bean.RankParam;
import com.teacher.judge.demo.bean.Result;
import com.teacher.judge.demo.bo.User;
import com.teacher.judge.demo.bo.UserCourse;
import com.teacher.judge.demo.enums.ResultEnum;
import com.teacher.judge.demo.exception.TeachException;
import com.teacher.judge.demo.service.CourseService;
import com.teacher.judge.demo.service.JudgeService;
import com.teacher.judge.demo.service.UserCourseService;
import com.teacher.judge.demo.service.UserService;
import com.teacher.judge.demo.util.ApplyUtil;
import com.teacher.judge.demo.vo.TeacherCourseVo;
import com.teacher.judge.demo.vo.UserCourseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/query")
@Api(value = "查询控制器")
@Slf4j
public class QueryController {
    @Autowired
    private JudgeService judgeService;
    @Autowired
    private UserCourseService userCourseService;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;

    @PostMapping(value = "/rank")
    @ApiOperation(value = "评价教师", notes = "提交数据评价教师")
    @ApiImplicitParam(paramType = "header", name = "token", value = "token", required = true, dataType = "String")
    public Result judge(@Valid @RequestBody RankParam rankParam, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info("提示参数:{}",bindingResult.getFieldError().getDefaultMessage());
            throw new TeachException(ResultEnum.PARAM_NOT_EXIST);
        }
        return ApplyUtil.success(judgeService.getRank(rankParam));
    }

    // 获取待评价的数量
    @GetMapping(value = "/toDoJudge")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "token值", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, dataType = "String")
    })
    public Result toDoJudge(@RequestParam(value = "userId") String userId) {
        List<TeacherCourseVo> list = userCourseService.getTeacherByUserId(userId);
        int count = 0;
        for (TeacherCourseVo vo : list) {
            if (!vo.getJudgeFlag()) {
                count++;
            }
        }
        return ApplyUtil.success(count);
    }

    @GetMapping(value = "/permission")
    @ApiOperation(value = "获取所有的权限列表", notes = "获取所有的权限列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "token值", required = true, dataType = "String")
    })
    public Result getPermission() {
        List<UserCourse> userCourseList = userCourseService.findUserCourseList();
        List<UserCourseVo> courseVoList = new ArrayList<>();
        for (UserCourse uc : userCourseList){
            UserCourseVo vo = new UserCourseVo();
            BeanUtils.copyProperties(uc, vo);
            User user = userService.findById(uc.getUserId());
            User teacher = userService.findById(uc.getTeacherId());
            vo.setUserName(user.getUserName());
            vo.setUserNikeName(user.getNikeName());
            vo.setTeacherName(teacher.getUserName());
            vo.setTeacherNikeName(teacher.getNikeName());
            vo.setCourseName(courseService.getAllCourse().get(uc.getCourseId()));
            courseVoList.add(vo);
        }
        return ApplyUtil.success(courseVoList);
    }

}
