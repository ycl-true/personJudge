package com.teacher.judge.demo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.teacher.judge.demo.bean.Result;
import com.teacher.judge.demo.bo.CommentInfo;
import com.teacher.judge.demo.bo.User;
import com.teacher.judge.demo.enums.Constant;
import com.teacher.judge.demo.enums.ResultEnum;
import com.teacher.judge.demo.exception.TeachException;
import com.teacher.judge.demo.vo.CommentInfoVo;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    // array[0] 求某个属性的值或者所有属性的总和
    // array[1] 求答题总分
    public static int[] getScope(String json, String property){
        int[] arrray = new int[6];
        // 手动解析
        JSONObject jsonObject = JSON.parseObject(json);
        Map<String, Object> map = jsonObject.getInnerMap();
        // 某大类答题总分（每题满分10分）
        arrray[1] = map.size() * 10;
        for(Map.Entry<String, Object> entry : map.entrySet()){
            // 有字段取字段的值，没有计算和
            if(property != null && !property.isEmpty()){
                if(property.equals(entry.getKey())){
                    arrray[0] = Integer.parseInt(entry.getValue().toString());
                    break;
                }
            } else {
                arrray[0] += Integer.parseInt(entry.getValue().toString());
            }
        }
        return arrray;
    }

    public static void main(String[] args){
        String json = "{\"A01\":1,\"A02\":2,\"A03\":3}";
//        System.out.println(getScope(json,"A02")[0]);
//        System.out.println(getScope(json,"A02")[1]);
//        System.out.println(getScope(json,null)[1]);
        BigDecimal b1 = new BigDecimal(Double.toString(12));


        System.out.println((MathUtil.div(13,30)*MathUtil.div(13,30)));
        System.out.println(MathUtil.mul(MathUtil.div(13,30),MathUtil.div(13,30)));
    }
}
