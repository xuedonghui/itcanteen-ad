package com.tensquare.base.service;


import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LabelService {

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    public List<Label> findAll(){
        return labelDao.findAll();
    }

    public Label findById(String id){

        return labelDao.findById(id).get();
    }

    public void update(Label label){

        labelDao.save(label);
    }

    public void deleteById(String id){
        labelDao.deleteById(id);
    }




    public List<Label> findSerach(Label label) {

        return  labelDao.findAll(new Specification<Label>() {
            /**
             *
             * @param root 当前对象 也就是要把条件封装到哪个对象中. where 类名 = label.getid
             * @param query  封装关键字 比如 group by order by等
             * @param cb 用来封装条件对象 如果直接返回null 表示不需要任何条件
             *           as : 来指定类型
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                List<Predicate> list = new ArrayList();


                if(label.getLabelname()!=null && !"".equals(label.getLabelname())){
                    Predicate predicate = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                    list.add(predicate);
                }

                if(label.getState()!=null && !"".equals(label.getState())){
                    Predicate predicate = cb.equal(root.get("state").as(String.class), label.getState());//state="1"
                    list.add(predicate);
                }

                if(label.getRecommend()!=null && !"".equals(label.getRecommend())){
                    Predicate predicate = cb.equal(root.get("recommend").as(String.class), label.getRecommend());
                    list.add(predicate);

                }
                //new一个数组最为最终返回值的条件
                Predicate[] parr = new Predicate[list.size()];
                //把list直接转为数组
                parr =  list.toArray(parr);

                return cb.and(parr);
            }

        });

    }

    public Page<Label> pageQuery(Label label, int page, int size) {
        //封装分页对象
        Pageable pageable = PageRequest.of(page-1,size);
        return labelDao.findAll(new Specification<Label>() {
            /**
             *
             * @param root 当前对象 也就是要把条件封装到哪个对象中. where 类名 = label.getid
             * @param query  封装关键字 比如 group by order by等
             * @param cb 用来封装条件对象 如果直接返回null 表示不需要任何条件
             *           as : 来指定类型
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                List<Predicate> list = new ArrayList();


                if(label.getLabelname()!=null && !"".equals(label.getLabelname())){
                    Predicate predicate = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                    list.add(predicate);
                }

                if(label.getState()!=null && !"".equals(label.getState())){
                    Predicate predicate = cb.equal(root.get("state").as(String.class), label.getState());//state="1"
                    list.add(predicate);
                }

                if(label.getRecommend()!=null && !"".equals(label.getRecommend())){
                    Predicate predicate = cb.equal(root.get("recommend").as(String.class), label.getRecommend());
                    list.add(predicate);

                }
                //new一个数组最为最终返回值的条件
                Predicate[] parr = new Predicate[list.size()];
                //把list直接转为数组
                parr =  list.toArray(parr);

                return cb.and(parr);
            }

        },pageable);
    }
}
