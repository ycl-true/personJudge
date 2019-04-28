package com.teacher.judge.demo.util;

import com.teacher.judge.demo.bean.Result;
import com.teacher.judge.demo.bo.CommentInfo;
import com.teacher.judge.demo.bo.User;
import com.teacher.judge.demo.enums.Constant;
import com.teacher.judge.demo.enums.ResultEnum;
import com.teacher.judge.demo.exception.TeachException;
import com.teacher.judge.demo.vo.CommentInfoVo;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApplyUtil {

    public static Result getResult(Integer code, String msg, Object data) {
        return new Result(code, msg, data);
    }
    public static Result success(Object data){
        return new Result(ResultEnum.SUCCESS, data);
    }
    public static Result success(){
        return new Result(ResultEnum.SUCCESS);
    }

    public static String getPersonType(String type){
        if(Arrays.asList(Constant.TEACHER.getValue(), Constant.STUDENT.getValue(), Constant.PROFESSIONAL.getValue()
        ).contains(type)){
            return type;
        } else {
            throw new TeachException(ResultEnum.TOKEN_NOT_EXIST);
        }
    }

    public static List<CommentInfoVo> commentInfoList2VoList(List<CommentInfo> infoList){
        List<CommentInfoVo> voList = new ArrayList<>();
        for(CommentInfo info : infoList){
            CommentInfoVo vo = new CommentInfoVo();
            BeanUtils.copyProperties(info, vo);
            voList.add(vo);
        }
        return voList;
    }

    public static String getJudgeType(User judgePerson, User teacher){
        if(judgePerson.getUserId().equals(teacher.getUserId())){
            return Constant.TS.getValue();
        } else {
            return judgePerson.getPersonType() + "0";
        }
    }
}
