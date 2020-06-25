package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/label")
public class LabelController {

    @Autowired
    private LabelService labelService;

    @Autowired
    private HttpServletRequest request;

    //查询列表
    @RequestMapping(method = RequestMethod.GET)
   public Result findAll(){
        //获取头信息
        String header = request.getHeader("Authorization");
        System.out.println(header);
        return new Result(true, StatusCode.OK,"查询成功",labelService.findAll());
   }

   //根据id查询
   @RequestMapping(value = "/{labelId}",method = RequestMethod.GET)
   public Result findById(@PathVariable("labelId") String labelId){
       return new Result(true,StatusCode.OK,"查询成功",labelService.findById(labelId));
   }

   //添加
    @RequestMapping(method = RequestMethod.POST)
    public Result add( @RequestBody Label label){
       labelService.update(label);
       return new Result(true,StatusCode.OK,"增加成功");
   }

   //修改
    @RequestMapping(value ="/{labelId}",method = RequestMethod.PUT)
    public Result update(@RequestBody Label label,@PathVariable  String labelId){
        label.setId(labelId);
        labelService.update(label);
        return new Result(true,StatusCode.OK,"修改成功");
   }

   //删除
    @RequestMapping(value ="/{labelId}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String labelId){
       labelService.deleteById(labelId);
       return new Result(true,StatusCode.OK,"删除成功"); }


     //条件查询
     @RequestMapping(value ="/search",method = RequestMethod.POST)
    public Result findSearch(@RequestBody Label label){
       List<Label> list = labelService.findSerach(label);
       return new Result(true,StatusCode.OK,"查询成功");
    }

    //分页
    @RequestMapping(value ="/search/{page}/{size}",method = RequestMethod.POST)
    public Result pageQuery(@RequestBody Label label,@PathVariable int page,@PathVariable int size){
       Page<Label> pageDate  = labelService.pageQuery(label,page,size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<Label>(pageDate.getTotalElements(),pageDate.getContent()));
    }


}
