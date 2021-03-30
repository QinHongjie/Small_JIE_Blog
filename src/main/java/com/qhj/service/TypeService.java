package com.qhj.service;

import com.qhj.pojo.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by QHJ on 2021/3/25
 */
public interface TypeService {

    Type saveTypes(Type type);

    Type getType(Long id);

    Type getTypeByName(String name);

    Page<Type> listPage(Pageable pageable);

    List<Type> listType();

    List<Type> listTypeTop(Integer size);

    Type updateType(Long id, Type type);

    void deleteType(Long id);

}
