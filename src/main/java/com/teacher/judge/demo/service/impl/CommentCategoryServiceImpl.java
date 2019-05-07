package com.teacher.judge.demo.service.impl;

import com.teacher.judge.demo.bo.CommentCategory;
import com.teacher.judge.demo.dao.CommentCategoryDao;
import com.teacher.judge.demo.dao.CommentInfoDao;
import com.teacher.judge.demo.enums.Constant;
import com.teacher.judge.demo.service.CommentCategoryService;
import com.teacher.judge.demo.util.ApplyUtil;
import com.teacher.judge.demo.vo.CommentCategoryVo;
import com.teacher.judge.demo.vo.CommentInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
public class CommentCategoryServiceImpl implements CommentCategoryService {
    @Autowired
    private CommentCategoryDao commentCategoryDao;
    @Autowired
    private CommentInfoDao commentInfoDao;

    @Override
    @Cacheable(value = "CommentCategory")
    public List<CommentCategoryVo> getAllCommon() {
        List<CommentCategory> category = commentCategoryDao.findAll();
        List<CommentCategoryVo> resultList = new ArrayList<>();
        List<CommentInfoVo> infoVoList = null;
        if (category != null) {
            for (CommentCategory cate : category) {
                CommentCategoryVo vo = new CommentCategoryVo();
                // 复制主类
                BeanUtils.copyProperties(cate, vo);
                // 复制详情类
                infoVoList = ApplyUtil.commentInfoList2VoList(commentInfoDao.findAllByParentType(cate.getCategoryType()));
                vo.setCommentInfoVoList(infoVoList);
                resultList.add(vo);
            }
            return resultList;
        } else {
            return null;
        }
    }

    @Override
    public int getTotalQuestionScope(){
        return commentInfoDao.countAllByValid(Constant.YES.getValue());
    }
}
