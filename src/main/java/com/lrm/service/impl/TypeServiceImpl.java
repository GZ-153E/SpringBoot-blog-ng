package com.lrm.service.impl;

import com.lrm.NotFoundException;
import com.lrm.dao.TypeRepository;
import com.lrm.po.Type;
import com.lrm.service.TypeService;
import org.hibernate.validator.constraints.br.TituloEleitoral;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeRepository typeRepository;

    @Transactional
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    public Type getType(Long id) {
        return typeRepository.findOne(id);
    }

    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Transactional
    public Type updateType(Long id, Type type) {
        Type one = typeRepository.findOne(id);
        if(null == one){
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(type,one);
        return typeRepository.save(one);
    }

    @Transactional
    public void deleteType(Long id) {
        typeRepository.delete(id);
    }

    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    public List<Type> listType() {
        return typeRepository.findAll();
    }

    public List<Type> listTypeTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = new PageRequest(0,size,sort);
        return typeRepository.findTop(pageable);
    }
}
