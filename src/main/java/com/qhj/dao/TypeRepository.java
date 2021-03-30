package com.qhj.dao;

import com.qhj.pojo.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by QHJ on 2021/3/25
 */
public interface TypeRepository extends JpaRepository<Type,Long> {

    Type findByName(String name);

    @Query("select t from t_type t")
    List<Type> findTop(Pageable pageable);

}
