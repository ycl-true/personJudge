package com.teacher.judge.demo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.teacher.judge.demo.bean.Result;
import com.teacher.judge.demo.bo.CommentInfo;
import com.teacher.judge.demo.bo.User;
import com.teacher.judge.demo.enums.Constant;
import com.teacher.judge.demo.enums.ResultEnum;
import com.teacher.judge.demo.vo.CommentInfoVo;
import org.springframework.beans.BeanUtils;

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
            throw new RuntimeException("没有对应的人员类型");
        }
    }

    public static String getMsgOfPersonType(String type){
        String types = getPersonType(type);
        String msg = null;
        if(types.equals(Constant.TEACHER.getValue())){
            msg = "教师";
        } else if(types.equals(Constant.STUDENT.getValue())){
            msg = "学生";
        } else if(types.equals(Constant.PROFESSIONAL.getValue())){
            msg = "专家";
        }
        return msg;
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

    // 获取留言点赞状态
    public static String getLikedType(String jsonStr, String userId){
        if(!jsonStr.contains(userId)){
            return Constant.LIKE_BALANCE.getValue();
        }
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        JSONArray likeArray = jsonObject.getJSONArray("like");
        JSONArray disLikeArray = jsonObject.getJSONArray("dislike");
        if(likeArray.contains(userId)){
            return Constant.LIKE_AGREE.getValue();
        }
        if(disLikeArray.contains(userId)){
            return Constant.LIKE_DISAGREE.getValue();
        }
        return null;
//        array.remove(1);
//        array.add("3333333");
//        System.out.println(jsonObject.toString());
//        System.out.println(jsonObject.toJSONString());
    }

    public static Object[] getChangedInfo(String jsonStr, String type, String userId){
        Object[] result = new Object[3];
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        JSONArray likeArray = jsonObject.getJSONArray("like");
        JSONArray disLikeArray = jsonObject.getJSONArray("dislike");
        if(type.equals(Constant.LIKE_1.getValue())){
            if(!likeArray.contains(userId)){
                likeArray.add(userId);
            }
        } else if(type.equals(Constant.LIKE_2.getValue())){
            if(likeArray.contains(userId)){
                likeArray.remove(userId);
            }
        } else if(type.equals(Constant.LIKE_3.getValue())){
            if(!disLikeArray.contains(userId)){
                disLikeArray.add(userId);
            }
        } else if(type.equals(Constant.LIKE_4.getValue())){
            if(disLikeArray.contains(userId)){
                disLikeArray.remove(userId);
            }
        }
        result[0] = likeArray.size();
        result[1] = disLikeArray.size();
        result[2] = jsonObject.toString();
        return result;
    }

    public static void main(String[] args){
        String json = "{\"like\":[\"11111\",\"22222222\"],\"dislike\":[]}";
    }
}
