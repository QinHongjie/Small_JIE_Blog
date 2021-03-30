package com.qhj.dao;

import com.qhj.pojo.Blog;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by QHJ on 2021/3/26
 */
public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {

    @Query("select b from t_blog b where b.recommend = true and b.published=true")
    List<Blog> findTop(Pageable pageable);

    @Query("select b from t_blog b where b.title like ?1 or b.content like ?1 and b.published=true")
    Page<Blog> findByQuery(String query, Pageable pageable);

    @Query("select b from t_blog b where b.published=true")
    Page<Blog> findPublished(Pageable pageable);

    @Transient
    @Modifying
    @Query("update t_blog b set b.views = b.views + 1 where b.id = ?1")
    int updateViews(Long id);

    // select date_forma(b.update_time, '%Y') as year from t_blog b group by year order by year DESC;
    @Query("select function('date_format', b.updateTime, '%Y') from t_blog b group by function('date_format', b.updateTime, '%Y') order by function('date_format', b.updateTime, '%Y') desc")
    List<String> findGroupYear();

    // select * from t_blog b where date_format(b.updateTime, '%Y') = '2021'
    @Query("select b from t_blog b where function('date_format', b.updateTime, '%Y') = ?1")
    List<Blog> findByYear(String year);

}
