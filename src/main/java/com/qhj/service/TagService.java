package com.qhj.service;

import com.qhj.pojo.Tag;
import com.qhj.pojo.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by QHJ on 2021/3/26
 */
public interface TagService {

    Tag saveTags(Tag tag);

    Tag getTag(Long id);

    Tag getTagByName(String name);

    Page<Tag> listPage(Pageable pageable);

    List<Tag> listTag();

    List<Tag> listTagTop(Integer size);

    List<Tag> listTag(String ids);

    Tag updateTag(Long id, Tag tag);

    void deleteTag(Long id);

}
