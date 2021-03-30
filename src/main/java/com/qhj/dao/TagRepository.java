package com.qhj.dao;

import com.qhj.pojo.Tag;
import com.qhj.pojo.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by QHJ on 2021/3/26
 */
public interface TagRepository extends JpaRepository<Tag,Long> {

    Tag findByName(String name);

    @Query("select t from t_tag t")
    List<Tag> findTop(Pageable pageable);

}
