package com.enreach.ssm.service.impl;

import com.enreach.ssm.dao.TagMapper;
import com.enreach.ssm.entity.Tag;
import com.enreach.ssm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public Tag findOrAddByName(String name) {

        Tag findTag = tagMapper.getByName(name);
        if (findTag != null) {
            return findTag;
        } else {
            //insert
            Tag newTag = new Tag();
            newTag.setTagName(name);
            newTag.setCreateTime(new Date());
            newTag.setCreator("lizhi");
            tagMapper.insertSelective(newTag);

            return newTag;
        }

    }
}
