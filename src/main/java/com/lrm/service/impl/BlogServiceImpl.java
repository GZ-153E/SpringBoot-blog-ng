package com.lrm.service.impl;

import com.lrm.NotFoundException;
import com.lrm.dao.BlogRepository;
import com.lrm.po.Blog;
import com.lrm.po.Type;
import com.lrm.service.BlogService;
import com.lrm.util.MarkdownUtils;
import com.lrm.util.MyBeanUtils;
import com.lrm.vo.BlogQuery;
import org.hibernate.validator.constraints.br.TituloEleitoral;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    public Blog getBlog(Long id) {
        return blogRepository.findOne(id);
    }

    @Transactional
    public Blog getAndConvert(Long id) {
        Blog blog = blogRepository.findOne(id);
        if(blog == null){
            throw new NotFoundException("该博客不存在！");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        blogRepository.updateViews(id);
        return b;
    }

    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (blog.getTitle() != null) {
                    predicates.add(cb.like(root.<String>get("title"), "%" + blog.getTitle() + "%"));
                }
                if (blog.getTypeId() != null) {
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if (blog.isRecommend()) {
                    predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"), tagId);
            }
        }, pageable);
    }

    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query,pageable);
    }

    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"updateTime");
        Pageable pageable = new PageRequest(0,size,sort);
        return blogRepository.findTop(pageable);
    }

    @Transactional
    public Blog saveBlog(Blog blog) {
        if(blog.getId() == null){
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }else{
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }

    @Transactional
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepository.findOne(id);
        if(null == b){
            throw new NotFoundException("该博客不存在！");
        }
        BeanUtils.copyProperties(blog,b,MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return blogRepository.save(b);
    }

    @Transactional
    public void deleteBlog(Long id) {
        blogRepository.delete(id);
    }

    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogRepository.findGroupYear();
        Map<String,List<Blog>> map = new HashMap<>();
        for(String year : years){
            map.put(year,blogRepository.findByYear(year));
        }
        return map;
    }

    public Long countBlog(){
        return blogRepository.count();
    }
}
